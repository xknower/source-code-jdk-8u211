/*    */ package com.sun.corba.se.impl.dynamicany;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import java.io.Serializable;
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.TCKind;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.DynamicAny.DynAny;
/*    */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*    */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
/*    */ import org.omg.DynamicAny.DynValue;
/*    */ import org.omg.DynamicAny.NameDynAnyPair;
/*    */ import org.omg.DynamicAny.NameValuePair;
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
/*    */ public class DynValueImpl
/*    */   extends DynValueCommonImpl
/*    */   implements DynValue
/*    */ {
/*    */   private DynValueImpl() {
/* 49 */     this(null, (Any)null, false);
/*    */   }
/*    */   
/*    */   protected DynValueImpl(ORB paramORB, Any paramAny, boolean paramBoolean) {
/* 53 */     super(paramORB, paramAny, paramBoolean);
/*    */   }
/*    */   
/*    */   protected DynValueImpl(ORB paramORB, TypeCode paramTypeCode) {
/* 57 */     super(paramORB, paramTypeCode);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\dynamicany\DynValueImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */