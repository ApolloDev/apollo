package edu.pitt.apollo.apollolocationservicesdk;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.apollolocationservicesdk.interfaces.ApolloLocationServiceCacheInterface;
import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceCacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by mas400 on 7/12/16.
 */
public class FileBackedApolloLocationServiceCache implements ApolloLocationServiceCacheInterface {

	static Logger logger = LoggerFactory.getLogger(FileBackedApolloLocationServiceCache.class);
	protected AbstractApolloLocationServiceCache cache;
	private Properties prop = new Properties();
	private static final String PROPERTIES_FILE = "location_cache_config.properties";
	private static final String CODE_CACHE_LOCATION = "codeToLocationCache";
	private static final String NAME_CACHE_LOCATION = "nameToLocationCache";
	private String codeToCacheFileLocation;
	private String nameToCacheFileLocation;

	public FileBackedApolloLocationServiceCache(AbstractApolloLocationServiceCache cache) {
		this.cache = cache;

		try {
			readPropertiesFile();
			readCacheFile();
			cache.codeToJsonMap.setMaxEntries(cache.maxEntries);
			cache.nameToJsonMap.setMaxEntries(cache.maxEntries);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FileBackedApolloLocationServiceCache(AbstractApolloLocationServiceCache cache, String codeCacheFileLocation,
			String nameCacheFileLocation) {
		this.cache = cache;

		try {
			this.codeToCacheFileLocation = codeCacheFileLocation;
			this.nameToCacheFileLocation = nameCacheFileLocation;
			readCacheFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getGeoJsonFromLocationCode(String locationCode) {
		return cache.getGeoJsonFromLocationCode(locationCode);
	}

	@Override
	public String getGeoJsonFromLocationName(String locationName) {
		return cache.getGeoJsonFromLocationName(locationName);
	}

	@Override
	public void addLocationCodeToCache(String locationCode, String geoJson) {
		cache.addLocationCodeToCache(locationCode, geoJson);
	}

	@Override
	public void addLocationNameToCache(String locationName, String geoJson) {
		cache.addLocationNameToCache(locationName, geoJson);
	}

	public void writeCachedFile() throws IOException {

		OutputStream file;
		OutputStream buffer;
		ObjectOutput output;
		if (codeToCacheFileLocation != null) {
			file = new FileOutputStream(codeToCacheFileLocation);
			buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			output.writeObject(this.cache.codeToJsonMap);
			output.flush();
			output.close();
		}
		if (nameToCacheFileLocation != null) {
			file = new FileOutputStream(nameToCacheFileLocation);
			buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);

			output.writeObject(this.cache.nameToJsonMap);
			output.flush();
			output.close();
		}
	}

	public void readCacheFile() throws IOException {
		try {
			InputStream file;
			InputStream buffer;
			ObjectInput input;
			if (codeToCacheFileLocation != null) {
				file = new FileInputStream(codeToCacheFileLocation);
				buffer = new BufferedInputStream(file);
				input = new ObjectInputStream(buffer);

				cache.codeToJsonMap = (ApolloLocationServiceCacheContainer) input.readObject();
			}
			if (nameToCacheFileLocation != null) {
				file = new FileInputStream(nameToCacheFileLocation);
				buffer = new BufferedInputStream(file);
				input = new ObjectInputStream(buffer);

				cache.nameToJsonMap = (ApolloLocationServiceCacheContainer) input.readObject();
			}
		} catch (ClassNotFoundException ex) {
			logger.error("Cannot use Apollo Location Service disk cache. Class not found: " + ex.getMessage());
		} catch (EOFException ex) {
			// this is OK
		} catch (IOException ex) {
			logger.error("Cannot use Apollo Location Service disk cache: " + ex.getMessage());
		}
	}

	public static Properties getPropertiesFile() throws IOException {
		Properties properties = new Properties();

		File configurationFile = new File(ApolloServiceConstants.APOLLO_DIR + "/" + PROPERTIES_FILE);

		try {
			InputStream input = new FileInputStream(configurationFile);
			// load a properties file
			properties.load(input);
			input.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return properties;
	}

	public void readPropertiesFile() throws IOException {
		prop = getPropertiesFile();
		codeToCacheFileLocation = prop.getProperty(CODE_CACHE_LOCATION);
		nameToCacheFileLocation = prop.getProperty(NAME_CACHE_LOCATION);
	}

	public static void main(String[] args) {
		new FileBackedApolloLocationServiceCache(new LeastRecentlyUsedCache(100));
	}
}
