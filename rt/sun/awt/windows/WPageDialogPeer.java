/*    */ package sun.awt.windows;
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
/*    */ final class WPageDialogPeer
/*    */   extends WPrintDialogPeer
/*    */ {
/*    */   WPageDialogPeer(WPageDialog paramWPageDialog) {
/* 31 */     super(paramWPageDialog);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void show() {
/* 42 */     (new Thread(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/*    */             try {
/* 48 */               ((WPrintDialog)WPageDialogPeer.this.target).setRetVal(WPageDialogPeer.this._show());
/* 49 */             } catch (Exception exception) {}
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 54 */             ((WPrintDialog)WPageDialogPeer.this.target).setVisible(false);
/*    */           }
/* 56 */         })).start();
/*    */   }
/*    */   
/*    */   private native boolean _show();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WPageDialogPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */