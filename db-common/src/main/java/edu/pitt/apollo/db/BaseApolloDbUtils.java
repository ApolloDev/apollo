package edu.pitt.apollo.db;


import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordAlreadyExistsException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseUserPasswordException;
import edu.pitt.apollo.utilities.JsonUtils;
import edu.pitt.apollo.utilities.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static edu.pitt.apollo.GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 6, 2014
 * Time: 11:10:59 AM
 * Class: BaseApolloDbUtils
 */
public abstract class BaseApolloDbUtils implements AutoCloseable {

    protected static final String SYSTEM_SALT;
    private static final String SALT_FILE_NAME = "salt.txt";
    private static final String USER_ID_TOKEN_SEPERATOR = "\\+";
    protected static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
    protected static String APOLLO_DIR;
    static Logger logger = LoggerFactory.getLogger(BaseApolloDbUtils.class);
    protected final DataSource datasource;
    JsonUtils jsonUtils = new JsonUtils();
    //	Connection dbcon;
//	Properties properties;
//	private final boolean AUTO_COMMIT;
    Md5Utils md5Utils = new Md5Utils();

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

    protected Connection getConnection(boolean autoCommit) throws SQLException {
        Connection connection = datasource.getConnection();
        connection.setAutoCommit(autoCommit);
        return connection;
    }

//		try {
//			dbcon = datasource.getConnection();
//			connectionsOpen.addAndGet(1);
//			System.out.println("connections open: " + connectionsOpen.intValue());
//		} catch (SQLException ex) {
//			throw new ApolloDatabaseException("SQLException initializing DB Utils: " + ex.getMessage());
//		}


//	public BaseApolloDbUtils(File databasePropertiesFile, boolean autocommit) throws IOException {
//		InputStream fis = new FileInputStream(databasePropertiesFile);
//		properties = new Properties();
//		properties.load(fis);
//		fis.close();
//		AUTO_COMMIT = autocommit;
//	}
//
//	public BaseApolloDbUtils(InputStream databasePropertiesInputStream, boolean autocommit) throws IOException {
//
//		properties = new Properties();
//		properties.load(databasePropertiesInputStream);
//		databasePropertiesInputStream.close();
//		AUTO_COMMIT = autocommit;
//	}

    protected void setupResource(String resourceName) {
        try {
            Properties properties = new Properties();
            properties.load(new BufferedReader(new FileReader(APOLLO_DIR + "database.properties")));

            com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds
                    = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();

            ds.setServerName(properties.getProperty("server"));
            ds.setPortNumber(Integer.valueOf(properties.getProperty("port")));
            ds.setDatabaseName(properties.getProperty("database_name"));
            ds.setUser(properties.getProperty("user"));
            ds.setPassword(properties.getProperty("password"));
            dataSourceMap.put(resourceName, ds);
        } catch (IOException ex) {
            logger.error("Fallback failed, error creating default datasource!  Error was: " + ex.getMessage());
        }
    }

    public BaseApolloDbUtils(boolean autocommit, String resourceName) throws ApolloDatabaseException {
        if (!dataSourceMap.containsKey(resourceName)) {
            try {
                Context initCtx = new InitialContext();
                Context envCtx = (Context) initCtx.lookup("java:comp/env");
                dataSourceMap.put(resourceName, (DataSource) envCtx.lookup("jdbc/" + resourceName));
            } catch (NamingException e) {

                logger.error("Error initializing db resource, falling back to default configuration:" + e.getMessage());
                setupResource(resourceName);
            }

        }
        datasource = dataSourceMap.get(resourceName);
    }


//	protected void establishDbConn() throws ClassNotFoundException, SQLException {
//
//		String dbClass = properties.getProperty("class");
//		String url = properties.getProperty("url");
//		String user = properties.getProperty("user");
//		String password = properties.getProperty("password");
//
//		try {
//			if (dbcon != null) {
//				dbcon.close();
//			}
//		} catch (SQLException e) {
//			// who cares, making a new one anyway
//		}
//		dbcon = null;
//		try {
//			Class.forName(dbClass);
//			logger.debug("Getting DB connection");
//			dbcon = DriverManager.getConnection(url, user, password);
//			dbcon.setAutoCommit(AUTO_COMMIT);
//		} catch (SQLException e) {
//			throw new SQLException("Error getting connection to database: " + url + " using username " + user
//					+ ".   Specific error was:\n" + e.getMessage());
//		}
//	}
//	public Connection getConn() throws ClassNotFoundException, SQLException {
//
//		boolean connIsValid = false;
//		try {
//			connIsValid = dbcon.isValid(1000);
//		} catch (SQLException e1) {
//			// who cares, we are making a new one anyway!
//		}
//		if (!connIsValid) {
//			dbcon = datasource.getConnection();
//		}
//
//		return dbcon;
//	}

    public abstract int addUser(String userName, String userPassword, String userEmail) throws ApolloDatabaseRecordAlreadyExistsException,
            ApolloDatabaseUserPasswordException, ApolloDatabaseException;

    @Override
    public void close() throws ApolloDatabaseException {
//		try {
//			dbcon.close();
//			connectionsOpen.addAndGet(-1);
//		} catch (SQLException ex) {
//			throw new ApolloDatabaseException("Could not close connection");
//		}
    }

    //	public void closeConnection() throws ApolloDatabaseException {
//		if (dbcon != null) {
//			try {
//				dbcon.close();
//			} catch (SQLException ex) {
//				throw new ApolloDatabaseException("SQLException attempting to close database connection: "
//						+ ex.getMessage());
//			}
//		}
//	}
    protected String getHashOfUserPasswordAndSalt(String password, String salt) {

        String passwordAndSalt = password + salt + SYSTEM_SALT;
        return md5Utils.getMd5FromString(passwordAndSalt);
    }

    protected String getSaltForPassword() {
        Random random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
