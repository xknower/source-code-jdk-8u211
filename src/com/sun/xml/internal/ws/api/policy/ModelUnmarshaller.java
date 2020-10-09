/*    */ package com.sun.xml.internal.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.PolicySourceModel;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.XmlPolicyModelUnmarshaller;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
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
/*    */ public class ModelUnmarshaller
/*    */   extends XmlPolicyModelUnmarshaller
/*    */ {
/* 39 */   private static final ModelUnmarshaller INSTANCE = new ModelUnmarshaller();
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
/*    */   public static ModelUnmarshaller getUnmarshaller() {
/* 54 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected PolicySourceModel createSourceModel(NamespaceVersion nsVersion, String id, String name) {
/* 59 */     return SourceModel.createSourceModel(nsVersion, id, name);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\policy\ModelUnmarshaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */