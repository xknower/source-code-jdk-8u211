/*     */ package jdk.management.resource.internal;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.util.Objects;
/*     */ import jdk.Exported;
/*     */ import jdk.management.resource.ResourceAccuracy;
/*     */ import jdk.management.resource.ResourceId;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
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
/*     */ @Exported(false)
/*     */ public class ResourceIdImpl
/*     */   implements ResourceId
/*     */ {
/*  28 */   private static final JavaIOFileDescriptorAccess FD_ACCESS = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ResourceAccuracy accuracy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean forceUpdate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceIdImpl of(Object paramObject) {
/*  53 */     return (paramObject == null) ? null : new ResourceIdImpl(paramObject, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceIdImpl of(FileDescriptor paramFileDescriptor) {
/*  64 */     long l = -1L;
/*  65 */     if (paramFileDescriptor != null) {
/*  66 */       l = FD_ACCESS.get(paramFileDescriptor);
/*  67 */       if (l == -1L) {
/*     */         try {
/*  69 */           l = FD_ACCESS.getHandle(paramFileDescriptor);
/*  70 */         } catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  76 */     return (l == -1L) ? null : of(Integer.valueOf((int)l));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceIdImpl of(Object paramObject, ResourceAccuracy paramResourceAccuracy) {
/*  87 */     return (paramObject == null) ? null : new ResourceIdImpl(paramObject, paramResourceAccuracy, false);
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
/*     */   public static ResourceIdImpl of(Object paramObject, ResourceAccuracy paramResourceAccuracy, boolean paramBoolean) {
/*  99 */     return (paramObject == null) ? null : new ResourceIdImpl(paramObject, paramResourceAccuracy, paramBoolean);
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
/*     */   protected ResourceIdImpl(Object paramObject, ResourceAccuracy paramResourceAccuracy, boolean paramBoolean) {
/* 111 */     this.target = paramObject;
/* 112 */     this.accuracy = paramResourceAccuracy;
/* 113 */     this.forceUpdate = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 118 */     return Objects.toString(this.target, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceAccuracy getAccuracy() {
/* 123 */     return this.accuracy;
/*     */   }
/*     */   
/*     */   public boolean isForcedUpdate() {
/* 127 */     return this.forceUpdate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 134 */     StringBuilder stringBuilder = new StringBuilder();
/* 135 */     stringBuilder.append(getName());
/* 136 */     ResourceAccuracy resourceAccuracy = getAccuracy();
/* 137 */     if (resourceAccuracy != null) {
/* 138 */       stringBuilder.append(", accuracy: ");
/* 139 */       stringBuilder.append(resourceAccuracy);
/*     */     } 
/* 141 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\ResourceIdImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */