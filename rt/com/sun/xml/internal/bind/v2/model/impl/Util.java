/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*    */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationSource;
/*    */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*    */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*    */ import javax.activation.MimeType;
/*    */ import javax.activation.MimeTypeParseException;
/*    */ import javax.xml.bind.annotation.XmlMimeType;
/*    */ import javax.xml.bind.annotation.XmlSchemaType;
/*    */ import javax.xml.bind.annotation.XmlSchemaTypes;
/*    */ import javax.xml.namespace.QName;
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
/*    */ final class Util
/*    */ {
/*    */   static <T, C, F, M> QName calcSchemaType(AnnotationReader<T, C, F, M> reader, AnnotationSource primarySource, C enclosingClass, T individualType, Locatable src) {
/* 50 */     XmlSchemaType xst = primarySource.<XmlSchemaType>readAnnotation(XmlSchemaType.class);
/* 51 */     if (xst != null) {
/* 52 */       return new QName(xst.namespace(), xst.name());
/*    */     }
/*    */ 
/*    */     
/* 56 */     XmlSchemaTypes xsts = reader.<XmlSchemaTypes>getPackageAnnotation(XmlSchemaTypes.class, enclosingClass, src);
/* 57 */     XmlSchemaType[] values = null;
/* 58 */     if (xsts != null) {
/* 59 */       values = xsts.value();
/*    */     } else {
/* 61 */       xst = reader.<XmlSchemaType>getPackageAnnotation(XmlSchemaType.class, enclosingClass, src);
/* 62 */       if (xst != null) {
/* 63 */         values = new XmlSchemaType[1];
/* 64 */         values[0] = xst;
/*    */       } 
/*    */     } 
/* 67 */     if (values != null) {
/* 68 */       for (XmlSchemaType item : values) {
/* 69 */         if (reader.getClassValue(item, "type").equals(individualType)) {
/* 70 */           return new QName(item.namespace(), item.name());
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 75 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   static MimeType calcExpectedMediaType(AnnotationSource primarySource, ModelBuilder builder) {
/* 80 */     XmlMimeType xmt = primarySource.<XmlMimeType>readAnnotation(XmlMimeType.class);
/* 81 */     if (xmt == null) {
/* 82 */       return null;
/*    */     }
/*    */     try {
/* 85 */       return new MimeType(xmt.value());
/* 86 */     } catch (MimeTypeParseException e) {
/* 87 */       builder.reportError(new IllegalAnnotationException(Messages.ILLEGAL_MIME_TYPE
/* 88 */             .format(new Object[] { xmt.value(), e.getMessage() }, ), xmt));
/*    */ 
/*    */       
/* 91 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\impl\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */