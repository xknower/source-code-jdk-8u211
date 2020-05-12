/*    */ package org.omg.PortableServer;
/*    */ 
/*    */ import org.omg.CORBA.BAD_PARAM;
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadPolicyValue
/*    */   implements IDLEntity
/*    */ {
/*    */   private int __value;
/* 22 */   private static int __size = 2;
/* 23 */   private static ThreadPolicyValue[] __array = new ThreadPolicyValue[__size];
/*    */   
/*    */   public static final int _ORB_CTRL_MODEL = 0;
/* 26 */   public static final ThreadPolicyValue ORB_CTRL_MODEL = new ThreadPolicyValue(0);
/*    */   public static final int _SINGLE_THREAD_MODEL = 1;
/* 28 */   public static final ThreadPolicyValue SINGLE_THREAD_MODEL = new ThreadPolicyValue(1);
/*    */ 
/*    */   
/*    */   public int value() {
/* 32 */     return this.__value;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ThreadPolicyValue from_int(int paramInt) {
/* 37 */     if (paramInt >= 0 && paramInt < __size) {
/* 38 */       return __array[paramInt];
/*    */     }
/* 40 */     throw new BAD_PARAM();
/*    */   }
/*    */ 
/*    */   
/*    */   protected ThreadPolicyValue(int paramInt) {
/* 45 */     this.__value = paramInt;
/* 46 */     __array[this.__value] = this;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableServer\ThreadPolicyValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */