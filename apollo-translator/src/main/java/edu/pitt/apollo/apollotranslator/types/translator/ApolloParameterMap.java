package edu.pitt.apollo.apollotranslator.types.translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 18, 2013
 * Time: 5:28:45 PM
 * Class: ApolloParameterMap
 * IDE: NetBeans 6.9.1
 */
public class ApolloParameterMap extends HashMap<String, List<TranslationInstruction>> {
    
    
    public ApolloParameterMap() {
        super();
    }
    
    public void put(String string, TranslationInstruction instruction) {
        
        if (this.containsKey(string)) {
            this.get(string).add(instruction);
        } else {
            List<TranslationInstruction> instructions = new ArrayList<TranslationInstruction>();
            instructions.add(instruction);
            this.put(string, instructions);
        }
    }
}
