package org.configureme.repository;

import org.configureme.Environment;
import org.configureme.GlobalEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The internal representation of an attribute.
 * @author lrosenberg
 */
public class Attribute {
	/**
	 * The logger.
	 */
	private static Logger log = LoggerFactory.getLogger(Attribute.class);
	/**
	 * The name of the attribute.
	 */
	private String name;

	/**
	 * The container for attribute values.
	 */
	private AttributeValue attributeValue;

	/**
	 * Creates a new attribute.
	 * @param aName attribute name
	 */
	public Attribute(String aName){
		name = aName;
		attributeValue = new AttributeValue();
	}

	/**
	 * Return the value of the attribute in the global environment.
	 * @return the value of the attribute in the global environment
	 */
	public Value getValue(){
		return getValue(GlobalEnvironment.INSTANCE);
	}

	/**
	 * Returns the value of the attribute in the given environment.
	 * @param in the environment to look up
	 * @return the value of the attribute in the given environment
	 */
	public Value getValue(Environment in){
		log.debug("looking up value for "+name+" in "+in);
		return attributeValue.get(in);
	}

	/**
	 * Returns the name of the AttributeValue.
	 * @return the name of the AttributeValue
	 */
	public String getName(){
		return name;
	}

	/**
	 * Adds a value for a given environment.
	 * @param value the value to add
	 * @param in the environment in which the value applies
	 */
	public void addValue(Value value, Environment in){
		attributeValue.set(value, in);
	}

	@Override public String toString(){
		return getName()+"="+attributeValue;
	}
}
