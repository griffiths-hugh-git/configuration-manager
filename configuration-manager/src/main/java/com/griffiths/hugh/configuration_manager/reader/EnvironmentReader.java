package com.griffiths.hugh.configuration_manager.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.griffiths.hugh.configuration_manager.data.EnvironmentConfiguration;
import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;

/**
 * Class responsible for loading environment files.
 * 
 * The environemnt files much each be a properties file, with a reserved key
 * "environment_id", which specifies the name of the environment. This key will
 * be prefixed to the name of the modified archive with the environment's values
 * in, so it should be compatible with file system naming requirements.
 * 
 * @author hugh
 *
 */
public class EnvironmentReader {
	private static final String ENVIRONMENT_ID_KEY = "environment_id";

	/**
	 * Read the environment file.
	 * 
	 * @param environmentFile
	 * @return
	 * @throws IOException File cannot be read, or is not a valid properties file.
	 * @throws EnvironmentConfigurationException File does not contain an environment ID.
	 */
	public EnvironmentConfiguration readEnvironmentProperties(File environmentFile)
			throws IOException, EnvironmentConfigurationException {
		// Load the environment properties
		Properties props = loadEnvironmentProperties(environmentFile);

		// Read environment ID
		String environmentID = props.getProperty(ENVIRONMENT_ID_KEY);
		if (environmentID == null) {
			throw new EnvironmentConfigurationException();
		}

		// Read properties in
		EnvironmentConfiguration env = new EnvironmentConfiguration(environmentID);
		for (Object key : props.keySet()) {
			if (!ENVIRONMENT_ID_KEY.equals(key)) {
				env.setPropertyValue((String) key, props.getProperty((String) key));
			}
		}

		return env;
	}

	private Properties loadEnvironmentProperties(File environmentFile) throws FileNotFoundException, IOException {
		InputStream is = null;
		Properties props = new Properties();
		try {
			is = new FileInputStream(environmentFile);
			props.load(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return props;
	}
}
