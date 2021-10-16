package com.entonomachia.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Transaction")
public class TransactionStatus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8084818429926080316L;
	public enum Status { 
        PENDING, ERROR, FINISHED
    }
	
	@Id private String id;
	//private Status status;
	private String status;
	private String resultError;
	private String resultMutation;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResultError() {
		return resultError;
	}
	public void setResultError(String result) {
		this.resultError = result;
	}
	
	public String getResultMutation() {
		return resultMutation;
	}
	public void setResultMutation(String result) {
		this.resultMutation = result;
	}
	
	@Override
	public String toString() {
		return "{\n" + 
				"\t\"id\":\"" + id + "\",\n"+ 
				"\t\"status\":\"" + status + "\",\n"+ 
				"\t\"resultError\":\"" + resultError + "\"" + "\n}"+ 
				"\t\"resultMutation\":\"" + resultMutation + "\"" + "\n}";
	}
	
}
