/*      */ package sun.print;
/*      */ 
/*      */ import java.awt.Window;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.File;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import javax.print.DocFlavor;
/*      */ import javax.print.DocPrintJob;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.ServiceUIFactory;
/*      */ import javax.print.attribute.Attribute;
/*      */ import javax.print.attribute.AttributeSet;
/*      */ import javax.print.attribute.AttributeSetUtilities;
/*      */ import javax.print.attribute.HashAttributeSet;
/*      */ import javax.print.attribute.HashPrintServiceAttributeSet;
/*      */ import javax.print.attribute.PrintRequestAttributeSet;
/*      */ import javax.print.attribute.PrintServiceAttribute;
/*      */ import javax.print.attribute.PrintServiceAttributeSet;
/*      */ import javax.print.attribute.standard.Chromaticity;
/*      */ import javax.print.attribute.standard.ColorSupported;
/*      */ import javax.print.attribute.standard.Copies;
/*      */ import javax.print.attribute.standard.CopiesSupported;
/*      */ import javax.print.attribute.standard.Destination;
/*      */ import javax.print.attribute.standard.Fidelity;
/*      */ import javax.print.attribute.standard.JobName;
/*      */ import javax.print.attribute.standard.Media;
/*      */ import javax.print.attribute.standard.MediaPrintableArea;
/*      */ import javax.print.attribute.standard.MediaSize;
/*      */ import javax.print.attribute.standard.MediaSizeName;
/*      */ import javax.print.attribute.standard.MediaTray;
/*      */ import javax.print.attribute.standard.OrientationRequested;
/*      */ import javax.print.attribute.standard.PageRanges;
/*      */ import javax.print.attribute.standard.PrintQuality;
/*      */ import javax.print.attribute.standard.PrinterIsAcceptingJobs;
/*      */ import javax.print.attribute.standard.PrinterName;
/*      */ import javax.print.attribute.standard.PrinterResolution;
/*      */ import javax.print.attribute.standard.PrinterState;
/*      */ import javax.print.attribute.standard.PrinterStateReason;
/*      */ import javax.print.attribute.standard.PrinterStateReasons;
/*      */ import javax.print.attribute.standard.QueuedJobCount;
/*      */ import javax.print.attribute.standard.RequestingUserName;
/*      */ import javax.print.attribute.standard.Severity;
/*      */ import javax.print.attribute.standard.SheetCollate;
/*      */ import javax.print.attribute.standard.Sides;
/*      */ import javax.print.event.PrintServiceAttributeListener;
/*      */ import sun.awt.windows.WPrinterJob;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Win32PrintService
/*      */   implements PrintService, AttributeUpdater, SunPrinterJobService
/*      */ {
/*   80 */   public static MediaSize[] predefMedia = Win32MediaSize.getPredefMedia();
/*      */   
/*   82 */   private static final DocFlavor[] supportedFlavors = new DocFlavor[] { DocFlavor.BYTE_ARRAY.GIF, DocFlavor.INPUT_STREAM.GIF, DocFlavor.URL.GIF, DocFlavor.BYTE_ARRAY.JPEG, DocFlavor.INPUT_STREAM.JPEG, DocFlavor.URL.JPEG, DocFlavor.BYTE_ARRAY.PNG, DocFlavor.INPUT_STREAM.PNG, DocFlavor.URL.PNG, DocFlavor.SERVICE_FORMATTED.PAGEABLE, DocFlavor.SERVICE_FORMATTED.PRINTABLE, DocFlavor.BYTE_ARRAY.AUTOSENSE, DocFlavor.URL.AUTOSENSE, DocFlavor.INPUT_STREAM.AUTOSENSE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  100 */   private static final Class[] serviceAttrCats = new Class[] { PrinterName.class, PrinterIsAcceptingJobs.class, QueuedJobCount.class, ColorSupported.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  110 */   private static Class[] otherAttrCats = new Class[] { JobName.class, RequestingUserName.class, Copies.class, Destination.class, OrientationRequested.class, PageRanges.class, Media.class, MediaPrintableArea.class, Fidelity.class, SheetCollate.class, SunAlternateMedia.class, Chromaticity.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  132 */   public static final MediaSizeName[] dmPaperToPrintService = new MediaSizeName[] { MediaSizeName.NA_LETTER, MediaSizeName.NA_LETTER, MediaSizeName.TABLOID, MediaSizeName.LEDGER, MediaSizeName.NA_LEGAL, MediaSizeName.INVOICE, MediaSizeName.EXECUTIVE, MediaSizeName.ISO_A3, MediaSizeName.ISO_A4, MediaSizeName.ISO_A4, MediaSizeName.ISO_A5, MediaSizeName.JIS_B4, MediaSizeName.JIS_B5, MediaSizeName.FOLIO, MediaSizeName.QUARTO, MediaSizeName.NA_10X14_ENVELOPE, MediaSizeName.B, MediaSizeName.NA_LETTER, MediaSizeName.NA_NUMBER_9_ENVELOPE, MediaSizeName.NA_NUMBER_10_ENVELOPE, MediaSizeName.NA_NUMBER_11_ENVELOPE, MediaSizeName.NA_NUMBER_12_ENVELOPE, MediaSizeName.NA_NUMBER_14_ENVELOPE, MediaSizeName.C, MediaSizeName.D, MediaSizeName.E, MediaSizeName.ISO_DESIGNATED_LONG, MediaSizeName.ISO_C5, MediaSizeName.ISO_C3, MediaSizeName.ISO_C4, MediaSizeName.ISO_C6, MediaSizeName.ITALY_ENVELOPE, MediaSizeName.ISO_B4, MediaSizeName.ISO_B5, MediaSizeName.ISO_B6, MediaSizeName.ITALY_ENVELOPE, MediaSizeName.MONARCH_ENVELOPE, MediaSizeName.PERSONAL_ENVELOPE, MediaSizeName.NA_10X15_ENVELOPE, MediaSizeName.NA_9X12_ENVELOPE, MediaSizeName.FOLIO, MediaSizeName.ISO_B4, MediaSizeName.JAPANESE_POSTCARD, MediaSizeName.NA_9X11_ENVELOPE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   private static final MediaTray[] dmPaperBinToPrintService = new MediaTray[] { MediaTray.TOP, MediaTray.BOTTOM, MediaTray.MIDDLE, MediaTray.MANUAL, MediaTray.ENVELOPE, Win32MediaTray.ENVELOPE_MANUAL, Win32MediaTray.AUTO, Win32MediaTray.TRACTOR, Win32MediaTray.SMALL_FORMAT, Win32MediaTray.LARGE_FORMAT, MediaTray.LARGE_CAPACITY, null, null, MediaTray.MAIN, Win32MediaTray.FORMSOURCE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  167 */   private static int DM_PAPERSIZE = 2;
/*  168 */   private static int DM_PRINTQUALITY = 1024;
/*  169 */   private static int DM_YRESOLUTION = 8192;
/*      */   
/*      */   private static final int DMRES_MEDIUM = -3;
/*      */   
/*      */   private static final int DMRES_HIGH = -4;
/*      */   
/*      */   private static final int DMORIENT_LANDSCAPE = 2;
/*      */   
/*      */   private static final int DMDUP_VERTICAL = 2;
/*      */   
/*      */   private static final int DMDUP_HORIZONTAL = 3;
/*      */   
/*      */   private static final int DMCOLLATE_TRUE = 1;
/*      */   
/*      */   private static final int DMCOLOR_MONOCHROME = 1;
/*      */   
/*      */   private static final int DMCOLOR_COLOR = 2;
/*      */   
/*      */   private static final int DMPAPER_A2 = 66;
/*      */   private static final int DMPAPER_A6 = 70;
/*      */   private static final int DMPAPER_B6_JIS = 88;
/*      */   private static final int DEVCAP_COLOR = 1;
/*      */   private static final int DEVCAP_DUPLEX = 2;
/*      */   private static final int DEVCAP_COLLATE = 4;
/*      */   private static final int DEVCAP_QUALITY = 8;
/*      */   private static final int DEVCAP_POSTSCRIPT = 16;
/*      */   private String printer;
/*      */   private PrinterName name;
/*      */   private String port;
/*      */   private transient PrintServiceAttributeSet lastSet;
/*  199 */   private transient ServiceNotifier notifier = null;
/*      */ 
/*      */   
/*      */   private MediaSizeName[] mediaSizeNames;
/*      */   
/*      */   private MediaPrintableArea[] mediaPrintables;
/*      */   
/*      */   private MediaTray[] mediaTrays;
/*      */   
/*      */   private PrinterResolution[] printRes;
/*      */   
/*      */   private HashMap mpaMap;
/*      */   
/*      */   private int nCopies;
/*      */   
/*      */   private int prnCaps;
/*      */   
/*      */   private int[] defaultSettings;
/*      */   
/*      */   private boolean gotTrays;
/*      */   
/*      */   private boolean gotCopies;
/*      */   
/*      */   private boolean mediaInitialized;
/*      */   
/*      */   private boolean mpaListInitialized;
/*      */   
/*      */   private ArrayList idList;
/*      */   
/*      */   private MediaSize[] mediaSizes;
/*      */   
/*      */   private boolean isInvalid;
/*      */   
/*      */   private Win32DocumentPropertiesUI docPropertiesUI;
/*      */   
/*      */   private Win32ServiceUIFactory uiFactory;
/*      */ 
/*      */   
/*      */   public void invalidateService() {
/*  238 */     this.isInvalid = true;
/*      */   }
/*      */   
/*      */   public String getName() {
/*  242 */     return this.printer;
/*      */   }
/*      */   
/*      */   private PrinterName getPrinterName() {
/*  246 */     if (this.name == null) {
/*  247 */       this.name = new PrinterName(this.printer, null);
/*      */     }
/*  249 */     return this.name;
/*      */   }
/*      */   
/*      */   public int findPaperID(MediaSizeName paramMediaSizeName) {
/*  253 */     if (paramMediaSizeName instanceof Win32MediaSize) {
/*  254 */       Win32MediaSize win32MediaSize = (Win32MediaSize)paramMediaSizeName;
/*  255 */       return win32MediaSize.getDMPaper();
/*      */     }  byte b;
/*  257 */     for (b = 0; b < dmPaperToPrintService.length; b++) {
/*  258 */       if (dmPaperToPrintService[b].equals(paramMediaSizeName)) {
/*  259 */         return b + 1;
/*      */       }
/*      */     } 
/*  262 */     if (paramMediaSizeName.equals(MediaSizeName.ISO_A2)) {
/*  263 */       return 66;
/*      */     }
/*  265 */     if (paramMediaSizeName.equals(MediaSizeName.ISO_A6)) {
/*  266 */       return 70;
/*      */     }
/*  268 */     if (paramMediaSizeName.equals(MediaSizeName.JIS_B6)) {
/*  269 */       return 88;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  276 */     initMedia();
/*      */     
/*  278 */     if (this.idList != null && this.mediaSizes != null && this.idList
/*  279 */       .size() == this.mediaSizes.length) {
/*  280 */       for (b = 0; b < this.idList.size(); b++) {
/*  281 */         if (this.mediaSizes[b].getMediaSizeName() == paramMediaSizeName) {
/*  282 */           return ((Integer)this.idList.get(b)).intValue();
/*      */         }
/*      */       } 
/*      */     }
/*  286 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int findTrayID(MediaTray paramMediaTray) {
/*  291 */     getMediaTrays();
/*      */     
/*  293 */     if (paramMediaTray instanceof Win32MediaTray) {
/*  294 */       Win32MediaTray win32MediaTray = (Win32MediaTray)paramMediaTray;
/*  295 */       return win32MediaTray.getDMBinID();
/*      */     } 
/*  297 */     for (byte b = 0; b < dmPaperBinToPrintService.length; b++) {
/*  298 */       if (paramMediaTray.equals(dmPaperBinToPrintService[b])) {
/*  299 */         return b + 1;
/*      */       }
/*      */     } 
/*  302 */     return 0;
/*      */   }
/*      */   
/*      */   public MediaTray findMediaTray(int paramInt) {
/*  306 */     if (paramInt >= 1 && paramInt <= dmPaperBinToPrintService.length) {
/*  307 */       return dmPaperBinToPrintService[paramInt - 1];
/*      */     }
/*  309 */     MediaTray[] arrayOfMediaTray = getMediaTrays();
/*  310 */     if (arrayOfMediaTray != null) {
/*  311 */       for (byte b = 0; b < arrayOfMediaTray.length; b++) {
/*  312 */         if (arrayOfMediaTray[b] instanceof Win32MediaTray) {
/*  313 */           Win32MediaTray win32MediaTray = (Win32MediaTray)arrayOfMediaTray[b];
/*  314 */           if (win32MediaTray.winID == paramInt) {
/*  315 */             return win32MediaTray;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*  320 */     return Win32MediaTray.AUTO;
/*      */   }
/*      */   
/*      */   public MediaSizeName findWin32Media(int paramInt) {
/*  324 */     if (paramInt >= 1 && paramInt <= dmPaperToPrintService.length) {
/*  325 */       return dmPaperToPrintService[paramInt - 1];
/*      */     }
/*  327 */     switch (paramInt) {
/*      */ 
/*      */       
/*      */       case 66:
/*  331 */         return MediaSizeName.ISO_A2;
/*      */       case 70:
/*  333 */         return MediaSizeName.ISO_A6;
/*      */       case 88:
/*  335 */         return MediaSizeName.JIS_B6;
/*      */     } 
/*  337 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean addToUniqueList(ArrayList<MediaSizeName> paramArrayList, MediaSizeName paramMediaSizeName) {
/*  343 */     for (byte b = 0; b < paramArrayList.size(); b++) {
/*  344 */       MediaSizeName mediaSizeName = paramArrayList.get(b);
/*  345 */       if (mediaSizeName == paramMediaSizeName) {
/*  346 */         return false;
/*      */       }
/*      */     } 
/*  349 */     paramArrayList.add(paramMediaSizeName);
/*  350 */     return true;
/*      */   }
/*      */   
/*      */   private synchronized void initMedia() {
/*  354 */     if (this.mediaInitialized == true) {
/*      */       return;
/*      */     }
/*  357 */     this.mediaInitialized = true;
/*  358 */     int[] arrayOfInt = getAllMediaIDs(this.printer, getPort());
/*  359 */     if (arrayOfInt == null) {
/*      */       return;
/*      */     }
/*      */     
/*  363 */     ArrayList arrayList1 = new ArrayList();
/*  364 */     ArrayList<Win32MediaSize> arrayList = new ArrayList();
/*  365 */     ArrayList arrayList2 = new ArrayList();
/*      */ 
/*      */     
/*  368 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  378 */     this.idList = new ArrayList();
/*  379 */     for (byte b1 = 0; b1 < arrayOfInt.length; b1++) {
/*  380 */       this.idList.add(Integer.valueOf(arrayOfInt[b1]));
/*      */     }
/*      */     
/*  383 */     ArrayList<String> arrayList3 = new ArrayList();
/*  384 */     this.mediaSizes = getMediaSizes(this.idList, arrayOfInt, arrayList3);
/*  385 */     for (byte b2 = 0; b2 < this.idList.size(); b2++) {
/*      */ 
/*      */       
/*  388 */       MediaSizeName mediaSizeName = findWin32Media(((Integer)this.idList.get(b2)).intValue());
/*      */ 
/*      */ 
/*      */       
/*  392 */       if (mediaSizeName != null && this.idList
/*  393 */         .size() == this.mediaSizes.length) {
/*  394 */         MediaSize mediaSize1 = MediaSize.getMediaSizeForName(mediaSizeName);
/*  395 */         MediaSize mediaSize2 = this.mediaSizes[b2];
/*  396 */         char c = '৬';
/*  397 */         if (Math.abs(mediaSize1.getX(1) - mediaSize2.getX(1)) > c || 
/*  398 */           Math.abs(mediaSize1.getY(1) - mediaSize2.getY(1)) > c)
/*      */         {
/*  400 */           mediaSizeName = null;
/*      */         }
/*      */       } 
/*  403 */       boolean bool2 = (mediaSizeName != null) ? true : false;
/*      */ 
/*      */ 
/*      */       
/*  407 */       if (mediaSizeName == null && this.idList.size() == this.mediaSizes.length) {
/*  408 */         mediaSizeName = this.mediaSizes[b2].getMediaSizeName();
/*      */       }
/*      */ 
/*      */       
/*  412 */       boolean bool1 = false;
/*  413 */       if (mediaSizeName != null) {
/*  414 */         bool1 = addToUniqueList(arrayList1, mediaSizeName);
/*      */       }
/*  416 */       if ((!bool2 || !bool1) && this.idList.size() == arrayList3.size()) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  421 */         Win32MediaSize win32MediaSize = Win32MediaSize.findMediaName(arrayList3.get(b2));
/*  422 */         if (win32MediaSize == null && this.idList.size() == this.mediaSizes.length) {
/*  423 */           win32MediaSize = new Win32MediaSize(arrayList3.get(b2), ((Integer)this.idList.get(b2)).intValue());
/*  424 */           this.mediaSizes[b2] = new MediaSize(this.mediaSizes[b2].getX(1000), this.mediaSizes[b2]
/*  425 */               .getY(1000), 1000, win32MediaSize);
/*      */         } 
/*  427 */         if (win32MediaSize != null && win32MediaSize != mediaSizeName) {
/*  428 */           if (!bool1) {
/*  429 */             bool1 = addToUniqueList(arrayList1, mediaSizeName = win32MediaSize);
/*      */           } else {
/*  431 */             arrayList.add(win32MediaSize);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*  436 */     for (Win32MediaSize win32MediaSize : arrayList) {
/*  437 */       boolean bool1 = addToUniqueList(arrayList1, win32MediaSize);
/*      */     }
/*      */ 
/*      */     
/*  441 */     this.mediaSizeNames = new MediaSizeName[arrayList1.size()];
/*  442 */     arrayList1.toArray((Object[])this.mediaSizeNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized MediaPrintableArea[] getMediaPrintables(MediaSizeName paramMediaSizeName) {
/*      */     MediaSizeName[] arrayOfMediaSizeName;
/*  453 */     if (paramMediaSizeName == null) {
/*  454 */       if (this.mpaListInitialized == true) {
/*  455 */         return this.mediaPrintables;
/*      */       
/*      */       }
/*      */     }
/*  459 */     else if (this.mpaMap != null && this.mpaMap.get(paramMediaSizeName) != null) {
/*  460 */       MediaPrintableArea[] arrayOfMediaPrintableArea = new MediaPrintableArea[1];
/*  461 */       arrayOfMediaPrintableArea[0] = (MediaPrintableArea)this.mpaMap.get(paramMediaSizeName);
/*  462 */       return arrayOfMediaPrintableArea;
/*      */     } 
/*      */ 
/*      */     
/*  466 */     initMedia();
/*      */     
/*  468 */     if (this.mediaSizeNames == null || this.mediaSizeNames.length == 0) {
/*  469 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  473 */     if (paramMediaSizeName != null) {
/*  474 */       arrayOfMediaSizeName = new MediaSizeName[1];
/*  475 */       arrayOfMediaSizeName[0] = paramMediaSizeName;
/*      */     } else {
/*  477 */       arrayOfMediaSizeName = this.mediaSizeNames;
/*      */     } 
/*      */     
/*  480 */     if (this.mpaMap == null) {
/*  481 */       this.mpaMap = new HashMap<>();
/*      */     }
/*      */     
/*  484 */     for (byte b = 0; b < arrayOfMediaSizeName.length; b++) {
/*  485 */       MediaSizeName mediaSizeName = arrayOfMediaSizeName[b];
/*      */       
/*  487 */       if (this.mpaMap.get(mediaSizeName) == null)
/*      */       {
/*      */ 
/*      */         
/*  491 */         if (mediaSizeName != null) {
/*  492 */           int i = findPaperID(mediaSizeName);
/*  493 */           float[] arrayOfFloat = (i != 0) ? getMediaPrintableArea(this.printer, i) : null;
/*  494 */           MediaPrintableArea mediaPrintableArea = null;
/*  495 */           if (arrayOfFloat != null) {
/*      */             try {
/*  497 */               mediaPrintableArea = new MediaPrintableArea(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], 25400);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  503 */               this.mpaMap.put(mediaSizeName, mediaPrintableArea);
/*      */             }
/*  505 */             catch (IllegalArgumentException illegalArgumentException) {}
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  510 */             MediaSize mediaSize = MediaSize.getMediaSizeForName(mediaSizeName);
/*      */             
/*  512 */             if (mediaSize != null) {
/*      */               
/*      */               try {
/*      */                 
/*  516 */                 mediaPrintableArea = new MediaPrintableArea(0.0F, 0.0F, mediaSize.getX(25400), mediaSize.getY(25400), 25400);
/*      */                 
/*  518 */                 this.mpaMap.put(mediaSizeName, mediaPrintableArea);
/*  519 */               } catch (IllegalArgumentException illegalArgumentException) {}
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  526 */     if (this.mpaMap.size() == 0) {
/*  527 */       return null;
/*      */     }
/*      */     
/*  530 */     if (paramMediaSizeName != null) {
/*  531 */       if (this.mpaMap.get(paramMediaSizeName) == null) {
/*  532 */         return null;
/*      */       }
/*  534 */       MediaPrintableArea[] arrayOfMediaPrintableArea = new MediaPrintableArea[1];
/*      */       
/*  536 */       arrayOfMediaPrintableArea[0] = (MediaPrintableArea)this.mpaMap.get(paramMediaSizeName);
/*  537 */       return arrayOfMediaPrintableArea;
/*      */     } 
/*  539 */     this.mediaPrintables = (MediaPrintableArea[])this.mpaMap.values().toArray((Object[])new MediaPrintableArea[0]);
/*  540 */     this.mpaListInitialized = true;
/*  541 */     return this.mediaPrintables;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized MediaTray[] getMediaTrays() {
/*  547 */     if (this.gotTrays == true && this.mediaTrays != null) {
/*  548 */       return this.mediaTrays;
/*      */     }
/*  550 */     String str = getPort();
/*  551 */     int[] arrayOfInt = getAllMediaTrays(this.printer, str);
/*  552 */     String[] arrayOfString = getAllMediaTrayNames(this.printer, str);
/*      */     
/*  554 */     if (arrayOfInt == null || arrayOfString == null) {
/*  555 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  561 */     byte b1 = 0;
/*  562 */     for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
/*  563 */       if (arrayOfInt[b2] > 0) b1++;
/*      */     
/*      */     } 
/*  566 */     MediaTray[] arrayOfMediaTray = new MediaTray[b1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  575 */     for (byte b3 = 0, b4 = 0; b3 < Math.min(arrayOfInt.length, arrayOfString.length); b3++) {
/*  576 */       int i = arrayOfInt[b3];
/*  577 */       if (i > 0)
/*      */       {
/*  579 */         if (i > dmPaperBinToPrintService.length || dmPaperBinToPrintService[i - 1] == null) {
/*      */           
/*  581 */           arrayOfMediaTray[b4++] = new Win32MediaTray(i, arrayOfString[b3]);
/*      */         } else {
/*  583 */           arrayOfMediaTray[b4++] = dmPaperBinToPrintService[i - 1];
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  589 */     this.mediaTrays = arrayOfMediaTray;
/*  590 */     this.gotTrays = true;
/*  591 */     return this.mediaTrays;
/*      */   }
/*      */   
/*      */   private boolean isSameSize(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  595 */     float f1 = paramFloat1 - paramFloat3;
/*  596 */     float f2 = paramFloat2 - paramFloat4;
/*      */ 
/*      */     
/*  599 */     float f3 = paramFloat1 - paramFloat4;
/*  600 */     float f4 = paramFloat2 - paramFloat3;
/*      */     
/*  602 */     if ((Math.abs(f1) <= 1.0F && Math.abs(f2) <= 1.0F) || (
/*  603 */       Math.abs(f3) <= 1.0F && Math.abs(f4) <= 1.0F)) {
/*  604 */       return true;
/*      */     }
/*  606 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public MediaSizeName findMatchingMediaSizeNameMM(float paramFloat1, float paramFloat2) {
/*  611 */     if (predefMedia != null)
/*  612 */       for (byte b = 0; b < predefMedia.length; b++) {
/*  613 */         if (predefMedia[b] != null)
/*      */         {
/*      */ 
/*      */           
/*  617 */           if (isSameSize(predefMedia[b].getX(1000), predefMedia[b]
/*  618 */               .getY(1000), paramFloat1, paramFloat2))
/*      */           {
/*  620 */             return predefMedia[b].getMediaSizeName();
/*      */           }
/*      */         }
/*      */       }  
/*  624 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private MediaSize[] getMediaSizes(ArrayList paramArrayList, int[] paramArrayOfint, ArrayList<String> paramArrayList1) {
/*  629 */     if (paramArrayList1 == null) {
/*  630 */       paramArrayList1 = new ArrayList<>();
/*      */     }
/*      */     
/*  633 */     String str = getPort();
/*  634 */     int[] arrayOfInt = getAllMediaSizes(this.printer, str);
/*  635 */     String[] arrayOfString = getAllMediaNames(this.printer, str);
/*  636 */     MediaSizeName mediaSizeName = null;
/*  637 */     MediaSize mediaSize = null;
/*      */ 
/*      */     
/*  640 */     if (arrayOfInt == null || arrayOfString == null) {
/*  641 */       return null;
/*      */     }
/*      */     
/*  644 */     int i = arrayOfInt.length / 2;
/*  645 */     ArrayList<MediaSize> arrayList = new ArrayList();
/*      */     
/*  647 */     for (byte b = 0; b < i; b++, mediaSize = null) {
/*  648 */       float f1 = arrayOfInt[b * 2] / 10.0F;
/*  649 */       float f2 = arrayOfInt[b * 2 + 1] / 10.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  654 */       if (f1 <= 0.0F || f2 <= 0.0F) {
/*      */         
/*  656 */         if (i == paramArrayOfint.length) {
/*  657 */           Integer integer = Integer.valueOf(paramArrayOfint[b]);
/*  658 */           paramArrayList.remove(paramArrayList.indexOf(integer));
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  664 */         mediaSizeName = findMatchingMediaSizeNameMM(f1, f2);
/*  665 */         if (mediaSizeName != null) {
/*  666 */           mediaSize = MediaSize.getMediaSizeForName(mediaSizeName);
/*      */         }
/*      */         
/*  669 */         if (mediaSize != null) {
/*  670 */           arrayList.add(mediaSize);
/*  671 */           paramArrayList1.add(arrayOfString[b]);
/*      */         } else {
/*  673 */           Win32MediaSize win32MediaSize = Win32MediaSize.findMediaName(arrayOfString[b]);
/*  674 */           if (win32MediaSize == null) {
/*  675 */             win32MediaSize = new Win32MediaSize(arrayOfString[b], paramArrayOfint[b]);
/*      */           }
/*      */           try {
/*  678 */             mediaSize = new MediaSize(f1, f2, 1000, win32MediaSize);
/*  679 */             arrayList.add(mediaSize);
/*  680 */             paramArrayList1.add(arrayOfString[b]);
/*  681 */           } catch (IllegalArgumentException illegalArgumentException) {
/*  682 */             if (i == paramArrayOfint.length) {
/*  683 */               Integer integer = Integer.valueOf(paramArrayOfint[b]);
/*  684 */               paramArrayList.remove(paramArrayList.indexOf(integer));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  690 */     MediaSize[] arrayOfMediaSize = new MediaSize[arrayList.size()];
/*  691 */     arrayList.toArray(arrayOfMediaSize);
/*      */     
/*  693 */     return arrayOfMediaSize;
/*      */   }
/*      */   
/*      */   private PrinterIsAcceptingJobs getPrinterIsAcceptingJobs() {
/*  697 */     if (getJobStatus(this.printer, 2) != 1) {
/*  698 */       return PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS;
/*      */     }
/*      */     
/*  701 */     return PrinterIsAcceptingJobs.ACCEPTING_JOBS;
/*      */   }
/*      */ 
/*      */   
/*      */   private PrinterState getPrinterState() {
/*  706 */     if (this.isInvalid) {
/*  707 */       return PrinterState.STOPPED;
/*      */     }
/*  709 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private PrinterStateReasons getPrinterStateReasons() {
/*  714 */     if (this.isInvalid) {
/*  715 */       PrinterStateReasons printerStateReasons = new PrinterStateReasons();
/*  716 */       printerStateReasons.put(PrinterStateReason.SHUTDOWN, Severity.ERROR);
/*  717 */       return printerStateReasons;
/*      */     } 
/*  719 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private QueuedJobCount getQueuedJobCount() {
/*  725 */     int i = getJobStatus(this.printer, 1);
/*  726 */     if (i != -1) {
/*  727 */       return new QueuedJobCount(i);
/*      */     }
/*      */     
/*  730 */     return new QueuedJobCount(0);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isSupportedCopies(Copies paramCopies) {
/*  735 */     synchronized (this) {
/*  736 */       if (!this.gotCopies) {
/*  737 */         this.nCopies = getCopiesSupported(this.printer, getPort());
/*  738 */         this.gotCopies = true;
/*      */       } 
/*      */     } 
/*  741 */     int i = paramCopies.getValue();
/*  742 */     return (i > 0 && i <= this.nCopies);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isSupportedMedia(MediaSizeName paramMediaSizeName) {
/*  747 */     initMedia();
/*      */     
/*  749 */     if (this.mediaSizeNames != null) {
/*  750 */       for (byte b = 0; b < this.mediaSizeNames.length; b++) {
/*  751 */         if (paramMediaSizeName.equals(this.mediaSizeNames[b])) {
/*  752 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  756 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isSupportedMediaPrintableArea(MediaPrintableArea paramMediaPrintableArea) {
/*  761 */     getMediaPrintables(null);
/*      */     
/*  763 */     if (this.mediaPrintables != null) {
/*  764 */       for (byte b = 0; b < this.mediaPrintables.length; b++) {
/*  765 */         if (paramMediaPrintableArea.equals(this.mediaPrintables[b])) {
/*  766 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isSupportedMediaTray(MediaTray paramMediaTray) {
/*  774 */     MediaTray[] arrayOfMediaTray = getMediaTrays();
/*      */     
/*  776 */     if (arrayOfMediaTray != null) {
/*  777 */       for (byte b = 0; b < arrayOfMediaTray.length; b++) {
/*  778 */         if (paramMediaTray.equals(arrayOfMediaTray[b])) {
/*  779 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  783 */     return false;
/*      */   }
/*      */   
/*      */   private int getPrinterCapabilities() {
/*  787 */     if (this.prnCaps == 0) {
/*  788 */       this.prnCaps = getCapabilities(this.printer, getPort());
/*      */     }
/*  790 */     return this.prnCaps;
/*      */   }
/*      */   
/*      */   private String getPort() {
/*  794 */     if (this.port == null) {
/*  795 */       this.port = getPrinterPort(this.printer);
/*      */     }
/*  797 */     return this.port;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] getDefaultPrinterSettings() {
/*  804 */     if (this.defaultSettings == null) {
/*  805 */       this.defaultSettings = getDefaultSettings(this.printer, getPort());
/*      */     }
/*  807 */     return this.defaultSettings;
/*      */   }
/*      */   
/*      */   private PrinterResolution[] getPrintResolutions() {
/*  811 */     if (this.printRes == null) {
/*  812 */       int[] arrayOfInt = getAllResolutions(this.printer, getPort());
/*  813 */       if (arrayOfInt == null) {
/*  814 */         this.printRes = new PrinterResolution[0];
/*      */       } else {
/*  816 */         int i = arrayOfInt.length / 2;
/*      */         
/*  818 */         ArrayList<PrinterResolution> arrayList = new ArrayList();
/*      */ 
/*      */         
/*  821 */         for (byte b = 0; b < i; b++) {
/*      */           try {
/*  823 */             PrinterResolution printerResolution = new PrinterResolution(arrayOfInt[b * 2], arrayOfInt[b * 2 + 1], 100);
/*      */             
/*  825 */             arrayList.add(printerResolution);
/*  826 */           } catch (IllegalArgumentException illegalArgumentException) {}
/*      */         } 
/*      */ 
/*      */         
/*  830 */         this.printRes = arrayList.<PrinterResolution>toArray(
/*  831 */             new PrinterResolution[arrayList.size()]);
/*      */       } 
/*      */     } 
/*  834 */     return this.printRes;
/*      */   }
/*      */   
/*      */   private boolean isSupportedResolution(PrinterResolution paramPrinterResolution) {
/*  838 */     PrinterResolution[] arrayOfPrinterResolution = getPrintResolutions();
/*  839 */     if (arrayOfPrinterResolution != null) {
/*  840 */       for (byte b = 0; b < arrayOfPrinterResolution.length; b++) {
/*  841 */         if (paramPrinterResolution.equals(arrayOfPrinterResolution[b])) {
/*  842 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  846 */     return false;
/*      */   }
/*      */   
/*      */   public DocPrintJob createPrintJob() {
/*  850 */     SecurityManager securityManager = System.getSecurityManager();
/*  851 */     if (securityManager != null) {
/*  852 */       securityManager.checkPrintJobAccess();
/*      */     }
/*  854 */     return new Win32PrintJob(this);
/*      */   }
/*      */   
/*      */   private PrintServiceAttributeSet getDynamicAttributes() {
/*  858 */     HashPrintServiceAttributeSet hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet();
/*  859 */     hashPrintServiceAttributeSet.add(getPrinterIsAcceptingJobs());
/*  860 */     hashPrintServiceAttributeSet.add(getQueuedJobCount());
/*  861 */     return hashPrintServiceAttributeSet;
/*      */   }
/*      */   
/*      */   public PrintServiceAttributeSet getUpdatedAttributes() {
/*  865 */     PrintServiceAttributeSet printServiceAttributeSet = getDynamicAttributes();
/*  866 */     if (this.lastSet == null) {
/*  867 */       this.lastSet = printServiceAttributeSet;
/*  868 */       return AttributeSetUtilities.unmodifiableView(printServiceAttributeSet);
/*      */     } 
/*  870 */     HashPrintServiceAttributeSet hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet();
/*      */     
/*  872 */     Attribute[] arrayOfAttribute = printServiceAttributeSet.toArray();
/*  873 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*  874 */       Attribute attribute = arrayOfAttribute[b];
/*  875 */       if (!this.lastSet.containsValue(attribute)) {
/*  876 */         hashPrintServiceAttributeSet.add(attribute);
/*      */       }
/*      */     } 
/*  879 */     this.lastSet = printServiceAttributeSet;
/*  880 */     return AttributeSetUtilities.unmodifiableView(hashPrintServiceAttributeSet);
/*      */   }
/*      */ 
/*      */   
/*      */   public void wakeNotifier() {
/*  885 */     synchronized (this) {
/*  886 */       if (this.notifier != null) {
/*  887 */         this.notifier.wake();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addPrintServiceAttributeListener(PrintServiceAttributeListener paramPrintServiceAttributeListener) {
/*  894 */     synchronized (this) {
/*  895 */       if (paramPrintServiceAttributeListener == null) {
/*      */         return;
/*      */       }
/*  898 */       if (this.notifier == null) {
/*  899 */         this.notifier = new ServiceNotifier(this);
/*      */       }
/*  901 */       this.notifier.addListener(paramPrintServiceAttributeListener);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void removePrintServiceAttributeListener(PrintServiceAttributeListener paramPrintServiceAttributeListener) {
/*  907 */     synchronized (this) {
/*  908 */       if (paramPrintServiceAttributeListener == null || this.notifier == null) {
/*      */         return;
/*      */       }
/*  911 */       this.notifier.removeListener(paramPrintServiceAttributeListener);
/*  912 */       if (this.notifier.isEmpty()) {
/*  913 */         this.notifier.stopNotifier();
/*  914 */         this.notifier = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends PrintServiceAttribute> T getAttribute(Class<T> paramClass) {
/*  922 */     if (paramClass == null) {
/*  923 */       throw new NullPointerException("category");
/*      */     }
/*  925 */     if (!PrintServiceAttribute.class.isAssignableFrom(paramClass)) {
/*  926 */       throw new IllegalArgumentException("Not a PrintServiceAttribute");
/*      */     }
/*  928 */     if (paramClass == ColorSupported.class) {
/*  929 */       int i = getPrinterCapabilities();
/*  930 */       if ((i & 0x1) != 0) {
/*  931 */         return (T)ColorSupported.SUPPORTED;
/*      */       }
/*  933 */       return (T)ColorSupported.NOT_SUPPORTED;
/*      */     } 
/*  935 */     if (paramClass == PrinterName.class)
/*  936 */       return (T)getPrinterName(); 
/*  937 */     if (paramClass == PrinterState.class)
/*  938 */       return (T)getPrinterState(); 
/*  939 */     if (paramClass == PrinterStateReasons.class)
/*  940 */       return (T)getPrinterStateReasons(); 
/*  941 */     if (paramClass == QueuedJobCount.class)
/*  942 */       return (T)getQueuedJobCount(); 
/*  943 */     if (paramClass == PrinterIsAcceptingJobs.class) {
/*  944 */       return (T)getPrinterIsAcceptingJobs();
/*      */     }
/*  946 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PrintServiceAttributeSet getAttributes() {
/*  952 */     HashPrintServiceAttributeSet hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet();
/*  953 */     hashPrintServiceAttributeSet.add(getPrinterName());
/*  954 */     hashPrintServiceAttributeSet.add(getPrinterIsAcceptingJobs());
/*  955 */     PrinterState printerState = getPrinterState();
/*  956 */     if (printerState != null) {
/*  957 */       hashPrintServiceAttributeSet.add(printerState);
/*      */     }
/*  959 */     PrinterStateReasons printerStateReasons = getPrinterStateReasons();
/*  960 */     if (printerStateReasons != null) {
/*  961 */       hashPrintServiceAttributeSet.add(printerStateReasons);
/*      */     }
/*  963 */     hashPrintServiceAttributeSet.add(getQueuedJobCount());
/*  964 */     int i = getPrinterCapabilities();
/*  965 */     if ((i & 0x1) != 0) {
/*  966 */       hashPrintServiceAttributeSet.add(ColorSupported.SUPPORTED);
/*      */     } else {
/*  968 */       hashPrintServiceAttributeSet.add(ColorSupported.NOT_SUPPORTED);
/*      */     } 
/*      */     
/*  971 */     return AttributeSetUtilities.unmodifiableView(hashPrintServiceAttributeSet);
/*      */   }
/*      */   public DocFlavor[] getSupportedDocFlavors() {
/*      */     DocFlavor[] arrayOfDocFlavor;
/*  975 */     int i = supportedFlavors.length;
/*      */     
/*  977 */     int j = getPrinterCapabilities();
/*      */ 
/*      */     
/*  980 */     if ((j & 0x10) != 0) {
/*  981 */       arrayOfDocFlavor = new DocFlavor[i + 3];
/*  982 */       System.arraycopy(supportedFlavors, 0, arrayOfDocFlavor, 0, i);
/*  983 */       arrayOfDocFlavor[i] = DocFlavor.BYTE_ARRAY.POSTSCRIPT;
/*  984 */       arrayOfDocFlavor[i + 1] = DocFlavor.INPUT_STREAM.POSTSCRIPT;
/*  985 */       arrayOfDocFlavor[i + 2] = DocFlavor.URL.POSTSCRIPT;
/*      */     } else {
/*  987 */       arrayOfDocFlavor = new DocFlavor[i];
/*  988 */       System.arraycopy(supportedFlavors, 0, arrayOfDocFlavor, 0, i);
/*      */     } 
/*  990 */     return arrayOfDocFlavor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDocFlavorSupported(DocFlavor paramDocFlavor) {
/*      */     DocFlavor[] arrayOfDocFlavor;
/*  999 */     if (isPostScriptFlavor(paramDocFlavor)) {
/* 1000 */       arrayOfDocFlavor = getSupportedDocFlavors();
/*      */     } else {
/* 1002 */       arrayOfDocFlavor = supportedFlavors;
/*      */     } 
/* 1004 */     for (byte b = 0; b < arrayOfDocFlavor.length; b++) {
/* 1005 */       if (paramDocFlavor.equals(arrayOfDocFlavor[b])) {
/* 1006 */         return true;
/*      */       }
/*      */     } 
/* 1009 */     return false;
/*      */   }
/*      */   
/*      */   public Class<?>[] getSupportedAttributeCategories() {
/* 1013 */     ArrayList<Class<?>> arrayList = new ArrayList(otherAttrCats.length + 3); int i;
/* 1014 */     for (i = 0; i < otherAttrCats.length; i++) {
/* 1015 */       arrayList.add(otherAttrCats[i]);
/*      */     }
/*      */     
/* 1018 */     i = getPrinterCapabilities();
/*      */     
/* 1020 */     if ((i & 0x2) != 0) {
/* 1021 */       arrayList.add(Sides.class);
/*      */     }
/*      */     
/* 1024 */     if ((i & 0x8) != 0) {
/* 1025 */       int[] arrayOfInt = getDefaultPrinterSettings();
/*      */       
/* 1027 */       if (arrayOfInt[3] >= -4 && arrayOfInt[3] < 0) {
/* 1028 */         arrayList.add(PrintQuality.class);
/*      */       }
/*      */     } 
/*      */     
/* 1032 */     PrinterResolution[] arrayOfPrinterResolution = getPrintResolutions();
/* 1033 */     if (arrayOfPrinterResolution != null && arrayOfPrinterResolution.length > 0) {
/* 1034 */       arrayList.add(PrinterResolution.class);
/*      */     }
/*      */     
/* 1037 */     return (Class[])arrayList.<Class<?>[]>toArray((Class<?>[][])new Class[arrayList.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttributeCategorySupported(Class<? extends Attribute> paramClass) {
/* 1044 */     if (paramClass == null) {
/* 1045 */       throw new NullPointerException("null category");
/*      */     }
/*      */     
/* 1048 */     if (!Attribute.class.isAssignableFrom(paramClass)) {
/* 1049 */       throw new IllegalArgumentException(paramClass + " is not an Attribute");
/*      */     }
/*      */ 
/*      */     
/* 1053 */     Class[] arrayOfClass = getSupportedAttributeCategories();
/* 1054 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 1055 */       if (paramClass.equals(arrayOfClass[b])) {
/* 1056 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1060 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getDefaultAttributeValue(Class<? extends Attribute> paramClass) {
/* 1066 */     if (paramClass == null) {
/* 1067 */       throw new NullPointerException("null category");
/*      */     }
/* 1069 */     if (!Attribute.class.isAssignableFrom(paramClass)) {
/* 1070 */       throw new IllegalArgumentException(paramClass + " is not an Attribute");
/*      */     }
/*      */ 
/*      */     
/* 1074 */     if (!isAttributeCategorySupported(paramClass)) {
/* 1075 */       return null;
/*      */     }
/*      */     
/* 1078 */     int[] arrayOfInt = getDefaultPrinterSettings();
/*      */     
/* 1080 */     int i = arrayOfInt[0];
/* 1081 */     int j = arrayOfInt[2];
/* 1082 */     int k = arrayOfInt[3];
/* 1083 */     int m = arrayOfInt[4];
/* 1084 */     int n = arrayOfInt[5];
/* 1085 */     int i1 = arrayOfInt[6];
/* 1086 */     int i2 = arrayOfInt[7];
/* 1087 */     int i3 = arrayOfInt[8];
/*      */     
/* 1089 */     if (paramClass == Copies.class) {
/* 1090 */       if (m > 0) {
/* 1091 */         return new Copies(m);
/*      */       }
/* 1093 */       return new Copies(1);
/*      */     } 
/* 1095 */     if (paramClass == Chromaticity.class) {
/* 1096 */       if (i3 == 2) {
/* 1097 */         return Chromaticity.COLOR;
/*      */       }
/* 1099 */       return Chromaticity.MONOCHROME;
/*      */     } 
/* 1101 */     if (paramClass == JobName.class)
/* 1102 */       return new JobName("Java Printing", null); 
/* 1103 */     if (paramClass == OrientationRequested.class) {
/* 1104 */       if (n == 2) {
/* 1105 */         return OrientationRequested.LANDSCAPE;
/*      */       }
/* 1107 */       return OrientationRequested.PORTRAIT;
/*      */     } 
/* 1109 */     if (paramClass == PageRanges.class)
/* 1110 */       return new PageRanges(1, 2147483647); 
/* 1111 */     if (paramClass == Media.class) {
/* 1112 */       MediaSizeName mediaSizeName = findWin32Media(i);
/* 1113 */       if (mediaSizeName != null) {
/* 1114 */         if (!isSupportedMedia(mediaSizeName) && this.mediaSizeNames != null) {
/* 1115 */           mediaSizeName = this.mediaSizeNames[0];
/* 1116 */           i = findPaperID(mediaSizeName);
/*      */         } 
/* 1118 */         return mediaSizeName;
/*      */       } 
/* 1120 */       initMedia();
/* 1121 */       if (this.mediaSizeNames != null && this.mediaSizeNames.length > 0) {
/*      */ 
/*      */         
/* 1124 */         if (this.idList != null && this.mediaSizes != null && this.idList
/* 1125 */           .size() == this.mediaSizes.length) {
/* 1126 */           Integer integer = Integer.valueOf(i);
/* 1127 */           int i4 = this.idList.indexOf(integer);
/* 1128 */           if (i4 >= 0 && i4 < this.mediaSizes.length) {
/* 1129 */             return this.mediaSizes[i4].getMediaSizeName();
/*      */           }
/*      */         } 
/*      */         
/* 1133 */         return this.mediaSizeNames[0];
/*      */       } 
/*      */     } else {
/* 1136 */       if (paramClass == MediaPrintableArea.class) {
/*      */         
/* 1138 */         MediaSizeName mediaSizeName = findWin32Media(i);
/* 1139 */         if (mediaSizeName != null && 
/* 1140 */           !isSupportedMedia(mediaSizeName) && this.mediaSizeNames != null) {
/* 1141 */           i = findPaperID(this.mediaSizeNames[0]);
/*      */         }
/* 1143 */         float[] arrayOfFloat = getMediaPrintableArea(this.printer, i);
/* 1144 */         if (arrayOfFloat != null) {
/* 1145 */           MediaPrintableArea mediaPrintableArea = null;
/*      */           try {
/* 1147 */             mediaPrintableArea = new MediaPrintableArea(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], 25400);
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 1152 */           catch (IllegalArgumentException illegalArgumentException) {}
/*      */           
/* 1154 */           return mediaPrintableArea;
/*      */         } 
/* 1156 */         return null;
/* 1157 */       }  if (paramClass == SunAlternateMedia.class)
/* 1158 */         return null; 
/* 1159 */       if (paramClass == Destination.class)
/*      */         try {
/* 1161 */           return new Destination((new File("out.prn")).toURI());
/* 1162 */         } catch (SecurityException securityException) {
/*      */           try {
/* 1164 */             return new Destination(new URI("file:out.prn"));
/* 1165 */           } catch (URISyntaxException uRISyntaxException) {
/* 1166 */             return null;
/*      */           } 
/*      */         }  
/* 1169 */       if (paramClass == Sides.class) {
/* 1170 */         switch (i1) {
/*      */           case 2:
/* 1172 */             return Sides.TWO_SIDED_LONG_EDGE;
/*      */           case 3:
/* 1174 */             return Sides.TWO_SIDED_SHORT_EDGE;
/*      */         } 
/* 1176 */         return Sides.ONE_SIDED;
/*      */       } 
/* 1178 */       if (paramClass == PrinterResolution.class)
/* 1179 */       { int i4 = j;
/* 1180 */         int i5 = k;
/* 1181 */         if (i5 < 0 || i4 < 0) {
/* 1182 */           int i6 = (i4 > i5) ? i4 : i5;
/* 1183 */           if (i6 > 0) {
/* 1184 */             return new PrinterResolution(i6, i6, 100);
/*      */           }
/*      */         } else {
/*      */           
/* 1188 */           return new PrinterResolution(i5, i4, 100);
/*      */         }  }
/* 1190 */       else { if (paramClass == ColorSupported.class) {
/* 1191 */           int i4 = getPrinterCapabilities();
/* 1192 */           if ((i4 & 0x1) != 0) {
/* 1193 */             return ColorSupported.SUPPORTED;
/*      */           }
/* 1195 */           return ColorSupported.NOT_SUPPORTED;
/*      */         } 
/* 1197 */         if (paramClass == PrintQuality.class)
/* 1198 */         { if (k < 0 && k >= -4) {
/* 1199 */             switch (k) {
/*      */               case -4:
/* 1201 */                 return PrintQuality.HIGH;
/*      */               case -3:
/* 1203 */                 return PrintQuality.NORMAL;
/*      */             } 
/* 1205 */             return PrintQuality.DRAFT;
/*      */           }  }
/*      */         else
/* 1208 */         { if (paramClass == RequestingUserName.class) {
/* 1209 */             String str = "";
/*      */             try {
/* 1211 */               str = System.getProperty("user.name", "");
/* 1212 */             } catch (SecurityException securityException) {}
/*      */             
/* 1214 */             return new RequestingUserName(str, null);
/* 1215 */           }  if (paramClass == SheetCollate.class) {
/* 1216 */             if (i2 == 1) {
/* 1217 */               return SheetCollate.COLLATED;
/*      */             }
/* 1219 */             return SheetCollate.UNCOLLATED;
/*      */           } 
/* 1221 */           if (paramClass == Fidelity.class)
/* 1222 */             return Fidelity.FIDELITY_FALSE;  }  }
/*      */     
/* 1224 */     }  return null;
/*      */   }
/*      */   
/*      */   private boolean isPostScriptFlavor(DocFlavor paramDocFlavor) {
/* 1228 */     if (paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.POSTSCRIPT) || paramDocFlavor
/* 1229 */       .equals(DocFlavor.INPUT_STREAM.POSTSCRIPT) || paramDocFlavor
/* 1230 */       .equals(DocFlavor.URL.POSTSCRIPT)) {
/* 1231 */       return true;
/*      */     }
/*      */     
/* 1234 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isPSDocAttr(Class<OrientationRequested> paramClass) {
/* 1239 */     if (paramClass == OrientationRequested.class || paramClass == Copies.class) {
/* 1240 */       return true;
/*      */     }
/*      */     
/* 1243 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isAutoSense(DocFlavor paramDocFlavor) {
/* 1248 */     if (paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.AUTOSENSE) || paramDocFlavor
/* 1249 */       .equals(DocFlavor.INPUT_STREAM.AUTOSENSE) || paramDocFlavor
/* 1250 */       .equals(DocFlavor.URL.AUTOSENSE)) {
/* 1251 */       return true;
/*      */     }
/*      */     
/* 1254 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getSupportedAttributeValues(Class<? extends Attribute> paramClass, DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/* 1263 */     if (paramClass == null) {
/* 1264 */       throw new NullPointerException("null category");
/*      */     }
/* 1266 */     if (!Attribute.class.isAssignableFrom(paramClass)) {
/* 1267 */       throw new IllegalArgumentException(paramClass + " does not implement Attribute");
/*      */     }
/*      */     
/* 1270 */     if (paramDocFlavor != null) {
/* 1271 */       if (!isDocFlavorSupported(paramDocFlavor)) {
/* 1272 */         throw new IllegalArgumentException(paramDocFlavor + " is an unsupported flavor");
/*      */       }
/*      */ 
/*      */       
/* 1276 */       if (isAutoSense(paramDocFlavor) || (isPostScriptFlavor(paramDocFlavor) && 
/* 1277 */         isPSDocAttr(paramClass))) {
/* 1278 */         return null;
/*      */       }
/*      */     } 
/* 1281 */     if (!isAttributeCategorySupported(paramClass)) {
/* 1282 */       return null;
/*      */     }
/*      */     
/* 1285 */     if (paramClass == JobName.class)
/* 1286 */       return new JobName("Java Printing", null); 
/* 1287 */     if (paramClass == RequestingUserName.class) {
/* 1288 */       String str = "";
/*      */       try {
/* 1290 */         str = System.getProperty("user.name", "");
/* 1291 */       } catch (SecurityException securityException) {}
/*      */       
/* 1293 */       return new RequestingUserName(str, null);
/* 1294 */     }  if (paramClass == ColorSupported.class) {
/* 1295 */       int i = getPrinterCapabilities();
/* 1296 */       if ((i & 0x1) != 0) {
/* 1297 */         return ColorSupported.SUPPORTED;
/*      */       }
/* 1299 */       return ColorSupported.NOT_SUPPORTED;
/*      */     } 
/* 1301 */     if (paramClass == Chromaticity.class) {
/* 1302 */       if (paramDocFlavor == null || paramDocFlavor
/* 1303 */         .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/* 1304 */         .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE) || paramDocFlavor
/* 1305 */         .equals(DocFlavor.BYTE_ARRAY.GIF) || paramDocFlavor
/* 1306 */         .equals(DocFlavor.INPUT_STREAM.GIF) || paramDocFlavor
/* 1307 */         .equals(DocFlavor.URL.GIF) || paramDocFlavor
/* 1308 */         .equals(DocFlavor.BYTE_ARRAY.JPEG) || paramDocFlavor
/* 1309 */         .equals(DocFlavor.INPUT_STREAM.JPEG) || paramDocFlavor
/* 1310 */         .equals(DocFlavor.URL.JPEG) || paramDocFlavor
/* 1311 */         .equals(DocFlavor.BYTE_ARRAY.PNG) || paramDocFlavor
/* 1312 */         .equals(DocFlavor.INPUT_STREAM.PNG) || paramDocFlavor
/* 1313 */         .equals(DocFlavor.URL.PNG)) {
/* 1314 */         int i = getPrinterCapabilities();
/* 1315 */         if ((i & 0x1) == 0) {
/* 1316 */           Chromaticity[] arrayOfChromaticity1 = new Chromaticity[1];
/* 1317 */           arrayOfChromaticity1[0] = Chromaticity.MONOCHROME;
/* 1318 */           return arrayOfChromaticity1;
/*      */         } 
/* 1320 */         Chromaticity[] arrayOfChromaticity = new Chromaticity[2];
/* 1321 */         arrayOfChromaticity[0] = Chromaticity.MONOCHROME;
/* 1322 */         arrayOfChromaticity[1] = Chromaticity.COLOR;
/* 1323 */         return arrayOfChromaticity;
/*      */       } 
/*      */       
/* 1326 */       return null;
/*      */     } 
/* 1328 */     if (paramClass == Destination.class)
/*      */       try {
/* 1330 */         return new Destination((new File("out.prn")).toURI());
/* 1331 */       } catch (SecurityException securityException) {
/*      */         try {
/* 1333 */           return new Destination(new URI("file:out.prn"));
/* 1334 */         } catch (URISyntaxException uRISyntaxException) {
/* 1335 */           return null;
/*      */         } 
/*      */       }  
/* 1338 */     if (paramClass == OrientationRequested.class) {
/* 1339 */       if (paramDocFlavor == null || paramDocFlavor
/* 1340 */         .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/* 1341 */         .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE) || paramDocFlavor
/* 1342 */         .equals(DocFlavor.INPUT_STREAM.GIF) || paramDocFlavor
/* 1343 */         .equals(DocFlavor.INPUT_STREAM.JPEG) || paramDocFlavor
/* 1344 */         .equals(DocFlavor.INPUT_STREAM.PNG) || paramDocFlavor
/* 1345 */         .equals(DocFlavor.BYTE_ARRAY.GIF) || paramDocFlavor
/* 1346 */         .equals(DocFlavor.BYTE_ARRAY.JPEG) || paramDocFlavor
/* 1347 */         .equals(DocFlavor.BYTE_ARRAY.PNG) || paramDocFlavor
/* 1348 */         .equals(DocFlavor.URL.GIF) || paramDocFlavor
/* 1349 */         .equals(DocFlavor.URL.JPEG) || paramDocFlavor
/* 1350 */         .equals(DocFlavor.URL.PNG)) {
/* 1351 */         OrientationRequested[] arrayOfOrientationRequested = new OrientationRequested[3];
/* 1352 */         arrayOfOrientationRequested[0] = OrientationRequested.PORTRAIT;
/* 1353 */         arrayOfOrientationRequested[1] = OrientationRequested.LANDSCAPE;
/* 1354 */         arrayOfOrientationRequested[2] = OrientationRequested.REVERSE_LANDSCAPE;
/* 1355 */         return arrayOfOrientationRequested;
/*      */       } 
/* 1357 */       return null;
/*      */     } 
/* 1359 */     if (paramClass == Copies.class || paramClass == CopiesSupported.class) {
/*      */       
/* 1361 */       synchronized (this) {
/* 1362 */         if (!this.gotCopies) {
/* 1363 */           this.nCopies = getCopiesSupported(this.printer, getPort());
/* 1364 */           this.gotCopies = true;
/*      */         } 
/*      */       } 
/* 1367 */       return new CopiesSupported(1, this.nCopies);
/* 1368 */     }  if (paramClass == Media.class) {
/*      */       
/* 1370 */       initMedia();
/*      */       
/* 1372 */       int i = (this.mediaSizeNames == null) ? 0 : this.mediaSizeNames.length;
/*      */       
/* 1374 */       MediaTray[] arrayOfMediaTray = getMediaTrays();
/*      */       
/* 1376 */       i += (arrayOfMediaTray == null) ? 0 : arrayOfMediaTray.length;
/*      */       
/* 1378 */       Media[] arrayOfMedia = new Media[i];
/* 1379 */       if (this.mediaSizeNames != null) {
/* 1380 */         System.arraycopy(this.mediaSizeNames, 0, arrayOfMedia, 0, this.mediaSizeNames.length);
/*      */       }
/*      */       
/* 1383 */       if (arrayOfMediaTray != null) {
/* 1384 */         System.arraycopy(arrayOfMediaTray, 0, arrayOfMedia, i - arrayOfMediaTray.length, arrayOfMediaTray.length);
/*      */       }
/*      */       
/* 1387 */       return arrayOfMedia;
/* 1388 */     }  if (paramClass == MediaPrintableArea.class) {
/*      */       
/* 1390 */       Media media = null;
/* 1391 */       if (paramAttributeSet != null && (
/*      */         
/* 1393 */         media = (Media)paramAttributeSet.get(Media.class)) != null)
/*      */       {
/* 1395 */         if (!(media instanceof MediaSizeName))
/*      */         {
/*      */           
/* 1398 */           media = null;
/*      */         }
/*      */       }
/*      */ 
/*      */       
/* 1403 */       MediaPrintableArea[] arrayOfMediaPrintableArea = getMediaPrintables((MediaSizeName)media);
/* 1404 */       if (arrayOfMediaPrintableArea != null) {
/* 1405 */         MediaPrintableArea[] arrayOfMediaPrintableArea1 = new MediaPrintableArea[arrayOfMediaPrintableArea.length];
/* 1406 */         System.arraycopy(arrayOfMediaPrintableArea, 0, arrayOfMediaPrintableArea1, 0, arrayOfMediaPrintableArea.length);
/* 1407 */         return arrayOfMediaPrintableArea1;
/*      */       } 
/* 1409 */       return null;
/*      */     } 
/* 1411 */     if (paramClass == SunAlternateMedia.class)
/* 1412 */       return new SunAlternateMedia((Media)
/* 1413 */           getDefaultAttributeValue((Class)Media.class)); 
/* 1414 */     if (paramClass == PageRanges.class) {
/* 1415 */       if (paramDocFlavor == null || paramDocFlavor
/* 1416 */         .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/* 1417 */         .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/* 1418 */         PageRanges[] arrayOfPageRanges = new PageRanges[1];
/* 1419 */         arrayOfPageRanges[0] = new PageRanges(1, 2147483647);
/* 1420 */         return arrayOfPageRanges;
/*      */       } 
/* 1422 */       return null;
/*      */     } 
/* 1424 */     if (paramClass == PrinterResolution.class) {
/* 1425 */       PrinterResolution[] arrayOfPrinterResolution1 = getPrintResolutions();
/* 1426 */       if (arrayOfPrinterResolution1 == null) {
/* 1427 */         return null;
/*      */       }
/* 1429 */       PrinterResolution[] arrayOfPrinterResolution2 = new PrinterResolution[arrayOfPrinterResolution1.length];
/*      */       
/* 1431 */       System.arraycopy(arrayOfPrinterResolution1, 0, arrayOfPrinterResolution2, 0, arrayOfPrinterResolution1.length);
/* 1432 */       return arrayOfPrinterResolution2;
/* 1433 */     }  if (paramClass == Sides.class) {
/* 1434 */       if (paramDocFlavor == null || paramDocFlavor
/* 1435 */         .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/* 1436 */         .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/* 1437 */         Sides[] arrayOfSides = new Sides[3];
/* 1438 */         arrayOfSides[0] = Sides.ONE_SIDED;
/* 1439 */         arrayOfSides[1] = Sides.TWO_SIDED_LONG_EDGE;
/* 1440 */         arrayOfSides[2] = Sides.TWO_SIDED_SHORT_EDGE;
/* 1441 */         return arrayOfSides;
/*      */       } 
/* 1443 */       return null;
/*      */     } 
/* 1445 */     if (paramClass == PrintQuality.class) {
/* 1446 */       PrintQuality[] arrayOfPrintQuality = new PrintQuality[3];
/* 1447 */       arrayOfPrintQuality[0] = PrintQuality.DRAFT;
/* 1448 */       arrayOfPrintQuality[1] = PrintQuality.HIGH;
/* 1449 */       arrayOfPrintQuality[2] = PrintQuality.NORMAL;
/* 1450 */       return arrayOfPrintQuality;
/* 1451 */     }  if (paramClass == SheetCollate.class) {
/* 1452 */       if (paramDocFlavor == null || paramDocFlavor
/* 1453 */         .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/* 1454 */         .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/* 1455 */         SheetCollate[] arrayOfSheetCollate = new SheetCollate[2];
/* 1456 */         arrayOfSheetCollate[0] = SheetCollate.COLLATED;
/* 1457 */         arrayOfSheetCollate[1] = SheetCollate.UNCOLLATED;
/* 1458 */         return arrayOfSheetCollate;
/*      */       } 
/* 1460 */       return null;
/*      */     } 
/* 1462 */     if (paramClass == Fidelity.class) {
/* 1463 */       Fidelity[] arrayOfFidelity = new Fidelity[2];
/* 1464 */       arrayOfFidelity[0] = Fidelity.FIDELITY_FALSE;
/* 1465 */       arrayOfFidelity[1] = Fidelity.FIDELITY_TRUE;
/* 1466 */       return arrayOfFidelity;
/*      */     } 
/* 1468 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttributeValueSupported(Attribute paramAttribute, DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/* 1476 */     if (paramAttribute == null) {
/* 1477 */       throw new NullPointerException("null attribute");
/*      */     }
/* 1479 */     Class<? extends Attribute> clazz = paramAttribute.getCategory();
/* 1480 */     if (paramDocFlavor != null) {
/* 1481 */       if (!isDocFlavorSupported(paramDocFlavor)) {
/* 1482 */         throw new IllegalArgumentException(paramDocFlavor + " is an unsupported flavor");
/*      */       }
/*      */ 
/*      */       
/* 1486 */       if (isAutoSense(paramDocFlavor) || (isPostScriptFlavor(paramDocFlavor) && 
/* 1487 */         isPSDocAttr(clazz))) {
/* 1488 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1492 */     if (!isAttributeCategorySupported(clazz)) {
/* 1493 */       return false;
/*      */     }
/* 1495 */     if (clazz == Chromaticity.class) {
/* 1496 */       if (paramDocFlavor == null || paramDocFlavor
/* 1497 */         .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/* 1498 */         .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE) || paramDocFlavor
/* 1499 */         .equals(DocFlavor.BYTE_ARRAY.GIF) || paramDocFlavor
/* 1500 */         .equals(DocFlavor.INPUT_STREAM.GIF) || paramDocFlavor
/* 1501 */         .equals(DocFlavor.URL.GIF) || paramDocFlavor
/* 1502 */         .equals(DocFlavor.BYTE_ARRAY.JPEG) || paramDocFlavor
/* 1503 */         .equals(DocFlavor.INPUT_STREAM.JPEG) || paramDocFlavor
/* 1504 */         .equals(DocFlavor.URL.JPEG) || paramDocFlavor
/* 1505 */         .equals(DocFlavor.BYTE_ARRAY.PNG) || paramDocFlavor
/* 1506 */         .equals(DocFlavor.INPUT_STREAM.PNG) || paramDocFlavor
/* 1507 */         .equals(DocFlavor.URL.PNG)) {
/* 1508 */         int i = getPrinterCapabilities();
/* 1509 */         if ((i & 0x1) != 0) {
/* 1510 */           return true;
/*      */         }
/* 1512 */         return (paramAttribute == Chromaticity.MONOCHROME);
/*      */       } 
/*      */       
/* 1515 */       return false;
/*      */     } 
/* 1517 */     if (clazz == Copies.class) {
/* 1518 */       return isSupportedCopies((Copies)paramAttribute);
/*      */     }
/* 1520 */     if (clazz == Destination.class) {
/* 1521 */       URI uRI = ((Destination)paramAttribute).getURI();
/* 1522 */       if ("file".equals(uRI.getScheme()) && 
/* 1523 */         !uRI.getSchemeSpecificPart().equals("")) {
/* 1524 */         return true;
/*      */       }
/* 1526 */       return false;
/*      */     } 
/*      */     
/* 1529 */     if (clazz == Media.class) {
/* 1530 */       if (paramAttribute instanceof MediaSizeName) {
/* 1531 */         return isSupportedMedia((MediaSizeName)paramAttribute);
/*      */       }
/* 1533 */       if (paramAttribute instanceof MediaTray) {
/* 1534 */         return isSupportedMediaTray((MediaTray)paramAttribute);
/*      */       }
/*      */     } else {
/* 1537 */       if (clazz == MediaPrintableArea.class) {
/* 1538 */         return isSupportedMediaPrintableArea((MediaPrintableArea)paramAttribute);
/*      */       }
/* 1540 */       if (clazz == SunAlternateMedia.class) {
/* 1541 */         Media media = ((SunAlternateMedia)paramAttribute).getMedia();
/* 1542 */         return isAttributeValueSupported(media, paramDocFlavor, paramAttributeSet);
/*      */       } 
/* 1544 */       if (clazz == PageRanges.class || clazz == SheetCollate.class || clazz == Sides.class) {
/*      */ 
/*      */         
/* 1547 */         if (paramDocFlavor != null && 
/* 1548 */           !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) && 
/* 1549 */           !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/* 1550 */           return false;
/*      */         }
/* 1552 */       } else if (clazz == PrinterResolution.class) {
/* 1553 */         if (paramAttribute instanceof PrinterResolution) {
/* 1554 */           return isSupportedResolution((PrinterResolution)paramAttribute);
/*      */         }
/* 1556 */       } else if (clazz == OrientationRequested.class) {
/* 1557 */         if (paramAttribute == OrientationRequested.REVERSE_PORTRAIT || (paramDocFlavor != null && 
/*      */           
/* 1559 */           !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) && 
/* 1560 */           !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE) && 
/* 1561 */           !paramDocFlavor.equals(DocFlavor.INPUT_STREAM.GIF) && 
/* 1562 */           !paramDocFlavor.equals(DocFlavor.INPUT_STREAM.JPEG) && 
/* 1563 */           !paramDocFlavor.equals(DocFlavor.INPUT_STREAM.PNG) && 
/* 1564 */           !paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.GIF) && 
/* 1565 */           !paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.JPEG) && 
/* 1566 */           !paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.PNG) && 
/* 1567 */           !paramDocFlavor.equals(DocFlavor.URL.GIF) && 
/* 1568 */           !paramDocFlavor.equals(DocFlavor.URL.JPEG) && 
/* 1569 */           !paramDocFlavor.equals(DocFlavor.URL.PNG))) {
/* 1570 */           return false;
/*      */         }
/*      */       }
/* 1573 */       else if (clazz == ColorSupported.class) {
/* 1574 */         int i = getPrinterCapabilities();
/* 1575 */         boolean bool = ((i & 0x1) != 0) ? true : false;
/* 1576 */         if ((!bool && paramAttribute == ColorSupported.SUPPORTED) || (bool && paramAttribute == ColorSupported.NOT_SUPPORTED))
/*      */         {
/* 1578 */           return false; } 
/*      */       } 
/*      */     } 
/* 1581 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeSet getUnsupportedAttributes(DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/* 1587 */     if (paramDocFlavor != null && !isDocFlavorSupported(paramDocFlavor)) {
/* 1588 */       throw new IllegalArgumentException("flavor " + paramDocFlavor + "is not supported");
/*      */     }
/*      */ 
/*      */     
/* 1592 */     if (paramAttributeSet == null) {
/* 1593 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1597 */     HashAttributeSet hashAttributeSet = new HashAttributeSet();
/* 1598 */     Attribute[] arrayOfAttribute = paramAttributeSet.toArray();
/* 1599 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*      */       try {
/* 1601 */         Attribute attribute = arrayOfAttribute[b];
/* 1602 */         if (!isAttributeCategorySupported(attribute.getCategory())) {
/* 1603 */           hashAttributeSet.add(attribute);
/*      */         }
/* 1605 */         else if (!isAttributeValueSupported(attribute, paramDocFlavor, paramAttributeSet)) {
/* 1606 */           hashAttributeSet.add(attribute);
/*      */         } 
/* 1608 */       } catch (ClassCastException classCastException) {}
/*      */     } 
/*      */     
/* 1611 */     if (hashAttributeSet.isEmpty()) {
/* 1612 */       return null;
/*      */     }
/* 1614 */     return hashAttributeSet;
/*      */   }
/*      */   
/*      */   Win32PrintService(String paramString) {
/* 1618 */     this.docPropertiesUI = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1681 */     this.uiFactory = null; if (paramString == null) throw new IllegalArgumentException("null printer name");  this.printer = paramString; this.mediaInitialized = false; this.gotTrays = false; this.gotCopies = false; this.isInvalid = false; this.printRes = null; this.prnCaps = 0; this.defaultSettings = null; this.port = null;
/*      */   }
/*      */   private static class Win32DocumentPropertiesUI extends DocumentPropertiesUI {
/* 1684 */     Win32PrintService service; private Win32DocumentPropertiesUI(Win32PrintService param1Win32PrintService) { this.service = param1Win32PrintService; } public PrintRequestAttributeSet showDocumentProperties(PrinterJob param1PrinterJob, Window param1Window, PrintService param1PrintService, PrintRequestAttributeSet param1PrintRequestAttributeSet) { if (!(param1PrinterJob instanceof WPrinterJob)) return null;  WPrinterJob wPrinterJob = (WPrinterJob)param1PrinterJob; return wPrinterJob.showDocumentProperties(param1Window, param1PrintService, param1PrintRequestAttributeSet); } } public synchronized ServiceUIFactory getServiceUIFactory() { if (this.uiFactory == null) {
/* 1685 */       this.uiFactory = new Win32ServiceUIFactory(this);
/*      */     }
/* 1687 */     return this.uiFactory; }
/*      */   private synchronized DocumentPropertiesUI getDocumentPropertiesUI() { return new Win32DocumentPropertiesUI(this); }
/*      */   private static class Win32ServiceUIFactory extends ServiceUIFactory {
/*      */     Win32PrintService service;
/* 1691 */     Win32ServiceUIFactory(Win32PrintService param1Win32PrintService) { this.service = param1Win32PrintService; } public Object getUI(int param1Int, String param1String) { if (param1Int <= 3) return null;  if (param1Int == 199 && DocumentPropertiesUI.DOCPROPERTIESCLASSNAME.equals(param1String)) return this.service.getDocumentPropertiesUI();  throw new IllegalArgumentException("Unsupported role"); } public String[] getUIClassNamesForRole(int param1Int) { if (param1Int <= 3) return null;  if (param1Int == 199) { String[] arrayOfString = new String[0]; arrayOfString[0] = DocumentPropertiesUI.DOCPROPERTIESCLASSNAME; return arrayOfString; }  throw new IllegalArgumentException("Unsupported role"); } } public String toString() { return "Win32 Printer : " + getName(); }
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 1695 */     return (paramObject == this || (paramObject instanceof Win32PrintService && ((Win32PrintService)paramObject)
/*      */       
/* 1697 */       .getName().equals(getName())));
/*      */   }
/*      */   
/*      */   public int hashCode() {
/* 1701 */     return getClass().hashCode() + getName().hashCode();
/*      */   }
/*      */   
/*      */   public boolean usesClass(Class<WPrinterJob> paramClass) {
/* 1705 */     return (paramClass == WPrinterJob.class);
/*      */   }
/*      */   
/*      */   private native int[] getAllMediaIDs(String paramString1, String paramString2);
/*      */   
/*      */   private native int[] getAllMediaSizes(String paramString1, String paramString2);
/*      */   
/*      */   private native int[] getAllMediaTrays(String paramString1, String paramString2);
/*      */   
/*      */   private native float[] getMediaPrintableArea(String paramString, int paramInt);
/*      */   
/*      */   private native String[] getAllMediaNames(String paramString1, String paramString2);
/*      */   
/*      */   private native String[] getAllMediaTrayNames(String paramString1, String paramString2);
/*      */   
/*      */   private native int getCopiesSupported(String paramString1, String paramString2);
/*      */   
/*      */   private native int[] getAllResolutions(String paramString1, String paramString2);
/*      */   
/*      */   private native int getCapabilities(String paramString1, String paramString2);
/*      */   
/*      */   private native int[] getDefaultSettings(String paramString1, String paramString2);
/*      */   
/*      */   private native int getJobStatus(String paramString, int paramInt);
/*      */   
/*      */   private native String getPrinterPort(String paramString);
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\print\Win32PrintService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */