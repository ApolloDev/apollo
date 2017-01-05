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

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.interfaces.LibraryServiceInterface;
import edu.pitt.apollo.library_service_types.v4_0_1.*;
import edu.pitt.apollo.libraryservice.methods.*;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.util.Map;

public class LibraryServiceImpl implements LibraryServiceInterface {

	public static final String APOLLO_DIR;
	static final Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);
	private static final LibraryDbUtils libraryDbUtils;
	private static final Map<String, Integer> roles;

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
			libraryDbUtils = new LibraryDbUtils();

			roles = libraryDbUtils.getRoleIds();

		} catch (ApolloDatabaseException ex) {
			throw new ExceptionInInitializerError("ApolloDatabaseException initializing LibraryDbUtils: " + ex.getMessage());
		}
	}

	@Override
	public GetLibraryItemContainerResult getLibraryItem(int urn, Integer version, Authentication authentication) throws LibraryServiceException {
		return new GetLibraryItemMethod(authentication, roles).getLibraryItemMethod(libraryDbUtils, urn, version);
	}

	@Override
	public UpdateLibraryItemContainerResult reviseLibraryItem(int urn, LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException {
		return new UpdateLibraryItemMethod(authentication, roles).updateLibraryItem(libraryDbUtils, urn, libraryItemContainer, comment);
	}

	@Override
	public AddLibraryItemContainerResult addLibraryItem(LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException {
		return new AddLibraryItemMethod(authentication, roles).addLibraryItem(libraryDbUtils, libraryItemContainer, comment);
	}

	@Override
	public GetCommentsResult getCommentsForLibraryItem(int urn, int version, Authentication authentication) throws LibraryServiceException {
		return new GetCommentsMethod(authentication, roles).getComments(libraryDbUtils, urn, version);
	}

	@Override
	public GetRevisionsResult getAllRevisionsOfLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
		return new GetVersionsMethod(authentication, roles).getVersions(libraryDbUtils, urn);
	}

	@Override
	public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public GetLibraryItemURNsResult getLibraryItemURNs(String itemType, Authentication authentication) throws LibraryServiceException {
		return new GetLibraryItemURNsMethod(authentication, roles).getLibraryItemURIs(libraryDbUtils, itemType);
	}

	@Override
	public SetReleaseVersionResult approveRevisionOfLibraryItem(int urn, int version, String comment, Authentication authentication) throws LibraryServiceException {
		return new SetReleaseVersionMethod(authentication, roles).setReleaseVersion(libraryDbUtils, urn, version, comment);
	}

	@Override
	public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public AddReviewerCommentResult addReviewerCommentToLibraryItem(int urn, int version, String comment, Authentication authentication) throws LibraryServiceException {
		return new AddReviewerCommentMethod(authentication, roles).addReviewerComment(libraryDbUtils, urn, version, comment);
	}

	@Override
	public SetLibraryItemAsNotReleasedResult hideLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
		return new SetLibraryItemAsNotReleasedMethod(authentication, roles).setLibraryItemAsNotReleased(libraryDbUtils, urn);
	}

	@Override
	public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime, Authentication authentication) throws LibraryServiceException {
		return new GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod(authentication, roles).getChangeLogForLibraryItemsModifiedSinceDateTime(libraryDbUtils, dateTime);
	}

	@Override
	public GetReleaseVersionResult getApprovedRevisionOfLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
		return new GetApprovedRevisionOfLibraryItemMethod(authentication, roles).getLibraryItemMethod(libraryDbUtils, urn);
	}

    @Override
    public GetCacheDataResult getCacheData(Authentication authentication) throws LibraryServiceException {
        return new GetCacheDataMethod(authentication, roles).getCacheData(libraryDbUtils);
    }

    @Override
    public GetLibraryItemContainersResult getLibraryItemContainers(String className, boolean includeUnreleasedItems, Authentication authentication) throws LibraryServiceException {
        return new GetLibraryItemContainersMethod(authentication, roles).getLibraryItemContainers(libraryDbUtils, className, includeUnreleasedItems);
    }

    @Override
    public GetCollectionsResult getCollections(String className, boolean includeUnreleasedItems, Authentication authentication) throws LibraryServiceException {
        return new GetCollectionsMethod(authentication, roles).getCollections(libraryDbUtils, className, includeUnreleasedItems);
    }

    @Override
    public GetMembersOfCollectionResult getMembersOfCollection(int urn, int revision, boolean includeUnreleasedItems, Authentication authentication) throws LibraryServiceException {
        return new GetMembersOfCollectionMethod(authentication, roles).getMembersOfCollectionResult(libraryDbUtils, urn, revision, includeUnreleasedItems);
    }
}
