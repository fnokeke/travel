package com.ibm.arc.documentsecurity.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataBean {
	
	private String data1;
	private double data2;
	private DataEmbedMe data_embed;
	
	
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
