package bettingshop.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
	
	public static MongoClient mclient;
	
	//make conncetion, but first need manual run MongoDB instance!
	static{
		//connecect to localhost on port 27017 (localhost is default)
		mclient = new MongoClient();
	}
	
	
	public static MongoDatabase getDatabase(){
		MongoDatabase db = mclient.getDatabase("betDB");
		return db;
	}
	
	public static void closeConn(){
		mclient.close();
	}
	
}
