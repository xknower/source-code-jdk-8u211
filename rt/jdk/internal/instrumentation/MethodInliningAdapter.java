/*    */ package jdk.internal.instrumentation;
/*    */ 
/*    */ import jdk.internal.org.objectweb.asm.Label;
/*    */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*    */ import jdk.internal.org.objectweb.asm.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class MethodInliningAdapter
/*    */   extends MethodVisitor
/*    */ {
/*    */   private final Label end;
/*    */   private final int remapOffset;
/*    */   
/*    */   public MethodInliningAdapter(MethodVisitor paramMethodVisitor, Label paramLabel, int paramInt1, String paramString, int paramInt2) {
/* 18 */     super(327680, paramMethodVisitor);
/* 19 */     this.remapOffset = paramInt2;
/* 20 */     this.end = paramLabel;
/*    */     
/* 22 */     Type[] arrayOfType = Type.getArgumentTypes(paramString);
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
/* 33 */     int i = isStatic(paramInt1) ? 0 : 1;
/* 34 */     for (Type type : arrayOfType) {
/* 35 */       i += type.getSize();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     int j = i;
/* 42 */     for (int k = arrayOfType.length - 1; k >= 0; k--) {
/* 43 */       j -= arrayOfType[k].getSize();
/* 44 */       int m = j + paramInt2;
/* 45 */       int n = arrayOfType[k].getOpcode(54);
/*    */       
/* 47 */       super.visitVarInsn(n, m);
/*    */     } 
/* 49 */     if (!isStatic(paramInt1))
/*    */     {
/* 51 */       super.visitVarInsn(58, 0 + paramInt2);
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean isStatic(int paramInt) {
/* 56 */     return ((paramInt & 0x8) != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitInsn(int paramInt) {
/* 61 */     if (paramInt == 177 || paramInt == 172 || paramInt == 176 || paramInt == 173) {
/*    */       
/* 63 */       visitJumpInsn(167, this.end);
/*    */     } else {
/* 65 */       super.visitInsn(paramInt);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitVarInsn(int paramInt1, int paramInt2) {
/* 71 */     super.visitVarInsn(paramInt1, paramInt2 + this.remapOffset);
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitIincInsn(int paramInt1, int paramInt2) {
/* 76 */     super.visitIincInsn(paramInt1 + this.remapOffset, paramInt2);
/*    */   }
/*    */   
/*    */   public void visitMaxs(int paramInt1, int paramInt2) {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\instrumentation\MethodInliningAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */