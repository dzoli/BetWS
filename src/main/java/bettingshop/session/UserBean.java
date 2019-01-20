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
import bettingshop.entity.User;
import bettingshop.provider.MongoConnection;

@Stateless
public class UserBean {
	private static final String COLL_NAME = "users";
	private ObjectMapper mapper;
	
	@Inject
	MongoConnection conn;
	MongoDatabase db = null;
	
	@PostConstruct
	public void init() {
		mapper = new ObjectMapper();
		db = conn.getDB();
	}
	
	public Response save(User newUser) {
		try {
			newUser.set_id(new ObjectId());
			String jsonUser = mapper.writeValueAsString(newUser);
			db.getCollection(COLL_NAME).insertOne(Document.parse(jsonUser));
			return Response.ok(newUser).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	public Response find(LoginParams params) {
		final MongoCollection<Document> collection = db.getCollection(COLL_NAME);
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

}
