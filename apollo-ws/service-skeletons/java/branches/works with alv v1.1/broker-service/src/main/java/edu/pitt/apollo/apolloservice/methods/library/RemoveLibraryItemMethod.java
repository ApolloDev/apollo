package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.types.v2_1_0.Authentication;
import edu.pitt.apollo.types.v2_1_0.MethodCallStatus;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:57:36 PM
 * Class: RemoveLibraryItemMethod
 * IDE: NetBeans 6.9.1
 */
public class RemoveLibraryItemMethod extends LibraryMethod {

    public static MethodCallStatus removeLibraryItem(Authentication authentication, String uuid) {
        return getLibraryServicePort().removeLibraryItem(authentication, uuid);
    }
}
