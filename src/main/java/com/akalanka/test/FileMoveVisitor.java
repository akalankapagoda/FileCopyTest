package com.akalanka.test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Visitor for a walkFileTree operation that moves files to a target folder.
 * 
 * @author akalanka
 *
 */
public class FileMoveVisitor extends SimpleFileVisitor<Path> {
	
	private Logger logger = Logger.getLogger(FileMoveVisitor.class.getName());
	
	private Path source;
	private Path target;
	
	/**
	 * Constructor which initializes source and target.
	 * 
	 * @param source Source directory path
	 * @param target Target directory path
	 * 
	 * @throws FileMoveException
	 */
	FileMoveVisitor(Path source, Path target) throws FileMoveException {
		
		if (source == null || target == null) {
			throw new FileMoveException("Sourch path and target path cannot be empty");
		}
		
		this.source = source;
		this.target = target;
	}

	/**
	 * Create a directory in the target folder that matches a directory in the source.
	 * 
	 * @param dir The directory that is about to visit
	 * @param attrs The directory's basic attributes
	 * 
	 * @return Unless operation failed returns {@link FileVisitResult#CONTINUE}
	 * @throws IOException
	 */
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		
		Path relativePath = source.relativize(dir);
		
		Path destinationDir = target.resolve(relativePath);
		
		// Create destination directory
		destinationDir.toFile().mkdir();
		
		logger.log(Level.INFO, "Created Directory " + destinationDir.toString());
		return FileVisitResult.CONTINUE;
	}

	/**
	 * Visit a file and move that file to the corresponding location in the target directory.
	 * 
	 * @param file The file which is visiting
	 * @param attrs The file's basic attributes
	 * 
	 * @return Unless operation failed returns {@link FileVisitResult#CONTINUE}
	 * @throws IOException
	 */
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		
		Path relativePath = source.relativize(file);
		
		Path destinationFile = target.resolve(relativePath);
		
		Files.move(file, destinationFile);
		
		return FileVisitResult.CONTINUE;
	}

	/**
	 * Removes a folder in source in which all the files are moved to the target folder.
	 * 
	 * @param dir The directory in which all the files traversed and moved to the target folder
     * @param   exc
     *          {@code null} if the iteration of the directory completes without
     *          an error; otherwise the I/O exception that caused the iteration
     *          of the directory to complete prematurely
	 * 
	 * @return Unless operation failed returns {@link FileVisitResult#CONTINUE}
	 * @throws IOException
	 */
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		
		if (exc != null) { // Log and continue
			logger.log(Level.WARNING, "There was an error in moving files inside " + dir, exc);
		}
		
		// If this is not the root source directory delete this
		if (source.compareTo(dir) < 0) {
			dir.toFile().delete();
		}
		
		return FileVisitResult.CONTINUE;
	}
	
	

}
