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
    }

    @Test
    void pluginVarsShouldHaveDefaultValuesWhenConfigFileIsEmpty() {
        plugin.loadConfigVars("src/test/resources/i18nConfigEmpty.groovy")
        assert plugin.basePath == "WEB-INF/i18n"
        assert plugin.baseName == "messages"
    }

    @Test
    void pluginVarsShouldHaveI18nConfigFileValues() {
        plugin.loadConfigVars("src/test/resources/i18nConfig.groovy")
        assert plugin.basePath == "WEB-INF/i18n_test"
        assert plugin.baseName == "messages_test"
        assert plugin.messagesEncoding == "UTF-8"
    }


    @Test
    void noLocale() {
        plugin.setBasePath("src/test/resources/i18n")
        plugin.setBaseName("messages")
        assertNotNull plugin.loadBundle(null)
    }

    @Test
    void fullLocale() {
        plugin.setBasePath("src/test/resources/i18n")
        plugin.setBaseName("messages")
        def bundle = plugin.loadBundle("ru_RU")
        assertNotNull bundle
        assertNotNull bundle.getProperty("entry0")
        assertEquals("число", bundle.getProperty("entry0"))
    }

    @Test
    void partiallyLocale() {
        plugin.setBasePath("src/test/resources/i18n")
        plugin.setBaseName("messages")
        def bundle = plugin.loadBundle("he")
        assertNotNull bundle
        assertNotNull bundle.getProperty("entry0")

        bundle = plugin.loadBundle("he_IL")
        assertNotNull bundle
        assertNotNull bundle.getProperty("entry0")
    }
}

