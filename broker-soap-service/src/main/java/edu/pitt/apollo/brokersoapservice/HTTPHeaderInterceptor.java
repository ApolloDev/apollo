package edu.pitt.apollo.brokersoapservice;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import javax.servlet.http.HttpServletRequest;

public class HTTPHeaderInterceptor extends AbstractSoapInterceptor {
    public static String AUTHORIZATION_EXCHANGE_PROPERTY = "authorization";

    public HTTPHeaderInterceptor() {
        super(Phase.PRE_PROTOCOL);
        getAfter().add(SAAJInInterceptor.class.getName());
    }

    public void handleMessage(SoapMessage message) throws Fault {
        //if you want to read more http header messages, just use get method to obtain from  HttpServletRequest.
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        if (null != request) {
            //Read http header to get HeaderNames
            String authorization = request.getHeader("Authorization");
            message.getExchange().put(AUTHORIZATION_EXCHANGE_PROPERTY, authorization);
        }
    }
}