package edu.pitt.apollo.apollotranslator.types.js;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 14, 2013
 * Time: 11:30:13 AM
 * Class: JsFunction
 * IDE: NetBeans 6.9.1
 */
public class JsFunction {

    private String functionName;
    private List<String> arguments;

    public JsFunction() {
        this.arguments = new ArrayList<String>();
    }
    
    /**
     * @return the functionName
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * @param functionName the functionName to set
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void addJsFunctionArgument(String argument) {
        this.arguments.add(argument);
    }
    
    public int getNumArguments() {
        
        if (arguments == null) {
            return 0;
        } else {
            return arguments.size();
        }
    }
    
    public List<String> getArgumentNames() {
        return arguments;
    }
    
    @Override
    public boolean equals(Object obj) {
        // compare only on function name
        
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JsFunction other = (JsFunction) obj;
        if ((this.functionName == null) ? (other.functionName != null) : !this.functionName.equals(other.functionName)) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.functionName != null ? this.functionName.hashCode() : 0);
        return hash;
    }
    
}
