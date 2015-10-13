package com.griffiths.hugh.configuration_manager;

import java.io.File;
import java.io.IOException;

import com.griffiths.hugh.configuration_manager.data.Configuration;
import com.griffiths.hugh.configuration_manager.data.ConfigurationMetadata;
import com.griffiths.hugh.configuration_manager.data.Environment;
import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.InvalidConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;
import com.griffiths.hugh.configuration_manager.reader.EnvironmentReader;
import com.griffiths.hugh.configuration_manager.reader.MetadataReader;

public class ConfigurationBuilder {
	public Configuration buildConfiguration(String metadataFile, String... environmentFiles) throws IOException, EnvironmentConfigurationException, MetadataConfigurationException, InvalidConfigurationException{
		ConfigurationMetadata metadata = buildMetadata(metadataFile);
		Configuration cfg = new Configuration(metadata);
		
		for (String environment : environmentFiles){
			Environment env = buildEnvironment(environment);
			cfg.addEnvironment(env);
		}
		
		if (!cfg.validate()){
			throw new InvalidConfigurationException();
		}
		
		return cfg;
	}
	
	private Environment buildEnvironment(String environment) throws IOException, EnvironmentConfigurationException {
		File dev = new File(environment);
		Environment env = new EnvironmentReader().readEnvironmentProperties(dev);
		return env;
	}

	private ConfigurationMetadata buildMetadata(String file) throws MetadataConfigurationException, IOException {
		File metadataFile = new File(file);
		ConfigurationMetadata metadata = (new MetadataReader()).readMetadata(metadataFile);
		return metadata;
	}
}
