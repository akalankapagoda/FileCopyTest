package com.akalanka.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MainTest extends BaseTest {
	
	private String[] args = {SOURCE_DIR, TARGET_DIR};
	
	@Test
	public void testMain() {
		Main.main(args);
		
		assertTrue(true);
	}

}
