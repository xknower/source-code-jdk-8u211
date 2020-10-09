/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLFeaturedObject;
/*    */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ abstract class AbstractFeaturedObjectImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements WSDLFeaturedObject
/*    */ {
/*    */   protected WebServiceFeatureList features;
/*    */   
/*    */   protected AbstractFeaturedObjectImpl(XMLStreamReader xsr) {
/* 41 */     super(xsr);
/*    */   }
/*    */   protected AbstractFeaturedObjectImpl(String systemId, int lineNumber) {
/* 44 */     super(systemId, lineNumber);
/*    */   }
/*    */   
/*    */   public final void addFeature(WebServiceFeature feature) {
/* 48 */     if (this.features == null) {
/* 49 */       this.features = new WebServiceFeatureList();
/*    */     }
/* 51 */     this.features.add(feature);
/*    */   }
/*    */   @NotNull
/*    */   public WebServiceFeatureList getFeatures() {
/* 55 */     if (this.features == null)
/* 56 */       return new WebServiceFeatureList(); 
/* 57 */     return this.features;
/*    */   }
/*    */   
/*    */   public final WebServiceFeature getFeature(String id) {
/* 61 */     if (this.features != null) {
/* 62 */       for (WebServiceFeature f : this.features) {
/* 63 */         if (f.getID().equals(id)) {
/* 64 */           return f;
/*    */         }
/*    */       } 
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <F extends WebServiceFeature> F getFeature(@NotNull Class<F> featureType) {
/* 73 */     if (this.features == null) {
/* 74 */       return null;
/*    */     }
/* 76 */     return this.features.get(featureType);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\model\wsdl\AbstractFeaturedObjectImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */