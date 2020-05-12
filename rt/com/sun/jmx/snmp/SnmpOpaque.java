/*    */ package com.sun.jmx.snmp;
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
/*    */ 
/*    */ public class SnmpOpaque
/*    */   extends SnmpString
/*    */ {
/*    */   private static final long serialVersionUID = 380952213936036664L;
/*    */   static final String name = "Opaque";
/*    */   
/*    */   public SnmpOpaque(byte[] paramArrayOfbyte) {
/* 49 */     super(paramArrayOfbyte);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpOpaque(Byte[] paramArrayOfByte) {
/* 57 */     super(paramArrayOfByte);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpOpaque(String paramString) {
/* 65 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 76 */     StringBuffer stringBuffer = new StringBuffer();
/* 77 */     for (byte b = 0; b < this.value.length; b++) {
/* 78 */       byte b1 = this.value[b];
/* 79 */       byte b2 = (b1 >= 0) ? b1 : (b1 + 256);
/* 80 */       stringBuffer.append(Character.forDigit(b2 / 16, 16));
/* 81 */       stringBuffer.append(Character.forDigit(b2 % 16, 16));
/*    */     } 
/* 83 */     return stringBuffer.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final String getTypeName() {
/* 91 */     return "Opaque";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpOpaque.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */