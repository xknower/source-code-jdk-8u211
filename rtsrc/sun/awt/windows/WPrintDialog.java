/*    */ package sun.awt.windows;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dialog;
/*    */ import java.awt.Frame;
/*    */ import java.awt.LayoutManager;
/*    */ import java.awt.PrintJob;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.peer.ComponentPeer;
/*    */ import java.awt.print.PrinterJob;
/*    */ import sun.awt.AWTAccessor;
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
/*    */ class WPrintDialog
/*    */   extends Dialog
/*    */ {
/*    */   protected PrintJob job;
/*    */   protected PrinterJob pjob;
/*    */   private boolean retval;
/*    */   
/*    */   static {
/* 37 */     initIDs();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   WPrintDialog(Frame paramFrame, PrinterJob paramPrinterJob)
/*    */   {
/* 44 */     super(paramFrame, true);
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
/* 77 */     this.retval = false; this.pjob = paramPrinterJob; setLayout((LayoutManager)null); } WPrintDialog(Dialog paramDialog, PrinterJob paramPrinterJob) { super(paramDialog, "", true); this.retval = false;
/*    */     this.pjob = paramPrinterJob;
/*    */     setLayout((LayoutManager)null); }
/* 80 */   final void setPeer(ComponentPeer paramComponentPeer) { AWTAccessor.getComponentAccessor().setPeer(this, paramComponentPeer); } final void setRetVal(boolean paramBoolean) { this.retval = paramBoolean; }
/*    */   public void addNotify() { synchronized (getTreeLock()) { Container container = getParent(); if (container != null && container.getPeer() == null)
/*    */         container.addNotify();  if (getPeer() == null) { WPrintDialogPeer wPrintDialogPeer = ((WToolkit)Toolkit.getDefaultToolkit()).createWPrintDialog(this); setPeer(wPrintDialogPeer); }
/*    */        super.addNotify(); }
/* 84 */      } final boolean getRetVal() { return this.retval; }
/*    */ 
/*    */   
/*    */   private static native void initIDs();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WPrintDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */