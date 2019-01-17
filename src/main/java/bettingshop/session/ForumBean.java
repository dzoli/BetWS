package bettingshop.session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bettingshop.entity.Message;
import bettingshop.entity.Topic;
import bettingshop.entity.User;
import bettingshop.provider.MongoConnection;
import bettingshop.util.Collections;

@Stateless
public class ForumBean {
	
	@Inject
	MongoConnection conn;
	MongoDatabase db = null;

	@PostConstruct
	public void init() {
		db = conn.getDB();
	}

	public Response saveMessageAndSync(Message message) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonMsg = mapper.writeValueAsString(message);
			db.getCollection(Collections.FORUM).insertOne(Document.parse(jsonMsg));
			
			
			return Response.ok(message).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	public Response saveTopicAndSync(Topic topic) {
		String jsonRes = "";
		Map<String, Object> resMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		List<Topic> resultList = new ArrayList<Topic>();
		try {
			String jsonMsg = mapper.writeValueAsString(topic);
			MongoCollection<Document> collection = db.getCollection(Collections.FORUM);
			collection.insertOne(Document.parse(jsonMsg));
			
			FindIterable<Document> findIterable = collection.find();
			for (Document document : findIterable) {
				Topic t = new Topic();
				t.setCreated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse((String)document.get("created")));
				Object object = document.get("creator");
				
				
				t.setCreator(User.fromMongo(document.get("creator", Document.class)));
				t.setDescription((String) document.get("description"));
				t.setName((String) document.get("name"));
//				TODO: t.setMessages(messages);
				resultList.add(t);
			}
			jsonRes = mapper.writeValueAsString(resultList);
			return Response.ok(resultList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
		
	
	
	
}
