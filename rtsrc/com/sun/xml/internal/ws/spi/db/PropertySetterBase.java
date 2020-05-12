/*    */ package com.sun.xml.internal.ws.spi.db;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PropertySetterBase
/*    */   implements PropertySetter
/*    */ {
/*    */   protected Class type;
/*    */   
/*    */   public Class getType() {
/* 37 */     return this.type;
/*    */   }
/*    */   
/*    */   public static boolean setterPattern(Method method) {
/* 41 */     return (method.getName().startsWith("set") && method
/* 42 */       .getName().length() > 3 && method
/* 43 */       .getReturnType().equals(void.class) && method
/* 44 */       .getParameterTypes() != null && (method
/* 45 */       .getParameterTypes()).length == 1);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\spi\db\PropertySetterBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */