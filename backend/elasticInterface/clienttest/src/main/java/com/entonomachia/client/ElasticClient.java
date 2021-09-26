package com.entonomachia.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.entonomachia.server.ElasticInterface;


//this class will not be used but will be used as template in the spring backend application
public class ElasticClient {
	private static ElasticInterface ElFac;
	
	public static void main(String[] args) {
		try {
			//ElFac = (ElasticInterface)Naming.lookup("//elastic-facade-server/ElasticFacade");
			ElFac = (ElasticInterface)Naming.lookup("//localhost/ElasticFacade");
			System.out.println(ElFac.getResult());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch(RemoteException e) {
			e.printStackTrace();
		} catch(NotBoundException e) {
			e.printStackTrace();
		}
	}

}
