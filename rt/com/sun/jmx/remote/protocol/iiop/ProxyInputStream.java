/*     */ package com.sun.jmx.remote.protocol.iiop;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.Context;
/*     */ import org.omg.CORBA.NO_IMPLEMENT;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Principal;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.BoxedValueHelper;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProxyInputStream
/*     */   extends InputStream
/*     */ {
/*     */   protected final InputStream in;
/*     */   
/*     */   public ProxyInputStream(InputStream paramInputStream) {
/*  42 */     this.in = paramInputStream;
/*     */   }
/*     */   
/*     */   public boolean read_boolean() {
/*  46 */     return this.in.read_boolean();
/*     */   }
/*     */   
/*     */   public char read_char() {
/*  50 */     return this.in.read_char();
/*     */   }
/*     */   
/*     */   public char read_wchar() {
/*  54 */     return this.in.read_wchar();
/*     */   }
/*     */   
/*     */   public byte read_octet() {
/*  58 */     return this.in.read_octet();
/*     */   }
/*     */   
/*     */   public short read_short() {
/*  62 */     return this.in.read_short();
/*     */   }
/*     */   
/*     */   public short read_ushort() {
/*  66 */     return this.in.read_ushort();
/*     */   }
/*     */   
/*     */   public int read_long() {
/*  70 */     return this.in.read_long();
/*     */   }
/*     */   
/*     */   public int read_ulong() {
/*  74 */     return this.in.read_ulong();
/*     */   }
/*     */   
/*     */   public long read_longlong() {
/*  78 */     return this.in.read_longlong();
/*     */   }
/*     */   
/*     */   public long read_ulonglong() {
/*  82 */     return this.in.read_ulonglong();
/*     */   }
/*     */   
/*     */   public float read_float() {
/*  86 */     return this.in.read_float();
/*     */   }
/*     */   
/*     */   public double read_double() {
/*  90 */     return this.in.read_double();
/*     */   }
/*     */   
/*     */   public String read_string() {
/*  94 */     return this.in.read_string();
/*     */   }
/*     */   
/*     */   public String read_wstring() {
/*  98 */     return this.in.read_wstring();
/*     */   }
/*     */   
/*     */   public void read_boolean_array(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2) {
/* 102 */     this.in.read_boolean_array(paramArrayOfboolean, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_char_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 106 */     this.in.read_char_array(paramArrayOfchar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_wchar_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 110 */     this.in.read_wchar_array(paramArrayOfchar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_octet_array(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 114 */     this.in.read_octet_array(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_short_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 118 */     this.in.read_short_array(paramArrayOfshort, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_ushort_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 122 */     this.in.read_ushort_array(paramArrayOfshort, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_long_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 126 */     this.in.read_long_array(paramArrayOfint, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_ulong_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 130 */     this.in.read_ulong_array(paramArrayOfint, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_longlong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 134 */     this.in.read_longlong_array(paramArrayOflong, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_ulonglong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 138 */     this.in.read_ulonglong_array(paramArrayOflong, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_float_array(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 142 */     this.in.read_float_array(paramArrayOffloat, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void read_double_array(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 146 */     this.in.read_double_array(paramArrayOfdouble, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public Object read_Object() {
/* 150 */     return this.in.read_Object();
/*     */   }
/*     */   
/*     */   public TypeCode read_TypeCode() {
/* 154 */     return this.in.read_TypeCode();
/*     */   }
/*     */   
/*     */   public Any read_any() {
/* 158 */     return this.in.read_any();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Principal read_Principal() {
/* 167 */     return this.in.read_Principal();
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 172 */     return this.in.read();
/*     */   }
/*     */ 
/*     */   
/*     */   public BigDecimal read_fixed() {
/* 177 */     return this.in.read_fixed();
/*     */   }
/*     */ 
/*     */   
/*     */   public Context read_Context() {
/* 182 */     return this.in.read_Context();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object read_Object(Class paramClass) {
/* 187 */     return this.in.read_Object(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public ORB orb() {
/* 192 */     return this.in.orb();
/*     */   }
/*     */ 
/*     */   
/*     */   public Serializable read_value() {
/* 197 */     return narrow().read_value();
/*     */   }
/*     */ 
/*     */   
/*     */   public Serializable read_value(Class paramClass) {
/* 202 */     return narrow().read_value(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public Serializable read_value(BoxedValueHelper paramBoxedValueHelper) {
/* 207 */     return narrow().read_value(paramBoxedValueHelper);
/*     */   }
/*     */ 
/*     */   
/*     */   public Serializable read_value(String paramString) {
/* 212 */     return narrow().read_value(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Serializable read_value(Serializable paramSerializable) {
/* 217 */     return narrow().read_value(paramSerializable);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object read_abstract_interface() {
/* 222 */     return narrow().read_abstract_interface();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object read_abstract_interface(Class paramClass) {
/* 227 */     return narrow().read_abstract_interface(paramClass);
/*     */   }
/*     */   
/*     */   protected InputStream narrow() {
/* 231 */     if (this.in instanceof InputStream)
/* 232 */       return (InputStream)this.in; 
/* 233 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */   
/*     */   public InputStream getProxiedInputStream() {
/* 237 */     return this.in;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\remote\protocol\iiop\ProxyInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */