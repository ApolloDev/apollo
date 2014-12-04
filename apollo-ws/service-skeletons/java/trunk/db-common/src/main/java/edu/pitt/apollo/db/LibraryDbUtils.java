package edu.pitt.apollo.db;

import edu.pitt.apollo.db.exceptions.ApolloDatabaseExplicitException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordNotInsertedException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordAlreadyExistsException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseUserPasswordException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.library.NoLibraryItemException;
import edu.pitt.apollo.db.exceptions.library.NoURNFoundException;
import edu.pitt.apollo.library_service_types.v2_1_0.CatalogEntry;
import edu.pitt.apollo.library_service_types.v2_1_0.ChangeLogEntry;
import edu.pitt.apollo.library_service_types.v2_1_0.CommentFromLibrary;
import edu.pitt.apollo.library_service_types.v2_1_0.GetCommentsResult;
import edu.pitt.apollo.library_service_types.v2_1_0.LibraryActionTypeEnum;
import edu.pitt.apollo.library_service_types.v2_1_0.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v2_1_0.QueryResult;
import edu.pitt.apollo.services_common.v2_1_0.Authentication;
import edu.pitt.apollo.types.v2_1_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_1_0.Epidemic;
import edu.pitt.apollo.types.v2_1_0.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_1_0.InfectiousDiseaseControlStrategy;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 6, 2014
 * Time: 11:21:38 AM
 * Class: LibraryDbUtils
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

	public LibraryDbUtils(File databasePropertiesFile) throws IOException {
		super(databasePropertiesFile, LIBRARY_AUTO_COMMIT);
	}

	public LibraryDbUtils(InputStream databasePropertiesInputStream) throws IOException {
		super(databasePropertiesInputStream, LIBRARY_AUTO_COMMIT);
	}

	private String getJSONStringForLibraryItem(Object obj) throws JAXBException {
		String itemJson = getJsonBytes(obj).toString();
		return itemJson;
	}

	private ApolloDatabaseException createApolloDatabaseExceptionAndLog(String actionThatWasToBePerformed, Throwable exception) {
		libraryLogger.error(exception.getMessage());
		return new ApolloDatabaseException("There was an error " + actionThatWasToBePerformed + ".");
	}

	private LibraryItemContainer getLibraryItemContainderFromJson(String json) throws IOException {
		InputStream contentInputStream = new ByteArrayInputStream(json.getBytes());
		Class clazz = LibraryItemContainer.class;
		Map<String, Object> properties = new HashMap<String, Object>(2);
		properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		JAXBContext jc;
		try {
			jc = (JAXBContext) JAXBContext
					.newInstance(new Class[]{clazz, ObjectFactory.class}, properties);
			JAXBUnmarshaller unmarshaller = jc.createUnmarshaller();
			StreamSource ss = new StreamSource(contentInputStream);
			return (LibraryItemContainer) unmarshaller.unmarshal(ss, clazz).getValue();
		} catch (Exception e) {
			System.err.println("Exception encoding " + clazz.getName() + " to JSON.  Error message was: "
					+ e.getMessage());
			return null;
		}
	}

	@Override
	protected ByteArrayOutputStream getJsonBytes(Object obj) throws JAXBException {
		Class clazz = LibraryItemContainer.class;

		Map<String, Object> properties = new HashMap<String, Object>(2);
		properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		JAXBContext jc = (JAXBContext) JAXBContext.newInstance(new Class[]{clazz, ObjectFactory.class},
				properties);
		JAXBMarshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		marshaller.marshal(obj, baos);
		return baos;
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
		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setString(2, saltedPasswordHash);
			pstmt.setString(3, salt);
			pstmt.setString(4, userEmail);
			pstmt.executeUpdate();

			getConn().commit();

			return -1;
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_USER, ex);
		} catch (SQLException ex) {
			try {
				getConn().rollback();
			} catch (ClassNotFoundException ex1) {
				throw createApolloDatabaseExceptionAndLog(ADDING_USER, ex1);
			} catch (SQLException ex1) {
				throw createApolloDatabaseExceptionAndLog(ADDING_USER, ex1);
			}
			throw createApolloDatabaseExceptionAndLog(ADDING_USER, ex);
		}
	}

	public void addUserRole(String userName, String userPassword, LibraryUserRoleTypeEnum role) throws ApolloDatabaseException {

		int userId;
		int roleId;
		try {
			userId = getUserId(userName, userPassword);
			roleId = getRoleId(role);
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex);
		} catch (ApolloDatabaseUserPasswordException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex);
		}

		try {
			String query = "INSERT INTO user_roles (user_id,role_id) VALUES (?,?)";

			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, roleId);
			pstmt.executeUpdate();

			getConn().commit();
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex);
		} catch (SQLException ex) {
			try {
				getConn().rollback();
			} catch (ClassNotFoundException ex1) {
				throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex1);
			} catch (SQLException ex1) {
				throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex1);
			}
			throw createApolloDatabaseExceptionAndLog(ADDING_USER_ROLE, ex);
		}
	}

	private int getUserId(String userName, String userPassword) throws ApolloDatabaseUserPasswordException,
			ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException {
		try {
			String query = "SELECT id, hash_of_user_password_and_salt, salt FROM users WHERE user_name = ?";
			PreparedStatement pstmt = getConn().prepareStatement(query);
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
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException attempting to get user key: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException attempting to get user key: " + ex.getMessage());
		}
	}

	private int getRoleId(LibraryUserRoleTypeEnum roleEnum) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException {
		String sql = "SELECT id FROM roles WHERE description = '" + roleEnum.toString().toLowerCase() + "'";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
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
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException attempting to get role: " + ex.getMessage());
		}
	}

	public boolean authorizeUser(Authentication authentication, LibraryUserRoleTypeEnum roleEnum) throws ApolloDatabaseException {
		try {
			int roleId = getRoleId(roleEnum);
			int userId = getUserId(authentication.getRequesterId(), authentication.getRequesterPassword());

			String query = "SELECT * FROM user_roles where user_id = " + userId + " AND role_id = " + roleId;

			PreparedStatement pstmt = getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(AUTHORIZING_USER, ex);
		} catch (SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(AUTHORIZING_USER, ex);
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(AUTHORIZING_USER, ex);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(AUTHORIZING_USER, ex);
		} catch (ApolloDatabaseUserPasswordException ex) {
			throw createApolloDatabaseExceptionAndLog(AUTHORIZING_USER, ex);
		}
	}

	private boolean checkIfUrnAlreadyExists(String urn) throws ApolloDatabaseExplicitException {

		String query = "SELECT urn FROM library_item_container_urns WHERE urn = ?";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setString(1, urn);
			ResultSet rs = pstmt.executeQuery();

			return rs.next();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException getting URN " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException getting URN: " + ex.getMessage());
		}
	}

	private int getActionId(LibraryActionTypeEnum action) throws ApolloDatabaseExplicitException {

		String sql = "SELECT id FROM library_actions WHERE action = ?";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			pstmt.setString(1, action.toString().toLowerCase());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException getting action ID: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException getting action ID: " + ex.getMessage());
		}

		throw new ApolloDatabaseExplicitException("No action ID matched the given action");
	}

	private void insertActionPerformed(int catalogId, int version, Timestamp date, LibraryActionTypeEnum action) throws ApolloDatabaseExplicitException {

		String sql = "INSERT INTO library_item_action_history (urn_id, version, action_performed, date_of_action) VALUES (?,?,?,?);";

		int actionId = getActionId(action);
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, catalogId);
			pstmt.setInt(2, version);
			pstmt.setInt(3, actionId);
			pstmt.setTimestamp(4, date);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException inserting action performed: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException inserting action performed: " + ex.getMessage());
		}
	}

	private Timestamp getDateOfItem(int catalogId, int version) throws ApolloDatabaseExplicitException {
		// need to get the date of the insert
		String sql = "SELECT id,date_created FROM library_item_containers WHERE urn_id = " + catalogId + " AND version = " + version;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			Timestamp date;
			if (rs.next()) {
				date = rs.getTimestamp(2);
				return date;
			} else {
				throw new ApolloDatabaseExplicitException("The inserted item was not found in the library_item_containers table");
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException getting ID and date for item: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException getting ID and date for item: " + ex.getMessage());
		}
	}

	public List<String> getURNs(String itemType) throws ApolloDatabaseException {
		try {
			List<String> urns = new ArrayList<String>();
			String query;
			if (itemType == null) {
				query = "SELECT urn FROM library_item_container_urns";
			} else {
				query = "SELECT urn FROM library_item_container_urns WHERE id in ("
						+ "SELECT id FROM library_item_containers WHERE json_represenation->'libraryItem'->>'type' = '" + itemType + "')";
			}
			PreparedStatement pstmt = getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				urns.add(rs.getString(1));
			}

			return urns;
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_URNS, ex);
		} catch (SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_URNS, ex);
		}
	}

	public int addLibraryItem(String urn, LibraryItemContainer item, Authentication authentication, String commitMessage) throws ApolloDatabaseException {
		// user has already been authenticated

		try {
			boolean itemAlreadyExists = checkIfUrnAlreadyExists(urn);
			if (itemAlreadyExists) {
				throw new ApolloDatabaseException("There was an error " + ADDING_LIBARY_ITEM
						+ ". The specified URI \"" + urn + "\" already exists in the library.");
			}

			String sql = "INSERT INTO library_item_container_urns (urn) VALUES (?);";

			PreparedStatement pstmt = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, urn);
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			int catalogId;
			if (rs.next()) {
				catalogId = rs.getInt(1);
			} else {
				throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, new ApolloDatabaseException("No ID was returned "
						+ " when adding the item with URN " + urn + " to the Apollo library."));
			}

			int itemVersion = updateLibraryItem(catalogId, item, authentication);
			addComment(catalogId, itemVersion, commitMessage, authentication, LibraryCommentTypeEnum.COMMIT);

			Timestamp date = getDateOfItem(catalogId, itemVersion);

			insertActionPerformed(catalogId, itemVersion, date, LibraryActionTypeEnum.ADDED_ITEM);

			getConn().commit();

			return itemVersion;
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex);
		} catch (SQLException ex) {
			try {
				getConn().rollback();
			} catch (ClassNotFoundException ex1) {
				throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex1);
			} catch (SQLException ex1) {
				throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex1);
			}
			throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex);
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex);
		} catch (ApolloDatabaseRecordNotInsertedException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex);
		} catch (ApolloDatabaseUserPasswordException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex);
		} catch (NoLibraryItemException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_LIBARY_ITEM, ex);
		}
	}

	public int updateLibraryItem(String urn, Object item, Authentication authentication, String comment) throws ApolloDatabaseException {

		try {
			int catalogId = getCatalogIDFromURN(urn);
			int version = updateLibraryItem(catalogId, item, authentication);
			addComment(catalogId, version, comment, authentication, LibraryCommentTypeEnum.COMMIT);

			Timestamp date = getDateOfItem(catalogId, version);

			insertActionPerformed(catalogId, version, date, LibraryActionTypeEnum.UPDATED_ITEM);

			getConn().commit();
			return version;
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(UPDATING_LIBRARY_ITEM, ex);
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(UPDATING_LIBRARY_ITEM, ex);
		} catch (ApolloDatabaseRecordNotInsertedException ex) {
			throw createApolloDatabaseExceptionAndLog(UPDATING_LIBRARY_ITEM, ex);
		} catch (ApolloDatabaseUserPasswordException ex) {
			throw createApolloDatabaseExceptionAndLog(UPDATING_LIBRARY_ITEM, ex);
		} catch (NoURNFoundException ex) {
			throw new ApolloDatabaseException("There was an error " + UPDATING_LIBRARY_ITEM + ". There is no item with the specified URN: \"" + urn + "\".");
		} catch (NoLibraryItemException ex) {
			throw createApolloDatabaseExceptionAndLog(UPDATING_LIBRARY_ITEM, ex);
		} catch (SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(UPDATING_LIBRARY_ITEM, ex);
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(UPDATING_LIBRARY_ITEM, ex);
		}
	}

	private int updateLibraryItem(int catalogId, Object item, Authentication authentication) throws ApolloDatabaseKeyNotFoundException,
			ApolloDatabaseUserPasswordException, ApolloDatabaseExplicitException, ApolloDatabaseRecordNotInsertedException {

		try {
			int userKey = getUserId(authentication.getRequesterId(), authentication.getRequesterPassword());

			String itemJson = getJSONStringForLibraryItem(item);
			String sql = "INSERT INTO library_item_containers (urn_id,json_represenation,committer_id) VALUES (?,?,?)";
			PreparedStatement pstmt = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, catalogId);
			pstmt.setString(2, itemJson);
			pstmt.setInt(3, userKey);
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (!rs.next()) {
				throw new ApolloDatabaseRecordNotInsertedException("Catalog item insert did not return an id");
			}

			return rs.getInt("version");
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException updating library item: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException updating library item: " + ex.getMessage());
		} catch (JAXBException ex) {
			throw new ApolloDatabaseExplicitException("JAXBException updating library item: " + ex.getMessage());
		}
	}

	private int getCommentTypeId(LibraryCommentTypeEnum commentType) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException {

		try {
			String query = "SELECT id FROM comment_type WHERE description = ?";
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setString(1, commentType.toString().toLowerCase());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new ApolloDatabaseKeyNotFoundException("No comment type with description " + commentType.toString().toLowerCase() + " exists");
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException getting comment type id: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException getting comment type id: " + ex.getMessage());
		}

	}

	public void addReviewerComment(String urn, int version, String comment, Authentication authentication) throws ApolloDatabaseException {
		try {
			int catalogId = getCatalogIDFromURN(urn);
			addComment(catalogId, version, comment, authentication, LibraryCommentTypeEnum.REVIEW);

			Timestamp date = getDateOfItem(catalogId, version);
			insertActionPerformed(catalogId, version, date, LibraryActionTypeEnum.ADDED_REVIEWER_COMMENT);

			getConn().commit();
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_REVIEWER_COMMENT, ex);
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_REVIEWER_COMMENT, ex);
		} catch (ApolloDatabaseUserPasswordException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_REVIEWER_COMMENT, ex);
		} catch (NoURNFoundException ex) {
			throw new ApolloDatabaseException("There was an error " + ADDING_REVIEWER_COMMENT + ". There is no item with the specified URN: \"" + urn + "\".");
		} catch (NoLibraryItemException ex) {
			throw new ApolloDatabaseException("There was an error " + ADDING_REVIEWER_COMMENT
					+ ". There is no item with the specified URN: \"" + urn + "\" and version " + version);
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(ADDING_REVIEWER_COMMENT, ex);
		} catch (SQLException ex) {
			try {
				getConn().rollback();
			} catch (ClassNotFoundException ex1) {
				throw createApolloDatabaseExceptionAndLog(ADDING_REVIEWER_COMMENT, ex1);
			} catch (SQLException ex1) {
				throw createApolloDatabaseExceptionAndLog(ADDING_REVIEWER_COMMENT, ex1);
			}
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
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			pstmt.setInt(1, itemId);
			pstmt.setString(2, comment);
			pstmt.setInt(3, commentTypeId);
			pstmt.setInt(4, userId);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException adding comment for catalog_uuid " + catalogId + " and version " + version + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException adding comment for catalog_uuid " + catalogId + " and version " + version + ": " + ex.getMessage());
		}
	}

	public LibraryCommentTypeEnum getCommentTypeFromId(int id) throws ClassNotFoundException, SQLException, ApolloDatabaseExplicitException {

		String sql = "SELECT description FROM comment_type WHERE id = " + id;

		PreparedStatement pstmt = getConn().prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			return LibraryCommentTypeEnum.valueOf(rs.getString(1).toUpperCase());
		}

		throw new ApolloDatabaseExplicitException("There was no comment type with ID " + id);
	}

	public GetCommentsResult getComments(String urn, int version) throws ApolloDatabaseException {

		try {
			int catalogId = getCatalogIDFromURN(urn);
			int itemId = getItemIdFromCatalogIdAndVersion(catalogId, version);
			String sql = "SELECT date_created,comment,comment_type,user_id FROM comments WHERE item_id = " + itemId;

			GetCommentsResult result = new GetCommentsResult();

			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Date date = rs.getDate(1);
				String comment = rs.getString(2);
				int commentTypeId = rs.getInt(3);
				LibraryCommentTypeEnum commentTypeEnum = getCommentTypeFromId(commentTypeId);
				String userId = rs.getString(4);

				CommentFromLibrary commentFomLibrary = new CommentFromLibrary();
				commentFomLibrary.setComment(comment);
				commentFomLibrary.setCommenter(userId);

				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(date);
				XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
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

		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_COMMENTS, ex);
		} catch (SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_COMMENTS, ex);
		} catch (DatatypeConfigurationException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_COMMENTS, ex);
		} catch (IllegalArgumentException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_COMMENTS, ex);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_COMMENTS, ex);
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_COMMENTS, ex);
		} catch (NoURNFoundException ex) {
			throw new ApolloDatabaseException("There was an error " + GETTING_COMMENTS + ". There is no item with the specified URN: \"" + urn + "\".");
		} catch (NoLibraryItemException ex) {
			throw new ApolloDatabaseException("There was an error " + GETTING_COMMENTS
					+ ". There is no item with the specified URN: \"" + urn + "\" and version " + version);
		}
	}

	public void setReleaseVersion(String urn, int version, Authentication authentication, String message) throws ApolloDatabaseException {

		try {
			int catalogId = getCatalogIDFromURN(urn);
			setReleaseVersion(catalogId, version);
			addComment(catalogId, version, message, authentication, LibraryCommentTypeEnum.RELEASE);

			Timestamp date = getDateOfItem(catalogId, version);
			insertActionPerformed(catalogId, version, date, LibraryActionTypeEnum.SET_AS_RELEASE_VERSION);

			getConn().commit();
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(SETTING_RELEASE_VERSION, ex);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(SETTING_RELEASE_VERSION, ex);
		} catch (ApolloDatabaseUserPasswordException ex) {
			throw createApolloDatabaseExceptionAndLog(SETTING_RELEASE_VERSION, ex);
		} catch (NoURNFoundException ex) {
			throw new ApolloDatabaseException("There was an error " + SETTING_RELEASE_VERSION + "to " + version
					+ " for the following resource: \"" + urn + "\". The specified resoucre does not exist in the library.");
		} catch (NoLibraryItemException ex) {
			throw new ApolloDatabaseException("There was an error " + SETTING_RELEASE_VERSION
					+ ". There is no item with the specified URN: \"" + urn + "\" and version " + version);
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(SETTING_RELEASE_VERSION, ex);

		} catch (SQLException ex) {
			try {
				getConn().rollback();
			} catch (ClassNotFoundException ex1) {
				throw createApolloDatabaseExceptionAndLog(SETTING_RELEASE_VERSION, ex1);
			} catch (SQLException ex1) {
				throw createApolloDatabaseExceptionAndLog(SETTING_RELEASE_VERSION, ex1);
			}
		}
	}

	private void setReleaseVersion(int catalogId, int version) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException,
			NoLibraryItemException, ApolloDatabaseException {

		int itemId = getItemIdFromCatalogIdAndVersion(catalogId, version);
//		String insert = "INSERT INTO release_versions (catalog_urn_id, item_id) SELECT " + catalogId + "," + itemId;
//		String upsert = "UPDATE release_versions SET item_id = " + itemId + " WHERE catalog_urn_id = " + catalogId;
		String sql = "UPDATE library_item_containers SET is_latest_release_version = true WHERE id = " + itemId;

		try {
			clearPreviousReleaseVersion(catalogId);

			PreparedStatement pstmt = getConn().prepareStatement(sql);
			pstmt.executeUpdate();
			if (pstmt.getUpdateCount() == 0) {
				throw new ApolloDatabaseException("There was an error setting the release version. You specified a version of the object that does not exist");
			}

		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException setting public version for urn_id " + catalogId + " and version " + version + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException setting public version for urn_id " + catalogId + " and version " + version + ": " + ex.getMessage());
		}
	}

	private void clearPreviousReleaseVersion(int catalogId) throws ApolloDatabaseExplicitException {

		String sql = "UPDATE library_item_containers SET is_latest_release_version = false, was_previously_released = true WHERE urn_id = " + catalogId
				+ " AND is_latest_release_version = true";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException setting public version for urn_id " + catalogId + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException setting public version for urn_id " + catalogId + ": " + ex.getMessage());
		}
	}

	public int setLibraryItemAsNotReleased(String urn) throws ApolloDatabaseException {

		try {
			Integer currentReleaseVersion = getReleaseVersion(urn);
			if (currentReleaseVersion == null) {
				return -1;
			} else {
				int catalogId = getCatalogIDFromURN(urn);
				clearPreviousReleaseVersion(catalogId);
				return currentReleaseVersion;
			}
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(SETTING_ITEM_AS_NOT_RELEASED, ex);
		} catch (NoURNFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(SETTING_ITEM_AS_NOT_RELEASED, ex);
		}
	}

	public List<Integer> getVersions(String urn) throws ApolloDatabaseException {

		try {
			int catalogId = getCatalogIDFromURN(urn);
			String sql = "SELECT version FROM library_item_containers WHERE urn_id = " + catalogId;
			List<Integer> versions = new ArrayList<Integer>();

			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int version = rs.getInt(1);
				versions.add(version);
			}

			return versions;
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_VERSIONS, ex);
		} catch (SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_VERSIONS, ex);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_VERSIONS, ex);
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_VERSIONS, ex);
		} catch (NoURNFoundException ex) {
			throw new ApolloDatabaseException("There was an error " + GETTING_VERSIONS + ". There is no item with the specified URN: \"" + urn + "\".");
		}

	}

	public Integer getReleaseVersion(String urn) throws ApolloDatabaseException {

		try {
			int catalogId = getCatalogIDFromURN(urn);
			String sql = "SELECT version FROM library_item_containers WHERE is_latest_release_version = true AND urn_id = " + catalogId;

			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			int version;
			if (rs.next()) {
				version = rs.getInt(1);
				return version;
			} else {
				return null;
			}

//			sql = "SELECT version FROM library_objects WHERE id = " + itemId;
//			pstmt = getConn().prepareStatement(sql);
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				return rs.getInt(1);
//			} else {
//				throw createApolloDatabaseExceptionAndLog(GETTING_RELEASE_VERSION,
//						new ApolloDatabaseKeyNotFoundException("No item exists with catalog_urn_id " + catalogId));
//			}
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_RELEASE_VERSION, ex);
		} catch (SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_RELEASE_VERSION, ex);
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_RELEASE_VERSION, ex);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_RELEASE_VERSION, ex);
		} catch (NoURNFoundException ex) {
			throw new ApolloDatabaseException("There was an error " + GETTING_RELEASE_VERSION + ". There is no item with the specified URN: \"" + urn + "\".");
		}
	}

	public LibraryItemContainer getLibraryItemContainer(String urn, int version) throws ApolloDatabaseException {

		try {
			String itemJson = getLibraryItemContainerJSON(urn, version);
			return getLibraryItemContainderFromJson(itemJson);
		} catch (IOException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_LIBRARY_ITEM_CONTAINER, ex);
		} catch (ApolloDatabaseExplicitException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_LIBRARY_ITEM_CONTAINER, ex);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_LIBRARY_ITEM_CONTAINER, ex);
		} catch (NoURNFoundException ex) {
			throw new ApolloDatabaseException("There was an error " + GETTING_LIBRARY_ITEM_CONTAINER + ". There is no item with the specified URN: \"" + urn + "\".");
		} catch (NoLibraryItemException ex) {
			throw new ApolloDatabaseException("There was an error " + GETTING_LIBRARY_ITEM_CONTAINER
					+ ". There is no item with the specified URN: \"" + urn + "\" and version " + version);
		}
	}

	public LibraryItemContainer getReleaseLibraryItemContainer(String urn) throws ApolloDatabaseException {

		Integer publicVersion = getReleaseVersion(urn);
		if (publicVersion == null) {
			return null;
		} else {
			return getLibraryItemContainer(urn, publicVersion);
		}
	}

	private String getLibraryItemContainerJSON(String urn, int version) throws ApolloDatabaseKeyNotFoundException,
			ApolloDatabaseExplicitException, NoURNFoundException, NoLibraryItemException {
		int catalogId = getCatalogIDFromURN(urn);
		return getItemJSONFromCatalogIdAndVersion(catalogId, version, urn);
	}

	private int getCatalogIDFromURN(String urn) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException, NoURNFoundException {
		String sql = "SELECT id FROM library_item_container_urns WHERE urn = ?";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			pstmt.setString(1, urn);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new NoURNFoundException("No item with urn " + urn + " exists");
			}
			int id = rs.getInt(1);
			return id;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException checking library_item_container_urns table");
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException checking library_item_container_urns table");
		}

	}

	private String getItemJSONFromCatalogIdAndVersion(int catalogId, int versionId, String urn) throws ApolloDatabaseKeyNotFoundException,
			ApolloDatabaseExplicitException, NoLibraryItemException {
		String sql = "SELECT json_represenation FROM library_item_containers WHERE urn_id = " + catalogId + " AND version = " + versionId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new NoLibraryItemException("No library item was found with URN \"" + urn + "\" and version " + versionId);
			}

			String json = rs.getString(1);
			return json;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException checking library_item_container_urns table");
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException checking library_item_container_urns table");
		}
	}

	private int getItemIdFromCatalogIdAndVersion(int catalogId, int versionId) throws ApolloDatabaseKeyNotFoundException,
			ApolloDatabaseExplicitException, NoLibraryItemException {
		String sql = "SELECT id FROM library_item_containers WHERE urn_id = " + catalogId + " AND version = " + versionId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new NoLibraryItemException("No library item was found with URN \"" + catalogId + "\" and version " + versionId);
			}

			int id = rs.getInt(1);
			return id;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseExplicitException("ClassNotFoundException checking library_item_containers table");
		} catch (SQLException ex) {
			throw new ApolloDatabaseExplicitException("SQLException checking library_item_containers table");
		}
	}

	public List<ChangeLogEntry> getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime) throws ApolloDatabaseException {

		Timestamp timestamp = new Timestamp(dateTime.toGregorianCalendar().getTimeInMillis());

		try {
			String sql = "SELECT library_item_action_history.version, library_item_action_history.date_of_action, library_item_container_urns.urn, library_actions.action "
					+ "FROM library_item_action_history "
					+ "INNER JOIN library_item_container_urns "
					+ "ON library_item_action_history.urn_id = library_item_container_urns.id "
					+ "INNER JOIN library_actions "
					+ "ON library_item_action_history.action_performed = library_actions.id "
					+ "WHERE library_item_action_history.date_of_action >= ?";
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			pstmt.setTimestamp(1, timestamp);
			ResultSet rs = pstmt.executeQuery();

			List<ChangeLogEntry> entries = new ArrayList<ChangeLogEntry>();
			while (rs.next()) {
				int version = rs.getInt(1);
				Timestamp actionTime = rs.getTimestamp(2);
				String urn = rs.getString(3);
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
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_CHANGE_LOG, ex);
		} catch (SQLException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_CHANGE_LOG, ex);
		} catch (DatatypeConfigurationException ex) {
			throw createApolloDatabaseExceptionAndLog(GETTING_CHANGE_LOG, ex);
		}
	}

//	private void checkCatalogId(int catalogId) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException {
//		String sql = "SELECT * FROM library_item_container_urns WHERE id = " + catalogId;
//		try {
//			PreparedStatement pstmt = getConn().prepareStatement(sql);
//			ResultSet rs = pstmt.executeQuery();
//			if (!rs.next()) {
//				throw new ApolloDatabaseKeyNotFoundException("No library_item_container_urns entry with id " + catalogId + " exists");
//			}
//		} catch (ClassNotFoundException ex) {
//			throw new ApolloDatabaseExplicitException("ClassNotFoundException checking library_item_container_urns table");
//		} catch (SQLException ex) {
//			throw new ApolloDatabaseExplicitException("SQLException checking library_item_container_urns table");
//		}
//	}
//	private <T extends Object> T getObjectFromJson(String json, Class<T> type) throws IOException {
//		ObjectMapper mapper = new ObjectMapper();
//		T object = mapper.readValue(json, type);
//		return object;
//	}
	public QueryResult queryObjects(String sql) throws ApolloDatabaseException {

		QueryResult result = new QueryResult();
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String columnName = rsmd.getColumnName(i);
				result.getColumnNames().add(columnName);
			}

			List<String> resultSetDataAs1DArray = new ArrayList<String>();
			while (rs.next()) {
				for (int column = 1; column <= numColumns; column++) {
					String value = rs.getString(column);
					resultSetDataAs1DArray.add(value);
				}
			}

			result.getTable().addAll(resultSetDataAs1DArray);
			return result;
		} catch (ClassNotFoundException ex) {
			throw createApolloDatabaseExceptionAndLog(QUERYING, ex);
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

	public static void main(String[] args) throws IOException, ApolloDatabaseException, ApolloDatabaseKeyNotFoundException, ApolloDatabaseExplicitException, DatatypeConfigurationException {

		LibraryDbUtils dbUtils = new LibraryDbUtils(new File("C:\\apollo_210\\library_database.properties"));

		Authentication authentication = new Authentication();
		authentication.setRequesterId("library_demo");
		authentication.setRequesterPassword("password");

//		int role = dbUtils.getRoleId(LibraryUserRoleTypeEnum.REVIEWER);
//		System.out.println(role);
//		LibraryItemContainer item = new LibraryItemContainer();
//		Epidemic epidemic = getEpidemic();
//		InfectiousDiseaseControlStrategy strategy = new IndividualTreatmentControlStrategy();
//		strategy.setDescription("test strategy");
//		epidemic.getInfectiousDiseaseControlStrategies().add(strategy);
//////
//		CatalogEntry entry = new CatalogEntry();
//		entry.setItemDescription("test description");
//
//		item.setLibraryItem(epidemic);
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

		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2014, 11, 5);
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		List<ChangeLogEntry> changeLog = dbUtils.getChangeLogForLibraryItemsModifiedSinceDateTime(xcal);
		System.out.println("done");
	}
}
