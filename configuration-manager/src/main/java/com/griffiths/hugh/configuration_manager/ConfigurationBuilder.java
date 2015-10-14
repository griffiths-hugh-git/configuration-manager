package com.griffiths.hugh.configuration_manager;

import java.io.File;
import java.io.IOException;

import com.griffiths.hugh.configuration_manager.data.Configuration;
import com.griffiths.hugh.configuration_manager.data.ConfigurationMetadata;
import com.griffiths.hugh.configuration_manager.data.EnvironmentConfiguration;
import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.InvalidConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;
import com.griffiths.hugh.configuration_manager.reader.EnvironmentReader;
import com.griffiths.hugh.configuration_manager.reader.MetadataReader;

/**
 * Class to load and parse the configuration files.
 * 
 * Expects one CSV file containing property definitions, and 0 or more
 * environment files, which describe values of those variables in a particular
 * environment.
 * 
 * @author hugh
 *
 */
public class ConfigurationBuilder {
	/**
	 * @param metadataFile
	 *            Location of the metadata (CSV) file.
	 * @param environmentFiles
	 *            List of environment file locations. Each should be the
	 *            location of an environment properties file.
	 * @return
	 * @throws IOException
	 *             One of the files cannot be read.
	 * @throws MetadataConfigurationException
	 *             The metadata file is malformed.
	 * @throws EnvironmentConfigurationException
	 *             One of the environment files is malformed.
	 * @throws InvalidConfigurationException
	 *             One of more of the environment files does not define all of
	 *             the variables, or defines a variable not present in the
	 *             metadata file.
	 */
	public Configuration buildConfiguration(String metadataFile, String... environmentFiles) throws IOException,
			EnvironmentConfigurationException, MetadataConfigurationException, InvalidConfigurationException {
		// Load metadata file
		ConfigurationMetadata metadata = buildMetadata(metadataFile);
		Configuration cfg = new Configuration(metadata);

		// Configure environment files
		for (String environment : environmentFiles) {
			EnvironmentConfiguration env = buildEnvironment(environment);
			cfg.addEnvironment(env);
		}

		// Validate the environment files against the metadata file
		if (!cfg.validate()) {
			throw new InvalidConfigurationException();
		}

		return cfg;
	}

	private EnvironmentConfiguration buildEnvironment(String environment) throws IOException, EnvironmentConfigurationException {
		File dev = new File(environment);
		EnvironmentConfiguration env = new EnvironmentReader().readEnvironmentProperties(dev);
		return env;
	}

	private ConfigurationMetadata buildMetadata(String file) throws MetadataConfigurationException, IOException {
		File metadataFile = new File(file);
		ConfigurationMetadata metadata = (new MetadataReader()).readMetadata(metadataFile);
		return metadata;
	}
}
