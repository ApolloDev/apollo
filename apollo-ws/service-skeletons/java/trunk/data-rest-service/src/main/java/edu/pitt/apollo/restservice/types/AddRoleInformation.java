package edu.pitt.apollo.restservice.types;

import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 * Created by dcs27 on 5/18/15.
 */
public class AddRoleInformation {
    SoftwareIdentification softwareIdentification;
    boolean canRun;
    boolean canViewCache;

    public SoftwareIdentification getSoftwareIdentification() {
        return softwareIdentification;
    }

    public void setSoftwareIdentification(SoftwareIdentification softwareIdentification) {
        this.softwareIdentification = softwareIdentification;
    }

    public boolean isCanRun() {
        return canRun;
    }

    public void setCanRunSoftware(boolean canRun) {
        this.canRun = canRun;
    }

    public boolean isCanViewCache() {
        return canViewCache;
    }

    public void setCanViewCache(boolean canViewCache) {
        this.canViewCache = canViewCache;
    }
}
