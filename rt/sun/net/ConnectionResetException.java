/*    */ package sun.net;
/*    */ 
/*    */ import java.net.SocketException;
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
/*    */ public class ConnectionResetException
/*    */   extends SocketException
/*    */ {
/*    */   private static final long serialVersionUID = -7633185991801851556L;
/*    */   
/*    */   public ConnectionResetException(String paramString) {
/* 40 */     super(paramString);
/*    */   }
/*    */   
/*    */   public ConnectionResetException() {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\ConnectionResetException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */