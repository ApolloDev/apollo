package edu.pitt.apollo.client.wrapper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class GsonHelper<T> {

	Type t;

	public GsonHelper(Type t) {
		super();
		this.t = t;
	}

	public void writeJsonStream(OutputStream out, List<T> list)
			throws IOException {
		Gson gson = new Gson();
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
		writer.setIndent("  ");
		writer.beginArray();
		for (T input : list) {
			gson.toJson(input, t, writer);
		}
		writer.endArray();
		writer.close();
	}

}
