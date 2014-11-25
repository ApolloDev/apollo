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

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;

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
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemURIsMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemURIsResult;
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
import edu.pitt.apollo.libraryservice.methods.AddLibraryItemMethod;
import edu.pitt.apollo.libraryservice.methods.AddReviewerCommentMethod;
import edu.pitt.apollo.libraryservice.methods.GetCommentsMethod;
import edu.pitt.apollo.libraryservice.methods.GetDiffMethod;
import edu.pitt.apollo.libraryservice.methods.GetLibraryItemMethod;
import edu.pitt.apollo.libraryservice.methods.GetLibraryItemURIsMethod;
import edu.pitt.apollo.libraryservice.methods.GetReleaseVersionMethod;
import edu.pitt.apollo.libraryservice.methods.GetVersionsMethod;
import edu.pitt.apollo.libraryservice.methods.QueryLibraryMethod;
import edu.pitt.apollo.libraryservice.methods.SetReleaseVersionMethod;
import edu.pitt.apollo.libraryservice.methods.UpdateLibraryItemMethod;
import edu.pitt.apollo.service.libraryservice.v2_1_0.LibraryServiceEI;
import edu.pitt.apollo.services_common.v2_1_0.Authentication;

import java.io.IOException;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_1_0/", portName = "LibraryServiceEndpoint", serviceName = "LibraryService_v2.1.0", endpointInterface = "edu.pitt.apollo.service.libraryservice.v2_1_0.LibraryServiceEI")
public class LibraryServiceImpl implements LibraryServiceEI {

	static final Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);
	public static final String APOLLO_DIR;
	private static final LibraryDbUtils libraryDbUtils;
	private static final LibraryDbUtils readonlyLibraryDbUtils;

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

		APOLLO_DIR = apolloDir;
		try {
			libraryDbUtils = new LibraryDbUtils(new File(APOLLO_DIR + "library_database.properties"));
			readonlyLibraryDbUtils = new LibraryDbUtils(new File(APOLLO_DIR + "library_database_readonly.properties"));
		} catch (IOException ex) {
			throw new ExceptionInInitializerError("IOException creating LibraryDbUtils: " + ex.getMessage());
		}
	}

	@Override
	public GetVersionsResult getVersions(GetVersionsMessage getVersionsMessage) {
		return GetVersionsMethod.getVersions(libraryDbUtils, getVersionsMessage);
	}

	@Override
	public QueryResult query(QueryMessage queryMessage) {
		return QueryLibraryMethod.queryLibrary(libraryDbUtils, readonlyLibraryDbUtils, queryMessage);
	}

	@Override
	public GetLibraryItemContainerResult getLibraryItemContainer(
			GetLibraryItemContainerMessage getLibraryItemContainerMessage) {
		return GetLibraryItemMethod.getLibraryItemMethod(libraryDbUtils, getLibraryItemContainerMessage);
	}

	@Override
	public GetDiffResult getDiff(GetDiffMessage getDiffMessage) {
		return GetDiffMethod.getDiff(libraryDbUtils, getDiffMessage);
	}

	@Override
	public GetCommentsResult getComments(GetCommentsMessage getCommentsMessage) {
		return GetCommentsMethod.getComments(libraryDbUtils, getCommentsMessage);
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
		return GetReleaseVersionMethod.getReleaseVersion(libraryDbUtils, getReleaseVersionMessage);
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
		return AddReviewerCommentMethod.addReviewerComment(libraryDbUtils, addReviewerCommentMessage);
	}

	@Override
	public AddOrUpdateLibraryItemContainerResult updateLibraryItemContainer(
			AddOrUpdateLibraryItemContainerMessage addOrUpdateLibraryItemContainerMessage) {
		return UpdateLibraryItemMethod.updateLibraryItem(libraryDbUtils, addOrUpdateLibraryItemContainerMessage);
	}

	@Override
	public SetReleaseVersionResult setReleaseVersion(
			SetReleaseVersionMessage setReleaseVersionMessage) {
		return SetReleaseVersionMethod.setReleaseVersion(libraryDbUtils, setReleaseVersionMessage);
	}

	@Override
	public AddOrUpdateLibraryItemContainerResult addLibraryItemContainer(
			AddOrUpdateLibraryItemContainerMessage addOrUpdateLibraryItemContainerMessage) {
		return AddLibraryItemMethod.addLibraryItem(libraryDbUtils, addOrUpdateLibraryItemContainerMessage);
	}

	public static void main(String[] args) throws ApolloDatabaseException {
		
		LibraryServiceImpl impl = new LibraryServiceImpl();
		
		GetLibraryItemURIsMessage message = new GetLibraryItemURIsMessage();
		
		Authentication auth = new Authentication();
		auth.setRequesterId("library_demo");
		auth.setRequesterPassword("password");
		
		message.setAuthentication(auth);
		
//		message.setItemType("Census");
		
		GetLibraryItemURIsResult result = impl.getLibraryItemURIs(message);
		System.out.println("done");
	}

	@Override
	public GetLibraryItemURIsResult getLibraryItemURIs(
			GetLibraryItemURIsMessage getLibraryItemURIsMessage) {
		return GetLibraryItemURIsMethod.getLibraryItemURIs(libraryDbUtils, getLibraryItemURIsMessage);
	}


	
}
