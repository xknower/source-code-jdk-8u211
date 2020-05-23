package com.xknower.web.jx.seventeen;

/**
 * Each web page loaded in the browser has its own document object.
 * To access document object of loaded web page use the Browser.getDocument() method.
 * This method returns DOMDocument instance that you can use to access document's properties and its child nodes. For example:
 * <p>
 * DOMDocument document = browser.getDocument();
 * If web page contains frames (see IFRAME or FRAME), then each frame has its own document object.
 * To access document object of a specified frame use the Browser.getDocument(long frameId) method.
 * The frameId you can obtain from the Browser.getFramesIds() method. For example:
 * <p>
 * for (Long frameId : browser.getFramesIds()) {
 * DOMDocument frameDocument = browser.getDocument(frameId);
 * }
 * Having DOMDocument instance you can work with the document object and its DOM structure.
 * <p>
 * If web page isn't completely loaded or it doesn't have document, then the Browser.getDocument() method returns null.
 * So, before you invoke this method, make sure that web page is loaded completely.
 */
public class WorkingWithDocument {
}
