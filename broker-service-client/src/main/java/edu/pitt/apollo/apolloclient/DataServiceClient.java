package edu.pitt.apollo.apolloclient;

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
import edu.pitt.apollo.services_common.v4_0_1.Authentication;

import javax.xml.namespace.QName;
import java.io.*;
import java.util.Map;
import java.util.Properties;

public class DataServiceClient {

	private static final String WSDL_LOC = "http://localhost:8080/broker-service-war-3.0.0-SNAPSHOT/services/apolloservice?wsdl";
	public static final QName SERVICE = new QName("http://service.apollo.pitt.edu/apolloservice/v4_0/", "ApolloService_v3.1.0");
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

//	public static void main(String[] args) throws MalformedURLException, IOException {
//
//		ApolloServiceV40 ls = new ApolloServiceV40(new URL(WSDL_LOC), SERVICE);
//		ApolloServiceEI port = ls.getApolloServiceEndpoint();
//
////		testGettingOutputFilesURLAsZip(port);
////		testGettingOutputFiles(port);
//		testGettingAllOutputFiles(port);
//	}

//	private static void testGettingOutputFilesURLAsZip(ApolloServiceEI port) throws IOException {
//
//		GetOutputFilesURLAsZipMessage message = new GetOutputFilesURLAsZipMessage();
//		message.setAuthentication(getAuthentication());
//
//		RunIdAndFiles runIdAndFiles = new RunIdAndFiles();
//		runIdAndFiles.setRunId(new BigInteger("505"));
////		runIdAndFiles.getFiles().add("exposed.txt");
//		runIdAndFiles.getFiles().add("config.txt");
//		runIdAndFiles.getFiles().add("verbose.html");
//		message.getRunIdsAndFiles().add(runIdAndFiles);
//
//		GetOutputFilesURLAsZipResult result = port.getOutputFilesURLAsZip(message);
//		System.out.println(result.getMethodCallStatus().getStatus());
//		System.out.println(result.getMethodCallStatus().getMessage());
//		System.out.println(result.getUrl());
//	}

//	private static void testGettingOutputFiles(ApolloServiceEI port) throws IOException {
//		GetOutputFilesURLsMessage message = new GetOutputFilesURLsMessage();
//		message.setAuthentication(getAuthentication());
//
//		RunIdAndFiles runIdAndFiles = new RunIdAndFiles();
//		runIdAndFiles.setRunId(new BigInteger("17"));
////		runIdAndFiles.getFiles().add("susceptible.txt");
//		runIdAndFiles.getFiles().add("batch_inputs_with_run_ids.txt");
//		message.getRunIdsAndFiles().add(runIdAndFiles);
//
//		GetOutputFilesURLsResult result = port.getOutputFilesURLs(message);
//		System.out.println(result.getMethodCallStatus().getStatus());
//		System.out.println(result.getMethodCallStatus().getMessage());
//		System.out.println(result.getUrlsForRunIdsAndFiles().get(0).getUrl());
//	}

//	private static void testGettingAllOutputFiles(ApolloServiceEI port) throws IOException {
//		GetAllOutputFilesURLAsZipMessage message = new GetAllOutputFilesURLAsZipMessage();
//		message.setAuthentication(getAuthentication());
//		message.setRunId(new BigInteger("70106"));
//		message.getFileNames().add("batch_inputs_with_run_ids.txt");
//		message.getFileNames().add("ods_required_output_for_model_validation.csv");
//		message.getFileNames().add("test11");
//		
//		
//		GetAllOutputFilesURLAsZipResult result = port.getAllOutputFilesURLAsZip(message);
//		System.out.println(result.getMethodCallStatus().getStatus());
//		System.out.println(result.getMethodCallStatus().getMessage());
//		System.out.println(result.getUrl());
//	}

	private static Authentication getAuthentication() throws FileNotFoundException, IOException {
		File props = new File(APOLLO_DIR + DATA_SERVICE_CONNECTION_PROPERTIES_FILE);
		InputStream fis = new FileInputStream(props);
		Properties properties = new Properties();
		properties.load(fis);
		fis.close();

		Authentication auth = new Authentication();
		return auth;
	}
}
