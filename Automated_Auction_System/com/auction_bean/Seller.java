package com.auction_bean;

import java.sql.Date;

public class Seller {
	private String seller_name;
	private String seller_email;
	private String seller_password;
	private Date seller_signup_date;

	public Seller(String seller_name, String seller_email, String seller_password, Date seller_signup_date) {
		this.seller_name = seller_name;
		this.seller_email = seller_email;
		this.seller_password = seller_password;
		this.seller_signup_date = seller_signup_date;
	}

	public String getSeller_name() {
		return seller_name;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public String getSeller_password() {
		return seller_password;
	}

	public Date getSeller_signup_date() {
		return seller_signup_date;
	}

	@Override
	public String toString() {
		return "- Name: " + seller_name + "\n- Email: " + seller_email + "\n- Password: "
				+ seller_password + "\n- Signup Date: " + seller_signup_date + "\n";
	}
}
