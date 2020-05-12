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
/*    */ 
/*    */ 
/*    */ public class IdAssignmentPolicyValue
/*    */   implements IDLEntity
/*    */ {
/*    */   private int __value;
/* 24 */   private static int __size = 2;
/* 25 */   private static IdAssignmentPolicyValue[] __array = new IdAssignmentPolicyValue[__size];
/*    */   
/*    */   public static final int _USER_ID = 0;
/* 28 */   public static final IdAssignmentPolicyValue USER_ID = new IdAssignmentPolicyValue(0);
/*    */   public static final int _SYSTEM_ID = 1;
/* 30 */   public static final IdAssignmentPolicyValue SYSTEM_ID = new IdAssignmentPolicyValue(1);
/*    */ 
/*    */   
/*    */   public int value() {
/* 34 */     return this.__value;
/*    */   }
/*    */ 
/*    */   
/*    */   public static IdAssignmentPolicyValue from_int(int paramInt) {
/* 39 */     if (paramInt >= 0 && paramInt < __size) {
/* 40 */       return __array[paramInt];
/*    */     }
/* 42 */     throw new BAD_PARAM();
/*    */   }
/*    */ 
/*    */   
/*    */   protected IdAssignmentPolicyValue(int paramInt) {
/* 47 */     this.__value = paramInt;
/* 48 */     __array[this.__value] = this;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableServer\IdAssignmentPolicyValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */