package com.entonomachia.unmarshalling;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Total {
	@SerializedName("value")
	@Expose
	private Integer value;
	@SerializedName("relation")
	@Expose
	private String relation;

	public Integer getValue() {
	return value;
	}

	public void setValue(Integer value) {
	this.value = value;
	}

	public String getRelation() {
	return relation;
	}

	public void setRelation(String relation) {
	this.relation = relation;
	}

}
