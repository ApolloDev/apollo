package edu.pitt.apollo.restservice.rest.responsemessage;

import edu.pitt.apollo.restservice.types.RunInformation;

/**
 * Created by dcs27 on 5/21/15.
 */
public class GetRunInformationRestMessage {
    Meta meta;
    RunInformation runInformation;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public RunInformation getRunInformation() {
        return runInformation;
    }

    public void setRunInformation(RunInformation runInformation) {
        this.runInformation = runInformation;
    }
}
