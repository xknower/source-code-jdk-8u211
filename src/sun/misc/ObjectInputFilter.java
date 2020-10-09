/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.SerializablePermission;
/*     */ import java.security.AccessController;
/*     */ import java.security.Security;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface ObjectInputFilter
/*     */ {
/*     */   Status checkInput(FilterInfo paramFilterInfo);
/*     */   
/*     */   public enum Status
/*     */   {
/* 178 */     UNDECIDED,
/*     */ 
/*     */ 
/*     */     
/* 182 */     ALLOWED,
/*     */ 
/*     */ 
/*     */     
/* 186 */     REJECTED;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface FilterInfo
/*     */   {
/*     */     Class<?> serialClass();
/*     */ 
/*     */ 
/*     */     
/*     */     long arrayLength();
/*     */ 
/*     */     
/*     */     long depth();
/*     */ 
/*     */     
/*     */     long references();
/*     */ 
/*     */     
/*     */     long streamBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Config
/*     */   {
/* 212 */     private static final Object serialFilterLock = new Object();
/*     */     
/*     */     private static final PlatformLogger configLog;
/*     */     
/*     */     private static final String SERIAL_FILTER_PROPNAME = "jdk.serialFilter";
/*     */     
/*     */     private static final ObjectInputFilter configuredFilter;
/*     */     
/*     */     private static ObjectInputFilter serialFilter;
/*     */     
/*     */     static void filterLog(PlatformLogger.Level param1Level, String param1String, Object... param1VarArgs) {
/* 223 */       if (configLog != null) {
/* 224 */         if (PlatformLogger.Level.INFO.equals(param1Level)) {
/* 225 */           configLog.info(param1String, param1VarArgs);
/* 226 */         } else if (PlatformLogger.Level.WARNING.equals(param1Level)) {
/* 227 */           configLog.warning(param1String, param1VarArgs);
/*     */         } else {
/* 229 */           configLog.severe(param1String, param1VarArgs);
/*     */         } 
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 249 */       configuredFilter = AccessController.<ObjectInputFilter>doPrivileged(() -> {
/*     */             String str = System.getProperty("jdk.serialFilter");
/*     */             if (str == null) {
/*     */               str = Security.getProperty("jdk.serialFilter");
/*     */             }
/*     */             if (str != null) {
/*     */               PlatformLogger platformLogger = PlatformLogger.getLogger("java.io.serialization");
/*     */               platformLogger.info("Creating serialization filter from {0}", new Object[] { str });
/*     */               try {
/*     */                 return createFilter(str);
/* 259 */               } catch (RuntimeException runtimeException) {
/*     */                 platformLogger.warning("Error configuring filter: {0}", runtimeException);
/*     */               } 
/*     */             } 
/*     */             return null;
/*     */           });
/* 265 */       configLog = (configuredFilter != null) ? PlatformLogger.getLogger("java.io.serialization") : null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 271 */       serialFilter = configuredFilter;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static ObjectInputFilter getObjectInputFilter(ObjectInputStream param1ObjectInputStream) {
/* 280 */       Objects.requireNonNull(param1ObjectInputStream, "inputStream");
/* 281 */       return SharedSecrets.getJavaOISAccess().getObjectInputFilter(param1ObjectInputStream);
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
/*     */ 
/*     */     
/*     */     public static void setObjectInputFilter(ObjectInputStream param1ObjectInputStream, ObjectInputFilter param1ObjectInputFilter) {
/* 295 */       Objects.requireNonNull(param1ObjectInputStream, "inputStream");
/* 296 */       SharedSecrets.getJavaOISAccess().setObjectInputFilter(param1ObjectInputStream, param1ObjectInputFilter);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static ObjectInputFilter getSerialFilter() {
/* 305 */       synchronized (serialFilterLock) {
/* 306 */         return serialFilter;
/*     */       } 
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
/*     */     public static void setSerialFilter(ObjectInputFilter param1ObjectInputFilter) {
/* 319 */       Objects.requireNonNull(param1ObjectInputFilter, "filter");
/* 320 */       SecurityManager securityManager = System.getSecurityManager();
/* 321 */       if (securityManager != null) {
/* 322 */         securityManager.checkPermission(new SerializablePermission("serialFilter"));
/*     */       }
/* 324 */       synchronized (serialFilterLock) {
/* 325 */         if (serialFilter != null) {
/* 326 */           throw new IllegalStateException("Serial filter can only be set once");
/*     */         }
/* 328 */         serialFilter = param1ObjectInputFilter;
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static ObjectInputFilter createFilter(String param1String) {
/* 381 */       Objects.requireNonNull(param1String, "pattern");
/* 382 */       return Global.createFilter(param1String, true);
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
/*     */     public static ObjectInputFilter createFilter2(String param1String) {
/* 394 */       Objects.requireNonNull(param1String, "pattern");
/* 395 */       return Global.createFilter(param1String, false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static final class Global
/*     */       implements ObjectInputFilter
/*     */     {
/*     */       private final String pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private final List<Function<Class<?>, ObjectInputFilter.Status>> filters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private long maxStreamBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private long maxDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private long maxReferences;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private long maxArrayLength;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private final boolean checkComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       static ObjectInputFilter createFilter(String param2String, boolean param2Boolean) {
/* 446 */         Global global = new Global(param2String, param2Boolean);
/* 447 */         return global.isEmpty() ? null : global;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private Global(String param2String, boolean param2Boolean) {
/* 459 */         this.pattern = param2String;
/* 460 */         this.checkComponentType = param2Boolean;
/*     */         
/* 462 */         this.maxArrayLength = Long.MAX_VALUE;
/* 463 */         this.maxDepth = Long.MAX_VALUE;
/* 464 */         this.maxReferences = Long.MAX_VALUE;
/* 465 */         this.maxStreamBytes = Long.MAX_VALUE;
/*     */         
/* 467 */         String[] arrayOfString = param2String.split(";");
/* 468 */         this.filters = new ArrayList<>(arrayOfString.length);
/* 469 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 470 */           String str = arrayOfString[b];
/* 471 */           int i = str.length();
/* 472 */           if (i != 0)
/*     */           {
/*     */             
/* 475 */             if (!parseLimit(str)) {
/*     */ 
/*     */ 
/*     */               
/* 479 */               boolean bool = (str.charAt(0) == '!') ? true : false;
/*     */               
/* 481 */               if (str.indexOf('/') >= 0) {
/* 482 */                 throw new IllegalArgumentException("invalid character \"/\" in: \"" + param2String + "\"");
/*     */               }
/*     */               
/* 485 */               if (str.endsWith("*")) {
/*     */                 
/* 487 */                 if (str.endsWith(".*")) {
/*     */                   
/* 489 */                   String str1 = str.substring(bool ? 1 : 0, i - 1);
/* 490 */                   if (str1.length() < 2) {
/* 491 */                     throw new IllegalArgumentException("package missing in: \"" + param2String + "\"");
/*     */                   }
/* 493 */                   if (bool) {
/*     */                     
/* 495 */                     this.filters.add(param2Class -> matchesPackage(param2Class, param2String) ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } else {
/*     */                     
/* 498 */                     this.filters.add(param2Class -> matchesPackage(param2Class, param2String) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } 
/* 500 */                 } else if (str.endsWith(".**")) {
/*     */                   
/* 502 */                   String str1 = str.substring(bool ? 1 : 0, i - 2);
/* 503 */                   if (str1.length() < 2) {
/* 504 */                     throw new IllegalArgumentException("package missing in: \"" + param2String + "\"");
/*     */                   }
/* 506 */                   if (bool) {
/*     */                     
/* 508 */                     this.filters.add(param2Class -> param2Class.getName().startsWith(param2String) ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } else {
/*     */                     
/* 511 */                     this.filters.add(param2Class -> param2Class.getName().startsWith(param2String) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } 
/*     */                 } else {
/*     */                   
/* 515 */                   String str1 = str.substring(bool ? 1 : 0, i - 1);
/* 516 */                   if (bool) {
/*     */                     
/* 518 */                     this.filters.add(param2Class -> param2Class.getName().startsWith(param2String) ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } else {
/*     */                     
/* 521 */                     this.filters.add(param2Class -> param2Class.getName().startsWith(param2String) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.UNDECIDED);
/*     */                   } 
/*     */                 } 
/*     */               } else {
/* 525 */                 String str1 = str.substring(bool ? 1 : 0);
/* 526 */                 if (str1.isEmpty()) {
/* 527 */                   throw new IllegalArgumentException("class or package missing in: \"" + param2String + "\"");
/*     */                 }
/*     */                 
/* 530 */                 if (bool) {
/*     */                   
/* 532 */                   this.filters.add(param2Class -> param2Class.getName().equals(param2String) ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.UNDECIDED);
/*     */                 } else {
/*     */                   
/* 535 */                   this.filters.add(param2Class -> param2Class.getName().equals(param2String) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.UNDECIDED);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private boolean isEmpty() {
/* 547 */         return (this.filters.isEmpty() && this.maxArrayLength == Long.MAX_VALUE && this.maxDepth == Long.MAX_VALUE && this.maxReferences == Long.MAX_VALUE && this.maxStreamBytes == Long.MAX_VALUE);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private boolean parseLimit(String param2String) {
/* 563 */         int i = param2String.indexOf('=');
/* 564 */         if (i < 0)
/*     */         {
/* 566 */           return false;
/*     */         }
/* 568 */         String str = param2String.substring(i + 1);
/* 569 */         if (param2String.startsWith("maxdepth=")) {
/* 570 */           this.maxDepth = parseValue(str);
/* 571 */         } else if (param2String.startsWith("maxarray=")) {
/* 572 */           this.maxArrayLength = parseValue(str);
/* 573 */         } else if (param2String.startsWith("maxrefs=")) {
/* 574 */           this.maxReferences = parseValue(str);
/* 575 */         } else if (param2String.startsWith("maxbytes=")) {
/* 576 */           this.maxStreamBytes = parseValue(str);
/*     */         } else {
/* 578 */           throw new IllegalArgumentException("unknown limit: " + param2String.substring(0, i));
/*     */         } 
/* 580 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private static long parseValue(String param2String) throws IllegalArgumentException {
/* 591 */         long l = Long.parseLong(param2String);
/* 592 */         if (l < 0L) {
/* 593 */           throw new IllegalArgumentException("negative limit: " + param2String);
/*     */         }
/* 595 */         return l;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public ObjectInputFilter.Status checkInput(ObjectInputFilter.FilterInfo param2FilterInfo) {
/* 603 */         if (param2FilterInfo.references() < 0L || param2FilterInfo
/* 604 */           .depth() < 0L || param2FilterInfo
/* 605 */           .streamBytes() < 0L || param2FilterInfo
/* 606 */           .references() > this.maxReferences || param2FilterInfo
/* 607 */           .depth() > this.maxDepth || param2FilterInfo
/* 608 */           .streamBytes() > this.maxStreamBytes) {
/* 609 */           return ObjectInputFilter.Status.REJECTED;
/*     */         }
/*     */         
/* 612 */         Class<?> clazz = param2FilterInfo.serialClass();
/* 613 */         if (clazz != null) {
/* 614 */           if (clazz.isArray()) {
/* 615 */             if (param2FilterInfo.arrayLength() >= 0L && param2FilterInfo.arrayLength() > this.maxArrayLength)
/*     */             {
/* 617 */               return ObjectInputFilter.Status.REJECTED;
/*     */             }
/* 619 */             if (!this.checkComponentType)
/*     */             {
/* 621 */               return ObjectInputFilter.Status.UNDECIDED;
/*     */             }
/*     */             
/*     */             do {
/* 625 */               clazz = clazz.getComponentType();
/* 626 */             } while (clazz.isArray());
/*     */           } 
/*     */           
/* 629 */           if (clazz.isPrimitive())
/*     */           {
/* 631 */             return ObjectInputFilter.Status.UNDECIDED;
/*     */           }
/*     */           
/* 634 */           Class<?> clazz1 = clazz;
/*     */ 
/*     */ 
/*     */           
/* 638 */           Optional<ObjectInputFilter.Status> optional = this.filters.stream().map(param2Function -> (ObjectInputFilter.Status)param2Function.apply(param2Class)).filter(param2Status -> (param2Status != ObjectInputFilter.Status.UNDECIDED)).findFirst();
/* 639 */           return optional.orElse(ObjectInputFilter.Status.UNDECIDED);
/*     */         } 
/*     */         
/* 642 */         return ObjectInputFilter.Status.UNDECIDED;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private static boolean matchesPackage(Class<?> param2Class, String param2String) {
/* 654 */         String str = param2Class.getName();
/* 655 */         return (str.startsWith(param2String) && str.lastIndexOf('.') == param2String.length() - 1);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public String toString() {
/* 664 */         return this.pattern;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\ObjectInputFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */