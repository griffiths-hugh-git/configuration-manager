 package com.griffiths.hugh.configuration_manager.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.griffiths.hugh.configuration_manager.data.Environment;
import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;

public class EnvironmentReader {
	private static final String ENVIRONMENT_ID_KEY = "environment_id";

	public Environment readEnvironmentProperties(File environmentFile) throws IOException, EnvironmentConfigurationException{
		// Load the properties
		InputStream is=null;
		Properties props = new Properties();
		try {
			is=new FileInputStream(environmentFile);
		props.load(is);
		}
		finally {
			if (is!=null){
				is.close();
			}
		}
		
		// Read environment ID
		String environmentID=props.getProperty(ENVIRONMENT_ID_KEY);
		if (environmentID==null){
			throw new EnvironmentConfigurationException();
		}
		
		// Read properties in
		Environment env = new Environment(environmentID);
		for (Object key: props.keySet()){
			if (!ENVIRONMENT_ID_KEY.equals(key)){
				env.setPropertyValue((String)key, props.getProperty((String)key));
			}
		}
		
		return env;
	}
}
