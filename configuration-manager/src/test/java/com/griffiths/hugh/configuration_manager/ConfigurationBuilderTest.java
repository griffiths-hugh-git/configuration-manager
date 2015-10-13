package com.griffiths.hugh.configuration_manager;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

import com.griffiths.hugh.configuration_manager.data.Configuration;
import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.InvalidConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;

public class ConfigurationBuilderTest 
{
	@Test
	public void test() throws IOException, EnvironmentConfigurationException, MetadataConfigurationException, InvalidConfigurationException{
		ConfigurationBuilder builder= new ConfigurationBuilder();
		Configuration cfg = builder.buildConfiguration("src/test/resources/config_metadata.csv" 
				,"src/test/resources/dev.properties"
				,"src/test/resources/preprod.properties"
				,"src/test/resources/live.properties");
		
		assertEquals(3, cfg.getEnvironments().size());
	}
}
