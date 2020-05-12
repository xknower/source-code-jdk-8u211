/*     */ package com.sun.jmx.snmp;
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
/*     */ public class ThreadContext
/*     */   implements Cloneable
/*     */ {
/*     */   private ThreadContext previous;
/*     */   private String key;
/*     */   private Object value;
/*     */   
/*     */   private ThreadContext(ThreadContext paramThreadContext, String paramString, Object paramObject) {
/* 104 */     this.previous = paramThreadContext;
/* 105 */     this.key = paramString;
/* 106 */     this.value = paramObject;
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
/*     */   public static Object get(String paramString) throws IllegalArgumentException {
/* 125 */     ThreadContext threadContext = contextContaining(paramString);
/* 126 */     if (threadContext == null) {
/* 127 */       return null;
/*     */     }
/* 129 */     return threadContext.value;
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
/*     */   public static boolean contains(String paramString) throws IllegalArgumentException {
/* 148 */     return (contextContaining(paramString) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ThreadContext contextContaining(String paramString) throws IllegalArgumentException {
/* 159 */     if (paramString == null)
/* 160 */       throw new IllegalArgumentException("null key"); 
/* 161 */     ThreadContext threadContext = getContext();
/* 162 */     for (; threadContext != null; 
/* 163 */       threadContext = threadContext.previous) {
/* 164 */       if (paramString.equals(threadContext.key)) {
/* 165 */         return threadContext;
/*     */       }
/*     */     } 
/*     */     
/* 169 */     return null;
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
/*     */   public static ThreadContext push(String paramString, Object paramObject) throws IllegalArgumentException {
/* 213 */     if (paramString == null) {
/* 214 */       throw new IllegalArgumentException("null key");
/*     */     }
/* 216 */     ThreadContext threadContext1 = getContext();
/* 217 */     if (threadContext1 == null)
/* 218 */       threadContext1 = new ThreadContext(null, null, null); 
/* 219 */     ThreadContext threadContext2 = new ThreadContext(threadContext1, paramString, paramObject);
/* 220 */     setContext(threadContext2);
/* 221 */     return threadContext1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadContext getThreadContext() {
/* 232 */     return getContext();
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
/*     */   public static void restore(ThreadContext paramThreadContext) throws NullPointerException, IllegalArgumentException {
/* 255 */     if (paramThreadContext == null) {
/* 256 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 259 */     ThreadContext threadContext = getContext();
/* 260 */     for (; threadContext != paramThreadContext; 
/* 261 */       threadContext = threadContext.previous) {
/* 262 */       if (threadContext == null) {
/* 263 */         throw new IllegalArgumentException("Restored context is not contained in current context");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     if (paramThreadContext.key == null) {
/* 275 */       paramThreadContext = null;
/*     */     }
/* 277 */     setContext(paramThreadContext);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInitialContext(ThreadContext paramThreadContext) throws IllegalArgumentException {
/* 311 */     if (getContext() != null)
/* 312 */       throw new IllegalArgumentException("previous context not empty"); 
/* 313 */     setContext(paramThreadContext);
/*     */   }
/*     */   
/*     */   private static ThreadContext getContext() {
/* 317 */     return localContext.get();
/*     */   }
/*     */   
/*     */   private static void setContext(ThreadContext paramThreadContext) {
/* 321 */     localContext.set(paramThreadContext);
/*     */   }
/*     */   
/* 324 */   private static ThreadLocal<ThreadContext> localContext = new ThreadLocal<>();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\ThreadContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */