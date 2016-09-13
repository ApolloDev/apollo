package edu.pitt.apollo.apollotranslator.file;

import java.io.IOException;
import java.util.List;

import edu.pitt.apollo.apollotranslator.RecursiveSroCrawler;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.NativeFileCreatorInput;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 23, 2013
 * Time: 5:31:51 PM
 * Class: NativeFileCreator
 * IDE: NetBeans 6.9.1
 */
public abstract class NativeFileCreator {

    protected List<SetterReturnObject> setterReturnObjects;

    public NativeFileCreator(NativeFileCreatorInput nativeFileCreatorInput, List<SetterReturnObject> setterReturnObjects) {

        RecursiveSroCrawler crawler = new RecursiveSroCrawler();

        for (int i = 0; i < setterReturnObjects.size(); i++) {
            crawler.addToList(setterReturnObjects.get(i));
        }

        this.setterReturnObjects = crawler.getSroList();
    }

    public abstract Map<String, ByteArrayOutputStream> createNativeFiles() throws ApolloTranslatorException, IOException;
}
