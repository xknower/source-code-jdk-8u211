/*     */ package com.sun.xml.internal.ws.model;
/*     */ 
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.WildcardType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class FieldSignature
/*     */ {
/*     */   static String vms(Type t) {
/*  45 */     if (t instanceof Class && ((Class)t).isPrimitive()) {
/*  46 */       Class<int> c = (Class)t;
/*  47 */       if (c == int.class)
/*  48 */         return "I"; 
/*  49 */       if (c == void.class)
/*  50 */         return "V"; 
/*  51 */       if (c == boolean.class)
/*  52 */         return "Z"; 
/*  53 */       if (c == byte.class)
/*  54 */         return "B"; 
/*  55 */       if (c == char.class)
/*  56 */         return "C"; 
/*  57 */       if (c == short.class)
/*  58 */         return "S"; 
/*  59 */       if (c == double.class)
/*  60 */         return "D"; 
/*  61 */       if (c == float.class)
/*  62 */         return "F"; 
/*  63 */       if (c == long.class)
/*  64 */         return "J"; 
/*     */     } else {
/*  66 */       if (t instanceof Class && ((Class)t).isArray())
/*  67 */         return "[" + vms(((Class)t).getComponentType()); 
/*  68 */       if (t instanceof Class || t instanceof ParameterizedType)
/*  69 */         return "L" + fqcn(t) + ";"; 
/*  70 */       if (t instanceof GenericArrayType)
/*  71 */         return "[" + vms(((GenericArrayType)t).getGenericComponentType()); 
/*  72 */       if (t instanceof java.lang.reflect.TypeVariable)
/*     */       {
/*     */ 
/*     */         
/*  76 */         return "Ljava/lang/Object;"; } 
/*  77 */       if (t instanceof WildcardType) {
/*  78 */         WildcardType w = (WildcardType)t;
/*  79 */         if ((w.getLowerBounds()).length > 0)
/*  80 */           return "-" + vms(w.getLowerBounds()[0]); 
/*  81 */         if ((w.getUpperBounds()).length > 0) {
/*  82 */           Type wt = w.getUpperBounds()[0];
/*  83 */           if (wt.equals(Object.class)) {
/*  84 */             return "*";
/*     */           }
/*  86 */           return "+" + vms(wt);
/*     */         } 
/*     */       } 
/*     */     } 
/*  90 */     throw new IllegalArgumentException("Illegal vms arg " + t);
/*     */   }
/*     */   
/*     */   private static String fqcn(Type t) {
/*  94 */     if (t instanceof Class) {
/*  95 */       Class c = (Class)t;
/*  96 */       if (c.getDeclaringClass() == null) {
/*  97 */         return c.getName().replace('.', '/');
/*     */       }
/*  99 */       return fqcn(c.getDeclaringClass()) + "$" + c.getSimpleName();
/*     */     } 
/* 101 */     if (t instanceof ParameterizedType) {
/* 102 */       ParameterizedType p = (ParameterizedType)t;
/* 103 */       if (p.getOwnerType() == null) {
/* 104 */         return fqcn(p.getRawType()) + args(p);
/*     */       }
/* 106 */       assert p.getRawType() instanceof Class;
/* 107 */       return fqcn(p.getOwnerType()) + "." + ((Class)p
/* 108 */         .getRawType()).getSimpleName() + args(p);
/*     */     } 
/*     */     
/* 111 */     throw new IllegalArgumentException("Illegal fqcn arg = " + t);
/*     */   }
/*     */   
/*     */   private static String args(ParameterizedType p) {
/* 115 */     StringBuilder sig = new StringBuilder("<");
/* 116 */     for (Type t : p.getActualTypeArguments()) {
/* 117 */       sig.append(vms(t));
/*     */     }
/* 119 */     return sig.append(">").toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\model\FieldSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */