package edu.pitt.apollo.apollotranslator.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstNode;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.js.JsContainer;
import edu.pitt.apollo.apollotranslator.types.js.JsFunctionList;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2013
 * Time: 1:21:57 PM
 * Class: JsFileLoader
 * IDE: NetBeans 6.9.1
 */
public class JsFileLoader {

    public static JsContainer loadJavaScriptFunctionsFromFile(String javaScriptFilePath) throws FileNotFoundException, ApolloTranslatorException {

        // load the file as text into a single string
        File jsFile = new File(javaScriptFilePath);
        Scanner scanner;
        try {
            scanner = new Scanner(jsFile);
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File " + javaScriptFilePath + " could not be opened for reading: " + ex.getMessage());
        }

        StringBuilder stBuild = new StringBuilder();
        while (scanner.hasNextLine()) {
            stBuild.append(scanner.nextLine()).append("\r\n");
        }

        String javaScriptString = stBuild.toString();
        JsFunctionList jsFunctionList = getJsFunctionList(javaScriptString);
        // check jsFunctionCollection to see if multiple equal function definitions exist
        jsFunctionList.checkForDuplicateFunctions();
        JsContainer jsContainer = new JsContainer(javaScriptString, jsFunctionList);

        return jsContainer;
    }
    
    public static JsFunctionList getJsFunctionList(String javaScript) {
		AstNode node = new Parser().parse(javaScript, javaScript, 0);
		JsFunctionList list = new JsFunctionList();
		node.visit(list);

		return list;
	}
}
