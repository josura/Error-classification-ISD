package com.entonomachia.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.entonomachia.server.ElasticInterface;
import com.entonomachia.server.QueryResultDTO;


//this class will not be used but will be used as template in the spring backend application
public class ElasticClient {
	private static ElasticInterface ElFac;
	
	public static void main(String[] args) {
		try {
			System.setProperty("java.rmi.server.hostname","elastic-facade-server");
			//ElFac = (ElasticInterface)Naming.lookup("ElasticFacade");
			Registry reg=LocateRegistry.getRegistry("elastic-facade-server",1099);
			ElFac = (ElasticInterface)reg.lookup("ElasticFacade");
			//ElFac = (ElasticInterface)Naming.lookup("//localhost/ElasticFacade");
			//ElFac.findCodeByLabelErrorSync(14.0);
			//System.out.println(ElFac.getResult());
			System.out.println(ElFac.findCodeByUserSyncString("user"));
			QueryResultDTO res = ElFac.findCodeByLabelErrorSync(40.0);
			System.out.println(res.code[0]);
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}

}
