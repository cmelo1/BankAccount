package com.project.pt1;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;


public class BankAccount implements Serializable{ //Going to make it so when application is approved, the approve method will instantiate a bank account within the customer class.
	
	public int accountID = 0; //the Bank account will be serialized as the AccountID being the key. A joint account method will copy the AccountID to the 2nd customer.
	public double funds = 0;
	


	/**
	 * 
	 */

BankAccount(){ //The account ID will be generated when the employee approves it.
	Random r = new Random();
	this.accountID = r.nextInt(9999-1000)+1000;
	
// Give bank account unique ID
	
}

public double getFunds() {
	return funds;
}

public void depositFunds(double x) {
	this.funds += x;
	System.out.println("Deposited .... $" + x);
	System.out.println("New Balance .... $" + this.funds);
	this.updateFile();

}

public void withdrawFunds(double x) {
	Scanner sc = new Scanner(System.in);

	if ((funds - x) >= 0) {
		this.funds -= x;
		System.out.println("Dispensing .... "+ x);
		System.out.println("Your new balance is .... $" + this.getFunds());
		this.updateFile();


	} else {
		System.out.println("Error - Not enough funds");
		
	}
}


public void updateFile() {
	String fileName = "/Users/Caio/Documents/workspace-sts-3.9.4.RELEASE/Project0PT1/src/com/project/pt1/accounts/"+ accountID + ".ser";//#0 needed for writing to file

	//BankAccount object = this; 
	//WRITE TO FILE
	try {
		
		FileOutputStream file = new FileOutputStream(fileName); //#1 
        ObjectOutputStream out = new ObjectOutputStream(file);//#2 for writing to file
        
        
        //OUTPUT(Serialization) of object
        out.writeObject(this); //#3 thats needed for writing to file. *
        
        out.close(); 
        file.close(); 
          
        System.out.println("Bank Account Serialized & Updated");
        
	}
	
		catch(IOException ex) 
        { 
            System.out.println("IOException is caught - accountApp"); 
        }
	

	
}

public void readFile() {
	
String fileName = "/Users/Caio/Documents/workspace-sts-3.9.4.RELEASE/Project0PT1/src/com/project/pt1/accounts/"+ this.accountID + ".ser"; //hoooly shit this works

	BankAccount object1 = null;
	try
    {    
        // Reading the object from a file 
        FileInputStream file = new FileInputStream(fileName); 
        ObjectInputStream in = new ObjectInputStream(file); 
          
        // Method for deserialization of object 
        object1 = (BankAccount)in.readObject(); 
        
        this.accountID = object1.accountID;
        this.funds = object1.funds;
          
        in.close(); 
        file.close(); 
      this.accountID = object1.accountID;
      this.funds = object1.funds;
       
    } 
      
    catch(IOException ex) 
    { 
        System.out.println("No Account found by the name: '" +  this.accountID + "'"); 
    } 
      
    catch(ClassNotFoundException ex) 
    { 
        System.out.println("ClassNotFoundException is caught"); 
    }
	
	
	
}

public void cloneThis(BankAccount x) {
	
	this.accountID = x.accountID;
	this.funds = x.funds;
	
}

}
