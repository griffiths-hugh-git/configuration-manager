package com.griffiths.hugh.configuration_manager.substitution;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

public class ZipArchive implements AutoCloseable{
	private final File archiveFile; 
	private final ZipFile zipFile;
	private final Set<String> names;
	
	public ZipArchive(File archiveFile) throws ZipException, IOException {
		this.archiveFile=archiveFile;
		zipFile = new ZipFile(archiveFile);
		
		names=new HashSet<String>();
		Enumeration<? extends ZipEntry> entries=zipFile.entries();
		ZipEntry entry;
		while (entries.hasMoreElements()){
			entry = entries.nextElement();
			names.add(entry.getName());
		}
	}
	
	public File getArchiveFile() {
		return archiveFile;
	}

	public Set<String> getNamesIndex(){
		return names;
	}
	
	public boolean containsFile(String path){
		return (null!=zipFile.getEntry(path));
	}
	
	public InputStream getFileContents(String path) throws IOException{
		ZipEntry entry = zipFile.getEntry(path);
		InputStream is = zipFile.getInputStream(entry);
		return is;
	}
	
	public String getFileContentsAsString(String path) throws IOException{
		return IOUtils.toString(getFileContents(path));
	}
	
	public static void main(String[] args) throws IOException{
		File jar = new File("src/test/resources/configuration-test.jar");
		ZipArchive archive = new ZipArchive(jar);

		System.out.println(archive.getFileContents("db.properties"));
		archive.close();
	}

	public void close() throws IOException {
		zipFile.close();
	}
	
}
