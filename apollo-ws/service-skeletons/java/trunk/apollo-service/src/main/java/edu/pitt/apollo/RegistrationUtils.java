package edu.pitt.apollo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import edu.pitt.apollo.types._07._03._2013.Authentication;
import edu.pitt.apollo.types._07._03._2013.ServiceRecord;
import edu.pitt.apollo.types._07._03._2013.ServiceRegistrationRecord;
import edu.pitt.apollo.types._07._03._2013.SoftwareIdentification;

//import edu.pitt.apollo.types.Authentication;
//import edu.pitt.apollo.types.ServiceRecord;
//import edu.pitt.apollo.types.ServiceRegistrationRecord;
//import edu.pitt.apollo.types.SoftwareIdentification;

@SuppressWarnings("unchecked")
public class RegistrationUtils {
	
	public synchronized static List<ServiceRegistrationRecord> getServiceRegistrationRecords()
			throws IOException {
		XStream xstream = new XStream();
		List<ServiceRegistrationRecord> records = null;
		File f = new File(ApolloServiceImpl.getRegistryFilename());
		if (f.exists()) {

			BufferedReader br = new BufferedReader(new FileReader(
					ApolloServiceImpl.getRegistryFilename()));
			StringBuffer buff = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				buff.append(line);
			}
			records = (List<ServiceRegistrationRecord>) xstream.fromXML(buff
					.toString());
			br.close();
		} else {
			records = new ArrayList<ServiceRegistrationRecord>();
		}
		return records;

	}

	public static ServiceRecord getServiceRecordForSoftwareId(
			SoftwareIdentification sid) throws IOException {
		List<ServiceRegistrationRecord> srrs = getServiceRegistrationRecords();
		for (ServiceRegistrationRecord srr : srrs) {
			if (softwareIdentificationEqual(srr.getSoftwareIdentification(),
					sid)) {
				ServiceRecord sr = new ServiceRecord();
				sr.setSoftwareIdentification(srr.getSoftwareIdentification());
				sr.setUrl(srr.getUrl());
				return sr;
			}
		}
		return null;
	}

	public static URL getUrlForSoftwareIdentification(
			SoftwareIdentification vi) throws IOException {
		List<ServiceRegistrationRecord> records = getServiceRegistrationRecords();
		for (ServiceRegistrationRecord record : records) {
			if (record.getSoftwareIdentification() != null) {
				SoftwareIdentification sid = record.getSoftwareIdentification();
				if (sid.getSoftwareDeveloper()
						.equals(vi.getSoftwareDeveloper())
						&& sid.getSoftwareName().equals(vi.getSoftwareName())
						&& sid.getSoftwareType().equals(sid.getSoftwareType())
						&& sid.getSoftwareVersion().equals(
								vi.getSoftwareVersion())) {
					return new URL(record.getUrl());
				}
			}
		}
		return null;
	}

	public synchronized static List<ServiceRecord> getRegisteredSoftware()
			throws IOException {
		List<ServiceRegistrationRecord> serviceRegistrationRecords = getServiceRegistrationRecords();
		List<ServiceRecord> serviceRecords = new ArrayList<ServiceRecord>(
				serviceRegistrationRecords.size());

		for (ServiceRegistrationRecord serviceRegistrationRecord : serviceRegistrationRecords) {
			ServiceRecord sr = new ServiceRecord();
			sr.setSoftwareIdentification(serviceRegistrationRecord
					.getSoftwareIdentification());
			sr.setUrl(serviceRegistrationRecord.getUrl());
			serviceRecords.add(sr);
		}

		return serviceRecords;

	}

	public synchronized static void addServiceRegistrationRecord(
			ServiceRegistrationRecord serviceRegistrationRecord)
			throws IOException {
		List<ServiceRegistrationRecord> serviceRegistrationRecords = getServiceRegistrationRecords();
		serviceRegistrationRecords.add(serviceRegistrationRecord);

		XStream xstream = new XStream();
		FileWriter fw = new FileWriter(ApolloServiceImpl.getRegistryFilename());
		fw.write(xstream.toXML(serviceRegistrationRecords));
		fw.close();
	}

	public synchronized static void removeServiceRegistrationRecord(
			ServiceRegistrationRecord serviceRegistrationRecord)
			throws IOException {
		List<ServiceRegistrationRecord> serviceRegistrationRecords = getServiceRegistrationRecords();
		for (int i = serviceRegistrationRecords.size() - 1; i >= 0; i--) {
			if (serviceRegistrationRecordsEqual(
					serviceRegistrationRecords.get(i),
					serviceRegistrationRecord)) {
				serviceRegistrationRecords.remove(i);
				break;

			}
		}

		XStream xstream = new XStream();
		FileWriter fw = new FileWriter(ApolloServiceImpl.getRegistryFilename());
		fw.write(xstream.toXML(serviceRegistrationRecords));
		fw.close();
	}

	public static boolean serviceRegistrationRecordsEqual(
			ServiceRegistrationRecord sr1, ServiceRegistrationRecord sr2) {
		return authenticationEqual(sr1.getAuthentication(),
				sr2.getAuthentication())
				&& softwareIdentificationEqual(sr1.getSoftwareIdentification(),
						sr2.getSoftwareIdentification())
				&& sr1.getUrl().equals(sr2.getUrl());

	}

	public static boolean authenticationEqual(Authentication a1,
			Authentication a2) {
		return a1.getRequesterId().equals(a2.getRequesterId())
				&& a1.getRequesterPassword().equals(a2.getRequesterPassword());
	}

	public static boolean softwareIdentificationEqual(
			SoftwareIdentification si1, SoftwareIdentification si2) {

		if (si1.getSoftwareDeveloper().equals(si2.getSoftwareDeveloper())
				&& si1.getSoftwareName().equals(si2.getSoftwareName())
				&& si1.getSoftwareType().equals(si2.getSoftwareType())
				&& si1.getSoftwareVersion().equals(si2.getSoftwareVersion())) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean serviceUrlEqual(ServiceRegistrationRecord srr1,
			ServiceRegistrationRecord srr2) {

		return srr1.getUrl().equalsIgnoreCase(srr2.getUrl());
	}
	
}
