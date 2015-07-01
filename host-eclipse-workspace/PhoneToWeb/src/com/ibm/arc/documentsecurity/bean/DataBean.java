package com.ibm.arc.documentsecurity.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.ibm.arc.documentsecurity.db2.ConnectDB;
import com.ibm.json.java.JSONObject;

@XmlRootElement
public class DataBean {

	// private String data1;
	// private double data2;

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

	public JSONObject getQuery() {
		JSONObject result = null;
		try {
			ConnectDB handler = new ConnectDB();
			result = handler.run_query(this.query);
			handler.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}

}
