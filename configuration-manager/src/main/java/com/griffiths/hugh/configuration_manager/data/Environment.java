package com.griffiths.hugh.configuration_manager.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Environment {
	private final String id;
	private final Map<String, String> values = new HashMap<String, String>();
	public Environment(String id) {
		super();
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public String getValue(String property) {
		return values.get(property);
	}
	public Set<String> getProperties(){
		return values.keySet();
	}
	public void setPropertyValue(String property, String value){
		values.put(property, value);
	}
}
