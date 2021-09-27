package com.entonomachia.server;


import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ElasticInterface extends Remote {
	public void setResult(QueryResult res)throws RemoteException;
	public QueryResult getResult() throws RemoteException;
	public void closeElasticClient() throws IOException;
}
