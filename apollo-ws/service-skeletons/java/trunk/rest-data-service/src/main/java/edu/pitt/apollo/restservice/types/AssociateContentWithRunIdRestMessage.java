package edu.pitt.apollo.restservice.types;

import java.math.BigInteger;

/**
 * Created by dcs27 on 5/20/15.
 */
public class AssociateContentWithRunIdRestMessage {
    String fileContentOrUrl;
    String sourceSoftwareName;
    String sourceSoftwareVersion;
    String destinationSoftwareName;
    String destinationSoftwareVersion;
    String contentLabel;
    String contentType;


    public String getFileContentOrUrl() {
        return fileContentOrUrl;
    }

    public void setFileContentOrUrl(String fileContentOrUrl) {
        this.fileContentOrUrl = fileContentOrUrl;
    }

    public String getSourceSoftwareName() {
        return sourceSoftwareName;
    }

    public void setSourceSoftwareName(String sourceSoftwareName) {
        this.sourceSoftwareName = sourceSoftwareName;
    }

    public String getSourceSoftwareVersion() {
        return sourceSoftwareVersion;
    }

    public void setSourceSoftwareVersion(String sourceSoftwareVersion) {
        this.sourceSoftwareVersion = sourceSoftwareVersion;
    }

    public String getDestinationSoftwareName() {
        return destinationSoftwareName;
    }

    public void setDestinationSoftwareName(String destinationSoftwareName) {
        this.destinationSoftwareName = destinationSoftwareName;
    }

    public String getDestinationSoftwareVersion() {
        return destinationSoftwareVersion;
    }

    public void setDestinationSoftwareVersion(String destinationSoftwareVersion) {
        this.destinationSoftwareVersion = destinationSoftwareVersion;
    }

    public String getContentLabel() {
        return contentLabel;
    }

    public void setContentLabel(String contentLabel) {
        this.contentLabel = contentLabel;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
