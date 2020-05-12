/*    */ package sun.net.www.protocol.http;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
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
/*    */ public class Handler
/*    */   extends URLStreamHandler
/*    */ {
/*    */   protected String proxy;
/*    */   protected int proxyPort;
/*    */   
/*    */   protected int getDefaultPort() {
/* 42 */     return 80;
/*    */   }
/*    */   
/*    */   public Handler() {
/* 46 */     this.proxy = null;
/* 47 */     this.proxyPort = -1;
/*    */   }
/*    */   
/*    */   public Handler(String paramString, int paramInt) {
/* 51 */     this.proxy = paramString;
/* 52 */     this.proxyPort = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   protected URLConnection openConnection(URL paramURL) throws IOException {
/* 57 */     return openConnection(paramURL, (Proxy)null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected URLConnection openConnection(URL paramURL, Proxy paramProxy) throws IOException {
/* 62 */     return new HttpURLConnection(paramURL, paramProxy, this);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\www\protocol\http\Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */