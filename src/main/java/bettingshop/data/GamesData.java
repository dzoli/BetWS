package bettingshop.data;

import java.util.ArrayList;
import java.util.List;

import bettingshop.entity.Game;

public class GamesData {

	private List<Game> games = new ArrayList<Game>();

	/**
	 * @param games
	 */
	public GamesData(List<Game> games) {
		super();
		this.games = games;
	}
	
	public GamesData() {
		super();
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GamesData [games=");
		builder.append(games);
		builder.append(", getGames()=");
		builder.append(getGames());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
