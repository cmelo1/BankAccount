package com.practice.serializable;
import java.io.*;

public class demo implements java.io.Serializable {
	
	public int funds;
	public String name;

	
	demo(int a, String b){
		
		this.funds = a;
		this.name = b;
		
	}
}
