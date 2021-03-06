package services;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bettingshop.data.TicketData;
import bettingshop.session.TicketBean;

@Path("/ticket")
public class TicketService {

	@Inject
	TicketBean ticketBean;
	
	@Path("/game/{date}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response allGamesForDate(@QueryParam(value = "id") String id) {
		return ticketBean.getAllGamesForDate(id);
	}

	@Path("/save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(TicketData body) {
		return ticketBean.saveTicket(body);
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCategories(@QueryParam("id") String id) {
		return ticketBean.allForUser(id);
	}
}
