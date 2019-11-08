/*TODO - Employee register option along with customer
 * TODO - Input Validation - Duplicate check
 * TODO- Employee Method that when APPROVES application - WILL copy accountID -> Secondary account 
 * TODO- Employee Method that when APPROVES application - WILL change status to approved.
 * 			-Additionally should impliment if not approved-> resitrict access. 
 * 
 * 
 */
package com.project.pt1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

enum Status {

	PENDING, APPROVED, DENIED;

}

enum jStatus {
	NOT_JOINT,PENDING, APPROVED, DENIED, LOST_IN_BEUROCRACY;
}

public class Customer implements Serializable,CustomerIF {

	public String username;
	public String password;
	public Status status;
	public jStatus jointStatus;
	BankAccount account = new BankAccount();

	Customer() {
	}

	Customer(String x, String y) {

		this.username = x;
		this.password = y;
		status = Status.PENDING;
		jointStatus = jStatus.NOT_JOINT;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getFunds() {
		return account.getFunds();
	}
	//setFunds will be employee/Admin option

	public void reDisplay() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Would you like to make another transaction?");
		System.out.println("1 \t Yes");
		System.out.println("2 \t No");

		int input = sc.nextInt();
		switch (input) {
		case 1:
			customerPrompt();
			break;
		case 2:
			System.out.println("Thank you for using Atlas Bank, Goodbye.");
			System.exit(0);
		}

	}

	public void setStatus(Status stat) {
		this.status = stat;
	}

	public Status getStatus() {
		return status;
	}
	public void withdrawFunds(double x) {
		Scanner sc = new Scanner(System.in);

		if ((account.funds - x) >= 0) {
			account.funds -= x;
			System.out.println("Dispensing .... "+ x);
			System.out.println("Your new balance is .... $" + account.getFunds());
			account.updateFile();


		} else {
			System.out.println("Error - Not enough funds");
			reDisplay();
			
		}
	}

	// TODO generate a toString() Method
	public void writeFile() {
		String fileName = "/Users/Caio/Documents/workspace-sts-3.9.4.RELEASE/Project0PT1/src/com/project/applications/"
				+ this.getUsername() + "applications.ser";

		try {

			FileOutputStream file = new FileOutputStream(fileName); // #1
			ObjectOutputStream out = new ObjectOutputStream(file);// #2 for writing to file

			// OUTPUT(Serialization) of object
			out.writeObject(this); // #3 thats needed for writing to file.

			out.close();
			file.close();

		} catch (IOException ex) {
			System.out.println("File IO error - Customer Model");
		}
	}

	// we dont need a customer read file.

	public void customerPrompt() {
		account.readFile(); //HOW TO DESERIALIZE BANK ACCOUNT AND READ INFO FROM IT.
		Scanner sc = new Scanner(System.in);
		System.out.println("What would you like to do?");
		System.out.println("1 \t Deposit Funds");
		System.out.println("2 \t Withdraw Funds");
		System.out.println("3 \t Transfer Funds");
		System.out.println("4 \t View Balance");
		System.out.println("5 \t Apply for a Joint Account"); // In the works
		System.out.println("6 \t Logout.");
		System.out.println("Please make a selection(1-6)");

		int selection = sc.nextInt();
		switch (selection) {

		case 1: // TODO verify input as XXX.XX & switch to double. With ALL money methods.
			System.out.print("How much would you like to deposit?");
			int depo = sc.nextInt();
			account.depositFunds(depo);
			reDisplay();
			break;
		case 2:
			System.out.println("Available funds .... $" + this.getFunds());
			System.out.println("Please enter withdrawl amount.");

			double wd = sc.nextDouble();
			account.withdrawFunds(wd);
			reDisplay();

			break;
		case 3:// Transfer funds between two banks I guess?
			break;
		case 4:
			System.out.println("Account Balance .... " + account.getFunds());
			reDisplay();
			break;
		case 5:
			System.out.println("Please enter the username for the secondary account");
			String tempName = sc.next();

			System.out.println("Please enter the password for the secondary account");
			String tempPw = sc.next();
			jointApplication(tempName, tempPw);
			break;
		case 6:
			System.out.println("Thank you for banking with Atlas");
			this.account.updateFile();
			System.exit(0);

			break;
		}
	}

	public void jointApplication(String x, String y) {

		{

			// Attempt to fetch a file with the extension named username + applications
			String fileName = "/Users/Caio/Documents/workspace-sts-3.9.4.RELEASE/Project0PT1/src/com/project/pt1/applications/"
					+ x + ".ser"; // hoooly shit this works

			Customer object1 = null;
			try {
				// Reading the object from a file
				FileInputStream file = new FileInputStream(fileName);
				ObjectInputStream in = new ObjectInputStream(file);

				// Method for deserialization of object
				object1 = (Customer) in.readObject();

				in.close();
				file.close();

				// TODO implement another branch so they cant get in if account is still
				// pending.
				if (object1.password.contentEquals(y)) {

					System.out.println("Joint Account Application Successfully Sent.");
					System.out.println("1 \t Continue");
					System.out.println("2 \t Logout");
					Scanner sc = new Scanner(System.in);
					int cont = sc.nextInt();
					switch (cont) {
					case 1:
						object1.customerPrompt();
						break;
					case 2:
						System.out.println("Thank you for using Atlas Bank. Goodbye.");
						System.exit(0);

					}

				} else {

					System.out.println("Secondary Account Password Incorrect. \nWould you like to try again? ");// TODO
																												// Should
																												// I
																												// give
																												// them
																												// a
																												// chance
																												// to
																												// rewrite
																												// username
																												// too?
																												// for
																												// now -
																												// NO!!!
					System.out.println("1\tYes");
					System.out.println("2\tNo");
					System.out.println("Please make a selection(1-2)");
					@SuppressWarnings("resource")
					Scanner sc = new Scanner(System.in);
					int selection = sc.nextInt();

					switch (selection) {
					case 1:
						System.out.println("Please enter secondary account password.");
						String pw = sc.next();

						jointApplication(x, pw); // uses same username that was passed to it, but new password
						break;
					case 2: // Send back to customer screen.
						customerPrompt();
						break;
					}
				}

			}

			catch (IOException ex) {
				System.out.println("No user found by the name: '" + x + "'");
			}

			catch (ClassNotFoundException ex) {
				System.out.println("ClassNotFoundException is caught");
			}

		}
	}

}
