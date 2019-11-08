package com.practice.serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainDriver {
	
	public static void main(String[] args) {
		
		demo object = new demo(10000, "JohnDoe");
		String fileName = "/Users/Caio/Documents/workspace-sts-3.9.4.RELEASE/Project0PT1/src/com/practice/serializable/sample.ser.txt";
	try {
		FileOutputStream file = new FileOutputStream(fileName); 
        ObjectOutputStream out = new ObjectOutputStream(file);
        
        
        //OUTPUT(Serialization) of object
        out.writeObject(object); 
        
        out.close(); 
        file.close(); 
          
        System.out.println("Object has been serialized");
	}
	
		catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        }
	
	demo object1 = null;
	
	try
    {    
        // Reading the object from a file 
        FileInputStream file = new FileInputStream(fileName); 
        ObjectInputStream in = new ObjectInputStream(file); 
          
        // Method for deserialization of object 
        object1 = (demo)in.readObject(); 
          
        in.close(); 
        file.close(); 
          
        System.out.println("Object has been deserialized "); 
        System.out.println("funds = " + object1.funds); 
        System.out.println("name = " + object1.name); 
    } 
      
    catch(IOException ex) 
    { 
        System.out.println("IOException is caught"); 
    } 
      
    catch(ClassNotFoundException ex) 
    { 
        System.out.println("ClassNotFoundException is caught"); 
    } 
	
		
		
		
	}

}
