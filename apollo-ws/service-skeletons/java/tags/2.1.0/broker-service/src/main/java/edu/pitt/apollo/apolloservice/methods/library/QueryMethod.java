package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v2_1_0.QueryMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.QueryResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:58:15 PM
 * Class: QueryMethod
 */
public class QueryMethod extends LibraryMethod {

	public static QueryResult query(QueryMessage queryMessage) {
		return getLibraryServicePort().query(queryMessage);
	}
	
}
