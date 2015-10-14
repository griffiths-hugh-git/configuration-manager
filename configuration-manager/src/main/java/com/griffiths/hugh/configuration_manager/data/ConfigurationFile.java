package com.griffiths.hugh.configuration_manager.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a subset of the configuration properties defined in the metadata
 * which occur in a single file within the archive.
 * 
 * @author hugh
 *
 */
public class ConfigurationFile {
	private final Map<String, ConfigurationProperty> properties = new HashMap<String, ConfigurationProperty>();
	private final String path;

	public ConfigurationFile(String path) {
		super();
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public Collection<ConfigurationProperty> getProperties() {
		return properties.values();
	}

	public ConfigurationProperty getProperty(String key) {
		return properties.get(key);
	}

	public void addProperty(ConfigurationProperty property) {
		properties.put(property.getKey(), property);
	}
}
