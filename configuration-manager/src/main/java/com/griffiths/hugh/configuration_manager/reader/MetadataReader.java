package com.griffiths.hugh.configuration_manager.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.griffiths.hugh.configuration_manager.data.ConfigurationMetadata;
import com.griffiths.hugh.configuration_manager.data.ConfigurationProperty;
import com.griffiths.hugh.configuration_manager.exception.MetadataConfigurationException;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Class responsible for reading the metadata file.
 * 
 * The metadata file must be a CSV file, with each row representing a property
 * to be substituted. Each row must contain exactly 3 entries: -The property key
 * (the value to be substituted) -A description of the property (for
 * documentation purposes) -The path of the file in which the property is found.
 * 
 * Values can be surrounded by quotes if required, for instance if the
 * description contains commas. For full details, see the OpenCSV documentation.
 * 
 * @author hugh
 *
 */
public class MetadataReader {
	/**
	 * @param metadataFile Location of the metadata file
	 * @return Metadata object.
	 * @throws MetadataConfigurationException If the metadata file is malformed.
	 * @throws IOException If the file cannot be read.
	 */
	public ConfigurationMetadata readMetadata(File metadataFile) throws MetadataConfigurationException, IOException {
		// Read file
		List<String[]> rows = readCSVFile(metadataFile);

		// Parse content
		ConfigurationMetadata conf = new ConfigurationMetadata();
		for (String[] row : rows) {
			// Validate row
			if (row.length != 3) {
				throw new MetadataConfigurationException();
			}

			// Construct Property object
			String path = row[2];
			ConfigurationProperty property = new ConfigurationProperty(row[0], row[1]);
			conf.setProperty(path, property);
		}

		return conf;
	}

	private List<String[]> readCSVFile(File metadataFile) throws FileNotFoundException, IOException {
		FileReader fr = null;
		CSVReader reader = null;
		List<String[]> rows = null;
		try {
			fr = new FileReader(metadataFile);
			reader = new CSVReader(fr);
			rows = reader.readAll();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (fr != null) {
				fr.close();
			}
		}
		return rows;
	}
}
