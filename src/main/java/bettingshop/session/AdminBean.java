package bettingshop.session;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bettingshop.entity.Game;
import bettingshop.entity.Team;
import bettingshop.provider.MongoConnection;
import bettingshop.util.Collections;

@Stateless
public class AdminBean {
	private ObjectMapper mapper;
	private MongoCollection<Document> teamColl;
	private MongoCollection<Document> gameColl;

	
	@Inject
	MongoConnection conn;
	MongoDatabase db = null;
	
	@PostConstruct
	public void init() {
		mapper = new ObjectMapper();
		db = conn.getDB();
		teamColl = db.getCollection(Collections.TEAM);
		gameColl = db.getCollection(Collections.GAME);
	}
	
	public Response saveTeam(Team body) {
		body.set_id(new ObjectId());
		try {
			final String jsonTeam = mapper.writeValueAsString(body);
			teamColl.insertOne(Document.parse(jsonTeam));
			return Response.ok(body).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	public Response saveGame(Game body) {
		body.set_id(new ObjectId());
		body.setTime(new Date());
		try {
			final String jsonGame = mapper.writeValueAsString(body);
			gameColl.insertOne(Document.parse(jsonGame));
			return Response.ok(body).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
}
