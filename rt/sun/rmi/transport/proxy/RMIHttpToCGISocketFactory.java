/*    */ package sun.rmi.transport.proxy;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.ServerSocket;
/*    */ import java.net.Socket;
/*    */ import java.net.URL;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RMIHttpToCGISocketFactory
/*    */   extends RMISocketFactory
/*    */ {
/*    */   public Socket createSocket(String paramString, int paramInt) throws IOException {
/* 45 */     return new HttpSendSocket(paramString, paramInt, new URL("http", paramString, "/cgi-bin/java-rmi.cgi?forward=" + paramInt));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerSocket createServerSocket(int paramInt) throws IOException {
/* 53 */     return new HttpAwareServerSocket(paramInt);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\transport\proxy\RMIHttpToCGISocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */