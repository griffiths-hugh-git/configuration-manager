package com.griffiths.hugh.configuration_manager.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.griffiths.hugh.configuration_manager.data.ConfigurationFile;
import com.griffiths.hugh.configuration_manager.data.ConfigurationMetadata;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;
import com.griffiths.hugh.configuration_manager.reader.MetadataReader;

public class MetadataReaderTest {

	@Test
	public void test() throws MetadataConfigurationException, IOException {
		File metadataFile=new File("src/test/resources/config_metadata.csv");
		ConfigurationMetadata metadata = (new MetadataReader()).readMetadata(metadataFile);
		
		ConfigurationFile file = metadata.getFile("db.properties");
		assertNotNull(file);
		assertEquals(2, file.getProperties().size());
		assertEquals("Database username", file.getProperty("%DB_USER%").getDescription());
	}

}
