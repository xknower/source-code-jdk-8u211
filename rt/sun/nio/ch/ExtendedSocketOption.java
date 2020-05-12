/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.net.SocketOption;
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
/*    */ class ExtendedSocketOption
/*    */ {
/* 38 */   static final SocketOption<Boolean> SO_OOBINLINE = new SocketOption<Boolean>() {
/*    */       public String name() {
/* 40 */         return "SO_OOBINLINE";
/* 41 */       } public Class<Boolean> type() { return Boolean.class; } public String toString() {
/* 42 */         return name();
/*    */       }
/*    */     };
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\ExtendedSocketOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */