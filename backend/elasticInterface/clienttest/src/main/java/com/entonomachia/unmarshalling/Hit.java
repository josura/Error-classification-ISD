package com.entonomachia.unmarshalling;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hit {
	@SerializedName("_index")
	@Expose
	private String index;
	//TODO Additional filtering on the type returned, like classified for the first data from the classifier
	@SerializedName("_type")
	@Expose
	private String type;
	@SerializedName("_id")
	@Expose
	private String id;
	@SerializedName("_score")
	@Expose
	private Double score;
	@SerializedName("_source")
	@Expose
	private Source source;

	public String getIndex() {
	return index;
	}

	public void setIndex(String index) {
	this.index = index;
	}

	public String getType() {
	return type;
	}

	public void setType(String type) {
	this.type = type;
	}

	public String getId() {
	return id;
	}

	public void setId(String id) {
	this.id = id;
	}

	public Double getScore() {
	return score;
	}

	public void setScore(Double score) {
	this.score = score;
	}

	public Source getSource() {
	return source;
	}

	public void setSource(Source source) {
	this.source = source;
	}
}
