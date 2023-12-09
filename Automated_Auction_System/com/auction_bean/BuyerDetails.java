package com.auction_bean;

import java.sql.Date;

public class BuyerDetails {
	private int buyer_id;
	private String buyer_name;
	private String buyer_email;
	private String buyer_password;
	private Date buyer_signup_date;

	public BuyerDetails(int buyer_id, String buyer_name, String buyer_email, String buyer_password, Date buyer_signup_date) {
		this.buyer_id = buyer_id;
		this.buyer_name = buyer_name;
		this.buyer_email = buyer_email;
		this.buyer_password = buyer_password;
		this.buyer_signup_date = buyer_signup_date;
	}

	@Override
	public String toString() {
		return "\u2588 Buyer ID: " + buyer_id + "\n  Name: " + buyer_name + "\n  Email: " + buyer_email
				+ "\n  Password: " + buyer_password + "\n  Signup Date: " + buyer_signup_date + "\n";
	}
}
