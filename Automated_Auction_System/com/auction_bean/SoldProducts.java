package com.auction_bean;

import java.sql.Date;

public class SoldProducts {
	private int product_id;
	private String product_name;
	private int seller_id;
	private boolean disputed;
	private String dispute_reason;
	private Date product_added_date;
	private Date product_last_date;
	private Date product_sold_date;
	private boolean availability;
	private boolean sold;
	private int buyer_id;
	private int product_selling_price;

	public SoldProducts(int product_id,String product_name, int seller_id, boolean disputed,Date product_added_date, 
			Date product_last_date, boolean availability, boolean sold,int buyer_id, 
			int product_selling_price,String dispute_reason, Date product_sold_date) {
		this.product_id = product_id;
		this.product_name = product_name;
		this.seller_id = seller_id;
		this.disputed = disputed;
		this.product_added_date = product_added_date;
		this.product_last_date = product_last_date;
		this.availability = availability;
		this.sold = sold;
		this.buyer_id = buyer_id;
		this.product_selling_price = product_selling_price;
		this.product_sold_date = product_sold_date;
		
		if(disputed == true){
			this.dispute_reason = dispute_reason;
		}else{
			this.dispute_reason = "No Dispute";
		}
	}

	@Override
	public String toString() {
		return "\u2588 "+product_name +"\n  - Product ID: " + product_id + "\n  - Seller ID: " + seller_id +"\n  - Availability: " + availability +
				 "\n  - sold: " + sold + "\n  - Buyer ID: " + buyer_id + "\n  - Selling Price: $" + product_selling_price + "\n  - Disputed: "+disputed + "\n  - Dispute Reason: " + dispute_reason + "\n  - Product Added Date: " + product_added_date + "\n  - Product Last Date: " + product_last_date + "\n  - Product Sold Date: " + product_sold_date +"\n";
	}
}
