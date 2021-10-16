package com.entonomachia.unmarshalling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


//TODO controls over the schema of the json
public class JsonHandler {
	static Gson gson;
	static {
		gson = new GsonBuilder().disableHtmlEscaping().create();
	}
	
	public static String generalToJson(Object gen) {
		return gson.toJson(gen);
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
