package com.entonomachia.server;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ElasticInterfaceImpl extends UnicastRemoteObject implements ElasticInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected QueryResult result = null;
	
	public ElasticInterfaceImpl() throws RemoteException{
		super();
		
	}
	public void setResult(QueryResult res) {
		result=res;
	}
	
	public QueryResult getResult() {
		return result;
	}
	
}
