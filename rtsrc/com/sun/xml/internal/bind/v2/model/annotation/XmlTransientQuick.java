/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlTransient;
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
/*    */ final class XmlTransientQuick
/*    */   extends Quick
/*    */   implements XmlTransient
/*    */ {
/*    */   private final XmlTransient core;
/*    */   
/*    */   public XmlTransientQuick(Locatable upstream, XmlTransient core) {
/* 44 */     super(upstream);
/* 45 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 49 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 53 */     return new XmlTransientQuick(upstream, (XmlTransient)core);
/*    */   }
/*    */   
/*    */   public Class<XmlTransient> annotationType() {
/* 57 */     return XmlTransient.class;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\annotation\XmlTransientQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */