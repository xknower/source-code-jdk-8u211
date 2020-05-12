/*    */ package com.sun.corba.se.impl.corba;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.TCKind;
/*    */ import org.omg.CORBA.TypeCode;
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
/*    */ public abstract class TypeCodeImplHelper
/*    */ {
/* 36 */   private static String _id = "IDL:omg.org/CORBA/TypeCode:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, TypeCode paramTypeCode) {
/* 40 */     OutputStream outputStream = paramAny.create_output_stream();
/* 41 */     paramAny.type(type());
/* 42 */     write(outputStream, paramTypeCode);
/* 43 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static TypeCode extract(Any paramAny) {
/* 48 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 51 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 54 */     if (__typeCode == null)
/*    */     {
/* 56 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
/*    */     }
/* 58 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 63 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static TypeCode read(InputStream paramInputStream) {
/* 68 */     return paramInputStream.read_TypeCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, TypeCode paramTypeCode) {
/* 73 */     paramOutputStream.write_TypeCode(paramTypeCode);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, TypeCodeImpl paramTypeCodeImpl) {
/* 78 */     paramOutputStream.write_TypeCode(paramTypeCodeImpl);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\corba\TypeCodeImplHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */