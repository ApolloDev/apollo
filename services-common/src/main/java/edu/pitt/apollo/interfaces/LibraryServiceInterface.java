package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v3_0_2.*;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by jdl50 on 8/4/15.
 */
public interface LibraryServiceInterface {

    public QueryResult query(QueryMessage queryMessage) throws LibraryServiceException;

    public GetLibraryItemContainerResult getLibraryItemContainer(int urn, int version, Authentication authentication) throws LibraryServiceException;

    public UpdateLibraryItemContainerResult updateLibraryItemContainer(int urn, LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException;

    public AddLibraryItemContainerResult addLibraryItemContainer(LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException;

    public GetReleaseVersionResult getLibraryItemReleaseVersion(int urn, Authentication authentication) throws LibraryServiceException;

    public GetCommentsResult getCommentsForLibraryItem(int urn, int version, Authentication authentication) throws LibraryServiceException;

    public GetVersionsResult getVersionNumbersForLibraryItem(int urn, Authentication authentication) throws LibraryServiceException;

    public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException;

    public GetLibraryItemURNsResult getLibraryItemURNs(String itemType, Authentication authentication) throws LibraryServiceException;

    public SetReleaseVersionResult setReleaseVersionForLibraryItem(int urn, int version, String comment, Authentication authentication) throws LibraryServiceException;

    public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException;

    public AddReviewerCommentResult addReviewerCommentToLibraryItem(int urn, int version, String comment, Authentication authentication) throws LibraryServiceException;

    public SetLibraryItemAsNotReleasedResult setLibraryItemAsNotReleased(int urn, Authentication authentication) throws LibraryServiceException;

    public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime, Authentication authentication) throws LibraryServiceException;

}