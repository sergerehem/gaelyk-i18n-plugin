package groovyx.gaelyk.plugins.i18n

import org.junit.Test

class I18nPluginTest {

    @Test
    void staticVariablesShouldHaveDefaultValues() {
        I18nPlugin plugin = new I18nPlugin();

        assert plugin.basePath == "WEB-INF/i18n"
        assert plugin.baseName == "messages"
        assert plugin.defaultLocale == "en_US"
    }

    @Test
    void staticVariablesShouldHaveI18nConfigValues() {
        I18nPlugin plugin = new I18nPlugin("src/test/groovy/resources/i18nConfig.groovy");

        assert plugin.basePath == "WEB-INF/i18n_test"
        assert plugin.baseName == "messages_test"
        assert plugin.defaultLocale == "pt_BR"
    }
}

