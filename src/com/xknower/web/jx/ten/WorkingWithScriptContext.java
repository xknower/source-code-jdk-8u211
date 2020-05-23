package com.xknower.web.jx.ten;

/**
 * When web page is loaded, Chromium engine creates JavaScript Context instance.
 * This instance represents context in which all JavaScript code is executed.
 * The JavaScript Context instance is created when web page is loaded completely,
 * but JavaScript code on it hasn't been executed yet.
 * To get notifications about JavaScript Context creation use the ScriptContextListener.onScriptContextCreated(ScriptContextEvent event) event.
 * <p>
 * When web page is unloaded because of loading another web page or reloading existing one,
 * Chromium engine destroys JavaScript Context and disposes all JavaScript objects running in scope of this context.
 * To get notifications about JavaScript Context disposal use the ScriptContextListener.onScriptContextDestroyed(ScriptContextEvent event) event.
 * <p>
 * You might want to execute some JavaScript code before any other JavaScript on the loaded web page is executed.
 * For example, to inject your own JavaScript objects/functions/properties.
 * JxBrowser API provides functionality that allows receiving notifications before JavaScript code is executed on the loaded web page.
 * To get such notification you must register ScriptContextListener and override its onScriptContextCreated(ScriptContextEvent event) method.
 * Inside this method you can execute your JavaScript code.
 * It will be executed before any other JavaScript on the loaded web page is executed.
 * For example:
 */
public class WorkingWithScriptContext {
    public static void main(String[] args) {
//        browser.addScriptContextListener(new ScriptContextAdapter() {
//            @Override
//            public void onScriptContextCreated(ScriptContextEvent event) {
//                Browser browser = event.getBrowser();
//                // Access and modify document.title property before any other
//                // JavaScript on the loaded web page has been executed.
//                JSValue document = browser.executeJavaScriptAndReturnValue("document");
//                document.asObject().setProperty("title", "My title");
//            }
//        });
    }
}
