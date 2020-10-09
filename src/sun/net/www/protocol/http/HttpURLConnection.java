/*      */ package sun.net.www.protocol.http;
/*      */ 
/*      */ import java.io.FilterInputStream;
/*      */ import java.io.FilterOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.net.Authenticator;
/*      */ import java.net.CacheRequest;
/*      */ import java.net.CacheResponse;
/*      */ import java.net.CookieHandler;
/*      */ import java.net.HttpCookie;
/*      */ import java.net.HttpRetryException;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.PasswordAuthentication;
/*      */ import java.net.ProtocolException;
/*      */ import java.net.Proxy;
/*      */ import java.net.ProxySelector;
/*      */ import java.net.ResponseCache;
/*      */ import java.net.SocketPermission;
/*      */ import java.net.SocketTimeoutException;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.net.URLPermission;
/*      */ import java.net.UnknownHostException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permission;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TimeZone;
/*      */ import sun.misc.JavaNetHttpCookieAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.net.NetProperties;
/*      */ import sun.net.ProgressSource;
/*      */ import sun.net.www.HeaderParser;
/*      */ import sun.net.www.MessageHeader;
/*      */ import sun.net.www.ParseUtil;
/*      */ import sun.net.www.http.ChunkedOutputStream;
/*      */ import sun.net.www.http.HttpClient;
/*      */ import sun.net.www.http.PosterOutputStream;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ import sun.security.action.GetIntegerAction;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HttpURLConnection
/*      */   extends HttpURLConnection
/*      */ {
/*   93 */   static String HTTP_CONNECT = "CONNECT";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final String version;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String userAgent;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int defaultmaxRedirects = 20;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int maxRedirects;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final boolean validateProxy;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final boolean validateServer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final Set<String> disabledProxyingSchemes;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final Set<String> disabledTunnelingSchemes;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StreamingOutputStream strOutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String RETRY_MSG1 = "cannot retry due to proxy authentication, in streaming mode";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String RETRY_MSG2 = "cannot retry due to server authentication, in streaming mode";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String RETRY_MSG3 = "cannot retry due to redirection, in streaming mode";
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean enableESBuffer = false;
/*      */ 
/*      */ 
/*      */   
/*  159 */   private static int timeout4ESBuffer = 0;
/*      */ 
/*      */ 
/*      */   
/*  163 */   private static int bufSize4ES = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean allowRestrictedHeaders;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Set<String> restrictedHeaderSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  188 */   private static final String[] restrictedHeaders = new String[] { "Access-Control-Request-Headers", "Access-Control-Request-Method", "Connection", "Content-Length", "Content-Transfer-Encoding", "Host", "Keep-Alive", "Origin", "Trailer", "Transfer-Encoding", "Upgrade", "Via" };
/*      */   
/*      */   static final String httpVersion = "HTTP/1.1";
/*      */   
/*      */   static final String acceptString = "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2";
/*      */   
/*      */   private static final String[] EXCLUDE_HEADERS;
/*      */   
/*      */   private static final String[] EXCLUDE_HEADERS2;
/*      */   
/*      */   protected HttpClient http;
/*      */   
/*      */   protected Handler handler;
/*      */   
/*      */   protected Proxy instProxy;
/*      */   
/*      */   private CookieHandler cookieHandler;
/*      */   
/*      */   private final ResponseCache cacheHandler;
/*      */   
/*      */   protected CacheResponse cachedResponse;
/*      */   
/*      */   private MessageHeader cachedHeaders;
/*      */   private InputStream cachedInputStream;
/*      */   
/*      */   private static String getNetProperty(String paramString) {
/*  214 */     PrivilegedAction<String> privilegedAction = () -> NetProperties.get(paramString);
/*  215 */     return AccessController.<String>doPrivileged(privilegedAction);
/*      */   }
/*      */   
/*      */   private static Set<String> schemesListToSet(String paramString) {
/*  219 */     if (paramString == null || paramString.isEmpty()) {
/*  220 */       return Collections.emptySet();
/*      */     }
/*  222 */     HashSet<String> hashSet = new HashSet();
/*  223 */     String[] arrayOfString = paramString.split("\\s*,\\s*");
/*  224 */     for (String str : arrayOfString)
/*  225 */       hashSet.add(str.toLowerCase(Locale.ROOT)); 
/*  226 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  232 */     maxRedirects = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("http.maxRedirects", 20))).intValue();
/*  233 */     version = AccessController.<String>doPrivileged(new GetPropertyAction("java.version"));
/*      */     
/*  235 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("http.agent"));
/*      */     
/*  237 */     if (str1 == null) {
/*  238 */       str1 = "Java/" + version;
/*      */     } else {
/*  240 */       str1 = str1 + " Java/" + version;
/*      */     } 
/*  242 */     userAgent = str1;
/*      */ 
/*      */ 
/*      */     
/*  246 */     String str2 = getNetProperty("jdk.http.auth.tunneling.disabledSchemes");
/*  247 */     disabledTunnelingSchemes = schemesListToSet(str2);
/*  248 */     str2 = getNetProperty("jdk.http.auth.proxying.disabledSchemes");
/*  249 */     disabledProxyingSchemes = schemesListToSet(str2);
/*      */ 
/*      */ 
/*      */     
/*  253 */     validateProxy = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("http.auth.digest.validateProxy"))).booleanValue();
/*      */ 
/*      */     
/*  256 */     validateServer = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("http.auth.digest.validateServer"))).booleanValue();
/*      */ 
/*      */ 
/*      */     
/*  260 */     enableESBuffer = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.net.http.errorstream.enableBuffering"))).booleanValue();
/*      */ 
/*      */     
/*  263 */     timeout4ESBuffer = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.net.http.errorstream.timeout", 300))).intValue();
/*  264 */     if (timeout4ESBuffer <= 0) {
/*  265 */       timeout4ESBuffer = 300;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  270 */     bufSize4ES = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.net.http.errorstream.bufferSize", 4096))).intValue();
/*  271 */     if (bufSize4ES <= 0) {
/*  272 */       bufSize4ES = 4096;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  277 */     allowRestrictedHeaders = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.net.http.allowRestrictedHeaders"))).booleanValue();
/*  278 */     if (!allowRestrictedHeaders) {
/*  279 */       restrictedHeaderSet = new HashSet<>(restrictedHeaders.length);
/*  280 */       for (byte b = 0; b < restrictedHeaders.length; b++) {
/*  281 */         restrictedHeaderSet.add(restrictedHeaders[b].toLowerCase());
/*      */       }
/*      */     } else {
/*  284 */       restrictedHeaderSet = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  294 */     EXCLUDE_HEADERS = new String[] { "Proxy-Authorization", "Authorization" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  300 */     EXCLUDE_HEADERS2 = new String[] { "Proxy-Authorization", "Authorization", "Cookie", "Cookie2" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  431 */     logger = PlatformLogger.getLogger("sun.net.www.protocol.http.HttpURLConnection");
/*      */   }
/*      */   protected PrintStream ps = null; private InputStream errorStream = null; private boolean setUserCookies = true; private String userCookies = null; private String userCookies2 = null; @Deprecated
/*      */   private static HttpAuthenticator defaultAuth; private MessageHeader requests; private MessageHeader userHeaders; private boolean connecting = false; String domain; DigestAuthentication.Parameters digestparams; AuthenticationInfo currentProxyCredentials = null; AuthenticationInfo currentServerCredentials = null; boolean needToCheck = true; private boolean doingNTLM2ndStage = false; private boolean doingNTLMp2ndStage = false; private boolean tryTransparentNTLMServer = true; private boolean tryTransparentNTLMProxy = true; private boolean useProxyResponseCode = false; private Object authObj; boolean isUserServerAuth; boolean isUserProxyAuth; String serverAuthKey; String proxyAuthKey; protected ProgressSource pi; private MessageHeader responses; private InputStream inputStream = null; private PosterOutputStream poster = null; private boolean setRequests = false; private boolean failedOnce = false; private Exception rememberedException = null; private HttpClient reuseClient = null;
/*      */   public enum TunnelState {
/*      */     NONE, SETUP, TUNNELING; } private TunnelState tunnelState = TunnelState.NONE; private int connectTimeout = -1;
/*      */   private int readTimeout = -1;
/*      */   private SocketPermission socketPermission;
/*      */   private static final PlatformLogger logger;
/*      */   String requestURI;
/*      */   byte[] cdata;
/*      */   private static final String SET_COOKIE = "set-cookie";
/*      */   private static final String SET_COOKIE2 = "set-cookie2";
/*      */   private Map<String, List<String>> filteredHeaders;
/*      */   
/*      */   private static PasswordAuthentication privilegedRequestPasswordAuthentication(final String host, final InetAddress addr, final int port, final String protocol, final String prompt, final String scheme, final URL url, final Authenticator.RequestorType authType) {
/*  447 */     return AccessController.<PasswordAuthentication>doPrivileged(new PrivilegedAction<PasswordAuthentication>()
/*      */         {
/*      */           public PasswordAuthentication run() {
/*  450 */             if (HttpURLConnection.logger.isLoggable(PlatformLogger.Level.FINEST)) {
/*  451 */               HttpURLConnection.logger.finest("Requesting Authentication: host =" + host + " url = " + url);
/*      */             }
/*  453 */             PasswordAuthentication passwordAuthentication = Authenticator.requestPasswordAuthentication(host, addr, port, protocol, prompt, scheme, url, authType);
/*      */ 
/*      */             
/*  456 */             if (HttpURLConnection.logger.isLoggable(PlatformLogger.Level.FINEST)) {
/*  457 */               HttpURLConnection.logger.finest("Authentication returned: " + ((passwordAuthentication != null) ? passwordAuthentication.toString() : "null"));
/*      */             }
/*  459 */             return passwordAuthentication;
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private boolean isRestrictedHeader(String paramString1, String paramString2) {
/*  465 */     if (allowRestrictedHeaders) {
/*  466 */       return false;
/*      */     }
/*      */     
/*  469 */     paramString1 = paramString1.toLowerCase();
/*  470 */     if (restrictedHeaderSet.contains(paramString1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  476 */       if (paramString1.equals("connection") && paramString2.equalsIgnoreCase("close")) {
/*  477 */         return false;
/*      */       }
/*  479 */       return true;
/*  480 */     }  if (paramString1.startsWith("sec-")) {
/*  481 */       return true;
/*      */     }
/*  483 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isExternalMessageHeaderAllowed(String paramString1, String paramString2) {
/*  492 */     checkMessageHeader(paramString1, paramString2);
/*  493 */     if (!isRestrictedHeader(paramString1, paramString2)) {
/*  494 */       return true;
/*      */     }
/*  496 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static PlatformLogger getHttpLogger() {
/*  501 */     return logger;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object authObj() {
/*  506 */     return this.authObj;
/*      */   }
/*      */   
/*      */   public void authObj(Object paramObject) {
/*  510 */     this.authObj = paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkMessageHeader(String paramString1, String paramString2) {
/*  518 */     byte b = 10;
/*  519 */     int i = paramString1.indexOf(b);
/*  520 */     int j = paramString1.indexOf(':');
/*  521 */     if (i != -1 || j != -1) {
/*  522 */       throw new IllegalArgumentException("Illegal character(s) in message header field: " + paramString1);
/*      */     }
/*      */ 
/*      */     
/*  526 */     if (paramString2 == null) {
/*      */       return;
/*      */     }
/*      */     
/*  530 */     i = paramString2.indexOf(b);
/*  531 */     while (i != -1) {
/*  532 */       i++;
/*  533 */       if (i < paramString2.length()) {
/*  534 */         char c = paramString2.charAt(i);
/*  535 */         if (c == ' ' || c == '\t') {
/*      */           
/*  537 */           i = paramString2.indexOf(b, i);
/*      */           continue;
/*      */         } 
/*      */       } 
/*  541 */       throw new IllegalArgumentException("Illegal character(s) in message header value: " + paramString2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setRequestMethod(String paramString) throws ProtocolException {
/*  549 */     if (this.connecting) {
/*  550 */       throw new IllegalStateException("connect in progress");
/*      */     }
/*  552 */     super.setRequestMethod(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeRequests() throws IOException {
/*  564 */     if (this.http.usingProxy && tunnelState() != TunnelState.TUNNELING) {
/*  565 */       setPreemptiveProxyAuthentication(this.requests);
/*      */     }
/*  567 */     if (!this.setRequests) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  579 */       if (!this.failedOnce) {
/*  580 */         checkURLFile();
/*  581 */         this.requests.prepend(this.method + " " + getRequestURI() + " " + "HTTP/1.1", null);
/*      */       } 
/*      */       
/*  584 */       if (!getUseCaches()) {
/*  585 */         this.requests.setIfNotSet("Cache-Control", "no-cache");
/*  586 */         this.requests.setIfNotSet("Pragma", "no-cache");
/*      */       } 
/*  588 */       this.requests.setIfNotSet("User-Agent", userAgent);
/*  589 */       int i = this.url.getPort();
/*  590 */       String str1 = this.url.getHost();
/*  591 */       if (i != -1 && i != this.url.getDefaultPort()) {
/*  592 */         str1 = str1 + ":" + String.valueOf(i);
/*      */       }
/*  594 */       String str2 = this.requests.findValue("Host");
/*  595 */       if (str2 == null || (
/*  596 */         !str2.equalsIgnoreCase(str1) && !checkSetHost()))
/*      */       {
/*  598 */         this.requests.set("Host", str1);
/*      */       }
/*  600 */       this.requests.setIfNotSet("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  610 */       if (!this.failedOnce && this.http.getHttpKeepAliveSet()) {
/*  611 */         if (this.http.usingProxy && tunnelState() != TunnelState.TUNNELING) {
/*  612 */           this.requests.setIfNotSet("Proxy-Connection", "keep-alive");
/*      */         } else {
/*  614 */           this.requests.setIfNotSet("Connection", "keep-alive");
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  623 */         this.requests.setIfNotSet("Connection", "close");
/*      */       } 
/*      */       
/*  626 */       long l = getIfModifiedSince();
/*  627 */       if (l != 0L) {
/*  628 */         Date date = new Date(l);
/*      */ 
/*      */         
/*  631 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
/*      */         
/*  633 */         simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
/*  634 */         this.requests.setIfNotSet("If-Modified-Since", simpleDateFormat.format(date));
/*      */       } 
/*      */       
/*  637 */       AuthenticationInfo authenticationInfo = AuthenticationInfo.getServerAuth(this.url);
/*  638 */       if (authenticationInfo != null && authenticationInfo.supportsPreemptiveAuthorization()) {
/*      */         
/*  640 */         this.requests.setIfNotSet(authenticationInfo.getHeaderName(), authenticationInfo.getHeaderValue(this.url, this.method));
/*  641 */         this.currentServerCredentials = authenticationInfo;
/*      */       } 
/*      */       
/*  644 */       if (!this.method.equals("PUT") && (this.poster != null || streaming())) {
/*  645 */         this.requests.setIfNotSet("Content-type", "application/x-www-form-urlencoded");
/*      */       }
/*      */ 
/*      */       
/*  649 */       boolean bool = false;
/*      */       
/*  651 */       if (streaming()) {
/*  652 */         if (this.chunkLength != -1) {
/*  653 */           this.requests.set("Transfer-Encoding", "chunked");
/*  654 */           bool = true;
/*      */         }
/*  656 */         else if (this.fixedContentLengthLong != -1L) {
/*  657 */           this.requests.set("Content-Length", 
/*  658 */               String.valueOf(this.fixedContentLengthLong));
/*  659 */         } else if (this.fixedContentLength != -1) {
/*  660 */           this.requests.set("Content-Length", 
/*  661 */               String.valueOf(this.fixedContentLength));
/*      */         }
/*      */       
/*  664 */       } else if (this.poster != null) {
/*      */         
/*  666 */         synchronized (this.poster) {
/*      */           
/*  668 */           this.poster.close();
/*  669 */           this.requests.set("Content-Length", 
/*  670 */               String.valueOf(this.poster.size()));
/*      */         } 
/*      */       } 
/*      */       
/*  674 */       if (!bool && 
/*  675 */         this.requests.findValue("Transfer-Encoding") != null) {
/*  676 */         this.requests.remove("Transfer-Encoding");
/*  677 */         if (logger.isLoggable(PlatformLogger.Level.WARNING)) {
/*  678 */           logger.warning("use streaming mode for chunked encoding");
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  686 */       setCookieHeader();
/*      */       
/*  688 */       this.setRequests = true;
/*      */     } 
/*  690 */     if (logger.isLoggable(PlatformLogger.Level.FINE)) {
/*  691 */       logger.fine(this.requests.toString());
/*      */     }
/*  693 */     this.http.writeRequests(this.requests, this.poster, streaming());
/*  694 */     if (this.ps.checkError()) {
/*  695 */       String str = this.http.getProxyHostUsed();
/*  696 */       int i = this.http.getProxyPortUsed();
/*  697 */       disconnectInternal();
/*  698 */       if (this.failedOnce) {
/*  699 */         throw new IOException("Error writing to server");
/*      */       }
/*  701 */       this.failedOnce = true;
/*  702 */       if (str != null) {
/*  703 */         setProxiedClient(this.url, str, i);
/*      */       } else {
/*  705 */         setNewClient(this.url);
/*      */       } 
/*  707 */       this.ps = (PrintStream)this.http.getOutputStream();
/*  708 */       this.connected = true;
/*  709 */       this.responses = new MessageHeader();
/*  710 */       this.setRequests = false;
/*  711 */       writeRequests();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean checkSetHost() {
/*  717 */     SecurityManager securityManager = System.getSecurityManager();
/*  718 */     if (securityManager != null) {
/*  719 */       String str = securityManager.getClass().getName();
/*  720 */       if (str.equals("sun.plugin2.applet.AWTAppletSecurityManager") || str
/*  721 */         .equals("sun.plugin2.applet.FXAppletSecurityManager") || str
/*  722 */         .equals("com.sun.javaws.security.JavaWebStartSecurity") || str
/*  723 */         .equals("sun.plugin.security.ActivatorSecurityManager")) {
/*      */         
/*  725 */         byte b = -2;
/*      */         try {
/*  727 */           securityManager.checkConnect(this.url.toExternalForm(), b);
/*  728 */         } catch (SecurityException securityException) {
/*  729 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*  733 */     return true;
/*      */   }
/*      */   
/*      */   private void checkURLFile() {
/*  737 */     SecurityManager securityManager = System.getSecurityManager();
/*  738 */     if (securityManager != null) {
/*  739 */       String str = securityManager.getClass().getName();
/*  740 */       if (str.equals("sun.plugin2.applet.AWTAppletSecurityManager") || str
/*  741 */         .equals("sun.plugin2.applet.FXAppletSecurityManager") || str
/*  742 */         .equals("com.sun.javaws.security.JavaWebStartSecurity") || str
/*  743 */         .equals("sun.plugin.security.ActivatorSecurityManager")) {
/*      */         
/*  745 */         byte b = -3;
/*      */         try {
/*  747 */           securityManager.checkConnect(this.url.toExternalForm(), b);
/*  748 */         } catch (SecurityException securityException) {
/*  749 */           throw new SecurityException("denied access outside a permitted URL subpath", securityException);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setNewClient(URL paramURL) throws IOException {
/*  763 */     setNewClient(paramURL, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setNewClient(URL paramURL, boolean paramBoolean) throws IOException {
/*  775 */     this.http = HttpClient.New(paramURL, (String)null, -1, paramBoolean, this.connectTimeout, this);
/*  776 */     this.http.setReadTimeout(this.readTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setProxiedClient(URL paramURL, String paramString, int paramInt) throws IOException {
/*  791 */     setProxiedClient(paramURL, paramString, paramInt, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setProxiedClient(URL paramURL, String paramString, int paramInt, boolean paramBoolean) throws IOException {
/*  809 */     proxiedConnect(paramURL, paramString, paramInt, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void proxiedConnect(URL paramURL, String paramString, int paramInt, boolean paramBoolean) throws IOException {
/*  816 */     this.http = HttpClient.New(paramURL, paramString, paramInt, paramBoolean, this.connectTimeout, this);
/*      */     
/*  818 */     this.http.setReadTimeout(this.readTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected HttpURLConnection(URL paramURL, Handler paramHandler) throws IOException {
/*  825 */     this(paramURL, (Proxy)null, paramHandler);
/*      */   }
/*      */   
/*      */   private static String checkHost(String paramString) throws IOException {
/*  829 */     if (paramString != null && 
/*  830 */       paramString.indexOf('\n') > -1) {
/*  831 */       throw new MalformedURLException("Illegal character in host");
/*      */     }
/*      */     
/*  834 */     return paramString;
/*      */   }
/*      */   public HttpURLConnection(URL paramURL, String paramString, int paramInt) throws IOException {
/*  837 */     this(paramURL, new Proxy(Proxy.Type.HTTP, 
/*  838 */           InetSocketAddress.createUnresolved(checkHost(paramString), paramInt)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpURLConnection(URL paramURL, Proxy paramProxy) throws IOException {
/*  844 */     this(paramURL, paramProxy, new Handler());
/*      */   }
/*      */   
/*      */   private static URL checkURL(URL paramURL) throws IOException {
/*  848 */     if (paramURL != null && 
/*  849 */       paramURL.toExternalForm().indexOf('\n') > -1) {
/*  850 */       throw new MalformedURLException("Illegal character in URL");
/*      */     }
/*      */     
/*  853 */     return paramURL;
/*      */   } @Deprecated public static void setDefaultAuthenticator(HttpAuthenticator paramHttpAuthenticator) { defaultAuth = paramHttpAuthenticator; } public static InputStream openConnectionCheckRedirects(URLConnection paramURLConnection) throws IOException { byte b = 0; while (true) { if (paramURLConnection instanceof HttpURLConnection) ((HttpURLConnection)paramURLConnection).setInstanceFollowRedirects(false);  InputStream inputStream = paramURLConnection.getInputStream(); boolean bool = false; if (paramURLConnection instanceof HttpURLConnection) { HttpURLConnection httpURLConnection = (HttpURLConnection)paramURLConnection; int i = httpURLConnection.getResponseCode(); if (i >= 300 && i <= 307 && i != 306 && i != 304) { URL uRL1 = httpURLConnection.getURL(); String str = httpURLConnection.getHeaderField("Location"); URL uRL2 = null; if (str != null) uRL2 = new URL(uRL1, str);  httpURLConnection.disconnect(); if (uRL2 == null || !uRL1.getProtocol().equals(uRL2.getProtocol()) || uRL1.getPort() != uRL2.getPort() || !hostsEqual(uRL1, uRL2) || b >= 5) throw new SecurityException("illegal URL redirect");  bool = true; paramURLConnection = uRL2.openConnection(); b++; }  }  if (!bool) return inputStream;  }  } private static boolean hostsEqual(URL paramURL1, URL paramURL2) { final String h1 = paramURL1.getHost(); final String h2 = paramURL2.getHost(); if (str1 == null) return (str2 == null);  if (str2 == null) return false;  if (str1.equalsIgnoreCase(str2)) return true;  final boolean[] result = { false }; AccessController.doPrivileged(new PrivilegedAction<Void>() { public Void run() { try { InetAddress inetAddress1 = InetAddress.getByName(h1); InetAddress inetAddress2 = InetAddress.getByName(h2); result[0] = inetAddress1.equals(inetAddress2); } catch (UnknownHostException|SecurityException unknownHostException) {} return null; } }
/*      */       ); return arrayOfBoolean[0]; } public void connect() throws IOException { synchronized (this) { this.connecting = true; }  plainConnect(); } private boolean checkReuseConnection() { if (this.connected) return true;  if (this.reuseClient != null) { this.http = this.reuseClient; this.http.setReadTimeout(getReadTimeout()); this.http.reuse = false; this.reuseClient = null; this.connected = true; return true; }  return false; } private String getHostAndPort(URL paramURL) { String str1 = paramURL.getHost(); final String hostarg = str1; try { str1 = AccessController.<String>doPrivileged(new PrivilegedExceptionAction<String>() { public String run() throws IOException { InetAddress inetAddress = InetAddress.getByName(hostarg); return inetAddress.getHostAddress(); } }
/*      */         ); } catch (PrivilegedActionException privilegedActionException) {} int i = paramURL.getPort(); if (i == -1) { String str = paramURL.getProtocol(); if ("http".equals(str)) return str1 + ":80";  return str1 + ":443"; }  return str1 + ":" + Integer.toString(i); } protected void plainConnect() throws IOException { synchronized (this) { if (this.connected) return;  }  SocketPermission socketPermission = URLtoSocketPermission(this.url); if (socketPermission != null) { try { AccessController.doPrivilegedWithCombiner(new PrivilegedExceptionAction<Void>() { public Void run() throws IOException { HttpURLConnection.this.plainConnect0(); return null; }
/*  857 */             },  (AccessControlContext)null, new Permission[] { socketPermission }); } catch (PrivilegedActionException privilegedActionException) { throw (IOException)privilegedActionException.getException(); }  } else { plainConnect0(); }  } SocketPermission URLtoSocketPermission(URL paramURL) throws IOException { if (this.socketPermission != null) return this.socketPermission;  SecurityManager securityManager = System.getSecurityManager(); if (securityManager == null) return null;  SocketPermission socketPermission = new SocketPermission(getHostAndPort(paramURL), "connect"); String str1 = getRequestMethod() + ":" + getUserSetHeaders().getHeaderNamesInList(); String str2 = paramURL.getProtocol() + "://" + paramURL.getAuthority() + paramURL.getPath(); URLPermission uRLPermission = new URLPermission(str2, str1); try { securityManager.checkPermission(uRLPermission); this.socketPermission = socketPermission; return this.socketPermission; } catch (SecurityException securityException) { return null; }  } protected void plainConnect0() throws IOException { if (this.cacheHandler != null && getUseCaches()) { try { URI uRI = ParseUtil.toURI(this.url); if (uRI != null) { this.cachedResponse = this.cacheHandler.get(uRI, getRequestMethod(), getUserSetHeaders().getHeaders()); if ("https".equalsIgnoreCase(uRI.getScheme()) && !(this.cachedResponse instanceof java.net.SecureCacheResponse)) this.cachedResponse = null;  if (logger.isLoggable(PlatformLogger.Level.FINEST)) { logger.finest("Cache Request for " + uRI + " / " + getRequestMethod()); logger.finest("From cache: " + ((this.cachedResponse != null) ? this.cachedResponse.toString() : "null")); }  if (this.cachedResponse != null) { this.cachedHeaders = mapToMessageHeader(this.cachedResponse.getHeaders()); this.cachedInputStream = this.cachedResponse.getBody(); }  }  } catch (IOException iOException) {} if (this.cachedHeaders != null && this.cachedInputStream != null) { this.connected = true; return; }  this.cachedResponse = null; }  try { if (this.instProxy == null) { ProxySelector proxySelector = AccessController.<ProxySelector>doPrivileged(new PrivilegedAction<ProxySelector>() { public ProxySelector run() { return ProxySelector.getDefault(); } }); if (proxySelector != null) { URI uRI = ParseUtil.toURI(this.url); if (logger.isLoggable(PlatformLogger.Level.FINEST)) logger.finest("ProxySelector Request for " + uRI);  Iterator<Proxy> iterator = proxySelector.select(uRI).iterator(); while (iterator.hasNext()) { Proxy proxy = iterator.next(); try { if (!this.failedOnce) { this.http = getNewHttpClient(this.url, proxy, this.connectTimeout); this.http.setReadTimeout(this.readTimeout); } else { this.http = getNewHttpClient(this.url, proxy, this.connectTimeout, false); this.http.setReadTimeout(this.readTimeout); }  if (logger.isLoggable(PlatformLogger.Level.FINEST) && proxy != null) logger.finest("Proxy used: " + proxy.toString());  } catch (IOException iOException) { if (proxy != Proxy.NO_PROXY) { proxySelector.connectFailed(uRI, proxy.address(), iOException); if (!iterator.hasNext()) { this.http = getNewHttpClient(this.url, (Proxy)null, this.connectTimeout, false); this.http.setReadTimeout(this.readTimeout); break; }  continue; }  throw iOException; }  }  } else if (!this.failedOnce) { this.http = getNewHttpClient(this.url, (Proxy)null, this.connectTimeout); this.http.setReadTimeout(this.readTimeout); } else { this.http = getNewHttpClient(this.url, (Proxy)null, this.connectTimeout, false); this.http.setReadTimeout(this.readTimeout); }  } else if (!this.failedOnce) { this.http = getNewHttpClient(this.url, this.instProxy, this.connectTimeout); this.http.setReadTimeout(this.readTimeout); } else { this.http = getNewHttpClient(this.url, this.instProxy, this.connectTimeout, false); this.http.setReadTimeout(this.readTimeout); }  this.ps = (PrintStream)this.http.getOutputStream(); } catch (IOException iOException) { throw iOException; }  this.connected = true; } protected HttpURLConnection(URL paramURL, Proxy paramProxy, Handler paramHandler) throws IOException { super(checkURL(paramURL));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2579 */     this.requestURI = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2796 */     this.cdata = new byte[128]; this.requests = new MessageHeader(); this.responses = new MessageHeader(); this.userHeaders = new MessageHeader(); this.handler = paramHandler; this.instProxy = paramProxy; if (this.instProxy instanceof sun.net.ApplicationProxy) { try { this.cookieHandler = CookieHandler.getDefault(); } catch (SecurityException securityException) {} } else { this.cookieHandler = AccessController.<CookieHandler>doPrivileged(new PrivilegedAction<CookieHandler>() { public CookieHandler run() { return CookieHandler.getDefault(); } }
/*      */         ); }  this.cacheHandler = AccessController.<ResponseCache>doPrivileged(new PrivilegedAction<ResponseCache>() { public ResponseCache run() { return ResponseCache.getDefault(); } }
/*      */       ); } protected HttpClient getNewHttpClient(URL paramURL, Proxy paramProxy, int paramInt) throws IOException { return HttpClient.New(paramURL, paramProxy, paramInt, this); } protected HttpClient getNewHttpClient(URL paramURL, Proxy paramProxy, int paramInt, boolean paramBoolean) throws IOException { return HttpClient.New(paramURL, paramProxy, paramInt, paramBoolean, this); } private void expect100Continue() throws IOException { int i = this.http.getReadTimeout(); boolean bool1 = false; boolean bool2 = false; if (i <= 0) { this.http.setReadTimeout(5000); bool1 = true; }  try { this.http.parseHTTP(this.responses, this.pi, this); } catch (SocketTimeoutException socketTimeoutException) { if (!bool1) throw socketTimeoutException;  bool2 = true; this.http.setIgnoreContinue(true); }  if (!bool2) { String str = this.responses.getValue(0); if (str != null && str.startsWith("HTTP/")) { String[] arrayOfString = str.split("\\s+"); this.responseCode = -1; try { if (arrayOfString.length > 1) this.responseCode = Integer.parseInt(arrayOfString[1]);  } catch (NumberFormatException numberFormatException) {} }  if (this.responseCode != 100) throw new ProtocolException("Server rejected operation");  }  this.http.setReadTimeout(i); this.responseCode = -1; this.responses.reset(); } public synchronized OutputStream getOutputStream() throws IOException { this.connecting = true; SocketPermission socketPermission = URLtoSocketPermission(this.url); if (socketPermission != null) try { return AccessController.<OutputStream>doPrivilegedWithCombiner(new PrivilegedExceptionAction<OutputStream>() {
/*      */               public OutputStream run() throws IOException { return HttpURLConnection.this.getOutputStream0(); }
/*      */             },  (AccessControlContext)null, new Permission[] { socketPermission }); } catch (PrivilegedActionException privilegedActionException) { throw (IOException)privilegedActionException.getException(); }   return getOutputStream0(); } private synchronized OutputStream getOutputStream0() throws IOException { try { if (!this.doOutput) throw new ProtocolException("cannot write to a URLConnection if doOutput=false - call setDoOutput(true)");  if (this.method.equals("GET")) this.method = "POST";  if ("TRACE".equals(this.method) && "http".equals(this.url.getProtocol())) throw new ProtocolException("HTTP method TRACE doesn't support output");  if (this.inputStream != null) throw new ProtocolException("Cannot write output after reading input.");  if (!checkReuseConnection()) connect();  boolean bool = false; String str = this.requests.findValue("Expect"); if ("100-Continue".equalsIgnoreCase(str) && streaming()) { this.http.setIgnoreContinue(false); bool = true; }  if (streaming() && this.strOutputStream == null) writeRequests();  if (bool) expect100Continue();  this.ps = (PrintStream)this.http.getOutputStream(); if (streaming()) { if (this.strOutputStream == null) if (this.chunkLength != -1) { this.strOutputStream = new StreamingOutputStream(new ChunkedOutputStream(this.ps, this.chunkLength), -1L); } else { long l = 0L; if (this.fixedContentLengthLong != -1L) { l = this.fixedContentLengthLong; } else if (this.fixedContentLength != -1) { l = this.fixedContentLength; }  this.strOutputStream = new StreamingOutputStream(this.ps, l); }   return this.strOutputStream; }  if (this.poster == null) this.poster = new PosterOutputStream();  return this.poster; } catch (RuntimeException runtimeException) { disconnectInternal(); throw runtimeException; } catch (ProtocolException protocolException) { int i = this.responseCode; disconnectInternal(); this.responseCode = i; throw protocolException; } catch (IOException iOException) { disconnectInternal(); throw iOException; }  } public boolean streaming() { return (this.fixedContentLength != -1 || this.fixedContentLengthLong != -1L || this.chunkLength != -1); } private void setCookieHeader() throws IOException { if (this.cookieHandler != null) { synchronized (this) { if (this.setUserCookies) { int i = this.requests.getKey("Cookie"); if (i != -1) this.userCookies = this.requests.getValue(i);  i = this.requests.getKey("Cookie2"); if (i != -1) this.userCookies2 = this.requests.getValue(i);  this.setUserCookies = false; }  }  this.requests.remove("Cookie"); this.requests.remove("Cookie2"); URI uRI = ParseUtil.toURI(this.url); if (uRI != null) { if (logger.isLoggable(PlatformLogger.Level.FINEST)) logger.finest("CookieHandler request for " + uRI);  Map<String, List<String>> map = this.cookieHandler.get(uRI, this.requests.getHeaders(EXCLUDE_HEADERS)); if (!map.isEmpty()) { if (logger.isLoggable(PlatformLogger.Level.FINEST)) logger.finest("Cookies retrieved: " + map.toString());  for (Map.Entry<String, List<String>> entry : map.entrySet()) { String str = (String)entry.getKey(); if (!"Cookie".equalsIgnoreCase(str) && !"Cookie2".equalsIgnoreCase(str)) continue;  List list = (List)entry.getValue(); if (list != null && !list.isEmpty()) { StringBuilder stringBuilder = new StringBuilder(); for (String str1 : list) stringBuilder.append(str1).append("; ");  try { this.requests.add(str, stringBuilder.substring(0, stringBuilder.length() - 2)); } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {} }  }  }  }  if (this.userCookies != null) { int i; if ((i = this.requests.getKey("Cookie")) != -1) { this.requests.set("Cookie", this.requests.getValue(i) + ";" + this.userCookies); } else { this.requests.set("Cookie", this.userCookies); }  }  if (this.userCookies2 != null) { int i; if ((i = this.requests.getKey("Cookie2")) != -1) { this.requests.set("Cookie2", this.requests.getValue(i) + ";" + this.userCookies2); } else { this.requests.set("Cookie2", this.userCookies2); }  }  }  } public synchronized InputStream getInputStream() throws IOException { this.connecting = true; SocketPermission socketPermission = URLtoSocketPermission(this.url); if (socketPermission != null) try { return AccessController.<InputStream>doPrivilegedWithCombiner(new PrivilegedExceptionAction<InputStream>() {
/*      */               public InputStream run() throws IOException { return HttpURLConnection.this.getInputStream0(); }
/* 2802 */             },  (AccessControlContext)null, new Permission[] { socketPermission }); } catch (PrivilegedActionException privilegedActionException) { throw (IOException)privilegedActionException.getException(); }   return getInputStream0(); } private void reset() throws IOException { this.http.reuse = true;
/*      */     
/* 2804 */     this.reuseClient = this.http;
/* 2805 */     InputStream inputStream = this.http.getInputStream();
/* 2806 */     if (!this.method.equals("HEAD")) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 2812 */         if (inputStream instanceof sun.net.www.http.ChunkedInputStream || inputStream instanceof sun.net.www.MeteredStream)
/*      */         {
/*      */           
/* 2815 */           while (inputStream.read(this.cdata) > 0);
/*      */         
/*      */         }
/*      */         else
/*      */         {
/* 2820 */           long l1 = 0L;
/* 2821 */           int i = 0;
/* 2822 */           String str = this.responses.findValue("Content-Length");
/* 2823 */           if (str != null)
/*      */             try {
/* 2825 */               l1 = Long.parseLong(str);
/* 2826 */             } catch (NumberFormatException numberFormatException) {
/* 2827 */               l1 = 0L;
/*      */             }  
/*      */           long l2;
/* 2830 */           for (l2 = 0L; l2 < l1 && (
/* 2831 */             i = inputStream.read(this.cdata)) != -1;)
/*      */           {
/*      */             
/* 2834 */             l2 += i;
/*      */           }
/*      */         }
/*      */       
/* 2838 */       } catch (IOException iOException) {
/* 2839 */         this.http.reuse = false;
/* 2840 */         this.reuseClient = null;
/* 2841 */         disconnectInternal();
/*      */         return;
/*      */       } 
/*      */       try {
/* 2845 */         if (inputStream instanceof sun.net.www.MeteredStream) {
/* 2846 */           inputStream.close();
/*      */         }
/* 2848 */       } catch (IOException iOException) {}
/*      */     } 
/* 2850 */     this.responseCode = -1;
/* 2851 */     this.responses = new MessageHeader();
/* 2852 */     this.connected = false; } private synchronized InputStream getInputStream0() throws IOException { // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield doInput : Z
/*      */     //   4: ifne -> 18
/*      */     //   7: new java/net/ProtocolException
/*      */     //   10: dup
/*      */     //   11: ldc_w 'Cannot read from URLConnection if doInput=false (call setDoInput(true))'
/*      */     //   14: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   17: athrow
/*      */     //   18: aload_0
/*      */     //   19: getfield rememberedException : Ljava/lang/Exception;
/*      */     //   22: ifnull -> 59
/*      */     //   25: aload_0
/*      */     //   26: getfield rememberedException : Ljava/lang/Exception;
/*      */     //   29: instanceof java/lang/RuntimeException
/*      */     //   32: ifeq -> 47
/*      */     //   35: new java/lang/RuntimeException
/*      */     //   38: dup
/*      */     //   39: aload_0
/*      */     //   40: getfield rememberedException : Ljava/lang/Exception;
/*      */     //   43: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   46: athrow
/*      */     //   47: aload_0
/*      */     //   48: aload_0
/*      */     //   49: getfield rememberedException : Ljava/lang/Exception;
/*      */     //   52: checkcast java/io/IOException
/*      */     //   55: invokespecial getChainedException : (Ljava/io/IOException;)Ljava/io/IOException;
/*      */     //   58: athrow
/*      */     //   59: aload_0
/*      */     //   60: getfield inputStream : Ljava/io/InputStream;
/*      */     //   63: ifnull -> 71
/*      */     //   66: aload_0
/*      */     //   67: getfield inputStream : Ljava/io/InputStream;
/*      */     //   70: areturn
/*      */     //   71: aload_0
/*      */     //   72: invokevirtual streaming : ()Z
/*      */     //   75: ifeq -> 118
/*      */     //   78: aload_0
/*      */     //   79: getfield strOutputStream : Lsun/net/www/protocol/http/HttpURLConnection$StreamingOutputStream;
/*      */     //   82: ifnonnull -> 90
/*      */     //   85: aload_0
/*      */     //   86: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
/*      */     //   89: pop
/*      */     //   90: aload_0
/*      */     //   91: getfield strOutputStream : Lsun/net/www/protocol/http/HttpURLConnection$StreamingOutputStream;
/*      */     //   94: invokevirtual close : ()V
/*      */     //   97: aload_0
/*      */     //   98: getfield strOutputStream : Lsun/net/www/protocol/http/HttpURLConnection$StreamingOutputStream;
/*      */     //   101: invokevirtual writtenOK : ()Z
/*      */     //   104: ifne -> 118
/*      */     //   107: new java/io/IOException
/*      */     //   110: dup
/*      */     //   111: ldc_w 'Incomplete output stream'
/*      */     //   114: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   117: athrow
/*      */     //   118: iconst_0
/*      */     //   119: istore_1
/*      */     //   120: iconst_0
/*      */     //   121: istore_2
/*      */     //   122: ldc2_w -1
/*      */     //   125: lstore_3
/*      */     //   126: aconst_null
/*      */     //   127: astore #5
/*      */     //   129: aconst_null
/*      */     //   130: astore #6
/*      */     //   132: aconst_null
/*      */     //   133: astore #7
/*      */     //   135: iconst_0
/*      */     //   136: istore #8
/*      */     //   138: iconst_0
/*      */     //   139: istore #9
/*      */     //   141: aload_0
/*      */     //   142: aload_0
/*      */     //   143: getfield requests : Lsun/net/www/MessageHeader;
/*      */     //   146: ldc_w 'Authorization'
/*      */     //   149: invokevirtual getKey : (Ljava/lang/String;)I
/*      */     //   152: iconst_m1
/*      */     //   153: if_icmpeq -> 160
/*      */     //   156: iconst_1
/*      */     //   157: goto -> 161
/*      */     //   160: iconst_0
/*      */     //   161: putfield isUserServerAuth : Z
/*      */     //   164: aload_0
/*      */     //   165: aload_0
/*      */     //   166: getfield requests : Lsun/net/www/MessageHeader;
/*      */     //   169: ldc_w 'Proxy-Authorization'
/*      */     //   172: invokevirtual getKey : (Ljava/lang/String;)I
/*      */     //   175: iconst_m1
/*      */     //   176: if_icmpeq -> 183
/*      */     //   179: iconst_1
/*      */     //   180: goto -> 184
/*      */     //   183: iconst_0
/*      */     //   184: putfield isUserProxyAuth : Z
/*      */     //   187: aload_0
/*      */     //   188: invokespecial checkReuseConnection : ()Z
/*      */     //   191: ifne -> 198
/*      */     //   194: aload_0
/*      */     //   195: invokevirtual connect : ()V
/*      */     //   198: aload_0
/*      */     //   199: getfield cachedInputStream : Ljava/io/InputStream;
/*      */     //   202: ifnull -> 242
/*      */     //   205: aload_0
/*      */     //   206: getfield cachedInputStream : Ljava/io/InputStream;
/*      */     //   209: astore #10
/*      */     //   211: aload_0
/*      */     //   212: getfield proxyAuthKey : Ljava/lang/String;
/*      */     //   215: ifnull -> 225
/*      */     //   218: aload_0
/*      */     //   219: getfield proxyAuthKey : Ljava/lang/String;
/*      */     //   222: invokestatic endAuthRequest : (Ljava/lang/String;)V
/*      */     //   225: aload_0
/*      */     //   226: getfield serverAuthKey : Ljava/lang/String;
/*      */     //   229: ifnull -> 239
/*      */     //   232: aload_0
/*      */     //   233: getfield serverAuthKey : Ljava/lang/String;
/*      */     //   236: invokestatic endAuthRequest : (Ljava/lang/String;)V
/*      */     //   239: aload #10
/*      */     //   241: areturn
/*      */     //   242: invokestatic getDefault : ()Lsun/net/ProgressMonitor;
/*      */     //   245: aload_0
/*      */     //   246: getfield url : Ljava/net/URL;
/*      */     //   249: aload_0
/*      */     //   250: getfield method : Ljava/lang/String;
/*      */     //   253: invokevirtual shouldMeterInput : (Ljava/net/URL;Ljava/lang/String;)Z
/*      */     //   256: istore #10
/*      */     //   258: iload #10
/*      */     //   260: ifeq -> 289
/*      */     //   263: aload_0
/*      */     //   264: new sun/net/ProgressSource
/*      */     //   267: dup
/*      */     //   268: aload_0
/*      */     //   269: getfield url : Ljava/net/URL;
/*      */     //   272: aload_0
/*      */     //   273: getfield method : Ljava/lang/String;
/*      */     //   276: invokespecial <init> : (Ljava/net/URL;Ljava/lang/String;)V
/*      */     //   279: putfield pi : Lsun/net/ProgressSource;
/*      */     //   282: aload_0
/*      */     //   283: getfield pi : Lsun/net/ProgressSource;
/*      */     //   286: invokevirtual beginTracking : ()V
/*      */     //   289: aload_0
/*      */     //   290: aload_0
/*      */     //   291: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   294: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
/*      */     //   297: checkcast java/io/PrintStream
/*      */     //   300: putfield ps : Ljava/io/PrintStream;
/*      */     //   303: aload_0
/*      */     //   304: invokevirtual streaming : ()Z
/*      */     //   307: ifne -> 314
/*      */     //   310: aload_0
/*      */     //   311: invokespecial writeRequests : ()V
/*      */     //   314: aload_0
/*      */     //   315: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   318: aload_0
/*      */     //   319: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   322: aload_0
/*      */     //   323: getfield pi : Lsun/net/ProgressSource;
/*      */     //   326: aload_0
/*      */     //   327: invokevirtual parseHTTP : (Lsun/net/www/MessageHeader;Lsun/net/ProgressSource;Lsun/net/www/protocol/http/HttpURLConnection;)Z
/*      */     //   330: pop
/*      */     //   331: getstatic sun/net/www/protocol/http/HttpURLConnection.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   334: getstatic sun/util/logging/PlatformLogger$Level.FINE : Lsun/util/logging/PlatformLogger$Level;
/*      */     //   337: invokevirtual isLoggable : (Lsun/util/logging/PlatformLogger$Level;)Z
/*      */     //   340: ifeq -> 356
/*      */     //   343: getstatic sun/net/www/protocol/http/HttpURLConnection.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   346: aload_0
/*      */     //   347: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   350: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   353: invokevirtual fine : (Ljava/lang/String;)V
/*      */     //   356: aload_0
/*      */     //   357: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   360: ldc_w 'WWW-Authenticate'
/*      */     //   363: invokevirtual filterNTLMResponses : (Ljava/lang/String;)Z
/*      */     //   366: istore #11
/*      */     //   368: aload_0
/*      */     //   369: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   372: ldc_w 'Proxy-Authenticate'
/*      */     //   375: invokevirtual filterNTLMResponses : (Ljava/lang/String;)Z
/*      */     //   378: istore #12
/*      */     //   380: iload #11
/*      */     //   382: ifne -> 390
/*      */     //   385: iload #12
/*      */     //   387: ifeq -> 424
/*      */     //   390: getstatic sun/net/www/protocol/http/HttpURLConnection.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   393: getstatic sun/util/logging/PlatformLogger$Level.FINE : Lsun/util/logging/PlatformLogger$Level;
/*      */     //   396: invokevirtual isLoggable : (Lsun/util/logging/PlatformLogger$Level;)Z
/*      */     //   399: ifeq -> 424
/*      */     //   402: getstatic sun/net/www/protocol/http/HttpURLConnection.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   405: ldc_w '>>>> Headers are filtered'
/*      */     //   408: invokevirtual fine : (Ljava/lang/String;)V
/*      */     //   411: getstatic sun/net/www/protocol/http/HttpURLConnection.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   414: aload_0
/*      */     //   415: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   418: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   421: invokevirtual fine : (Ljava/lang/String;)V
/*      */     //   424: aload_0
/*      */     //   425: aload_0
/*      */     //   426: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   429: invokevirtual getInputStream : ()Ljava/io/InputStream;
/*      */     //   432: putfield inputStream : Ljava/io/InputStream;
/*      */     //   435: aload_0
/*      */     //   436: invokevirtual getResponseCode : ()I
/*      */     //   439: istore_2
/*      */     //   440: iload_2
/*      */     //   441: iconst_m1
/*      */     //   442: if_icmpne -> 460
/*      */     //   445: aload_0
/*      */     //   446: invokespecial disconnectInternal : ()V
/*      */     //   449: new java/io/IOException
/*      */     //   452: dup
/*      */     //   453: ldc_w 'Invalid Http response'
/*      */     //   456: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   459: athrow
/*      */     //   460: iload_2
/*      */     //   461: sipush #407
/*      */     //   464: if_icmpne -> 764
/*      */     //   467: aload_0
/*      */     //   468: invokevirtual streaming : ()Z
/*      */     //   471: ifeq -> 492
/*      */     //   474: aload_0
/*      */     //   475: invokespecial disconnectInternal : ()V
/*      */     //   478: new java/net/HttpRetryException
/*      */     //   481: dup
/*      */     //   482: ldc_w 'cannot retry due to proxy authentication, in streaming mode'
/*      */     //   485: sipush #407
/*      */     //   488: invokespecial <init> : (Ljava/lang/String;I)V
/*      */     //   491: athrow
/*      */     //   492: iconst_0
/*      */     //   493: istore #13
/*      */     //   495: aload_0
/*      */     //   496: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   499: ldc_w 'Proxy-Authenticate'
/*      */     //   502: invokevirtual multiValueIterator : (Ljava/lang/String;)Ljava/util/Iterator;
/*      */     //   505: astore #14
/*      */     //   507: aload #14
/*      */     //   509: invokeinterface hasNext : ()Z
/*      */     //   514: ifeq -> 582
/*      */     //   517: aload #14
/*      */     //   519: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   524: checkcast java/lang/String
/*      */     //   527: invokevirtual trim : ()Ljava/lang/String;
/*      */     //   530: astore #15
/*      */     //   532: aload #15
/*      */     //   534: ldc_w 'Negotiate'
/*      */     //   537: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
/*      */     //   540: ifne -> 554
/*      */     //   543: aload #15
/*      */     //   545: ldc_w 'Kerberos'
/*      */     //   548: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
/*      */     //   551: ifeq -> 579
/*      */     //   554: iload #9
/*      */     //   556: ifne -> 565
/*      */     //   559: iconst_1
/*      */     //   560: istore #9
/*      */     //   562: goto -> 582
/*      */     //   565: iconst_1
/*      */     //   566: istore #13
/*      */     //   568: aload_0
/*      */     //   569: iconst_0
/*      */     //   570: putfield doingNTLMp2ndStage : Z
/*      */     //   573: aconst_null
/*      */     //   574: astore #6
/*      */     //   576: goto -> 582
/*      */     //   579: goto -> 507
/*      */     //   582: new sun/net/www/protocol/http/AuthenticationHeader
/*      */     //   585: dup
/*      */     //   586: ldc_w 'Proxy-Authenticate'
/*      */     //   589: aload_0
/*      */     //   590: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   593: new sun/net/www/protocol/http/HttpCallerInfo
/*      */     //   596: dup
/*      */     //   597: aload_0
/*      */     //   598: getfield url : Ljava/net/URL;
/*      */     //   601: aload_0
/*      */     //   602: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   605: invokevirtual getProxyHostUsed : ()Ljava/lang/String;
/*      */     //   608: aload_0
/*      */     //   609: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   612: invokevirtual getProxyPortUsed : ()I
/*      */     //   615: invokespecial <init> : (Ljava/net/URL;Ljava/lang/String;I)V
/*      */     //   618: iload #13
/*      */     //   620: getstatic sun/net/www/protocol/http/HttpURLConnection.disabledProxyingSchemes : Ljava/util/Set;
/*      */     //   623: invokespecial <init> : (Ljava/lang/String;Lsun/net/www/MessageHeader;Lsun/net/www/protocol/http/HttpCallerInfo;ZLjava/util/Set;)V
/*      */     //   626: astore #15
/*      */     //   628: aload_0
/*      */     //   629: getfield doingNTLMp2ndStage : Z
/*      */     //   632: ifne -> 660
/*      */     //   635: aload_0
/*      */     //   636: aload #6
/*      */     //   638: aload #15
/*      */     //   640: invokespecial resetProxyAuthentication : (Lsun/net/www/protocol/http/AuthenticationInfo;Lsun/net/www/protocol/http/AuthenticationHeader;)Lsun/net/www/protocol/http/AuthenticationInfo;
/*      */     //   643: astore #6
/*      */     //   645: aload #6
/*      */     //   647: ifnull -> 761
/*      */     //   650: iinc #1, 1
/*      */     //   653: aload_0
/*      */     //   654: invokespecial disconnectInternal : ()V
/*      */     //   657: goto -> 1852
/*      */     //   660: aload_0
/*      */     //   661: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   664: ldc_w 'Proxy-Authenticate'
/*      */     //   667: invokevirtual findValue : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   670: astore #16
/*      */     //   672: aload_0
/*      */     //   673: invokespecial reset : ()V
/*      */     //   676: aload #6
/*      */     //   678: aload_0
/*      */     //   679: aload #15
/*      */     //   681: invokevirtual headerParser : ()Lsun/net/www/HeaderParser;
/*      */     //   684: aload #16
/*      */     //   686: invokevirtual setHeaders : (Lsun/net/www/protocol/http/HttpURLConnection;Lsun/net/www/HeaderParser;Ljava/lang/String;)Z
/*      */     //   689: ifne -> 707
/*      */     //   692: aload_0
/*      */     //   693: invokespecial disconnectInternal : ()V
/*      */     //   696: new java/io/IOException
/*      */     //   699: dup
/*      */     //   700: ldc_w 'Authentication failure'
/*      */     //   703: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   706: athrow
/*      */     //   707: aload #5
/*      */     //   709: ifnull -> 748
/*      */     //   712: aload #7
/*      */     //   714: ifnull -> 748
/*      */     //   717: aload #5
/*      */     //   719: aload_0
/*      */     //   720: aload #7
/*      */     //   722: invokevirtual headerParser : ()Lsun/net/www/HeaderParser;
/*      */     //   725: aload #16
/*      */     //   727: invokevirtual setHeaders : (Lsun/net/www/protocol/http/HttpURLConnection;Lsun/net/www/HeaderParser;Ljava/lang/String;)Z
/*      */     //   730: ifne -> 748
/*      */     //   733: aload_0
/*      */     //   734: invokespecial disconnectInternal : ()V
/*      */     //   737: new java/io/IOException
/*      */     //   740: dup
/*      */     //   741: ldc_w 'Authentication failure'
/*      */     //   744: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   747: athrow
/*      */     //   748: aload_0
/*      */     //   749: aconst_null
/*      */     //   750: putfield authObj : Ljava/lang/Object;
/*      */     //   753: aload_0
/*      */     //   754: iconst_0
/*      */     //   755: putfield doingNTLMp2ndStage : Z
/*      */     //   758: goto -> 1852
/*      */     //   761: goto -> 789
/*      */     //   764: iconst_0
/*      */     //   765: istore #9
/*      */     //   767: aload_0
/*      */     //   768: iconst_0
/*      */     //   769: putfield doingNTLMp2ndStage : Z
/*      */     //   772: aload_0
/*      */     //   773: getfield isUserProxyAuth : Z
/*      */     //   776: ifne -> 789
/*      */     //   779: aload_0
/*      */     //   780: getfield requests : Lsun/net/www/MessageHeader;
/*      */     //   783: ldc_w 'Proxy-Authorization'
/*      */     //   786: invokevirtual remove : (Ljava/lang/String;)V
/*      */     //   789: aload #6
/*      */     //   791: ifnull -> 799
/*      */     //   794: aload #6
/*      */     //   796: invokevirtual addToCache : ()V
/*      */     //   799: iload_2
/*      */     //   800: sipush #401
/*      */     //   803: if_icmpne -> 1121
/*      */     //   806: aload_0
/*      */     //   807: invokevirtual streaming : ()Z
/*      */     //   810: ifeq -> 831
/*      */     //   813: aload_0
/*      */     //   814: invokespecial disconnectInternal : ()V
/*      */     //   817: new java/net/HttpRetryException
/*      */     //   820: dup
/*      */     //   821: ldc_w 'cannot retry due to server authentication, in streaming mode'
/*      */     //   824: sipush #401
/*      */     //   827: invokespecial <init> : (Ljava/lang/String;I)V
/*      */     //   830: athrow
/*      */     //   831: iconst_0
/*      */     //   832: istore #13
/*      */     //   834: aload_0
/*      */     //   835: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   838: ldc_w 'WWW-Authenticate'
/*      */     //   841: invokevirtual multiValueIterator : (Ljava/lang/String;)Ljava/util/Iterator;
/*      */     //   844: astore #14
/*      */     //   846: aload #14
/*      */     //   848: invokeinterface hasNext : ()Z
/*      */     //   853: ifeq -> 921
/*      */     //   856: aload #14
/*      */     //   858: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   863: checkcast java/lang/String
/*      */     //   866: invokevirtual trim : ()Ljava/lang/String;
/*      */     //   869: astore #15
/*      */     //   871: aload #15
/*      */     //   873: ldc_w 'Negotiate'
/*      */     //   876: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
/*      */     //   879: ifne -> 893
/*      */     //   882: aload #15
/*      */     //   884: ldc_w 'Kerberos'
/*      */     //   887: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
/*      */     //   890: ifeq -> 918
/*      */     //   893: iload #8
/*      */     //   895: ifne -> 904
/*      */     //   898: iconst_1
/*      */     //   899: istore #8
/*      */     //   901: goto -> 921
/*      */     //   904: iconst_1
/*      */     //   905: istore #13
/*      */     //   907: aload_0
/*      */     //   908: iconst_0
/*      */     //   909: putfield doingNTLM2ndStage : Z
/*      */     //   912: aconst_null
/*      */     //   913: astore #5
/*      */     //   915: goto -> 921
/*      */     //   918: goto -> 846
/*      */     //   921: new sun/net/www/protocol/http/AuthenticationHeader
/*      */     //   924: dup
/*      */     //   925: ldc_w 'WWW-Authenticate'
/*      */     //   928: aload_0
/*      */     //   929: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   932: new sun/net/www/protocol/http/HttpCallerInfo
/*      */     //   935: dup
/*      */     //   936: aload_0
/*      */     //   937: getfield url : Ljava/net/URL;
/*      */     //   940: invokespecial <init> : (Ljava/net/URL;)V
/*      */     //   943: iload #13
/*      */     //   945: invokespecial <init> : (Ljava/lang/String;Lsun/net/www/MessageHeader;Lsun/net/www/protocol/http/HttpCallerInfo;Z)V
/*      */     //   948: astore #7
/*      */     //   950: aload #7
/*      */     //   952: invokevirtual raw : ()Ljava/lang/String;
/*      */     //   955: astore #15
/*      */     //   957: aload_0
/*      */     //   958: getfield doingNTLM2ndStage : Z
/*      */     //   961: ifne -> 1073
/*      */     //   964: aload #5
/*      */     //   966: ifnull -> 1040
/*      */     //   969: aload #5
/*      */     //   971: invokevirtual getAuthScheme : ()Lsun/net/www/protocol/http/AuthScheme;
/*      */     //   974: getstatic sun/net/www/protocol/http/AuthScheme.NTLM : Lsun/net/www/protocol/http/AuthScheme;
/*      */     //   977: if_acmpeq -> 1040
/*      */     //   980: aload #5
/*      */     //   982: aload #15
/*      */     //   984: invokevirtual isAuthorizationStale : (Ljava/lang/String;)Z
/*      */     //   987: ifeq -> 1035
/*      */     //   990: aload_0
/*      */     //   991: invokespecial disconnectWeb : ()V
/*      */     //   994: iinc #1, 1
/*      */     //   997: aload_0
/*      */     //   998: getfield requests : Lsun/net/www/MessageHeader;
/*      */     //   1001: aload #5
/*      */     //   1003: invokevirtual getHeaderName : ()Ljava/lang/String;
/*      */     //   1006: aload #5
/*      */     //   1008: aload_0
/*      */     //   1009: getfield url : Ljava/net/URL;
/*      */     //   1012: aload_0
/*      */     //   1013: getfield method : Ljava/lang/String;
/*      */     //   1016: invokevirtual getHeaderValue : (Ljava/net/URL;Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1019: invokevirtual set : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1022: aload_0
/*      */     //   1023: aload #5
/*      */     //   1025: putfield currentServerCredentials : Lsun/net/www/protocol/http/AuthenticationInfo;
/*      */     //   1028: aload_0
/*      */     //   1029: invokespecial setCookieHeader : ()V
/*      */     //   1032: goto -> 1852
/*      */     //   1035: aload #5
/*      */     //   1037: invokevirtual removeFromCache : ()V
/*      */     //   1040: aload_0
/*      */     //   1041: aload #7
/*      */     //   1043: invokespecial getServerAuthentication : (Lsun/net/www/protocol/http/AuthenticationHeader;)Lsun/net/www/protocol/http/AuthenticationInfo;
/*      */     //   1046: astore #5
/*      */     //   1048: aload_0
/*      */     //   1049: aload #5
/*      */     //   1051: putfield currentServerCredentials : Lsun/net/www/protocol/http/AuthenticationInfo;
/*      */     //   1054: aload #5
/*      */     //   1056: ifnull -> 1121
/*      */     //   1059: aload_0
/*      */     //   1060: invokespecial disconnectWeb : ()V
/*      */     //   1063: iinc #1, 1
/*      */     //   1066: aload_0
/*      */     //   1067: invokespecial setCookieHeader : ()V
/*      */     //   1070: goto -> 1852
/*      */     //   1073: aload_0
/*      */     //   1074: invokespecial reset : ()V
/*      */     //   1077: aload #5
/*      */     //   1079: aload_0
/*      */     //   1080: aconst_null
/*      */     //   1081: aload #15
/*      */     //   1083: invokevirtual setHeaders : (Lsun/net/www/protocol/http/HttpURLConnection;Lsun/net/www/HeaderParser;Ljava/lang/String;)Z
/*      */     //   1086: ifne -> 1104
/*      */     //   1089: aload_0
/*      */     //   1090: invokespecial disconnectWeb : ()V
/*      */     //   1093: new java/io/IOException
/*      */     //   1096: dup
/*      */     //   1097: ldc_w 'Authentication failure'
/*      */     //   1100: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1103: athrow
/*      */     //   1104: aload_0
/*      */     //   1105: iconst_0
/*      */     //   1106: putfield doingNTLM2ndStage : Z
/*      */     //   1109: aload_0
/*      */     //   1110: aconst_null
/*      */     //   1111: putfield authObj : Ljava/lang/Object;
/*      */     //   1114: aload_0
/*      */     //   1115: invokespecial setCookieHeader : ()V
/*      */     //   1118: goto -> 1852
/*      */     //   1121: aload #5
/*      */     //   1123: ifnull -> 1345
/*      */     //   1126: aload #5
/*      */     //   1128: instanceof sun/net/www/protocol/http/DigestAuthentication
/*      */     //   1131: ifeq -> 1141
/*      */     //   1134: aload_0
/*      */     //   1135: getfield domain : Ljava/lang/String;
/*      */     //   1138: ifnonnull -> 1234
/*      */     //   1141: aload #5
/*      */     //   1143: instanceof sun/net/www/protocol/http/BasicAuthentication
/*      */     //   1146: ifeq -> 1226
/*      */     //   1149: aload_0
/*      */     //   1150: getfield url : Ljava/net/URL;
/*      */     //   1153: invokevirtual getPath : ()Ljava/lang/String;
/*      */     //   1156: invokestatic reducePath : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1159: astore #13
/*      */     //   1161: aload #5
/*      */     //   1163: getfield path : Ljava/lang/String;
/*      */     //   1166: astore #14
/*      */     //   1168: aload #14
/*      */     //   1170: aload #13
/*      */     //   1172: invokevirtual startsWith : (Ljava/lang/String;)Z
/*      */     //   1175: ifeq -> 1191
/*      */     //   1178: aload #13
/*      */     //   1180: invokevirtual length : ()I
/*      */     //   1183: aload #14
/*      */     //   1185: invokevirtual length : ()I
/*      */     //   1188: if_icmplt -> 1200
/*      */     //   1191: aload #14
/*      */     //   1193: aload #13
/*      */     //   1195: invokestatic getRootPath : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1198: astore #13
/*      */     //   1200: aload #5
/*      */     //   1202: invokevirtual clone : ()Ljava/lang/Object;
/*      */     //   1205: checkcast sun/net/www/protocol/http/BasicAuthentication
/*      */     //   1208: astore #15
/*      */     //   1210: aload #5
/*      */     //   1212: invokevirtual removeFromCache : ()V
/*      */     //   1215: aload #15
/*      */     //   1217: aload #13
/*      */     //   1219: putfield path : Ljava/lang/String;
/*      */     //   1222: aload #15
/*      */     //   1224: astore #5
/*      */     //   1226: aload #5
/*      */     //   1228: invokevirtual addToCache : ()V
/*      */     //   1231: goto -> 1345
/*      */     //   1234: aload #5
/*      */     //   1236: checkcast sun/net/www/protocol/http/DigestAuthentication
/*      */     //   1239: astore #13
/*      */     //   1241: new java/util/StringTokenizer
/*      */     //   1244: dup
/*      */     //   1245: aload_0
/*      */     //   1246: getfield domain : Ljava/lang/String;
/*      */     //   1249: ldc ' '
/*      */     //   1251: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1254: astore #14
/*      */     //   1256: aload #13
/*      */     //   1258: getfield realm : Ljava/lang/String;
/*      */     //   1261: astore #15
/*      */     //   1263: aload #13
/*      */     //   1265: getfield pw : Ljava/net/PasswordAuthentication;
/*      */     //   1268: astore #16
/*      */     //   1270: aload_0
/*      */     //   1271: aload #13
/*      */     //   1273: getfield params : Lsun/net/www/protocol/http/DigestAuthentication$Parameters;
/*      */     //   1276: putfield digestparams : Lsun/net/www/protocol/http/DigestAuthentication$Parameters;
/*      */     //   1279: aload #14
/*      */     //   1281: invokevirtual hasMoreTokens : ()Z
/*      */     //   1284: ifeq -> 1345
/*      */     //   1287: aload #14
/*      */     //   1289: invokevirtual nextToken : ()Ljava/lang/String;
/*      */     //   1292: astore #17
/*      */     //   1294: new java/net/URL
/*      */     //   1297: dup
/*      */     //   1298: aload_0
/*      */     //   1299: getfield url : Ljava/net/URL;
/*      */     //   1302: aload #17
/*      */     //   1304: invokespecial <init> : (Ljava/net/URL;Ljava/lang/String;)V
/*      */     //   1307: astore #18
/*      */     //   1309: new sun/net/www/protocol/http/DigestAuthentication
/*      */     //   1312: dup
/*      */     //   1313: iconst_0
/*      */     //   1314: aload #18
/*      */     //   1316: aload #15
/*      */     //   1318: ldc_w 'Digest'
/*      */     //   1321: aload #16
/*      */     //   1323: aload_0
/*      */     //   1324: getfield digestparams : Lsun/net/www/protocol/http/DigestAuthentication$Parameters;
/*      */     //   1327: invokespecial <init> : (ZLjava/net/URL;Ljava/lang/String;Ljava/lang/String;Ljava/net/PasswordAuthentication;Lsun/net/www/protocol/http/DigestAuthentication$Parameters;)V
/*      */     //   1330: astore #19
/*      */     //   1332: aload #19
/*      */     //   1334: invokevirtual addToCache : ()V
/*      */     //   1337: goto -> 1342
/*      */     //   1340: astore #18
/*      */     //   1342: goto -> 1279
/*      */     //   1345: iconst_0
/*      */     //   1346: istore #8
/*      */     //   1348: iconst_0
/*      */     //   1349: istore #9
/*      */     //   1351: aload_0
/*      */     //   1352: iconst_0
/*      */     //   1353: putfield doingNTLMp2ndStage : Z
/*      */     //   1356: aload_0
/*      */     //   1357: iconst_0
/*      */     //   1358: putfield doingNTLM2ndStage : Z
/*      */     //   1361: aload_0
/*      */     //   1362: getfield isUserServerAuth : Z
/*      */     //   1365: ifne -> 1378
/*      */     //   1368: aload_0
/*      */     //   1369: getfield requests : Lsun/net/www/MessageHeader;
/*      */     //   1372: ldc_w 'Authorization'
/*      */     //   1375: invokevirtual remove : (Ljava/lang/String;)V
/*      */     //   1378: aload_0
/*      */     //   1379: getfield isUserProxyAuth : Z
/*      */     //   1382: ifne -> 1395
/*      */     //   1385: aload_0
/*      */     //   1386: getfield requests : Lsun/net/www/MessageHeader;
/*      */     //   1389: ldc_w 'Proxy-Authorization'
/*      */     //   1392: invokevirtual remove : (Ljava/lang/String;)V
/*      */     //   1395: iload_2
/*      */     //   1396: sipush #200
/*      */     //   1399: if_icmpne -> 1410
/*      */     //   1402: aload_0
/*      */     //   1403: iconst_0
/*      */     //   1404: invokespecial checkResponseCredentials : (Z)V
/*      */     //   1407: goto -> 1415
/*      */     //   1410: aload_0
/*      */     //   1411: iconst_0
/*      */     //   1412: putfield needToCheck : Z
/*      */     //   1415: aload_0
/*      */     //   1416: iconst_1
/*      */     //   1417: putfield needToCheck : Z
/*      */     //   1420: aload_0
/*      */     //   1421: invokespecial followRedirect : ()Z
/*      */     //   1424: ifeq -> 1437
/*      */     //   1427: iinc #1, 1
/*      */     //   1430: aload_0
/*      */     //   1431: invokespecial setCookieHeader : ()V
/*      */     //   1434: goto -> 1852
/*      */     //   1437: aload_0
/*      */     //   1438: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   1441: ldc_w 'content-length'
/*      */     //   1444: invokevirtual findValue : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1447: invokestatic parseLong : (Ljava/lang/String;)J
/*      */     //   1450: lstore_3
/*      */     //   1451: goto -> 1456
/*      */     //   1454: astore #13
/*      */     //   1456: aload_0
/*      */     //   1457: getfield method : Ljava/lang/String;
/*      */     //   1460: ldc_w 'HEAD'
/*      */     //   1463: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1466: ifne -> 1489
/*      */     //   1469: lload_3
/*      */     //   1470: lconst_0
/*      */     //   1471: lcmp
/*      */     //   1472: ifeq -> 1489
/*      */     //   1475: iload_2
/*      */     //   1476: sipush #304
/*      */     //   1479: if_icmpeq -> 1489
/*      */     //   1482: iload_2
/*      */     //   1483: sipush #204
/*      */     //   1486: if_icmpne -> 1536
/*      */     //   1489: aload_0
/*      */     //   1490: getfield pi : Lsun/net/ProgressSource;
/*      */     //   1493: ifnull -> 1508
/*      */     //   1496: aload_0
/*      */     //   1497: getfield pi : Lsun/net/ProgressSource;
/*      */     //   1500: invokevirtual finishTracking : ()V
/*      */     //   1503: aload_0
/*      */     //   1504: aconst_null
/*      */     //   1505: putfield pi : Lsun/net/ProgressSource;
/*      */     //   1508: aload_0
/*      */     //   1509: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   1512: invokevirtual finished : ()V
/*      */     //   1515: aload_0
/*      */     //   1516: aconst_null
/*      */     //   1517: putfield http : Lsun/net/www/http/HttpClient;
/*      */     //   1520: aload_0
/*      */     //   1521: new sun/net/www/protocol/http/EmptyInputStream
/*      */     //   1524: dup
/*      */     //   1525: invokespecial <init> : ()V
/*      */     //   1528: putfield inputStream : Ljava/io/InputStream;
/*      */     //   1531: aload_0
/*      */     //   1532: iconst_0
/*      */     //   1533: putfield connected : Z
/*      */     //   1536: iload_2
/*      */     //   1537: sipush #200
/*      */     //   1540: if_icmpeq -> 1578
/*      */     //   1543: iload_2
/*      */     //   1544: sipush #203
/*      */     //   1547: if_icmpeq -> 1578
/*      */     //   1550: iload_2
/*      */     //   1551: sipush #206
/*      */     //   1554: if_icmpeq -> 1578
/*      */     //   1557: iload_2
/*      */     //   1558: sipush #300
/*      */     //   1561: if_icmpeq -> 1578
/*      */     //   1564: iload_2
/*      */     //   1565: sipush #301
/*      */     //   1568: if_icmpeq -> 1578
/*      */     //   1571: iload_2
/*      */     //   1572: sipush #410
/*      */     //   1575: if_icmpne -> 1699
/*      */     //   1578: aload_0
/*      */     //   1579: getfield cacheHandler : Ljava/net/ResponseCache;
/*      */     //   1582: ifnull -> 1699
/*      */     //   1585: aload_0
/*      */     //   1586: invokevirtual getUseCaches : ()Z
/*      */     //   1589: ifeq -> 1699
/*      */     //   1592: aload_0
/*      */     //   1593: getfield url : Ljava/net/URL;
/*      */     //   1596: invokestatic toURI : (Ljava/net/URL;)Ljava/net/URI;
/*      */     //   1599: astore #13
/*      */     //   1601: aload #13
/*      */     //   1603: ifnull -> 1699
/*      */     //   1606: aload_0
/*      */     //   1607: astore #14
/*      */     //   1609: ldc_w 'https'
/*      */     //   1612: aload #13
/*      */     //   1614: invokevirtual getScheme : ()Ljava/lang/String;
/*      */     //   1617: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
/*      */     //   1620: ifeq -> 1647
/*      */     //   1623: aload_0
/*      */     //   1624: invokevirtual getClass : ()Ljava/lang/Class;
/*      */     //   1627: ldc_w 'httpsURLConnection'
/*      */     //   1630: invokevirtual getField : (Ljava/lang/String;)Ljava/lang/reflect/Field;
/*      */     //   1633: aload_0
/*      */     //   1634: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1637: checkcast java/net/URLConnection
/*      */     //   1640: astore #14
/*      */     //   1642: goto -> 1647
/*      */     //   1645: astore #15
/*      */     //   1647: aload_0
/*      */     //   1648: getfield cacheHandler : Ljava/net/ResponseCache;
/*      */     //   1651: aload #13
/*      */     //   1653: aload #14
/*      */     //   1655: invokevirtual put : (Ljava/net/URI;Ljava/net/URLConnection;)Ljava/net/CacheRequest;
/*      */     //   1658: astore #15
/*      */     //   1660: aload #15
/*      */     //   1662: ifnull -> 1699
/*      */     //   1665: aload_0
/*      */     //   1666: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   1669: ifnull -> 1699
/*      */     //   1672: aload_0
/*      */     //   1673: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   1676: aload #15
/*      */     //   1678: invokevirtual setCacheRequest : (Ljava/net/CacheRequest;)V
/*      */     //   1681: aload_0
/*      */     //   1682: new sun/net/www/protocol/http/HttpURLConnection$HttpInputStream
/*      */     //   1685: dup
/*      */     //   1686: aload_0
/*      */     //   1687: aload_0
/*      */     //   1688: getfield inputStream : Ljava/io/InputStream;
/*      */     //   1691: aload #15
/*      */     //   1693: invokespecial <init> : (Lsun/net/www/protocol/http/HttpURLConnection;Ljava/io/InputStream;Ljava/net/CacheRequest;)V
/*      */     //   1696: putfield inputStream : Ljava/io/InputStream;
/*      */     //   1699: aload_0
/*      */     //   1700: getfield inputStream : Ljava/io/InputStream;
/*      */     //   1703: instanceof sun/net/www/protocol/http/HttpURLConnection$HttpInputStream
/*      */     //   1706: ifne -> 1725
/*      */     //   1709: aload_0
/*      */     //   1710: new sun/net/www/protocol/http/HttpURLConnection$HttpInputStream
/*      */     //   1713: dup
/*      */     //   1714: aload_0
/*      */     //   1715: aload_0
/*      */     //   1716: getfield inputStream : Ljava/io/InputStream;
/*      */     //   1719: invokespecial <init> : (Lsun/net/www/protocol/http/HttpURLConnection;Ljava/io/InputStream;)V
/*      */     //   1722: putfield inputStream : Ljava/io/InputStream;
/*      */     //   1725: iload_2
/*      */     //   1726: sipush #400
/*      */     //   1729: if_icmplt -> 1805
/*      */     //   1732: iload_2
/*      */     //   1733: sipush #404
/*      */     //   1736: if_icmpeq -> 1746
/*      */     //   1739: iload_2
/*      */     //   1740: sipush #410
/*      */     //   1743: if_icmpne -> 1761
/*      */     //   1746: new java/io/FileNotFoundException
/*      */     //   1749: dup
/*      */     //   1750: aload_0
/*      */     //   1751: getfield url : Ljava/net/URL;
/*      */     //   1754: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   1757: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1760: athrow
/*      */     //   1761: new java/io/IOException
/*      */     //   1764: dup
/*      */     //   1765: new java/lang/StringBuilder
/*      */     //   1768: dup
/*      */     //   1769: invokespecial <init> : ()V
/*      */     //   1772: ldc_w 'Server returned HTTP response code: '
/*      */     //   1775: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1778: iload_2
/*      */     //   1779: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*      */     //   1782: ldc_w ' for URL: '
/*      */     //   1785: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1788: aload_0
/*      */     //   1789: getfield url : Ljava/net/URL;
/*      */     //   1792: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   1795: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1798: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   1801: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1804: athrow
/*      */     //   1805: aload_0
/*      */     //   1806: aconst_null
/*      */     //   1807: putfield poster : Lsun/net/www/http/PosterOutputStream;
/*      */     //   1810: aload_0
/*      */     //   1811: aconst_null
/*      */     //   1812: putfield strOutputStream : Lsun/net/www/protocol/http/HttpURLConnection$StreamingOutputStream;
/*      */     //   1815: aload_0
/*      */     //   1816: getfield inputStream : Ljava/io/InputStream;
/*      */     //   1819: astore #13
/*      */     //   1821: aload_0
/*      */     //   1822: getfield proxyAuthKey : Ljava/lang/String;
/*      */     //   1825: ifnull -> 1835
/*      */     //   1828: aload_0
/*      */     //   1829: getfield proxyAuthKey : Ljava/lang/String;
/*      */     //   1832: invokestatic endAuthRequest : (Ljava/lang/String;)V
/*      */     //   1835: aload_0
/*      */     //   1836: getfield serverAuthKey : Ljava/lang/String;
/*      */     //   1839: ifnull -> 1849
/*      */     //   1842: aload_0
/*      */     //   1843: getfield serverAuthKey : Ljava/lang/String;
/*      */     //   1846: invokestatic endAuthRequest : (Ljava/lang/String;)V
/*      */     //   1849: aload #13
/*      */     //   1851: areturn
/*      */     //   1852: iload_1
/*      */     //   1853: getstatic sun/net/www/protocol/http/HttpURLConnection.maxRedirects : I
/*      */     //   1856: if_icmplt -> 187
/*      */     //   1859: new java/net/ProtocolException
/*      */     //   1862: dup
/*      */     //   1863: new java/lang/StringBuilder
/*      */     //   1866: dup
/*      */     //   1867: invokespecial <init> : ()V
/*      */     //   1870: ldc_w 'Server redirected too many  times ('
/*      */     //   1873: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1876: iload_1
/*      */     //   1877: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*      */     //   1880: ldc_w ')'
/*      */     //   1883: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1886: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   1889: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1892: athrow
/*      */     //   1893: astore #10
/*      */     //   1895: aload_0
/*      */     //   1896: invokespecial disconnectInternal : ()V
/*      */     //   1899: aload_0
/*      */     //   1900: aload #10
/*      */     //   1902: putfield rememberedException : Ljava/lang/Exception;
/*      */     //   1905: aload #10
/*      */     //   1907: athrow
/*      */     //   1908: astore #10
/*      */     //   1910: aload_0
/*      */     //   1911: aload #10
/*      */     //   1913: putfield rememberedException : Ljava/lang/Exception;
/*      */     //   1916: aload_0
/*      */     //   1917: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   1920: ldc 'Transfer-Encoding'
/*      */     //   1922: invokevirtual findValue : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1925: astore #11
/*      */     //   1927: aload_0
/*      */     //   1928: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   1931: ifnull -> 1987
/*      */     //   1934: aload_0
/*      */     //   1935: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   1938: invokevirtual isKeepingAlive : ()Z
/*      */     //   1941: ifeq -> 1987
/*      */     //   1944: getstatic sun/net/www/protocol/http/HttpURLConnection.enableESBuffer : Z
/*      */     //   1947: ifeq -> 1987
/*      */     //   1950: lload_3
/*      */     //   1951: lconst_0
/*      */     //   1952: lcmp
/*      */     //   1953: ifgt -> 1971
/*      */     //   1956: aload #11
/*      */     //   1958: ifnull -> 1987
/*      */     //   1961: aload #11
/*      */     //   1963: ldc 'chunked'
/*      */     //   1965: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
/*      */     //   1968: ifeq -> 1987
/*      */     //   1971: aload_0
/*      */     //   1972: aload_0
/*      */     //   1973: getfield inputStream : Ljava/io/InputStream;
/*      */     //   1976: lload_3
/*      */     //   1977: aload_0
/*      */     //   1978: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   1981: invokestatic getErrorStream : (Ljava/io/InputStream;JLsun/net/www/http/HttpClient;)Ljava/io/InputStream;
/*      */     //   1984: putfield errorStream : Ljava/io/InputStream;
/*      */     //   1987: aload #10
/*      */     //   1989: athrow
/*      */     //   1990: astore #20
/*      */     //   1992: aload_0
/*      */     //   1993: getfield proxyAuthKey : Ljava/lang/String;
/*      */     //   1996: ifnull -> 2006
/*      */     //   1999: aload_0
/*      */     //   2000: getfield proxyAuthKey : Ljava/lang/String;
/*      */     //   2003: invokestatic endAuthRequest : (Ljava/lang/String;)V
/*      */     //   2006: aload_0
/*      */     //   2007: getfield serverAuthKey : Ljava/lang/String;
/*      */     //   2010: ifnull -> 2020
/*      */     //   2013: aload_0
/*      */     //   2014: getfield serverAuthKey : Ljava/lang/String;
/*      */     //   2017: invokestatic endAuthRequest : (Ljava/lang/String;)V
/*      */     //   2020: aload #20
/*      */     //   2022: athrow
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1499	-> 0
/*      */     //   #1500	-> 7
/*      */     //   #1504	-> 18
/*      */     //   #1505	-> 25
/*      */     //   #1506	-> 35
/*      */     //   #1508	-> 47
/*      */     //   #1512	-> 59
/*      */     //   #1513	-> 66
/*      */     //   #1516	-> 71
/*      */     //   #1517	-> 78
/*      */     //   #1518	-> 85
/*      */     //   #1521	-> 90
/*      */     //   #1522	-> 97
/*      */     //   #1523	-> 107
/*      */     //   #1527	-> 118
/*      */     //   #1528	-> 120
/*      */     //   #1529	-> 122
/*      */     //   #1530	-> 126
/*      */     //   #1531	-> 129
/*      */     //   #1532	-> 132
/*      */     //   #1554	-> 135
/*      */     //   #1555	-> 138
/*      */     //   #1558	-> 141
/*      */     //   #1559	-> 164
/*      */     //   #1563	-> 187
/*      */     //   #1564	-> 194
/*      */     //   #1566	-> 198
/*      */     //   #1567	-> 205
/*      */     //   #1920	-> 211
/*      */     //   #1921	-> 218
/*      */     //   #1923	-> 225
/*      */     //   #1924	-> 232
/*      */     //   #1567	-> 239
/*      */     //   #1571	-> 242
/*      */     //   #1573	-> 258
/*      */     //   #1574	-> 263
/*      */     //   #1575	-> 282
/*      */     //   #1582	-> 289
/*      */     //   #1584	-> 303
/*      */     //   #1585	-> 310
/*      */     //   #1587	-> 314
/*      */     //   #1588	-> 331
/*      */     //   #1589	-> 343
/*      */     //   #1592	-> 356
/*      */     //   #1593	-> 368
/*      */     //   #1594	-> 380
/*      */     //   #1595	-> 390
/*      */     //   #1596	-> 402
/*      */     //   #1597	-> 411
/*      */     //   #1601	-> 424
/*      */     //   #1603	-> 435
/*      */     //   #1604	-> 440
/*      */     //   #1605	-> 445
/*      */     //   #1606	-> 449
/*      */     //   #1608	-> 460
/*      */     //   #1609	-> 467
/*      */     //   #1610	-> 474
/*      */     //   #1611	-> 478
/*      */     //   #1616	-> 492
/*      */     //   #1617	-> 495
/*      */     //   #1618	-> 507
/*      */     //   #1619	-> 517
/*      */     //   #1620	-> 532
/*      */     //   #1621	-> 548
/*      */     //   #1622	-> 554
/*      */     //   #1623	-> 559
/*      */     //   #1625	-> 565
/*      */     //   #1626	-> 568
/*      */     //   #1627	-> 573
/*      */     //   #1629	-> 576
/*      */     //   #1631	-> 579
/*      */     //   #1639	-> 582
/*      */     //   #1643	-> 605
/*      */     //   #1644	-> 612
/*      */     //   #1649	-> 628
/*      */     //   #1650	-> 635
/*      */     //   #1651	-> 640
/*      */     //   #1652	-> 645
/*      */     //   #1653	-> 650
/*      */     //   #1654	-> 653
/*      */     //   #1655	-> 657
/*      */     //   #1659	-> 660
/*      */     //   #1660	-> 672
/*      */     //   #1661	-> 676
/*      */     //   #1662	-> 681
/*      */     //   #1661	-> 686
/*      */     //   #1663	-> 692
/*      */     //   #1664	-> 696
/*      */     //   #1666	-> 707
/*      */     //   #1668	-> 722
/*      */     //   #1667	-> 727
/*      */     //   #1669	-> 733
/*      */     //   #1670	-> 737
/*      */     //   #1672	-> 748
/*      */     //   #1673	-> 753
/*      */     //   #1674	-> 758
/*      */     //   #1676	-> 761
/*      */     //   #1677	-> 764
/*      */     //   #1678	-> 767
/*      */     //   #1679	-> 772
/*      */     //   #1680	-> 779
/*      */     //   #1684	-> 789
/*      */     //   #1686	-> 794
/*      */     //   #1689	-> 799
/*      */     //   #1690	-> 806
/*      */     //   #1691	-> 813
/*      */     //   #1692	-> 817
/*      */     //   #1697	-> 831
/*      */     //   #1698	-> 834
/*      */     //   #1699	-> 846
/*      */     //   #1700	-> 856
/*      */     //   #1701	-> 871
/*      */     //   #1702	-> 887
/*      */     //   #1703	-> 893
/*      */     //   #1704	-> 898
/*      */     //   #1706	-> 904
/*      */     //   #1707	-> 907
/*      */     //   #1708	-> 912
/*      */     //   #1710	-> 915
/*      */     //   #1712	-> 918
/*      */     //   #1714	-> 921
/*      */     //   #1720	-> 950
/*      */     //   #1721	-> 957
/*      */     //   #1722	-> 964
/*      */     //   #1723	-> 971
/*      */     //   #1724	-> 980
/*      */     //   #1726	-> 990
/*      */     //   #1727	-> 994
/*      */     //   #1728	-> 997
/*      */     //   #1729	-> 1016
/*      */     //   #1728	-> 1019
/*      */     //   #1730	-> 1022
/*      */     //   #1731	-> 1028
/*      */     //   #1732	-> 1032
/*      */     //   #1734	-> 1035
/*      */     //   #1737	-> 1040
/*      */     //   #1738	-> 1048
/*      */     //   #1740	-> 1054
/*      */     //   #1741	-> 1059
/*      */     //   #1742	-> 1063
/*      */     //   #1743	-> 1066
/*      */     //   #1744	-> 1070
/*      */     //   #1747	-> 1073
/*      */     //   #1749	-> 1077
/*      */     //   #1750	-> 1089
/*      */     //   #1751	-> 1093
/*      */     //   #1753	-> 1104
/*      */     //   #1754	-> 1109
/*      */     //   #1755	-> 1114
/*      */     //   #1756	-> 1118
/*      */     //   #1760	-> 1121
/*      */     //   #1762	-> 1126
/*      */     //   #1764	-> 1141
/*      */     //   #1766	-> 1149
/*      */     //   #1767	-> 1161
/*      */     //   #1768	-> 1168
/*      */     //   #1770	-> 1191
/*      */     //   #1773	-> 1200
/*      */     //   #1774	-> 1202
/*      */     //   #1775	-> 1210
/*      */     //   #1776	-> 1215
/*      */     //   #1777	-> 1222
/*      */     //   #1779	-> 1226
/*      */     //   #1782	-> 1234
/*      */     //   #1784	-> 1241
/*      */     //   #1785	-> 1256
/*      */     //   #1786	-> 1263
/*      */     //   #1787	-> 1270
/*      */     //   #1788	-> 1279
/*      */     //   #1789	-> 1287
/*      */     //   #1792	-> 1294
/*      */     //   #1793	-> 1309
/*      */     //   #1795	-> 1332
/*      */     //   #1796	-> 1337
/*      */     //   #1797	-> 1342
/*      */     //   #1804	-> 1345
/*      */     //   #1805	-> 1348
/*      */     //   #1808	-> 1351
/*      */     //   #1809	-> 1356
/*      */     //   #1810	-> 1361
/*      */     //   #1811	-> 1368
/*      */     //   #1812	-> 1378
/*      */     //   #1813	-> 1385
/*      */     //   #1815	-> 1395
/*      */     //   #1816	-> 1402
/*      */     //   #1818	-> 1410
/*      */     //   #1822	-> 1415
/*      */     //   #1824	-> 1420
/*      */     //   #1829	-> 1427
/*      */     //   #1833	-> 1430
/*      */     //   #1835	-> 1434
/*      */     //   #1839	-> 1437
/*      */     //   #1840	-> 1451
/*      */     //   #1842	-> 1456
/*      */     //   #1846	-> 1489
/*      */     //   #1847	-> 1496
/*      */     //   #1848	-> 1503
/*      */     //   #1850	-> 1508
/*      */     //   #1851	-> 1515
/*      */     //   #1852	-> 1520
/*      */     //   #1853	-> 1531
/*      */     //   #1856	-> 1536
/*      */     //   #1858	-> 1578
/*      */     //   #1860	-> 1592
/*      */     //   #1861	-> 1601
/*      */     //   #1862	-> 1606
/*      */     //   #1863	-> 1609
/*      */     //   #1868	-> 1623
/*      */     //   #1872	-> 1642
/*      */     //   #1869	-> 1645
/*      */     //   #1874	-> 1647
/*      */     //   #1875	-> 1655
/*      */     //   #1876	-> 1660
/*      */     //   #1877	-> 1672
/*      */     //   #1878	-> 1681
/*      */     //   #1884	-> 1699
/*      */     //   #1885	-> 1709
/*      */     //   #1888	-> 1725
/*      */     //   #1889	-> 1732
/*      */     //   #1890	-> 1746
/*      */     //   #1892	-> 1761
/*      */     //   #1894	-> 1792
/*      */     //   #1897	-> 1805
/*      */     //   #1898	-> 1810
/*      */     //   #1899	-> 1815
/*      */     //   #1920	-> 1821
/*      */     //   #1921	-> 1828
/*      */     //   #1923	-> 1835
/*      */     //   #1924	-> 1842
/*      */     //   #1899	-> 1849
/*      */     //   #1900	-> 1852
/*      */     //   #1902	-> 1859
/*      */     //   #1904	-> 1893
/*      */     //   #1905	-> 1895
/*      */     //   #1906	-> 1899
/*      */     //   #1907	-> 1905
/*      */     //   #1908	-> 1908
/*      */     //   #1909	-> 1910
/*      */     //   #1913	-> 1916
/*      */     //   #1914	-> 1927
/*      */     //   #1915	-> 1965
/*      */     //   #1916	-> 1971
/*      */     //   #1918	-> 1987
/*      */     //   #1920	-> 1990
/*      */     //   #1921	-> 1999
/*      */     //   #1923	-> 2006
/*      */     //   #1924	-> 2013
/*      */     //   #1926	-> 2020
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   187	211	1893	java/lang/RuntimeException
/*      */     //   187	211	1908	java/io/IOException
/*      */     //   187	211	1990	finally
/*      */     //   242	1821	1893	java/lang/RuntimeException
/*      */     //   242	1821	1908	java/io/IOException
/*      */     //   242	1821	1990	finally
/*      */     //   1294	1337	1340	java/lang/Exception
/*      */     //   1437	1451	1454	java/lang/Exception
/*      */     //   1623	1642	1645	java/lang/IllegalAccessException
/*      */     //   1623	1642	1645	java/lang/NoSuchFieldException
/*      */     //   1852	1893	1893	java/lang/RuntimeException
/*      */     //   1852	1893	1908	java/io/IOException
/*      */     //   1852	1992	1990	finally }
/*      */   private IOException getChainedException(final IOException rememberedException) { try { final Object[] args = { rememberedException.getMessage() }; IOException iOException = AccessController.<IOException>doPrivileged(new PrivilegedExceptionAction<IOException>() { public IOException run() throws Exception { return rememberedException.getClass().getConstructor(new Class[] { String.class }).newInstance(args); } }
/*      */         ); iOException.initCause(rememberedException); return iOException; } catch (Exception exception) { return rememberedException; }  }
/*      */   public InputStream getErrorStream() { if (this.connected && this.responseCode >= 400) { if (this.errorStream != null) return this.errorStream;  if (this.inputStream != null) return this.inputStream;  }  return null; }
/*      */   private AuthenticationInfo resetProxyAuthentication(AuthenticationInfo paramAuthenticationInfo, AuthenticationHeader paramAuthenticationHeader) throws IOException { if (paramAuthenticationInfo != null && paramAuthenticationInfo.getAuthScheme() != AuthScheme.NTLM) { String str = paramAuthenticationHeader.raw(); if (paramAuthenticationInfo.isAuthorizationStale(str)) { String str1; if (paramAuthenticationInfo instanceof DigestAuthentication) { DigestAuthentication digestAuthentication = (DigestAuthentication)paramAuthenticationInfo; if (tunnelState() == TunnelState.SETUP) { str1 = digestAuthentication.getHeaderValue(connectRequestURI(this.url), HTTP_CONNECT); } else { str1 = digestAuthentication.getHeaderValue(getRequestURI(), this.method); }  } else { str1 = paramAuthenticationInfo.getHeaderValue(this.url, this.method); }  this.requests.set(paramAuthenticationInfo.getHeaderName(), str1); this.currentProxyCredentials = paramAuthenticationInfo; return paramAuthenticationInfo; }  paramAuthenticationInfo.removeFromCache(); }  paramAuthenticationInfo = getHttpProxyAuthentication(paramAuthenticationHeader); this.currentProxyCredentials = paramAuthenticationInfo; return paramAuthenticationInfo; }
/*      */   TunnelState tunnelState() { return this.tunnelState; }
/*      */   public void setTunnelState(TunnelState paramTunnelState) { this.tunnelState = paramTunnelState; }
/*      */   public synchronized void doTunneling() throws IOException { // Byte code:
/*      */     //   0: iconst_0
/*      */     //   1: istore_1
/*      */     //   2: ldc_w ''
/*      */     //   5: astore_2
/*      */     //   6: iconst_0
/*      */     //   7: istore_3
/*      */     //   8: aconst_null
/*      */     //   9: astore #4
/*      */     //   11: aconst_null
/*      */     //   12: astore #5
/*      */     //   14: iconst_m1
/*      */     //   15: istore #6
/*      */     //   17: aload_0
/*      */     //   18: getfield requests : Lsun/net/www/MessageHeader;
/*      */     //   21: astore #7
/*      */     //   23: aload_0
/*      */     //   24: new sun/net/www/MessageHeader
/*      */     //   27: dup
/*      */     //   28: invokespecial <init> : ()V
/*      */     //   31: putfield requests : Lsun/net/www/MessageHeader;
/*      */     //   34: iconst_0
/*      */     //   35: istore #8
/*      */     //   37: aload_0
/*      */     //   38: getstatic sun/net/www/protocol/http/HttpURLConnection$TunnelState.SETUP : Lsun/net/www/protocol/http/HttpURLConnection$TunnelState;
/*      */     //   41: invokevirtual setTunnelState : (Lsun/net/www/protocol/http/HttpURLConnection$TunnelState;)V
/*      */     //   44: aload_0
/*      */     //   45: invokespecial checkReuseConnection : ()Z
/*      */     //   48: ifne -> 64
/*      */     //   51: aload_0
/*      */     //   52: aload_0
/*      */     //   53: getfield url : Ljava/net/URL;
/*      */     //   56: aload #5
/*      */     //   58: iload #6
/*      */     //   60: iconst_0
/*      */     //   61: invokevirtual proxiedConnect : (Ljava/net/URL;Ljava/lang/String;IZ)V
/*      */     //   64: aload_0
/*      */     //   65: invokespecial sendCONNECTRequest : ()V
/*      */     //   68: aload_0
/*      */     //   69: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   72: invokevirtual reset : ()V
/*      */     //   75: aload_0
/*      */     //   76: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   79: aload_0
/*      */     //   80: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   83: aconst_null
/*      */     //   84: aload_0
/*      */     //   85: invokevirtual parseHTTP : (Lsun/net/www/MessageHeader;Lsun/net/ProgressSource;Lsun/net/www/protocol/http/HttpURLConnection;)Z
/*      */     //   88: pop
/*      */     //   89: getstatic sun/net/www/protocol/http/HttpURLConnection.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   92: getstatic sun/util/logging/PlatformLogger$Level.FINE : Lsun/util/logging/PlatformLogger$Level;
/*      */     //   95: invokevirtual isLoggable : (Lsun/util/logging/PlatformLogger$Level;)Z
/*      */     //   98: ifeq -> 114
/*      */     //   101: getstatic sun/net/www/protocol/http/HttpURLConnection.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   104: aload_0
/*      */     //   105: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   108: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   111: invokevirtual fine : (Ljava/lang/String;)V
/*      */     //   114: aload_0
/*      */     //   115: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   118: ldc_w 'Proxy-Authenticate'
/*      */     //   121: invokevirtual filterNTLMResponses : (Ljava/lang/String;)Z
/*      */     //   124: ifeq -> 161
/*      */     //   127: getstatic sun/net/www/protocol/http/HttpURLConnection.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   130: getstatic sun/util/logging/PlatformLogger$Level.FINE : Lsun/util/logging/PlatformLogger$Level;
/*      */     //   133: invokevirtual isLoggable : (Lsun/util/logging/PlatformLogger$Level;)Z
/*      */     //   136: ifeq -> 161
/*      */     //   139: getstatic sun/net/www/protocol/http/HttpURLConnection.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   142: ldc_w '>>>> Headers are filtered'
/*      */     //   145: invokevirtual fine : (Ljava/lang/String;)V
/*      */     //   148: getstatic sun/net/www/protocol/http/HttpURLConnection.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   151: aload_0
/*      */     //   152: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   155: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   158: invokevirtual fine : (Ljava/lang/String;)V
/*      */     //   161: aload_0
/*      */     //   162: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   165: iconst_0
/*      */     //   166: invokevirtual getValue : (I)Ljava/lang/String;
/*      */     //   169: astore_2
/*      */     //   170: new java/util/StringTokenizer
/*      */     //   173: dup
/*      */     //   174: aload_2
/*      */     //   175: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   178: astore #9
/*      */     //   180: aload #9
/*      */     //   182: invokevirtual nextToken : ()Ljava/lang/String;
/*      */     //   185: pop
/*      */     //   186: aload #9
/*      */     //   188: invokevirtual nextToken : ()Ljava/lang/String;
/*      */     //   191: invokevirtual trim : ()Ljava/lang/String;
/*      */     //   194: invokestatic parseInt : (Ljava/lang/String;)I
/*      */     //   197: istore_3
/*      */     //   198: iload_3
/*      */     //   199: sipush #407
/*      */     //   202: if_icmpne -> 451
/*      */     //   205: iconst_0
/*      */     //   206: istore #10
/*      */     //   208: aload_0
/*      */     //   209: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   212: ldc_w 'Proxy-Authenticate'
/*      */     //   215: invokevirtual multiValueIterator : (Ljava/lang/String;)Ljava/util/Iterator;
/*      */     //   218: astore #11
/*      */     //   220: aload #11
/*      */     //   222: invokeinterface hasNext : ()Z
/*      */     //   227: ifeq -> 295
/*      */     //   230: aload #11
/*      */     //   232: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   237: checkcast java/lang/String
/*      */     //   240: invokevirtual trim : ()Ljava/lang/String;
/*      */     //   243: astore #12
/*      */     //   245: aload #12
/*      */     //   247: ldc_w 'Negotiate'
/*      */     //   250: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
/*      */     //   253: ifne -> 267
/*      */     //   256: aload #12
/*      */     //   258: ldc_w 'Kerberos'
/*      */     //   261: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
/*      */     //   264: ifeq -> 292
/*      */     //   267: iload #8
/*      */     //   269: ifne -> 278
/*      */     //   272: iconst_1
/*      */     //   273: istore #8
/*      */     //   275: goto -> 295
/*      */     //   278: iconst_1
/*      */     //   279: istore #10
/*      */     //   281: aload_0
/*      */     //   282: iconst_0
/*      */     //   283: putfield doingNTLMp2ndStage : Z
/*      */     //   286: aconst_null
/*      */     //   287: astore #4
/*      */     //   289: goto -> 295
/*      */     //   292: goto -> 220
/*      */     //   295: new sun/net/www/protocol/http/AuthenticationHeader
/*      */     //   298: dup
/*      */     //   299: ldc_w 'Proxy-Authenticate'
/*      */     //   302: aload_0
/*      */     //   303: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   306: new sun/net/www/protocol/http/HttpCallerInfo
/*      */     //   309: dup
/*      */     //   310: aload_0
/*      */     //   311: getfield url : Ljava/net/URL;
/*      */     //   314: aload_0
/*      */     //   315: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   318: invokevirtual getProxyHostUsed : ()Ljava/lang/String;
/*      */     //   321: aload_0
/*      */     //   322: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   325: invokevirtual getProxyPortUsed : ()I
/*      */     //   328: invokespecial <init> : (Ljava/net/URL;Ljava/lang/String;I)V
/*      */     //   331: iload #10
/*      */     //   333: getstatic sun/net/www/protocol/http/HttpURLConnection.disabledTunnelingSchemes : Ljava/util/Set;
/*      */     //   336: invokespecial <init> : (Ljava/lang/String;Lsun/net/www/MessageHeader;Lsun/net/www/protocol/http/HttpCallerInfo;ZLjava/util/Set;)V
/*      */     //   339: astore #12
/*      */     //   341: aload_0
/*      */     //   342: getfield doingNTLMp2ndStage : Z
/*      */     //   345: ifne -> 391
/*      */     //   348: aload_0
/*      */     //   349: aload #4
/*      */     //   351: aload #12
/*      */     //   353: invokespecial resetProxyAuthentication : (Lsun/net/www/protocol/http/AuthenticationInfo;Lsun/net/www/protocol/http/AuthenticationHeader;)Lsun/net/www/protocol/http/AuthenticationInfo;
/*      */     //   356: astore #4
/*      */     //   358: aload #4
/*      */     //   360: ifnull -> 451
/*      */     //   363: aload_0
/*      */     //   364: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   367: invokevirtual getProxyHostUsed : ()Ljava/lang/String;
/*      */     //   370: astore #5
/*      */     //   372: aload_0
/*      */     //   373: getfield http : Lsun/net/www/http/HttpClient;
/*      */     //   376: invokevirtual getProxyPortUsed : ()I
/*      */     //   379: istore #6
/*      */     //   381: aload_0
/*      */     //   382: invokespecial disconnectInternal : ()V
/*      */     //   385: iinc #1, 1
/*      */     //   388: goto -> 492
/*      */     //   391: aload_0
/*      */     //   392: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   395: ldc_w 'Proxy-Authenticate'
/*      */     //   398: invokevirtual findValue : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   401: astore #13
/*      */     //   403: aload_0
/*      */     //   404: invokespecial reset : ()V
/*      */     //   407: aload #4
/*      */     //   409: aload_0
/*      */     //   410: aload #12
/*      */     //   412: invokevirtual headerParser : ()Lsun/net/www/HeaderParser;
/*      */     //   415: aload #13
/*      */     //   417: invokevirtual setHeaders : (Lsun/net/www/protocol/http/HttpURLConnection;Lsun/net/www/HeaderParser;Ljava/lang/String;)Z
/*      */     //   420: ifne -> 438
/*      */     //   423: aload_0
/*      */     //   424: invokespecial disconnectInternal : ()V
/*      */     //   427: new java/io/IOException
/*      */     //   430: dup
/*      */     //   431: ldc_w 'Authentication failure'
/*      */     //   434: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   437: athrow
/*      */     //   438: aload_0
/*      */     //   439: aconst_null
/*      */     //   440: putfield authObj : Ljava/lang/Object;
/*      */     //   443: aload_0
/*      */     //   444: iconst_0
/*      */     //   445: putfield doingNTLMp2ndStage : Z
/*      */     //   448: goto -> 492
/*      */     //   451: aload #4
/*      */     //   453: ifnull -> 461
/*      */     //   456: aload #4
/*      */     //   458: invokevirtual addToCache : ()V
/*      */     //   461: iload_3
/*      */     //   462: sipush #200
/*      */     //   465: if_icmpne -> 478
/*      */     //   468: aload_0
/*      */     //   469: getstatic sun/net/www/protocol/http/HttpURLConnection$TunnelState.TUNNELING : Lsun/net/www/protocol/http/HttpURLConnection$TunnelState;
/*      */     //   472: invokevirtual setTunnelState : (Lsun/net/www/protocol/http/HttpURLConnection$TunnelState;)V
/*      */     //   475: goto -> 499
/*      */     //   478: aload_0
/*      */     //   479: invokespecial disconnectInternal : ()V
/*      */     //   482: aload_0
/*      */     //   483: getstatic sun/net/www/protocol/http/HttpURLConnection$TunnelState.NONE : Lsun/net/www/protocol/http/HttpURLConnection$TunnelState;
/*      */     //   486: invokevirtual setTunnelState : (Lsun/net/www/protocol/http/HttpURLConnection$TunnelState;)V
/*      */     //   489: goto -> 499
/*      */     //   492: iload_1
/*      */     //   493: getstatic sun/net/www/protocol/http/HttpURLConnection.maxRedirects : I
/*      */     //   496: if_icmplt -> 44
/*      */     //   499: iload_1
/*      */     //   500: getstatic sun/net/www/protocol/http/HttpURLConnection.maxRedirects : I
/*      */     //   503: if_icmpge -> 513
/*      */     //   506: iload_3
/*      */     //   507: sipush #200
/*      */     //   510: if_icmpeq -> 547
/*      */     //   513: new java/io/IOException
/*      */     //   516: dup
/*      */     //   517: new java/lang/StringBuilder
/*      */     //   520: dup
/*      */     //   521: invokespecial <init> : ()V
/*      */     //   524: ldc_w 'Unable to tunnel through proxy. Proxy returns "'
/*      */     //   527: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   530: aload_2
/*      */     //   531: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   534: ldc_w '"'
/*      */     //   537: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   540: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   543: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   546: athrow
/*      */     //   547: aload_0
/*      */     //   548: getfield proxyAuthKey : Ljava/lang/String;
/*      */     //   551: ifnull -> 583
/*      */     //   554: aload_0
/*      */     //   555: getfield proxyAuthKey : Ljava/lang/String;
/*      */     //   558: invokestatic endAuthRequest : (Ljava/lang/String;)V
/*      */     //   561: goto -> 583
/*      */     //   564: astore #14
/*      */     //   566: aload_0
/*      */     //   567: getfield proxyAuthKey : Ljava/lang/String;
/*      */     //   570: ifnull -> 580
/*      */     //   573: aload_0
/*      */     //   574: getfield proxyAuthKey : Ljava/lang/String;
/*      */     //   577: invokestatic endAuthRequest : (Ljava/lang/String;)V
/*      */     //   580: aload #14
/*      */     //   582: athrow
/*      */     //   583: aload_0
/*      */     //   584: aload #7
/*      */     //   586: putfield requests : Lsun/net/www/MessageHeader;
/*      */     //   589: aload_0
/*      */     //   590: getfield responses : Lsun/net/www/MessageHeader;
/*      */     //   593: invokevirtual reset : ()V
/*      */     //   596: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #2026	-> 0
/*      */     //   #2027	-> 2
/*      */     //   #2028	-> 6
/*      */     //   #2029	-> 8
/*      */     //   #2030	-> 11
/*      */     //   #2031	-> 14
/*      */     //   #2034	-> 17
/*      */     //   #2035	-> 23
/*      */     //   #2038	-> 34
/*      */     //   #2042	-> 37
/*      */     //   #2045	-> 44
/*      */     //   #2046	-> 51
/*      */     //   #2050	-> 64
/*      */     //   #2051	-> 68
/*      */     //   #2055	-> 75
/*      */     //   #2058	-> 89
/*      */     //   #2059	-> 101
/*      */     //   #2062	-> 114
/*      */     //   #2063	-> 127
/*      */     //   #2064	-> 139
/*      */     //   #2065	-> 148
/*      */     //   #2069	-> 161
/*      */     //   #2070	-> 170
/*      */     //   #2071	-> 180
/*      */     //   #2072	-> 186
/*      */     //   #2073	-> 198
/*      */     //   #2075	-> 205
/*      */     //   #2076	-> 208
/*      */     //   #2077	-> 220
/*      */     //   #2078	-> 230
/*      */     //   #2079	-> 245
/*      */     //   #2080	-> 261
/*      */     //   #2081	-> 267
/*      */     //   #2082	-> 272
/*      */     //   #2084	-> 278
/*      */     //   #2085	-> 281
/*      */     //   #2086	-> 286
/*      */     //   #2088	-> 289
/*      */     //   #2090	-> 292
/*      */     //   #2092	-> 295
/*      */     //   #2096	-> 318
/*      */     //   #2097	-> 325
/*      */     //   #2101	-> 341
/*      */     //   #2102	-> 348
/*      */     //   #2103	-> 353
/*      */     //   #2104	-> 358
/*      */     //   #2105	-> 363
/*      */     //   #2106	-> 372
/*      */     //   #2107	-> 381
/*      */     //   #2108	-> 385
/*      */     //   #2109	-> 388
/*      */     //   #2112	-> 391
/*      */     //   #2113	-> 403
/*      */     //   #2114	-> 407
/*      */     //   #2115	-> 412
/*      */     //   #2114	-> 417
/*      */     //   #2116	-> 423
/*      */     //   #2117	-> 427
/*      */     //   #2119	-> 438
/*      */     //   #2120	-> 443
/*      */     //   #2121	-> 448
/*      */     //   #2125	-> 451
/*      */     //   #2127	-> 456
/*      */     //   #2130	-> 461
/*      */     //   #2131	-> 468
/*      */     //   #2132	-> 475
/*      */     //   #2136	-> 478
/*      */     //   #2137	-> 482
/*      */     //   #2138	-> 489
/*      */     //   #2139	-> 492
/*      */     //   #2141	-> 499
/*      */     //   #2142	-> 513
/*      */     //   #2147	-> 547
/*      */     //   #2148	-> 554
/*      */     //   #2147	-> 564
/*      */     //   #2148	-> 573
/*      */     //   #2150	-> 580
/*      */     //   #2153	-> 583
/*      */     //   #2156	-> 589
/*      */     //   #2157	-> 596
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   37	547	564	finally
/*      */     //   564	566	564	finally }
/*      */   static String connectRequestURI(URL paramURL) { String str = paramURL.getHost(); int i = paramURL.getPort(); i = (i != -1) ? i : paramURL.getDefaultPort(); return str + ":" + i; }
/* 2861 */   private void disconnectWeb() throws IOException { if (usingProxy() && this.http.isKeepingAlive())
/* 2862 */     { this.responseCode = -1;
/*      */ 
/*      */       
/* 2865 */       reset(); }
/*      */     else
/* 2867 */     { disconnectInternal(); }  } private void sendCONNECTRequest() throws IOException { int i = this.url.getPort(); this.requests.set(0, HTTP_CONNECT + " " + connectRequestURI(this.url) + " " + "HTTP/1.1", null); this.requests.setIfNotSet("User-Agent", userAgent); String str = this.url.getHost(); if (i != -1 && i != this.url.getDefaultPort()) str = str + ":" + String.valueOf(i);  this.requests.setIfNotSet("Host", str); this.requests.setIfNotSet("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"); if (this.http.getHttpKeepAliveSet()) this.requests.setIfNotSet("Proxy-Connection", "keep-alive");  setPreemptiveProxyAuthentication(this.requests); if (logger.isLoggable(PlatformLogger.Level.FINE)) logger.fine(this.requests.toString());  this.http.writeRequests(this.requests, (PosterOutputStream)null); } private void setPreemptiveProxyAuthentication(MessageHeader paramMessageHeader) throws IOException { AuthenticationInfo authenticationInfo = AuthenticationInfo.getProxyAuth(this.http.getProxyHostUsed(), this.http.getProxyPortUsed()); if (authenticationInfo != null && authenticationInfo.supportsPreemptiveAuthorization()) { String str; if (authenticationInfo instanceof DigestAuthentication) { DigestAuthentication digestAuthentication = (DigestAuthentication)authenticationInfo; if (tunnelState() == TunnelState.SETUP) { str = digestAuthentication.getHeaderValue(connectRequestURI(this.url), HTTP_CONNECT); } else { str = digestAuthentication.getHeaderValue(getRequestURI(), this.method); }  } else { str = authenticationInfo.getHeaderValue(this.url, this.method); }  paramMessageHeader.set(authenticationInfo.getHeaderName(), str); this.currentProxyCredentials = authenticationInfo; }  } private AuthenticationInfo getHttpProxyAuthentication(AuthenticationHeader paramAuthenticationHeader) { AuthenticationInfo authenticationInfo = null; String str1 = paramAuthenticationHeader.raw(); String str2 = this.http.getProxyHostUsed(); int i = this.http.getProxyPortUsed(); if (str2 != null && paramAuthenticationHeader.isPresent()) { HeaderParser headerParser = paramAuthenticationHeader.headerParser(); String str3 = headerParser.findValue("realm"); String str4 = paramAuthenticationHeader.scheme(); AuthScheme authScheme = AuthScheme.UNKNOWN; if ("basic".equalsIgnoreCase(str4)) { authScheme = AuthScheme.BASIC; } else if ("digest".equalsIgnoreCase(str4)) { authScheme = AuthScheme.DIGEST; } else if ("ntlm".equalsIgnoreCase(str4)) { authScheme = AuthScheme.NTLM; this.doingNTLMp2ndStage = true; } else if ("Kerberos".equalsIgnoreCase(str4)) { authScheme = AuthScheme.KERBEROS; this.doingNTLMp2ndStage = true; } else if ("Negotiate".equalsIgnoreCase(str4)) { authScheme = AuthScheme.NEGOTIATE; this.doingNTLMp2ndStage = true; }  if (str3 == null) str3 = "";  this.proxyAuthKey = AuthenticationInfo.getProxyAuthKey(str2, i, str3, authScheme); authenticationInfo = AuthenticationInfo.getProxyAuth(this.proxyAuthKey); if (authenticationInfo == null) { InetAddress inetAddress; PasswordAuthentication passwordAuthentication; switch (authScheme) { case BASIC: inetAddress = null; try { final String finalHost = str2; inetAddress = AccessController.<InetAddress>doPrivileged(new PrivilegedExceptionAction<InetAddress>() { public InetAddress run() throws UnknownHostException { return InetAddress.getByName(finalHost); } }
/*      */                 ); } catch (PrivilegedActionException privilegedActionException) {} passwordAuthentication = privilegedRequestPasswordAuthentication(str2, inetAddress, i, "http", str3, str4, this.url, Authenticator.RequestorType.PROXY); if (passwordAuthentication != null) authenticationInfo = new BasicAuthentication(true, str2, i, str3, passwordAuthentication);  break;case DIGEST: passwordAuthentication = privilegedRequestPasswordAuthentication(str2, (InetAddress)null, i, this.url.getProtocol(), str3, str4, this.url, Authenticator.RequestorType.PROXY); if (passwordAuthentication != null) { DigestAuthentication.Parameters parameters = new DigestAuthentication.Parameters(); authenticationInfo = new DigestAuthentication(true, str2, i, str3, str4, passwordAuthentication, parameters); }  break;case NTLM: if (NTLMAuthenticationProxy.supported) { if (this.tryTransparentNTLMProxy) { this.tryTransparentNTLMProxy = NTLMAuthenticationProxy.supportsTransparentAuth; if (this.tryTransparentNTLMProxy && this.useProxyResponseCode) this.tryTransparentNTLMProxy = false;  }  passwordAuthentication = null; if (this.tryTransparentNTLMProxy) { logger.finest("Trying Transparent NTLM authentication"); } else { passwordAuthentication = privilegedRequestPasswordAuthentication(str2, (InetAddress)null, i, this.url.getProtocol(), "", str4, this.url, Authenticator.RequestorType.PROXY); }  if (this.tryTransparentNTLMProxy || (!this.tryTransparentNTLMProxy && passwordAuthentication != null)) authenticationInfo = NTLMAuthenticationProxy.proxy.create(true, str2, i, passwordAuthentication);  this.tryTransparentNTLMProxy = false; }  break;case NEGOTIATE: authenticationInfo = new NegotiateAuthentication(new HttpCallerInfo(paramAuthenticationHeader.getHttpCallerInfo(), "Negotiate")); break;case KERBEROS: authenticationInfo = new NegotiateAuthentication(new HttpCallerInfo(paramAuthenticationHeader.getHttpCallerInfo(), "Kerberos")); break;case UNKNOWN: if (logger.isLoggable(PlatformLogger.Level.FINEST)) logger.finest("Unknown/Unsupported authentication scheme: " + str4); default: throw new AssertionError("should not reach here"); }  }  if (authenticationInfo == null && defaultAuth != null && defaultAuth.schemeSupported(str4)) try { URL uRL = new URL("http", str2, i, "/"); final String finalHost = defaultAuth.authString(uRL, str4, str3); if (str != null) authenticationInfo = new BasicAuthentication(true, str2, i, str3, str);  } catch (MalformedURLException malformedURLException) {}  if (authenticationInfo != null && !authenticationInfo.setHeaders(this, headerParser, str1)) authenticationInfo = null;  }  if (logger.isLoggable(PlatformLogger.Level.FINER)) logger.finer("Proxy Authentication for " + paramAuthenticationHeader.toString() + " returned " + ((authenticationInfo != null) ? authenticationInfo.toString() : "null"));  return authenticationInfo; } private AuthenticationInfo getServerAuthentication(AuthenticationHeader paramAuthenticationHeader) { AuthenticationInfo authenticationInfo = null; String str = paramAuthenticationHeader.raw(); if (paramAuthenticationHeader.isPresent()) { HeaderParser headerParser = paramAuthenticationHeader.headerParser(); String str1 = headerParser.findValue("realm"); String str2 = paramAuthenticationHeader.scheme(); AuthScheme authScheme = AuthScheme.UNKNOWN; if ("basic".equalsIgnoreCase(str2)) { authScheme = AuthScheme.BASIC; } else if ("digest".equalsIgnoreCase(str2)) { authScheme = AuthScheme.DIGEST; } else if ("ntlm".equalsIgnoreCase(str2)) { authScheme = AuthScheme.NTLM; this.doingNTLM2ndStage = true; } else if ("Kerberos".equalsIgnoreCase(str2)) { authScheme = AuthScheme.KERBEROS; this.doingNTLM2ndStage = true; } else if ("Negotiate".equalsIgnoreCase(str2)) { authScheme = AuthScheme.NEGOTIATE; this.doingNTLM2ndStage = true; }  this.domain = headerParser.findValue("domain"); if (str1 == null) str1 = "";  this.serverAuthKey = AuthenticationInfo.getServerAuthKey(this.url, str1, authScheme); authenticationInfo = AuthenticationInfo.getServerAuth(this.serverAuthKey); InetAddress inetAddress = null; if (authenticationInfo == null) try { inetAddress = InetAddress.getByName(this.url.getHost()); } catch (UnknownHostException unknownHostException) {}  int i = this.url.getPort(); if (i == -1) i = this.url.getDefaultPort();  if (authenticationInfo == null) { PasswordAuthentication passwordAuthentication; switch (authScheme) { case KERBEROS: authenticationInfo = new NegotiateAuthentication(new HttpCallerInfo(paramAuthenticationHeader.getHttpCallerInfo(), "Kerberos")); break;case NEGOTIATE: authenticationInfo = new NegotiateAuthentication(new HttpCallerInfo(paramAuthenticationHeader.getHttpCallerInfo(), "Negotiate")); break;case BASIC: passwordAuthentication = privilegedRequestPasswordAuthentication(this.url.getHost(), inetAddress, i, this.url.getProtocol(), str1, str2, this.url, Authenticator.RequestorType.SERVER); if (passwordAuthentication != null) authenticationInfo = new BasicAuthentication(false, this.url, str1, passwordAuthentication);  break;case DIGEST: passwordAuthentication = privilegedRequestPasswordAuthentication(this.url.getHost(), inetAddress, i, this.url.getProtocol(), str1, str2, this.url, Authenticator.RequestorType.SERVER); if (passwordAuthentication != null) { this.digestparams = new DigestAuthentication.Parameters(); authenticationInfo = new DigestAuthentication(false, this.url, str1, str2, passwordAuthentication, this.digestparams); }  break;case NTLM: if (NTLMAuthenticationProxy.supported) { URL uRL; try { uRL = new URL(this.url, "/"); } catch (Exception exception) { uRL = this.url; }  if (this.tryTransparentNTLMServer) { this.tryTransparentNTLMServer = NTLMAuthenticationProxy.supportsTransparentAuth; if (this.tryTransparentNTLMServer) this.tryTransparentNTLMServer = NTLMAuthenticationProxy.isTrustedSite(this.url);  }  passwordAuthentication = null; if (this.tryTransparentNTLMServer) { logger.finest("Trying Transparent NTLM authentication"); } else { passwordAuthentication = privilegedRequestPasswordAuthentication(this.url.getHost(), inetAddress, i, this.url.getProtocol(), "", str2, this.url, Authenticator.RequestorType.SERVER); }  if (this.tryTransparentNTLMServer || (!this.tryTransparentNTLMServer && passwordAuthentication != null)) authenticationInfo = NTLMAuthenticationProxy.proxy.create(false, uRL, passwordAuthentication);  this.tryTransparentNTLMServer = false; }  break;case UNKNOWN: if (logger.isLoggable(PlatformLogger.Level.FINEST)) logger.finest("Unknown/Unsupported authentication scheme: " + str2); default: throw new AssertionError("should not reach here"); }  }  if (authenticationInfo == null && defaultAuth != null && defaultAuth.schemeSupported(str2)) { String str3 = defaultAuth.authString(this.url, str2, str1); if (str3 != null) authenticationInfo = new BasicAuthentication(false, this.url, str1, str3);  }  if (authenticationInfo != null && !authenticationInfo.setHeaders(this, headerParser, str)) authenticationInfo = null;  }  if (logger.isLoggable(PlatformLogger.Level.FINER)) logger.finer("Server Authentication for " + paramAuthenticationHeader.toString() + " returned " + ((authenticationInfo != null) ? authenticationInfo.toString() : "null"));  return authenticationInfo; }
/*      */   private void checkResponseCredentials(boolean paramBoolean) throws IOException { try { if (!this.needToCheck) return;  if (validateProxy && this.currentProxyCredentials != null && this.currentProxyCredentials instanceof DigestAuthentication) { String str = this.responses.findValue("Proxy-Authentication-Info"); if (paramBoolean || str != null) { DigestAuthentication digestAuthentication = (DigestAuthentication)this.currentProxyCredentials; digestAuthentication.checkResponse(str, this.method, getRequestURI()); this.currentProxyCredentials = null; }  }  if (validateServer && this.currentServerCredentials != null && this.currentServerCredentials instanceof DigestAuthentication) { String str = this.responses.findValue("Authentication-Info"); if (paramBoolean || str != null) { DigestAuthentication digestAuthentication = (DigestAuthentication)this.currentServerCredentials; digestAuthentication.checkResponse(str, this.method, this.url); this.currentServerCredentials = null; }  }  if (this.currentServerCredentials == null && this.currentProxyCredentials == null) this.needToCheck = false;  } catch (IOException iOException) { disconnectInternal(); this.connected = false; throw iOException; }  }
/*      */   String getRequestURI() throws IOException { if (this.requestURI == null) this.requestURI = this.http.getURLFile();  return this.requestURI; }
/*      */   private boolean followRedirect() throws IOException { URL uRL1; if (!getInstanceFollowRedirects()) return false;  final int stat = getResponseCode(); if (i < 300 || i > 307 || i == 306 || i == 304) return false;  final String loc = getHeaderField("Location"); if (str == null) return false;  try { uRL1 = new URL(str); if (!this.url.getProtocol().equalsIgnoreCase(uRL1.getProtocol())) return false;  } catch (MalformedURLException malformedURLException) { uRL1 = new URL(this.url, str); }  final URL locUrl0 = uRL1; this.socketPermission = null; SocketPermission socketPermission = URLtoSocketPermission(uRL1); if (socketPermission != null) try { return ((Boolean)AccessController.<Boolean>doPrivilegedWithCombiner(new PrivilegedExceptionAction<Boolean>() { public Boolean run() throws IOException { return Boolean.valueOf(HttpURLConnection.this.followRedirect0(loc, stat, locUrl0)); } }
/*      */             (AccessControlContext)null, new Permission[] { socketPermission })).booleanValue(); } catch (PrivilegedActionException privilegedActionException) { throw (IOException)privilegedActionException.getException(); }   return followRedirect0(str, i, uRL1); }
/*      */   private boolean followRedirect0(String paramString, int paramInt, URL paramURL) throws IOException { disconnectInternal(); if (streaming()) throw new HttpRetryException("cannot retry due to redirection, in streaming mode", paramInt, paramString);  if (logger.isLoggable(PlatformLogger.Level.FINE)) logger.fine("Redirected from " + this.url + " to " + paramURL);  this.responses = new MessageHeader(); if (paramInt == 305) { String str = paramURL.getHost(); int i = paramURL.getPort(); SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkConnect(str, i);  setProxiedClient(this.url, str, i); this.requests.set(0, this.method + " " + getRequestURI() + " " + "HTTP/1.1", null); this.connected = true; this.useProxyResponseCode = true; } else { URL uRL = this.url; this.url = paramURL; this.requestURI = null; if (this.method.equals("POST") && !Boolean.getBoolean("http.strictPostRedirect") && paramInt != 307) { this.requests = new MessageHeader(); this.setRequests = false; super.setRequestMethod("GET"); this.poster = null; if (!checkReuseConnection()) connect();  if (!sameDestination(uRL, this.url)) { this.userCookies = null; this.userCookies2 = null; }  } else { if (!checkReuseConnection()) connect();  if (this.http != null) { this.requests.set(0, this.method + " " + getRequestURI() + " " + "HTTP/1.1", null); int i = this.url.getPort(); String str = this.url.getHost(); if (i != -1 && i != this.url.getDefaultPort()) str = str + ":" + String.valueOf(i);  this.requests.set("Host", str); }  if (!sameDestination(uRL, this.url)) { this.userCookies = null; this.userCookies2 = null; this.requests.remove("Cookie"); this.requests.remove("Cookie2"); this.requests.remove("Authorization"); AuthenticationInfo authenticationInfo = AuthenticationInfo.getServerAuth(this.url); if (authenticationInfo != null && authenticationInfo.supportsPreemptiveAuthorization()) { this.requests.setIfNotSet(authenticationInfo.getHeaderName(), authenticationInfo.getHeaderValue(this.url, this.method)); this.currentServerCredentials = authenticationInfo; }  }  }  }  return true; }
/*      */   private static boolean sameDestination(URL paramURL1, URL paramURL2) { assert paramURL1.getProtocol().equalsIgnoreCase(paramURL2.getProtocol()) : "protocols not equal: " + paramURL1 + " - " + paramURL2; if (!paramURL1.getHost().equalsIgnoreCase(paramURL2.getHost())) return false;  int i = paramURL1.getPort(); if (i == -1) i = paramURL1.getDefaultPort();  int j = paramURL2.getPort(); if (j == -1) j = paramURL2.getDefaultPort();  if (i != j) return false;  return true; }
/* 2875 */   private void disconnectInternal() { this.responseCode = -1;
/* 2876 */     this.inputStream = null;
/* 2877 */     if (this.pi != null) {
/* 2878 */       this.pi.finishTracking();
/* 2879 */       this.pi = null;
/*      */     } 
/* 2881 */     if (this.http != null) {
/* 2882 */       this.http.closeServer();
/* 2883 */       this.http = null;
/* 2884 */       this.connected = false;
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disconnect() {
/* 2893 */     this.responseCode = -1;
/* 2894 */     if (this.pi != null) {
/* 2895 */       this.pi.finishTracking();
/* 2896 */       this.pi = null;
/*      */     } 
/*      */     
/* 2899 */     if (this.http != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2925 */       if (this.inputStream != null) {
/* 2926 */         HttpClient httpClient = this.http;
/*      */ 
/*      */         
/* 2929 */         boolean bool = httpClient.isKeepingAlive();
/*      */         
/*      */         try {
/* 2932 */           this.inputStream.close();
/* 2933 */         } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2940 */         if (bool) {
/* 2941 */           httpClient.closeIdleConnection();
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 2949 */         this.http.setDoNotRetry(true);
/*      */         
/* 2951 */         this.http.closeServer();
/*      */       } 
/*      */ 
/*      */       
/* 2955 */       this.http = null;
/* 2956 */       this.connected = false;
/*      */     } 
/* 2958 */     this.cachedInputStream = null;
/* 2959 */     if (this.cachedHeaders != null) {
/* 2960 */       this.cachedHeaders.reset();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean usingProxy() {
/* 2965 */     if (this.http != null) {
/* 2966 */       return (this.http.getProxyHostUsed() != null);
/*      */     }
/* 2968 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String filterHeaderField(String paramString1, String paramString2) {
/* 2982 */     if (paramString2 == null) {
/* 2983 */       return null;
/*      */     }
/* 2985 */     if ("set-cookie".equalsIgnoreCase(paramString1) || "set-cookie2"
/* 2986 */       .equalsIgnoreCase(paramString1)) {
/*      */ 
/*      */ 
/*      */       
/* 2990 */       if (this.cookieHandler == null || paramString2.length() == 0) {
/* 2991 */         return paramString2;
/*      */       }
/*      */       
/* 2994 */       JavaNetHttpCookieAccess javaNetHttpCookieAccess = SharedSecrets.getJavaNetHttpCookieAccess();
/* 2995 */       StringBuilder stringBuilder = new StringBuilder();
/* 2996 */       List<HttpCookie> list = javaNetHttpCookieAccess.parse(paramString2);
/* 2997 */       boolean bool = false;
/* 2998 */       for (HttpCookie httpCookie : list) {
/*      */         
/* 3000 */         if (httpCookie.isHttpOnly())
/*      */           continue; 
/* 3002 */         if (bool)
/* 3003 */           stringBuilder.append(','); 
/* 3004 */         stringBuilder.append(javaNetHttpCookieAccess.header(httpCookie));
/* 3005 */         bool = true;
/*      */       } 
/*      */       
/* 3008 */       return (stringBuilder.length() == 0) ? "" : stringBuilder.toString();
/*      */     } 
/*      */     
/* 3011 */     return paramString2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, List<String>> getFilteredHeaderFields() {
/*      */     Map<String, List<String>> map;
/* 3019 */     if (this.filteredHeaders != null) {
/* 3020 */       return this.filteredHeaders;
/*      */     }
/* 3022 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */     
/* 3024 */     if (this.cachedHeaders != null) {
/* 3025 */       map = this.cachedHeaders.getHeaders();
/*      */     } else {
/* 3027 */       map = this.responses.getHeaders();
/*      */     } 
/* 3029 */     for (Map.Entry<String, List<String>> entry : map.entrySet()) {
/* 3030 */       String str = (String)entry.getKey();
/* 3031 */       List list = (List)entry.getValue(); ArrayList<String> arrayList = new ArrayList();
/* 3032 */       for (String str1 : list) {
/* 3033 */         String str2 = filterHeaderField(str, str1);
/* 3034 */         if (str2 != null)
/* 3035 */           arrayList.add(str2); 
/*      */       } 
/* 3037 */       if (!arrayList.isEmpty()) {
/* 3038 */         hashMap.put(str, Collections.unmodifiableList(arrayList));
/*      */       }
/*      */     } 
/* 3041 */     return this.filteredHeaders = Collections.unmodifiableMap(hashMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHeaderField(String paramString) {
/*      */     try {
/* 3051 */       getInputStream();
/* 3052 */     } catch (IOException iOException) {}
/*      */     
/* 3054 */     if (this.cachedHeaders != null) {
/* 3055 */       return filterHeaderField(paramString, this.cachedHeaders.findValue(paramString));
/*      */     }
/*      */     
/* 3058 */     return filterHeaderField(paramString, this.responses.findValue(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, List<String>> getHeaderFields() {
/*      */     try {
/* 3074 */       getInputStream();
/* 3075 */     } catch (IOException iOException) {}
/*      */     
/* 3077 */     return getFilteredHeaderFields();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHeaderField(int paramInt) {
/*      */     try {
/* 3087 */       getInputStream();
/* 3088 */     } catch (IOException iOException) {}
/*      */     
/* 3090 */     if (this.cachedHeaders != null) {
/* 3091 */       return filterHeaderField(this.cachedHeaders.getKey(paramInt), this.cachedHeaders
/* 3092 */           .getValue(paramInt));
/*      */     }
/* 3094 */     return filterHeaderField(this.responses.getKey(paramInt), this.responses.getValue(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHeaderFieldKey(int paramInt) {
/*      */     try {
/* 3104 */       getInputStream();
/* 3105 */     } catch (IOException iOException) {}
/*      */     
/* 3107 */     if (this.cachedHeaders != null) {
/* 3108 */       return this.cachedHeaders.getKey(paramInt);
/*      */     }
/*      */     
/* 3111 */     return this.responses.getKey(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setRequestProperty(String paramString1, String paramString2) {
/* 3121 */     if (this.connected || this.connecting)
/* 3122 */       throw new IllegalStateException("Already connected"); 
/* 3123 */     if (paramString1 == null) {
/* 3124 */       throw new NullPointerException("key is null");
/*      */     }
/* 3126 */     if (isExternalMessageHeaderAllowed(paramString1, paramString2)) {
/* 3127 */       this.requests.set(paramString1, paramString2);
/* 3128 */       if (!paramString1.equalsIgnoreCase("Content-Type")) {
/* 3129 */         this.userHeaders.set(paramString1, paramString2);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   MessageHeader getUserSetHeaders() {
/* 3135 */     return this.userHeaders;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addRequestProperty(String paramString1, String paramString2) {
/* 3151 */     if (this.connected || this.connecting)
/* 3152 */       throw new IllegalStateException("Already connected"); 
/* 3153 */     if (paramString1 == null) {
/* 3154 */       throw new NullPointerException("key is null");
/*      */     }
/* 3156 */     if (isExternalMessageHeaderAllowed(paramString1, paramString2)) {
/* 3157 */       this.requests.add(paramString1, paramString2);
/* 3158 */       if (!paramString1.equalsIgnoreCase("Content-Type")) {
/* 3159 */         this.userHeaders.add(paramString1, paramString2);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAuthenticationProperty(String paramString1, String paramString2) {
/* 3169 */     checkMessageHeader(paramString1, paramString2);
/* 3170 */     this.requests.set(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized String getRequestProperty(String paramString) {
/* 3175 */     if (paramString == null) {
/* 3176 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 3180 */     for (byte b = 0; b < EXCLUDE_HEADERS.length; b++) {
/* 3181 */       if (paramString.equalsIgnoreCase(EXCLUDE_HEADERS[b])) {
/* 3182 */         return null;
/*      */       }
/*      */     } 
/* 3185 */     if (!this.setUserCookies) {
/* 3186 */       if (paramString.equalsIgnoreCase("Cookie")) {
/* 3187 */         return this.userCookies;
/*      */       }
/* 3189 */       if (paramString.equalsIgnoreCase("Cookie2")) {
/* 3190 */         return this.userCookies2;
/*      */       }
/*      */     } 
/* 3193 */     return this.requests.findValue(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Map<String, List<String>> getRequestProperties() {
/* 3210 */     if (this.connected) {
/* 3211 */       throw new IllegalStateException("Already connected");
/*      */     }
/*      */     
/* 3214 */     if (this.setUserCookies) {
/* 3215 */       return this.requests.getHeaders(EXCLUDE_HEADERS);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3221 */     HashMap<Object, Object> hashMap = null;
/* 3222 */     if (this.userCookies != null || this.userCookies2 != null) {
/* 3223 */       hashMap = new HashMap<>();
/* 3224 */       if (this.userCookies != null) {
/* 3225 */         hashMap.put("Cookie", Arrays.asList(new String[] { this.userCookies }));
/*      */       }
/* 3227 */       if (this.userCookies2 != null) {
/* 3228 */         hashMap.put("Cookie2", Arrays.asList(new String[] { this.userCookies2 }));
/*      */       }
/*      */     } 
/* 3231 */     return this.requests.filterAndAddHeaders(EXCLUDE_HEADERS2, (Map)hashMap);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setConnectTimeout(int paramInt) {
/* 3236 */     if (paramInt < 0)
/* 3237 */       throw new IllegalArgumentException("timeouts can't be negative"); 
/* 3238 */     this.connectTimeout = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getConnectTimeout() {
/* 3256 */     return (this.connectTimeout < 0) ? 0 : this.connectTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadTimeout(int paramInt) {
/* 3281 */     if (paramInt < 0)
/* 3282 */       throw new IllegalArgumentException("timeouts can't be negative"); 
/* 3283 */     this.readTimeout = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getReadTimeout() {
/* 3299 */     return (this.readTimeout < 0) ? 0 : this.readTimeout;
/*      */   }
/*      */   
/*      */   public CookieHandler getCookieHandler() {
/* 3303 */     return this.cookieHandler;
/*      */   }
/*      */   
/*      */   String getMethod() {
/* 3307 */     return this.method;
/*      */   }
/*      */   
/*      */   private MessageHeader mapToMessageHeader(Map<String, List<String>> paramMap) {
/* 3311 */     MessageHeader messageHeader = new MessageHeader();
/* 3312 */     if (paramMap == null || paramMap.isEmpty()) {
/* 3313 */       return messageHeader;
/*      */     }
/* 3315 */     for (Map.Entry<String, List<String>> entry : paramMap.entrySet()) {
/* 3316 */       String str = (String)entry.getKey();
/* 3317 */       List list = (List)entry.getValue();
/* 3318 */       for (String str1 : list) {
/* 3319 */         if (str == null) {
/* 3320 */           messageHeader.prepend(str, str1); continue;
/*      */         } 
/* 3322 */         messageHeader.add(str, str1);
/*      */       } 
/*      */     } 
/*      */     
/* 3326 */     return messageHeader;
/*      */   }
/*      */ 
/*      */   
/*      */   class HttpInputStream
/*      */     extends FilterInputStream
/*      */   {
/*      */     private CacheRequest cacheRequest;
/*      */     
/*      */     private OutputStream outputStream;
/*      */     private boolean marked = false;
/* 3337 */     private int inCache = 0;
/* 3338 */     private int markCount = 0; private boolean closed; private byte[] skipBuffer;
/*      */     private static final int SKIP_BUFFER_SIZE = 8096;
/*      */     
/*      */     public HttpInputStream(InputStream param1InputStream) {
/* 3342 */       super(param1InputStream);
/* 3343 */       this.cacheRequest = null;
/* 3344 */       this.outputStream = null;
/*      */     }
/*      */     
/*      */     public HttpInputStream(InputStream param1InputStream, CacheRequest param1CacheRequest) {
/* 3348 */       super(param1InputStream);
/* 3349 */       this.cacheRequest = param1CacheRequest;
/*      */       try {
/* 3351 */         this.outputStream = param1CacheRequest.getBody();
/* 3352 */       } catch (IOException iOException) {
/* 3353 */         this.cacheRequest.abort();
/* 3354 */         this.cacheRequest = null;
/* 3355 */         this.outputStream = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void mark(int param1Int) {
/* 3378 */       super.mark(param1Int);
/* 3379 */       if (this.cacheRequest != null) {
/* 3380 */         this.marked = true;
/* 3381 */         this.markCount = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void reset() throws IOException {
/* 3408 */       super.reset();
/* 3409 */       if (this.cacheRequest != null) {
/* 3410 */         this.marked = false;
/* 3411 */         this.inCache += this.markCount;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void ensureOpen() throws IOException {
/* 3416 */       if (this.closed) {
/* 3417 */         throw new IOException("stream is closed");
/*      */       }
/*      */     }
/*      */     
/*      */     public int read() throws IOException {
/* 3422 */       ensureOpen();
/*      */       try {
/* 3424 */         byte[] arrayOfByte = new byte[1];
/* 3425 */         int i = read(arrayOfByte);
/* 3426 */         return (i == -1) ? i : (arrayOfByte[0] & 0xFF);
/* 3427 */       } catch (IOException iOException) {
/* 3428 */         if (this.cacheRequest != null) {
/* 3429 */           this.cacheRequest.abort();
/*      */         }
/* 3431 */         throw iOException;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/* 3437 */       return read(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*      */     }
/*      */ 
/*      */     
/*      */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 3442 */       ensureOpen();
/*      */       try {
/* 3444 */         int j, i = super.read(param1ArrayOfbyte, param1Int1, param1Int2);
/*      */ 
/*      */         
/* 3447 */         if (this.inCache > 0) {
/* 3448 */           if (this.inCache >= i) {
/* 3449 */             this.inCache -= i;
/* 3450 */             j = 0;
/*      */           } else {
/* 3452 */             j = i - this.inCache;
/* 3453 */             this.inCache = 0;
/*      */           } 
/*      */         } else {
/* 3456 */           j = i;
/*      */         } 
/* 3458 */         if (j > 0 && this.outputStream != null)
/* 3459 */           this.outputStream.write(param1ArrayOfbyte, param1Int1 + i - j, j); 
/* 3460 */         if (this.marked) {
/* 3461 */           this.markCount += i;
/*      */         }
/* 3463 */         return i;
/* 3464 */       } catch (IOException iOException) {
/* 3465 */         if (this.cacheRequest != null) {
/* 3466 */           this.cacheRequest.abort();
/*      */         }
/* 3468 */         throw iOException;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long param1Long) throws IOException {
/* 3480 */       ensureOpen();
/* 3481 */       long l = param1Long;
/*      */       
/* 3483 */       if (this.skipBuffer == null) {
/* 3484 */         this.skipBuffer = new byte[8096];
/*      */       }
/* 3486 */       byte[] arrayOfByte = this.skipBuffer;
/*      */       
/* 3488 */       if (param1Long <= 0L) {
/* 3489 */         return 0L;
/*      */       }
/*      */       
/* 3492 */       while (l > 0L) {
/* 3493 */         int i = read(arrayOfByte, 0, 
/* 3494 */             (int)Math.min(8096L, l));
/* 3495 */         if (i < 0) {
/*      */           break;
/*      */         }
/* 3498 */         l -= i;
/*      */       } 
/*      */       
/* 3501 */       return param1Long - l;
/*      */     }
/*      */ 
/*      */     
/*      */     public void close() throws IOException {
/* 3506 */       if (this.closed) {
/*      */         return;
/*      */       }
/*      */       try {
/* 3510 */         if (this.outputStream != null) {
/* 3511 */           if (read() != -1) {
/* 3512 */             this.cacheRequest.abort();
/*      */           } else {
/* 3514 */             this.outputStream.close();
/*      */           } 
/*      */         }
/* 3517 */         super.close();
/* 3518 */       } catch (IOException iOException) {
/* 3519 */         if (this.cacheRequest != null) {
/* 3520 */           this.cacheRequest.abort();
/*      */         }
/* 3522 */         throw iOException;
/*      */       } finally {
/* 3524 */         this.closed = true;
/* 3525 */         HttpURLConnection.this.http = null;
/* 3526 */         HttpURLConnection.this.checkResponseCredentials(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class StreamingOutputStream
/*      */     extends FilterOutputStream
/*      */   {
/*      */     long expected;
/*      */     
/*      */     long written;
/*      */     
/*      */     boolean closed;
/*      */     
/*      */     boolean error;
/*      */     
/*      */     IOException errorExcp;
/*      */     
/*      */     StreamingOutputStream(OutputStream param1OutputStream, long param1Long) {
/* 3546 */       super(param1OutputStream);
/* 3547 */       this.expected = param1Long;
/* 3548 */       this.written = 0L;
/* 3549 */       this.closed = false;
/* 3550 */       this.error = false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(int param1Int) throws IOException {
/* 3555 */       checkError();
/* 3556 */       this.written++;
/* 3557 */       if (this.expected != -1L && this.written > this.expected) {
/* 3558 */         throw new IOException("too many bytes written");
/*      */       }
/* 3560 */       this.out.write(param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte[] param1ArrayOfbyte) throws IOException {
/* 3565 */       write(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 3570 */       checkError();
/* 3571 */       this.written += param1Int2;
/* 3572 */       if (this.expected != -1L && this.written > this.expected) {
/* 3573 */         this.out.close();
/* 3574 */         throw new IOException("too many bytes written");
/*      */       } 
/* 3576 */       this.out.write(param1ArrayOfbyte, param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     void checkError() throws IOException {
/* 3580 */       if (this.closed) {
/* 3581 */         throw new IOException("Stream is closed");
/*      */       }
/* 3583 */       if (this.error) {
/* 3584 */         throw this.errorExcp;
/*      */       }
/* 3586 */       if (((PrintStream)this.out).checkError()) {
/* 3587 */         throw new IOException("Error writing request body to server");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean writtenOK() {
/* 3596 */       return (this.closed && !this.error);
/*      */     }
/*      */ 
/*      */     
/*      */     public void close() throws IOException {
/* 3601 */       if (this.closed) {
/*      */         return;
/*      */       }
/* 3604 */       this.closed = true;
/* 3605 */       if (this.expected != -1L) {
/*      */         
/* 3607 */         if (this.written != this.expected) {
/* 3608 */           this.error = true;
/* 3609 */           this.errorExcp = new IOException("insufficient data written");
/* 3610 */           this.out.close();
/* 3611 */           throw this.errorExcp;
/*      */         } 
/* 3613 */         flush();
/*      */       } else {
/*      */         
/* 3616 */         super.close();
/*      */         
/* 3618 */         OutputStream outputStream = HttpURLConnection.this.http.getOutputStream();
/* 3619 */         outputStream.write(13);
/* 3620 */         outputStream.write(10);
/* 3621 */         outputStream.flush();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static class ErrorStream
/*      */     extends InputStream {
/*      */     ByteBuffer buffer;
/*      */     InputStream is;
/*      */     
/*      */     private ErrorStream(ByteBuffer param1ByteBuffer) {
/* 3632 */       this.buffer = param1ByteBuffer;
/* 3633 */       this.is = null;
/*      */     }
/*      */     
/*      */     private ErrorStream(ByteBuffer param1ByteBuffer, InputStream param1InputStream) {
/* 3637 */       this.buffer = param1ByteBuffer;
/* 3638 */       this.is = param1InputStream;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static InputStream getErrorStream(InputStream param1InputStream, long param1Long, HttpClient param1HttpClient) {
/* 3646 */       if (param1Long == 0L) {
/* 3647 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 3653 */         int i = param1HttpClient.getReadTimeout();
/* 3654 */         param1HttpClient.setReadTimeout(HttpURLConnection.timeout4ESBuffer / 5);
/*      */         
/* 3656 */         long l = 0L;
/* 3657 */         boolean bool = false;
/*      */         
/* 3659 */         if (param1Long < 0L) {
/* 3660 */           l = HttpURLConnection.bufSize4ES;
/* 3661 */           bool = true;
/*      */         } else {
/* 3663 */           l = param1Long;
/*      */         } 
/* 3665 */         if (l <= HttpURLConnection.bufSize4ES) {
/* 3666 */           int j = (int)l;
/* 3667 */           byte[] arrayOfByte = new byte[j];
/* 3668 */           int k = 0, m = 0, n = 0;
/*      */           do {
/*      */             try {
/* 3671 */               n = param1InputStream.read(arrayOfByte, k, arrayOfByte.length - k);
/*      */               
/* 3673 */               if (n < 0) {
/* 3674 */                 if (bool) {
/*      */                   break;
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 3681 */                 throw new IOException("the server closes before sending " + param1Long + " bytes of data");
/*      */               } 
/*      */ 
/*      */               
/* 3685 */               k += n;
/* 3686 */             } catch (SocketTimeoutException socketTimeoutException) {
/* 3687 */               m += HttpURLConnection.timeout4ESBuffer / 5;
/*      */             } 
/* 3689 */           } while (k < j && m < HttpURLConnection.timeout4ESBuffer);
/*      */ 
/*      */           
/* 3692 */           param1HttpClient.setReadTimeout(i);
/*      */ 
/*      */ 
/*      */           
/* 3696 */           if (k == 0)
/*      */           {
/*      */ 
/*      */             
/* 3700 */             return null; } 
/* 3701 */           if ((k == l && !bool) || (bool && n < 0)) {
/*      */ 
/*      */             
/* 3704 */             param1InputStream.close();
/* 3705 */             return new ErrorStream(ByteBuffer.wrap(arrayOfByte, 0, k));
/*      */           } 
/*      */           
/* 3708 */           return new ErrorStream(
/* 3709 */               ByteBuffer.wrap(arrayOfByte, 0, k), param1InputStream);
/*      */         } 
/*      */         
/* 3712 */         return null;
/* 3713 */       } catch (IOException iOException) {
/*      */         
/* 3715 */         return null;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int available() throws IOException {
/* 3721 */       if (this.is == null) {
/* 3722 */         return this.buffer.remaining();
/*      */       }
/* 3724 */       return this.buffer.remaining() + this.is.available();
/*      */     }
/*      */ 
/*      */     
/*      */     public int read() throws IOException {
/* 3729 */       byte[] arrayOfByte = new byte[1];
/* 3730 */       int i = read(arrayOfByte);
/* 3731 */       return (i == -1) ? i : (arrayOfByte[0] & 0xFF);
/*      */     }
/*      */ 
/*      */     
/*      */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/* 3736 */       return read(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*      */     }
/*      */ 
/*      */     
/*      */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 3741 */       int i = this.buffer.remaining();
/* 3742 */       if (i > 0) {
/* 3743 */         int j = (i < param1Int2) ? i : param1Int2;
/* 3744 */         this.buffer.get(param1ArrayOfbyte, param1Int1, j);
/* 3745 */         return j;
/*      */       } 
/* 3747 */       if (this.is == null) {
/* 3748 */         return -1;
/*      */       }
/* 3750 */       return this.is.read(param1ArrayOfbyte, param1Int1, param1Int2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() throws IOException {
/* 3757 */       this.buffer = null;
/* 3758 */       if (this.is != null)
/* 3759 */         this.is.close(); 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\www\protocol\http\HttpURLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */