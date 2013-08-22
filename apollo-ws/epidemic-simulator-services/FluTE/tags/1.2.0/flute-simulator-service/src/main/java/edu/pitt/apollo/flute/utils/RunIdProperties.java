package edu.pitt.apollo.flute.utils;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2013
 * Time: 1:45:49 PM
 * Class: RunIdProperties
 * IDE: NetBeans 6.9.1
 */
public class RunIdProperties {

    private String runId = null;
    private boolean runIdInDatabase = false;

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public void setRunIdInDatabase(boolean runIdInDatabase) {
        this.runIdInDatabase = runIdInDatabase;
    }

    public String getRunId() {
        return runId;
    }

    public boolean isRunIdInDatabase() {
        return runIdInDatabase;
    }
}
