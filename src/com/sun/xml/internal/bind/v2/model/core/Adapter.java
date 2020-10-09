/*    */ package com.sun.xml.internal.bind.v2.model.core;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*    */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*    */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
/*    */ public class Adapter<TypeT, ClassDeclT>
/*    */ {
/*    */   public final ClassDeclT adapterType;
/*    */   public final TypeT defaultType;
/*    */   public final TypeT customType;
/*    */   
/*    */   public Adapter(XmlJavaTypeAdapter spec, AnnotationReader<TypeT, ClassDeclT, ?, ?> reader, Navigator<TypeT, ClassDeclT, ?, ?> nav) {
/* 68 */     this(nav.asDecl(reader.getClassValue(spec, "value")), nav);
/*    */   }
/*    */   
/*    */   public Adapter(ClassDeclT adapterType, Navigator<TypeT, ClassDeclT, ?, ?> nav) {
/* 72 */     this.adapterType = adapterType;
/* 73 */     TypeT baseClass = nav.getBaseClass(nav.use(adapterType), nav.asDecl(XmlAdapter.class));
/*    */ 
/*    */     
/* 76 */     assert baseClass != null;
/*    */     
/* 78 */     if (nav.isParameterizedType(baseClass)) {
/* 79 */       this.defaultType = nav.getTypeArgument(baseClass, 0);
/*    */     } else {
/* 81 */       this.defaultType = nav.ref(Object.class);
/*    */     } 
/* 83 */     if (nav.isParameterizedType(baseClass)) {
/* 84 */       this.customType = nav.getTypeArgument(baseClass, 1);
/*    */     } else {
/* 86 */       this.customType = nav.ref(Object.class);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\core\Adapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */