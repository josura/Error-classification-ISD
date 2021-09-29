package com.entonomachia.unmarshalling;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source {
	@SerializedName("ids")
	@Expose
	private Integer ids;
	@SerializedName("code")
	@Expose
	private String code;
	@SerializedName("solution")
	@Expose
	private String solution;
	@SerializedName("source")
	@Expose
	private String source;
	@SerializedName("user")
	@Expose
	private String user;
	@SerializedName("group")
	@Expose
	private String group;
	@SerializedName("labelError")
	@Expose
	private Double labelError;
	@SerializedName("probabilityErrorString")
	@Expose
	private String probabilityErrorString;
	@SerializedName("rawPredictionErrorString")
	@Expose
	private String rawPredictionErrorString;
	@SerializedName("labelMutation")
	@Expose
	private Double labelMutation;
	@SerializedName("probabilityMutationString")
	@Expose
	private String probabilityMutationString;
	@SerializedName("rawPredictionMutationString")
	@Expose
	private String rawPredictionMutationString;
	@SerializedName("featureString")
	@Expose
	private String featureString;

	public Integer getIds() {
	return ids;
	}

	public void setIds(Integer ids) {
	this.ids = ids;
	}

	public String getCode() {
	return code;
	}

	public void setCode(String code) {
	this.code = code;
	}

	public String getSolution() {
	return solution;
	}

	public void setSolution(String solution) {
	this.solution = solution;
	}

	public String getSource() {
	return source;
	}

	public void setSource(String source) {
	this.source = source;
	}

	public String getUser() {
	return user;
	}

	public void setUser(String user) {
	this.user = user;
	}

	public String getGroup() {
	return group;
	}

	public void setGroup(String group) {
	this.group = group;
	}

	public Double getLabelError() {
	return labelError;
	}

	public void setLabelError(Double labelError) {
	this.labelError = labelError;
	}

	public String getProbabilityErrorString() {
	return probabilityErrorString;
	}

	public void setProbabilityErrorString(String probabilityErrorString) {
	this.probabilityErrorString = probabilityErrorString;
	}

	public String getRawPredictionErrorString() {
	return rawPredictionErrorString;
	}

	public void setRawPredictionErrorString(String rawPredictionErrorString) {
	this.rawPredictionErrorString = rawPredictionErrorString;
	}

	public Double getLabelMutation() {
	return labelMutation;
	}

	public void setLabelMutation(Double labelMutation) {
	this.labelMutation = labelMutation;
	}

	public String getProbabilityMutationString() {
	return probabilityMutationString;
	}

	public void setProbabilityMutationString(String probabilityMutationString) {
	this.probabilityMutationString = probabilityMutationString;
	}

	public String getRawPredictionMutationString() {
	return rawPredictionMutationString;
	}

	public void setRawPredictionMutationString(String rawPredictionMutationString) {
	this.rawPredictionMutationString = rawPredictionMutationString;
	}

	public String getFeatureString() {
	return featureString;
	}

	public void setFeatureString(String featureString) {
	this.featureString = featureString;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
