package edu.pitt.apollo.db;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/11/15.
 */
public class RunIdAndCollisionId {
    BigInteger runId;
    Integer collisionId;

    public RunIdAndCollisionId(BigInteger runId, Integer collisionId) {
        this.runId = runId;
        this.collisionId = collisionId;
    }

    public Integer getCollisionId() {
        return collisionId;
    }

    public BigInteger getRunId() {
        return runId;
    }
}
