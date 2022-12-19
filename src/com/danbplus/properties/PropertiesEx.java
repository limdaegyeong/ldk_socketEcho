package com.danbplus.properties;

import java.util.Properties;

public class PropertiesEx {

	public static Properties main(String[] args) {
        Properties prop = new Properties();

        prop.setProperty("port", "6077");
        
        return prop;
	}

}
