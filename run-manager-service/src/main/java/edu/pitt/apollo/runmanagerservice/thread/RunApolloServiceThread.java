package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jul 30, 2014
 * Time: 2:53:29 PM
 * Class: RunApolloServiceThread
 */
public abstract class RunApolloServiceThread extends Thread {

    static Logger logger = LoggerFactory.getLogger(RunApolloServiceThread.class);

    protected Authentication authentication;
	protected SoftwareIdentification softwareId;
    protected BigInteger runId;

    public RunApolloServiceThread(BigInteger runId, SoftwareIdentification softwareId, Authentication authentication) {
        this.runId = runId;
        this.authentication = authentication;
		this.softwareId = softwareId;
    }
}
