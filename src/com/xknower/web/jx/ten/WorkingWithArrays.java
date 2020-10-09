package com.xknower.web.jx.ten;

/**
 * JavaScript Java Bridge API provides JSArray class that represents a wrapper for JavaScript array object.
 * If JavaScript returns an array to Java code, the array value will be represented in Java code as JSArray object.
 * For example:
 * <p>
 * Java
 * JSValue result = browser.executeJavaScriptAndReturnValue("[1, 'test', 3]");
 * JSArray array = result.asArray();
 * In the example above JavaScript code returns an array with three items (1, 'test', 3) to Java code.
 * The JSValue can be cast to JSArray type to work with JavaScript array.
 * Using JSArray class you can get length of the array, access array items, and modify JavaScript array with new items.
 * <p>
 * <p>
 * <p>
 * Please note that JSArray points to existing JavaScript array on the loaded web page.
 * If the web page was reloaded or unloaded, JSArray object will point to an invalid JavaScript array that was disposed during unloading the web page. If you try to use this JSArray instance, the IllegalStateException error will be thrown.
 * <p>
 * <p>
 * <p>
 * Getting Array Length
 * To get the array length use the JSArray.length() method. For example:
 * <p>
 * Java
 * JSValue result = browser.executeJavaScriptAndReturnValue("[1, 'test', 3]");
 * JSArray array = result.asArray();
 * assert array.length() == 3;
 * <p>
 * <p>
 * Getting Array Items
 * To access the array item at specific index use the JSArray.get(int index) method. The index cannot be negative.
 * It can be more than array length. In this case the "undefined" value will be returned. For example:
 * <p>
 * Java
 * assert array.get(0).asNumber().getInteger() == 1;
 * assert array.get(1).getStringValue().equals("test");
 * assert array.get(2).asNumber().getInteger() == 3;
 * assert array.get(100).isUndefined();
 * <p>
 * <p>
 * Setting Array Items
 * You can modify existing array items or add new items using the JSArray.set(int index, Object value) method.
 * The method returns a boolean value that indicates whether the given value was successfully inserted into the array.
 * You can insert new values at the index more than array length. In this case the size of the array will be increased,
 * the new item will be inserted at the given position,
 * all items between the latest valid array element and the new one will be filled with "undefined" values.
 * <p>
 * HTML
 * assert array.set(0, "String value");
 * assert array.set(100, 123);
 */
public class WorkingWithArrays {
}
