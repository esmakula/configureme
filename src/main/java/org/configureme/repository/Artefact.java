package org.configureme.repository;

import org.configureme.Environment;
import org.configureme.GlobalEnvironment;
import org.configureme.sources.ConfigurationSourceKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The artefact is the internal representation of a configuration.
 *
 * @author lrosenberg
 */
public class Artefact {
	/**
	 * The name of the configuration and therefore the name of the artefact.
	 */
	private String name;
	/**
	 * The attribute map.
	 */
	private Map<String, Attribute> attributes;
	/**
	 * Content map.
	 */
	private Map<Environment, Map<String, Object>> contentMap;

	/**
	 * External configurations that was included in current configuration
	 * this field use for reconfiguration of the current configuration
	 */
	private List<ConfigurationSourceKey> externalConfigurations;

	/**
	 * Creates a new Artefact with the given name.
	 *
	 * @param aName the name of the artefact
	 */
	Artefact(String aName) {
		name = aName;
		attributes = new ConcurrentHashMap<String, Attribute>();
		externalConfigurations = new ArrayList<ConfigurationSourceKey>();
		contentMap = new HashMap<Environment, Map<String, Object>>();
	}

	public List<ConfigurationSourceKey> getExternalConfigurations() {
		return externalConfigurations;
	}

	public void addExternalConfigurations(ConfigurationSourceKey configurationSourceKey) {
		if (configurationSourceKey == null)
			return;
		externalConfigurations.add(configurationSourceKey);
	}

	/**
	 * Returns the attribute with the given name. Throws an IllegalArgumentException if there is no such attribute.
	 *
	 * @param attributeName
	 * @return the attribute with the given name
	 */
	public Attribute getAttribute(String attributeName) {
		Attribute a = attributes.get(attributeName);
		if (a == null)
			throw new IllegalArgumentException("Attribute " + attributeName + " doesn't exists");
		return a;
	}

	/**
	 * Adds an attribute value. If the attribute doesn't exist it will be created.
	 *
	 * @param attributeName  the name of the attribute.
	 * @param attributeValue the value of the attribute in the given environment.
	 * @param in             the environment in which the attribute value applies
	 */
	public void addAttributeValue(String attributeName, Value attributeValue, Environment in) {
		if (in == null)
			in = GlobalEnvironment.INSTANCE;
		Attribute attr = attributes.get(attributeName);
		if (attr == null) {
			attr = new Attribute(attributeName);
			attributes.put(attr.getName(), attr);
		}
		attr.addValue(attributeValue, in);

		Map<String, Object> valueMap = contentMap.get(in);
		if(valueMap == null)
			valueMap = new HashMap<String, Object>();
		valueMap.put(attributeName, attributeValue.getRaw());
		contentMap.put(in, valueMap);
		//TODO check for loops and process such situation
		if (attributeValue instanceof IncludeValue)
			externalConfigurations.add(((IncludeValue) attributeValue).getConfigName());
	}

	public Map<Environment, Map<String, Object>> getContent() {
		return this.contentMap;
	}

	@Override
	public String toString() {
		return getName() + ": " + attributes;
	}

	/**
	 * Returns the name of the artefact.
	 *
	 * @return the name of the artefact
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns names of the contained attributes.
	 *
	 * @return names of the contained attributes
	 */
	public List<String> getAttributeNames() {
		ArrayList<String> names = new ArrayList<String>();
		names.addAll(attributes.keySet());
		return names;
	}
}
