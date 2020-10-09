/*    */ package com.sun.xml.internal.ws.client;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.ResourceLoader;
/*    */ import com.sun.xml.internal.ws.api.server.Container;
/*    */ import java.net.MalformedURLException;
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
/*    */ final class ClientContainer
/*    */   extends Container
/*    */ {
/* 39 */   private final ResourceLoader loader = new ResourceLoader() {
/*    */       public URL getResource(String resource) throws MalformedURLException {
/* 41 */         ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 42 */         if (cl == null) {
/* 43 */           cl = getClass().getClassLoader();
/*    */         }
/* 45 */         return cl.getResource("META-INF/" + resource);
/*    */       }
/*    */     };
/*    */   
/*    */   public <T> T getSPI(Class<T> spiType) {
/* 50 */     T t = super.getSPI(spiType);
/* 51 */     if (t != null)
/* 52 */       return t; 
/* 53 */     if (spiType == ResourceLoader.class) {
/* 54 */       return spiType.cast(this.loader);
/*    */     }
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\client\ClientContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */