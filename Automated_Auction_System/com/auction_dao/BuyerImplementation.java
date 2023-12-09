package com.auction_dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.auction_bean.Bid;
import com.auction_bean.Buyer;
import com.auction_bean.BuyerId;
import com.auction_bean.ProductFull;
import com.auction_bean.SoldProducts;
import com.auction_utility.DBUtil;

public class BuyerImplementation implements BuyerInterface {

    private final Connection conn;

    public BuyerImplementation() {
        this.conn = DBUtil.provideConnection();
    }

    @Override
    public String registerBuyer(Buyer b) throws SQLException {
        String message = "Registration Unsuccessful";

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO buyer(buyer_name, buyer_email, buyer_password, buyer_signup_date) VALUES (?, ?, ?, ?)");
            ps.setString(1, b.getBuyer_name());
            ps.setString(2, b.getBuyer_email());
            ps.setString(3, b.getBuyer_password());
            ps.setDate(4, b.getBuyer_signup_date());

            int x = ps.executeUpdate();
            if (x > 0) {
                message = "Registration successful";
            } else {
                throw new SQLException(message);
            }
        } catch (SQLException e) {
            message = e.getMessage();
        }
        return message;
    }

    @Override
    public boolean isEmailAlreadyRegistered(String email) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM buyer WHERE buyer_email=?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public boolean isNameAlreadyRegistered(String name) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM buyer WHERE buyer_name=?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public Buyer loginBuyer(String email, String password) throws SQLException {

        try{
            PreparedStatement ps = conn.prepareStatement("select * from buyer where buyer_email=? AND buyer_password=?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("buyer_name");
                Date b_signup_date = rs.getDate("buyer_signup_date");

                return new Buyer(name, email, password, b_signup_date);
            } else {
                throw new SQLException("Invalid email or password");
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public ProductFull[] viewAllProdcut() throws SQLException { 
        ProductFull[] plist = null;
        try{
            PreparedStatement ps = conn.prepareStatement("select * from product where availability=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setBoolean(1, true);
    
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
    
                    ProductFull p1 = new ProductFull(product_id, product_name, seller_id, disputed,
                            product_added_date, product_time_limit, availability, current_bid,dispute_reason,product_sold_date);
                    plist[index++] = p1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (plist == null || plist.length == 0)
            throw new SQLException("No products found. Try later");
        return plist;
    }

    @Override
    public ProductFull findProductDetails(int productId) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from product where product_id=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
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

                return new ProductFull(product_id, product_name, seller_id, disputed, product_added_date, product_time_limit, availability, current_bid, dispute_reason, product_sold_date);
            } else {
                throw new SQLException("Product not found with ID: " + productId);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving product details: " + e.getMessage());
        }
    }
    
    @Override
    public Bid bidOnProduct(int amount, int pid, int b_id) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM product WHERE product_id=? AND availability=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, pid);
            ps.setBoolean(2, true);
    
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int product_current_price = rs.getInt("current_bid");
    
                if (product_current_price <= amount) {
                    PreparedStatement ps2 = conn.prepareStatement(
                            "UPDATE product SET current_bid=? WHERE product_id=?");
                    ps2.setInt(1, amount);
                    ps2.setInt(2, pid);
    
                    int x = ps2.executeUpdate();
                    if (x > 0) {
                        PreparedStatement ps3 = conn.prepareStatement(
                                "INSERT INTO bids(product_id, buyer_id, bid_amount, bid_date) VALUES (?, ?, ?, ?)");
                        ps3.setInt(1, pid);
                        ps3.setInt(2, b_id);
                        ps3.setInt(3, amount);
                        ps3.setDate(4, Date.valueOf(LocalDate.now()));
    
                        int y = ps3.executeUpdate();
                        if (y > 0) {
                            System.out.println("Bid placed successfully!");
                            return new Bid(pid, b_id, Date.valueOf(LocalDate.now()), amount);
                        } else {
                            throw new SQLException("Failed to insert bid into bids table");
                        }
                    } else {
                        throw new SQLException("Error registering the Bid in the table");
                    }
                } else {
                    throw new SQLException("Bid amount must be greater than the Current Bid: $"+product_current_price);
                }
            } else {
                throw new SQLException("No such Product found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public BuyerId getBuyerId(String name) throws SQLException {
        BuyerId bid = null;
        try{
            PreparedStatement ps = conn.prepareStatement("select * from buyer where buyer_name=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("buyer_id");

                bid = new BuyerId(id);
                return bid;
            } else
                throw new SQLException("Buyer name not found ");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bid;
    }

    @Override
    public String markDisputed(int productId, String disputeReason, int buyerId) throws SQLException {
        String message = "Not marked as disputed";
        try {
            if (hasBuyerPurchasedProduct(productId, buyerId)) {
                PreparedStatement ps = conn.prepareStatement("update product SET disputed=?, availability=?, dispute_reason=? where product_id=?");
                ps.setBoolean(1, true);
                ps.setBoolean(2, false);
                ps.setString(3, disputeReason);
                ps.setInt(4, productId);
                int x = ps.executeUpdate();
                if (x > 0)
                    message = "Marked as Disputed";
                else
                    throw new SQLException("Invalid Input");
            } else {
                message = "Buyer has not purchased the product. Cannot mark dispute.";
            }
        } catch (SQLException e) {
            message = "Error occurred while marking dispute: " + e.getMessage();
        }
        return message;
    }

    private boolean hasBuyerPurchasedProduct(int productId, int buyerId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM product WHERE product_id=? AND buyer_id=?")) {
            ps.setInt(1, productId);
            ps.setInt(2, buyerId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    @Override
    public SoldProducts[] viewAllBoughtProducts(int buyerId) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM product WHERE sold = ? AND buyer_id=?",
                ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ps.setBoolean(1, true);            
            ps.setInt(2, buyerId );

            ResultSet rs = ps.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            SoldProducts[] soldProducts = new SoldProducts[rowCount];
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

                soldProducts[index++] = new SoldProducts(product_id,product_name, seller_id, disputed,
                        product_added_date, product_time_limit, availability, sold, buyer_id,
                        product_selling_price,dispute_reason,product_sold_date);
            }

            return soldProducts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving selling report");
        }
    }

    @Override
    public Bid[] getBuyerBids(int buyerId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT buyer_id,bid_date,product_id, MAX(bid_amount) AS max_bid_amount FROM bids WHERE buyer_id=? GROUP BY product_id,buyer_id,bid_date;",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();
            
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            Bid[] buyerBids = new Bid[rowCount];
            int index = 0;

            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                int buyer_id = rs.getInt("buyer_id");
                int bid_amount = rs.getInt("max_bid_amount");                
                Date bid_date  =  rs.getDate("bid_date");
                
                buyerBids[index++] = new Bid(product_id,buyer_id,bid_date,bid_amount);
            }
            return buyerBids;
        } catch (SQLException e) {
            throw new SQLException("Error retrieving buyer bids: " + e.getMessage());
        }
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
