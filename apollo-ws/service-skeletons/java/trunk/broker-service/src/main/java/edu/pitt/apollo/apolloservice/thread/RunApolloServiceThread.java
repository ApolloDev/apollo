package edu.pitt.apollo.apolloservice.thread;

import edu.pitt.apollo.apolloservice.database.ApolloDbUtilsContainer;
import edu.pitt.apollo.db.ApolloDbUtils;
import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jul 30, 2014
 * Time: 2:53:29 PM
 * Class: RunApolloServiceThread
 */
public abstract class RunApolloServiceThread extends Thread {

	static Logger logger = LoggerFactory.getLogger(RunApolloServiceThread.class);
	protected final ApolloDbUtils dbUtils;
	protected BigInteger runId;

	public RunApolloServiceThread() {
		this.dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();
	}
	
	public abstract void setAuthenticationPasswordFieldToBlank();
	

}
