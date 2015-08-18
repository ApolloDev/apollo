package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.library_service_types.v3_0_2.*;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by jdl50 on 8/4/15.
 */
public interface LibraryServiceInterface {

    public QueryResult query(QueryMessage queryMessage);

    public GetLibraryItemContainerResult getLibraryItemContainer(int urn, int version, Authentication authentication);

    public UpdateLibraryItemContainerResult updateLibraryItemContainer(int urn, LibraryItemContainer libraryItemContainer, String comment, Authentication authentication);

    public AddLibraryItemContainerResult addLibraryItemContainer(LibraryItemContainer libraryItemContainer, String comment, Authentication authentication);

    public GetReleaseVersionResult getLibraryItemReleaseVersion(int urn, Authentication authentication);

    public GetCommentsResult getCommentsForLibraryItem(int urn, int version, Authentication authentication);

    public GetVersionsResult getVersionNumbersForLibraryItem(int urn, Authentication authentication);

    public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(int urn, String group, Authentication authentication);

    public GetLibraryItemURNsResult getLibraryItemURNs(String itemType, Authentication authentication);

    public SetReleaseVersionResult setReleaseVersionForLibraryItem(int urn, int version, String comment, Authentication authentication);

    public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(int urn, String group, Authentication authentication);

    public AddReviewerCommentResult addReviewerCommentToLibraryItem(int urn, int version, String comment, Authentication authentication);

    public SetLibraryItemAsNotReleasedResult setLibraryItemAsNotReleased(int urn, Authentication authentication);

    public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime, Authentication authentication);

}