package bettingshop.session;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.Document;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.*;

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
	
	@Inject
	MongoConnection conn;
	MongoDatabase db = null;
	
	@PostConstruct
	public void init() {
		db = conn.getDB();
	}
	
	public Response save(User newUser) {
		try {
			ObjectMapper mapper = new ObjectMapper();
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
		
		// get first user with params
		User lUser = User.fromMongo(find.first());
		return Response.ok(lUser).build();
	}

	public static void main(String[] args) {
		UserBean tc = new UserBean();
		
		User u = new User(null, "dr", "1234", "d@d.com", "dragin", "dusan", 12.3, "user");
		Response save = tc.save(u);
		
		System.out.println(save);
		
	}
}
