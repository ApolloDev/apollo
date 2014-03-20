package edu.pitt.apollo;

//import edu.pitt.apollo.types.UrlOutputResource;
//import edu.pitt.apollo.types.VisualizerResult;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import edu.pitt.apollo.types.v2_0.Authentication;
import edu.pitt.apollo.types.v2_0.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0.UrlOutputResource;
import edu.pitt.apollo.types.v2_0.VisualizerResult;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time:
 * 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public class DbUtils {

	static Connection dbcon = null;
	static final Properties properties = new Properties();

	public static final int PASSWORD_NOT_CORRECT = -2;
	public static final int RECORD_NOT_FOUND = -1;
	public static final int RECORD_ALREADY_EXISTS = -3;

	static {
		InputStream input;
		Map<String, String> env = System.getenv();
		// String dir =
		// env.get(ApolloServiceImpl.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		// String fn = dir
		// + "/database.properties";
		String fn = "db.properties";
		try {

			input = new FileInputStream(fn);
			properties.load(input);
			System.out.println("Successfully loaded " + fn + " file.");
		} catch (Exception e) {
			System.out.println("\n\n\nError loading " + fn + " file\n\n\n");
		}

	}

	private static void establishDbConn() throws ClassNotFoundException, SQLException {

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

	public static Connection getConn() throws ClassNotFoundException, SQLException {
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

	public static Map<String, String> getStoredRuns() throws SQLException, ClassNotFoundException {
		try {
			String query = "SELECT LABEL,MD5HASHOFCONFIGURATIONFILE from run";
			PreparedStatement pstmt = getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			Map<String, String> hashLabelMap = new HashMap<String, String>();
			while (rs.next()) {
				String hash = rs.getString("md5HashOfConfigurationFile");
				String label = rs.getString("label");
				if (label != null) {
					hashLabelMap.put(label, hash);
				}
			}
			return hashLabelMap;
		} catch (SQLException e) {
			throw new SQLException("Error retreiving all stored run hashes. Specific error was:\n" + e.getMessage());
		}
	}

	public static void insertIntoRunCache(String runId, String md5HashOfRunSimulationMessage) throws ClassNotFoundException,
			SQLException {

		String query = "INSERT INTO apollo_service_simulator_run_cache (LABEL, MD5HASHOFSIMULATORCONFIGURATION) " + "VALUES ('"
				+ runId + "','" + md5HashOfRunSimulationMessage + "')";

		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.execute();
	}

	public static String checkRunCache(String md5HashOfRunSimulationMessage) throws SQLException, ClassNotFoundException {

		String query = "SELECT LABEL,MD5HASHOFSIMULATORCONFIGURATION FROM apollo_service_simulator_run_cache"
				+ " where MD5HASHOFSIMULATORCONFIGURATION LIKE " + "'" + md5HashOfRunSimulationMessage + "'";

		PreparedStatement pstmt = getConn().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();

		String runId = null;
		while (rs.next()) {
			runId = rs.getString("LABEL");
		}

		return runId;
	}

	public static void deleteFromRunCache(String md5HashOfRunSimulationMessage) throws ClassNotFoundException, SQLException {

		String query = "DELETE FROM apollo_service_simulator_run_cache WHERE MD5HASHOFSIMULATORCONFIGURATION = '"
				+ md5HashOfRunSimulationMessage + "'";

		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.execute();
	}

	public static String insertIntoVisualizerCache(String runId, String md5HashOfVisualizerConfiguration) throws SQLException,
			ClassNotFoundException {

		String query = "INSERT INTO apollo_service_visualizer_cache (LABEL, MD5HASHOFCONFIGURATION) " + "VALUES ('" + runId
				+ "', '" + md5HashOfVisualizerConfiguration + "')";

		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.execute();

		query = "SELECT CACHE_ID FROM apollo_service_visualizer_cache WHERE LABEL LIKE '" + runId + "'";
		pstmt = getConn().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();

		String cacheId = null;
		while (rs.next()) {
			cacheId = rs.getString("CACHE_ID");
		}

		return cacheId;
	}

	public static void insertIntoVisualizerResultsCache(String cacheId, List<UrlOutputResource> urlList)
			throws ClassNotFoundException, SQLException {

		String query = "INSERT INTO apollo_service_visualizer_cache_results (CACHE_ID, URL, DESCRIPTION) VALUES (?,?,?)";

		PreparedStatement pstmt = getConn().prepareStatement(query);

		for (UrlOutputResource uor : urlList) {
			String url = uor.getURL().toString();
			String description = uor.getDescription();
			pstmt.setString(1, cacheId);
			pstmt.setString(2, url);
			pstmt.setString(3, description);
			pstmt.addBatch();
		}

		pstmt.executeBatch();
	}

	public static VisualizerResult checkVisualizerCache(String md5HashOfVisualizerConfiguration) throws SQLException,
			ClassNotFoundException {

		String query = "SELECT CACHE_ID,LABEL,MD5HASHOFCONFIGURATION FROM apollo_service_visualizer_cache WHERE"
				+ " MD5HASHOFCONFIGURATION LIKE '" + md5HashOfVisualizerConfiguration + "'";

		PreparedStatement pstmt = getConn().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();

		String cacheId = null;
		String label = null;
		while (rs.next()) {
			cacheId = rs.getString("CACHE_ID");
			label = rs.getString("LABEL");
		}

		if (cacheId == null) {
			// this visualizer run does not exist in the database
			return null;
		}

		// create the visualizer result
		VisualizerResult vizResult = new VisualizerResult();
		vizResult.setRunId(label);
		List<UrlOutputResource> urlList = vizResult.getVisualizerOutputResource();

		query = "SELECT URL,DESCRIPTION FROM apollo_service_visualizer_cache_results WHERE CACHE_ID = " + cacheId;
		pstmt = getConn().prepareStatement(query);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			String url = rs.getString("URL");
			String description = rs.getString("DESCRIPTION");

			UrlOutputResource resource = new UrlOutputResource();
			resource.setDescription(description);
			resource.setURL(url);
			urlList.add(resource);
		}

		return vizResult;
	}

	public static void deleteFromVisualizerCache(String md5HashOfVisualizerConfiguration) throws ClassNotFoundException,
			SQLException {

		String query = "SELECT CACHE_ID FROM apollo_service_visualizer_cache WHERE MD5HASHOFCONFIGURATION = '"
				+ md5HashOfVisualizerConfiguration + "'";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();

		String cacheId = null;
		while (rs.next()) {
			cacheId = rs.getString("CACHE_ID");
		}

		query = "DELETE FROM apollo_service_visualizer_cache_results WHERE CACHE_ID = " + cacheId;
		pstmt = getConn().prepareStatement(query);
		pstmt.execute();

		query = "DELETE FROM apollo_service_visualizer_cache WHERE MD5HASHOFCONFIGURATION = '" + md5HashOfVisualizerConfiguration
				+ "'";
		pstmt = getConn().prepareStatement(query);
		pstmt.execute();
	}

	public static int getUserKey(String requesterId, String requesterPassword) throws SQLException, ClassNotFoundException {

		String query = "SELECT id, requester_password FROM users WHERE requester_id = ?";
		PreparedStatement pstmt = getConn().prepareStatement(query);
		pstmt.setString(1, requesterId);
		ResultSet rs = pstmt.executeQuery();
		int result = -1;
		if (rs.isLast()) {

		} else {
			while (rs.next()) {
				String password = rs.getString("requester_password");
				if (requesterPassword.equals(password)) {
					result = rs.getInt("id");
					return result;
				} else {
					return PASSWORD_NOT_CORRECT;
				}
			}
		}
		return RECORD_NOT_FOUND;
	}

	// user key doesn't exist
	public static int addUser(String requesterId, String requesterPassword, String email) throws SQLException,
			ClassNotFoundException {
		// check authorization?!
		int userKey = getUserKey(requesterId, requesterPassword);
		
		if (userKey == RECORD_NOT_FOUND) {
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
		
		if (userKey == PASSWORD_NOT_CORRECT) {
			return RECORD_ALREADY_EXISTS;
		}
		return RECORD_ALREADY_EXISTS;

	}

	public static int getSoftwareIdentificationKey(SoftwareIdentification fredSoftwareId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int insertTextContent(String string) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static SoftwareIdentification getSoftwareIdentification(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Authentication getAuthentication(int runId) {
		return null;
	}

	public static int getRunKey(RunSimulationMessage runSimulationMessage, Object object) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int addContentToRun(int runKey, int dataContentKey, String dataFormat, String dataLabel, String dataType,
			String dataSourceSoftware, String dataDestinationSoftware) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void addRole(SoftwareIdentification softwareIdentification, boolean canRun, boolean canViewCache) {
		
		
		getRoleKey(softwareIdentification,canRun, canViewCache);
		
		
		
		//find role key
		//add role to table
		
	}

	private static void getRoleKey(SoftwareIdentification softwareIdentification, boolean canRun, boolean canViewCache) {
		int softwareIdKey = getSoftwareIdentificationKey(softwareIdentification);
		if (softwareIdKey == RECORD_NOT_FOUND) {
			
		} else if (RECORD_ALREADY_EXISTS) {
			
		}
	}

}
