/*    */ package com.sun.jmx.snmp.IPAcl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.security.acl.Permission;
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
/*    */ class PermissionImpl
/*    */   implements Permission, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4478110422746916589L;
/* 43 */   private String perm = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PermissionImpl(String paramString) {
/* 51 */     this.perm = paramString;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 55 */     return super.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 65 */     if (paramObject instanceof PermissionImpl) {
/* 66 */       return this.perm.equals(((PermissionImpl)paramObject).getString());
/*    */     }
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 78 */     return this.perm;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getString() {
/* 87 */     return this.perm;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\PermissionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */