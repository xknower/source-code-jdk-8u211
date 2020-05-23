package com.xknower.web.jx.four;

/**
 * Google Chrome and Chromium have several differences. For example, they support different set of audio and video codecs:
 * <p>
 * Google Chrome
 * AAC, H.264, MP3, MP4, Opus, Theora, Vorbis, VP8, VP9, and WAV
 * <p>
 * Chromium
 * Opus, Theora, Vorbis, VP8, VP9, WAV, and MP3 (since 6.23)
 * <p>
 * As you may see Google Chrome supports H.264 and MP4 codecs when Chromium doesn't.
 * The reason is that these codecs are proprietary and cannot be used in the open-source and commercial projects by default.
 * To get the rights to use the codecs, companies must pay a royalty fee to the patent holders.
 * Different codecs have different patent holders. For example, in order to use H.264 codec,
 * companies must acquire the licence from MPEG-LA company. You can read more about their license terms on the MPEG-LA's website.
 * <p>
 * Enabling proprietary codecs
 * Patent holders don't licence codecs to the software that represent only a part of the final product deployed
 * to the end users (e.g. libraries, such as JxBrowser).
 * In order to support H.264, MP4 and other proprietary codecs in your products, you need to acquire an appropriate licence.
 * <p>
 * If you need to play MP4 or H.264 formats on the web pages loaded in JxBrowser, you need to perform the following actions:
 * Contact the patent holder (e.g. MPEG-LA) and obtain licence to use the proprietary codecs you need.
 * Contact our support team and request a custom build of JxBrowser with enabled proprietary codecs.
 * If you are not yet a customer of JxBrowser, please write to sales@teamdev.com.
 * <p>
 * With the licence and custom JxBrowser build, you will be able to load web pages with the MP4 or H.264 formats
 * and play audio and video files, just like in Google Chrome.
 * <p>
 * Netflix
 * JxBrowser does not support Widevine at the moment. Web services that use Widevine to distribute content,
 * such as Netflix, Amazon Prime, etc., do not work in JxBrowser/Chromium.
 */
public class MP3MP4H264 {
}
