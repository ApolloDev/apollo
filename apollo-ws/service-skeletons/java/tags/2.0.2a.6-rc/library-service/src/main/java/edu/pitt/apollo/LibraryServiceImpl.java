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

import edu.pitt.apollo.libraryservice.methods.AddLibraryItemMethod;
import edu.pitt.apollo.libraryservice.methods.GetLibraryItemMethod;
import edu.pitt.apollo.libraryservice.methods.GetUuidsForLibraryItemsCreatedSinceDateTimeMethod;
import edu.pitt.apollo.libraryservice.methods.GetUuidsForLibraryItemsGivenTypeMethod;
import edu.pitt.apollo.libraryservice.methods.RemoveLibraryItemMethod;
import edu.pitt.apollo.service.libraryservice.v2_0_2.LibraryServiceEI;
import edu.pitt.apollo.types.v2_0_2.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0_2.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_0_2.Authentication;
import edu.pitt.apollo.types.v2_0_2.GetLibraryItemResult;
import edu.pitt.apollo.types.v2_0_2.GetLibraryItemUuidsResult;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", portName = "LibraryServiceEndpoint", serviceName = "LibraryService_v2.0.2", endpointInterface = "edu.pitt.apollo.service.libraryservice.v2_0_2.LibraryServiceEI")
class LibraryServiceImpl implements LibraryServiceEI {

	static final Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);
	private static final String DB4O_FILENAME = "db4o_db_202";
	private static final Db4oDatabaseAccessor db4oAccessor;

	static {
		Map<String, String> env = System.getenv();
		String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			logger.info(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + apolloDir);
		} else {
			logger.error(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + "environment variable not found!");
		}

		db4oAccessor = new Db4oDatabaseAccessor(apolloDir + File.separator + DB4O_FILENAME);
	}

	@Override
	@WebResult(name = "getLibraryItemsResult", targetNamespace = "")
	@RequestWrapper(localName = "getUuidsForLibraryItemsGivenType", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", className = "edu.pitt.apollo.service.libraryservice.v2_0_2.GetUuidsForLibraryItemsGivenType")
	@WebMethod(action = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/getUuidsForLibraryItemsGivenType")
	@ResponseWrapper(localName = "getUuidsForLibraryItemsGivenTypeResponse", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", className = "edu.pitt.apollo.service.libraryservice.v2_0_2.GetUuidsForLibraryItemsGivenTypeResponse")
	public GetLibraryItemUuidsResult getUuidsForLibraryItemsGivenType(@WebParam(name = "type", targetNamespace = "") String type) {

		return GetUuidsForLibraryItemsGivenTypeMethod.getUuidsForLibraryItemsGivenType(db4oAccessor, type);
	}

	@Override
	@WebResult(name = "getLibraryItemsResult", targetNamespace = "")
	@RequestWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTime", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", className = "edu.pitt.apollo.service.libraryservice.v2_0_2.GetUuidsForLibraryItemsCreatedSinceDateTime")
	@WebMethod(action = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/getUuidsForLibraryItemsCreatedSinceDateTime")
	@ResponseWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTimeResponse", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", className = "edu.pitt.apollo.service.libraryservice.v2_0_2.GetUuidsForLibraryItemsCreatedSinceDateTimeResponse")
	public GetLibraryItemUuidsResult getUuidsForLibraryItemsCreatedSinceDateTime(
			@WebParam(name = "creationDateTime", targetNamespace = "") XMLGregorianCalendar creationDateTime) {

		return GetUuidsForLibraryItemsCreatedSinceDateTimeMethod.getUuidsForLibraryItemsCreatedSinceDateTime(db4oAccessor, creationDateTime);
	}

	@Override
	@WebResult(name = "getLibraryItemResult", targetNamespace = "")
	@RequestWrapper(localName = "getLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", className = "edu.pitt.apollo.service.libraryservice.v2_0_2.GetLibraryItem")
	@WebMethod(action = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/getLibraryItem")
	@ResponseWrapper(localName = "getLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", className = "edu.pitt.apollo.service.libraryservice.v2_0_2.GetLibraryItemResponse")
	public GetLibraryItemResult getLibraryItem(@WebParam(name = "uuid", targetNamespace = "") String uuid) {

		return GetLibraryItemMethod.getLibraryItemMethod(db4oAccessor, uuid);
	}

	@Override
	@WebResult(name = "addLibraryItemResult", targetNamespace = "")
	@RequestWrapper(localName = "addLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", className = "edu.pitt.apollo.service.libraryservice.v2_0_2.AddLibraryItem")
	@WebMethod(action = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/addLibraryItem")
	@ResponseWrapper(localName = "addLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", className = "edu.pitt.apollo.service.libraryservice.v2_0_2.AddLibraryItemResponse")
	public AddLibraryItemResult addLibraryItem(
			@WebParam(name = "authentication", targetNamespace = "") Authentication authentication,
			@WebParam(name = "apolloIndexableItem", targetNamespace = "") ApolloIndexableItem apolloIndexableItem,
			@WebParam(name = "itemDescription", targetNamespace = "") String itemDescription,
			@WebParam(name = "itemSource", targetNamespace = "") String itemSource,
			@WebParam(name = "itemType", targetNamespace = "") String itemType,
			@WebParam(name = "itemIndexingLabels", targetNamespace = "") List<String> itemIndexingLabels) {

		return AddLibraryItemMethod.addLibraryItem(db4oAccessor, authentication, apolloIndexableItem, itemDescription, itemSource, itemType, itemIndexingLabels);
	}

	@Override
	@WebResult(name = "methodCallStatus", targetNamespace = "")
	@RequestWrapper(localName = "removeLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", className = "edu.pitt.apollo.service.libraryservice.v2_0_2.RemoveLibraryItem")
	@WebMethod(action = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/removeLibraryItem")
	@ResponseWrapper(localName = "removeLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/libraryservice/v2_0_2/", className = "edu.pitt.apollo.service.libraryservice.v2_0_2.RemoveLibraryItemResponse")
	public MethodCallStatus removeLibraryItem(
			@WebParam(name = "authentication", targetNamespace = "") Authentication authentication,
			@WebParam(name = "uuid", targetNamespace = "") String uuid) {

		return RemoveLibraryItemMethod.removeLibraryItem(db4oAccessor, authentication, uuid);
	}
}
