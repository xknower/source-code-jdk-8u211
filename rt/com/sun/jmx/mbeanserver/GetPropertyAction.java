/*    */ package com.sun.jmx.mbeanserver;
/*    */ 
/*    */ import java.security.PrivilegedAction;
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
/*    */ public class GetPropertyAction
/*    */   implements PrivilegedAction<String>
/*    */ {
/*    */   private final String key;
/*    */   
/*    */   public GetPropertyAction(String paramString) {
/* 40 */     this.key = paramString;
/*    */   }
/*    */   
/*    */   public String run() {
/* 44 */     return System.getProperty(this.key);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\mbeanserver\GetPropertyAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */