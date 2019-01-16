package bettingshop.entity;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.mongodb.DBObject;

public class User {
	@JsonTypeId
	@JsonIgnore
	private ObjectId _id;
	
	@JsonProperty private String username;
	@JsonProperty private String password;
	@JsonProperty private String email;
	@JsonProperty private String lastname;
	@JsonProperty private String firstname;
	@JsonProperty("credit") private Double credit;
	@JsonProperty("role") private String role;

	/**
	 * @param _id
	 * @param username
	 * @param password
	 * @param email
	 * @param lastname
	 * @param firstname
	 * @param credit
	 * @param role
	 */
	public User(ObjectId _id, String username, String password, String email, String lastname, String firstname,
			double credit, String role) {
		super();
		this._id = _id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.lastname = lastname;
		this.firstname = firstname;
		this.credit = credit;
		this.role = role;
	}

	public User() {
		super();
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Double getCredit() {
		return credit;
	}

	@JsonSetter("credit")
	public void setCredit(Double credit) {
		if (credit == null) {
			this.setCredit(0.0);
		}
		this.credit = credit;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return role;
	}

	@JsonSetter("role")
	public void setRole(String role) {
		if (role == null) {
			this.setRole("user");
		}
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [_id=");
		builder.append(_id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);
		builder.append(", lastname=");
		builder.append(lastname);
		builder.append(", firstname=");
		builder.append(firstname);
		builder.append(", credit=");
		builder.append(credit);
		builder.append(", role=");
		builder.append(role);
		builder.append("]");
		return builder.toString();
	}
	
	public static User fromMongo(DBObject user) {
		return new User((ObjectId)user.get("_id"), 
				(String)user.get("username"), (String)user.get("password"), 
				(String)user.get("email"), (String)user.get("lastname"), 
				(String)user.get("firstname"), (Double)user.get("credit"), 
				(String)user.get("role"));
	}
}
