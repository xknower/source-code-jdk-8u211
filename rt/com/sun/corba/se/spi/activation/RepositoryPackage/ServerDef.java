/*    */ package com.sun.corba.se.spi.activation.RepositoryPackage;
/*    */ 
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerDef
/*    */   implements IDLEntity
/*    */ {
/* 13 */   public String applicationName = null;
/*    */ 
/*    */   
/* 16 */   public String serverName = null;
/*    */ 
/*    */   
/* 19 */   public String serverClassPath = null;
/*    */ 
/*    */   
/* 22 */   public String serverArgs = null;
/* 23 */   public String serverVmArgs = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerDef() {}
/*    */ 
/*    */   
/*    */   public ServerDef(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 31 */     this.applicationName = paramString1;
/* 32 */     this.serverName = paramString2;
/* 33 */     this.serverClassPath = paramString3;
/* 34 */     this.serverArgs = paramString4;
/* 35 */     this.serverVmArgs = paramString5;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\RepositoryPackage\ServerDef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */