/*     */ package sun.net.dns;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolverConfigurationImpl
/*     */   extends ResolverConfiguration
/*     */ {
/*  40 */   private static Object lock = new Object();
/*     */ 
/*     */   
/*     */   private final ResolverConfiguration.Options opts;
/*     */ 
/*     */   
/*     */   private static boolean changed = false;
/*     */ 
/*     */   
/*  49 */   private static long lastRefresh = -1L;
/*     */ 
/*     */   
/*     */   private static final int TIMEOUT = 120000;
/*     */ 
/*     */   
/*     */   private static String os_searchlist;
/*     */ 
/*     */   
/*     */   private static String os_nameservers;
/*     */   
/*     */   private static LinkedList<String> searchlist;
/*     */   
/*     */   private static LinkedList<String> nameservers;
/*     */ 
/*     */   
/*     */   private LinkedList<String> stringToList(String paramString) {
/*  66 */     LinkedList<String> linkedList = new LinkedList();
/*     */ 
/*     */     
/*  69 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ", ");
/*  70 */     while (stringTokenizer.hasMoreTokens()) {
/*  71 */       String str = stringTokenizer.nextToken();
/*  72 */       if (!linkedList.contains(str)) {
/*  73 */         linkedList.add(str);
/*     */       }
/*     */     } 
/*  76 */     return linkedList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadConfig() {
/*  82 */     assert Thread.holdsLock(lock);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     if (changed) {
/*  88 */       changed = false;
/*     */     }
/*  90 */     else if (lastRefresh >= 0L) {
/*  91 */       long l = System.currentTimeMillis();
/*  92 */       if (l - lastRefresh < 120000L) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     loadDNSconfig0();
/*     */     
/* 103 */     lastRefresh = System.currentTimeMillis();
/* 104 */     searchlist = stringToList(os_searchlist);
/* 105 */     nameservers = stringToList(os_nameservers);
/* 106 */     os_searchlist = null;
/* 107 */     os_nameservers = null;
/*     */   }
/*     */   
/*     */   ResolverConfigurationImpl() {
/* 111 */     this.opts = new OptionsImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> searchlist() {
/* 116 */     synchronized (lock) {
/* 117 */       loadConfig();
/*     */ 
/*     */       
/* 120 */       return (List<String>)searchlist.clone();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> nameservers() {
/* 126 */     synchronized (lock) {
/* 127 */       loadConfig();
/*     */ 
/*     */       
/* 130 */       return (List<String>)nameservers.clone();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ResolverConfiguration.Options options() {
/* 135 */     return this.opts;
/*     */   }
/*     */ 
/*     */   
/*     */   static class AddressChangeListener
/*     */     extends Thread
/*     */   {
/*     */     public void run() {
/*     */       while (true) {
/* 144 */         if (ResolverConfigurationImpl.notifyAddrChange0() != 0)
/*     */           return; 
/* 146 */         synchronized (ResolverConfigurationImpl.lock) {
/* 147 */           ResolverConfigurationImpl.changed = true;
/*     */         } 
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
/*     */   static {
/* 163 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 166 */             System.loadLibrary("net");
/* 167 */             return null;
/*     */           }
/*     */         });
/* 170 */     init0();
/*     */ 
/*     */     
/* 173 */     AddressChangeListener addressChangeListener = new AddressChangeListener();
/* 174 */     addressChangeListener.setDaemon(true);
/* 175 */     addressChangeListener.start();
/*     */   }
/*     */   
/*     */   static native void init0();
/*     */   
/*     */   static native void loadDNSconfig0();
/*     */   
/*     */   static native int notifyAddrChange0();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\dns\ResolverConfigurationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */