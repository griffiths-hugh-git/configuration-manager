package com.griffiths.hugh.configuration_manager.substitution;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

import com.griffiths.hugh.configuration_manager.ConfigurationBuilder;
import com.griffiths.hugh.configuration_manager.data.Configuration;
import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.InvalidConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;

public class ArchivePackagerTest {
	@Test
	public void test() throws IOException, EnvironmentConfigurationException, MetadataConfigurationException, InvalidConfigurationException {
		// Load archive
		File originalJar = new File("target/test-classes/configuration-test.jar");
		ZipArchive original = new ZipArchive(originalJar);

		// Load configuration
		ConfigurationBuilder builder= new ConfigurationBuilder();
		Configuration cfg = builder.buildConfiguration("target/test-classes/config_metadata.csv" 
				,"target/test-classes/dev.properties"
				,"target/test-classes/preprod.properties"
				,"target/test-classes/live.properties");
		
		// Package
		(new ArchivePropertySubstituter()).packageArchive(original, cfg);
		original.close();
		
		// Test dev archive results
		File devJar=new File("target/test-classes/dev_0af479b8d4efa18c303d5a6087803ea2_configuration-test.jar");
		assertTrue(devJar.exists());
		ZipArchive devArchive = new ZipArchive(devJar);
		assertTrue(devArchive.containsFile("db.properties"));
		String devDBProps = devArchive.getFileContentsAsString("db.properties");
		assertTrue(devDBProps.contains("config.test.db.user=test_user"));
		
		devArchive.close();
	}
}
