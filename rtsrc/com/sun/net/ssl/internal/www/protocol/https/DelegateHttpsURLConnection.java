/*    */ package com.sun.net.ssl.internal.www.protocol.https;
/*    */ 
/*    */ import com.sun.net.ssl.HttpsURLConnection;
/*    */ import java.io.IOException;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import javax.net.ssl.HostnameVerifier;
/*    */ import javax.net.ssl.SSLSocketFactory;
/*    */ import sun.net.www.protocol.http.Handler;
/*    */ import sun.net.www.protocol.https.AbstractDelegateHttpsURLConnection;
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
/*    */ public class DelegateHttpsURLConnection
/*    */   extends AbstractDelegateHttpsURLConnection
/*    */ {
/*    */   public HttpsURLConnection httpsURLConnection;
/*    */   
/*    */   DelegateHttpsURLConnection(URL paramURL, Handler paramHandler, HttpsURLConnection paramHttpsURLConnection) throws IOException {
/* 71 */     this(paramURL, null, paramHandler, paramHttpsURLConnection);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   DelegateHttpsURLConnection(URL paramURL, Proxy paramProxy, Handler paramHandler, HttpsURLConnection paramHttpsURLConnection) throws IOException {
/* 78 */     super(paramURL, paramProxy, paramHandler);
/* 79 */     this.httpsURLConnection = paramHttpsURLConnection;
/*    */   }
/*    */   
/*    */   protected SSLSocketFactory getSSLSocketFactory() {
/* 83 */     return this.httpsURLConnection.getSSLSocketFactory();
/*    */   }
/*    */ 
/*    */   
/*    */   protected HostnameVerifier getHostnameVerifier() {
/* 88 */     return new VerifierWrapper(this.httpsURLConnection.getHostnameVerifier());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void dispose() throws Throwable {
/* 96 */     finalize();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\net\ssl\internal\www\protocol\https\DelegateHttpsURLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */