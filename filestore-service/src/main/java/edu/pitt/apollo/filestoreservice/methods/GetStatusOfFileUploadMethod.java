package edu.pitt.apollo.filestoreservice.methods;

import edu.pitt.apollo.filestoreservice.types.DirectoryContentFile;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFileEntry;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by jdl50 on 2/10/16.
 */
public class GetStatusOfFileUploadMethod extends FileStoreCoreMethod {

	public GetStatusOfFileUploadMethod(String rootDirectory, String webRoot, BigInteger runId, String salt) {
		super(rootDirectory, webRoot, runId, salt);
	}

	public MethodCallStatus getStatus(String filename, ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType) throws IOException {
		DirectoryContentFile directoryContentFile = readDirectoryContentFile();
		MethodCallStatus status = new MethodCallStatus();
		// default status
		status.setMessage("File does not exist and is not downloading.");
		status.setStatus(MethodCallStatusEnum.NOT_FOUND);

		for (DirectoryContentFileEntry directoryContentFileEntry : directoryContentFile.getFiles()) {
			if (directoryContentFileEntry.getFileIdentification().getFormat() == fileFormat
					&& directoryContentFileEntry.getFileIdentification().getType() == fileType
					&& directoryContentFileEntry.getFileIdentification().getLabel().equals(filename)) {

				File localFile = getLocalFile(directoryContentFileEntry);
				File tempFile = getLocalFileTemporaryName(directoryContentFileEntry);

				if (localFile.exists()) {
					status.setMessage("Stored.");
					status.setStatus(MethodCallStatusEnum.COMPLETED);
					break;
				} else if (tempFile.exists()) {
					status.setMessage("Downloading.");
					status.setStatus(MethodCallStatusEnum.RUNNING);
					break;
				} else {
					status.setMessage("File does not exist and is not downloading.");
					status.setStatus(MethodCallStatusEnum.NOT_FOUND);
					break;
				}

			}
		}
		return status;
	}
}
