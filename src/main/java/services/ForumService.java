package services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bettingshop.entity.Topic;
import bettingshop.session.ForumBean;

@Path("forum")
public class ForumService {

	@Inject
	ForumBean forumBean;
	
//	@Path("/save")
//	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response register(User user) throws Exception {
//		return userBean.save(user);
//	}
//	
	@Path("/save")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(Topic topic) throws Exception {
		return forumBean.saveTopicAndSync(topic);
	}
	
}
