/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Hashtable;
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
/*     */ public class EnumRowStatus
/*     */   extends Enumerated
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8966519271130162420L;
/*     */   public static final int unspecified = 0;
/*     */   public static final int active = 1;
/*     */   public static final int notInService = 2;
/*     */   public static final int notReady = 3;
/*     */   public static final int createAndGo = 4;
/*     */   public static final int createAndWait = 5;
/*     */   public static final int destroy = 6;
/*     */   
/*     */   public EnumRowStatus(int paramInt) throws IllegalArgumentException {
/* 142 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRowStatus(Enumerated paramEnumerated) throws IllegalArgumentException {
/* 154 */     this(paramEnumerated.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRowStatus(long paramLong) throws IllegalArgumentException {
/* 166 */     this((int)paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRowStatus(Integer paramInteger) throws IllegalArgumentException {
/* 178 */     super(paramInteger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRowStatus(Long paramLong) throws IllegalArgumentException {
/* 190 */     this(paramLong.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRowStatus() throws IllegalArgumentException {
/* 198 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRowStatus(String paramString) throws IllegalArgumentException {
/* 210 */     super(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRowStatus(SnmpInt paramSnmpInt) throws IllegalArgumentException {
/* 222 */     this(paramSnmpInt.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpInt toSnmpValue() throws IllegalArgumentException {
/* 234 */     if (this.value == 0) {
/* 235 */       throw new IllegalArgumentException("`unspecified' is not a valid SNMP value.");
/*     */     }
/* 237 */     return new SnmpInt(this.value);
/*     */   }
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
/*     */   public static boolean isValidValue(int paramInt) {
/* 255 */     if (paramInt < 0) return false; 
/* 256 */     if (paramInt > 6) return false; 
/* 257 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Hashtable<Integer, String> getIntTable() {
/* 264 */     return getRSIntTable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Hashtable<String, Integer> getStringTable() {
/* 271 */     return getRSStringTable();
/*     */   }
/*     */   
/*     */   static Hashtable<Integer, String> getRSIntTable() {
/* 275 */     return intTable;
/*     */   }
/*     */   
/*     */   static Hashtable<String, Integer> getRSStringTable() {
/* 279 */     return stringTable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 284 */   static final Hashtable<Integer, String> intTable = new Hashtable<>();
/* 285 */   static final Hashtable<String, Integer> stringTable = new Hashtable<>();
/*     */   static {
/* 287 */     intTable.put(new Integer(0), "unspecified");
/* 288 */     intTable.put(new Integer(3), "notReady");
/* 289 */     intTable.put(new Integer(6), "destroy");
/* 290 */     intTable.put(new Integer(2), "notInService");
/* 291 */     intTable.put(new Integer(5), "createAndWait");
/* 292 */     intTable.put(new Integer(1), "active");
/* 293 */     intTable.put(new Integer(4), "createAndGo");
/* 294 */     stringTable.put("unspecified", new Integer(0));
/* 295 */     stringTable.put("notReady", new Integer(3));
/* 296 */     stringTable.put("destroy", new Integer(6));
/* 297 */     stringTable.put("notInService", new Integer(2));
/* 298 */     stringTable.put("createAndWait", new Integer(5));
/* 299 */     stringTable.put("active", new Integer(1));
/* 300 */     stringTable.put("createAndGo", new Integer(4));
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\EnumRowStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */