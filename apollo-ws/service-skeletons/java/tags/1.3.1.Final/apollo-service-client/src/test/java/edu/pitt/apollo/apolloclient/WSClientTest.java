package edu.pitt.apollo.apolloclient;



public class WSClientTest {
	
//	private ServiceRegistrationRecord getDefaultServiceRegistrationRecord() {
//		Authentication auth = new Authentication();
//		auth.setRequesterId("fake_user");
//		auth.setRequesterPassword("fake_password");
//
//		SoftwareIdentification si = new SoftwareIdentification();
//		si.setSoftwareDeveloper("UPitt,PSC,CMU");
//		si.setSoftwareName("YNIC");
//		si.setSoftwareVersion("2.0.1");
//		si.setSoftwareType(ApolloSoftwareType.SIMULATOR);
//
//		ServiceRegistrationRecord srr = new ServiceRegistrationRecord();
//		srr.setAuthentication(auth);
//		srr.setSoftwareIdentification(si);
//		srr.setUrl("http://fake.com/fake?wsdl");
//		
//		return srr;
//	}
//	
//	@Test
//	public void SingleSoftwareRegistryTest() {
//		
//		ApolloService service = null;
//		try {
//			service = new ApolloService(new URL(WSClient.WSDL_LOC));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		ApolloServiceEI port = service.getApolloServiceEndpoint();
//
//		ServiceRegistrationRecord srr1 = getDefaultServiceRegistrationRecord();
//
//		assertEquals(0, port.getRegisteredServices().size());
//		ServiceRegistrationResult registrationResult = port.registerService(srr1);
//		assertEquals(true, registrationResult.isActionSuccessful());
//		List<ServiceRecord> l = port.getRegisteredServices();
//		assertEquals(1, l.size());
//		
//		assertEquals(l.get(0).getSoftwareIdentification().getSoftwareDeveloper(), "UPitt,PSC,CMU");
//		assertEquals(l.get(0).getSoftwareIdentification().getSoftwareType(), ApolloSoftwareType.SIMULATOR);
//		assertEquals(l.get(0).getUrl(), "http://fake.com/fake?wsdl");
//		
//		
//		port.unRegisterService(srr1);
//		l = port.getRegisteredServices();
//		assertEquals(0, l.size());
//	}
//	
//	@Test
//	public void TwoSoftwareRegistryTest() {
//		
//		ApolloService service = null;
//		try {
//			service = new ApolloService(new URL(WSClient.WSDL_LOC));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		ApolloServiceEI port = service.getApolloServiceEndpoint();
//
//		ServiceRegistrationRecord srr1 = getDefaultServiceRegistrationRecord();
//		ServiceRegistrationRecord srr2 = getDefaultServiceRegistrationRecord();
//		srr2.getSoftwareIdentification().setSoftwareType(ApolloSoftwareType.VISUALIZER);
//		srr2.setUrl("http://fake.com/viz?wsdl");
//
//		assertEquals(0, port.getRegisteredServices().size());
//		ServiceRegistrationResult registrationResult1 = port.registerService(srr1);
//		ServiceRegistrationResult registrationResult2 = port.registerService(srr2);
//		assertEquals(true, registrationResult1.isActionSuccessful());
//		assertEquals(true, registrationResult2.isActionSuccessful());
//		List<ServiceRecord> l = port.getRegisteredServices();
//		assertEquals(2, l.size());
//		
//		assertEquals(l.get(0).getSoftwareIdentification().getSoftwareDeveloper(), "UPitt,PSC,CMU");
//		assertEquals(l.get(0).getSoftwareIdentification().getSoftwareType(), ApolloSoftwareType.SIMULATOR);
//		assertEquals(l.get(0).getUrl(), "http://fake.com/fake?wsdl");
//		
//		assertEquals(l.get(1).getSoftwareIdentification().getSoftwareDeveloper(), "UPitt,PSC,CMU");
//		assertEquals(l.get(1).getSoftwareIdentification().getSoftwareType(), ApolloSoftwareType.VISUALIZER);
//		assertEquals(l.get(1).getUrl(), "http://fake.com/viz?wsdl");
//		
//		
//		port.unRegisterService(srr1);
//		l = port.getRegisteredServices();
//		assertEquals(1, l.size());
//		
//		port.unRegisterService(srr2);
//		l = port.getRegisteredServices();
//		assertEquals(0, l.size());
//	}
}
