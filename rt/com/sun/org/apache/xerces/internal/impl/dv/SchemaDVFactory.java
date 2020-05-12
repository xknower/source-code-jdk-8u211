/*    */ package com.sun.org.apache.xerces.internal.impl.dv;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.util.SymbolHash;
/*    */ import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
/*    */ import com.sun.org.apache.xerces.internal.xs.XSObjectList;
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
/*    */ public abstract class SchemaDVFactory
/*    */ {
/*    */   private static final String DEFAULT_FACTORY_CLASS = "com.sun.org.apache.xerces.internal.impl.dv.xs.SchemaDVFactoryImpl";
/*    */   
/*    */   public static final synchronized SchemaDVFactory getInstance() throws DVFactoryException {
/* 57 */     return getInstance("com.sun.org.apache.xerces.internal.impl.dv.xs.SchemaDVFactoryImpl");
/*    */   }
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
/*    */   public static final synchronized SchemaDVFactory getInstance(String factoryClass) throws DVFactoryException {
/*    */     try {
/* 73 */       return (SchemaDVFactory)ObjectFactory.newInstance(factoryClass, true);
/* 74 */     } catch (ClassCastException e4) {
/* 75 */       throw new DVFactoryException("Schema factory class " + factoryClass + " does not extend from SchemaDVFactory.");
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract XSSimpleType getBuiltInType(String paramString);
/*    */   
/*    */   public abstract SymbolHash getBuiltInTypes();
/*    */   
/*    */   public abstract XSSimpleType createTypeRestriction(String paramString1, String paramString2, short paramShort, XSSimpleType paramXSSimpleType, XSObjectList paramXSObjectList);
/*    */   
/*    */   public abstract XSSimpleType createTypeList(String paramString1, String paramString2, short paramShort, XSSimpleType paramXSSimpleType, XSObjectList paramXSObjectList);
/*    */   
/*    */   public abstract XSSimpleType createTypeUnion(String paramString1, String paramString2, short paramShort, XSSimpleType[] paramArrayOfXSSimpleType, XSObjectList paramXSObjectList);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\dv\SchemaDVFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */