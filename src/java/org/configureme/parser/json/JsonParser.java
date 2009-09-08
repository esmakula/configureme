package org.configureme.parser.json;

import java.util.ArrayList;
import java.util.List;

import org.configureme.Environment;
import org.configureme.environments.DynamicEnvironment;
import org.configureme.parser.ConfigurationParser;
import org.configureme.parser.ConfigurationParserException;
import org.configureme.parser.ParsedConfiguration;
import org.configureme.parser.ParsedAttribute;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ConfigurationParser implementation for JSON.  
 * @author lrosenberg
 */
public class JsonParser implements ConfigurationParser {

	@Override
	public ParsedConfiguration parseConfiguration(String name, String content) throws ConfigurationParserException {
		
		try {
			JSONObject j = new JSONObject(content);
			ParsedConfiguration pa = new ParsedConfiguration(name);
			
			DynamicEnvironment env = new DynamicEnvironment();
			
			String[] names = JSONObject.getNames(j);
			if(names != null)
				for (String key : names) {
					List<ParsedAttribute> attList = parse(key, j, env);
					if (attList!=null){
						for (ParsedAttribute att : attList){
							pa.addAttribute(att);
						}
					}
				}
			
			return pa;
			
		} catch (JSONException e) {
			throw new ConfigurationParserException("JSON Error", e); 
		}
	}
	
	private List<ParsedAttribute> parse(String key, JSONObject root, DynamicEnvironment environment) throws JSONException{
		List<ParsedAttribute> ret = new ArrayList<ParsedAttribute>();
		
		Object value = root.get(key);
		// an object value means a change in environment, let's see what it is
		if (value instanceof JSONObject){
			environment.extendThis(key);
			JSONObject inc = (JSONObject) value;
			String[] names = JSONObject.getNames(inc);
			if(names != null)
				for (String subKey : names) {
					List<ParsedAttribute> subAttributes = parse(subKey, inc, environment);
					if (subAttributes!=null)
						ret.addAll(subAttributes);
				}
			environment.reduceThis();
		}else{
			ParsedAttribute at = new ParsedAttribute();
			at.setName(key);
			at.setValue(root.getString(key));//.getString(key));
			at.setEnvironment((Environment)environment.clone());
			ret.add(at);
		}
		return ret;
	}
	
}
