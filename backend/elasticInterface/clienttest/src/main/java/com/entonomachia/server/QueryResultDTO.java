package com.entonomachia.server;

import java.io.Serializable;


public class QueryResultDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7200475353812708410L;
	
	public String fullJson;
	public Integer[] ids;
	public String[] code;
	public String[] solution;
	public String[] source;
	public String[] user;
	public String[] group;
	public Double[] labelError;
	public String[] probabilityErrorString;
	public String[] rawPredictionErrorString;
	public Double[] labelMutation;
	public String[] probabilityMutationString;
	public String[] rawPredictionMutationString;
	public String[] featureString;

}
