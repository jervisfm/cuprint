package com.jmuindi.cuprint;

import java.io.Serializable;


public class PrinterOptions implements Serializable{		
	private static final long serialVersionUID = 1L;
	public boolean double_sided = false;
	public boolean collate = false; 	
	public int copies = 1;
	
	public PrinterOptions() {} // Use Default Options
	public PrinterOptions(boolean double_sided, boolean collate, int copies) {
		this.double_sided = double_sided;
		this.collate = collate;
		this.copies = copies; 
	}	
}