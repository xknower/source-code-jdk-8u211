/*     */ package sun.print;
/*     */ 
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Pageable;
/*     */ import java.awt.print.Paper;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Vector;
/*     */ import javax.print.CancelablePrintJob;
/*     */ import javax.print.Doc;
/*     */ import javax.print.DocFlavor;
/*     */ import javax.print.PrintException;
/*     */ import javax.print.PrintService;
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.AttributeSetUtilities;
/*     */ import javax.print.attribute.DocAttributeSet;
/*     */ import javax.print.attribute.HashPrintJobAttributeSet;
/*     */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*     */ import javax.print.attribute.PrintJobAttributeSet;
/*     */ import javax.print.attribute.PrintRequestAttributeSet;
/*     */ import javax.print.attribute.standard.Copies;
/*     */ import javax.print.attribute.standard.Destination;
/*     */ import javax.print.attribute.standard.DocumentName;
/*     */ import javax.print.attribute.standard.Fidelity;
/*     */ import javax.print.attribute.standard.JobName;
/*     */ import javax.print.attribute.standard.JobOriginatingUserName;
/*     */ import javax.print.attribute.standard.Media;
/*     */ import javax.print.attribute.standard.MediaSize;
/*     */ import javax.print.attribute.standard.MediaSizeName;
/*     */ import javax.print.attribute.standard.OrientationRequested;
/*     */ import javax.print.attribute.standard.PrinterIsAcceptingJobs;
/*     */ import javax.print.attribute.standard.PrinterState;
/*     */ import javax.print.attribute.standard.PrinterStateReason;
/*     */ import javax.print.attribute.standard.PrinterStateReasons;
/*     */ import javax.print.attribute.standard.RequestingUserName;
/*     */ import javax.print.event.PrintJobAttributeListener;
/*     */ import javax.print.event.PrintJobEvent;
/*     */ import javax.print.event.PrintJobListener;
/*     */ import sun.awt.windows.WPrinterJob;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Win32PrintJob
/*     */   implements CancelablePrintJob
/*     */ {
/*     */   private transient Vector jobListeners;
/*     */   private transient Vector attrListeners;
/*     */   private transient Vector listenedAttributeSets;
/*     */   private Win32PrintService service;
/*     */   private boolean fidelity;
/*     */   private boolean printing = false;
/*     */   private boolean printReturned = false;
/*  90 */   private PrintRequestAttributeSet reqAttrSet = null;
/*  91 */   private PrintJobAttributeSet jobAttrSet = null;
/*     */   private PrinterJob job;
/*     */   private Doc doc;
/*  94 */   private String mDestination = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   private InputStream instream = null;
/* 101 */   private Reader reader = null;
/*     */ 
/*     */   
/* 104 */   private String jobName = "Java Printing";
/* 105 */   private int copies = 0;
/* 106 */   private MediaSizeName mediaName = null;
/* 107 */   private MediaSize mediaSize = null;
/* 108 */   private OrientationRequested orient = null;
/*     */ 
/*     */   
/*     */   private long hPrintJob;
/*     */   
/*     */   private static final int PRINTBUFFERLEN = 8192;
/*     */ 
/*     */   
/*     */   Win32PrintJob(Win32PrintService paramWin32PrintService) {
/* 117 */     this.service = paramWin32PrintService;
/*     */   }
/*     */   
/*     */   public PrintService getPrintService() {
/* 121 */     return this.service;
/*     */   }
/*     */   
/*     */   public PrintJobAttributeSet getAttributes() {
/* 125 */     synchronized (this) {
/* 126 */       if (this.jobAttrSet == null) {
/*     */         
/* 128 */         HashPrintJobAttributeSet hashPrintJobAttributeSet = new HashPrintJobAttributeSet();
/* 129 */         return AttributeSetUtilities.unmodifiableView(hashPrintJobAttributeSet);
/*     */       } 
/* 131 */       return this.jobAttrSet;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPrintJobListener(PrintJobListener paramPrintJobListener) {
/* 137 */     synchronized (this) {
/* 138 */       if (paramPrintJobListener == null) {
/*     */         return;
/*     */       }
/* 141 */       if (this.jobListeners == null) {
/* 142 */         this.jobListeners = new Vector();
/*     */       }
/* 144 */       this.jobListeners.add(paramPrintJobListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removePrintJobListener(PrintJobListener paramPrintJobListener) {
/* 149 */     synchronized (this) {
/* 150 */       if (paramPrintJobListener == null || this.jobListeners == null) {
/*     */         return;
/*     */       }
/* 153 */       this.jobListeners.remove(paramPrintJobListener);
/* 154 */       if (this.jobListeners.isEmpty()) {
/* 155 */         this.jobListeners = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeDataStreams() {
/* 169 */     if (this.doc == null) {
/*     */       return;
/*     */     }
/*     */     
/* 173 */     Object object = null;
/*     */     
/*     */     try {
/* 176 */       object = this.doc.getPrintData();
/* 177 */     } catch (IOException iOException) {
/*     */       return;
/*     */     } 
/*     */     
/* 181 */     if (this.instream != null) {
/*     */       
/* 183 */       try { this.instream.close(); }
/* 184 */       catch (IOException iOException) {  }
/*     */       finally
/* 186 */       { this.instream = null; }
/*     */ 
/*     */     
/* 189 */     } else if (this.reader != null) {
/*     */       
/* 191 */       try { this.reader.close(); }
/* 192 */       catch (IOException iOException) {  }
/*     */       finally
/* 194 */       { this.reader = null; }
/*     */ 
/*     */     
/* 197 */     } else if (object instanceof InputStream) {
/*     */       try {
/* 199 */         ((InputStream)object).close();
/* 200 */       } catch (IOException iOException) {}
/*     */     
/*     */     }
/* 203 */     else if (object instanceof Reader) {
/*     */       try {
/* 205 */         ((Reader)object).close();
/* 206 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void notifyEvent(int paramInt) {
/* 216 */     switch (paramInt) {
/*     */       case 101:
/*     */       case 102:
/*     */       case 103:
/*     */       case 105:
/*     */       case 106:
/* 222 */         closeDataStreams();
/*     */         break;
/*     */     } 
/* 225 */     synchronized (this) {
/* 226 */       if (this.jobListeners != null) {
/*     */         
/* 228 */         PrintJobEvent printJobEvent = new PrintJobEvent(this, paramInt);
/* 229 */         for (byte b = 0; b < this.jobListeners.size(); b++) {
/* 230 */           PrintJobListener printJobListener = this.jobListeners.elementAt(b);
/* 231 */           switch (paramInt) {
/*     */             
/*     */             case 102:
/* 234 */               printJobListener.printJobCompleted(printJobEvent);
/*     */               break;
/*     */             
/*     */             case 101:
/* 238 */               printJobListener.printJobCanceled(printJobEvent);
/*     */               break;
/*     */             
/*     */             case 103:
/* 242 */               printJobListener.printJobFailed(printJobEvent);
/*     */               break;
/*     */             
/*     */             case 106:
/* 246 */               printJobListener.printDataTransferCompleted(printJobEvent);
/*     */               break;
/*     */             
/*     */             case 105:
/* 250 */               printJobListener.printJobNoMoreEvents(printJobEvent);
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPrintJobAttributeListener(PrintJobAttributeListener paramPrintJobAttributeListener, PrintJobAttributeSet paramPrintJobAttributeSet) {
/* 264 */     synchronized (this) {
/* 265 */       if (paramPrintJobAttributeListener == null) {
/*     */         return;
/*     */       }
/* 268 */       if (this.attrListeners == null) {
/* 269 */         this.attrListeners = new Vector();
/* 270 */         this.listenedAttributeSets = new Vector();
/*     */       } 
/* 272 */       this.attrListeners.add(paramPrintJobAttributeListener);
/* 273 */       if (paramPrintJobAttributeSet == null) {
/* 274 */         paramPrintJobAttributeSet = new HashPrintJobAttributeSet();
/*     */       }
/* 276 */       this.listenedAttributeSets.add(paramPrintJobAttributeSet);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePrintJobAttributeListener(PrintJobAttributeListener paramPrintJobAttributeListener) {
/* 282 */     synchronized (this) {
/* 283 */       if (paramPrintJobAttributeListener == null || this.attrListeners == null) {
/*     */         return;
/*     */       }
/* 286 */       int i = this.attrListeners.indexOf(paramPrintJobAttributeListener);
/* 287 */       if (i == -1) {
/*     */         return;
/*     */       }
/* 290 */       this.attrListeners.remove(i);
/* 291 */       this.listenedAttributeSets.remove(i);
/* 292 */       if (this.attrListeners.isEmpty()) {
/* 293 */         this.attrListeners = null;
/* 294 */         this.listenedAttributeSets = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(Doc paramDoc, PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrintException {
/*     */     Object object;
/* 303 */     synchronized (this) {
/* 304 */       if (this.printing) {
/* 305 */         throw new PrintException("already printing");
/*     */       }
/* 307 */       this.printing = true;
/*     */     } 
/*     */ 
/*     */     
/* 311 */     PrinterState printerState = this.service.<PrinterState>getAttribute(PrinterState.class);
/*     */     
/* 313 */     if (printerState == PrinterState.STOPPED) {
/*     */       
/* 315 */       PrinterStateReasons printerStateReasons = this.service.<PrinterStateReasons>getAttribute(PrinterStateReasons.class);
/*     */       
/* 317 */       if (printerStateReasons != null && printerStateReasons
/* 318 */         .containsKey(PrinterStateReason.SHUTDOWN))
/*     */       {
/* 320 */         throw new PrintException("PrintService is no longer available.");
/*     */       }
/*     */     } 
/*     */     
/* 324 */     if ((PrinterIsAcceptingJobs)this.service.<PrinterIsAcceptingJobs>getAttribute(PrinterIsAcceptingJobs.class) == PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS)
/*     */     {
/*     */       
/* 327 */       throw new PrintException("Printer is not accepting job.");
/*     */     }
/*     */ 
/*     */     
/* 331 */     this.doc = paramDoc;
/*     */     
/* 333 */     DocFlavor docFlavor = paramDoc.getDocFlavor();
/*     */ 
/*     */     
/*     */     try {
/* 337 */       object = paramDoc.getPrintData();
/* 338 */     } catch (IOException iOException) {
/* 339 */       notifyEvent(103);
/* 340 */       throw new PrintException("can't get print data: " + iOException.toString());
/*     */     } 
/*     */     
/* 343 */     if (object == null) {
/* 344 */       throw new PrintException("Null print data.");
/*     */     }
/*     */     
/* 347 */     if (docFlavor == null || !this.service.isDocFlavorSupported(docFlavor)) {
/* 348 */       notifyEvent(103);
/* 349 */       throw new PrintJobFlavorException("invalid flavor", docFlavor);
/*     */     } 
/*     */     
/* 352 */     initializeAttributeSets(paramDoc, paramPrintRequestAttributeSet);
/*     */     
/* 354 */     getAttributeValues(docFlavor);
/*     */     
/* 356 */     String str = docFlavor.getRepresentationClassName();
/*     */     
/* 358 */     if (docFlavor.equals(DocFlavor.INPUT_STREAM.GIF) || docFlavor
/* 359 */       .equals(DocFlavor.INPUT_STREAM.JPEG) || docFlavor
/* 360 */       .equals(DocFlavor.INPUT_STREAM.PNG) || docFlavor
/* 361 */       .equals(DocFlavor.BYTE_ARRAY.GIF) || docFlavor
/* 362 */       .equals(DocFlavor.BYTE_ARRAY.JPEG) || docFlavor
/* 363 */       .equals(DocFlavor.BYTE_ARRAY.PNG))
/*     */       try {
/* 365 */         this.instream = paramDoc.getStreamForBytes();
/* 366 */         if (this.instream == null) {
/* 367 */           notifyEvent(103);
/* 368 */           throw new PrintException("No stream for data");
/*     */         } 
/* 370 */         printableJob(new ImagePrinter(this.instream));
/* 371 */         this.service.wakeNotifier();
/*     */         return;
/* 373 */       } catch (ClassCastException classCastException) {
/* 374 */         notifyEvent(103);
/* 375 */         throw new PrintException(classCastException);
/* 376 */       } catch (IOException iOException) {
/* 377 */         notifyEvent(103);
/* 378 */         throw new PrintException(iOException);
/*     */       }  
/* 380 */     if (docFlavor.equals(DocFlavor.URL.GIF) || docFlavor
/* 381 */       .equals(DocFlavor.URL.JPEG) || docFlavor
/* 382 */       .equals(DocFlavor.URL.PNG))
/*     */       try {
/* 384 */         printableJob(new ImagePrinter((URL)object));
/* 385 */         this.service.wakeNotifier();
/*     */         return;
/* 387 */       } catch (ClassCastException classCastException) {
/* 388 */         notifyEvent(103);
/* 389 */         throw new PrintException(classCastException);
/*     */       }  
/* 391 */     if (str.equals("java.awt.print.Pageable"))
/*     */       try {
/* 393 */         pageableJob((Pageable)paramDoc.getPrintData());
/* 394 */         this.service.wakeNotifier();
/*     */         return;
/* 396 */       } catch (ClassCastException classCastException) {
/* 397 */         notifyEvent(103);
/* 398 */         throw new PrintException(classCastException);
/* 399 */       } catch (IOException iOException) {
/* 400 */         notifyEvent(103);
/* 401 */         throw new PrintException(iOException);
/*     */       }  
/* 403 */     if (str.equals("java.awt.print.Printable"))
/*     */       try {
/* 405 */         printableJob((Printable)paramDoc.getPrintData());
/* 406 */         this.service.wakeNotifier();
/*     */         return;
/* 408 */       } catch (ClassCastException classCastException) {
/* 409 */         notifyEvent(103);
/* 410 */         throw new PrintException(classCastException);
/* 411 */       } catch (IOException iOException) {
/* 412 */         notifyEvent(103);
/* 413 */         throw new PrintException(iOException);
/*     */       }  
/* 415 */     if (str.equals("[B") || str
/* 416 */       .equals("java.io.InputStream") || str
/* 417 */       .equals("java.net.URL")) {
/*     */       
/* 419 */       if (str.equals("java.net.URL")) {
/* 420 */         URL uRL = (URL)object;
/*     */         try {
/* 422 */           this.instream = uRL.openStream();
/* 423 */         } catch (IOException iOException) {
/* 424 */           notifyEvent(103);
/* 425 */           throw new PrintException(iOException.toString());
/*     */         } 
/*     */       } else {
/*     */         try {
/* 429 */           this.instream = paramDoc.getStreamForBytes();
/* 430 */         } catch (IOException iOException) {
/* 431 */           notifyEvent(103);
/* 432 */           throw new PrintException(iOException.toString());
/*     */         } 
/*     */       } 
/*     */       
/* 436 */       if (this.instream == null) {
/* 437 */         notifyEvent(103);
/* 438 */         throw new PrintException("No stream for data");
/*     */       } 
/*     */       
/* 441 */       if (this.mDestination != null) {
/*     */         try {
/* 443 */           FileOutputStream fileOutputStream = new FileOutputStream(this.mDestination);
/* 444 */           byte[] arrayOfByte = new byte[1024];
/*     */           
/*     */           int j;
/* 447 */           while ((j = this.instream.read(arrayOfByte, 0, arrayOfByte.length)) >= 0) {
/* 448 */             fileOutputStream.write(arrayOfByte, 0, j);
/*     */           }
/* 450 */           fileOutputStream.flush();
/* 451 */           fileOutputStream.close();
/* 452 */         } catch (FileNotFoundException fileNotFoundException) {
/* 453 */           notifyEvent(103);
/* 454 */           throw new PrintException(fileNotFoundException.toString());
/* 455 */         } catch (IOException iOException) {
/* 456 */           notifyEvent(103);
/* 457 */           throw new PrintException(iOException.toString());
/*     */         } 
/* 459 */         notifyEvent(106);
/* 460 */         notifyEvent(102);
/* 461 */         this.service.wakeNotifier();
/*     */         
/*     */         return;
/*     */       } 
/* 465 */       if (!startPrintRawData(this.service.getName(), this.jobName)) {
/* 466 */         notifyEvent(103);
/* 467 */         throw new PrintException("Print job failed to start.");
/*     */       } 
/* 469 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(this.instream);
/* 470 */       int i = 0;
/*     */       try {
/* 472 */         byte[] arrayOfByte = new byte[8192];
/*     */         
/* 474 */         while ((i = bufferedInputStream.read(arrayOfByte, 0, 8192)) >= 0) {
/* 475 */           if (!printRawData(arrayOfByte, i)) {
/* 476 */             bufferedInputStream.close();
/* 477 */             notifyEvent(103);
/* 478 */             throw new PrintException("Problem while spooling data");
/*     */           } 
/*     */         } 
/* 481 */         bufferedInputStream.close();
/* 482 */         if (!endPrintRawData()) {
/* 483 */           notifyEvent(103);
/* 484 */           throw new PrintException("Print job failed to close properly.");
/*     */         } 
/* 486 */         notifyEvent(106);
/* 487 */       } catch (IOException iOException) {
/* 488 */         notifyEvent(103);
/* 489 */         throw new PrintException(iOException.toString());
/*     */       } finally {
/* 491 */         notifyEvent(105);
/*     */       } 
/*     */     } else {
/* 494 */       notifyEvent(103);
/* 495 */       throw new PrintException("unrecognized class: " + str);
/*     */     } 
/* 497 */     this.service.wakeNotifier();
/*     */   }
/*     */   
/*     */   public void printableJob(Printable paramPrintable) throws PrintException {
/*     */     try {
/* 502 */       synchronized (this) {
/* 503 */         if (this.job != null) {
/* 504 */           throw new PrintException("already printing");
/*     */         }
/* 506 */         this.job = new WPrinterJob();
/*     */       } 
/*     */       
/* 509 */       PrintService printService = getPrintService();
/* 510 */       this.job.setPrintService(printService);
/* 511 */       if (this.copies == 0) {
/* 512 */         Copies copies = (Copies)printService.getDefaultAttributeValue((Class)Copies.class);
/* 513 */         this.copies = copies.getValue();
/*     */       } 
/*     */       
/* 516 */       if (this.mediaName == null) {
/* 517 */         Object object = printService.getDefaultAttributeValue((Class)Media.class);
/* 518 */         if (object instanceof MediaSizeName) {
/* 519 */           this.mediaName = (MediaSizeName)object;
/* 520 */           this.mediaSize = MediaSize.getMediaSizeForName(this.mediaName);
/*     */         } 
/*     */       } 
/*     */       
/* 524 */       if (this.orient == null) {
/* 525 */         this
/* 526 */           .orient = (OrientationRequested)printService.getDefaultAttributeValue((Class)OrientationRequested.class);
/*     */       }
/*     */       
/* 529 */       this.job.setCopies(this.copies);
/* 530 */       this.job.setJobName(this.jobName);
/* 531 */       PageFormat pageFormat = new PageFormat();
/* 532 */       if (this.mediaSize != null) {
/* 533 */         Paper paper = new Paper();
/* 534 */         paper.setSize(this.mediaSize.getX(25400) * 72.0D, this.mediaSize
/* 535 */             .getY(25400) * 72.0D);
/* 536 */         paper.setImageableArea(72.0D, 72.0D, paper.getWidth() - 144.0D, paper
/* 537 */             .getHeight() - 144.0D);
/* 538 */         pageFormat.setPaper(paper);
/*     */       } 
/* 540 */       if (this.orient == OrientationRequested.REVERSE_LANDSCAPE) {
/* 541 */         pageFormat.setOrientation(2);
/* 542 */       } else if (this.orient == OrientationRequested.LANDSCAPE) {
/* 543 */         pageFormat.setOrientation(0);
/*     */       } 
/* 545 */       this.job.setPrintable(paramPrintable, pageFormat);
/* 546 */       this.job.print(this.reqAttrSet);
/* 547 */       notifyEvent(106);
/*     */       return;
/* 549 */     } catch (PrinterException printerException) {
/* 550 */       notifyEvent(103);
/* 551 */       throw new PrintException(printerException);
/*     */     } finally {
/* 553 */       this.printReturned = true;
/* 554 */       notifyEvent(105);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void pageableJob(Pageable paramPageable) throws PrintException {
/*     */     try {
/* 560 */       synchronized (this) {
/* 561 */         if (this.job != null) {
/* 562 */           throw new PrintException("already printing");
/*     */         }
/* 564 */         this.job = new WPrinterJob();
/*     */       } 
/*     */       
/* 567 */       PrintService printService = getPrintService();
/* 568 */       this.job.setPrintService(printService);
/* 569 */       if (this.copies == 0) {
/* 570 */         Copies copies = (Copies)printService.getDefaultAttributeValue((Class)Copies.class);
/* 571 */         this.copies = copies.getValue();
/*     */       } 
/* 573 */       this.job.setCopies(this.copies);
/* 574 */       this.job.setJobName(this.jobName);
/* 575 */       this.job.setPageable(paramPageable);
/* 576 */       this.job.print(this.reqAttrSet);
/* 577 */       notifyEvent(106);
/*     */       return;
/* 579 */     } catch (PrinterException printerException) {
/* 580 */       notifyEvent(103);
/* 581 */       throw new PrintException(printerException);
/*     */     } finally {
/* 583 */       this.printReturned = true;
/* 584 */       notifyEvent(105);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void initializeAttributeSets(Doc paramDoc, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 594 */     this.reqAttrSet = new HashPrintRequestAttributeSet();
/* 595 */     this.jobAttrSet = new HashPrintJobAttributeSet();
/*     */ 
/*     */     
/* 598 */     if (paramPrintRequestAttributeSet != null) {
/* 599 */       this.reqAttrSet.addAll(paramPrintRequestAttributeSet);
/* 600 */       Attribute[] arrayOfAttribute = paramPrintRequestAttributeSet.toArray();
/* 601 */       for (byte b = 0; b < arrayOfAttribute.length; b++) {
/* 602 */         if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintJobAttribute) {
/* 603 */           this.jobAttrSet.add(arrayOfAttribute[b]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 608 */     DocAttributeSet docAttributeSet = paramDoc.getAttributes();
/* 609 */     if (docAttributeSet != null) {
/* 610 */       Attribute[] arrayOfAttribute = docAttributeSet.toArray();
/* 611 */       for (byte b = 0; b < arrayOfAttribute.length; b++) {
/* 612 */         if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintRequestAttribute) {
/* 613 */           this.reqAttrSet.add(arrayOfAttribute[b]);
/*     */         }
/* 615 */         if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintJobAttribute) {
/* 616 */           this.jobAttrSet.add(arrayOfAttribute[b]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 622 */     String str = "";
/*     */     try {
/* 624 */       str = System.getProperty("user.name");
/* 625 */     } catch (SecurityException securityException) {}
/*     */ 
/*     */     
/* 628 */     if (str == null || str.equals("")) {
/*     */       
/* 630 */       RequestingUserName requestingUserName = (RequestingUserName)paramPrintRequestAttributeSet.get(RequestingUserName.class);
/* 631 */       if (requestingUserName != null) {
/* 632 */         this.jobAttrSet.add(new JobOriginatingUserName(requestingUserName
/* 633 */               .getValue(), requestingUserName
/* 634 */               .getLocale()));
/*     */       } else {
/* 636 */         this.jobAttrSet.add(new JobOriginatingUserName("", null));
/*     */       } 
/*     */     } else {
/* 639 */       this.jobAttrSet.add(new JobOriginatingUserName(str, null));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 644 */     if (this.jobAttrSet.get(JobName.class) == null)
/*     */     {
/* 646 */       if (docAttributeSet != null && docAttributeSet.get(DocumentName.class) != null) {
/*     */         
/* 648 */         DocumentName documentName = (DocumentName)docAttributeSet.get(DocumentName.class);
/* 649 */         JobName jobName = new JobName(documentName.getValue(), documentName.getLocale());
/* 650 */         this.jobAttrSet.add(jobName);
/*     */       } else {
/* 652 */         String str1 = "JPS Job:" + paramDoc;
/*     */         try {
/* 654 */           Object object = paramDoc.getPrintData();
/* 655 */           if (object instanceof URL) {
/* 656 */             str1 = ((URL)paramDoc.getPrintData()).toString();
/*     */           }
/* 658 */         } catch (IOException iOException) {}
/*     */         
/* 660 */         JobName jobName = new JobName(str1, null);
/* 661 */         this.jobAttrSet.add(jobName);
/*     */       } 
/*     */     }
/*     */     
/* 665 */     this.jobAttrSet = AttributeSetUtilities.unmodifiableView(this.jobAttrSet);
/*     */   }
/*     */ 
/*     */   
/*     */   private void getAttributeValues(DocFlavor paramDocFlavor) throws PrintException {
/* 670 */     if (this.reqAttrSet.get(Fidelity.class) == Fidelity.FIDELITY_TRUE) {
/* 671 */       this.fidelity = true;
/*     */     } else {
/* 673 */       this.fidelity = false;
/*     */     } 
/*     */ 
/*     */     
/* 677 */     Attribute[] arrayOfAttribute = this.reqAttrSet.toArray();
/* 678 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/* 679 */       Attribute attribute = arrayOfAttribute[b];
/* 680 */       Class<? extends Attribute> clazz = attribute.getCategory();
/* 681 */       if (this.fidelity == true) {
/* 682 */         if (!this.service.isAttributeCategorySupported(clazz)) {
/* 683 */           notifyEvent(103);
/* 684 */           throw new PrintJobAttributeException("unsupported category: " + clazz, clazz, null);
/*     */         } 
/*     */         
/* 687 */         if (!this.service.isAttributeValueSupported(attribute, paramDocFlavor, null)) {
/* 688 */           notifyEvent(103);
/* 689 */           throw new PrintJobAttributeException("unsupported attribute: " + attribute, null, attribute);
/*     */         } 
/*     */       } 
/*     */       
/* 693 */       if (clazz == Destination.class) {
/* 694 */         URI uRI = ((Destination)attribute).getURI();
/* 695 */         if (!"file".equals(uRI.getScheme())) {
/* 696 */           notifyEvent(103);
/* 697 */           throw new PrintException("Not a file: URI");
/*     */         } 
/*     */         try {
/* 700 */           this.mDestination = (new File(uRI)).getPath();
/* 701 */         } catch (Exception exception) {
/* 702 */           throw new PrintException(exception);
/*     */         } 
/*     */         
/* 705 */         SecurityManager securityManager = System.getSecurityManager();
/* 706 */         if (securityManager != null) {
/*     */           try {
/* 708 */             securityManager.checkWrite(this.mDestination);
/* 709 */           } catch (SecurityException securityException) {
/* 710 */             notifyEvent(103);
/* 711 */             throw new PrintException(securityException);
/*     */           }
/*     */         
/*     */         }
/* 715 */       } else if (clazz == JobName.class) {
/* 716 */         this.jobName = ((JobName)attribute).getValue();
/* 717 */       } else if (clazz == Copies.class) {
/* 718 */         this.copies = ((Copies)attribute).getValue();
/* 719 */       } else if (clazz == Media.class) {
/* 720 */         if (attribute instanceof MediaSizeName) {
/* 721 */           this.mediaName = (MediaSizeName)attribute;
/*     */ 
/*     */ 
/*     */           
/* 725 */           if (!this.service.isAttributeValueSupported(attribute, null, null)) {
/* 726 */             this.mediaSize = MediaSize.getMediaSizeForName(this.mediaName);
/*     */           }
/*     */         } 
/* 729 */       } else if (clazz == OrientationRequested.class) {
/* 730 */         this.orient = (OrientationRequested)attribute;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private native boolean startPrintRawData(String paramString1, String paramString2);
/*     */   
/*     */   private native boolean printRawData(byte[] paramArrayOfbyte, int paramInt);
/*     */   
/*     */   private native boolean endPrintRawData();
/*     */   
/*     */   public void cancel() throws PrintException {
/* 742 */     synchronized (this) {
/* 743 */       if (!this.printing)
/* 744 */         throw new PrintException("Job is not yet submitted."); 
/* 745 */       if (this.job != null && !this.printReturned) {
/* 746 */         this.job.cancel();
/* 747 */         notifyEvent(101);
/*     */         return;
/*     */       } 
/* 750 */       throw new PrintException("Job could not be cancelled.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\print\Win32PrintJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */