package edu.pitt.apollo.db;

import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 2, 2015
 * Time: 4:11:49 PM
 * Class: LibraryReadOnlyDbUtils
 */
public class LibraryReadOnlyDbUtils extends LibraryDbUtils {

	private static final String LIBRARY_READ_ONLY_DB_RESOURCE_NAME = "ApolloLibraryDBReadOnly_310";

	public LibraryReadOnlyDbUtils() throws ApolloDatabaseException {
		super(LIBRARY_READ_ONLY_DB_RESOURCE_NAME);
	}

}
