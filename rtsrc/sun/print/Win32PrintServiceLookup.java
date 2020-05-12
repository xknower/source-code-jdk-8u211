/*     */ package sun.print;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import javax.print.DocFlavor;
/*     */ import javax.print.MultiDocPrintService;
/*     */ import javax.print.PrintService;
/*     */ import javax.print.PrintServiceLookup;
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.AttributeSet;
/*     */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*     */ import javax.print.attribute.HashPrintServiceAttributeSet;
/*     */ import javax.print.attribute.PrintServiceAttributeSet;
/*     */ import javax.print.attribute.standard.PrinterName;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ 
/*     */ public class Win32PrintServiceLookup
/*     */   extends PrintServiceLookup
/*     */ {
/*     */   private String defaultPrinter;
/*     */   private PrintService defaultPrintService;
/*     */   private String[] printers;
/*     */   private PrintService[] printServices;
/*     */   private static Win32PrintServiceLookup win32PrintLUS;
/*     */   
/*     */   static {
/*  58 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  61 */             System.loadLibrary("awt");
/*  62 */             return null;
/*     */           }
/*     */         });
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
/*     */   
/*     */   public static Win32PrintServiceLookup getWin32PrintLUS() {
/*  77 */     if (win32PrintLUS == null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/*  82 */       PrintServiceLookup.lookupDefaultPrintService();
/*     */     }
/*  84 */     return win32PrintLUS;
/*     */   }
/*     */ 
/*     */   
/*     */   public Win32PrintServiceLookup() {
/*  89 */     if (win32PrintLUS == null) {
/*  90 */       win32PrintLUS = this;
/*     */       
/*  92 */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/*     */ 
/*     */ 
/*     */       
/*  96 */       if (str != null && str.startsWith("Windows 98")) {
/*     */         return;
/*     */       }
/*     */       
/* 100 */       PrinterChangeListener printerChangeListener = new PrinterChangeListener();
/* 101 */       printerChangeListener.setDaemon(true);
/* 102 */       printerChangeListener.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized PrintService[] getPrintServices() {
/* 112 */     SecurityManager securityManager = System.getSecurityManager();
/* 113 */     if (securityManager != null) {
/* 114 */       securityManager.checkPrintJobAccess();
/*     */     }
/* 116 */     if (this.printServices == null) {
/* 117 */       refreshServices();
/*     */     }
/* 119 */     return this.printServices;
/*     */   }
/*     */   
/*     */   private synchronized void refreshServices() {
/* 123 */     this.printers = getAllPrinterNames();
/* 124 */     if (this.printers == null) {
/*     */ 
/*     */       
/* 127 */       this.printServices = new PrintService[0];
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     PrintService[] arrayOfPrintService = new PrintService[this.printers.length];
/* 132 */     PrintService printService = getDefaultPrintService(); byte b;
/* 133 */     for (b = 0; b < this.printers.length; b++) {
/* 134 */       if (printService != null && this.printers[b]
/* 135 */         .equals(printService.getName())) {
/* 136 */         arrayOfPrintService[b] = printService;
/*     */       }
/* 138 */       else if (this.printServices == null) {
/* 139 */         arrayOfPrintService[b] = new Win32PrintService(this.printers[b]);
/*     */       } else {
/*     */         byte b1;
/* 142 */         for (b1 = 0; b1 < this.printServices.length; b1++) {
/* 143 */           if (this.printServices[b1] != null && this.printers[b]
/* 144 */             .equals(this.printServices[b1].getName())) {
/* 145 */             arrayOfPrintService[b] = this.printServices[b1];
/* 146 */             this.printServices[b1] = null;
/*     */             break;
/*     */           } 
/*     */         } 
/* 150 */         if (b1 == this.printServices.length) {
/* 151 */           arrayOfPrintService[b] = new Win32PrintService(this.printers[b]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 158 */     if (this.printServices != null) {
/* 159 */       for (b = 0; b < this.printServices.length; b++) {
/* 160 */         if (this.printServices[b] instanceof Win32PrintService && 
/* 161 */           !this.printServices[b].equals(this.defaultPrintService)) {
/* 162 */           ((Win32PrintService)this.printServices[b]).invalidateService();
/*     */         }
/*     */       } 
/*     */     }
/* 166 */     this.printServices = arrayOfPrintService;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized PrintService getPrintServiceByName(String paramString) {
/* 172 */     if (paramString == null || paramString.equals("")) {
/* 173 */       return null;
/*     */     }
/*     */     
/* 176 */     PrintService[] arrayOfPrintService = getPrintServices();
/* 177 */     for (byte b = 0; b < arrayOfPrintService.length; b++) {
/* 178 */       if (arrayOfPrintService[b].getName().equals(paramString)) {
/* 179 */         return arrayOfPrintService[b];
/*     */       }
/*     */     } 
/* 182 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean matchingService(PrintService paramPrintService, PrintServiceAttributeSet paramPrintServiceAttributeSet) {
/* 188 */     if (paramPrintServiceAttributeSet != null) {
/* 189 */       Attribute[] arrayOfAttribute = paramPrintServiceAttributeSet.toArray();
/*     */       
/* 191 */       for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*     */         
/* 193 */         Object object = paramPrintService.getAttribute((Class)arrayOfAttribute[b].getCategory());
/* 194 */         if (object == null || !object.equals(arrayOfAttribute[b])) {
/* 195 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PrintService[] getPrintServices(DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/* 205 */     SecurityManager securityManager = System.getSecurityManager();
/* 206 */     if (securityManager != null) {
/* 207 */       securityManager.checkPrintJobAccess();
/*     */     }
/* 209 */     HashPrintRequestAttributeSet hashPrintRequestAttributeSet = null;
/* 210 */     HashPrintServiceAttributeSet hashPrintServiceAttributeSet = null;
/*     */     
/* 212 */     if (paramAttributeSet != null && !paramAttributeSet.isEmpty()) {
/*     */       
/* 214 */       hashPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
/* 215 */       hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet();
/*     */       
/* 217 */       Attribute[] arrayOfAttribute = paramAttributeSet.toArray();
/* 218 */       for (byte b1 = 0; b1 < arrayOfAttribute.length; b1++) {
/* 219 */         if (arrayOfAttribute[b1] instanceof javax.print.attribute.PrintRequestAttribute) {
/* 220 */           hashPrintRequestAttributeSet.add(arrayOfAttribute[b1]);
/* 221 */         } else if (arrayOfAttribute[b1] instanceof javax.print.attribute.PrintServiceAttribute) {
/* 222 */           hashPrintServiceAttributeSet.add(arrayOfAttribute[b1]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     PrintService[] arrayOfPrintService = null;
/* 233 */     if (hashPrintServiceAttributeSet != null && hashPrintServiceAttributeSet.get(PrinterName.class) != null) {
/* 234 */       PrinterName printerName = (PrinterName)hashPrintServiceAttributeSet.get(PrinterName.class);
/* 235 */       PrintService printService = getPrintServiceByName(printerName.getValue());
/* 236 */       if (printService == null || !matchingService(printService, hashPrintServiceAttributeSet)) {
/* 237 */         arrayOfPrintService = new PrintService[0];
/*     */       } else {
/* 239 */         arrayOfPrintService = new PrintService[1];
/* 240 */         arrayOfPrintService[0] = printService;
/*     */       } 
/*     */     } else {
/* 243 */       arrayOfPrintService = getPrintServices();
/*     */     } 
/*     */     
/* 246 */     if (arrayOfPrintService.length == 0) {
/* 247 */       return arrayOfPrintService;
/*     */     }
/* 249 */     ArrayList<PrintService> arrayList = new ArrayList();
/* 250 */     for (byte b = 0; b < arrayOfPrintService.length; b++) {
/*     */       try {
/* 252 */         if (arrayOfPrintService[b]
/* 253 */           .getUnsupportedAttributes(paramDocFlavor, hashPrintRequestAttributeSet) == null) {
/* 254 */           arrayList.add(arrayOfPrintService[b]);
/*     */         }
/* 256 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */     } 
/*     */     
/* 259 */     arrayOfPrintService = new PrintService[arrayList.size()];
/* 260 */     return arrayList.<PrintService>toArray(arrayOfPrintService);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiDocPrintService[] getMultiDocPrintServices(DocFlavor[] paramArrayOfDocFlavor, AttributeSet paramAttributeSet) {
/* 270 */     SecurityManager securityManager = System.getSecurityManager();
/* 271 */     if (securityManager != null) {
/* 272 */       securityManager.checkPrintJobAccess();
/*     */     }
/* 274 */     return new MultiDocPrintService[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized PrintService getDefaultPrintService() {
/* 279 */     SecurityManager securityManager = System.getSecurityManager();
/* 280 */     if (securityManager != null) {
/* 281 */       securityManager.checkPrintJobAccess();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     this.defaultPrinter = getDefaultPrinterName();
/* 288 */     if (this.defaultPrinter == null) {
/* 289 */       return null;
/*     */     }
/*     */     
/* 292 */     if (this.defaultPrintService != null && this.defaultPrintService
/* 293 */       .getName().equals(this.defaultPrinter))
/*     */     {
/* 295 */       return this.defaultPrintService;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 301 */     this.defaultPrintService = null;
/*     */     
/* 303 */     if (this.printServices != null) {
/* 304 */       for (byte b = 0; b < this.printServices.length; b++) {
/* 305 */         if (this.defaultPrinter.equals(this.printServices[b].getName())) {
/* 306 */           this.defaultPrintService = this.printServices[b];
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 312 */     if (this.defaultPrintService == null) {
/* 313 */       this.defaultPrintService = new Win32PrintService(this.defaultPrinter);
/*     */     }
/* 315 */     return this.defaultPrintService;
/*     */   } private native String getDefaultPrinterName(); private native String[] getAllPrinterNames();
/*     */   private native long notifyFirstPrinterChange(String paramString);
/*     */   private native void notifyClosePrinterChange(long paramLong);
/*     */   private native int notifyPrinterChange(long paramLong);
/*     */   class PrinterChangeListener extends Thread { PrinterChangeListener() {
/* 321 */       this.chgObj = Win32PrintServiceLookup.this.notifyFirstPrinterChange(null);
/*     */     }
/*     */     long chgObj;
/*     */     public void run() {
/* 325 */       if (this.chgObj != -1L)
/*     */         while (true) {
/*     */           
/* 328 */           if (Win32PrintServiceLookup.this.notifyPrinterChange(this.chgObj) != 0) {
/*     */             try {
/* 330 */               Win32PrintServiceLookup.this.refreshServices(); continue;
/* 331 */             } catch (SecurityException securityException) {
/*     */               break;
/*     */             } 
/*     */           }
/* 335 */           Win32PrintServiceLookup.this.notifyClosePrinterChange(this.chgObj);
/*     */           break;
/*     */         }  
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\print\Win32PrintServiceLookup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */