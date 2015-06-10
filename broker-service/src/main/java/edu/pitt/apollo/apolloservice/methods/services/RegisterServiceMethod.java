package edu.pitt.apollo.apolloservice.methods.services;

import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:48:19 PM
 * Class: RegisterServiceMethod
 * IDE: NetBeans 6.9.1
 */
public class RegisterServiceMethod extends RegisteredServicesMethod {

    public static MethodCallStatus registerService(ServiceRegistrationRecord serviceRegistrationRecord) {
 //       MethodCallStatus result = new MethodCallStatus();

        // this method needs to be updated
        
//        List<ServiceRegistrationRecord> records;
//        try {
//
//            records = RegistrationUtils.getServiceRegistrationRecords();
//
//            for (ServiceRegistrationRecord record : records) {
//                if (RegistrationUtils.softwareIdentificationEqual(
//                        record.getSoftwareIdentification(),
//                        serviceRegistrationRecord.getSoftwareIdentification())) {
//                    result.setMessage("Service is already registered.  Please unRegisterService to make changes to the existing ServiceRecord.");
//                    result.setStatus(MethodCallStatusEnum.FAILED);
//                    return result;
//                }
//
//                if (RegistrationUtils.serviceUrlEqual(record,
//                        serviceRegistrationRecord)) {
//                    result.setMessage("URL is already registered.");
//                    result.setStatus(MethodCallStatusEnum.FAILED);
//                    return result;
//                }
//            }
//
//            // if we are here, it looks like a valid registration
//            try {
//                RegistrationUtils.addServiceRegistrationRecord(serviceRegistrationRecord);
//                result.setMessage("Service Registration Successful!");
//                result.setStatus(MethodCallStatusEnum.COMPLETED);
//            } catch (IOException e) {
//                result.setMessage("Error registering service: "
//                        + e.getMessage());
//                result.setStatus(MethodCallStatusEnum.FAILED);
//            }
//
//        } catch (IOException e) {
//            result.setMessage("Error reading registry!");
//            result.setStatus(MethodCallStatusEnum.FAILED);
//        }
//        return result;
        
        return null;
    }
}
