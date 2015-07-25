package com.ibm.arc.documentsecurity.resources;

import com.ibm.json.java.JSONObject;

public class Utility {
	public static Boolean isDebugMode = true;

	/*
	 * convert key,value pair to json object output
	 */
	public static JSONObject toJSON(String key, String value) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(key, value);

		return jsonObject;
	}
	
	public static JSONObject toJSON(String key, Integer value) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(key, value);

		return jsonObject;
	}

	/*
	 * convert HTTP errorcode and errormsg to key,value pair for json object
	 */
	public static JSONObject getErrorInJSON(Integer errorCode) {
		assert errorCode != null : "Error code passed is null";

		JSONObject jsonError = new JSONObject();
		if (errorCode != null) {

			if (errorCode == 404) {
				jsonError.put("404", "Could not get result");
			}

		}

		return jsonError;
	}

}
