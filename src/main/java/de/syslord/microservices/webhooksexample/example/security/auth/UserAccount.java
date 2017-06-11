package de.syslord.microservices.webhooksexample.example.security.auth;

public class UserAccount {

	private String name;

	private String password;

	private String permissions;

	public UserAccount(String name, String password, String permissions) {
		this.name = name;
		this.password = password;
		this.permissions = permissions;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getPermissions() {
		return permissions;
	}

	@Override
	public String toString() {
		return "UserAccount [name=" + name + ", permissions=" + permissions + "]";
	}

}
