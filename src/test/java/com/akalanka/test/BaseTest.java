package com.akalanka.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Base test class.
 * 
 * @author akalanka
 *
 */
public abstract class BaseTest {
	
	protected static final String TEST_RESOURCES = "testResources";
	protected static final String SOURCE_DIR = "sourceDir";
	protected static final String TARGET_DIR = "targetDir";
	
	protected static Path testResourcesPath;
	protected static Path sourceDirPath;
	protected static Path targetDirPath;
	
	/**
	 * Initialize basic folders required for tests.
	 * 
	 * @throws FileMoveException
	 */
	@BeforeClass
	public static void initFolders() throws FileMoveException {
		testResourcesPath = Paths.get(TEST_RESOURCES);
		sourceDirPath = testResourcesPath.resolve(SOURCE_DIR);
		targetDirPath = testResourcesPath.resolve(TARGET_DIR);
		
		sourceDirPath.toFile().mkdirs();
		targetDirPath.toFile().mkdirs();
	}
	
	/**
	 * Destroy created files and folders.
	 */
	@AfterClass
	public static void destroyFolders() {
		deleteDirectory(testResourcesPath.toFile());
	}
	
	/**
	 * Delete a directory with all it's children.
	 * 
	 * @param dir Directory to delete.
	 * @return <code>true</code> if and only if the file or directory is
     *          successfully deleted; <code>false</code> otherwise
	 */
	public static boolean deleteDirectory(File dir) {

		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(children[i]);
				if (!success) {
					return false;
				}
			}
		}

		return dir.delete();
	}

}
