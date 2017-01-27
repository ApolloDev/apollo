package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0_1.*;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by jdl50 on 8/4/15.
 */
public interface LibraryServiceInterface {

    GetLibraryItemContainerResult getLibraryItem(int urn, Integer revision, Authentication authentication) throws LibraryServiceException;

    UpdateLibraryItemContainerResult reviseLibraryItem(int urn, LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException;

    AddLibraryItemContainerResult addLibraryItem(LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException;

    GetCommentsResult getCommentsForLibraryItem(int urn, int revision, Authentication authentication) throws LibraryServiceException;

    GetRevisionsResult getAllRevisionsOfLibraryItem(int urn, Authentication authentication) throws LibraryServiceException;

    ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException;

    GetLibraryItemURNsResult getLibraryItemURNs(String itemType, Authentication authentication) throws LibraryServiceException;

    SetReleaseVersionResult approveRevisionOfLibraryItem(int urn, int revision, String comment, Authentication authentication) throws LibraryServiceException;

    ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException;

    AddReviewerCommentResult addReviewerCommentToLibraryItem(int urn, int revision, String comment, Authentication authentication) throws LibraryServiceException;

    SetLibraryItemAsNotReleasedResult hideLibraryItem(int urn, Authentication authentication) throws LibraryServiceException;

    GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime, Authentication authentication) throws LibraryServiceException;

    GetReleaseVersionResult getApprovedRevisionOfLibraryItem(int urn, Authentication authentication) throws LibraryServiceException;

    GetCacheDataResult getCacheData(Authentication authentication) throws LibraryServiceException;

    GetLibraryItemContainersResult getLibraryItemContainers(String className, boolean includeUnreleasedItems, Authentication authentication) throws LibraryServiceException;

    GetLibraryItemDisplayNamesAndURNsResult getLibraryItemDisplayNamesAndURNs(String className, boolean includeUnreleasedItems, Authentication authentication) throws LibraryServiceException;

    GetCollectionsResult getCollections(String className, boolean includeUnreleasedItems, Authentication authentication) throws LibraryServiceException;

    GetMembersOfCollectionResult getMembersOfCollection(int urn, int revision, boolean includeUnreleasedItems, Authentication authentication) throws LibraryServiceException;
}