package com.amdocs.bill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UserOperation extends Establish {
	static InputData inputData = new InputData();
	public static void UpdatePassword(int meterNumber) throws SQLException {
		Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
		System.out.println("Enter New Password of User");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		String sqlQuery = "UPDATE ElectricityBill set PASSWORD = ? where METER_NUMBER = ?";
		PreparedStatement ps = connection.prepareStatement(sqlQuery);
		ps.setString(1, name);
		ps.setInt(2, meterNumber);
		int res = ps.executeUpdate();
		if (res > 0)
			System.out.println("Password Updated");
		else
			System.out.println("Failed to Update");
		ps.close();
	}
	
	public static void UserRegistation() {
			inputData.takeInputFromUser();
			System.out.println("--------------------------------------------------------------------");
			int meterId = inputData.getMeterId();
			String aadhar_card = inputData.getAadhar();
			String fullName = inputData.getFullName();
			String startDate = inputData.getStartDate();
			String address = inputData.getAddress();
			String paymentStatus = inputData.getPaymentStatus();
			int totalBill = inputData.getTotalBill();
			int totalunits = inputData.getUnits();
			String mobile_number = inputData.getMobile();
			String email_id = inputData.getEmailId();
			String Current_Status = "Active";
			String PERPOSE = inputData.getPerpose();
			int perUnitPrice = inputData.getUnitPrice(PERPOSE);
			String PASSWORD = inputData.getPassword();
			try {
				insertIntoOracleDB(aadhar_card, meterId, fullName, startDate, PERPOSE, address, paymentStatus, 
						totalBill, totalunits, perUnitPrice, jdbcUrl, username, password, mobile_number, 
						email_id, PASSWORD, Current_Status);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void insertIntoOracleDB(String AADHAR_CARD, int METER_NUMBER, String FULL_NAME,
			String DATE_OF_CONNECTION, String PERPOSE,
			String FULL_ADDRESS, String BILLING_STUTAS, int TOTAL_BILL, int TOTAL_UNIT_USAGE, int UNIT_PRIZE,
			String jdbcUrl, String username, String password, String MOBILE_NUMBER, String EMAIL_ID, String PASSWORD,
			String CURRENT_STATUS_OF_CONNECTION) {
		try {
			// Establish a database connection
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			TOTAL_BILL = TOTAL_UNIT_USAGE * UNIT_PRIZE;
			if (TOTAL_BILL != 0) {
				CURRENT_STATUS_OF_CONNECTION = "ACTIVE";
				BILLING_STUTAS = "NOT PAID";
			} else {
				BILLING_STUTAS = "PAID";
			}

			// Define the SQL insert statement
			String insertSQL = "INSERT INTO ElectricityBill(AADHAR_CARD, METER_NUMBER, FULL_NAME,"
					+ " DATE_OF_CONNECTION, PERPOSE, FULL_ADDRESS, BILLING_STUTAS, TOTAL_BILL,"
					+ " TOTAL_UNIT_USAGE, UNIT_PRIZE, PASSWORD, MOBILE_NUMBER, EMAIL_ID,"
					+ " CURRENT_STATUS_OF_CONNECTION)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			// Create a PreparedStatement
			Statement statement = connection.createStatement();
			PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

			// Set values for the placeholders
			preparedStatement.setString(1, AADHAR_CARD);
			preparedStatement.setInt(2, METER_NUMBER);
			preparedStatement.setString(3, FULL_NAME);
			preparedStatement.setString(4, DATE_OF_CONNECTION);
			preparedStatement.setString(5, PERPOSE);
			preparedStatement.setString(6, FULL_ADDRESS);
			preparedStatement.setString(7, BILLING_STUTAS);
			preparedStatement.setInt(8, TOTAL_BILL);
			preparedStatement.setInt(9, TOTAL_UNIT_USAGE);
			preparedStatement.setInt(10, UNIT_PRIZE);
			preparedStatement.setString(11, PASSWORD);
			preparedStatement.setString(12, MOBILE_NUMBER);
			preparedStatement.setString(13, EMAIL_ID);
			preparedStatement.setString(14, CURRENT_STATUS_OF_CONNECTION);
			
			// Execute the SQL statement to insert the record
			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Record inserted successfully.");
			} else {
				System.out.println("Record insertion failed.");
			}
			statement.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
