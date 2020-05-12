/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.Constants;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Constant;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantPool;
/*     */ import java.util.StringTokenizer;
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
/*     */ public abstract class InvokeInstruction
/*     */   extends FieldOrMethod
/*     */   implements ExceptionThrower, TypedInstruction, StackConsumer, StackProducer
/*     */ {
/*     */   InvokeInstruction() {}
/*     */   
/*     */   protected InvokeInstruction(short opcode, int index) {
/*  81 */     super(opcode, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(ConstantPool cp) {
/*  88 */     Constant c = cp.getConstant(this.index);
/*  89 */     StringTokenizer tok = new StringTokenizer(cp.constantToString(c));
/*     */     
/*  91 */     return Constants.OPCODE_NAMES[this.opcode] + " " + tok
/*  92 */       .nextToken().replace('.', '/') + tok.nextToken();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int consumeStack(ConstantPoolGen cpg) {
/*     */     int sum;
/* 101 */     String signature = getSignature(cpg);
/* 102 */     Type[] args = Type.getArgumentTypes(signature);
/*     */ 
/*     */     
/* 105 */     if (this.opcode == 184) {
/* 106 */       sum = 0;
/*     */     } else {
/* 108 */       sum = 1;
/*     */     } 
/* 110 */     int n = args.length;
/* 111 */     for (int i = 0; i < n; i++) {
/* 112 */       sum += args[i].getSize();
/*     */     }
/* 114 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int produceStack(ConstantPoolGen cpg) {
/* 123 */     return getReturnType(cpg).getSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType(ConstantPoolGen cpg) {
/* 129 */     return getReturnType(cpg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMethodName(ConstantPoolGen cpg) {
/* 135 */     return getName(cpg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getReturnType(ConstantPoolGen cpg) {
/* 141 */     return Type.getReturnType(getSignature(cpg));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Type[] getArgumentTypes(ConstantPoolGen cpg) {
/* 147 */     return Type.getArgumentTypes(getSignature(cpg));
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\InvokeInstruction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */