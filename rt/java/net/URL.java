/*      */ package java.net;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.util.Hashtable;
/*      */ import java.util.StringTokenizer;
/*      */ import sun.net.ApplicationProxy;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class URL
/*      */   implements Serializable
/*      */ {
/*      */   static final String BUILTIN_HANDLERS_PREFIX = "sun.net.www.protocol";
/*      */   static final long serialVersionUID = -7627629688361524110L;
/*      */   private static final String protocolPathProp = "java.protocol.handler.pkgs";
/*      */   private String protocol;
/*      */   private String host;
/*  175 */   private int port = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String file;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient String query;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String authority;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient String path;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient String userInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String ref;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient InetAddress hostAddress;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient URLStreamHandler handler;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  225 */   private int hashCode = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient UrlDeserializedState tempState;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static URLStreamHandlerFactory factory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL(String paramString1, String paramString2, int paramInt, String paramString3) throws MalformedURLException {
/*  310 */     this(paramString1, paramString2, paramInt, paramString3, null);
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
/*      */   public URL(String paramString1, String paramString2, String paramString3) throws MalformedURLException {
/*  333 */     this(paramString1, paramString2, -1, paramString3);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL(String paramString1, String paramString2, int paramInt, String paramString3, URLStreamHandler paramURLStreamHandler) throws MalformedURLException {
/*  377 */     if (paramURLStreamHandler != null) {
/*  378 */       SecurityManager securityManager = System.getSecurityManager();
/*  379 */       if (securityManager != null)
/*      */       {
/*  381 */         checkSpecifyHandler(securityManager);
/*      */       }
/*      */     } 
/*      */     
/*  385 */     paramString1 = paramString1.toLowerCase();
/*  386 */     this.protocol = paramString1;
/*  387 */     if (paramString2 != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  393 */       if (paramString2.indexOf(':') >= 0 && !paramString2.startsWith("[")) {
/*  394 */         paramString2 = "[" + paramString2 + "]";
/*      */       }
/*  396 */       this.host = paramString2;
/*      */       
/*  398 */       if (paramInt < -1) {
/*  399 */         throw new MalformedURLException("Invalid port number :" + paramInt);
/*      */       }
/*      */       
/*  402 */       this.port = paramInt;
/*  403 */       this.authority = (paramInt == -1) ? paramString2 : (paramString2 + ":" + paramInt);
/*      */     } 
/*      */     
/*  406 */     Parts parts = new Parts(paramString3);
/*  407 */     this.path = parts.getPath();
/*  408 */     this.query = parts.getQuery();
/*      */     
/*  410 */     if (this.query != null) {
/*  411 */       this.file = this.path + "?" + this.query;
/*      */     } else {
/*  413 */       this.file = this.path;
/*      */     } 
/*  415 */     this.ref = parts.getRef();
/*      */ 
/*      */ 
/*      */     
/*  419 */     if (paramURLStreamHandler == null && (
/*  420 */       paramURLStreamHandler = getURLStreamHandler(paramString1)) == null) {
/*  421 */       throw new MalformedURLException("unknown protocol: " + paramString1);
/*      */     }
/*  423 */     this.handler = paramURLStreamHandler;
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
/*      */   public URL(String paramString) throws MalformedURLException {
/*  439 */     this(null, paramString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL(URL paramURL, String paramString) throws MalformedURLException {
/*  490 */     this(paramURL, paramString, (URLStreamHandler)null);
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
/*      */   
/*      */   public URL(URL paramURL, String paramString, URLStreamHandler paramURLStreamHandler) throws MalformedURLException {
/*  516 */     String str1 = paramString;
/*      */     
/*  518 */     int i = 0;
/*  519 */     String str2 = null;
/*  520 */     boolean bool1 = false;
/*  521 */     boolean bool2 = false;
/*      */ 
/*      */     
/*  524 */     if (paramURLStreamHandler != null) {
/*  525 */       SecurityManager securityManager = System.getSecurityManager();
/*  526 */       if (securityManager != null) {
/*  527 */         checkSpecifyHandler(securityManager);
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/*  532 */       int k = paramString.length();
/*  533 */       while (k > 0 && paramString.charAt(k - 1) <= ' ') {
/*  534 */         k--;
/*      */       }
/*  536 */       while (i < k && paramString.charAt(i) <= ' ') {
/*  537 */         i++;
/*      */       }
/*      */       
/*  540 */       if (paramString.regionMatches(true, i, "url:", 0, 4)) {
/*  541 */         i += 4;
/*      */       }
/*  543 */       if (i < paramString.length() && paramString.charAt(i) == '#')
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  548 */         bool1 = true; }  int j;
/*      */       char c;
/*  550 */       for (j = i; !bool1 && j < k && (
/*  551 */         c = paramString.charAt(j)) != '/'; j++) {
/*  552 */         if (c == ':') {
/*      */           
/*  554 */           String str = paramString.substring(i, j).toLowerCase();
/*  555 */           if (isValidProtocol(str)) {
/*  556 */             str2 = str;
/*  557 */             i = j + 1;
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  564 */       this.protocol = str2;
/*  565 */       if (paramURL != null && (str2 == null || str2
/*  566 */         .equalsIgnoreCase(paramURL.protocol))) {
/*      */ 
/*      */         
/*  569 */         if (paramURLStreamHandler == null) {
/*  570 */           paramURLStreamHandler = paramURL.handler;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  577 */         if (paramURL.path != null && paramURL.path.startsWith("/")) {
/*  578 */           str2 = null;
/*      */         }
/*  580 */         if (str2 == null) {
/*  581 */           this.protocol = paramURL.protocol;
/*  582 */           this.authority = paramURL.authority;
/*  583 */           this.userInfo = paramURL.userInfo;
/*  584 */           this.host = paramURL.host;
/*  585 */           this.port = paramURL.port;
/*  586 */           this.file = paramURL.file;
/*  587 */           this.path = paramURL.path;
/*  588 */           bool2 = true;
/*      */         } 
/*      */       } 
/*      */       
/*  592 */       if (this.protocol == null) {
/*  593 */         throw new MalformedURLException("no protocol: " + str1);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  598 */       if (paramURLStreamHandler == null && (
/*  599 */         paramURLStreamHandler = getURLStreamHandler(this.protocol)) == null) {
/*  600 */         throw new MalformedURLException("unknown protocol: " + this.protocol);
/*      */       }
/*      */       
/*  603 */       this.handler = paramURLStreamHandler;
/*      */       
/*  605 */       j = paramString.indexOf('#', i);
/*  606 */       if (j >= 0) {
/*  607 */         this.ref = paramString.substring(j + 1, k);
/*  608 */         k = j;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  615 */       if (bool2 && i == k) {
/*  616 */         this.query = paramURL.query;
/*  617 */         if (this.ref == null) {
/*  618 */           this.ref = paramURL.ref;
/*      */         }
/*      */       } 
/*      */       
/*  622 */       paramURLStreamHandler.parseURL(this, paramString, i, k);
/*      */     }
/*  624 */     catch (MalformedURLException malformedURLException) {
/*  625 */       throw malformedURLException;
/*  626 */     } catch (Exception exception) {
/*  627 */       MalformedURLException malformedURLException = new MalformedURLException(exception.getMessage());
/*  628 */       malformedURLException.initCause(exception);
/*  629 */       throw malformedURLException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidProtocol(String paramString) {
/*  637 */     int i = paramString.length();
/*  638 */     if (i < 1)
/*  639 */       return false; 
/*  640 */     char c = paramString.charAt(0);
/*  641 */     if (!Character.isLetter(c))
/*  642 */       return false; 
/*  643 */     for (byte b = 1; b < i; b++) {
/*  644 */       c = paramString.charAt(b);
/*  645 */       if (!Character.isLetterOrDigit(c) && c != '.' && c != '+' && c != '-')
/*      */       {
/*  647 */         return false;
/*      */       }
/*      */     } 
/*  650 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkSpecifyHandler(SecurityManager paramSecurityManager) {
/*  657 */     paramSecurityManager.checkPermission(SecurityConstants.SPECIFY_HANDLER_PERMISSION);
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
/*      */   void set(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4) {
/*  673 */     synchronized (this) {
/*  674 */       this.protocol = paramString1;
/*  675 */       this.host = paramString2;
/*  676 */       this.authority = (paramInt == -1) ? paramString2 : (paramString2 + ":" + paramInt);
/*  677 */       this.port = paramInt;
/*  678 */       this.file = paramString3;
/*  679 */       this.ref = paramString4;
/*      */ 
/*      */       
/*  682 */       this.hashCode = -1;
/*  683 */       this.hostAddress = null;
/*  684 */       int i = paramString3.lastIndexOf('?');
/*  685 */       if (i != -1) {
/*  686 */         this.query = paramString3.substring(i + 1);
/*  687 */         this.path = paramString3.substring(0, i);
/*      */       } else {
/*  689 */         this.path = paramString3;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void set(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) {
/*  711 */     synchronized (this) {
/*  712 */       this.protocol = paramString1;
/*  713 */       this.host = paramString2;
/*  714 */       this.port = paramInt;
/*  715 */       this.file = (paramString6 == null) ? paramString5 : (paramString5 + "?" + paramString6);
/*  716 */       this.userInfo = paramString4;
/*  717 */       this.path = paramString5;
/*  718 */       this.ref = paramString7;
/*      */ 
/*      */       
/*  721 */       this.hashCode = -1;
/*  722 */       this.hostAddress = null;
/*  723 */       this.query = paramString6;
/*  724 */       this.authority = paramString3;
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
/*      */   public String getQuery() {
/*  736 */     return this.query;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPath() {
/*  747 */     return this.path;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserInfo() {
/*  758 */     return this.userInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAuthority() {
/*  768 */     return this.authority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPort() {
/*  777 */     return this.port;
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
/*      */   public int getDefaultPort() {
/*  790 */     return this.handler.getDefaultPort();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProtocol() {
/*  799 */     return this.protocol;
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
/*      */   public String getHost() {
/*  811 */     return this.host;
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
/*      */   public String getFile() {
/*  826 */     return this.file;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRef() {
/*  837 */     return this.ref;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  866 */     if (!(paramObject instanceof URL))
/*  867 */       return false; 
/*  868 */     URL uRL = (URL)paramObject;
/*      */     
/*  870 */     return this.handler.equals(this, uRL);
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
/*      */   public synchronized int hashCode() {
/*  882 */     if (this.hashCode != -1) {
/*  883 */       return this.hashCode;
/*      */     }
/*  885 */     this.hashCode = this.handler.hashCode(this);
/*  886 */     return this.hashCode;
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
/*      */   public boolean sameFile(URL paramURL) {
/*  901 */     return this.handler.sameFile(this, paramURL);
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
/*      */   public String toString() {
/*  915 */     return toExternalForm();
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
/*      */   public String toExternalForm() {
/*  929 */     return this.handler.toExternalForm(this);
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
/*      */   public URI toURI() throws URISyntaxException {
/*  946 */     return new URI(toString());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URLConnection openConnection() throws IOException {
/*  979 */     return this.handler.openConnection(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URLConnection openConnection(Proxy paramProxy) throws IOException {
/* 1013 */     if (paramProxy == null) {
/* 1014 */       throw new IllegalArgumentException("proxy can not be null");
/*      */     }
/*      */ 
/*      */     
/* 1018 */     Proxy proxy = (paramProxy == Proxy.NO_PROXY) ? Proxy.NO_PROXY : ApplicationProxy.create(paramProxy);
/* 1019 */     SecurityManager securityManager = System.getSecurityManager();
/* 1020 */     if (proxy.type() != Proxy.Type.DIRECT && securityManager != null) {
/* 1021 */       InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy.address();
/* 1022 */       if (inetSocketAddress.isUnresolved()) {
/* 1023 */         securityManager.checkConnect(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
/*      */       } else {
/* 1025 */         securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/* 1026 */             .getPort());
/*      */       } 
/* 1028 */     }  return this.handler.openConnection(this, proxy);
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
/*      */   public final InputStream openStream() throws IOException {
/* 1045 */     return openConnection().getInputStream();
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
/*      */   public final Object getContent() throws IOException {
/* 1059 */     return openConnection().getContent();
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
/*      */   public final Object getContent(Class[] paramArrayOfClass) throws IOException {
/* 1078 */     return openConnection().getContent(paramArrayOfClass);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setURLStreamHandlerFactory(URLStreamHandlerFactory paramURLStreamHandlerFactory) {
/* 1110 */     synchronized (streamHandlerLock) {
/* 1111 */       if (factory != null) {
/* 1112 */         throw new Error("factory already defined");
/*      */       }
/* 1114 */       SecurityManager securityManager = System.getSecurityManager();
/* 1115 */       if (securityManager != null) {
/* 1116 */         securityManager.checkSetFactory();
/*      */       }
/* 1118 */       handlers.clear();
/* 1119 */       factory = paramURLStreamHandlerFactory;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1126 */   static Hashtable<String, URLStreamHandler> handlers = new Hashtable<>();
/* 1127 */   private static Object streamHandlerLock = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static URLStreamHandler getURLStreamHandler(String paramString) {
/* 1135 */     URLStreamHandler uRLStreamHandler = handlers.get(paramString);
/* 1136 */     if (uRLStreamHandler == null) {
/*      */       
/* 1138 */       boolean bool = false;
/*      */ 
/*      */       
/* 1141 */       if (factory != null) {
/* 1142 */         uRLStreamHandler = factory.createURLStreamHandler(paramString);
/* 1143 */         bool = true;
/*      */       } 
/*      */ 
/*      */       
/* 1147 */       if (uRLStreamHandler == null) {
/* 1148 */         String str = null;
/*      */ 
/*      */         
/* 1151 */         str = AccessController.<String>doPrivileged(new GetPropertyAction("java.protocol.handler.pkgs", ""));
/*      */ 
/*      */         
/* 1154 */         if (str != "") {
/* 1155 */           str = str + "|";
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1160 */         str = str + "sun.net.www.protocol";
/*      */         
/* 1162 */         StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*      */ 
/*      */         
/* 1165 */         while (uRLStreamHandler == null && stringTokenizer
/* 1166 */           .hasMoreTokens()) {
/*      */ 
/*      */           
/* 1169 */           String str1 = stringTokenizer.nextToken().trim();
/*      */           try {
/* 1171 */             String str2 = str1 + "." + paramString + ".Handler";
/*      */             
/* 1173 */             Class<?> clazz = null;
/*      */             try {
/* 1175 */               clazz = Class.forName(str2);
/* 1176 */             } catch (ClassNotFoundException classNotFoundException) {
/* 1177 */               ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 1178 */               if (classLoader != null) {
/* 1179 */                 clazz = classLoader.loadClass(str2);
/*      */               }
/*      */             } 
/* 1182 */             if (clazz != null)
/*      */             {
/* 1184 */               uRLStreamHandler = (URLStreamHandler)clazz.newInstance();
/*      */             }
/* 1186 */           } catch (Exception exception) {}
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1192 */       synchronized (streamHandlerLock) {
/*      */         
/* 1194 */         URLStreamHandler uRLStreamHandler1 = null;
/*      */ 
/*      */ 
/*      */         
/* 1198 */         uRLStreamHandler1 = handlers.get(paramString);
/*      */         
/* 1200 */         if (uRLStreamHandler1 != null) {
/* 1201 */           return uRLStreamHandler1;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1206 */         if (!bool && factory != null) {
/* 1207 */           uRLStreamHandler1 = factory.createURLStreamHandler(paramString);
/*      */         }
/*      */         
/* 1210 */         if (uRLStreamHandler1 != null)
/*      */         {
/*      */ 
/*      */           
/* 1214 */           uRLStreamHandler = uRLStreamHandler1;
/*      */         }
/*      */ 
/*      */         
/* 1218 */         if (uRLStreamHandler != null) {
/* 1219 */           handlers.put(paramString, uRLStreamHandler);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1225 */     return uRLStreamHandler;
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
/* 1245 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("protocol", String.class), new ObjectStreamField("host", String.class), new ObjectStreamField("port", int.class), new ObjectStreamField("authority", String.class), new ObjectStreamField("file", String.class), new ObjectStreamField("ref", String.class), new ObjectStreamField("hashCode", int.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1267 */     paramObjectOutputStream.defaultWriteObject();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1277 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 1278 */     String str1 = (String)getField.get("protocol", (Object)null);
/* 1279 */     if (getURLStreamHandler(str1) == null) {
/* 1280 */       throw new IOException("unknown protocol: " + str1);
/*      */     }
/* 1282 */     String str2 = (String)getField.get("host", (Object)null);
/* 1283 */     int i = getField.get("port", -1);
/* 1284 */     String str3 = (String)getField.get("authority", (Object)null);
/* 1285 */     String str4 = (String)getField.get("file", (Object)null);
/* 1286 */     String str5 = (String)getField.get("ref", (Object)null);
/* 1287 */     int j = getField.get("hashCode", -1);
/* 1288 */     if (str3 == null && ((str2 != null && str2
/* 1289 */       .length() > 0) || i != -1)) {
/* 1290 */       if (str2 == null)
/* 1291 */         str2 = ""; 
/* 1292 */       str3 = (i == -1) ? str2 : (str2 + ":" + i);
/*      */     } 
/* 1294 */     this.tempState = new UrlDeserializedState(str1, str2, i, str3, str4, str5, j);
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
/*      */   private Object readResolve() throws ObjectStreamException {
/* 1309 */     URLStreamHandler uRLStreamHandler = null;
/*      */     
/* 1311 */     uRLStreamHandler = getURLStreamHandler(this.tempState.getProtocol());
/*      */     
/* 1313 */     URL uRL = null;
/* 1314 */     if (isBuiltinStreamHandler(uRLStreamHandler.getClass().getName())) {
/* 1315 */       uRL = fabricateNewURL();
/*      */     } else {
/* 1317 */       uRL = setDeserializedFields(uRLStreamHandler);
/*      */     } 
/* 1319 */     return uRL;
/*      */   }
/*      */ 
/*      */   
/*      */   private URL setDeserializedFields(URLStreamHandler paramURLStreamHandler) {
/* 1324 */     String str1 = null;
/* 1325 */     String str2 = this.tempState.getProtocol();
/* 1326 */     String str3 = this.tempState.getHost();
/* 1327 */     int i = this.tempState.getPort();
/* 1328 */     String str4 = this.tempState.getAuthority();
/* 1329 */     String str5 = this.tempState.getFile();
/* 1330 */     String str6 = this.tempState.getRef();
/* 1331 */     int j = this.tempState.getHashCode();
/*      */ 
/*      */ 
/*      */     
/* 1335 */     if (str4 == null && ((str3 != null && str3
/* 1336 */       .length() > 0) || i != -1)) {
/* 1337 */       if (str3 == null)
/* 1338 */         str3 = ""; 
/* 1339 */       str4 = (i == -1) ? str3 : (str3 + ":" + i);
/*      */ 
/*      */       
/* 1342 */       int k = str3.lastIndexOf('@');
/* 1343 */       if (k != -1) {
/* 1344 */         str1 = str3.substring(0, k);
/* 1345 */         str3 = str3.substring(k + 1);
/*      */       } 
/* 1347 */     } else if (str4 != null) {
/*      */       
/* 1349 */       int k = str4.indexOf('@');
/* 1350 */       if (k != -1) {
/* 1351 */         str1 = str4.substring(0, k);
/*      */       }
/*      */     } 
/*      */     
/* 1355 */     String str7 = null;
/* 1356 */     String str8 = null;
/* 1357 */     if (str5 != null) {
/*      */       
/* 1359 */       int k = str5.lastIndexOf('?');
/* 1360 */       if (k != -1) {
/* 1361 */         str8 = str5.substring(k + 1);
/* 1362 */         str7 = str5.substring(0, k);
/*      */       } else {
/* 1364 */         str7 = str5;
/*      */       } 
/*      */     } 
/*      */     
/* 1368 */     this.protocol = str2;
/* 1369 */     this.host = str3;
/* 1370 */     this.port = i;
/* 1371 */     this.file = str5;
/* 1372 */     this.authority = str4;
/* 1373 */     this.ref = str6;
/* 1374 */     this.hashCode = j;
/* 1375 */     this.handler = paramURLStreamHandler;
/* 1376 */     this.query = str8;
/* 1377 */     this.path = str7;
/* 1378 */     this.userInfo = str1;
/* 1379 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private URL fabricateNewURL() throws InvalidObjectException {
/* 1386 */     URL uRL = null;
/* 1387 */     String str = this.tempState.reconstituteUrlString();
/*      */     
/*      */     try {
/* 1390 */       uRL = new URL(str);
/* 1391 */     } catch (MalformedURLException malformedURLException) {
/* 1392 */       resetState();
/* 1393 */       InvalidObjectException invalidObjectException = new InvalidObjectException("Malformed URL: " + str);
/*      */       
/* 1395 */       invalidObjectException.initCause(malformedURLException);
/* 1396 */       throw invalidObjectException;
/*      */     } 
/* 1398 */     uRL.setSerializedHashCode(this.tempState.getHashCode());
/* 1399 */     resetState();
/* 1400 */     return uRL;
/*      */   }
/*      */   
/*      */   private boolean isBuiltinStreamHandler(String paramString) {
/* 1404 */     return paramString.startsWith("sun.net.www.protocol");
/*      */   }
/*      */   
/*      */   private void resetState() {
/* 1408 */     this.protocol = null;
/* 1409 */     this.host = null;
/* 1410 */     this.port = -1;
/* 1411 */     this.file = null;
/* 1412 */     this.authority = null;
/* 1413 */     this.ref = null;
/* 1414 */     this.hashCode = -1;
/* 1415 */     this.handler = null;
/* 1416 */     this.query = null;
/* 1417 */     this.path = null;
/* 1418 */     this.userInfo = null;
/* 1419 */     this.tempState = null;
/*      */   }
/*      */   
/*      */   private void setSerializedHashCode(int paramInt) {
/* 1423 */     this.hashCode = paramInt;
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\URL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */