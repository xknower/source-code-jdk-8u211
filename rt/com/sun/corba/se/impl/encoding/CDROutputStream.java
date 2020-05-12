/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.Context;
/*     */ import org.omg.CORBA.ContextList;
/*     */ import org.omg.CORBA.DataOutputStream;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Principal;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.BoxedValueHelper;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.ValueOutputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CDROutputStream
/*     */   extends OutputStream
/*     */   implements MarshalOutputStream, DataOutputStream, ValueOutputStream
/*     */ {
/*     */   private CDROutputStreamBase impl;
/*     */   protected ORB orb;
/*     */   protected ORBUtilSystemException wrapper;
/*     */   protected CorbaMessageMediator corbaMessageMediator;
/*     */   
/*     */   private static class OutputStreamFactory
/*     */   {
/*     */     public static CDROutputStreamBase newOutputStream(ORB param1ORB, GIOPVersion param1GIOPVersion, byte param1Byte) {
/*  69 */       switch (param1GIOPVersion.intValue()) {
/*     */         case 256:
/*  71 */           return new CDROutputStream_1_0();
/*     */         case 257:
/*  73 */           return new CDROutputStream_1_1();
/*     */         case 258:
/*  75 */           if (param1Byte != 0) {
/*  76 */             return new IDLJavaSerializationOutputStream(param1Byte);
/*     */           }
/*     */           
/*  79 */           return new CDROutputStream_1_2();
/*     */       } 
/*  81 */       ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get(param1ORB, "rpc.encoding");
/*     */ 
/*     */ 
/*     */       
/*  85 */       throw oRBUtilSystemException.unsupportedGiopVersion(param1GIOPVersion);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CDROutputStream(ORB paramORB, GIOPVersion paramGIOPVersion, byte paramByte1, boolean paramBoolean1, BufferManagerWrite paramBufferManagerWrite, byte paramByte2, boolean paramBoolean2) {
/* 107 */     this.impl = OutputStreamFactory.newOutputStream(paramORB, paramGIOPVersion, paramByte1);
/*     */     
/* 109 */     this.impl.init(paramORB, paramBoolean1, paramBufferManagerWrite, paramByte2, paramBoolean2);
/*     */ 
/*     */     
/* 112 */     this.impl.setParent(this);
/* 113 */     this.orb = paramORB;
/* 114 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.encoding");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CDROutputStream(ORB paramORB, GIOPVersion paramGIOPVersion, byte paramByte1, boolean paramBoolean, BufferManagerWrite paramBufferManagerWrite, byte paramByte2) {
/* 125 */     this(paramORB, paramGIOPVersion, paramByte1, paramBoolean, paramBufferManagerWrite, paramByte2, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract InputStream create_input_stream();
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_boolean(boolean paramBoolean) {
/* 135 */     this.impl.write_boolean(paramBoolean);
/*     */   }
/*     */   public final void write_char(char paramChar) {
/* 138 */     this.impl.write_char(paramChar);
/*     */   }
/*     */   public final void write_wchar(char paramChar) {
/* 141 */     this.impl.write_wchar(paramChar);
/*     */   }
/*     */   public final void write_octet(byte paramByte) {
/* 144 */     this.impl.write_octet(paramByte);
/*     */   }
/*     */   public final void write_short(short paramShort) {
/* 147 */     this.impl.write_short(paramShort);
/*     */   }
/*     */   public final void write_ushort(short paramShort) {
/* 150 */     this.impl.write_ushort(paramShort);
/*     */   }
/*     */   public final void write_long(int paramInt) {
/* 153 */     this.impl.write_long(paramInt);
/*     */   }
/*     */   public final void write_ulong(int paramInt) {
/* 156 */     this.impl.write_ulong(paramInt);
/*     */   }
/*     */   public final void write_longlong(long paramLong) {
/* 159 */     this.impl.write_longlong(paramLong);
/*     */   }
/*     */   public final void write_ulonglong(long paramLong) {
/* 162 */     this.impl.write_ulonglong(paramLong);
/*     */   }
/*     */   public final void write_float(float paramFloat) {
/* 165 */     this.impl.write_float(paramFloat);
/*     */   }
/*     */   public final void write_double(double paramDouble) {
/* 168 */     this.impl.write_double(paramDouble);
/*     */   }
/*     */   public final void write_string(String paramString) {
/* 171 */     this.impl.write_string(paramString);
/*     */   }
/*     */   public final void write_wstring(String paramString) {
/* 174 */     this.impl.write_wstring(paramString);
/*     */   }
/*     */   
/*     */   public final void write_boolean_array(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2) {
/* 178 */     this.impl.write_boolean_array(paramArrayOfboolean, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_char_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 181 */     this.impl.write_char_array(paramArrayOfchar, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_wchar_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 184 */     this.impl.write_wchar_array(paramArrayOfchar, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_octet_array(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 187 */     this.impl.write_octet_array(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_short_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 190 */     this.impl.write_short_array(paramArrayOfshort, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_ushort_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 193 */     this.impl.write_ushort_array(paramArrayOfshort, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_long_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 196 */     this.impl.write_long_array(paramArrayOfint, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_ulong_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 199 */     this.impl.write_ulong_array(paramArrayOfint, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_longlong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 202 */     this.impl.write_longlong_array(paramArrayOflong, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_ulonglong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 205 */     this.impl.write_ulonglong_array(paramArrayOflong, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_float_array(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 208 */     this.impl.write_float_array(paramArrayOffloat, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_double_array(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 211 */     this.impl.write_double_array(paramArrayOfdouble, paramInt1, paramInt2);
/*     */   }
/*     */   public final void write_Object(Object paramObject) {
/* 214 */     this.impl.write_Object(paramObject);
/*     */   }
/*     */   public final void write_TypeCode(TypeCode paramTypeCode) {
/* 217 */     this.impl.write_TypeCode(paramTypeCode);
/*     */   }
/*     */   public final void write_any(Any paramAny) {
/* 220 */     this.impl.write_any(paramAny);
/*     */   }
/*     */   
/*     */   public final void write_Principal(Principal paramPrincipal) {
/* 224 */     this.impl.write_Principal(paramPrincipal);
/*     */   }
/*     */   
/*     */   public final void write(int paramInt) throws IOException {
/* 228 */     this.impl.write(paramInt);
/*     */   }
/*     */   
/*     */   public final void write_fixed(BigDecimal paramBigDecimal) {
/* 232 */     this.impl.write_fixed(paramBigDecimal);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_Context(Context paramContext, ContextList paramContextList) {
/* 237 */     this.impl.write_Context(paramContext, paramContextList);
/*     */   }
/*     */   
/*     */   public final ORB orb() {
/* 241 */     return this.impl.orb();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_value(Serializable paramSerializable) {
/* 246 */     this.impl.write_value(paramSerializable);
/*     */   }
/*     */   
/*     */   public final void write_value(Serializable paramSerializable, Class paramClass) {
/* 250 */     this.impl.write_value(paramSerializable, paramClass);
/*     */   }
/*     */   
/*     */   public final void write_value(Serializable paramSerializable, String paramString) {
/* 254 */     this.impl.write_value(paramSerializable, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_value(Serializable paramSerializable, BoxedValueHelper paramBoxedValueHelper) {
/* 259 */     this.impl.write_value(paramSerializable, paramBoxedValueHelper);
/*     */   }
/*     */   
/*     */   public final void write_abstract_interface(Object paramObject) {
/* 263 */     this.impl.write_abstract_interface(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write(byte[] paramArrayOfbyte) throws IOException {
/* 268 */     this.impl.write(paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   public final void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 272 */     this.impl.write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public final void flush() throws IOException {
/* 276 */     this.impl.flush();
/*     */   }
/*     */   
/*     */   public final void close() throws IOException {
/* 280 */     this.impl.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void start_block() {
/* 285 */     this.impl.start_block();
/*     */   }
/*     */   
/*     */   public final void end_block() {
/* 289 */     this.impl.end_block();
/*     */   }
/*     */   
/*     */   public final void putEndian() {
/* 293 */     this.impl.putEndian();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream paramOutputStream) throws IOException {
/* 299 */     this.impl.writeTo(paramOutputStream);
/*     */   }
/*     */   
/*     */   public final byte[] toByteArray() {
/* 303 */     return this.impl.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_Abstract(Object paramObject) {
/* 308 */     this.impl.write_Abstract(paramObject);
/*     */   }
/*     */   
/*     */   public final void write_Value(Serializable paramSerializable) {
/* 312 */     this.impl.write_Value(paramSerializable);
/*     */   }
/*     */   
/*     */   public final void write_any_array(Any[] paramArrayOfAny, int paramInt1, int paramInt2) {
/* 316 */     this.impl.write_any_array(paramArrayOfAny, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMessageMediator(MessageMediator paramMessageMediator) {
/* 321 */     this.corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageMediator getMessageMediator() {
/* 326 */     return this.corbaMessageMediator;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String[] _truncatable_ids() {
/* 331 */     return this.impl._truncatable_ids();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int getSize() {
/* 336 */     return this.impl.getSize();
/*     */   }
/*     */   
/*     */   protected final int getIndex() {
/* 340 */     return this.impl.getIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getRealIndex(int paramInt) {
/* 345 */     return paramInt;
/*     */   }
/*     */   
/*     */   protected final void setIndex(int paramInt) {
/* 349 */     this.impl.setIndex(paramInt);
/*     */   }
/*     */   
/*     */   protected final ByteBuffer getByteBuffer() {
/* 353 */     return this.impl.getByteBuffer();
/*     */   }
/*     */   
/*     */   protected final void setByteBuffer(ByteBuffer paramByteBuffer) {
/* 357 */     this.impl.setByteBuffer(paramByteBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean isSharing(ByteBuffer paramByteBuffer) {
/* 364 */     return (getByteBuffer() == paramByteBuffer);
/*     */   }
/*     */   
/*     */   public final boolean isLittleEndian() {
/* 368 */     return this.impl.isLittleEndian();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufferWithInfo getByteBufferWithInfo() {
/* 374 */     return this.impl.getByteBufferWithInfo();
/*     */   }
/*     */   
/*     */   protected void setByteBufferWithInfo(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 378 */     this.impl.setByteBufferWithInfo(paramByteBufferWithInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   public final BufferManagerWrite getBufferManager() {
/* 383 */     return this.impl.getBufferManager();
/*     */   }
/*     */   
/*     */   public final void write_fixed(BigDecimal paramBigDecimal, short paramShort1, short paramShort2) {
/* 387 */     this.impl.write_fixed(paramBigDecimal, paramShort1, paramShort2);
/*     */   }
/*     */   
/*     */   public final void writeOctetSequenceTo(OutputStream paramOutputStream) {
/* 391 */     this.impl.writeOctetSequenceTo(paramOutputStream);
/*     */   }
/*     */   
/*     */   public final GIOPVersion getGIOPVersion() {
/* 395 */     return this.impl.getGIOPVersion();
/*     */   }
/*     */   
/*     */   public final void writeIndirection(int paramInt1, int paramInt2) {
/* 399 */     this.impl.writeIndirection(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected CodeSetConversion.CTBConverter createCharCTBConverter() {
/* 405 */     return CodeSetConversion.impl().getCTBConverter(OSFCodeSetRegistry.ISO_8859_1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract CodeSetConversion.CTBConverter createWCharCTBConverter();
/*     */ 
/*     */   
/*     */   protected final void freeInternalCaches() {
/* 413 */     this.impl.freeInternalCaches();
/*     */   }
/*     */   
/*     */   void printBuffer() {
/* 417 */     this.impl.printBuffer();
/*     */   }
/*     */   
/*     */   public void alignOnBoundary(int paramInt) {
/* 421 */     this.impl.alignOnBoundary(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeaderPadding(boolean paramBoolean) {
/* 426 */     this.impl.setHeaderPadding(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start_value(String paramString) {
/* 432 */     this.impl.start_value(paramString);
/*     */   }
/*     */   
/*     */   public void end_value() {
/* 436 */     this.impl.end_value();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\encoding\CDROutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */