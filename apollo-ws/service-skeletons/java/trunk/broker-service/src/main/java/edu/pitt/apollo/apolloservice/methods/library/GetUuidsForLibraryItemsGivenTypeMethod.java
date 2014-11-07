package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.types.v2_1_0.GetLibraryItemUuidsResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:51:00 PM
 * Class: GetUuidsForLibraryItemsGivenTypeMethod
 * IDE: NetBeans 6.9.1
 */
public class GetUuidsForLibraryItemsGivenTypeMethod extends LibraryMethod {

    public static GetLibraryItemUuidsResult getUuidsForLibraryItemsGivenType(String type) {
        return getLibraryServicePort().getUuidsForLibraryItemsGivenType(type);
    }
}
