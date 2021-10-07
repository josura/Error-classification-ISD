package com.entonomachia.domains;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class Code {
	@NotBlank(message="user must be specified(not printable because user is passed by default)")
	 private String user;
	 @NotBlank(message="group must be specified(not printable because group is passed by default)")
	 private String group;
	 // (\/\/|\/\*|\*\/|@)
	 @NotBlank(message="code must not be empty")
	 @Pattern(regexp="^((?!\\/\\/|\\/\\*|\\*\\/|@).)*$",
	 message="Code must not contain comments")
	 private String code;
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
	 
}
