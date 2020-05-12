/*    */ package javax.security.auth.kerberos;
/*    */ 
/*    */ import sun.security.krb5.JavaxSecurityAuthKerberosAccess;
/*    */ import sun.security.krb5.internal.ktab.KeyTab;
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
/*    */ class JavaxSecurityAuthKerberosAccessImpl
/*    */   implements JavaxSecurityAuthKerberosAccess
/*    */ {
/*    */   public KeyTab keyTabTakeSnapshot(KeyTab paramKeyTab) {
/* 36 */     return paramKeyTab.takeSnapshot();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\security\auth\kerberos\JavaxSecurityAuthKerberosAccessImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */