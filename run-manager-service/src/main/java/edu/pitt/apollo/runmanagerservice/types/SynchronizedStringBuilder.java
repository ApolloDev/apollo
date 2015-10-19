package edu.pitt.apollo.runmanagerservice.types;

/**
 * Created by jdl50 on 4/8/15.
 */
public class SynchronizedStringBuilder {

    private StringBuilder sb;

    public SynchronizedStringBuilder() {
        this.sb = new StringBuilder();
    }

    public synchronized SynchronizedStringBuilder append(String str) {
        sb.append(str);
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
