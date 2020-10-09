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
/*    */ 
/*    */ public class IntegerEditor
/*    */   extends NumberEditor
/*    */ {
/*    */   public void setAsText(String paramString) throws IllegalArgumentException {
/* 39 */     setValue((paramString == null) ? null : Integer.decode(paramString));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\beans\editors\IntegerEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */