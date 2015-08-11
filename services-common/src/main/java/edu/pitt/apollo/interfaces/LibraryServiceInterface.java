package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.library_service_types.v3_0_2.*;

/**
 * Created by jdl50 on 8/4/15.
 */
public interface LibraryServiceInterface {

    public QueryResult query(QueryMessage queryMessage);

    public GetLibraryItemContainerResult getLibraryItemContainer(GetLibraryItemContainerMessage getLibraryItemContainerMessage);

    public UpdateLibraryItemContainerResult updateLibraryItemContainer(UpdateLibraryItemContainerMessage addOrUpdateLibraryItemContainerMessage);

    public AddLibraryItemContainerResult addLibraryItemContainer(AddLibraryItemContainerMessage addOrUpdateLibraryItemContainerMessage);

    public GetReleaseVersionResult getLibraryItemReleaseVersion(GetReleaseVersionMessage getLibraryItemReleaseVersionMessage);

    public GetCommentsResult getCommentsForLibraryItem(GetCommentsMessage getCommentsForLibraryItemMessage);

    public GetVersionsResult getVersionNumbersForLibraryItem(GetVersionsMessage getVersionNumbersForLibraryItemMessage);

    public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(ModifyGroupOwnershipMessage removeGroupAccessToLibraryItemMessage);

    public GetLibraryItemURNsResult getLibraryItemURNs(GetLibraryItemURNsMessage getLibraryItemURNsMessage);

    public SetReleaseVersionResult setReleaseVersionForLibraryItem(SetReleaseVersionMessage setReleaseVersionForLibraryItemMessage);

    public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(ModifyGroupOwnershipMessage grantGroupAccessToLibraryItemMessage);

    public AddReviewerCommentResult addReviewerCommentToLibraryItem(AddReviewerCommentMessage addReviewerCommentToLibraryItemMessage);

    public SetLibraryItemAsNotReleasedResult setLibraryItemAsNotReleased(SetLibraryItemAsNotReleasedMessage setLibraryItemAsNotReleasedMessage);

    public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage getChangeLogForLibraryItemsModifiedSinceDateTimeMessage);

}