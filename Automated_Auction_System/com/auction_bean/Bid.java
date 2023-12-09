package com.auction_bean;

import java.sql.Date;

public class Bid {
	private int product_id;
	private int buyer_id;
	private Date bid_date;
	private int bid_amount;

	public Bid(int product_id, int buyer_id, Date bid_date, int bid_amount) {
		this.product_id = product_id;
		this.buyer_id = buyer_id;
		this.bid_date = bid_date;
		this.bid_amount = bid_amount;
	}

	public int getProduct_id() {
		return product_id;
	}

	public int getBuyer_id() {
		return buyer_id;
	}

	public int getBid_amount() {
		return bid_amount;
	}

	@Override
	public String toString() {
		return "\n" + "\u2588 Bid Details:" + "\n  - Product ID: " + product_id + "\n  - Buyer ID: " + buyer_id + "\n  - Bid Date: " + bid_date +
		 "\n  - Bid Amount: $" + bid_amount + "\n";
	}
}
