package com.griffiths.hugh.configuration_manager;

import java.io.IOException;
import java.util.Arrays;

import com.griffiths.hugh.configuration_manager.exception.EnvironmentConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.InvalidConfigurationException;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;

public class Main {
public static void main(String[] args) throws IOException, EnvironmentConfigurationException, MetadataConfigurationException, InvalidConfigurationException {
	if (args.length<3){
		System.out.println("Usage : java -jar configuration-manager.jar [archive] [configuration metadata CSV] [environment1]...");
		System.exit(1);
	}
	
	(new ConfigurationController()).configure(args[0], args[1], Arrays.copyOfRange(args, 2, args.length));
}
}
