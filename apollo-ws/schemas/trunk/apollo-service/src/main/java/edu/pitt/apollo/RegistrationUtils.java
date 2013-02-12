package edu.pitt.apollo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import edu.pitt.apollo.types.Authentication;
import edu.pitt.apollo.types.ServiceRecord;
import edu.pitt.apollo.types.ServiceRegistrationRecord;
import edu.pitt.apollo.types.SimulatorIdentification;
import edu.pitt.apollo.types.VisualizerIdentification;

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
	
	public static String getUrlForSimulatorIdentification(SimulatorIdentification si) throws IOException {
		List<ServiceRegistrationRecord> records = getServiceRegistrationRecords();
		for (ServiceRegistrationRecord record: records) {
			if (record.getServiceRecord().getSimulatorIdentification() != null) {
				SimulatorIdentification sid = record.getServiceRecord().getSimulatorIdentification();
				if (sid.getSimulatorDeveloper().equals(si.getSimulatorDeveloper()) &&
						sid.getSimulatorName().equals(si.getSimulatorName()) &&
						sid.getSimulatorVersion().equals(si.getSimulatorVersion())) {
					return record.getUrl();
				}
			}
		}
		return null;
	}

	public static String getUrlForVisualizerIdentification(VisualizerIdentification vi) throws IOException {
		List<ServiceRegistrationRecord> records = getServiceRegistrationRecords();
		for (ServiceRegistrationRecord record: records) {
			if (record.getServiceRecord().getVisualizerIdentification() != null) {
				VisualizerIdentification sid = record.getServiceRecord().getVisualizerIdentification();
				if (sid.getVisualizerDeveloper().equals(vi.getVisualizerDeveloper()) &&
						sid.getVisualizerName().equals(vi.getVisualizerName()) &&
						sid.getVisualizerVersion().equals(vi.getVisualizerVersion())) {
					return record.getUrl();
				}
			}
		}
		return null;
	}
	public synchronized static List<ServiceRecord> getServiceRecords()
			throws IOException {
		List<ServiceRegistrationRecord> serviceRegistrationRecords = getServiceRegistrationRecords();
		List<ServiceRecord> serviceRecords = new ArrayList<ServiceRecord>(
				serviceRegistrationRecords.size());

		for (ServiceRegistrationRecord serviceRegistrationRecord : serviceRegistrationRecords) {
			serviceRecords.add(serviceRegistrationRecord.getServiceRecord());
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
			if (serviceRegistrationRecordsEqual(serviceRegistrationRecords.get(i), serviceRegistrationRecord)) {
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
				&& serviceIdentificationEqual(sr1.getServiceRecord(),
						sr2.getServiceRecord())
				&& sr1.getUrl().equals(sr2.getUrl());

	}

	public static boolean authenticationEqual(Authentication a1,
			Authentication a2) {
		return a1.getRequesterId().equals(a2.getRequesterId())
				&& a1.getRequesterPassword().equals(a2.getRequesterPassword());
	}

	public static boolean serviceIdentificationEqual(ServiceRecord sr1,
			ServiceRecord sr2) {
		if (sr1.getSimulatorIdentification() != null) {
			if (sr2.getSimulatorIdentification() == null)
				return false;
			SimulatorIdentification si1 = sr1.getSimulatorIdentification();
			SimulatorIdentification si2 = sr2.getSimulatorIdentification();

			if (si1.getSimulatorDeveloper().equals(si2.getSimulatorDeveloper())
					&& si1.getSimulatorName().equals(si2.getSimulatorName())
					&& si1.getSimulatorVersion().equals(
							si2.getSimulatorVersion())) {
				return true;
			} else {
				return false;
			}
		}

		if (sr1.getVisualizerIdentification() != null) {
			if (sr2.getVisualizerIdentification() == null)
				return false;

			VisualizerIdentification vi1 = sr1.getVisualizerIdentification();
			VisualizerIdentification vi2 = sr2.getVisualizerIdentification();

			if (vi1.getVisualizerDeveloper().equals(
					vi2.getVisualizerDeveloper())
					&& vi1.getVisualizerName().equals(vi2.getVisualizerName())
					&& vi1.getVisualizerVersion().equals(
							vi2.getVisualizerVersion())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public static boolean serviceUrlEqual(ServiceRegistrationRecord srr1,
			ServiceRegistrationRecord srr2) {

		return srr1.getUrl().equalsIgnoreCase(srr2.getUrl());
	}
}
