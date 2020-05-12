/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpString
/*     */   extends SnmpValue
/*     */ {
/*     */   private static final long serialVersionUID = -7011986973225194188L;
/*     */   static final String name = "String";
/*     */   protected byte[] value;
/*     */   
/*     */   public SnmpString() {
/* 277 */     this.value = null; this.value = new byte[0]; } public SnmpString(byte[] paramArrayOfbyte) { this.value = null; this.value = (byte[])paramArrayOfbyte.clone(); } public SnmpString(Byte[] paramArrayOfByte) { this.value = null; this.value = new byte[paramArrayOfByte.length]; for (byte b = 0; b < paramArrayOfByte.length; b++) this.value[b] = paramArrayOfByte[b].byteValue();  } public SnmpString(String paramString) { this.value = null; this.value = paramString.getBytes(); } public SnmpString(InetAddress paramInetAddress) { this.value = null;
/*     */     this.value = paramInetAddress.getAddress(); }
/*     */ 
/*     */   
/*     */   public InetAddress inetAddressValue() throws UnknownHostException {
/*     */     return InetAddress.getByAddress(this.value);
/*     */   }
/*     */   
/*     */   public static String BinToChar(String paramString) {
/*     */     char[] arrayOfChar = new char[paramString.length() / 8];
/*     */     int i = arrayOfChar.length;
/*     */     for (byte b = 0; b < i; b++)
/*     */       arrayOfChar[b] = (char)Integer.parseInt(paramString.substring(8 * b, 8 * b + 8), 2); 
/*     */     return new String(arrayOfChar);
/*     */   }
/*     */   
/*     */   public static String HexToChar(String paramString) {
/*     */     char[] arrayOfChar = new char[paramString.length() / 2];
/*     */     int i = arrayOfChar.length;
/*     */     for (byte b = 0; b < i; b++)
/*     */       arrayOfChar[b] = (char)Integer.parseInt(paramString.substring(2 * b, 2 * b + 2), 16); 
/*     */     return new String(arrayOfChar);
/*     */   }
/*     */   
/*     */   public byte[] byteValue() {
/*     */     return (byte[])this.value.clone();
/*     */   }
/*     */   
/*     */   public Byte[] toByte() {
/*     */     Byte[] arrayOfByte = new Byte[this.value.length];
/*     */     for (byte b = 0; b < this.value.length; b++)
/*     */       arrayOfByte[b] = new Byte(this.value[b]); 
/*     */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     return new String(this.value);
/*     */   }
/*     */   
/*     */   public SnmpOid toOid() {
/*     */     long[] arrayOfLong = new long[this.value.length];
/*     */     for (byte b = 0; b < this.value.length; b++)
/*     */       arrayOfLong[b] = (this.value[b] & 0xFF); 
/*     */     return new SnmpOid(arrayOfLong);
/*     */   }
/*     */   
/*     */   public static SnmpOid toOid(long[] paramArrayOflong, int paramInt) throws SnmpStatusException {
/*     */     try {
/*     */       if (paramArrayOflong[paramInt] > 2147483647L)
/*     */         throw new SnmpStatusException(2); 
/*     */       int i = (int)paramArrayOflong[paramInt++];
/*     */       long[] arrayOfLong = new long[i];
/*     */       for (byte b = 0; b < i; b++)
/*     */         arrayOfLong[b] = paramArrayOflong[paramInt + b]; 
/*     */       return new SnmpOid(arrayOfLong);
/*     */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */       throw new SnmpStatusException(2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int nextOid(long[] paramArrayOflong, int paramInt) throws SnmpStatusException {
/*     */     try {
/*     */       if (paramArrayOflong[paramInt] > 2147483647L)
/*     */         throw new SnmpStatusException(2); 
/*     */       int i = (int)paramArrayOflong[paramInt++];
/*     */       paramInt += i;
/*     */       if (paramInt <= paramArrayOflong.length)
/*     */         return paramInt; 
/*     */       throw new SnmpStatusException(2);
/*     */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */       throw new SnmpStatusException(2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void appendToOid(SnmpOid paramSnmpOid1, SnmpOid paramSnmpOid2) {
/*     */     paramSnmpOid2.append(paramSnmpOid1.getLength());
/*     */     paramSnmpOid2.append(paramSnmpOid1);
/*     */   }
/*     */   
/*     */   public final synchronized SnmpValue duplicate() {
/*     */     return (SnmpValue)clone();
/*     */   }
/*     */   
/*     */   public synchronized Object clone() {
/*     */     SnmpString snmpString = null;
/*     */     try {
/*     */       snmpString = (SnmpString)super.clone();
/*     */       snmpString.value = new byte[this.value.length];
/*     */       System.arraycopy(this.value, 0, snmpString.value, 0, this.value.length);
/*     */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */     return snmpString;
/*     */   }
/*     */   
/*     */   public String getTypeName() {
/*     */     return "String";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */