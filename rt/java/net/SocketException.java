/*    */ package java.net;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class SocketException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = -5935874303556886934L;
/*    */   
/*    */   public SocketException(String paramString) {
/* 47 */     super(paramString);
/*    */   }
/*    */   
/*    */   public SocketException() {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\SocketException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */