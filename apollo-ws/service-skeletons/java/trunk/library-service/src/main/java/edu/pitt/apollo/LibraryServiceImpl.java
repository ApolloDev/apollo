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

import java.io.File;
import java.util.Map;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pitt.apollo.library_service_types.v2_1_0.AddOrUpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.AddOrUpdateLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v2_1_0.AddReviewerCommentMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.AddReviewerCommentResult;
import edu.pitt.apollo.library_service_types.v2_1_0.GetCommentsMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetCommentsResult;
import edu.pitt.apollo.library_service_types.v2_1_0.GetDiffMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetDiffResult;
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v2_1_0.GetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v2_1_0.GetVersionsMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetVersionsResult;
import edu.pitt.apollo.library_service_types.v2_1_0.ModifyGroupOwnershipMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.ModifyGroupOwnershipResult;
import edu.pitt.apollo.library_service_types.v2_1_0.QueryMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.QueryResult;
import edu.pitt.apollo.library_service_types.v2_1_0.SetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.SetReleaseVersionResult;
import edu.pitt.apollo.service.libraryservice.v2_1_0.LibraryServiceEI;



@WebService(targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_1_0/", portName = "LibraryServiceEndpoint", serviceName = "LibraryService_v2.1.0", endpointInterface = "edu.pitt.apollo.service.libraryservice.v2_1_0.LibraryServiceEI")
class LibraryServiceImpl implements LibraryServiceEI {

	static final Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);
	
	

	static {
		Map<String, String> env = System.getenv();
		String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			logger.info(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + apolloDir);
		} else {
			logger.error(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + "environment variable not found!");
		}

		
	}



	@Override
	public GetVersionsResult getVersions(GetVersionsMessage getVersionsMessage) {
		return null;
	}



	@Override
	public QueryResult query(QueryMessage queryMessage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public GetLibraryItemContainerResult getLibraryItemContainer(
			GetLibraryItemContainerMessage getLibraryItemContainerMessage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public GetDiffResult getDiff(GetDiffMessage getDiffMessage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public GetCommentsResult getComments(GetCommentsMessage getCommentsMessage) {
		GetCommentsResult res = new GetCommentsResult();
		System.out.println("ok!");
		return null;
	}



	@Override
	public ModifyGroupOwnershipResult addGroup(
			ModifyGroupOwnershipMessage addGroupMessage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public GetReleaseVersionResult getReleaseVersion(
			GetReleaseVersionMessage getReleaseVersionMessage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ModifyGroupOwnershipResult removeGroup(
			ModifyGroupOwnershipMessage removeGroupMessage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public AddReviewerCommentResult addReviewerComment(
			AddReviewerCommentMessage addReviewerCommentMessage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public AddOrUpdateLibraryItemContainerResult updateLibraryItemContainer(
			AddOrUpdateLibraryItemContainerMessage addOrUpdateLibraryItemContainerMessage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public SetReleaseVersionResult setReleaseVersion(
			SetReleaseVersionMessage setReleaseVersionMessage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public AddOrUpdateLibraryItemContainerResult addLibraryItemContainer(
			AddOrUpdateLibraryItemContainerMessage addOrUpdateLibraryItemContainerMessage) {
		// TODO Auto-generated method stub
		return null;
	}

}
