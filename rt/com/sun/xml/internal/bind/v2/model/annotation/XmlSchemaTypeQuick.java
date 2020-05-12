/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlSchemaType;
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
/*    */ final class XmlSchemaTypeQuick
/*    */   extends Quick
/*    */   implements XmlSchemaType
/*    */ {
/*    */   private final XmlSchemaType core;
/*    */   
/*    */   public XmlSchemaTypeQuick(Locatable upstream, XmlSchemaType core) {
/* 44 */     super(upstream);
/* 45 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 49 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 53 */     return new XmlSchemaTypeQuick(upstream, (XmlSchemaType)core);
/*    */   }
/*    */   
/*    */   public Class<XmlSchemaType> annotationType() {
/* 57 */     return XmlSchemaType.class;
/*    */   }
/*    */   
/*    */   public String name() {
/* 61 */     return this.core.name();
/*    */   }
/*    */   
/*    */   public Class type() {
/* 65 */     return this.core.type();
/*    */   }
/*    */   
/*    */   public String namespace() {
/* 69 */     return this.core.namespace();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\annotation\XmlSchemaTypeQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */