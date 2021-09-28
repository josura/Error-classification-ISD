package com.entonomachia.server;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.entonomachia.unmarshalling.DocumentUnmarshalled;
import com.entonomachia.unmarshalling.Hit;
import com.entonomachia.unmarshalling.JsonHandler;
import com.entonomachia.unmarshalling.Source;


public class QueryResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String fullJsonRes = "placeholder";
	public DocumentUnmarshalled fullDoc;
	public List<Source> listRes;
	
	public QueryResult() {
		listRes = new ArrayList<Source>();
	}
	
	public void readJson(String json) {
		fullJsonRes=json;
		this.addDocument(JsonHandler.jsonToDocument(json));
		
	}
	
	public void addJsonSource(String json) {
		listRes.add(JsonHandler.jsonToSource(json));
		
	}
	
	private void addSource(Source src) {
		listRes.add(src);
	}
	
	private void addDocument(DocumentUnmarshalled docu) {
		fullDoc = docu;
		for(Hit hit: docu.getHits().getHits()) {
			this.addSource(hit.getSource());
		}
	}
	
//	public void addDocument(SearchHit hit) {
//		listJsonRes.add(hit.getSourceAsString());
//		listMapRes.add(hit.getSourceAsMap());
//	}
	
	@Override
	public String toString() {
		// The map is the same as the list of json
		return fullJsonRes;
	}
	

}
