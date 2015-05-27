package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.services_common.v3_0_0.Authentication;
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
    protected BigInteger runId;

    public RunApolloServiceThread(BigInteger runId, Authentication authentication) {
        this.runId = runId;
        this.authentication = authentication;
    }
}
