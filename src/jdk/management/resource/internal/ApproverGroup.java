/*     */ package jdk.management.resource.internal;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import jdk.management.resource.ResourceContext;
/*     */ import jdk.management.resource.ResourceId;
/*     */ import jdk.management.resource.ResourceRequest;
/*     */ import jdk.management.resource.ResourceRequestDeniedException;
/*     */ import jdk.management.resource.ResourceType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ApproverGroup
/*     */ {
/*  31 */   private static final ConcurrentHashMap<ResourceType, ApproverGroup> approverGroups = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final WeakKeyConcurrentHashMap<Object, ResourceContext> approvers;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ResourceType type;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isBoundToContext;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final ResourceRequest fallback;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  57 */     fallback = ((paramLong, paramResourceId) -> paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ApproverGroup getGroup(ResourceType paramResourceType) {
/*  66 */     return approverGroups.get(paramResourceType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ApproverGroup create(ResourceType paramResourceType, boolean paramBoolean) {
/*  77 */     return approverGroups.computeIfAbsent(paramResourceType, paramResourceType -> new ApproverGroup(paramResourceType, paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ApproverGroup(ResourceType paramResourceType, boolean paramBoolean) {
/*  89 */     this.type = paramResourceType;
/*  90 */     this.isBoundToContext = paramBoolean;
/*  91 */     this.approvers = new WeakKeyConcurrentHashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ResourceType getId() {
/* 100 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ResourceRequest getApprover(Object paramObject) {
/*     */     ResourceContext resourceContext;
/* 120 */     if (this.isBoundToContext) {
/* 121 */       if (paramObject != null) {
/* 122 */         resourceContext = this.approvers.computeIfAbsent(paramObject, paramObject -> SimpleResourceContext.getThreadContext(Thread.currentThread()));
/*     */       } else {
/* 124 */         throw new ResourceRequestDeniedException("null resource instance for ResourceType: " + this.type);
/*     */       } 
/*     */     } else {
/* 127 */       resourceContext = SimpleResourceContext.getThreadContext(Thread.currentThread());
/*     */     } 
/* 129 */     ResourceRequest resourceRequest = resourceContext.getResourceRequest(this.type);
/* 130 */     return (resourceRequest != null) ? resourceRequest : fallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void purgeResourceContext(ResourceContext paramResourceContext) {
/* 141 */     this.approvers.purgeValue(paramResourceContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public static final ApproverGroup FILE_OPEN_GROUP = create(ResourceType.FILE_OPEN, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public static final ApproverGroup FILE_READ_GROUP = create(ResourceType.FILE_READ, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static final ApproverGroup FILE_WRITE_GROUP = create(ResourceType.FILE_WRITE, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public static final ApproverGroup STDERR_WRITE_GROUP = create(ResourceType.STDERR_WRITE, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public static final ApproverGroup STDIN_READ_GROUP = create(ResourceType.STDIN_READ, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public static final ApproverGroup STDOUT_WRITE_GROUP = create(ResourceType.STDOUT_WRITE, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   public static final ApproverGroup SOCKET_OPEN_GROUP = create(ResourceType.SOCKET_OPEN, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   public static final ApproverGroup SOCKET_READ_GROUP = create(ResourceType.SOCKET_READ, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   public static final ApproverGroup SOCKET_WRITE_GROUP = create(ResourceType.SOCKET_WRITE, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public static final ApproverGroup DATAGRAM_OPEN_GROUP = create(ResourceType.DATAGRAM_OPEN, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 197 */   public static final ApproverGroup DATAGRAM_RECEIVED_GROUP = create(ResourceType.DATAGRAM_RECEIVED, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public static final ApproverGroup DATAGRAM_SENT_GROUP = create(ResourceType.DATAGRAM_SENT, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   public static final ApproverGroup DATAGRAM_READ_GROUP = create(ResourceType.DATAGRAM_READ, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   public static final ApproverGroup DATAGRAM_WRITE_GROUP = create(ResourceType.DATAGRAM_WRITE, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   public static final ApproverGroup THREAD_CREATED_GROUP = create(ResourceType.THREAD_CREATED, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   public static final ApproverGroup THREAD_CPU_GROUP = create(ResourceType.THREAD_CPU, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   public static final ApproverGroup FILEDESCRIPTOR_OPEN_GROUP = create(ResourceType.FILEDESCRIPTOR_OPEN, true);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\ApproverGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */