package com.entonomachia.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryResultOld implements Serializable{
	private static final long serialVersionUID = 1L;
	public String jsonRes;
	public List<String> listJsonRes;
	public List<Map<String,Object>> listMapRes;
	
	public QueryResultOld() {
		listJsonRes = new ArrayList<String>();
		listMapRes = new ArrayList<Map<String,Object>>();
	}
	
	public void readJson(String json) {
		jsonRes=json;
		//TODO unmarshalling
	}
	
	public void addJson(String json) {
		listJsonRes.add(json);
		//TODO from json to Map
	}
	
	public void addMap(Map<String, Object> map) {
		listMapRes.add(map);
	}
	
//	public void addDocument(SearchHit hit) {
//		listJsonRes.add(hit.getSourceAsString());
//		listMapRes.add(hit.getSourceAsMap());
//	}
	
	@Override
	public String toString() {
		// The map is the same as the list of json
		return jsonRes + "\n" + String.join(",\n", listJsonRes);
	}
}
