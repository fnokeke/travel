package com.ibm.almaden.security.compliance;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
	
	public final String database_password 	= "database.password";
	public final String database_login 		= "database.login";
	public final String database_host 		= "database.host";
	
	private Properties properties = new Properties();
	
	/** 
	 * Constructor
	 * 
	 * @return
	 */
	public ConfigurationReader() {
	}
	
	/** 
	 * Constructor
	 * 
	 * @return
	 */
	public ConfigurationReader(String propertyFileName) {
		try {
			properties = getProperties(propertyFileName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	/**
	 * Returns the value of the given property
	 * @param property
	 * @return
	 */
	public String getValue(String property) {
		return properties.getProperty(property);
	}
	
	
	/**
	 * Read properties from configuration file
	 * @param propertyFileName
	 * @return
	 * @throws IOException
	 */
	public Properties getProperties(String propertyFileName) 
			throws IOException {
		InputStream inputStream = 
				this.getClass().getClassLoader().getResourceAsStream(propertyFileName);
		if(inputStream == null) { 
			System.err.println();
			return null;
		}
		properties.load(inputStream);

		return properties;
	}
	
	
	public static void main(String[] args) {
		ConfigurationReader configReader = 
				new ConfigurationReader();
		try {
			Properties props = configReader.getProperties("config.properties");
			System.out.println(props);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}



}
