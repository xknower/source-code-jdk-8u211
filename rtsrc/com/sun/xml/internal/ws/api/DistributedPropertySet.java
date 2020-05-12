/*    */ package com.sun.xml.internal.ws.api;
/*    */ 
/*    */ import com.oracle.webservices.internal.api.message.BaseDistributedPropertySet;
/*    */ import com.oracle.webservices.internal.api.message.PropertySet;
/*    */ import com.sun.istack.internal.NotNull;
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
/*    */ public abstract class DistributedPropertySet
/*    */   extends BaseDistributedPropertySet
/*    */ {
/*    */   public void addSatellite(@NotNull PropertySet satellite) {
/* 42 */     addSatellite(satellite);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSatellite(@NotNull Class<? extends PropertySet> keyClass, @NotNull PropertySet satellite) {
/* 49 */     addSatellite(keyClass, satellite);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void copySatelliteInto(@NotNull DistributedPropertySet r) {
/* 56 */     copySatelliteInto(r);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeSatellite(PropertySet satellite) {
/* 63 */     removeSatellite(satellite);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\DistributedPropertySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */