package com.entonomachia.server;


import java.io.Serializable;

public class QueryResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String jsonRes;
	public String code;
	public String user;
	public String group;
	public String errorLabel;
	public String mutantLabel;

	public void readJson(String json) {
		jsonRes=json;
		//TODO unmarshalling
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return jsonRes;
	}
	

}
