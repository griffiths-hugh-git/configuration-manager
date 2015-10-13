package com.griffiths.hugh.configuration_manager.substitution;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZipArchiveReadTest {
	private File jar = new File("src/test/resources/configuration-test.jar");
	private ZipArchive archive;
	
	@Before
	public void setup() throws IOException {
		archive = new ZipArchive(jar);
	}
	
	@Test
	public void testFiles() throws ZipException, IOException {
		assertTrue(archive.containsFile("db.properties"));
		assertFalse(archive.containsFile("db_fake.properties"));
	}
	
	@Test
	public void testContents() throws ZipException, IOException {
		String dbProperties=archive.getFileContentsAsString("db.properties");
		assertTrue(dbProperties.contains("%DB_USER%"));
		assertFalse(dbProperties.contains("%TEST_USER%"));
	}

	@After
	public void tearDown() throws IOException{
		archive.close();
	}
}
