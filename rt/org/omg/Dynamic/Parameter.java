/*    */ package org.omg.Dynamic;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ParameterMode;
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Parameter
/*    */   implements IDLEntity
/*    */ {
/* 13 */   public Any argument = null;
/* 14 */   public ParameterMode mode = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public Parameter() {}
/*    */ 
/*    */   
/*    */   public Parameter(Any paramAny, ParameterMode paramParameterMode) {
/* 22 */     this.argument = paramAny;
/* 23 */     this.mode = paramParameterMode;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\Dynamic\Parameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */