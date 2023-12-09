package com.auction_utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.auction_bean.Buyer;
import com.auction_bean.Seller;

public class userOptions {
    
    static public void showOptions( String category) {
        try {
            File file = new File("userOptions.txt");
            Scanner scanner = new Scanner(file);
            boolean isInSection = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith(category)) {
                    isInSection = true;
                    continue; 
                }

                if (isInSection) {
                    if (line.trim().equals("END")) {
                        break;
                    }

                    System.out.println(line);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeBuyerDetailsToFile(Buyer buyer) throws IOException {
        try (FileWriter writer = new FileWriter("newUserRegistered.txt",true)) {

            writer.write("New Buyer Registered:\n");
            writer.write("Name: " + buyer.getBuyer_name() + "\n");
            writer.write("Email: " + buyer.getBuyer_email() + "\n");
            writer.write("Password: " + buyer.getBuyer_password() + "\n");
            writer.write("Signup Date: " + buyer.getBuyer_signup_date() + "\n");
            writer.write("END\n\n");
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static void writeSellerDetailsToFile(Seller seller) throws IOException {
        try (FileWriter writer = new FileWriter("newUserRegistered.txt",true)) {

            writer.write("New Seller Registered:\n");
            writer.write("Name: " + seller.getSeller_name() + "\n");
            writer.write("Email: " + seller.getSeller_email() + "\n");
            writer.write("Password: " + seller.getSeller_password() + "\n");
            writer.write("Signup Date: " + seller.getSeller_signup_date() + "\n");
            writer.write("END\n\n");
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
