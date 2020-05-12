/*    */ package com.sun.corba.se.spi.activation.LocatorPackage;
/*    */ 
/*    */ import com.sun.corba.se.spi.activation.EndPointInfo;
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerLocationPerORB
/*    */   implements IDLEntity
/*    */ {
/* 13 */   public String hostname = null;
/* 14 */   public EndPointInfo[] ports = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerLocationPerORB() {}
/*    */ 
/*    */   
/*    */   public ServerLocationPerORB(String paramString, EndPointInfo[] paramArrayOfEndPointInfo) {
/* 22 */     this.hostname = paramString;
/* 23 */     this.ports = paramArrayOfEndPointInfo;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\LocatorPackage\ServerLocationPerORB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */