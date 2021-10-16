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
	
	private void addJsonSource(String json) {
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
	
	public QueryResultDTO toDTO() {
		QueryResultDTO res = new QueryResultDTO();
		res.fullJson = fullJsonRes;
		res.onlyHitsJson = JsonHandler.generalToJson(fullDoc.getHits().getHits());
		res.code = new String[listRes.size()];
		res.featureString = new String[listRes.size()];
		res.group = new String[listRes.size()];
		res.probabilityErrorString = new String[listRes.size()];
		res.probabilityMutationString = new String[listRes.size()];
		res.rawPredictionErrorString = new String[listRes.size()];
		res.rawPredictionMutationString = new String[listRes.size()];
		res.solution = new String[listRes.size()];
		res.source = new String[listRes.size()];
		res.user = new String[listRes.size()];
		res.ids = new Integer[listRes.size()];
		res.labelError = new Double[listRes.size()];
		res.labelMutation = new Double[listRes.size()];
		int i = 0;
		for(Source src: listRes) {
			res.code[i] = src.getCode();
			res.featureString[i] = src.getFeatureString();
			res.group[i] = src.getGroup();
			res.probabilityErrorString[i] = src.getProbabilityErrorString();
			res.probabilityMutationString[i] = src.getProbabilityMutationString();
			res.rawPredictionErrorString[i] = src.getRawPredictionErrorString();
			res.rawPredictionMutationString[i] = src.getRawPredictionMutationString();
			res.solution[i] = src.getSolution();
			res.source[i] = src.getSource();
			res.user[i] = src.getUser();
			res.ids[i] = src.getIds();
			res.labelError[i] = src.getLabelError();
			res.labelMutation[i] = src.getLabelMutation();
			i++;
		}
		return res;
	}
	
	@Override
	public String toString() {
		// The map is the same as the list of json
		return fullJsonRes;
	}
	

}
