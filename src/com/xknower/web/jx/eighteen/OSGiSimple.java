package com.xknower.web.jx.eighteen;

/**
 * JxBrowser can be used in OSGi environment. There are 4 popular OSGi specification implementations:
 * // Felix
 * // Eclipse
 * // Knopflerfish
 * // ProSyst
 * <p>
 * JxBrowser supports the following implementations:
 * // Felix
 * // Eclipse
 * <p>
 * Configuring license
 * Each JxBrowser JAR file represents an OSGi bundle. You need to include the required JxBrowser OSGi bundles (JAR files)
 * into your OSGi application. The license.jar file that contains JxBrowser license isn't an OSGi bundle,
 * so we recommend that you extract the teamdev.licenses file from the license.
 * jar archive and put it into the META-INF folder of the jxbrowser.jar file, so that license checker could find it.
 */
public class OSGiSimple {
}
