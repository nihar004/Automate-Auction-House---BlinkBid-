package com.auction_client;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.auction_bean.Admin;
import com.auction_bean.Bid;
import com.auction_bean.Buyer;
import com.auction_bean.BuyerDetails;
import com.auction_bean.Product;
import com.auction_bean.ProductFull;
import com.auction_bean.Seller;
import com.auction_bean.SellerDetails;
import com.auction_bean.SoldProducts;
import com.auction_dao.AdminImplementation;
import com.auction_dao.AdminInterface;
import com.auction_dao.BuyerImplementation;
import com.auction_dao.BuyerInterface;
import com.auction_dao.SellerImplementation;
import com.auction_dao.SellerInterface;
import com.auction_utility.userOptions;

abstract class MainImplementation{
	static final int MAX_ATTEMPTS = 5;
    static int incorrectAttempts = 0;

	void printLine() {
		System.out.println("---------------------------------------------");
	}

	abstract void showAdminOptions();

    abstract void showBuyerOptions();

    abstract void showSellerOptions();

	abstract void showSellerOptions2();

	abstract void productRegularUpdate();
}

public class Main extends MainImplementation {
	@Override
	void showAdminOptions() {
		System.out.println("\nSelect from following options");
		printLine();
		userOptions.showOptions("Admin");
		printLine();
	}

	@Override
	void showBuyerOptions() {
		System.out.println("Select from following options");
		printLine();
		userOptions.showOptions("Buyer");
		printLine();
	}

	@Override
	void showSellerOptions() {
		System.out.println("\nSelect from following options");
		printLine();
		userOptions.showOptions("Seller1");
		printLine();
	}

	@Override
	void showSellerOptions2() {
		System.out.println("\nSelect from following options");
		printLine();
		userOptions.showOptions("Seller2");
		printLine();
	}

	@Override
	void productRegularUpdate() {
		AdminInterface a = new AdminImplementation();
		a.updateProduct();
	}

	public static void main(String[] args) {
		Main main = new Main();
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("╔═════════════════════════════════════════╗");
		System.out.println("║   Welcome to BlinkBid Automate Auction    ║");
		System.out.println("╚═════════════════════════════════════════╝");

		int choice = 0;

		outerLoop: while (choice != 4) {
			System.out.println();
			System.out.println("Press 1) Admin 2) Buyer 3) Seller 4) Exit");
			try {
				choice = Integer.parseInt(sc.nextLine());
				main.productRegularUpdate();
				switch (choice) {
					case 1: {
						// admin
						if (incorrectAttempts > 0) {
							incorrectAttempts--;
						}
						System.out.print("\nEnter admin email: ");
						String aemail = sc.nextLine();
						System.out.print("Enter admin password: ");
						String apassword = sc.nextLine();
						AdminInterface a1 = new AdminImplementation();
						try {
							Admin admin = a1.adminLogin(aemail, apassword);
							System.out.println("\nHello " + admin.getAdmin_name() + ",\n");
							System.out.println(admin);

							int achoice = 0;
							while (achoice != 8) {
								main.showAdminOptions();
								try {
									achoice = Integer.parseInt(sc.nextLine());
									System.out.println();
									switch (achoice) {
										case 1: {
											// View registered buyer list
											if (incorrectAttempts > 0) {
												incorrectAttempts--;
											}
											try {
												BuyerDetails[] blist = a1.viewAllBuyers();
												System.out.println("Registered Buyers:");
												main.printLine();
												for (BuyerDetails buyer : blist) {
													System.out.println(buyer);
												}
												main.printLine();
												System.out.println();
											} catch (SQLException e) {
												System.out.println(e.getMessage());
											}
											break;
										}
										case 2: {
											// View registered seller list
											if (incorrectAttempts > 0) {
												incorrectAttempts--;
											}
											SellerDetails[] slist;
											try {
												slist = a1.viewAllSellers();
												System.out.println("Registered Sellers:");
												main.printLine();
												for (SellerDetails seller : slist) {
													System.out.println(seller);
												}
												main.printLine();
												System.out.println();
											} catch (SQLException e) {
												System.out.println(e.getMessage());
											}
											break;
										}
										case 3: {
											// View daily dispute report
											if (incorrectAttempts > 0) {
												incorrectAttempts--;
											}
											try {
												SoldProducts[] dlist = a1.dailyDisputeReport();
												System.out.println("Daily Dispute Report:");
												main.printLine();
												for (SoldProducts product : dlist) {
													System.out.println(product);
												}
												main.printLine();
												System.out.println();
											} catch (SQLException e) {
												System.out.println(e.getMessage());
											}
											break;
										}
										case 4: {
											if (incorrectAttempts > 0) {
												incorrectAttempts--;
											}
											try {
												SoldProducts[] splist = a1.dailySellingReport();
												System.out.println("Daily Selling Report:");
												main.printLine();
												for (SoldProducts product : splist) {
													System.out.println(product);
												}
												main.printLine();
												System.out.println();
											} catch (SQLException e) {
												System.out.println(e.getMessage());
											}
											break;
										}
										case 5: {
											// Solve the disputed reports
											if (incorrectAttempts > 0) {
												incorrectAttempts--;
											}
											System.out.print("Enter Product ID to resolve disputes: ");
											int p_id = Integer.parseInt(sc.nextLine());
											try {
												String message = a1.solveDisputes(p_id);
												System.out.println(message);
											} catch (SQLException e) {
												System.out.println(e.getMessage());
											}
											break;
										}
										case 6: {
											// View specific buyer list
											if (incorrectAttempts > 0) {
												incorrectAttempts--;
											}
											try {
												System.out.print("Enter Buyer ID: ");
												int buyer_id = Integer.parseInt(sc.nextLine());
												BuyerDetails[] blist = a1.viewAllBuyers(buyer_id);
												System.out.println("\nBuyer Details:");
												main.printLine();
												for (BuyerDetails buyer : blist) {
													System.out.println(buyer);
												}
												main.printLine();
												System.out.println();
											} catch (SQLException e) {
												System.out.println(e.getMessage());
											}
											break;
										}
										case 7: {
											// View specific seller list
											if (incorrectAttempts > 0) {
												incorrectAttempts--;
											}
											try {
												System.out.print("Enter Seller ID: ");
												int seller_id = Integer.parseInt(sc.nextLine());
												SellerDetails[] slist = a1.viewAllSellers(seller_id);
												System.out.println("\nSeller Details:");
												main.printLine();
												for (SellerDetails seller : slist) {
													System.out.println(seller);
												}
												main.printLine();
												System.out.println();
											} catch (SQLException e) {
												System.out.println(e.getMessage());
											}
											break;
										}
										case 8: {
											if (incorrectAttempts > 0) {
												incorrectAttempts--;
											}
											break;
										}
										default:
											System.out
													.println("Invalid Option. Please enter a number between 1 and 8.");
									}
								} catch (NumberFormatException e) {
									incorrectAttempts++;
									if (incorrectAttempts >= MAX_ATTEMPTS) {
										System.out.println();
										System.out.println("Maximum attempts reached. Exiting program... T_T");
										System.out.println();
										break outerLoop;
									}
									System.out.println("\nInvalid input. Please enter a valid number.");
									System.out
											.println("Number of Attempts Left: " + (MAX_ATTEMPTS - incorrectAttempts));
								}
							}
						} catch (SQLException e) {
							System.out.println(e.getMessage());
						} catch (Exception e) {
							e.printStackTrace();
						}
						a1.closeConnection();
						break;
					}
					case 2: {
						// buyer
						if (incorrectAttempts > 0) {
							incorrectAttempts--;
						}
						BuyerInterface b1 = new BuyerImplementation();

						int bchoice1 = 0;
						while (bchoice1 != 3) {
							System.out.println("\nPress 1)Login as Buyer  2)Register as Buyer  3) Exit");
							try {
								bchoice1 = Integer.parseInt(sc.nextLine());
								switch (bchoice1) {
									case 1: {
										if (incorrectAttempts > 0) {
											incorrectAttempts--;
										}
										System.out.println();
										System.out.print("Enter your email :");
										String bemail = sc.nextLine();
										System.out.print("Enter your password :");
										String bpass = sc.nextLine();

										try {
											Buyer buyer = b1.loginBuyer(bemail, bpass);
											System.out.println("\nHello " + buyer.getBuyer_name() + ",\n");
											System.out.println("Your Details are:");
											System.out.println(buyer);

											int bchoice2 = 0;
											while (bchoice2 != 6) {
												main.showBuyerOptions();
												try {
													bchoice2 = Integer.parseInt(sc.nextLine());

													switch (bchoice2) {
														case 1: {
															// view all products
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															try {
																ProductFull[] plist = b1.viewAllProdcut();
																System.out.println("\nRegistered Products: ");
																main.printLine();
																for (ProductFull product : plist) {
																	System.out.println(product);
																}
																main.printLine();
																System.out.println();
															} catch (SQLException e) {
																System.out.println(e.getMessage());
															}

															break;
														}
														case 2: {
															// View bidded products
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															try {
																int buyerId = b1.getBuyerId(buyer.getBuyer_name())
																		.getBuyer_id();
																Bid buyerBids[] = b1.getBuyerBids(buyerId);

																System.out.println("\nBidded Products:");
																main.printLine();
																for (Bid bid : buyerBids) {
																	ProductFull productDetails = b1
																			.findProductDetails(bid.getProduct_id());
																	System.out.println("\u2588 Product ID: "
																			+ bid.getProduct_id());
																	System.out.println(
																			"  Product Name: "
																					+ productDetails.getProduct_name());
																	System.out.println(
																			"  Seller ID: "
																					+ productDetails.getSeller_id());
																	System.out.println("  Time Limit: "
																			+ productDetails.getProduct_last_date());
																	System.out.println(
																			"  Current Bid: $"
																					+ productDetails.get_current_bid());
																	System.out.println(
																			"  Your Bid: $" + bid.getBid_amount());
																	if (productDetails.getProduct_sold_date() != null) {
																		System.out.println("  Bid Status: Sold");
																		if (productDetails.get_current_bid() == bid
																				.getBid_amount()) {
																			System.out
																					.println(" -You won this Bid :)");
																		} else {
																			System.out.println(
																					" -You lost this Bid T_T");
																		}
																	} else {
																		System.out.println("  Bid Status: Ongoing");
																		if (productDetails.get_current_bid() == bid
																				.getBid_amount()) {
																			System.out.println(
																					" -Your bid is currently the highest!");
																		} else {
																			System.out.println(
																					" -Your bid is not the highest at the moment.");
																		}
																	}
																	System.out.println();
																}
																main.printLine();
																System.out.println();
															} catch (SQLException e) {
																System.out.println(e.getMessage());
															}
															break;
														}
														case 3: {
															// View bought products
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															try {
																int buyerId = b1.getBuyerId(buyer.getBuyer_name())
																		.getBuyer_id();

																SoldProducts[] splist = b1
																		.viewAllBoughtProducts(buyerId);
																System.out.println("\nBought Products:");
																main.printLine();
																for (SoldProducts product : splist) {
																	System.out.println(product);
																}
																main.printLine();
																System.out.println();
															} catch (SQLException e) {
																System.out.println(e.getMessage());
															}
															break;
														}
														case 4: {
															// bid on a product by id
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															System.out.print("\nEnter product id: ");
															int pid = Integer.parseInt(sc.nextLine());
															System.out.print(
																	"Enter your bid (should be more than minimum bidding amount): $");
															int min_b_amount = Integer.parseInt(sc.nextLine());
															int buyerId = b1.getBuyerId(buyer.getBuyer_name())
																	.getBuyer_id();
															try {
																Bid bid1 = b1.bidOnProduct(min_b_amount, pid, buyerId);
																if (bid1 != null) {
																	System.out.println(bid1);
																}
																System.out.println();
															} catch (SQLException e) {
																System.out.println(e.getMessage());
																System.out.println();
															}
															break;
														}
														case 5: {
															// Mark a product disputed by id
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															System.out
																	.print("\nEnter product ID to mark as disputed: ");
															int p_id = Integer.parseInt(sc.nextLine());

															System.out.print("Enter reason for the dispute: ");
															String disputeReason = sc.nextLine();

															try {
																int buyerId = b1.getBuyerId(buyer.getBuyer_name())
																		.getBuyer_id();
																String message = b1.markDisputed(p_id, disputeReason,
																		buyerId);
																System.out.println(message + "\n");
															} catch (SQLException e) {
																System.out.println(e.getMessage());
															}
															break;
														}

														case 6: {
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															break;
														}
														default:
															System.out.println(
																	"Invalid option. Please enter a number between 1 and 6.");
													}
												} catch (NumberFormatException e) {
													incorrectAttempts++;
													if (incorrectAttempts >= MAX_ATTEMPTS) {
														System.out.println();
														System.out.println(
																"Maximum attempts reached. Exiting program... T_T");
														System.out.println();
														break outerLoop;
													}
													System.out.println("\nInvalid input. Please enter a valid number.");
													System.out.println("Number of Attempts Left: "
															+ (MAX_ATTEMPTS - incorrectAttempts) + "\n");

												}
											}
										} catch (SQLException e) {
											System.out.println(e.getMessage());
										} catch (Exception e) {
											e.printStackTrace();
										}
										break;
									}
									case 2: {
										try {
											// Register Buyer
											if (incorrectAttempts > 0) {
												incorrectAttempts--;
											}
											System.out.print("\nEnter buyer email:");
											String bemail = sc.nextLine();

											// Check if the email is already registered
											if (b1.isEmailAlreadyRegistered(bemail)) {
												System.out.println("Email is already registered. Please try Again.");
												break;
											}

											System.out.print("Enter buyer name :");
											String bname = sc.nextLine();

											// Check if the name is already registered
											if (b1.isNameAlreadyRegistered(bname)) {
												System.out.println("Name is already registered. Please try Again.");
												break;
											}

											System.out.print("Enter buyer password:");
											String bpass = sc.nextLine();
											System.out.println();
											Date date = Date.valueOf(LocalDate.now());
											Buyer buyer = new Buyer(bname, bemail, bpass, date);
											String message = b1.registerBuyer(buyer);
											userOptions.writeBuyerDetailsToFile(buyer);
											System.out.println(message);

										} catch (SQLException e) {
											e.printStackTrace();
										} catch (IOException e) {
											e.printStackTrace();
										}
										break;
									}
									case 3: {
										if (incorrectAttempts > 0) {
											incorrectAttempts--;
										}
										break;
									}
									default:
										System.out.println("\nInvalid Option. Please enter a number between 1 and 3.");
								}
							} catch (NumberFormatException e) {
								incorrectAttempts++;
								if (incorrectAttempts >= MAX_ATTEMPTS) {
									System.out.println();
									System.out.println("Maximum attempts reached. Exiting program... T_T");
									System.out.println();
									break outerLoop;
								}
								System.out.println("\nInvalid input. Please enter a valid number.");
								System.out.println("Number of Attempts Left: " + (MAX_ATTEMPTS - incorrectAttempts));
								System.out.println();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						b1.closeConnection();
						break;
					}
					case 3: {
						// seller
						if (incorrectAttempts > 0) {
							incorrectAttempts--;
						}
						SellerInterface s1 = new SellerImplementation();

						int schoice1 = 0;
						while (schoice1 != 3) {
							System.out.println("\nPress 1)Login as Seller  2)Register as Seller  3) Exit");
							try {
								schoice1 = Integer.parseInt(sc.nextLine());								
								switch (schoice1) {
									case 1: {
										// login
										if (incorrectAttempts > 0) {
											incorrectAttempts--;
										}
										System.out.println();
										System.out.print("Enter your email :");
										String semail = sc.nextLine();
										System.out.print("Enter your password :");
										String spass = sc.nextLine();
										try {
											Seller seller = s1.sellerLogin(semail, spass);
											System.out.println("\nHello " + seller.getSeller_name() + ",\n");
											System.out.println("Your Details are:");
											System.out.println(seller);

											int schoice2 = 0;
											while (schoice2 != 7) {
												main.showSellerOptions();
												try {
													schoice2 = Integer.parseInt(sc.nextLine());
													switch (schoice2) {
														case 1: {
															// add a product
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															System.out.print("\nEnter name of the product: ");
															String product_name = sc.nextLine();
															System.out.print("Enter minimum bid amount: $");
															int current_bid = Integer.parseInt(sc.nextLine());
															System.out.print(
																	"Set time limit for the product (in days): ");
															int days = Integer.parseInt(sc.nextLine());
															System.out.println();

															LocalDate ld = LocalDate.now().plusDays(days);
															Date lastDate = Date.valueOf(ld);
															int sellerId = s1.getSellerId(seller.getSeller_name())
																	.getSeller_id();

															Product p1 = new Product(product_name,
																	sellerId,
																	false,
																	Date.valueOf(LocalDate.now()), lastDate, true,
																	current_bid,
																	false, -1);
															try {
																String message = s1.sellerAddProduct(p1);
																System.out.println(message + "\n");
															} catch (SQLException e) {
																System.out.println(e.getMessage());
															}
															break;
														}
														case 2: {
															// Update a product
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															System.out
																	.print("Enter product ID to change its details: ");
															int pid = sc.nextInt();

															// Validate productId
															try {
																int sellerId = s1.getSellerId(seller.getSeller_name())
																		.getSeller_id();
																String validationMessage = s1
																		.validateProductConditions(
																				sellerId,
																				pid);

																if (validationMessage.equals("Success")) {

																	System.out.print("Enter name of the product: ");
																	String product_name = sc.next();
																	System.out.print("Enter minimum bid amount: $");
																	int current_bid = sc.nextInt();
																	System.out.print(
																			"Set time limit for the product (in days): ");
																	int days = sc.nextInt();

																	LocalDate ld = LocalDate.now().plusDays(days);
																	Date lastDate = Date.valueOf(ld);

																	Product p1 = new Product(product_name,
																			sellerId,
																			false, Date.valueOf(LocalDate.now()),
																			lastDate,
																			true,
																			current_bid, false, -1);

																	try {
																		String message = s1.updateProductDetails(pid,
																				p1);
																		System.out.println(message);
																	} catch (SQLException e) {
																		System.out.println(e.getMessage());
																	}
																} else {
																	System.out.println(validationMessage);
																}
															} catch (SQLException e) {
																System.out
																		.println(
																				"Error occurred while validating Product ID.");
															}
															break;
														}
														case 3: {
															// Remove a product
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															System.out.print(
																	"\nEnter the Product ID of the item you want to remove: ");
															int pid = sc.nextInt();

															try {
																int sellerId = s1.getSellerId(seller.getSeller_name())
																		.getSeller_id();
																String validationMessage = s1
																		.validateProductConditions(
																				sellerId,
																				pid);

																if (validationMessage.equals("Success")) {
																	String message = s1.deleteProduct(pid);
																	System.out.println(message);
																} else {
																	System.out.println(validationMessage);
																}
															} catch (SQLException e) {
																System.out.println(
																		"Error occurred while validating Product ID for removal.");
															}
															break;
														}

														case 4: {
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															int schoice3 = 0;
															while (schoice3 != 3) {
																main.showSellerOptions2();
																schoice3 = sc.nextInt();
																switch (schoice3) {
																	case 1: {
																		// view all sold product with dispute
																		if (incorrectAttempts > 0) {
																			incorrectAttempts--;
																		}
																		try {
																			int sellerId = s1
																					.getSellerId(
																							seller.getSeller_name())
																					.getSeller_id();
																			SoldProducts[] plist = s1
																					.showAllSoldProduct(
																							sellerId,
																							true);
																			System.out.println();
																			System.out.println(
																					"Sold Products (with Dispute):");
																			main.printLine();
																			for (SoldProducts product : plist) {
																				System.out.println(product);
																			}
																			main.printLine();
																		} catch (SQLException e) {
																			System.out.println(e.getMessage());
																		}
																		break;
																	}
																	case 2: {
																		// View all sold products without dispute
																		if (incorrectAttempts > 0) {
																			incorrectAttempts--;
																		}
																		try {
																			int sellerId = s1
																					.getSellerId(
																							seller.getSeller_name())
																					.getSeller_id();
																			SoldProducts[] plist = s1
																					.showAllSoldProduct(
																							sellerId,
																							false);
																			System.out.println();
																			System.out.println(
																					"Sold Products (without Dispute):");
																			main.printLine();
																			for (SoldProducts product : plist) {
																				System.out.println(product);
																			}
																			main.printLine();
																		} catch (SQLException e) {
																			System.out.println(e.getMessage());
																		}
																		break;
																	}
																	case 3: {
																		if (incorrectAttempts > 0) {
																			incorrectAttempts--;
																		}
																		break;
																	}
																	default:
																		System.out.println("Invalid Option\n");
																}
															}
															break;
														}
														case 5: {
															// view all unsold product
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															try {
																int sellerId = s1.getSellerId(seller.getSeller_name())
																		.getSeller_id();
																ProductFull[] plist = s1.showAllUnSoldProduct(sellerId);
																System.out.println();
																System.out.println("Unsold Products:");
																main.printLine();
																for (ProductFull product : plist) {
																	System.out.println(product);
																}
																main.printLine();
															} catch (SQLException e) {
																System.out.println(e.getMessage());
															}
															break;
														}
														case 6: {
															// Sell Product at Current Bid Price
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															System.out
																	.print("\nEnter the product id you want to sell: ");
															int productId = sc.nextInt();

															int sellerId = s1.getSellerId(seller.getSeller_name())
																	.getSeller_id();
															try {
																if (s1.isSellerProduct(sellerId, productId)) {
																	if (!s1.isProductSold(productId)) {

																		String message = s1
																				.sellProductAtCurrentBid(productId);
																		System.out.println(message);
																	} else {
																		System.out.println(
																				"Product is already sold. Sale failed.");
																	}
																} else {
																	System.out.println(
																			"Product does not belong to you. Sale failed.");
																}
															} catch (SQLException e) {
																System.out.println(e.getMessage());
															}
															break;
														}
														case 7: {
															if (incorrectAttempts > 0) {
																incorrectAttempts--;
															}
															break;
														}
														default:
															System.out.println(
																	"Invalid Option. Please enter a number between 1 and 7.\n");
													}
												} catch (NumberFormatException e) {
													incorrectAttempts++;
													if (incorrectAttempts >= MAX_ATTEMPTS) {
														System.out.println();
														System.out.println(
																"Maximum attempts reached. Exiting program... T_T");
														System.out.println();
														break outerLoop;
													}
													System.out.println("\nInvalid input. Please enter a valid number.");
													System.out.println("Number of Attempts Left: "
															+ (MAX_ATTEMPTS - incorrectAttempts));
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										} catch (SQLException e) {
											System.out.println(e.getMessage());
										}
										break;
									}
									case 2: {
										if (incorrectAttempts > 0) {
											incorrectAttempts--;
										}
										try {
											// Register Seller
											System.out.print("\nEnter seller email:");
											String semail = sc.next();

											if (s1.isSellerEmailAlreadyRegistered(semail)) {
												System.out.println("Email is already registered. Please try Again.");
												break;
											}

											System.out.print("Enter seller name :");
											String sname = sc.next();

											if (s1.isSellerNameAlreadyRegistered(sname)) {
												System.out.println("Name is already registered. Please try Again.");
												break;
											}

											System.out.print("Enter seller password:");
											String spass = sc.next();
											System.out.println();
											Date date = Date.valueOf(LocalDate.now());
											Seller seller = new Seller(sname, semail, spass, date);
											String message = s1.sellerRegistration(seller);
											userOptions.writeSellerDetailsToFile(seller);
											System.out.println(message);

										} catch (SQLException e) {
											e.printStackTrace();
										} catch (IOException e) {
											e.printStackTrace();
										}

										break;
									}
									case 3: {
										if (incorrectAttempts > 0) {
											incorrectAttempts--;
										}
										break;
									}
									default:
										System.out.println("Invalid Option. Please enter a number between 1 and 2.\n");
								}
							} catch (NumberFormatException e) {
								incorrectAttempts++;
								if (incorrectAttempts >= MAX_ATTEMPTS) {
									System.out.println();
									System.out.println("Maximum attempts reached. Exiting program... T_T");
									System.out.println();
									break outerLoop;
								}
								System.out.println("\nInvalid input. Please enter a valid number.");
								System.out.println("Number of Attempts Left: " + (MAX_ATTEMPTS - incorrectAttempts));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						s1.closeConnection();
						break;
					}
					case 4: {
						if (incorrectAttempts > 0) {
							incorrectAttempts--;
						}
						break;
					}
					default:
						System.out.println("\nInvalid Option. Please enter a number between 1 and 4.");
				}
			} catch (NumberFormatException e) {
				incorrectAttempts++;
				if (incorrectAttempts >= MAX_ATTEMPTS) {
					System.out.println();
					System.out.println("Maximum attempts reached. Exiting program... T_T");
					System.out.println();
					break outerLoop;
				}
				System.out.println("\nInvalid input. Please enter a valid number.");
				System.out.println("Number of Attempts Left: " + (MAX_ATTEMPTS - incorrectAttempts));
			}
		}
		System.out.println(
				" _____ _              _                      __                   _             ___ _ _      _   ___ _    _ ");
		System.out.println(
				"|_   _| |_  __ _ _ _ | |__  _  _ ___ _  _   / _|___ _ _   _  _ __(_)_ _  __ _  | _ ) (_)_ _ | |_| _ |_)__| |");
		System.out.println(
				"  | | | ' \\/ _` | ' \\| / / | || / _ \\ || | |  _/ _ \\ '_| | || (_-< | ' \\/ _` | | _ \\ | | ' \\| / / _ \\ / _` |");
		System.out.println(
				"  |_| |_||_\\__,_|_||_|_\\_\\  \\_, \\___/\\_,_| |_| \\___/_|    \\_,_/__/_|_||_\\__, | |___/_|_|_||_|_\\_\\___/_\\__,_|");
		System.out.println(
				"                            |__/                                        |___/                               ");

		sc.close();
	}
}