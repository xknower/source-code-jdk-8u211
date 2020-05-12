/*    */ package java.rmi.activation;
/*    */ 
/*    */ import java.rmi.RemoteException;
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
/*    */ public class ActivateFailedException
/*    */   extends RemoteException
/*    */ {
/*    */   private static final long serialVersionUID = 4863550261346652506L;
/*    */   
/*    */   public ActivateFailedException(String paramString) {
/* 48 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ActivateFailedException(String paramString, Exception paramException) {
/* 60 */     super(paramString, paramException);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\rmi\activation\ActivateFailedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */