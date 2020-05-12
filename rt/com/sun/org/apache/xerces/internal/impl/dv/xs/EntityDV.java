/*    */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*    */ import com.sun.org.apache.xerces.internal.util.XMLChar;
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
/*    */ public class EntityDV
/*    */   extends TypeValidator
/*    */ {
/*    */   public short getAllowedFacets() {
/* 39 */     return 2079;
/*    */   }
/*    */   
/*    */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/* 43 */     if (!XMLChar.isValidNCName(content)) {
/* 44 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "NCName" });
/*    */     }
/*    */     
/* 47 */     return content;
/*    */   }
/*    */   
/*    */   public void checkExtraRules(Object value, ValidationContext context) throws InvalidDatatypeValueException {
/* 51 */     if (!context.isEntityUnparsed((String)value))
/* 52 */       throw new InvalidDatatypeValueException("UndeclaredEntity", new Object[] { value }); 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\dv\xs\EntityDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */