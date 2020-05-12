/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.Identifiable;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
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
/*    */ public class TaggedProfileTemplateFactoryFinderImpl
/*    */   extends IdentifiableFactoryFinderBase
/*    */ {
/*    */   public TaggedProfileTemplateFactoryFinderImpl(ORB paramORB) {
/* 46 */     super(paramORB);
/*    */   }
/*    */ 
/*    */   
/*    */   public Identifiable handleMissingFactory(int paramInt, InputStream paramInputStream) {
/* 51 */     throw this.wrapper.taggedProfileTemplateFactoryNotFound(new Integer(paramInt));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\ior\TaggedProfileTemplateFactoryFinderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */