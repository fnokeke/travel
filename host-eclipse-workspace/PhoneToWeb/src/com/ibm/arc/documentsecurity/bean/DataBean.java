package com.ibm.arc.documentsecurity.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataBean {
	
	private String data1;
	private double data2;
	private DataEmbedMe data_embed;
	
	private String username;
	private String passwd;
	private String dbname;

	// username
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	//passwd
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	// dbname
	public String getDB() {
		return dbname;
	}
	public void setDB(String dbname) {
		this.dbname = dbname;
	}
	
	
	//
	//  data1, data2
 	//
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public double getData2() {
		return data2;
	}
	public void setData2(double data2) {
		this.data2 = data2;
	}
	public DataEmbedMe getData_embed() {
		return data_embed;
	}
	public void setData_embed(DataEmbedMe data_embed) {
		this.data_embed = data_embed;
	}
	
	
	
	

}
