/*    */ package com.sun.corba.se.impl.ior.iiop;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.TaggedComponentBase;
/*    */ import com.sun.corba.se.spi.ior.iiop.AlternateIIOPAddressComponent;
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
/*    */ public class AlternateIIOPAddressComponentImpl
/*    */   extends TaggedComponentBase
/*    */   implements AlternateIIOPAddressComponent
/*    */ {
/*    */   private IIOPAddress addr;
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 47 */     if (!(paramObject instanceof AlternateIIOPAddressComponentImpl)) {
/* 48 */       return false;
/*    */     }
/* 50 */     AlternateIIOPAddressComponentImpl alternateIIOPAddressComponentImpl = (AlternateIIOPAddressComponentImpl)paramObject;
/*    */ 
/*    */     
/* 53 */     return this.addr.equals(alternateIIOPAddressComponentImpl.addr);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 58 */     return this.addr.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return "AlternateIIOPAddressComponentImpl[addr=" + this.addr + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public AlternateIIOPAddressComponentImpl(IIOPAddress paramIIOPAddress) {
/* 68 */     this.addr = paramIIOPAddress;
/*    */   }
/*    */ 
/*    */   
/*    */   public IIOPAddress getAddress() {
/* 73 */     return this.addr;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeContents(OutputStream paramOutputStream) {
/* 78 */     this.addr.write(paramOutputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 83 */     return 3;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\ior\iiop\AlternateIIOPAddressComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */