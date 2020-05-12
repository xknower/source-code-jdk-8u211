/*    */ package com.oracle.nio;
/*    */ 
/*    */ import java.security.BasicPermission;
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
/*    */ public final class BufferSecretsPermission
/*    */   extends BasicPermission
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   public BufferSecretsPermission(String paramString) {
/* 30 */     super(paramString);
/* 31 */     if (!paramString.equals("access"))
/* 32 */       throw new IllegalArgumentException(); 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\oracle\nio\BufferSecretsPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */