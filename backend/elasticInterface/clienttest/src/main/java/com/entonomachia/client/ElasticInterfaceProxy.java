package com.entonomachia.client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.entonomachia.server.ElasticInterface;
import com.entonomachia.server.QueryResultDTO;

public class ElasticInterfaceProxy implements ElasticInterface{
	private ElasticInterface ElFac;

    public ElasticInterfaceProxy() {
        try {
            
          //RMI client to elastic-interface setup
		  System.setProperty("java.rmi.server.hostname","elastic-facade-server");
		  //ElFac = (ElasticInterface)Naming.lookup("ElasticFacade");
		  Registry reg=LocateRegistry.getRegistry("elastic-facade-server",1099);
		  ElFac = (ElasticInterface)reg.lookup("ElasticFacade");
		      
        } catch (RemoteException e) {
            System.err.println(e);
        } catch (NotBoundException e) {
            System.err.println(e);
        }
    }

	@Override
	public String findCodeByUserSyncString(String user) throws IOException, RemoteException {
		System.out.println("Finding Code by User");
        try {
            return ElFac.findCodeByUserSyncString(user);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public String findCodeByGroupSyncString(String group) throws IOException, RemoteException {
		System.out.println("Finding Code by Group");
        try {
            return ElFac.findCodeByGroupSyncString(group);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public String findCodeByLabelErrorSyncString(Double label) throws IOException, RemoteException {
		System.out.println("Finding Code by Label Error");
        try {
            return ElFac.findCodeByLabelErrorSyncString(label);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public String findCodeByLabelMutantSyncString(Double label) throws IOException, RemoteException {
		System.out.println("Finding Code by Label Mutant");
        try {
            return ElFac.findCodeByLabelMutantSyncString(label);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public QueryResultDTO findCodeByUserSync(String user) throws IOException, RemoteException {
		System.out.println("Finding Code DTO by User");
        try {
            return ElFac.findCodeByUserSync(user);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public QueryResultDTO findCodeByGroupSync(String group) throws IOException, RemoteException {
		System.out.println("Finding Code DTO by group");
        try {
            return ElFac.findCodeByGroupSync(group);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public QueryResultDTO findCodeByLabelErrorSync(Double label) throws IOException, RemoteException {
		System.out.println("Finding Code DTO by Label Error");
        try {
            return ElFac.findCodeByLabelErrorSync(label);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public QueryResultDTO findCodeByLabelMutantSync(Double label) throws IOException, RemoteException {
		System.out.println("Finding Code DTO by Label Mutant");
        try {
            return ElFac.findCodeByLabelMutantSync(label);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public String findCodeByLabelErrorCredentialsSyncString(Double label, String user, String group)
			throws IOException, RemoteException {
		System.out.println("Finding Code by label Error and Credentials: " + label.toString() + " " + user + " " + group);
        try {
            return ElFac.findCodeByLabelErrorCredentialsSyncString(label,user,group);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public String findCodeByLabelMutantCredentialsSyncString(Double label, String user, String group)
			throws IOException, RemoteException {
		System.out.println("Finding Code by label Mutant and Credentials: " + label.toString() + " " + user + " " + group);
        try {
            return ElFac.findCodeByLabelMutantCredentialsSyncString(label,user,group);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public QueryResultDTO findCodeByLabelErrorCredentialsSync(Double label, String user, String group)
			throws IOException, RemoteException {
		System.out.println("Finding Code DTO by label Error and Credentials: " + label.toString() + " " + user + " " + group);
        try {
            return ElFac.findCodeByLabelErrorCredentialsSync(label,user,group);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}

	@Override
	public QueryResultDTO findCodeByLabelMutantCredentialsSync(Double label, String user, String group)
			throws IOException, RemoteException {
		System.out.println("Finding Code DTO by label Mutant and Credentials: " + label.toString() + " " + user + " " + group);
        try {
            return ElFac.findCodeByLabelMutantCredentialsSync(label,user,group);
        } catch (RemoteException e) {
            System.err.println(e);
        }
		return null;
	}
}
