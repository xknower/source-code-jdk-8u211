/*    */ package com.sun.corba.se.impl.ior.iiop;
/*    */ 
/*    */ import com.sun.corba.se.impl.logging.IORSystemException;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*    */ public final class IIOPAddressImpl
/*    */   extends IIOPAddressBase
/*    */ {
/*    */   private ORB orb;
/*    */   private IORSystemException wrapper;
/*    */   private String host;
/*    */   private int port;
/*    */   
/*    */   public IIOPAddressImpl(ORB paramORB, String paramString, int paramInt) {
/* 51 */     this.orb = paramORB;
/* 52 */     this.wrapper = IORSystemException.get(paramORB, "oa.ior");
/*    */ 
/*    */     
/* 55 */     if (paramInt < 0 || paramInt > 65535) {
/* 56 */       throw this.wrapper.badIiopAddressPort(new Integer(paramInt));
/*    */     }
/* 58 */     this.host = paramString;
/* 59 */     this.port = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public IIOPAddressImpl(InputStream paramInputStream) {
/* 64 */     this.host = paramInputStream.read_string();
/* 65 */     short s = paramInputStream.read_short();
/* 66 */     this.port = shortToInt(s);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHost() {
/* 71 */     return this.host;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPort() {
/* 76 */     return this.port;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\ior\iiop\IIOPAddressImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */