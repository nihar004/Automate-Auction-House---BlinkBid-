package com.auction_bean;

import java.sql.Date;

public class SellerDetails {
	private int seller_id;;
	private String seller_name;
	private String seller_email;
	private String seller_password;
	private Date seller_signup_date;

	public SellerDetails(int seller_id, String seller_name, String seller_email, String seller_password,
			Date seller_signup_date) {
		this.seller_id = seller_id;
		this.seller_name = seller_name;
		this.seller_email = seller_email;
		this.seller_password = seller_password;
		this.seller_signup_date = seller_signup_date;
	}
	
	@Override
	public String toString() {
		return "\u2588 Seller ID: " + seller_id + "\n  Name: " + seller_name + "\n  Email: "
				+ seller_email + "\n  Password:" + seller_password + "\n  Signup Date: " + seller_signup_date + "\n";
	}
}
