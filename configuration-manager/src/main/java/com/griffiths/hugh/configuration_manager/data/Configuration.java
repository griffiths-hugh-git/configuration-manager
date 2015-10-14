package com.griffiths.hugh.configuration_manager.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Represents the full set of configuration files.
 * 
 * @author hugh
 *
 */
public class Configuration {
	private final Logger LOG = Logger.getLogger(Configuration.class);
	private final ConfigurationMetadata metadata;
	private final List<EnvironmentConfiguration> environments = new ArrayList<EnvironmentConfiguration>();

	public Configuration(ConfigurationMetadata metadata) {
		super();
		this.metadata = metadata;
	}

	public ConfigurationMetadata getMetadata() {
		return metadata;
	}

	public List<EnvironmentConfiguration> getEnvironments() {
		return environments;
	}

	public void addEnvironment(EnvironmentConfiguration env) {
		environments.add(env);
	}

	/**
	 * Validate the environment settings against the metadata. To be valid, each
	 * environment must contain a definition for each property defined in the
	 * metadata file. All errors are logged as WARN messages. The validation
	 * will scan the full set and report all problems, rather than failing fast.
	 * 
	 * @return Whether the environment definitions are valid.
	 */
	public boolean validate() {
		boolean valid = true;
		for (EnvironmentConfiguration env : environments) {
			LOG.info("Validating environment : " + env.getId());

			Set<String> environmentProperties = new HashSet<String>(env.getPropertyKeys());
			for (ConfigurationFile file : metadata) {
				for (ConfigurationProperty property : file.getProperties()) {
					if (env.getValue(property.getKey()) == null) {
						LOG.warn(String.format("Value '%s' not set for environment '%s'.", property.getKey(),
								env.getId()));
						valid = false;
					} else {
						environmentProperties.remove(property.getKey());
					}
				}
			}

			for (String remainingProperty : environmentProperties) {
				LOG.warn(String.format("Unrecognised property '%s' was specified for environment '%s'.",
						remainingProperty, env.getId()));
				valid = false;
			}
		}

		return valid;
	}
}
