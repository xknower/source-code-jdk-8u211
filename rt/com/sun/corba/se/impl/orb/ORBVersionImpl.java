/*    */ package com.sun.corba.se.impl.orb;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.ORBVersion;
/*    */ import org.omg.CORBA.portable.OutputStream;
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
/*    */ public class ORBVersionImpl
/*    */   implements ORBVersion
/*    */ {
/*    */   private byte orbType;
/*    */   
/*    */   public ORBVersionImpl(byte paramByte) {
/* 37 */     this.orbType = paramByte;
/*    */   }
/*    */   
/* 40 */   public static final ORBVersion FOREIGN = new ORBVersionImpl((byte)0);
/*    */ 
/*    */   
/* 43 */   public static final ORBVersion OLD = new ORBVersionImpl((byte)1);
/*    */ 
/*    */   
/* 46 */   public static final ORBVersion NEW = new ORBVersionImpl((byte)2);
/*    */ 
/*    */   
/* 49 */   public static final ORBVersion JDK1_3_1_01 = new ORBVersionImpl((byte)3);
/*    */ 
/*    */   
/* 52 */   public static final ORBVersion NEWER = new ORBVersionImpl((byte)10);
/*    */ 
/*    */   
/* 55 */   public static final ORBVersion PEORB = new ORBVersionImpl((byte)20);
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getORBType() {
/* 60 */     return this.orbType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(OutputStream paramOutputStream) {
/* 65 */     paramOutputStream.write_octet(this.orbType);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return "ORBVersionImpl[" + Byte.toString(this.orbType) + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 75 */     if (!(paramObject instanceof ORBVersion)) {
/* 76 */       return false;
/*    */     }
/* 78 */     ORBVersion oRBVersion = (ORBVersion)paramObject;
/* 79 */     return (oRBVersion.getORBType() == this.orbType);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 84 */     return this.orbType;
/*    */   }
/*    */   
/*    */   public boolean lessThan(ORBVersion paramORBVersion) {
/* 88 */     return (this.orbType < paramORBVersion.getORBType());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(Object paramObject) {
/* 96 */     return getORBType() - ((ORBVersion)paramObject).getORBType();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\orb\ORBVersionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */