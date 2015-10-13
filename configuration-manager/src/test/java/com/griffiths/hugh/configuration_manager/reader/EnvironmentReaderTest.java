package com.griffiths.hugh.configuration_manager.reader;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.griffiths.hugh.configuration_manager.data.Environment;
import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;
import com.griffiths.hugh.configuration_manager.reader.EnvironmentReader;

import static org.junit.Assert.*;

public class EnvironmentReaderTest {

	@Test
	public void test() throws IOException, EnvironmentConfigurationException {
		File dev=new File("src/test/resources/dev.properties");
		Environment env = new EnvironmentReader().readEnvironmentProperties(dev);
		
		assertEquals("dev", env.getId());
		assertEquals("test_user", env.getValue("%DB_USER%"));
		assertEquals(3, env.getProperties().size());
	}

}
