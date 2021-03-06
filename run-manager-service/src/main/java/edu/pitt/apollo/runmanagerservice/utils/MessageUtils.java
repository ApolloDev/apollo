/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservice.utils;

import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0_2.RunResult;

import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class MessageUtils {

	public static RunResult createRunResult(BigInteger runId, MethodCallStatusEnum statusEnum, String error) {
		RunResult runResult = new RunResult();
		MethodCallStatus methodCallStatus = new MethodCallStatus();
		runResult.setMethodCallStatus(methodCallStatus);
		runResult.setRunId(runId);
		runResult.getMethodCallStatus().setMessage(error);
		runResult.getMethodCallStatus().setStatus(statusEnum);
		return runResult;
	}

}
