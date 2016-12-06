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

import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.interfaces.LibraryServiceInterface;
import edu.pitt.apollo.library_service_types.v4_0_1.AddLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v4_0_1.AddReviewerCommentMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.AddReviewerCommentResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.GetChangeLogForLibraryItemsModifiedSinceDateTimeResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetCommentsMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.GetCommentsResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.GetLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetLibraryItemURNsMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.GetLibraryItemURNsResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.GetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetRevisionsResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetVersionsMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.ModifyGroupOwnershipMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.ModifyGroupOwnershipResult;
import edu.pitt.apollo.library_service_types.v4_0_1.QueryMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.QueryResult;
import edu.pitt.apollo.library_service_types.v4_0_1.SetLibraryItemAsNotReleasedMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.SetLibraryItemAsNotReleasedResult;
import edu.pitt.apollo.library_service_types.v4_0_1.SetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.SetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v4_0_1.UpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v4_0_1.UpdateLibraryItemContainerResult;

import javax.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pitt.apollo.service.libraryservice.v4_0_1.LibraryServiceEI;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import java.util.logging.Level;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v4_0/", portName = "LibraryServiceEndpoint", serviceName = "LibraryService_v4.0", endpointInterface = "edu.pitt.apollo.service.libraryservice.v4_0_1.LibraryServiceEI")
public class LibraryServiceSoapImpl implements LibraryServiceEI {

	static final Logger logger = LoggerFactory.getLogger(LibraryServiceSoapImpl.class);
	static final LibraryServiceInterface libraryService = new LibraryServiceImpl();

	@Override
	public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage getChangeLogForLibraryItemsModifiedSinceDateTimeMessage) {
		try {
			return libraryService.getChangeLogForLibraryItemsModifiedSinceDateTime(getChangeLogForLibraryItemsModifiedSinceDateTimeMessage.getDateTime(),
					getChangeLogForLibraryItemsModifiedSinceDateTimeMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			GetChangeLogForLibraryItemsModifiedSinceDateTimeResult result = new GetChangeLogForLibraryItemsModifiedSinceDateTimeResult();
			result.setStatus(createStatus("Error running getChangeLogForLibraryItemsModifiedSinceDateTime: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public AddReviewerCommentResult addReviewerCommentToLibraryItem(AddReviewerCommentMessage addReviewerCommentToLibraryItemMessage) {
		try {
			return libraryService.addReviewerCommentToLibraryItem(addReviewerCommentToLibraryItemMessage.getUrn(),
					addReviewerCommentToLibraryItemMessage.getVersion(), addReviewerCommentToLibraryItemMessage.getComment(),
					addReviewerCommentToLibraryItemMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			AddReviewerCommentResult result = new AddReviewerCommentResult();
			result.setStatus(createStatus("Error running addReviewerCommentToLibraryItem: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public SetLibraryItemAsNotReleasedResult setLibraryItemAsNotReleased(SetLibraryItemAsNotReleasedMessage setLibraryItemAsNotReleasedMessage) {
		try {
			return libraryService.hideLibraryItem(setLibraryItemAsNotReleasedMessage.getUrn(),
					setLibraryItemAsNotReleasedMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			SetLibraryItemAsNotReleasedResult result = new SetLibraryItemAsNotReleasedResult();
			result.setStatus(createStatus("Error running hideLibraryItem: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public GetCommentsResult getCommentsForLibraryItem(GetCommentsMessage getCommentsForLibraryItemMessage) {
		try {
			return libraryService.getCommentsForLibraryItem(getCommentsForLibraryItemMessage.getUrn(),
					getCommentsForLibraryItemMessage.getVersion(), getCommentsForLibraryItemMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			GetCommentsResult result = new GetCommentsResult();
			result.setStatus(createStatus("Error running getCommentsForLibraryItem: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(ModifyGroupOwnershipMessage grantGroupAccessToLibraryItemMessage) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public SetReleaseVersionResult setReleaseVersionForLibraryItem(SetReleaseVersionMessage setReleaseVersionForLibraryItemMessage) {
		try {
			return libraryService.approveRevisionOfLibraryItem(setReleaseVersionForLibraryItemMessage.getUrn(),
					setReleaseVersionForLibraryItemMessage.getVersion(), setReleaseVersionForLibraryItemMessage.getComment(),
					setReleaseVersionForLibraryItemMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			SetReleaseVersionResult result = new SetReleaseVersionResult();
			result.setStatus(createStatus("Error running approveRevisionOfLibraryItem: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public GetLibraryItemURNsResult getLibraryItemURNs(GetLibraryItemURNsMessage getLibraryItemURNsMessage) {
		try {
			return libraryService.getLibraryItemURNs(getLibraryItemURNsMessage.getItemType(),
					getLibraryItemURNsMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			GetLibraryItemURNsResult result = new GetLibraryItemURNsResult();
			result.setStatus(createStatus("Error running getLibraryItemURNs: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public UpdateLibraryItemContainerResult updateLibraryItemContainer(UpdateLibraryItemContainerMessage updateLibraryItemContainerMessage) {
		try {
			return libraryService.reviseLibraryItem(updateLibraryItemContainerMessage.getUrn(),
					updateLibraryItemContainerMessage.getLibraryItemContainer(), updateLibraryItemContainerMessage.getComment(),
					updateLibraryItemContainerMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			UpdateLibraryItemContainerResult result = new UpdateLibraryItemContainerResult();
			result.setStatus(createStatus("Error running reviseLibraryItem: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public GetRevisionsResult getVersionNumbersForLibraryItem(GetVersionsMessage getVersionNumbersForLibraryItemMessage) {
		try {
			return libraryService.getAllRevisionsOfLibraryItem(getVersionNumbersForLibraryItemMessage.getUrn(),
					getVersionNumbersForLibraryItemMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			GetRevisionsResult result = new GetRevisionsResult();
			result.setStatus(createStatus("Error running getAllRevisionsOfLibraryItem: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public GetReleaseVersionResult getLibraryItemReleaseVersion(GetReleaseVersionMessage getLibraryItemReleaseVersionMessage) {
		try {
			return libraryService.getApprovedRevisionOfLibraryItem(getLibraryItemReleaseVersionMessage.getUrn(),
					getLibraryItemReleaseVersionMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			GetReleaseVersionResult result = new GetReleaseVersionResult();
			result.setStatus(createStatus("Error running getAllRevisionsOfLibraryItem: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public AddLibraryItemContainerResult addLibraryItemContainer(AddLibraryItemContainerMessage addLibraryItemContainerMessage) {
		try {
			return libraryService.addLibraryItem(addLibraryItemContainerMessage.getLibraryItemContainer(),
					addLibraryItemContainerMessage.getComment(), addLibraryItemContainerMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			AddLibraryItemContainerResult result = new AddLibraryItemContainerResult();
			result.setStatus(createStatus("Error running addLibraryItem: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(ModifyGroupOwnershipMessage removeGroupAccessToLibraryItemMessage) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public GetLibraryItemContainerResult getLibraryItemContainer(GetLibraryItemContainerMessage getLibraryItemContainerMessage) {
		try {
			return libraryService.getLibraryItem(getLibraryItemContainerMessage.getUrn(),
					getLibraryItemContainerMessage.getVersion(), getLibraryItemContainerMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			GetLibraryItemContainerResult result = new GetLibraryItemContainerResult();
			result.setStatus(createStatus("Error running getLibraryItem: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	@Override
	public QueryResult query(QueryMessage queryMessage) {
		try {
			return libraryService.query(queryMessage.getQuery(), queryMessage.getAuthentication());
		} catch (LibraryServiceException ex) {
			QueryResult result = new QueryResult();
			result.setStatus(createStatus("Error running query: " + ex.getMessage(),
					MethodCallStatusEnum.FAILED));
			return result;
		}
	}

	private MethodCallStatus createStatus(String message, MethodCallStatusEnum enumVal) {
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage(message);
		status.setStatus(enumVal);
		return status;
	}
}
