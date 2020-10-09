package com.xknower.web.jx.twenty;

/**
 * By default spell checker is enable and configured to use English (en-US) language.
 * Chromium engine checks text in all text fields and text areas on the loaded web page and highlights all misspelled words.
 * <p>
 * JxBrowser provides API that allows enabling/disabling spell checker and configuring it to use specified language.
 * The following code demonstrates how to enable spell checker functionality and configure it to use German (Standard) language (de):
 * <p>
 * Java
 * Browser browser = new Browser();
 * SpellCheckerService spellChecker = browser.getContext().getSpellCheckerService();
 * spellChecker.setEnabled(true);
 * spellChecker.setLanguage("de");
 * <p>
 * <p>
 * Dictionaries
 * Chromium supports both custom dictionary and dictionaries for different languages.
 * It downloads required dictionary for the current language automatically.
 * You can also add words to your custom dictionary which is stored in Chromium user's profile directory.
 */
public class ConfiguringSpellChecker {
}
