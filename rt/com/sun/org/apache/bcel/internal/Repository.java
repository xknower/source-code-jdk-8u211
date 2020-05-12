/*     */ package com.sun.org.apache.bcel.internal;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.classfile.JavaClass;
/*     */ import com.sun.org.apache.bcel.internal.util.ClassPath;
/*     */ import com.sun.org.apache.bcel.internal.util.Repository;
/*     */ import com.sun.org.apache.bcel.internal.util.SyntheticRepository;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Repository
/*     */ {
/*  77 */   private static Repository _repository = SyntheticRepository.getInstance();
/*     */ 
/*     */ 
/*     */   
/*     */   public static Repository getRepository() {
/*  82 */     return _repository;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setRepository(Repository rep) {
/*  88 */     _repository = rep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaClass lookupClass(String class_name) {
/*     */     try {
/*  99 */       JavaClass clazz = _repository.findClass(class_name);
/*     */       
/* 101 */       if (clazz == null) {
/* 102 */         return _repository.loadClass(class_name);
/*     */       }
/* 104 */       return clazz;
/*     */     } catch (ClassNotFoundException ex) {
/* 106 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaClass lookupClass(Class clazz) {
/*     */     
/* 116 */     try { return _repository.loadClass(clazz); }
/* 117 */     catch (ClassNotFoundException ex) { return null; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public static ClassPath.ClassFile lookupClassFile(String class_name) {
/*     */     
/* 124 */     try { return ClassPath.SYSTEM_CLASS_PATH.getClassFile(class_name); }
/* 125 */     catch (IOException e) { return null; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearCache() {
/* 131 */     _repository.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaClass addClass(JavaClass clazz) {
/* 140 */     JavaClass old = _repository.findClass(clazz.getClassName());
/* 141 */     _repository.storeClass(clazz);
/* 142 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeClass(String clazz) {
/* 149 */     _repository.removeClass(_repository.findClass(clazz));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeClass(JavaClass clazz) {
/* 156 */     _repository.removeClass(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaClass[] getSuperClasses(JavaClass clazz) {
/* 164 */     return clazz.getSuperClasses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaClass[] getSuperClasses(String class_name) {
/* 173 */     JavaClass jc = lookupClass(class_name);
/* 174 */     return (jc == null) ? null : getSuperClasses(jc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaClass[] getInterfaces(JavaClass clazz) {
/* 183 */     return clazz.getAllInterfaces();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaClass[] getInterfaces(String class_name) {
/* 191 */     return getInterfaces(lookupClass(class_name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean instanceOf(JavaClass clazz, JavaClass super_class) {
/* 199 */     return clazz.instanceOf(super_class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean instanceOf(String clazz, String super_class) {
/* 206 */     return instanceOf(lookupClass(clazz), lookupClass(super_class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean instanceOf(JavaClass clazz, String super_class) {
/* 213 */     return instanceOf(clazz, lookupClass(super_class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean instanceOf(String clazz, JavaClass super_class) {
/* 220 */     return instanceOf(lookupClass(clazz), super_class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean implementationOf(JavaClass clazz, JavaClass inter) {
/* 227 */     return clazz.implementationOf(inter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean implementationOf(String clazz, String inter) {
/* 234 */     return implementationOf(lookupClass(clazz), lookupClass(inter));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean implementationOf(JavaClass clazz, String inter) {
/* 241 */     return implementationOf(clazz, lookupClass(inter));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean implementationOf(String clazz, JavaClass inter) {
/* 248 */     return implementationOf(lookupClass(clazz), inter);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\Repository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */