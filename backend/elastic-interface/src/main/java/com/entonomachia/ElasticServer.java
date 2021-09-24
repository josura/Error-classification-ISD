package com.entonomachia;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ElasticServer extends UnicastRemoteObject implements ElasticInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected QueryResult result = null;
	protected ElasticServer() throws RemoteException {
		super();
		// TODO additional data initializer
	}

	@Override
	public QueryResult getResult() throws RemoteException {		
		return result;
	}
	
	public static void main(String[] args) {
		try {
			Naming.rebind("//localhost/ElasticServer", new ElasticServer());
		} catch (RemoteException | MalformedURLException e) {
			// TODO additional error handling
			e.printStackTrace();
		}
	}

}
