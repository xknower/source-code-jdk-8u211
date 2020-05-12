/*    */ package java.lang;
/*    */ 
/*    */ import sun.misc.Signal;
/*    */ import sun.misc.SignalHandler;
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
/*    */ class Terminator
/*    */ {
/* 42 */   private static SignalHandler handler = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void setup() {
/* 49 */     if (handler != null)
/* 50 */       return;  SignalHandler signalHandler = new SignalHandler() {
/*    */         public void handle(Signal param1Signal) {
/* 52 */           Shutdown.exit(param1Signal.getNumber() + 128);
/*    */         }
/*    */       };
/* 55 */     handler = signalHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 61 */       Signal.handle(new Signal("INT"), signalHandler);
/* 62 */     } catch (IllegalArgumentException illegalArgumentException) {}
/*    */     
/*    */     try {
/* 65 */       Signal.handle(new Signal("TERM"), signalHandler);
/* 66 */     } catch (IllegalArgumentException illegalArgumentException) {}
/*    */   }
/*    */   
/*    */   static void teardown() {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\Terminator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */