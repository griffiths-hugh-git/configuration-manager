package com.griffiths.hugh.configuration_manager;

import java.io.File;
import java.io.IOException;

import com.griffiths.hugh.configuration_manager.data.Configuration;
import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.InvalidConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;
import com.griffiths.hugh.configuration_manager.substitution.ArchivePackager;
import com.griffiths.hugh.configuration_manager.substitution.ZipArchive;

public class ConfigurationController {
	public void configure(String archive, String config, String... environments) throws IOException, EnvironmentConfigurationException, MetadataConfigurationException,
			InvalidConfigurationException {
		// Load archive
		File originalJar = new File(archive);
		ZipArchive original = new ZipArchive(originalJar);

		// Load configuration
		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration cfg = builder.buildConfiguration(config,
				environments);

		// Package
		(new ArchivePackager()).packageArchive(original, cfg);
		original.close();
	}
}
