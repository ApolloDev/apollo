package edu.pitt.apollo.db;

//import edu.pitt.apollo.types.UrlOutputResource;
//import edu.pitt.apollo.types.VisualizerResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.mysql.jdbc.Statement;

import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.Role;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time:
 * 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public class ApolloDbUtils {

    static Logger logger = LoggerFactory.getLogger(ApolloDbUtils.class);
    static Map<String, Integer> populationAxisCache = new HashMap<String, Integer>();
    static Map<String, Integer> simulatedPopulationCache = new HashMap<String, Integer>();
    Connection dbcon = null;
    Properties properties;

    // public final int RECORD_NOT_FOUND = -1;
    // public final int PASSWORD_NOT_CORRECT = -2;
    // public final int RECORD_ALREADY_EXISTS = -3;
    // public final int SOFTWARE_ID_RECORD_NOT_FOUND = -4;
    // public final int KEY_NOT_FOUND = -5;
    // public final int RECORD_NOT_INSERTED = -6;
    public enum DbContentDataFormatEnum {

        TEXT, URL, ZIP,};

    public enum DbContentDataType {

        SIMULATOR_LOG_FILE, CONFIGURATION_FILE
    };

    public ApolloDbUtils(File databasePropertiesFile) throws IOException {
        FileInputStream fis = new FileInputStream(databasePropertiesFile);
        properties = new Properties();
        properties.load(fis);
        fis.close();
    }

    private void establishDbConn() throws ClassNotFoundException, SQLException {

        String dbClass = properties.getProperty("class");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        try {
            if (dbcon != null) {
                dbcon.close();
            }
        } catch (SQLException e) {
            // who cares, making a new one anyway
        }
        dbcon = null;
        try {
            Class.forName(dbClass);
            dbcon = DriverManager.getConnection(url, user, password);
            dbcon.setAutoCommit(true);
        } catch (SQLException e) {
            throw new SQLException("Error getting connection to database: " + url + " using username " + user
                    + ".   Specific error was:\n" + e.getMessage());
        }
    }

    private ByteArrayOutputStream getJsonBytes(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mapper.writeValue(baos, obj);

            return baos;
        } catch (IOException ex) {
            System.err.println("IO Exception JSON encoding and getting bytes from RunSimulationMessage");
            return null;
        }
    }

    public Connection getConn() throws ClassNotFoundException, SQLException {
        if (dbcon == null) {
            establishDbConn();
        } else {
            boolean connIsValid = false;
            try {
                connIsValid = dbcon.isValid(1000);
            } catch (SQLException e1) {
                // who cares, we are making a new one anyway!
            }
            if (!connIsValid) {
                establishDbConn();
            }
        }
        return dbcon;
    }

    // public Map<String, String> getStoredRuns() throws SQLException,
    // ClassNotFoundException {
    // try {
    // String query = "SELECT LABEL,MD5HASHOFCONFIGURATIONFILE from run";
    // PreparedStatement pstmt = getConn().prepareStatement(query);
    // ResultSet rs = pstmt.executeQuery();
    //
    // Map<String, String> hashLabelMap = new HashMap<String, String>();
    // while (rs.next()) {
    // String hash = rs.getString("md5HashOfConfigurationFile");
    // String label = rs.getString("label");
    // if (label != null) {
    // hashLabelMap.put(label, hash);
    // }
    // }
    // return hashLabelMap;
    // } catch (SQLException e) {
    // throw new
    // SQLException("Error retreiving all stored run hashes. Specific error was:\n"
    // + e.getMessage());
    // }
    // }
    public int getUserKey(String requesterId, String requesterPassword) throws SQLException, ClassNotFoundException,
            ApolloDatabaseUserPasswordException, ApolloDatabaseKeyNotFoundException {

        String query = "SELECT id, requester_password FROM users WHERE requester_id = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setString(1, requesterId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String password = rs.getString("requester_password");
            if (requesterPassword.equals(password)) {
                return rs.getInt("id");
            } else {
                throw new ApolloDatabaseUserPasswordException("No entry in the users table where requester_id = " + requesterId
                        + " for the given password.");
            }
        } else {
            throw new ApolloDatabaseKeyNotFoundException("No entry in the users table where requester_id = " + requesterId);
        }
    }

    // user key doesn't exist
    public int addUser(String requesterId, String requesterPassword, String email) throws SQLException, ClassNotFoundException,
            ApolloDatabaseRecordAlreadyExistsException {
        // check authorization?!
        try {
            int userKey = getUserKey(requesterId, requesterPassword);
            throw new ApolloDatabaseRecordAlreadyExistsException("User " + requesterId + " already exists in the database.");
        } catch (ApolloDatabaseKeyNotFoundException e) {
            // good this means the user doesn't already exist
        } catch (ApolloDatabaseUserPasswordException e) {
            // error
        }

        String query = "INSERT INTO users (requester_id,requester_password,requester_email) VALUES (?,?,?)";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setString(1, requesterId);
        pstmt.setString(2, requesterPassword);
        pstmt.setString(3, email);
        pstmt.execute();

        query = "SELECT LAST_INSERT_ID()";
        pstmt = getConn().prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        return rs.getInt(1);

    }

    public int getSoftwareIdentificationKey(SoftwareIdentification softwareIdentification) throws SQLException,
            ClassNotFoundException, ApolloDatabaseKeyNotFoundException {

        String query = "SELECT id FROM software_identification where developer = ? and name = ? and version = ? and service_type = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setString(1, softwareIdentification.getSoftwareDeveloper());
        pstmt.setString(2, softwareIdentification.getSoftwareName());
        pstmt.setString(3, softwareIdentification.getSoftwareVersion());
        pstmt.setString(4, softwareIdentification.getSoftwareType().toString());
        ResultSet rs = pstmt.executeQuery();
        int softwareIdKey = -1;
        if (rs.next()) {
            softwareIdKey = rs.getInt(1);
            return softwareIdKey;
        } else {
            throw new ApolloDatabaseKeyNotFoundException("No entry in the software_identification table where developer = "
                    + softwareIdentification.getSoftwareDeveloper() + " and name = " + softwareIdentification.getSoftwareName()
                    + " and version = " + softwareIdentification.getSoftwareVersion() + " and service_type = "
                    + softwareIdentification.getSoftwareType().toString());

        }
    }

    private String getMd5(Object object) {
        return DigestUtils.md5Hex(getJsonBytes(object).toString());
    }

    public int getRunKey(RunSimulationMessage runSimulationMessage) throws SQLException, ClassNotFoundException,
            ApolloDatabaseException {
        Authentication auth = runSimulationMessage.getAuthentication();

        int userKey = getUserKey(auth.getRequesterId(), auth.getRequesterPassword());
        int softwareKey = getSoftwareIdentificationKey(runSimulationMessage.getSimulatorIdentification());

        String hash = getMd5(runSimulationMessage);

        String query = "SELECT id FROM run WHERE md5_hash_of_run_message = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setString(1, hash);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            query = "INSERT INTO run (requester_id, software_id, md5_hash_of_run_message) VALUES (?,?,?)";
            pstmt = getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, userKey);
            pstmt.setInt(2, softwareKey);
            pstmt.setString(3, hash);
            pstmt.execute();
            rs = pstmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    public int addRole(SoftwareIdentification softwareIdentification, boolean canRun, boolean canViewCache) throws SQLException,
            ClassNotFoundException, ApolloDatabaseException {

        int softwareIdKey;
        try {
            softwareIdKey = getSoftwareIdentificationKey(softwareIdentification);
        } catch (ApolloDatabaseKeyNotFoundException e) {
            throw new ApolloDatabaseKeyNotFoundException(
                    "The softwareIdentifiation object provided to addRole() does not have an entry in the software_identification table. Error was: "
                    + e.getMessage());
        }

        int roleKey;
        try {
            roleKey = getRoleKey(softwareIdKey, canRun, canViewCache);
            return roleKey;
        } catch (ApolloDatabaseKeyNotFoundException e) {
            // this means that we need to insert
        }

        String query = "INSERT INTO ROLES (software_id, can_run, can_view_cached_results) values (?, ?, ?)";

        PreparedStatement pstmt = getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try {
            pstmt.setInt(1, softwareIdKey);
            pstmt.setBoolean(2, canRun);
            pstmt.setBoolean(3, canViewCache);
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                roleKey = rs.getInt(1);
                return roleKey;
            } else {
                throw new ApolloDatabaseException("No primary key returned from addRole().");
            }
        } finally {
            pstmt.close();
        }

    }

    private int getRoleKey(int softwareIdKey, boolean canRun, boolean canViewCache) throws SQLException, ClassNotFoundException,
            ApolloDatabaseKeyNotFoundException {
        if (softwareIdKey >= 1) {
            // software id found...now lets see if this specific role exists...
            String query = "SELECT id FROM roles WHERE software_id = ? AND can_run = ? AND can_view_cached_results = ?";
            PreparedStatement pstmt = getConn().prepareStatement(query);
            try {
                pstmt.setInt(1, softwareIdKey);
                pstmt.setBoolean(2, canRun);
                pstmt.setBoolean(3, canViewCache);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    throw new ApolloDatabaseKeyNotFoundException("No entry found in the roles table where software_id = "
                            + softwareIdKey + " and can_run = " + canRun + " and can_view_cached_resuls = " + canViewCache);
                } else {
                    return rs.getInt(1);
                }
            } finally {
                pstmt.close();
            }
        } else {
            throw new ApolloDatabaseKeyNotFoundException("getRoleKey() called with invalid softwareIdKey: " + softwareIdKey);
        }
    }

    public Map<Integer, ServiceRegistrationRecord> getRegisteredSoftware() throws SQLException, ClassNotFoundException {
        Map<Integer, ServiceRegistrationRecord> result = new HashMap<Integer, ServiceRegistrationRecord>();

        // get all of the users that are an admin of a software
        String query = "SELECT u.id, u.requester_id FROM users u, software_identification s WHERE " + "s.admin_id = u.id";

        Map<Integer, String> userIdMap = new HashMap<Integer, String>();

        PreparedStatement pstmt = getConn().prepareStatement(query);
        try {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt(1);
                String requester_id = rs.getString(2);
                userIdMap.put(userId, requester_id);
            }

        } finally {
            pstmt.close();
        }

        query = "SELECT id, developer, name, version, service_type, wsdl_url, admin_id FROM software_identification";
        pstmt = getConn().prepareStatement(query);
        try {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ServiceRegistrationRecord srr = new ServiceRegistrationRecord();
                srr.setSoftwareIdentification(new SoftwareIdentification());
                srr.setAuthentication(new Authentication());
                int id = rs.getInt(1);
                srr.getSoftwareIdentification().setSoftwareDeveloper(rs.getString(2));
                srr.getSoftwareIdentification().setSoftwareName(rs.getString(3));
                srr.getSoftwareIdentification().setSoftwareVersion(rs.getString(4));
                srr.getSoftwareIdentification().setSoftwareType(ApolloSoftwareTypeEnum.fromValue(rs.getString(5)));
                srr.setUrl(rs.getString(6));
                srr.getAuthentication().setRequesterId(userIdMap.get(rs.getInt(7)));
                srr.getAuthentication().setRequesterPassword("");
                result.put(id, srr);
            }
        } finally {
            pstmt.close();
        }
        return result;

    }

    // okay so user A can see...?
    private List<Role> getUserRoles(int userId) throws SQLException, ClassNotFoundException {
        // List<Role> roles = new ArrayList<Role>();
        //
        // String query =
        // "SELECT r.software_id, r.can_run, r.can_view_cached_results from roles r, user_roles ur where "
        // + "r.id = ur.role_id AND ur.user_id = ?";
        // PreparedStatement pstmt = getConn().prepareStatement(query);
        // try {
        // pstmt.setInt(1, userId);
        // ResultSet rs = pstmt.executeQuery();
        // while (rs.next()) {
        // Role r = new Role();
        // r.setSoftwareIdentification(getSoftwareIdentification(rs.getInt(1)));
        // r.setCanRun(rs.getBoolean(2));
        // r.setCanViewCachedResults(rs.getBoolean(3));
        // roles.add(r);
        // }
        // } finally {
        // pstmt.close();
        // }
        // return roles;
        return null;
    }

    private int addUserRole(int userId, int roleId) {

        return 0;
    }

    public int addTextDataContent(InputStream content) throws SQLException, ClassNotFoundException, IOException {
        return addTextDataContent(IOUtils.toString(content));
    }

    public int addTextDataContent(String content) throws SQLException, ClassNotFoundException {
        String md5 = DigestUtils.md5Hex(content);
        // is the data already in the table?
        String query = "SELECT id FROM run_data_content where md5_hash_of_content = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setString(1, md5);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            // no need to store the data twice
            return rs.getInt(1);
        } else {
            pstmt.close();
            // store it
            query = "INSERT INTO run_data_content (text_content, md5_hash_of_content) values (?,?)";
            pstmt = getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            try {
                pstmt.setString(1, content);
                pstmt.setString(2, md5);
                pstmt.execute();
                rs = pstmt.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            } finally {
                pstmt.close();
            }
        }
    }

    public Map<String, ByteArrayOutputStream> getDataContentForSoftware(int runKey, int sourceSoftwareIdKey,
            int destinationSoftwareIdKey) throws SQLException, ClassNotFoundException, IOException {
        Map<String, ByteArrayOutputStream> result = new HashMap<String, ByteArrayOutputStream>();

        String query = "SELECT " + "rddv.label, " + "rdc.text_content " + "FROM " + "run_data_content rdc, " + "run_data rd, "
                + "run_data_description_view rddv " + "WHERE " + "rd.content_id = rdc.id AND " + "rd.run_id = ? AND "
                + "rddv.run_data_description_id = rd.description_id AND " + "rddv.source_software = ? AND "
                + "rddv.destination_software = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = getConn().prepareStatement(query);
            pstmt.setInt(1, runKey);
            pstmt.setInt(2, sourceSoftwareIdKey);
            pstmt.setInt(3, destinationSoftwareIdKey);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String label = rs.getString(1);
                String dataContent = rs.getString(2);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                baos.write(dataContent.getBytes());
                result.put(label, baos);
            }
        } finally {
            pstmt.close();
        }

        return result;

    }

    public int associateContentWithRunId(int runKey, int dataContentKey, int runDataDescriptionId) throws ClassNotFoundException,
            SQLException, ApolloDatabaseKeyNotFoundException {

        if (runDataDescriptionId >= 0) {
            String query = "SELECT id FROM run_data WHERE run_id = ? AND description_id = ? and content_id = ?";
            PreparedStatement pstmt = null;
            try {
                pstmt = getConn().prepareStatement(query);
                pstmt.setInt(1, runKey);
                pstmt.setInt(2, runDataDescriptionId);
                pstmt.setInt(3, dataContentKey);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } finally {
                pstmt.close();
            }

            query = "INSERT INTO run_data (run_id, description_id, content_id) values (?,?,?)";
            try {
                pstmt = getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, runKey);

                pstmt.setInt(2, runDataDescriptionId);
                pstmt.setInt(3, dataContentKey);
                pstmt.execute();
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            } finally {
                pstmt.close();
            }
        } else {
            throw new ApolloDatabaseKeyNotFoundException("associateContentWithRunId() called with an invalid key: " + runKey);
        }

    }

    public int getRunDataDescriptionId(DbContentDataFormatEnum dataFormat, String dataLabel, DbContentDataType dataType,
            int dataSourceSoftwareIdKey, int dataDestinationSoftwareIdKey) throws SQLException, ClassNotFoundException,
            ApolloDatabaseKeyNotFoundException {
        String query = "SELECT v.run_data_description_id FROM run_data_description_view v WHERE "
                + "v.format = ? AND v.label = ? and v.type = ? and v.source_software = ? and v.destination_software = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setString(1, dataFormat.toString());
        pstmt.setString(2, dataLabel);
        pstmt.setString(3, dataType.toString());
        pstmt.setInt(4, dataSourceSoftwareIdKey);
        pstmt.setInt(5, dataDestinationSoftwareIdKey);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new ApolloDatabaseKeyNotFoundException("No entry found in run_data_description_view where format = "
                    + dataFormat.toString() + " and label = " + dataLabel + " and type = " + dataType.toString()
                    + " and source_software = " + dataSourceSoftwareIdKey + " and destination_software = "
                    + dataDestinationSoftwareIdKey);
        }
    }

    public int getRunDataDescriptionId(DbContentDataFormatEnum dataFormat, String dataLabel, DbContentDataType dataType,
            SoftwareIdentification dataSourceSoftwareIdentification, SoftwareIdentification dataDestinationSoftwareIdentification)
            throws SQLException, ClassNotFoundException, ApolloDatabaseKeyNotFoundException {
        return getRunDataDescriptionId(dataFormat, dataLabel, dataType,
                getSoftwareIdentificationKey(dataSourceSoftwareIdentification),
                getSoftwareIdentificationKey(dataDestinationSoftwareIdentification));
    }

    public int addRunDataDescription(String description, String dataFormat, String dataLabel, String dataType,
            String dataSourceSoftware, String dataDestinationSoftware) throws SQLException, ClassNotFoundException {
        int runDataDescriptionKey = -1;
        String query = "INSERT INTO run_data_description SET label = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query, Statement.NO_GENERATED_KEYS);
        try {
            pstmt.setString(1, description);
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                runDataDescriptionKey = rs.getInt(1);
            }
            query = "INSERT INTO run_data_description_axis_value (run_data_description_id, run_data_description_axis_id, value) values (?,?,?)";
            pstmt.setInt(1, runDataDescriptionKey);
            // pstmt.setIn
            // not done yet
            return -1;
        } catch (Exception e) {
        }
        return -1;
    }

    public SoftwareIdentification getSoftwareIdentification(int i) throws ApolloDatabaseKeyNotFoundException, SQLException,
            ClassNotFoundException {
        String query = "SELECT developer, name, version, service_type FROM software_identification WHERE " + "id = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setInt(1, i);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            SoftwareIdentification softwareIdentification = new SoftwareIdentification();
            softwareIdentification.setSoftwareDeveloper(rs.getString(1));
            softwareIdentification.setSoftwareName(rs.getString(2));
            softwareIdentification.setSoftwareVersion(rs.getString(3));
            softwareIdentification.setSoftwareType(ApolloSoftwareTypeEnum.fromValue(rs.getString(4)));
            return softwareIdentification;
        } else {
            throw new ApolloDatabaseKeyNotFoundException("No entry found in software_identification where id = " + i);
        }
    }

    public String getUrlForSoftwareIdentification(SoftwareIdentification softwareIdentification) throws SQLException,
            ApolloDatabaseKeyNotFoundException, ClassNotFoundException {
        String query = "SELECT wsdl_url FROM software_identification WHERE developer = ? and name = ? and version = ? and service_type = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setString(1, softwareIdentification.getSoftwareDeveloper());
        pstmt.setString(2, softwareIdentification.getSoftwareName());
        pstmt.setString(3, softwareIdentification.getSoftwareVersion());
        pstmt.setString(4, softwareIdentification.getSoftwareType().toString());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        } else {
            throw new ApolloDatabaseKeyNotFoundException("Unable to get wsdl_url from software_identification where developer = "
                    + softwareIdentification.getSoftwareDeveloper() + " and name = " + softwareIdentification.getSoftwareName()
                    + " and version = " + softwareIdentification.getSoftwareVersion() + " and service_type = "
                    + softwareIdentification.getSoftwareType().toString());
        }

    }

    public String getUrlForSoftwareIdentification(int softwareIdentificaitonKey) throws ApolloDatabaseKeyNotFoundException,
            SQLException, ClassNotFoundException {
        String query = "SELECT wsdl_url FROM software_identification WHERE id = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setInt(1, softwareIdentificaitonKey);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        } else {
            throw new ApolloDatabaseKeyNotFoundException(
                    "Unable to get wsdl_url from software_identification where software_identification.id = "
                    + softwareIdentificaitonKey);
        }

    }

    // get files
    public int addSimulationRun(RunSimulationMessage runSimulationMessage) throws ApolloDatabaseRecordNotInsertedException,
            SQLException, ClassNotFoundException, ApolloDatabaseKeyNotFoundException {
        int softwareKey = getSoftwareIdentificationKey(runSimulationMessage.getSimulatorIdentification());

        String query = "INSERT INTO run (md5_hash_of_run_message, software_id, requester_id, last_service_to_be_called) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, getMd5(runSimulationMessage));
        pstmt.setInt(2, softwareKey);
        pstmt.setInt(3, 1);
        pstmt.setInt(4, 1);
        pstmt.execute();

        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new ApolloDatabaseRecordNotInsertedException("Record not inserted!");
        }

    }

    public int getSimulationRunId(RunSimulationMessage runSimulationMessage) throws ApolloDatabaseKeyNotFoundException,
            SQLException, ClassNotFoundException {
        int softwareKey = getSoftwareIdentificationKey(runSimulationMessage.getSimulatorIdentification());
        String md5Hash = getMd5(runSimulationMessage);

        String query = "SELECT id FROM run WHERE md5_hash_of_run_message = ? AND software_id = ? and requester_id = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setString(1, md5Hash);
        pstmt.setInt(2, softwareKey);
        pstmt.setInt(3, 1);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new ApolloDatabaseKeyNotFoundException("No id found for simulation run where md5_hash_of_run_message = "
                    + md5Hash + " and softare_id = " + softwareKey + " and requester_id = 1");
        }
    }

    /**
     * 
     * @param runId
     * @param softwareIdentificationKey
     * @return The number of rows that were updated (either 1 or 0).
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int updateLastServiceToBeCalledForRun(Integer runId, Integer softwareIdentificationKey) throws SQLException,
            ClassNotFoundException {
        String query = "UPDATE run SET last_service_to_be_called = ? WHERE id = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setInt(1, softwareIdentificationKey);
        pstmt.setInt(2, runId);
        return pstmt.executeUpdate();

    }

    public int updateLastServiceToBeCalledForRun(Integer runId, SoftwareIdentification softwareIdentification)
            throws ApolloDatabaseKeyNotFoundException, SQLException, ClassNotFoundException {
        int softwareIdentificationKey = getSoftwareIdentificationKey(softwareIdentification);
        return updateLastServiceToBeCalledForRun(runId, softwareIdentificationKey);
    }

    public int getIdOfLastServiceToBeCalledForRun(Integer runId) throws ApolloDatabaseKeyNotFoundException, SQLException,
            ClassNotFoundException {
        String query = "SELECT last_service_to_be_called FROM run WHERE id = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setInt(1, runId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new ApolloDatabaseKeyNotFoundException("No last_service_to_be_called found for simulation run where id = "
                    + runId);
        }
    }

    public SoftwareIdentification getLastServiceToBeCalledForRun(Integer runId) throws ApolloDatabaseKeyNotFoundException,
            SQLException, ClassNotFoundException {

        int softwareId = getIdOfLastServiceToBeCalledForRun(runId);

        String query = "SELECT developer, name, version, service_type FROM software_identification WHERE id = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setInt(1, softwareId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            String developer = rs.getString("developer");
            String name = rs.getString("name");
            String version = rs.getString("version");
            String type = rs.getString("service_type");

            SoftwareIdentification softwareIdentification = new SoftwareIdentification();
            softwareIdentification.setSoftwareDeveloper(developer);
            softwareIdentification.setSoftwareName(name);
            softwareIdentification.setSoftwareVersion(version);
            softwareIdentification.setSoftwareType(ApolloSoftwareTypeEnum.fromValue(type));
            return softwareIdentification;
        } else {
            throw new ApolloDatabaseKeyNotFoundException("No software identification found for id = " + softwareId);
        }

    }

    public int getNewSimulationGroupId() throws SQLException, ClassNotFoundException, ApolloDatabaseRecordNotInsertedException {
        String query = "INSERT INTO simulation_groups DEFAULT";
        PreparedStatement pstmt = getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.execute();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new ApolloDatabaseRecordNotInsertedException("Unable to create new simulation group, insert failed.");
        }
    }

    public void addRunIdsToSimulationGroup(int simulationGroupId, List<String> simulationRunIds) throws SQLException,
            ClassNotFoundException {
        String query = "INSERT INTO simulation_groups_definition (simulation_group_id, run_id) VALUES (?,?)";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        for (String simulationRunId : simulationRunIds) {
            pstmt.setInt(1, simulationGroupId);
            pstmt.setInt(2, Integer.valueOf(simulationRunId));
            pstmt.execute();
        }
    }

    public int addVisualizationRun(RunVisualizationMessage runVisualizationMessage) throws ApolloDatabaseKeyNotFoundException,
            SQLException, ClassNotFoundException, ApolloDatabaseRecordNotInsertedException {

        int softwareKey = getSoftwareIdentificationKey(runVisualizationMessage.getVisualizerIdentification());
        int simulationGroupId = getNewSimulationGroupId();
        addRunIdsToSimulationGroup(simulationGroupId, runVisualizationMessage.getSimulationRunIds());

        String query = "INSERT INTO run (md5_hash_of_run_message, software_id, requester_id, last_service_to_be_called, simulation_group_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, getMd5(runVisualizationMessage));
        pstmt.setInt(2, softwareKey);
        pstmt.setInt(3, 1);
        pstmt.setInt(4, 4); // 4 is translator
        pstmt.setInt(5, simulationGroupId);
        pstmt.execute();

        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new ApolloDatabaseRecordNotInsertedException("Record not inserted!");
        }
    }

    public void removeRunData(int runId) throws SQLException, ClassNotFoundException {
        // need to delete the data content
        // find out if there any other runs that reference this data content
        String query = "SELECT content_id FROM run_data WHERE run_id = ?";
        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.setInt(1, runId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int content_id = rs.getInt(1);
            String innerQuery = "SELECT content_id FROM run_data WHERE run_id <> ?";
            PreparedStatement innerPstmt = getConn().prepareStatement(innerQuery);
            innerPstmt.setInt(1, runId);
            ResultSet innerRs = innerPstmt.executeQuery();
            if (!innerRs.next()) {
                // content_id is not used by any other run, delete it!
                String deleteQuery = "DELETE FROM run_data_content WHERE id = ?";
                PreparedStatement deletePstmt = getConn().prepareStatement(deleteQuery);
                deletePstmt.setInt(1, content_id);
                deletePstmt.execute();
            }

        }
        query = "DELETE FROM run_data WHERE run_id = ?";
        pstmt = getConn().prepareStatement(query);
        pstmt.setInt(1, runId);
        pstmt.execute();

        query = "SELECT simulation_group_id FROM run WHERE id = ?";
        pstmt = getConn().prepareStatement(query);
        pstmt.setInt(1, runId);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            int simulation_group_id = rs.getInt(1);
            if (!rs.wasNull()) {
                String innerQuery = "DELETE FROM simulation_groups_definition WHERE simulation_group_id = ?";
                pstmt = getConn().prepareStatement(innerQuery);
                pstmt.setInt(1, simulation_group_id);
                pstmt.execute();

                innerQuery = "DELETE FROM simulation_groups WHERE id = ?";
                pstmt = getConn().prepareStatement(innerQuery);
                pstmt.setInt(1, simulation_group_id);
                pstmt.execute();
            }
        }

        query = "DELETE FROM time_series WHERE run_id = ?";
        pstmt = getConn().prepareStatement(query);
        pstmt.setInt(1, runId);
        pstmt.execute();

        query = "DELETE FROM run WHERE id = ?";
        pstmt = getConn().prepareStatement(query);
        pstmt.setInt(1, runId);
        pstmt.execute();

    }

    public void createAxisId(String label) throws ClassNotFoundException, SQLException {
        String query = "insert into population_axis (label) values (?)";
        PreparedStatement pstmt;
        try {
            pstmt = getConn().prepareStatement(query);
            pstmt.setString(1, label);
            pstmt.execute();
        } catch (SQLException e) {
            throw new SQLException("Error creating axis id for label: " + label + ".   Specific error was:\n" + e.getMessage());
        }

    }

    public int getAxisId(String label) throws SQLException, ClassNotFoundException {

        Integer id = populationAxisCache.get(label);

        if (id == null) {
            try {
                String query = "Select id from population_axis where label like ?";
                PreparedStatement pstmt = getConn().prepareStatement(query);

                pstmt.setString(1, "%" + label + "%");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    id = rs.getInt(1);
                    populationAxisCache.put(label, new Integer(id));
                    // System.out.println(id);
                } // end while
            } catch (SQLException e) {
                throw new SQLException("Error retreiving axis id for label: " + label + ".   Specific error was:\n"
                        + e.getMessage());

            }
            try {
                if (id == -1) {
                    String query = "insert into population_axis (label) values (?)";

                    PreparedStatement pstmt = getConn().prepareStatement(query);
                    pstmt.setString(1, label);
                }
            } catch (SQLException e) {
                throw new SQLException("Error creating axis id for label: " + label + ".   Specific error was:\n"
                        + e.getMessage());

            }
        }
        return id;

    }

    public Integer getPopulationId(String disease_state) throws ClassNotFoundException, SQLException {
        Integer popId = simulatedPopulationCache.get(disease_state);

        if (popId == null) {
            try {
                String query = "select id from simulated_population where label like ?";
                PreparedStatement pstmt = getConn().prepareStatement(query);
                pstmt.setString(1, disease_state);

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    // System.out.println("Internal id is:" + rs.getInt(1));
                    popId = rs.getInt(1);

                    simulatedPopulationCache.put(disease_state, popId);
                } // end while

            } catch (SQLException e) {
                throw new SQLException("Error retreiving population id for: " + disease_state
                        + " from simulated_population.  Specific error was:\n" + e.getMessage());
            }
        }
        return popId;

    }

    public int getOrCreatePopulationId(int axisId, String disease_state) throws ClassNotFoundException, SQLException {
        Integer popId = getPopulationId(disease_state);

        if (popId == null) {
            try {
                String query = "INSERT INTO simulated_population (LABEL) VALUES ('" + disease_state + "')";

                PreparedStatement pstmt = getConn().prepareStatement(query);
                pstmt.execute();
            } catch (SQLException e) {
                throw new SQLException("Error inserting disease state: " + disease_state + " into simulated_population."
                        + " Specific error was:\n" + e.getMessage());
            }

            try {
                String query = "SELECT ID FROM simulated_population WHERE LABEL like ?";
                PreparedStatement pstmt = getConn().prepareStatement(query);
                pstmt.setString(1, disease_state);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    // System.out.println("Population id is:" +
                    // rs.getInt(1));
                    popId = rs.getInt(1);
                    simulatedPopulationCache.put(disease_state, popId);
                } // end while
            } catch (SQLException e) {
                throw new SQLException("Error retreiving ID from simulated_population for label: " + disease_state + "."
                        + "   Specific error was:\n" + e.getMessage());
            }

            try {
                String query = "INSERT INTO simulated_population_axis_value (population_id, axis_id, value) values (?,?,?)";
                PreparedStatement pstmt = getConn().prepareStatement(query);
                pstmt.setInt(1, popId);
                pstmt.setInt(2, axisId);
                pstmt.setString(3, disease_state);
                pstmt.execute();
            } catch (SQLException e) {
                throw new SQLException("Error inserting value: " + disease_state + " into simulated_population_axis_value."
                        + " Specific error was:\n" + e.getMessage());
            }
        }

        return popId;
    }

    public int getOrCreatePopulationId(int diseaseStateAxisId, int locationAxisId, String disease_state, String location)
            throws ClassNotFoundException, SQLException {
        Integer popId = -1;

        popId = getPopulationId(disease_state + " in " + location);

        if (popId == null || popId == -1) {
            try {
                String query = "INSERT INTO simulated_population (LABEL) VALUES ('" + disease_state + " in " + location + "')";

                PreparedStatement pstmt = getConn().prepareStatement(query);
                pstmt.execute();
            } catch (SQLException e) {
                throw new SQLException("Error inserting disease state: " + disease_state + " into simulated_population."
                        + " Specific error was:\n" + e.getMessage());
            }

            try {
                String query = "SELECT ID FROM simulated_population WHERE LABEL like ?";
                PreparedStatement pstmt = getConn().prepareStatement(query);
                pstmt.setString(1, disease_state + " in " + location);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    // System.out.println("Population id is:" +
                    // rs.getInt(1));
                    popId = rs.getInt(1);
                } // end while
            } catch (SQLException e) {
                throw new SQLException("Error retreiving ID from simulated_population for label: " + disease_state + "."
                        + "   Specific error was:\n" + e.getMessage());
            }

            // there needs to be 2 inserts, one for the disease state, and one
            // for the regionId
            // they will have the same population ID but different axis IDs
            try {
                // disease state
                String query = "INSERT INTO simulated_population_axis_value (population_id, axis_id, value) values (?,?,?)";
                PreparedStatement pstmt = getConn().prepareStatement(query);
                pstmt.setInt(1, popId);
                pstmt.setInt(2, diseaseStateAxisId);
                pstmt.setString(3, disease_state);
                pstmt.execute();

                // location
                query = "INSERT INTO simulated_population_axis_value (population_id, axis_id, value) values (?,?,?)";
                pstmt = getConn().prepareStatement(query);
                pstmt.setInt(1, popId);
                pstmt.setInt(2, locationAxisId);
                pstmt.setString(3, location);
                pstmt.execute();

            } catch (SQLException e) {
                throw new SQLException("Error inserting value: " + disease_state + " into simulated_population_axis_value."
                        + " Specific error was:\n" + e.getMessage());
            }
        }

        return popId;
    }

    public void insertDiseaseStateTimeSeries(PreparedStatement pstmt, int runId, int popId, String disease_state, List<Double> ts)
            throws SQLException, ClassNotFoundException {

        logger.info("In insertDiseaseStateTimeSeries, runId={}, popId={}, disease_state={}", runId, popId, disease_state);
        // logger.info("Time Series is:");

        try {
            for (int i = 0; i < ts.size(); i++) {
                // logger.debug("Time Series[{}] is: {}", i, ts.get(i));
                pstmt.setInt(1, runId);
                pstmt.setInt(2, popId);
                pstmt.setInt(3, i);
                pstmt.setDouble(4, ts.get(i));
                pstmt.addBatch();
            }
        } catch (SQLException e) {
            logger.error("Error inserting disease state time series for runId={}, popId={}, disease_state={}", runId, popId,
                    disease_state);

            throw new SQLException("Error inserting disease state time series for internal run id: " + runId
                    + ", disease state: " + disease_state + ".   Specific error was:\n" + e.getMessage());
        }

    }

    public void insertDiseaseStateTimeSeriesNegative(PreparedStatement pstmt, int runId, int popId, String disease_state,
            List<Double> ts) throws SQLException, ClassNotFoundException {

        logger.info("In insertDiseaseStateTimeSeries, runId={}, popId={}, disease_state={}", runId, popId, disease_state);
        // logger.info("Time Series is:");

        try {
            int counter = 0;
            for (int i = -ts.size(); i < 0; i++) {
                // logger.debug("Time Series[{}] is: {}", i, ts.get(i));
                pstmt.setInt(1, runId);
                pstmt.setInt(2, popId);
                pstmt.setInt(3, i);
                pstmt.setDouble(4, ts.get(counter));
                pstmt.addBatch();
                counter++;
            }
        } catch (SQLException e) {
            logger.error("Error inserting disease state time series for runId={}, popId={}, disease_state={}", runId, popId,
                    disease_state);

            throw new SQLException("Error inserting disease state time series for internal run id: " + runId
                    + ", disease state: " + disease_state + ".   Specific error was:\n" + e.getMessage());
        }

    }

    public void awaitRowCountForTimeSeriesTable(int runId, int totalRowCount) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = getConn().prepareStatement("select count(*) from time_series where run_id = ?");
        int actualCount = -1;
        for (int sleepSeconds = 0; actualCount != totalRowCount; sleepSeconds = Math.min(sleepSeconds + 1, 10)) {
            if (actualCount != -1) {
                try {
                    Thread.sleep(sleepSeconds * 1000);
                } catch (InterruptedException e) {
                    // it is okay to interrupt sleep
                }
            }
            pstmt.setInt(1, runId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            actualCount = rs.getInt(1);
            rs.close();
        }
        System.out.printf("Confirmed that %d expected rows are in the database for run id %d.\n", actualCount, runId);
    }

//    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

//        ApolloDbUtils utils = new ApolloDbUtils(new File("/apollo_201/database.properties"));
//        Map<String, ByteArrayOutputStream> map = utils.getDataContentForSoftware(3, 1, 2);
//        
//        String configurationFileContent = null;
//            for (String label : map.keySet()) {
//                System.out.println("label: " + label);
//                if (label.equals("config.txt")) {
//                    configurationFileContent = map.get(label).toString();
////                    break;
//                }
//            }
//            
//            System.out.println("content: " + configurationFileContent);
//
//    }
}
