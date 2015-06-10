/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservice.serviceaccessors;

/**
 *
 * @author nem41
 */
public abstract class ServiceAccessor {
	
	protected final String url;
	
	public ServiceAccessor(String url) {
		this.url = url;
	}
	
}
