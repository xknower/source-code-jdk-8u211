/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TryCatchBlockSorter
/*     */   extends MethodNode
/*     */ {
/*     */   public TryCatchBlockSorter(MethodVisitor paramMethodVisitor, int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  89 */     this(327680, paramMethodVisitor, paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected TryCatchBlockSorter(int paramInt1, MethodVisitor paramMethodVisitor, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  95 */     super(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramArrayOfString);
/*  96 */     this.mv = paramMethodVisitor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 102 */     Comparator<TryCatchBlockNode> comparator = new Comparator<TryCatchBlockNode>()
/*     */       {
/*     */         public int compare(TryCatchBlockNode param1TryCatchBlockNode1, TryCatchBlockNode param1TryCatchBlockNode2) {
/* 105 */           int i = blockLength(param1TryCatchBlockNode1);
/* 106 */           int j = blockLength(param1TryCatchBlockNode2);
/* 107 */           return i - j;
/*     */         }
/*     */         
/*     */         private int blockLength(TryCatchBlockNode param1TryCatchBlockNode) {
/* 111 */           int i = TryCatchBlockSorter.this.instructions.indexOf(param1TryCatchBlockNode.start);
/* 112 */           int j = TryCatchBlockSorter.this.instructions.indexOf(param1TryCatchBlockNode.end);
/* 113 */           return j - i;
/*     */         }
/*     */       };
/* 116 */     Collections.sort(this.tryCatchBlocks, comparator);
/*     */     
/* 118 */     for (byte b = 0; b < this.tryCatchBlocks.size(); b++) {
/* 119 */       ((TryCatchBlockNode)this.tryCatchBlocks.get(b)).updateIndex(b);
/*     */     }
/* 121 */     if (this.mv != null)
/* 122 */       accept(this.mv); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\org\objectweb\asm\commons\TryCatchBlockSorter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */