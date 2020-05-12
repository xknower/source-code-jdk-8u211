/*    */ package com.sun.corba.se.impl.ior.iiop;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.iiop.IIOPAddress;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class IIOPAddressBase
/*    */   implements IIOPAddress
/*    */ {
/*    */   protected short intToShort(int paramInt) {
/* 48 */     if (paramInt > 32767)
/* 49 */       return (short)(paramInt - 65536); 
/* 50 */     return (short)paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int shortToInt(short paramShort) {
/* 55 */     if (paramShort < 0)
/* 56 */       return paramShort + 65536; 
/* 57 */     return paramShort;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(OutputStream paramOutputStream) {
/* 62 */     paramOutputStream.write_string(getHost());
/* 63 */     int i = getPort();
/* 64 */     paramOutputStream.write_short(intToShort(i));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 69 */     if (!(paramObject instanceof IIOPAddress)) {
/* 70 */       return false;
/*    */     }
/* 72 */     IIOPAddress iIOPAddress = (IIOPAddress)paramObject;
/*    */     
/* 74 */     return (getHost().equals(iIOPAddress.getHost()) && 
/* 75 */       getPort() == iIOPAddress.getPort());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 80 */     return getHost().hashCode() ^ getPort();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 85 */     return "IIOPAddress[" + getHost() + "," + getPort() + "]";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\ior\iiop\IIOPAddressBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */