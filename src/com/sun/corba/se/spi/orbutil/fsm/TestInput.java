/*    */ package com.sun.corba.se.spi.orbutil.fsm;
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
/*    */ class TestInput
/*    */ {
/*    */   Input value;
/*    */   String msg;
/*    */   
/*    */   TestInput(Input paramInput, String paramString) {
/* 39 */     this.value = paramInput;
/* 40 */     this.msg = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return "Input " + this.value + " : " + this.msg;
/*    */   }
/*    */ 
/*    */   
/*    */   public Input getInput() {
/* 50 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\orbutil\fsm\TestInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */