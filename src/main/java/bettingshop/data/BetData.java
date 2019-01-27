package bettingshop.data;

public class BetData {

	private String gameKey;
	private int bet;
	/**
	 * 
	 */
	public BetData() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param gameKey
	 * @param bet
	 */
	public BetData(String gameKey, int bet) {
		super();
		this.gameKey = gameKey;
		this.bet = bet;
	}
	public String getGameKey() {
		return gameKey;
	}
	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
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
		builder.append("BetData [gameKey=");
		builder.append(gameKey);
		builder.append(", bet=");
		builder.append(bet);
		builder.append("]");
		return builder.toString();
	}
}
