/*    */ package com.sun.management.jmx;
/*    */ 
/*    */ import javax.management.MBeanInfo;
/*    */ import javax.management.NotCompliantMBeanException;
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
/*    */ @Deprecated
/*    */ public class Introspector
/*    */ {
/*    */   @Deprecated
/*    */   public static synchronized MBeanInfo testCompliance(Class<?> paramClass) throws NotCompliantMBeanException {
/* 51 */     return com.sun.jmx.mbeanserver.Introspector.testCompliance(paramClass);
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
/*    */   @Deprecated
/*    */   public static synchronized Class getMBeanInterface(Class<?> paramClass) {
/* 67 */     return com.sun.jmx.mbeanserver.Introspector.getMBeanInterface(paramClass);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\management\jmx\Introspector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */