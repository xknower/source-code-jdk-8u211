/*     */ package sun.usagetracker;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import jdk.internal.util.EnvUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UsageTrackerClient
/*     */ {
/*  98 */   private static final Object LOCK = new Object();
/*     */   
/*     */   private static final String ORCL_UT_CONFIG_FILE_NAME = "usagetracker.properties";
/*     */   
/*     */   private static final String ORCL_UT_USAGE_DIR = ".oracle_jre_usage";
/*     */   
/*     */   private static final String ORCL_UT_PROPERTY_NAME = "com.oracle.usagetracker.";
/*     */   
/*     */   private static final String ORCL_UT_PROPERTY_RUN_SYNCHRONOUSLY = "com.oracle.usagetracker.run.synchronous";
/*     */   
/*     */   private static final String ORCL_UT_PROPERTY_CONFIG_FILE_NAME = "com.oracle.usagetracker.config.file";
/*     */   
/*     */   private static final String ORCL_UT_LOGTOFILE = "com.oracle.usagetracker.logToFile";
/*     */   
/*     */   private static final String ORCL_UT_LOGFILEMAXSIZE = "com.oracle.usagetracker.logFileMaxSize";
/*     */   
/*     */   private static final String ORCL_UT_LOGTOUDP = "com.oracle.usagetracker.logToUDP";
/*     */   
/*     */   private static final String ORCL_UT_RECORD_MAXSIZE = "com.oracle.usagetracker.maxSize";
/*     */   
/*     */   private static final String ORCL_UT_RECORD_MAXFIELDSIZE = "com.oracle.usagetracker.maxFieldSize";
/*     */   private static final String ORCL_UT_SEND_TRUNCATED = "com.oracle.usagetracker.sendTruncatedRecords";
/*     */   private static final String ORCL_UT_TRACK_LAST_USAGE = "com.oracle.usagetracker.track.last.usage";
/*     */   private static final String ORCL_UT_VERBOSE = "com.oracle.usagetracker.verbose";
/*     */   private static final String ORCL_UT_DEBUG = "com.oracle.usagetracker.debug";
/*     */   private static final String ORCL_UT_ADDITIONALPROPERTIES = "com.oracle.usagetracker.additionalProperties";
/*     */   private static final String ORCL_UT_SEPARATOR = "com.oracle.usagetracker.separator";
/*     */   private static final String ORCL_UT_QUOTE = "com.oracle.usagetracker.quote";
/*     */   private static final String ORCL_UT_QUOTE_INNER = "com.oracle.usagetracker.innerQuote";
/*     */   private static final String DISABLE_LAST_USAGE_PROP_NAME = "jdk.disableLastUsageTracking";
/*     */   private static final String DEFAULT_SEP = ",";
/*     */   private static final String DEFAULT_QUOTE = "\"";
/*     */   private static final String DEFAULT_QUOTE_INNER = "'";
/* 131 */   private static final AtomicBoolean isFirstRun = new AtomicBoolean(true);
/*     */   
/* 133 */   private static final String javaHome = getPropertyPrivileged("java.home");
/*     */   
/*     */   private static final String userHomeKeyword = "${user.home}";
/*     */   
/*     */   private static String separator;
/*     */   private static String quote;
/*     */   private static String innerQuote;
/*     */   private static boolean enabled;
/*     */   private static boolean verbose;
/*     */   private static boolean debug;
/* 143 */   private static boolean trackTime = initTrackTime();
/*     */   
/*     */   private static String[] additionalProperties;
/*     */   
/*     */   private static String fullLogFilename;
/*     */   
/*     */   private static long logFileMaxSize;
/*     */   
/*     */   private static int maxSize;
/*     */   private static int maxFieldSize;
/*     */   private static boolean sendTruncated;
/*     */   private static String datagramHost;
/*     */   private static int datagramPort;
/*     */   private static String staticMessage;
/*     */   private static boolean staticMessageIsTruncated;
/*     */   
/*     */   private static String getPropertyPrivileged(String paramString) {
/* 160 */     return getPropertyPrivileged(paramString, null);
/*     */   }
/*     */   
/*     */   private static String getPropertyPrivileged(final String property, final String defaultValue) {
/* 164 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/* 166 */             return System.getProperty(property, defaultValue);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static String getEnvPrivileged(final String envName) {
/* 172 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/* 174 */             return EnvUtils.getEnvVar(envName);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static boolean initTrackTime() {
/* 180 */     String str = getPropertyPrivileged("jdk.disableLastUsageTracking");
/* 181 */     if (str == null) {
/* 182 */       String str1 = getPropertyPrivileged("os.name");
/* 183 */       if (str1 != null) {
/* 184 */         str1 = str1.toLowerCase();
/* 185 */         if (str1.startsWith("sunos") || str1.startsWith("linux")) {
/* 186 */           return false;
/*     */         }
/*     */       } 
/* 189 */       return true;
/*     */     } 
/* 191 */     return (!str.isEmpty() && !str.equalsIgnoreCase("true"));
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
/*     */   private static File getConfigFilePrivileged() {
/* 203 */     File file = null;
/* 204 */     String[] arrayOfString = new String[3];
/* 205 */     arrayOfString[0] = getPropertyPrivileged("com.oracle.usagetracker.config.file");
/* 206 */     arrayOfString[1] = getOSSpecificConfigFilePath();
/* 207 */     arrayOfString[2] = javaHome + File.separator + "lib" + File.separator + "management" + File.separator + "usagetracker.properties";
/*     */ 
/*     */     
/* 210 */     for (String str : arrayOfString) {
/* 211 */       if (str != null) {
/* 212 */         file = AccessController.<File>doPrivileged(new PrivilegedAction<File>() {
/*     */               public File run() {
/* 214 */                 File file = new File(path);
/* 215 */                 return file.exists() ? file : null;
/*     */               }
/*     */             });
/* 218 */         if (file != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     return file;
/*     */   }
/*     */   
/*     */   private static String getOSSpecificConfigFilePath() {
/* 228 */     String str = getPropertyPrivileged("os.name");
/* 229 */     if (str != null) {
/* 230 */       if (str.toLowerCase().startsWith("sunos"))
/* 231 */         return "/etc/oracle/java/usagetracker.properties"; 
/* 232 */       if (str.toLowerCase().startsWith("mac"))
/* 233 */         return "/Library/Application Support/Oracle/Java/usagetracker.properties"; 
/* 234 */       if (str.toLowerCase().startsWith("win")) {
/* 235 */         String str1 = getEnvPrivileged("ProgramFiles");
/* 236 */         return (str1 == null) ? null : (str1 + "\\Java\\conf\\" + "usagetracker.properties");
/*     */       } 
/* 238 */       if (str.toLowerCase().startsWith("linux")) {
/* 239 */         return "/etc/oracle/java/usagetracker.properties";
/*     */       }
/*     */     } 
/* 242 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getFullLogFilename(Properties paramProperties) {
/* 248 */     String str = paramProperties.getProperty("com.oracle.usagetracker.logToFile", "");
/* 249 */     if (str.isEmpty()) {
/* 250 */       return null;
/*     */     }
/* 252 */     if (str.startsWith("${user.home}")) {
/* 253 */       if (str.length() > "${user.home}".length()) {
/* 254 */         str = getPropertyPrivileged("user.home") + str.substring("${user.home}".length());
/*     */       } else {
/* 256 */         printVerbose("UsageTracker: blank filename after user.home.");
/* 257 */         return null;
/*     */       }
/*     */     
/* 260 */     } else if (!(new File(str)).isAbsolute()) {
/* 261 */       printVerbose("UsageTracker: relative path disallowed.");
/* 262 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 266 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long getPropertyValueLong(Properties paramProperties, String paramString) {
/* 274 */     String str = paramProperties.getProperty(paramString, "");
/* 275 */     if (!str.isEmpty()) {
/*     */       try {
/* 277 */         return Long.parseLong(str);
/* 278 */       } catch (NumberFormatException numberFormatException) {
/* 279 */         printVerbose("UsageTracker: bad value: " + paramString);
/*     */       } 
/*     */     }
/* 282 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean getPropertyValueBoolean(Properties paramProperties, String paramString, boolean paramBoolean) {
/* 290 */     String str = paramProperties.getProperty(paramString, "");
/* 291 */     if (!str.isEmpty()) {
/* 292 */       return Boolean.parseBoolean(str);
/*     */     }
/* 294 */     return paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] getAdditionalProperties(Properties paramProperties) {
/* 300 */     String str = paramProperties.getProperty("com.oracle.usagetracker.additionalProperties", "");
/* 301 */     return str.isEmpty() ? new String[0] : str.split(",");
/*     */   }
/*     */   
/*     */   private String parseDatagramHost(String paramString) {
/* 305 */     if (paramString != null) {
/* 306 */       int i = paramString.indexOf(':');
/* 307 */       if (i > 0 && i < paramString.length() - 1) {
/* 308 */         return paramString.substring(0, i);
/*     */       }
/* 310 */       printVerbose("UsageTracker: bad UDP details.");
/*     */     } 
/*     */     
/* 313 */     return null;
/*     */   }
/*     */   
/*     */   private int parseDatagramPort(String paramString) {
/* 317 */     if (paramString != null) {
/* 318 */       int i = paramString.indexOf(':');
/*     */       try {
/* 320 */         return Integer.parseInt(paramString.substring(i + 1));
/* 321 */       } catch (Exception exception) {
/* 322 */         printVerbose("UsageTracker: bad UDP port.");
/*     */       } 
/*     */     } 
/* 325 */     return 0;
/*     */   }
/*     */   
/*     */   private void printVerbose(String paramString) {
/* 329 */     if (verbose) {
/* 330 */       System.err.println(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   private void printDebug(String paramString) {
/* 335 */     if (debug) {
/* 336 */       System.err.println(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   private void printDebugStackTrace(Throwable paramThrowable) {
/* 341 */     if (debug) {
/* 342 */       paramThrowable.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupAndTimestamp(long paramLong) {
/* 353 */     if (isFirstRun.compareAndSet(true, false)) {
/* 354 */       File file = getConfigFilePrivileged();
/* 355 */       if (file != null) {
/* 356 */         setup(file);
/*     */       }
/* 358 */       if (trackTime) {
/* 359 */         registerUsage(paramLong);
/*     */       }
/*     */     } 
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
/*     */   public void run(final String callerName, final String javaCommand) {
/* 374 */     printDebug("UsageTracker.run: " + callerName + ", javaCommand: " + javaCommand);
/*     */     try {
/* 376 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */             public Void run() {
/* 378 */               long l = System.currentTimeMillis();
/* 379 */               boolean bool = Boolean.parseBoolean(System.getProperty("com.oracle.usagetracker.run.synchronous", "true"));
/* 380 */               if (bool) {
/* 381 */                 UsageTrackerClient.this.setupAndTimestamp(l);
/* 382 */                 UsageTrackerClient.this.printVerbose("UsageTracker: running synchronous.");
/*     */               } 
/* 384 */               if (UsageTrackerClient.enabled || !bool) {
/* 385 */                 UsageTrackerClient.UsageTrackerRunnable usageTrackerRunnable = new UsageTrackerClient.UsageTrackerRunnable(callerName, javaCommand, l, !bool);
/*     */ 
/*     */                 
/* 388 */                 ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
/* 389 */                 while (threadGroup.getParent() != null) {
/* 390 */                   threadGroup = threadGroup.getParent();
/*     */                 }
/* 392 */                 Thread thread = new Thread(threadGroup, usageTrackerRunnable, "UsageTracker");
/* 393 */                 thread.setDaemon(true);
/* 394 */                 thread.start();
/*     */               } 
/* 396 */               return null;
/*     */             }
/*     */           });
/* 399 */     } catch (Throwable throwable) {
/* 400 */       printVerbose("UsageTracker: error in starting thread.");
/* 401 */       printDebugStackTrace(throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setup(File paramFile) {
/* 406 */     Properties properties = new Properties();
/* 407 */     if (paramFile != null) {
/* 408 */       try(FileInputStream null = new FileInputStream(paramFile); 
/* 409 */           BufferedInputStream null = new BufferedInputStream(fileInputStream)) {
/* 410 */         properties.load(bufferedInputStream);
/* 411 */       } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 416 */         properties.clear();
/*     */       } 
/*     */     }
/* 419 */     verbose = getPropertyValueBoolean(properties, "com.oracle.usagetracker.verbose", false);
/* 420 */     debug = getPropertyValueBoolean(properties, "com.oracle.usagetracker.debug", false);
/* 421 */     separator = properties.getProperty("com.oracle.usagetracker.separator", ",");
/* 422 */     quote = properties.getProperty("com.oracle.usagetracker.quote", "\"");
/* 423 */     innerQuote = properties.getProperty("com.oracle.usagetracker.innerQuote", "'");
/*     */     
/* 425 */     fullLogFilename = getFullLogFilename(properties);
/* 426 */     logFileMaxSize = getPropertyValueLong(properties, "com.oracle.usagetracker.logFileMaxSize");
/* 427 */     maxSize = (int)getPropertyValueLong(properties, "com.oracle.usagetracker.maxSize");
/* 428 */     maxFieldSize = (int)getPropertyValueLong(properties, "com.oracle.usagetracker.maxFieldSize");
/* 429 */     sendTruncated = getPropertyValueBoolean(properties, "com.oracle.usagetracker.sendTruncatedRecords", true);
/* 430 */     additionalProperties = getAdditionalProperties(properties);
/*     */     
/* 432 */     String str = properties.getProperty("com.oracle.usagetracker.logToUDP");
/* 433 */     datagramHost = parseDatagramHost(str);
/* 434 */     datagramPort = parseDatagramPort(str);
/*     */     
/* 436 */     enabled = (((fullLogFilename != null || (datagramHost != null && datagramPort > 0)) ? true : false) == true);
/*     */ 
/*     */     
/* 439 */     if (trackTime) {
/* 440 */       trackTime = getPropertyValueBoolean(properties, "com.oracle.usagetracker.track.last.usage", true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerUsage(long paramLong) {
/*     */     try {
/* 447 */       String str1 = (new File(System.getProperty("java.home"))).getCanonicalPath();
/* 448 */       String str2 = getPropertyPrivileged("os.name");
/* 449 */       File file = null;
/*     */ 
/*     */ 
/*     */       
/* 453 */       if (str2.toLowerCase().startsWith("win")) {
/* 454 */         String str = getEnvPrivileged("ProgramData");
/*     */         
/* 456 */         if (str != null) {
/*     */           
/* 458 */           file = new File(str + File.separator + "Oracle" + File.separator + "Java" + File.separator + ".oracle_jre_usage", getPathHash(str1) + ".timestamp");
/*     */ 
/*     */ 
/*     */           
/* 462 */           if (!file.exists()) {
/* 463 */             if (!file.getParentFile().exists()) {
/* 464 */               if (file.getParentFile().mkdirs()) {
/*     */                 
/* 466 */                 String str3 = getEnvPrivileged("SYSTEMROOT");
/* 467 */                 File file1 = new File(str3 + File.separator + "system32" + File.separator + "icacls.exe");
/* 468 */                 if (file1.exists()) {
/* 469 */                   Runtime.getRuntime().exec(file1 + " " + file.getParentFile() + " /grant \"everyone\":(OI)(CI)M");
/*     */                 }
/*     */               } else {
/*     */                 
/* 473 */                 file = null;
/*     */               } 
/*     */             }
/* 476 */             if (file != null) {
/* 477 */               file.createNewFile();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/* 482 */         String str = System.getProperty("user.home");
/*     */         
/* 484 */         if (str != null) {
/*     */           
/* 486 */           file = new File(str + File.separator + ".oracle_jre_usage", getPathHash(str1) + ".timestamp");
/*     */ 
/*     */           
/* 489 */           if (!file.exists()) {
/* 490 */             if (!file.getParentFile().exists()) {
/* 491 */               file.getParentFile().mkdirs();
/*     */             }
/* 493 */             file.createNewFile();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 498 */       if (file != null) {
/* 499 */         try (FileOutputStream null = new FileOutputStream(file)) {
/*     */ 
/*     */ 
/*     */           
/* 503 */           String str = str1 + System.lineSeparator() + paramLong + System.lineSeparator();
/* 504 */           fileOutputStream.write(str.getBytes("UTF-8"));
/* 505 */         } catch (IOException iOException) {
/* 506 */           printDebugStackTrace(iOException);
/*     */         } 
/*     */       }
/* 509 */     } catch (IOException iOException) {
/* 510 */       printDebugStackTrace(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getPathHash(String paramString) {
/* 515 */     long l = 0L;
/* 516 */     for (byte b = 0; b < paramString.length(); b++) {
/* 517 */       l = 31L * l + paramString.charAt(b);
/*     */     }
/* 519 */     return Long.toHexString(l);
/*     */   }
/*     */ 
/*     */   
/*     */   class UsageTrackerRunnable
/*     */     implements Runnable
/*     */   {
/*     */     private String callerName;
/*     */     private String javaCommand;
/*     */     private long timestamp;
/*     */     private boolean runAsync;
/*     */     private boolean truncated;
/*     */     
/*     */     UsageTrackerRunnable(String param1String1, String param1String2, long param1Long, boolean param1Boolean) {
/* 533 */       this.callerName = param1String1;
/* 534 */       this.javaCommand = (param1String2 != null) ? param1String2 : "";
/* 535 */       this.timestamp = param1Long;
/* 536 */       this.runAsync = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String limitString(String param1String, int param1Int) {
/* 544 */       if (param1Int > 0 && param1String.length() >= param1Int) {
/* 545 */         UsageTrackerClient.this.printDebug("UsgeTracker: limitString truncating: max=" + param1Int + " length=" + param1String.length() + " String: " + param1String);
/* 546 */         this.truncated = true;
/* 547 */         param1String = param1String.substring(0, param1Int);
/*     */       } 
/* 549 */       return param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String buildMessage(String param1String1, String param1String2, long param1Long) {
/* 558 */       param1String2 = limitString(param1String2, UsageTrackerClient.maxFieldSize);
/* 559 */       if (this.truncated && !UsageTrackerClient.sendTruncated) {
/* 560 */         return null;
/*     */       }
/* 562 */       StringBuilder stringBuilder = new StringBuilder();
/* 563 */       appendWithQuotes(stringBuilder, param1String1);
/* 564 */       stringBuilder.append(UsageTrackerClient.separator);
/* 565 */       Date date = new Date(param1Long);
/* 566 */       appendWithQuotes(stringBuilder, date.toString());
/* 567 */       stringBuilder.append(UsageTrackerClient.separator);
/*     */       
/* 569 */       String str1 = "0";
/*     */       try {
/* 571 */         InetAddress inetAddress = InetAddress.getLocalHost();
/* 572 */         str1 = inetAddress.toString();
/* 573 */       } catch (Throwable throwable) {}
/*     */ 
/*     */       
/* 576 */       appendWithQuotes(stringBuilder, str1);
/* 577 */       stringBuilder.append(UsageTrackerClient.separator);
/*     */       
/* 579 */       appendWithQuotes(stringBuilder, param1String2);
/* 580 */       stringBuilder.append(UsageTrackerClient.separator);
/*     */       
/* 582 */       stringBuilder.append(getRuntimeDetails());
/* 583 */       stringBuilder.append("\n");
/* 584 */       String str2 = limitString(stringBuilder.toString(), UsageTrackerClient.maxSize);
/* 585 */       if (this.truncated && !UsageTrackerClient.sendTruncated) {
/* 586 */         UsageTrackerClient.this.printVerbose("UsageTracker: length limit exceeded.");
/* 587 */         return null;
/*     */       } 
/* 589 */       return str2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String getRuntimeDetails() {
/* 598 */       synchronized (UsageTrackerClient.LOCK) {
/* 599 */         if (UsageTrackerClient.staticMessage == null) {
/* 600 */           StringBuilder stringBuilder1 = new StringBuilder();
/*     */ 
/*     */ 
/*     */           
/* 604 */           boolean bool = this.truncated;
/* 605 */           this.truncated = false;
/* 606 */           appendWithQuotes(stringBuilder1, UsageTrackerClient.javaHome);
/* 607 */           stringBuilder1.append(UsageTrackerClient.separator);
/* 608 */           appendWithQuotes(stringBuilder1, UsageTrackerClient.getPropertyPrivileged("java.version"));
/* 609 */           stringBuilder1.append(UsageTrackerClient.separator);
/* 610 */           appendWithQuotes(stringBuilder1, UsageTrackerClient.getPropertyPrivileged("java.vm.version"));
/* 611 */           stringBuilder1.append(UsageTrackerClient.separator);
/* 612 */           appendWithQuotes(stringBuilder1, UsageTrackerClient.getPropertyPrivileged("java.vendor"));
/* 613 */           stringBuilder1.append(UsageTrackerClient.separator);
/* 614 */           appendWithQuotes(stringBuilder1, UsageTrackerClient.getPropertyPrivileged("java.vm.vendor"));
/* 615 */           stringBuilder1.append(UsageTrackerClient.separator);
/* 616 */           appendWithQuotes(stringBuilder1, UsageTrackerClient.getPropertyPrivileged("os.name"));
/* 617 */           stringBuilder1.append(UsageTrackerClient.separator);
/* 618 */           appendWithQuotes(stringBuilder1, UsageTrackerClient.getPropertyPrivileged("os.arch"));
/* 619 */           stringBuilder1.append(UsageTrackerClient.separator);
/* 620 */           appendWithQuotes(stringBuilder1, UsageTrackerClient.getPropertyPrivileged("os.version"));
/* 621 */           stringBuilder1.append(UsageTrackerClient.separator);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 627 */           List<String> list = getInputArguments();
/* 628 */           StringBuilder stringBuilder2 = new StringBuilder();
/* 629 */           for (String str : list) {
/* 630 */             stringBuilder2.append(addQuotesFor(str, " ", UsageTrackerClient.innerQuote));
/* 631 */             stringBuilder2.append(' ');
/*     */           } 
/* 633 */           appendWithQuotes(stringBuilder1, stringBuilder2.toString());
/* 634 */           stringBuilder1.append(UsageTrackerClient.separator);
/* 635 */           appendWithQuotes(stringBuilder1, UsageTrackerClient.getPropertyPrivileged("java.class.path"));
/* 636 */           stringBuilder1.append(UsageTrackerClient.separator);
/*     */ 
/*     */ 
/*     */           
/* 640 */           StringBuilder stringBuilder3 = new StringBuilder();
/* 641 */           for (String str : UsageTrackerClient.additionalProperties) {
/* 642 */             stringBuilder3.append(str.trim());
/* 643 */             stringBuilder3.append("=");
/* 644 */             stringBuilder3.append(addQuotesFor(UsageTrackerClient.getPropertyPrivileged(str.trim()), " ", UsageTrackerClient.innerQuote));
/* 645 */             stringBuilder3.append(" ");
/*     */           } 
/* 647 */           appendWithQuotes(stringBuilder1, stringBuilder3.toString());
/*     */ 
/*     */           
/* 650 */           UsageTrackerClient.staticMessage = stringBuilder1.toString();
/* 651 */           UsageTrackerClient.staticMessageIsTruncated = this.truncated;
/* 652 */           this.truncated = bool | UsageTrackerClient.staticMessageIsTruncated;
/*     */         }
/*     */         else {
/*     */           
/* 656 */           this.truncated |= UsageTrackerClient.staticMessageIsTruncated;
/*     */         } 
/* 658 */         return UsageTrackerClient.staticMessage;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void appendWithQuotes(StringBuilder param1StringBuilder, String param1String) {
/* 669 */       param1StringBuilder.append(UsageTrackerClient.quote);
/* 670 */       param1String = limitString(param1String, UsageTrackerClient.maxFieldSize);
/* 671 */       param1String = param1String.replace(UsageTrackerClient.quote, UsageTrackerClient.quote + UsageTrackerClient.quote);
/* 672 */       if (!param1String.isEmpty()) {
/* 673 */         param1StringBuilder.append(param1String);
/*     */       } else {
/* 675 */         param1StringBuilder.append(" ");
/*     */       } 
/* 677 */       param1StringBuilder.append(UsageTrackerClient.quote);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String addQuotesFor(String param1String1, String param1String2, String param1String3) {
/* 689 */       if (param1String1 == null) {
/* 690 */         return param1String1;
/*     */       }
/* 692 */       param1String1 = param1String1.replace(param1String3, param1String3 + param1String3);
/* 693 */       if (param1String1.indexOf(param1String2) >= 0) {
/* 694 */         param1String1 = param1String3 + param1String1 + param1String3;
/*     */       }
/* 696 */       return param1String1;
/*     */     }
/*     */     
/*     */     private List<String> getInputArguments() {
/* 700 */       return AccessController.<List<String>>doPrivileged(new PrivilegedAction<List<String>>() {
/*     */             public List<String> run() {
/*     */               try {
/* 703 */                 Class<?> clazz = Class.forName("java.lang.management.ManagementFactory", true, null);
/* 704 */                 Method method = clazz.getMethod("getRuntimeMXBean", (Class[])null);
/* 705 */                 Object object = method.invoke(null, (Object[])null);
/* 706 */                 clazz = Class.forName("java.lang.management.RuntimeMXBean", true, null);
/* 707 */                 method = clazz.getMethod("getInputArguments", (Class[])null);
/*     */                 
/* 709 */                 return (List)method.invoke(object, (Object[])null);
/*     */               }
/* 711 */               catch (ClassNotFoundException classNotFoundException) {
/* 712 */                 return Collections.singletonList("n/a");
/* 713 */               } catch (NoSuchMethodException noSuchMethodException) {
/* 714 */                 throw new AssertionError(noSuchMethodException);
/* 715 */               } catch (IllegalAccessException illegalAccessException) {
/* 716 */                 throw new AssertionError(illegalAccessException);
/* 717 */               } catch (InvocationTargetException invocationTargetException) {
/* 718 */                 throw new AssertionError(invocationTargetException.getCause());
/*     */               } 
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void sendDatagram(String param1String) {
/* 733 */       UsageTrackerClient.this.printDebug("UsageTracker: sendDatagram");
/* 734 */       try (DatagramSocket null = new DatagramSocket()) {
/* 735 */         byte[] arrayOfByte = param1String.getBytes("UTF-8");
/* 736 */         if (arrayOfByte.length > datagramSocket.getSendBufferSize()) {
/* 737 */           UsageTrackerClient.this.printVerbose("UsageTracker: message truncated for Datagram.");
/*     */         }
/* 739 */         UsageTrackerClient.this.printDebug("UsageTracker: host=" + UsageTrackerClient.datagramHost + ", port=" + UsageTrackerClient.datagramPort);
/* 740 */         UsageTrackerClient.this.printDebug("UsageTracker: SendBufferSize = " + datagramSocket.getSendBufferSize());
/* 741 */         UsageTrackerClient.this.printDebug("UsageTracker: packet length  = " + arrayOfByte.length);
/* 742 */         InetAddress inetAddress = InetAddress.getByName(UsageTrackerClient.datagramHost);
/*     */ 
/*     */         
/* 745 */         DatagramPacket datagramPacket = new DatagramPacket(arrayOfByte, (arrayOfByte.length > datagramSocket.getSendBufferSize()) ? datagramSocket.getSendBufferSize() : arrayOfByte.length, inetAddress, UsageTrackerClient.datagramPort);
/* 746 */         datagramSocket.send(datagramPacket);
/* 747 */         UsageTrackerClient.this.printVerbose("UsageTracker: done sending to UDP.");
/* 748 */         UsageTrackerClient.this.printDebug("UsageTracker: sent size = " + datagramPacket.getLength());
/* 749 */       } catch (Throwable throwable) {
/* 750 */         UsageTrackerClient.this.printVerbose("UsageTracker: error in sendDatagram: " + throwable);
/* 751 */         UsageTrackerClient.this.printDebugStackTrace(throwable);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void sendToFile(String param1String) {
/* 756 */       UsageTrackerClient.this.printDebug("UsageTracker: sendToFile");
/* 757 */       File file = new File(UsageTrackerClient.fullLogFilename);
/* 758 */       if (UsageTrackerClient.logFileMaxSize >= 0L && 
/* 759 */         file.length() >= UsageTrackerClient.logFileMaxSize) {
/* 760 */         UsageTrackerClient.this.printVerbose("UsageTracker: log file size exceeds maximum.");
/*     */         
/*     */         return;
/*     */       } 
/* 764 */       synchronized (UsageTrackerClient.LOCK) {
/* 765 */         try(FileOutputStream null = new FileOutputStream(file, true); 
/* 766 */             OutputStreamWriter null = new OutputStreamWriter(fileOutputStream, "UTF-8")) {
/* 767 */           outputStreamWriter.write(param1String, 0, param1String.length());
/* 768 */           UsageTrackerClient.this.printVerbose("UsageTracker: done sending to file.");
/* 769 */           UsageTrackerClient.this.printDebug("UsageTracker: " + UsageTrackerClient.fullLogFilename);
/* 770 */         } catch (Throwable throwable) {
/* 771 */           UsageTrackerClient.this.printVerbose("UsageTracker: error in sending to file.");
/* 772 */           UsageTrackerClient.this.printDebugStackTrace(throwable);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void run() {
/* 778 */       if (this.runAsync) {
/* 779 */         UsageTrackerClient.this.setupAndTimestamp(this.timestamp);
/* 780 */         UsageTrackerClient.this.printVerbose("UsageTracker: running asynchronous.");
/*     */       } 
/* 782 */       if (UsageTrackerClient.enabled) {
/* 783 */         UsageTrackerClient.this.printDebug("UsageTrackerRunnable.run: " + this.callerName + ", javaCommand: " + this.javaCommand);
/* 784 */         String str = buildMessage(this.callerName, this.javaCommand, this.timestamp);
/*     */         
/* 786 */         if (str != null) {
/* 787 */           if (UsageTrackerClient.datagramHost != null && UsageTrackerClient.datagramPort > 0) {
/* 788 */             sendDatagram(str);
/*     */           }
/* 790 */           if (UsageTrackerClient.fullLogFilename != null) {
/* 791 */             sendToFile(str);
/*     */           }
/*     */         } else {
/* 794 */           UsageTrackerClient.this.printVerbose("UsageTracker: length limit exceeded.");
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\su\\usagetracker\UsageTrackerClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */