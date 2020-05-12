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
/*     */ public final class CompletionStatus
/*     */   implements IDLEntity
/*     */ {
/*     */   public static final int _COMPLETED_YES = 0;
/*     */   public static final int _COMPLETED_NO = 1;
/*     */   public static final int _COMPLETED_MAYBE = 2;
/*  78 */   public static final CompletionStatus COMPLETED_YES = new CompletionStatus(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final CompletionStatus COMPLETED_NO = new CompletionStatus(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final CompletionStatus COMPLETED_MAYBE = new CompletionStatus(2);
/*     */ 
/*     */ 
/*     */   
/*     */   private int _value;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int value() {
/* 100 */     return this._value;
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
/*     */   public static CompletionStatus from_int(int paramInt) {
/* 117 */     switch (paramInt) {
/*     */       case 0:
/* 119 */         return COMPLETED_YES;
/*     */       case 1:
/* 121 */         return COMPLETED_NO;
/*     */       case 2:
/* 123 */         return COMPLETED_MAYBE;
/*     */     } 
/* 125 */     throw new BAD_PARAM();
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
/*     */   private CompletionStatus(int paramInt) {
/* 138 */     this._value = paramInt;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\CompletionStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */