/*    */ package sun.net.httpserver;
/*    */ 
/*    */ import com.sun.net.httpserver.HttpServer;
/*    */ import com.sun.net.httpserver.HttpsServer;
/*    */ import com.sun.net.httpserver.spi.HttpServerProvider;
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
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
/*    */ public class DefaultHttpServerProvider
/*    */   extends HttpServerProvider
/*    */ {
/*    */   public HttpServer createHttpServer(InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException {
/* 35 */     return new HttpServerImpl(paramInetSocketAddress, paramInt);
/*    */   }
/*    */   
/*    */   public HttpsServer createHttpsServer(InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException {
/* 39 */     return new HttpsServerImpl(paramInetSocketAddress, paramInt);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\httpserver\DefaultHttpServerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */