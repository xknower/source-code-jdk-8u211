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
/*    */ public class MethodAccessor_Double
/*    */   extends Accessor
/*    */ {
/*    */   public MethodAccessor_Double() {
/* 42 */     super((Class)Double.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 46 */     return Double.valueOf(((Bean)bean).get_double());
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 50 */     ((Bean)bean).set_double((value == null) ? Const.default_value_double : ((Double)value).doubleValue());
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\reflect\opt\MethodAccessor_Double.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */