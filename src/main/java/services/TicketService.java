package services;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bettingshop.session.TicketBean;

@Path("ticket")
public class TicketService {

	@Inject
	TicketBean ticketBean;
	
	@Path("/game/{date}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response allGamesForDate(@PathParam(value = "date") String date) {
		return ticketBean.getAllGamesForDate(date);
	}
	
}
