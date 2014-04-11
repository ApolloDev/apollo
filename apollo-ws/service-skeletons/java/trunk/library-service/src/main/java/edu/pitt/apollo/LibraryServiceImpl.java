/* Copyright 2012 University of Pittsburgh
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package edu.pitt.apollo;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.ConfigScope;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.Db4oUUID;

import edu.pitt.apollo.service.libraryservice.v2_0_1.LibraryServiceEI;
import edu.pitt.apollo.types.v2_0_1.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0_1.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_0_1.ApolloIndexableItemTypeEnum;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.CatalogEntryForApolloLibraryItem;
import edu.pitt.apollo.types.v2_0_1.CuratedLibraryItemContainer;
import edu.pitt.apollo.types.v2_0_1.GetLibraryItemResult;
import edu.pitt.apollo.types.v2_0_1.GetLibraryItemUuidsResult;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", portName = "LibraryServiceEndpoint", serviceName = "LibraryService_v2.0.1", endpointInterface = "edu.pitt.apollo.service.libraryservice.v2_0_1.LibraryServiceEI")
class LibraryServiceImpl implements LibraryServiceEI {

	private static final String APOLLO_WORKDIR_ENVIRONMENT_VARIABLE = "APOLLO_201_WORK_DIR";
	private static final String DB4O_FILENAME = "db4o_db_201";

	private static ObjectContainer db4o;
	private static String APOLLO_DIR = "";

	static {
		Map<String, String> env = System.getenv();
		APOLLO_DIR = env.get(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (APOLLO_DIR != null) {
			if (!APOLLO_DIR.endsWith(File.separator)) {
				APOLLO_DIR += File.separator;
			}
			System.out.println(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + APOLLO_DIR);
		} else {
			System.out.println(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + "environment variable not found!");
			APOLLO_DIR = "";
		}
		EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
		configuration.file().generateUUIDs(ConfigScope.GLOBALLY);
		db4o = Db4oEmbedded.openFile(configuration, APOLLO_DIR + "/" + DB4O_FILENAME);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		db4o.close();
	}

	@Override
	@WebResult(name = "getLibraryItemsResult", targetNamespace = "")
	@RequestWrapper(localName = "getUuidsForLibraryItemsGivenType", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", className = "edu.pitt.apollo.service.libraryservice.v2_0_1.GetUuidsForLibraryItemsGivenType")
	@WebMethod(action = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/getUuidsForLibraryItemsGivenType")
	@ResponseWrapper(localName = "getUuidsForLibraryItemsGivenTypeResponse", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", className = "edu.pitt.apollo.service.libraryservice.v2_0_1.GetUuidsForLibraryItemsGivenTypeResponse")
	public GetLibraryItemUuidsResult getUuidsForLibraryItemsGivenType(@WebParam(name = "type", targetNamespace = "") String type) {
		GetLibraryItemUuidsResult result = new GetLibraryItemUuidsResult();
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("Okay.");
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		result.setMethodCallStatus(status);

		List<String> resultList = new ArrayList<String>();
		//
		CatalogEntryForApolloLibraryItem cli = new CatalogEntryForApolloLibraryItem();
		final ObjectSet<CatalogEntryForApolloLibraryItem> allItems = db4o.queryByExample(cli);

		for (CatalogEntryForApolloLibraryItem item : allItems) {
			if (item.getItemType().equals(type)) {
				resultList.add(item.getItemUuid());
			}
		}
		result.getUuids().addAll(resultList);
		return result;
	}

	@Override
	@WebResult(name = "getLibraryItemsResult", targetNamespace = "")
	@RequestWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTime", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", className = "edu.pitt.apollo.service.libraryservice.v2_0_1.GetUuidsForLibraryItemsCreatedSinceDateTime")
	@WebMethod(action = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/getUuidsForLibraryItemsCreatedSinceDateTime")
	@ResponseWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTimeResponse", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", className = "edu.pitt.apollo.service.libraryservice.v2_0_1.GetUuidsForLibraryItemsCreatedSinceDateTimeResponse")
	public GetLibraryItemUuidsResult getUuidsForLibraryItemsCreatedSinceDateTime(
			@WebParam(name = "creationDateTime", targetNamespace = "") XMLGregorianCalendar creationDateTime) {
		GetLibraryItemUuidsResult result = new GetLibraryItemUuidsResult();
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("Okay.");
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		result.setMethodCallStatus(status);

		List<String> resultList = new ArrayList<String>();
		//
		CatalogEntryForApolloLibraryItem cli = new CatalogEntryForApolloLibraryItem();
		final ObjectSet<CatalogEntryForApolloLibraryItem> allItems = db4o.queryByExample(cli);

		for (CatalogEntryForApolloLibraryItem item : allItems) {
			int c = item.getItemCreationTime().compare(creationDateTime);
			if ((c == DatatypeConstants.EQUAL) || (c == DatatypeConstants.GREATER)) {
				resultList.add(item.getItemUuid());
			}
		}
		result.getUuids().addAll(resultList);
		return result;
	}

	@Override
	@WebResult(name = "getLibraryItemResult", targetNamespace = "")
	@RequestWrapper(localName = "getLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", className = "edu.pitt.apollo.service.libraryservice.v2_0_1.GetLibraryItem")
	@WebMethod(action = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/getLibraryItem")
	@ResponseWrapper(localName = "getLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", className = "edu.pitt.apollo.service.libraryservice.v2_0_1.GetLibraryItemResponse")
	public GetLibraryItemResult getLibraryItem(@WebParam(name = "uuid", targetNamespace = "") String uuid) {
		// TODO Auto-generated method stub
		GetLibraryItemResult result = new GetLibraryItemResult();
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("Okay.");
		status.setStatus(MethodCallStatusEnum.COMPLETED);

		long longPart = Long.valueOf(uuid.split(" ")[1]);
		String sig = uuid.split(" ")[0];
		byte[] signaturePart = new byte[sig.length()];
		for (int i = 0; i < sig.length(); i++) {
			signaturePart[i] = (byte) sig.charAt(i);
		}
		Db4oUUID db4oUuid = new Db4oUUID(longPart, signaturePart);
		Object o = db4o.ext().getByUUID(db4oUuid);

		CuratedLibraryItemContainer container = new CuratedLibraryItemContainer();
		container.setApolloIndexableItem((ApolloIndexableItem) o);
		CatalogEntryForApolloLibraryItem cli = new CatalogEntryForApolloLibraryItem();
		cli.setItemUuid(uuid);
		ObjectSet<Object> r = db4o.queryByExample(cli);
		CatalogEntryForApolloLibraryItem item = (CatalogEntryForApolloLibraryItem) r.get(0);
		db4o.activate(item, 100);
		db4o.activate(o, 100);

		container.setCuratedLibraryItem(item);
		result.setMethodCallStatus(status);
		result.setCuratedLibraryItemContainer(container);
		return result;
	}

	@Override
	@WebResult(name = "addLibraryItemResult", targetNamespace = "")
	@RequestWrapper(localName = "addLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", className = "edu.pitt.apollo.service.libraryservice.v2_0_1.AddLibraryItem")
	@WebMethod(action = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/addLibraryItem")
	@ResponseWrapper(localName = "addLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", className = "edu.pitt.apollo.service.libraryservice.v2_0_1.AddLibraryItemResponse")
	public AddLibraryItemResult addLibraryItem(
			@WebParam(name = "authentication", targetNamespace = "") Authentication authentication,
			@WebParam(name = "apolloIndexableItem", targetNamespace = "") ApolloIndexableItem apolloIndexableItem,
			@WebParam(name = "itemDescription", targetNamespace = "") String itemDescription,
			@WebParam(name = "itemSource", targetNamespace = "") String itemSource,
			@WebParam(name = "itemType", targetNamespace = "") String itemType,
			@WebParam(name = "itemIndexingLabels", targetNamespace = "") List<String> itemIndexingLabels) {
		AddLibraryItemResult result = new AddLibraryItemResult();
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("Okay.");
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		result.setMethodCallStatus(status);

		String apolloUuid = "";
		db4o.store(apolloIndexableItem);
		db4o.commit();
		Db4oUUID uuid = db4o.ext().getObjectInfo(apolloIndexableItem).getUUID();

		for (int i = 0; i < uuid.getSignaturePart().length; i++) {
			apolloUuid += (char) uuid.getSignaturePart()[i];
		}
		apolloUuid += " " + uuid.getLongPart();

		// Db4oUUId u = new Db4oUUID(longPart_, signaturePart_)
		System.out.println("uuid is " + apolloUuid);

		GregorianCalendar c = new GregorianCalendar();
		XMLGregorianCalendar date;
		try {
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			CatalogEntryForApolloLibraryItem cli = new CatalogEntryForApolloLibraryItem();
			cli.setItemCreationTime(date);
			cli.setItemDescription(itemDescription);
			cli.setItemSource(itemSource);
			cli.setItemType(ApolloIndexableItemTypeEnum.fromValue(itemType + "Type"));
			cli.setItemUuid(apolloUuid);
			cli.getItemIndexingLabels().addAll(itemIndexingLabels);
			db4o.store(cli);
			db4o.commit();
			result.setUuid(apolloUuid);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	@Override
	@WebResult(name = "methodCallStatus", targetNamespace = "")
	@RequestWrapper(localName = "removeLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", className = "edu.pitt.apollo.service.libraryservice.v2_0_1.RemoveLibraryItem")
	@WebMethod(action = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/removeLibraryItem")
	@ResponseWrapper(localName = "removeLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_1/", className = "edu.pitt.apollo.service.libraryservice.v2_0_1.RemoveLibraryItemResponse")
	public MethodCallStatus removeLibraryItem(
			@WebParam(name = "authentication", targetNamespace = "") Authentication authentication,
			@WebParam(name = "uuid", targetNamespace = "") String uuid) {
		// TODO Auto-generated method stub
		// GetLibraryItemResult result = new GetLibraryItemResult();
		// MethodCallStatus status = new MethodCallStatus();
		// // status.setMessage("Okay.");
		// // status.setStatus(MethodCallStatusEnum.COMPLETED);
		//
		// long longPart = Long.valueOf(uuid.split(" ")[1]);
		// String sig = uuid.split(" ")[0];
		// byte[] signaturePart = new byte[sig.length()];
		// for (int i = 0; i < sig.length(); i++) {
		// signaturePart[i] = (byte) sig.charAt(i);
		// }
		// Db4oUUID db4oUuid = new Db4oUUID(longPart, signaturePart);
		// Object o = db4o.ext().getByUUID(db4oUuid);
		//
		// CuratedLibraryItemContainer container = new
		// CuratedLibraryItemContainer();
		// container.setApolloIndexableItem((ApolloIndexableItem) o);
		// CatalogEntryForApolloLibraryItem cli = new
		// CatalogEntryForApolloLibraryItem();
		// cli.setItemUuid(uuid);
		// ObjectSet<Object> r = db4o.queryByExample(cli);
		// CatalogEntryForApolloLibraryItem item =
		// (CatalogEntryForApolloLibraryItem) r
		// .get(0);
		//
		// db4o.activate(item, 100);
		// db4o.activate(o, 100);
		// db4o.ext().purge(o);
		// db4o.ext().purge(item);
		// db4o.delete(o);
		// db4o.delete(item);
		// db4o.commit();
		//
		// status.setMessage("Deleted object.");
		// status.setStatus(MethodCallStatusEnum.COMPLETED);
		//
		// return status;
		return null;
	}
}