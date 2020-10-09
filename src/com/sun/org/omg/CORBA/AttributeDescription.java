/*    */ package com.sun.org.omg.CORBA;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.IDLEntity;
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
/*    */ public final class AttributeDescription
/*    */   implements IDLEntity
/*    */ {
/* 37 */   public String name = null;
/* 38 */   public String id = null;
/* 39 */   public String defined_in = null;
/* 40 */   public String version = null;
/* 41 */   public TypeCode type = null;
/* 42 */   public AttributeMode mode = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public AttributeDescription() {}
/*    */ 
/*    */   
/*    */   public AttributeDescription(String paramString1, String paramString2, String paramString3, String paramString4, TypeCode paramTypeCode, AttributeMode paramAttributeMode) {
/* 50 */     this.name = paramString1;
/* 51 */     this.id = paramString2;
/* 52 */     this.defined_in = paramString3;
/* 53 */     this.version = paramString4;
/* 54 */     this.type = paramTypeCode;
/* 55 */     this.mode = paramAttributeMode;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\omg\CORBA\AttributeDescription.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */