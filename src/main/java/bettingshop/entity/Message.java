package bettingshop.entity;

import java.util.Date;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import bettingshop.convertors.CustomDateDeserializer;
import bettingshop.convertors.CustomDateSerializer;

public class Message {

	@JsonTypeId
	@JsonIgnore
	private ObjectId _id;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonProperty private Date created;
	
	@JsonProperty private User creator;
	@JsonProperty private String text;
	
	/**
	 * @param created
	 * @param creator
	 * @param text
	 */
	public Message(User creator, String text) {
		super();
		this.created = new Date();
		this.creator = creator;
		this.text = text;
	}

	public Message() {
		super();
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [created=");
		builder.append(created);
		builder.append(", creator=");
		builder.append(creator);
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
	
}
