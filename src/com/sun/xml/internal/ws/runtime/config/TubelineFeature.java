/*    */ package com.sun.xml.internal.ws.runtime.config;
/*    */ 
/*    */ import com.sun.org.glassfish.gmbal.ManagedAttribute;
/*    */ import com.sun.org.glassfish.gmbal.ManagedData;
/*    */ import com.sun.xml.internal.ws.api.FeatureConstructor;
/*    */ import java.util.List;
/*    */ import javax.xml.ws.WebServiceFeature;
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
/*    */ @ManagedData
/*    */ public class TubelineFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   public static final String ID = "com.sun.xml.internal.ws.runtime.config.TubelineFeature";
/*    */   
/*    */   @FeatureConstructor({"enabled"})
/*    */   public TubelineFeature(boolean enabled) {
/* 49 */     this.enabled = enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getID() {
/* 55 */     return "com.sun.xml.internal.ws.runtime.config.TubelineFeature";
/*    */   }
/*    */ 
/*    */   
/*    */   List<String> getTubeFactories() {
/* 60 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\runtime\config\TubelineFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */