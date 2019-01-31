package bettingshop.session;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
		Ticket ticket = new Ticket();
		boolean valid = true;
		try {
			FindIterable<Document> itUsers = userColl.find(eq("_id", body.getUserKey()));
			final User user = User.fromMongo(itUsers.first());
			String check = checkBodyValidity(body, user);
			if (check != null) {
				return Response.status(Response.Status.EXPECTATION_FAILED).entity(check).build();
			}
			final double totalOdd = body.getTotalOdd();
			final Double money = body.getSum();
			List<Bet> ticketBets = new ArrayList<Bet>();
			for (BetData bData : body.getBets()) {
				FindIterable<Document> itGames = gameColl.find(eq("_id", bData.getGameKey()));
				Game game = Game.fromMongo(itGames.first());

				valid = checkTicketValidity(bData, game);
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
			ticket.setTotalOdd(totalOdd);
			ticket.setTime(new Date());
			ticket.set_id(new ObjectId());

			userColl.updateOne(eq("_id", body.getUserKey()), set("credit", user.getCredit() - body.getSum()));

			String jsonTicket = mapper.writeValueAsString(ticket);
			ticketColl.insertOne(Document.parse(jsonTicket));

			return Response.ok(ticket).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	private boolean checkTicketValidity(BetData bData, Game game) {
		int deltaResult = game.getHomeScore() - game.getAwayScore();
		int result = bData.getBet();
		if((deltaResult > 0 && result != 1)  || 
		   (deltaResult == 0 && result != 0) ||
		   (deltaResult < 0 && result != 2)) {
			return false;
		} else {
			return true;
		}
	}

	public Response allForUser(String userId) {
		System.out.println("user" + userId);
		final String ERROR_MESSAGE = "There are no ticktes";
		final FindIterable<Document> iterable = ticketColl.find(eq("user._id", userId));
		String jsont = StreamSupport.stream(iterable.spliterator(), false).map(Document::toJson)
				.collect(Collectors.joining(", ", "[", "]"));
		;
		if (iterable == null || iterable.first() == null) {
			return Response.status(Response.Status.EXPECTATION_FAILED).entity(ERROR_MESSAGE).build();
		} else {
			// Spliterator is like list.stream()
			// map ticket object [id...], [id...] to string
			// use ',' as delimiter and '[' ,']' for prefix and suffix to create collection
			// of tickets
			final String jsonTickets = StreamSupport.stream(iterable.spliterator(), false).map(Document::toJson)
					.collect(Collectors.joining(", ", "[", "]"));
			return Response.ok(jsonTickets).build();
		}
	}

	private String checkBodyValidity(TicketData body, User user) {
		if (body.getSum() < 10) {
			return "Minimum cache amount is 10$.";
		} else if (body.getBets() == null || body.getBets().isEmpty()) {
			return "Please select at least one game.";
		} else if (body.getTotalOdd() == null || body.getTotalOdd() == 0) {
			return "Total odd is 0.00";
		} else if ((user.getCredit() - body.getSum()) < 0) {
			return "No sufficient funds.";
		} else {
			return null;
		}
	}

}
