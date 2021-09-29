package com.entonomachia.unmarshalling;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hits {
	@SerializedName("total")
	@Expose
	private Total total;
	@SerializedName("max_score")
	@Expose
	private Double maxScore;
	@SerializedName("hits")
	@Expose
	private List<Hit> hits = null;

	public Total getTotal() {
	return total;
	}

	public void setTotal(Total total) {
	this.total = total;
	}

	public Double getMaxScore() {
	return maxScore;
	}

	public void setMaxScore(Double maxScore) {
	this.maxScore = maxScore;
	}

	public List<Hit> getHits() {
	return hits;
	}

	public void setHits(List<Hit> hits) {
	this.hits = hits;
	}

}
