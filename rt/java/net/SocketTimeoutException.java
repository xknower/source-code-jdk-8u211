/*    */ package java.net;
/*    */ 
/*    */ import java.io.InterruptedIOException;
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
/*    */ public class SocketTimeoutException
/*    */   extends InterruptedIOException
/*    */ {
/*    */   private static final long serialVersionUID = -8846654841826352300L;
/*    */   
/*    */   public SocketTimeoutException(String paramString) {
/* 43 */     super(paramString);
/*    */   }
/*    */   
/*    */   public SocketTimeoutException() {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\SocketTimeoutException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */