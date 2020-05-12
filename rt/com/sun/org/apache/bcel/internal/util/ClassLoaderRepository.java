/*     */ package com.sun.org.apache.bcel.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.classfile.ClassParser;
/*     */ import com.sun.org.apache.bcel.internal.classfile.JavaClass;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassLoaderRepository
/*     */   implements Repository
/*     */ {
/*     */   private java.lang.ClassLoader loader;
/*  84 */   private HashMap loadedClasses = new HashMap<>();
/*     */ 
/*     */   
/*     */   public ClassLoaderRepository(java.lang.ClassLoader loader) {
/*  88 */     this.loader = loader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeClass(JavaClass clazz) {
/*  95 */     this.loadedClasses.put(clazz.getClassName(), clazz);
/*     */     
/*  97 */     clazz.setRepository(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeClass(JavaClass clazz) {
/* 104 */     this.loadedClasses.remove(clazz.getClassName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaClass findClass(String className) {
/* 111 */     if (this.loadedClasses.containsKey(className)) {
/* 112 */       return (JavaClass)this.loadedClasses.get(className);
/*     */     }
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaClass loadClass(String className) throws ClassNotFoundException {
/* 124 */     String classFile = className.replace('.', '/');
/*     */     
/* 126 */     JavaClass RC = findClass(className);
/* 127 */     if (RC != null) return RC;
/*     */ 
/*     */     
/*     */     try {
/* 131 */       InputStream is = this.loader.getResourceAsStream(classFile + ".class");
/*     */       
/* 133 */       if (is == null) {
/* 134 */         throw new ClassNotFoundException(className + " not found.");
/*     */       }
/*     */       
/* 137 */       ClassParser parser = new ClassParser(is, className);
/* 138 */       RC = parser.parse();
/*     */       
/* 140 */       storeClass(RC);
/*     */       
/* 142 */       return RC;
/* 143 */     } catch (IOException e) {
/* 144 */       throw new ClassNotFoundException(e.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public JavaClass loadClass(Class clazz) throws ClassNotFoundException {
/* 149 */     return loadClass(clazz.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 155 */     this.loadedClasses.clear();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\interna\\util\ClassLoaderRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */