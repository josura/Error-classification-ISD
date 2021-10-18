package com.entonomachia.domains;

public class ImproveModelData {
	private String ids="default";
	private String error="";
	private String source="";
	private String solution;
	private String CodeWithNoComments;
	private String SolutionWithNoComments;
	private String user;
	private String group;
	private String code;
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getCodeWithNoComments() {
		return CodeWithNoComments;
	}
	public void setCodeWithNoComments(String codeWithNoComments) {
		CodeWithNoComments = codeWithNoComments;
	}
	public String getSolutionWithNoComments() {
		return SolutionWithNoComments;
	}
	public void setSolutionWithNoComments(String solutionWithNoComments) {
		SolutionWithNoComments = solutionWithNoComments;
	}
}
