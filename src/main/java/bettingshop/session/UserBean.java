package bettingshop.session;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoDatabase;

import bettingshop.entity.User;
import bettingshop.provider.MongoConnection;

@Stateless
public class UserBean {

	@Inject
	MongoConnection conn;
	
	MongoDatabase db = null;
	
	@PostConstruct
	public void init() {
		db = conn.getDB();
	}
	
	public Response save(User newUser) {
		try {
//			MongoDatabase db = MongoConnection.getDatabase();
			ObjectMapper mapper = new ObjectMapper();
			String jsonUser = mapper.writeValueAsString(newUser);
			db.getCollection("users").insertOne(Document.parse(jsonUser));
			return Response.ok(newUser).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		} finally {
//			MongoConnection.closeConn();
		}
	}
	
//	public Response find(LoginParams params) {
//		DBCollection userCol = MongoConnection.getCollection("data");
//		QueryBuilder qb = QueryBuilder.start()
//							.put("email").is(params.getEmail())
//							.and("password").is(params.getPassword());
//		DBObject queryLogin = qb.get();
//		DBCursor cursor = userCol.find(queryLogin);
//		
//		// get first user with params
//		User lUser = User.fromMongo(cursor.next());
//		return Response.ok(lUser).build();
//	}

	public static void main(String[] args) {
		UserBean tc = new UserBean();
		
		User u = new User(null, "dr", "1234", "d@d.com", "dragin", "dusan", 12.3, "user");
		Response save = tc.save(u);
		
		System.out.println(save);
		
	}
}
