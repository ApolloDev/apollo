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

import edu.pitt.apollo.service.syntheticpopulationservice.v4_0_1.KillRunRequest;
import edu.pitt.apollo.service.syntheticpopulationservice.v4_0_1.KillRunResponse;
import edu.pitt.apollo.service.syntheticpopulationservice.v4_0_1.SyntheticPopulationServiceEI;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.synthetic_population_service_types.v4_0_1.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v4_0_1.SyntheticPopulationRunStatusMessage;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.*;
import java.util.Date;
import java.util.Properties;


@WebService(targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/",
portName="SyntheticPopulationServiceEndpoint",
serviceName="SyntheticPopulationService_v4.0",
endpointInterface="edu.pitt.apollo.service.syntheticpopulationservice.v4_0_1.SyntheticPopulationServiceEI")
class SyntheticPopulationServiceImpl implements SyntheticPopulationServiceEI {
	private void emailAdmin(String message) {
		String protocol = "smtp";
		String host = "smtp.gmail.com";
		String provider = "";
		int port = 587;
		String sender = "apollotestsuite@gmail.com";
		String password = "apollotestsuite";
		String username = "apollotestsuite";
		String recipient = "apollotestsuite@gmail.com";
		
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.transport.protocol", protocol);
			props.put("mail.smtp.class", provider);
			props.put("mail.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.user", username);
			props.put("mail.from", sender);
			
			PasswordAuthentication authentication = new PasswordAuthentication(username, password);
			
			Session session = Session.getInstance(props, null);
			
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom();
			InternetAddress[] toAddress = {new InternetAddress(recipient)};
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			msg.setSubject("Syneco error has occurred");
			msg.setSentDate(new Date());
			msg.setText("The following error has occurred:\n" + message);
System.out.println("Attempting to send mail...");
			Transport transport = session.getTransport("smtps");
			transport.connect(host, username, password);
			transport.sendMessage(msg, msg.getAllRecipients());
System.out.println("Mail sent.");
		}
		catch (MessagingException mex) {
			System.out.println("send failed, exception: " + mex);
			mex.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("send failed, exception: " + e);
			e.printStackTrace();
		}
		
		return;
	}
	
	private void printResultSet(ResultSet resultset) throws SQLException {
		if(!resultset.isClosed()) {
			while(resultset.next()) {
				int columnCount = resultset.getMetaData().getColumnCount();
				System.out.print("|");
				
				for(int c = 0; c < columnCount; c++) {
					System.out.print(resultset.getString(c));
					System.out.print("|");
				}
				
				System.out.println();
			}
		}
		
		return;
	}
	
	@Override
	@WebResult(name = "serviceResult", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/")
	@RequestWrapper(localName = "runSyntheticPopulationGeneration", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/", className = "edu.pitt.apollo.service.syntheticpopulationservice.v4_0_1.RunSyntheticPopulationGeneration")
	@WebMethod(action = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/runSyntheticPopulationGeneration")
	@ResponseWrapper(localName = "runSyntheticPopulationGenerationResponse", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/", className = "edu.pitt.apollo.service.syntheticpopulationservice.v4_0_1.RunSyntheticPopulationGenerationResponse")
	public BigInteger runSyntheticPopulationGeneration(
		@WebParam(name = "runSyntheticPopulationGenerationMessage", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/") RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage) {
		Properties properties = new Properties();
		properties.put("user", "synthia");
		properties.put("password", "synthia");
		String url = "jdbc:postgresql://localhost:5432/midas";
		String email = "'DEPRECATED'";
		String status = "'QUEUED'";
		Long requestID = null;
		
		Connection connection = null;
		PreparedStatement addRequest = null;
		PreparedStatement addVariables = null;
		PreparedStatement addBoundaries = null;
		Statement statement = null;
		ResultSet resultset = null;
		
		try{
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			resultset = statement.executeQuery("SELECT nextval('synthia.request_id_seq');");
			resultset.next();
			requestID = resultset.getLong(1);
			connection.setAutoCommit(false);
			
			String query1 = "INSERT INTO synthia.requests(request_id, email_address, request_status, request_date) VALUES (" + requestID + ", " + email +", " + status + ", now());";
			addRequest = connection.prepareStatement(query1);
			addRequest.execute();
			
			String query2 = "INSERT INTO synthia.request_variables(request_id, variable_id) ";
			int variablesCount = runSyntheticPopulationGenerationMessage.getVariableId().size();
			
			for(int c = 0; c < variablesCount; c++) {
				String values = "VALUES(" + requestID + ", ?);";
				addVariables = connection.prepareStatement(query2 + values);
				addVariables.setString(1, runSyntheticPopulationGenerationMessage.getVariableId().get(c));
				addVariables.execute();
			}
			
			String query3 = "INSERT INTO synthia.request_boundaries(request_id, boundary_fips) ";
			int boundariesCount = runSyntheticPopulationGenerationMessage.getBoundaryId().size();
			for(int c = 0; c < boundariesCount; c++) {
				String values = "VALUES(" + requestID + ", ?);";
				addBoundaries = connection.prepareStatement(query3 + values);
				addBoundaries.setString(1, runSyntheticPopulationGenerationMessage.getBoundaryId().get(c));
				addBoundaries.execute();
			}
			
			connection.commit();
			System.out.println("\nSuccessfully executed query");
			
			Properties configuration = new Properties();
			InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("syntheticpopulation-service.properties");
			configuration.load(inStream);
			inStream.close();
			Runtime.getRuntime().exec(configuration.getProperty("process") + " " + requestID);
		}
		catch(ClassNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
			//e-mail admin error
			emailAdmin(e.toString()+ ": " + e.getMessage());
		}
		catch(SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			//e-mail admin error
			emailAdmin(e.toString()+ ": " + e.getMessage());
		}
		catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
			//e-mail admin error
			emailAdmin(e.toString()+ ": " + e.getMessage());
		}
		finally {
			try {
				if(resultset != null) {
					resultset.close();
				}
				
				if(statement != null) {
					statement.close();
				}
				
				if(addRequest != null) {
					addRequest.close();
				}
				
				if(addVariables != null) {
					addVariables.close();
				}
				
				if(addBoundaries != null) {
					addBoundaries.close();
				}
				
				if(connection != null) {
					connection.setAutoCommit(false);
					connection.close();
				}
			}
			catch(SQLException e) {
				System.out.println(e);
				e.printStackTrace();
				//e-mail admin error
				emailAdmin(e.toString()+ ": " + e.getMessage());
			}
		}
		
		if(requestID == null) {
			return null;
		}
		
		return BigInteger.valueOf(requestID);
	}
	
	@Override
	@WebResult(name = "runStatus", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/", className = "edu.pitt.apollo.service.syntheticpopulationservice.v4_0_1.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/", className = "edu.pitt.apollo.service.syntheticpopulationservice.v4_0_1.GetRunStatusResponse")
	public SyntheticPopulationRunStatusMessage getRunStatus(
			@WebParam(name = "runId", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v4_0/") BigInteger runId) {
		SyntheticPopulationRunStatusMessage runStatus = new SyntheticPopulationRunStatusMessage();
		Properties properties = new Properties();
		properties.put("user", "synthia");
		properties.put("password", "synthia");
		String url = "jdbc:postgresql://localhost:5432/midas";
		
		Connection connection = null;
		PreparedStatement getRequest = null;
		ResultSet resultset = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, properties);
			
			String query1 = "SELECT request_status, output_url, error from synthia.requests WHERE request_id = ?";
			getRequest = connection.prepareStatement(query1);
			getRequest.setLong(1, runId.longValue());
			getRequest.execute();
			resultset = getRequest.getResultSet();
			resultset.next();
			runStatus.setRunStatus(MethodCallStatusEnum.fromValue(resultset.getString(1).toLowerCase()));
			runStatus.setCompletedRunUrl(resultset.getString(2));
			runStatus.setErrorMessage(resultset.getString(3));
		}
		catch(ClassNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
			//e-mail admin error
			emailAdmin(e.toString()+ ": " + e.getMessage());
		}
		catch(SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			//e-mail admin error
			emailAdmin(e.toString()+ ": " + e.getMessage());
		}
		finally {
			try {
				if(resultset != null) {
					resultset.close();
				}
				
				if(getRequest != null) {
					getRequest.close();
				}
				
				if(connection != null) {
					connection.setAutoCommit(false);
					connection.close();
				}
			}
			catch(SQLException e) {
				System.out.println(e);
				e.printStackTrace();
				//e-mail admin error
				emailAdmin(e.toString()+ ": " + e.getMessage());
			}
		}
		
		System.out.println("Run Status: " + runStatus);
		
		return runStatus;
	}
	
	@Override
	public KillRunResponse killRun(KillRunRequest parameters) {
		KillRunResponse killRunResponse = new KillRunResponse();
		Properties properties = new Properties();
		properties.put("user", "synthia");
		properties.put("password", "synthia");
		String url = "jdbc:postgresql://localhost:5432/midas";
		//String status = "RUN_TERMINATED";
		long runID = parameters.getRunId().longValue();
		
		Connection connection = null;
		PreparedStatement killRequest = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, properties);
			
			String query1 = "UPDATE synthia.requests SET terminate = 'true' WHERE request_id = ?";
			killRequest = connection.prepareStatement(query1);
			killRequest.setLong(1, runID);
			killRequest.execute();
			
			SyntheticPopulationRunStatusMessage response = getRunStatus(BigInteger.valueOf(runID));
			killRunResponse.setRunStatus(response);
		}
		catch(ClassNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
			
			SyntheticPopulationRunStatusMessage response = new SyntheticPopulationRunStatusMessage();
			response.setRunStatus(MethodCallStatusEnum.FAILED);
			response.setErrorMessage(e.toString());
			killRunResponse.setRunStatus(response);
		}
		catch(SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			
			SyntheticPopulationRunStatusMessage response = new SyntheticPopulationRunStatusMessage();
			response.setRunStatus(MethodCallStatusEnum.FAILED);
			response.setErrorMessage(e.toString());
			killRunResponse.setRunStatus(response);
		}
		finally {
			try {
				if(killRequest != null) {
					killRequest.close();
				}
				
				if(connection != null) {
					connection.setAutoCommit(false);
					connection.close();
				}
			}
			catch(SQLException e) {
				System.out.println(e);
				e.printStackTrace();
				
				SyntheticPopulationRunStatusMessage response = new SyntheticPopulationRunStatusMessage();
				response.setRunStatus(MethodCallStatusEnum.FAILED);
				response.setErrorMessage(e.toString());
				killRunResponse.setRunStatus(response);
			}
		}
		
		System.out.println("Kill Run Response: " + killRunResponse);
		
		return killRunResponse;
	}
}