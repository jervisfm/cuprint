package com.jmuindi.cuprint;

import java.io.File;
import java.io.Serializable;

/**
 * Encapsulates the state of the PrintActivity
 * @author Jervis
 */
public class PrintActivityState implements Serializable {				
	
	private static final long serialVersionUID = 1L;
	/**
	 * Building where printer is located. 
	 */
	public String building;
	
	/**
	 * Name of printer
	 */
	public String printer;
	
	/**
	 * Options to use for the selected printer.  
	 */	
	public PrinterOptions printerOptions; 
	
	/**
	 * Filename of document to be printed
	 */
	public String filename; 

	/**
	 * Actual file to be printed
	 */
	public File file;
	
	/**
	 * Current Status Message
	 */	
	public String statusmessage;
	
	/**
	 * Is the top status bar currently visible
	 */
	public boolean statusbarvisible; 
	
	public PrintActivityState() {}
	public void setBuilding(String building) {
		this.building = building;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	public void setPrintOptions(PrinterOptions printerOptions) {
		this.printerOptions = printerOptions;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setStatusmessage(String statusmessage) {
		this.statusmessage = statusmessage;
	}

	public void setStatusbarvisible(boolean statusbarvisible) {
		this.statusbarvisible = statusbarvisible;
	}
	
	public String getBuilding() {
		return building;
	}
	public String getPrinter() {
		return printer;
	}
	public PrinterOptions getPrinterOptions() {
		return printerOptions;
	}
	public String getFilename() {
		return filename;
	}
	public File getFile() {
		return file;
	}
	public String getStatusmessage() {
		return statusmessage;
	}
	public boolean isStatusbarvisible() {
		return statusbarvisible;
	}
	public boolean isCollate() {
		if (this.printerOptions != null) {
			return this.printerOptions.collate;
		} else {
			PrinterOptions defaultOptions = new PrinterOptions(); 
			return defaultOptions.collate;
		}
	}
	public boolean isDoubleSided() {
		if (this.printerOptions != null) {
			return this.printerOptions.double_sided; 
		} else {
			PrinterOptions defaultOptions = new PrinterOptions(); 
			return defaultOptions.double_sided;
		}
	}
	public int getCopies() {
		if (this.printerOptions != null) {
			return this.printerOptions.copies; 
		} else {
			PrinterOptions defaultOptions = new PrinterOptions(); 
			return defaultOptions.copies; 
		}
	}
}