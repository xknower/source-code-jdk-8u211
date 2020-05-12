/*    */ package com.sun.beans.editors;
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
/*    */ public class FloatEditor
/*    */   extends NumberEditor
/*    */ {
/*    */   public String getJavaInitializationString() {
/* 38 */     Object object = getValue();
/* 39 */     return (object != null) ? (object + "F") : "null";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAsText(String paramString) throws IllegalArgumentException {
/* 45 */     setValue((paramString == null) ? null : Float.valueOf(paramString));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\beans\editors\FloatEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */