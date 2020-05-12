/*     */ package com.sun.org.apache.bcel.internal.generic;
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
/*     */ public interface InstructionComparator
/*     */ {
/*  74 */   public static final InstructionComparator DEFAULT = new InstructionComparator()
/*     */     {
/*     */       public boolean equals(Instruction i1, Instruction i2) {
/*  77 */         if (i1.opcode == i2.opcode) {
/*  78 */           if (i1 instanceof Select)
/*  79 */           { InstructionHandle[] t1 = ((Select)i1).getTargets();
/*  80 */             InstructionHandle[] t2 = ((Select)i2).getTargets();
/*     */             
/*  82 */             if (t1.length == t2.length) {
/*  83 */               for (int i = 0; i < t1.length; i++) {
/*  84 */                 if (t1[i] != t2[i]) {
/*  85 */                   return false;
/*     */                 }
/*     */               } 
/*     */               
/*  89 */               return true;
/*     */             }  }
/*  91 */           else { if (i1 instanceof BranchInstruction) {
/*  92 */               return (((BranchInstruction)i1).target == ((BranchInstruction)i2).target);
/*     */             }
/*  94 */             if (i1 instanceof ConstantPushInstruction)
/*  95 */               return ((ConstantPushInstruction)i1).getValue()
/*  96 */                 .equals(((ConstantPushInstruction)i2).getValue()); 
/*  97 */             if (i1 instanceof IndexedInstruction)
/*  98 */               return 
/*  99 */                 (((IndexedInstruction)i1).getIndex() == ((IndexedInstruction)i2).getIndex()); 
/* 100 */             if (i1 instanceof NEWARRAY) {
/* 101 */               return (((NEWARRAY)i1).getTypecode() == ((NEWARRAY)i2).getTypecode());
/*     */             }
/* 103 */             return true; }
/*     */         
/*     */         }
/*     */         
/* 107 */         return false;
/*     */       }
/*     */     };
/*     */   
/*     */   boolean equals(Instruction paramInstruction1, Instruction paramInstruction2);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\InstructionComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */