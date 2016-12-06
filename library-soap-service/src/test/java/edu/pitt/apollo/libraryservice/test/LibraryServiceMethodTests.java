package edu.pitt.apollo.libraryservice.test;


import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.isg.objectserializer.JsonUtils;
import junit.framework.TestCase;

import javax.xml.bind.JAXBException;
import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:27:28 PM
 * Class: LibraryServiceMethodTests
 */
public class LibraryServiceMethodTests extends TestCase {


    public void test() {
        JohnsTest jt = new JohnsTest();
        String test = "hello!";
        JohnsTestChild jtc = new JohnsTestChild();

        JsonUtils jsonUtils = new JsonUtils(Arrays.asList(ApolloClassList.classList));
        try {
            System.out.println(jsonUtils.getJsonBytes(test));
            System.out.println(jsonUtils.getJsonBytes(jtc));
        } catch (JAXBException e) {
            fail(e.getClass().getName() + " " + e.getMessage());
        }

    }

}
