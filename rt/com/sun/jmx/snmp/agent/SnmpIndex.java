/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpIndex
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8712159739982192146L;
/*     */   private Vector<SnmpOid> oids;
/*     */   private int size;
/*     */   
/*     */   public SnmpIndex(SnmpOid[] paramArrayOfSnmpOid) {
/* 184 */     this.oids = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     this.size = 0; this.size = paramArrayOfSnmpOid.length; for (byte b = 0; b < this.size; b++) this.oids.addElement(paramArrayOfSnmpOid[b]);  } public SnmpIndex(SnmpOid paramSnmpOid) { this.oids = new Vector<>(); this.size = 0;
/*     */     this.oids.addElement(paramSnmpOid);
/*     */     this.size = 1; }
/*     */ 
/*     */   
/*     */   public int getNbComponents() {
/*     */     return this.size;
/*     */   }
/*     */   
/*     */   public Vector<SnmpOid> getComponents() {
/*     */     return this.oids;
/*     */   }
/*     */   
/*     */   public boolean equals(SnmpIndex paramSnmpIndex) {
/*     */     if (this.size != paramSnmpIndex.getNbComponents())
/*     */       return false; 
/*     */     Vector<SnmpOid> vector = paramSnmpIndex.getComponents();
/*     */     for (byte b = 0; b < this.size; b++) {
/*     */       SnmpOid snmpOid1 = this.oids.elementAt(b);
/*     */       SnmpOid snmpOid2 = vector.elementAt(b);
/*     */       if (!snmpOid1.equals(snmpOid2))
/*     */         return false; 
/*     */     } 
/*     */     return true;
/*     */   }
/*     */   
/*     */   public int compareTo(SnmpIndex paramSnmpIndex) {
/*     */     int i = paramSnmpIndex.getNbComponents();
/*     */     Vector<SnmpOid> vector = paramSnmpIndex.getComponents();
/*     */     for (byte b = 0; b < this.size; ) {
/*     */       if (b > i)
/*     */         return 1; 
/*     */       SnmpOid snmpOid1 = this.oids.elementAt(b);
/*     */       SnmpOid snmpOid2 = vector.elementAt(b);
/*     */       int j = snmpOid1.compareTo(snmpOid2);
/*     */       if (j == 0) {
/*     */         b++;
/*     */         continue;
/*     */       } 
/*     */       return j;
/*     */     } 
/*     */     return 0;
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     StringBuilder stringBuilder = new StringBuilder();
/*     */     for (Enumeration<SnmpOid> enumeration = this.oids.elements(); enumeration.hasMoreElements(); ) {
/*     */       SnmpOid snmpOid = enumeration.nextElement();
/*     */       stringBuilder.append("//").append(snmpOid.toString());
/*     */     } 
/*     */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */