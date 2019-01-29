package bettingshop.data;

import java.io.Serializable;
import java.util.List;

public class TicketData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userKey;
	private Double sum;
	private Double totalOdd;
	private List<BetData> bets;
	/**
	 * @param userKey
	 * @param sum
	 * @param bets
	 */
	public TicketData(String userKey, Double sum, List<BetData> bets) {
		super();
		this.userKey = userKey;
		this.sum = sum;
		this.bets = bets;
	}
	/**
	 * 
	 */
	public TicketData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public List<BetData> getBets() {
		return bets;
	}
	public void setBets(List<BetData> bets) {
		this.bets = bets;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TicketData [userKey=");
		builder.append(userKey);
		builder.append(", sum=");
		builder.append(sum);
		builder.append(", bets=");
		builder.append(bets);
		builder.append(", getUserKey()=");
		builder.append(getUserKey());
		builder.append(", getSum()=");
		builder.append(getSum());
		builder.append(", getBets()=");
		builder.append(getBets());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	public Double getTotalOdd() {
		return totalOdd;
	}
	public void setTotalOdd(Double totalOdd) {
		this.totalOdd = totalOdd;
	}
	
}
