/*    */ package com.sun.beans.decoder;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class AccessorElementHandler
/*    */   extends ElementHandler
/*    */ {
/*    */   private String name;
/*    */   private ValueObject value;
/*    */   
/*    */   public void addAttribute(String paramString1, String paramString2) {
/* 56 */     if (paramString1.equals("name")) {
/* 57 */       this.name = paramString2;
/*    */     } else {
/* 59 */       super.addAttribute(paramString1, paramString2);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final void addArgument(Object paramObject) {
/* 70 */     if (this.value != null) {
/* 71 */       throw new IllegalStateException("Could not add argument to evaluated element");
/*    */     }
/* 73 */     setValue(this.name, paramObject);
/* 74 */     this.value = ValueObjectImpl.VOID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final ValueObject getValueObject() {
/* 84 */     if (this.value == null) {
/* 85 */       this.value = ValueObjectImpl.create(getValue(this.name));
/*    */     }
/* 87 */     return this.value;
/*    */   }
/*    */   
/*    */   protected abstract Object getValue(String paramString);
/*    */   
/*    */   protected abstract void setValue(String paramString, Object paramObject);
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\beans\decoder\AccessorElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */