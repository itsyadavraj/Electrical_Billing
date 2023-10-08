package com.amdocs.bill;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Stack;


class Establish {
	static String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
	static String username = "SYSTEM";
	static String password = "Raj";
}

public class Main extends Establish {
	
	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		InputData inputData = new InputData();

		while (true) {
			System.out.println("Who are you ADMIN OR USER");
			System.out.println("Choose 1 for ADMIN and 2 for USER");
			System.out.println("press 3 for quit Operaion");
			int a = scanner.nextInt();
			if (a == 1) {
				int adminChoice = a;
				while (adminChoice == 1) {
					inputData.adminOperation();
					System.out.println("For Continue Press 1");
					adminChoice = scanner.nextInt();
				}
			}
			else if (a == 2) {
				while (true) {
					System.out.println("choose 1 for new User");
					System.out.println("Choose 2 for already Registered User");
					System.out.println("choose 3 for Exit");
					int b = scanner.nextInt();
					if (b == 1) {
						UserOperation.UserRegistation();
					} else if (b == 2) {
						checkUserProfile(username, password);
					} else {
						System.out.println("Thankyou for using our service");
						break;
					}
				} 

			} else if (a == 3) {
				break;
			}
		}
		// Define the data for the new record
	}

	
	public static void checkUserProfile(String username, String password) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter your targeted meterID");
		String targetMeterId = scan.nextLine(); // Replace with the specific meterId you're looking for
		System.out.println("Enter Your Password");
		String targetPassword = scan.nextLine(); // Replace with the specific password you're looking for

		try {
			// Establish a database connection
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

			// Define the SQL query with placeholders for meterId and password
			String sqlQuery = "SELECT * FROM ElectricityBill WHERE METER_NUMBER = ? AND PASSWORD = ? ";

			// Create a PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

			// Set parameter values for the placeholders
			preparedStatement.setString(1, targetMeterId);
			preparedStatement.setString(2, targetPassword);

			// Execute the query and retrieve the result set
			ResultSet resultSet = preparedStatement.executeQuery();

			// Process the result set
			while (resultSet.next()) {
				int meterId = resultSet.getInt("METER_NUMBER");
				String fullName = resultSet.getString("FULL_NAME");
				String MOBILE_NUMBER = resultSet.getString("MOBILE_NUMBER");
				String EMAIL_ID = resultSet.getString("EMAIL_ID");
				String paymentStatus = resultSet.getString("BILLING_STUTAS");
				int totalBill = resultSet.getInt("TOTAL_BILL");
				int perUnitPrice = resultSet.getInt("UNIT_PRIZE");

				// Print or process the retrieved data
				System.out.println("---------------------------------------------------------------------");
				System.out.println("---------------------------------------------------------------------");
				System.out.println("---------------------------------------------------------------------");

				System.out.println("Meter ID: " + meterId);
				System.out.println("Full Name: " + fullName);
				System.out.println("Mobile Number: " + MOBILE_NUMBER);
				System.out.println("Email Id: " + EMAIL_ID);
				System.out.println("PaymentStatus: " + paymentStatus);
				System.out.println("TotalBills :" + totalBill);
				System.out.println("per Unit Price :" + perUnitPrice);

				System.out.println("---------------------------------------------------------------------");
				System.out.println("---------------------------------------------------------------------");
				System.out.println("---------------------------------------------------------------------");
				
				System.out.println("Do You Want To Pay Bill");
			}

			// Close resources
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
