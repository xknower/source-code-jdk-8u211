/*    */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
/*    */ import com.sun.org.apache.xerces.internal.util.SymbolHash;
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
/*    */ public class ExtendedSchemaDVFactoryImpl
/*    */   extends BaseSchemaDVFactory
/*    */ {
/* 39 */   static SymbolHash fBuiltInTypes = new SymbolHash();
/*    */   static {
/* 41 */     createBuiltInTypes();
/*    */   }
/*    */ 
/*    */   
/*    */   static void createBuiltInTypes() {
/* 46 */     String ANYATOMICTYPE = "anyAtomicType";
/* 47 */     String DURATION = "duration";
/* 48 */     String YEARMONTHDURATION = "yearMonthDuration";
/* 49 */     String DAYTIMEDURATION = "dayTimeDuration";
/*    */     
/* 51 */     createBuiltInTypes(fBuiltInTypes, XSSimpleTypeDecl.fAnyAtomicType);
/*    */ 
/*    */     
/* 54 */     fBuiltInTypes.put("anyAtomicType", XSSimpleTypeDecl.fAnyAtomicType);
/*    */ 
/*    */     
/* 57 */     XSSimpleTypeDecl durationDV = (XSSimpleTypeDecl)fBuiltInTypes.get("duration");
/* 58 */     fBuiltInTypes.put("yearMonthDuration", new XSSimpleTypeDecl(durationDV, "yearMonthDuration", (short)27, (short)1, false, false, false, true, (short)46));
/* 59 */     fBuiltInTypes.put("dayTimeDuration", new XSSimpleTypeDecl(durationDV, "dayTimeDuration", (short)28, (short)1, false, false, false, true, (short)47));
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
/*    */   public XSSimpleType getBuiltInType(String name) {
/* 74 */     return (XSSimpleType)fBuiltInTypes.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SymbolHash getBuiltInTypes() {
/* 84 */     return fBuiltInTypes.makeClone();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\dv\xs\ExtendedSchemaDVFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */