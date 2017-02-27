package edu.pitt.apollo.apollotranslator.types.js;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2013
 * Time: 1:42:04 PM
 * Class: JsContainer
 * IDE: NetBeans 6.9.1
 */
public class JsContainer {

    private String javaScript = null; // the entire JavaScript string for this container
    private JsFunctionList functionList = null; // the list of all functions in the JavaScript string

    public JsContainer(String javaScript, JsFunctionList functionList) {
        // once you create a JsContainer there should be no need to update the
        // JavaScript or function list for the object, just create a new JsContainer
        
        this.javaScript = javaScript;
        this.functionList = functionList;
    }

    /**
     * @return the javaScript
     */
    public String getJavaScript() {
        return javaScript;
    }

    /**
     * @return the functionList
     */
    public JsFunctionList getFunctionList() {
        return functionList;
    }
}
