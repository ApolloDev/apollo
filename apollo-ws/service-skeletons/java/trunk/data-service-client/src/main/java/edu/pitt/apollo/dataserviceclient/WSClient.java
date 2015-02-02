package edu.pitt.apollo.dataserviceclient;

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
import edu.pitt.apollo.GlobalConstants;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsResult;
import edu.pitt.apollo.data_service_types.v3_0_0.RunIdAndFiles;
import edu.pitt.apollo.service.dataservice.v3_0_0.DataServiceEI;
import edu.pitt.apollo.service.dataservice.v3_0_0.DataServiceV300;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import javax.xml.namespace.QName;

public class WSClient {

	private static final String WSDL_LOC = "http://localhost:8080/data-service-war-3.0.0-SNAPSHOT/services/dataservice?wsdl";
	public static final QName SERVICE = new QName("http://service.apollo.pitt.edu/dataservice/v3_0_0/", "DataService_v3.0.0");
	private static final String DATA_SERVICE_CONNECTION_PROPERTIES_FILE = "data_service_connection.properties";

	public static final String APOLLO_DIR;

	static {
		Map<String, String> env = System.getenv();
		String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			System.out.println(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + apolloDir);
		} else {
			System.out.println(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + "environment variable not found!");
		}

		APOLLO_DIR = apolloDir;
	}

	public static void main(String[] args) throws MalformedURLException, IOException {


//		DataServiceV300 ls = new DataServiceV300(new URL(WSDL_LOC), SERVICE);
//		DataServiceEI port = ls.getDataServiceEndpoint();
//
//		GetOutputFilesURLsMessage message = new GetOutputFilesURLsMessage();
//		message.setAuthentication(getAuthentication());
//		
//		RunIdAndFiles runIdAndFiles = new RunIdAndFiles();
//		runIdAndFiles.setRunId(new BigInteger("1"));
//		runIdAndFiles.getFiles().add("susceptible.txt");
//		message.getRunIdsAndFiles().add(runIdAndFiles);
//		
//		GetOutputFilesURLsResult result = port.getOutputFilesURLs(message);
//		System.out.println(result.getMethodCallStatus().getStatus());
//		System.out.println(result.getMethodCallStatus().getMessage());
//		System.out.println(result.getUrlsForRunIdsAndFiles().get(0).getUrl());
	}

	private static Authentication getAuthentication() throws FileNotFoundException, IOException {
		File props = new File(APOLLO_DIR + DATA_SERVICE_CONNECTION_PROPERTIES_FILE);
		InputStream fis = new FileInputStream(props);
		Properties properties = new Properties();
		properties.load(fis);
		fis.close();

		Authentication auth = new Authentication();
		auth.setRequesterId(properties.getProperty("user"));
		auth.setRequesterPassword(properties.getProperty("password"));
		return auth;
	}
}
