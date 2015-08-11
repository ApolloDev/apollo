package edu.pitt.apollo.db;

import edu.pitt.apollo.utilities.JsonUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseExplicitException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordNotInsertedException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordAlreadyExistsException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseUserPasswordException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.library.NoLibraryItemException;
import edu.pitt.apollo.db.exceptions.library.NoURNFoundException;
import edu.pitt.apollo.library_service_types.v3_0_2.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_2.ChangeLogEntry;
import edu.pitt.apollo.library_service_types.v3_0_2.CommentFromLibrary;
import edu.pitt.apollo.library_service_types.v3_0_2.GetCommentsResult;
import edu.pitt.apollo.library_service_types.v3_0_2.LibraryActionTypeEnum;
import edu.pitt.apollo.library_service_types.v3_0_2.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v3_0_2.QueryResult;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.types.v3_0_2.ApolloPathogenCode;
import edu.pitt.apollo.types.v3_0_2.Epidemic;
import edu.pitt.apollo.types.v3_0_2.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v3_0_2.ProbabilisticParameter;
import edu.pitt.apollo.types.v3_0_2.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v3_0_2.TimeScaleEnum;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static edu.pitt.apollo.GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Oct 6, 2014 Time: 11:21:38 AM Class: LibraryDbUtils
 */
public class LibraryDbUtils extends BaseApolloDbUtils {

	private static final Logger libraryLogger = LoggerFactory.getLogger(LibraryDbUtils.class);
	private static final String ADDING_USER = "adding the user";
	private static final String ADDING_USER_ROLE = "adding the user role";
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
	private static final String LIBRARY_DB_RESOURCE_NAME = "ApolloLibraryDB";

	JsonUtils jsonUtils = new JsonUtils();

	public LibraryDbUtils() throws ApolloDatabaseException {
		super(LIBRARY_AUTO_COMMIT, LIBRARY_DB_RESOURCE_NAME);

	}

	@Override
	protected void setupResource(String resourceName) {
		try {
			Properties properties = new Properties();
			properties.load(new BufferedReader(new FileReader(APOLLO_DIR + "library_database.properties")));

			org.postgresql.ds.PGSimpleDataSource ds = new PGSimpleDataSource();
			//com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds
			//		= new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();

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
		super(LIBRARY_AUTO_COMMIT, resourceName);
	}

//	public LibraryDbUtils(File databasePropertiesFile) throws IOException {
//		super(databasePropertiesFile, LIBRARY_AUTO_COMMIT);
//	}
//
//	public LibraryDbUtils(InputStream databasePropertiesInputStream) throws IOException {
//		super(databasePropertiesInputStream, LIBRARY_AUTO_COMMIT);
//	}
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

	@Override
	public int addUser(String userName, String userPassword, String userEmail) throws ApolloDatabaseRecordAlreadyExistsException,
			ApolloDatabaseUserPasswordException, ApolloDatabaseException {

		try {
			getUserId(userName, userPassword);
			// if getUserId returns without exception, this means the user already exists
			throw new ApolloDatabaseRecordAlreadyExistsException("There was an error adding user to the Apollo library. User " + userName
					+ " already exists.");
		} catch (ApolloDatabaseKeyNotFoundException e) {
			// good this means the user doesn't already exist
		} catch (ApolloDatabaseUserPasswordException e) {
			// this also means the user already exists
			throw new ApolloDatabaseRecordAlreadyExistsException("There was an error adding user to the Apollo library. User " + userName
					+ " already exists.");
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_USER, ex);
		}

		String query = "INSERT INTO users (user_name,hash_of_user_password_and_salt,salt, user_email) VALUES (?,?,?,?)";
		String salt = getSaltForPassword();
		String saltedPasswordHash = getHashOfUserPasswordAndSalt(userPassword, salt);
		try (Connection conn = datasource.getConnection()) {

			try {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, userName);
				pstmt.setString(2, saltedPasswordHash);
				pstmt.setString(3, salt);
				pstmt.setString(4, userEmail);
				pstmt.executeUpdate();

				conn.commit();

				return -1;
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

	public void addUserRole(String userName, String userPassword, LibraryUserRoleTypeEnum role) throws ApolloDatabaseException {

		int userId;
		int roleId;
		try {
			userId = getUserId(userName, userPassword);
			roleId = getRoleId(role);
		} catch (ApolloDatabaseExplicitException | ApolloDatabaseKeyNotFoundException | ApolloDatabaseUserPasswordException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex);
		}

		String query = "INSERT INTO user_roles (user_id,role_id) VALUES (?,?)";

		try (Connection conn = datasource.getConnection()) {
			try {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, userId);
				pstmt.setInt(2, roleId);
				pstmt.executeUpdate();

				conn.commit();
			} catch (SQLException ex) {
                libraryLogger.error("Rolling back due to SQLException: " + ex.getMessage());
                try {
					conn.rollback();
				} catch (SQLException ex1) {
					throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex1);
				}
				throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex);
			}
		} catch (SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex);
		}
	}

	private int getUserId(String userName, String userPassword) throws ApolloDatabaseUserPasswordException,
			ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException {

		String query = "SELECT id, hash_of_user_password_and_salt, salt FROM users WHERE user_name = ?";

		try (Connection conn = datasource.getConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String storedSaltedPasswordHash = rs.getString("hash_of_user_password_and_salt");
				String salt = rs.getString("salt");
				String saltedPasswordHash = getHashOfUserPasswordAndSalt(userPassword, salt);
				if (saltedPasswordHash.equals(storedSaltedPasswordHash)) {
					return rs.getInt("id");
				} else {
					throw new ApolloDatabaseUserPasswordException(
							"Incorrect password provided for user " + userName);
				}
			} else {
				throw new ApolloDatabaseKeyNotFoundException("No entry in the users table where user_name = "
						+ userName);
			}
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException attempting to get user key: " + ex.getMessage());
		}

	}

	private int getRoleId(LibraryUserRoleTypeEnum roleEnum) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException {
		String sql = "SELECT id FROM roles WHERE description = '" + roleEnum.toString().toLowerCase() + "'";
		try (Connection conn = datasource.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			int id;
			if (rs.next()) {
				id = rs.getInt(1);
			} else {
				throw new ApolloDatabaseKeyNotFoundException("There was no role with description " + roleEnum.toString().toLowerCase());
			}

			return id;
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException attempting to get role: " + ex.getMessage());
		}
	}

	public boolean authorizeUser(Authentication authentication, LibraryUserRoleTypeEnum roleEnum) throws ApolloDatabaseException {
		try (Connection conn = datasource.getConnection()) {
			int roleId = getRoleId(roleEnum);
			int userId = getUserId(authentication.getRequesterId(), authentication.getRequesterPassword());

			String query = "SELECT * FROM user_roles where user_id = " + userId + " AND role_id = " + roleId;

			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException | ApolloDatabaseExplicitException | ApolloDatabaseKeyNotFoundException | ApolloDatabaseUserPasswordException ex) {
			throw createApolloDatabaseExceptionAndLog(AUTHORIZING_USER, ex);
		}
	}

//	private boolean checkIfUrnAlreadyExists(int urn) throws ApolloDatabaseExplicitException {
//
//		String query = "SELECT urn FROM library_item_container_urns WHERE urn = ?";
//		try {
//			PreparedStatement pstmt = getConn().prepareStatement(query);
//			pstmt.setInt(1, urn);
//			ResultSet rs = pstmt.executeQuery();
//
//			return rs.next();
//		} catch (ClassNotFoundException ex) {
//			throw new ApolloDatabaseExplicitException("ClassNotFoundException getting URN " + ex.getMessage());
//		} catch (SQLException ex) {
//			throw new ApolloDatabaseExplicitException("SQLException getting URN: " + ex.getMessage());
//		}
//	}
	private int getActionId(LibraryActionTypeEnum action) throws ApolloDatabaseExplicitException {

		String sql = "SELECT id FROM library_actions WHERE action = ?";
		try (Connection conn = datasource.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, action.toString().toLowerCase());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException getting action ID: " + ex.getMessage());
		}

		throw new ApolloDatabaseExplicitException("No action ID matched the given action");
	}

	private void insertActionPerformed(int catalogId, int version, Timestamp date, LibraryActionTypeEnum action) throws ApolloDatabaseExplicitException {

		String sql = "INSERT INTO library_item_action_history (urn_id, version, action_performed, date_of_action) VALUES (?,?,?,?);";

		int actionId = getActionId(action);
		try (Connection conn = datasource.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, catalogId);
			pstmt.setInt(2, version);
			pstmt.setInt(3, actionId);
			pstmt.setTimestamp(4, date);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException inserting action performed: " + ex.getMessage());
		}
	}

	private Timestamp getDateOfItem(int catalogId, int version) throws ApolloDatabaseExplicitException {
		// need to get the date of the insert
		String sql = "SELECT id,date_created FROM library_item_containers WHERE urn_id = " + catalogId + " AND version = " + version;
		try (Connection conn = datasource.getConnection()) {
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
		try (Connection conn = datasource.getConnection()) {

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

	public AddLibraryItemContainerResult addLibraryItem(LibraryItemContainer item, Authentication authentication, String commitMessage) throws ApolloDatabaseException {
		// user has already been authenticated

		try (Connection conn = datasource.getConnection()) {
//			boolean itemAlreadyExists = checkIfUrnAlreadyExists(urn);
//			if (itemAlreadyExists) {
//				throw new ApolloDatabaseException("There was an error " + ADDING_LIBARY_ITEM
//						+ ". The specified URI \"" + urn + "\" already exists in the library.");
//			}

			String sql = "INSERT INTO library_item_container_urns DEFAULT VALUES";

			try {
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();

				ResultSet rs = pstmt.getGeneratedKeys();
				int catalogId;
				if (rs.next()) {
					catalogId = rs.getInt(1);
				} else {
					throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, new ApolloDatabaseException("No ID was returned "
							+ " when adding the item to the Apollo library."));
				}

				int itemVersion = updateLibraryItem(catalogId, item, authentication);
				addComment(catalogId, itemVersion, commitMessage, authentication, LibraryCommentTypeEnum.COMMIT);

				Timestamp date = getDateOfItem(catalogId, itemVersion);

				insertActionPerformed(catalogId, itemVersion, date, LibraryActionTypeEnum.ADDED_ITEM);

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

	public int updateLibraryItem(int urn, Object item, Authentication authentication, String comment) throws ApolloDatabaseException {

		try (Connection conn = datasource.getConnection()) {
//			int catalogId = getCatalogIDFromURN(urn);
			int version = updateLibraryItem(urn, item, authentication);
			addComment(urn, version, comment, authentication, LibraryCommentTypeEnum.COMMIT);

			Timestamp date = getDateOfItem(urn, version);

			insertActionPerformed(urn, version, date, LibraryActionTypeEnum.UPDATED_ITEM);

			conn.commit();
			return version;
		} catch (ApolloDatabaseExplicitException | ApolloDatabaseRecordNotInsertedException | ApolloDatabaseUserPasswordException | NoLibraryItemException | SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(UPDATING_LIBRARY_ITEM, ex);
		}
	}

	private int updateLibraryItem(int catalogId, Object item, Authentication authentication) throws ApolloDatabaseKeyNotFoundException,
			ApolloDatabaseUserPasswordException, ApolloDatabaseExplicitException, ApolloDatabaseRecordNotInsertedException {

		try (Connection conn = datasource.getConnection()) {
			int userKey = getUserId(authentication.getRequesterId(), authentication.getRequesterPassword());

			String itemJson = getJSONStringForLibraryItem(item);
			String sql = "INSERT INTO library_item_containers (urn_id,json_representation,committer_id) VALUES (?,?,?)";
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
		}
	}

	private int getCommentTypeId(LibraryCommentTypeEnum commentType) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException {

		try (Connection conn = datasource.getConnection()) {
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
		}

	}

	public void addReviewerComment(int urn, int version, String comment, Authentication authentication) throws ApolloDatabaseException {
		try (Connection conn = datasource.getConnection()) {
			try {
				addComment(urn, version, comment, authentication, LibraryCommentTypeEnum.REVIEW);

				Timestamp date = getDateOfItem(urn, version);
				insertActionPerformed(urn, version, date, LibraryActionTypeEnum.ADDED_REVIEWER_COMMENT);

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

	private void addComment(int catalogId, int version, String comment, Authentication authentication, LibraryCommentTypeEnum commentEnum)
			throws ApolloDatabaseExplicitException, ApolloDatabaseKeyNotFoundException, ApolloDatabaseUserPasswordException, NoLibraryItemException {

		// check catalogId and itemVersion
		int itemId = getItemIdFromCatalogIdAndVersion(catalogId, version);
		int userId = getUserId(authentication.getRequesterId(), authentication.getRequesterPassword());
		int commentTypeId = getCommentTypeId(commentEnum);
		String sql = "INSERT INTO comments (item_id,comment,comment_type,user_id) VALUES (?,?,?,?)";
		try (Connection conn = datasource.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			pstmt.setString(2, comment);
			pstmt.setInt(3, commentTypeId);
			pstmt.setInt(4, userId);

			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException adding comment for catalog_uuid " + catalogId + " and version " + version + ": " + ex.getMessage());
		}
	}

	public LibraryCommentTypeEnum getCommentTypeFromId(int id) throws SQLException, ApolloDatabaseExplicitException {

		String sql = "SELECT description FROM comment_type WHERE id = " + id;

		try (Connection conn = datasource.getConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				return LibraryCommentTypeEnum.valueOf(rs.getString(1).toUpperCase());
			}
		}

		throw new ApolloDatabaseExplicitException("There was no comment type with ID " + id);
	}

	public GetCommentsResult getComments(int urn, int version) throws ApolloDatabaseException {

		try (Connection conn = datasource.getConnection()) {
//			int catalogId = getCatalogIDFromURN(urn);
			int itemId = getItemIdFromCatalogIdAndVersion(urn, version);
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

	public void setReleaseVersion(int urn, int version, Authentication authentication, String message) throws ApolloDatabaseException {

		try (Connection conn = datasource.getConnection()) {
//			int catalogId = getCatalogIDFromURN(urn);
			try {
				setReleaseVersion(urn, version);
				addComment(urn, version, message, authentication, LibraryCommentTypeEnum.RELEASE);

				Timestamp date = getDateOfItem(urn, version);
				insertActionPerformed(urn, version, date, LibraryActionTypeEnum.SET_AS_RELEASE_VERSION);

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

	private void setReleaseVersion(int catalogId, int version) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException,
			NoLibraryItemException, ApolloDatabaseException {

		int itemId = getItemIdFromCatalogIdAndVersion(catalogId, version);
		String sql = "UPDATE library_item_containers SET is_latest_release_version = true WHERE id = " + itemId;

		try (Connection conn = datasource.getConnection()) {
			clearPreviousReleaseVersion(catalogId);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			if (pstmt.getUpdateCount() == 0) {
				throw new ApolloDatabaseException("There was an error setting the release version. You specified a version of the object that does not exist");
			}

		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException setting public version for urn_id " + catalogId + " and version " + version + ": " + ex.getMessage());
		}
	}

	private void clearPreviousReleaseVersion(int catalogId) throws ApolloDatabaseExplicitException {

		String sql = "UPDATE library_item_containers SET is_latest_release_version = false, was_previously_released = true WHERE urn_id = " + catalogId
				+ " AND is_latest_release_version = true";
		try (Connection conn = datasource.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException setting public version for urn_id " + catalogId + ": " + ex.getMessage());
		}
	}

	public int setLibraryItemAsNotReleased(int urn) throws ApolloDatabaseException {

		try {
			Integer currentReleaseVersion = getReleaseVersion(urn);
			if (currentReleaseVersion == null) {
				return -1;
			} else {
//				int catalogId = getCatalogIDFromURN(urn);
				clearPreviousReleaseVersion(urn);
				return currentReleaseVersion;
			}
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(SETTING_ITEM_AS_NOT_RELEASED, ex);
//		} catch (NoURNFoundException ex) {
//			throw createApolloDatabaseExceptionAndLog(SETTING_ITEM_AS_NOT_RELEASED, ex);
		}
	}

	public List<Integer> getVersions(int urn) throws ApolloDatabaseException {

		try (Connection conn = datasource.getConnection()) {
			String sql = "SELECT version FROM library_item_containers WHERE urn_id = " + urn;
			List<Integer> versions = new ArrayList<>();

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int version = rs.getInt(1);
				versions.add(version);
			}

			return versions;
		} catch (SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_VERSIONS, ex);
		}

	}

	public Integer getReleaseVersion(int urn) throws ApolloDatabaseException {

		try (Connection conn = datasource.getConnection()) {
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

	public LibraryItemContainer getLibraryItemContainer(int urn, int version) throws ApolloDatabaseException {

		try {
			String itemJson = getLibraryItemContainerJSON(urn, version);
			return getLibraryItemContainderFromJson(itemJson);
		} catch (IOException | ApolloDatabaseExplicitException | ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_LIBRARY_ITEM_CONTAINER, ex);
		} catch (NoURNFoundException ex) {
			throw new ApolloDatabaseException("There was an error " + GETTING_LIBRARY_ITEM_CONTAINER + ". There is no item with the specified URN: \"" + urn + "\".");
		} catch (NoLibraryItemException ex) {
			throw new ApolloDatabaseException("There was an error " + GETTING_LIBRARY_ITEM_CONTAINER
					+ ". There is no item with the specified URN: \"" + urn + "\" and version " + version);
		}
	}

	public LibraryItemContainer getReleaseLibraryItemContainer(int urn) throws ApolloDatabaseException {

		Integer publicVersion = getReleaseVersion(urn);
		if (publicVersion == null) {
			return null;
		} else {
			return getLibraryItemContainer(urn, publicVersion);
		}
	}

	private String getLibraryItemContainerJSON(int urn, int version) throws ApolloDatabaseKeyNotFoundException,
			ApolloDatabaseExplicitException, NoURNFoundException, NoLibraryItemException {
		return getItemJSONFromCatalogIdAndVersion(version, urn);
	}

	private String getItemJSONFromCatalogIdAndVersion(int versionId, int urn) throws ApolloDatabaseKeyNotFoundException,
			ApolloDatabaseExplicitException, NoLibraryItemException {
		String sql = "SELECT json_representation FROM library_item_containers WHERE urn_id = " + urn + " AND version = " + versionId;
		try (Connection conn = datasource.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new NoLibraryItemException("No library item was found with URN \"" + urn + "\" and version " + versionId);
			}

			String json = rs.getString(1);
			return json;
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException checking library_item_container_urns table");
		}
	}

	private int getItemIdFromCatalogIdAndVersion(int catalogId, int versionId) throws ApolloDatabaseKeyNotFoundException,
			ApolloDatabaseExplicitException, NoLibraryItemException {
		String sql = "SELECT id FROM library_item_containers WHERE urn_id = " + catalogId + " AND version = " + versionId;
		try (Connection conn = datasource.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new NoLibraryItemException("No library item was found with URN \"" + catalogId + "\" and version " + versionId);
			}

			int id = rs.getInt(1);
			return id;
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException checking library_item_containers table");
		}
	}

	public List<ChangeLogEntry> getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime) throws ApolloDatabaseException {

		Timestamp timestamp = new Timestamp(dateTime.toGregorianCalendar().getTimeInMillis());

		try (Connection conn = datasource.getConnection()) {
			String sql = "SELECT library_item_action_history.version, library_item_action_history.date_of_action, library_item_container_urns.id, library_actions.action "
					+ "FROM library_item_action_history "
					+ "INNER JOIN library_item_container_urns "
					+ "ON library_item_action_history.urn_id = library_item_container_urns.id "
					+ "INNER JOIN library_actions "
					+ "ON library_item_action_history.action_performed = library_actions.id "
					+ "WHERE library_item_action_history.date_of_action >= ?";
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

	public QueryResult queryObjects(String sql) throws ApolloDatabaseException {

		QueryResult result = new QueryResult();
		try (Connection conn = datasource.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String columnName = rsmd.getColumnName(i);
				result.getColumnNames().add(columnName);
			}

			List<String> resultSetDataAs1DArray = new ArrayList<>();
			while (rs.next()) {
				for (int column = 1; column <= numColumns; column++) {
					String value = rs.getString(column);
					resultSetDataAs1DArray.add(value);
				}
			}

			result.getTable().addAll(resultSetDataAs1DArray);
			return result;
		} catch (SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(QUERYING, ex);
		}
	}

	private static Epidemic getEpidemic() {
		Epidemic epidemic = new Epidemic();
		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setNcbiTaxonId("12");
		epidemic.getCausalPathogens().add(code);
		return epidemic;
	}

	public static void main(String[] args) throws IOException, ApolloDatabaseException, ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException, DatatypeConfigurationException, JAXBException, SQLException {

		Authentication authentication = new Authentication();
		authentication.setRequesterId("library_demo");
		authentication.setRequesterPassword("password");

//		int role = dbUtils.getRoleId(LibraryUserRoleTypeEnum.REVIEWER);
//		System.out.println(role);
		LibraryItemContainer item = new LibraryItemContainer();
//		Epidemic epidemic = getEpidemic();
		IndividualTreatmentControlStrategy strategy = new IndividualTreatmentControlStrategy();
		strategy.setDescription("test strategy");
		ProbabilisticParameter prob = new ProbabilisticParameter();
		prob.setProbability(0.5);
		strategy.setCompliance(prob);

		TemporalTriggerDefinition trigger = new TemporalTriggerDefinition();
		trigger.setTimeScale(TimeScaleEnum.DECSISION_TIME_SCALE);
		strategy.getControlStrategyStartTime().add(trigger);

//		epidemic.getInfectiousDiseaseControlStrategies().add(strategy);
//////
//		CatalogEntry entry = new CatalogEntry();
//		entry.setItemDescription("test description");
//
		item.setLibraryItem(strategy);
		ByteArrayOutputStream bytes = new JsonUtils().getJsonBytes(item);
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
//		int versionId = dbUtils.updateLibraryItem(catalogId, epidemic, authentication);
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
//		LibraryItemContainer container = dbUtils.getLibraryItemContainer("scenario_urn", 1);
//		Epidemic epidemicFromLibrary = (Epidemic) container.getLibraryItem();
//		GetCommentsResult result = dbUtils.getComments("/scenario/influenza/H1N1/US/PA/42003/2009", 1);

//		GregorianCalendar cal = new GregorianCalendar();
//		cal.set(2014, 11, 5);
//		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
//		List<ChangeLogEntry> changeLog = dbUtils.getChangeLogForLibraryItemsModifiedSinceDateTime(xcal);
		System.out.println("done");
	}
}
