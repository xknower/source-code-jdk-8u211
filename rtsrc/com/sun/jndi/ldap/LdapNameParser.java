/*    */ package com.sun.jndi.ldap;
/*    */ 
/*    */ import javax.naming.Name;
/*    */ import javax.naming.NameParser;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.ldap.LdapName;
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
/*    */ class LdapNameParser
/*    */   implements NameParser
/*    */ {
/*    */   public Name parse(String paramString) throws NamingException {
/* 39 */     return new LdapName(paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jndi\ldap\LdapNameParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */