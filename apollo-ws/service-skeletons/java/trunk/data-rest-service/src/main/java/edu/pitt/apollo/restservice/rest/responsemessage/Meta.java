package edu.pitt.apollo.restservice.rest.responsemessage;

/**
 * Created by dcs27 on 4/21/15.
 */
public class Meta {
    Integer status;
    String statusMessage;
    String source = "Powered by the Apollo Web Services v3.0.0";
    Integer numberOfReturnedResults;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getNumberOfReturnedResults() {
        return numberOfReturnedResults;
    }

    public void setNumberOfReturnedResults(Integer numberOfReturnedResults) {
        this.numberOfReturnedResults = numberOfReturnedResults;
    }
}
