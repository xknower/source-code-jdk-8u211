/*    */ package com.sun.corba.se.spi.activation.RepositoryPackage;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StringSeqHelper
/*    */ {
/* 13 */   private static String _id = "IDL:activation/Repository/StringSeq:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, String[] paramArrayOfString) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramArrayOfString);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 31 */     if (__typeCode == null) {
/*    */       
/* 33 */       __typeCode = ORB.init().create_string_tc(0);
/* 34 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 35 */       __typeCode = ORB.init().create_alias_tc(id(), "StringSeq", __typeCode);
/*    */     } 
/* 37 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 42 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] read(InputStream paramInputStream) {
/* 47 */     String[] arrayOfString = null;
/* 48 */     int i = paramInputStream.read_long();
/* 49 */     arrayOfString = new String[i];
/* 50 */     for (byte b = 0; b < arrayOfString.length; b++)
/* 51 */       arrayOfString[b] = paramInputStream.read_string(); 
/* 52 */     return arrayOfString;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, String[] paramArrayOfString) {
/* 57 */     paramOutputStream.write_long(paramArrayOfString.length);
/* 58 */     for (byte b = 0; b < paramArrayOfString.length; b++)
/* 59 */       paramOutputStream.write_string(paramArrayOfString[b]); 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\RepositoryPackage\StringSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */