package edu.pitt.apollo.libraryservice.test;


import edu.pitt.apollo.types.v4_0.InfectiousDiseaseScenario;
import edu.pitt.apollo.utilities.JsonUtils;
import junit.framework.TestCase;

import javax.xml.bind.JAXBException;
import java.io.Serializable;

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

        JsonUtils jsonUtils = new JsonUtils();
        try {
            System.out.println(jsonUtils.getJsonBytes(test));
            System.out.println(jsonUtils.getJsonBytes(jtc));
        } catch (JAXBException e) {
            fail(e.getClass().getName() + " " + e.getMessage());
        }

    }

}
