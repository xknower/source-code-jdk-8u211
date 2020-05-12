/*    */ package org.omg.CosNaming;
/*    */ 
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Binding
/*    */   implements IDLEntity
/*    */ {
/* 13 */   public NameComponent[] binding_name = null;
/*    */ 
/*    */   
/* 16 */   public BindingType binding_type = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public Binding() {}
/*    */ 
/*    */   
/*    */   public Binding(NameComponent[] paramArrayOfNameComponent, BindingType paramBindingType) {
/* 24 */     this.binding_name = paramArrayOfNameComponent;
/* 25 */     this.binding_type = paramBindingType;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CosNaming\Binding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */