package com.griffiths.hugh.configuration_manager.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class Configuration {
	private final Logger LOG=Logger.getLogger(Configuration.class);
	private final ConfigurationMetadata metadata ;
	private final List<Environment> environments = new ArrayList<Environment>();

	public Configuration(ConfigurationMetadata metadata) {
		super();
		this.metadata = metadata;
	}

	public ConfigurationMetadata getMetadata() {
		return metadata;
	}

	public List<Environment> getEnvironments() {
		return environments;
	}
	
	public void addEnvironment(Environment env){
		environments.add(env);
	}
	
	public boolean validate(){
		boolean valid=true;
		for (Environment env : environments){
			LOG.info("Validating environment : "+env.getId());
			
			Set<String> environmentProperties = new HashSet<String>(env.getProperties());
			for (ConfigurationFile file : metadata){
				for (ConfigurationProperty property : file.getProperties()){
					if (env.getValue(property.getKey())==null){
						LOG.warn(String.format("Value '%s' not set for environment '%s'.", property.getKey(), env.getId()));
						valid=false;
					}
					else {
						environmentProperties.remove(property.getKey());
					}
				}
			}
			
			for (String remainingProperty: environmentProperties){
				LOG.warn(String.format("Unrecognised property '%s' was specified for environment '%s'.", remainingProperty, env.getId()));
				valid=false;
			}
		}
		
		return valid;
	}
}
