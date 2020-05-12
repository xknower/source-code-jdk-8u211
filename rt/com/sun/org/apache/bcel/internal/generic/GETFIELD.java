/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.ExceptionConstants;
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
/*     */ 
/*     */ 
/*     */ public class GETFIELD
/*     */   extends FieldInstruction
/*     */   implements ExceptionThrower, StackConsumer, StackProducer
/*     */ {
/*     */   GETFIELD() {}
/*     */   
/*     */   public GETFIELD(int index) {
/*  81 */     super((short)180, index);
/*     */   }
/*     */   public int produceStack(ConstantPoolGen cpg) {
/*  84 */     return getFieldSize(cpg);
/*     */   }
/*     */   public Class[] getExceptions() {
/*  87 */     Class[] cs = new Class[2 + ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length];
/*     */     
/*  89 */     System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length);
/*     */ 
/*     */     
/*  92 */     cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 1] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
/*     */     
/*  94 */     cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.NULL_POINTER_EXCEPTION;
/*     */ 
/*     */     
/*  97 */     return cs;
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
/*     */   public void accept(Visitor v) {
/* 110 */     v.visitExceptionThrower(this);
/* 111 */     v.visitStackConsumer(this);
/* 112 */     v.visitStackProducer(this);
/* 113 */     v.visitTypedInstruction(this);
/* 114 */     v.visitLoadClass(this);
/* 115 */     v.visitCPInstruction(this);
/* 116 */     v.visitFieldOrMethod(this);
/* 117 */     v.visitFieldInstruction(this);
/* 118 */     v.visitGETFIELD(this);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\GETFIELD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */