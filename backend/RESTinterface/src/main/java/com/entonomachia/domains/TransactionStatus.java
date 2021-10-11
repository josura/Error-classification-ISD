package com.entonomachia.domains;

import java.io.Serializable;

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
	
	private String transactionId;
	private Status status;
	private String result;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
