package com.griffiths.hugh.configuration_manager.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Represents the full set of properties defined in the metadata file.
 * Properties are broken down by file to allow efficient substitution.
 * 
 * @author hugh
 *
 */
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
