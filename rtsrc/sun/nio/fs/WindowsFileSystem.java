/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.FileStore;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.PathMatcher;
/*     */ import java.nio.file.WatchService;
/*     */ import java.nio.file.attribute.GroupPrincipal;
/*     */ import java.nio.file.attribute.UserPrincipal;
/*     */ import java.nio.file.attribute.UserPrincipalLookupService;
/*     */ import java.nio.file.attribute.UserPrincipalNotFoundException;
/*     */ import java.nio.file.spi.FileSystemProvider;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
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
/*     */ class WindowsFileSystem
/*     */   extends FileSystem
/*     */ {
/*     */   private final WindowsFileSystemProvider provider;
/*     */   private final String defaultDirectory;
/*     */   private final String defaultRoot;
/*     */   private final boolean supportsLinks;
/*     */   private final boolean supportsStreamEnumeration;
/*     */   
/*     */   WindowsFileSystem(WindowsFileSystemProvider paramWindowsFileSystemProvider, String paramString) {
/*  54 */     this.provider = paramWindowsFileSystemProvider;
/*     */ 
/*     */     
/*  57 */     WindowsPathParser.Result result = WindowsPathParser.parse(paramString);
/*     */     
/*  59 */     if (result.type() != WindowsPathType.ABSOLUTE && result
/*  60 */       .type() != WindowsPathType.UNC)
/*  61 */       throw new AssertionError("Default directory is not an absolute path"); 
/*  62 */     this.defaultDirectory = result.path();
/*  63 */     this.defaultRoot = result.root();
/*     */     
/*  65 */     GetPropertyAction getPropertyAction = new GetPropertyAction("os.version");
/*  66 */     String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  67 */     String[] arrayOfString = Util.split(str, '.');
/*  68 */     int i = Integer.parseInt(arrayOfString[0]);
/*  69 */     int j = Integer.parseInt(arrayOfString[1]);
/*     */ 
/*     */     
/*  72 */     this.supportsLinks = (i >= 6);
/*     */ 
/*     */     
/*  75 */     this.supportsStreamEnumeration = (i >= 6 || (i == 5 && j >= 2));
/*     */   }
/*     */ 
/*     */   
/*     */   String defaultDirectory() {
/*  80 */     return this.defaultDirectory;
/*     */   }
/*     */   
/*     */   String defaultRoot() {
/*  84 */     return this.defaultRoot;
/*     */   }
/*     */   
/*     */   boolean supportsLinks() {
/*  88 */     return this.supportsLinks;
/*     */   }
/*     */   
/*     */   boolean supportsStreamEnumeration() {
/*  92 */     return this.supportsStreamEnumeration;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileSystemProvider provider() {
/*  97 */     return this.provider;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSeparator() {
/* 102 */     return "\\";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 117 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<Path> getRootDirectories() {
/* 122 */     int i = 0;
/*     */     try {
/* 124 */       i = WindowsNativeDispatcher.GetLogicalDrives();
/* 125 */     } catch (WindowsException windowsException) {
/*     */       
/* 127 */       throw new AssertionError(windowsException.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 131 */     ArrayList<WindowsPath> arrayList = new ArrayList();
/* 132 */     SecurityManager securityManager = System.getSecurityManager();
/* 133 */     for (byte b = 0; b <= 25; b++) {
/* 134 */       if ((i & 1 << b) != 0) {
/* 135 */         StringBuilder stringBuilder = new StringBuilder(3);
/* 136 */         stringBuilder.append((char)(65 + b));
/* 137 */         stringBuilder.append(":\\");
/* 138 */         String str = stringBuilder.toString();
/* 139 */         if (securityManager != null) {
/*     */           try {
/* 141 */             securityManager.checkRead(str);
/* 142 */           } catch (SecurityException securityException) {}
/*     */         }
/*     */ 
/*     */         
/* 146 */         arrayList.add(WindowsPath.createFromNormalizedPath(this, str));
/*     */       } 
/*     */     } 
/* 149 */     return Collections.unmodifiableList((List)arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class FileStoreIterator
/*     */     implements Iterator<FileStore>
/*     */   {
/* 160 */     private final Iterator<Path> roots = WindowsFileSystem.this.getRootDirectories().iterator();
/*     */     private FileStore next;
/*     */     
/*     */     private FileStore readNext() {
/* 164 */       assert Thread.holdsLock(this);
/*     */       while (true) {
/* 166 */         if (!this.roots.hasNext())
/* 167 */           return null; 
/* 168 */         WindowsPath windowsPath = (WindowsPath)this.roots.next();
/*     */         
/*     */         try {
/* 171 */           windowsPath.checkRead();
/* 172 */         } catch (SecurityException securityException) {
/*     */           continue;
/*     */         } 
/*     */         try {
/* 176 */           WindowsFileStore windowsFileStore = WindowsFileStore.create(windowsPath.toString(), true);
/* 177 */           if (windowsFileStore != null)
/* 178 */             return windowsFileStore; 
/* 179 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized boolean hasNext() {
/* 187 */       if (this.next != null)
/* 188 */         return true; 
/* 189 */       this.next = readNext();
/* 190 */       return (this.next != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized FileStore next() {
/* 195 */       if (this.next == null)
/* 196 */         this.next = readNext(); 
/* 197 */       if (this.next == null) {
/* 198 */         throw new NoSuchElementException();
/*     */       }
/* 200 */       FileStore fileStore = this.next;
/* 201 */       this.next = null;
/* 202 */       return fileStore;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 208 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<FileStore> getFileStores() {
/* 214 */     SecurityManager securityManager = System.getSecurityManager();
/* 215 */     if (securityManager != null) {
/*     */       try {
/* 217 */         securityManager.checkPermission(new RuntimePermission("getFileStoreAttributes"));
/* 218 */       } catch (SecurityException securityException) {
/* 219 */         return Collections.emptyList();
/*     */       } 
/*     */     }
/* 222 */     return new Iterable<FileStore>() {
/*     */         public Iterator<FileStore> iterator() {
/* 224 */           return new WindowsFileSystem.FileStoreIterator();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 231 */   private static final Set<String> supportedFileAttributeViews = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new String[] { "basic", "dos", "acl", "owner", "user" })));
/*     */   private static final String GLOB_SYNTAX = "glob";
/*     */   
/*     */   public Set<String> supportedFileAttributeViews() {
/* 235 */     return supportedFileAttributeViews;
/*     */   }
/*     */   private static final String REGEX_SYNTAX = "regex";
/*     */   
/*     */   public final Path getPath(String paramString, String... paramVarArgs) {
/*     */     String str;
/* 241 */     if (paramVarArgs.length == 0) {
/* 242 */       str = paramString;
/*     */     } else {
/* 244 */       StringBuilder stringBuilder = new StringBuilder();
/* 245 */       stringBuilder.append(paramString);
/* 246 */       for (String str1 : paramVarArgs) {
/* 247 */         if (str1.length() > 0) {
/* 248 */           if (stringBuilder.length() > 0)
/* 249 */             stringBuilder.append('\\'); 
/* 250 */           stringBuilder.append(str1);
/*     */         } 
/*     */       } 
/* 253 */       str = stringBuilder.toString();
/*     */     } 
/* 255 */     return WindowsPath.parse(this, str);
/*     */   }
/*     */ 
/*     */   
/*     */   public UserPrincipalLookupService getUserPrincipalLookupService() {
/* 260 */     return LookupService.instance;
/*     */   }
/*     */   
/*     */   private static class LookupService {
/* 264 */     static final UserPrincipalLookupService instance = new UserPrincipalLookupService()
/*     */       {
/*     */ 
/*     */         
/*     */         public UserPrincipal lookupPrincipalByName(String param2String) throws IOException
/*     */         {
/* 270 */           return WindowsUserPrincipals.lookup(param2String);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public GroupPrincipal lookupPrincipalByGroupName(String param2String) throws IOException {
/* 276 */           UserPrincipal userPrincipal = WindowsUserPrincipals.lookup(param2String);
/* 277 */           if (!(userPrincipal instanceof GroupPrincipal))
/* 278 */             throw new UserPrincipalNotFoundException(param2String); 
/* 279 */           return (GroupPrincipal)userPrincipal;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public PathMatcher getPathMatcher(String paramString) {
/*     */     String str3;
/* 286 */     int i = paramString.indexOf(':');
/* 287 */     if (i <= 0 || i == paramString.length())
/* 288 */       throw new IllegalArgumentException(); 
/* 289 */     String str1 = paramString.substring(0, i);
/* 290 */     String str2 = paramString.substring(i + 1);
/*     */ 
/*     */     
/* 293 */     if (str1.equals("glob")) {
/* 294 */       str3 = Globs.toWindowsRegexPattern(str2);
/*     */     }
/* 296 */     else if (str1.equals("regex")) {
/* 297 */       str3 = str2;
/*     */     } else {
/* 299 */       throw new UnsupportedOperationException("Syntax '" + str1 + "' not recognized");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     final Pattern pattern = Pattern.compile(str3, 66);
/*     */ 
/*     */ 
/*     */     
/* 309 */     return new PathMatcher()
/*     */       {
/*     */         public boolean matches(Path param1Path) {
/* 312 */           return pattern.matcher(param1Path.toString()).matches();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WatchService newWatchService() throws IOException {
/* 323 */     return new WindowsWatchService(this);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsFileSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */