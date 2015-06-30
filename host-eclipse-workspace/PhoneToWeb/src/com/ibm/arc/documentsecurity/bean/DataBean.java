package com.ibm.arc.documentsecurity.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataBean {
	
	//private String data1;
	//private double data2;
	
	private DataEmbedMe data_embed;
	private String username = "";
	private String passwd = "";
	private String dbname = "";
	private String query = "";
	
	//
	// SETTER
	//
	public void setValues(String username, String passwd, String dbname) {
		this.username = username;
		this.passwd = passwd;
		this.dbname = dbname;
	}
	
	public void setData_embed(DataEmbedMe data_embed) {
		this.data_embed = data_embed;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	//
	// GETTERS
	//
	public String getUsername() {
		return username;
	}
	
	public DataEmbedMe getData_embed() {
		return data_embed;
	}
	
	public String getPasswd() {
		return passwd;
	}

	public String getDB() {
		return dbname;
	}

	public String getQuery() {
		return "awesome result coming soon for " + query;
	}

}
