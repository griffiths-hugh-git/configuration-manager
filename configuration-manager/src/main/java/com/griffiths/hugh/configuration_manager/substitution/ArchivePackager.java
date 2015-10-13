package com.griffiths.hugh.configuration_manager.substitution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import com.griffiths.hugh.configuration_manager.data.Configuration;
import com.griffiths.hugh.configuration_manager.data.ConfigurationFile;
import com.griffiths.hugh.configuration_manager.data.ConfigurationMetadata;
import com.griffiths.hugh.configuration_manager.data.ConfigurationProperty;
import com.griffiths.hugh.configuration_manager.data.Environment;

public class ArchivePackager {
	public void packageArchive(ZipArchive original, Configuration config) throws IOException{
		for (Environment env : config.getEnvironments()){
			// Construct target filename
			File newPath = constructNewFilename(original, env.getId());
			
			FileOutputStream fos = new FileOutputStream(newPath);
			ZipOutputStream zos = new ZipOutputStream(fos);

			transformArchive(original, zos, config.getMetadata(), env);

			zos.flush();
			zos.close();
		}
	}

	private File constructNewFilename(ZipArchive original, String environmentId) {
		File originalPath = original.getArchiveFile().getParentFile();
		String originalName = original.getArchiveFile().getName();
		String newName = originalName+"_"+environmentId;
		File newPath=new File(originalPath, newName);
		return newPath;
	}

	private void transformArchive(ZipArchive original, ZipOutputStream output, ConfigurationMetadata metadata, Environment env) throws IOException {
		for (String name : original.getNamesIndex()) {
			// Transcribe entry
			ZipEntry transcribed = new ZipEntry(name);
			output.putNextEntry(transcribed);
			
			if (metadata.getFilenames().contains(name)){
				byte[] transformedEntry=transformEntry(original.getFileContentsAsString(name), metadata.getFile(name), env).getBytes();
				output.write(transformedEntry);
			}else{
				output.write(IOUtils.toByteArray(original.getFileContents(name)));
			}
		}
	}
	
	private String transformEntry(String original, ConfigurationFile config, Environment env){
		String inProgress=original;
		for (ConfigurationProperty property : config.getProperties()){
			inProgress=inProgress.replace(property.getKey(), env.getValue(property.getKey()));
		}
		
		return inProgress;
	}
}
