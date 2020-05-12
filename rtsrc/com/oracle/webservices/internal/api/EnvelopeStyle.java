/*     */ package com.oracle.webservices.internal.api;
/*     */ 
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import javax.xml.ws.spi.WebServiceFeatureAnnotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @WebServiceFeatureAnnotation(id = "", bean = EnvelopeStyleFeature.class)
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ public @interface EnvelopeStyle
/*     */ {
/*     */   Style[] style() default {Style.SOAP11};
/*     */   
/*     */   public enum Style
/*     */   {
/*  67 */     SOAP11("http://schemas.xmlsoap.org/wsdl/soap/http"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     SOAP12("http://www.w3.org/2003/05/soap/bindings/HTTP/"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     XML("http://www.w3.org/2004/08/wsdl/http");
/*     */ 
/*     */     
/*     */     public final String bindingId;
/*     */ 
/*     */ 
/*     */     
/*     */     Style(String id) {
/*  87 */       this.bindingId = id;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSOAP11() {
/*  95 */       return equals(SOAP11);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSOAP12() {
/* 102 */       return equals(SOAP12);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isXML() {
/* 109 */       return equals(XML);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\oracle\webservices\internal\api\EnvelopeStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */