/* Copyright 2012 University of Pittsburgh
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package edu.pitt.apollo;

import java.math.BigInteger;

import edu.pitt.apollo.data_service_types.v3_0_0.*;
import edu.pitt.apollo.dataservice.methods.*;
import edu.pitt.apollo.dataservice.methods.file.GetFileContentMethod;
import edu.pitt.apollo.dataservice.methods.softwaremethods.GetListOfRegisteredSoftwareMethod;
import edu.pitt.apollo.dataservice.methods.url.GetURLContentMethod;
import edu.pitt.apollo.dataservice.methods.user.AddRoleMethod;
import edu.pitt.apollo.dataservice.methods.user.AddUserMethod;
import edu.pitt.apollo.dataservice.methods.user.AddUserRoleMethod;
import edu.pitt.apollo.dataservice.methods.user.DeleteUserMethod;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.service.dataservice.v3_0_0.*;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

import javax.jws.WebService;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/dataservice/v3_0_0/", portName = "DataServiceEndpoint", serviceName = "DataService_v3.0.0", endpointInterface = "edu.pitt.apollo.service.dataservice.v3_0_0.DataServiceEI")
public class DataServiceImpl implements DataServiceEI {

	private static final ApolloServiceQueue serviceQueue;

	static {
		serviceQueue = new ApolloServiceQueue();
	}

	@Override
	public ListOutputFilesForSoftwareResult listOutputFilesForSoftware(
			ListOutputFilesForSoftwareMessage listOutputFilesForSoftwareMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AddRoleResult addRole(AddRoleMessage message) {
		AddRoleResult result = AddRoleMethod.addRole(message);
		return result;
	}



	@Override
	public GetFileContentResult getFileContent(GetFileContentMessage message) {
		GetFileContentResult result = GetFileContentMethod.getFileContent(message);
		return result;
	}

	@Override
	public AssociateContentWithRunIdResult associateContentWithRunId(AssociateContentWithRunIdMessage message) {
		AssociateContentWithRunIdResult result = AssociateContentWithRunIdMethod.associateContentWithRunIdResult(message);
		return result;
	}

	@Override
	public GetDataContentForSoftwareResult getDataContentForSoftware(GetDataContentForSoftwareMessage getDataContentForSoftware) {
//		GetDataContentForSoftwareResult result = GetDataContentForSoftwareMethod.getDataContentForSoftware();
		return null;
	}

	@Override
	public GetStatusOfRunResult getStatusOfRun(GetStatusOfRunMessage message) {
		GetStatusOfRunResult result = GetStatusOfRunMethod.getStatusOfRunAndGetResult(message);
		return result;
	}

	@Override
	public RemoveRunDataResult removeRunData(RemoveRunDataMessage message) {
		RemoveRunDataResult result = RemoveRunDataMethod.removeRunDataAndGetResult(message);
		return result;
	}

	@Override
	public GetRunDataDescriptionIdResult getRunDataDescriptionId(GetRunDataDescriptionIdMessage message) {
		GetRunDataDescriptionIdResult result = GetRunDataDescriptionIdMethod.buildRunDataDescriptionIdResultMessage(message);
		return result;
	}

	@Override
	public GetSoftwareIdentificationKeyFromSoftwareIdentificationResult getSoftwareIdentificationKeyFromSoftwareIdentification(GetSoftwareIdentificationKeyFromSoftwareIdentificationMessage message) {
		GetSoftwareIdentificationKeyFromSoftwareIdentificationResult result = GetSoftwareIdentificationKeyFromSoftwareIdentificationMethod.getSoftwareIdentificationKeyFromSoftwareIdentification(message);
		return result;
	}


	@Override
	public GetSoftwareIdentificationKeyForRunResult getSoftwareIdentificationKeyForRun(GetSoftwareIdentificationKeyForRunMessage message) {
		GetSoftwareIdentificationKeyForRunResult result = GetSoftwareIdentificationKeyForRunMethod.getSoftwareIdentificationKeyForRun(message);
		return result;
	}

	@Override
	public DeleteUserResult deleteUser(DeleteUserMessage message) {
		DeleteUserResult result = DeleteUserMethod.deleteUser(message);
		return result;
	}

	@Override
	public ListFilesResult listFilesAssociatedToRun(ListFilesMessage message) {
		ListFilesResult result = ListFilesAssociatedToRunMethod.listFilesAssocaitedToRun(message);
		return result;
	}

	@Override
	public GetListOfRegisteredSoftwareResult getListOfRegisteredSoftware() {
		GetListOfRegisteredSoftwareResult result = GetListOfRegisteredSoftwareMethod.getListOfRegisteredSoftware();
		return result;
	}


	@Override
	public AddUserResult addUser(AddUserMessage message) {
		AddUserResult result = AddUserMethod.addUser(message);
		return result;
	}

	@Override
	public GetURLContentResult getURLContent(GetURLContentMessage message) {
		GetURLContentResult result = GetURLContentMethod.getUrl(message);
		return result;
	}

	@Override
	public AssociateFileWithRunIdResult associateFileWithRunId(AssociateFileWithRunIdMessage message) {
		AssociateFileWithRunIdResult result = AssociateFileWithRunIdMethod.associateFileWithRunId(message);
		return result;
	}


	@Override
	public GetSoftwareIdentificationForRunResult getSoftwareIdentificationForRun(GetSoftwareIdentificationForRunMessage message) {
		try {
			return GetSoftwareIdentificationForRunMethod.buildResultMessage(message);
		} catch (ApolloDatabaseException e) {
			GetSoftwareIdentificationForRunResult result = new GetSoftwareIdentificationForRunResult();

			MethodCallStatus status = new MethodCallStatus();
			status.setMessage(e.getMessage());
			status.setStatus(MethodCallStatusEnum.FAILED);
			result.setMethodCallStatus(status);
			return result;
		}

	}

	@Override
	public MethodCallStatus getOutputFilesURLAsZip(BigInteger runId) {
		GetOutputFilesURLAsZipMethod method = new GetOutputFilesURLAsZipMethod(serviceQueue, runId);
		method.downloadFiles();

		return null;
	}

	@Override
	public AddUserRoleResult addUserRole(AddUserRoleMessage message) {
		AddUserRoleResult result = AddUserRoleMethod.addUserRole(message);
		return result;
	}

	@Override
	public AssociateURLWithRunIdResult associateURLWithRunId(AssociateURLWithRunIdMessage message) {
		AssociateURLWithRunIdResult result = AssociateURLWithRunIdMethod.associateURLWithRunId(message);
		return result;
	}

	@Override
	public ListURLsResult listURLsAssociatedToRun(ListURLsMessage message) {
		ListURLsResult result = ListURLsAssociatedToRunMethod.listURLsAssociatedToRun(message);
		return result;
	}

	@Override
	public AddTextDataContentResult addTextDataContent(AddTextDataContentMessage message) {
		AddTextDataContentResult result = AddTextDataContentMethod.addTextDataContent(message);

		return result;
	}



	@Override
	public MethodCallStatus getOutputFilesURLs(BigInteger runId) {
		GetOutputFilesURLsMethod method = new GetOutputFilesURLsMethod(serviceQueue, runId);
		method.downloadFiles();
		
		return null;
	}



	@Override
	public MethodCallStatus getAllOutputFilesURLAsZip(BigInteger runId) {
		GetAllOutputFilesURLAsZipMethod method = new GetAllOutputFilesURLAsZipMethod(serviceQueue, runId);
		method.downloadFiles();
		
		return null;
	}

	@Override
	public UpdateStatusOfRunResult updateStatusOfRun(UpdateStatusOfRunMessage message) {
		UpdateStatusOfRunResult result = UpdateStatusOfRunMethod.updateStatusOfRunAndGetResult(message);
		return result;
	}


}
