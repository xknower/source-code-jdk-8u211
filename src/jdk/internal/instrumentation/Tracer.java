/*     */ package jdk.internal.instrumentation;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import sun.misc.VM;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Tracer
/*     */ {
/*     */   private final class InstrumentationData
/*     */   {
/*     */     Class<?> instrumentation;
/*     */     Class<?> target;
/*     */     Logger logger;
/*     */     
/*     */     private InstrumentationData() {}
/*     */   }
/* 148 */   private final List<InstrumentationData> items = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 154 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 157 */             System.loadLibrary("bci");
/* 158 */             return null;
/*     */           }
/*     */         },  (AccessControlContext)null, new Permission[] { new RuntimePermission("loadLibrary.bci") });
/* 161 */   } private static final Tracer singleton = new Tracer(); static {
/* 162 */     init();
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
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static Tracer getInstance() {
/* 178 */     Class<?> clazz = Reflection.getCallerClass();
/* 179 */     if (!VM.isSystemDomainLoader(clazz.getClassLoader()))
/* 180 */       throw new SecurityException("Only classes in the system domain can get a Tracer instance"); 
/* 181 */     return singleton;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addInstrumentations(List<Class<?>> paramList, Logger paramLogger) throws ClassNotFoundException {
/* 198 */     if (paramLogger == null) {
/* 199 */       throw new IllegalArgumentException("logger can't be null");
/*     */     }
/* 201 */     ArrayList<Class<?>> arrayList = new ArrayList();
/* 202 */     for (Class<?> clazz : paramList) {
/*     */       
/* 204 */       InstrumentationTarget instrumentationTarget = (InstrumentationTarget)clazz.getAnnotation(InstrumentationTarget.class);
/* 205 */       InstrumentationData instrumentationData = new InstrumentationData();
/* 206 */       instrumentationData.instrumentation = clazz;
/* 207 */       instrumentationData.target = Class.forName(instrumentationTarget.value(), true, clazz
/* 208 */           .getClassLoader());
/* 209 */       instrumentationData.logger = paramLogger;
/* 210 */       arrayList.add(instrumentationData.target);
/* 211 */       this.items.add(instrumentationData);
/*     */     } 
/* 213 */     retransformClasses0((Class[])arrayList.<Class<?>[]>toArray((Class<?>[][])new Class[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] transform(Class<?> paramClass, byte[] paramArrayOfbyte) {
/* 218 */     byte[] arrayOfByte = paramArrayOfbyte;
/* 219 */     for (InstrumentationData instrumentationData : this.items) {
/* 220 */       if (instrumentationData.target.equals(paramClass)) {
/*     */         try {
/* 222 */           instrumentationData.logger.trace("Processing instrumentation class: " + instrumentationData.instrumentation);
/*     */ 
/*     */           
/* 225 */           arrayOfByte = (new ClassInstrumentation(instrumentationData.instrumentation, paramClass.getName(), arrayOfByte, instrumentationData.logger)).getNewBytes();
/* 226 */         } catch (Throwable throwable) {
/* 227 */           instrumentationData.logger
/* 228 */             .error("Failure during class instrumentation:", throwable);
/*     */         } 
/*     */       }
/*     */     } 
/* 232 */     if (arrayOfByte == paramArrayOfbyte) {
/* 233 */       return null;
/*     */     }
/* 235 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] retransformCallback(Class<?> paramClass, byte[] paramArrayOfbyte) {
/* 244 */     return singleton.transform(paramClass, paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   private static native void retransformClasses0(Class<?>[] paramArrayOfClass);
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\internal\instrumentation\Tracer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */