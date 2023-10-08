package com.amdocs.bill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminOperation extends Establish {

//	private static String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE"; // Replace with your Oracle database
																			// connection URL
//	private static String username = "SYSTEM";
//	private static String password = "Raj";
	private static int meterNumber = 0;
	private static int No_of_Unit = 0;
	private static int UNIT_PRIZE = 0;
	private static String name_of_user = "";

	private static void CollectData(String detail) {
		try {
			// Establish a database connection
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

			// Define the SQL query with placeholders for meterId and password
			String sqlQuery = "SELECT * FROM ElectricityBill "
					+ "WHERE AADHAR_CARD = ? OR MOBILE_NUMBER = ? OR EMAIL_ID = ?";

			// Create a PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

			// Set parameter values for the placeholders
			preparedStatement.setString(1, detail);
			preparedStatement.setString(2, detail);
			preparedStatement.setString(3, detail);

			// Execute the query and retrieve the result set
			ResultSet resultSet = preparedStatement.executeQuery();

			// Process the result set
			while (resultSet.next()) {

				meterNumber = resultSet.getInt("METER_NUMBER");
				name_of_user = resultSet.getString("FULL_NAME");
				No_of_Unit = resultSet.getInt("TOTAL_UNIT_USAGE");
				UNIT_PRIZE = resultSet.getInt("UNIT_PRIZE");
			}
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void PayBill(String detail) {
		// System.out.println("Search User By Aadhar Card or Mobile Number or Email
		// Id");
		Scanner sc = new Scanner(System.in);
		// String detail = sc.nextLine();
		Search(detail);
		System.out.println("Enter Amt. For Paid: ");
		int amt = sc.nextInt();
		CollectData(detail);
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			int totalBillCost = (No_of_Unit * UNIT_PRIZE) - amt;
			String BILLING_STUTAS = "";
			if (totalBillCost > 0) {
				BILLING_STUTAS = "Not Paid";
			} else
				BILLING_STUTAS = "Paid";
			String sqlQuery = "UPDATE ElectricityBill set TOTAL_UNIT_USAGE = ?, TOTAL_BILL = ?, BILLING_STUTAS = ? where METER_NUMBER = ?";
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			ps.setInt(1, totalBillCost / 10);
			ps.setInt(2, totalBillCost);
			ps.setString(3, BILLING_STUTAS);
			ps.setInt(4, meterNumber);
			int res = ps.executeUpdate();
			if (res > 0)
				System.out.println("Unit Updated");
			else
				System.out.println("Failed to Update");
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void MeterCheck(String Detail) {
		 CollectData(Detail);
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			System.out.println("Enter Unit for " + name_of_user);
			Scanner sc = new Scanner(System.in);
			int addUnit = sc.nextInt();
			addUnit += No_of_Unit;
			int totalBillCost = addUnit * UNIT_PRIZE;
			System.out.println(totalBillCost);
			String BILLING_STUTAS = "";
			if (totalBillCost > 0) {
				BILLING_STUTAS = "Not Paid";
			} else
				BILLING_STUTAS = "Paid";
			String sqlQuery = "UPDATE ElectricityBill set TOTAL_UNIT_USAGE = ?, TOTAL_BILL = ?, BILLING_STUTAS = ? where METER_NUMBER = ?";
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			ps.setInt(1, addUnit);
			ps.setInt(2, totalBillCost);
			ps.setString(3, BILLING_STUTAS);
			ps.setInt(4, meterNumber);
			int res = ps.executeUpdate();
			if (res > 0)
				System.out.println("Unit Updated");
			else
				System.out.println("Failed to Update");
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean Search(String detail) {

		try {
			// Establish a database connection
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

			// Define the SQL query with placeholders for meterId and password
			String sqlQuery = "SELECT * FROM ElectricityBill "
					+ "WHERE AADHAR_CARD = ? OR MOBILE_NUMBER = ? OR EMAIL_ID = ?";

			// Create a PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

			// Set parameter values for the placeholders
			preparedStatement.setString(1, detail);
			preparedStatement.setString(2, detail);
			preparedStatement.setString(3, detail);

			// Execute the query and retrieve the result set
			ResultSet resultSet = preparedStatement.executeQuery();

			// Process the result set
			while (resultSet.next()) {

				int meterId = resultSet.getInt("METER_NUMBER");
//				meterNumber = meterId;
				String fullName = resultSet.getString("FULL_NAME");
				String MOBILE_NUMBER = resultSet.getString("MOBILE_NUMBER");
				String EMAIL_ID = resultSet.getString("EMAIL_ID");
				String paymentStatus = resultSet.getString("BILLING_STUTAS");
				int totalBill = resultSet.getInt("TOTAL_BILL");
				int perUnitPrice = resultSet.getInt("UNIT_PRIZE");
//				UNIT_PRIZE = perUnitPrice;
				

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
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void TaskByAdmin() {
		// TODO Auto-generated method stub
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("Search User By Aadhar Card or Mobile Number or Email Id");
			System.out.println("Enter The User Detail : ");
			String detail = scan.nextLine();
			boolean result = Search(detail);
			if (result != true) {
				System.out.println("No User Found");
			} else {
				System.out.println("Press 1 for update Name");
				System.out.println("Press 2 for update Mobile");
				System.out.println("Press 3 for update Email Id");
				System.out.println("Press 4 for update Address");
				System.out.println("Press 5 for update Aadhar Number");
				System.out.println("Press 6 for update Password");
				System.out.println("Press 7 for Add Unit");
				System.out.println("Press 8 for Pay Bill");

				// Establish a database connection
				Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

				int val = scan.nextInt();

				if (val == 1) {
					System.out.println("Enter New Name of User");
					Scanner sc = new Scanner(System.in);
					String name = sc.nextLine();
					String sqlQuery = "UPDATE ElectricityBill set FULL_NAME = ? where METER_NUMBER = ?";
					PreparedStatement ps = connection.prepareStatement(sqlQuery);
					ps.setString(1, name);
					ps.setInt(2, meterNumber);
					int res = ps.executeUpdate();
					if (res > 0)
						System.out.println("Name Updated");
					else
						System.out.println("Failed to Update");
					ps.close();

				} else if (val == 2) {
					System.out.println("Enter New Mobile Number of User");
					Scanner sc = new Scanner(System.in);
					String name = sc.nextLine();
					String sqlQuery = "UPDATE ElectricityBill set MOBILE_NUMBER = ? where METER_NUMBER = ?";
					PreparedStatement ps = connection.prepareStatement(sqlQuery);
					ps.setString(1, name);
					ps.setInt(2, meterNumber);
					int res = ps.executeUpdate();
					if (res > 0)
						System.out.println("Mobile Number Updated");
					else
						System.out.println("Failed to Update");
					ps.close();
				} else if (val == 3) {
					System.out.println("Enter New Email Id of User");
					Scanner sc = new Scanner(System.in);
					String name = sc.nextLine();
					String sqlQuery = "UPDATE ElectricityBill set EMAIL_ID = ? where METER_NUMBER = ?";
					PreparedStatement ps = connection.prepareStatement(sqlQuery);
					ps.setString(1, name);
					ps.setInt(2, meterNumber);
					int res = ps.executeUpdate();
					if (res > 0)
						System.out.println("Email Id Updated");
					else
						System.out.println("Failed to Update");
					ps.close();
				} else if (val == 4) {
					System.out.println("Enter New Address of User");
					Scanner sc = new Scanner(System.in);
					String name = sc.nextLine();
					String sqlQuery = "UPDATE ElectricityBill set FULL_ADDRESS = ? where METER_NUMBER = ?";
					PreparedStatement ps = connection.prepareStatement(sqlQuery);
					ps.setString(1, name);
					ps.setInt(2, meterNumber);
					int res = ps.executeUpdate();
					if (res > 0)
						System.out.println("Address Updated");
					else
						System.out.println("Failed to Update");
					ps.close();
				} else if (val == 5) {
					System.out.println("Enter New Aadhar Card Number of User");
					Scanner sc = new Scanner(System.in);
					String name = sc.nextLine();
					String sqlQuery = "UPDATE ElectricityBill set AADHAR_CARD = ? where METER_NUMBER = ?";
					PreparedStatement ps = connection.prepareStatement(sqlQuery);
					ps.setString(1, name);
					ps.setInt(2, meterNumber);
					int res = ps.executeUpdate();
					if (res > 0)
						System.out.println("Aadhar Card Updated");
					else
						System.out.println("Failed to Update");
					ps.close();
				} else if (val == 6) {
					UserOperation.UpdatePassword(meterNumber);
				} else if (val == 7) {
					MeterCheck(detail);
				} else if (val == 8) {
					PayBill(detail);
					Search(detail);
				} else {
					System.out.println("Invalid Input");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
