/*    */ package jdk.management.resource.internal;
/*    */ 
/*    */ import jdk.management.resource.ResourceRequest;
/*    */ import jdk.management.resource.ResourceType;
/*    */ import jdk.management.resource.SimpleMeter;
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
/*    */ public class UnassignedContext
/*    */   extends SimpleResourceContext
/*    */ {
/* 27 */   private static final UnassignedContext unassignedContext = new UnassignedContext("Unassigned");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   private static final UnassignedContext systemContext = new UnassignedContext("System", 0);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private UnassignedContext(String paramString) {
/* 38 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private UnassignedContext(String paramString, int paramInt) {
/* 45 */     super(paramString, paramInt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static UnassignedContext getSystemContext() {
/* 53 */     return systemContext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static UnassignedContext getUnassignedContext() {
/* 62 */     return unassignedContext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceRequest getResourceRequest(ResourceType paramResourceType) {
/* 81 */     ResourceRequest resourceRequest = super.getResourceRequest(paramResourceType);
/* 82 */     if (resourceRequest == null) {
/*    */       try {
/* 84 */         addResourceMeter(SimpleMeter.create(paramResourceType));
/* 85 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*    */ 
/*    */       
/* 88 */       resourceRequest = super.getResourceRequest(paramResourceType);
/*    */     } 
/* 90 */     return resourceRequest;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\UnassignedContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */