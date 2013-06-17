package edu.pitt.apollo;

import edu.pitt.apollo.types.UrlOutputResource;
import edu.pitt.apollo.types.VisualizerResult;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 17, 2013
 * Time: 4:35:10 PM
 * Class: DbUtils
 * IDE: NetBeans 6.9.1
 */
public class DbUtils {

    static Connection dbcon = null;
    static final ResourceBundle DATABASE_PROPERTIES = ResourceBundle.getBundle("database");

    private static void establishDbConn() throws ClassNotFoundException,
            SQLException {

        String dbClass = DATABASE_PROPERTIES.getString("class");
        String url = DATABASE_PROPERTIES.getString("url");
        String user = DATABASE_PROPERTIES.getString("user");
        String password = DATABASE_PROPERTIES.getString("password");

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
            throw new SQLException("Error getting connection to database: "
                    + password + " using username " + user
                    + ".   Specific error was:\n" + e.getMessage());
        }
    }

    public static Connection getConn() throws ClassNotFoundException,
            SQLException {
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

    public static Map<String, String> getStoredRuns() throws SQLException,
            ClassNotFoundException {
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
            throw new SQLException(
                    "Error retreiving all stored run hashes. Specific error was:\n"
                    + e.getMessage());
        }
    }

    public static void insertIntoRunCache(String runId, String md5HashOfSimulatorConfiguration) throws ClassNotFoundException, SQLException {

        String query = "INSERT INTO apollo_service_simulator_run_cache (LABEL, MD5HASHOFSIMULATORCONFIGURATION) "
                + "VALUES ('" + runId + "','" + md5HashOfSimulatorConfiguration + "')";

        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.execute();
    }

    public static String checkRunCache(String md5HashOfSimulatorConfiguration) throws SQLException, ClassNotFoundException {

        String query = "SELECT LABEL,MD5HASHOFSIMULATORCONFIGURATION FROM apollo_service_simulator_run_cache"
                + " where MD5HASHOFSIMULATORCONFIGURATION LIKE " + "'" + md5HashOfSimulatorConfiguration + "'";

        PreparedStatement pstmt = getConn().prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        String runId = null;
        while (rs.next()) {
            runId = rs.getString("LABEL");
        }

        return runId;
    }

    public static void deleteFromRunCache(String md5HashOfSimulatorConfiguration) throws ClassNotFoundException, SQLException {

        String query = "DELETE FROM apollo_service_simulator_run_cache WHERE MD5HASHOFSIMULATORCONFIGURATION = '"
                + md5HashOfSimulatorConfiguration + "'";

        PreparedStatement pstmt = getConn().prepareStatement(query);
        pstmt.execute();
    }

    public static String insertIntoVisualizerCache(String runId, String md5HashOfVisualizerConfiguration) throws SQLException, ClassNotFoundException {

        String query = "INSERT INTO apollo_service_visualizer_cache (LABEL, MD5HASHOFCONFIGURATION) "
                + "VALUES ('" + runId + "', '" + md5HashOfVisualizerConfiguration + "')";

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

    public static void insertIntoVisualizerResultsCache(String cacheId, List<UrlOutputResource> urlList) throws ClassNotFoundException, SQLException {

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

    public static VisualizerResult checkVisualizerCache(String md5HashOfVisualizerConfiguration) throws SQLException, ClassNotFoundException {

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

    public static void deleteFromVisualizerCache(String md5HashOfVisualizerConfiguration) throws ClassNotFoundException, SQLException {

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
        
        query = "DELETE FROM apollo_service_visualizer_cache WHERE MD5HASHOFCONFIGURATION = '" + md5HashOfVisualizerConfiguration + "'";
        pstmt = getConn().prepareStatement(query);
        pstmt.execute();
    }
}
