/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ThemeReader
/*     */ {
/*  54 */   private static final Map<String, Long> widgetToTheme = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
/*     */   
/*  61 */   private static final Lock readLock = readWriteLock.readLock();
/*  62 */   private static final Lock writeLock = readWriteLock.writeLock();
/*     */   
/*     */   private static volatile boolean valid = false;
/*     */   
/*     */   private static volatile boolean isThemed;
/*     */   
/*     */   static volatile boolean xpStyleEnabled;
/*     */   
/*     */   static void flush() {
/*  71 */     valid = false;
/*     */   }
/*     */   
/*     */   private static native boolean initThemes();
/*     */   
/*     */   public static boolean isThemed() {
/*  77 */     writeLock.lock();
/*     */     try {
/*  79 */       isThemed = initThemes();
/*  80 */       return isThemed;
/*     */     } finally {
/*  82 */       writeLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isXPStyleEnabled() {
/*  87 */     return xpStyleEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Long getThemeImpl(String paramString) {
/*  92 */     Long long_ = widgetToTheme.get(paramString);
/*  93 */     if (long_ == null) {
/*  94 */       int i = paramString.indexOf("::");
/*  95 */       if (i > 0) {
/*     */ 
/*     */         
/*  98 */         setWindowTheme(paramString.substring(0, i));
/*  99 */         long_ = Long.valueOf(openTheme(paramString.substring(i + 2)));
/* 100 */         setWindowTheme(null);
/*     */       } else {
/* 102 */         long_ = Long.valueOf(openTheme(paramString));
/*     */       } 
/* 104 */       widgetToTheme.put(paramString, long_);
/*     */     } 
/* 106 */     return long_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Long getTheme(String paramString) {
/* 112 */     if (!isThemed) {
/* 113 */       throw new IllegalStateException("Themes are not loaded");
/*     */     }
/* 115 */     if (!valid) {
/* 116 */       readLock.unlock();
/* 117 */       writeLock.lock();
/*     */       try {
/* 119 */         if (!valid) {
/*     */           
/* 121 */           for (Long long_1 : widgetToTheme.values()) {
/* 122 */             closeTheme(long_1.longValue());
/*     */           }
/* 124 */           widgetToTheme.clear();
/* 125 */           valid = true;
/*     */         } 
/*     */       } finally {
/* 128 */         readLock.lock();
/* 129 */         writeLock.unlock();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 134 */     Long long_ = widgetToTheme.get(paramString);
/* 135 */     if (long_ == null) {
/* 136 */       readLock.unlock();
/* 137 */       writeLock.lock();
/*     */       try {
/* 139 */         long_ = getThemeImpl(paramString);
/*     */       } finally {
/* 141 */         readLock.lock();
/* 142 */         writeLock.unlock();
/*     */       } 
/*     */     } 
/* 145 */     return long_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void paintBackground(int[] paramArrayOfint, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */ 
/*     */   
/*     */   public static void paintBackground(int[] paramArrayOfint, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/* 154 */     readLock.lock();
/*     */     try {
/* 156 */       paintBackground(paramArrayOfint, getTheme(paramString).longValue(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */     } finally {
/* 158 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native Insets getThemeMargins(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   public static Insets getThemeMargins(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 166 */     readLock.lock();
/*     */     try {
/* 168 */       return getThemeMargins(getTheme(paramString).longValue(), paramInt1, paramInt2, paramInt3);
/*     */     } finally {
/* 170 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static native boolean isThemePartDefined(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   public static boolean isThemePartDefined(String paramString, int paramInt1, int paramInt2) {
/* 177 */     readLock.lock();
/*     */     try {
/* 179 */       return isThemePartDefined(getTheme(paramString).longValue(), paramInt1, paramInt2);
/*     */     } finally {
/* 181 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native Color getColor(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   public static Color getColor(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 189 */     readLock.lock();
/*     */     try {
/* 191 */       return getColor(getTheme(paramString).longValue(), paramInt1, paramInt2, paramInt3);
/*     */     } finally {
/* 193 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native int getInt(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   public static int getInt(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 201 */     readLock.lock();
/*     */     try {
/* 203 */       return getInt(getTheme(paramString).longValue(), paramInt1, paramInt2, paramInt3);
/*     */     } finally {
/* 205 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native int getEnum(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   public static int getEnum(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 213 */     readLock.lock();
/*     */     try {
/* 215 */       return getEnum(getTheme(paramString).longValue(), paramInt1, paramInt2, paramInt3);
/*     */     } finally {
/* 217 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native boolean getBoolean(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 226 */     readLock.lock();
/*     */     try {
/* 228 */       return getBoolean(getTheme(paramString).longValue(), paramInt1, paramInt2, paramInt3);
/*     */     } finally {
/* 230 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static native boolean getSysBoolean(long paramLong, int paramInt);
/*     */   
/*     */   public static boolean getSysBoolean(String paramString, int paramInt) {
/* 237 */     readLock.lock();
/*     */     try {
/* 239 */       return getSysBoolean(getTheme(paramString).longValue(), paramInt);
/*     */     } finally {
/* 241 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native Point getPoint(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   public static Point getPoint(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 249 */     readLock.lock();
/*     */     try {
/* 251 */       return getPoint(getTheme(paramString).longValue(), paramInt1, paramInt2, paramInt3);
/*     */     } finally {
/* 253 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native Dimension getPosition(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   
/*     */   public static Dimension getPosition(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 262 */     readLock.lock();
/*     */     try {
/* 264 */       return getPosition(getTheme(paramString).longValue(), paramInt1, paramInt2, paramInt3);
/*     */     } finally {
/* 266 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native Dimension getPartSize(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   public static Dimension getPartSize(String paramString, int paramInt1, int paramInt2) {
/* 274 */     readLock.lock();
/*     */     try {
/* 276 */       return getPartSize(getTheme(paramString).longValue(), paramInt1, paramInt2);
/*     */     } finally {
/* 278 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native long openTheme(String paramString);
/*     */ 
/*     */   
/*     */   private static native void closeTheme(long paramLong);
/*     */   
/*     */   private static native void setWindowTheme(String paramString);
/*     */   
/*     */   private static native long getThemeTransitionDuration(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   public static long getThemeTransitionDuration(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 293 */     readLock.lock();
/*     */     try {
/* 295 */       return getThemeTransitionDuration(getTheme(paramString).longValue(), paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } finally {
/*     */       
/* 298 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static native boolean isGetThemeTransitionDurationDefined();
/*     */ 
/*     */   
/*     */   private static native Insets getThemeBackgroundContentMargins(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   public static Insets getThemeBackgroundContentMargins(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 309 */     readLock.lock();
/*     */     try {
/* 311 */       return getThemeBackgroundContentMargins(getTheme(paramString).longValue(), paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } finally {
/*     */       
/* 314 */       readLock.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\ThemeReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */