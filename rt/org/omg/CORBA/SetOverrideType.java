/*     */ package org.omg.CORBA;
/*     */ 
/*     */ import org.omg.CORBA.portable.IDLEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SetOverrideType
/*     */   implements IDLEntity
/*     */ {
/*     */   public static final int _SET_OVERRIDE = 0;
/*     */   public static final int _ADD_OVERRIDE = 1;
/*  65 */   public static final SetOverrideType SET_OVERRIDE = new SetOverrideType(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final SetOverrideType ADD_OVERRIDE = new SetOverrideType(1);
/*     */ 
/*     */   
/*     */   private int _value;
/*     */ 
/*     */ 
/*     */   
/*     */   public int value() {
/*  78 */     return this._value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SetOverrideType from_int(int paramInt) {
/*  96 */     switch (paramInt) {
/*     */       case 0:
/*  98 */         return SET_OVERRIDE;
/*     */       case 1:
/* 100 */         return ADD_OVERRIDE;
/*     */     } 
/* 102 */     throw new BAD_PARAM();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SetOverrideType(int paramInt) {
/* 113 */     this._value = paramInt;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\SetOverrideType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */