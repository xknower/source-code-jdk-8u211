/*     */ package javax.management;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BinaryOpValueExp
/*     */   extends QueryEval
/*     */   implements ValueExp
/*     */ {
/*     */   private static final long serialVersionUID = 1216286847881456786L;
/*     */   private int op;
/*     */   private ValueExp exp1;
/*     */   private ValueExp exp2;
/*     */   
/*     */   public BinaryOpValueExp() {}
/*     */   
/*     */   public BinaryOpValueExp(int paramInt, ValueExp paramValueExp1, ValueExp paramValueExp2) {
/*  68 */     this.op = paramInt;
/*  69 */     this.exp1 = paramValueExp1;
/*  70 */     this.exp2 = paramValueExp2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOperator() {
/*  78 */     return this.op;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueExp getLeftValue() {
/*  85 */     return this.exp1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueExp getRightValue() {
/*  92 */     return this.exp2;
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
/*     */   
/*     */   public ValueExp apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
/* 109 */     ValueExp valueExp1 = this.exp1.apply(paramObjectName);
/* 110 */     ValueExp valueExp2 = this.exp2.apply(paramObjectName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     boolean bool = valueExp1 instanceof NumericValueExp;
/*     */     
/* 119 */     if (bool) {
/* 120 */       if (((NumericValueExp)valueExp1).isLong()) {
/* 121 */         long l1 = ((NumericValueExp)valueExp1).longValue();
/* 122 */         long l2 = ((NumericValueExp)valueExp2).longValue();
/*     */         
/* 124 */         switch (this.op) {
/*     */           case 0:
/* 126 */             return Query.value(l1 + l2);
/*     */           case 2:
/* 128 */             return Query.value(l1 * l2);
/*     */           case 1:
/* 130 */             return Query.value(l1 - l2);
/*     */           case 3:
/* 132 */             return Query.value(l1 / l2);
/*     */         } 
/*     */       
/*     */       } else {
/* 136 */         double d1 = ((NumericValueExp)valueExp1).doubleValue();
/* 137 */         double d2 = ((NumericValueExp)valueExp2).doubleValue();
/*     */         
/* 139 */         switch (this.op) {
/*     */           case 0:
/* 141 */             return Query.value(d1 + d2);
/*     */           case 2:
/* 143 */             return Query.value(d1 * d2);
/*     */           case 1:
/* 145 */             return Query.value(d1 - d2);
/*     */           case 3:
/* 147 */             return Query.value(d1 / d2);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 151 */       String str1 = ((StringValueExp)valueExp1).getValue();
/* 152 */       String str2 = ((StringValueExp)valueExp2).getValue();
/*     */       
/* 154 */       switch (this.op) {
/*     */         case 0:
/* 156 */           return new StringValueExp(str1 + str2);
/*     */       } 
/* 158 */       throw new BadStringOperationException(opString());
/*     */     } 
/*     */ 
/*     */     
/* 162 */     throw new BadBinaryOpValueExpException(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     try {
/* 170 */       return parens(this.exp1, true) + " " + opString() + " " + parens(this.exp2, false);
/* 171 */     } catch (BadBinaryOpValueExpException badBinaryOpValueExpException) {
/* 172 */       return "invalid expression";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String parens(ValueExp paramValueExp, boolean paramBoolean) throws BadBinaryOpValueExpException {
/*     */     boolean bool;
/* 214 */     if (paramValueExp instanceof BinaryOpValueExp)
/* 215 */     { int i = ((BinaryOpValueExp)paramValueExp).op;
/* 216 */       if (paramBoolean) {
/* 217 */         bool = (precedence(i) >= precedence(this.op)) ? true : false;
/*     */       } else {
/* 219 */         bool = (precedence(i) > precedence(this.op)) ? true : false;
/*     */       }  }
/* 221 */     else { bool = true; }
/*     */     
/* 223 */     if (bool) {
/* 224 */       return paramValueExp.toString();
/*     */     }
/* 226 */     return "(" + paramValueExp + ")";
/*     */   }
/*     */   
/*     */   private int precedence(int paramInt) throws BadBinaryOpValueExpException {
/* 230 */     switch (paramInt) { case 0: case 1:
/* 231 */         return 0;
/* 232 */       case 2: case 3: return 1; }
/*     */     
/* 234 */     throw new BadBinaryOpValueExpException(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private String opString() throws BadBinaryOpValueExpException {
/* 239 */     switch (this.op) {
/*     */       case 0:
/* 241 */         return "+";
/*     */       case 2:
/* 243 */         return "*";
/*     */       case 1:
/* 245 */         return "-";
/*     */       case 3:
/* 247 */         return "/";
/*     */     } 
/*     */     
/* 250 */     throw new BadBinaryOpValueExpException(this);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setMBeanServer(MBeanServer paramMBeanServer) {
/* 255 */     super.setMBeanServer(paramMBeanServer);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\BinaryOpValueExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */