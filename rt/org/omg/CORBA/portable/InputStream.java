/*     */ package org.omg.CORBA.portable;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigDecimal;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.Context;
/*     */ import org.omg.CORBA.NO_IMPLEMENT;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Principal;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InputStream
/*     */   extends InputStream
/*     */ {
/*     */   public abstract boolean read_boolean();
/*     */   
/*     */   public abstract char read_char();
/*     */   
/*     */   public abstract char read_wchar();
/*     */   
/*     */   public abstract byte read_octet();
/*     */   
/*     */   public abstract short read_short();
/*     */   
/*     */   public abstract short read_ushort();
/*     */   
/*     */   public abstract int read_long();
/*     */   
/*     */   public abstract int read_ulong();
/*     */   
/*     */   public abstract long read_longlong();
/*     */   
/*     */   public abstract long read_ulonglong();
/*     */   
/*     */   public abstract float read_float();
/*     */   
/*     */   public abstract double read_double();
/*     */   
/*     */   public abstract String read_string();
/*     */   
/*     */   public abstract String read_wstring();
/*     */   
/*     */   public abstract void read_boolean_array(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_char_array(char[] paramArrayOfchar, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_wchar_array(char[] paramArrayOfchar, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_octet_array(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_short_array(short[] paramArrayOfshort, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_ushort_array(short[] paramArrayOfshort, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_long_array(int[] paramArrayOfint, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_ulong_array(int[] paramArrayOfint, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_longlong_array(long[] paramArrayOflong, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_ulonglong_array(long[] paramArrayOflong, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_float_array(float[] paramArrayOffloat, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_double_array(double[] paramArrayOfdouble, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract Object read_Object();
/*     */   
/*     */   public abstract TypeCode read_TypeCode();
/*     */   
/*     */   public abstract Any read_any();
/*     */   
/*     */   @Deprecated
/*     */   public Principal read_Principal() {
/* 256 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 265 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigDecimal read_fixed() {
/* 273 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Context read_Context() {
/* 283 */     throw new NO_IMPLEMENT();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object read_Object(Class paramClass) {
/* 306 */     throw new NO_IMPLEMENT();
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
/*     */   public ORB orb() {
/* 318 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\portable\InputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */