package com.auction_dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.auction_bean.Admin;
import com.auction_bean.BuyerDetails;
import com.auction_bean.SellerDetails;
import com.auction_bean.SoldProducts;
import com.auction_utility.DBUtil;

public class AdminImplementation implements AdminInterface {

    private final Connection conn;

    public AdminImplementation() {
        this.conn = DBUtil.provideConnection();
    }

    @Override
    public Admin adminLogin(String email, String password) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from admin where admin_email=? AND admin_password=?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("admin_id");
                String name = rs.getString("admin_name");
                return new Admin(id, name, email, password);
            } else
                throw new SQLException("Invalid email or password\n");
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public BuyerDetails[] viewAllBuyers() throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from buyer", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            BuyerDetails[] buyerDetails = new BuyerDetails[rowCount];
            int index = 0;

            while (rs.next()) {
                int b_id = rs.getInt("buyer_id");
                String b_name = rs.getString("buyer_name");
                String b_email = rs.getString("buyer_email");
                String b_password = rs.getString("buyer_password");
                Date b_signup_date = rs.getDate("buyer_signup_date");

                buyerDetails[index++] = new BuyerDetails(b_id, b_name, b_email, b_password, b_signup_date);
            }

            return buyerDetails;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving buyers");
        }
    }

    @Override
    public BuyerDetails[] viewAllBuyers(int buyer_id) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from buyer where buyer_id=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, buyer_id);
            ResultSet rs = ps.executeQuery();

            BuyerDetails[] buyerDetails = new BuyerDetails[1];

            if (rs.next()) {
                int b_id = rs.getInt("buyer_id");
                String b_name = rs.getString("buyer_name");
                String b_email = rs.getString("buyer_email");
                String b_password = rs.getString("buyer_password");
                Date b_signup_date = rs.getDate("buyer_signup_date");

                buyerDetails[0] = new BuyerDetails(b_id, b_name, b_email, b_password, b_signup_date);
                return buyerDetails;
            } else {
                throw new SQLException("Buyer with ID " + buyer_id + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving buyer details");
        }
    }

    @Override
    public SellerDetails[] viewAllSellers() throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from seller",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            SellerDetails[] sellerDetails = new SellerDetails[rowCount];
            int index = 0;

            while (rs.next()) {
                int b_id = rs.getInt("seller_id");
                String b_name = rs.getString("seller_name");
                String b_email = rs.getString("seller_email");
                String b_password = rs.getString("seller_password");
                Date b_signup_date = rs.getDate("seller_signup_date");

                sellerDetails[index++] = new SellerDetails(b_id, b_name, b_email, b_password, b_signup_date);
            }

            return sellerDetails;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving sellers");
        }
    }

    @Override
    public SellerDetails[] viewAllSellers(int seller_id) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from seller where seller_id=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, seller_id);
            ResultSet rs = ps.executeQuery();

            SellerDetails[] sellerDetails = new SellerDetails[1];

            if (rs.next()) {
                int b_id = rs.getInt("seller_id");
                String b_name = rs.getString("seller_name");
                String b_email = rs.getString("seller_email");
                String b_password = rs.getString("seller_password");
                Date b_signup_date = rs.getDate("seller_signup_date");

                sellerDetails[0] =  new SellerDetails(b_id, b_name, b_email, b_password, b_signup_date);

                return sellerDetails;
            } else {
                throw new SQLException("Seller with ID " + seller_id + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving seller details");
        }
    }
    
    @Override
    public String updateProduct() {
        String message = "No Product Updated";
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT product_id FROM product WHERE product_time_limit < ? AND sold = false", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setDate(1, Date.valueOf(LocalDate.now()));
    
            ResultSet rs = ps.executeQuery();
    
            SellerInterface s1 = new SellerImplementation();
    
            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                try {
                    s1.sellProductAtCurrentBid(product_id);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            message = "Products Updated Successfully";
        } catch (SQLException e) {
            message = "Error occurred: " + e.getMessage();
        }
        return message;
    }

    @Override
    public SoldProducts[] dailySellingReport() throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM product WHERE sold = ? AND DATE(product_sold_date) = DATE(NOW())",
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
            ps.setBoolean(1, true);
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
    public SoldProducts[] dailyDisputeReport() throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from product where disputed=? AND DATE(product_sold_date) = DATE(NOW())",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setBoolean(1, true);
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
            throw new SQLException("Error retrieving dispute report");
        }
    }

    @Override
    public String solveDisputes(int p_id) throws SQLException {
    try {
        if (!productExists(p_id)) {
            throw new SQLException("No product found with the specified ID");
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE product SET disputed=?,dispute_reason=null WHERE product_id=? AND product_time_limit>? AND disputed=true")) {
            ps.setBoolean(1, false);
            ps.setInt(2, p_id);
            ps.setDate(3, Date.valueOf(LocalDate.now()));

            int x = ps.executeUpdate();
            if (x > 0) {
                return "Dispute resolved";
            } else {
                throw new SQLException("No issues were found for the specified product.");
            }
        }
    } catch (SQLException e) {
        throw new SQLException(e.getMessage());
    }
}

    private boolean productExists(int p_id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM product WHERE product_id=?")) {
            ps.setInt(1, p_id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
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
