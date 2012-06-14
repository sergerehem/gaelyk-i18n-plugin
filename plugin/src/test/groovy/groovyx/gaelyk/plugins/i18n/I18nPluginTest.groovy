package groovyx.gaelyk.plugins.i18n

import java.io.FileNotFoundException
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.*

class I18nPluginTest {
    I18nPlugin plugin
    
    @Before
    void setUp() {
        plugin = new I18nPlugin()
    }
    
    @Test
    void pluginVarsShouldHaveDefaultValues() {
        plugin.loadConfigVars()
        assert plugin.basePath == "WEB-INF/i18n"
        assert plugin.baseName == "messages"
        assert plugin.defaultLocale == ""
        assertFalse plugin.useBrowserLocale
        assertTrue plugin.useMemcache
    }

    @Test
    void pluginVarsShouldHaveDefaultValuesWhenConfigFileIsEmpty() {
        plugin.loadConfigVars("src/test/groovy/resources/i18nConfigEmpty.groovy")
        assert plugin.basePath == "WEB-INF/i18n"
        assert plugin.baseName == "messages"
        assert plugin.defaultLocale == ""
        assertFalse plugin.useBrowserLocale 
        assertTrue plugin.useMemcache
    }
    
    @Test
    void pluginVarsShouldHaveI18nConfigFileValues() {
        plugin.loadConfigVars("src/test/groovy/resources/i18nConfig.groovy")
        assert plugin.basePath == "WEB-INF/i18n_test"
        assert plugin.baseName == "messages_test"
        assert plugin.defaultLocale == "en_US"
        assertTrue plugin.useBrowserLocale 
        assertFalse plugin.useMemcache
    }
    
    
    @Test
    void existingBundleFileShouldBeLoaded() {
        assertNotNull plugin.loadBundle("src/test/groovy/resources/i18n/messages.properties")
    }  
    
    @Test(expected=FileNotFoundException.class)
    void existingBundleFileShuldThrowException() {
        plugin.loadBundle("src/test/groovy/resources/i18n/messages_invalid.properties")
    }          
}

