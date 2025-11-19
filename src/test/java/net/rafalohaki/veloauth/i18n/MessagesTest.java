package net.rafalohaki.veloauth.i18n;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link Messages} covering language selection, fallbacks and formatting.
 */
class MessagesTest {

    private Messages messages;

    @BeforeEach
    void setUp() {
        messages = new Messages();
    }

    @Test
    void setLanguage_nullValue_fallsBackToEnglish() {
        messages.setLanguage(null);

        assertEquals("en", messages.getCurrentLanguage());
    }

    @Test
    void setLanguage_unsupportedLanguage_fallsBackToEnglish() {
        messages.setLanguage("de");

        assertEquals("en", messages.getCurrentLanguage());
    }

    @Test
    void setLanguage_supportedLanguage_loadsMessages() {
        messages.setLanguage("pl");

        assertEquals("pl", messages.getCurrentLanguage());
        assertEquals("Gracz {} nie istnieje w bazie danych", messages.get("player.not.found"));
    }

    @Test
    void get_missingKey_returnsKeyItself() {
        String key = "non.existing.key";

        String result = messages.get(key);

        assertEquals(key, result);
    }

    @Test
    void get_withArguments_formatsMessage() {
        messages.setLanguage("en");

        String message = messages.get("security.brute_force.blocked", 5);

        assertEquals("Too many login attempts! Blocked for 5 minutes.", message);
    }

    @Test
    void isLanguageSupported_recognizesSupportedLanguages() {
        assertTrue(messages.isLanguageSupported("en"));
        assertTrue(messages.isLanguageSupported("pl"));
        assertFalse(messages.isLanguageSupported("de"));
    }

    @Test
    void getSupportedLanguages_returnsConsistentList() {
        assertArrayEquals(new String[]{"en", "pl"}, messages.getSupportedLanguages());
    }
}
