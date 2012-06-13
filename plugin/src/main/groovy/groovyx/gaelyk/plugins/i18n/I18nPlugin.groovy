package groovyx.gaelyk.plugins.i18n

import groovyx.gaelyk.plugins.PluginBaseScript
import java.io.FileNotFoundException

class I18nPlugin extends PluginBaseScript {
    static String basePath = "WEB-INF/i18n"
    static String baseName = "messages"
    static String defaultLocale = "en_US"

    String configFileName

    I18nPlugin() {
        this('WEB-INF/i18nConfig.groovy')
    }

    I18nPlugin(String i18nConfigFileName) {
        def config = null
        try {
            config = new ConfigSlurper().parse(new File(i18nConfigFileName).toURL())
            basePath = config.i18nBasePath
            baseName = config.i18nBaseName
            defaultLocale = config.i18nDefaultLocale
            configFileName = i18nConfigFileName
        } catch (FileNotFoundException e) {
        }
    }

    def run() {
        log.info "Gaelyk i18n Plugin Registered! Config file: $configFileName"

        String bundleFileName = "$basePath/${baseName}_${defaultLocale}.properties"

        // Instantiate your resource bundle for default locale here
        binding {
                i18n = new SerializablePropertyResourceBundle(new PropertyResourceBundle(
                        new FileReader(bundleFileName)))
        }

        // Use this section if you want to use the browser locale

        /*
        before {
                bundleName = "${binding.BUNDLE_NAME}_${request.locale}"
                if  (request.locale in memcache) {
                    binding.i18n = memcache[request.locale]
                } else {
                    bundle = new SerializablePropertyResourceBundle(new PropertyResourceBundle(
				        new FileReader("${binding.I18N_FILES_PATH}/${bundleName}.properties")))
                    binding.memcache.put(request.locale, bundle)
                    binding.i18n = bundle
                }
        }   
        */
    }
}
