/*    */ package org.omg.CosNaming;
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
/*    */ public class BindingType
/*    */   implements IDLEntity
/*    */ {
/*    */   private int __value;
/* 19 */   private static int __size = 2;
/* 20 */   private static BindingType[] __array = new BindingType[__size];
/*    */   
/*    */   public static final int _nobject = 0;
/* 23 */   public static final BindingType nobject = new BindingType(0);
/*    */   public static final int _ncontext = 1;
/* 25 */   public static final BindingType ncontext = new BindingType(1);
/*    */ 
/*    */   
/*    */   public int value() {
/* 29 */     return this.__value;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BindingType from_int(int paramInt) {
/* 34 */     if (paramInt >= 0 && paramInt < __size) {
/* 35 */       return __array[paramInt];
/*    */     }
/* 37 */     throw new BAD_PARAM();
/*    */   }
/*    */ 
/*    */   
/*    */   protected BindingType(int paramInt) {
/* 42 */     this.__value = paramInt;
/* 43 */     __array[this.__value] = this;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CosNaming\BindingType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */