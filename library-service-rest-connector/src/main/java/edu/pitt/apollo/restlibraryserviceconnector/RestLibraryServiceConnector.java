package edu.pitt.apollo.restlibraryserviceconnector;

import edu.pitt.apollo.connector.LibraryServiceConnector;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.interfaces.LibraryServiceInterface;
import edu.pitt.apollo.library_service_types.v3_0_2.*;
import edu.pitt.apollo.restserviceconnectorcommon.RestServiceUtils;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;

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
    public GetLibraryItemContainerResult getLibraryItemContainer(int urn, int version, Authentication authentication) throws LibraryServiceException {

        String uri = restServiceUri + "items/" + urn + "/revisions/" + version + "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, GetLibraryItemContainerResult.class);
        } catch (RestServiceException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }

    @Override
    public UpdateLibraryItemContainerResult updateLibraryItemContainer(int urn, LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException {

        String uri = restServiceUri + "items/" + urn + "?" + "comment=" + comment + "&"
                + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, libraryItemContainer, UpdateLibraryItemContainerResult.class);
        } catch (RestServiceException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }

    @Override
    public AddLibraryItemContainerResult addLibraryItemContainer(LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException {
        String uri = restServiceUri + "items?" + "comment=" + comment + "&"
                + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, libraryItemContainer, AddLibraryItemContainerResult.class);
        } catch (RestServiceException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }

    @Override
    public GetReleaseVersionResult getLibraryItemReleaseVersion(int urn, Authentication authentication) throws LibraryServiceException {
        String uri = restServiceUri + "items/" + urn + "/revisions/public?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, GetReleaseVersionResult.class);
        } catch (RestServiceException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }

    @Override
    public GetCommentsResult getCommentsForLibraryItem(int urn, int version, Authentication authentication) throws LibraryServiceException {
        String uri = restServiceUri + "items/" + urn + "/revisions/" + version + "/comments?"
                + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, GetCommentsResult.class);
        } catch (RestServiceException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }

    @Override
    public GetVersionsResult getVersionNumbersForLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
        String uri = restServiceUri + "items/" + urn + "/revisions?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, GetVersionsResult.class);
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
        String uri = restServiceUri + "items?itemType=" + itemType + "&" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, GetLibraryItemURNsResult.class);
        } catch (RestServiceException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }

    @Override
    public SetReleaseVersionResult setReleaseVersionForLibraryItem(int urn, int version, String comment, Authentication authentication) throws LibraryServiceException {
        String uri = restServiceUri + "/items/" + urn + "/revisions/" + version + "/public?comment=" + comment + "&" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, "", SetReleaseVersionResult.class);
        } catch (RestServiceException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }

    @Override
    public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException {
        throw new LibraryServiceException("Method not supported yet");
    }

    @Override
    public AddReviewerCommentResult addReviewerCommentToLibraryItem(int urn, int version, String comment, Authentication authentication) throws LibraryServiceException {
        String uri = restServiceUri + "/items/" + urn + "/revisions/" + version + "/comments?comment=" + comment + "&" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, "", AddReviewerCommentResult.class);
        } catch (RestServiceException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }

    @Override
    public SetLibraryItemAsNotReleasedResult hideLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
        String uri = restServiceUri + "/items/" + urn + "/revisions/" + "/public?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
    }

    @Override
    public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime, Authentication authentication) throws LibraryServiceException {
        return null;
    }
}
