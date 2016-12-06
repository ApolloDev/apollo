/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.TemplatedInfectiousDiseaseControlMeasureUrlForSoftware;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nem41
 */
public class TemplatedInfectiousDiseaseControlMeasureUrlForSoftwareSetter extends AbstractTypedSetter<TemplatedInfectiousDiseaseControlMeasureUrlForSoftware> {

	private static final String URL = "URL";
	
	public TemplatedInfectiousDiseaseControlMeasureUrlForSoftwareSetter() {

	}

	public TemplatedInfectiousDiseaseControlMeasureUrlForSoftwareSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setUrl(String url) throws ApolloSetterException {
		return setValue(URL, url, section);
	}

	@Override
	public List<SetterReturnObject> set(TemplatedInfectiousDiseaseControlMeasureUrlForSoftware t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<>();
		list.addAll(setUrl(t.getControlMeasureTemplateUrl()));
		
		return list;
	}

}
