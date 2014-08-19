package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.Db4oDatabaseAccessor;
import edu.pitt.apollo.types.v2_0_2.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_0_2.CatalogEntryForApolloLibraryItem;
import edu.pitt.apollo.types.v2_0_2.CuratedLibraryItemContainer;
import edu.pitt.apollo.types.v2_0_2.GetLibraryItemResult;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:46:59 PM
 * Class: GetLibraryItemMethod
 */
public class GetLibraryItemMethod {

	public static GetLibraryItemResult getLibraryItemMethod(Db4oDatabaseAccessor db4oAccessor, String uuid) {
		GetLibraryItemResult result = new GetLibraryItemResult();
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("Okay.");
		status.setStatus(MethodCallStatusEnum.COMPLETED);

		CatalogEntryForApolloLibraryItem catalogEntry = db4oAccessor.getCatalogEntryForApolloLibraryItemFromMap(uuid);
		if (catalogEntry == null) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage("No CatalogEntryForApolloLibraryItem with hash \"" + uuid + "\" exists in the database");
		} else {
			CuratedLibraryItemContainer container = new CuratedLibraryItemContainer();
			String apolloIndexableItemHash = catalogEntry.getItemUuid();
			ApolloIndexableItem apolloIndexableItem = db4oAccessor.getApolloIndexableItemFromMap(apolloIndexableItemHash);
			if (apolloIndexableItem == null) {
				status.setStatus(MethodCallStatusEnum.FAILED);
				status.setMessage("No ApolloIndexableItem entry with hash \"" + apolloIndexableItemHash + "\" exists in the database");
			} else {
				container.setApolloIndexableItem(apolloIndexableItem);
				container.setCuratedLibraryItem(catalogEntry);
				result.setCuratedLibraryItemContainer(container);
			}
		}

		result.setMethodCallStatus(status);
		return result;
	}

}
