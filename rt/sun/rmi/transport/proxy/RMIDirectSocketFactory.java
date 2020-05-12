/*    */ package sun.rmi.transport.proxy;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.ServerSocket;
/*    */ import java.net.Socket;
/*    */ import java.rmi.server.RMISocketFactory;
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
/*    */ public class RMIDirectSocketFactory
/*    */   extends RMISocketFactory
/*    */ {
/*    */   public Socket createSocket(String paramString, int paramInt) throws IOException {
/* 40 */     return new Socket(paramString, paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerSocket createServerSocket(int paramInt) throws IOException {
/* 45 */     return new ServerSocket(paramInt);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\transport\proxy\RMIDirectSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */