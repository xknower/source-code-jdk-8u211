package com.xknower.web.jx.eighteen;

/**
 * This section describes how you can deploy your Java application with JxBrowser library via Java Web Start (JWS).
 * To deploy your application via JWS, please follow the following steps:
 * <p>
 * <p>
 * 1. Pack your application classes into an application JAR file(s). For example myapp.jar.
 * <p>
 * 2. Sign all JAR files used in your application (including all JxBrowser JARs and the license JAR file) with a valid signature.
 * To sign JAR files you can use the jarsigner tool. For example.
 * <p>
 * jarsigner -keystore keystore_file -storepass storepass -keypass keypass myapp.jar alias
 * <p>
 * Note: make sure that you replace keystore_file, storepass, keypass, and alias with your own values.
 * <p>
 * Note: JxBrowser JAR files are not signed, so you need to sign them with the same signature you use
 * for signing your application JAR files.
 */
// 3. Create and configure your application JNLP file (e.g. myapp.jnlp) as described below:
// <jnlp spec="1.6+" codebase="my_url" href="myapp.jnlp">
//    <information>
//        <title>My Application</title>
//        <vendor>My Company</vendor>
//        <description>My Application Description</description>
//        <offline-allowed/>
//    </information>
//
//    <security>
//        <all-permissions/>
//    </security>
//
//    <resources>
//        <j2se version="1.6+"/>
//        <jar href="lib/myapp.jar"/>
//        <jar href="lib/jxbrowser.jar" />
//        <jar href="lib/license.jar" />
//    </resources>
//
//    <resources os="Windows">
//        <jar href="lib/jxbrowser-win32.jar" />
//        <jar href="lib/jxbrowser-win64.jar" />
//    </resources>
//
//    <resources os="Mac OS X">
//        <jar href="lib/jxbrowser-mac.jar" />
//    </resources>
//
//    <resources os="Linux" arch="x86_64 amd64">
//        <jar href="lib/jxbrowser-linux64.jar" />
//    </resources>
//</jnlp>

/**
 * Note: make sure that you replace codebase attribute value with a valid URL address of your web server where JAR files will be located.
 * <p>
 * Depending on the network speed it might take some time to download all JxBrowser JAR files.
 * To speed up start up time of your Java Web Start application you might want to use the download="lazy" attribute of the jar element.
 * <p>
 * 4. Upload all signed JAR files and the myapp.jnlp file to a web-server.
 * <p>
 * Java 9 and higher
 * On Java 9, Java Web start sets the --illegal-access=deny VM parameter by default.
 * However, in order to work correctly, your Java Web start application should be run with the  --illegal-access=permit VM parameter.
 * Otherwise you will be getting the IllegalAccessError. This is a known limitation,
 * as JxBrowser support Java 9 with some limitations (more  details  here ).
 * <p>
 * Unfortunately, Java Web Start does not support the  --illegal-access=permit VM parameter.
 * JNLP applications that need to break encapsulation should use the precise options, like  --add-exports and  --add-opens.
 * <p>
 * The solution is adding the following line to your JNLP file:
 * <p>
 * java-vm-args="--add-opens=java.base/java.util=ALL-UNNAMED--add-exports=java.desktop/sun.awt=ALL-UNNA
 */
public class JavaWebStartSimple {
}
