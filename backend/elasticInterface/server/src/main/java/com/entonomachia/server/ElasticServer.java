package com.entonomachia.server;


import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ElasticServer{
	
	public static void main(String[] args) {
		ElasticInterface exported = null;
		try {			
			if(InetAddress.getByName("elastic-search").isReachable(1000)) {
				System.out.println("elastic-search server reachable");
			}
			exported = new ElasticInterfaceImplDTO();
			//TESTING
			//QueryResult res = new QueryResult();
			//res.readJson("{\"ids\":1,\"user\":\"GIORGIO\",\"group\":\"SELF\",\"code\":\"if(testing=false){String goodbye = \\\"\\\";}\"}");
			//exported.setResult(res);
			String teststr = exported.findCodeByLabelMutantSyncString(14.0);
			System.out.println(teststr);
			//TESTING
			Registry rgsty =  LocateRegistry.createRegistry(1099);
			System.out.println("Opening the connection to the interface");
			//Naming.rebind("//localhost/ElasticFacade", exported);
			rgsty.rebind("ElasticFacade", exported);
			//System.out.println(exported.getResult());
			while(true) {
				Thread.sleep(4000);
			}
		} catch (Exception e) {
			System.out.println("Exception in ElasticServer");
			e.printStackTrace();
		}
	}

}
