package edu.pitt.apollo.dataservice.types;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 23, 2015
 * Time: 2:03:32 PM
 * Class: FileInformationCollection
 */
public class FileInformationCollection extends HashMap<BigInteger, List<FileInformation>> {

	private boolean zipFiles;
	private String zipFileName;
	private String outputDirectory;

	/**
	 * @return the zipFiles
	 */
	public boolean isZipFiles() {
		return zipFiles;
	}

	/**
	 * @param zipFiles the zipFiles to set
	 */
	public void setZipFiles(boolean zipFiles) {
		this.zipFiles = zipFiles;
	}

	/**
	 * @return the zipFileName
	 */
	public String getZipFileName() {
		return zipFileName;
	}

	/**
	 * @param zipFilePath the zipFileName to set
	 */
	public void setZipFileName(String zipFilePath) {
		this.zipFileName = zipFilePath;
	}

	/**
	 * @return the outputDirectory
	 */
	public String getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @param outputDirectory the outputDirectory to set
	 */
	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}
	
}
