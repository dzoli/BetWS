package services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bettingshop.data.LoginParams;
import bettingshop.entity.User;
import bettingshop.session.UserBean;

@Path("user")
public class UserService {

	@Inject
	UserBean userBean;
	
	@Path("/save")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(User user) throws Exception {
		return userBean.save(user);
	}
	
	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginSubmit(LoginParams userData) throws Exception {
		return userBean.find(userData);
	}
	
}
