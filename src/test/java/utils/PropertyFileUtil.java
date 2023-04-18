package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtil {
	private static final Properties generalProperty = new Properties();
	private static PropertyFileUtil propertyFileUtilInstance;
	
	private PropertyFileUtil() {
		
	}
	
	public static PropertyFileUtil getPropertyFileUtilInstance() {
		if (propertyFileUtilInstance == null) {
			propertyFileUtilInstance = new PropertyFileUtil();
		}
		return propertyFileUtilInstance;
	}
	
	public synchronized void loadPropertiesFromFile(String propertyFileName) {
		String propertyFilePath = System.getProperty("user.dir") + File.separator + propertyFileName + ".properties";
		File f = new File(propertyFilePath);
		try {
			FileInputStream inputStream = new FileInputStream(f);
			generalProperty.clear();
			generalProperty.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public synchronized String getValueFromProperty(String propertyName) {
		return generalProperty.getProperty(propertyName);
	}
	
	public synchronized void setValueToProperty(String propertyName, String propertyValue) {
		generalProperty.setProperty(propertyName, propertyValue);
	}
	
	public synchronized void writePropertiesToPropertyFile(String propertyFileName) {
		String propertyFilePath = System.getProperty("user.dir") + File.separator + propertyFileName + ".properties";
		File f = new File(propertyFilePath);
		try {
			FileOutputStream outputStream = new FileOutputStream(f);
			generalProperty.store(outputStream, "write configuration to a property file");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
