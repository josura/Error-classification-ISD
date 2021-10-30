package com.entonomachia.server;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;


public class ElasticRequestHandler {
	
	static ElasticRequestHandler instance = null;



	static RestClient client;
	static {
		client = RestClient.builder(new HttpHost("elastic-search", 9200, "http")).build();
	}

	private ElasticRequestHandler(){

	}
	
	public static ElasticRequestHandler getInstance(){
		if(instance == null){
			instance = new ElasticRequestHandler();
		}

		return instance;
	}

	/**
	* GET request to elasticsearch
	*
	* @param  index the index of elastic search, example /index/
	* @return      response body
	*/
	public static String GetRequest(String index) throws IOException {
		Request request = new Request(
			    "GET",  
			    index + "_search"); 
		request.addParameter("pretty", "true");
		Response response = client.performRequest(request);
		
		//Header[] headers = response.getHeaders(); 
		return EntityUtils.toString(response.getEntity());
	}
	
	/**
	* GET request to elasticsearch with request body to query(unsafe, better to do a POST)
	*
	* @param  index the index of elastic search, example /index/
	* @param  requestBody  the query request body in json, sometime It does not work so use POST to query with request body
	* @return      response body
	*/
	public static String GetRequest(String index,String requestBody) throws IOException {
		Request request = new Request(
			    "GET",  
			    index + "_search"); 
		request.addParameter("pretty", "true");
		request.setEntity(new NStringEntity(requestBody, ContentType.APPLICATION_JSON));
		Response response = client.performRequest(request);
		
		//Header[] headers = response.getHeaders(); 
		return EntityUtils.toString(response.getEntity());
	}
	
	/**
	* POST request to elasticsearch with request body to query
	*
	* @param  index the index of elastic search, example /index/
	* @param  requestBody  the query request body in json, sometime It does not work so use POST to query with request body
	* @return      response body
	*/
	public static String PostRequest(String index,String requestBody) throws IOException {
		Request request = new Request(
			    "POST",  
			    index + "_search"); 
		request.addParameter("pretty", "true");
		request.setEntity(new NStringEntity(requestBody, ContentType.APPLICATION_JSON));
		Response response = client.performRequest(request);
		
		//Header[] headers = response.getHeaders(); 
		return EntityUtils.toString(response.getEntity());
	}
	
	
}
