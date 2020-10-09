/*    */ package sun.reflect;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import sun.misc.Unsafe;
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
/*    */ class ClassDefiner
/*    */ {
/* 37 */   static final Unsafe unsafe = Unsafe.getUnsafe();
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
/*    */   static Class<?> defineClass(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, final ClassLoader parentClassLoader) {
/* 57 */     ClassLoader classLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*    */         {
/*    */           public ClassLoader run() {
/* 60 */             return new DelegatingClassLoader(parentClassLoader);
/*    */           }
/*    */         });
/* 63 */     return unsafe.defineClass(paramString, paramArrayOfbyte, paramInt1, paramInt2, classLoader, null);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\ClassDefiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */