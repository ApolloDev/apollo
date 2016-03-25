package edu.pitt.apollo.filestoreservice.test;


import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.filestoreservice.FileStoreService;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.types.v4_0.InfectiousDiseaseScenario;
import edu.pitt.apollo.utilities.JsonUtils;
import junit.framework.TestCase;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class FileStoreTests extends TestCase {
    public void test() {
        Authentication authentication = new Authentication();
        FileStoreService fileStoreService = new FileStoreService();
        FileIdentification fileIdentification = new FileIdentification();
        fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
        fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
        fileIdentification.setLabel("test_download_file");
        try {
            //fileStoreService.uploadFile(new BigInteger("0"), "http://data.olympus.psc.edu/apollo/current.version/output.allegheny.county_age.race.gender.location.hdf5", fileIdentification);
            fileStoreService.deleteFilesForRun(new BigInteger("0"));

            //fileStoreService.uploadFile(new BigInteger("0"), "http://download.thinkbroadband.com/100MB.zip" , fileIdentification);
            fileStoreService.uploadFile(new BigInteger("0"), "http://distro.ibiblio.org/tinycorelinux/7.x/x86/release/CorePlus-current.iso", 
					fileIdentification.getLabel(), fileIdentification.getFormat(), fileIdentification.getType(), authentication);
            String publicUrl = fileStoreService.getUrlOfFile(new BigInteger("0"), fileIdentification.getLabel(), fileIdentification.getFormat(), fileIdentification.getType(), authentication);

            System.out.println("File will be downloaded to " + publicUrl);
            List<FileIdentification> list = fileStoreService.listFilesForRun(new BigInteger("0"), null);
            for (FileIdentification fi : list) {
                System.out.println("File will be saved as: " + fi.getLabel());
            }
            String status = "";
            while (!status.equals("Stored.")) {
                status = fileStoreService.getStatusOfFileUpload(new BigInteger("0"), fileIdentification.getLabel(), fileIdentification.getFormat(), fileIdentification.getType(), authentication).getMessage();
                System.out.println(status);
                Thread.sleep(250);
            }
        } catch (Exception e) {
            fail("Test failure.  Error was (" + e.getClass() + ") " + e.getMessage());
        }
    }
}