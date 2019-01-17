package bettingshop.entity;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import bettingshop.convertors.CustomDateDeserializer;
import bettingshop.convertors.CustomDateSerializer;

public class Topic {

	@JsonTypeId
	@JsonIgnore
	private ObjectId _id;

	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonProperty private Date created;
	@JsonProperty private User creator;
	@JsonProperty private String name;
	@JsonProperty private String description;
	@JsonProperty private List<Message> messages;
	
	/**
	 * @param name
	 * @param description
	 * @param messages
	 */
	public Topic(String name, String description, List<Message> messages) {
		super();
		this.name = name;
		this.description = description;
		this.messages = messages;
	}

	/**
	 * @param name
	 * @param description
	 */
	public Topic(String name, String description, User creator) {
		super();
		this.setCreator(creator);
		this.name = name;
		this.description = description;
	}

	public Topic() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Topic [name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", created=");
		builder.append(created);
		builder.append(", creator=");
		builder.append(creator);
		builder.append(", messages=");
		builder.append(messages);
		builder.append("]");
		return builder.toString();
	}

}
