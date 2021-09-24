package com.entonomachia;

import java.rmi.RemoteException;

public class ElasticInterfaceImpl implements ElasticInterface{
	
	protected QueryResult result = null;
	
	public ElasticInterfaceImpl() {
		//TODO
		
	}
	
	@Override
	public QueryResult getResult() throws RemoteException{
		return result;
	}
	
}
