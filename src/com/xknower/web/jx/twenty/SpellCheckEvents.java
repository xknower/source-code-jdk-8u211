package com.xknower.web.jx.twenty;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * When a text field or text area on the loaded web page receives focus,
 * Chromium’s spell checker functionality automatically checks the text and highlights misspelled words.
 * Using the SpellCheckListener you can obtain the information about misspelled words as well.
 * <p>
 * The following example demonstrates how to get notifications about spell check results:
 */
public class SpellCheckEvents {
    public static void main(String[] args) throws Exception {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        SpellCheckerService spellCheckerService =
                browser.getContext().getSpellCheckerService();
        spellCheckerService.addSpellCheckListener(new SpellCheckListener() {
            @Override
            public void onSpellCheckCompleted(SpellCheckCompletedParams params) {
                String text = params.getText();
                List<SpellCheckResult> checkResults = params.getResults();
                for (SpellCheckResult checkResult : checkResults) {
                    int errorStartIndex = checkResult.getStartIndex();
                    int errorLength = checkResult.getLength();
                }
            }
        });

        browser.loadHTML("<html><body><textarea rows='20' cols='30'>" +
                "Smple text with mitake.</textarea></body></html>");
    }
}
