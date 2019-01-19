package services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bettingshop.entity.Message;
import bettingshop.entity.Topic;
import bettingshop.session.ForumBean;

@Path("forum")
public class ForumService {

	@Inject
	ForumBean forumBean;
	
	@Path("/all")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response all() {
		return forumBean.all();
	}
	
	@Path("/save")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(Topic topic) {
		return forumBean.saveTopicAndSync(topic);
	}
	
	@Path("/{topicId}/save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveMessageAndSync(@PathParam(value = "topicId") String topicId, Message message) {
		return forumBean.saveMessageAndSync(topicId, message);
	}
}
