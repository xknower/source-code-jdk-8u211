package com.xknower.web.jx.four;

import com.teamdev.jxbrowser.chromium.*;

import java.util.List;

/**
 * JxBrowser supports web camera and microphone.
 * By default, first webcam and microphone in the list of available video/audio capture devices are used as default.
 * To get information about available media stream devices such as webcam or microphone, use the MediaStreamDeviceManager API.
 */
public class WebCameraMicrophone {


    public static void main(String[] args) {
        Browser browser = new Browser();
        // Audio & Video Capture Devices 音频和视频捕获设备
        // The following sample demonstrates how to get list of available audio and video capture devices:
        final MediaStreamDeviceManager deviceManager = browser.getMediaStreamDeviceManager();
        // Get list of all available audio capture devices (microphones).
        List<MediaStreamDevice> audioCaptureDevices =
                deviceManager.getMediaStreamDevices(MediaStreamType.AUDIO_CAPTURE);
        // Get list of all available video capture devices (webcams).
        List<MediaStreamDevice> videoCaptureDevices =
                deviceManager.getMediaStreamDevices(MediaStreamType.VIDEO_CAPTURE);

        // Default Audio & Video Capture Devices
        // By default, first webcam and microphone in the list of available video/audio capture devices are used as default.
        // To change this default behavior register your own MediaStreamDeviceProvider implementation that configures default device.
        // In your own implementation you let end user to select
        // which devices should be used by default if there are more than one device is available.
        // Register own provider to provide Chromium with default device.
        deviceManager.setMediaStreamDeviceProvider(new MediaStreamDeviceProvider() {
            @Override
            public void onRequestDefaultDevice(MediaStreamDeviceRequest request) {
                // Set first available device as default.
                List<MediaStreamDevice> availableDevices = request.getMediaStreamDevices();
                if (!availableDevices.isEmpty()) {
                    MediaStreamDevice defaultDevice = availableDevices.get(0);
                    request.setDefaultMediaStreamDevice(defaultDevice);
                }
            }
        });
    }
}
