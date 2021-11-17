package com.prac.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JdbcConnect {
	public static final String URL = "jdbc:mysql://localhost:3306/mydb";
	public static final String USERNAME = "root";
	public static final String USERPASSWORD = "root";

	Statement stmt;
	Connection dbCon;
	String query;
	PreparedStatement prepSt;
	Scanner sc = new Scanner(System.in);
	Integer x[]= {5004001,5004002,5004003,50040056,5004087,5004006,5004007,5004099,5004009,5004100,5004011,5004012};

	public JdbcConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			dbCon = DriverManager.getConnection(URL, USERNAME, USERPASSWORD);
			System.out.println(
					"\n Thank You for choosing our XYZ bank. \n" + " You are getting redirected to our server ");
		} catch (ClassNotFoundException e) {
			System.out.println("Registering of driver failed : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Connection failed : " + e.getMessage());
		}
	}

	void createAccount(String customerName, String gender, String usePin, int custId, String userLoginId) {
		query = "INSERT INTO customers(customerName,gender,userPwd,custId,userLoginId) values(?,?,?,?,?)";
		try {
			prepSt = dbCon.prepareStatement(query);

			prepSt.setString(1, customerName);
			prepSt.setString(2, gender);
			prepSt.setString(3, usePin);
			prepSt.setInt(4, custId);
			prepSt.setString(5, userLoginId);

			int res = prepSt.executeUpdate();
			if (res > 0) {
				System.out.println("\nCongragulation! You have created your account.");
				insertIntoAccount(x[custId-1], custId);
			}
		} catch (SQLException e) {
			System.out.println("\nCouldn't create your account. " + e.getMessage());
		}
	}

	void showAccountDetails(String userLoginId) {
		query = "select customers.customerName,account.accountNo,customers.gender,customers.custId "
				+ "from customers inner join account on customers.custId=account.custId where customers.userLoginId=?";
		try {
			prepSt = dbCon.prepareStatement(query);

			prepSt.setString(1, userLoginId);

			ResultSet res = prepSt.executeQuery();

			while (res.next()) {
				System.out.println(res.getString("customerName") + "  |  " + res.getInt("accountNo") + "  |  "
						+ res.getString("gender")+ "  |  " +res.getString("userLoginId"));
				int customerId=res.getInt("custId");
			}
		} catch (SQLException e) {
			System.out.println("Error showing your details: " + e.getMessage());
		}

	}

	void insertIntoAccount(int accountNo, int custId) {

		query = "insert into account (accountNo,custId,balance) values(?,?,0)";
		

		try {
			prepSt = dbCon.prepareStatement(query);
			prepSt.setInt(1, accountNo);
			prepSt.setInt(2, custId);
			
			int res= prepSt.executeUpdate();
			System.out.println(res+"->Account has been created.");

		} catch (SQLException e) {
			System.out.println("Appending to account's data failed: " + e.getMessage());
		}

	}
	void viewBalance(String userLoginId)
	{
		query="select customers.customerName,account.accountNo,account.balance"
				+ " from customers inner join account on customers.custId=account.custId where customers.userLoginId=?;";
		
		try {
			prepSt=dbCon.prepareStatement(query);
			prepSt.setString(1, userLoginId);
			
			ResultSet res= prepSt.executeQuery();
			while(res.next()) {
				if(res.getInt("balance")<1000) {
					System.out.println("Your Account balance is too low.");
					System.out.println(res.getString("customerName")+ 
							"  |  "+res.getInt("accountNo")+ "  |  "+res.getInt("balance"));
				}
				else {
					System.out.println(res.getString("customerName")+
							"  |  "+res.getInt("accountNo")+ "  |  "+res.getInt("balance"));					
				}
					
			}
		} catch (SQLException e) {
			System.out.println("Error viewing your balance: "+e.getMessage());
		}
		
	}
	
	int getCustomerId(String userLoginId)
	{
		query="select custId from customers where userLoginId=?";
		int customerId=0;
		try {
			prepSt=dbCon.prepareStatement(query);
			prepSt.setString(1, userLoginId);
			ResultSet res= prepSt.executeQuery();
			while(res.next()) {
				customerId= res.getInt("custId");
			}
			
		} catch (SQLException e) {
			System.out.println("Sorry! Unable to fetch your customerId:"+e.getMessage());
		}
		
		return customerId;
	}
	
	void depositAmount(int balance,int custId)
	{
		query="update account set balance=balance+? where custId=? ";
		try {
			prepSt=dbCon.prepareStatement(query);
			prepSt.setInt(1,balance);
			prepSt.setInt(2,custId );
			
			
			int res= prepSt.executeUpdate();
			if(res>0)
				System.out.println("Amount has been deposited to your account.");			
		} catch (SQLException e) {
			System.out.println("Error! depositing the entered amount!");
		}		
	}

}
