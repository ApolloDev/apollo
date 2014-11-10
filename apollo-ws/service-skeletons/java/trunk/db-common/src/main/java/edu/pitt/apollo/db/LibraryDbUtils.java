package edu.pitt.apollo.db;

import edu.pitt.apollo.library_service_types.v2_1_0.CatalogEntry;
import edu.pitt.apollo.library_service_types.v2_1_0.CommentFromLibrary;
import edu.pitt.apollo.library_service_types.v2_1_0.GetCommentsResult;
import edu.pitt.apollo.library_service_types.v2_1_0.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v2_1_0.QueryResult;
import edu.pitt.apollo.services_common.v2_1_0.Authentication;
import edu.pitt.apollo.types.v2_1_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_1_0.Epidemic;
import edu.pitt.apollo.types.v2_1_0.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_1_0.InfectiousDiseaseControlStrategy;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 6, 2014
 * Time: 11:21:38 AM
 * Class: LibraryDbUtils
 */
public class LibraryDbUtils extends BaseApolloDbUtils {

	public LibraryDbUtils(File databasePropertiesFile) throws IOException {
		super(databasePropertiesFile);
	}

	public LibraryDbUtils(InputStream databasePropertiesInputStream) throws IOException {
		super(databasePropertiesInputStream);
	}

	protected String getJSONStringForLibraryItem(Object obj) {
		String itemJson = getJsonBytes(obj).toString();
//		itemJson = "{\"item\":" + itemJson + "}";
		return itemJson;
	}

	private LibraryItemContainer getLibraryItemContainderFromJson(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		LibraryItemContainer container = mapper.readValue(json, LibraryItemContainer.class);
		return container;
	}

	@Override
	protected ByteArrayOutputStream getJsonBytes(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
			mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
			mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			mapper.writeValue(baos, obj);

			return baos;
		} catch (IOException ex) {
			System.err.println("IO Exception JSON encoding and getting bytes from RunSimulationMessage: " + ex.getMessage());
			return null;
		}
	}

	@Override
	public int addUser(String userName, String userPassword, String userEmail) throws ApolloDatabaseRecordAlreadyExistsException,
			ApolloDatabaseUserPasswordException, ApolloDatabaseException {

		try {
			getUserId(userName, userPassword);
			throw new ApolloDatabaseRecordAlreadyExistsException("User " + userName
					+ " already exists in the database.");
		} catch (ApolloDatabaseKeyNotFoundException e) {
			// good this means the user doesn't already exist
		} catch (ApolloDatabaseUserPasswordException e) {
			throw new ApolloDatabaseUserPasswordException("A user with userID \"" + userName + "\" already exists.");
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

			return -1;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotfoundException attempting to add user: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException attempting to add user: " + ex.getMessage());
		}
	}

	public void addUserRole(String userName, String userPassword, LibraryUserRoleTypeEnum role) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {

		int userId = getUserId(userName, userPassword);
		int roleId = getRoleId(role);
		String query = "INSERT INTO user_roles (user_id,role_id) VALUES (?,?)";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, roleId);
			pstmt.executeUpdate();

		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotfoundException attempting to add user role: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException attempting to add user role: " + ex.getMessage());
		}
	}

	public int getUserId(String userName, String userPassword) throws ApolloDatabaseUserPasswordException,
			ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {
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
							"Incorrect password");
				}
			} else {
				throw new ApolloDatabaseKeyNotFoundException("No entry in the users table where user_name = "
						+ userName);
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException attempting to get user key: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException attempting to get user key: " + ex.getMessage());
		}
	}

	private int getRoleId(LibraryUserRoleTypeEnum roleEnum) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {
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
			throw new ApolloDatabaseException("SQLException attempting to get role: " + ex.getMessage());
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException attempting to get role: " + ex.getMessage());
		}
	}

	public boolean authorizeUser(Authentication authentication, LibraryUserRoleTypeEnum roleEnum) throws ApolloDatabaseException {

		int roleId = getRoleId(roleEnum);
		int userId = getUserId(authentication.getRequesterId(), authentication.getRequesterPassword());

		String query = "SELECT * FROM user_roles where user_id = " + userId + " AND role_id = " + roleId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException attempting to authorize user: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException attempting to authorize user: " + ex.getMessage());
		}
	}

	public int addLibraryItem(String uri, LibraryItemContainer item, Authentication authentication, String commitMessage) throws ApolloDatabaseException {
		// user has already been authenticated

		String sql = "INSERT INTO catalog_of_uris (uri) VALUES (?);";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, uri);
			pstmt.execute();

			ResultSet rs = pstmt.getGeneratedKeys();
			int catalogId;
			if (rs.next()) {
				catalogId = rs.getInt(1);
			} else {
				throw new ApolloDatabaseRecordNotInsertedException("Catalog item insert did not return a run ID");
			}

			int itemVersion = updateLibraryItem(catalogId, item, authentication);
			addComment(catalogId, itemVersion, commitMessage, authentication, LibraryCommentTypeEnum.COMMIT);

			return itemVersion;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException adding catalog_of_uuids item: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException adding catalog_of_uuids item: " + ex.getMessage());
		}
	}

	public int updateLibraryItem(String uri, Object item, Authentication authentication, String comment) throws ApolloDatabaseException {

		int catalogId = getCatalogIDFromURI(uri);
		int version = updateLibraryItem(catalogId, item, authentication);
		addComment(catalogId, version, comment, authentication, LibraryCommentTypeEnum.COMMIT);
		return version;
	}

	private int updateLibraryItem(int catalogId, Object item, Authentication authentication) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {

		// check catalog_of_uuids version
		try {
			checkCatalogId(catalogId);

			int userKey = getUserId(authentication.getRequesterId(), authentication.getRequesterPassword());

			String itemJson = getJSONStringForLibraryItem(item);
			String sql = "INSERT INTO library_objects (catalog_uri_id,json_of_library_object,user_id) VALUES (?,?,?)";
			PreparedStatement pstmt = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, catalogId);
			pstmt.setString(2, itemJson);
			pstmt.setInt(3, userKey);
			pstmt.execute();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (!rs.next()) {
				throw new ApolloDatabaseRecordNotInsertedException("Catalog item insert did not return an id");
			}

			return rs.getInt("version");
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException adding item version: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException adding item version: " + ex.getMessage());
		}
	}

	private int getCommentTypeId(LibraryCommentTypeEnum commentType) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {

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
			throw new ApolloDatabaseException("ClassNotFoundException getting comment type id: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException getting comment type id: " + ex.getMessage());
		}

	}

	public void addReviewerComment(String uri, int version, String comment, Authentication authentication) throws ApolloDatabaseException {
		int catalogId = getCatalogIDFromURI(uri);
		addComment(catalogId, version, comment, authentication, LibraryCommentTypeEnum.REVIEW);
	}

	private void addComment(int catalogId, int version, String comment, Authentication authentication, LibraryCommentTypeEnum commentEnum) throws ApolloDatabaseException {

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

			pstmt.execute();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException adding comment for catalog_uuid " + catalogId + " and version " + version + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException adding comment for catalog_uuid " + catalogId + " and version " + version + ": " + ex.getMessage());
		}
	}

	public GetCommentsResult getComments(String uri, int version) throws ApolloDatabaseException {

		int catalogId = getCatalogIDFromURI(uri);
		int itemId = getItemIdFromCatalogIdAndVersion(catalogId, version);
		String sql = "SELECT date,comment,comment_type,user_id FROM comments WHERE item_id = " + itemId;

		GetCommentsResult result = new GetCommentsResult();

		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Date date = rs.getDate(1);
				String comment = rs.getString(2);
				LibraryCommentTypeEnum commentTypeEnum = LibraryCommentTypeEnum.valueOf(rs.getString(3).toUpperCase());
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
			throw new ApolloDatabaseException("ClassNotFoundException getting comments for uri " + uri + " and version " + version + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException getting comments for uri " + uri + " and version " + version + ": " + ex.getMessage());
		} catch (DatatypeConfigurationException ex) {
			throw new ApolloDatabaseException("DatatypeConfigurationException getting date for comment for uri " + uri + " and version " + version + ": " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new ApolloDatabaseException("IllegalArgumentException getting comment type for uri " + uri + " and version " + version + ": " + ex.getMessage());
		}
	}

	public void setPublicVersion(String uri, int version, Authentication authentication, String message) throws ApolloDatabaseException {

		int catalogId = getCatalogIDFromURI(uri);
		setPublicVersion(catalogId, version);
		addComment(catalogId, version, message, authentication, LibraryCommentTypeEnum.RELEASE);
	}

	private void setPublicVersion(int catalogId, int version) throws ApolloDatabaseException {

		int itemId = getItemIdFromCatalogIdAndVersion(catalogId, version);
		String insert = "INSERT INTO release_versions (catalog_uri_id, item_id) SELECT " + catalogId + "," + itemId;
		String upsert = "UPDATE release_versions SET item_id = " + itemId + " WHERE catalog_uri_id = " + catalogId;
		String sql = "BEGIN; LOCK TABLE release_versions IN SHARE ROW EXCLUSIVE MODE; "
				+ "WITH upsert AS (" + upsert + " RETURNING *) " + insert + " WHERE NOT EXISTS (SELECT * FROM upsert);"
				+ "COMMIT;";

		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			pstmt.execute();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException setting public version for catalog_uuid " + catalogId + " and version " + version + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException setting public version for catalog_uuid " + catalogId + " and version " + version + ": " + ex.getMessage());
		}
	}

	public List<Integer> getVersions(String uri) throws ApolloDatabaseException {

		int catalogId = getCatalogIDFromURI(uri);
		String sql = "SELECT version FROM library_objects WHERE catalog_uri_id = " + catalogId;
		List<Integer> versions = new ArrayList<Integer>();
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int version = rs.getInt(1);
				versions.add(version);
			}

		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException getting versions for uri " + uri + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException getting versions for uri " + uri + ": " + ex.getMessage());
		}

		return versions;
	}

	public int getPublicVersion(String uri) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {

		int catalogId = getCatalogIDFromURI(uri);
		String sql = "SELECT item_id FROM release_versions WHERE catalog_uri_id = " + catalogId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			int itemId;
			if (rs.next()) {
				itemId = rs.getInt(1);
			} else {
				throw new ApolloDatabaseKeyNotFoundException("There was no public version for the catalog_uuid " + catalogId);
			}

			sql = "SELECT version FROM library_objects WHERE id = " + itemId;
			pstmt = getConn().prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new ApolloDatabaseKeyNotFoundException("No item exists with catalog_uri_id " + catalogId);
			}

		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException getting public version for catalog_uri_id " + catalogId + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException getting public version for catalog_uri_id " + catalogId + ": " + ex.getMessage());
		}
	}

	public LibraryItemContainer getLibraryItemContainer(String uri, int version) throws ApolloDatabaseException, IOException {

		String itemJson = getLibraryItemContainerJSON(uri, version);
		return getLibraryItemContainderFromJson(itemJson);
	}

	public LibraryItemContainer getReleaseLibraryItemContainer(String uri) throws ApolloDatabaseException, IOException {

		int publicVersion = getPublicVersion(uri);
		return getLibraryItemContainer(uri, publicVersion);
	}

	public String getLibraryItemContainerJSON(String uri, int version) throws ApolloDatabaseException {
		int catalogId = getCatalogIDFromURI(uri);
		return getItemJSONFromCatalogIdAndVersion(catalogId, version);
	}

	private int getCatalogIDFromURI(String uri) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {
		String sql = "SELECT id FROM catalog_of_uris WHERE uri = ?";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			pstmt.setString(1, uri);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new ApolloDatabaseKeyNotFoundException("No catalog entry with uri " + uri + " exists");
			}
			int id = rs.getInt(1);
			return id;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException checking catalog_of_uris table");
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException checking catalog_of_uris table");
		}

	}

	private String getItemJSONFromCatalogIdAndVersion(int catalogId, int versionId) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {
		String sql = "SELECT json_of_library_object FROM library_objects WHERE catalog_uri_id = " + catalogId + " AND version = " + versionId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new ApolloDatabaseKeyNotFoundException("No library_objects entry with catalog_uri_id " + catalogId + " and version " + versionId + " exists");
			}

			String json = rs.getString(1);
			return json;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException checking catalog_of_uris table");
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException checking catalog_of_uris table");
		}
	}

	private int getItemIdFromCatalogIdAndVersion(int catalogId, int versionId) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {
		String sql = "SELECT id FROM library_objects WHERE catalog_uri_id = " + catalogId + " AND version = " + versionId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new ApolloDatabaseKeyNotFoundException("No library_objects entry with catalog_uri_id " + catalogId + " and version " + versionId + " exists");
			}

			int id = rs.getInt(1);
			return id;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException checking catalog_of_uris table");
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException checking catalog_of_uris table");
		}
	}

	private void checkCatalogId(int catalogId) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {
		String sql = "SELECT * FROM catalog_of_uris WHERE id = " + catalogId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new ApolloDatabaseKeyNotFoundException("No catalog_of_uris entry with id " + catalogId + " exists");
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException checking catalog_of_uris table");
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException checking catalog_of_uris table");
		}
	}

	private <T extends Object> T getObjectFromJson(String json, Class<T> type) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		T object = mapper.readValue(json, type);
		return object;
	}

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
			throw new ApolloDatabaseException("ClassNotFoundException querying objects: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException querying objects: " + ex.getMessage());
		}
	}
	
	private static Epidemic getEpidemic() {
		Epidemic epidemic = new Epidemic();
		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setNcbiTaxonId("12");
		epidemic.getCausalPathogens().add(code);
		return epidemic;
	}

	public static void main(String[] args) throws IOException, ApolloDatabaseException {

		LibraryDbUtils dbUtils = new LibraryDbUtils(new File("C:\\apollo_202\\library_database.properties"));

		Authentication authentication = new Authentication();
		authentication.setRequesterId("library_demo");
		authentication.setRequesterPassword("password");

		int role = dbUtils.getRoleId(LibraryUserRoleTypeEnum.REVIEWER);
		System.out.println(role);
		
//		LibraryItemContainer item = new LibraryItemContainer();
//		Epidemic epidemic = getEpidemic();
//		InfectiousDiseaseControlStrategy strategy = new IndividualTreatmentControlStrategy();
//		strategy.setDescription("test strategy");
//		epidemic.getInfectiousDiseaseControlStrategies().add(strategy);
//
//		CatalogEntry entry = new CatalogEntry();
//		entry.setItemDescription("test description");
//
//		item.setLibraryItem(epidemic);
//
//		int catalogId = dbUtils.addLibraryItem("scenario_uri", item, authentication, "first item");
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
//		dbUtils.setPublicVersion(catalogId, 1);
//		dbUtils.setPublicVersion(catalogId, 2);
//		int publicVersion = dbUtils.getPublicVersion(catalogId);
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
//		LibraryItemContainer container = dbUtils.getLibraryItemContainer("scenario_uri", 1);
//		Epidemic epidemicFromLibrary = (Epidemic) container.getLibraryItem();
		System.out.println("done");
	}
}
