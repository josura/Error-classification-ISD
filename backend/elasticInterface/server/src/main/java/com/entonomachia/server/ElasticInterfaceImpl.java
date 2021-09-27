package com.entonomachia.server;


import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.asyncsearch.AsyncSearchResponse;
import org.elasticsearch.client.asyncsearch.SubmitAsyncSearchRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ElasticInterfaceImpl extends UnicastRemoteObject implements ElasticInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected QueryResult result = null;
	public RestHighLevelClient client = null;
	
	public ElasticInterfaceImpl() throws RemoteException{
		super();
		client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("elastic-search", 9200, "http")));	
	}
	/*
	 * SYNC OPERATIONS
	 */
	
	public QueryResult findCodeByUserSync(String user) throws IOException {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("user", user)); 
		sourceBuilder.from(0); 
		sourceBuilder.size(5); //returns 5 hits max
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); 
		SearchRequest request = new SearchRequest("primarydirect");
		request.source(sourceBuilder);
	    SearchResponse queryResult = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] searchHits = queryResult.getHits().getHits();
        for (SearchHit hit : searchHits) {
            result.addDocument(hit);
        }
		return result;
	}
	
	public QueryResult findCodeByGroupSync(String group) throws IOException {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("group", group)); 
		sourceBuilder.from(0); 
		sourceBuilder.size(5); //returns 5 hits max
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); 
		SearchRequest request = new SearchRequest("primarydirect");
		request.source(sourceBuilder);
	    SearchResponse queryResult = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] searchHits = queryResult.getHits().getHits();
        for (SearchHit hit : searchHits) {
            result.addDocument(hit);
        }
		return result;
	}
	
	public QueryResult findCodeByLabelErrorSync(Double label) throws IOException {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("labelError", label)); 
		sourceBuilder.from(0); 
		sourceBuilder.size(5); //returns 5 hits max
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); 
		SearchRequest request = new SearchRequest("primarydirect");
		request.source(sourceBuilder);
	    SearchResponse queryResult = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] searchHits = queryResult.getHits().getHits();
        for (SearchHit hit : searchHits) {
            result.addDocument(hit);
        }
		return result;
	}
	
	public QueryResult findCodeByLabelMutantSync(Double label) throws IOException {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("labelMutant", label)); 
		sourceBuilder.from(0); 
		sourceBuilder.size(5); //returns 5 hits max
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); 
		SearchRequest request = new SearchRequest("primarydirect");
		request.source(sourceBuilder);
	    SearchResponse queryResult = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] searchHits = queryResult.getHits().getHits();
        for (SearchHit hit : searchHits) {
            result.addDocument(hit);
        }
		return result;
	}
	
	
	
	/*
	 * ASYNC OPERATIONS
	 */
	//TODO fix to wait for the async operation to complete
	public QueryResult findCodeByUserAsync(String user) {
		SearchSourceBuilder searchSource = new SearchSourceBuilder().
				query(QueryBuilders.matchQuery("user", user));
		String[] indices = new String[] { "my-index" };
		SubmitAsyncSearchRequest request = new SubmitAsyncSearchRequest(searchSource, indices);
		//request.setWaitForCompletionTimeout(TimeValue.timeValueSeconds(30)); 
		//request.setKeepAlive(TimeValue.timeValueMinutes(15));
		request.setKeepOnCompletion(false); 
		
		ActionListener<AsyncSearchResponse> listener =
			    new ActionListener<AsyncSearchResponse>() {
			        @Override
			        public void onResponse(AsyncSearchResponse response) {
			            SearchResponse queryResult = response.getSearchResponse();
			            SearchHit[] searchHits = queryResult.getHits().getHits();
			            for (SearchHit hit : searchHits) {
			                result.addDocument(hit);
			            }
			        }

			        @Override
			        public void onFailure(Exception e) {
			            //TODO logging
			        	//throw new RemoteException("Elastic response exception");
			        }
			    };
		Cancellable queryProcess = client.asyncSearch().
	    	submitAsync(request, RequestOptions.DEFAULT, listener);
		return result;
	}
	
	
	public void setResult(QueryResult res) {
		result=res;
	}
	
	public QueryResult getResult() {
		return result;
	}
	
	public void closeElasticClient() throws IOException {
		client.close();
	}
	
}
