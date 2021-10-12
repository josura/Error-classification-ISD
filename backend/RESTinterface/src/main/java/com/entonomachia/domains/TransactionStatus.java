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
	private String result;
	
	
	
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "{\n" + 
				"\t\"id\":\"" + id + "\",\n"+ 
				"\t\"status\":\"" + status + "\",\n"+ 
				"\t\"result\":\"" + result + "\"" + "\n}";
	}
	
}
