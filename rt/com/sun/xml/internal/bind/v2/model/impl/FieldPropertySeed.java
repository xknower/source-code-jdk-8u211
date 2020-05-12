/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*    */ import com.sun.xml.internal.bind.v2.runtime.Location;
/*    */ import java.lang.annotation.Annotation;
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
/*    */ class FieldPropertySeed<TypeT, ClassDeclT, FieldT, MethodT>
/*    */   implements PropertySeed<TypeT, ClassDeclT, FieldT, MethodT>
/*    */ {
/*    */   protected final FieldT field;
/*    */   private ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> parent;
/*    */   
/*    */   FieldPropertySeed(ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> classInfo, FieldT field) {
/* 44 */     this.parent = classInfo;
/* 45 */     this.field = field;
/*    */   }
/*    */   
/*    */   public <A extends Annotation> A readAnnotation(Class<A> a) {
/* 49 */     return (A)this.parent.reader().getFieldAnnotation(a, this.field, this);
/*    */   }
/*    */   
/*    */   public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
/* 53 */     return this.parent.reader().hasFieldAnnotation(annotationType, this.field);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 59 */     return this.parent.nav().getFieldName(this.field);
/*    */   }
/*    */   
/*    */   public TypeT getRawType() {
/* 63 */     return (TypeT)this.parent.nav().getFieldType(this.field);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Locatable getUpstream() {
/* 70 */     return this.parent;
/*    */   }
/*    */   
/*    */   public Location getLocation() {
/* 74 */     return this.parent.nav().getFieldLocation(this.field);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\impl\FieldPropertySeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */