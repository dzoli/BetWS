package bettingshop.session;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

import bettingshop.entity.Message;
import bettingshop.entity.Topic;
import bettingshop.entity.User;
import bettingshop.provider.MongoConnection;
import bettingshop.util.Collections;

@Stateless
public class ForumBean {
	private final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
	private final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.US);

	@Inject
	MongoConnection conn;
	MongoDatabase db = null;

	@PostConstruct
	public void init() {
		db = conn.getDB();
	}

	
	@SuppressWarnings("unchecked")
	public Response all() {
		List<Topic> resultList = new ArrayList<Topic>();
		// fetch all data
		try {
			MongoCollection<Document> collection = db.getCollection(Collections.FORUM);
			FindIterable<Document> findIterable = collection.find();
			for (Document document : findIterable) {
				Topic t = new Topic();
				t.setCreated(DATE_FORMAT.parse(document.get("created", String.class)));
				t.setCreator(User.fromMongo(document.get("creator", Document.class)));
				t.setDescription(document.get("description", String.class));
				t.setName(document.get("name", String.class));
				t.set_id(new ObjectId(document.get("_id", String.class)));
				List<Message> messages = new ArrayList<Message>();
				List<Document> mDocument = (List<Document>) document.get("messages");
				if (mDocument != null && !mDocument.isEmpty()) {
					for (Document msg : mDocument) {
						Message m = Message.fromMongo(msg);
						messages.add(m);
					}
					t.setMessages(messages);
				}
				resultList.add(t);
			}
			return Response.ok(resultList).build();
		} catch (ParseException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@SuppressWarnings("unchecked")
	public Response saveMessageAndSync(String topicId, Message message) {
		ObjectMapper mapper = new ObjectMapper();
		List<Topic> resultList = new ArrayList<Topic>();
		try {
			message.setCreated(new Date());
			message.set_id(new ObjectId());
			String jsonMsg = mapper.writeValueAsString(message);
			MongoCollection<Document> collection = db.getCollection(Collections.FORUM);
			collection.updateOne(eq("_id", topicId), push("messages", Document.parse(jsonMsg)));

			// fetch all data
			FindIterable<Document> findIterable = collection.find();
			for (Document document : findIterable) {
				Topic t = new Topic();
				t.setCreated(DATE_FORMAT.parse(document.get("created", String.class)));
				t.setCreator(User.fromMongo(document.get("creator", Document.class)));
				t.setDescription(document.get("description", String.class));
				t.setName(document.get("name", String.class));
				t.set_id(new ObjectId(document.get("_id", String.class)));
				List<Message> messages = new ArrayList<Message>();
				List<Document> mDocument = (List<Document>) document.get("messages");
				if (mDocument != null && !mDocument.isEmpty()) {
					for (Document msg : mDocument) {
						Message m = Message.fromMongo(msg);
						messages.add(m);
					}
					t.setMessages(messages);
				}
				resultList.add(t);
			}
			return Response.ok(resultList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@SuppressWarnings("unchecked")
	public Response saveTopicAndSync(Topic topic) {
		ObjectMapper mapper = new ObjectMapper();
		List<Topic> resultList = new ArrayList<Topic>();
		try {
			topic.set_id(new ObjectId());
			if (topic.getMessages() == null || topic.getMessages().isEmpty()) {
				topic.setMessages(new ArrayList<Message>());
			}
			String jsonTopic = mapper.writeValueAsString(topic);
			MongoCollection<Document> collection = db.getCollection(Collections.FORUM);
			collection.insertOne(Document.parse(jsonTopic));

			// fetch all data
			FindIterable<Document> findIterable = collection.find();
			for (Document document : findIterable) {
				Topic t = new Topic();
				t.setCreated(DATE_FORMAT.parse(document.get("created", String.class)));
				t.setCreator(User.fromMongo(document.get("creator", Document.class)));
				t.setDescription(document.get("description", String.class));
				t.setName(document.get("name", String.class));
				t.set_id(new ObjectId(document.get("_id", String.class)));
				List<Message> messages = new ArrayList<Message>();
				List<Document> mDocument = (List<Document>) document.get("messages");
				if (mDocument != null && !mDocument.isEmpty()) {
					for (Document msg : mDocument) {
						Message m = Message.fromMongo(msg);
						messages.add(m);
					}
					t.setMessages(messages);
				} 
				resultList.add(t);
			}
			return Response.ok(resultList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
