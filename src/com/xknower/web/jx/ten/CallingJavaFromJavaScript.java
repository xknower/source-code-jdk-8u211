package com.xknower.web.jx.ten;

/**
 * JavaScript Java Bridge API allows injecting Java objects into JavaScript code.
 * This is a powerful feature that allows accessing Java functionality from JavaScript.
 * <p>
 * Note: To access JavaScript and inject Java object, make sure that web page is loaded completely and JavaScript support is enabled.
 * If you inject/register Java object and then load web page, all registered Java objects will be destroyed
 * and will not be available in JavaScript of the loaded web page.
 * <p>
 * Injecting Java Objects
 * To inject Java object into JavaScript you must associate the Java object with specified property of a JavaScript object.
 * For example, you can add a new java property to global window JavaScript object (other objects would work as well) and associate
 * this property with Java object using the following code:
 *
 * JSValue window = browser.executeJavaScriptAndReturnValue("window");
 * window.asObject().setProperty("java", new JavaObject());
 */

/**
 * Note: To inject Java object into JavaScript code, we recommend that you use the following approach:
 * browser.addScriptContextListener(new ScriptContextAdapter() {
 *
 * @Override public void onScriptContextCreated(ScriptContextEvent <span class="fr-marker" data-id="0" data-type="false" style="display: none; line-height: 0;"></span><span class="fr-marker" data-id="0" data-type="true" style="display: none; line-height: 0;"></span>event) {
 * Browser browser = event.getBrowser();
 * JSValue window = browser.executeJavaScriptAndReturnValue("window");
 * window.asObject().setProperty("java", new JavaObject());
 * }
 * });
 * <p>
 * Implementation of JavaObject can be the following:
 * <p>
 * public static class JavaObject {
 * public void print(String message) {
 * System.out.println(message);
 * }
 * }
 */

/**
 * Note: Make sure that you inject Java object which class/interface is marked as public.
 * Only public classes can be registered and accessed from JavaScript.
 *
 * Now JavaScript code can invoke JavaObject public methods:
 *
 * window.java.print('Hello Java!');
 * Note: JavaScript code can access/invoke only public methods of registered Java object.
 */

/**
 * Getting Java Object from JSValue
 * You can inject Java object into JavaScript by setting it as a property value for existing JavaScript object:
 *
 * JSValue window = browser.executeJavaScriptAndReturnValue("window");
 * window.asObject().setProperty("java", new JavaObject());
 * If you read the property value in Java code, then you can extract Java object associated with this JavaScript value:
 *
 * JSValue value = window.asObject().getProperty("java");
 * if (value.isJavaObject()) {
 *     JavaObject object = (JavaObject) value.asJavaObject();
 * }
 *
 * Example: Accessing File System from JavaScript
 * You can provide JavaScript with access to local file system by injecting java.io.File object:
 *
 * JSValue window = browser.executeJavaScriptAndReturnValue("window");
 * window.asObject().setProperty("fileSystem", new File("/"));
 * Now JavaScript can work with the java.io.File instance directly:
 *
 * window.fileSystem.listFiles()[0].getAbsolutePath();
 */

/**
 * How JavaScript calls Java method
 * When JavaScript calls a public method of a registered Java object, JavaScript parameters are automatically converted
 * to corresponding Java objects/primitive values by JxBrowser.
 *
 * If JxBrowser cannot convert passed JavaScript values or find a method with appropriate signature, JavaScript error will be thrown.
 *
 * If registered Java object has several methods with same name and number of parameters, but different parameter types,
 * JavaScript will invoke the first method declared in Java class. For example, if you register the following Java object:
 *
 * public class JavaObject {
 *     public void doAction(int arg1, double arg2) {}
 *     public void doAction(double arg1, int arg2) {}
 *     public void doAction(double arg1, double arg2) {}
 * }
 */

/**
 * JavaScript to Java types conversion
 * JavaScript and Java work with different primitive types. JxBrowser implements automatic types conversion from JavaScript to Java types.
 * Here's how JavaScript objects will be converted to their Java equivalents by JxBrowser:
 *
 * JavaScript	            Java
 * Number	                double
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
 * Boolean	                boolean
 * java.lang.Boolean
 * String	                java.lang.String
 * null	                    null
 * Object	                com.teamdev.jxbrowser.chromium.JSObject
 * Array	                com.teamdev.jxbrowser.chromium.JSArray
 * Function	                com.teamdev.jxbrowser.chromium.JSFunction
 */
public class CallingJavaFromJavaScript {
}
