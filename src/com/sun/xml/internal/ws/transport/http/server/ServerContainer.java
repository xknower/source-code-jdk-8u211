/*    */ package com.sun.xml.internal.ws.transport.http.server;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.server.BoundEndpoint;
/*    */ import com.sun.xml.internal.ws.api.server.Container;
/*    */ import com.sun.xml.internal.ws.api.server.Module;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ class ServerContainer
/*    */   extends Container
/*    */ {
/* 42 */   private final Module module = new Module() {
/* 43 */       private final List<BoundEndpoint> endpoints = new ArrayList<>();
/*    */       @NotNull
/*    */       public List<BoundEndpoint> getBoundEndpoints() {
/* 46 */         return this.endpoints;
/*    */       }
/*    */     };
/*    */   
/*    */   public <T> T getSPI(Class<T> spiType) {
/* 51 */     T t = super.getSPI(spiType);
/* 52 */     if (t != null)
/* 53 */       return t; 
/* 54 */     if (spiType == Module.class) {
/* 55 */       return spiType.cast(this.module);
/*    */     }
/* 57 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\transport\http\server\ServerContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */