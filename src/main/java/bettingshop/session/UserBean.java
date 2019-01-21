package bettingshop.session;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bettingshop.data.LoginParams;
import bettingshop.data.UserData;
import bettingshop.entity.User;
import bettingshop.provider.MongoConnection;

@Stateless
public class UserBean {
	private static final String COLL_NAME = "users";
	private ObjectMapper mapper;
	private MongoCollection<Document> collection; 
	
	@Inject
	MongoConnection conn;
	MongoDatabase db = null;
	
	@PostConstruct
	public void init() {
		mapper = new ObjectMapper();
		db = conn.getDB();
		collection = db.getCollection(COLL_NAME);
	}
	
	public Response save(User newUser) {
		try {
			newUser.set_id(new ObjectId());
			String jsonUser = mapper.writeValueAsString(newUser);
			collection.insertOne(Document.parse(jsonUser));
			return Response.ok(newUser).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	public Response find(LoginParams params) {
		FindIterable<Document> find = collection.find(and(eq("email",params.getEmail()),
						  	eq("password",params.getPassword())));
		Document first = find.first();
		try {
			return Response.ok(first.toJson()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	public Response addCredit(UserData body) {
		String id = body.getUser().get_id().toString();
		Double credit = body.getCredit();
		Document userDoc = collection.find(eq("_id", id)).first();
		User user = User.fromMongo(userDoc);
		credit += user.getCredit();
		collection.updateOne(eq("_id", id), new Document("$set", new Document("credit", credit)));
		return Response.ok(credit).build();
	}
	
}
