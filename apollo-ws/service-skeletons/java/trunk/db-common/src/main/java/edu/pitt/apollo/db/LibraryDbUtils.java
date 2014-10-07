package edu.pitt.apollo.db;

import edu.pitt.apollo.types.v2_0_2.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_0_2.Authentication;
import edu.pitt.apollo.types.v2_0_2.Epidemic;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

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

	@Override
	protected ByteArrayOutputStream getJsonBytes(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
//			mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
//			mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
//			mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
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
			getUserKey(userName, userPassword);
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

	public int getUserKey(String userName, String userPassword) throws ApolloDatabaseUserPasswordException,
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

	public int addCatalogItem(String description, Object item, Authentication authentication) throws ApolloDatabaseException {
		// user has already been authenticated

		String sql = "INSERT INTO catalog (description) VALUES (?);";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, description);
			pstmt.execute();

			ResultSet rs = pstmt.getGeneratedKeys();
			int catalogId;
			if (rs.next()) {
				catalogId = rs.getInt(1);
			} else {
				throw new ApolloDatabaseRecordNotInsertedException("Catalog item insert did not return a run ID");
			}

			addItemVersion(catalogId, item, authentication);

			return catalogId;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException adding catalog item: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException adding catalog item: " + ex.getMessage());
		}
	}

	public int addItemVersion(int catalogId, Object item, Authentication authentication) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {

		// check catalog version
		try {
			checkCatalogId(catalogId);

			int userKey = getUserKey(authentication.getRequesterId(), authentication.getRequesterPassword());

			String itemJson = getJSONStringForLibraryItem(item);
			String sql = "INSERT INTO library_objects (catalog_id,library_object_as_json,user_id) VALUES (?,?,?)";
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

	public void addComment(int catalogId, int version, String comment, Authentication authentication) throws ApolloDatabaseException {

		// check catalogId and itemVersion
		int itemId = getItemIdFromCatalogIdAndVersion(catalogId, version);
		int userId = getUserKey(authentication.getRequesterId(), authentication.getRequesterPassword());
		String sql = "INSERT INTO comments (item_id,comment,user_id) VALUES (?,?,?)";
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			pstmt.setInt(1, itemId);
			pstmt.setString(2, comment);
			pstmt.setInt(3, userId);

			pstmt.execute();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException adding comment for catalog_id " + catalogId + " and version " + version + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException adding comment for catalog_id " + catalogId + " and version " + version + ": " + ex.getMessage());
		}
	}
	
	public void setPublicVersion(int catalogId, int version) throws ApolloDatabaseException {

		int itemId = getItemIdFromCatalogIdAndVersion(catalogId, version);
		String insert = "INSERT INTO public_versions (catalog_id, item_id) SELECT " + catalogId + "," + itemId;
		String upsert = "UPDATE public_versions SET item_id = " + itemId + " WHERE catalog_id = " + catalogId;
		String sql = "BEGIN; LOCK TABLE public_versions IN SHARE ROW EXCLUSIVE MODE; "
				+ "WITH upsert AS (" + upsert + " RETURNING *) " + insert + " WHERE NOT EXISTS (SELECT * FROM upsert);"
				+ "COMMIT;";

		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			pstmt.execute();
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException setting public version for catalog_id " + catalogId + " and version " + version + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException setting public version for catalog_id " + catalogId + " and version " + version + ": " + ex.getMessage());
		}
	}

	public int getPublicVersion(int catalogId) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {

		String sql = "SELECT item_id FROM public_versions WHERE catalog_id = " + catalogId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			int itemId;
			if (rs.next()) {
				itemId = rs.getInt(1);
			} else {
				throw new ApolloDatabaseKeyNotFoundException("There was no public version for the catalog_id " + catalogId);
			}

			sql = "SELECT version FROM library_objects WHERE id = " + itemId;
			pstmt = getConn().prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new ApolloDatabaseKeyNotFoundException("No item exists with catalog_id " + catalogId);
			}

		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException getting public version for catalog_id " + catalogId + ": " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException getting public version for catalog_id " + catalogId + ": " + ex.getMessage());
		}

	}

	private int getItemIdFromCatalogIdAndVersion(int catalogId, int versionId) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {
		String sql = "SELECT id FROM library_objects WHERE catalog_id = " + catalogId + " AND version = " + versionId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new ApolloDatabaseKeyNotFoundException("No library_objects entry with catalog_id " + catalogId + " and version " + versionId + " exists");
			}

			int itemId = rs.getInt(1);
			return itemId;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException checking catalog table");
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException checking catalog table");
		}
	}

	private void checkCatalogId(int catalogId) throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {
		String sql = "SELECT * FROM catalog WHERE id = " + catalogId;
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				throw new ApolloDatabaseKeyNotFoundException("No catalog entry with id " + catalogId + " exists");
			}
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException checking catalog table");
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException checking catalog table");
		}
	}

	private <T extends Object> T getObjectFromJson(String json, Class<T> type) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		T object = mapper.readValue(json, type);
		return object;
	}

	public <T extends Object> List<T> queryObjects(String sql, Class<T> type) throws ApolloDatabaseException {

		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				String library_object_as_json = rs.getString("library_object_as_json");
				T object = getObjectFromJson(library_object_as_json, type);
				list.add(object);
			}

			return list;
		} catch (ClassNotFoundException ex) {
			throw new ApolloDatabaseException("ClassNotFoundException querying objects: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException querying objects: " + ex.getMessage());
		} catch (IOException ex) {
			throw new ApolloDatabaseException("IOException parsing JSON object: " + ex.getMessage());
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

		Epidemic epidemic = getEpidemic();
		int catalogId = dbUtils.addCatalogItem("scenario", epidemic, authentication);
		System.out.println("Catalog ID: " + catalogId);

		Epidemic epidemic2 = getEpidemic();
		epidemic2.getCausalPathogens().get(0).setNcbiTaxonId("15");

		catalogId = dbUtils.addCatalogItem("scenario", epidemic2, authentication);
		System.out.println("Catalog ID: " + catalogId);

		Epidemic epidemic3 = getEpidemic();
		epidemic3.setCurator("someone");
		epidemic3.getCausalPathogens().get(0).setNcbiTaxonId("15");

		catalogId = dbUtils.addCatalogItem("scenario", epidemic3, authentication);
		System.out.println("Catalog ID: " + catalogId);

//		int versionId = dbUtils.addItemVersion(catalogId, epidemic, authentication);
//		System.out.println("Version ID: " + versionId);
//		dbUtils.addComment(catalogId, versionId, "this is a comment", authentication);
//		dbUtils.setPublicVersion(catalogId, 1);
//		dbUtils.setPublicVersion(catalogId, 2);
//		int publicVersion = dbUtils.getPublicVersion(catalogId);
//		System.out.println("Public version: " + publicVersion);
		String sql = "SELECT\n"
				+ "	library_object_as_json \n"
				+ "FROM\n"
				+ "	(	SELECT\n"
				+ "			library_object_as_json,\n"
				+ "			json_array_elements(library_object_as_json->'causalPathogens') AS pathogen \n"
				+ "		FROM\n"
				+ "			library_objects \n"
				+ "		WHERE\n"
				+ "			library_object_as_json->>'class' = 'edu.pitt.apollo.types.v2_0_2.Epidemic' ) t \n"
				+ "WHERE\n"
				+ "	t.pathogen->>'ncbiTaxonId' = '15'";
		List<Epidemic> objects = dbUtils.queryObjects(sql, Epidemic.class);
		System.out.println("list size: " + objects.size());
	}
}
