package com.griffiths.hugh.configuration_manager.substitution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.griffiths.hugh.configuration_manager.data.Configuration;
import com.griffiths.hugh.configuration_manager.data.ConfigurationFile;
import com.griffiths.hugh.configuration_manager.data.ConfigurationMetadata;
import com.griffiths.hugh.configuration_manager.data.ConfigurationProperty;
import com.griffiths.hugh.configuration_manager.data.EnvironmentConfiguration;

/**
 * Class responsible for applying property substitutions and creating the new
 * archive.
 * 
 * For each environment, a new archive is created. It's name is that of the
 * original archive, prefixed by the environment ID. The new archives are
 * created in the same folder as the original.
 * 
 * @author hugh
 *
 */
public class ArchivePropertySubstituter {
	private final Logger LOG = Logger.getLogger(ArchivePropertySubstituter.class);

	public void packageArchive(ZipArchive original, Configuration config) throws IOException {
		for (EnvironmentConfiguration env : config.getEnvironments()) {
			// Construct target filename
			File newPath = constructNewFilename(original, env.getId());

			// Open files
			FileOutputStream fos = new FileOutputStream(newPath);
			ZipOutputStream zos = new ZipOutputStream(fos);

			// Transform and transcribe
			LOG.info(String.format("Configuring environment '%s', archive will be written to '%s'.", env.getId(), newPath.getAbsolutePath()));
			transformArchive(original, zos, config.getMetadata(), env);

			// Close resources
			zos.flush();
			zos.close();
		}
	}

	/**
	 * Constructs the new filename. The pattern is "<environmentID>_oldFilename"
	 * , in the same folder as the original.
	 * 
	 * @param original
	 * @param environmentId
	 * @return
	 */
	private File constructNewFilename(ZipArchive original, String environmentId) {
		File originalPath = original.getArchiveFile().getParentFile();
		String originalName = original.getArchiveFile().getName();
		String newName = environmentId + "_" + originalName;
		File newPath = new File(originalPath, newName);
		return newPath;
	}

	private void transformArchive(ZipArchive original, ZipOutputStream output, ConfigurationMetadata metadata,
			EnvironmentConfiguration env) throws IOException {
		// For every file in the original archive
		for (String name : original.getPathsIndex()) {
			// Create a transcribed copy
			ZipEntry transcribed = new ZipEntry(name);
			output.putNextEntry(transcribed);

			// If the file is one of those containing configuration, make the
			// changes
			if (metadata.getFilenames().contains(name)) {
				String contents = original.getFileContentsAsString(name);
				byte[] transformedEntry = transformEntry(contents, metadata.getFile(name), env).getBytes();
				output.write(transformedEntry);
			}
			// Otherwise, copy it across unaltered.
			else {
				output.write(IOUtils.toByteArray(original.getFileContents(name)));
			}
		}
	}

	private String transformEntry(String original, ConfigurationFile config, EnvironmentConfiguration env) {
		LOG.debug(String.format("Applying configuration changes to '%s'.", config.getPath()));
		String inProgress = original;
		// For each property
		for (ConfigurationProperty property : config.getProperties()) {
			int occurences = StringUtils.countMatches(inProgress, property.getKey());
			if (occurences == 0) {
				LOG.warn(String.format("No occurences of propery '%s' found in path '%s'.  This may indicate the path has been configured incorrectly.", property.getKey(),
						config.getPath()));
			}
			else {
				LOG.debug(String.format("Replaced %d occurences of key '%s' in path '%s'", occurences, property.getKey(), config.getPath()));
			}

			// Replace all occurences of that property
			inProgress = inProgress.replace(property.getKey(), env.getValue(property.getKey()));
		}

		return inProgress;
	}
}
