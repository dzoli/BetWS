package bettingshop.session;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bettingshop.data.BetData;
import bettingshop.data.GamesData;
import bettingshop.data.TicketData;
import bettingshop.entity.Bet;
import bettingshop.entity.Game;
import bettingshop.entity.Ticket;
import bettingshop.entity.User;
import bettingshop.provider.MongoConnection;
import bettingshop.util.Collections;

@Stateless
public class TicketBean {
	private ObjectMapper mapper;
	private MongoCollection<Document> ticketColl; 
	private MongoCollection<Document> gameColl; 
	private MongoCollection<Document> userColl; 
	
	
	@Inject
	MongoConnection conn;
	MongoDatabase db = null;
	
	@PostConstruct
	public void init() {
		mapper = new ObjectMapper();
		db = conn.getDB();
		ticketColl = db.getCollection(Collections.TICKET);
		gameColl = db.getCollection(Collections.GAME);
		userColl = db.getCollection(Collections.USERS);
	}

	public Response getAllGamesForDate(String date) {
		List<Game> games = new ArrayList<Game>();
		FindIterable<Document> findIterable = gameColl.find();
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
	
	public Response saveTicket(TicketData body) {
		final String ERROR_MESSAGE = "No sufficient funds.";
		Ticket ticket = new Ticket();
		double totalOdd = 1.;
		boolean valid = true;
		try {
			FindIterable<Document> itUsers = userColl.find(eq("_id",body.getUserKey()));
			final User user = User.fromMongo(itUsers.first());
			if ((user.getCredit() - body.getSum()) < 0) {
				return Response.status(Response.Status.EXPECTATION_FAILED).entity(ERROR_MESSAGE).build();
			}
			final Double money = body.getSum();
			List<Bet> ticketBets = new ArrayList<Bet>();
			for (BetData bData : body.getBets()) {
				FindIterable<Document> itGames = gameColl.find(eq("_id", bData.getGameKey()));
				Game game = Game.fromMongo(itGames.first());
				double tmpOdd = 1.;
				
				// check validity
				if ((game.getHomeScore() - game.getAwayScore()) == 0) {
					if (bData.getBet() != 0) {
						valid = false;
					}
					tmpOdd = game.getEgalOdd();
				} else if ((game.getHomeScore() - game.getAwayScore()) < 0) {
					if (bData.getBet() > 0) {
						valid = false;
					}
					tmpOdd = game.getAwayOdd();
				} else {
					if (bData.getBet() < 0) {
						valid = false;
					}
					tmpOdd = game.getHomeOdd();
				}
				totalOdd *= tmpOdd;
				
				Bet bet = new Bet();
				bet.setGame(game);
				bet.setBet(bData.getBet());
				ticketBets.add(bet);
			}
			ticket.setBets(ticketBets);
			ticket.setStake(money);
			ticket.setPotentionalWinnings(totalOdd * money);
			ticket.setValid(valid);
			ticket.setUser(user);
			ticket.setTime(new Date());
			ticket.set_id(new ObjectId());

			userColl.updateOne(eq("_id", body.getUserKey()), 
							   set("credit", user.getCredit() - body.getSum()));
			
			String jsonTicket = mapper.writeValueAsString(ticket);
			ticketColl.insertOne(Document.parse(jsonTicket));
			
			return Response.ok(ticket).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	public Response allTickets() {
		FindIterable<Document> iterable = ticketColl.find();
		for (Document document : iterable) {
			System.out.println(document);
		}
		return null;
	}

}
