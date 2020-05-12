/*    */ package com.sun.org.omg.CORBA;
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
/*    */ public class OperationMode
/*    */   implements IDLEntity
/*    */ {
/*    */   private int __value;
/* 38 */   private static int __size = 2;
/* 39 */   private static OperationMode[] __array = new OperationMode[__size];
/*    */   
/*    */   public static final int _OP_NORMAL = 0;
/* 42 */   public static final OperationMode OP_NORMAL = new OperationMode(0);
/*    */   public static final int _OP_ONEWAY = 1;
/* 44 */   public static final OperationMode OP_ONEWAY = new OperationMode(1);
/*    */ 
/*    */   
/*    */   public int value() {
/* 48 */     return this.__value;
/*    */   }
/*    */ 
/*    */   
/*    */   public static OperationMode from_int(int paramInt) {
/* 53 */     if (paramInt >= 0 && paramInt < __size) {
/* 54 */       return __array[paramInt];
/*    */     }
/* 56 */     throw new BAD_PARAM();
/*    */   }
/*    */ 
/*    */   
/*    */   protected OperationMode(int paramInt) {
/* 61 */     this.__value = paramInt;
/* 62 */     __array[this.__value] = this;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\omg\CORBA\OperationMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */