package edu.pitt.apollo.client.wrapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import edu.pitt.apollo.types.EpidemicModelOutput;

public class EpidemicModelOutputIterator implements
		Iterator<EpidemicModelOutput> {

	JsonReader reader;
	Gson gson;

	public EpidemicModelOutputIterator(GZIPInputStream gis, String charSetName)
			throws IOException {
		this.gson = new Gson();
		this.reader = new JsonReader(new InputStreamReader(gis, charSetName));
		reader.beginArray();
	}

	@Override
	public boolean hasNext() {
		try {
			return reader.hasNext();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public EpidemicModelOutput next() {
		return gson.fromJson(reader, EpidemicModelOutput.class);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	// reader.endArray();
	// reader.close();

}
