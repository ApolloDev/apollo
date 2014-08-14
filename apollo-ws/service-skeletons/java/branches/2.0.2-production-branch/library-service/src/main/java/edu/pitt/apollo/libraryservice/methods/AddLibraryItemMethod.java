package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.Db4oDatabaseAccessor;
import edu.pitt.apollo.types.v2_0_2.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0_2.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_0_2.Authentication;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:41:53 PM
 * Class: AddLibraryItemMethod
 */
public class AddLibraryItemMethod {

	public static AddLibraryItemResult addLibraryItem(Db4oDatabaseAccessor db4oAccessor,
			Authentication authentication,
			ApolloIndexableItem apolloIndexableItem,
			String itemDescription,
			String itemSource,
			String itemType,
			List<String> itemIndexingLabels) {

		AddLibraryItemResult result = new AddLibraryItemResult();
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("Okay.");
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		result.setMethodCallStatus(status);

		try {
			String uuid = db4oAccessor.addApolloLibraryItem(apolloIndexableItem, itemDescription,
					itemSource, itemType, itemIndexingLabels);
			result.setUuid(uuid);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage("DataTypeConfigurationException occurred adding library item: " + e.getMessage());
		}

		return result;
	}
}
