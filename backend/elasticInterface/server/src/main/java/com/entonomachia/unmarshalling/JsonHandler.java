package com.entonomachia.unmarshalling;

import com.google.gson.Gson;


//TODO controls over the schema of the json
public class JsonHandler {
	static Gson gson;
	static {
		gson = new Gson();
	}
	
	public static DocumentUnmarshalled jsonToDocument(String json) {
		return gson.fromJson(json, DocumentUnmarshalled.class);
	}
	
	public static String documentToJson(DocumentUnmarshalled docu) {
		return gson.toJson(docu);
	}

	public static Source jsonToSource(String json) {
		return gson.fromJson(json, Source.class);
	}
	
	public static String sourceToJson(Source src) {
		return gson.toJson(src);
	}

}
