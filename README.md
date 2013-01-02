Gaelyk i18n Plugin
==================

The plugin implements an easy way to work with Java [Properties](http://docs.oracle.com/javase/tutorial/essential/environment/properties.html)  files in your [Gaelyk](gaelyk.appspot.com) application.
It's also very useful to work with [internationalization](http://www.oracle.com/technetwork/java/javase/tech/intl-139810.html).
The plugin was successfuly tested with Gaelyk version 1.2.

## Installation

To use the plugin just donwload the [gaelyk-i18n-plugin-0.2.jar](https://github.com/sergerehem/gaelyk-i18n-plugin/blob/master/downloads/gaelyk-i18n-plugin-0.2.jar?raw=true) 
and put it in your `WEB-INF/lib/` folder in your Gaelyk application extract the distribution into your project directory.



```
14/06/2012 13:23:44 gaelyk.plugins.I18nPlugin
INFO: Gaelyk i18n Plugin Registered!
14/06/2012 13:23:44 gaelyk.plugins.I18nPlugin
INFO: Using Config file: true.
```

## Usage

After installing the plugin you will be able to use the `i18n` global variable inside your templates (.gtpl) ou Groovlets (.groovy)
to obtain entry values in your `.properties` files, in various forms. Example:

`message.properties`
```
title="Page Title"
plugin="Gaelyk i18n Plugin"
url="http://github.com/sergerehem/gaelyk-i18n-plugin"
congrats="Congratulations {0}! Your plugin is running!"
```  

`sample.gtpl`
```html
 <html>
   <head><title>$i18.title</title>
   <body>
     <h1>$i18n['plugin']</h1>
     <a href="${i18n.getProperty('url')}"/>
     <p>${i18.congrats('Serge'}</p>
   </bodu>
</html>
```

You can change locale manually using session "locale' attribute

If session does not contain locale attribute, the plugin always using the request locale

The plugin searches properties file in the standard i18n order - if the locale is "he_IL" it will first look for "messages_he_IL.properties", if it does not exists will search for "messages_he.properties" and then for "messages.properties"

You can copy samples files to your Gaelyk app from [here](https://github.com/sergerehem/gaelyk-i18n-plugin/tree/master/usage/WEB-INF).

### Configuration

The plugin uses the convention over configuration concept, so you can just put your `messages.properties` 
file in the `WEB-INF/i18n` folder

You can also create a `i18nConfig.groovy` file in `WEB-INF/` folder and configure those optional variables:
* `basePath`: the base path for your `.properties` files. Default is `"WEB-INF/i18n"`.
* `baseName`: the base name for your `.properties` files. Default is `"messages"`.
* `messagesEncoding`: by default is used the server operationg system encoding

TIP: After version upgrade, clean your memcache using app engine console
