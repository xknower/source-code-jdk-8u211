/*     */ package java.util.jar;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.CodeSource;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Spliterators;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import sun.misc.IOUtils;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.util.ManifestEntryVerifier;
/*     */ import sun.security.util.SignatureFileVerifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JarFile
/*     */   extends ZipFile
/*     */ {
/*     */   private SoftReference<Manifest> manRef;
/*     */   private JarEntry manEntry;
/*     */   private JarVerifier jv;
/*     */   private boolean jvInitialized;
/*     */   private boolean verify;
/*     */   private boolean hasClassPathAttribute;
/*     */   private volatile boolean hasCheckedSpecialAttributes;
/*     */   public static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";
/*     */   
/*     */   static {
/*  85 */     SharedSecrets.setJavaUtilJarAccess(new JavaUtilJarAccessImpl());
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
/*     */   
/*     */   public JarFile(String paramString) throws IOException {
/* 103 */     this(new File(paramString), true, 1);
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
/*     */   public JarFile(String paramString, boolean paramBoolean) throws IOException {
/* 117 */     this(new File(paramString), paramBoolean, 1);
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
/*     */   public JarFile(File paramFile) throws IOException {
/* 130 */     this(paramFile, true, 1);
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
/*     */   public JarFile(File paramFile, boolean paramBoolean) throws IOException {
/* 145 */     this(paramFile, paramBoolean, 1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JarFile(File paramFile, boolean paramBoolean, int paramInt) throws IOException {
/* 166 */     super(paramFile, paramInt);
/* 167 */     this.verify = paramBoolean;
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
/*     */   public Manifest getManifest() throws IOException {
/* 180 */     return getManifestFromReference();
/*     */   }
/*     */   
/*     */   private Manifest getManifestFromReference() throws IOException {
/* 184 */     Manifest manifest = (this.manRef != null) ? this.manRef.get() : null;
/*     */     
/* 186 */     if (manifest == null) {
/*     */       
/* 188 */       JarEntry jarEntry = getManEntry();
/*     */ 
/*     */       
/* 191 */       if (jarEntry != null) {
/* 192 */         if (this.verify) {
/* 193 */           byte[] arrayOfByte = getBytes(jarEntry);
/* 194 */           if (!this.jvInitialized) {
/* 195 */             this.jv = new JarVerifier(arrayOfByte);
/*     */           }
/* 197 */           manifest = new Manifest(this.jv, new ByteArrayInputStream(arrayOfByte));
/*     */         } else {
/* 199 */           manifest = new Manifest(super.getInputStream(jarEntry));
/*     */         } 
/* 201 */         this.manRef = new SoftReference<>(manifest);
/*     */       } 
/*     */     } 
/* 204 */     return manifest;
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
/*     */ 
/*     */   
/*     */   public JarEntry getJarEntry(String paramString) {
/* 223 */     return (JarEntry)getEntry(paramString);
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
/*     */   public ZipEntry getEntry(String paramString) {
/* 240 */     ZipEntry zipEntry = super.getEntry(paramString);
/* 241 */     if (zipEntry != null) {
/* 242 */       return new JarFileEntry(zipEntry);
/*     */     }
/* 244 */     return null;
/*     */   }
/*     */   
/*     */   private class JarEntryIterator
/*     */     implements Enumeration<JarEntry>, Iterator<JarEntry>
/*     */   {
/* 250 */     final Enumeration<? extends ZipEntry> e = JarFile.this.entries();
/*     */     
/*     */     public boolean hasNext() {
/* 253 */       return this.e.hasMoreElements();
/*     */     }
/*     */     
/*     */     public JarEntry next() {
/* 257 */       ZipEntry zipEntry = this.e.nextElement();
/* 258 */       return new JarFile.JarFileEntry(zipEntry);
/*     */     }
/*     */     
/*     */     public boolean hasMoreElements() {
/* 262 */       return hasNext();
/*     */     }
/*     */     
/*     */     public JarEntry nextElement() {
/* 266 */       return next();
/*     */     }
/*     */ 
/*     */     
/*     */     private JarEntryIterator() {}
/*     */   }
/*     */   
/*     */   public Enumeration<JarEntry> entries() {
/* 274 */     return new JarEntryIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<JarEntry> stream() {
/* 279 */     return StreamSupport.stream(Spliterators.spliterator(new JarEntryIterator(), 
/* 280 */           size(), 1297), false);
/*     */   }
/*     */   
/*     */   private class JarFileEntry
/*     */     extends JarEntry
/*     */   {
/*     */     JarFileEntry(ZipEntry param1ZipEntry) {
/* 287 */       super(param1ZipEntry);
/*     */     }
/*     */     public Attributes getAttributes() throws IOException {
/* 290 */       Manifest manifest = JarFile.this.getManifest();
/* 291 */       if (manifest != null) {
/* 292 */         return manifest.getAttributes(getName());
/*     */       }
/* 294 */       return null;
/*     */     }
/*     */     
/*     */     public Certificate[] getCertificates() {
/*     */       try {
/* 299 */         JarFile.this.maybeInstantiateVerifier();
/* 300 */       } catch (IOException iOException) {
/* 301 */         throw new RuntimeException(iOException);
/*     */       } 
/* 303 */       if (this.certs == null && JarFile.this.jv != null) {
/* 304 */         this.certs = JarFile.this.jv.getCerts(JarFile.this, this);
/*     */       }
/* 306 */       return (this.certs == null) ? null : (Certificate[])this.certs.clone();
/*     */     }
/*     */     public CodeSigner[] getCodeSigners() {
/*     */       try {
/* 310 */         JarFile.this.maybeInstantiateVerifier();
/* 311 */       } catch (IOException iOException) {
/* 312 */         throw new RuntimeException(iOException);
/*     */       } 
/* 314 */       if (this.signers == null && JarFile.this.jv != null) {
/* 315 */         this.signers = JarFile.this.jv.getCodeSigners(JarFile.this, this);
/*     */       }
/* 317 */       return (this.signers == null) ? null : (CodeSigner[])this.signers.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void maybeInstantiateVerifier() throws IOException {
/* 328 */     if (this.jv != null) {
/*     */       return;
/*     */     }
/*     */     
/* 332 */     if (this.verify) {
/* 333 */       String[] arrayOfString = getMetaInfEntryNames();
/* 334 */       if (arrayOfString != null) {
/* 335 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 336 */           String str = arrayOfString[b].toUpperCase(Locale.ENGLISH);
/* 337 */           if (str.endsWith(".DSA") || str
/* 338 */             .endsWith(".RSA") || str
/* 339 */             .endsWith(".EC") || str
/* 340 */             .endsWith(".SF")) {
/*     */ 
/*     */ 
/*     */             
/* 344 */             getManifest();
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 351 */       this.verify = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeVerifier() {
/* 361 */     ManifestEntryVerifier manifestEntryVerifier = null;
/*     */ 
/*     */     
/*     */     try {
/* 365 */       String[] arrayOfString = getMetaInfEntryNames();
/* 366 */       if (arrayOfString != null) {
/* 367 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 368 */           String str = arrayOfString[b].toUpperCase(Locale.ENGLISH);
/* 369 */           if ("META-INF/MANIFEST.MF".equals(str) || 
/* 370 */             SignatureFileVerifier.isBlockOrSF(str)) {
/* 371 */             JarEntry jarEntry = getJarEntry(arrayOfString[b]);
/* 372 */             if (jarEntry == null) {
/* 373 */               throw new JarException("corrupted jar file");
/*     */             }
/* 375 */             if (manifestEntryVerifier == null)
/*     */             {
/* 377 */               manifestEntryVerifier = new ManifestEntryVerifier(getManifestFromReference());
/*     */             }
/* 379 */             byte[] arrayOfByte = getBytes(jarEntry);
/* 380 */             if (arrayOfByte != null && arrayOfByte.length > 0) {
/* 381 */               this.jv.beginEntry(jarEntry, manifestEntryVerifier);
/* 382 */               this.jv.update(arrayOfByte.length, arrayOfByte, 0, arrayOfByte.length, manifestEntryVerifier);
/* 383 */               this.jv.update(-1, null, 0, 0, manifestEntryVerifier);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 388 */     } catch (IOException iOException) {
/*     */ 
/*     */       
/* 391 */       this.jv = null;
/* 392 */       this.verify = false;
/* 393 */       if (JarVerifier.debug != null) {
/* 394 */         JarVerifier.debug.println("jarfile parsing error!");
/* 395 */         iOException.printStackTrace();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 402 */     if (this.jv != null) {
/*     */       
/* 404 */       this.jv.doneWithMeta();
/* 405 */       if (JarVerifier.debug != null) {
/* 406 */         JarVerifier.debug.println("done with meta!");
/*     */       }
/*     */       
/* 409 */       if (this.jv.nothingToVerify()) {
/* 410 */         if (JarVerifier.debug != null) {
/* 411 */           JarVerifier.debug.println("nothing to verify!");
/*     */         }
/* 413 */         this.jv = null;
/* 414 */         this.verify = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] getBytes(ZipEntry paramZipEntry) throws IOException {
/* 424 */     try (InputStream null = super.getInputStream(paramZipEntry)) {
/* 425 */       return IOUtils.readFully(inputStream, (int)paramZipEntry.getSize(), true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized InputStream getInputStream(ZipEntry paramZipEntry) throws IOException {
/* 445 */     maybeInstantiateVerifier();
/* 446 */     if (this.jv == null) {
/* 447 */       return super.getInputStream(paramZipEntry);
/*     */     }
/* 449 */     if (!this.jvInitialized) {
/* 450 */       initializeVerifier();
/* 451 */       this.jvInitialized = true;
/*     */ 
/*     */ 
/*     */       
/* 455 */       if (this.jv == null) {
/* 456 */         return super.getInputStream(paramZipEntry);
/*     */       }
/*     */     } 
/*     */     
/* 460 */     return new JarVerifier.VerifierStream(
/* 461 */         getManifestFromReference(), (paramZipEntry instanceof JarFileEntry) ? (JarEntry)paramZipEntry : 
/*     */         
/* 463 */         getJarEntry(paramZipEntry.getName()), super
/* 464 */         .getInputStream(paramZipEntry), this.jv);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 469 */   private static final char[] CLASSPATH_CHARS = new char[] { 'c', 'l', 'a', 's', 's', '-', 'p', 'a', 't', 'h' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 476 */   private static final int[] CLASSPATH_LASTOCC = new int[128];
/* 477 */   private static final int[] CLASSPATH_OPTOSFT = new int[10]; private static String javaHome; static {
/* 478 */     CLASSPATH_LASTOCC[99] = 1;
/* 479 */     CLASSPATH_LASTOCC[108] = 2;
/* 480 */     CLASSPATH_LASTOCC[115] = 5;
/* 481 */     CLASSPATH_LASTOCC[45] = 6;
/* 482 */     CLASSPATH_LASTOCC[112] = 7;
/* 483 */     CLASSPATH_LASTOCC[97] = 8;
/* 484 */     CLASSPATH_LASTOCC[116] = 9;
/* 485 */     CLASSPATH_LASTOCC[104] = 10;
/* 486 */     for (byte b = 0; b < 9; b++)
/* 487 */       CLASSPATH_OPTOSFT[b] = 10; 
/* 488 */     CLASSPATH_OPTOSFT[9] = 1;
/*     */   }
/*     */   private static volatile String[] jarNames;
/*     */   private JarEntry getManEntry() {
/* 492 */     if (this.manEntry == null) {
/*     */       
/* 494 */       this.manEntry = getJarEntry("META-INF/MANIFEST.MF");
/* 495 */       if (this.manEntry == null) {
/*     */ 
/*     */         
/* 498 */         String[] arrayOfString = getMetaInfEntryNames();
/* 499 */         if (arrayOfString != null) {
/* 500 */           for (byte b = 0; b < arrayOfString.length; b++) {
/* 501 */             if ("META-INF/MANIFEST.MF".equals(arrayOfString[b]
/* 502 */                 .toUpperCase(Locale.ENGLISH))) {
/* 503 */               this.manEntry = getJarEntry(arrayOfString[b]);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 510 */     return this.manEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasClassPathAttribute() throws IOException {
/* 518 */     checkForSpecialAttributes();
/* 519 */     return this.hasClassPathAttribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean match(char[] paramArrayOfchar, byte[] paramArrayOfbyte, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 528 */     int i = paramArrayOfchar.length;
/* 529 */     int j = paramArrayOfbyte.length - i;
/* 530 */     int k = 0;
/*     */     
/* 532 */     label19: while (k <= j) {
/* 533 */       for (int m = i - 1; m >= 0; m--) {
/* 534 */         char c = (char)paramArrayOfbyte[k + m];
/* 535 */         c = ((c - 65 | 90 - c) >= 0) ? (char)(c + 32) : c;
/* 536 */         if (c != paramArrayOfchar[m]) {
/* 537 */           k += Math.max(m + 1 - paramArrayOfint1[c & 0x7F], paramArrayOfint2[m]);
/*     */           continue label19;
/*     */         } 
/*     */       } 
/* 541 */       return true;
/*     */     } 
/* 543 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkForSpecialAttributes() throws IOException {
/* 551 */     if (this.hasCheckedSpecialAttributes)
/* 552 */       return;  if (!isKnownNotToHaveSpecialAttributes()) {
/* 553 */       JarEntry jarEntry = getManEntry();
/* 554 */       if (jarEntry != null) {
/* 555 */         byte[] arrayOfByte = getBytes(jarEntry);
/* 556 */         if (match(CLASSPATH_CHARS, arrayOfByte, CLASSPATH_LASTOCC, CLASSPATH_OPTOSFT))
/* 557 */           this.hasClassPathAttribute = true; 
/*     */       } 
/*     */     } 
/* 560 */     this.hasCheckedSpecialAttributes = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isKnownNotToHaveSpecialAttributes() {
/* 570 */     if (javaHome == null) {
/* 571 */       javaHome = AccessController.<String>doPrivileged(new GetPropertyAction("java.home"));
/*     */     }
/*     */     
/* 574 */     if (jarNames == null) {
/* 575 */       String[] arrayOfString = new String[11];
/* 576 */       String str = File.separator;
/* 577 */       byte b = 0;
/* 578 */       arrayOfString[b++] = str + "rt.jar";
/* 579 */       arrayOfString[b++] = str + "jsse.jar";
/* 580 */       arrayOfString[b++] = str + "jce.jar";
/* 581 */       arrayOfString[b++] = str + "charsets.jar";
/* 582 */       arrayOfString[b++] = str + "dnsns.jar";
/* 583 */       arrayOfString[b++] = str + "zipfs.jar";
/* 584 */       arrayOfString[b++] = str + "localedata.jar";
/* 585 */       arrayOfString[b++] = str = "cldrdata.jar";
/* 586 */       arrayOfString[b++] = str + "sunjce_provider.jar";
/* 587 */       arrayOfString[b++] = str + "sunpkcs11.jar";
/* 588 */       arrayOfString[b++] = str + "sunec.jar";
/* 589 */       jarNames = arrayOfString;
/*     */     } 
/*     */     
/* 592 */     String str1 = getName();
/* 593 */     String str2 = javaHome;
/* 594 */     if (str1.startsWith(str2)) {
/* 595 */       String[] arrayOfString = jarNames;
/* 596 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 597 */         if (str1.endsWith(arrayOfString[b])) {
/* 598 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 602 */     return false;
/*     */   }
/*     */   
/*     */   synchronized void ensureInitialization() {
/*     */     try {
/* 607 */       maybeInstantiateVerifier();
/* 608 */     } catch (IOException iOException) {
/* 609 */       throw new RuntimeException(iOException);
/*     */     } 
/* 611 */     if (this.jv != null && !this.jvInitialized) {
/* 612 */       initializeVerifier();
/* 613 */       this.jvInitialized = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   JarEntry newEntry(ZipEntry paramZipEntry) {
/* 618 */     return new JarFileEntry(paramZipEntry);
/*     */   }
/*     */   
/*     */   Enumeration<String> entryNames(CodeSource[] paramArrayOfCodeSource) {
/* 622 */     ensureInitialization();
/* 623 */     if (this.jv != null) {
/* 624 */       return this.jv.entryNames(this, paramArrayOfCodeSource);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 631 */     boolean bool = false;
/* 632 */     for (byte b = 0; b < paramArrayOfCodeSource.length; b++) {
/* 633 */       if (paramArrayOfCodeSource[b].getCodeSigners() == null) {
/* 634 */         bool = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 638 */     if (bool) {
/* 639 */       return unsignedEntryNames();
/*     */     }
/* 641 */     return new Enumeration<String>()
/*     */       {
/*     */         public boolean hasMoreElements() {
/* 644 */           return false;
/*     */         }
/*     */         
/*     */         public String nextElement() {
/* 648 */           throw new NoSuchElementException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Enumeration<JarEntry> entries2() {
/* 660 */     ensureInitialization();
/* 661 */     if (this.jv != null) {
/* 662 */       return this.jv.entries2(this, super.entries());
/*     */     }
/*     */ 
/*     */     
/* 666 */     final Enumeration<? extends ZipEntry> enum_ = super.entries();
/* 667 */     return new Enumeration<JarEntry>()
/*     */       {
/*     */         ZipEntry entry;
/*     */         
/*     */         public boolean hasMoreElements() {
/* 672 */           if (this.entry != null) {
/* 673 */             return true;
/*     */           }
/* 675 */           while (enum_.hasMoreElements()) {
/* 676 */             ZipEntry zipEntry = enum_.nextElement();
/* 677 */             if (JarVerifier.isSigningRelated(zipEntry.getName())) {
/*     */               continue;
/*     */             }
/* 680 */             this.entry = zipEntry;
/* 681 */             return true;
/*     */           } 
/* 683 */           return false;
/*     */         }
/*     */         
/*     */         public JarFile.JarFileEntry nextElement() {
/* 687 */           if (hasMoreElements()) {
/* 688 */             ZipEntry zipEntry = this.entry;
/* 689 */             this.entry = null;
/* 690 */             return new JarFile.JarFileEntry(zipEntry);
/*     */           } 
/* 692 */           throw new NoSuchElementException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   CodeSource[] getCodeSources(URL paramURL) {
/* 698 */     ensureInitialization();
/* 699 */     if (this.jv != null) {
/* 700 */       return this.jv.getCodeSources(this, paramURL);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 707 */     Enumeration<String> enumeration = unsignedEntryNames();
/* 708 */     if (enumeration.hasMoreElements()) {
/* 709 */       return new CodeSource[] { JarVerifier.getUnsignedCS(paramURL) };
/*     */     }
/* 711 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Enumeration<String> unsignedEntryNames() {
/* 716 */     final Enumeration<JarEntry> entries = entries();
/* 717 */     return new Enumeration<String>()
/*     */       {
/*     */         String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean hasMoreElements() {
/* 726 */           if (this.name != null) {
/* 727 */             return true;
/*     */           }
/* 729 */           while (entries.hasMoreElements()) {
/*     */             
/* 731 */             ZipEntry zipEntry = entries.nextElement();
/* 732 */             String str = zipEntry.getName();
/* 733 */             if (zipEntry.isDirectory() || JarVerifier.isSigningRelated(str)) {
/*     */               continue;
/*     */             }
/* 736 */             this.name = str;
/* 737 */             return true;
/*     */           } 
/* 739 */           return false;
/*     */         }
/*     */         
/*     */         public String nextElement() {
/* 743 */           if (hasMoreElements()) {
/* 744 */             String str = this.name;
/* 745 */             this.name = null;
/* 746 */             return str;
/*     */           } 
/* 748 */           throw new NoSuchElementException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   CodeSource getCodeSource(URL paramURL, String paramString) {
/* 754 */     ensureInitialization();
/* 755 */     if (this.jv != null) {
/* 756 */       if (this.jv.eagerValidation) {
/* 757 */         CodeSource codeSource = null;
/* 758 */         JarEntry jarEntry = getJarEntry(paramString);
/* 759 */         if (jarEntry != null) {
/* 760 */           codeSource = this.jv.getCodeSource(paramURL, this, jarEntry);
/*     */         } else {
/* 762 */           codeSource = this.jv.getCodeSource(paramURL, paramString);
/*     */         } 
/* 764 */         return codeSource;
/*     */       } 
/* 766 */       return this.jv.getCodeSource(paramURL, paramString);
/*     */     } 
/*     */ 
/*     */     
/* 770 */     return JarVerifier.getUnsignedCS(paramURL);
/*     */   }
/*     */   
/*     */   void setEagerValidation(boolean paramBoolean) {
/*     */     try {
/* 775 */       maybeInstantiateVerifier();
/* 776 */     } catch (IOException iOException) {
/* 777 */       throw new RuntimeException(iOException);
/*     */     } 
/* 779 */     if (this.jv != null) {
/* 780 */       this.jv.setEagerValidation(paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */   List<Object> getManifestDigests() {
/* 785 */     ensureInitialization();
/* 786 */     if (this.jv != null) {
/* 787 */       return this.jv.getManifestDigests();
/*     */     }
/* 789 */     return new ArrayList();
/*     */   }
/*     */   
/*     */   private native String[] getMetaInfEntryNames();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\jar\JarFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */