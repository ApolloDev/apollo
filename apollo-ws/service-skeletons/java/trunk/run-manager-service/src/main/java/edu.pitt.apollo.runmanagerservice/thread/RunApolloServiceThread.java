package edu.pitt.apollo.runmanagerservice.thread;

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
    protected BigInteger runId;

    public RunApolloServiceThread(BigInteger runId) {
        this.runId = runId;
    }

    public abstract void setAuthenticationPasswordFieldToBlank();


}
