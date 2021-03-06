package edu.pitt.apollo.libraryservicerestfrontend.controller;


import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.library_service_types.v4_0_2.*;
import edu.pitt.apollo.services_common.v4_0_2.*;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.isg.objectserializer.Deserializer;
import edu.pitt.isg.objectserializer.DeserializerFactory;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.SerializerFactory;
import edu.pitt.isg.objectserializer.exceptions.DeserializationException;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import junit.framework.TestCase;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

public class ItemControllerTest extends TestCase {

	String username = null, password = null;
	public static final int TEST_URN = 5;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		username = System.getenv("LIBRARY_USER");
		password = System.getenv("LIBRARY_PASSWORD");
	}

	@Test
	public void testUpdateItem() {
		try {
			Authentication authentication = new Authentication();
/*			authentication.setRequesterId(username);
			authentication.setRequesterPassword(password);*/
			UpdateLibraryItemContainerMessage ulicm = new UpdateLibraryItemContainerMessage();
			ulicm.setAuthentication(authentication);
			ulicm.setComment("Hello this is a comment");

			LibraryItemContainer lic = new LibraryItemContainer();
			CatalogEntry ce = new CatalogEntry();
			ce.setDisplayName("Item description here!");
			ce.setTextualIdentifier("Item description here!");

			ce.setJavaClassName(TextContainer.class.getName());

			TextContainer tc = new TextContainer();
			tc.setText("Hello this is the container text!");
			lic.setLibraryItem(tc);
			lic.setCatalogEntry(ce);

			ulicm.setUrn(5);
			ulicm.setComment("Test comment!");
			ulicm.setAuthentication(authentication);
			ulicm.setLibraryItemContainer(lic);

			Serializer serializer = SerializerFactory.getSerializer(edu.pitt.isg.objectserializer.SerializationFormat.XML, Arrays.asList(ApolloClassList.classList));

			String serializedString = serializer.serializeObject(ulicm);

			Request request = new Request();
			request.setRequestBody(serializedString);
			RequestMeta requestMeta = new RequestMeta();
			requestMeta.setIsBodySerialized(true);
			ObjectSerializationInformation objectSerializationInformation = new ObjectSerializationInformation();
			objectSerializationInformation.setClassName(UpdateLibraryItemContainerMessage.class.getSimpleName());
			objectSerializationInformation.setClassNameSpace(Serializer.LIBRARY_SERVICE_NAMESPACE);
			objectSerializationInformation.setFormat(SerializationFormat.XML);
			requestMeta.setRequestBodySerializationInformation(objectSerializationInformation);
			request.setRequestMeta(requestMeta);

			ItemsController ic = new ItemsController();
			try {
				String apolloResponse = ic.reviseLibraryItem("", 5, serializer.serializeObject(request), "");
				Deserializer deserializer = DeserializerFactory.getDeserializer(edu.pitt.isg.objectserializer.SerializationFormat.XML);

				Response response = (Response) deserializer.getObjectFromMessage(apolloResponse, Response.class);
				UpdateLibraryItemContainerResult updateLibraryItemContainerResult = (UpdateLibraryItemContainerResult) deserializer.getObjectFromMessage(response.getResponseBody().get(0), UpdateLibraryItemContainerResult.class);
				if (!updateLibraryItemContainerResult.getStatus().getStatus().equals(MethodCallStatusEnum.COMPLETED)) {
					fail(updateLibraryItemContainerResult.getStatus().getMessage());
				}
			} catch (UnsupportedSerializationFormatException | UnsupportedAuthorizationTypeException e) {
				e.printStackTrace();
			} catch (DeserializationException e) {
				e.printStackTrace();
			}

		} catch (UnsupportedSerializationFormatException e) {
			e.printStackTrace();
		} catch (SerializationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddItem() {
		try {
			Authentication authentication = new Authentication();
			/*authentication.setRequesterId(username);
			authentication.setRequesterPassword(password);*/
			AddLibraryItemContainerMessage alicm = new AddLibraryItemContainerMessage();
			alicm.setAuthentication(authentication);
			alicm.setComment("Hello this is a comment");

			LibraryItemContainer lic = new LibraryItemContainer();
			CatalogEntry ce = new CatalogEntry();
			ce.setDisplayName("Item description here!");
			ce.setTextualIdentifier("Item description here!");

			ce.setJavaClassName(TextContainer.class.getName());

			TextContainer tc = new TextContainer();
			tc.setText("Hello this is the container text!");
			lic.setLibraryItem(tc);
			lic.setCatalogEntry(ce);

			alicm.setLibraryItemContainer(lic);

			Serializer serializer = SerializerFactory.getSerializer(edu.pitt.isg.objectserializer.SerializationFormat.XML, Arrays.asList(ApolloClassList.classList));


			String serializedString = serializer.serializeObject(alicm);

			Request request = new Request();
			request.setRequestBody(serializedString);
			RequestMeta requestMeta = new RequestMeta();
			requestMeta.setIsBodySerialized(true);
			ObjectSerializationInformation objectSerializationInformation = new ObjectSerializationInformation();
			objectSerializationInformation.setClassName(AddLibraryItemContainerMessage.class.getSimpleName());
			objectSerializationInformation.setClassNameSpace(Serializer.LIBRARY_SERVICE_NAMESPACE);
			objectSerializationInformation.setFormat(SerializationFormat.XML);
			requestMeta.setRequestBodySerializationInformation(objectSerializationInformation);
			request.setRequestMeta(requestMeta);

			ItemsController ic = new ItemsController();
			try {
				String apolloResponse = ic.addLibraryItem("", serializer.serializeObject(request), "");
				Deserializer deserializer = DeserializerFactory.getDeserializer(edu.pitt.isg.objectserializer.SerializationFormat.XML);
				Response response = (Response) deserializer.getObjectFromMessage(apolloResponse, Response.class);
				AddLibraryItemContainerResult addLibraryItemContainerResult = (AddLibraryItemContainerResult) deserializer.getObjectFromMessage(response.getResponseBody().get(0), AddLibraryItemContainerResult.class);
				if (!addLibraryItemContainerResult.getStatus().getStatus().equals(MethodCallStatusEnum.COMPLETED)) {
					fail(addLibraryItemContainerResult.getStatus().getMessage());
				}
			} catch (UnsupportedSerializationFormatException | UnsupportedAuthorizationTypeException e) {
				e.printStackTrace();
			} catch (DeserializationException e) {
				e.printStackTrace();
			}

		} catch (UnsupportedSerializationFormatException e) {
			e.printStackTrace();
		} catch (SerializationException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testSetLibraryItemReleaseVersion() {
		ItemsController ic = new ItemsController();
		try {
			ic.setLibraryItemReleaseVersion(TEST_URN, 1, "Testing setting the release version!", "");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
//    @Test
//    public void testGetLibraryItemReleaseVersion() {
//        ItemsController ic = new ItemsController();
//        try {
//            String stringResponse = ic.getLibraryItemReleaseVersion(username, password, TEST_URN);
//            Deserializer deserializer = DeserializerFactory.getDeserializer(SerializationFormat.XML);
//            Response response = (Response) deserializer.getObjectFromMessage(stringResponse, Response.class);
//            GetReleaseVersionResult result = deserializer.getObjectFromMessage(response.getResponseBody().get(0), GetReleaseVersionResult.class);
//            if (!result.getStatus().getStatus().equals(MethodCallStatusEnum.COMPLETED)) {
//                fail(result.getStatus().getMessage());
//            }
//        } catch (UnsupportedSerializationFormatException e) {
//            fail(e.getMessage());
//        } catch (SerializationException e) {
//            fail(e.getMessage());
//        } catch (DeserializationException e) {
//            fail(e.getMessage());
//        }
//    }

	@Test
	public void testSetItemAsNotReleased() {
		ItemsController ic = new ItemsController();
		try {
			String result = ic.hideLibraryItem(TEST_URN, "");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetItem() {
		ItemsController ic = new ItemsController();
		String item = null;
		try {
			item = ic.getLibraryItem(TEST_URN, 1, "");

			Deserializer deserializer = DeserializerFactory.getDeserializer(edu.pitt.isg.objectserializer.SerializationFormat.XML);
			Response response = (Response) deserializer.getObjectFromMessage(item, Response.class);
			if (!response.getResponseMeta().getStatus().equals(new BigInteger("200"))) {
				fail(response.getResponseMeta().getStatusMessage());
			}
			GetLibraryItemContainerResult mylic = (GetLibraryItemContainerResult) deserializer.getObjectFromMessage(response.getResponseBody().get(0), GetLibraryItemContainerResult.class);
			if (!mylic.getStatus().getStatus().equals(MethodCallStatusEnum.COMPLETED)) {
				fail(mylic.getStatus().getMessage());
			} else {
				TextContainer mytc = (TextContainer) mylic.getLibraryItemContainer().getLibraryItem();
				assertEquals(mytc.getText(), "Hello this is the container text!");
			}
		} catch (UnsupportedSerializationFormatException | UnsupportedAuthorizationTypeException e) {
			fail(e.getMessage());
		} catch (SerializationException e) {
			fail(e.getMessage());
		} catch (DeserializationException e) {
			fail(e.getMessage());
		}

	}
}
