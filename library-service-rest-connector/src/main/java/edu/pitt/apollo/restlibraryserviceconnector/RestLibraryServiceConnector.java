package edu.pitt.apollo.restlibraryserviceconnector;

import edu.pitt.apollo.connector.LibraryServiceConnector;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.interfaces.LibraryServiceInterface;
import edu.pitt.apollo.library_service_types.v4_0_1.*;
import edu.pitt.apollo.restserviceconnectorcommon.RestServiceUtils;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;

/**
 * Created by jdl50 on 8/4/15.
 */
public class RestLibraryServiceConnector extends LibraryServiceConnector {

	private final String restServiceUri;
	private final RestServiceUtils restServiceUtils = new RestServiceUtils();

	public RestLibraryServiceConnector(String url) {
		super(url);
		restServiceUri = serviceUrl + "/ws/";
	}

	@Override
	public QueryResult query(String query, Authentication authentication) throws LibraryServiceException {
		return null;
	}

	@Override
	public GetLibraryItemContainerResult getLibraryItem(int urn, Integer revision, Authentication authentication) throws LibraryServiceException {

		String revisionString = (revision == null ? "" : "revision=" + Integer.toString(revision) + "&");
		String uri = restServiceUri + "items/" + urn;
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, GetLibraryItemContainerResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}

	@Override
	public UpdateLibraryItemContainerResult reviseLibraryItem(int urn, LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException {

		String uri = restServiceUri + "items/" + urn + "?" + "comment=" + comment;
		try {
			return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, authentication, libraryItemContainer, UpdateLibraryItemContainerResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}

	@Override
	public AddLibraryItemContainerResult addLibraryItem(LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException {
		String uri = restServiceUri + "items?" + "comment=" + comment;
		try {
			return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, authentication, libraryItemContainer, AddLibraryItemContainerResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}

	@Override
	public GetCommentsResult getCommentsForLibraryItem(int urn, int revision, Authentication authentication) throws LibraryServiceException {
		String uri = restServiceUri + "items/" + urn + "/revisions/" + revision + "/comments";
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, GetCommentsResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}

	@Override
	public GetRevisionsResult getAllRevisionsOfLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
		String uri = restServiceUri + "items/" + urn + "/revisions";
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, GetRevisionsResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}

	@Override
	public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException {
		throw new LibraryServiceException("Method not supported yet");
	}

	@Override
	public GetLibraryItemURNsResult getLibraryItemURNs(String itemType, Authentication authentication) throws LibraryServiceException {
		String uri = restServiceUri + "items?itemType=" + itemType;
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, GetLibraryItemURNsResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}

	@Override
	public SetReleaseVersionResult approveRevisionOfLibraryItem(int urn, int revision, String comment, Authentication authentication) throws LibraryServiceException {
		String uri = restServiceUri + "/items/" + urn + "/revisions/" + revision + "/approve?comment=" + comment;
		try {
			return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, authentication, "", SetReleaseVersionResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}

	@Override
	public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException {
		throw new LibraryServiceException("Method not supported yet");
	}

	@Override
	public AddReviewerCommentResult addReviewerCommentToLibraryItem(int urn, int revision, String comment, Authentication authentication) throws LibraryServiceException {
		String uri = restServiceUri + "/items/" + urn + "/revisions/" + revision + "/comments?comment=" + comment;
		try {
			return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, authentication, "", AddReviewerCommentResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}

	@Override
	public SetLibraryItemAsNotReleasedResult hideLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
		String uri = restServiceUri + "/items/" + urn + "/hide";
		try {
			return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, authentication, "", SetLibraryItemAsNotReleasedResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}

	@Override
	public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime, Authentication authentication) throws LibraryServiceException {
		String uri = restServiceUri + "items/changelog?dateTime=" + dateTime.toString();
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, GetChangeLogForLibraryItemsModifiedSinceDateTimeResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}

	@Override
	public GetReleaseVersionResult getApprovedRevisionOfLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
		String uri = restServiceUri + "items/" + urn + "/revisions/approved";
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, GetReleaseVersionResult.class);
		} catch (RestServiceException ex) {
			throw new LibraryServiceException(ex.getMessage());
		}
	}
}
