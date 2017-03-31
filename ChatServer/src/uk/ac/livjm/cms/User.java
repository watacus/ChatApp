package uk.ac.livjm.cms;

public class User {
	
	protected String username;
	
	public User(String username) {
		this.username = username;
	}
	public String toString() {
		return "\nusername: " + username + "\n";
	}
	public String getName() {
		return this.username;
	}
	@Override
	public boolean equals(Object other) {
		if(other instanceof User) {
			if(other == null || !(other instanceof User)) {
				return false;
			}
			
			if(other == this) {
				return true;
			}
			
			User otherUser = (User)other;
			
			return (this.username.equals(otherUser.username));
		}
		return false;
	}
}

