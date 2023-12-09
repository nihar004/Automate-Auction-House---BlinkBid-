package com.auction_bean;

public class Admin {
	private int admin_id;
	private String admin_name;
	private String admin_email;
	private String admin_password;

	public Admin(int admin_id, String admin_name, String admin_email, String admin_password) {
		this.admin_id = admin_id;
		this.admin_name = admin_name;
		this.admin_email = admin_email;
		this.admin_password = admin_password;
	}

	public String getAdmin_name() {
		return admin_name;
	}
	
	@Override
	public String toString() {
		return "Your Admin Details:\n[ID: " + admin_id +"]\nName: " + admin_name + "\nEmail: " + admin_email
				+ "\nPassword " + admin_password+"\n";
	}
}
