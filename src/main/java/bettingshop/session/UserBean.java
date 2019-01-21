package bettingshop.session;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

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
import bettingshop.util.Collections;

@Stateless
public class UserBean {
	private ObjectMapper mapper;
	private MongoCollection<Document> collection; 
	
	@Inject
	MongoConnection conn;
	MongoDatabase db = null;
	
	@PostConstruct
	public void init() {
		mapper = new ObjectMapper();
		db = conn.getDB();
		collection = db.getCollection(Collections.USERS);
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
		collection.updateOne(eq("_id", id), set("credit", credit));
		return Response.ok(credit).build();
	}
	
}
