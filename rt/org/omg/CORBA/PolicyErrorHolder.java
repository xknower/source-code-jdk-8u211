/*    */ package org.omg.CORBA;
/*    */ 
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PolicyErrorHolder
/*    */   implements Streamable
/*    */ {
/* 17 */   public PolicyError value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public PolicyErrorHolder() {}
/*    */ 
/*    */   
/*    */   public PolicyErrorHolder(PolicyError paramPolicyError) {
/* 25 */     this.value = paramPolicyError;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 30 */     this.value = PolicyErrorHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 35 */     PolicyErrorHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 40 */     return PolicyErrorHelper.type();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\PolicyErrorHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */