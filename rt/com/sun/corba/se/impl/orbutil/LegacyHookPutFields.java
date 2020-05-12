/*     */ package com.sun.corba.se.impl.orbutil;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ class LegacyHookPutFields
/*     */   extends ObjectOutputStream.PutField
/*     */ {
/*  39 */   private Hashtable fields = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String paramString, boolean paramBoolean) {
/*  45 */     this.fields.put(paramString, new Boolean(paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String paramString, char paramChar) {
/*  52 */     this.fields.put(paramString, new Character(paramChar));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String paramString, byte paramByte) {
/*  59 */     this.fields.put(paramString, new Byte(paramByte));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String paramString, short paramShort) {
/*  66 */     this.fields.put(paramString, new Short(paramShort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String paramString, int paramInt) {
/*  73 */     this.fields.put(paramString, new Integer(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String paramString, long paramLong) {
/*  80 */     this.fields.put(paramString, new Long(paramLong));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String paramString, float paramFloat) {
/*  88 */     this.fields.put(paramString, new Float(paramFloat));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String paramString, double paramDouble) {
/*  95 */     this.fields.put(paramString, new Double(paramDouble));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String paramString, Object paramObject) {
/* 102 */     this.fields.put(paramString, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ObjectOutput paramObjectOutput) throws IOException {
/* 109 */     paramObjectOutput.writeObject(this.fields);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\orbutil\LegacyHookPutFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */