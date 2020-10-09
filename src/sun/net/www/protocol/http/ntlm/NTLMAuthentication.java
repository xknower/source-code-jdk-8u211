/*     */ package sun.net.www.protocol.http.ntlm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.URL;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.net.NetProperties;
/*     */ import sun.net.www.HeaderParser;
/*     */ import sun.net.www.protocol.http.AuthScheme;
/*     */ import sun.net.www.protocol.http.AuthenticationInfo;
/*     */ import sun.net.www.protocol.http.HttpURLConnection;
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
/*     */ public class NTLMAuthentication
/*     */   extends AuthenticationInfo
/*     */ {
/*     */   private static final long serialVersionUID = 100L;
/*  50 */   private static final NTLMAuthenticationCallback NTLMAuthCallback = NTLMAuthenticationCallback.getNTLMAuthenticationCallback();
/*     */   
/*     */   private String hostname;
/*     */ 
/*     */   
/*     */   enum TransparentAuth
/*     */   {
/*  57 */     DISABLED,
/*  58 */     TRUSTED_HOSTS,
/*  59 */     ALL_HOSTS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static String defaultDomain = AccessController.<String>doPrivileged(new GetPropertyAction("http.auth.ntlm.domain", "domain")); private static final boolean ntlmCache; private static final TransparentAuth authMode;
/*     */   
/*     */   static {
/*  68 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.ntlm.cache", "true"));
/*     */     
/*  70 */     ntlmCache = Boolean.parseBoolean(str1);
/*  71 */     String str2 = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/*  74 */             return NetProperties.get("jdk.http.ntlm.transparentAuth");
/*     */           }
/*     */         });
/*     */     
/*  78 */     if ("trustedHosts".equalsIgnoreCase(str2)) {
/*  79 */       authMode = TransparentAuth.TRUSTED_HOSTS;
/*  80 */     } else if ("allHosts".equalsIgnoreCase(str2)) {
/*  81 */       authMode = TransparentAuth.ALL_HOSTS;
/*     */     } else {
/*  83 */       authMode = TransparentAuth.DISABLED;
/*     */     } 
/*     */   }
/*     */   String username; String ntdomain; String password;
/*     */   private void init0() {
/*  88 */     this.hostname = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/*     */             String str;
/*     */             try {
/*  93 */               str = InetAddress.getLocalHost().getHostName().toUpperCase();
/*  94 */             } catch (UnknownHostException unknownHostException) {
/*  95 */               str = "localhost";
/*     */             } 
/*  97 */             return str;
/*     */           }
/*     */         });
/* 100 */     int i = this.hostname.indexOf('.');
/* 101 */     if (i != -1) {
/* 102 */       this.hostname = this.hostname.substring(0, i);
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
/*     */   
/*     */   public NTLMAuthentication(boolean paramBoolean, URL paramURL, PasswordAuthentication paramPasswordAuthentication) {
/* 117 */     super(paramBoolean ? 112 : 115, AuthScheme.NTLM, paramURL, "");
/*     */ 
/*     */ 
/*     */     
/* 121 */     init(paramPasswordAuthentication);
/*     */   }
/*     */   
/*     */   private void init(PasswordAuthentication paramPasswordAuthentication) {
/* 125 */     this.pw = paramPasswordAuthentication;
/* 126 */     if (paramPasswordAuthentication != null) {
/* 127 */       String str = paramPasswordAuthentication.getUserName();
/* 128 */       int i = str.indexOf('\\');
/* 129 */       if (i == -1) {
/* 130 */         this.username = str;
/* 131 */         this.ntdomain = defaultDomain;
/*     */       } else {
/* 133 */         this.ntdomain = str.substring(0, i).toUpperCase();
/* 134 */         this.username = str.substring(i + 1);
/*     */       } 
/* 136 */       this.password = new String(paramPasswordAuthentication.getPassword());
/*     */     } else {
/*     */       
/* 139 */       this.username = null;
/* 140 */       this.ntdomain = null;
/* 141 */       this.password = null;
/*     */     } 
/* 143 */     init0();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NTLMAuthentication(boolean paramBoolean, String paramString, int paramInt, PasswordAuthentication paramPasswordAuthentication) {
/* 151 */     super(paramBoolean ? 112 : 115, AuthScheme.NTLM, paramString, paramInt, "");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     init(paramPasswordAuthentication);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean useAuthCache() {
/* 161 */     return (ntlmCache && super.useAuthCache());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsPreemptiveAuthorization() {
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean supportsTransparentAuth() {
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTrustedSite(URL paramURL) {
/* 184 */     if (NTLMAuthCallback != null) {
/* 185 */       return NTLMAuthCallback.isTrustedSite(paramURL);
/*     */     }
/* 187 */     switch (authMode) {
/*     */       case TRUSTED_HOSTS:
/* 189 */         return isTrustedSite(paramURL.toString());
/*     */       case ALL_HOSTS:
/* 191 */         return true;
/*     */     } 
/* 193 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderValue(URL paramURL, String paramString) {
/* 204 */     throw new RuntimeException("getHeaderValue not supported");
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
/*     */   public boolean isAuthorizationStale(String paramString) {
/* 217 */     return false;
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
/*     */   public synchronized boolean setHeaders(HttpURLConnection paramHttpURLConnection, HeaderParser paramHeaderParser, String paramString) {
/*     */     try {
/* 232 */       NTLMAuthSequence nTLMAuthSequence = (NTLMAuthSequence)paramHttpURLConnection.authObj();
/* 233 */       if (nTLMAuthSequence == null) {
/* 234 */         nTLMAuthSequence = new NTLMAuthSequence(this.username, this.password, this.ntdomain);
/* 235 */         paramHttpURLConnection.authObj(nTLMAuthSequence);
/*     */       } 
/* 237 */       String str = "NTLM " + nTLMAuthSequence.getAuthHeader((paramString.length() > 6) ? paramString.substring(5) : null);
/* 238 */       paramHttpURLConnection.setAuthenticationProperty(getHeaderName(), str);
/* 239 */       if (nTLMAuthSequence.isComplete()) {
/* 240 */         paramHttpURLConnection.authObj(null);
/*     */       }
/* 242 */       return true;
/* 243 */     } catch (IOException iOException) {
/* 244 */       paramHttpURLConnection.authObj(null);
/* 245 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   static native boolean isTrustedSite(String paramString);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\www\protocol\http\ntlm\NTLMAuthentication.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */