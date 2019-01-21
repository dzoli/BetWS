package bettingshop.session;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bettingshop.entity.Team;
import bettingshop.provider.MongoConnection;
import bettingshop.util.Collections;

@Stateless
public class TicketBean {
	private ObjectMapper mapper;
	private MongoCollection<Document> collection; 
	
	@Inject
	MongoConnection conn;
	MongoDatabase db = null;
	
	@PostConstruct
	public void init() {
		mapper = new ObjectMapper();
		db = conn.getDB();
		collection = db.getCollection(Collections.TICKET);
	}
	
	public Response saveTeam(Team body) {
		body.set_id(new ObjectId());
//		collection.insertOne(eq());
		return null;
	}
	
	

}
