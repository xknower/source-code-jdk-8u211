/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlValue;
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
/*    */ final class XmlValueQuick
/*    */   extends Quick
/*    */   implements XmlValue
/*    */ {
/*    */   private final XmlValue core;
/*    */   
/*    */   public XmlValueQuick(Locatable upstream, XmlValue core) {
/* 44 */     super(upstream);
/* 45 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 49 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 53 */     return new XmlValueQuick(upstream, (XmlValue)core);
/*    */   }
/*    */   
/*    */   public Class<XmlValue> annotationType() {
/* 57 */     return XmlValue.class;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\annotation\XmlValueQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */