package com.akalanka.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * File move utilities.
 * 
 * @author akalanka
 *
 */
public final class Utils {

	private static Logger logger = Logger.getLogger(Utils.class.getName());

	/**
	 * Move all files and folders within a given folder to a target folder.
	 * 
	 * @param sourceDir The source directory
	 * @param targetDir The target directory
	 * @throws FileMoveException
	 */
	public static void moveFiles(String sourceDir, String targetDir) throws FileMoveException {

		try {
			
			if (sourceDir == null || sourceDir.isEmpty() ||
					targetDir == null || targetDir.isEmpty()) {
				throw new FileMoveException("Source and target directories cannot be empty. Source : " +
					sourceDir + ", Target : " + targetDir);
			}
			
			Path sourcePath = Paths.get(sourceDir);
			
			File sourceFile = sourcePath.toFile();
			if (!sourceFile.exists() || !sourceFile.isDirectory()) {
				throw new FileMoveException("Source path does not exist or is not a directory");
			}
			
			Path targetPath = Paths.get(targetDir);
			
			File targetFile = targetPath.toFile();
			
			// Check and creates the target folder if it doesn't exist
			targetFile.mkdirs();
			
			logger.info("Target folder created : " + targetDir);
			
			Files.walkFileTree(sourcePath, new FileMoveVisitor(sourcePath, targetPath));

		} catch (InvalidPathException e) {
			throw new FileMoveException("Could not parse folder path", e);
		} catch (SecurityException e) {
			throw new FileMoveException("Failed to create target directory", e);
		} catch (IOException e) {
			throw new FileMoveException("Failed to move files", e);
		}
	}

}
