package edu.pitt.apollo.restservice.types;

import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 * Created by dcs27 on 5/18/15.
 */
public class UserAndRoleInformation {
    String userPassword;
    SoftwareIdentification softwareIdentification;
    boolean canRunSoftware;
    boolean canRequestPrivileged;

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public SoftwareIdentification getSoftwareIdentification() {
        return softwareIdentification;
    }

    public void setSoftwareIdentification(SoftwareIdentification softwareIdentification) {
        this.softwareIdentification = softwareIdentification;
    }

    public boolean isCanRunSoftware() {
        return canRunSoftware;
    }

    public void setCanRunSoftware(boolean canRunSoftware) {
        this.canRunSoftware = canRunSoftware;
    }

    public boolean isCanRequestPrivileged() {
        return canRequestPrivileged;
    }

    public void setCanRequestPrivileged(boolean canRequestPrivileged) {
        this.canRequestPrivileged = canRequestPrivileged;
    }
}
