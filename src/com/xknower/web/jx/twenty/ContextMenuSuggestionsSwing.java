package com.xknower.web.jx.twenty;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Note: By default, context menu is disabled.
 * <p>
 * <p>
 * <p>
 * When user right clicks highlighted misspelled word on the loaded web page, context menu with suggestions can be displayed.
 * JxBrowser provides API you can use to implement such context menu.
 * The following example demonstrates how to register custom context menu handler that:
 * <p>
 * <p>
 * <p>
 * Displays suggestions according to the currently selected language.
 * When user selects menu item with suggestion, the misspelled word is automatically replaced.
 * <p>
 * Displays Add to Dictionary menu item. When user selects the menu item, the misspelled word is added to custom dictionary.
 * The custom dictionary is stored in Chromium user's profile directory.
 */

/**
 * The sample demonstrates how to work with spellchecker API.
 */
public class ContextMenuSuggestionsSwing {
    public static void main(String[] args) throws Exception {
        // Enable heavyweight popup menu for heavyweight (default) BrowserView component.
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        BrowserContext context = browser.getContext();
        SpellCheckerService spellCheckerService = context.getSpellCheckerService();
        // Enable SpellChecker service.
        spellCheckerService.setEnabled(true);
        // Configure SpellChecker's language.
        spellCheckerService.setLanguage("en-US");

        browser.setContextMenuHandler(new MyContextMenuHandler(view, browser));
        browser.loadHTML("<html><body><textarea rows='20' cols='30'>" +
                "Smple text with mitake.</textarea></body></html>");
    }

    private static class MyContextMenuHandler implements ContextMenuHandler {

        private final JComponent component;
        private final Browser browser;

        private MyContextMenuHandler(JComponent parentComponent, Browser browser) {
            this.component = parentComponent;
            this.browser = browser;
        }

        @Override
        public void showContextMenu(final ContextMenuParams params) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JPopupMenu popupMenu = createPopupMenu(params);
                    Point location = params.getLocation();
                    popupMenu.show(component, location.x, location.y);
                }
            });
        }

        private JPopupMenu createPopupMenu(final ContextMenuParams params) {
            final JPopupMenu result = new JPopupMenu();
            // Add suggestions menu items.
            List<String> suggestions = params.getDictionarySuggestions();
            for (final String suggestion : suggestions) {
                result.add(createMenuItem(suggestion, new Runnable() {
                    @Override
                    public void run() {
                        browser.replaceMisspelledWord(suggestion);
                    }
                }));
            }
            if (!suggestions.isEmpty()) {
                // Add the "Add to Dictionary" menu item.
                result.addSeparator();
                result.add(createMenuItem("Add to Dictionary", new Runnable() {
                    @Override
                    public void run() {
                        String misspelledWord = params.getMisspelledWord();
                        browser.addWordToSpellCheckerDictionary(misspelledWord);
                    }
                }));
            }
            return result;
        }

        private static JMenuItem createMenuItem(String title, final Runnable action) {
            JMenuItem result = new JMenuItem(title);
            result.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action.run();
                }
            });
            return result;
        }
    }
}
