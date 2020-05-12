/*    */ package org.omg.PortableServer;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.BAD_PARAM;
/*    */ import org.omg.CORBA.MARSHAL;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.Object;
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
/*    */ public abstract class POAHelper
/*    */ {
/* 48 */   private static String _id = "IDL:omg.org/PortableServer/POA:2.3";
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, POA paramPOA) {
/* 53 */     OutputStream outputStream = paramAny.create_output_stream();
/* 54 */     paramAny.type(type());
/* 55 */     write(outputStream, paramPOA);
/* 56 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static POA extract(Any paramAny) {
/* 61 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 64 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 67 */     if (__typeCode == null)
/*    */     {
/* 69 */       __typeCode = ORB.init().create_interface_tc(id(), "POA");
/*    */     }
/* 71 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 76 */     return _id;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static POA read(InputStream paramInputStream) {
/* 82 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, POA paramPOA) {
/* 88 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static POA narrow(Object paramObject) {
/* 93 */     if (paramObject == null)
/* 94 */       return null; 
/* 95 */     if (paramObject instanceof POA)
/* 96 */       return (POA)paramObject; 
/* 97 */     if (!paramObject._is_a(id()))
/* 98 */       throw new BAD_PARAM(); 
/* 99 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableServer\POAHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */