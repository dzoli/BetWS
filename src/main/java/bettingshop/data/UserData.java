package bettingshop.data;

import bettingshop.entity.User;

public class UserData {
	
	private User user;
	private Double credit;
	
	/**
	 * @param user
	 * @param credit
	 */
	public UserData(User user, Double credit) {
		super();
		this.user = user;
		this.credit = credit;
	}
	
	public UserData() {
		super();
	}


	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserData [user=");
		builder.append(user);
		builder.append(", credit=");
		builder.append(credit);
		builder.append("]");
		return builder.toString();
	}
	
}
