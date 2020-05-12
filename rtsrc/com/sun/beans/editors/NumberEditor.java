/*    */ package com.sun.beans.editors;
/*    */ 
/*    */ import java.beans.PropertyEditorSupport;
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
/*    */ public abstract class NumberEditor
/*    */   extends PropertyEditorSupport
/*    */ {
/*    */   public String getJavaInitializationString() {
/* 38 */     Object object = getValue();
/* 39 */     return (object != null) ? object
/* 40 */       .toString() : "null";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\beans\editors\NumberEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */