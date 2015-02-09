package edu.pitt.apollo.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.RunIdAndFiles;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordAlreadyExistsException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordNotInsertedException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseStatusNotFoundForRunIdException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseUserPasswordException;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunIdentificationAndLabel;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time:
 * 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 * 
 */
public class ApolloDbUtils extends BaseApolloDbUtils {

	static Map<String, Integer> populationAxisCache = new HashMap<String, Integer>();
	static Map<String, Integer> simulatedPopulationCache = new HashMap<String, Integer>();
	private static final String PRIVILEGED_REQUEST_TOKEN = "priv";
	private static final String USER_ID_TOKEN_SEPERATOR = "\\+";
	private static final boolean APOLLO_DB_AUTO_COMMIT = true;

	// public final int RECORD_NOT_FOUND = -1;
	// public final int PASSWORD_NOT_CORRECT = -2;
	// public final int RECORD_ALREADY_EXISTS = -3;
	// public final int SOFTWARE_ID_RECORD_NOT_FOUND = -4;
	// public final int KEY_NOT_FOUND = -5;
	// public final int RECORD_NOT_INSERTED = -6;
	public enum DbContentDataFormatEnum {

		TEXT, URL, ZIP,
	}

	public enum DbContentDataType {

		SIMULATOR_LOG_FILE, CONFIGURATION_FILE, IMAGE, MOVIE, RUN_SIMULATION_MESSAGE, RUN_VISUALIZATION_MESSAGE, RUN_DATA_SERVICE_MESSAGE,
	}

	public ApolloDbUtils(File databasePropertiesFile) throws IOException {
		super(databasePropertiesFile, APOLLO_DB_AUTO_COMMIT);
	}

	public ApolloDbUtils(InputStream databasePropertiesInputStream)
			throws IOException {
		super(databasePropertiesInputStream, APOLLO_DB_AUTO_COMMIT);
	}

	// public String old_getJSONString(Object obj) {
	// try {
	// ObjectMapper mapper = new ObjectMapper();
	// mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
	// mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// mapper.writeValue(baos, obj);
	//
	// return baos.toString();
	// } catch (IOException ex) {
	// System.err.println("IO Exception JSON encoding and getting bytes from RunSimulationMessage");
	// return null;
	// }
	// }
	public boolean isRunBatch(BigInteger runId) throws ApolloDatabaseException {

		String query = "SELECT simulation_group_id from run WHERE id = "
				+ runId;

		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String simulationGroupId = rs.getString("simulation_group_id");
				if (simulationGroupId == null) {
					return false;
				} else {
					return true;
				}
			} else {
				throw new ApolloDatabaseException("No run exists with id "
						+ runId);
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to authorize user: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to authorize user: "
					+ ex.getMessage());
		}

	}

	public BigInteger getSimulationGroupIdForRun(BigInteger runId)
			throws ApolloDatabaseException {

		String query = "SELECT simulation_group_id from run WHERE id = "
				+ runId;

		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String simulationGroupId = rs.getString("simulation_group_id");
				if (simulationGroupId == null) {
					return null;
				} else {
					return new BigInteger(simulationGroupId);
				}
			} else {
				throw new ApolloDatabaseException("No run exists with id "
						+ runId);
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to authorize user: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to authorize user: "
					+ ex.getMessage());
		}

	}

	public List<BigInteger> getRunIdsForBatch(BigInteger batchRunId)
			throws ApolloDatabaseException {

		String query = "SELECT run_id FROM simulation_group_definition WHERE simulation_group_id "
				+ "in (SELECT simulation_group_id FROM run WHERE id = "
				+ batchRunId + ")";
		List<BigInteger> runIds = new ArrayList<BigInteger>();
		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int runId = rs.getInt("run_id");
				BigInteger bigIntRunId = new BigInteger(Integer.toString(runId));
				runIds.add(bigIntRunId);
			}

			return runIds;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to authorize user: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to authorize user: "
					+ ex.getMessage());
		}
	}

	public static String getJSONString(Object obj) {
		try {

			return getJsonBytes(obj).toString();

		} catch (Exception e) {
			System.err
					.println("Exception encoding to JSON.  Error message was: "
							+ e.getMessage());
			return null;
		}

	}

//	public Object getObjectFromJSON(InputStream contentInputStream, Class clazz) {
////		Map<String, Object> properties = new HashMap<String, Object>(2);
////		properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
////		properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
////		JAXBContext jc;
////		try {
////			jc = (JAXBContext) JAXBContext.newInstance(new Class[]{clazz,
////				ObjectFactory.class}, properties);
////			JAXBUnmarshaller unmarshaller = jc.createUnmarshaller();
////			StreamSource ss = new StreamSource(json);
////			return unmarshaller.unmarshal(ss, clazz).getValue();
////		} catch (Exception e) {
////			System.err.println("Exception encoding " + clazz.getName()
////					+ " to JSON.  Error message was: " + e.getMessage());
////			return null;
////		}
//
////		InputStream contentInputStream = new ByteArrayInputStream(json);
//		Map<String, Object> properties = new HashMap<String, Object>(2);
//		properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
//		properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
//		JAXBContext jc;
//		try {
//			jc = (JAXBContext) JAXBContext
//					.newInstance(new Class[]{clazz,
//						// All ApolloIndexableItems must be listed here
//						DecisionAnalysis.class,
//						Epidemic.class,
//						InfectiousDiseaseDecisionModel.class,
//						Infection.class,
//						InfectiousDisease.class,
//						CensusData.class,
//						Contamination.class,
//						Treatment.class,
//						InfectiousDiseaseScenario.class,
//						Census.class,
//						InfectiousDiseaseControlStrategy.class,
//						ObjectFactory.class}, properties);
//			JAXBUnmarshaller unmarshaller = jc.createUnmarshaller();
//			StreamSource ss = new StreamSource(contentInputStream);
//			return unmarshaller.unmarshal(ss, clazz).getValue();
//		} catch (Exception e) {
//			System.err.println("Exception encoding " + clazz.getName() + " to JSON.  Error message was: "
//					+ e.getMessage());
//			return null;
//		}
//	}
	public RunSimulationMessage getRunSimulationMessageForRun(BigInteger runId)
			throws ApolloDatabaseException, IOException {

		Map<String, ByteArrayOutputStream> contentForRun = getDataContentForSoftware(
				runId, 0, 1);
		for (String name : contentForRun.keySet()) {
			if (name.equals("run_simulation_message.json")) {
				InputStream contentInputStream = new ByteArrayInputStream(
						contentForRun.get(name).toByteArray());

				return (RunSimulationMessage) getObjectFromJson(
						contentInputStream, RunSimulationMessage.class);
			}
		}

		throw new ApolloDatabaseException(
				"Could not find run_simulation_message.json content associated with run ID"
				+ runId);
	}

	public GetOutputFilesURLsMessage getGetOutputFilesURLsMessageForRun(
			BigInteger runId) throws ApolloDatabaseException {
		Map<String, ByteArrayOutputStream> contentForRun = getDataContentForSoftware(runId);
		for (String name : contentForRun.keySet()) {
			if (name.equals("run_data_service_message.json")) {
				InputStream contentInputStream = new ByteArrayInputStream(
						contentForRun.get(name).toByteArray());

				return (GetOutputFilesURLsMessage) getObjectFromJson(
						contentInputStream, GetOutputFilesURLsMessage.class);
			}
		}

		throw new ApolloDatabaseException(
				"Could not find run_data_service_message.json content associated with run ID"
				+ runId);
	}

	public GetOutputFilesURLAsZipMessage getGetOutputFilesURLAsZipMessageForRun(
			BigInteger runId) throws ApolloDatabaseException {
		Map<String, ByteArrayOutputStream> contentForRun = getDataContentForSoftware(runId);
		for (String name : contentForRun.keySet()) {
			if (name.equals("run_data_service_message.json")) {
				InputStream contentInputStream = new ByteArrayInputStream(
						contentForRun.get(name).toByteArray());

				return (GetOutputFilesURLAsZipMessage) getObjectFromJson(
						contentInputStream, GetOutputFilesURLAsZipMessage.class);
			}
		}

		throw new ApolloDatabaseException(
				"Could not find run_data_service_message.json content associated with run ID"
				+ runId);
	}

	public GetAllOutputFilesURLAsZipMessage getGetAllOutputFilesURLAsZipMessageForRun(
			BigInteger runId) throws ApolloDatabaseException {
		Map<String, ByteArrayOutputStream> contentForRun = getDataContentForSoftware(runId);
		for (String name : contentForRun.keySet()) {
			if (name.equals("run_data_service_message.json")) {
				InputStream contentInputStream = new ByteArrayInputStream(
						contentForRun.get(name).toByteArray());

				return (GetAllOutputFilesURLAsZipMessage) getObjectFromJson(
						contentInputStream, GetAllOutputFilesURLAsZipMessage.class);
			}
		}

		throw new ApolloDatabaseException(
				"Could not find run_data_service_message.json content associated with run ID"
				+ runId);
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
//	protected ByteArrayOutputStream getJsonBytes(Object obj) {
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
//			mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
//					false);
//			mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			mapper.writeValue(baos, obj);
//
//			return baos;
//		} catch (IOException ex) {
//			System.err
//					.println("IO Exception JSON encoding and getting bytes from RunSimulationMessage: "
//							+ ex.getMessage());
//			return null;
//		}
//	}
	private int getRoleDescriptionId(int softwareId,
			boolean requestToRunSoftware, boolean requestPrivileged)
			throws ApolloDatabaseException {
		String query = "SELECT role_id FROM role_description WHERE software_id = "
				+ softwareId
				+ " AND "
				+ "can_run_software = "
				+ requestToRunSoftware
				+ " AND allow_privileged_request = "
				+ requestPrivileged;

		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int roleId = rs.getInt("role_id");
				return roleId;
			} else {
				throw new ApolloDatabaseException(
						"No role exists with software_id = " + softwareId
						+ ", can_run_software = "
						+ requestToRunSoftware
						+ ", and allow_privileged_request = "
						+ requestPrivileged);
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to authorize user: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to authorize user: "
					+ ex.getMessage());
		}
	}

	public boolean authorizeUser(int userId, int softwareId,
			boolean requestToRunSoftware, boolean requestPrivileged)
			throws ApolloDatabaseException {

		int roleId = getRoleDescriptionId(softwareId, requestToRunSoftware,
				requestPrivileged);
		return authorizeUser(userId, roleId);
	}

	public boolean authorizeUser(int userId,
			SoftwareIdentification softwareIdentification,
			boolean requestToRunSoftware, boolean requestPrivileged)
			throws ApolloDatabaseException {

		int softwareId = getSoftwareIdentificationKey(softwareIdentification);
		return authorizeUser(userId, softwareId, requestToRunSoftware,
				requestPrivileged);
	}

	public boolean authorizeUser(Authentication authentication,
			SoftwareIdentification softwareIdentification,
			boolean requestToRunSoftware)
			throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {

		String userId = authentication.getRequesterId();
		String userPassword = authentication.getRequesterPassword();

		String[] userIdTokens = parseUserId(userId);
		String userName = userIdTokens[0];
		boolean requestPrivileged = false;
		if (requestToRunSoftware) {
			if (userIdTokens.length > 1) {
				for (String token : userIdTokens) {
					if (token.equals(PRIVILEGED_REQUEST_TOKEN)) {
						requestPrivileged = true;
						break;
					}
				}
			}
		}

		int userKey = getUserKey(userName, userPassword);
		return authorizeUser(userKey, softwareIdentification,
				requestToRunSoftware, requestPrivileged);

	}

	private void addUserRole(int userId, int roleId)
			throws ApolloDatabaseException {

		boolean roleAlreadyExists = authorizeUser(userId, roleId);
		if (!roleAlreadyExists) {
			try {
				String query = "INSERT INTO user_roles VALUES(" + userId + ","
						+ roleId + ")";
				PreparedStatement pstmt = getConn().prepareStatement(query);
				pstmt.executeUpdate();
			} catch (ClassNotFoundException ex) {
				throw new ApolloDatabaseException(
						"ClassNotFoundException attempting to add user role: "
						+ ex.getMessage());
			} catch (SQLException ex) {
				throw new ApolloDatabaseException(
						"SQLException attempting to add user role: "
						+ ex.getMessage());
			}
		}
	}

	public void addUserRole(String userId, String userPassword,
			SoftwareIdentification softwareIdentification,
			boolean canRunSoftware, boolean canRequestPrivileged)
			throws ApolloDatabaseException {

		int softwareId = getSoftwareIdentificationKey(softwareIdentification);
		int roleId = getRoleDescriptionId(softwareId, canRunSoftware,
				canRequestPrivileged);
		int userKey = getUserKey(userId, userPassword);
		addUserRole(userKey, roleId);
	}

	public int getSoftwareIdentificationKey(
			SoftwareIdentification softwareIdentification)
			throws ApolloDatabaseException {

		String query = "SELECT id FROM software_identification where developer = ? and name = ? and version = ? and service_type = ?";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setString(1, softwareIdentification.getSoftwareDeveloper());
			pstmt.setString(2, softwareIdentification.getSoftwareName());
			pstmt.setString(3, softwareIdentification.getSoftwareVersion());
			pstmt.setString(4, softwareIdentification.getSoftwareType()
					.toString());
			ResultSet rs = pstmt.executeQuery();
			int softwareIdKey = -1;
			if (rs.next()) {
				softwareIdKey = rs.getInt(1);
				return softwareIdKey;
			} else {
				throw new ApolloDatabaseKeyNotFoundException(
						"No entry in the software_identification table where developer = "
						+ softwareIdentification.getSoftwareDeveloper()
						+ " and name = "
						+ softwareIdentification.getSoftwareName()
						+ " and version = "
						+ softwareIdentification.getSoftwareVersion()
						+ " and service_type = "
						+ softwareIdentification.getSoftwareType()
						.toString());

			}
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ApolloDatabaseKeyNotFoundException attempting to get software identification key: "
					+ ex.getMessage());
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to get software identification key: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get software identification key: "
					+ ex.getMessage());
		}
	}

	public int getRunKey(RunSimulationMessage runSimulationMessage)
			throws SQLException, ClassNotFoundException,
			ApolloDatabaseException {
		Authentication auth = runSimulationMessage.getAuthentication();

		int userKey = getUserKey(auth.getRequesterId(),
				auth.getRequesterPassword());
		int softwareKey = getSoftwareIdentificationKey(runSimulationMessage
				.getSimulatorIdentification());

		String hash = getMd5(runSimulationMessage);

		String query = "SELECT id FROM run WHERE md5_hash_of_run_message = ?";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.setString(1, hash);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			query = "INSERT INTO run (requester_id, software_id, md5_hash_of_run_message) VALUES (?,?,?)";
			pstmt = getConn().prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, userKey);
			pstmt.setInt(2, softwareKey);
			pstmt.setString(3, hash);
			pstmt.execute();
			rs = pstmt.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		}
	}

	public int addRole(SoftwareIdentification softwareIdentification,
			boolean canRun, boolean canViewCache) throws SQLException,
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

		PreparedStatement pstmt = getConn().prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS);
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
				throw new ApolloDatabaseException(
						"No primary key returned from addRole().");
			}
		} finally {
			pstmt.close();
		}

	}

	private int getRoleKey(int softwareIdKey, boolean canRun,
			boolean canViewCache) throws SQLException, ClassNotFoundException,
			ApolloDatabaseKeyNotFoundException {
		if (softwareIdKey >= 1) {
			// software statusId found...now lets see if this specific role
			// exists...
			String query = "SELECT id FROM roles WHERE software_id = ? AND can_run = ? AND can_view_cached_results = ?";
			PreparedStatement pstmt = getConn().prepareStatement(query);
			try {
				pstmt.setInt(1, softwareIdKey);
				pstmt.setBoolean(2, canRun);
				pstmt.setBoolean(3, canViewCache);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					throw new ApolloDatabaseKeyNotFoundException(
							"No entry found in the roles table where software_id = "
							+ softwareIdKey + " and can_run = "
							+ canRun + " and can_view_cached_resuls = "
							+ canViewCache);
				} else {
					return rs.getInt(1);
				}
			} finally {
				pstmt.close();
			}
		} else {
			throw new ApolloDatabaseKeyNotFoundException(
					"getRoleKey() called with invalid softwareIdKey: "
					+ softwareIdKey);
		}
	}

	public Map<Integer, ServiceRegistrationRecord> getRegisteredSoftware()
			throws SQLException, ClassNotFoundException {
		Map<Integer, ServiceRegistrationRecord> result = new HashMap<Integer, ServiceRegistrationRecord>();

		// get all of the users that are an admin of a software
		String query = "SELECT u.id, u.requester_id FROM users u, software_identification s WHERE "
				+ "s.admin_id = u.id";

		Map<Integer, String> userIdMap = new HashMap<Integer, String>();

		PreparedStatement pstmt = getConn().prepareStatement(query);
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt(1);
				String user_id = rs.getString(2);
				userIdMap.put(userId, user_id);
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
				srr.getSoftwareIdentification().setSoftwareDeveloper(
						rs.getString(2));
				srr.getSoftwareIdentification()
						.setSoftwareName(rs.getString(3));
				srr.getSoftwareIdentification().setSoftwareVersion(
						rs.getString(4));
				srr.getSoftwareIdentification().setSoftwareType(
						ApolloSoftwareTypeEnum.fromValue(rs.getString(5)));
				srr.setUrl(rs.getString(6));
				srr.getAuthentication().setRequesterId(
						userIdMap.get(rs.getInt(7)));
				srr.getAuthentication().setRequesterPassword("");
				result.put(id, srr);
			}
		} finally {
			pstmt.close();
		}
		return result;

	}

	public int getUserKey(String userId, String userPassword)
			throws ApolloDatabaseUserPasswordException,
			ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {

		try {
			String query = "SELECT id, hash_of_user_password_and_salt, salt FROM users WHERE requester_id = ?";
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String storedSaltedPasswordHash = rs
						.getString("hash_of_user_password_and_salt");
				String salt = rs.getString("salt");
				String saltedPasswordHash = getHashOfUserPasswordAndSalt(
						userPassword, salt);
				if (saltedPasswordHash.equals(storedSaltedPasswordHash)) {
					return rs.getInt("id");
				} else {
					throw new ApolloDatabaseUserPasswordException(
							"Incorrect password");
				}
			} else {
				throw new ApolloDatabaseKeyNotFoundException(
						"No entry in the users table where user_id = " + userId);
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to get user key: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get user key: "
					+ ex.getMessage());
		}
	}

	// // user key doesn't exist
	@Override
	public int addUser(String userId, String userPassword, String userEmail)
			throws ApolloDatabaseRecordAlreadyExistsException,
			ApolloDatabaseUserPasswordException, ApolloDatabaseException {
		// check authorization?!

		try {
			getUserKey(userId, userPassword);
			throw new ApolloDatabaseRecordAlreadyExistsException("User "
					+ userId + " already exists in the database.");
		} catch (ApolloDatabaseKeyNotFoundException e) {
			// good this means the user doesn't already exist
		} catch (ApolloDatabaseUserPasswordException e) {
			throw new ApolloDatabaseUserPasswordException(
					"A user with userID \"" + userId + "\" already exists.");
		}

		String query = "INSERT INTO users (requester_id,hash_of_user_password_and_salt,salt, user_email) VALUES (?,?,?,?)";
		String salt = getSaltForPassword();
		String saltedPasswordHash = getHashOfUserPasswordAndSalt(userPassword,
				salt);
		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, saltedPasswordHash);
			pstmt.setString(3, salt);
			pstmt.setString(4, userEmail);
			pstmt.execute();

			query = "SELECT LAST_INSERT_ID()";
			pstmt = getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotfoundException attempting to add user: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to add user: " + ex.getMessage());
		}
	}

	public boolean authenticateUser(Authentication authentication)
			throws ApolloDatabaseUserPasswordException, ApolloDatabaseException {

		String userId = authentication.getRequesterId();
		String userPassword = authentication.getRequesterPassword();

		String[] userIdTokens = parseUserId(userId);
		String userName = userIdTokens[0];

		try {
			getUserKey(userName, userPassword);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			return false;
		}

		return true;
	}

	protected String[] parseUserId(String userId) {
		return userId.split(USER_ID_TOKEN_SEPERATOR);
	}

	protected boolean authorizeUser(int userId, int roleId)
			throws ApolloDatabaseException {

		String query = "SELECT * FROM user_roles where user_id = " + userId
				+ " AND role_id = " + roleId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to authorize user: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to authorize user: "
					+ ex.getMessage());
		}
	}

	public void deleteUser(String userId, String userPassword)
			throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {

		int userKey = getUserKey(userId, userPassword);
		try {
			String query = "DELETE FROM user_roles WHERE user_id = " + userKey;
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.executeUpdate();

			query = "DELETE FROM users WHERE id = " + userKey;
			pstmt = getConn().prepareStatement(query);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to delete user: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to delete user: "
					+ ex.getMessage());
		}
	}

	// okay so user A can see...?
	// private List<Role> getUserRoles(int userId) throws SQLException,
	// ClassNotFoundException {
	// // List<Role> roles = new ArrayList<Role>();
	// //
	// // String query =
	// //
	// "SELECT r.software_id, r.can_run, r.can_view_cached_results from roles r, user_roles ur where "
	// // + "r.statusId = ur.role_id AND ur.user_id = ?";
	// // PreparedStatement pstmt = getConn().prepareStatement(query);
	// // try {
	// // pstmt.setInt(1, userId);
	// // ResultSet rs = pstmt.executeQuery();
	// // while (rs.next()) {
	// // Role r = new Role();
	// // r.setSoftwareIdentification(getSoftwareIdentification(rs.getInt(1)));
	// // r.setCanRun(rs.getBoolean(2));
	// // r.setCanViewCachedResults(rs.getBoolean(3));
	// // roles.add(r);
	// // }
	// // } finally {
	// // pstmt.close();
	// // }
	// // return roles;
	// return null;
	// }
	// private int addUserRole(int userId, int roleId) {
	//
	// return 0;
	// }
	public int addTextDataContent(InputStream content, int md5CollisionId)
			throws SQLException, ClassNotFoundException, IOException,
			ApolloDatabaseException {
		return addTextDataContent(IOUtils.toString(content));
	}

	public int addTextDataContent(String content) throws SQLException,
			ClassNotFoundException, ApolloDatabaseException {

		String md5 = DigestUtils.md5Hex(content);
		// is the data already in the table?
		String query = "SELECT id, text_content FROM run_data_content where md5_hash_of_content = ?";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.setString(1, md5);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			// no need to store the data twice

			// let's not be too hasty, we need to see if it's a TRUE cache hit,
			// so compare the "content" parameter, with rs.getString(2)
			// if it's a FALSE cache hit..we need to insert the new content,
			// with an incremented md5_collision_id
			if (rs.getString(2).equals(content)) {
				// this is a true cache hit, so return the ID
				return rs.getInt(1);
			}
		}

		// if here then no cache hit
		// false cache hit, so insert the new content with an incremented
		// collision statusId
		int highestMD5CollisionId = getHighestMD5CollisionIdForRunDataContent(content);
		int md5CollisionId = highestMD5CollisionId + 1; // increment the ID to
		// insert with the new
		// content

		pstmt.close();
		// store it
		query = "INSERT INTO run_data_content (text_content, md5_hash_of_content, md5_collision_id) values (?,?,?)";
		pstmt = getConn().prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS);
		try {
			pstmt.setString(1, content);
			pstmt.setString(2, md5);
			pstmt.setInt(3, md5CollisionId);
			pstmt.execute();
			rs = pstmt.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);

		} finally {
			pstmt.close();
		}

	}

	public int getHighestMD5CollisionIdForRun(Object message)
			throws ApolloDatabaseException {
		return getHighestMD5CollisionIdForTable("run",
				"md5_hash_of_run_message", getMd5(message));
	}

	public int getHighestMD5CollisionIdForRunDataContent(String content)
			throws ApolloDatabaseException {
		return getHighestMD5CollisionIdForTable("run_data_content",
				"md5_hash_of_content", getMd5FromString(content));
	}

	private int getHighestMD5CollisionIdForTable(String tableName,
			String md5ColumnName, String md5Hash)
			throws ApolloDatabaseException {
		String query = "SELECT md5_collision_id FROM " + tableName + " where "
				+ md5ColumnName + " = ?";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setString(1, md5Hash);
			ResultSet rs = pstmt.executeQuery();

			int highestMd5CollisionId = 0; // if no runs exist, return 0
			while (rs.next()) {
				int collisionId = rs.getInt(1);
				if (collisionId > highestMd5CollisionId) {
					highestMd5CollisionId = collisionId;
				}
			}

			return highestMd5CollisionId;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to get highest MD5 collision ID for table "
					+ tableName + " and hash " + md5Hash + ": "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get highest MD5 collision ID for table "
					+ tableName + " and hash " + md5Hash + ": "
					+ ex.getMessage());
		}
	}

	public Map<String, ByteArrayOutputStream> getDataContentForSoftware(
			BigInteger runKey, int sourceSoftwareIdKey,
			int destinationSoftwareIdKey) throws ApolloDatabaseException {
		Map<String, ByteArrayOutputStream> result = new HashMap<String, ByteArrayOutputStream>();

		String query = "SELECT " + "rddv.label, " + "rdc.text_content "
				+ "FROM " + "run_data_content rdc, " + "run_data rd, "
				+ "run_data_description_view rddv " + "WHERE "
				+ "rd.content_id = rdc.id AND " + "rd.run_id = ? AND "
				+ "rddv.run_data_description_id = rd.description_id AND "
				+ "rddv.source_software = ? AND "
				+ "rddv.destination_software = ?";
		PreparedStatement pstmt = null;
		try {
			try {
				pstmt = getConn().prepareStatement(query);
				pstmt.setInt(1, runKey.intValue());
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
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to get data content for software for run ID "
					+ runKey + ": " + ex.getMessage());
		} catch (IOException ex) {
			throw new ApolloDatabaseException(
					"IOException attempting to get data content for software for run ID "
					+ runKey + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get data content for software for run ID "
					+ runKey + ": " + ex.getMessage());
		}

		return result;

	}

	public Map<String, ByteArrayOutputStream> getDataContentForSoftware(
			BigInteger runKey) throws ApolloDatabaseException {
		Map<String, ByteArrayOutputStream> result = new HashMap<String, ByteArrayOutputStream>();

		String query = "SELECT " + "rddv.label, " + "rdc.text_content "
				+ "FROM " + "run_data_content rdc, " + "run_data rd, "
				+ "run_data_description_view rddv " + "WHERE "
				+ "rd.content_id = rdc.id AND " + "rd.run_id = ? AND "
				+ "rddv.run_data_description_id = rd.description_id";
		PreparedStatement pstmt = null;
		try {
			try {
				pstmt = getConn().prepareStatement(query);
				pstmt.setInt(1, runKey.intValue());
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
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to get data content for software for run ID "
					+ runKey + ": " + ex.getMessage());
		} catch (IOException ex) {
			throw new ApolloDatabaseException(
					"IOException attempting to get data content for software for run ID "
					+ runKey + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get data content for software for run ID "
					+ runKey + ": " + ex.getMessage());
		}

		return result;
	}

	public int getSoftwareIdForRunId(BigInteger runId)
			throws ApolloDatabaseException {
		String query = "SELECT software_id FROM run WHERE id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = getConn().prepareStatement(query);
			pstmt.setInt(1, runId.intValue());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new ApolloDatabaseKeyNotFoundException(
						"No software_id key was found for run_id " + runId);
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to get software_id for run_id "
					+ runId);
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get software_id for run_id "
					+ runId);
		}

	}

	public Map<String, ByteArrayOutputStream> getConfigFilesForSimulation(
			BigInteger runKey, int sourceSoftwareIdKey)
			throws ApolloDatabaseException {

		// First get ID of simulator...then feet it to param 3 below
		// destinationSoftwareIdKey = select software_id from run where run_id =
		// runKey
		int destinationKey = getSoftwareIdForRunId(runKey);

		return getDataContentForSoftware(runKey, sourceSoftwareIdKey,
				destinationKey);
	}

	public int associateContentWithRunId(BigInteger runKey, int dataContentKey,
			int runDataDescriptionId) throws ClassNotFoundException,
			SQLException, ApolloDatabaseKeyNotFoundException {

		if (runDataDescriptionId >= 0) {
			String query = "SELECT id FROM run_data WHERE run_id = ? AND description_id = ? and content_id = ?";
			PreparedStatement pstmt = null;
			try {
				pstmt = getConn().prepareStatement(query);
				pstmt.setInt(1, runKey.intValue());
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
				pstmt = getConn().prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, runKey.intValue());

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
			throw new ApolloDatabaseKeyNotFoundException(
					"associateContentWithRunId() called with an invalid key: "
					+ runKey);
		}

	}

	public int getRunDataDescriptionId(DbContentDataFormatEnum dataFormat,
			String dataLabel, DbContentDataType dataType,
			int dataSourceSoftwareIdKey, int dataDestinationSoftwareIdKey)
			throws SQLException, ClassNotFoundException,
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
			throw new ApolloDatabaseKeyNotFoundException(
					"No entry found in run_data_description_view where format = "
					+ dataFormat.toString() + " and label = "
					+ dataLabel + " and type = " + dataType.toString()
					+ " and source_software = "
					+ dataSourceSoftwareIdKey
					+ " and destination_software = "
					+ dataDestinationSoftwareIdKey);
		}
	}

	public int getRunDataDescriptionId(DbContentDataFormatEnum dataFormat,
			String dataLabel, DbContentDataType dataType,
			SoftwareIdentification dataSourceSoftwareIdentification,
			SoftwareIdentification dataDestinationSoftwareIdentification)
			throws ApolloDatabaseException, SQLException,
			ClassNotFoundException {
		return getRunDataDescriptionId(
				dataFormat,
				dataLabel,
				dataType,
				getSoftwareIdentificationKey(dataSourceSoftwareIdentification),
				getSoftwareIdentificationKey(dataDestinationSoftwareIdentification));
	}

	public int addRunDataDescription(String description, String dataFormat,
			String dataLabel, String dataType, String dataSourceSoftware,
			String dataDestinationSoftware) throws SQLException,
			ClassNotFoundException {
		int runDataDescriptionKey = -1;
		String query = "INSERT INTO run_data_description SET label = ?";
		PreparedStatement pstmt = getConn().prepareStatement(query,
				Statement.NO_GENERATED_KEYS);

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
	}

	public SoftwareIdentification getSoftwareIdentification(int i)
			throws ApolloDatabaseKeyNotFoundException, SQLException,
			ClassNotFoundException {
		String query = "SELECT developer, name, version, service_type FROM software_identification WHERE "
				+ "id = ?";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.setInt(1, i);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			SoftwareIdentification softwareIdentification = new SoftwareIdentification();
			softwareIdentification.setSoftwareDeveloper(rs.getString(1));
			softwareIdentification.setSoftwareName(rs.getString(2));
			softwareIdentification.setSoftwareVersion(rs.getString(3));
			softwareIdentification.setSoftwareType(ApolloSoftwareTypeEnum
					.fromValue(rs.getString(4)));
			return softwareIdentification;
		} else {
			throw new ApolloDatabaseKeyNotFoundException(
					"No entry found in software_identification where id = " + i);
		}
	}

	public SoftwareIdentification getSoftwareIdentificationForRun(
			BigInteger runId) throws ClassNotFoundException, SQLException,
			ApolloDatabaseKeyNotFoundException {

		String query = "SELECT software_id from run WHERE " + "id = ?";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.setInt(1, runId.intValue());
		ResultSet rs = pstmt.executeQuery();
		int softwareId;
		if (rs.next()) {
			softwareId = rs.getInt("software_id");
		} else {
			throw new ApolloDatabaseKeyNotFoundException(
					"No entry found in run where id = " + runId);
		}

		return getSoftwareIdentification(softwareId);
	}

	public int getSoftwareIdentificationKeyForRun(BigInteger runId)
			throws ApolloDatabaseException {

		try {
			String query = "SELECT software_id from run WHERE " + "id = ?";
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setInt(1, runId.intValue());
			ResultSet rs = pstmt.executeQuery();
			int softwareId;
			if (rs.next()) {
				softwareId = rs.getInt("software_id");
				return softwareId;
			} else {
				throw new ApolloDatabaseKeyNotFoundException(
						"No entry found in run where id = " + runId);
			}
		} catch (SQLException e) {
			throw new ApolloDatabaseException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new ApolloDatabaseException(e.getMessage());
		}
	}

	public String getUrlForSoftwareIdentification(
			SoftwareIdentification softwareIdentification) throws SQLException,
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
			throw new ApolloDatabaseKeyNotFoundException(
					"Unable to get wsdl_url from software_identification where developer = "
					+ softwareIdentification.getSoftwareDeveloper()
					+ " and name = "
					+ softwareIdentification.getSoftwareName()
					+ " and version = "
					+ softwareIdentification.getSoftwareVersion()
					+ " and service_type = "
					+ softwareIdentification.getSoftwareType()
					.toString());
		}

	}

	public String getUrlForSoftwareIdentification(int softwareIdentificaitonKey)
			throws ApolloDatabaseKeyNotFoundException, SQLException,
			ClassNotFoundException {
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

	public BigInteger[] addDataServiceRun(GetAllOutputFilesURLAsZipMessage message, int md5CollisionId,
			Authentication authentication,
			SoftwareIdentification dataServiceSoftwareId)
			throws ApolloDatabaseException {

//		List<RunIdentificationAndLabel> runIds = new ArrayList<RunIdentificationAndLabel>();
//		RunIdentificationAndLabel runIdAndLabel = new RunIdentificationAndLabel();
//		runIdAndLabel.setRunIdentification(message.getRunId());
//		runIds.add(runIdAndLabel);
//
		return addDataServiceRunForAllMessageTypes(message, md5CollisionId, authentication, dataServiceSoftwareId);
	}
//
	public BigInteger[] addDataServiceRun(GetOutputFilesURLsMessage message, int md5CollisionId,
			Authentication authentication,
			SoftwareIdentification dataServiceSoftwareId)
			throws ApolloDatabaseException {
//
//		List<RunIdentificationAndLabel> runIds = new ArrayList<RunIdentificationAndLabel>();
//		List<RunIdAndFiles> runIdsAndFilesList = message.getRunIdsAndFiles();
//		for (RunIdAndFiles runIdAndFiles : runIdsAndFilesList) {
//			RunIdentificationAndLabel runIdAndLabel = new RunIdentificationAndLabel();
//			runIdAndLabel.setRunIdentification(runIdAndFiles.getRunId());
//			runIds.add(runIdAndLabel);
//		}
//
		return addDataServiceRunForAllMessageTypes(message, md5CollisionId, authentication, dataServiceSoftwareId);
	}
	public BigInteger[] addDataServiceRun(GetOutputFilesURLAsZipMessage message, int md5CollisionId,
			Authentication authentication,
			SoftwareIdentification dataServiceSoftwareId)
			throws ApolloDatabaseException {
//
//		List<RunIdentificationAndLabel> runIds = new ArrayList<RunIdentificationAndLabel>();
//		List<RunIdAndFiles> runIdsAndFilesList = message.getRunIdsAndFiles();
//		for (RunIdAndFiles runIdAndFiles : runIdsAndFilesList) {
//			RunIdentificationAndLabel runIdAndLabel = new RunIdentificationAndLabel();
//			runIdAndLabel.setRunIdentification(runIdAndFiles.getRunId());
//			runIds.add(runIdAndLabel);
//		}
//
		return addDataServiceRunForAllMessageTypes(message, md5CollisionId, authentication, dataServiceSoftwareId);
	}
	private BigInteger[] addDataServiceRunForAllMessageTypes(Object message, int md5CollisionId,
			Authentication authentication,
			SoftwareIdentification dataServiceSoftwareId)
			throws ApolloDatabaseException {

		String userName = authentication.getRequesterId();
		String password = authentication.getRequesterPassword();

		String[] userIdTokens = parseUserId(userName);
		userName = userIdTokens[0];

		int softwareKey = getSoftwareIdentificationKey(dataServiceSoftwareId);
		int userKey = getUserKey(userName, password);

		try {
			BigInteger simulationGroupId = getNewSimulationGroupId();

			String query = "INSERT INTO run (md5_hash_of_run_message, software_id, requester_id, last_service_to_be_called, simulation_group_id, md5_collision_id) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = getConn().prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, getMd5(message));
			pstmt.setInt(2, softwareKey);
			pstmt.setInt(3, userKey);
			pstmt.setInt(4, 1);
			pstmt.setInt(5, simulationGroupId.intValue());
			pstmt.setInt(6, md5CollisionId);
			pstmt.execute();

			ResultSet rs = pstmt.getGeneratedKeys();
			int runId;
			if (rs.next()) {
				runId = rs.getInt(1);
			} else {
				throw new ApolloDatabaseRecordNotInsertedException(
						"Record not inserted!");
			}

			// ALSO NEED TO ADD serialized run data service message (JSON) to
			// run_data_content table...
			// use insertDataContentForRun for this
			int dataContentKey = addTextDataContent(getJSONString(message));
			int runDataDescriptionId = getRunDataDescriptionId(
					DbContentDataFormatEnum.TEXT,
					"run_data_service_message.json",
					DbContentDataType.RUN_DATA_SERVICE_MESSAGE, 0,
					getSoftwareIdentificationKey(dataServiceSoftwareId));
			// int runDataId = the following line returns the runDataId, but
			// it's not used at this point.
			associateContentWithRunId(new BigInteger(String.valueOf(runId)),
					dataContentKey, runDataDescriptionId);

			List<RunIdentificationAndLabel> runIdsForDataService = new ArrayList<RunIdentificationAndLabel>();
			RunIdentificationAndLabel runIdentificationAndLabel = new RunIdentificationAndLabel();
			runIdentificationAndLabel.setRunIdentification(new BigInteger(Integer.toString(runId)));
			runIdentificationAndLabel
					.setRunLabel("Individual simulation set id: "
							+ simulationGroupId.toString());
			runIdsForDataService = new ArrayList<RunIdentificationAndLabel>();
			runIdsForDataService.add(runIdentificationAndLabel);
			addRunIdsToSimulationGroup(simulationGroupId, runIdsForDataService);

			BigInteger[] runIdSimulationGroupId = new BigInteger[2];
			runIdSimulationGroupId[0] = new BigInteger(Integer.toString(runId));
			runIdSimulationGroupId[1] = simulationGroupId;
			return runIdSimulationGroupId;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to add simulation run: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to add simulation run: "
					+ ex.getMessage());
		}
	}

	public BigInteger[] addSimulationRun(Object runMessage,
			BigInteger memberOfSimulationGroupIdOrNull, int md5CollisionId,
			SoftwareIdentification sourceSoftwareForRunSimulationMessage,
			SoftwareIdentification destinationSoftwareForRunSimulationMessage,
			Authentication authentication) throws ApolloDatabaseException {

		String userName = authentication.getRequesterId();
		String password = authentication.getRequesterPassword();

		String[] userIdTokens = parseUserId(userName);
		userName = userIdTokens[0];

		int softwareKey = getSoftwareIdentificationKey(sourceSoftwareForRunSimulationMessage);
		int userKey = getUserKey(userName, password);

		BigInteger simulationGroupId = null;
		String additionalInsertField = "";
		String additionalParamHolder = "";
		BigInteger[] runIdSimulationGroupId = new BigInteger[2];

		try {
			simulationGroupId = getNewSimulationGroupId();
			runIdSimulationGroupId[1] = simulationGroupId;
			additionalInsertField = ", simulation_group_id";
			additionalParamHolder = ",?";

			String query = "INSERT INTO run (md5_hash_of_run_message, software_id, requester_id, last_service_to_be_called, md5_collision_id "
					+ additionalInsertField
					+ ") VALUES (?, ?, ?, ?, ? "
					+ additionalParamHolder + ")";
			PreparedStatement pstmt = getConn().prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, getMd5(runMessage));
			pstmt.setInt(2, softwareKey);
			pstmt.setInt(3, userKey);
			pstmt.setInt(4, 1);
			pstmt.setInt(5, md5CollisionId);
			pstmt.setLong(6, simulationGroupId.longValue());

			pstmt.execute();

			ResultSet rs = pstmt.getGeneratedKeys();
			BigInteger runId;
			if (rs.next()) {
				runId = new BigInteger(rs.getString(1));
			} else {
				throw new ApolloDatabaseRecordNotInsertedException(
						"Record not inserted!");
			}
			if (memberOfSimulationGroupIdOrNull != null) {
				RunIdentificationAndLabel runIdentificationAndLabel = new RunIdentificationAndLabel();
				runIdentificationAndLabel.setRunIdentification(runId);
				runIdentificationAndLabel.setRunLabel("Member of batch run: "
						+ memberOfSimulationGroupIdOrNull.toString());
				List<RunIdentificationAndLabel> runIdentificationAndLabels = new ArrayList<RunIdentificationAndLabel>();
				runIdentificationAndLabels.add(runIdentificationAndLabel);
				addRunIdsToSimulationGroup(memberOfSimulationGroupIdOrNull,
						runIdentificationAndLabels);
				addRunIdsToSimulationGroup(simulationGroupId,
						runIdentificationAndLabels);

			} else {
				RunIdentificationAndLabel runIdentificationAndLabel = new RunIdentificationAndLabel();
				runIdentificationAndLabel.setRunIdentification(runId);
				runIdentificationAndLabel
						.setRunLabel("Individual simulation set id: "
								+ simulationGroupId.toString());
				List<RunIdentificationAndLabel> runIdentificationAndLabels = new ArrayList<RunIdentificationAndLabel>();
				runIdentificationAndLabels.add(runIdentificationAndLabel);
				if (!(runMessage instanceof RunSimulationsMessage)) {
					//don't make a batch run a member of a siulation group
					addRunIdsToSimulationGroup(simulationGroupId,
							runIdentificationAndLabels);
				}
			}

			// ALSO NEED TO ADD serialized runSimulationMessage(JSON) to
			// run_data_content table...
			// use insertDataContentForRun for this
			int dataContentKey = addTextDataContent(getJSONString(runMessage));
			int runDataDescriptionId = getRunDataDescriptionId(
					DbContentDataFormatEnum.TEXT,
					"run_simulation_message.json",
					DbContentDataType.RUN_SIMULATION_MESSAGE,
					0,
					getSoftwareIdentificationKey(destinationSoftwareForRunSimulationMessage));
			// int runDataId = the following line returns the runDataId, but
			// it's not used at this point.
			associateContentWithRunId(new BigInteger(String.valueOf(runId)),
					dataContentKey, runDataDescriptionId);

			runIdSimulationGroupId[0] = runId;
			if (runIdSimulationGroupId.length == 2) {
				runIdSimulationGroupId[1] = simulationGroupId;
			}

			updateStatusOfRun(runId,
					MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE,
					"Adding config information to the database for runId: "
					+ runId.toString());

			return runIdSimulationGroupId;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to add simulation run: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to add simulation run: "
					+ ex.getMessage());
		}
	}

	private List<BigInteger> getRunIdsAssociatedWithHash(String hash,
			int softwareKey) throws ApolloDatabaseException {
		String query = "SELECT id FROM run WHERE md5_hash_of_run_message = ? AND software_id = ?";

		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setString(1, hash);
			pstmt.setInt(2, softwareKey);
			// pstmt.setInt(3, 1);
			ResultSet rs = pstmt.executeQuery();

			List<BigInteger> runIds = new ArrayList<BigInteger>();
			while (rs.next()) {
				runIds.add(new BigInteger(String.valueOf(rs.getInt(1))));
			}

			// if (runIds.isEmpty()) {
			// throw new ApolloDatabaseKeyNotFoundException(
			// "No statusId found for simulation run where md5_hash_of_run_message = "
			// + md5Hash + " and softare_id = " + softwareKey
			// + " and user_id = 1");
			// }
			return runIds;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to get run IDs associated with hash "
					+ hash + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get run IDs associated with hash "
					+ hash + ": " + ex.getMessage());
		}
	}

	public List<BigInteger> getSimulationRunIdsAssociatedWithRunSimulationMessageHash(
			SoftwareIdentification softwareIdentification,
			Object runMessageToBeHashed) throws ApolloDatabaseException {

		int softwareKey = getSoftwareIdentificationKey(softwareIdentification);
		String md5Hash = getMd5(runMessageToBeHashed);

		return getRunIdsAssociatedWithHash(md5Hash, softwareKey);
	}

	public List<BigInteger> getRunIdsAssociatedWithMessageHashAndSoftware(
			Object message, SoftwareIdentification softwareId)
			throws ApolloDatabaseException {
		int softwareKey = getSoftwareIdentificationKey(softwareId);
		String md5Hash = getMd5(message);

		return getRunIdsAssociatedWithHash(md5Hash, softwareKey);
	}

	public List<BigInteger> getVisualizationRunIdsAssociatedWithRunVisualizationMessageHash(
			RunVisualizationMessage runVisualizationMessageToHash)
			throws ApolloDatabaseException {
		int softwareKey = getSoftwareIdentificationKey(runVisualizationMessageToHash
				.getVisualizerIdentification());
		String md5Hash = getMd5(runVisualizationMessageToHash);

		try {
			String query = "SELECT id FROM run WHERE md5_hash_of_run_message = ? AND software_id = ?";
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setString(1, md5Hash);
			pstmt.setInt(2, softwareKey);
			// pstmt.setInt(3, 1);
			ResultSet rs = pstmt.executeQuery();
			List<BigInteger> runIds = new ArrayList<BigInteger>();
			while (rs.next()) {
				runIds.add(new BigInteger(String.valueOf(rs.getInt(1))));
			}
			// else {
			// throw new ApolloDatabaseKeyNotFoundException(
			// "No statusId found for simulation run where md5_hash_of_run_message = "
			// + md5Hash + " and softare_id = " + softwareKey
			// + " and user_id = 1");
			// }

			return runIds;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundEXception attempting to get visualization run ID: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get visualization run ID: "
					+ ex.getMessage());
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
	public int updateLastServiceToBeCalledForRun(BigInteger runId,
			Integer softwareIdentificationKey) throws SQLException,
			ClassNotFoundException {
		String query = "UPDATE run SET last_service_to_be_called = ? WHERE id = ?";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.setInt(1, softwareIdentificationKey);
		pstmt.setInt(2, runId.intValue());
		return pstmt.executeUpdate();

	}

	public void updateStatusOfRun(BigInteger runId, int statusId, String message)
			throws ApolloDatabaseException {
		String query = "SELECT id FROM run_status WHERE run_id = "
				+ runId.intValue();
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = getConn().prepareStatement(query);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				query = "UPDATE run_status SET status_id = ?, message = ? WHERE run_id = ?";
			} else {
				query = "INSERT INTO run_status (status_id, message, run_id) VALUES (?,?,?)";
			}
			pstmt = getConn().prepareStatement(query);

			pstmt.setInt(1, statusId);
			pstmt.setString(2, message);
			pstmt.setInt(3, runId.intValue());
			pstmt.execute();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to insert or update status of run for run ID "
					+ runId + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to insert or update status of run for run ID "
					+ runId + ": " + ex.getMessage());
		}
	}

	public void updateStatusOfRun(BigInteger runId,
			MethodCallStatusEnum statusEnum, String message)
			throws ApolloDatabaseException {

		int statusId = getIdOfStatusEnum(statusEnum);
		updateStatusOfRun(runId, statusId, message);

	}

	public int updateLastServiceToBeCalledForRun(BigInteger runId,
			SoftwareIdentification softwareIdentification)
			throws ApolloDatabaseException, SQLException,
			ClassNotFoundException {
		int softwareIdentificationKey = getSoftwareIdentificationKey(softwareIdentification);
		return updateLastServiceToBeCalledForRun(runId,
				softwareIdentificationKey);
	}

	public int getIdOfLastServiceToBeCalledForRun(BigInteger runId)
			throws ApolloDatabaseKeyNotFoundException, SQLException,
			ClassNotFoundException {
		String query = "SELECT last_service_to_be_called FROM run WHERE id = ?";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.setInt(1, runId.intValue());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			throw new ApolloDatabaseKeyNotFoundException(
					"No last_service_to_be_called found for simulation run where id = "
					+ runId);
		}
	}

	public SoftwareIdentification getLastServiceToBeCalledForRun(
			BigInteger runId) throws ApolloDatabaseKeyNotFoundException,
			ApolloDatabaseException {

		try {
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
				softwareIdentification.setSoftwareType(ApolloSoftwareTypeEnum
						.fromValue(type));
				return softwareIdentification;
			} else {
				throw new ApolloDatabaseKeyNotFoundException(
						"No software identification found for id = "
						+ softwareId);
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to get last service to be called for run "
					+ runId + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get last service to be called for run "
					+ runId + ": " + ex.getMessage());
		}

	}

	private int getIdOfStatusEnum(MethodCallStatusEnum statusEnum)
			throws ApolloDatabaseException {
		String query = "SELECT id FROM run_status_description WHERE status = \""
				+ statusEnum.toString().toLowerCase() + "\"";
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = getConn().prepareStatement(query);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new ApolloDatabaseException(
						"There was no status in the run_status_description table corresponding to status enum\""
						+ statusEnum + "\"");
			}

		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to get status ID of status "
					+ statusEnum + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get status ID of status  "
					+ statusEnum + ": " + ex.getMessage());
		}
	}

	public MethodCallStatusEnum getStatusEnumForStatusId(int statusId)
			throws ApolloDatabaseException {
		String query = "SELECT status FROM run_status_description WHERE id = "
				+ statusId;

		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = getConn().prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String statusEnumString = rs.getString(1);
				MethodCallStatusEnum statusEnum = MethodCallStatusEnum
						.fromValue(statusEnumString);
				return statusEnum;
			} else {
				throw new ApolloDatabaseKeyNotFoundException(
						"No status was found in the run_status_description table for status ID "
						+ statusId);
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to get status enum of status ID "
					+ statusId + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get status enum of status ID "
					+ statusId + ": " + ex.getMessage());
		}
	}

	public MethodCallStatus getStatusOfLastServiceToBeCalledForRun(
			BigInteger runId) throws ApolloDatabaseException {
		String query = "SELECT status_id, message FROM run_status WHERE run_id = "
				+ runId.intValue();
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = getConn().prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int statusId = rs.getInt(1);
				String message = rs.getString(2);

				MethodCallStatusEnum statusEnum = getStatusEnumForStatusId(statusId);

				MethodCallStatus status = new MethodCallStatus();
				status.setMessage(message);
				status.setStatus(statusEnum);
				return status;
			} else {
				throw new ApolloDatabaseStatusNotFoundForRunIdException(
						"No status was found in the run_status table for run ID "
						+ runId.intValue());
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundExceptionattempting  to get status and message for run ID "
					+ runId.intValue() + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to get status and message for run ID "
					+ runId.intValue() + ": " + ex.getMessage());
		}
	}

	public BigInteger getNewSimulationGroupId() throws SQLException,
			ClassNotFoundException, ApolloDatabaseRecordNotInsertedException {
		String query = "INSERT INTO simulation_groups VALUES ()";
		PreparedStatement pstmt = getConn().prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.execute();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			return new BigInteger(rs.getString(1));
		} else {
			throw new ApolloDatabaseRecordNotInsertedException(
					"Unable to create new simulation group, insert failed.");
		}
	}

	public void addRunIdsToSimulationGroup(
			BigInteger simulationGroupId,
			List<RunIdentificationAndLabel> simulationRunIdentificationsAndLabels)
			throws SQLException, ClassNotFoundException {
		String query = "INSERT INTO simulation_group_definition (simulation_group_id, run_id) VALUES (?,?)";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		for (RunIdentificationAndLabel runIdAndLabel : simulationRunIdentificationsAndLabels) {
			BigInteger simulationRunId = runIdAndLabel.getRunIdentification();
			pstmt.setLong(1, simulationGroupId.longValue());
			pstmt.setLong(2, simulationRunId.longValue());
			pstmt.execute();
		}
	}

	public BigInteger[] addVisualizationRun(
			RunVisualizationMessage runVisualizationMessage,
			int md5CollisionId, Authentication authentication)
			throws ApolloDatabaseException,
			ApolloDatabaseRecordNotInsertedException {

		String userName = authentication.getRequesterId();
		String password = authentication.getRequesterPassword();

		String[] userIdTokens = parseUserId(userName);
		userName = userIdTokens[0];

		int userKey = getUserKey(userName, password);

		int softwareKey = getSoftwareIdentificationKey(runVisualizationMessage
				.getVisualizerIdentification());
		try {
			BigInteger simulationGroupId = getNewSimulationGroupId();
			addRunIdsToSimulationGroup(simulationGroupId,
					runVisualizationMessage.getSimulationRunIds());

			String query = "INSERT INTO run (md5_hash_of_run_message, software_id, requester_id, last_service_to_be_called, simulation_group_id, md5_collision_id) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = getConn().prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, getMd5(runVisualizationMessage));
			pstmt.setInt(2, softwareKey);
			pstmt.setInt(3, userKey);
			pstmt.setInt(4, 4); // 4 is translator
			pstmt.setLong(5, simulationGroupId.longValue());
			pstmt.setInt(6, md5CollisionId);
			pstmt.execute();

			BigInteger runId = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				runId = new BigInteger(rs.getString(1));
			} else {
				throw new ApolloDatabaseRecordNotInsertedException(
						"Record not inserted!");
			}

			// ALSO NEED TO ADD serialized runVisualizationMessage(JSON) to
			// run_data_content table...
			// use insertDataContentForRun for this
			int dataContentKey = addTextDataContent(getJSONString(runVisualizationMessage));
			int runDataDescriptionId = getRunDataDescriptionId(
					DbContentDataFormatEnum.TEXT,
					"run_visualization_message.json",
					DbContentDataType.RUN_VISUALIZATION_MESSAGE, 0,
					getSoftwareIdentificationKey(runVisualizationMessage
							.getVisualizerIdentification()));
			// int runDataId = the following line returns the runDataId, but
			// it's not used at this point.
			associateContentWithRunId(new BigInteger(String.valueOf(runId)),
					dataContentKey, runDataDescriptionId);
			BigInteger[] runIdSimulationGroupId = new BigInteger[2];
			runIdSimulationGroupId[0] = runId;
			runIdSimulationGroupId[1] = simulationGroupId;
			return runIdSimulationGroupId;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to add visualization run: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to add visualization run: "
					+ ex.getMessage());
		}
	}

	public void removeRunData(BigInteger runId) throws ApolloDatabaseException {
		// need to delete the data content
		// find out if there any other runs that reference this data content
		String query = "SELECT content_id FROM run_data WHERE run_id = ?";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setInt(1, runId.intValue());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int content_id = rs.getInt(1);
				String innerQuery = "SELECT content_id FROM run_data WHERE run_id <> ? AND content_id = ?";
				PreparedStatement innerPstmt = getConn().prepareStatement(
						innerQuery);
				innerPstmt.setInt(1, runId.intValue());
				innerPstmt.setInt(2, content_id);
				ResultSet innerRs = innerPstmt.executeQuery();
				if (!innerRs.next()) {
					// content_id is not used by any other run, delete it!
					String deleteQuery = "DELETE FROM run_data_content WHERE id = ?";
					PreparedStatement deletePstmt = getConn().prepareStatement(
							deleteQuery);
					deletePstmt.setInt(1, content_id);
					deletePstmt.execute();
				}

			}
			query = "DELETE FROM run_data WHERE run_id = ?";
			pstmt = getConn().prepareStatement(query);
			pstmt.setInt(1, runId.intValue());
			pstmt.execute();

			query = "SELECT simulation_group_id FROM run WHERE id = ?";
			pstmt = getConn().prepareStatement(query);
			pstmt.setInt(1, runId.intValue());
			rs = pstmt.executeQuery();
			List<Integer> simulationGroupIds = new ArrayList<Integer>();
			if (rs.next()) {
				if (!rs.wasNull()) {
					simulationGroupIds.add(rs.getInt(1));
				}

			}

			query = "DELETE FROM run_status WHERE run_id = ?";
			pstmt = getConn().prepareStatement(query);
			pstmt.setInt(1, runId.intValue());
			pstmt.execute();

			query = "DELETE FROM time_series WHERE run_id = ?";
			pstmt = getConn().prepareStatement(query);
			pstmt.setInt(1, runId.intValue());
			pstmt.execute();

			query = "DELETE FROM run WHERE id = ?";
			pstmt = getConn().prepareStatement(query);
			pstmt.setInt(1, runId.intValue());
			pstmt.execute();

			for (Integer simulation_group_id : simulationGroupIds) {
				// int simulation_group_id = rs.getInt(1);
				String innerQuery = "DELETE FROM simulation_group_definition WHERE simulation_group_id = ?";
				pstmt = getConn().prepareStatement(innerQuery);
				pstmt.setInt(1, simulation_group_id);
				pstmt.execute();

				innerQuery = "DELETE FROM simulation_groups WHERE id = ?";
				pstmt = getConn().prepareStatement(innerQuery);
				pstmt.setInt(1, simulation_group_id);
				pstmt.execute();

			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException(
					"ClassNotFoundException attempting to remove all data for run "
					+ runId + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException(
					"SQLException attempting to remove all data for run "
					+ runId + ": " + ex.getMessage());
		}

	}

	public void createAxisId(String label) throws ClassNotFoundException,
			SQLException {
		String query = "insert into population_axis (label) values (?)";
		PreparedStatement pstmt;
		try {
			pstmt = getConn().prepareStatement(query);
			pstmt.setString(1, label);
			pstmt.execute();
		} catch (SQLException e) {
			throw new SQLException("Error creating axis id for label: " + label
					+ ".   Specific error was:\n" + e.getMessage());
		}

	}

	public int getAxisId(String label) throws SQLException,
			ClassNotFoundException {

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
					// System.out.println(statusId);
				} // end while
			} catch (SQLException e) {
				throw new SQLException("Error retreiving axis id for label: "
						+ label + ".   Specific error was:\n" + e.getMessage());

			}
			try {
				if (id == -1) {
					String query = "insert into population_axis (label) values (?)";

					PreparedStatement pstmt = getConn().prepareStatement(query);
					pstmt.setString(1, label);
				}
			} catch (SQLException e) {
				throw new SQLException("Error creating axis id for label: "
						+ label + ".   Specific error was:\n" + e.getMessage());

			}
		}
		return id;

	}

	public Integer getPopulationId(String disease_state)
			throws ClassNotFoundException, SQLException {
		Integer popId = simulatedPopulationCache.get(disease_state);

		if (popId == null) {
			try {
				String query = "select id from simulated_population where label like ?";
				PreparedStatement pstmt = getConn().prepareStatement(query);
				pstmt.setString(1, disease_state);

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					// System.out.println("Internal statusId is:" +
					// rs.getInt(1));
					popId = rs.getInt(1);

					simulatedPopulationCache.put(disease_state, popId);
				} // end while

			} catch (SQLException e) {
				throw new SQLException("Error retreiving population id for: "
						+ disease_state
						+ " from simulated_population.  Specific error was:\n"
						+ e.getMessage());
			}
		}
		return popId;

	}

	public int getOrCreatePopulationId(int axisId, String disease_state)
			throws ClassNotFoundException, SQLException {
		Integer popId = getPopulationId(disease_state);

		if (popId == null) {
			try {
				String query = "INSERT INTO simulated_population (LABEL) VALUES ('"
						+ disease_state + "')";

				PreparedStatement pstmt = getConn().prepareStatement(query);
				pstmt.execute();
			} catch (SQLException e) {
				throw new SQLException("Error inserting disease state: "
						+ disease_state + " into simulated_population."
						+ " Specific error was:\n" + e.getMessage());
			}

			try {
				String query = "SELECT ID FROM simulated_population WHERE LABEL like ?";
				PreparedStatement pstmt = getConn().prepareStatement(query);
				pstmt.setString(1, disease_state);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					// System.out.println("Population statusId is:" +
					// rs.getInt(1));
					popId = rs.getInt(1);
					simulatedPopulationCache.put(disease_state, popId);
				} // end while
			} catch (SQLException e) {
				throw new SQLException(
						"Error retreiving ID from simulated_population for label: "
						+ disease_state + "."
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
				throw new SQLException("Error inserting value: "
						+ disease_state
						+ " into simulated_population_axis_value."
						+ " Specific error was:\n" + e.getMessage());
			}
		}

		return popId;
	}

	public int getOrCreatePopulationId(int diseaseStateAxisId,
			int locationAxisId, String disease_state, String location)
			throws ClassNotFoundException, SQLException {
		Integer popId = -1;

		popId = getPopulationId(disease_state + " in " + location);

		if (popId == null || popId == -1) {
			try {
				String query = "INSERT INTO simulated_population (LABEL) VALUES ('"
						+ disease_state + " in " + location + "')";

				PreparedStatement pstmt = getConn().prepareStatement(query);
				pstmt.execute();
			} catch (SQLException e) {
				throw new SQLException("Error inserting disease state: "
						+ disease_state + " into simulated_population."
						+ " Specific error was:\n" + e.getMessage());
			}

			try {
				String query = "SELECT ID FROM simulated_population WHERE LABEL like ?";
				PreparedStatement pstmt = getConn().prepareStatement(query);
				pstmt.setString(1, disease_state + " in " + location);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					// System.out.println("Population statusId is:" +
					// rs.getInt(1));
					popId = rs.getInt(1);
				} // end while
			} catch (SQLException e) {
				throw new SQLException(
						"Error retreiving ID from simulated_population for label: "
						+ disease_state + "."
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
				throw new SQLException("Error inserting value: "
						+ disease_state
						+ " into simulated_population_axis_value."
						+ " Specific error was:\n" + e.getMessage());
			}
		}

		return popId;
	}

	public void insertDiseaseStateTimeSeries(PreparedStatement pstmt,
			int runId, int popId, String disease_state, List<Double> ts)
			throws SQLException, ClassNotFoundException {

		logger.info(
				"In insertDiseaseStateTimeSeries, runId={}, popId={}, disease_state={}",
				runId, popId, disease_state);
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
			logger.error(
					"Error inserting disease state time series for runId={}, popId={}, disease_state={}",
					runId, popId, disease_state);

			throw new SQLException(
					"Error inserting disease state time series for internal run id: "
					+ runId + ", disease state: " + disease_state
					+ ".   Specific error was:\n" + e.getMessage());
		}

	}

	public void insertTimeSeries(int runId, int popId, String label,
			List<Integer> ts) throws SQLException, ClassNotFoundException {

		try {
			PreparedStatement pstmt = getConn()
					.prepareStatement(
							"INSERT INTO time_series (run_id, population_id, time_step, pop_count) VALUES (?,?,?,?)");
			for (int i = 0; i < ts.size(); i++) {
				pstmt.setInt(1, runId);
				pstmt.setInt(2, popId);
				pstmt.setInt(3, i);
				pstmt.setInt(4, ts.get(i));
				pstmt.execute();
			}
		} catch (SQLException e) {
			throw new SQLException(
					"Error inserting disease state time series for internal run id: "
					+ runId + ", label: " + label
					+ ".   Specific error was:\n" + e.getMessage());
		}

	}

	public void insertDiseaseStateTimeSeriesNegative(PreparedStatement pstmt,
			int runId, int popId, String disease_state, List<Double> ts)
			throws SQLException, ClassNotFoundException {

		logger.info(
				"In insertDiseaseStateTimeSeries, runId={}, popId={}, disease_state={}",
				runId, popId, disease_state);
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
			logger.error(
					"Error inserting disease state time series for runId={}, popId={}, disease_state={}",
					runId, popId, disease_state);

			throw new SQLException(
					"Error inserting disease state time series for internal run id: "
					+ runId + ", disease state: " + disease_state
					+ ".   Specific error was:\n" + e.getMessage());
		}

	}

	public void awaitRowCountForTimeSeriesTable(int runId, int totalRowCount)
			throws SQLException, ClassNotFoundException {
		PreparedStatement pstmt = getConn().prepareStatement(
				"select count(*) from time_series where run_id = ?");
		int actualCount = -1;
		for (int sleepSeconds = 0; actualCount != totalRowCount; sleepSeconds = Math
				.min(sleepSeconds + 1, 10)) {
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
		System.out
				.printf("Confirmed that %d expected rows are in the database for run id %d.\n",
						actualCount, runId);
	}

//	public static void main(String[] args) throws IOException,
//			ApolloDatabaseException {
//
//		ApolloDbUtils dbUtils = new ApolloDbUtils(new File("C:\\apollo_300\\database.properties"));
//
//		RunSimulationMessage message = new AnthraxRunSimulationMessageBuilder().getRunSimulationMessage(new HashSet<AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum>());
//		String json = dbUtils.getJSONString(message);
//		System.out.println(json);
//
//	}
}
