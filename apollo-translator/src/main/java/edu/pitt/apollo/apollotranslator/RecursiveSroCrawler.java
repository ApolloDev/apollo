package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 31, 2013
 * Time: 6:30:05 PM
 * Class: RecursiveSroCrawler
 * IDE: NetBeans 6.9.1
 */
public class RecursiveSroCrawler {

    private List<SetterReturnObject> sroList = new ArrayList<SetterReturnObject>();

    public List<SetterReturnObject> getSroList() {
        return sroList;
    }

    public void addToList(SetterReturnObject sro) {
        sroList.add(sro);
        List<SetterReturnObject> list = sro.getSubApolloParameters();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                addToList(list.get(i));
            }
        }
    }
}
