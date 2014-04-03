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
import edu.pitt.apollo.types.v2_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.UrlOutputResource;
import edu.pitt.apollo.types.v2_0_1.VisualizerResult;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time:
 * 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public class ApolloDbUtils {

	Connection dbcon = null;
	Properties properties;

	// public final int RECORD_NOT_FOUND = -1;
	// public final int PASSWORD_NOT_CORRECT = -2;
	// public final int RECORD_ALREADY_EXISTS = -3;
	// public final int SOFTWARE_ID_RECORD_NOT_FOUND = -4;
	// public final int KEY_NOT_FOUND = -5;
	// public final int RECORD_NOT_INSERTED = -6;

	public enum DbContentDataFormatEnum {
		TEXT, URL, ZIP,
	};

	public enum DbContentDataType {
		CONFIGURATION_FILE
	};

	public ApolloDbUtils(File databasePropertiesFile) throws IOException {
		FileInputStream fis = new FileInputStream(databasePropertiesFile);
		properties = new Properties();
		properties.load(fis);
		fis.close();
	}

	public String getSoftwareIdAsDbString(SoftwareIdentification softwareIdentification) {
		return softwareIdentification.getSoftwareDeveloper() + "#" + softwareIdentification.getSoftwareName() + "#"
				+ softwareIdentification.getSoftwareVersion();
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

	public int getSoftwareIdentificationKey(SoftwareIdentification softwareIdentificationId) throws SQLException,
			ClassNotFoundException, ApolloDatabaseKeyNotFoundException {

		String query = "SELECT id FROM software_identification where developer = ? and name = ? and version = ? and service_type = ?";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.setString(1, softwareIdentificationId.getSoftwareDeveloper());
		pstmt.setString(2, softwareIdentificationId.getSoftwareName());
		pstmt.setString(3, softwareIdentificationId.getSoftwareVersion());
		pstmt.setString(4, softwareIdentificationId.getSoftwareType().toString());
		ResultSet rs = pstmt.executeQuery();
		int softwareIdKey = -1;
		if (rs.next()) {
			softwareIdKey = rs.getInt(1);
			return softwareIdKey;
		} else {
			throw new ApolloDatabaseKeyNotFoundException("No entry in the software_identification table where developer = "
					+ softwareIdentificationId.getSoftwareDeveloper() + " and name = "
					+ softwareIdentificationId.getSoftwareName() + " and version = "
					+ softwareIdentificationId.getSoftwareVersion() + " and service_type = "
					+ softwareIdentificationId.getSoftwareType().toString());

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

	private Map<Integer, ServiceRegistrationRecord> getRegisteredSoftware() throws SQLException, ClassNotFoundException {
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
				srr.getSoftwareIdentification().setSoftwareType(ApolloSoftwareTypeEnum.valueOf(rs.getString(4)));
				srr.setUrl(rs.getString(5));
				srr.getAuthentication().setRequesterId(userIdMap.get(rs.getInt(6)));
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

	public int associateContentWithRunId(int runKey, int dataContentKey, DbContentDataFormatEnum dataFormat, String dataLabel,
			DbContentDataType dataType, String dataSourceSoftware, String dataDestinationSoftware) throws ClassNotFoundException,
			SQLException, ApolloDatabaseKeyNotFoundException {

		int runDataDescriptionId;
		try {
			runDataDescriptionId = getRunDataDescriptionId(dataFormat, dataLabel, dataType, dataSourceSoftware,
					dataDestinationSoftware);
		} catch (ApolloDatabaseKeyNotFoundException e) {
			throw new ApolloDatabaseKeyNotFoundException(
					"Unable to associate data content with run id.  The runDataDescriptionId was not found.  Error was: "
							+ e.getMessage());

		}
		return associateContentWithRunId(runKey, dataContentKey, runDataDescriptionId);
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
			String dataSourceSoftware, String dataDestinationSoftware) throws SQLException, ClassNotFoundException,
			ApolloDatabaseKeyNotFoundException {
		String query = "SELECT v.run_data_description_id FROM run_data_description_view v WHERE "
				+ "v.format = ? AND v.label = ? and v.type = ? and v.source_software = ? and v.destination_software = ?";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.setString(1, dataFormat.toString());
		pstmt.setString(2, dataLabel);
		pstmt.setString(3, dataType.toString());
		pstmt.setString(4, dataSourceSoftware);
		pstmt.setString(5, dataDestinationSoftware);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			throw new ApolloDatabaseKeyNotFoundException("No entry found in run_data_description_view where format = "
					+ dataFormat.toString() + " and label = " + dataLabel + " and type = " + dataType.toString()
					+ " and source_software = " + dataSourceSoftware + " and destination_software = " + dataDestinationSoftware);
		}
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

	public int addSimulationRun(RunSimulationMessage runSimulationMessage) throws ApolloDatabaseRecordNotInsertedException, SQLException, ClassNotFoundException, ApolloDatabaseKeyNotFoundException {
		int softwareKey = getSoftwareIdentificationKey(runSimulationMessage.getSimulatorIdentification());
	
		String query = "INSERT INTO run (md5_hash_of_run_message, software_id, requester_id, last_service_to_be_called) VALUES (?, ?, ?, ?)";
		PreparedStatement pstmt = getConn().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, getMd5(runSimulationMessage));
		pstmt.setInt(2, softwareKey);
		pstmt.setInt(3, 1);
		pstmt.setInt(4, 4); //4 is translator
		pstmt.execute();
		
	
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			throw new ApolloDatabaseRecordNotInsertedException("Record not inserted!");
		}
		
	}



}
