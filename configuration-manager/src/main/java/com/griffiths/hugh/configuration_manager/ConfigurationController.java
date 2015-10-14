package com.griffiths.hugh.configuration_manager;

import java.io.File;
import java.io.IOException;

import com.griffiths.hugh.configuration_manager.data.Configuration;
import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.InvalidConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;
import com.griffiths.hugh.configuration_manager.substitution.ArchivePropertySubstituter;
import com.griffiths.hugh.configuration_manager.substitution.ZipArchive;

/**
 * Controller class which orchestrates the sequence of applying configuration
 * values to an archive. The archive may be a JAR, WAR, EAR or any other ZIP
 * archive.
 * 
 * @author hugh
 *
 */
public class ConfigurationController {
	/**
	 * Parsing the configuration and applies to the specified archive.
	 * 
	 * @param archiveFile
	 *            Location of the archive file.
	 * @param metadataFile
	 *            Location of the metadata (CSV) file.
	 * @param environmentFiles
	 *            Locations of the environment (properties) files.
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
	public void applyConfiguration(String archiveFile, String metadataFile, String... environmentFiles)
			throws IOException, EnvironmentConfigurationException, MetadataConfigurationException,
			InvalidConfigurationException {
		// Load archive
		File originalJar = new File(archiveFile);
		ZipArchive original = new ZipArchive(originalJar);

		// Load configuration
		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration cfg = builder.buildConfiguration(metadataFile, environmentFiles);

		// Package
		(new ArchivePropertySubstituter()).packageArchive(original, cfg);
		original.close();
	}
}
