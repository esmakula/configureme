package org.configureme.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.configureme.Configuration;

public class ConfigurationImpl implements Configuration{
	private String name;
	private Map<String,String> attributes;
	
	public ConfigurationImpl(String aName){
		name = aName;
		attributes = new ConcurrentHashMap<String, String>();
	}
	
	@Override
	public String getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setAttribute(String attributeName, String attributeValue){
		attributes.put(attributeName, attributeValue);
	}
	
	public String toString(){
		return getName()+": "+attributes;
	}
	

}