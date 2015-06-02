package edu.pitt.apollo.utilities;

/**
 *
 * @author nem41
 */
public abstract class Serializer implements SerializerInterface {

	public static final String APOLLO_NAMESPACE = "http://types.apollo.pitt.edu/v3_0_0";
	public static final String APOLLO_NAMESPACE_TNS_PREFIX = "tns";
	
	protected final String namespace;
	protected final String prefix;
	
	public Serializer(String namespace, String prefix) {
		this.namespace = namespace;
		this.prefix = prefix;
	}

}
