package com.akalanka.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests utility methods.
 * 
 * @author akalanka
 *
 */
public class UtilsTest extends BaseTest {
	
    @Rule
    public ExpectedException thrown = ExpectedException.none();
	
	private static final String FILE_U1_NAME = "FileU1.txt";
	private static final String FILE_U2_NAME = "FileU2.txt";
	
	private static final String FOLDER_U1_NAME = "FolderU1";
	private static final String FOLDER_U1_FILE_1_NAME = "folderU1File1.txt";
	
	private static final String FOLDER_U2_NAME = "FolderU2";
	private static final String FOLDER_U2_FILE_1_NAME = "folderU2File1.txt";
	private static final String FOLDER_U2_FILE_2_NAME = "folderU2File2.txt";
	
	private static final String FOLDER_U2_SUB1_NAME = "FolderU2";
	private static final String FOLDER_U2_SUB1_FILE_1_NAME = "folderU2Sub1File1.txt";
	private static final String FOLDER_U2_SUB1_FILE_2_NAME = "folderU2Sub1File2.txt";
	
	private static final String FOLDER_U3_NAME = "FolderU3";
	
	private static Path fileU1Path;
	private static Path fileU2Path;
	
	private static Path folderU1Path;
	private static Path folderU1File1Path;
	
	private static Path folderU2Path;
	private static Path folderU2File1Path;
	private static Path folderU2File2Path;
	
	private static Path folderU2Sub1Path;
	private static Path folderU2Sub1File1Path;
	private static Path folderU2Sub1File2Path;
	
	private static Path folderU3Path;
	
	/**
	 * Set up a folder with files for testing.
	 * 
	 * @throws FileMoveException
	 * @throws IOException
	 */
	@BeforeClass
	public static void setUp() throws FileMoveException, IOException {
		
		folderU1Path = sourceDirPath.resolve(FOLDER_U1_NAME);
		folderU1Path.toFile().mkdirs();
		
		folderU2Path = sourceDirPath.resolve(FOLDER_U2_NAME);
		folderU2Path.toFile().mkdirs();
		
		folderU2Sub1Path = folderU2Path.resolve(FOLDER_U2_SUB1_NAME);
		folderU2Sub1Path.toFile().mkdirs();
		
		folderU3Path = sourceDirPath.resolve(FOLDER_U3_NAME);
		folderU3Path.toFile().mkdirs();
		
		fileU1Path = sourceDirPath.resolve(FILE_U1_NAME);
		fileU1Path.toFile().createNewFile();
		
		fileU2Path = sourceDirPath.resolve(FILE_U2_NAME);
		fileU2Path.toFile().createNewFile();
		
		folderU1File1Path = folderU1Path.resolve(FOLDER_U1_FILE_1_NAME);
		folderU1File1Path.toFile().createNewFile();
		
		folderU2File1Path = folderU2Path.resolve(FOLDER_U2_FILE_1_NAME);
		folderU2File1Path.toFile().createNewFile();
		
		folderU2File2Path = folderU2Path.resolve(FOLDER_U2_FILE_2_NAME);
		folderU2File2Path.toFile().createNewFile();
		
		folderU2Sub1File1Path = folderU2Sub1Path.resolve(FOLDER_U2_SUB1_FILE_1_NAME);
		folderU2Sub1File1Path.toFile().createNewFile();
		
		folderU2Sub1File2Path = folderU2Sub1Path.resolve(FOLDER_U2_SUB1_FILE_2_NAME);
		folderU2Sub1File2Path.toFile().createNewFile();
	}

	/**
	 * Tests file move operation.
	 * 
	 * @throws FileMoveException
	 */
	@Test
	public void testMoveFiles() throws FileMoveException {
		Utils.moveFiles(sourceDirPath.toString(), targetDirPath.toString());
		
		File sourceFolder = sourceDirPath.toFile();
		
		assertTrue("Source folder has been removed", sourceFolder.exists());
		assertTrue("Source folder is not a directory anymore", sourceFolder.isDirectory());
		
		assertEquals("Expected zero files in the source directory", 0, sourceFolder.list().length);
		
		Path targetU1Folder = targetDirPath.resolve(FOLDER_U1_NAME);
		Path targetU2Folder = targetDirPath.resolve(FOLDER_U2_NAME);
		Path targetU2Sub1Folder = targetU2Folder.resolve(FOLDER_U2_NAME);
		Path targetU3Folder = targetDirPath.resolve(FOLDER_U3_NAME);
		
		Path targetFileU1 = targetDirPath.resolve(FILE_U1_NAME);
		Path targetFileU2 = targetDirPath.resolve(FILE_U2_NAME);
		Path targetFileU1File1 = targetU1Folder.resolve(FOLDER_U1_FILE_1_NAME);
		Path targetFileU2File1 = targetU2Folder.resolve(FOLDER_U2_FILE_1_NAME);
		Path targetFileU2File2 = targetU2Folder.resolve(FOLDER_U2_FILE_2_NAME);
		Path targetFileU2Sub1File1 = targetU2Sub1Folder.resolve(FOLDER_U2_SUB1_FILE_1_NAME);
		Path targetFileU2Sub1File2 = targetU2Sub1Folder.resolve(FOLDER_U2_SUB1_FILE_1_NAME);
		
		assertTrue("Folder " + targetU1Folder + " has not been copied", targetU1Folder.toFile().exists());
		assertTrue("Folder " + targetU2Folder + " has not been copied", targetU2Folder.toFile().exists());
		assertTrue("Folder " + targetU2Sub1Folder + " has not been copied", targetU2Sub1Folder.toFile().exists());
		assertTrue("Folder " + targetU3Folder + " has not been copied", targetU3Folder.toFile().exists());
		
		assertTrue("File " + targetFileU1 + " has not been copied", targetFileU1.toFile().exists());
		assertTrue("File " + targetFileU2 + " has not been copied", targetFileU2.toFile().exists());
		assertTrue("File " + targetFileU1File1 + " has not been copied", targetFileU1File1.toFile().exists());
		assertTrue("File " + targetFileU2File1 + " has not been copied", targetFileU2File1.toFile().exists());
		assertTrue("File " + targetFileU2File2 + " has not been copied", targetFileU2File2.toFile().exists());
		assertTrue("File " + targetFileU2Sub1File1 + " has not been copied", targetFileU2Sub1File1.toFile().exists());
		assertTrue("File " + targetFileU2Sub1File2 + " has not been copied", targetFileU2Sub1File2.toFile().exists());
	}
	
	@Test
	public void testMoveFilesNonExisting() throws FileMoveException {
		
		thrown.expect(FileMoveException.class);
        thrown.expectMessage("Source path does not exist or is not a directory");
        
        Utils.moveFiles("nonExistingSource", "target");
	}
	
	@Test
	public void testMoveFilesEmptySource() throws FileMoveException {
		
		thrown.expect(FileMoveException.class);
        thrown.expectMessage("Source and target directories cannot be empty.");
        
        Utils.moveFiles(null, "target");
	}

}
