package com.jack.entity;

/**
 * 管理员实体类  
 * @author Administrator
 */
public class Manager {
	private int id;
	@Override
	public String toString() {
		return "Manager [id=" + id + ", lname=" + lname + ", pwd=" + pwd
				+ ", mobile=" + mobile + ", email=" + email + ", status="
				+ status + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	private String lname;
	private String pwd;
	private String mobile;
	private String email;
	private int status; //状态：0表示正常，-1表示锁定
	
	
	
}
