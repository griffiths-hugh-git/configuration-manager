package com.griffiths.hugh.configuration_manager.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ConfigurationMetadata implements Iterable<ConfigurationFile> {
	private final Map<String, ConfigurationFile> files = new HashMap<String, ConfigurationFile>();

	public ConfigurationFile getFile(String path) {
		return files.get(path);
	}

	public Set<String> getFilenames(){
		return files.keySet();
	}
	
	public void setProperty(String path, ConfigurationProperty property) {
		if (!files.containsKey(path)) {
			files.put(path, new ConfigurationFile(path));
		}
		files.get(path).addProperty(property);
	}

	public Iterator<ConfigurationFile> iterator() {
		return files.values().iterator();
	}
}
