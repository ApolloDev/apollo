package edu.pitt.apollo.db;


import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
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

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 6, 2014
 * Time: 11:10:59 AM
 * Class: BaseDbUtils
 */
public abstract class BaseDbUtils implements AutoCloseable {

    protected static String systemSalt;
    protected static final String SALT_FILE_NAME = "salt.txt";
    protected static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
    protected static String APOLLO_DIR;
    protected static Logger logger = LoggerFactory.getLogger(BaseDbUtils.class);
    protected final DataSource datasource;
    JsonUtils jsonUtils = new JsonUtils();
    Md5Utils md5Utils = new Md5Utils();

    protected abstract void setBaseDirectory();
    protected abstract String getSystemSaltFileDir();
    protected abstract String getDatabasePropertiesFileName();

    protected String getSystemSalt(String saltFileDir) {


        File saltFile = new File(saltFileDir);
        Scanner saltFileScanner;
        try {
            saltFileScanner = new Scanner(saltFile);
        } catch (FileNotFoundException ex) {
            throw new ExceptionInInitializerError("File \"" + saltFile.getAbsolutePath() + "\" could not be found");
        }

        String salt = saltFileScanner.nextLine();
        saltFileScanner.close();
        return salt;
    }

    protected Connection getConnection(boolean autoCommit) throws SQLException {
        Connection connection = datasource.getConnection();
        connection.setAutoCommit(autoCommit);
        return connection;
    }

    protected void setupResource(String resourceName) {
        try {
            Properties properties = new Properties();
            properties.load(new BufferedReader(new FileReader(APOLLO_DIR + getDatabasePropertiesFileName())));

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

    public BaseDbUtils(String resourceName) throws ApolloDatabaseException {
        setBaseDirectory();
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

        if (datasource == null) {
            throw new ApolloDatabaseException("No datasource was created");
        }

        if (systemSalt == null) {
            systemSalt = getSystemSalt(getSystemSaltFileDir());
        }
    }

    @Override
    public void close() throws ApolloDatabaseException {
    }

    protected String getHashOfUserPasswordAndSalt(String password, String salt) {

        String passwordAndSalt = password + salt + systemSalt;
        return md5Utils.getMd5FromString(passwordAndSalt);
    }

    protected String getSecureRandomString() {
        Random random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
