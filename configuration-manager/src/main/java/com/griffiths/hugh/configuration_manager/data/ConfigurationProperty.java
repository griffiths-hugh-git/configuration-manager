package com.griffiths.hugh.configuration_manager.data;

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
