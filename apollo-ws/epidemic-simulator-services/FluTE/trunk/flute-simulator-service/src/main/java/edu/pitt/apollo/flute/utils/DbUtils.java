package edu.pitt.apollo.flute.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DbUtils {

    static Connection dbcon = null;
    static final ResourceBundle DATABASE_PROPERTIES = ResourceBundle.getBundle("connections");

    private static void establishDbConn() throws ClassNotFoundException,
            SQLException {

        String dbClass = DATABASE_PROPERTIES.getString("db_class");
        String url = DATABASE_PROPERTIES.getString("db_url");
        String user = DATABASE_PROPERTIES.getString("db_user");
        String password = DATABASE_PROPERTIES.getString("db_password");

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
                    + url + " using username " + user
                    + ".   Specific error was:\n" + e.getMessage());
        }
    }

    /**
     * 
     * @return
     * @throws ClassNotFoundException
     *             - if mysql jdbc driver is missing
     * @throws SQLException
     *             - if a database connection cannot be established
     */
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

    public static void createAxisId(String label)
            throws ClassNotFoundException, SQLException {
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

    public static int getAxisId(String label) throws SQLException,
            ClassNotFoundException {

        int id = -1;

        try {
            String query = "Select id from population_axis where label like ?";
            PreparedStatement pstmt = getConn().prepareStatement(query);

            pstmt.setString(1, "%" + label + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt(1);
                // System.out.println(id);
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
            throw new SQLException("Error creating axis id for label: " + label
                    + ".   Specific error was:\n" + e.getMessage());

        }
        return id;

    }

    // public static void getAllRunIds() throws ClassNotFoundException,
    // SQLException {
    //
    // int internalRunId = -1;
    // try {
    // String query = "Select id, label from run";
    //
    // Statement stmt = getConn().createStatement();
    //
    // ResultSet rs = stmt.executeQuery(query);
    //
    // while (rs.next()) {
    // // System.out.println(rs.getInt(1) + "," + rs.getString(2));
    // } // end while
    //
    // } catch (SQLException e) {
    // throw new SQLException(
    // "Error retreiving all run IDs from the database.  Specific error was:"
    // + e.getMessage());
    // }
    //
    // }
    public static int getInternalRunId(String runId, String md5Hash,
            String simConfigJson) throws SQLException, ClassNotFoundException {

        int internalRunId = -1;

        String query = "INSERT INTO run (LABEL, MD5HASHOFCONFIGURATIONFILE, CONFIGURATIONFILE) VALUES ('"
                + runId + "', '" + md5Hash + "', '" + simConfigJson + "')";

        Connection conn = getConn();
        Statement stmt = conn.createStatement();

        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new SQLException(
                    "Error inserting run configuration into database.   Specific error was:\n"
                    + e.getMessage());
        }

        query = "SELECT ID FROM run WHERE LABEL = ?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, runId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // System.out.println("Internal id is:" + rs.getInt(1));
                internalRunId = rs.getInt(1);
            } // end while
        } catch (SQLException e) {
            throw new SQLException(
                    "Error retreiving run id from database for runId: " + runId
                    + ".   Specific error was:\n" + e.getMessage());
        }
        return internalRunId;

    }

    public static Map<String, String> getStoredRunHashes() throws SQLException,
            ClassNotFoundException {
        try {
            String query = "SELECT LABEL,MD5HASHOFCONFIGURATIONFILE from run";
            PreparedStatement pstmt = getConn().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            Map<String, String> hashLabelMap = new HashMap<String, String>();
            while (rs.next()) {
                String hash = rs.getString("md5HashOfConfigurationFile");
                String label = rs.getString("label");
                if (hash != null) {
                    hashLabelMap.put(hash, label);
                }
            }
            return hashLabelMap;
        } catch (SQLException e) {
            throw new SQLException(
                    "Error retreiving all stored run hashes. Specific error was:\n"
                    + e.getMessage());
        }
    }

    public static String getConfigurationFileJson(String runId)
            throws SQLException, ClassNotFoundException {
        try {
            String query = "SELECT CONFIGURATIONFILE from run where LABEL like '"
                    + runId + "'";
            PreparedStatement pstmt = getConn().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            String json = null;
            while (rs.next()) {
                json = rs.getString("configurationFile");
                if (!rs.isLast()) {
                    System.err.println("Warning: multiple rows matching given run ID"
                            + runId + " found");
                }
            }
            return json;
        } catch (SQLException e) {
            throw new SQLException(
                    "Error retreiving configuration file for runId: " + runId
                    + ". Specific error was:\n" + e.getMessage());
        }
    }

    public static int getPopulationId(String disease_state)
            throws ClassNotFoundException, SQLException {
        int popId = -1;

        try {
            String query = "select id from simulated_population where label like ?";
            PreparedStatement pstmt = getConn().prepareStatement(query);
            pstmt.setString(1, disease_state);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // System.out.println("Internal id is:" + rs.getInt(1));
                popId = rs.getInt(1);

            } // end while

        } catch (SQLException e) {
            throw new SQLException("Error retreiving population id for: "
                    + disease_state
                    + " from simulated_population.  Specific error was:\n"
                    + e.getMessage());
        }
        return popId;

    }

    public static int getOrCreatePopulationId(int axisId, String disease_state)
            throws ClassNotFoundException, SQLException {
        int popId = -1;

        popId = getPopulationId(disease_state);

        if (popId == -1) {
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
                    // System.out.println("Population id is:" +
                    // rs.getInt(1));
                    popId = rs.getInt(1);
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

    public static int getOrCreatePopulationId(int diseaseStateAxisId, int locationAxisId,
            String disease_state, String location)
            throws ClassNotFoundException, SQLException {
        int popId = -1;

        popId = getPopulationId(disease_state + " in " + location);

        if (popId == -1) {
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
                    // System.out.println("Population id is:" +
                    // rs.getInt(1));
                    popId = rs.getInt(1);
                } // end while
            } catch (SQLException e) {
                throw new SQLException(
                        "Error retreiving ID from simulated_population for label: "
                        + disease_state + "."
                        + "   Specific error was:\n" + e.getMessage());
            }

            // there needs to be 2 inserts, one for the disease state, and one for the regionId
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

    public static int getOrCreatePopulationId(int diseaseStateAxisId, int locationAxisId, int ageRangeAxisId,
            String disease_state, String location, String ageRange)
            throws ClassNotFoundException, SQLException {
        int popId = -1;

        popId = getPopulationId(disease_state + " in " + location + " age " + ageRange);

        if (popId == -1) {
            try {
                String query = "INSERT INTO simulated_population (LABEL) VALUES ('"
                        + disease_state + " in " + location + " age " + ageRange + "')";

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
                pstmt.setString(1, disease_state + " in " + location + " age " + ageRange);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    // System.out.println("Population id is:" +
                    // rs.getInt(1));
                    popId = rs.getInt(1);
                } // end while
            } catch (SQLException e) {
                throw new SQLException(
                        "Error retreiving ID from simulated_population for label: "
                        + disease_state + "."
                        + "   Specific error was:\n" + e.getMessage());
            }

            // there needs to be 3 inserts, one for the disease state, and one for the regionId, one for age range
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

                // age range
                query = "INSERT INTO simulated_population_axis_value (population_id, axis_id, value) values (?,?,?)";
                pstmt = getConn().prepareStatement(query);
                pstmt.setInt(1, popId);
                pstmt.setInt(2, ageRangeAxisId);
                pstmt.setString(3, ageRange);
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

    public static void insertTimeSeries(int runId, int popId,
            String label, List<Double> ts) throws SQLException,
            ClassNotFoundException {

        try {
            getConn().setAutoCommit(false);
            PreparedStatement pstmt = getConn().prepareStatement(
                    "INSERT INTO time_series (run_id, population_id, time_step, pop_count) VALUES (?,?,?,?)");
            for (int i = 0; i < ts.size(); i++) {
                pstmt.setInt(1, runId);
                pstmt.setInt(2, popId);
                pstmt.setInt(3, i);
                pstmt.setDouble(4, ts.get(i));
                pstmt.execute();
            }
            getConn().commit();
        } catch (SQLException e) {
            throw new SQLException(
                    "Error inserting disease state time series for internal run id: "
                    + runId + ", label: " + label
                    + ".   Specific error was:\n" + e.getMessage());
        }

    }

    public static void main(String args[]) throws Exception {
        int dsId = (DbUtils.getAxisId("disease_state"));
        System.out.println("Disease_State ID is:" + dsId);
        int popId = getOrCreatePopulationId(dsId, "infectious");
        System.out.println("Population ID is:" + popId);
        // DbUtils.getAllRunIds();
        // DbUtils.getInternalRunId("JUST_A_TEST2");
    } // end main
    // public static String isRunCached(SimulatorConfiguration sc) {
    // //create MD5
    // //query for MD5
    // //if exists, then return external runId
    // //if not, return null
    // }
} // end class