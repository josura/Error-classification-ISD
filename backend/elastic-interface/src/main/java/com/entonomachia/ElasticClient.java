package com.entonomachia;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ElasticClient {
	private static ElasticInterface ElFac;
	
	public static void main(String[] args) {
		try {
			ElFac = (ElasticInterface)Naming.lookup("//elastic-facade-server/ElasticServer");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO this class will be used in the backend along the interface
			e.printStackTrace();
		}
	}

}
