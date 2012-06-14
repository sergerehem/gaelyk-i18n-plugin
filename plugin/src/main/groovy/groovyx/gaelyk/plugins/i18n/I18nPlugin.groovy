package groovyx.gaelyk.plugins.i18n

import groovyx.gaelyk.plugins.PluginBaseScript
import java.io.FileNotFoundException

class I18nPlugin extends PluginBaseScript {
    final static String CONFIG_FILE = "WEB-INF/i18nConfig.groovy"
    final static String DEFAULT_BASE_PATH = "WEB-INF/i18n"
    final static String DEFAULT_BASE_NAME = "messages"
    final static String DEFAULT_EXT = ".properties"
    final static String DEFAULT_LOCALE = ""
    final static boolean DEFAULT_USE_BROWSER_LOCALE = false
    final static boolean DEFAULT_USE_MEMCACHE = true
    
    String basePath = I18nPlugin.DEFAULT_BASE_PATH
    String baseName = I18nPlugin.DEFAULT_BASE_NAME
    String defaultLocale = I18nPlugin.DEFAULT_LOCALE
    boolean useBrowserLocale = I18nPlugin.DEFAULT_USE_BROWSER_LOCALE
    boolean useMemcache = I18nPlugin.DEFAULT_USE_MEMCACHE
    
    boolean loadConfigVars(String configFileName = CONFIG_FILE) {
        try {
            config = new ConfigSlurper().parse(new File(configFileName).toURL())
            if (config.containsKey('basePath')) {
                basePath = config.basePath
            }
            if (config.containsKey('baseName')) {
                baseName = config.baseName
            }
            if (config.containsKey('defaultLocale')) {
                defaultLocale = config.defaultLocale
            }
            if (config.containsKey('useBrowserLocale')) {
                useBrowserLocale = config.useBrowserLocale
            }
            if (config.containsKey('useMemcache')) {
                useMemcache = config.useMemcache
            }            
            return true
        } catch(FileNotFoundException e) {
            println "CONFIG NOT FOUND"
        }
        return false
    }
    
    def loadBundle(String bundleFileName) {
        new SerializablePropertyResourceBundle(
            new PropertyResourceBundle(
                new FileReader(bundleFileName)))
    }
    
    def run() {
        boolean configFileFound = loadConfigVars()
        
        log.info "Gaelyk i18n Plugin Registered!"
        log.info "Using Config file: $configFileFound. Use Browser locale: $useBrowserLocale. Use Memcache: $useMemcache"

        def underscore = defaultLocale ? "_" : ""
        String bundleFileName = "$basePath/${baseName}${underscore}${defaultLocale}${DEFAULT_EXT}"

        binding {
                i18n = loadBundle(bundleFileName)
        }

        before {
            if(this.useBrowserLocale) {
                bundleFileName = "${this.basePath}/${this.baseName}_${request.locale}${DEFAULT_EXT}"
                if (this.useMemcache) {
                    if  (request.locale in this.memcache) {
                        binding.i18n = this.memcache[request.locale]
                    } else {
                        bundle = loadBundle(bundleFileName)
                        this.memcache.put(request.locale, bundle)
                        binding.i18n = bundle
                    }                
                } else {
                    binding.i18n = loadBundle(bundleFileName)                
                }
            }
        }   

    }
}
