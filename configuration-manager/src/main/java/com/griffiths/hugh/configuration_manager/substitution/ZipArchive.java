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

/**
 * Wrapper for a zip archive, providing more limited access to its contents.
 * 
 * @author hugh
 */
/**
 * @author hugh
 *
 */
/**
 * @author hugh
 *
 */
public class ZipArchive implements AutoCloseable{
	private final File archiveFile; 
	private final ZipFile zipFile;
	private final Set<String> names;
	
	/**
	 * @param archiveFile Source file.
	 * @throws IOException File cannot be read.
	 * @throws ZipException File is not a valid zip.
	 */
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

	/**
	 * Returns a collection of all the paths within the archive.
	 * 
	 * @return
	 */
	public Set<String> getPathsIndex(){
		return names;
	}
	
	/**
	 * Check whether the archive contains a particular file.
	 * 
	 * @param path
	 * @return
	 */
	public boolean containsFile(String path){
		return (null!=zipFile.getEntry(path));
	}
	
	/**
	 * Get an InputStream of the binary contents of a file within the archive.
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public InputStream getFileContents(String path) throws IOException{
		ZipEntry entry = zipFile.getEntry(path);
		InputStream is = zipFile.getInputStream(entry);
		return is;
	}
	
	/**
	 * Gets the contents of a file within the archive rendered as a string.
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String getFileContentsAsString(String path) throws IOException{
		return IOUtils.toString(getFileContents(path));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	public void close() throws IOException {
		zipFile.close();
	}
	
}
