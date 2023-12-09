package com.auction_bean;

import java.sql.Date;

// used by the Buyer implementation 
public class ProductFull {
	private int product_id;
	private String product_name;
	private int seller_id;
	private boolean disputed;
	private Date product_added_date;
	private Date product_last_date;
	private boolean availability;
	private int current_bid;
	private String dispute_reason;
	private Date product_sold_date;
	
	public ProductFull(int product_id, String product_name, int seller_id, boolean disputed,
			Date product_added_date, Date product_last_date, boolean availability, int current_bid,String dispute_reason ,Date product_sold_date) {
		this.product_id = product_id;
		this.product_name = product_name;
		this.seller_id = seller_id;
		this.disputed = disputed;
		this.product_added_date = product_added_date;
		this.product_last_date = product_last_date;
		this.availability = availability;
		this.current_bid = current_bid;
		this.product_sold_date = product_sold_date;

		if(disputed == true){
			this.dispute_reason = dispute_reason;
		}else{
			this.dispute_reason = "No Dispute";
		}
	}
	public int getProduct_id() {
		return product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public int getSeller_id() {
		return seller_id;
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

	public Date getProduct_sold_date() {
		return product_sold_date;
	}

	public int get_current_bid(){
		return current_bid;
	}

	@Override
	public String toString() {
		return "\u2588 "+product_name +"\n  - Product ID: " + product_id + "\n  - Seller ID: " + seller_id + "\n  - Current Bid Amount: $"+ current_bid +"\n  - Availability: " + availability + "\n  - Disputed: "+disputed + "\n  - Dispute Reason: "+dispute_reason  +"\n  - Product Added Date: " + product_added_date + "\n  - Last Date: " + product_last_date + "\n";
	}
}
