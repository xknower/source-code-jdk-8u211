/*    */ package sun.awt.windows;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dialog;
/*    */ import java.awt.Frame;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.print.PageFormat;
/*    */ import java.awt.print.Printable;
/*    */ import java.awt.print.PrinterJob;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class WPageDialog
/*    */   extends WPrintDialog
/*    */ {
/*    */   PageFormat page;
/*    */   Printable painter;
/*    */   
/*    */   static {
/* 39 */     initIDs();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   WPageDialog(Frame paramFrame, PrinterJob paramPrinterJob, PageFormat paramPageFormat, Printable paramPrintable) {
/* 46 */     super(paramFrame, paramPrinterJob);
/* 47 */     this.page = paramPageFormat;
/* 48 */     this.painter = paramPrintable;
/*    */   }
/*    */ 
/*    */   
/*    */   WPageDialog(Dialog paramDialog, PrinterJob paramPrinterJob, PageFormat paramPageFormat, Printable paramPrintable) {
/* 53 */     super(paramDialog, paramPrinterJob);
/* 54 */     this.page = paramPageFormat;
/* 55 */     this.painter = paramPrintable;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addNotify() {
/* 61 */     synchronized (getTreeLock()) {
/* 62 */       Container container = getParent();
/* 63 */       if (container != null && container.getPeer() == null) {
/* 64 */         container.addNotify();
/*    */       }
/*    */       
/* 67 */       if (getPeer() == null) {
/*    */         
/* 69 */         WPageDialogPeer wPageDialogPeer = ((WToolkit)Toolkit.getDefaultToolkit()).createWPageDialog(this);
/* 70 */         setPeer(wPageDialogPeer);
/*    */       } 
/* 72 */       super.addNotify();
/*    */     } 
/*    */   }
/*    */   
/*    */   private static native void initIDs();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WPageDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */