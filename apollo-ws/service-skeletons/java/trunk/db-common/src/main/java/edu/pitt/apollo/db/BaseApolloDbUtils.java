package edu.pitt.apollo.db;

import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordAlreadyExistsException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseUserPasswordException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import static edu.pitt.apollo.GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE;
import static edu.pitt.apollo.db.ApolloDbUtils.logger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 6, 2014
 * Time: 11:10:59 AM
 * Class: BaseApolloDbUtils
 */
public abstract class BaseApolloDbUtils {

	static Logger logger = LoggerFactory.getLogger(BaseApolloDbUtils.class);
	private static final String APOLLO_DIR;
	private static final String SALT_FILE_NAME = "salt.txt";
	protected static final String SYSTEM_SALT;
	private static final String USER_ID_TOKEN_SEPERATOR = "\\+";
	Connection dbcon = null;
	Properties properties;
	private final boolean AUTO_COMMIT;

	public BaseApolloDbUtils(File databasePropertiesFile, boolean autocommit) throws IOException {
		InputStream fis = new FileInputStream(databasePropertiesFile);
		properties = new Properties();
		properties.load(fis);
		fis.close();
		AUTO_COMMIT = autocommit;
	}

	public BaseApolloDbUtils(InputStream databasePropertiesInputStream, boolean autocommit) throws IOException {

		properties = new Properties();
		properties.load(databasePropertiesInputStream);
		databasePropertiesInputStream.close();
		AUTO_COMMIT = autocommit;
	}

	public abstract int addUser(String userName, String userPassword, String userEmail) throws ApolloDatabaseRecordAlreadyExistsException,
			ApolloDatabaseUserPasswordException, ApolloDatabaseException;

	protected abstract ByteArrayOutputStream getJsonBytes(Object obj) throws Exception;

	protected void establishDbConn() throws ClassNotFoundException, SQLException {

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
			logger.debug("Getting DB connection");
			dbcon = DriverManager.getConnection(url, user, password);
			dbcon.setAutoCommit(AUTO_COMMIT);
		} catch (SQLException e) {
			throw new SQLException("Error getting connection to database: " + url + " using username " + user
					+ ".   Specific error was:\n" + e.getMessage());
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

	public void closeConnection() throws ApolloDatabaseException {
		if (dbcon != null) {
			try {
				dbcon.close();
			} catch (SQLException ex) {
				throw new ApolloDatabaseException("SQLException attempting to close database connection: "
						+ ex.getMessage());
			}
		}
	}

	protected String getHashOfUserPasswordAndSalt(String password, String salt) {

		String passwordAndSalt = password + salt + SYSTEM_SALT;
		return getMd5FromString(passwordAndSalt);
	}

	public String getMd5(Object object) throws ApolloDatabaseException {

		try {
			return DigestUtils.md5Hex(getJsonBytes(object).toString());
		} catch (Exception ex) {
			throw new ApolloDatabaseException("Exception getting MD5 hash: " + ex.getMessage());
		}
	}

	public String getMd5FromString(String string) {
		return DigestUtils.md5Hex(string);
	}

	protected String getSaltForPassword() {
		Random random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		return new String(bytes);
	}

	static {

		Map<String, String> env = System.getenv();
		String apolloDir = env.get(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			APOLLO_DIR = apolloDir;
			logger.info(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:"
					+ APOLLO_DIR);
		} else {
			logger.error(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE
					+ " environment variable not found!");
			APOLLO_DIR = "";
		}

		File saltFile = new File(APOLLO_DIR + SALT_FILE_NAME);
		Scanner saltFileScanner;
		try {
			saltFileScanner = new Scanner(saltFile);
		} catch (FileNotFoundException ex) {
			throw new ExceptionInInitializerError("File \"" + saltFile.getAbsolutePath() + "\" could not be found");
		}

		SYSTEM_SALT = saltFileScanner.nextLine();
		saltFileScanner.close();
	}
}
