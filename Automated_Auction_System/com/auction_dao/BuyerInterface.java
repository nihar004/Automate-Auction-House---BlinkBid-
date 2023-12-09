package com.auction_dao;

import java.sql.SQLException;

import com.auction_bean.Bid;
import com.auction_bean.Buyer;
import com.auction_bean.BuyerId;
import com.auction_bean.ProductFull;
import com.auction_bean.SoldProducts;

public interface BuyerInterface {
	public String registerBuyer(Buyer buyer) throws SQLException;
	
	public Buyer loginBuyer(String email,String password) throws SQLException;
	
	public ProductFull[] viewAllProdcut() throws SQLException;
	
	public Bid bidOnProduct(int amount, int pid, int bid) throws SQLException;
	
	public BuyerId getBuyerId(String name) throws SQLException;
	
	public String markDisputed(int id, String disputeReason, int buyerId) throws SQLException;

    public SoldProducts[] viewAllBoughtProducts(int buyerId) throws SQLException;

	public boolean isEmailAlreadyRegistered(String email) throws SQLException;
	
    public boolean isNameAlreadyRegistered(String name) throws SQLException;

    public Bid[] getBuyerBids(int buyerId) throws SQLException;

    public ProductFull findProductDetails(int productId) throws SQLException;
	    
	public void closeConnection();
}
