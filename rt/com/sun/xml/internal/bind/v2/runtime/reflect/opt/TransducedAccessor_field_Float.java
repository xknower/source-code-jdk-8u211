/*    */ package com.sun.xml.internal.bind.v2.runtime.reflect.opt;
/*    */ 
/*    */ import com.sun.xml.internal.bind.DatatypeConverterImpl;
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.DefaultTransducedAccessor;
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TransducedAccessor_field_Float
/*    */   extends DefaultTransducedAccessor
/*    */ {
/*    */   public String print(Object o) {
/* 46 */     return DatatypeConverterImpl._printFloat(((Bean)o).f_float);
/*    */   }
/*    */   
/*    */   public void parse(Object o, CharSequence lexical) {
/* 50 */     ((Bean)o).f_float = DatatypeConverterImpl._parseFloat(lexical);
/*    */   }
/*    */   
/*    */   public boolean hasValue(Object o) {
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\reflect\opt\TransducedAccessor_field_Float.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */