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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyntheticRepository
/*     */   implements Repository
/*     */ {
/*  89 */   private static final String DEFAULT_PATH = ClassPath.getClassPath();
/*     */   
/*  91 */   private static HashMap _instances = new HashMap<>();
/*     */   
/*  93 */   private ClassPath _path = null;
/*  94 */   private HashMap _loadedClasses = new HashMap<>();
/*     */   
/*     */   private SyntheticRepository(ClassPath path) {
/*  97 */     this._path = path;
/*     */   }
/*     */   
/*     */   public static SyntheticRepository getInstance() {
/* 101 */     return getInstance(ClassPath.SYSTEM_CLASS_PATH);
/*     */   }
/*     */   
/*     */   public static SyntheticRepository getInstance(ClassPath classPath) {
/* 105 */     SyntheticRepository rep = (SyntheticRepository)_instances.get(classPath);
/*     */     
/* 107 */     if (rep == null) {
/* 108 */       rep = new SyntheticRepository(classPath);
/* 109 */       _instances.put(classPath, rep);
/*     */     } 
/*     */     
/* 112 */     return rep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeClass(JavaClass clazz) {
/* 119 */     this._loadedClasses.put(clazz.getClassName(), clazz);
/* 120 */     clazz.setRepository(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeClass(JavaClass clazz) {
/* 127 */     this._loadedClasses.remove(clazz.getClassName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaClass findClass(String className) {
/* 134 */     return (JavaClass)this._loadedClasses.get(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaClass loadClass(String className) throws ClassNotFoundException {
/* 144 */     if (className == null || className.equals("")) {
/* 145 */       throw new IllegalArgumentException("Invalid class name " + className);
/*     */     }
/*     */     
/* 148 */     className = className.replace('/', '.');
/*     */     
/*     */     try {
/* 151 */       return loadClass(this._path.getInputStream(className), className);
/* 152 */     } catch (IOException e) {
/* 153 */       throw new ClassNotFoundException("Exception while looking for class " + className + ": " + e
/* 154 */           .toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaClass loadClass(Class clazz) throws ClassNotFoundException {
/* 164 */     String className = clazz.getName();
/* 165 */     String name = className;
/* 166 */     int i = name.lastIndexOf('.');
/*     */     
/* 168 */     if (i > 0) {
/* 169 */       name = name.substring(i + 1);
/*     */     }
/*     */     
/* 172 */     return loadClass(clazz.getResourceAsStream(name + ".class"), className);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JavaClass loadClass(InputStream is, String className) throws ClassNotFoundException {
/* 178 */     JavaClass clazz = findClass(className);
/*     */     
/* 180 */     if (clazz != null) {
/* 181 */       return clazz;
/*     */     }
/*     */     
/*     */     try {
/* 185 */       if (is != null) {
/* 186 */         ClassParser parser = new ClassParser(is, className);
/* 187 */         clazz = parser.parse();
/*     */         
/* 189 */         storeClass(clazz);
/*     */         
/* 191 */         return clazz;
/*     */       } 
/* 193 */     } catch (IOException e) {
/* 194 */       throw new ClassNotFoundException("Exception while looking for class " + className + ": " + e
/* 195 */           .toString());
/*     */     } 
/*     */     
/* 198 */     throw new ClassNotFoundException("SyntheticRepository could not load " + className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 205 */     this._loadedClasses.clear();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\interna\\util\SyntheticRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */