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
    String messagesEncoding


    boolean loadConfigVars(String configFileName = CONFIG_FILE) {
        try {
            config = new ConfigSlurper().parse(new File(configFileName).toURL())
            if (config.containsKey('basePath')) {
                basePath = config.basePath
            }
            if (config.containsKey('baseName')) {
                baseName = config.baseName
            }

            if (config.containsKey('messagesEncoding')) {
                messagesEncoding = config.messagesEncoding
            }

            return true
        } catch (FileNotFoundException e) {
            println "CONFIG NOT FOUND"
        }
        return false
    }

    def loadBundle(String locale) {
        def path = new File("$basePath/${baseName}${DEFAULT_EXT}")

        if (locale != null) {
            path = new File("$basePath/${baseName}_$locale${DEFAULT_EXT}")
            if (!path.exists()) {
                if (locale.contains("_")) {
                    def langLocale = locale.substring(0, locale.indexOf("_"))
                    path = new File("$basePath/${baseName}_$langLocale${DEFAULT_EXT}")
                    if (!path.exists()) {
                        path = new File("$basePath/${baseName}${DEFAULT_EXT}")
                    }
                }
                else {
                    path = new File("$basePath/${baseName}${DEFAULT_EXT}")
                }
            }
        }

        Reader reader
        if (messagesEncoding) {
            reader = new InputStreamReader(new FileInputStream(path), messagesEncoding)
        }
        else {
            reader = new FileReader(path)
        }
        return new SerializablePropertyResourceBundle(new PropertyResourceBundle(reader))
    }

    def run() {
        boolean configFileFound = loadConfigVars()

        log.info "Gaelyk i18n Plugin Registered!"
        log.info "Using Config file: $configFileFound. "


        binding {

        }

        before {

            def locale = request.getSession().getAttribute("locale")
            if (locale == null) {
                locale = request.locale.toString()
            }

            if (locale in this.memcache) {
                binding.i18n = this.memcache[locale]
            } else {
                bundle = loadBundle(locale)
                this.memcache.put(locale, bundle)
                binding.i18n = bundle
            }

        }

    }

}
