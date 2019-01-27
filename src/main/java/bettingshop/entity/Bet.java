package bettingshop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bet {
	
	@JsonProperty private Game game;
	@JsonProperty private int bet;
	
	/**
	 * @param game
	 * @param bet
	 */
	public Bet(Game game, int bet) {
		super();
		this.game = game;
		this.bet = bet;
	}

	public Bet() {
		super();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bet [game=");
		builder.append(game);
		builder.append(", bet=");
		builder.append(bet);
		builder.append("]");
		return builder.toString();
	}
	
}
