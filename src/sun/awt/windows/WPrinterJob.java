/*      */ package sun.awt.windows;
/*      */ 
/*      */ import java.awt.Button;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FileDialog;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Label;
/*      */ import java.awt.Panel;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Pageable;
/*      */ import java.awt.print.Paper;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.File;
/*      */ import java.io.FilePermission;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.PrintServiceLookup;
/*      */ import javax.print.attribute.Attribute;
/*      */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*      */ import javax.print.attribute.PrintRequestAttributeSet;
/*      */ import javax.print.attribute.standard.Chromaticity;
/*      */ import javax.print.attribute.standard.Copies;
/*      */ import javax.print.attribute.standard.Destination;
/*      */ import javax.print.attribute.standard.Media;
/*      */ import javax.print.attribute.standard.MediaSize;
/*      */ import javax.print.attribute.standard.MediaSizeName;
/*      */ import javax.print.attribute.standard.MediaTray;
/*      */ import javax.print.attribute.standard.OrientationRequested;
/*      */ import javax.print.attribute.standard.PageRanges;
/*      */ import javax.print.attribute.standard.PrintQuality;
/*      */ import javax.print.attribute.standard.PrinterResolution;
/*      */ import javax.print.attribute.standard.SheetCollate;
/*      */ import javax.print.attribute.standard.Sides;
/*      */ import sun.awt.Win32FontManager;
/*      */ import sun.java2d.Disposer;
/*      */ import sun.java2d.DisposerRecord;
/*      */ import sun.java2d.DisposerTarget;
/*      */ import sun.print.DialogOwner;
/*      */ import sun.print.PeekGraphics;
/*      */ import sun.print.PeekMetrics;
/*      */ import sun.print.RasterPrinterJob;
/*      */ import sun.print.ServiceDialog;
/*      */ import sun.print.SunAlternateMedia;
/*      */ import sun.print.SunPageSelection;
/*      */ import sun.print.Win32MediaTray;
/*      */ import sun.print.Win32PrintService;
/*      */ import sun.print.Win32PrintServiceLookup;
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
/*      */ public final class WPrinterJob
/*      */   extends RasterPrinterJob
/*      */   implements DisposerTarget
/*      */ {
/*      */   protected static final long PS_ENDCAP_ROUND = 0L;
/*      */   protected static final long PS_ENDCAP_SQUARE = 256L;
/*      */   protected static final long PS_ENDCAP_FLAT = 512L;
/*      */   protected static final long PS_JOIN_ROUND = 0L;
/*      */   protected static final long PS_JOIN_BEVEL = 4096L;
/*      */   protected static final long PS_JOIN_MITER = 8192L;
/*      */   protected static final int POLYFILL_ALTERNATE = 1;
/*      */   protected static final int POLYFILL_WINDING = 2;
/*      */   private static final int MAX_WCOLOR = 255;
/*      */   private static final int SET_DUP_VERTICAL = 16;
/*      */   private static final int SET_DUP_HORIZONTAL = 32;
/*      */   private static final int SET_RES_HIGH = 64;
/*      */   private static final int SET_RES_LOW = 128;
/*      */   private static final int SET_COLOR = 512;
/*      */   private static final int SET_ORIENTATION = 16384;
/*      */   private static final int SET_COLLATED = 32768;
/*      */   private static final int PD_COLLATE = 16;
/*      */   private static final int PD_PRINTTOFILE = 32;
/*      */   private static final int DM_ORIENTATION = 1;
/*      */   private static final int DM_PAPERSIZE = 2;
/*      */   private static final int DM_COPIES = 256;
/*      */   private static final int DM_DEFAULTSOURCE = 512;
/*      */   private static final int DM_PRINTQUALITY = 1024;
/*      */   private static final int DM_COLOR = 2048;
/*      */   private static final int DM_DUPLEX = 4096;
/*      */   private static final int DM_YRESOLUTION = 8192;
/*      */   private static final int DM_COLLATE = 32768;
/*      */   private static final short DMCOLLATE_FALSE = 0;
/*      */   private static final short DMCOLLATE_TRUE = 1;
/*      */   private static final short DMORIENT_PORTRAIT = 1;
/*      */   private static final short DMORIENT_LANDSCAPE = 2;
/*      */   private static final short DMCOLOR_MONOCHROME = 1;
/*      */   private static final short DMCOLOR_COLOR = 2;
/*      */   private static final short DMRES_DRAFT = -1;
/*      */   private static final short DMRES_LOW = -2;
/*      */   private static final short DMRES_MEDIUM = -3;
/*      */   private static final short DMRES_HIGH = -4;
/*      */   private static final short DMDUP_SIMPLEX = 1;
/*      */   private static final short DMDUP_VERTICAL = 2;
/*      */   private static final short DMDUP_HORIZONTAL = 3;
/*      */   private static final int MAX_UNKNOWN_PAGES = 9999;
/*      */   private boolean driverDoesMultipleCopies = false;
/*      */   private boolean driverDoesCollation = false;
/*      */   private boolean userRequestedCollation = false;
/*      */   private boolean noDefaultPrinter = false;
/*      */   
/*      */   static class HandleRecord
/*      */     implements DisposerRecord
/*      */   {
/*      */     private long mPrintDC;
/*      */     private long mPrintHDevMode;
/*      */     private long mPrintHDevNames;
/*      */     
/*      */     public void dispose() {
/*  284 */       WPrinterJob.deleteDC(this.mPrintDC, this.mPrintHDevMode, this.mPrintHDevNames);
/*      */     }
/*      */   }
/*      */   
/*  288 */   private HandleRecord handleRecord = new HandleRecord();
/*      */ 
/*      */   
/*      */   private int mPrintPaperSize;
/*      */ 
/*      */   
/*      */   private int mPrintXRes;
/*      */ 
/*      */   
/*      */   private int mPrintYRes;
/*      */ 
/*      */   
/*      */   private int mPrintPhysX;
/*      */ 
/*      */   
/*      */   private int mPrintPhysY;
/*      */ 
/*      */   
/*      */   private int mPrintWidth;
/*      */ 
/*      */   
/*      */   private int mPrintHeight;
/*      */ 
/*      */   
/*      */   private int mPageWidth;
/*      */ 
/*      */   
/*      */   private int mPageHeight;
/*      */   
/*      */   private int mAttSides;
/*      */   
/*      */   private int mAttChromaticity;
/*      */   
/*      */   private int mAttXRes;
/*      */   
/*      */   private int mAttYRes;
/*      */   
/*      */   private int mAttQuality;
/*      */   
/*      */   private int mAttCollate;
/*      */   
/*      */   private int mAttCopies;
/*      */   
/*      */   private int mAttMediaSizeName;
/*      */   
/*      */   private int mAttMediaTray;
/*      */   
/*  335 */   private String mDestination = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private Color mLastColor;
/*      */ 
/*      */   
/*      */   private Color mLastTextColor;
/*      */ 
/*      */   
/*      */   private String mLastFontFamily;
/*      */ 
/*      */   
/*      */   private float mLastFontSize;
/*      */ 
/*      */   
/*      */   private int mLastFontStyle;
/*      */ 
/*      */   
/*      */   private int mLastRotation;
/*      */ 
/*      */   
/*      */   private float mLastAwScale;
/*      */ 
/*      */   
/*      */   private PrinterJob pjob;
/*      */ 
/*      */   
/*  363 */   private ComponentPeer dialogOwnerPeer = null;
/*      */   private Object disposerReferent;
/*      */   private String lastNativeService;
/*      */   private boolean defaultCopies;
/*      */   public Object getDisposerReferent() { return this.disposerReferent; }
/*      */   public PageFormat pageDialog(PageFormat paramPageFormat) throws HeadlessException { if (GraphicsEnvironment.isHeadless()) throw new HeadlessException();  if (!(getPrintService() instanceof Win32PrintService)) return super.pageDialog(paramPageFormat);  PageFormat pageFormat = (PageFormat)paramPageFormat.clone(); boolean bool = false; WPageDialog wPageDialog = new WPageDialog((Frame)null, this, pageFormat, null); wPageDialog.setRetVal(false); wPageDialog.setVisible(true); bool = wPageDialog.getRetVal(); wPageDialog.dispose(); if (bool && this.myService != null) { String str = getNativePrintService(); if (!this.myService.getName().equals(str)) try { setPrintService(Win32PrintServiceLookup.getWin32PrintLUS().getPrintServiceByName(str)); } catch (PrinterException printerException) {}  updatePageAttributes(this.myService, pageFormat); return pageFormat; }  return paramPageFormat; }
/*  369 */   private boolean displayNativeDialog() { if (this.attributes == null) return false;  DialogOwner dialogOwner = (DialogOwner)this.attributes.get(DialogOwner.class); Frame frame = (dialogOwner != null) ? dialogOwner.getOwner() : null; WPrintDialog wPrintDialog = new WPrintDialog(frame, this); wPrintDialog.setRetVal(false); wPrintDialog.setVisible(true); boolean bool = wPrintDialog.getRetVal(); wPrintDialog.dispose(); Destination destination = (Destination)this.attributes.get(Destination.class); if (destination == null || !bool) return bool;  String str1 = null; String str2 = "sun.print.resources.serviceui"; ResourceBundle resourceBundle = ResourceBundle.getBundle(str2); try { str1 = resourceBundle.getString("dialog.printtofile"); } catch (MissingResourceException missingResourceException) {} FileDialog fileDialog = new FileDialog(frame, str1, 1); URI uRI = destination.getURI(); String str3 = (uRI != null) ? uRI.getSchemeSpecificPart() : null; if (str3 != null) { File file3 = new File(str3); fileDialog.setFile(file3.getName()); File file4 = file3.getParentFile(); if (file4 != null) fileDialog.setDirectory(file4.getPath());  } else { fileDialog.setFile("out.prn"); }  fileDialog.setVisible(true); String str4 = fileDialog.getFile(); if (str4 == null) { fileDialog.dispose(); return false; }  String str5 = fileDialog.getDirectory() + str4; File file1 = new File(str5); File file2 = file1.getParentFile(); while ((file1.exists() && (!file1.isFile() || !file1.canWrite())) || (file2 != null && (!file2.exists() || (file2.exists() && !file2.canWrite())))) { (new PrintToFileErrorDialog(frame, ServiceDialog.getMsg("dialog.owtitle"), ServiceDialog.getMsg("dialog.writeerror") + " " + str5, ServiceDialog.getMsg("button.ok"))).setVisible(true); fileDialog.setVisible(true); str4 = fileDialog.getFile(); if (str4 == null) { fileDialog.dispose(); return false; }  str5 = fileDialog.getDirectory() + str4; file1 = new File(str5); file2 = file1.getParentFile(); }  fileDialog.dispose(); this.attributes.add(new Destination(file1.toURI())); return true; } public boolean printDialog() throws HeadlessException { if (GraphicsEnvironment.isHeadless()) throw new HeadlessException();  if (this.attributes == null) this.attributes = new HashPrintRequestAttributeSet();  if (!(getPrintService() instanceof Win32PrintService)) return printDialog(this.attributes);  if (this.noDefaultPrinter == true) return false;  return displayNativeDialog(); } public void setPrintService(PrintService paramPrintService) throws PrinterException { super.setPrintService(paramPrintService); if (!(paramPrintService instanceof Win32PrintService)) return;  this.driverDoesMultipleCopies = false; this.driverDoesCollation = false; setNativePrintServiceIfNeeded(paramPrintService.getName()); } private void setNativePrintServiceIfNeeded(String paramString) throws PrinterException { if (paramString != null && !paramString.equals(this.lastNativeService)) { setNativePrintService(paramString); this.lastNativeService = paramString; }  } public PrintService getPrintService() { if (this.myService == null) { String str = getNativePrintService(); if (str != null) { this.myService = Win32PrintServiceLookup.getWin32PrintLUS().getPrintServiceByName(str); if (this.myService != null) return this.myService;  }  this.myService = PrintServiceLookup.lookupDefaultPrintService(); if (this.myService instanceof Win32PrintService) try { setNativePrintServiceIfNeeded(this.myService.getName()); } catch (Exception exception) { this.myService = null; }   }  return this.myService; } static { Toolkit.getDefaultToolkit();
/*      */     
/*  371 */     initIDs();
/*      */     
/*  373 */     Win32FontManager.registerJREFontsForPrinting(); }
/*      */   private void initAttributeMembers() { this.mAttSides = 0; this.mAttChromaticity = 0; this.mAttXRes = 0; this.mAttYRes = 0; this.mAttQuality = 0; this.mAttCollate = -1; this.mAttCopies = 0; this.mAttMediaTray = 0; this.mAttMediaSizeName = 0; this.mDestination = null; }
/*      */   protected void setAttributes(PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrinterException { initAttributeMembers(); super.setAttributes(paramPrintRequestAttributeSet); this.mAttCopies = getCopiesInt(); this.mDestination = this.destinationAttr; if (paramPrintRequestAttributeSet == null)
/*      */       return;  Attribute[] arrayOfAttribute = paramPrintRequestAttributeSet.toArray(); for (byte b = 0; b < arrayOfAttribute.length; b++) { Attribute attribute = arrayOfAttribute[b]; try { if (attribute.getCategory() == Sides.class) { setSidesAttrib(attribute); } else if (attribute.getCategory() == Chromaticity.class) { setColorAttrib(attribute); } else if (attribute.getCategory() == PrinterResolution.class) { setResolutionAttrib(attribute); } else if (attribute.getCategory() == PrintQuality.class) { setQualityAttrib(attribute); } else if (attribute.getCategory() == SheetCollate.class) { setCollateAttrib(attribute); }
/*      */         else if (attribute.getCategory() == Media.class || attribute.getCategory() == SunAlternateMedia.class) { if (attribute.getCategory() == SunAlternateMedia.class) { Media media = (Media)paramPrintRequestAttributeSet.get(Media.class); if (media == null || !(media instanceof MediaTray))
/*      */               attribute = ((SunAlternateMedia)attribute).getMedia();  }
/*      */            if (attribute instanceof MediaSizeName)
/*      */             setWin32MediaAttrib(attribute);  if (attribute instanceof MediaTray)
/*      */             setMediaTrayAttrib(attribute);  }
/*      */          }
/*      */       catch (ClassCastException classCastException) {} }
/*      */      }
/*      */   public PageFormat defaultPage(PageFormat paramPageFormat) { PageFormat pageFormat = (PageFormat)paramPageFormat.clone(); getDefaultPage(pageFormat); return pageFormat; }
/*      */   protected Graphics2D createPathGraphics(PeekGraphics paramPeekGraphics, PrinterJob paramPrinterJob, Printable paramPrintable, PageFormat paramPageFormat, int paramInt) { WPathGraphics wPathGraphics; PeekMetrics peekMetrics = paramPeekGraphics.getMetrics(); if (!forcePDL && (forceRaster == true || peekMetrics.hasNonSolidColors() || peekMetrics.hasCompositing())) { wPathGraphics = null; }
/*      */     else { BufferedImage bufferedImage = new BufferedImage(8, 8, 1); Graphics2D graphics2D = bufferedImage.createGraphics(); boolean bool = !paramPeekGraphics.getAWTDrawingOnly() ? true : false; wPathGraphics = new WPathGraphics(graphics2D, paramPrinterJob, paramPrintable, paramPageFormat, paramInt, bool); }
/*      */      return wPathGraphics; }
/*      */   protected double getXRes() { if (this.mAttXRes != 0)
/*      */       return this.mAttXRes;  return this.mPrintXRes; }
/*      */   protected double getYRes() { if (this.mAttYRes != 0)
/*      */       return this.mAttYRes;  return this.mPrintYRes; }
/*  393 */   protected double getPhysicalPrintableX(Paper paramPaper) { return this.mPrintPhysX; } public WPrinterJob() { this.disposerReferent = new Object();
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
/*  615 */     this.lastNativeService = null;
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
/* 1314 */     this.defaultCopies = true; Disposer.addRecord(this.disposerReferent, this.handleRecord = new HandleRecord()); initAttributeMembers(); } protected double getPhysicalPrintableY(Paper paramPaper) { return this.mPrintPhysY; } protected double getPhysicalPrintableWidth(Paper paramPaper) { return this.mPrintWidth; } protected double getPhysicalPrintableHeight(Paper paramPaper) { return this.mPrintHeight; } protected double getPhysicalPageWidth(Paper paramPaper) { return this.mPageWidth; } protected double getPhysicalPageHeight(Paper paramPaper) { return this.mPageHeight; } protected boolean isCollated() { return this.userRequestedCollation; } protected int getCollatedCopies() { debug_println("driverDoesMultipleCopies=" + this.driverDoesMultipleCopies + " driverDoesCollation=" + this.driverDoesCollation); if (super.isCollated() && !this.driverDoesCollation) { this.mAttCollate = 0; this.mAttCopies = 1; return getCopies(); }  return 1; } protected int getNoncollatedCopies() { if (this.driverDoesMultipleCopies || super.isCollated()) return 1;  return getCopies(); }
/*      */   private long getPrintDC() { return this.handleRecord.mPrintDC; }
/*      */   private void setPrintDC(long paramLong) { this.handleRecord.mPrintDC = paramLong; }
/*      */   private long getDevMode() { return this.handleRecord.mPrintHDevMode; }
/*      */   private void setDevMode(long paramLong) { this.handleRecord.mPrintHDevMode = paramLong; }
/*      */   private long getDevNames() { return this.handleRecord.mPrintHDevNames; }
/* 1320 */   public void setCopies(int paramInt) { super.setCopies(paramInt);
/* 1321 */     this.defaultCopies = false;
/* 1322 */     this.mAttCopies = paramInt;
/* 1323 */     setNativeCopies(paramInt); } private void setDevNames(long paramLong) { this.handleRecord.mPrintHDevNames = paramLong; }
/*      */   protected void beginPath() { beginPath(getPrintDC()); }
/*      */   protected void endPath() { endPath(getPrintDC()); }
/*      */   protected void closeFigure() { closeFigure(getPrintDC()); }
/*      */   protected void fillPath() { fillPath(getPrintDC()); }
/*      */   protected void moveTo(float paramFloat1, float paramFloat2) { moveTo(getPrintDC(), paramFloat1, paramFloat2); }
/*      */   protected void lineTo(float paramFloat1, float paramFloat2) { lineTo(getPrintDC(), paramFloat1, paramFloat2); }
/*      */   protected void polyBezierTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) { polyBezierTo(getPrintDC(), paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6); }
/*      */   protected void setPolyFillMode(int paramInt) { setPolyFillMode(getPrintDC(), paramInt); }
/*      */   protected void selectSolidBrush(Color paramColor) { if (!paramColor.equals(this.mLastColor)) { this.mLastColor = paramColor; float[] arrayOfFloat = paramColor.getRGBColorComponents(null); selectSolidBrush(getPrintDC(), (int)(arrayOfFloat[0] * 255.0F), (int)(arrayOfFloat[1] * 255.0F), (int)(arrayOfFloat[2] * 255.0F)); }  }
/*      */   protected int getPenX() { return getPenX(getPrintDC()); }
/*      */   protected int getPenY() { return getPenY(getPrintDC()); }
/*      */   protected void selectClipPath() { selectClipPath(getPrintDC()); }
/*      */   protected void frameRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) { frameRect(getPrintDC(), paramFloat1, paramFloat2, paramFloat3, paramFloat4); }
/*      */   protected void fillRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Color paramColor) { float[] arrayOfFloat = paramColor.getRGBColorComponents(null); fillRect(getPrintDC(), paramFloat1, paramFloat2, paramFloat3, paramFloat4, (int)(arrayOfFloat[0] * 255.0F), (int)(arrayOfFloat[1] * 255.0F), (int)(arrayOfFloat[2] * 255.0F)); }
/*      */   protected void selectPen(float paramFloat, Color paramColor) { float[] arrayOfFloat = paramColor.getRGBColorComponents(null); selectPen(getPrintDC(), paramFloat, (int)(arrayOfFloat[0] * 255.0F), (int)(arrayOfFloat[1] * 255.0F), (int)(arrayOfFloat[2] * 255.0F)); }
/*      */   protected boolean selectStylePen(int paramInt1, int paramInt2, float paramFloat, Color paramColor) { long l1; float[] arrayOfFloat = paramColor.getRGBColorComponents(null); switch (paramInt1) { case 0: l1 = 512L; break;
/*      */       case 1:
/*      */         l1 = 0L; break;
/*      */       default:
/*      */         l1 = 256L; break; }  switch (paramInt2) { case 2:
/*      */         l2 = 4096L; return selectStylePen(getPrintDC(), l1, l2, paramFloat, (int)(arrayOfFloat[0] * 255.0F), (int)(arrayOfFloat[1] * 255.0F), (int)(arrayOfFloat[2] * 255.0F));
/*      */       default:
/*      */         l2 = 8192L; return selectStylePen(getPrintDC(), l1, l2, paramFloat, (int)(arrayOfFloat[0] * 255.0F), (int)(arrayOfFloat[1] * 255.0F), (int)(arrayOfFloat[2] * 255.0F));
/*      */       case 1:
/*      */         break; }  long l2 = 0L; return selectStylePen(getPrintDC(), l1, l2, paramFloat, (int)(arrayOfFloat[0] * 255.0F), (int)(arrayOfFloat[1] * 255.0F), (int)(arrayOfFloat[2] * 255.0F)); }
/*      */   protected boolean setFont(String paramString, float paramFloat1, int paramInt1, int paramInt2, float paramFloat2) { boolean bool = true; if (!paramString.equals(this.mLastFontFamily) || paramFloat1 != this.mLastFontSize || paramInt1 != this.mLastFontStyle || paramInt2 != this.mLastRotation || paramFloat2 != this.mLastAwScale) { bool = setFont(getPrintDC(), paramString, paramFloat1, ((paramInt1 & 0x1) != 0), ((paramInt1 & 0x2) != 0), paramInt2, paramFloat2); if (bool) { this.mLastFontFamily = paramString; this.mLastFontSize = paramFloat1; this.mLastFontStyle = paramInt1; this.mLastRotation = paramInt2; this.mLastAwScale = paramFloat2; }  }  return bool; }
/*      */   protected void setTextColor(Color paramColor) { if (!paramColor.equals(this.mLastTextColor)) { this.mLastTextColor = paramColor; float[] arrayOfFloat = paramColor.getRGBColorComponents(null); setTextColor(getPrintDC(), (int)(arrayOfFloat[0] * 255.0F), (int)(arrayOfFloat[1] * 255.0F), (int)(arrayOfFloat[2] * 255.0F)); }  }
/*      */   protected String removeControlChars(String paramString) { return super.removeControlChars(paramString); }
/*      */   protected void textOut(String paramString, float paramFloat1, float paramFloat2, float[] paramArrayOffloat) { String str = removeControlChars(paramString); assert paramArrayOffloat == null || str.length() == paramString.length(); if (str.length() == 0)
/*      */       return;  textOut(getPrintDC(), str, str.length(), false, paramFloat1, paramFloat2, paramArrayOffloat); }
/*      */   protected void glyphsOut(int[] paramArrayOfint, float paramFloat1, float paramFloat2, float[] paramArrayOffloat) { char[] arrayOfChar = new char[paramArrayOfint.length]; for (byte b = 0; b < paramArrayOfint.length; b++)
/*      */       arrayOfChar[b] = (char)(paramArrayOfint[b] & 0xFFFF);  String str = new String(arrayOfChar); textOut(getPrintDC(), str, paramArrayOfint.length, true, paramFloat1, paramFloat2, paramArrayOffloat); }
/*      */   protected int getGDIAdvance(String paramString) { paramString = removeControlChars(paramString); if (paramString.length() == 0)
/*      */       return 0;  return getGDIAdvance(getPrintDC(), paramString); }
/*      */   protected void drawImage3ByteBGR(byte[] paramArrayOfbyte, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) { drawDIBImage(getPrintDC(), paramArrayOfbyte, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, 24, (byte[])null); }
/*      */   protected void drawDIBImage(byte[] paramArrayOfbyte, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt, IndexColorModel paramIndexColorModel) { int i = 24; byte[] arrayOfByte = null; if (paramIndexColorModel != null) { i = paramInt; arrayOfByte = new byte[(1 << paramIndexColorModel.getPixelSize()) * 4]; for (byte b = 0; b < paramIndexColorModel.getMapSize(); b++) { arrayOfByte[b * 4 + 0] = (byte)(paramIndexColorModel.getBlue(b) & 0xFF); arrayOfByte[b * 4 + 1] = (byte)(paramIndexColorModel.getGreen(b) & 0xFF); arrayOfByte[b * 4 + 2] = (byte)(paramIndexColorModel.getRed(b) & 0xFF); }  }  drawDIBImage(getPrintDC(), paramArrayOfbyte, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, i, arrayOfByte); }
/*      */   protected void startPage(PageFormat paramPageFormat, Printable paramPrintable, int paramInt, boolean paramBoolean) { invalidateCachedState(); deviceStartPage(paramPageFormat, paramPrintable, paramInt, paramBoolean); }
/*      */   protected void endPage(PageFormat paramPageFormat, Printable paramPrintable, int paramInt) { deviceEndPage(paramPageFormat, paramPrintable, paramInt); }
/*      */   private void invalidateCachedState() { this.mLastColor = null; this.mLastTextColor = null; this.mLastFontFamily = null; }
/* 1363 */   protected void startDoc() throws PrinterException { if (!_startDoc(this.mDestination, getJobName())) {
/* 1364 */       cancel();
/*      */     } }
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
/*      */   private final String getPrinterAttrib() {
/* 1576 */     PrintService printService = getPrintService();
/* 1577 */     return (printService != null) ? printService.getName() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int getCollateAttrib() {
/* 1584 */     return this.mAttCollate;
/*      */   }
/*      */   
/*      */   private void setCollateAttrib(Attribute paramAttribute) {
/* 1588 */     if (paramAttribute == SheetCollate.COLLATED) {
/* 1589 */       this.mAttCollate = 1;
/*      */     } else {
/* 1591 */       this.mAttCollate = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setCollateAttrib(Attribute paramAttribute, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 1597 */     setCollateAttrib(paramAttribute);
/* 1598 */     paramPrintRequestAttributeSet.add(paramAttribute);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final int getOrientAttrib() {
/* 1604 */     byte b = 1;
/*      */     
/* 1606 */     OrientationRequested orientationRequested = (this.attributes == null) ? null : (OrientationRequested)this.attributes.get(OrientationRequested.class);
/* 1607 */     if (orientationRequested == null)
/*      */     {
/* 1609 */       orientationRequested = (OrientationRequested)this.myService.getDefaultAttributeValue((Class)OrientationRequested.class);
/*      */     }
/* 1611 */     if (orientationRequested != null) {
/* 1612 */       if (orientationRequested == OrientationRequested.REVERSE_LANDSCAPE) {
/* 1613 */         b = 2;
/* 1614 */       } else if (orientationRequested == OrientationRequested.LANDSCAPE) {
/* 1615 */         b = 0;
/*      */       } 
/*      */     }
/*      */     
/* 1619 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setOrientAttrib(Attribute paramAttribute, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 1624 */     if (paramPrintRequestAttributeSet != null) {
/* 1625 */       paramPrintRequestAttributeSet.add(paramAttribute);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final int getCopiesAttrib() {
/* 1631 */     if (this.defaultCopies) {
/* 1632 */       return 0;
/*      */     }
/* 1634 */     return getCopiesInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void setRangeCopiesAttribute(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3) {
/* 1641 */     if (this.attributes != null) {
/* 1642 */       if (paramBoolean) {
/* 1643 */         this.attributes.add(new PageRanges(paramInt1, paramInt2));
/* 1644 */         setPageRange(paramInt1, paramInt2);
/*      */       } 
/* 1646 */       this.defaultCopies = false;
/* 1647 */       this.attributes.add(new Copies(paramInt3));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1652 */       super.setCopies(paramInt3);
/* 1653 */       this.mAttCopies = paramInt3;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean getDestAttrib() {
/* 1660 */     return (this.mDestination != null);
/*      */   }
/*      */ 
/*      */   
/*      */   private final int getQualityAttrib() {
/* 1665 */     return this.mAttQuality;
/*      */   }
/*      */   
/*      */   private void setQualityAttrib(Attribute paramAttribute) {
/* 1669 */     if (paramAttribute == PrintQuality.HIGH) {
/* 1670 */       this.mAttQuality = -4;
/* 1671 */     } else if (paramAttribute == PrintQuality.NORMAL) {
/* 1672 */       this.mAttQuality = -3;
/*      */     } else {
/* 1674 */       this.mAttQuality = -2;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setQualityAttrib(Attribute paramAttribute, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 1680 */     setQualityAttrib(paramAttribute);
/* 1681 */     paramPrintRequestAttributeSet.add(paramAttribute);
/*      */   }
/*      */ 
/*      */   
/*      */   private final int getColorAttrib() {
/* 1686 */     return this.mAttChromaticity;
/*      */   }
/*      */   
/*      */   private void setColorAttrib(Attribute paramAttribute) {
/* 1690 */     if (paramAttribute == Chromaticity.COLOR) {
/* 1691 */       this.mAttChromaticity = 2;
/*      */     } else {
/* 1693 */       this.mAttChromaticity = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setColorAttrib(Attribute paramAttribute, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 1699 */     setColorAttrib(paramAttribute);
/* 1700 */     paramPrintRequestAttributeSet.add(paramAttribute);
/*      */   }
/*      */ 
/*      */   
/*      */   private final int getSidesAttrib() {
/* 1705 */     return this.mAttSides;
/*      */   }
/*      */   
/*      */   private void setSidesAttrib(Attribute paramAttribute) {
/* 1709 */     if (paramAttribute == Sides.TWO_SIDED_LONG_EDGE) {
/* 1710 */       this.mAttSides = 2;
/* 1711 */     } else if (paramAttribute == Sides.TWO_SIDED_SHORT_EDGE) {
/* 1712 */       this.mAttSides = 3;
/*      */     } else {
/* 1714 */       this.mAttSides = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setSidesAttrib(Attribute paramAttribute, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 1720 */     setSidesAttrib(paramAttribute);
/* 1721 */     paramPrintRequestAttributeSet.add(paramAttribute);
/*      */   }
/*      */ 
/*      */   
/*      */   private final int[] getWin32MediaAttrib() {
/* 1726 */     int[] arrayOfInt = { 0, 0 };
/* 1727 */     if (this.attributes != null) {
/* 1728 */       Media media = (Media)this.attributes.get(Media.class);
/* 1729 */       if (media instanceof MediaSizeName) {
/* 1730 */         MediaSizeName mediaSizeName = (MediaSizeName)media;
/* 1731 */         MediaSize mediaSize = MediaSize.getMediaSizeForName(mediaSizeName);
/* 1732 */         if (mediaSize != null) {
/* 1733 */           arrayOfInt[0] = (int)(mediaSize.getX(25400) * 72.0D);
/* 1734 */           arrayOfInt[1] = (int)(mediaSize.getY(25400) * 72.0D);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1738 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   private void setWin32MediaAttrib(Attribute paramAttribute) {
/* 1742 */     if (!(paramAttribute instanceof MediaSizeName)) {
/*      */       return;
/*      */     }
/* 1745 */     MediaSizeName mediaSizeName = (MediaSizeName)paramAttribute;
/* 1746 */     this.mAttMediaSizeName = ((Win32PrintService)this.myService).findPaperID(mediaSizeName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addPaperSize(PrintRequestAttributeSet paramPrintRequestAttributeSet, int paramInt1, int paramInt2, int paramInt3) {
/* 1752 */     if (paramPrintRequestAttributeSet == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1756 */     MediaSizeName mediaSizeName = ((Win32PrintService)this.myService).findWin32Media(paramInt1);
/* 1757 */     if (mediaSizeName == null)
/*      */     {
/* 1759 */       mediaSizeName = ((Win32PrintService)this.myService).findMatchingMediaSizeNameMM(paramInt2, paramInt3);
/*      */     }
/*      */     
/* 1762 */     if (mediaSizeName != null) {
/* 1763 */       paramPrintRequestAttributeSet.add(mediaSizeName);
/*      */     }
/*      */   }
/*      */   
/*      */   private void setWin32MediaAttrib(int paramInt1, int paramInt2, int paramInt3) {
/* 1768 */     addPaperSize(this.attributes, paramInt1, paramInt2, paramInt3);
/* 1769 */     this.mAttMediaSizeName = paramInt1;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setMediaTrayAttrib(Attribute paramAttribute) {
/* 1774 */     if (paramAttribute == MediaTray.BOTTOM) {
/* 1775 */       this.mAttMediaTray = 2;
/* 1776 */     } else if (paramAttribute == MediaTray.ENVELOPE) {
/* 1777 */       this.mAttMediaTray = 5;
/* 1778 */     } else if (paramAttribute == MediaTray.LARGE_CAPACITY) {
/* 1779 */       this.mAttMediaTray = 11;
/* 1780 */     } else if (paramAttribute == MediaTray.MAIN) {
/* 1781 */       this.mAttMediaTray = 1;
/* 1782 */     } else if (paramAttribute == MediaTray.MANUAL) {
/* 1783 */       this.mAttMediaTray = 4;
/* 1784 */     } else if (paramAttribute == MediaTray.MIDDLE) {
/* 1785 */       this.mAttMediaTray = 3;
/* 1786 */     } else if (paramAttribute == MediaTray.SIDE) {
/*      */       
/* 1788 */       this.mAttMediaTray = 7;
/* 1789 */     } else if (paramAttribute == MediaTray.TOP) {
/* 1790 */       this.mAttMediaTray = 1;
/*      */     }
/* 1792 */     else if (paramAttribute instanceof Win32MediaTray) {
/* 1793 */       this.mAttMediaTray = ((Win32MediaTray)paramAttribute).winID;
/*      */     } else {
/* 1795 */       this.mAttMediaTray = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setMediaTrayAttrib(int paramInt) {
/* 1801 */     this.mAttMediaTray = paramInt;
/* 1802 */     MediaTray mediaTray = ((Win32PrintService)this.myService).findMediaTray(paramInt);
/*      */   }
/*      */   
/*      */   private int getMediaTrayAttrib() {
/* 1806 */     return this.mAttMediaTray;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean getPrintToFileEnabled() {
/* 1812 */     SecurityManager securityManager = System.getSecurityManager();
/* 1813 */     if (securityManager != null) {
/* 1814 */       FilePermission filePermission = new FilePermission("<<ALL FILES>>", "read,write");
/*      */       
/*      */       try {
/* 1817 */         securityManager.checkPermission(filePermission);
/* 1818 */       } catch (SecurityException securityException) {
/* 1819 */         return false;
/*      */       } 
/*      */     } 
/* 1822 */     return true;
/*      */   }
/*      */   
/*      */   private final void setNativeAttributes(int paramInt1, int paramInt2, int paramInt3) {
/* 1826 */     if (this.attributes == null) {
/*      */       return;
/*      */     }
/* 1829 */     if ((paramInt1 & 0x20) != 0) {
/* 1830 */       Destination destination = (Destination)this.attributes.get(Destination.class);
/*      */       
/* 1832 */       if (destination == null) {
/*      */         try {
/* 1834 */           this.attributes.add(new Destination((new File("./out.prn"))
/* 1835 */                 .toURI()));
/* 1836 */         } catch (SecurityException securityException) {
/*      */           try {
/* 1838 */             this.attributes.add(new Destination(new URI("file:out.prn")));
/*      */           }
/* 1840 */           catch (URISyntaxException uRISyntaxException) {}
/*      */         } 
/*      */       }
/*      */     } else {
/*      */       
/* 1845 */       this.attributes.remove(Destination.class);
/*      */     } 
/*      */     
/* 1848 */     if ((paramInt1 & 0x10) != 0) {
/* 1849 */       setCollateAttrib(SheetCollate.COLLATED, this.attributes);
/*      */     } else {
/* 1851 */       setCollateAttrib(SheetCollate.UNCOLLATED, this.attributes);
/*      */     } 
/*      */     
/* 1854 */     if ((paramInt1 & 0x2) != 0) {
/* 1855 */       this.attributes.add(SunPageSelection.RANGE);
/* 1856 */     } else if ((paramInt1 & 0x1) != 0) {
/* 1857 */       this.attributes.add(SunPageSelection.SELECTION);
/*      */     } else {
/* 1859 */       this.attributes.add(SunPageSelection.ALL);
/*      */     } 
/*      */     
/* 1862 */     if ((paramInt2 & 0x1) != 0) {
/* 1863 */       if ((paramInt3 & 0x4000) != 0) {
/* 1864 */         setOrientAttrib(OrientationRequested.LANDSCAPE, this.attributes);
/*      */       } else {
/* 1866 */         setOrientAttrib(OrientationRequested.PORTRAIT, this.attributes);
/*      */       } 
/*      */     }
/*      */     
/* 1870 */     if ((paramInt2 & 0x800) != 0) {
/* 1871 */       if ((paramInt3 & 0x200) != 0) {
/* 1872 */         setColorAttrib(Chromaticity.COLOR, this.attributes);
/*      */       } else {
/* 1874 */         setColorAttrib(Chromaticity.MONOCHROME, this.attributes);
/*      */       } 
/*      */     }
/*      */     
/* 1878 */     if ((paramInt2 & 0x400) != 0) {
/*      */       PrintQuality printQuality;
/* 1880 */       if ((paramInt3 & 0x80) != 0) {
/* 1881 */         printQuality = PrintQuality.DRAFT;
/* 1882 */       } else if ((paramInt2 & 0x40) != 0) {
/* 1883 */         printQuality = PrintQuality.HIGH;
/*      */       } else {
/* 1885 */         printQuality = PrintQuality.NORMAL;
/*      */       } 
/* 1887 */       setQualityAttrib(printQuality, this.attributes);
/*      */     } 
/*      */     
/* 1890 */     if ((paramInt2 & 0x1000) != 0) {
/*      */       Sides sides;
/* 1892 */       if ((paramInt3 & 0x10) != 0) {
/* 1893 */         sides = Sides.TWO_SIDED_LONG_EDGE;
/* 1894 */       } else if ((paramInt3 & 0x20) != 0) {
/* 1895 */         sides = Sides.TWO_SIDED_SHORT_EDGE;
/*      */       } else {
/* 1897 */         sides = Sides.ONE_SIDED;
/*      */       } 
/* 1899 */       setSidesAttrib(sides, this.attributes);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static final class DevModeValues {
/*      */     int dmFields;
/*      */     short copies;
/*      */     short collate;
/*      */     short color;
/*      */     short duplex;
/*      */     short orient;
/*      */     short paper;
/*      */     short bin;
/*      */     short xres_quality;
/*      */     short yres;
/*      */     
/*      */     private DevModeValues() {}
/*      */   }
/*      */   
/*      */   private void getDevModeValues(PrintRequestAttributeSet paramPrintRequestAttributeSet, DevModeValues paramDevModeValues) {
/* 1919 */     Copies copies = (Copies)paramPrintRequestAttributeSet.get(Copies.class);
/* 1920 */     if (copies != null) {
/* 1921 */       paramDevModeValues.dmFields |= 0x100;
/* 1922 */       paramDevModeValues.copies = (short)copies.getValue();
/*      */     } 
/*      */     
/* 1925 */     SheetCollate sheetCollate = (SheetCollate)paramPrintRequestAttributeSet.get(SheetCollate.class);
/* 1926 */     if (sheetCollate != null) {
/* 1927 */       paramDevModeValues.dmFields |= 0x8000;
/* 1928 */       paramDevModeValues.collate = (sheetCollate == SheetCollate.COLLATED) ? 1 : 0;
/*      */     } 
/*      */ 
/*      */     
/* 1932 */     Chromaticity chromaticity = (Chromaticity)paramPrintRequestAttributeSet.get(Chromaticity.class);
/* 1933 */     if (chromaticity != null) {
/* 1934 */       paramDevModeValues.dmFields |= 0x800;
/* 1935 */       if (chromaticity == Chromaticity.COLOR) {
/* 1936 */         paramDevModeValues.color = 2;
/*      */       } else {
/* 1938 */         paramDevModeValues.color = 1;
/*      */       } 
/*      */     } 
/*      */     
/* 1942 */     Sides sides = (Sides)paramPrintRequestAttributeSet.get(Sides.class);
/* 1943 */     if (sides != null) {
/* 1944 */       paramDevModeValues.dmFields |= 0x1000;
/* 1945 */       if (sides == Sides.TWO_SIDED_LONG_EDGE) {
/* 1946 */         paramDevModeValues.duplex = 2;
/* 1947 */       } else if (sides == Sides.TWO_SIDED_SHORT_EDGE) {
/* 1948 */         paramDevModeValues.duplex = 3;
/*      */       } else {
/* 1950 */         paramDevModeValues.duplex = 1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1955 */     OrientationRequested orientationRequested = (OrientationRequested)paramPrintRequestAttributeSet.get(OrientationRequested.class);
/* 1956 */     if (orientationRequested != null) {
/* 1957 */       paramDevModeValues.dmFields |= 0x1;
/* 1958 */       paramDevModeValues.orient = (orientationRequested == OrientationRequested.LANDSCAPE) ? 2 : 1;
/*      */     } 
/*      */ 
/*      */     
/* 1962 */     Media media = (Media)paramPrintRequestAttributeSet.get(Media.class);
/* 1963 */     if (media instanceof MediaSizeName) {
/* 1964 */       paramDevModeValues.dmFields |= 0x2;
/* 1965 */       MediaSizeName mediaSizeName = (MediaSizeName)media;
/* 1966 */       paramDevModeValues
/* 1967 */         .paper = (short)((Win32PrintService)this.myService).findPaperID(mediaSizeName);
/*      */     } 
/*      */     
/* 1970 */     MediaTray mediaTray = null;
/* 1971 */     if (media instanceof MediaTray) {
/* 1972 */       mediaTray = (MediaTray)media;
/*      */     }
/* 1974 */     if (mediaTray == null) {
/*      */       
/* 1976 */       SunAlternateMedia sunAlternateMedia = (SunAlternateMedia)paramPrintRequestAttributeSet.get(SunAlternateMedia.class);
/* 1977 */       if (sunAlternateMedia != null && sunAlternateMedia.getMedia() instanceof MediaTray) {
/* 1978 */         mediaTray = (MediaTray)sunAlternateMedia.getMedia();
/*      */       }
/*      */     } 
/*      */     
/* 1982 */     if (mediaTray != null) {
/* 1983 */       paramDevModeValues.dmFields |= 0x200;
/* 1984 */       paramDevModeValues.bin = (short)((Win32PrintService)this.myService).findTrayID(mediaTray);
/*      */     } 
/*      */     
/* 1987 */     PrintQuality printQuality = (PrintQuality)paramPrintRequestAttributeSet.get(PrintQuality.class);
/* 1988 */     if (printQuality != null) {
/* 1989 */       paramDevModeValues.dmFields |= 0x400;
/* 1990 */       if (printQuality == PrintQuality.DRAFT) {
/* 1991 */         paramDevModeValues.xres_quality = -1;
/* 1992 */       } else if (printQuality == PrintQuality.HIGH) {
/* 1993 */         paramDevModeValues.xres_quality = -4;
/*      */       } else {
/* 1995 */         paramDevModeValues.xres_quality = -3;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2000 */     PrinterResolution printerResolution = (PrinterResolution)paramPrintRequestAttributeSet.get(PrinterResolution.class);
/* 2001 */     if (printerResolution != null) {
/* 2002 */       paramDevModeValues.dmFields |= 0x2400;
/* 2003 */       paramDevModeValues
/* 2004 */         .xres_quality = (short)printerResolution.getCrossFeedResolution(100);
/* 2005 */       paramDevModeValues.yres = (short)printerResolution.getFeedResolution(100);
/*      */     } 
/*      */   }
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
/*      */   private final void setJobAttributes(PrintRequestAttributeSet paramPrintRequestAttributeSet, int paramInt1, int paramInt2, short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5, short paramShort6, short paramShort7) {
/* 2027 */     if (paramPrintRequestAttributeSet == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2031 */     if ((paramInt1 & 0x100) != 0) {
/* 2032 */       paramPrintRequestAttributeSet.add(new Copies(paramShort1));
/*      */     }
/*      */     
/* 2035 */     if ((paramInt1 & 0x8000) != 0) {
/* 2036 */       if ((paramInt2 & 0x8000) != 0) {
/* 2037 */         paramPrintRequestAttributeSet.add(SheetCollate.COLLATED);
/*      */       } else {
/* 2039 */         paramPrintRequestAttributeSet.add(SheetCollate.UNCOLLATED);
/*      */       } 
/*      */     }
/*      */     
/* 2043 */     if ((paramInt1 & 0x1) != 0) {
/* 2044 */       if ((paramInt2 & 0x4000) != 0) {
/* 2045 */         paramPrintRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
/*      */       } else {
/* 2047 */         paramPrintRequestAttributeSet.add(OrientationRequested.PORTRAIT);
/*      */       } 
/*      */     }
/*      */     
/* 2051 */     if ((paramInt1 & 0x800) != 0) {
/* 2052 */       if ((paramInt2 & 0x200) != 0) {
/* 2053 */         paramPrintRequestAttributeSet.add(Chromaticity.COLOR);
/*      */       } else {
/* 2055 */         paramPrintRequestAttributeSet.add(Chromaticity.MONOCHROME);
/*      */       } 
/*      */     }
/*      */     
/* 2059 */     if ((paramInt1 & 0x400) != 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2067 */       if (paramShort6 < 0) {
/*      */         PrintQuality printQuality;
/* 2069 */         if ((paramInt2 & 0x80) != 0) {
/* 2070 */           printQuality = PrintQuality.DRAFT;
/* 2071 */         } else if ((paramInt1 & 0x40) != 0) {
/* 2072 */           printQuality = PrintQuality.HIGH;
/*      */         } else {
/* 2074 */           printQuality = PrintQuality.NORMAL;
/*      */         } 
/* 2076 */         paramPrintRequestAttributeSet.add(printQuality);
/* 2077 */       } else if (paramShort6 > 0 && paramShort7 > 0) {
/* 2078 */         paramPrintRequestAttributeSet.add(new PrinterResolution(paramShort6, paramShort7, 100));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2083 */     if ((paramInt1 & 0x1000) != 0) {
/*      */       Sides sides;
/* 2085 */       if ((paramInt2 & 0x10) != 0) {
/* 2086 */         sides = Sides.TWO_SIDED_LONG_EDGE;
/* 2087 */       } else if ((paramInt2 & 0x20) != 0) {
/* 2088 */         sides = Sides.TWO_SIDED_SHORT_EDGE;
/*      */       } else {
/* 2090 */         sides = Sides.ONE_SIDED;
/*      */       } 
/* 2092 */       paramPrintRequestAttributeSet.add(sides);
/*      */     } 
/*      */     
/* 2095 */     if ((paramInt1 & 0x2) != 0) {
/* 2096 */       addPaperSize(paramPrintRequestAttributeSet, paramShort2, paramShort3, paramShort4);
/*      */     }
/*      */     
/* 2099 */     if ((paramInt1 & 0x200) != 0) {
/*      */       
/* 2101 */       MediaTray mediaTray = ((Win32PrintService)this.myService).findMediaTray(paramShort5);
/* 2102 */       paramPrintRequestAttributeSet.add(new SunAlternateMedia(mediaTray));
/*      */     } 
/*      */   }
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
/*      */   public PrintRequestAttributeSet showDocumentProperties(Window paramWindow, PrintService paramPrintService, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/*      */     try {
/* 2126 */       setNativePrintServiceIfNeeded(paramPrintService.getName());
/* 2127 */     } catch (PrinterException printerException) {}
/*      */     
/* 2129 */     long l = ((WWindowPeer)paramWindow.getPeer()).getHWnd();
/* 2130 */     DevModeValues devModeValues = new DevModeValues();
/* 2131 */     getDevModeValues(paramPrintRequestAttributeSet, devModeValues);
/*      */     
/* 2133 */     boolean bool = showDocProperties(l, paramPrintRequestAttributeSet, devModeValues.dmFields, devModeValues.copies, devModeValues.collate, devModeValues.color, devModeValues.duplex, devModeValues.orient, devModeValues.paper, devModeValues.bin, devModeValues.xres_quality, devModeValues.yres);
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
/* 2145 */     if (bool) {
/* 2146 */       return paramPrintRequestAttributeSet;
/*      */     }
/* 2148 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void setResolutionDPI(int paramInt1, int paramInt2) {
/* 2154 */     if (this.attributes != null) {
/* 2155 */       PrinterResolution printerResolution = new PrinterResolution(paramInt1, paramInt2, 100);
/*      */       
/* 2157 */       this.attributes.add(printerResolution);
/*      */     } 
/* 2159 */     this.mAttXRes = paramInt1;
/* 2160 */     this.mAttYRes = paramInt2;
/*      */   }
/*      */   
/*      */   private void setResolutionAttrib(Attribute paramAttribute) {
/* 2164 */     PrinterResolution printerResolution = (PrinterResolution)paramAttribute;
/* 2165 */     this.mAttXRes = printerResolution.getCrossFeedResolution(100);
/* 2166 */     this.mAttYRes = printerResolution.getFeedResolution(100);
/*      */   }
/*      */   
/*      */   private void setPrinterNameAttrib(String paramString) {
/* 2170 */     PrintService printService = getPrintService();
/*      */     
/* 2172 */     if (paramString == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2176 */     if (printService != null && paramString.equals(printService.getName())) {
/*      */       return;
/*      */     }
/* 2179 */     PrintService[] arrayOfPrintService = PrinterJob.lookupPrintServices();
/* 2180 */     for (byte b = 0; b < arrayOfPrintService.length; b++) {
/* 2181 */       if (paramString.equals(arrayOfPrintService[b].getName())) {
/*      */         
/*      */         try {
/* 2184 */           setPrintService(arrayOfPrintService[b]);
/* 2185 */         } catch (PrinterException printerException) {}
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   } private native void setNativePrintService(String paramString) throws PrinterException; private native String getNativePrintService(); private native void getDefaultPage(PageFormat paramPageFormat); protected native void validatePaper(Paper paramPaper1, Paper paramPaper2); private native void setNativeCopies(int paramInt); private native boolean jobSetup(Pageable paramPageable, boolean paramBoolean); protected native void initPrinter(); private native boolean _startDoc(String paramString1, String paramString2) throws PrinterException; protected native void endDoc(); protected native void abortDoc(); private static native void deleteDC(long paramLong1, long paramLong2, long paramLong3); protected native void deviceStartPage(PageFormat paramPageFormat, Printable paramPrintable, int paramInt, boolean paramBoolean); protected native void deviceEndPage(PageFormat paramPageFormat, Printable paramPrintable, int paramInt); protected native void printBand(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4); protected native void beginPath(long paramLong); protected native void endPath(long paramLong); protected native void closeFigure(long paramLong); protected native void fillPath(long paramLong); protected native void moveTo(long paramLong, float paramFloat1, float paramFloat2); protected native void lineTo(long paramLong, float paramFloat1, float paramFloat2); protected native void polyBezierTo(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6); protected native void setPolyFillMode(long paramLong, int paramInt); protected native void selectSolidBrush(long paramLong, int paramInt1, int paramInt2, int paramInt3); protected native int getPenX(long paramLong); protected native int getPenY(long paramLong); protected native void selectClipPath(long paramLong); protected native void frameRect(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4); protected native void fillRect(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3); protected native void selectPen(long paramLong, float paramFloat, int paramInt1, int paramInt2, int paramInt3); protected native boolean selectStylePen(long paramLong1, long paramLong2, long paramLong3, float paramFloat, int paramInt1, int paramInt2, int paramInt3);
/*      */   protected native boolean setFont(long paramLong, String paramString, float paramFloat1, boolean paramBoolean1, boolean paramBoolean2, int paramInt, float paramFloat2);
/*      */   protected native void setTextColor(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*      */   protected native void textOut(long paramLong, String paramString, int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2, float[] paramArrayOffloat);
/*      */   private native int getGDIAdvance(long paramLong, String paramString);
/*      */   private native void drawDIBImage(long paramLong, byte[] paramArrayOfbyte1, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt, byte[] paramArrayOfbyte2);
/*      */   private native boolean showDocProperties(long paramLong, PrintRequestAttributeSet paramPrintRequestAttributeSet, int paramInt, short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5, short paramShort6, short paramShort7, short paramShort8, short paramShort9);
/*      */   private static native void initIDs();
/*      */   class PrintToFileErrorDialog extends Dialog implements ActionListener { public PrintToFileErrorDialog(Frame param1Frame, String param1String1, String param1String2, String param1String3) {
/* 2198 */       super(param1Frame, param1String1, true);
/* 2199 */       init(param1Frame, param1String1, param1String2, param1String3);
/*      */     }
/*      */ 
/*      */     
/*      */     public PrintToFileErrorDialog(Dialog param1Dialog, String param1String1, String param1String2, String param1String3) {
/* 2204 */       super(param1Dialog, param1String1, true);
/* 2205 */       init(param1Dialog, param1String1, param1String2, param1String3);
/*      */     }
/*      */ 
/*      */     
/*      */     private void init(Component param1Component, String param1String1, String param1String2, String param1String3) {
/* 2210 */       Panel panel = new Panel();
/* 2211 */       add("Center", new Label(param1String2));
/* 2212 */       Button button = new Button(param1String3);
/* 2213 */       button.addActionListener(this);
/* 2214 */       panel.add(button);
/* 2215 */       add("South", panel);
/* 2216 */       pack();
/*      */       
/* 2218 */       Dimension dimension = getSize();
/* 2219 */       if (param1Component != null) {
/* 2220 */         Rectangle rectangle = param1Component.getBounds();
/* 2221 */         setLocation(rectangle.x + (rectangle.width - dimension.width) / 2, rectangle.y + (rectangle.height - dimension.height) / 2);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2228 */       setVisible(false);
/* 2229 */       dispose();
/*      */     } }
/*      */ 
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WPrinterJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */