/*    */ package com.sun.corba.se.impl.corba;
/*    */ 
/*    */ import org.omg.CORBA.Environment;
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
/*    */ public class EnvironmentImpl
/*    */   extends Environment
/*    */ {
/*    */   private Exception _exc;
/*    */   
/*    */   public Exception exception() {
/* 48 */     return this._exc;
/*    */   }
/*    */ 
/*    */   
/*    */   public void exception(Exception paramException) {
/* 53 */     this._exc = paramException;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 58 */     this._exc = null;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\corba\EnvironmentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */