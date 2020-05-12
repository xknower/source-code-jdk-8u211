/*    */ package org.omg.CORBA;
/*    */ 
/*    */ import org.omg.CORBA.portable.Delegate;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.ObjectImpl;
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
/*    */ public abstract class PolicyHelper
/*    */ {
/* 40 */   private static String _id = "IDL:omg.org/CORBA/Policy:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, Policy paramPolicy) {
/* 44 */     OutputStream outputStream = paramAny.create_output_stream();
/* 45 */     paramAny.type(type());
/* 46 */     write(outputStream, paramPolicy);
/* 47 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static Policy extract(Any paramAny) {
/* 52 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 55 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 58 */     if (__typeCode == null)
/*    */     {
/* 60 */       __typeCode = ORB.init().create_interface_tc(id(), "Policy");
/*    */     }
/* 62 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 67 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Policy read(InputStream paramInputStream) {
/* 72 */     return narrow(paramInputStream.read_Object(_PolicyStub.class));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, Policy paramPolicy) {
/* 77 */     paramOutputStream.write_Object(paramPolicy);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Policy narrow(Object paramObject) {
/* 82 */     if (paramObject == null)
/* 83 */       return null; 
/* 84 */     if (paramObject instanceof Policy)
/* 85 */       return (Policy)paramObject; 
/* 86 */     if (!paramObject._is_a(id())) {
/* 87 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 90 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 91 */     return new _PolicyStub(delegate);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\PolicyHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */