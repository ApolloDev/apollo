package edu.pitt.apollo.flute.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileUtils {

	public static void downloadFile(URL sourcefile, String destFileName)
			throws IOException {

		ReadableByteChannel rbc = Channels.newChannel(sourcefile.openStream());
		FileOutputStream fos = new FileOutputStream(destFileName);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		fos.flush();
		fos.close();
	}
}
