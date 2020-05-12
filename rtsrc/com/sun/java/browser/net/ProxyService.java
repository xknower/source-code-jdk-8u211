/*    */ package com.sun.java.browser.net;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
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
/*    */ public class ProxyService
/*    */ {
/* 36 */   private static ProxyServiceProvider provider = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setProvider(ProxyServiceProvider paramProxyServiceProvider) throws IOException {
/* 41 */     if (null == provider) {
/* 42 */       provider = paramProxyServiceProvider;
/*    */     } else {
/* 44 */       throw new IOException("Proxy service provider has already been set.");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ProxyInfo[] getProxyInfo(URL paramURL) throws IOException {
/* 56 */     if (null == provider) {
/* 57 */       throw new IOException("Proxy service provider is not yet set");
/*    */     }
/* 59 */     return provider.getProxyInfo(paramURL);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\java\browser\net\ProxyService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */