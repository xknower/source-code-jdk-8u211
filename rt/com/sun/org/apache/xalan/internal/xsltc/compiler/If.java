/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class If
/*     */   extends Instruction
/*     */ {
/*     */   private Expression _test;
/*     */   private boolean _ignore = false;
/*     */   
/*     */   public void display(int indent) {
/*  50 */     indent(indent);
/*  51 */     Util.println("If");
/*  52 */     indent(indent + 4);
/*  53 */     System.out.print("test ");
/*  54 */     Util.println(this._test.toString());
/*  55 */     displayContents(indent + 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/*  63 */     this._test = parser.parseExpression(this, "test", null);
/*     */ 
/*     */     
/*  66 */     if (this._test.isDummy()) {
/*  67 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "test");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  73 */     Object result = this._test.evaluateAtCompileTime();
/*  74 */     if (result != null && result instanceof Boolean) {
/*  75 */       this._ignore = !((Boolean)result).booleanValue();
/*     */     }
/*     */     
/*  78 */     parseChildren(parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/*  87 */     if (!(this._test.typeCheck(stable) instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.BooleanType)) {
/*  88 */       this._test = new CastExpr(this._test, Type.Boolean);
/*     */     }
/*     */     
/*  91 */     if (!this._ignore) {
/*  92 */       typeCheckContents(stable);
/*     */     }
/*  94 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 102 */     InstructionList il = methodGen.getInstructionList();
/* 103 */     this._test.translateDesynthesized(classGen, methodGen);
/*     */     
/* 105 */     InstructionHandle truec = il.getEnd();
/* 106 */     if (!this._ignore) {
/* 107 */       translateContents(classGen, methodGen);
/*     */     }
/* 109 */     this._test.backPatchFalseList(il.append(NOP));
/* 110 */     this._test.backPatchTrueList(truec.getNext());
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\If.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */