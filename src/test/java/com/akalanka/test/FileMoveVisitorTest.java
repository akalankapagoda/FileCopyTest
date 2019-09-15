package com.akalanka.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests FileMoveVisitor functinality.
 * 
 * @author akalanka
 *
 */
public class FileMoveVisitorTest extends BaseTest {
	
	private static final String FOLDER_1_NAME = "Folder1";
	
	private static final String FOLDER_2_NAME = "Folder2";
	private static final String FOLDER_2_FILE_1_NAME = "folder2File1.txt";
	
	private static final String FOLDER_3_NAME = "Folder3";
	private static final String FOLDER_4_NAME = "Folder4";
	
	private static FileMoveVisitor fileMoveVisitor;
	
	private static Path folder1Path;
	
	private static Path folder2Path;
	private static Path folder2File1Path;
	
	private static Path folder3Path;
	
	private static Path folder4Path;
	
	/**
	 * Setup required files and folders.
	 * 
	 * @throws FileMoveException
	 * @throws IOException
	 */
	@BeforeClass
	public static void setUp() throws FileMoveException, IOException {
		fileMoveVisitor = new FileMoveVisitor(sourceDirPath, targetDirPath);
		
		folder1Path = sourceDirPath.resolve(FOLDER_1_NAME);
		folder1Path.toFile().mkdirs();
		
		folder2Path = sourceDirPath.resolve(FOLDER_2_NAME);
		folder2Path.toFile().mkdirs();
		
		folder3Path = sourceDirPath.resolve(FOLDER_3_NAME);
		folder3Path.toFile().mkdirs();
		
		folder4Path = sourceDirPath.resolve(FOLDER_4_NAME);
		folder4Path.toFile().mkdirs();
		
		folder2File1Path = folder2Path.resolve(FOLDER_2_FILE_1_NAME);
		folder2File1Path.toFile().createNewFile();
	}

	@Test
	public void testPreVisitDirectoryPath() throws IOException {
		
		fileMoveVisitor.preVisitDirectory(folder1Path, null);
		
		File targetFolder = targetDirPath.resolve(FOLDER_1_NAME).toFile();
		
		assertTrue("Folder does not exist in the target location", targetFolder.exists());
		assertTrue("Generated object is not a directory", targetFolder.isDirectory());
	}

	@Test
	public void testVisitFilePath() throws IOException {
		fileMoveVisitor.preVisitDirectory(folder2Path, null);
		fileMoveVisitor.visitFile(folder2File1Path, null);
		
		File targetFile = targetDirPath.resolve(FOLDER_2_NAME).resolve(FOLDER_2_FILE_1_NAME).toFile();
		
		assertTrue("File has not been copied", targetFile.exists());
		assertFalse("Moved file is a directory", targetFile.isDirectory());
		assertFalse("Original file has not been removed", folder2File1Path.toFile().exists());
	}

	@Test
	public void testPostVisitDirectoryPath() throws IOException {
		fileMoveVisitor.postVisitDirectory(folder3Path, null);
		
		assertFalse("Folder has not removed from the original location", 
				targetDirPath.resolve(FOLDER_3_NAME).toFile().exists());
		
		IOException ioException = new IOException("This is a manually generated error for testing. Ignore This!!!");
		fileMoveVisitor.postVisitDirectory(folder4Path, ioException);
		
		assertFalse("Folder has not removed from the original location", 
				targetDirPath.resolve(FOLDER_4_NAME).toFile().exists());
	}

}
