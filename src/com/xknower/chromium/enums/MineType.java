package com.xknower.chromium.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * HTTP 资源类型
 *
 * @author xknower
 */
public enum MineType {

    //类型/子类型                   扩展名
    //01 application/envoy             vy
    ApplicationEnvoyVy("application", "envoy", "vy"),
    //02 application/fractals	        fif
    ApplicationFractalsFif("application", "fractals", "fif"),
    //03 application/futuresplash	    spl
    ApplicationFuturesplashSpl("application", "futuresplash", "spl"),
    //04 application/hta	            hta
    ApplicationHtaHta("application", "hta", "hta"),
    //05 application/internet-property-stream	acx
    ApplicationInternetpropertystreamAcx("application", "internet-property-stream", "acx"),
    //06 application/mac-binhex40	    hqx
    ApplicationMacbinhex40Hqx("application", "mac-binhex40", "hqx"),
    //07 application/msword	        doc
    ApplicationMswordDoc("application", "msword", "doc"),
    //08 application/msword	        dot
    //ApplicationMswordDot("application", "msword", "dot"),
    //09 application/octet-stream	    *
    //ApplicationOctetstream("application", "octet-stream", "*"),
    //10 application/octet-stream	    bin
    ApplicationOctetstreamBin("application", "octet-stream", "bin"),
    //11 application/octet-stream	    class
    //ApplicationOctetstreamClass("application", "octet-stream", "class"),
    //12 application/octet-stream	    dms
    //ApplicationOctetstreamDms("application", "octet-stream", "dms"),
    //13 application/octet-stream	    exe
    //ApplicationOctetstreamExe("application", "octet-stream", "exe"),
    //14 application/octet-stream	    lha
    //ApplicationOctetstreamLha("application", "octet-stream", "lha"),
    //15 application/octet-stream	    lzh
    //ApplicationOctetstreamLzh("application", "octet-stream", "lzh"),
    //16 application/oda	            oda
    ApplicationOctetstreamOda("application", "oda", "oda"),
    //17 application/olescript	        axs
    ApplicationOctetstreamAxs("application", "olescript", "axs"),
    //18 application/pdf	            pdf
    ApplicationPdfPdf("application", "pdf", "pdf"),
    //19 application/pics-rules	    prf
    ApplicationPpicsrulesPrf("application", "pics-rules", "prf"),
    //20 application/pkcs10	        p10
    ApplicationPkcs10P10("application", "pkcs10", "p10"),
    //21 application/pkix-crl	        crl
    ApplicationPkixcrlCrl("application", "pkix-crl", "crl"),
    //22 application/postscript	    ai
    //ApplicationPostscriptAi("application", "postscript", "ai"),
    //23 application/postscript	    eps
    //ApplicationPostscriptEps("application", "postscript", "eps"),
    //24 application/postscript	    ps
    ApplicationPostscriptPs("application", "postscript", "ps"),
    //25 application/rtf	            rtf
    ApplicationRtfRtf("application", "rtf", "rtf"),
    //26 application/set-payment-initiation	    setpay
    ApplicationSetpaymentinitiationSetpay("application", "set-payment-initiation", "setpay"),
    //27 application/set-registration-initiation	setreg
    ApplicationSetregistrationinitiationSetreg("application", "set-registration-initiation", "setreg"),
    //28 application/vnd.ms-excel	    xla
    //ApplicationVndmsexcelXla("application", "vnd.ms-excel", "xla"),
    //29 application/vnd.ms-excel	    xlc
    //ApplicationVndmsexcelXlc("application", "vnd.ms-excel", "xlc"),
    //30 application/vnd.ms-excel	    xlm
    //ApplicationVndmsexcelXlm("application", "vnd.ms-excel", "xlm"),
    //31 application/vnd.ms-excel	    xls
    ApplicationVndmsexcelXls("application", "vnd.ms-excel", "xls"),
    //32 application/vnd.ms-excel	    xlt
    //ApplicationVndmsexcelXlt("application", "vnd.ms-excel", "xlt"),
    //33 application/vnd.ms-excel	    xlw
    //ApplicationVndmsexcelXlw("application", "vnd.ms-excel", "xlt"),
    //34 application/vnd.ms-outlook	msg
    ApplicationVndmsoutlookMsg("application", "vnd.ms-outlook", "msg"),
    //35 application/vnd.ms-pkicertstore	sst
    ApplicationVndmspkicertstoreSst("application", "vnd.ms-pkicertstore", "sst"),
    //36 application/vnd.ms-pkiseccat	    cat
    ApplicationVndmspkiseccatCat("application", "vnd.ms-pkiseccat", "cat"),
    //37 application/vnd.ms-pkistl	        stl
    ApplicationVndmspkistlStl("application", "vnd.ms-pkistl", "stl"),
    //38 application/vnd.ms-powerpoint	    pot
    //ApplicationVndmspowerpointPot("application", "vnd.ms-powerpoint", "pot"),
    //39 application/vnd.ms-powerpoint	    pps
    //ApplicationVndmspowerpointPps("application", "vnd.ms-powerpoint", "pps"),
    //40 application/vnd.ms-powerpoint	    ppt
    ApplicationVndmspowerpointPpt("application", "vnd.ms-powerpoint", "ppt"),
    //41 application/vnd.ms-project	    mpp
    ApplicationVndmsprojectMpp("application", "vnd.ms-project", "mpp"),
    //42 application/vnd.ms-works	        wcm
    //ApplicationVndmsworksWcm("application", "vnd.ms-works", "wcm"),
    //43 application/vnd.ms-works	        wdb
    //ApplicationVndmsworksWdb("application", "vnd.ms-works", "wdb"),
    //44 application/vnd.ms-works	        wks
    //ApplicationVndmsworksWks("application", "vnd.ms-works", "wks"),
    //45 application/vnd.ms-works	        wps
    ApplicationVndmsworksWps("application", "vnd.ms-works", "wps"),
    //46 application/winhlp	            hlp
    ApplicationWinhlpHlp("application", "winhlp", "hlp"),
    //47 application/x-bcpio	            bcpio
    ApplicationXbcpioBcpio("application", "x-bcpio", "bcpio"),
    //48 application/x-cdf	                cdf
    ApplicationXcdfCdf("application", "x-cdf", "cdf"),
    //49 application/x-compress	        z
    ApplicationXcompressZ("application", "x-compress", "z"),
    //50 application/x-compressed	        tgz
    ApplicationXcompressedTgz("application", "x-compressed", "tgz"),
    //52 application/x-cpio	            cpio
    ApplicationXcpioCpio("application", "x-cpio", "cpio"),
    //53 application/x-csh	                csh
    ApplicationXcshCsh("application", "x-csh", "csh"),
    //53 application/x-director	        dcr
    //ApplicationXdirectorDcr("application", "x-director", "dcr"),
    //54 application/x-director	        dir
    ApplicationXdirectorDir("application", "x-director", "dir"),
    //55 application/x-director	        dxr
    //ApplicationXdirectorDxr("application", "x-director", "dxr"),
    //56 application/x-dvi	                dvi
    ApplicationXdviDvi("application", "x-dvi", "dvi"),
    //57 application/x-gtar	            gtar
    ApplicationXgtarGtar("application", "x-gtar", "gtar"),
    //58 application/x-gzip	            gz
    ApplicationXgzipGz("application", "x-gzip", "gz"),
    //59 application/x-hdf	            hdf
    ApplicationXhdfHdf("application", "x-hdf", "hdf"),
    //60 application/x-internet-signup	ins
    ApplicationXinternetsignupIns("application", "x-internet-signup", "ins"),
    //61 application/x-internet-signup	isp
    //ApplicationXinternetsignupIsp("application", "x-internet-signup", "isp"),
    //62 application/x-iphone	        iii
    ApplicationXiphoneIii("application", "x-iphone", "iii"),
    // -> application/javascript
    ApplicationJavascriptJs("application", "javascript", "js"),
    // -> application/json
    ApplicationJsonJson("application", "json", "json"),
    // -> application/font-woff2
    ApplicationFontWoff2("application", "font-woff2", "woff2"),
    // -> application/x-mpegurl
    ApplicationXmpegurlMpegurl("application", "x-mpegurl", "mpegurl"),
    // -> application/x-download
    ApplicationXdownloadDownload("application", "x-download", "download"),
    //63 application/x-javascript	    js
    ApplicationXjavascriptJs("application", "x-javascript", "js"),
    //64 application/x-latex	        latex
    ApplicationXlatexLatex("application", "x-latex", "latex"),
    //65 application/x-msaccess	    mdb
    ApplicationXmsaccessMdb("application", "x-msaccess", "mdb"),
    //66 application/x-mscardfile	    crd
    ApplicationXmscardfileCrd("application", "x-mscardfile", "crd"),
    //67 application/x-msclip	        clp
    ApplicationXmsclipClp("application", "x-msclip", "clp"),
    //68 application/x-msdownload	    dll
    ApplicationXmsdownloadDll("application", "x-msdownload", "dll"),
    //69 application/x-msmediaview	    m13
    //ApplicationXmsmediaviewM13("application", "x-msmediaview", "m13"),
    //70 application/x-msmediaview	    m14
    //ApplicationXmsmediaviewM14("application", "x-msmediaview", "m14"),
    //71 application/x-msmediaview	    mvb
    ApplicationXmsmediaviewMvb("application", "x-msmediaview", "mvb"),
    //72 application/x-msmetafile	    wmf
    ApplicationXmsmetafileWmf("application", "x-msmetafile", "wmf"),
    //73 application/x-msmoney	        mny
    ApplicationXmsmoneyMny("application", "x-msmoney", "mny"),
    //74 application/x-mspublisher	    pub
    ApplicationXmspublisherPub("application", "x-mspublisher", "pub"),
    //75 application/x-msschedule	    scd
    ApplicationXmsscheduleScd("application", "x-msschedule", "scd"),
    //76 application/x-msterminal	    trm
    ApplicationXmsterminalTrm("application", "x-msterminal", "trm"),
    //77 application/x-mswrite	        wri
    ApplicationXmswriteWri("application", "x-mswrite", "wri"),
    //78 application/x-netcdf	        cdf
    ApplicationXnetcdfCdf("application", "x-netcdf", "cdf"),
    //79 application/x-netcdf	        nc
    //ApplicationXnetcdfNc("application", "x-netcdf", "nc"),
    //80 application/x-perfmon	        pma
    ApplicationXperfmonPma("application", "x-perfmon", "pma"),
    //81 application/x-perfmon	        pmc
    //ApplicationXperfmonPmc("application", "x-perfmon", "pmc"),
    //82 application/x-perfmon	        pml
    //ApplicationXperfmonPml("application", "x-perfmon", "pml"),
    //83 application/x-perfmon	        pmr
    //ApplicationXperfmonPmr("application", "x-perfmon", "pmr"),
    //84 application/x-perfmon	        pmw
    //ApplicationXperfmonPmw("application", "x-perfmon", "pmr"),
    //85 application/x-pkcs12	        p12
    //ApplicationXpkcs12P12("application", "x-pkcs12", "p12"),
    //86 application/x-pkcs12	        pfx
    ApplicationXpkcs12Pfx("application", "x-pkcs12", "pfx"),
    //87 application/x-pkcs7-certificates	p7b
    //ApplicationXpkcs7certificatesP7b("application", "x-pkcs7-certificates", "p7b"),
    //88 application/x-pkcs7-certificates	spc
    ApplicationXpkcs7certificatesSpc("application", "x-pkcs7-certificates", "spc"),
    //89 application/x-pkcs7-certreqresp	p7r
    ApplicationXpkcs7certreqrespP7r("application", "x-pkcs7-certreqresp", "p7r"),
    //90 application/x-pkcs7-mime	        p7c
    //ApplicationXpkcs7mimeP7c("application", "x-pkcs7-mime", "p7c"),
    //91 application/x-pkcs7-mime	        p7m
    ApplicationXpkcs7mimeP7m("application", "x-pkcs7-mime", "p7m"),
    //92 application/x-pkcs7-signature	    p7s
    ApplicationXpkcs7signatureP7s("application", "x-pkcs7-signature", "p7s"),
    //93 application/x-sh	            sh
    ApplicationXshSh("application", "x-sh", "sh"),
    //94 application/x-shar	        shar
    ApplicationXsharShar("application", "x-shar", "shar"),
    //95 application/x-shockwave-flash	swf
    ApplicationXshockwaveflashSwf("application", "x-shockwave-flash", "swf"),
    //96 application/x-stuffit	        sit
    ApplicationXstuffitSit("application", "x-stuffit", "sit"),
    //97 application/x-sv4cpio	        sv4cpio
    ApplicationXsv4cpioSv4cpio("application", "x-sv4cpio", "sv4cpio"),
    //98 application/x-sv4crc	        sv4crc
    ApplicationXsv4crcSv4crc("application", "x-sv4crc", "sv4crc"),
    //99 application/x-tar	            tar
    ApplicationXtarTar("application", "x-tar", "tar"),
    //100 application/x-tcl	            tcl
    ApplicationXtclTcl("application", "x-tcl", "tcl"),
    //101 application/x-tex	            tex
    ApplicationXtexTex("application", "x-tex", "tex"),
    //102 application/x-texinfo	        texi
    //ApplicationXtexinfoTexi("application", "x-texinfo", "texi"),
    //103 application/x-texinfo	        texinfo
    ApplicationXtexinfotexInfo("application", "x-texinfo", "texinfo"),
    //104 application/x-troff	        roff
    ApplicationXtroffTroff("application", "x-troff", "roff"),
    //105 application/x-troff	        t
    //ApplicationXtroffT("application", "x-troff", "t"),
    //106 application/x-troff	        tr
    //ApplicationXtroffTr("application", "x-troff", "tr"),
    //107 application/x-troff-man	    man
    ApplicationXtroffmanMan("application", "x-troff-man", "man"),
    //108 application/x-troff-me	    me
    ApplicationXtroffmeMe("application", "x-troff-me", "me"),
    //109 application/x-troff-ms	    ms
    ApplicationXtroffmsMs("application", "x-troff-ms", "ms"),
    //110 application/x-ustar	        ustar
    ApplicationXustarUstar("application", "x-ustar", "ustar"),
    //111 application/x-wais-source	    src
    ApplicationXwaissourceSrc("application", "x-wais-source", "src"),
    //112 application/x-x509-ca-cert	cer
    ApplicationXx509cacertCer("application", "x-x509-ca-cert", "cer"),
    //113 application/x-x509-ca-cert	crt
    //ApplicationXx509cacertCrt("application", "x-x509-ca-cert", "crt"),
    //114 application/x-x509-ca-cert	der
    //ApplicationXx509cacertDer("application", "x-x509-ca-cert", "der"),
    //115 application/ynd.ms-pkipko	    pko
    ApplicationYndmspkipkoPko("application", "ynd.ms-pkipko", "pko"),
    //116 application/zip	zip
    ApplicationZipZip("application", "zip", "zip"),
    //01 audio/basic	    au
    AudioBasicAu("audio", "basic", "au"),
    //02 audio/basic	    snd
    //AudioBasicSnd("audio", "basic", "snd"),
    //03 audio/mid	        mid
    AudioMidMid("audio", "mid", "mid"),
    //04 audio/mid	        rmi
    //AudioMidRmi("audio", "mid", "rmi"),
    //05 audio/mpeg	    mp3
    AudioMpegMp3("audio", "mpeg", "mp3"),
    //06 audio/x-aiff	    aif
    AudioXaiffAif("audio", "x-aiff", "aif"),
    //07 audio/x-aiff	    aifc
    //AudioXaiffAifc("audio", "x-aiff", "aifc"),
    //08 audio/x-aiff	    aiff
    //AudioXaiffAiff("audio", "x-aiff", "aiff"),
    //09 audio/x-mpegurl	m3u
    AudioXmpegurlM3u("audio", "x-mpegurl", "m3u"),
    //10 audio/x-pn-realaudio	ra
    AudioXpnrealaudioRa("audio", "x-pn-realaudio", "ra"),
    //11 audio/x-pn-realaudio	ram
    //AudioXpnrealaudioRam("audio", "x-pn-realaudio", "ram"),
    //12 audio/x-wav	        wav
    AudioXwavWav("audio", "x-wav", "wav"),
    //01 image/bmp	            bmp
    ImageBmpBmp("image", "bmp", "bmp"),
    //02 image/cis-cod	        cod
    ImageCiscodCod("image", "cis-cod", "cod"),
    //03 image/gif	            gif
    ImageGifGif("image", "gif", "gif"),
    //04 image/ief	            ief
    ImageIef("image", "ief", "ief"),
    //05 image/jpeg	        jpe
    //ImagejpegJpe("image", "jpeg", "jpe"),
    //06 image/jpeg	        jpeg
    //ImageJpegJpeg("image", "jpeg", "jpeg"),
    //07 image/jpeg	        jpg
    ImageJpegJpg("image", "jpeg", "jpg"),
    // -> image/jpg
    ImageJpgJpg("image", "jpg", "jpg"),
    //08 image/pipeg	        jfif
    ImagePipegJfif("image", "pipeg", "jfif"),
    //09 image/svg+xml	        svg
    ImageSvgxmlSvg("image", "svg+xml", "svg"),
    //10 image/tiff	        tif
    ImageTiffTif("image", "tiff", "tif"),
    //11 image/tiff	        tiff
    //ImageTiffTiff("image", "tiff", "tiff"),
    //12 image/x-cmu-raster	ras
    ImageXcmurasterRas("image", "x-cmu-raster", "ras"),
    //13 image/x-cmx	        cmx
    ImageXcmxCmx("image", "x-cmx", "cmx"),
    //14 image/x-icon	        ico
    ImageXiconIco("image", "x-icon", "ico"),
    //15 image/x-portable-anymap	pnm
    ImageXportableanymapPnm("image", "x-portable-anymap", "pnm"),
    //16 image/x-portable-bitmap	pbm
    ImageXportablebitmapPbm("image", "x-portable-bitmap", "pbm"),
    //17 image/x-portable-graymap	pgm
    ImageXportablegraymapPgm("image", "x-portable-graymap", "pgm"),
    //18 image/x-portable-pixmap	ppm
    ImageXportablepixmapPpm("image", "x-portable-pixmap", "ppm"),
    //19 image/x-rgb	            rgb
    ImageXrgbRgb("image", "x-rgb", "rgb"),
    //20 image/x-xbitmap	        xbm
    ImageXxbitmapXbm("image", "x-xbitmap", "xbm"),
    //21 image/x-xpixmap	        xpm
    ImageXxpixmapXpm("image", "x-xpixmap", "xpm"),
    //22 image/x-xwindowdump	    xwd
    ImageXxwindowdumpXwd("image", "x-xwindowdump", "xwd"),
    // -> image/png
    ImagePngPng("image", "png", "png"),
    // -> image/webp
    ImageWebpWebp("image", "webp", "webp"),
    // -> image/x-ms-bmp
    ImageXmsbmpBmp("image", "x-ms-bmp", "x.ms.bmp"),
    //01 message/rfc822	        mht
    MessageRfc822Mht("message", "rfc822", "mht"),
    //02 message/rfc822	        mhtml
    //MessageRfc822Mhtml("message", "rfc822", "mhtml"),
    //03 message/rfc822	        nws
    //MessageRfc822Nws("message", "rfc822", "nws"),
    //01 text/css	                css
    TextCssCss("text", "css", "css"),
    // -> text/javascript
    TextJavascriptJs("text", "javascript", "js"),
    // -> text/octet-stream
    TextOctetstreamStream("text", "octet-stream", "stream"),
    // -> text/json
    TextJsonJson("text", "json", "text.json"),
    //02 text/h323	                323
    TextH323323("text", "h323", "323"),
    //03 text/html	                htm
    //TextHtmlHtm("text", "html", "htm"),
    //04 text/html	                html
    TextHtmlHtml("text", "html", "html"),
    //05 text/html	                stm
    //TextHtmlStm("text", "html", "stm"),
    //06 text/iuls	                uls
    TextIulsUls("text", "iuls", "uls"),
    //07 text/plain	            bas
    //TextPlainBas("text", "plain", "bas"),
    //08 text/plain	            c
    //TextPlainC("text", "plain", "c"),
    //09 text/plain	            h
    //TextPlainH("text", "plain", "h"),
    //10 text/plain	            txt
    TextPlainTxt("text", "plain", "txt"),
    //11 text/richtext	            rtx
    TextRichtextRtx("text", "richtext", "rtx"),
    //12 text/scriptlet	        sct
    TextScriptletSct("text", "scriptlet", "sct"),
    //13 text/tab-separated-values	tsv
    TextTabseparatedvaluesTsv("text", "tab-separated-values", "tsv"),
    //14 text/webviewhtml	        htt
    TextWebviewhtmlHtt("text", "webviewhtml", "htt"),
    //15 text/x-component	        htc
    TextXcomponentHtc("text", "x-component", "htc"),
    //16 text/x-setext	            etx
    TextXsetextEtx("text", "x-setext", "etx"),
    //17 text/x-vcard	            vcf
    TextXvcardVcf("text", "x-vcard", "vcf"),
    //01 video/mpeg	            mp2
    //VideoMpegMp2("video", "mpeg", "mp2"),
    //02 video/mpeg	            mpa
    //VideoMpegMpa("video", "mpeg", "mpa"),
    //03 video/mpeg	            mpe
    //VideoMpegMpe("video", "mpeg", "mpe"),
    //04 video/mpeg	            mpeg
    VideoMpegMpeg("video", "mpeg", "mpeg"),
    //05 video/mpeg	            mpg
    //VideoMpegMpg("video", "mpeg", "mpg"),
    //06 video/mpeg	            mpv2
    //VideoMpegMpv2("video", "mpeg", "mpv2"),
    //07 video/quicktime	        mov
    VideoQuicktimeMov("video", "quicktime", "mov"),
    //08 video/quicktime	        qt
    //VideoQuicktimeMovQt("video", "quicktime", "qt"),
    //09 video/x-la-asf	        lsf
    //VideoXlaasfLsf("video", "x-la-asf", "lsf"),
    //10 video/x-la-asf	        lsx
    VideoXlaasfLsx("video", "x-la-asf", "lsx"),
    //11 video/x-ms-asf	        asf
    VideoXmsasfAsf("video", "x-ms-asf", "asf"),
    //12 video/x-ms-asf	        asr
    //VideoXmsasfAsr("video", "x-ms-asf", "asr"),
    //13 video/x-ms-asf	        asx
    //VideoXmsasfAsx("video", "x-ms-asf", "asx"),
    //14 video/x-msvideo	        avi
    VideoXmsvideoAvi("video", "x-msvideo", "avi"),
    // -> video/mpeg4
    VideoMpeg4Mpeg4("video", "mpeg4", "mpeg4"),
    // -> video/mp4
    VideoMp4Mp4("video", "mp4", "mp4"),
    // -> video/x-flv
    VideoXflvFlv("video", "x-flv", "flv"),

    //15 video/x-sgi-movie	        movie
    VideoXsgimovieMovie("video", "x-sgi-movie", "movie"),

    //01 x-world/x-vrml	        flr
    //XworldXvrmlFlr("x-world", "x-vrml", "flr"),
    //02 x-world/x-vrml	        vrml
    //XworldXvrmlVrml("x-world", "x-vrml", "vrml"),
    //03 x-world/x-vrml	        wrl
    //XworldXvrmlWrl("x-world", "x-vrml", "wrl"),
    //04 x-world/x-vrml	        wrz
    //XworldXvrmlWrz("x-world", "x-vrml", "wrz"),
    //05 x-world/x-vrml	        xaf
    //XworldXvrmlXaf("x-world", "x-vrml", "xaf"),
    //06 x-world/x-vrml	        xof
    XworldXvrmlXof("x-world", "x-vrml", "xof"),

    // -> font/woff2
    FontWoff2("x-world", "font", "font.woff2");

    /**
     * 类型
     */
    private String type;

    /**
     * 子类型
     */
    private String subtype;

    /**
     * 扩展名
     */
    private String extension;

    public String valueType() {
        return type;
    }

    public String valueSubtype() {
        return subtype;
    }

    public String valueExtension() {
        return extension;
    }

    private String valueTypeSubtype() {
        return type + "/" + subtype;
    }

    MineType(String type, String subtype, String extension) {
        this.type = type;
        this.subtype = subtype;
        this.extension = extension;
    }

    /**
     * 匹配查询传入字符串是否存在 [type/subtype]
     *
     * @param typesubtype type/subtype
     * @return
     */
    public static List<MineType> matchTypSubtype(String typesubtype) {
        List<MineType> mineTypeList = new ArrayList<>();
        for (MineType mineType : MineType.values()) {
            if (typesubtype.contains(";")) {
                System.out.println("存在多个 TypeSubtype : " + typesubtype);
            }
            if (typesubtype.equalsIgnoreCase(mineType.type + "/" + mineType.subtype)) {
                mineTypeList.add(mineType);
            }
        }
        return mineTypeList;
    }

    /**
     * 类型/子类型 + 扩展名, 匹配查询|为空不参与匹配
     */
    public static List<MineType> match(String type, String subtype, String extension) {
        List<MineType> mineTypeList = new ArrayList<>();
        if (type == null && subtype == null && extension == null) {
            // 查询字段不能都为空
            return mineTypeList;
        }
        for (MineType mineType : MineType.values()) {
            if ((type == null || mineType.type.equalsIgnoreCase(type))
                    && (subtype == null || mineType.subtype.equalsIgnoreCase(subtype))
                    && (extension == null || mineType.extension.equalsIgnoreCase(extension))) {
                mineTypeList.add(mineType);
            }
        }
        return mineTypeList;
    }

    /**
     * 类型/子类型 + 扩展名, 确定唯一类型
     */
    public static MineType value(String type, String subtype, String extension) {
        for (MineType mineType : MineType.values()) {
            if (mineType.type.equalsIgnoreCase(type)
                    && mineType.subtype.equalsIgnoreCase(subtype)
                    && mineType.extension.equalsIgnoreCase(extension)) {
                return mineType;
            }
        }
        return null;
    }
}

// MIME (Multipurpose Internet Mail Extensions) 描述消息内容类型的因特网标准
//Internet Engineering Task Force (IETF)
//RFC-822 Standard for ARPA Internet text messages            // http://www.rfc-editor.org/rfc/rfc822.txt
//RFC-2045 MIME Part 1: Format of Internet Message Bodies     // http://www.rfc-editor.org/rfc/rfc2045.txt
//RFC-2046 MIME Part 2: Media Types                           // http://www.rfc-editor.org/rfc/rfc2046.txt
//RFC-2047 MIME Part 3: Header Extensions for Non-ASCII Text  // http://www.rfc-editor.org/rfc/rfc2047.txt
//RFC-2048 MIME Part 4: Registration Procedures               // http://www.rfc-editor.org/rfc/rfc2048.txt
//RFC-2049 MIME Part 5: Conformance Criteria and Examples     // http://www.rfc-editor.org/rfc/rfc2049.txt
//类型/子类型                   扩展名
//application/envoy             vy
//application/fractals	        fif
//application/futuresplash	    spl
//application/hta	            hta
//application/internet-property-stream	acx
//application/mac-binhex40	    hqx
//application/msword	        doc
//application/msword	        dot
//application/octet-stream	    *
//application/octet-stream	    bin
//application/octet-stream	    class
//application/octet-stream	    dms
//application/octet-stream	    exe
//application/octet-stream	    lha
//application/octet-stream	    lzh
//application/oda	            oda
//application/olescript	        axs
//application/pdf	            pdf
//application/pics-rules	    prf
//application/pkcs10	        p10
//application/pkix-crl	        crl
//application/postscript	    ai
//application/postscript	    eps
//application/postscript	    ps
//application/rtf	            rtf
//application/set-payment-initiation	    setpay
//application/set-registration-initiation	setreg
//application/vnd.ms-excel	    xla
//application/vnd.ms-excel	    xlc
//application/vnd.ms-excel	    xlm
//application/vnd.ms-excel	    xls
//application/vnd.ms-excel	    xlt
//application/vnd.ms-excel	    xlw
//application/vnd.ms-outlook	msg
//application/vnd.ms-pkicertstore	sst
//application/vnd.ms-pkiseccat	    cat
//application/vnd.ms-pkistl	        stl
//application/vnd.ms-powerpoint	    pot
//application/vnd.ms-powerpoint	    pps
//application/vnd.ms-powerpoint	    ppt
//application/vnd.ms-project	    mpp
//application/vnd.ms-works	        wcm
//application/vnd.ms-works	        wdb
//application/vnd.ms-works	        wks
//application/vnd.ms-works	        wps
//application/winhlp	            hlp
//application/x-bcpio	            bcpio
//application/x-cdf	                cdf
//application/x-compress	        z
//application/x-compressed	        tgz
//application/x-cpio	            cpio
//application/x-csh	                csh
//application/x-director	        dcr
//application/x-director	        dir
//application/x-director	        dxr
//application/x-dvi	                dvi
//application/x-gtar	            gtar
//application/x-gzip	            gz
//application/x-hdf	            hdf
//application/x-internet-signup	ins
//application/x-internet-signup	isp
//application/x-iphone	        iii
//application/x-javascript	    js
//application/x-latex	        latex
//application/x-msaccess	    mdb
//application/x-mscardfile	    crd
//application/x-msclip	        clp
//application/x-msdownload	    dll
//application/x-msmediaview	    m13
//application/x-msmediaview	    m14
//application/x-msmediaview	    mvb
//application/x-msmetafile	    wmf
//application/x-msmoney	        mny
//application/x-mspublisher	    pub
//application/x-msschedule	    scd
//application/x-msterminal	    trm
//application/x-mswrite	        wri
//application/x-netcdf	        cdf
//application/x-netcdf	        nc
//application/x-perfmon	        pma
//application/x-perfmon	        pmc
//application/x-perfmon	        pml
//application/x-perfmon	        pmr
//application/x-perfmon	        pmw
//application/x-pkcs12	        p12
//application/x-pkcs12	        pfx
//application/x-pkcs7-certificates	p7b
//application/x-pkcs7-certificates	spc
//application/x-pkcs7-certreqresp	p7r
//application/x-pkcs7-mime	        p7c
//application/x-pkcs7-mime	        p7m
//application/x-pkcs7-signature	    p7s
//application/x-sh	            sh
//application/x-shar	        shar
//application/x-shockwave-flash	swf
//application/x-stuffit	        sit
//application/x-sv4cpio	        sv4cpio
//application/x-sv4crc	        sv4crc
//application/x-tar	            tar
//application/x-tcl	            tcl
//application/x-tex	            tex
//application/x-texinfo	        texi
//application/x-texinfo	        texinfo
//application/x-troff	        roff
//application/x-troff	        t
//application/x-troff	        tr
//application/x-troff-man	    man
//application/x-troff-me	    me
//application/x-troff-ms	    ms
//application/x-ustar	        ustar
//application/x-wais-source	    src
//application/x-x509-ca-cert	cer
//application/x-x509-ca-cert	crt
//application/x-x509-ca-cert	der
//application/ynd.ms-pkipko	    pko
//application/zip	zip
//audio/basic	    au
//audio/basic	    snd
//audio/mid	        mid
//audio/mid	        rmi
//audio/mpeg	    mp3
//audio/x-aiff	    aif
//audio/x-aiff	    aifc
//audio/x-aiff	    aiff
//audio/x-mpegurl	m3u
//audio/x-pn-realaudio	ra
//audio/x-pn-realaudio	ram
//audio/x-wav	        wav
//image/bmp	            bmp
//image/cis-cod	        cod
//image/gif	            gif
//image/ief	            ief
//image/jpeg	        jpe
//image/jpeg	        jpeg
//image/jpeg	        jpg
//image/pipeg	        jfif
//image/svg+xml	        svg
//image/tiff	        tif
//image/tiff	        tiff
//image/x-cmu-raster	ras
//image/x-cmx	        cmx
//image/x-icon	        ico
//image/x-portable-anymap	pnm
//image/x-portable-bitmap	pbm
//image/x-portable-graymap	pgm
//image/x-portable-pixmap	ppm
//image/x-rgb	            rgb
//image/x-xbitmap	        xbm
//image/x-xpixmap	        xpm
//image/x-xwindowdump	    xwd
//message/rfc822	        mht
//message/rfc822	        mhtml
//message/rfc822	        nws
//text/css	                css
//text/h323	                323
//text/html	                htm
//text/html	                html
//text/html	                stm
//text/iuls	                uls
//text/plain	            bas
//text/plain	            c
//text/plain	            h
//text/plain	            txt
//text/richtext	            rtx
//text/scriptlet	        sct
//text/tab-separated-values	tsv
//text/webviewhtml	        htt
//text/x-component	        htc
//text/x-setext	            etx
//text/x-vcard	            vcf
//video/mpeg	            mp2
//video/mpeg	            mpa
//video/mpeg	            mpe
//video/mpeg	            mpeg
//video/mpeg	            mpg
//video/mpeg	            mpv2
//video/quicktime	        mov
//video/quicktime	        qt
//video/x-la-asf	        lsf
//video/x-la-asf	        lsx
//video/x-ms-asf	        asf
//video/x-ms-asf	        asr
//video/x-ms-asf	        asx
//video/x-msvideo	        avi
//video/x-sgi-movie	        movie
//x-world/x-vrml	        flr
//x-world/x-vrml	        vrml
//x-world/x-vrml	        wrl
//x-world/x-vrml	        wrz
//x-world/x-vrml	        xaf
//x-world/x-vrml	        xof
