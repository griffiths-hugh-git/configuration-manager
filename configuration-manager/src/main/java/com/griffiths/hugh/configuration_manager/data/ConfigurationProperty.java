package com.griffiths.hugh.configuration_manager.data;

/**
 * Represents a single property appearing in a single file. The property
 * consists of a key to be subsituted in this file. The key may appear several
 * times within its file, all occurences will be substituted.
 * If the same key occurs is several files, a new property should be created for each file.
 * 
 * @author hugh
 *
 */
public class ConfigurationProperty {
	private final String key;
	private final String description;

	public ConfigurationProperty(String key, String description) {
		super();
		this.key = key;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}
}
