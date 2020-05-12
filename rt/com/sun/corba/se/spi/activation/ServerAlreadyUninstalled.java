/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerAlreadyUninstalled
/*    */   extends UserException
/*    */ {
/* 13 */   public int serverId = 0;
/*    */ 
/*    */   
/*    */   public ServerAlreadyUninstalled() {
/* 17 */     super(ServerAlreadyUninstalledHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerAlreadyUninstalled(int paramInt) {
/* 22 */     super(ServerAlreadyUninstalledHelper.id());
/* 23 */     this.serverId = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerAlreadyUninstalled(String paramString, int paramInt) {
/* 29 */     super(ServerAlreadyUninstalledHelper.id() + "  " + paramString);
/* 30 */     this.serverId = paramInt;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\ServerAlreadyUninstalled.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */