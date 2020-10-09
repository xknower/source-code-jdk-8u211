/*     */ package jdk.management.resource.internal;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WeakKeyConcurrentHashMap<K, V>
/*     */ {
/*  41 */   private final ConcurrentHashMap<WeakKey<K>, V> hashmap = new ConcurrentHashMap<>();
/*  42 */   private final ReferenceQueue<K> lastQueue = new ReferenceQueue<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  50 */     purgeStaleKeys();
/*  51 */     return this.hashmap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(K paramK) {
/*  61 */     Objects.requireNonNull(paramK, "key");
/*  62 */     purgeStaleKeys();
/*  63 */     WeakKey<K> weakKey = new WeakKey<>(paramK, null);
/*  64 */     return this.hashmap.get(weakKey);
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
/*     */   private boolean containsKey(K paramK) {
/*  76 */     Objects.requireNonNull(paramK, "key");
/*  77 */     WeakKey<K> weakKey = new WeakKey<>(paramK, null);
/*  78 */     return this.hashmap.containsKey(weakKey);
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
/*     */   public V put(K paramK, V paramV) {
/*  95 */     Objects.requireNonNull(paramK, "key");
/*  96 */     purgeStaleKeys();
/*  97 */     WeakKey<K> weakKey = new WeakKey<>(paramK, this.lastQueue);
/*  98 */     return this.hashmap.put(weakKey, paramV);
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
/*     */   public V remove(K paramK) {
/* 111 */     Objects.requireNonNull(paramK, "key");
/* 112 */     purgeStaleKeys();
/* 113 */     WeakKey<K> weakKey = new WeakKey<>(paramK, null);
/* 114 */     return this.hashmap.remove(weakKey);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V computeIfAbsent(K paramK, Function<? super K, ? extends V> paramFunction) {
/* 140 */     Objects.requireNonNull(paramK, "key");
/* 141 */     Objects.requireNonNull(paramFunction, "mappingFunction");
/* 142 */     purgeStaleKeys();
/* 143 */     WeakKey<K> weakKey = new WeakKey<>(paramK, this.lastQueue);
/* 144 */     return this.hashmap.computeIfAbsent(weakKey, paramWeakKey -> paramFunction.apply(paramObject));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stream<K> keysForValue(V paramV) {
/* 153 */     return this.hashmap.entrySet().stream()
/* 154 */       .filter(paramEntry -> (paramEntry.getValue() == paramObject))
/* 155 */       .map(paramEntry -> ((WeakKey)paramEntry.getKey()).get())
/* 156 */       .filter(paramObject -> (paramObject != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void purgeValue(V paramV) {
/* 165 */     purgeStaleKeys();
/* 166 */     Objects.requireNonNull(paramV, "value");
/* 167 */     this.hashmap.forEach((paramWeakKey, paramObject2) -> {
/*     */           if (paramObject1.equals(paramObject2)) {
/*     */             this.hashmap.remove(paramWeakKey, paramObject2);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void purgeStaleKeys() {
/*     */     Reference<? extends K> reference;
/* 179 */     while ((reference = this.lastQueue.poll()) != null) {
/* 180 */       this.hashmap.remove(reference);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class WeakKey<K>
/*     */     extends WeakReference<K>
/*     */   {
/*     */     private final int hash;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     WeakKey(K param1K, ReferenceQueue<K> param1ReferenceQueue) {
/* 200 */       super(param1K, param1ReferenceQueue);
/* 201 */       this.hash = System.identityHashCode(param1K);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 209 */       return this.hash;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 220 */       if (param1Object == this) {
/* 221 */         return true;
/*     */       }
/*     */       
/* 224 */       if (param1Object instanceof WeakKey) {
/* 225 */         K k = get();
/* 226 */         return (k != null && k == ((WeakKey<K>)param1Object)
/* 227 */           .get());
/*     */       } 
/* 229 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\WeakKeyConcurrentHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */