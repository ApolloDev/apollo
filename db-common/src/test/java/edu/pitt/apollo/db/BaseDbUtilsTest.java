package edu.pitt.apollo.db;

import junit.framework.TestCase;
import org.hsqldb.Server;

import java.sql.Connection;
import java.util.Properties;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Aug 4, 2014
 Time: 12:10:33 PM
 Class: BaseDbUtilsTest
 */
public class BaseDbUtilsTest extends TestCase {

	protected ApolloDbUtils apolloDbUtils;
	private Properties databaseProperties;
	private Server hsqlServer = null;
	private Connection connection = null;
	private static final String TEST_DATABASE_PROPERTIES_FILE = "/test_database.properties";
	private static final String APOLLO_DB_SCHEMA_FILE = "/apollo_db_schema.sql";

	public void testTemp() {
		assert(true);
	}
	
//	@Override
//	public void setUp() throws Exception {
//		super.setUp();
//		System.out.println("SETTING UP");
//		startHsqlDatabase();
//		connectToHsqlDatabase();
//		createApolloDatabase();
//		createApolloDbUtils();
//	}
//
//	@Override
//	public void tearDown() {
//		try {
//			stopHsqlDatabase();
//		} catch (SQLException ex) {
//			fail(ex.getMessage());
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//	}
//
//	private void startHsqlDatabase() {
//		// 'Server' is a class of HSQLDB representing
//		// the database server
//		hsqlServer = new Server();
//
//		// HSQLDB prints out a lot of informations when
//		// starting and closing, which we don't need now.
//		// Normally you should point the setLogWriter
//		// to some Writer object that could store the logs.
//		hsqlServer.setLogWriter(null);
//		hsqlServer.setSilent(false);
//		hsqlServer.setTrace(true);
//
//		// The actual database will be named 'xdb' and its
//		// settings and data will be stored in files
//		// testdb.properties and testdb.script
//		hsqlServer.setDatabaseName(0, "apollo202");
//		hsqlServer.setDatabasePath(0, "mem:apollo202;sql.syntax_mys=true");
//
//		// Start the database!
//		hsqlServer.start();
//	}
//
//	private void stopHsqlDatabase() throws SQLException, ApolloDatabaseException {
//		if (connection != null) {
//			connection.close();
//		}
//		if (hsqlServer != null) {
//			hsqlServer.stop();
//		}
//		apolloDbUtils.closeConnection();
//	}
//
//	private void connectToHsqlDatabase() throws IOException, SQLException, ClassNotFoundException {
//
//		databaseProperties = new Properties();
//		databaseProperties.load(this.getClass().getResourceAsStream(TEST_DATABASE_PROPERTIES_FILE));
//
//		String dbClass = databaseProperties.getProperty("class");
//		String url = databaseProperties.getProperty("url");
//		String user = databaseProperties.getProperty("user");
//		String password = databaseProperties.getProperty("password");
//
//		if (connection != null) {
//			connection.close();
//		}
//
//		Class.forName(dbClass);
//		connection = DriverManager.getConnection(url, user, password);
//	}
//
//	private void createApolloDatabase() throws IOException, SQLException {
//
//		InputStream apolloDatabaseScriptInputStream = this.getClass().getResourceAsStream(APOLLO_DB_SCHEMA_FILE);
//
//		Reader reader = null;
//		try {
//			reader = new BufferedReader(new InputStreamReader(apolloDatabaseScriptInputStream));
//			ScriptRunner scriptRunner = new ScriptRunner(connection, false, true);
//			scriptRunner.runScript(reader);
//		} finally {
//			if (reader != null) {
//				reader.close();
//			}
//		}
//	}
//
//	private void createApolloDbUtils() throws IOException {
//
//		apolloDbUtils = new ApolloDbUtils(this.getClass().getResourceAsStream(TEST_DATABASE_PROPERTIES_FILE));
//	}
//
//	public void testTest() {
//		assert (true);
//	}
//	
//	public void testTest2() {
//		assert(true);
//	}
}
