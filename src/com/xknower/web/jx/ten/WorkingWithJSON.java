package com.xknower.web.jx.ten;

/**
 * JxBrowser JavaScript Java Bridge API allows passing a string that represents JSON from Java side to JavaScript.
 * On JavaScript side this JSON string will be parsed and converted to appropriate JavaScript object/objects.
 * The following simple example demonstrates how to associate JSON with specific property of window JavaScript object:
 * <p>
 * Java
 * JSValue window = browser.executeJavaScriptAndReturnValue("window");
 * window.asObject().setProperty("myObject", new JSONString("[123, \"Hello\"]"));
 * In the code above the "[123, \"Hello\"]" JSON string is associated with the window.myObject property.
 * This JSON string will be converted to appropriate JavaScript object. In this case it will be converted to Array.
 * The following JavaScript code shows how to work with the window.myObject property:
 * <p>
 * Js
 * <script>
 * var length = window.myObject.length;
 * var numberValue = window.myObject[0];
 * var stringValue = window.myObject[1];
 * </script>
 * If you set JSONString with string value that doesn't represent JSON, you will get JavaScript error.
 */
public class WorkingWithJSON {
}
