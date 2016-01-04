package edu.pitt.apollo.db;

import edu.pitt.apollo.types.v4_0.ApolloSoftwareTypeEnum;;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jul 28, 2014
 * Time: 11:33:24 AM
 * Class: ApolloDbAuthenticationAuthorizationTest
 */
public class ApolloDbAuthenticationAuthorizationTest extends BaseDbUtilsTest {

	private static final String UNKNOWN_USER_NAME = "unknown_user";
	private static final String TEST_USER_NAME = "test_user";
	private static final String TEST_USER_PASSWORD = "test_user_password";
	private static final String TEST_USER_EMAIL = "test_user_email";
	private static final boolean TEST_AUTHORIZATION_REQUEST_RUN = true;
	private static final SoftwareIdentification TEST_AUTHORIZED_SOFTWARE_ID;
	private static final SoftwareIdentification TEST_UNAUTHORIZED_SOFTWARE_ID;
	private static final Authentication TEST_USER_AUTHENTICATION;

	public void testTemp() {
		assert(true);
	}
	
//	@Override
//	public void setUp() throws Exception {
//		super.setUp();
//	}
//	
//	private int addTestUser() throws ApolloDatabaseRecordAlreadyExistsException, ApolloDatabaseException {
//		return apolloDbUtils.addUser(TEST_USER_NAME, TEST_USER_PASSWORD, TEST_USER_EMAIL);
//	}
//
//	private int addTestUserIfNotAlreadyAdded() throws ApolloDatabaseException {
//		try {
//			return addTestUser();
//		} catch (ApolloDatabaseRecordAlreadyExistsException ex) {
//			return getTestUser();
//		}
//	}
//
//	private int getTestUser() throws ApolloDatabaseKeyNotFoundException, ApolloDatabaseException {
//		return apolloDbUtils.getUserKey(TEST_USER_NAME, TEST_USER_PASSWORD);
//	}
//
//	private void deleteTestUser() throws ApolloDatabaseException {
//		apolloDbUtils.deleteUser(TEST_USER_NAME, TEST_USER_PASSWORD);
//	}
//
//	private boolean getAuthorizationForPrivilegedRequest() throws ApolloDatabaseException {
//		Authentication privilegedAuthentication = new Authentication();
//		privilegedAuthentication.setRequesterId(TEST_USER_AUTHENTICATION.getRequesterId() + "+priv");
//		privilegedAuthentication.setRequesterPassword(TEST_USER_AUTHENTICATION.getRequesterPassword());
//
//		return apolloDbUtils.authorizeUser(privilegedAuthentication,
//			TEST_AUTHORIZED_SOFTWARE_ID, TEST_AUTHORIZATION_REQUEST_RUN);
//
//	}
//
//	public void testAuthenticateUnknownUser() {
//
//		Authentication unknownUserAuthentication = new Authentication();
//		unknownUserAuthentication.setRequesterId(UNKNOWN_USER_NAME);
//		unknownUserAuthentication.setRequesterPassword("");
//		try {
//			boolean userAuthenticated = apolloDbUtils.authenticateUser(unknownUserAuthentication);
//			assert (!userAuthenticated);
//		} catch (ApolloDatabaseUserPasswordException ex) {
//			fail("The unknown user \"" + UNKNOWN_USER_NAME + "\" exists in the Apollo database");
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//	}

//	public void testAuthenticateKnownUser() {
//
//		try {
//			addTestUserIfNotAlreadyAdded();
//			boolean userAuthenticated = apolloDbUtils.authenticateUser(TEST_USER_AUTHENTICATION);
//			assert (userAuthenticated);
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//	}
//
//	public void testAddUser() {
//		try {
//			addTestUser();
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		} catch (ApolloDatabaseRecordAlreadyExistsException ex) {
//			try {
//				deleteTestUser();
//			} catch (ApolloDatabaseException ex1) {
//				fail(ex1.getMessage());
//				return;
//			}
//			testAddUser();
//		}
//	}
//
//	public void testDeleteUser() {
//
//		try {
//			addTestUserIfNotAlreadyAdded();
//			deleteTestUser();
//			try {
//				getTestUser();
//			} catch (ApolloDatabaseKeyNotFoundException ex) {
//				assert (true);
//				return;
//			}
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//
//		fail("The test user was not succesfully deleted from the Apollo database");
//	}
//
//	public void testGetUser() {
//
//		try {
//			addTestUserIfNotAlreadyAdded();
//			int userKey = getTestUser();
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//	}
//
//	public void testAddUserRoleForNonPrivilegedAccess() {
//
//		try {
//			addTestUserIfNotAlreadyAdded();
//			apolloDbUtils.addUserRole(TEST_USER_NAME, TEST_USER_PASSWORD, TEST_AUTHORIZED_SOFTWARE_ID,
//				TEST_AUTHORIZATION_REQUEST_RUN, false);
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//	}
//
//	public void testAddUserRoleForPrivilegedAccess() {
//
//		try {
//			addTestUserIfNotAlreadyAdded();
//			apolloDbUtils.addUserRole(TEST_USER_NAME, TEST_USER_PASSWORD, TEST_AUTHORIZED_SOFTWARE_ID,
//				TEST_AUTHORIZATION_REQUEST_RUN, true);
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//	}
//
//	public void testSuccessfulUserAuthorization() {
//		try {
//			addTestUserIfNotAlreadyAdded();
//			testAddUserRoleForNonPrivilegedAccess();
//
//			boolean authorized = apolloDbUtils.authorizeUser(TEST_USER_AUTHENTICATION, TEST_AUTHORIZED_SOFTWARE_ID,
//				TEST_AUTHORIZATION_REQUEST_RUN);
//			assertEquals(true, authorized);
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//	}
//
//	public void testAuthorizationWithUnauthorizedPrivilegedRequest() {
//		try {
//			testAddUser();
//			testAddUserRoleForNonPrivilegedAccess();
//			boolean authorized = getAuthorizationForPrivilegedRequest();
//			assert(!authorized);
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//	}
//
//	public void testAuthorizationWithAuthorizedPrivilegedRequest() {
//		try {
//			testAddUser();
//			testAddUserRoleForPrivilegedAccess();
//			boolean authorized = getAuthorizationForPrivilegedRequest();
//			assert(authorized);
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//	}
//
//	public void testAuthorizationWithUnauthorizedSoftware() {
//
//		try {
//			addTestUserIfNotAlreadyAdded();
//			boolean authorized = apolloDbUtils.authorizeUser(TEST_USER_AUTHENTICATION,
//				TEST_UNAUTHORIZED_SOFTWARE_ID, TEST_AUTHORIZATION_REQUEST_RUN);
//			assertEquals(false, authorized);
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//		}
//	}

	static {
		TEST_AUTHORIZED_SOFTWARE_ID = new SoftwareIdentification();
		TEST_AUTHORIZED_SOFTWARE_ID.setSoftwareName("FRED");
		TEST_AUTHORIZED_SOFTWARE_ID.setSoftwareDeveloper("UPitt,PSC,CMU");
		TEST_AUTHORIZED_SOFTWARE_ID.setSoftwareVersion("2.0.1_i");
		TEST_AUTHORIZED_SOFTWARE_ID.setSoftwareType(ApolloSoftwareTypeEnum.SIMULATOR);
		TEST_UNAUTHORIZED_SOFTWARE_ID = new SoftwareIdentification();
		TEST_UNAUTHORIZED_SOFTWARE_ID.setSoftwareName("SEIR");
		TEST_UNAUTHORIZED_SOFTWARE_ID.setSoftwareDeveloper("UPitt");
		TEST_UNAUTHORIZED_SOFTWARE_ID.setSoftwareVersion("3.0");
		TEST_UNAUTHORIZED_SOFTWARE_ID.setSoftwareType(ApolloSoftwareTypeEnum.SIMULATOR);
		TEST_USER_AUTHENTICATION = new Authentication();
		TEST_USER_AUTHENTICATION.setRequesterId(TEST_USER_NAME);
		TEST_USER_AUTHENTICATION.setRequesterPassword(TEST_USER_PASSWORD);
	}
}
