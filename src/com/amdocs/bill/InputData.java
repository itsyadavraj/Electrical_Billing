//package Source;
package com.amdocs.bill;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class InputData {

	private int meterId;
	private String full_name;
	private String password;
	private String full_address;
	private String paymentStatus;
	private int totalBill;
	private int units;
	private int perUnitPrice;
	private String aadhar_card;
	private String mobile_number;
	private String email_id;
	LocalDate date_of_start;;

	Scanner scanner = new Scanner(System.in);
	Random random = new Random();

	// Generate a random 5-digit number for BILLID
	int min = 10000;
	int max = 99999;

	public InputData() {

		this.paymentStatus = "Done";
		this.totalBill = 0;
		this.units = 0;
	}

	public void takeInputFromUser() {
		System.out.println("---------Enter The Important Information for Registration---------");
		System.out.println("Enter Your Aadhar Card");
		aadhar_card = scanner.nextLine();
		System.out.println("Enter your Full Name");
		full_name = scanner.nextLine();
		date_of_start = LocalDate.now();
		System.out.println("Enter your Full Address ");
		full_address = scanner.nextLine();

		System.out.println("Enter Mobile No. : ");
		mobile_number = scanner.nextLine();

		System.out.println("Enter Email : ");
		email_id = scanner.nextLine();

		System.out.println("your meterID will be generated soon");

		meterId = random.nextInt((max - min) + 1) + min;
		System.out.println("----------------------------------------------");
		System.out.println("Your meterID is--> " + meterId);

		password = generatePassword();

		System.out.println("Your Password is --> " + password);
		System.out.println("***Kindly Note the Credentials***");
		System.out.println("---------------------------------------------------");

	}

	public String generatePassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		// Define the password length
		int passwordLength = 6;

		// Create a SecureRandom object for generating random numbers
		SecureRandom random = new SecureRandom();

		// Initialize a StringBuilder to build the password
		StringBuilder passwordBuilder = new StringBuilder();

		// Generate the random password
		for (int i = 0; i < passwordLength; i++) {
			// Get a random index within the character set
			int randomIndex = random.nextInt(characters.length());

			// Append the character at the random index to the password
			passwordBuilder.append(characters.charAt(randomIndex));
		}

		// Convert the StringBuilder to a String to get the final password
		String randomPassword = passwordBuilder.toString();

		return randomPassword;
	}

	void adminOperation() throws SQLException {
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Enter credentials for ADMIN");
//		System.out.println("Enter Username = 'SYSTEM' and Password = 'admin' ");
//		System.out.println("Enter Username");
//		String str1 = scanner.next();
//		System.out.println("Enter Password");
//		String pass = scanner.next();
//		if (str1.equals("SYSTEM") && pass.equals("admin")) {
//			// System.out.println("Perform Many Operation in future as admin");
			AdminOperation.TaskByAdmin();
//		}

	}

	public int getMeterId() {
		return meterId;
	}

	public String getFullName() {
		return full_name;
	}

	public String getPassword() {
		return password;
	}

	public String getStartDate() {
		return date_of_start.toString();
	}

	public String getAddress() {
		return full_address;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public int getTotalBill() {
		return totalBill;
	}

	public int getUnits() {
		return units;
	}

	public String getAadhar() {
		return aadhar_card;
	}

	public String getMobile() {
		return mobile_number;
	}

	public String getEmailId() {
		return email_id;
	}
	
	public String getPerpose() {
		System.out.println("Enter 1 for Private and 2 for Commercial");
		Scanner sc = new Scanner(System.in);
		int ch = sc.nextInt();
		while (true) {
			if (ch == 1) return "Private";
			else if (ch == 2) return "Commercial";
		}
	}
	
	public int getUnitPrice(String type_of_connection) {
		String ch = "Private";
		if (type_of_connection.equals(ch)) return 10;
		else return 50;
	}
}