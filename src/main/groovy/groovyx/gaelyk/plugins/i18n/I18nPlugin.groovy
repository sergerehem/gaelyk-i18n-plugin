package groovyx.gaelyk.plugins.i18n

import groovyx.gaelyk.plugins.PluginBaseScript

class I18nPlugin extends PluginBaseScript {

    def run() {


        log.info "Registering i18n plugin..."

        binding {
                /*
                I18N_FILES_PATH = "WEB-INF/i18n"
                BUNDLE_NAME = "messages"
                DEFAULT_LOCALE = "pt_BR"
                */
                
                // Instantiate your resource bundle for default locale here
                i18n = "i18n"
                /*
                i18n = new SerializablePropertyResourceBundle(new PropertyResourceBundle(
                        new FileReader("$I18N_FILES_PATH/${BUNDLE_NAME}_${DEFAULT_LOCALE}.properties")))
                */
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
