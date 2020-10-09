/*    */ package sun.misc;
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
/*    */ final class NativeSignalHandler
/*    */   implements SignalHandler
/*    */ {
/*    */   private final long handler;
/*    */   
/*    */   long getHandler() {
/* 35 */     return this.handler;
/*    */   }
/*    */   
/*    */   NativeSignalHandler(long paramLong) {
/* 39 */     this.handler = paramLong;
/*    */   }
/*    */   
/*    */   public void handle(Signal paramSignal) {
/* 43 */     handle0(paramSignal.getNumber(), this.handler);
/*    */   }
/*    */   
/*    */   private static native void handle0(int paramInt, long paramLong);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\NativeSignalHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */