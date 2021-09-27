package com.entonomachia.server;


import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ElasticServer{
	
	public static void main(String[] args) {
		ElasticInterface exported = null;
		try {			
			exported = new ElasticInterfaceImpl();
			//TESTING
			QueryResult res = new QueryResult();
			res.readJson("{\"ids\":1,\"user\":\"GIORGIO\",\"group\":\"SELF\",\"code\":\"if(testing=false){String goodbye = \\\"\\\";}\"}");
			exported.setResult(res);
			//TESTING
			Naming.rebind("//localhost/ElasticFacade", exported);
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch(MalformedURLException e) {
			// TODO additional error handling
			e.printStackTrace();
		}
		try {
			exported.closeElasticClient();
		}catch(IOException e) {
			
		}
	}

}
