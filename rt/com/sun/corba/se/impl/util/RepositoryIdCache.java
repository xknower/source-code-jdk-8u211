/*     */ package com.sun.corba.se.impl.util;
/*     */ 
/*     */ import java.util.Hashtable;
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
/*     */ public class RepositoryIdCache
/*     */   extends Hashtable
/*     */ {
/*  89 */   private RepositoryIdPool pool = new RepositoryIdPool();
/*     */   
/*     */   public RepositoryIdCache() {
/*  92 */     this.pool.setCaches(this);
/*     */   }
/*     */   
/*     */   public final synchronized RepositoryId getId(String paramString) {
/*  96 */     RepositoryId repositoryId = (RepositoryId)get(paramString);
/*     */     
/*  98 */     if (repositoryId != null) {
/*  99 */       return repositoryId;
/*     */     }
/*     */     
/* 102 */     repositoryId = new RepositoryId(paramString);
/* 103 */     put((K)paramString, (V)repositoryId);
/* 104 */     return repositoryId;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\imp\\util\RepositoryIdCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */