/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
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
/*     */ public abstract class Enumerated
/*     */   implements Serializable
/*     */ {
/*     */   protected int value;
/*     */   
/*     */   public Enumerated() throws IllegalArgumentException {
/*  57 */     Enumeration<Integer> enumeration = getIntTable().keys();
/*  58 */     if (enumeration.hasMoreElements()) {
/*  59 */       this.value = ((Integer)enumeration.nextElement()).intValue();
/*     */     } else {
/*     */       
/*  62 */       throw new IllegalArgumentException();
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
/*     */   public Enumerated(int paramInt) throws IllegalArgumentException {
/*  74 */     if (getIntTable().get(new Integer(paramInt)) == null) {
/*  75 */       throw new IllegalArgumentException();
/*     */     }
/*  77 */     this.value = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumerated(Integer paramInteger) throws IllegalArgumentException {
/*  88 */     if (getIntTable().get(paramInteger) == null) {
/*  89 */       throw new IllegalArgumentException();
/*     */     }
/*  91 */     this.value = paramInteger.intValue();
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
/*     */   public Enumerated(String paramString) throws IllegalArgumentException {
/* 103 */     Integer integer = getStringTable().get(paramString);
/* 104 */     if (integer == null) {
/* 105 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 108 */     this.value = integer.intValue();
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
/*     */   public int intValue() {
/* 120 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<Integer> valueIndexes() {
/* 131 */     return getIntTable().keys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> valueStrings() {
/* 142 */     return getStringTable().keys();
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
/*     */   public boolean equals(Object paramObject) {
/* 159 */     return (paramObject != null && 
/* 160 */       getClass() == paramObject.getClass() && this.value == ((Enumerated)paramObject).value);
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
/*     */   public int hashCode() {
/* 172 */     String str = getClass().getName() + String.valueOf(this.value);
/* 173 */     return str.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 184 */     return getIntTable().get(new Integer(this.value));
/*     */   }
/*     */   
/*     */   protected abstract Hashtable<Integer, String> getIntTable();
/*     */   
/*     */   protected abstract Hashtable<String, Integer> getStringTable();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\Enumerated.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */