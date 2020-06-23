package com.xknower.chromium.net;

import com.teamdev.jxbrowser.chromium.Browser;
import com.xknower.chromium.enums.MineType;
import com.xknower.chromium.spider.FileDataUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MimeTypeProcessor {

    private Map<String, String> fileNames = new HashMap<>();

    /**
     *
     */
    public void processorOnDataReceived(
            final Browser browser, final long requestId, final String url, final String method,
            final String mineType, final String charset, final byte[] data) {

        if (mineType == null || "".equalsIgnoreCase(mineType.trim())) {
            return;
        }

        MineType mine;
        List<MineType> mineTypeList = MineType.matchTypSubtype(mineType);
        if (mineTypeList.size() == 0) {
            // 类型未定义
            System.out.println("Mine Is Not Define : " + mineType);
            return;
        } else if (mineTypeList.size() == 1) {
            // 精确匹配到一个类型
            mine = mineTypeList.get(0);
        } else {
            // 匹配到多个
            System.out.println("Mine Match To multiple: " + mineType);
            mine = mineTypeList.get(0);
        }

        List<MineType> filter = new ArrayList<>();
        //
        filter.add(MineType.TextHtmlHtml);
        filter.add(MineType.TextCssCss);
        filter.add(MineType.TextJavascriptJs);
        filter.add(MineType.ApplicationJavascriptJs);
        filter.add(MineType.ApplicationXjavascriptJs);
        filter.add(MineType.ApplicationJsonJson);
        filter.add(MineType.TextJsonJson);
        filter.add(MineType.TextPlainTxt);
        //
        filter.add(MineType.ImageXiconIco);
        filter.add(MineType.ImageSvgxmlSvg);
        filter.add(MineType.ImageXmsbmpBmp);
        filter.add(MineType.ImageGifGif);
        filter.add(MineType.ImageJpegJpg);
        filter.add(MineType.ImagePngPng);
        filter.add(MineType.ImageWebpWebp);
        filter.add(MineType.ImageJpgJpg);
        //
        filter.add(MineType.VideoMpeg4Mpeg4);
        filter.add(MineType.VideoMp4Mp4);
        filter.add(MineType.VideoXflvFlv);
        filter.add(MineType.AudioMpegMp3);
        filter.add(MineType.ApplicationXshockwaveflashSwf);
        //
        filter.add(MineType.FontWoff2);
        filter.add(MineType.ApplicationOctetstreamBin);
        filter.add(MineType.TextOctetstreamStream);
        filter.add(MineType.ApplicationXdownloadDownload);
        filter.add(MineType.ApplicationXmpegurlMpegurl);
        if (filter.contains(mine)) {
            return;
        }

        //
        String filename = System.currentTimeMillis() + "-" + mine.valueType() + "-" + mine.valueSubtype() + "." + mine.valueExtension();
        //
        String key = requestId + "_" + url;
        if (fileNames.get(key) == null) {
            fileNames.put(key, filename);
        } else {
            filename = fileNames.get(key);
        }
        //

        writeData(filename, data);
        //
        switch (mine) {
            default:
                break;
        }
        writeData(filename, data);
    }

    // https://blog.csdn.net/u014744118/article/details/72779931
    private void writeData(String pathFilename, byte[] data) {
        if (!pathFilename.contains("\\") || !pathFilename.contains("//")) {
            if (!pathFilename.contains(":")) {
                pathFilename = "D:\\tmpdata\\" + pathFilename;
            }
        }
        //
        FileDataUtils.writeData(pathFilename, data);
    }

}
