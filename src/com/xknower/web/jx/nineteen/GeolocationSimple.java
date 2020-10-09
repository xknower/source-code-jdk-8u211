package com.xknower.web.jx.nineteen;

/**
 * Geolocation is one of those Chromium features that uses the Google API.
 * You must enable the Google Maps Geolocation API and billing, otherwise geolocation will not work.
 * <p>
 * Once you enable Google Maps Geolocation API and billing,
 * you can provide the keys to the Chromium engine using one of the approaches described in the Chromium API Keys article.
 * <p>
 * Note: due to the security restrictions in Chromium 50 and higher, geolocation doesn't work for file:/// URI.
 * You must put a web page that works with the Geolocation API on a web server and access the web page through HTTPS protocol.
 * <p>
 * Geolocation Permission
 * Please take into account that there is a specific permission that should be granted to enable geolocation in JxBrowser.
 * <p>
 * The following sample code demonstrates how to enable this permission:
 */
public class GeolocationSimple {
    public static void main(String[] args) {
//        browser.setPermissionHandler(new PermissionHandler() {
//            @Override
//            public PermissionStatus onRequestPermission(PermissionRequest request) {
//                if (request.getPermissionType() == PermissionType.GEOLOCATION) {
//                    return PermissionStatus.GRANTED;
//                }
//                return PermissionStatus.DENIED;
//            }
//        });
    }
}
