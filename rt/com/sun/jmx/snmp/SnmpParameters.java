/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpParameters
/*     */   extends SnmpParams
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1822462497931733790L;
/*     */   static final String defaultRdCommunity = "public";
/*     */   
/*     */   public SnmpParameters() {
/*  44 */     this._readCommunity = "public";
/*  45 */     this._informCommunity = "public";
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
/*     */   public SnmpParameters(String paramString1, String paramString2) {
/*  57 */     this._readCommunity = paramString1;
/*  58 */     this._writeCommunity = paramString2;
/*  59 */     this._informCommunity = "public";
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
/*     */   public SnmpParameters(String paramString1, String paramString2, String paramString3) {
/*  71 */     this._readCommunity = paramString1;
/*  72 */     this._writeCommunity = paramString2;
/*  73 */     this._informCommunity = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRdCommunity() {
/*  81 */     return this._readCommunity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setRdCommunity(String paramString) {
/*  89 */     if (paramString == null) {
/*  90 */       this._readCommunity = "public";
/*     */     } else {
/*  92 */       this._readCommunity = paramString;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWrCommunity() {
/* 100 */     return this._writeCommunity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWrCommunity(String paramString) {
/* 108 */     this._writeCommunity = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInformCommunity() {
/* 116 */     return this._informCommunity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInformCommunity(String paramString) {
/* 124 */     if (paramString == null) {
/* 125 */       this._informCommunity = "public";
/*     */     } else {
/* 127 */       this._informCommunity = paramString;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean allowSnmpSets() {
/* 135 */     return (this._writeCommunity != null);
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
/*     */   public synchronized boolean equals(Object paramObject) {
/* 147 */     if (!(paramObject instanceof SnmpParameters)) {
/* 148 */       return false;
/*     */     }
/* 150 */     if (this == paramObject)
/* 151 */       return true; 
/* 152 */     SnmpParameters snmpParameters = (SnmpParameters)paramObject;
/* 153 */     if (this._protocolVersion == snmpParameters._protocolVersion && 
/* 154 */       this._readCommunity.equals(snmpParameters._readCommunity))
/* 155 */       return true; 
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int hashCode() {
/* 161 */     return this._protocolVersion * 31 ^ Objects.hashCode(this._readCommunity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Object clone() {
/* 169 */     SnmpParameters snmpParameters = null;
/*     */     try {
/* 171 */       snmpParameters = (SnmpParameters)super.clone();
/*     */       
/* 173 */       snmpParameters._readCommunity = this._readCommunity;
/* 174 */       snmpParameters._writeCommunity = this._writeCommunity;
/* 175 */       snmpParameters._informCommunity = this._informCommunity;
/* 176 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 177 */       throw new InternalError();
/*     */     } 
/* 179 */     return snmpParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encodeAuthentication(int paramInt) throws SnmpStatusException {
/*     */     try {
/* 191 */       if (paramInt == 163)
/* 192 */         return this._writeCommunity.getBytes("8859_1"); 
/* 193 */       if (paramInt == 166) {
/* 194 */         return this._informCommunity.getBytes("8859_1");
/*     */       }
/* 196 */       return this._readCommunity.getBytes("8859_1");
/* 197 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 198 */       throw new SnmpStatusException(unsupportedEncodingException.getMessage());
/*     */     } 
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
/* 213 */   private int _protocolVersion = 0;
/*     */   private String _readCommunity;
/*     */   private String _writeCommunity;
/*     */   private String _informCommunity;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */