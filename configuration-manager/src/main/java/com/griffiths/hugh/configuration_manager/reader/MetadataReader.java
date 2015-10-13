package com.griffiths.hugh.configuration_manager.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.griffiths.hugh.configuration_manager.data.ConfigurationMetadata;
import com.griffiths.hugh.configuration_manager.data.ConfigurationProperty;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;

import au.com.bytecode.opencsv.CSVReader;

public class MetadataReader {
	public ConfigurationMetadata readMetadata(File metadataFile) throws MetadataConfigurationException, IOException{
		// Read file
		FileReader fr=null;
		CSVReader reader =null;
		List<String[]> rows=null;
		try {
			fr = new FileReader(metadataFile);
			reader = new CSVReader(fr);
			rows = reader.readAll();
		}
		finally {
			if (reader!=null){
				reader.close();
			}
			if (fr!=null){
				fr.close();
			}
		}
		
		// Parse content
		ConfigurationMetadata conf = new ConfigurationMetadata();
		for (String[] row : rows){
			// Validate row
			if (row.length!=3){
				throw new MetadataConfigurationException();
			}
			
			// Construct Property
			String path=row[2];
			ConfigurationProperty property = new ConfigurationProperty(row[0], row[1]);
			conf.setProperty(path, property);
		}
		
		return conf;
	}
}
