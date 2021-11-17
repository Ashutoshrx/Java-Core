package com.prac.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionMainApp {
	public static void main(String[] args) {
		int key, option;
		
		
//    	Connection for the mysql
		JdbcConnect jdbcConnect = new JdbcConnect();

		Scanner sc = new Scanner(System.in);

		System.out.println("\n 1. Sign up  " + "\n 2. Log in "+" \n 3. Cancel Operation");

		
		do {
			System.out.println("\n Enter the option you want to choose:");
			option = sc.nextInt();
			switch (option) {
			case 1:
				System.out.println("\n Enter your NAME, GENDER,LOGINID(10),CUSTOMERID,USERPASSWORD(10)");
				jdbcConnect.createAccount(sc.next(), sc.next(), sc.next(), sc.nextInt(), sc.next());
				System.out.println();

				break;
			case 2:
				System.out.println("\n Enter your User login Id:");
				String logId = sc.next();
				System.out.println("\nEnter your password:");
				String pwd = sc.next();
				
				do {
				System.out.println("\n Congrats! you have been logged in.");
				System.out.println("\n  1.Show Account Details\n 2.Show Balance \n 3.Deposit Amount\n What would you like to do?");
				key=sc.nextInt();
				
				switch (key) {
				
				case 1:
					jdbcConnect.showAccountDetails(logId);
					break;
				case 2:
					jdbcConnect.viewBalance(logId);
					break;
				case 3:
					System.out.println("\nEnter the amount you want to deposit:");
					int enterAmount=sc.nextInt();
					int loggedInCustomerId=jdbcConnect.getCustomerId(logId);
					jdbcConnect.depositAmount(enterAmount,loggedInCustomerId);
					break;
						
				default:
					throw new IllegalArgumentException("Access Denied " + key);
				}
				}while(key<=2);
			
				break;
			default:
				System.out.println("\nThankyou for choosing us!\n");
			}

			System.out.println("\n 1. Sign up  " + "\n 2. Log in "+" \n 3.Cancel Operation");

		} while (option <= 2);

		

	}
}



 
