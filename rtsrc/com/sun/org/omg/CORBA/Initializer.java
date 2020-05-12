/*    */ package com.sun.org.omg.CORBA;
/*    */ 
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.portable.IDLEntity;
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
/*    */ public final class Initializer
/*    */   implements IDLEntity
/*    */ {
/* 41 */   public StructMember[] members = null;
/* 42 */   public String name = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Initializer() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public Initializer(StructMember[] paramArrayOfStructMember, String paramString) {
/* 52 */     this.members = paramArrayOfStructMember;
/* 53 */     this.name = paramString;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\omg\CORBA\Initializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */