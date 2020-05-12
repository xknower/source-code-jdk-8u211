/*     */ package java.net;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultDatagramSocketImplFactory
/*     */ {
/*     */   private static final Class<?> prefixImplClass;
/*     */   private static float version;
/*     */   private static boolean preferIPv4Stack = false;
/*     */   private static final boolean useDualStackImpl;
/*     */   private static String exclBindProp;
/*     */   private static final boolean exclusiveBind;
/*     */   
/*     */   static {
/*  66 */     Class<?> clazz = null;
/*  67 */     boolean bool1 = false;
/*  68 */     boolean bool2 = true;
/*     */ 
/*     */     
/*  71 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/*  74 */             DefaultDatagramSocketImplFactory.version = 0.0F;
/*     */             try {
/*  76 */               DefaultDatagramSocketImplFactory.version = Float.parseFloat(System.getProperties()
/*  77 */                   .getProperty("os.version"));
/*  78 */               DefaultDatagramSocketImplFactory.preferIPv4Stack = Boolean.parseBoolean(
/*  79 */                   System.getProperties()
/*  80 */                   .getProperty("java.net.preferIPv4Stack"));
/*     */               
/*  82 */               DefaultDatagramSocketImplFactory.exclBindProp = System.getProperty("sun.net.useExclusiveBind");
/*     */             }
/*  84 */             catch (NumberFormatException numberFormatException) {
/*  85 */               assert false : numberFormatException;
/*     */             } 
/*  87 */             return null;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  92 */     if (version >= 6.0D && !preferIPv4Stack) {
/*  93 */       bool1 = true;
/*     */     }
/*  95 */     if (exclBindProp != null) {
/*     */ 
/*     */       
/*  98 */       bool2 = (exclBindProp.length() == 0) ? true : Boolean.parseBoolean(exclBindProp);
/*  99 */     } else if (version < 6.0D) {
/* 100 */       bool2 = false;
/*     */     } 
/*     */ 
/*     */     
/* 104 */     String str = null;
/*     */     try {
/* 106 */       str = AccessController.<String>doPrivileged(new GetPropertyAction("impl.prefix", null));
/*     */       
/* 108 */       if (str != null)
/* 109 */         clazz = Class.forName("java.net." + str + "DatagramSocketImpl"); 
/* 110 */     } catch (Exception exception) {
/* 111 */       System.err.println("Can't find class: java.net." + str + "DatagramSocketImpl: check impl.prefix property");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 116 */     prefixImplClass = clazz;
/* 117 */     useDualStackImpl = bool1;
/* 118 */     exclusiveBind = bool2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static DatagramSocketImpl createDatagramSocketImpl(boolean paramBoolean) throws SocketException {
/* 129 */     if (prefixImplClass != null) {
/*     */       try {
/* 131 */         return (DatagramSocketImpl)prefixImplClass.newInstance();
/* 132 */       } catch (Exception exception) {
/* 133 */         throw new SocketException("can't instantiate DatagramSocketImpl");
/*     */       } 
/*     */     }
/* 136 */     if (useDualStackImpl && !paramBoolean) {
/* 137 */       return new DualStackPlainDatagramSocketImpl(exclusiveBind);
/*     */     }
/* 139 */     return new TwoStacksPlainDatagramSocketImpl((exclusiveBind && !paramBoolean));
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\DefaultDatagramSocketImplFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */