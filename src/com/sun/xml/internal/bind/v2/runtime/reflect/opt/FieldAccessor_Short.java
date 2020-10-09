/*    */ package com.sun.xml.internal.bind.v2.runtime.reflect.opt;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldAccessor_Short
/*    */   extends Accessor
/*    */ {
/*    */   public FieldAccessor_Short() {
/* 42 */     super((Class)Short.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 46 */     return Short.valueOf(((Bean)bean).f_short);
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 50 */     ((Bean)bean).f_short = (value == null) ? Const.default_value_short : ((Short)value).shortValue();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\reflect\opt\FieldAccessor_Short.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */