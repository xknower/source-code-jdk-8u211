/*    */ package org.omg.CORBA;
/*    */ 
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class LongLongSeqHelper
/*    */ {
/* 52 */   private static String _id = "IDL:omg.org/CORBA/LongLongSeq:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, long[] paramArrayOflong) {
/* 56 */     OutputStream outputStream = paramAny.create_output_stream();
/* 57 */     paramAny.type(type());
/* 58 */     write(outputStream, paramArrayOflong);
/* 59 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static long[] extract(Any paramAny) {
/* 64 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 67 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 70 */     if (__typeCode == null) {
/*    */       
/* 72 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_longlong);
/* 73 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 74 */       __typeCode = ORB.init().create_alias_tc(id(), "LongLongSeq", __typeCode);
/*    */     } 
/* 76 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 81 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static long[] read(InputStream paramInputStream) {
/* 86 */     long[] arrayOfLong = null;
/* 87 */     int i = paramInputStream.read_long();
/* 88 */     arrayOfLong = new long[i];
/* 89 */     paramInputStream.read_longlong_array(arrayOfLong, 0, i);
/* 90 */     return arrayOfLong;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, long[] paramArrayOflong) {
/* 95 */     paramOutputStream.write_long(paramArrayOflong.length);
/* 96 */     paramOutputStream.write_longlong_array(paramArrayOflong, 0, paramArrayOflong.length);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\LongLongSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */