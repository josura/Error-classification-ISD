package com.entonomachia;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ElasticInterface extends Remote {
	public QueryResult getResult() throws RemoteException;
}
