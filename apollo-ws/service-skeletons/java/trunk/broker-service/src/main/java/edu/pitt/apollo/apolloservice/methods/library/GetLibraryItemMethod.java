package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.types.v2_1_0.GetLibraryItemResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:53:44 PM
 * Class: GetLibraryItemMethod
 * IDE: NetBeans 6.9.1
 */
public class GetLibraryItemMethod extends LibraryMethod {

    public static GetLibraryItemResult getLibraryItem(String uuid) {
        return getLibraryServicePort().getLibraryItem(uuid);
    }
}
