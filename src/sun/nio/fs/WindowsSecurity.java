/*     */ package sun.nio.fs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsSecurity
/*     */ {
/*     */   private static long openProcessToken(int paramInt) {
/*     */     try {
/*  41 */       return WindowsNativeDispatcher.OpenProcessToken(WindowsNativeDispatcher.GetCurrentProcess(), paramInt);
/*  42 */     } catch (WindowsException windowsException) {
/*  43 */       return 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   static final long processTokenWithDuplicateAccess = openProcessToken(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   static final long processTokenWithQueryAccess = openProcessToken(8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Privilege enablePrivilege(String paramString) {
/*     */     long l1;
/*     */     try {
/*  74 */       l1 = WindowsNativeDispatcher.LookupPrivilegeValue(paramString);
/*  75 */     } catch (WindowsException windowsException) {
/*     */       
/*  77 */       throw new AssertionError(windowsException);
/*     */     } 
/*     */     
/*  80 */     long l2 = 0L;
/*  81 */     boolean bool1 = false;
/*  82 */     boolean bool2 = false;
/*     */     try {
/*  84 */       l2 = WindowsNativeDispatcher.OpenThreadToken(WindowsNativeDispatcher.GetCurrentThread(), 32, false);
/*     */       
/*  86 */       if (l2 == 0L && processTokenWithDuplicateAccess != 0L) {
/*  87 */         l2 = WindowsNativeDispatcher.DuplicateTokenEx(processTokenWithDuplicateAccess, 36);
/*     */         
/*  89 */         WindowsNativeDispatcher.SetThreadToken(0L, l2);
/*  90 */         bool1 = true;
/*     */       } 
/*     */       
/*  93 */       if (l2 != 0L) {
/*  94 */         WindowsNativeDispatcher.AdjustTokenPrivileges(l2, l1, 2);
/*  95 */         bool2 = true;
/*     */       } 
/*  97 */     } catch (WindowsException windowsException) {}
/*     */ 
/*     */ 
/*     */     
/* 101 */     long l3 = l2;
/* 102 */     boolean bool3 = bool1;
/* 103 */     boolean bool4 = bool2;
/*     */     
/* 105 */     return () -> { try {
/*     */           if (paramLong1 != 0L) {
/*     */             try {
/*     */               if (paramBoolean1) {
/*     */                 WindowsNativeDispatcher.SetThreadToken(0L, 0L);
/*     */               } else if (paramBoolean2) {
/*     */                 WindowsNativeDispatcher.AdjustTokenPrivileges(paramLong1, paramLong2, 0);
/*     */               } 
/* 113 */             } catch (WindowsException windowsException) {
/*     */               throw new AssertionError(windowsException);
/*     */             } finally {
/*     */               WindowsNativeDispatcher.CloseHandle(paramLong1);
/*     */             } 
/*     */           }
/*     */         } finally {
/*     */           WindowsNativeDispatcher.LocalFree(paramLong2);
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean checkAccessMask(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws WindowsException {
/* 133 */     byte b = 8;
/* 134 */     long l = WindowsNativeDispatcher.OpenThreadToken(WindowsNativeDispatcher.GetCurrentThread(), b, false);
/* 135 */     if (l == 0L && processTokenWithDuplicateAccess != 0L) {
/* 136 */       l = WindowsNativeDispatcher.DuplicateTokenEx(processTokenWithDuplicateAccess, b);
/*     */     }
/*     */     
/* 139 */     boolean bool = false;
/* 140 */     if (l != 0L) {
/*     */       try {
/* 142 */         bool = WindowsNativeDispatcher.AccessCheck(l, paramLong, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */       } finally {
/*     */         
/* 145 */         WindowsNativeDispatcher.CloseHandle(l);
/*     */       } 
/*     */     }
/* 148 */     return bool;
/*     */   }
/*     */   
/*     */   static interface Privilege {
/*     */     void drop();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsSecurity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */