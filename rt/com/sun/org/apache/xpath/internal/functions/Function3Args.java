/*     */ package com.sun.org.apache.xpath.internal.functions;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*     */ import com.sun.org.apache.xpath.internal.Expression;
/*     */ import com.sun.org.apache.xpath.internal.ExpressionOwner;
/*     */ import com.sun.org.apache.xpath.internal.XPathVisitor;
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
/*     */ public class Function3Args
/*     */   extends Function2Args
/*     */ {
/*     */   static final long serialVersionUID = 7915240747161506646L;
/*     */   Expression m_arg2;
/*     */   
/*     */   public Expression getArg2() {
/*  50 */     return this.m_arg2;
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
/*     */   public void fixupVariables(Vector vars, int globalsSize) {
/*  65 */     super.fixupVariables(vars, globalsSize);
/*  66 */     if (null != this.m_arg2) {
/*  67 */       this.m_arg2.fixupVariables(vars, globalsSize);
/*     */     }
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
/*     */   public void setArg(Expression arg, int argNum) throws WrongNumberArgsException {
/*  83 */     if (argNum < 2) {
/*  84 */       super.setArg(arg, argNum);
/*  85 */     } else if (2 == argNum) {
/*     */       
/*  87 */       this.m_arg2 = arg;
/*  88 */       arg.exprSetParent(this);
/*     */     } else {
/*     */       
/*  91 */       reportWrongNumberArgs();
/*     */     } 
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
/*     */   public void checkNumberArgs(int argNum) throws WrongNumberArgsException {
/* 104 */     if (argNum != 3) {
/* 105 */       reportWrongNumberArgs();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void reportWrongNumberArgs() throws WrongNumberArgsException {
/* 115 */     throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("three", null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTraverseOutsideSubtree() {
/* 126 */     return super.canTraverseOutsideSubtree() ? true : this.m_arg2
/* 127 */       .canTraverseOutsideSubtree();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class Arg2Owner
/*     */     implements ExpressionOwner
/*     */   {
/*     */     public Expression getExpression() {
/* 137 */       return Function3Args.this.m_arg2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setExpression(Expression exp) {
/* 146 */       exp.exprSetParent(Function3Args.this);
/* 147 */       Function3Args.this.m_arg2 = exp;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void callArgVisitors(XPathVisitor visitor) {
/* 157 */     super.callArgVisitors(visitor);
/* 158 */     if (null != this.m_arg2) {
/* 159 */       this.m_arg2.callVisitors(new Arg2Owner(), visitor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deepEquals(Expression expr) {
/* 167 */     if (!super.deepEquals(expr)) {
/* 168 */       return false;
/*     */     }
/* 170 */     if (null != this.m_arg2) {
/*     */       
/* 172 */       if (null == ((Function3Args)expr).m_arg2) {
/* 173 */         return false;
/*     */       }
/* 175 */       if (!this.m_arg2.deepEquals(((Function3Args)expr).m_arg2)) {
/* 176 */         return false;
/*     */       }
/* 178 */     } else if (null != ((Function3Args)expr).m_arg2) {
/* 179 */       return false;
/*     */     } 
/* 181 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\functions\Function3Args.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */