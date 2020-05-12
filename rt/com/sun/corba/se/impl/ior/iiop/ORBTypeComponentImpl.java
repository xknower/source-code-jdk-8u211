/*    */ package com.sun.corba.se.impl.ior.iiop;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.TaggedComponentBase;
/*    */ import com.sun.corba.se.spi.ior.iiop.ORBTypeComponent;
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
/*    */ public class ORBTypeComponentImpl
/*    */   extends TaggedComponentBase
/*    */   implements ORBTypeComponent
/*    */ {
/*    */   private int ORBType;
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 46 */     if (!(paramObject instanceof ORBTypeComponentImpl)) {
/* 47 */       return false;
/*    */     }
/* 49 */     ORBTypeComponentImpl oRBTypeComponentImpl = (ORBTypeComponentImpl)paramObject;
/*    */     
/* 51 */     return (this.ORBType == oRBTypeComponentImpl.ORBType);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 56 */     return this.ORBType;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 61 */     return "ORBTypeComponentImpl[ORBType=" + this.ORBType + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public ORBTypeComponentImpl(int paramInt) {
/* 66 */     this.ORBType = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 71 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getORBType() {
/* 76 */     return this.ORBType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeContents(OutputStream paramOutputStream) {
/* 81 */     paramOutputStream.write_ulong(this.ORBType);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\ior\iiop\ORBTypeComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */