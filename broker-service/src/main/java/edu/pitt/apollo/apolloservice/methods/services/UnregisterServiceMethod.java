package edu.pitt.apollo.apolloservice.methods.services;

import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.ServiceRegistrationRecord;


/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:45:59 PM
 * Class: UnregisterServiceMethod
 * IDE: NetBeans 6.9.1
 */
public class UnregisterServiceMethod extends RegisteredServicesMethod {
    
        public static MethodCallStatus unregisterService(ServiceRegistrationRecord serviceRegistrationRecord) {
 //       MethodCallStatus result = new MethodCallStatus();

        // this method needs to be updated
        
//        List<ServiceRegistrationRecord> records;
//        try {
//            // get the entire list of current service registration records
//            records = RegistrationUtils.getServiceRegistrationRecords();
//
//            for (ServiceRegistrationRecord record : records) {
//                // for each record currently in the registry, see if we can find
//                // a record with a ServiceIdentification that is equal to one
//                // that the user is trying to unregister
//                if (RegistrationUtils.softwareIdentificationEqual(
//                        record.getSoftwareIdentification(),
//                        serviceRegistrationRecord.getSoftwareIdentification())) {
//                    // found the service the user wants to unregister, now check
//                    // that the username and password supplied with this request
//                    // match the username and password sent with the
//                    // registration request
//                    if (RegistrationUtils.authenticationEqual(
//                            record.getAuthentication(),
//                            serviceRegistrationRecord.getAuthentication())) {
//                        try {
//                            // the username/password match, so remove the record
//                            // from the registry
//                            RegistrationUtils.removeServiceRegistrationRecord(serviceRegistrationRecord);
//                        } catch (IOException e) {
//                            // there was en error removing the record, report
//                            // this error to the caller
//                            result.setMessage("Error Unregistering Service: "
//                                    + e.getMessage());
//                            result.setStatus(MethodCallStatusEnum.FAILED);
//                            return result;
//                        }
//                        // removal succeeded
//                        result.setMessage("unregistration Successful!");
//                        result.setStatus(MethodCallStatusEnum.COMPLETED);
//                        return result;
//                    } else {
//                        // username/passwords do not match
//                        result.setMessage("Error Unregistering Service: Username/Password does not match orignial ServiceRegistrationRecord!");
//                        result.setStatus(MethodCallStatusEnum.FAILED);
//                        return result;
//                    }
//                }
//            }
//            // couldn't find matching ServiceRecords
//            result.setMessage("Error Unregistering Service: Service not registered at this registry.");
//            result.setStatus(MethodCallStatusEnum.FAILED);
//            return result;
//        } catch (IOException e) {
//            result.setMessage("Error Unregistering Service: Error reading registry!");
//            result.setStatus(MethodCallStatusEnum.FAILED);
//            return result;
//        }
        
        return null;
    }
    
}
