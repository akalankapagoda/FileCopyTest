package com.akalanka.test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main execution class for running file move operation.
 * 
 * @author akalanka
 *
 */
public class Main {
	
	private static Logger logger = Logger.getLogger(Main.class.getName());
	
	private static String sourcDir;
	
	private static String targetDir;
	
	public static void main(String[] args) {
		
		try {
			parseInputs(args);
			Utils.moveFiles(sourcDir, targetDir);
			
			logger.info("File move completed");
		} catch (FileMoveException e) {
			logger.log(Level.SEVERE, "An error occured while moving files", e);
		}
	}
	
	/**
	 * Parse input arguments to validate and resolve parameters.
	 * 
	 * @param args Program arguments
	 * @throws FileMoveException
	 */
	private static void parseInputs(String[] args) throws FileMoveException {
		
		if (args == null || args.length < 2) {
			logger.log(Level.WARNING, "Invalid input\n"
					+ "Syntax : Main <source_dir> <target_dir>");
			throw new FileMoveException("Invalid inputs");
		}
		
		sourcDir = args[0];
		targetDir = args[1];
		
	}

}
