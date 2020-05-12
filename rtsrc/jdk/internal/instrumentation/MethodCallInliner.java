/*     */ package jdk.internal.instrumentation;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.Label;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MethodCallInliner
/*     */   extends MethodVisitor
/*     */ {
/*     */   private final String newClass;
/*     */   private final MethodNode inlineTarget;
/*  31 */   private final List<CatchBlock> blocks = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inlining;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Logger logger;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int maxLocals;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodCallInliner(int paramInt1, String paramString1, MethodVisitor paramMethodVisitor, MethodNode paramMethodNode, String paramString2, int paramInt2, Logger paramLogger) {
/*  52 */     super(327680, paramMethodVisitor);
/*  53 */     this.newClass = paramString2;
/*  54 */     this.inlineTarget = paramMethodNode;
/*  55 */     this.logger = paramLogger;
/*  56 */     this.maxLocals = paramInt2;
/*     */     
/*  58 */     paramLogger.trace("MethodCallInliner: targetMethod=" + paramString2 + "." + paramMethodNode.name + paramMethodNode.desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  66 */     if (!shouldBeInlined(paramString1, paramString2, paramString3)) {
/*     */       
/*  68 */       this.mv.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  74 */     this.logger.trace("Inlining call to " + paramString2 + paramString3);
/*  75 */     Label label = new Label();
/*  76 */     this.inlining = true;
/*  77 */     this.inlineTarget.instructions.resetLabels();
/*  78 */     MethodInliningAdapter methodInliningAdapter = new MethodInliningAdapter(this, label, (paramInt == 184) ? 8 : 0, paramString3, this.maxLocals);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     this.inlineTarget.accept(methodInliningAdapter);
/*  84 */     this.logger.trace("Inlining done");
/*  85 */     this.inlining = false;
/*  86 */     visitLabel(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldBeInlined(String paramString1, String paramString2, String paramString3) {
/*  93 */     return (this.inlineTarget.desc.equals(paramString3) && this.inlineTarget.name.equals(paramString2) && paramString1
/*  94 */       .equals(this.newClass.replace('.', '/')));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString) {
/* 100 */     if (!this.inlining) {
/*     */ 
/*     */       
/* 103 */       this.blocks.add(new CatchBlock(paramLabel1, paramLabel2, paramLabel3, paramString));
/*     */     } else {
/* 105 */       super.visitTryCatchBlock(paramLabel1, paramLabel2, paramLabel3, paramString);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMaxs(int paramInt1, int paramInt2) {
/* 111 */     for (CatchBlock catchBlock : this.blocks) {
/* 112 */       super.visitTryCatchBlock(catchBlock.start, catchBlock.end, catchBlock.handler, catchBlock.type);
/*     */     }
/* 114 */     super.visitMaxs(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   static final class CatchBlock
/*     */   {
/*     */     final Label start;
/*     */     final Label end;
/*     */     final Label handler;
/*     */     final String type;
/*     */     
/*     */     CatchBlock(Label param1Label1, Label param1Label2, Label param1Label3, String param1String) {
/* 125 */       this.start = param1Label1;
/* 126 */       this.end = param1Label2;
/* 127 */       this.handler = param1Label3;
/* 128 */       this.type = param1String;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\instrumentation\MethodCallInliner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */