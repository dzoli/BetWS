package bettingshop.entity;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import bettingshop.convertors.DateDeserializer;
import bettingshop.convertors.DateSerializer;
import bettingshop.convertors.ObjectIdJsonDeserializer;
import bettingshop.convertors.ObjectIdJsonSerializer;

public class Ticket {

	@JsonDeserialize(using = ObjectIdJsonDeserializer.class)
	@JsonSerialize(using = ObjectIdJsonSerializer.class)
	private ObjectId _id;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date time;
	
	@JsonProperty private double potentionalWinnings;
	@JsonProperty private double stake;
	@JsonProperty private double totalOdd;
	@JsonProperty private boolean valid;
	@JsonProperty private User user;
	@JsonProperty private List<Bet> bets;
	
	/**
	 * @param _id
	 * @param time
	 * @param user
	 * @param potentionalWinnings
	 * @param stake
	 * @param totalOdd
	 * @param valid
	 */
	public Ticket(ObjectId _id, Date time, User user, double potentionalWinnings, double stake, double totalOdd,
			boolean valid) {
		super();
		this._id = _id;
		this.time = time;
		this.user = user;
		this.potentionalWinnings = potentionalWinnings;
		this.stake = stake;
		this.totalOdd = totalOdd;
		this.valid = valid;
	}

	public Ticket() {
		super();
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getPotentionalWinnings() {
		return potentionalWinnings;
	}

	public void setPotentionalWinnings(double potentionalWinnings) {
		this.potentionalWinnings = potentionalWinnings;
	}

	public double getStake() {
		return stake;
	}

	public void setStake(double stake) {
		this.stake = stake;
	}

	public double getTotalOdd() {
		return totalOdd;
	}

	public void setTotalOdd(double totalOdd) {
		this.totalOdd = totalOdd;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public List<Bet> getBets() {
		return bets;
	}
	
	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ticket [_id=");
		builder.append(_id);
		builder.append(", time=");
		builder.append(time);
		builder.append(", potentionalWinnings=");
		builder.append(potentionalWinnings);
		builder.append(", stake=");
		builder.append(stake);
		builder.append(", totalOdd=");
		builder.append(totalOdd);
		builder.append(", valid=");
		builder.append(valid);
		builder.append(", user=");
		builder.append(user);
		builder.append(", bets=");
		builder.append(bets);
		builder.append("]");
		return builder.toString();
	}
}
