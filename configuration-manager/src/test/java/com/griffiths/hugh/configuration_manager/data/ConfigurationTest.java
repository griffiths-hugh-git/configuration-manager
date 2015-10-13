package com.griffiths.hugh.configuration_manager.data;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.griffiths.hugh.configuration_manager.data.Configuration;
import com.griffiths.hugh.configuration_manager.data.ConfigurationMetadata;
import com.griffiths.hugh.configuration_manager.data.Environment;
import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;
import com.griffiths.hugh.configuration_manager.reader.EnvironmentReader;
import com.griffiths.hugh.configuration_manager.reader.MetadataReader;

public class ConfigurationTest {

	@Test
	public void test() throws IOException, EnvironmentConfigurationException, MetadataConfigurationException {
		ConfigurationMetadata metadata = buildMetadata("src/test/resources/config_metadata.csv");
		Configuration cfg = new Configuration(metadata);

		Environment env = buildEnvironment();
		cfg.addEnvironment(env);

		assertEquals(true, cfg.validate());
	}

	@Test
	public void testExtraValue() throws IOException, EnvironmentConfigurationException, MetadataConfigurationException {
		ConfigurationMetadata metadata = buildMetadata("src/test/resources/config_metadata_extra_value.csv");
		Configuration cfg = new Configuration(metadata);

		Environment env = buildEnvironment();
		cfg.addEnvironment(env);

		assertEquals(false, cfg.validate());
	}

	@Test
	public void testMissingValue() throws IOException, EnvironmentConfigurationException, MetadataConfigurationException {
		ConfigurationMetadata metadata = buildMetadata("src/test/resources/config_metadata_missing_value.csv");
		Configuration cfg = new Configuration(metadata);

		Environment env = buildEnvironment();
		cfg.addEnvironment(env);

		assertEquals(false, cfg.validate());
	}
	
	private Environment buildEnvironment() throws IOException, EnvironmentConfigurationException {
		File dev = new File("src/test/resources/dev.properties");
		Environment env = new EnvironmentReader().readEnvironmentProperties(dev);
		return env;
	}

	private ConfigurationMetadata buildMetadata(String file) throws MetadataConfigurationException, IOException {
		File metadataFile = new File(file);
		ConfigurationMetadata metadata = (new MetadataReader()).readMetadata(metadataFile);
		return metadata;
	}

}
