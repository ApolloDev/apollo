package edu.pitt.apollo.apollolocationservicesdk;

import edu.pitt.apollo.apollolocationservicesdk.interfaces.ApolloLocationServiceAsFileInterface;
import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceEntry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by mas400 on 8/23/16.
 */
public class ApolloLocationServiceAsFile implements ApolloLocationServiceAsFileInterface {
    static Logger logger = LoggerFactory.getLogger(ApolloLocationServiceAsFile.class);
    protected static AbstractApolloLocationServiceAsFile apolloLocationServiceAsFile;
    private Properties prop = new Properties();
    private static final String PROPERTIES_FILE = "location_cache_config.properties";
    private static final String CODE_FILE_LOCATION = "codeToEntry";
    private static final String NAME_FILE_LOCATION = "nameToEntry";
    private static final String APOLLO_LS_LOCATION = "apolloLSAsFile";
    private static final String LOCATION_TYPE_LOCATION = "locationTypeIdLookup";
    private static final String LETTER_FILE_LOCATION = "letterToEntry";


    private static String codeToEntryFileLocation;
    private static String nameToEntryFileLocation;
    private static String apolloLSAsFileLocation;
    private static String locationTypeFileLocation;
    private static String letterToEntryFileLocation;

    public ApolloLocationServiceAsFile(AbstractApolloLocationServiceAsFile apolloLocationServiceAsFile) {
        this.apolloLocationServiceAsFile = apolloLocationServiceAsFile;
        try {
            readPropertiesFile();
            readApolloLocationServiceFile();
            createLocationTypeLookup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getGeoJsonFromLocationCode(int locationCode) {
        return apolloLocationServiceAsFile.getGeoJsonFromLocationCode(locationCode);
    }

    @Override
    public String getGeoJsonFromLocationName(String locationName, String encompassingRegion) {
        return apolloLocationServiceAsFile.getGeoJsonFromLocationName(locationName, encompassingRegion);
    }

    @Override
    public void addLocationCode(int locationCode, ApolloLocationServiceEntry entry) {
        apolloLocationServiceAsFile.addLocationCode(locationCode, entry);
    }

    @Override
    public void addLocationName(String locationName, ApolloLocationServiceEntry entry) {
        apolloLocationServiceAsFile.addLocationName(locationName, entry);
    }

    public static void serializeApolloLocationServiceAsFile() throws IOException {
        if(apolloLocationServiceAsFile.cacheIsDirty) {
            OutputStream file;
            OutputStream buffer;
            ObjectOutput output;
            if (codeToEntryFileLocation != null) {
                file = new FileOutputStream(codeToEntryFileLocation);
                buffer = new BufferedOutputStream(file);
                output = new ObjectOutputStream(buffer);
                output.writeObject(apolloLocationServiceAsFile.codeToEntryMap);
                output.flush();
                output.close();
            }
            if (nameToEntryFileLocation != null) {
                file = new FileOutputStream(nameToEntryFileLocation);
                buffer = new BufferedOutputStream(file);
                output = new ObjectOutputStream(buffer);

                output.writeObject(apolloLocationServiceAsFile.nameToEntryMap);
                output.flush();
                output.close();
            }
            if (letterToEntryFileLocation != null) {
                file = new FileOutputStream(letterToEntryFileLocation);
                buffer = new BufferedOutputStream(file);
                output = new ObjectOutputStream(buffer);

                output.writeObject(apolloLocationServiceAsFile.letterToEntryMap);
                output.flush();
                output.close();
            }
            apolloLocationServiceAsFile.cacheIsDirty = false;
        }
    }

    public void createApolloLocationServiceFile() throws IOException {
        if (apolloLSAsFileLocation != null) {
            ZipFile zipFile = new ZipFile(apolloLSAsFileLocation);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                InputStreamReader isr = new InputStreamReader(stream);


                String[] headerMapping = {"name", "gid", "parent_gid", "location_type_id"};
                String name = "name";
                String id = "gid";
                String parentId = "parent_gid";
                String locationTypeId = "location_type_id";

                CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(headerMapping);

                CSVParser csvFileParser = new CSVParser(isr, csvFileFormat);
                List<CSVRecord> csvRecords = csvFileParser.getRecords();

                //read the records, skip the header
                for (int i = 1; i < csvRecords.size(); i++) {
                    try {

                        CSVRecord record = csvRecords.get(i);
                        int locationType = Integer.parseInt(record.get(locationTypeId));
                        //skip hospitals, schools, census tracts, and bodies of water
                        if (locationType == 103 || locationType == 104 || locationType == 101 || locationType == 129) {
                            continue;
                        }

                        String editedLocationName = ApolloLocationService.stripAccents(record.get(name));
                        int encompassingRegionCode = -1;
                        try {
                            encompassingRegionCode = Integer.parseInt(record.get(parentId));
                        } catch (Exception e) {
                        }

                        //the entry object contains the location name with accents, the name used as the key has those accents stripped

                        ApolloLocationServiceEntry apolloLocationServiceEntry = new ApolloLocationServiceEntry(record.get(name), Integer.parseInt(record.get(id)), encompassingRegionCode, locationType);

                        apolloLocationServiceAsFile.addLocationCode(Integer.parseInt(record.get(id)), apolloLocationServiceEntry);
                        apolloLocationServiceAsFile.addLocationName(editedLocationName, apolloLocationServiceEntry);
                        apolloLocationServiceAsFile.addToEntryMapByLetter(editedLocationName, apolloLocationServiceEntry);
                    } catch (Exception e) {
                        logger.error("Cannot use Apollo Location Service CSV file: " + e.getMessage());
                    }
                }
            }
        }
    }

    public void createLocationTypeLookup() throws IOException {
        if (locationTypeFileLocation != null) {
            ZipFile zipFile = new ZipFile(locationTypeFileLocation);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                InputStreamReader isr = new InputStreamReader(stream);
                String[] headerMapping = {"id", "name"};
                String id = "id";
                String name = "name";

                CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(headerMapping);

                CSVParser csvFileParser = new CSVParser(isr, csvFileFormat);
                List<CSVRecord> csvRecords = csvFileParser.getRecords();

                //read the records, skip the header
                for (int i = 1; i < csvRecords.size(); i++) {
                    try {
                        CSVRecord record = csvRecords.get(i);
                        int locationTypeId = Integer.parseInt(record.get(id));
                        String locationTypeName = record.get(name);
                        apolloLocationServiceAsFile.setLocationTypeNameAndId(locationTypeId, locationTypeName);

                    } catch (Exception e) {
                        logger.error("Cannot use Apollo Location Service CSV file: " + e.getMessage());
                    }
                }
            }
        }
    }

    public void readApolloLocationServiceFile() throws IOException {
        try {
            InputStream file;
            InputStream buffer;
            ObjectInput input;
            File codeToEntryFile = new File(codeToEntryFileLocation);
            File nameToEntryFile = new File(nameToEntryFileLocation);
            File letterToEntryFile = new File(letterToEntryFileLocation);
            if (!codeToEntryFile.exists() || !nameToEntryFile.exists() || !letterToEntryFile.exists()) {
                createApolloLocationServiceFile();
            } else {
                if (codeToEntryFileLocation != null) {
                    file = new FileInputStream(codeToEntryFileLocation);
                    buffer = new BufferedInputStream(file);
                    input = new ObjectInputStream(buffer);

                    apolloLocationServiceAsFile.codeToEntryMap = (HashMap<Integer, ApolloLocationServiceEntry>) input.readObject();
                }
                if (nameToEntryFileLocation != null) {
                    file = new FileInputStream(nameToEntryFileLocation);
                    buffer = new BufferedInputStream(file);
                    input = new ObjectInputStream(buffer);

                    apolloLocationServiceAsFile.nameToEntryMap = (HashMap<String, List<ApolloLocationServiceEntry>>) input.readObject();
                }
                if (letterToEntryFileLocation != null) {
                    file = new FileInputStream(letterToEntryFileLocation);
                    buffer = new BufferedInputStream(file);
                    input = new ObjectInputStream(buffer);

                    apolloLocationServiceAsFile.letterToEntryMap = (HashMap<Character, List<ApolloLocationServiceEntry>>) input.readObject();
                }
            }
        } catch (ClassNotFoundException ex) {
            logger.error("Cannot use Apollo Location Service disk cache. Class not found: " + ex.getMessage());
        } catch (EOFException ex) {
            // this is OK
        } catch (IOException ex) {
            logger.error("Cannot use Apollo Location Service disk cache: " + ex.getMessage());
        }
    }

    public void readPropertiesFile() throws IOException {
        prop = FileBackedApolloLocationServiceCache.getPropertiesFile();
        codeToEntryFileLocation = prop.getProperty(CODE_FILE_LOCATION);
        nameToEntryFileLocation = prop.getProperty(NAME_FILE_LOCATION);
        apolloLSAsFileLocation = prop.getProperty(APOLLO_LS_LOCATION);
        locationTypeFileLocation = prop.getProperty(LOCATION_TYPE_LOCATION);
        letterToEntryFileLocation = prop.getProperty(LETTER_FILE_LOCATION);
    }
}
