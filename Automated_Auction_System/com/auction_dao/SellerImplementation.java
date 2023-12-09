package com.auction_dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.auction_bean.Bid;
import com.auction_bean.Product;
import com.auction_bean.ProductFull;
import com.auction_bean.Seller;
import com.auction_bean.SellerId;
import com.auction_bean.SoldProducts;
import com.auction_utility.DBUtil;

public class SellerImplementation implements SellerInterface {

    private final Connection conn;

    public SellerImplementation() {
        this.conn = DBUtil.provideConnection();
    }

    @Override
    public String sellerRegistration(Seller seller) {
        String message = "registration unsuccessful";
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "insert into seller(seller_name,seller_email,seller_password,seller_signup_date) value(?,?,?,?)");
            ps.setString(1, seller.getSeller_name());
            ps.setString(2, seller.getSeller_email());
            ps.setString(3, seller.getSeller_password());
            ps.setDate(4, seller.getSeller_signup_date());

            int x = ps.executeUpdate();
            if (x > 0){
                message = "Registration Successfull";
            } else {
                throw new SQLException(message);
            }
        } catch (SQLException e) {
            message = e.getMessage();
        }
        return message;
    }

    @Override
    public boolean isSellerEmailAlreadyRegistered(String email) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM seller WHERE seller_email=?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public boolean isSellerNameAlreadyRegistered(String name) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM seller WHERE seller_name=?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public Seller sellerLogin(String email, String password) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from seller where seller_email=? AND seller_password=?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("seller_name");
                Date date = rs.getDate("seller_signup_date");
                Seller seller = new Seller(name, email, password, date);
                return seller;
            } else
                throw new SQLException("Invalid email or password");
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public String sellerAddProduct(Product p) throws SQLException {
        String message = "Product not added";
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "insert into product(seller_id,disputed,product_added_date,availability,product_name,product_time_limit,current_bid,sold ) values(?,?,?,?,?,?,?,?)");
            ps.setInt(1, p.getSeller_id());
            ps.setBoolean(2, p.isDisputed());
            ps.setDate(3, p.getProduct_added_date());
            ps.setBoolean(4, p.isAvailability());
            ps.setString(5, p.getProduct_name());
            ps.setDate(6, p.getProduct_last_date());
            ps.setInt(7, p.getCurrentBid());
            ps.setBoolean(8, p.isSold());

            int x = ps.executeUpdate();
            if (x > 0)
                message = "Product added Successfully";
            else
                throw new SQLException(message);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return message;
    }

    @Override
    public SellerId getSellerId(String name) throws SQLException {
        try{
            PreparedStatement ps = conn.prepareStatement("select * from seller where seller_name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int seller_id = rs.getInt("seller_id");

                SellerId sid = new SellerId(seller_id);
                return sid;
            } else
                throw new SQLException("Invalid Input");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String updateProductDetails(int pid, Product p) throws SQLException {
        String message = "Update unsuccessful";
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "update product SET product_name=?,product_time_limit=?,current_bid=? where product_id=?");
            ps.setString(1, p.getProduct_name());
            ps.setDate(2, p.getProduct_last_date());
            ps.setInt(3, p.getCurrentBid());
            ps.setInt(4, pid);

            int x = ps.executeUpdate();
            if (x > 0)
                message = "Product Updated";
            else
                throw new SQLException(message);
        } catch (SQLException e) {
            message = e.getMessage();
        }
        return message;
    }
    
    @Override
    public String validateProductConditions(int sellerId, int productId) {
        try {
            if (!isSellerProduct(sellerId, productId)) {
                return "Product does not belong to you. Update failed.";
            }
    
            if (isProductSold(productId)) {
                return "Product is already sold. Update failed.";
            }
    
            if (isBiddingStarted(productId)) {
                return "Bidding has already started for this product. Update failed.";
            }
    
            return "Success";
        } catch (SQLException e) {
            return "Error occurred while validating product update conditions.";
        }
    }

    @Override
    public boolean isSellerProduct(int sellerId, int productId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM product WHERE product_id=? AND seller_id=?")) {
            ps.setInt(1, productId);
            ps.setInt(2, sellerId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public boolean isProductSold(int productId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT sold FROM product WHERE product_id=?")) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getBoolean("sold");
            }
        }
    }

    private boolean isBiddingStarted(int productId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM bids WHERE product_id=?")) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int bidCount = rs.getInt(1);
                    return bidCount > 0;
                }
            }
        }
        return false;
    }

    @Override
    public String deleteProduct(int pid) throws SQLException {
        String message = "Delete Unsuccessful";
        try{
            PreparedStatement ps = conn.prepareStatement("delete from product where product_id=?");
            ps.setInt(1, pid);
            int x = ps.executeUpdate();
            if (x > 0)
                message = "Deleted Successfully";
            else
                throw new SQLException(message);

        } catch (SQLException e) {
            message = e.getMessage();
        }
        return message;
    }

    @Override
    public ProductFull[] showAllUnSoldProduct(int sellerId) throws SQLException {
        ProductFull[] plist = null;
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM product WHERE sold=? AND seller_id=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setBoolean(1, false);
            ps.setInt(2, sellerId);

            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }

            if (count > 0) {
                plist = new ProductFull[count];
                rs.beforeFirst(); 
                int index = 0;
                while (rs.next()) {
                    int product_id = rs.getInt("product_id");
                    int seller_id = rs.getInt("seller_id");
                    boolean disputed = rs.getBoolean("disputed");
                    Date product_added_date = rs.getDate("product_added_date");
                    boolean availability = rs.getBoolean("availability");
                    String product_name = rs.getString("product_name");
                    Date product_time_limit = rs.getDate("product_time_limit");
                    int current_bid = rs.getInt("current_bid");
                    String dispute_reason = rs.getString("dispute_reason");
                    Date product_sold_date = rs.getDate("product_sold_date");

                    ProductFull p1 = new ProductFull(product_id,product_name, seller_id, disputed,
                            product_added_date, product_time_limit, availability,current_bid,dispute_reason,product_sold_date);
                    plist[index++] = p1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (plist == null || plist.length == 0)
            throw new SQLException("No Products found. Try to add few products");
        return plist;
    }

    @Override
    public SoldProducts[] showAllSoldProduct(int sellerId,boolean dispute) throws SQLException {
        SoldProducts[] plist = null;

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM product WHERE sold=? AND disputed=? AND seller_id=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setBoolean(1, true);
            ps.setBoolean(2, dispute);
            ps.setInt(3, sellerId);

            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }

            if (count > 0) {
                plist = new SoldProducts[count];
                rs.beforeFirst();
                int index = 0;
                while (rs.next()) {
                    int product_id = rs.getInt("product_id");
                    int seller_id = rs.getInt("seller_id");
                    boolean disputed = rs.getBoolean("disputed");
                    Date product_added_date = rs.getDate("product_added_date");
                    boolean availability = rs.getBoolean("availability");
                    String product_name = rs.getString("product_name");
                    Date product_time_limit = rs.getDate("product_time_limit");
                    int product_selling_price = rs.getInt("product_selling_price");
                    int buyer_id = rs.getInt("buyer_id");
                    boolean sold = rs.getBoolean("sold");
                    Date product_sold_date = rs.getDate("product_sold_date");
                    String dispute_reason = rs.getString("dispute_reason");

                    SoldProducts p1 = new SoldProducts(product_id, product_name, seller_id, disputed,
                            product_added_date, product_time_limit, availability, sold, buyer_id,
                            product_selling_price, dispute_reason, product_sold_date);
                    plist[index++] = p1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (plist == null || plist.length == 0)
            throw new SQLException("No Products found with disputes for the given seller.");
        return plist;
    }

    @Override
    public String sellProductAtCurrentBid(int productId) throws SQLException{
        try {
                Bid highestBid = getHighestBid(productId);

                if (highestBid != null) {
                    int buyerId = highestBid.getBuyer_id();
                    Date currentDate = Date.valueOf(LocalDate.now());

                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE product SET sold=?, buyer_id=?, product_sold_date=? WHERE product_id=?")) {
                        ps.setBoolean(1, true);
                        ps.setInt(2, buyerId);
                        ps.setDate(3, currentDate);
                        ps.setInt(4, productId);

                        int rowsAffected = ps.executeUpdate();

                        if (rowsAffected > 0) {
                            return "Product sold successfully to the highest bidder at $" + highestBid.getBid_amount();
                        } else {
                            return "Failed to update product details. Sale unsuccessful.";
                        }
                    }
                } else {
                    return "No bids found for the product. Sale unsuccessful.";
                }
        } catch (SQLException e) {
            return "Error occurred during the sale: " + e.getMessage();
        }
    }

    private Bid getHighestBid(int productId) throws SQLException {
        String query = "SELECT * FROM bids WHERE product_id = ? ORDER BY bid_amount DESC LIMIT 1";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, productId);
    
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int buyerId = rs.getInt("buyer_id");
                    int bidAmount = rs.getInt("bid_amount");
                    Date bid_date = rs.getDate("bid_date");
    
                    return new Bid(productId, buyerId, bid_date,bidAmount);
                }
            }
        }
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}





