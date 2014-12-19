package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.types.v2_1_0.GetLibraryItemUuidsResult;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:58:24 PM
 * Class: GetUuidsForLibraryItemsCreatedSinceDateTimeMethod
 * IDE: NetBeans 6.9.1
 */
public class GetUuidsForLibraryItemsCreatedSinceDateTimeMethod extends LibraryMethod {

    public static GetLibraryItemUuidsResult getUuidsForLibraryItemsCreatedSinceDateTime(XMLGregorianCalendar creationDateTime) {
        return getLibraryServicePort().getUuidsForLibraryItemsCreatedSinceDateTime(creationDateTime);
    }
}
