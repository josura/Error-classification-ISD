package com.entonomachia.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ElasticInterfaceImplDTO extends UnicastRemoteObject implements ElasticInterface {

	//TODO instantiate a single QueryResult and use It as an assembler for the QueryResultDTO to optimize

	/**
	 * 
	 */
	private static final long serialVersionUID = 2217960169529312921L;

	protected ElasticInterfaceImplDTO() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

//	@Override
	public void setResult(QueryResultDTO res) throws RemoteException {
		// TODO Auto-generated method stub

	}

//	@Override
	public QueryResultDTO getResult() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findCodeByUserSyncString(String user) throws IOException, RemoteException {
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/",
		        "{\n"
		        + "  \"query\": {\n"
		        + "    \"match_phrase\": {\n"
		        + "      \"user\" : \"" + user +  "\"\n"
		        + "    }\n"
		        + "  }\n"
		        + "}");
		return responseJson;
	}

	@Override
	public String findCodeByGroupSyncString(String group) throws IOException, RemoteException {
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/",
		        "{\n"
		        + "  \"query\": {\n"
		        + "    \"match_phrase\": {\n"
		        + "      \"group\" : \"" + group +  "\"\n"
		        + "    }\n"
		        + "  }\n"
		        + "}");
		return responseJson;
	}

	@Override
	public String findCodeByLabelErrorSyncString(Double label) throws IOException, RemoteException {
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/",
		        "{\n"
		        + "  \"query\": {\n"
		        + "    \"match\": {\n"
		        + "      \"labelError\" : " + label +  "\n"
		        + "    }\n"
		        + "  }\n"
		        + "}");
		return responseJson;
	}

	@Override
	public String findCodeByLabelMutantSyncString(Double label) throws IOException, RemoteException {
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/",
		        "{\n"
		        + "  \"query\": {\n"
		        + "    \"match\": {\n"
		        + "      \"labelMutant\" : " + label +  "\n"
		        + "    }\n"
		        + "  }\n"
		        + "}");
		return responseJson;
	}
	
	@Override
	public String findCodeByLabelErrorCredentialsSyncString(Double label,String user, String group) throws IOException,RemoteException {
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/", "{\n"
				+ "  \"query\": {\n"
				+ "    \"bool\": {\n"
				+ "      \"must\": [\n"
				+ "        { \"match\": { \"labelError\": " + label +  " } },\n"
				+ "        { \n"
				+ "            \"bool\": {\n"
				+ "                \"should\": [\n"
				+ "                    {\"match\": {\"group\" : \"ALL\"}},\n"
				+ "                    {\"bool\": {\n"
				+ "                            \"must\" : [\n"
				+ "                                { \"match\": { \"group\": \"" + group + "\" } }\n"
				+ "                            ],\n"
				+ "                            \"must_not\" : [\n"
				+ "                                { \"match\": {\"group\": \"SELF\"}}\n"
				+ "                            ]\n"
				+ "                        }\n"
				+ "                    },\n"
				+ "                    {\"match\": {\"user\":\"" + user + "\"}}\n"
				+ "                ]\n"
				+ "            }\n"
				+ "        }\n"
				+ "      ]\n"
				+ "    }\n"
				+ "  }\n"
				+ "}");
		return responseJson;
	}
	
	@Override
	public String findCodeByLabelMutantCredentialsSyncString(Double label,String user, String group) throws IOException,RemoteException {
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/", "{\n"
				+ "  \"query\": {\n"
				+ "    \"bool\": {\n"
				+ "      \"must\": [\n"
				+ "        { \"match\": { \"labelMutant\": " + label +  " } },\n"
				+ "        { \n"
				+ "            \"bool\": {\n"
				+ "                \"should\": [\n"
				+ "                    {\"match\": {\"group\" : \"ALL\"}},\n"
				+ "                    {\"bool\": {\n"
				+ "                            \"must\" : [\n"
				+ "                                { \"match\": { \"group\": \"" + group + "\" } }\n"
				+ "                            ],\n"
				+ "                            \"must_not\" : [\n"
				+ "                                { \"match\": {\"group\": \"SELF\"}}\n"
				+ "                            ]\n"
				+ "                        }\n"
				+ "                    },\n"
				+ "                    {\"match\": {\"user\":\"" + user + "\"}}\n"
				+ "                ]\n"
				+ "            }\n"
				+ "        }\n"
				+ "      ]\n"
				+ "    }\n"
				+ "  }\n"
				+ "}");
		return responseJson;
	}

	@Override
	public QueryResultDTO findCodeByUserSync(String user) throws IOException, RemoteException {
		QueryResult tmpRes = new QueryResult();
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/",
		        "{\n"
		        + "  \"query\": {\n"
		        + "    \"match_phrase\": {\n"
		        + "      \"user\" : \"" + user +  "\"\n"
		        + "    }\n"
		        + "  }\n"
		        + "}");
		tmpRes.readJson(responseJson);
		return tmpRes.toDTO();
	}

	@Override
	public QueryResultDTO findCodeByGroupSync(String group) throws IOException, RemoteException {
		QueryResult tmpRes = new QueryResult();
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/",
		        "{\n"
		        + "  \"query\": {\n"
		        + "    \"match_phrase\": {\n"
		        + "      \"group\" : \"" + group +  "\"\n"
		        + "    }\n"
		        + "  }\n"
		        + "}");
		tmpRes.readJson(responseJson);
		return tmpRes.toDTO();
	}

	@Override
	public QueryResultDTO findCodeByLabelErrorSync(Double label) throws IOException, RemoteException {
		QueryResult tmpRes = new QueryResult();
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/",
		        "{\n"
		        + "  \"query\": {\n"
		        + "    \"match_phrase\": {\n"
		        + "      \"labelError\" : \"" + label +  "\"\n"
		        + "    }\n"
		        + "  }\n"
		        + "}");
		tmpRes.readJson(responseJson);
		return tmpRes.toDTO();
	}

	@Override
	public QueryResultDTO findCodeByLabelMutantSync(Double label) throws IOException, RemoteException {
		QueryResult tmpRes = new QueryResult();
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/",
		        "{\n"
		        + "  \"query\": {\n"
		        + "    \"match_phrase\": {\n"
		        + "      \"label\" : \"" + label +  "\"\n"
		        + "    }\n"
		        + "  }\n"
		        + "}");
		tmpRes.readJson(responseJson);
		return tmpRes.toDTO();
	}
	
	@Override
	public QueryResultDTO findCodeByLabelErrorCredentialsSync(Double label,String user, String group) throws IOException,RemoteException {
		QueryResult tmpRes = new QueryResult();
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/", "{\n"
				+ "  \"query\": {\n"
				+ "    \"bool\": {\n"
				+ "      \"must\": [\n"
				+ "        { \"match\": { \"labelError\": " + label +  " } },\n"
				+ "        { \n"
				+ "            \"bool\": {\n"
				+ "                \"should\": [\n"
				+ "                    {\"match\": {\"group\" : \"ALL\"}},\n"
				+ "                    {\"bool\": {\n"
				+ "                            \"must\" : [\n"
				+ "                                { \"match\": { \"group\": \"" + group + "\" } }\n"
				+ "                            ],\n"
				+ "                            \"must_not\" : [\n"
				+ "                                { \"match\": {\"group\": \"SELF\"}}\n"
				+ "                            ]\n"
				+ "                        }\n"
				+ "                    },\n"
				+ "                    {\"match\": {\"user\":\"" + user + "\"}}\n"
				+ "                ]\n"
				+ "            }\n"
				+ "        }\n"
				+ "      ]\n"
				+ "    }\n"
				+ "  }\n"
				+ "}");
		tmpRes.readJson(responseJson);
		return tmpRes.toDTO();
	}
	
	@Override
	public QueryResultDTO findCodeByLabelMutantCredentialsSync(Double label,String user, String group) throws IOException,RemoteException {
		QueryResult tmpRes = new QueryResult();
		String responseJson = ElasticRequestHandler.PostRequest("/primarydirect/", "{\n"
				+ "  \"query\": {\n"
				+ "    \"bool\": {\n"
				+ "      \"must\": [\n"
				+ "        { \"match\": { \"labelMutant\": " + label +  " } },\n"
				+ "        { \n"
				+ "            \"bool\": {\n"
				+ "                \"should\": [\n"
				+ "                    {\"match\": {\"group\" : \"ALL\"}},\n"
				+ "                    {\"bool\": {\n"
				+ "                            \"must\" : [\n"
				+ "                                { \"match\": { \"group\": \"" + group + "\" } }\n"
				+ "                            ],\n"
				+ "                            \"must_not\" : [\n"
				+ "                                { \"match\": {\"group\": \"SELF\"}}\n"
				+ "                            ]\n"
				+ "                        }\n"
				+ "                    },\n"
				+ "                    {\"match\": {\"user\":\"" + user + "\"}}\n"
				+ "                ]\n"
				+ "            }\n"
				+ "        }\n"
				+ "      ]\n"
				+ "    }\n"
				+ "  }\n"
				+ "}");
		tmpRes.readJson(responseJson);
		return tmpRes.toDTO();
	}


}
