/*     */ package sun.management.snmp.util;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MibLogger
/*     */ {
/*     */   final Logger logger;
/*     */   final String className;
/*     */   
/*     */   static String getClassName(Class<?> paramClass) {
/*  36 */     if (paramClass == null) return null; 
/*  37 */     if (paramClass.isArray())
/*  38 */       return getClassName(paramClass.getComponentType()) + "[]"; 
/*  39 */     String str = paramClass.getName();
/*  40 */     int i = str.lastIndexOf('.');
/*  41 */     int j = str.length();
/*  42 */     if (i < 0 || i >= j)
/*  43 */       return str; 
/*  44 */     return str.substring(i + 1, j);
/*     */   }
/*     */   
/*     */   static String getLoggerName(Class<?> paramClass) {
/*  48 */     if (paramClass == null) return "sun.management.snmp.jvminstr"; 
/*  49 */     Package package_ = paramClass.getPackage();
/*  50 */     if (package_ == null) return "sun.management.snmp.jvminstr"; 
/*  51 */     String str = package_.getName();
/*  52 */     if (str == null) return "sun.management.snmp.jvminstr"; 
/*  53 */     return str;
/*     */   }
/*     */   
/*     */   public MibLogger(Class<?> paramClass) {
/*  57 */     this(getLoggerName(paramClass), getClassName(paramClass));
/*     */   }
/*     */   
/*     */   public MibLogger(Class<?> paramClass, String paramString) {
/*  61 */     this(getLoggerName(paramClass) + ((paramString == null) ? "" : ("." + paramString)), 
/*  62 */         getClassName(paramClass));
/*     */   }
/*     */   
/*     */   public MibLogger(String paramString) {
/*  66 */     this("sun.management.snmp.jvminstr", paramString);
/*     */   }
/*     */   
/*     */   public MibLogger(String paramString1, String paramString2) {
/*  70 */     Logger logger = null;
/*     */     try {
/*  72 */       logger = Logger.getLogger(paramString1);
/*  73 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  76 */     this.logger = logger;
/*  77 */     this.className = paramString2;
/*     */   }
/*     */   
/*     */   protected Logger getLogger() {
/*  81 */     return this.logger;
/*     */   }
/*     */   
/*     */   public boolean isTraceOn() {
/*  85 */     Logger logger = getLogger();
/*  86 */     if (logger == null) return false; 
/*  87 */     return logger.isLoggable(Level.FINE);
/*     */   }
/*     */   
/*     */   public boolean isDebugOn() {
/*  91 */     Logger logger = getLogger();
/*  92 */     if (logger == null) return false; 
/*  93 */     return logger.isLoggable(Level.FINEST);
/*     */   }
/*     */   
/*     */   public boolean isInfoOn() {
/*  97 */     Logger logger = getLogger();
/*  98 */     if (logger == null) return false; 
/*  99 */     return logger.isLoggable(Level.INFO);
/*     */   }
/*     */   
/*     */   public boolean isConfigOn() {
/* 103 */     Logger logger = getLogger();
/* 104 */     if (logger == null) return false; 
/* 105 */     return logger.isLoggable(Level.CONFIG);
/*     */   }
/*     */   
/*     */   public void config(String paramString1, String paramString2) {
/* 109 */     Logger logger = getLogger();
/* 110 */     if (logger != null) logger.logp(Level.CONFIG, this.className, paramString1, paramString2);
/*     */   
/*     */   }
/*     */   
/*     */   public void config(String paramString, Throwable paramThrowable) {
/* 115 */     Logger logger = getLogger();
/* 116 */     if (logger != null) logger.logp(Level.CONFIG, this.className, paramString, paramThrowable
/* 117 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public void config(String paramString1, String paramString2, Throwable paramThrowable) {
/* 121 */     Logger logger = getLogger();
/* 122 */     if (logger != null) logger.logp(Level.CONFIG, this.className, paramString1, paramString2, paramThrowable);
/*     */   
/*     */   }
/*     */   
/*     */   public void error(String paramString1, String paramString2) {
/* 127 */     Logger logger = getLogger();
/* 128 */     if (logger != null) logger.logp(Level.SEVERE, this.className, paramString1, paramString2);
/*     */   
/*     */   }
/*     */   
/*     */   public void info(String paramString1, String paramString2) {
/* 133 */     Logger logger = getLogger();
/* 134 */     if (logger != null) logger.logp(Level.INFO, this.className, paramString1, paramString2);
/*     */   
/*     */   }
/*     */   
/*     */   public void info(String paramString, Throwable paramThrowable) {
/* 139 */     Logger logger = getLogger();
/* 140 */     if (logger != null) logger.logp(Level.INFO, this.className, paramString, paramThrowable
/* 141 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public void info(String paramString1, String paramString2, Throwable paramThrowable) {
/* 145 */     Logger logger = getLogger();
/* 146 */     if (logger != null) logger.logp(Level.INFO, this.className, paramString1, paramString2, paramThrowable);
/*     */   
/*     */   }
/*     */   
/*     */   public void warning(String paramString1, String paramString2) {
/* 151 */     Logger logger = getLogger();
/* 152 */     if (logger != null) logger.logp(Level.WARNING, this.className, paramString1, paramString2);
/*     */   
/*     */   }
/*     */   
/*     */   public void warning(String paramString, Throwable paramThrowable) {
/* 157 */     Logger logger = getLogger();
/* 158 */     if (logger != null) logger.logp(Level.WARNING, this.className, paramString, paramThrowable
/* 159 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public void warning(String paramString1, String paramString2, Throwable paramThrowable) {
/* 163 */     Logger logger = getLogger();
/* 164 */     if (logger != null) logger.logp(Level.WARNING, this.className, paramString1, paramString2, paramThrowable);
/*     */   
/*     */   }
/*     */   
/*     */   public void trace(String paramString1, String paramString2) {
/* 169 */     Logger logger = getLogger();
/* 170 */     if (logger != null) logger.logp(Level.FINE, this.className, paramString1, paramString2);
/*     */   
/*     */   }
/*     */   
/*     */   public void trace(String paramString, Throwable paramThrowable) {
/* 175 */     Logger logger = getLogger();
/* 176 */     if (logger != null) logger.logp(Level.FINE, this.className, paramString, paramThrowable
/* 177 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public void trace(String paramString1, String paramString2, Throwable paramThrowable) {
/* 181 */     Logger logger = getLogger();
/* 182 */     if (logger != null) logger.logp(Level.FINE, this.className, paramString1, paramString2, paramThrowable);
/*     */   
/*     */   }
/*     */   
/*     */   public void debug(String paramString1, String paramString2) {
/* 187 */     Logger logger = getLogger();
/* 188 */     if (logger != null) logger.logp(Level.FINEST, this.className, paramString1, paramString2);
/*     */   
/*     */   }
/*     */   
/*     */   public void debug(String paramString, Throwable paramThrowable) {
/* 193 */     Logger logger = getLogger();
/* 194 */     if (logger != null) logger.logp(Level.FINEST, this.className, paramString, paramThrowable
/* 195 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public void debug(String paramString1, String paramString2, Throwable paramThrowable) {
/* 199 */     Logger logger = getLogger();
/* 200 */     if (logger != null) logger.logp(Level.FINEST, this.className, paramString1, paramString2, paramThrowable); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snm\\util\MibLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */