
package edu.pitt.apollo.service.syntheticpopulationservice.v3_0_0;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.pitt.apollo.service.syntheticpopulationservice.v3_0_0 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.pitt.apollo.service.syntheticpopulationservice.v3_0_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetRunStatus }
     * 
     */
    public GetRunStatus createGetRunStatus() {
        return new GetRunStatus();
    }

    /**
     * Create an instance of {@link RunSyntheticPopulationGeneration }
     * 
     */
    public RunSyntheticPopulationGeneration createRunSyntheticPopulationGeneration() {
        return new RunSyntheticPopulationGeneration();
    }

    /**
     * Create an instance of {@link KillRunResponse }
     * 
     */
    public KillRunResponse createKillRunResponse() {
        return new KillRunResponse();
    }

    /**
     * Create an instance of {@link KillRunRequest }
     * 
     */
    public KillRunRequest createKillRunRequest() {
        return new KillRunRequest();
    }

    /**
     * Create an instance of {@link GetRunStatusResponse }
     * 
     */
    public GetRunStatusResponse createGetRunStatusResponse() {
        return new GetRunStatusResponse();
    }

    /**
     * Create an instance of {@link RunSyntheticPopulationGenerationResponse }
     * 
     */
    public RunSyntheticPopulationGenerationResponse createRunSyntheticPopulationGenerationResponse() {
        return new RunSyntheticPopulationGenerationResponse();
    }

}
