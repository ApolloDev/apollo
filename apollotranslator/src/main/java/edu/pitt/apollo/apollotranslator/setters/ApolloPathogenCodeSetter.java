package edu.pitt.apollo.apollotranslator.setters;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.ApolloPathogenCode;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 3, 2013
 * Time: 12:06:48 PM
 * Class: AgeRangeSetter
 * IDE: NetBeans 6.9.1
 */
public class ApolloPathogenCodeSetter extends AbstractTypedSetter<ApolloPathogenCode> {

    private static final String NCBI_TAXON_ID_FIELD = "ncbiTaxonId";
    private static final String GISRN_CLADE_NAME_FIELD = "cladeName";

    public ApolloPathogenCodeSetter() {
    }

    public ApolloPathogenCodeSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setNcbiTaxonId(String id) throws ApolloSetterException {
        return setValue(NCBI_TAXON_ID_FIELD, id, section);
    }

    private List<SetterReturnObject> setCladeName(String name) throws ApolloSetterException {
        if (name != null) {
            return setValue(GISRN_CLADE_NAME_FIELD, name, section);
        } else {
            return new ArrayList<SetterReturnObject>();
        }
    }

    public List<SetterReturnObject> set(ApolloPathogenCode id) throws ApolloSetterException {

        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setNcbiTaxonId(id.getNcbiTaxonId()));
        if (id.getCladeName() != null) {
            list.addAll(setCladeName(id.getCladeName()));
        }

        return list;
    }
//    public List<SetterReturnObject> set(List<AgeRange> ageRanges) throws ApolloSetterException {
//
//        if (ageRanges != null && ageRanges.size() > 0) {
//            for (AgeRange range : ageRanges) {
//                results.addAll(set(range));
//            }
//        }
//        
//        return results;
//    }
}
