Gaelyk i18n Plugin
==================

The plugin implements an easy way to work with Java [Properties](http://docs.oracle.com/javase/tutorial/essential/environment/properties.html)  files in your [Gaelyk](gaelyk.appspot.com) application.
It's also very useful to work with [internationalization](http://www.oracle.com/technetwork/java/javase/tech/intl-139810.html).
The plugin was successfuly tested with Gaelyk version 1.2.

## Installation

To use the plugin just donwload the [gaelyk-i18n-plugin-0.1.jar](http://cloud.github.com/downloads/sergerehem/gaelyk-i18n-plugin/gaelyk-i18n-plugin-0.1.jar) 
and put it in your `WEB-INF/lib/` folder in your Gaelyk application extract the distribution into your project directory.

Run your app using  `gradlew gaeRun` and you should see lines like that in the console:

```
14/06/2012 13:23:44 gaelyk.plugins.I18nPlugin
INFO: Gaelyk i18n Plugin Registered!
14/06/2012 13:23:44 gaelyk.plugins.I18nPlugin
INFO: Using Config file: true. Use Browser locale: true. Use Memcache: true
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

You can copy samples files to your Gaelyk app from [here](https://github.com/sergerehem/gaelyk-i18n-plugin/tree/master/usage/WEB-INF).

### Configuration

The plugin uses the convention over configuration concept, so you can just put your `messages.properties` 
file in the `WEB-INF/i18n` folder

You can also create a `i18nConfig.groovy` file in `WEB-INF/` folder and configure those optional variables:
* `basePath`: the base path for your `.properties` files. Default is `"WEB-INF/i18n"`.
* `baseName`: the base name for your `.properties` files. Default is `"messages"`.
* `defaultLocale`: the default [locale](http://java.sun.com/developer/technicalArticles/J2SE/locale/). Default is `"en_US"`.
* `useBrowserLocale`: set true when you want to internationalize you app using current browser locale. Defaut is `false`.
* `useMemcache`: use this to store your .properties files in [GAE Memcache](https://developers.google.com/appengine/docs/java/memcache/overview), avoiding disk reading in each request. Works only when `useBrowserLocale` is true. Default is `true`.

TIP: During development time, work with `useMemcache=false`, so you can se any changes without reestart your app. Remember put `useMemcache=true` (or just remove this entry) before deploy to Google App Engine.