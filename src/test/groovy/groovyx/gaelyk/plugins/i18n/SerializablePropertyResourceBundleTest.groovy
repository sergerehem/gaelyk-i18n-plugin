package groovyx.gaelyk.plugins.i18n

import java.io.FileNotFoundException
import org.junit.Before
import org.junit.Test

class SerializablePropertyResourceBundleTest {
    Properties i18n;

    @Before
    void setUp() {
        i18n = new SerializablePropertyResourceBundle(
            new PropertyResourceBundle(
                new FileReader("src/test/groovy/resources/i18n/messages.properties")))
    }
    
    @Test
    void entriesWithNoParamShouldBeCorrect() {
        assert i18n.getProperty("entry0") == "value 0"
        assert i18n.entry1 == "value"
        assert i18n['entry2.value1'] == "entry 2 value 1"
        assert i18n.'entry2.value2' == "entry 2 value 2"
    }

    @Test
    void entriesWithParamsShouldBeCorrect() {
        assert i18n.'entry3.with.1param'("PARAM 1") == "entry PARAM 1"
        assert i18n.'entry3.with.3param'("P1","2","PARAM3") == "entry P1 with params 2 and PARAM3"
    }

    @Test
    void invalidEntryShouldBeNull() {
        assert i18n.invalidEntry == null
    }

    @Test(expected = FileNotFoundException.class)
    void invalidPropertiesFileShoudFail() {
        def i18nInvalid = new SerializablePropertyResourceBundle(
            new PropertyResourceBundle(
                new FileReader("src/test/groovy/resources/i18n/messages_XX_YY.properties")))
    }   
}

