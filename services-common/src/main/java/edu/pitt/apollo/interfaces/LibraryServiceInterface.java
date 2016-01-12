package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0.*;
import edu.pitt.apollo.services_common.v4_0.Authentication;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by jdl50 on 8/4/15.
 */
public interface LibraryServiceInterface {

    public QueryResult query(String query, Authentication authentication) throws LibraryServiceException;

    public GetLibraryItemContainerResult getLibraryItem(int urn, Integer revision, Authentication authentication) throws LibraryServiceException;

    public UpdateLibraryItemContainerResult reviseLibraryItem(int urn, LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException;

    public AddLibraryItemContainerResult addLibraryItem(LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException;

    public GetCommentsResult getCommentsForLibraryItem(int urn, int revision, Authentication authentication) throws LibraryServiceException;

    public GetRevisionsResult getAllRevisionsOfLibraryItem(int urn, Authentication authentication) throws LibraryServiceException;

    public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException;

    public GetLibraryItemURNsResult getLibraryItemURNs(String itemType, Authentication authentication) throws LibraryServiceException;

    public SetReleaseVersionResult approveRevisionOfLibraryItem(int urn, int revision, String comment, Authentication authentication) throws LibraryServiceException;

    public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException;

    public AddReviewerCommentResult addReviewerCommentToLibraryItem(int urn, int revision, String comment, Authentication authentication) throws LibraryServiceException;
	
    public SetLibraryItemAsNotReleasedResult hideLibraryItem(int urn, Authentication authentication) throws LibraryServiceException;

    public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime, Authentication authentication) throws LibraryServiceException;

}