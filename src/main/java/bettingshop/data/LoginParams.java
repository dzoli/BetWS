package bettingshop.data;

public class LoginParams {
	
	private String email;
	private String password;
	
	public LoginParams(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public LoginParams() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginParams [email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
	
}
