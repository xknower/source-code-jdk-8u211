/*    */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntegerDV
/*    */   extends DecimalDV
/*    */ {
/*    */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*    */     try {
/* 38 */       return new DecimalDV.XDecimal(content, true);
/* 39 */     } catch (NumberFormatException nfe) {
/* 40 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "integer" });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\dv\xs\IntegerDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */