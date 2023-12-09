
package com.auction_dao;

import java.sql.SQLException;

import com.auction_bean.Product;
import com.auction_bean.ProductFull;
import com.auction_bean.Seller;
import com.auction_bean.SellerId;
import com.auction_bean.SoldProducts;

public interface SellerInterface {
	public String sellerRegistration(Seller seller);
	
	public Seller sellerLogin(String email,String password) throws SQLException;
	
	public String sellerAddProduct(Product product) throws SQLException;
	
	public SellerId getSellerId(String name) throws SQLException;
	
	public String updateProductDetails(int pid,Product product) throws SQLException;
	
	public String deleteProduct(int pid) throws SQLException;
	
	public boolean isSellerEmailAlreadyRegistered(String email) throws SQLException;
	
    public boolean isSellerNameAlreadyRegistered(String name) throws SQLException;
	
    public SoldProducts[] showAllSoldProduct(int sellerId,boolean dispute) throws SQLException;
		
    public String validateProductConditions(int sellerId, int productId);

	public ProductFull[] showAllUnSoldProduct(int sellerId) throws SQLException;

    public boolean isProductSold(int productId) throws SQLException;

    public boolean isSellerProduct(int sellerId, int productId) throws SQLException;

    public String sellProductAtCurrentBid(int productId) throws SQLException;
	
	public void closeConnection();
}
