/*    */ package org.omg.CORBA_2_3.portable;
/*    */ 
/*    */ import org.omg.CORBA.portable.Delegate;
/*    */ import org.omg.CORBA.portable.ObjectImpl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ObjectImpl
/*    */   extends ObjectImpl
/*    */ {
/*    */   public String _get_codebase() {
/* 54 */     Delegate delegate = _get_delegate();
/* 55 */     if (delegate instanceof Delegate)
/* 56 */       return ((Delegate)delegate).get_codebase(this); 
/* 57 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA_2_3\portable\ObjectImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */