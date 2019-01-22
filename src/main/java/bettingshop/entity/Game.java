package bettingshop.entity;

import java.text.ParseException;
import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import bettingshop.convertors.DateDeserializer;
import bettingshop.convertors.DateSerializer;
import bettingshop.convertors.ObjectIdJsonDeserializer;
import bettingshop.convertors.ObjectIdJsonSerializer;
import bettingshop.util.TimeUtils;

public class Game {

	@JsonDeserialize(using = ObjectIdJsonDeserializer.class)
	@JsonSerialize(using = ObjectIdJsonSerializer.class)
	private ObjectId _id;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date time;
	
	@JsonProperty private Double awayOdd;
	@JsonProperty private Double homeOdd;
	@JsonProperty private Double egalOdd;
	@JsonProperty private int awayScore;
	@JsonProperty private int homeScore;
	@JsonProperty private Team team1;
	@JsonProperty private Team team2;
	
	/**
	 * @param time
	 * @param awayOdd
	 * @param homeOdd
	 * @param egalOdd
	 * @param awayScore
	 * @param homeScore
	 * @param team1
	 * @param team2
	 */
	public Game(Date time, Double awayOdd, Double homeOdd, Double egalOdd, int awayScore, int homeScore, Team team1,
			Team team2) {
		super();
		this.time = time;
		this.awayOdd = awayOdd;
		this.homeOdd = homeOdd;
		this.egalOdd = egalOdd;
		this.awayScore = awayScore;
		this.homeScore = homeScore;
		this.team1 = team1;
		this.team2 = team2;
	}

	public Game() {
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

	public Double getAwayOdd() {
		return awayOdd;
	}

	public void setAwayOdd(Double awayOdd) {
		this.awayOdd = awayOdd;
	}

	public Double getHomeOdd() {
		return homeOdd;
	}

	public void setHomeOdd(Double homeOdd) {
		this.homeOdd = homeOdd;
	}

	public Double getEgalOdd() {
		return egalOdd;
	}

	public void setEgalOdd(Double egalOdd) {
		this.egalOdd = egalOdd;
	}

	public int getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(int awayScore) {
		this.awayScore = awayScore;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Game [_id=");
		builder.append(_id);
		builder.append(", time=");
		builder.append(time);
		builder.append(", awayOdd=");
		builder.append(awayOdd);
		builder.append(", homeOdd=");
		builder.append(homeOdd);
		builder.append(", egalOdd=");
		builder.append(egalOdd);
		builder.append(", awayScore=");
		builder.append(awayScore);
		builder.append(", homeScore=");
		builder.append(homeScore);
		builder.append(", team1=");
		builder.append(team1);
		builder.append(", team2=");
		builder.append(team2);
		builder.append("]");
		return builder.toString();
	}

	public static Game fromMongo(Document document) throws ParseException {
		Game game = new Game();
		game.set_id(new ObjectId(document.getString("_id")));
		game.setTime(TimeUtils.DATE_FORMAT.parse(document.getString("time")));
		game.setAwayOdd(document.getDouble("awayOdd"));
		game.setHomeOdd(document.getDouble("homeOdd"));
		game.setEgalOdd(document.getDouble("egalOdd"));
		game.setAwayScore(document.getInteger("awayScore"));
		game.setHomeScore(document.getInteger("homeScore"));
		game.setTeam1(Team.fromMongo(document.get("team1", Document.class)));
		game.setTeam2(Team.fromMongo(document.get("team2", Document.class)));
		return game;
	}
	
}
