package services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bettingshop.entity.Team;
import bettingshop.session.TicketBean;

@Path("ticket")
public class TicketService {

	@Inject
	TicketBean ticketBean;
	
	@Path("/save/team")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveTeam(Team body) throws Exception {
		return ticketBean.saveTeam(body);
	}
	
}
