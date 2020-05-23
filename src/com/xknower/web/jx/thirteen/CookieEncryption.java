package com.xknower.web.jx.thirteen;

/**
 * JxBrowser does not encrypt cookies by default. To enable cookies encryption,
 * use the --enable-cookie-encryption Chromium switcher (see the Chromium Switches article to find out how to use Chromium switches).
 * <p>
 * JxBrowser uses Chromium cookies encryption routines, so it uses the same way to store cookies as Chromium.
 * <p>
 * On Linux to encrypt cookies JxBrowser uses GNOME Keyring or KWallet. Although Chromium chooses which store to use automatically,
 * the store to use can also be specified with a command line argument:
 * <p>
 * --password-store=gnome (to use GNOME Keyring)
 * --password-store=kwallet (to use KWallet)
 * <p>
 * <p>
 * On Windows to encrypt cookies JxBrowser uses only DPAPI. There are no alternatives at the moment.
 * <p>
 * On macOS, JxBrowser uses the private key stored with the Keychain Application to encrypt cookies with AES encryption.
 */
public class CookieEncryption {
}
