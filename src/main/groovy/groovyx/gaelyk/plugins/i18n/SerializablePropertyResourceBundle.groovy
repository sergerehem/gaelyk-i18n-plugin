package groovyx.gaelyk.plugins.i18n

import java.text.MessageFormat
import java.util.List

/**
 * The SerializablePropertyResourceBundle directly extends Properties 
 * because the classic java.util.PropertyResourceBundle isn't serializable itself.
 * It is used by i18nPlugin to store message.properties files in memcache, 
 * for better performance, when user decides to use the current browser locale.
 * 
 * This code was adapted from http://www.thatsjava.com/java-programming/133561/
 *
 * @author Serge Rehem
 */
class SerializablePropertyResourceBundle extends Properties implements Serializable {

	/**
     * This constructor receives the classic PropertyResourceBundle and store
     * all Properties calling setProperty() method.
     */
	public SerializablePropertyResourceBundle(PropertyResourceBundle rb) {
		String key;
		for (Enumeration e = rb.getKeys(); e.hasMoreElements() ;) {
			key = (String)e.nextElement();
			setProperty(key, rb.getString(key));
		}
	}

	@Override
	public String getString(String str) { 
		return getProperty(str);
	}

	@Override
	public String getProperty(String str) {
		super.getProperty(str)
	}

	/**
	 * Shortcut to get a message.properties key using 
     * $i18n.entryName notation
     */
    def propertyMissing(String name) { 
		this.getString(name) 
	}
	
	/**
	 * Shortcut to get a message.properties key using 
     * ${i18n.entryName(arg1, arg2, ..., argN)} notation
     */
    def methodMissing(String name, args) {
		MessageFormat.format(this.getString(name), args)
    }
}

