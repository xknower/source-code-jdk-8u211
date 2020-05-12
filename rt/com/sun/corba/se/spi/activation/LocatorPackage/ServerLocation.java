/*    */ package com.sun.corba.se.spi.activation.LocatorPackage;
/*    */ 
/*    */ import com.sun.corba.se.spi.activation.ORBPortInfo;
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerLocation
/*    */   implements IDLEntity
/*    */ {
/* 13 */   public String hostname = null;
/* 14 */   public ORBPortInfo[] ports = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerLocation() {}
/*    */ 
/*    */   
/*    */   public ServerLocation(String paramString, ORBPortInfo[] paramArrayOfORBPortInfo) {
/* 22 */     this.hostname = paramString;
/* 23 */     this.ports = paramArrayOfORBPortInfo;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\LocatorPackage\ServerLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */