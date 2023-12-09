package com.auction_bean;

import java.sql.Date;

public class Buyer {
	private String buyer_name;
	private String buyer_email;
	private String buyer_password;
	private Date buyer_signup_date;

	public Buyer(String buyer_name, String buyer_email, String buyer_password, Date buyer_signup_date) {
		this.buyer_name = buyer_name;
		this.buyer_email = buyer_email;
		this.buyer_password = buyer_password;
		this.buyer_signup_date = buyer_signup_date;
	}

	public String getBuyer_name() {
		return buyer_name;
	}

	public String getBuyer_email() {
		return buyer_email;
	}
	public String getBuyer_password() {
		return buyer_password;
	}

	public Date getBuyer_signup_date() {
		return buyer_signup_date;
	}

	@Override
	public String toString() {
		return "- Name: " + buyer_name + "\n- Email: " + buyer_email
		+ "\n- Password: " + buyer_password + "\n- Signup Date: " + buyer_signup_date + "\n";
	}
}
