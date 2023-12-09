package com.auction_dao;

import java.sql.SQLException;

import com.auction_bean.Admin;
import com.auction_bean.BuyerDetails;
import com.auction_bean.SellerDetails;
import com.auction_bean.SoldProducts;

public interface AdminInterface {
    public Admin adminLogin(String email, String password) throws SQLException;

    public BuyerDetails[] viewAllBuyers() throws SQLException;

    public BuyerDetails[] viewAllBuyers(int buyer_id) throws SQLException;

    public SellerDetails[] viewAllSellers() throws SQLException;

    public SellerDetails[] viewAllSellers(int seller_id) throws SQLException;

    public String updateProduct();

    public SoldProducts[] dailySellingReport() throws SQLException;

    public SoldProducts[] dailyDisputeReport() throws SQLException;

    public String solveDisputes(int id) throws SQLException;

    public void closeConnection();

}
