package com.xknower.web.jx.ten;

/**
 * JxBrowser API provides functionality that allows accessing and executing JavaScript code on the loaded web page.
 * <p>
 * Note: To access JavaScript make sure that web page is loaded completely and JavaScript support is enabled.
 * <p>
 * JxBrowser provides two ways for JavaScript code execution:
 * <p>
 * Executing JavaScript
 * To execute JavaScript code asynchronously, without blocking current thread execution until the code is executed and ignore return value,
 * use the Browser.executeJavaScript(String javaScript) method. This method tells Chromium engine to execute the given JavaScript code asynchronously. The return value of JavaScript code execution is ignored.
 * <p>
 * The following code updates document.title property with "My title" value:
 * browser.executeJavaScript("document.title = 'My title';");
 * <p>
 * Executing JavaScript and return value
 * To execute JavaScript code and return value use the Browser.executeJavaScriptAndReturnValue(String javaScript) method.
 * This method blocks current thread execution and waits until code is executed. The result of execution is stored in JSValue object.
 * <p>
 * <p>
 * The following code updates document.title property with "My title" value and returns JSValue object with string value
 * that represents document.title value:
 * JSValue title = browser.executeJavaScriptAndReturnValue(
 * "document.title = 'My title'; document.title");
 * System.out.println("title = " + title.getStringValue());
 * <p>
 * Accessing JavaScript objects
 * You can access JavaScript objects on the loaded web page using the Browser.executeJavaScriptAndReturnValue(String javaScript) method.
 * If return value represents JavaScript object, then JSValue will contain JSObject instance
 * that represents Java wrapper for JavaScript object.
 * JSObject class provides functionality that allows working with JavaScript object properties and calling its functions.
 * <p>
 * Getting properties
 * To access JavaScript object property use the JSObject.getProperty(String name) method. The following code demonstrates
 * how to get value of the document.title property:
 * <p>
 * JSValue document = browser.executeJavaScriptAndReturnValue("document");
 * JSValue titleValue = document.asObject().getProperty("title");
 * String title = titleValue.getStringValue();
 * <p>
 * Setting properties
 * To modify JavaScript object property with specified value use the JSObject.setProperty(String name, Object value) method.
 * The following code demonstrates how to modify document.title property with "My title" value:
 * JSValue document = browser.executeJavaScriptAndReturnValue("document");
 * document.asObject().setProperty("title", "My title");
 * <p>
 * Working with functions
 * JavaScript object property can represent a function (JSFunction). You can invoke JavaScript object function using the following approach:
 * JSValue document = browser.executeJavaScriptAndReturnValue("document");
 * JSValue write = document.asObject().getProperty("write");
 * write.asFunction().invoke(document.asObject(), "<html><body>Hello</body></html>");
 * Since JxBrowser 6.9, you can also invoke JavaScript function asynchronously and access the result of the invocation
 * through the Future<JSValue> object:
 * <p>
 * JSValue document = browser.executeJavaScriptAndReturnValue("document");
 * JSValue async = document.asObject().getProperty("asyncFunc");
 * Future<JSValue> asyncResult =
 * async.asFunction().invokeAsync(document.asObject(), "Hello World Async!");
 * String result = asyncResult.get().asString().getStringValue();
 * <p>
 * Working with arrays
 * JSValue can represent an Array (JSArray). You can access elements of the array using the following approach:
 * JSValue array = browser.executeJavaScriptAndReturnValue("['John', 'Doe', 46];");
 * JSValue john = array.asArray().get(0);
 * JSValue doe = array.asArray().get(1);
 */

/**
 * Java to JavaScript types conversion
 * JavaScript and Java work with different primitive types. JxBrowser implements automatic types conversion from Java to JavaScript types.
 * Here's how Java objects will be converted to their JavaScript equivalents by JxBrowser:
 * <p>
 * Java	                        JavaScript
 * double                       Number
 * float
 * long
 * int
 * short
 * byte
 * java.lang.Double
 * java.lang.Float
 * java.lang.Long
 * java.lang.Integer
 * java.lang.Short
 * java.lang.Byte
 * boolean                           Boolean
 * java.lang.Boolean
 * java.lang.String	                String
 * null	                            null
 * com.teamdev.jxbrowser.chromium.JSObject	    Object
 * com.teamdev.jxbrowser.chromium.JSArray	    Array
 * com.teamdev.jxbrowser.chromium.JSFunction	Function
 */
public class CallingJavaScriptFromJava {
    public static void main(String[] args) {
    }
}
