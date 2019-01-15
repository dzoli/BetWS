package bettingshop.provider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
	
	public static MongoClient mclient;
	
	// make connection, but first run MongoDB instance!
	static{
		// connect to localhost on port 27017 (localhost is default)
		MongoClientURI uri = new MongoClientURI("mongodb+srv://admin:1234@betdbcl-tqqv1.mongodb.net/test?retryWrites=true");
		
		mclient = new MongoClient(uri);
	}
	
	
	public static MongoDatabase getDatabase(){
		MongoDatabase db = mclient.getDatabase("betdb");
		return db;
	}
	
	public static void closeConn(){
		mclient.close();
	}
	
}
