/*     */ package com.sun.management.jmx;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.management.NotificationFilter;
/*     */ import javax.management.NotificationListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class Trace
/*     */ {
/*     */   public static final int LEVEL_TRACE = 1;
/*     */   public static final int LEVEL_DEBUG = 2;
/*     */   public static final int INFO_MBEANSERVER = 1;
/*     */   public static final int INFO_MLET = 2;
/*     */   public static final int INFO_MONITOR = 4;
/*     */   public static final int INFO_TIMER = 8;
/*     */   public static final int INFO_ADAPTOR_HTML = 16;
/*     */   public static final int INFO_MISC = 32;
/*     */   public static final int INFO_RELATION = 64;
/*     */   public static final int INFO_MODELMBEAN = 128;
/*     */   public static final int INFO_ALL = 255;
/*     */   protected static final String UNKOWNTYPE = "Unknown type";
/*     */   
/*     */   public static boolean isSelected(int paramInt1, int paramInt2) {
/* 103 */     return false;
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
/*     */   public static void parseTraceProperties() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean send(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3) {
/* 124 */     return false;
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
/*     */   public static boolean send(int paramInt1, int paramInt2, String paramString1, String paramString2, Throwable paramThrowable) {
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws IllegalArgumentException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addNotificationListener(TraceListener paramTraceListener, Object paramObject) throws IllegalArgumentException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeNotificationListener(NotificationListener paramNotificationListener) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeAllListeners() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String getRIType(int paramInt) {
/* 180 */     return getType(paramInt);
/*     */   }
/*     */   
/*     */   static String getType(int paramInt) {
/* 184 */     return "Unknown type";
/*     */   }
/*     */   
/*     */   static String getLevel(int paramInt) {
/* 188 */     return "Unknown level";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\management\jmx\Trace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */