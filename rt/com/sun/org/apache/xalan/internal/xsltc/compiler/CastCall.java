/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CastCall
/*     */   extends FunctionCall
/*     */ {
/*     */   private String _className;
/*     */   private Expression _right;
/*     */   
/*     */   public CastCall(QName fname, Vector arguments) {
/*  58 */     super(fname, arguments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/*  66 */     if (argumentCount() != 2) {
/*  67 */       throw new TypeCheckError(new ErrorMsg("ILLEGAL_ARG_ERR", 
/*  68 */             getName(), this));
/*     */     }
/*     */ 
/*     */     
/*  72 */     Expression exp = argument(0);
/*  73 */     if (exp instanceof LiteralExpr) {
/*  74 */       this._className = ((LiteralExpr)exp).getValue();
/*  75 */       this._type = Type.newObjectType(this._className);
/*     */     } else {
/*     */       
/*  78 */       throw new TypeCheckError(new ErrorMsg("NEED_LITERAL_ERR", 
/*  79 */             getName(), this));
/*     */     } 
/*     */ 
/*     */     
/*  83 */     this._right = argument(1);
/*  84 */     Type tright = this._right.typeCheck(stable);
/*  85 */     if (tright != Type.Reference && !(tright instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ObjectType))
/*     */     {
/*     */       
/*  88 */       throw new TypeCheckError(new ErrorMsg("DATA_CONVERSION_ERR", tright, this._type, this));
/*     */     }
/*     */ 
/*     */     
/*  92 */     return this._type;
/*     */   }
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/*  96 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  97 */     InstructionList il = methodGen.getInstructionList();
/*     */     
/*  99 */     this._right.translate(classGen, methodGen);
/* 100 */     il.append(new CHECKCAST(cpg.addClass(this._className)));
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\CastCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */