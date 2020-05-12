/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Timestamp
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -242456119149401823L;
/*     */   private long sysUpTime;
/*     */   private long crtime;
/*  42 */   private SnmpTimeticks uptimeCache = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp() {
/*  53 */     this.crtime = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp(long paramLong1, long paramLong2) {
/*  63 */     this.sysUpTime = paramLong1;
/*  64 */     this.crtime = paramLong2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp(long paramLong) {
/*  73 */     this.sysUpTime = paramLong;
/*  74 */     this.crtime = System.currentTimeMillis();
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
/*     */   public final synchronized SnmpTimeticks getTimeTicks() {
/*  86 */     if (this.uptimeCache == null)
/*  87 */       this.uptimeCache = new SnmpTimeticks((int)this.sysUpTime); 
/*  88 */     return this.uptimeCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getSysUpTime() {
/*  97 */     return this.sysUpTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized Date getDate() {
/* 105 */     return new Date(this.crtime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getDateTime() {
/* 113 */     return this.crtime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 121 */     StringBuffer stringBuffer = new StringBuffer();
/* 122 */     stringBuffer.append("{SysUpTime = " + SnmpTimeticks.printTimeTicks(this.sysUpTime));
/* 123 */     stringBuffer.append("} {Timestamp = " + getDate().toString() + "}");
/* 124 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\Timestamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */