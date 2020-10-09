package com.xknower.web.jx.seventeen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;

import java.util.List;

/**
 * JxBrowser DOM API provides functionality that can be used for finding HTML elements on loaded web page by different conditions.
 * The following sample code demonstrates how to find all HTML elements by specified tag name:
 */
public class FindingElements {
    public static void main(String[] args) {
        Browser browser = new Browser();
        DOMDocument document = browser.getDocument();
        // <span class="fr-marker" data-id="0" data-type="false" style="display: none; line-height: 0;"></span><span class="fr-marker" data-id="0" data-type="true" style="display: none; line-height: 0;"></span>me("div")
        List<DOMElement> divs = document.findElements(By.tagName(""));

        // If you need to find only first HTML element in the document use the following approach:
        DOMElement div = document.findElement(By.id("myId"));

        // In general you can search for HTML elements using different conditions:
        DOMElement element = document.findElement(By.id("myId"));
        DOMElement element1 = document.findElement(By.tagName("div"));
        DOMElement element2 = document.findElement(By.className("myClass"));
        DOMElement element3 = document.findElement(By.name("myName"));
        DOMElement element4 = document.findElement(By.xpath("//textarea"));
        DOMElement element5 = document.findElement(By.cssSelector("#root"));
    }
}
