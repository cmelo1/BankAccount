package com.project.pt1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class MainDriver {
	
	public static void main(String[] args) {

		prompt(); 	
		
	}
	
	public static void accountApp(String un, String pw) {  //( username,password)		
		String fileName = "/Users/Caio/Documents/workspace-sts-3.9.4.RELEASE/Project0PT1/src/com/project/pt1/applications/"+ un + ".ser";//#0 needed for writing to file

		Customer object = new Customer(un,pw);
		//WRITE TO FILE
		try {
			
			FileOutputStream file = new FileOutputStream(fileName); //#1 
	        ObjectOutputStream out = new ObjectOutputStream(file);//#2 for writing to file
	        
	        
	        //OUTPUT(Serialization) of object
	        out.writeObject(object); //#3 thats needed for writing to file.
	        
	        out.close(); 
	        file.close(); 
	          
	        System.out.println("Account Application Request Sent - Application Status: " + object.getStatus());
		}
		
			catch(IOException ex) 
	        { 
	            System.out.println("IOException is caught - accountApp"); 
	        }
		
		
	}
	
	public static void login(String x,String y) {
	
	
	//Attempt to fetch a file with the extension named username + applications
	String fileName = "/Users/Caio/Documents/workspace-sts-3.9.4.RELEASE/Project0PT1/src/com/project/pt1/applications/"+ x + ".ser"; //hoooly shit this works
	
	Customer object1 = null;
	try
    {    
        // Reading the object from a file 
        FileInputStream file = new FileInputStream(fileName); 
        ObjectInputStream in = new ObjectInputStream(file); 
          
        // Method for deserialization of object 
        object1 = (Customer)in.readObject(); 
          
        in.close(); 
        file.close(); 
         
        //TODO implement another branch so they cant get in if account is still pending.
        if(object1.password.contentEquals(y)) {
        	
        	System.out.println("Login Successful");
        	object1.customerPrompt();
        	
        }
        else { 
        	
        	
        	System.out.println("Login Unsuccessful \nWould you like to try again? ");//TODO Should I give them a chance to rewrite username too? for now - NO!!!
        	System.out.println("1\tYes");
        	System.out.println("2\tNo");
        	System.out.println("Please make a selection(1-2)");
        	@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
        	int selection = sc.nextInt(); 
        	
        	switch(selection) {
        	case 1: 
    			System.out.println("Please enter your password");
    			String pw = sc.next();
    			
    			login(x,pw); //uses same username that was passed to it, but new password
        		break;
        	case 2: //Send back to main screen.
        		prompt(); 
        		break;
        	}
        }
       
    } 
      
    catch(IOException ex) 
    { 
        System.out.println("No user found by the name: '" + x + "'"); 
    } 
      
    catch(ClassNotFoundException ex) 
    { 
        System.out.println("ClassNotFoundException is caught"); 
    } 

	}
	
	public static void prompt() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Atlas Bank, please select from the available options:");
		
		System.out.println("1 \t Apply for an Account.");
		System.out.println("2 \t Customer Login. ");
		System.out.println("3 \t Employee Login. ");
		System.out.println("4 \t Exit");
		System.out.println("Please make a selection(1-4)");
		int selection = sc.nextInt();
		
		switch(selection)
		{
		
		case 1: //TODO - ADD duplicate username validation
			System.out.println("Please enter your username");
			String tempName = sc.next();
			
			System.out.println("Please enter your password");
			String tempPassword = sc.next();
			
			accountApp(tempName,tempPassword);
			
			break;
		case 2:
			System.out.println("Please enter your username");
			String login = sc.next();
			System.out.println("Please enter your password");
			String pw = sc.next();
			
			login(login,pw);
			break;
		case 3: //TODO  implement employee login, with seperate .ser file.
			break;
			
		case 4:
			System.out.println("Now exiting......");
			System.exit(0);
			break;
		default:
			System.out.println("Invalid Selection made, please enter a valid selection");
		}
	}

}
