/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class VariableRefBase
/*     */   extends Expression
/*     */ {
/*     */   protected VariableBase _variable;
/*  45 */   protected Closure _closure = null;
/*     */   
/*     */   public VariableRefBase(VariableBase variable) {
/*  48 */     this._variable = variable;
/*  49 */     variable.addReference(this);
/*     */   }
/*     */   
/*     */   public VariableRefBase() {
/*  53 */     this._variable = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VariableBase getVariable() {
/*  60 */     return this._variable;
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
/*     */   public void addParentDependency() {
/*  76 */     SyntaxTreeNode node = this;
/*  77 */     while (node != null && !(node instanceof TopLevelElement)) {
/*  78 */       node = node.getParent();
/*     */     }
/*     */     
/*  81 */     TopLevelElement parent = (TopLevelElement)node;
/*  82 */     if (parent != null) {
/*  83 */       VariableBase var = this._variable;
/*  84 */       if (this._variable._ignore) {
/*  85 */         if (this._variable instanceof Variable) {
/*     */           
/*  87 */           var = parent.getSymbolTable().lookupVariable(this._variable._name);
/*  88 */         } else if (this._variable instanceof Param) {
/*  89 */           var = parent.getSymbolTable().lookupParam(this._variable._name);
/*     */         } 
/*     */       }
/*     */       
/*  93 */       parent.addDependency(var);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 103 */     return (obj == this || (obj instanceof VariableRefBase && this._variable == ((VariableRefBase)obj)._variable));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 109 */     return Objects.hashCode(this._variable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 119 */     return "variable-ref(" + this._variable.getName() + '/' + this._variable.getType() + ')';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 127 */     if (this._type != null) return this._type;
/*     */ 
/*     */     
/* 130 */     if (this._variable.isLocal()) {
/* 131 */       SyntaxTreeNode node = getParent();
/*     */       do {
/* 133 */         if (node instanceof Closure) {
/* 134 */           this._closure = (Closure)node;
/*     */           break;
/*     */         } 
/* 137 */         if (node instanceof TopLevelElement) {
/*     */           break;
/*     */         }
/* 140 */         node = node.getParent();
/* 141 */       } while (node != null);
/*     */       
/* 143 */       if (this._closure != null) {
/* 144 */         this._closure.addVariable(this);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 149 */     this._type = this._variable.getType();
/*     */ 
/*     */ 
/*     */     
/* 153 */     if (this._type == null) {
/* 154 */       this._variable.typeCheck(stable);
/* 155 */       this._type = this._variable.getType();
/*     */     } 
/*     */ 
/*     */     
/* 159 */     addParentDependency();
/*     */ 
/*     */     
/* 162 */     return this._type;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\VariableRefBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */