package bettingshop.provider;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

@Singleton
public class MongoConnection {
	private static final String DB_URI = 
			"mongodb+srv://admin:1234@betdbcl-tqqv1.mongodb.net/test?retryWrites=true";
	
	private MongoDatabase db = null;
	private MongoClient client = null;
	
	@PostConstruct
	public void init() {
		// connect to localhost on port 27017 (localhost is default)
		MongoClientURI uri = new MongoClientURI(DB_URI);
		
		client = new MongoClient(uri);
		db = client.getDatabase("betdb");
	}
	
	@Lock(LockType.READ)
	public MongoDatabase getDB(){
		return db;
	}

	@PreDestroy
	public void dest() {
		client.close();
	}
	
}
