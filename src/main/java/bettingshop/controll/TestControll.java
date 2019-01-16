package bettingshop.controll;

import javax.ws.rs.core.Response;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.client.MongoDatabase;

import bettingshop.data.LoginParams;
import bettingshop.entity.User;
import bettingshop.provider.MongoConnection;

public class TestControll {

	public Response save(User newUser) {
		try {
			MongoDatabase db = MongoConnection.getDatabase();
			ObjectMapper mapper = new ObjectMapper();
			String jsonUser = mapper.writeValueAsString(newUser);
			db.getCollection("data").insertOne(Document.parse(jsonUser));
			System.out.println(newUser);
			return Response.ok(newUser).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		} finally {
//			MongoConnection.closeConn();
		}
	}
	
	public Response find(LoginParams params) {
		DBCollection userCol = MongoConnection.getCollection("data");
		QueryBuilder qb = QueryBuilder.start()
							.put("email").is(params.getEmail())
							.and("password").is(params.getPassword());
		DBObject queryLogin = qb.get();
		DBCursor cursor = userCol.find(queryLogin);
		
		// get first user with params
		User lUser = User.fromMongo(cursor.next());
		return Response.ok(lUser).build();
	}

//	public static void main(String[] args) {
//		TestControll tc = new TestControll();
//		
//		User u = new User(null, "dr", "1234", "d@d.com", "dragin", "dusan", 12.3, "user");
//		User nu = tc.save(u);
//		
//		System.out.println(nu);
//		
//	}
}
