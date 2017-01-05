package edu.pitt.apollo.database;

import edu.pitt.apollo.db.BaseDbUtils;
import edu.pitt.apollo.db.exceptions.*;
import edu.pitt.apollo.db.exceptions.library.NoLibraryItemException;
import edu.pitt.apollo.db.exceptions.library.NoURNFoundException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_1.*;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.types.v4_0_1.*;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.isg.objectserializer.JsonUtils;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.sql.*;
import java.util.*;

import static edu.pitt.apollo.GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Oct 6, 2014 Time: 11:21:38 AM Class: LibraryDbUtils
 */
public class LibraryDbUtils extends BaseDbUtils {

    private class QueryResult {
        public List<String> table;
        public List<String> columnNames;
    }

    private static final Logger libraryLogger = LoggerFactory.getLogger(LibraryDbUtils.class);
    private static final String LIBRARY_DATABASE_PROPERTIES_FILE = "library_database.properties";
    private static final String LIBRARY_SALT_FILE = "library_salt.txt";
    private static final String ADDING_USER = "adding the user";
    private static final String ADDING_USER_role_id = "adding the user role_id";
    private static final String AUTHORIZING_USER = "authorizing the user";
    private static final String GETTING_URNS = "getting the list of URNs";
    private static final String ADDING_LIBARY_ITEM = "adding the item";
    private static final String UPDATING_LIBRARY_ITEM = "updating the item";
    private static final String ADDING_REVIEWER_COMMENT = "adding the reviewer comment";
    private static final String GETTING_COMMENTS = "getting the comments for the item";
    private static final String SETTING_RELEASE_VERSION = "setting the release version";
    private static final String GETTING_VERSIONS = "getting the versions of the item";
    private static final String GETTING_RELEASE_VERSION = "gettting the release version for the item";
    private static final String GETTING_LIBRARY_ITEM_CONTAINER = "getting the library item container";
    private static final String QUERYING = "executing the custom query";
    private static final String GETTING_CHANGE_LOG = "getting the change log for library items modeified since the specified date and time";
    private static final String SETTING_ITEM_AS_NOT_RELEASED = "setting the library item as not released";
    private static final boolean LIBRARY_AUTO_COMMIT = false;
    private static final String LIBRARY_DB_RESOURCE_NAME = "ApolloLibraryDB_400";
    protected static Logger logger = LoggerFactory.getLogger(LibraryDbUtils.class);


    JsonUtils jsonUtils = new JsonUtils(Arrays.asList(ApolloClassList.classList));

    public LibraryDbUtils() throws ApolloDatabaseException {
        super(LIBRARY_DB_RESOURCE_NAME);

    }

    @Override
    protected void setBaseDirectory() {
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
    }

    @Override
    protected String getDatabasePropertiesFileName() {
        return LIBRARY_DATABASE_PROPERTIES_FILE;
    }

    @Override
    protected void setupResource(String resourceName) {
        try {
            Properties properties = new Properties();
            properties.load(new BufferedReader(new FileReader(APOLLO_DIR + LIBRARY_DATABASE_PROPERTIES_FILE)));

            PGSimpleDataSource ds = new PGSimpleDataSource();

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

    protected LibraryDbUtils(String resourceName) throws ApolloDatabaseException {
        super(resourceName);
    }

    private String getJSONStringForLibraryItem(Object obj) throws JAXBException {
        String itemJson = jsonUtils.getJsonBytes(obj).toString();
        return itemJson;
    }

    private ApolloDatabaseException createApolloDatabaseExceptionAndLog(String actionThatWasToBePerformed, Throwable exception) {
        libraryLogger.error(exception.getMessage());
        return new ApolloDatabaseException("There was an error " + actionThatWasToBePerformed + ".");
    }

    private LibraryItemContainer getLibraryItemContainderFromJson(String json) throws IOException {
        try {

            return (LibraryItemContainer) jsonUtils.getObjectFromJson(json, LibraryItemContainer.class);
        } catch (Exception e) {
            System.err.println("Exception encoding library item container to JSON.  Error message was: "
                    + e.getMessage());
            return null;
        }
    }

    private int addUser(String userId) throws ApolloDatabaseException, ApolloDatabaseExplicitException {

        String query = "INSERT INTO users (user_id) VALUES (?)";
        try (Connection conn = getConnection(false)) {

            try {
                PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, userId);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet rs = pstmt.getGeneratedKeys();
                    rs.next();
                    conn.commit();
                    return rs.getInt(1);
                } else {
                    throw new ApolloDatabaseExplicitException("Attempted to add user but no rows were returned");
                }
            } catch (SQLException ex) {
                libraryLogger.error("Rolling back due to SQLException: " + ex.getMessage());
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    throw createApolloDatabaseExceptionAndLog(ADDING_USER, ex1);
                }
                throw createApolloDatabaseExceptionAndLog(ADDING_USER, ex);
            }
        } catch (SQLException ex) {
            throw createApolloDatabaseExceptionAndLog(ADDING_USER, ex);
        }
    }

    private int getOrAddUserId(String userId, Connection existingConnection) throws ApolloDatabaseException,
            ApolloDatabaseExplicitException {

        String query = "SELECT id FROM users WHERE user_id = ?";

        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return addUser(userId);
            }
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException attempting to get user ID: " + ex.getMessage());
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }
    }

    private int getActionId(LibraryActionTypeEnum action, Connection existingConnection) throws ApolloDatabaseExplicitException {

        String sql = "SELECT id FROM library_actions WHERE action = ?";
        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, action.toString().toLowerCase());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException getting action ID: " + ex.getMessage());
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }

        throw new ApolloDatabaseExplicitException("No action ID matched the given action");
    }

    private void insertActionPerformed(int catalogId, int version, Timestamp date, LibraryActionTypeEnum action, Connection existingConnection) throws ApolloDatabaseExplicitException {

        String sql = "INSERT INTO library_item_action_history (urn_id, version, action_performed, date_of_action) VALUES (?,?,?,?);";

        int actionId = getActionId(action, existingConnection);
        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, catalogId);
            pstmt.setInt(2, version);
            pstmt.setInt(3, actionId);
            pstmt.setTimestamp(4, date);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException inserting action performed: " + ex.getMessage());
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }
    }

    private Timestamp getDateOfItem(int catalogId, int version, Connection existingConnection) throws ApolloDatabaseExplicitException {
        // need to get the date of the insert
        String sql = "SELECT id,date_created FROM library_item_containers WHERE urn_id = " + catalogId + " AND version = " + version;
        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            Timestamp date;
            if (rs.next()) {
                date = rs.getTimestamp(2);
                return date;
            } else {
                throw new ApolloDatabaseExplicitException("The inserted item was not found in the library_item_containers table");
            }
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException getting ID and date for item: " + ex.getMessage());
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }
    }

    public List<Integer> getURNs(String itemType) throws ApolloDatabaseException {
        List<Integer> urns = new ArrayList<>();
        String query;
        if (itemType == null) {
            query = "SELECT id FROM library_item_container_urns";
        } else {
            query = "SELECT id FROM library_item_container_urns WHERE id in ("
                    + "SELECT urn_id FROM library_item_containers WHERE json_representation->'libraryItem'->>'type' = '" + itemType + "')";
        }
        try (Connection conn = getConnection(false)) {

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                urns.add(rs.getInt(1));
            }

            return urns;
        } catch (SQLException ex) {
            throw createApolloDatabaseExceptionAndLog(GETTING_URNS, ex);
        }
    }

    public AddLibraryItemContainerResult addLibraryItem(LibraryItemContainer item, String userIdentification, String commitMessage, int role_id) throws ApolloDatabaseException, UserNotAuthorizedException {
        // user has already been authenticated

        checkrole_idForAddingItemPermission(role_id);

        try (Connection conn = getConnection(false)) {

//			boolean itemAlreadyExists = checkIfUrnAlreadyExists(urn);
//			if (itemAlreadyExists) {
//				throw new ApolloDatabaseException("There was an error " + ADDING_LIBARY_ITEM
//						+ ". The specified URI \"" + urn + "\" already exists in the library.");
//			}

            String sql = "INSERT INTO library_item_container_urns (role_id) VALUES (?)";

            try {
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, role_id);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                int catalogId;
                if (rs.next()) {
                    catalogId = rs.getInt(1);
                } else {
                    throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, new ApolloDatabaseException("No ID was returned "
                            + " when adding the item to the Apollo library."));
                }

                int itemVersion = updateLibraryItem(catalogId, item, userIdentification, conn);
                addComment(catalogId, itemVersion, commitMessage, userIdentification, LibraryCommentTypeEnum.COMMIT, conn);

                Timestamp date = getDateOfItem(catalogId, itemVersion, conn);

                insertActionPerformed(catalogId, itemVersion, date, LibraryActionTypeEnum.ADDED_ITEM, conn);

                conn.commit();

                AddLibraryItemContainerResult result = new AddLibraryItemContainerResult();
                result.setUrn(catalogId);
                result.setVersion(itemVersion);

                return result;
            } catch (SQLException ex) {
                libraryLogger.error("Rolling back due to SQLException: " + ex.getMessage());
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex1);
                }
                throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex);
            } catch (ApolloDatabaseKeyNotFoundException | ApolloDatabaseRecordNotInsertedException
                    | ApolloDatabaseUserPasswordException | ApolloDatabaseExplicitException | NoLibraryItemException ex) {
                throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex);
            }
        } catch (SQLException ex) {
            throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex);
        }
    }

    public int updateLibraryItem(int urn, int role_id, LibraryItemContainer item, String userIdentification, String comment) throws ApolloDatabaseException, UserNotAuthorizedException, NoLibraryItemException, ApolloDatabaseExplicitException {

        checkItemForrole_idLevel(urn, role_id);

        try (Connection conn = getConnection(false)) {
//			int catalogId = getCatalogIDFromURN(urn);
            int version = updateLibraryItem(urn, item, userIdentification, conn);
            addComment(urn, version, comment, userIdentification, LibraryCommentTypeEnum.COMMIT, conn);

            Timestamp date = getDateOfItem(urn, version, conn);

            insertActionPerformed(urn, version, date, LibraryActionTypeEnum.UPDATED_ITEM, conn);

            conn.commit();
            return version;
        } catch (ApolloDatabaseExplicitException | ApolloDatabaseRecordNotInsertedException | ApolloDatabaseUserPasswordException | NoLibraryItemException | SQLException ex) {
            throw createApolloDatabaseExceptionAndLog(UPDATING_LIBRARY_ITEM, ex);
        }
    }

    private int updateLibraryItem(int catalogId, LibraryItemContainer item, String userIdentification, Connection existingConnection) throws ApolloDatabaseException,
            ApolloDatabaseExplicitException, UserNotAuthorizedException, NoLibraryItemException {

        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);
            int userKey = getOrAddUserId(userIdentification, conn);

            // set the java class name for the library item
            item.getCatalogEntry().setJavaClassName(item.getLibraryItem().getClass().getName());


            String itemJson = getJSONStringForLibraryItem(item);
            String sql = "INSERT INTO library_item_containers (urn_id,json_representation,committer_id) VALUES (?,?::jsonb,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, catalogId);
            pstmt.setString(2, itemJson);
            pstmt.setInt(3, userKey);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new ApolloDatabaseRecordNotInsertedException("Catalog item insert did not return an id");
            }

            return rs.getInt("version");
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException updating library item: " + ex.getMessage());
        } catch (JAXBException ex) {
            throw new ApolloDatabaseExplicitException("JAXBException updating library item: " + ex.getMessage());
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }
    }

    private int getCommentTypeId(LibraryCommentTypeEnum commentType, Connection existingConnection) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException {

        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);
            String query = "SELECT id FROM comment_type WHERE description = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, commentType.toString().toLowerCase());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new ApolloDatabaseKeyNotFoundException("No comment type with description " + commentType.toString().toLowerCase() + " exists");
            }
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException getting comment type id: " + ex.getMessage());
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }

    }

    public void addReviewerComment(int urn, int version, String comment, String userIdentification, int role_id) throws ApolloDatabaseException, UserNotAuthorizedException, ApolloDatabaseExplicitException, NoLibraryItemException {
        checkItemForrole_idLevel(urn, role_id);
        try (Connection conn = getConnection(false)) {
            try {
                addComment(urn, version, comment, userIdentification, LibraryCommentTypeEnum.REVIEW, conn);

                Timestamp date = getDateOfItem(urn, version, conn);
                insertActionPerformed(urn, version, date, LibraryActionTypeEnum.ADDED_REVIEWER_COMMENT, conn);

                conn.commit();
            } catch (ApolloDatabaseKeyNotFoundException | ApolloDatabaseExplicitException | ApolloDatabaseUserPasswordException ex) {
                throw createApolloDatabaseExceptionAndLog(ADDING_REVIEWER_COMMENT, ex);
            } catch (NoLibraryItemException ex) {
                throw new ApolloDatabaseException("There was an error " + ADDING_REVIEWER_COMMENT
                        + ". There is no item with the specified URN: \"" + urn + "\" and version " + version);
            } catch (SQLException ex) {
                libraryLogger.error("Rolling back due to SQLException: " + ex.getMessage());
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    throw createApolloDatabaseExceptionAndLog(ADDING_REVIEWER_COMMENT, ex1);
                }
                throw createApolloDatabaseExceptionAndLog(ADDING_REVIEWER_COMMENT, ex);
            }
        } catch (SQLException ex) {
            throw createApolloDatabaseExceptionAndLog(ADDING_REVIEWER_COMMENT, ex);
        }
    }

    private void addComment(int catalogId, int version, String comment, String userIdentification, LibraryCommentTypeEnum commentEnum, Connection existingConnection)
            throws ApolloDatabaseExplicitException, ApolloDatabaseException, NoLibraryItemException, UserNotAuthorizedException {

        // check catalogId and itemVersion
        int itemId = getItemIdFromCatalogIdAndVersion(catalogId, version, existingConnection);
        int userId = getOrAddUserId(userIdentification, existingConnection);
        int commentTypeId = getCommentTypeId(commentEnum, existingConnection);
        String sql = "INSERT INTO comments (item_id,comment,comment_type,user_id) VALUES (?,?,?,?)";
        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            pstmt.setString(2, comment);
            pstmt.setInt(3, commentTypeId);
            pstmt.setInt(4, userId);

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException adding comment for catalog_uuid " + catalogId + " and version " + version + ": " + ex.getMessage());
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }
    }

    public LibraryCommentTypeEnum getCommentTypeFromId(int id) throws SQLException, ApolloDatabaseExplicitException {

        String sql = "SELECT description FROM comment_type WHERE id = " + id;

        try (Connection conn = getConnection(false)) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                return LibraryCommentTypeEnum.valueOf(rs.getString(1).toUpperCase());
            }
        }

        throw new ApolloDatabaseExplicitException("There was no comment type with ID " + id);
    }

    public GetCommentsResult getComments(int urn, int version, int role_id) throws ApolloDatabaseException, UserNotAuthorizedException, NoLibraryItemException, ApolloDatabaseExplicitException {

        checkItemForrole_idLevel(urn, role_id);

        try (Connection conn = getConnection(false)) {
            int itemId = getItemIdFromCatalogIdAndVersion(urn, version, conn);
            String sql = "SELECT date_created,comment,comment_type,user_id FROM comments WHERE item_id = " + itemId;

            GetCommentsResult result = new GetCommentsResult();

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Timestamp date = rs.getTimestamp(1);

                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTimeInMillis(date.getTime());
                XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                        calendar);

                String comment = rs.getString(2);
                int commentTypeId = rs.getInt(3);
                LibraryCommentTypeEnum commentTypeEnum = getCommentTypeFromId(commentTypeId);
                String userId = rs.getString(4);

                CommentFromLibrary commentFomLibrary = new CommentFromLibrary();
                commentFomLibrary.setComment(comment);
                commentFomLibrary.setCommenter(userId);

                commentFomLibrary.setTime(xmlCal);

                if (commentTypeEnum.equals(LibraryCommentTypeEnum.COMMIT)) {
                    result.setCommitComment(commentFomLibrary);
                } else if (commentTypeEnum.equals(LibraryCommentTypeEnum.REVIEW)) {
                    result.getReviewerComments().add(commentFomLibrary);
                } else if (commentTypeEnum.equals(LibraryCommentTypeEnum.RELEASE)) {
                    result.getReleaseVersionComments().add(commentFomLibrary);
                }
            }

            return result;

        } catch (SQLException | DatatypeConfigurationException | IllegalArgumentException | ApolloDatabaseKeyNotFoundException | ApolloDatabaseExplicitException ex) {
            throw createApolloDatabaseExceptionAndLog(GETTING_COMMENTS, ex);
        } catch (NoLibraryItemException ex) {
            throw new ApolloDatabaseException("There was an error " + GETTING_COMMENTS
                    + ". There is no item with the specified URN: \"" + urn + "\" and version " + version);
        }
    }

    public void setReleaseVersion(int urn, int version, int role_id, String userIdentification, String message) throws ApolloDatabaseException, UserNotAuthorizedException, ApolloDatabaseExplicitException, NoLibraryItemException {

        checkItemForrole_idLevel(urn, role_id);

        try (Connection conn = getConnection(false)) {
//			int catalogId = getCatalogIDFromURN(urn);
            try {
                setReleaseVersion(urn, version, conn);
                addComment(urn, version, message, userIdentification, LibraryCommentTypeEnum.RELEASE, conn);

                Timestamp date = getDateOfItem(urn, version, conn);
                insertActionPerformed(urn, version, date, LibraryActionTypeEnum.SET_AS_RELEASE_VERSION, conn);

                conn.commit();
            } catch (ApolloDatabaseExplicitException | ApolloDatabaseKeyNotFoundException | ApolloDatabaseUserPasswordException ex) {
                throw createApolloDatabaseExceptionAndLog(SETTING_RELEASE_VERSION, ex);
            } catch (NoLibraryItemException ex) {
                throw new ApolloDatabaseException("There was an error " + SETTING_RELEASE_VERSION
                        + ". There is no item with the specified URN: \"" + urn + "\" and version " + version);
            } catch (SQLException ex) {
                libraryLogger.error("Rolling back due to SQLException: " + ex.getMessage());
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    throw createApolloDatabaseExceptionAndLog(SETTING_RELEASE_VERSION, ex1);
                }
            }
        } catch (SQLException ex) {
            throw createApolloDatabaseExceptionAndLog(SETTING_RELEASE_VERSION, ex);
        }
    }

    private void setReleaseVersion(int catalogId, int version, Connection existingConnection) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException,
            NoLibraryItemException, ApolloDatabaseException, UserNotAuthorizedException {

        int itemId = getItemIdFromCatalogIdAndVersion(catalogId, version, existingConnection);
        String sql = "UPDATE library_item_containers SET is_latest_release_version = true WHERE id = " + itemId;

        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);
            clearPreviousReleaseVersion(catalogId, conn);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            if (pstmt.getUpdateCount() == 0) {
                throw new ApolloDatabaseException("There was an error setting the release version. You specified a version of the object that does not exist");
            }

        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException setting public version for urn_id " + catalogId + " and version " + version + ": " + ex.getMessage());
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }
    }

    private void clearPreviousReleaseVersion(int catalogId, Connection existingConnection) throws ApolloDatabaseExplicitException {

        String sql = "UPDATE library_item_containers SET is_latest_release_version = false, was_previously_released = true WHERE urn_id = " + catalogId
                + " AND is_latest_release_version = true";
        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException setting public version for urn_id " + catalogId + ": " + ex.getMessage());
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }
    }

    public int setLibraryItemAsNotReleased(int urn, int role_id) throws ApolloDatabaseException, UserNotAuthorizedException, NoLibraryItemException {

        try {
            checkItemForrole_idLevel(urn, role_id);
            Integer currentReleaseVersion = getReleaseVersion(urn);
            if (currentReleaseVersion == null) {
                return -1;
            } else {
//				int catalogId = getCatalogIDFromURN(urn);
                clearPreviousReleaseVersion(urn, null);
                return currentReleaseVersion;
            }
        } catch (ApolloDatabaseExplicitException ex) {
            throw createApolloDatabaseExceptionAndLog(SETTING_ITEM_AS_NOT_RELEASED, ex);
//		} catch (NoURNFoundException ex) {
//			throw createApolloDatabaseExceptionAndLog(SETTING_ITEM_AS_NOT_RELEASED, ex);
        }
    }

    public List<RevisionAndComments> getRevisionsAndComments(int urn, int role_id) throws ApolloDatabaseException, ApolloDatabaseExplicitException, NoLibraryItemException, UserNotAuthorizedException {

        checkItemForrole_idLevel(urn, role_id);

        try (Connection conn = getConnection(false)) {
            String sql = "SELECT lics.version, cmts.comment, cmts.date_created, users.user_name " +
                    "FROM library_item_containers lics, comments cmts, users WHERE urn_id = " + urn +
                    " AND cmts.item_id = lics.id AND users.id = cmts.user_id";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            Map<Integer, List<CommentFromLibrary>> revisionsAndComments = new HashMap<>();

            while (rs.next()) {
                int version = rs.getInt(1);
                String comment = rs.getString(2);

                Timestamp date = rs.getTimestamp(3);

                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTimeInMillis(date.getTime());
                XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                        calendar);

                String username = rs.getString(4);

                CommentFromLibrary commentFromLibrary = new CommentFromLibrary();
                commentFromLibrary.setComment(comment);
                commentFromLibrary.setCommenter(username);
                commentFromLibrary.setTime(xmlCal);

                if (revisionsAndComments.containsKey(version)) {
                    revisionsAndComments.get(version).add(commentFromLibrary);
                } else {
                    List<CommentFromLibrary> list = new ArrayList<>();
                    list.add(commentFromLibrary);
                    revisionsAndComments.put(version, list);
                }
            }

            List<RevisionAndComments> listOfRevisionsAndComments = new ArrayList<>();
            for (Integer revision : revisionsAndComments.keySet()) {
                RevisionAndComments revisionAndComments = new RevisionAndComments();
                revisionAndComments.setRevision(revision);
                revisionAndComments.getComments().addAll(revisionsAndComments.get(revision));
                listOfRevisionsAndComments.add(revisionAndComments);
            }

            return listOfRevisionsAndComments;
        } catch (SQLException | DatatypeConfigurationException ex) {
            throw createApolloDatabaseExceptionAndLog(GETTING_VERSIONS, ex);
        }

    }

    public Integer getReleaseVersion(int urn) throws ApolloDatabaseException {

        try (Connection conn = getConnection(false)) {
            String sql = "SELECT version FROM library_item_containers WHERE is_latest_release_version = true AND urn_id = " + urn;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            int version;
            if (rs.next()) {
                version = rs.getInt(1);
                return version;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw createApolloDatabaseExceptionAndLog(GETTING_RELEASE_VERSION, ex);
        }
    }

    public boolean checkIfVersionIsApproved(int urn, int version) throws ApolloDatabaseException {

        try (Connection conn = getConnection(false)) {
            String sql = "SELECT was_previously_released FROM library_item_containers WHERE urn_id = " + urn + " AND version = " + version;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean(1);
            } else {
                return false;
            }

        } catch (SQLException ex) {
            throw createApolloDatabaseExceptionAndLog(GETTING_RELEASE_VERSION, ex);
        }
    }

    public GetLibraryItemContainerResult getLibraryItemContainer(int urn, Integer version, int role_id) throws ApolloDatabaseException, UserNotAuthorizedException, NoLibraryItemException, ApolloDatabaseExplicitException {

        checkItemForrole_idLevel(urn, role_id);

        try {

            GetLibraryItemContainerResult result = new GetLibraryItemContainerResult();
            if (version == null) {
                version = getReleaseVersion(urn);
                result.setIsLatestApprovedRevision(true);
                result.setIsApprovedRevision(true);
            }

            String itemJson = getLibraryItemContainerJSON(urn, version, null);
            LibraryItemContainer container = getLibraryItemContainderFromJson(itemJson);

            if (!result.isIsLatestApprovedRevision()) {
                // check if it is an approved version
                result.setIsApprovedRevision(checkIfVersionIsApproved(urn, version));
            }

            result.setLibraryItemContainer(container);
            result.setRevision(version);

            return result;

        } catch (IOException | ApolloDatabaseExplicitException | ApolloDatabaseKeyNotFoundException ex) {
            throw createApolloDatabaseExceptionAndLog(GETTING_LIBRARY_ITEM_CONTAINER, ex);
        } catch (NoURNFoundException ex) {
            throw new ApolloDatabaseException("There was an error " + GETTING_LIBRARY_ITEM_CONTAINER + ". There is no item with the specified URN: \"" + urn + "\".");
        } catch (NoLibraryItemException ex) {
            throw new ApolloDatabaseException("There was an error " + GETTING_LIBRARY_ITEM_CONTAINER
                    + ". There is no item with the specified URN: \"" + urn + "\" and version " + version);
        }
    }

//	public LibraryItemContainer getReleaseLibraryItemContainer(int urn) throws ApolloDatabaseException {
//
//		Integer publicVersion = getReleaseVersion(urn);
//		if (publicVersion == null) {
//			return null;
//		} else {
//			return getLibraryItemContainer(urn, publicVersion);
//		}
//	}

    private String getLibraryItemContainerJSON(int urn, int version, Connection existingConnection) throws ApolloDatabaseKeyNotFoundException,
            ApolloDatabaseExplicitException, NoURNFoundException, NoLibraryItemException, UserNotAuthorizedException {
        return getItemJSONFromCatalogIdAndVersion(version, urn, existingConnection);
    }

    private String getItemJSONFromCatalogIdAndVersion(int versionId, int urn, Connection existingConnection) throws ApolloDatabaseKeyNotFoundException,
            ApolloDatabaseExplicitException, NoLibraryItemException, UserNotAuthorizedException {
        String sql = "SELECT json_representation FROM library_item_containers WHERE urn_id = " + urn + " AND version = " + versionId;
        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new NoLibraryItemException("No library item was found with URN \"" + urn + "\" and version " + versionId);
            }

            String json = rs.getString(1);
            return json;
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException checking library_item_container_urns table");
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }
    }

    private int getItemIdFromCatalogIdAndVersion(int catalogId, int versionId, Connection existingConnection) throws ApolloDatabaseKeyNotFoundException,
            ApolloDatabaseExplicitException, NoLibraryItemException, UserNotAuthorizedException {
        String sql = "SELECT id FROM library_item_containers WHERE urn_id = " + catalogId + " AND version = " + versionId;
        Connection conn = null;
        try {
            conn = existingConnection != null ? existingConnection : getConnection(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new NoLibraryItemException("No library item was found with URN \"" + catalogId + "\" and version " + versionId);
            }

            int id = rs.getInt(1);
            return id;
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException checking library_item_containers table");
        } finally {
            if (existingConnection == null)
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    //this is okay.
                }
        }
    }

    public List<ChangeLogEntry> getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime, int role_id) throws ApolloDatabaseException {

        Timestamp timestamp = new Timestamp(dateTime.toGregorianCalendar().getTimeInMillis());

        try (Connection conn = getConnection(false)) {
            String sql = "SELECT library_item_action_history.version, library_item_action_history.date_of_action, library_item_container_urns.id, library_actions.action "
                    + "FROM library_item_action_history "
                    + "INNER JOIN library_item_container_urns "
                    + "ON library_item_action_history.urn_id = library_item_container_urns.id "
                    + "INNER JOIN library_actions "
                    + "ON library_item_action_history.action_performed = library_actions.id "
                    + "WHERE library_item_action_history.date_of_action >= ? AND library_item_container_urns.role_id <= " + role_id;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, timestamp);
            ResultSet rs = pstmt.executeQuery();

            List<ChangeLogEntry> entries = new ArrayList<>();
            while (rs.next()) {
                int version = rs.getInt(1);
                Timestamp actionTime = rs.getTimestamp(2);
                int urn = rs.getInt(3);
                LibraryActionTypeEnum actionType = LibraryActionTypeEnum.valueOf(rs.getString(4).toUpperCase());

                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTimeInMillis(actionTime.getTime());
                XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                        calendar);

                ChangeLogEntry entry = new ChangeLogEntry();
                entry.setUrn(urn);
                entry.setVersion(version);
                entry.setActionPerformed(actionType);
                entry.setDateOfAction(xmlCal);
                entries.add(entry);
            }

            return entries;
        } catch (SQLException | DatatypeConfigurationException ex) {
            throw createApolloDatabaseExceptionAndLog(GETTING_CHANGE_LOG, ex);
        }
    }

    public GetCacheDataResult getCacheData() throws ApolloDatabaseException {

        GetCacheDataResult getCacheDataResult = new GetCacheDataResult();

        // apollo location codes
        String query = "SELECT\n"
                + "	json_representation -> 'libraryItem' ->> 'administrativeLocations' AS loc1,\n"
                + "    json_representation -> 'libraryItem' ->> 'epidemicZones' AS loc2,\n"
                + "	json_representation -> 'libraryItem' ->> 'location' AS loc3\n"
                + "FROM\n"
                + "	library_item_containers\n"
                + "WHERE\n"
                + "	((json_representation -> 'libraryItem' ->> 'administrativeLocations') IS NOT NULL\n"
                + "OR (json_representation -> 'libraryItem' ->> 'epidemicZones') IS NOT NULL\n"
                + "OR (json_representation -> 'libraryItem' ->> 'location') IS NOT NULL)";

        Set<String> locations = new HashSet<>();
        QueryResult result = queryObjects(query);
        List<String> table = result.table;
        List<String> columns = result.columnNames;
        int numColumns = columns.size();
        int numRows = table.size() / numColumns;

        for (int i = 0; i < numRows; i++) {
            String loc1 = table.get(i * numColumns);
            String loc2 = table.get(i * numColumns + 1);
            String loc3 = table.get(i * numColumns + 2);

            if (loc1 != null) {
                setLocationCodesFromJsonArray(loc1, locations);
            }
            if (loc2 != null) {
                setLocationCodesFromJsonArray(loc2, locations);
            }
            if (loc3 != null) {
                setLocationCodesFromJsonArray(loc3, locations);
            }
        }

        getCacheDataResult.getApolloLocationCodes().addAll(locations);

        Set<String> ncbiCodes = new HashSet<>();
        query = "SELECT  json_representation->'libraryItem'->'causalPathogens'->0->>'ncbiTaxonId' AS causalPathogen, "
                + "json_representation->'libraryItem'->'infections'->0->>'host' AS host, "
                + " json_representation->'libraryItem'->'infections'->0->'infectionAcquisitionsFromInfectedHosts'->0->>'infectedHost' AS infectedHost "
                + "FROM library_item_containers "
                + "WHERE json_representation->'libraryItem'->>'type' = 'Epidemic'";

        result = queryObjects(query);
        table = result.table;
        columns = result.columnNames;
        numColumns = columns.size();
        numRows = table.size() / numColumns;
        for (int i = 0; i < numRows; i++) {

            if (table.get(i * numColumns) != null) {
                ncbiCodes.add(table.get(i * numColumns));
            }

            if (table.get(i * numColumns + 2) != null) {
                ncbiCodes.add(table.get(i * numColumns + 2));
            }

            if (table.get(i * numColumns + 1) != null) {
                ncbiCodes.add(table.get(i * numColumns + 1));
            }
        }

        getCacheDataResult.getNcbiTaxonIds().addAll(ncbiCodes);
        return getCacheDataResult;
    }

    public List<LibraryItemContainer> getLibraryItemContainers(String className, boolean includeUnreleasedItems, int role_id) throws ApolloDatabaseException {
        String query = "SELECT json_representation "
                + "FROM library_item_containers "
                + "INNER JOIN library_item_container_urns "
                + "ON library_item_containers.urn_id = library_item_container_urns.id "
                + " WHERE json_representation->'libraryItem'->>'type' = '" + className;
        if (!includeUnreleasedItems) { // only select released items
            query += " AND is_latest_release_version = 'true' ";
        } else { // select items which can be used by the role_id
            query += " AND library_item_container_urns.role_id <= " + role_id;
        }

        QueryResult result = queryObjects(query);
        List<String> table = result.table;
        List<String> columns = result.columnNames;
        int numColumns = columns.size();
        int numRows = table.size() / numColumns;

        List<LibraryItemContainer> containers = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            String json = table.get(i * numColumns);
            try {
                LibraryItemContainer container = getLibraryItemContainderFromJson(json);
                containers.add(container);
            } catch (IOException ex) {
                throw new ApolloDatabaseException("Could not get LibraryItemContainer from json. Json string was: " + json);
            }
        }

        return containers;
    }

    public List<LibraryItemContainerAndURN> getCollections(String collectionClassName, boolean includeUnreleasedItems, int role_id) throws ApolloDatabaseException {
        List<LibraryItemContainerAndURN> libraryCollections = new ArrayList<>();

        String query = "SELECT\n"
                + "           distinct urn_id, version, json_representation\n"
                + "       FROM\n"
                + "           library_item_containers\n"
                + "       INNER JOIN library_item_container_urns "
                + "       ON library_item_containers.urn_id = library_item_container_urns.id "
                + "       WHERE\n"
                + "           json_representation -> 'libraryItem' ->> 'javaClassNameOfMembers' = '" + collectionClassName + "' AND\n"
                + "           json_representation -> 'libraryItem' @> '{\"type\":\"LibraryCollection\"}' = 't'\n";
        if (!includeUnreleasedItems) {
            query += "       AND is_latest_release_version = TRUE";
        } else {
            query += "           AND library_item_container_urns.role_id <= " + role_id + "\n";
        }

        QueryResult result = queryObjects(query);
        List<String> table = result.table;
        List<String> columns = result.columnNames;
        int numColumns = columns.size();
        int numRows = table.size() / numColumns;
        for (int i = 0; i < numRows; i++) {

            String json = table.get(i * numColumns + 2);

            LibraryItemContainerAndURN libraryItemAndUrn = new LibraryItemContainerAndURN();
            libraryItemAndUrn.setUrn(Integer.parseInt(table.get(i * numColumns)));
            libraryItemAndUrn.setVersion(Integer.parseInt(table.get(i * numColumns + 1)));
            try {
                libraryItemAndUrn.setLibraryItemContainer(getLibraryItemContainderFromJson(json));
            } catch (IOException ex) {
                throw new ApolloDatabaseException("Could not get LibraryItemContainer from json. Json string was: " + json);
            }

            libraryCollections.add(libraryItemAndUrn);
        }

        return libraryCollections;
    }

    public Map<String, Integer> getRoleIds() throws ApolloDatabaseException {

        Map<String, Integer> role_ids = new HashMap<>();
        String query = "SELECT\n"
                + "           id, role_id\n"
                + "       FROM\n"
                + "           role_ids\n";
        QueryResult result = queryObjects(query);
        List<String> table = result.table;
        List<String> columns = result.columnNames;
        int numColumns = columns.size();
        int numRows = table.size() / numColumns;
        for (int i = 0; i < numRows; i++) {
            int id = Integer.parseInt(table.get(i * numColumns));
            String role_id = table.get(i * numColumns + 1);
            role_ids.put(role_id, id);
        }

        return role_ids;
    }

    public List<LibraryItemContainerAndURN> getMembersOfCollection(int urn, int version,
                                                                   boolean includeUnreleasedItems, int role_id) throws ApolloDatabaseException, ApolloDatabaseExplicitException, NoLibraryItemException, UserNotAuthorizedException {
        if (includeUnreleasedItems) {
            checkItemForrole_idLevel(urn, role_id);
        }

        // THIS DOESN'T AUTO GENERATE TITLES FOR EPIDEMICS YET
        String query = "SELECT\n"
                + "     lic.urn_id,\n"
                + "	lic.json_representation,\n"
                + "  version\n"
                + "FROM\n"
                + "     library_item_containers lic,\n"
                + "     ( \n"
                + "          SELECT\n"
                + "               jsonb_array_elements (\n"
                + "                    json_representation -> 'libraryItem' -> 'membersOfCollection'\n"
                + "               ) as collection_member_urns\n"
                + "          FROM\n"
                + "               library_item_containers\n"
                + "       INNER JOIN library_item_container_urns "
                + "       ON library_item_containers.urn_id = library_item_container_urns.id "
                + "          WHERE\n"
                + "               urn_id = " + urn + "\n";
        if (!includeUnreleasedItems) { // show only released items
            query += "      AND is_latest_release_version = TRUE\n";
        } else { // only show items the role_id can see
            query += "AND library_item_container_urns.role_id <= " + role_id + "\n";
            query += "      AND version = " + version + "\n";
        }
        query += "     ) members\n"
                + "       INNER JOIN library_item_container_urns "
                + "       ON lic.urn_id = library_item_container_urns.id "
                + "WHERE\n"
                + "     lic.urn_id::text = members.collection_member_urns::text\n";
        if (!includeUnreleasedItems) { // show only released items
            query += "     AND lic.is_latest_release_version = TRUE\n";
        } else { // show only items the role_id can see
            query += "AND library_item_container_urns.role_id <= " + role_id;
        }

        List<LibraryItemContainerAndURN> libraryItemContainerAndURNs = new ArrayList<>();
        QueryResult result = queryObjects(query);
        List<String> table = result.table;
        List<String> columns = result.columnNames;
        int numColumns = columns.size();
        int numRows = table.size() / numColumns;
        for (int i = 0; i < numRows; i++) {
            LibraryItemContainerAndURN libraryItemAndUrn = new LibraryItemContainerAndURN();
            int itemUrn = Integer.parseInt(table.get(i * numColumns));
            libraryItemAndUrn.setUrn(itemUrn);
            String json = table.get(i * numColumns + 1);
            int itemVersion = Integer.parseInt(table.get(i * numColumns + 2));

            libraryItemAndUrn.setVersion(itemVersion);
            try {
                libraryItemAndUrn.setLibraryItemContainer(getLibraryItemContainderFromJson(json));
            } catch (IOException ex) {
                throw new ApolloDatabaseException("Could not get LibraryItemContainer from json. Json string was: " + json);
            }

            libraryItemContainerAndURNs.add(libraryItemAndUrn);
        }

        return libraryItemContainerAndURNs;
    }

    private static void setLocationCodesFromJsonArray(String jsonArray, Set<String> locationsSet) {
        jsonArray = jsonArray.replace("\"", "");
        jsonArray = jsonArray.replace("[", "");
        jsonArray = jsonArray.replace("]", "");
        String[] locationsArray = jsonArray.split(",");
        for (String loc : locationsArray) {
            locationsSet.add(loc.trim());
        }
    }

    private QueryResult queryObjects(String sql) throws ApolloDatabaseException {

        QueryResult result = new QueryResult();
        try (Connection conn = getConnection(false)) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numColumns = rsmd.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                String columnName = rsmd.getColumnName(i);
                result.columnNames.add(columnName);
            }

            List<String> resultSetDataAs1DArray = new ArrayList<>();
            while (rs.next()) {
                for (int column = 1; column <= numColumns; column++) {
                    String value = rs.getString(column);
                    resultSetDataAs1DArray.add(value);
                }
            }

            result.table.addAll(resultSetDataAs1DArray);
            return result;
        } catch (SQLException ex) {
            throw createApolloDatabaseExceptionAndLog(QUERYING, ex);
        }
    }

    private void checkrole_idForAddingItemPermission(int role_id) throws UserNotAuthorizedException {
        if (role_id < 2) {
            throw new UserNotAuthorizedException("You are not authorized to add a library item");
        }
    }

    private void checkItemForrole_idLevel(int urn, int role_id) throws UserNotAuthorizedException, ApolloDatabaseExplicitException, NoLibraryItemException {
        int itemrole_id = getItemrole_id(urn);
        if (role_id < itemrole_id) {
            throw new UserNotAuthorizedException("You are not authorized to access this library item");
        }
    }

    private int getItemrole_id(int urn) throws ApolloDatabaseExplicitException, NoLibraryItemException {
        String sql = "SELECT role_id FROM library_item_container_urns WHERE id = " + urn;

        Connection conn = null;
        try {
            conn = getConnection(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new NoLibraryItemException("No library item was found with URN \"" + urn);
            }

            int storedrole_id = rs.getInt(1);
            return storedrole_id;
        } catch (SQLException ex) {
            throw new ApolloDatabaseExplicitException("SQLException checking library_item_containers table");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                //this is okay.
            }
        }
    }

    public static void main(String[] args) throws IOException, ApolloDatabaseException, ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException, DatatypeConfigurationException, JAXBException, SQLException {

        Authentication authentication = new Authentication();
//		authentication.setRequesterId("library_demo");
//		authentication.setRequesterPassword("password");

//		int role_id = dbUtils.getrole_idId(LibraryUserrole_idTypeEnum.REVIEWER);
//		System.out.println(role_id);
        LibraryItemContainer item = new LibraryItemContainer();
//		Epidemic epidemic = getEpidemic();
        IndividualTreatmentControlMeasure strategy = new IndividualTreatmentControlMeasure();
        strategy.setDescription("test strategy");
        ProbabilisticParameter prob = new ProbabilisticParameter();
        prob.setProbability(0.5);
        strategy.setCompliance(prob);

        TemporalTriggerDefinition trigger = new TemporalTriggerDefinition();
        trigger.setTimeScale(TimeScaleEnum.DECISION_TIME_SCALE);
        strategy.getControlMeasureStartTime().add(trigger);

//		epidemic.getInfectiousDiseaseControlStrategies().add(strategy);
//////
//		CatalogEntry entry = new CatalogEntry();
//		entry.setItemDescription("test description");
//
        item.setLibraryItem(strategy);
        ByteArrayOutputStream bytes = new JsonUtils(Arrays.asList(ApolloClassList.classList)).getJsonBytes(item);
////
//		int catalogId = dbUtils.addLibraryItem("scenario_urn2", item, authentication, "first item");
//		System.out.println("Catalog ID: " + catalogId);
//		Epidemic epidemic2 = getEpidemic();
//		epidemic2.getCausalPathogens().get(0).setNcbiTaxonId("15");
//		item.setLibraryItem(epidemic2);
//		catalogId = dbUtils.addLibraryItem("scenario", item, authentication, "second item");
//		System.out.println("Catalog ID: " + catalogId);
//
//		Epidemic epidemic3 = getEpidemic();
//		epidemic3.setCurator("someone");
//		epidemic3.getCausalPathogens().get(0).setNcbiTaxonId("15");
//
//		item.setLibraryItem(epidemic3);
//		catalogId = dbUtils.addLibraryItem("scenario", item, authentication, "third item");
//		System.out.println("Catalog ID: " + catalogId);
//		int versionId = dbUtils.reviseLibraryItem(catalogId, epidemic, authentication);
//		System.out.println("Version ID: " + versionId);
//		dbUtils.addComment(catalogId, versionId, "this is a comment", authentication);
//		dbUtils.setReleaseVersion(catalogId, 1);
//		dbUtils.setReleaseVersion(catalogId, 2);
//		int publicVersion = dbUtils.getReleaseVersion(catalogId);
//		System.out.println("Public version: " + publicVersion);
//		String sql = "SELECT\n"
//				+ "	json_of_library_object \n"
//				+ "FROM\n"
//				+ "	(	SELECT\n"
//				+ "			json_of_library_object,\n"
//				+ "			json_array_elements(json_of_library_object->'causalPathogens') AS pathogen \n"
//				+ "		FROM\n"
//				+ "			library_objects \n"
//				+ "		WHERE\n"
//				+ "			json_of_library_object->>'class' = 'edu.pitt.apollo.types.v2_0_2.Epidemic' ) t \n"
//				+ "WHERE\n"
//				+ "	t.pathogen->>'ncbiTaxonId' = '12'";
//		List<Epidemic> objects = dbUtils.queryObjects(sql, Epidemic.class);
//		System.out.println("list size: " + objects.size());
//		LibraryItemContainer container = dbUtils.getLibraryItem("scenario_urn", 1);
//		Epidemic epidemicFromLibrary = (Epidemic) container.getLibraryItem();
//		GetCommentsResult result = dbUtils.getComments("/scenario/influenza/H1N1/US/PA/42003/2009", 1);

//		GregorianCalendar cal = new GregorianCalendar();
//		cal.set(2014, 11, 5);
//		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
//		List<ChangeLogEntry> changeLog = dbUtils.getChangeLogForLibraryItemsModifiedSinceDateTime(xcal);
        System.out.println("done");
    }
}
