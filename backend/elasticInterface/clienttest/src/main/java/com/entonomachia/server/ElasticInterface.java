package com.entonomachia.server;


import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ElasticInterface extends Remote {
	public void setResult(QueryResult res)throws RemoteException;
	public QueryResult getResult() throws RemoteException;
	public String findCodeByUserSyncString(String user) throws IOException,RemoteException;
	public String findCodeByGroupSyncString(String user) throws IOException,RemoteException;
	public String findCodeByLabelErrorSyncString(Double label) throws IOException,RemoteException;
	public String findCodeByLabelMutantSyncString(Double label) throws IOException,RemoteException;
	public QueryResult findCodeByUserSync(String user) throws IOException,RemoteException;
	public QueryResult findCodeByGroupSync(String group) throws IOException,RemoteException;
	public QueryResult findCodeByLabelErrorSync(Double label) throws IOException,RemoteException;
	public QueryResult findCodeByLabelMutantSync(Double label) throws IOException,RemoteException;
	
}
