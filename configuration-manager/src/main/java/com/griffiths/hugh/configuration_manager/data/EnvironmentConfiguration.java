package com.griffiths.hugh.configuration_manager.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents the set of property values to use for a particular environment.
 * 
 * @author hugh
 *
 */
public class EnvironmentConfiguration {
	private final String id;
	private final Map<String, String> propertyValuesByKey = new HashMap<String, String>();
	public EnvironmentConfiguration(String id) {
		super();
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public String getValue(String property) {
		return propertyValuesByKey.get(property);
	}
	public Set<String> getPropertyKeys(){
		return propertyValuesByKey.keySet();
	}
	public void setPropertyValue(String property, String value){
		propertyValuesByKey.put(property, value);
	}
}
