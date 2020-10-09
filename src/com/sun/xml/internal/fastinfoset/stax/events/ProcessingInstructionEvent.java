/*    */ package com.sun.xml.internal.fastinfoset.stax.events;
/*    */ 
/*    */ import javax.xml.stream.events.ProcessingInstruction;
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
/*    */ public class ProcessingInstructionEvent
/*    */   extends EventBase
/*    */   implements ProcessingInstruction
/*    */ {
/*    */   private String targetName;
/*    */   private String _data;
/*    */   
/*    */   public ProcessingInstructionEvent() {
/* 40 */     init();
/*    */   }
/*    */   
/*    */   public ProcessingInstructionEvent(String targetName, String data) {
/* 44 */     this.targetName = targetName;
/* 45 */     this._data = data;
/* 46 */     init();
/*    */   }
/*    */   
/*    */   protected void init() {
/* 50 */     setEventType(3);
/*    */   }
/*    */   
/*    */   public String getTarget() {
/* 54 */     return this.targetName;
/*    */   }
/*    */   
/*    */   public void setTarget(String targetName) {
/* 58 */     this.targetName = targetName;
/*    */   }
/*    */   
/*    */   public void setData(String data) {
/* 62 */     this._data = data;
/*    */   }
/*    */   
/*    */   public String getData() {
/* 66 */     return this._data;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 70 */     if (this._data != null && this.targetName != null)
/* 71 */       return "<?" + this.targetName + " " + this._data + "?>"; 
/* 72 */     if (this.targetName != null)
/* 73 */       return "<?" + this.targetName + "?>"; 
/* 74 */     if (this._data != null) {
/* 75 */       return "<?" + this._data + "?>";
/*    */     }
/* 77 */     return "<??>";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\fastinfoset\stax\events\ProcessingInstructionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */