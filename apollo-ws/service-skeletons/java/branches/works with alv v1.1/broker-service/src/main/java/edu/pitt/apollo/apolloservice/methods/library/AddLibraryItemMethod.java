package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.types.v2_1_0.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_1_0.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_1_0.Authentication;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:51:35 PM
 * Class: AddLibraryItemMethod
 * IDE: NetBeans 6.9.1
 */
public class AddLibraryItemMethod extends LibraryMethod {

    public static AddLibraryItemResult addLibraryItem(Authentication authentication, ApolloIndexableItem apolloIndexableItem,
            String itemDescription, String itemSource, String itemType, List<String> itemIndexingLabels) {
        return getLibraryServicePort().addLibraryItem(authentication,
                apolloIndexableItem, itemDescription, itemSource, itemType,
                itemIndexingLabels);
    }
}
