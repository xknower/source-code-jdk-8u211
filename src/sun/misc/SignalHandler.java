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
/*    */ public interface SignalHandler
/*    */ {
/* 42 */   public static final SignalHandler SIG_DFL = new NativeSignalHandler(0L);
/*    */ 
/*    */ 
/*    */   
/* 46 */   public static final SignalHandler SIG_IGN = new NativeSignalHandler(1L);
/*    */   
/*    */   void handle(Signal paramSignal);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\SignalHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */