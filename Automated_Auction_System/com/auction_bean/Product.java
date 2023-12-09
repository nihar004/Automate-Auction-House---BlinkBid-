package com.auction_bean;

import java.sql.Date;

public class Product {
	private String product_name;
	private int seller_id;
	private boolean disputed;
	private Date product_added_date;
	private Date product_last_date;
	private boolean availability;
	private int current_bid;
	private boolean sold;
	private int buyer_id;

	public Product(String product_name, int seller_id, boolean disputed, Date product_added_date,
			Date product_last_date, boolean availability, int current_bid, boolean sold, int buyer_id) {
		this.product_name = product_name;
		this.seller_id = seller_id;
		this.disputed = disputed;
		this.product_added_date = product_added_date;
		this.product_last_date = product_last_date;
		this.availability = availability;
		this.current_bid = current_bid;
		this.sold = sold;
		this.buyer_id = buyer_id;
	}

	public int getSeller_id() {
		return seller_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public boolean isDisputed() {
		return disputed;
	}

	public Date getProduct_added_date() {
		return product_added_date;
	}

	public Date getProduct_last_date() {
		return product_last_date;
	}
	public boolean isAvailability() {
		return availability;
	}

	public boolean isSold() {
		return sold;
	}
	public int getBuyer_id() {
		return buyer_id;
	}

	public int getCurrentBid(){
		return current_bid;
	}

	@Override
	public String toString() {
		return "Product [product_name=" + product_name + ", seller_id=" + seller_id 
				+ ", disputed=" + disputed + ", product_added_date=" + product_added_date + ", product_last_date="
				+ product_last_date + ", availability=" + availability + ", Current_bid=" +current_bid
				+ ", sold=" + sold + ", buyer_id=" + buyer_id + "]";
	}
}
