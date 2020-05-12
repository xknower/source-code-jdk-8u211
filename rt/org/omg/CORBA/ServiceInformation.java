/*    */ package org.omg.CORBA;
/*    */ 
/*    */ import org.omg.CORBA.portable.IDLEntity;
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
/*    */ public final class ServiceInformation
/*    */   implements IDLEntity
/*    */ {
/*    */   public int[] service_options;
/*    */   public ServiceDetail[] service_details;
/*    */   
/*    */   public ServiceInformation() {}
/*    */   
/*    */   public ServiceInformation(int[] paramArrayOfint, ServiceDetail[] paramArrayOfServiceDetail) {
/* 58 */     this.service_options = paramArrayOfint;
/* 59 */     this.service_details = paramArrayOfServiceDetail;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\ServiceInformation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */