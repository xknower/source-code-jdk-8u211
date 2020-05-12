/*    */ package com.sun.security.jgss;
/*    */ 
/*    */ import javax.security.auth.Subject;
/*    */ import jdk.Exported;
/*    */ import org.ietf.jgss.GSSCredential;
/*    */ import org.ietf.jgss.GSSName;
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
/*    */ @Exported
/*    */ public class GSSUtil
/*    */ {
/*    */   public static Subject createSubject(GSSName paramGSSName, GSSCredential paramGSSCredential) {
/* 70 */     return sun.security.jgss.GSSUtil.getSubject(paramGSSName, paramGSSCredential);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\jgss\GSSUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */