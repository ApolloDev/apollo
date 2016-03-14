/*
 * Copyright 2016 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo.utilities;

import edu.pitt.apollo.connector.FilestoreServiceConnector;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.utilities.Md5Utils;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class FileStoreServiceUtility {
	
	public static void uploadTextFileContent(String content, BigInteger runId, 
			FileIdentification fileIdentification, Authentication authentication,
			String localDirectory, String localBaseUrl, FilestoreServiceConnector filestoreServiceConnector) throws FilestoreException {
		String fileName = fileIdentification.getLabel();
		ContentDataTypeEnum contentType = fileIdentification.getType();
		ContentDataFormatEnum contentFormat = fileIdentification.getFormat();

		try {
			String tempFileName = new Md5Utils().getMd5FromString(fileName + contentFormat + contentType + runId) + ".txt";
			File file = new File(localDirectory + File.separator + tempFileName);
			file.createNewFile();
			PrintStream ps = new PrintStream(file);
			ps.print(content);
			ps.close();

			String url = localBaseUrl + "/" + tempFileName;
			filestoreServiceConnector.uploadFile(runId, url, fileIdentification, authentication);
		} catch (IOException ex) {
			throw new FilestoreException("IOException: " + ex.getMessage());
		}
	}

}
