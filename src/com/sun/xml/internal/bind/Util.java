/*    */ package com.sun.xml.internal.bind;
/*    */ 
/*    */ import java.util.logging.Logger;
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
/*    */ public final class Util
/*    */ {
/*    */   public static Logger getClassLogger() {
/*    */     try {
/* 43 */       StackTraceElement[] trace = (new Exception()).getStackTrace();
/* 44 */       return Logger.getLogger(trace[1].getClassName());
/* 45 */     } catch (SecurityException e) {
/* 46 */       return Logger.getLogger("com.sun.xml.internal.bind");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getSystemProperty(String name) {
/*    */     try {
/* 55 */       return System.getProperty(name);
/* 56 */     } catch (SecurityException e) {
/* 57 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */