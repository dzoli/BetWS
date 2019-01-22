package bettingshop.session;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bettingshop.data.GamesData;
import bettingshop.entity.Game;
import bettingshop.provider.MongoConnection;
import bettingshop.util.Collections;

@Stateless
public class TicketBean {
	private ObjectMapper mapper;
	private MongoCollection<Document> ticketColl; 
	private MongoCollection<Document> gamesColl; 
	 
	
	@Inject
	MongoConnection conn;
	MongoDatabase db = null;
	
	@PostConstruct
	public void init() {
		mapper = new ObjectMapper();
		db = conn.getDB();
		ticketColl = db.getCollection(Collections.TICKET);
		gamesColl = db.getCollection(Collections.GAME);
	}

	public Response getAllGamesForDate(String date) {
		List<Game> games = new ArrayList<Game>();
		FindIterable<Document> findIterable = gamesColl.find();
		try {
			for (Document document : findIterable) {
				Game game = Game.fromMongo(document);
				games.add(game);
			}
			final GamesData gd = new GamesData(games);
			return Response.ok(gd).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
