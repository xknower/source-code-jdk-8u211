/*     */ package jdk.management.resource;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
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
/*     */ public class ResourceType
/*     */ {
/*  29 */   private static final WeakHashMap<String, ResourceType> types = new WeakHashMap<>(32);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   public static final ResourceType FILE_OPEN = ofBuiltin("file.open");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   public static final ResourceType FILE_READ = ofBuiltin("file.read");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   public static final ResourceType FILE_WRITE = ofBuiltin("file.write");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   public static final ResourceType STDERR_WRITE = ofBuiltin("stderr.write");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static final ResourceType STDIN_READ = ofBuiltin("stdin.read");
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final ResourceType STDOUT_WRITE = ofBuiltin("stdout.write");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final ResourceType SOCKET_OPEN = ofBuiltin("socket.open");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final ResourceType SOCKET_READ = ofBuiltin("socket.read");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final ResourceType SOCKET_WRITE = ofBuiltin("socket.write");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public static final ResourceType DATAGRAM_OPEN = ofBuiltin("datagram.open");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final ResourceType DATAGRAM_RECEIVED = ofBuiltin("datagram.received");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final ResourceType DATAGRAM_SENT = ofBuiltin("datagram.sent");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static final ResourceType DATAGRAM_READ = ofBuiltin("datagram.read");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public static final ResourceType DATAGRAM_WRITE = ofBuiltin("datagram.write");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static final ResourceType THREAD_CREATED = ofBuiltin("thread.created");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public static final ResourceType THREAD_CPU = ofBuiltin("thread.cpu");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public static final ResourceType HEAP_RETAINED = ofBuiltin("heap.retained");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public static final ResourceType HEAP_ALLOCATED = ofBuiltin("heap.allocated");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public static final ResourceType FILEDESCRIPTOR_OPEN = ofBuiltin("filedescriptor.open");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean builtin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceType of(String paramString) {
/* 144 */     synchronized (types) {
/* 145 */       return types.computeIfAbsent(paramString, paramString -> new ResourceType(paramString, false));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ResourceType ofBuiltin(String paramString) {
/* 157 */     synchronized (types) {
/* 158 */       return types.computeIfAbsent(paramString, paramString -> new ResourceType(paramString, true));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBuiltin() {
/* 167 */     return this.builtin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Set<ResourceType> builtinTypes() {
/* 175 */     synchronized (types) {
/* 176 */       HashSet<ResourceType> hashSet = new HashSet(types.values());
/* 177 */       hashSet.removeIf(paramResourceType -> !paramResourceType.isBuiltin());
/* 178 */       return hashSet;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceType(String paramString, boolean paramBoolean) {
/* 188 */     this.name = Objects.<String>requireNonNull(paramString, "name");
/* 189 */     this.builtin = paramBoolean;
/* 190 */     if (paramString.length() == 0) {
/* 191 */       throw new IllegalArgumentException("name must not be empty");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 202 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 207 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 217 */     int i = 5;
/* 218 */     i = 17 * i + Objects.hashCode(this.name);
/* 219 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 230 */     if (paramObject == null) {
/* 231 */       return false;
/*     */     }
/* 233 */     if (getClass() != paramObject.getClass()) {
/* 234 */       return false;
/*     */     }
/* 236 */     ResourceType resourceType = (ResourceType)paramObject;
/* 237 */     return Objects.equals(this.name, resourceType.name);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\ResourceType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */