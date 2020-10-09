/*     */ package sun.security.jgss;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GSSNameImpl
/*     */   implements GSSName
/*     */ {
/*     */   static final Oid oldHostbasedServiceName;
/*     */   
/*     */   static {
/*  98 */     Oid oid = null;
/*     */     try {
/* 100 */       oid = new Oid("1.3.6.1.5.6.2");
/* 101 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 104 */     oldHostbasedServiceName = oid;
/*     */   }
/*     */   
/* 107 */   private GSSManagerImpl gssManager = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   private String appNameStr = null;
/* 118 */   private byte[] appNameBytes = null;
/* 119 */   private Oid appNameType = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   private String printableName = null;
/* 127 */   private Oid printableNameType = null;
/*     */   
/* 129 */   private HashMap<Oid, GSSNameSpi> elements = null;
/* 130 */   private GSSNameSpi mechElement = null;
/*     */ 
/*     */   
/*     */   static GSSNameImpl wrapElement(GSSManagerImpl paramGSSManagerImpl, GSSNameSpi paramGSSNameSpi) throws GSSException {
/* 134 */     return (paramGSSNameSpi == null) ? null : new GSSNameImpl(paramGSSManagerImpl, paramGSSNameSpi);
/*     */   }
/*     */ 
/*     */   
/*     */   GSSNameImpl(GSSManagerImpl paramGSSManagerImpl, GSSNameSpi paramGSSNameSpi) {
/* 139 */     this.gssManager = paramGSSManagerImpl;
/* 140 */     this.appNameStr = this.printableName = paramGSSNameSpi.toString();
/* 141 */     this.appNameType = this.printableNameType = paramGSSNameSpi.getStringNameType();
/* 142 */     this.mechElement = paramGSSNameSpi;
/* 143 */     this.elements = new HashMap<>(1);
/* 144 */     this.elements.put(paramGSSNameSpi.getMechanism(), this.mechElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GSSNameImpl(GSSManagerImpl paramGSSManagerImpl, Object paramObject, Oid paramOid) throws GSSException {
/* 151 */     this(paramGSSManagerImpl, paramObject, paramOid, (Oid)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GSSNameImpl(GSSManagerImpl paramGSSManagerImpl, Object paramObject, Oid paramOid1, Oid paramOid2) throws GSSException {
/* 160 */     if (oldHostbasedServiceName.equals(paramOid1)) {
/* 161 */       paramOid1 = GSSName.NT_HOSTBASED_SERVICE;
/*     */     }
/* 163 */     if (paramObject == null) {
/* 164 */       throw new GSSExceptionImpl(3, "Cannot import null name");
/*     */     }
/* 166 */     if (paramOid2 == null) paramOid2 = ProviderList.DEFAULT_MECH_OID; 
/* 167 */     if (NT_EXPORT_NAME.equals(paramOid1)) {
/* 168 */       importName(paramGSSManagerImpl, paramObject);
/*     */     } else {
/* 170 */       init(paramGSSManagerImpl, paramObject, paramOid1, paramOid2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(GSSManagerImpl paramGSSManagerImpl, Object paramObject, Oid paramOid1, Oid paramOid2) throws GSSException {
/* 179 */     this.gssManager = paramGSSManagerImpl;
/* 180 */     this
/* 181 */       .elements = new HashMap<>((paramGSSManagerImpl.getMechs()).length);
/*     */     
/* 183 */     if (paramObject instanceof String) {
/* 184 */       this.appNameStr = (String)paramObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       if (paramOid1 != null) {
/* 192 */         this.printableName = this.appNameStr;
/* 193 */         this.printableNameType = paramOid1;
/*     */       } 
/*     */     } else {
/* 196 */       this.appNameBytes = (byte[])paramObject;
/*     */     } 
/*     */     
/* 199 */     this.appNameType = paramOid1;
/*     */     
/* 201 */     this.mechElement = getElement(paramOid2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     if (this.printableName == null) {
/* 208 */       this.printableName = this.mechElement.toString();
/* 209 */       this.printableNameType = this.mechElement.getStringNameType();
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
/*     */   private void importName(GSSManagerImpl paramGSSManagerImpl, Object paramObject) throws GSSException {
/* 226 */     int i = 0;
/* 227 */     byte[] arrayOfByte1 = null;
/*     */     
/* 229 */     if (paramObject instanceof String) {
/*     */       try {
/* 231 */         arrayOfByte1 = ((String)paramObject).getBytes("UTF-8");
/* 232 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */     }
/*     */     else {
/*     */       
/* 236 */       arrayOfByte1 = (byte[])paramObject;
/*     */     } 
/* 238 */     if (arrayOfByte1[i++] != 4 || arrayOfByte1[i++] != 1)
/*     */     {
/* 240 */       throw new GSSExceptionImpl(3, "Exported name token id is corrupted!");
/*     */     }
/*     */     
/* 243 */     int j = (0xFF & arrayOfByte1[i++]) << 8 | 0xFF & arrayOfByte1[i++];
/*     */     
/* 245 */     ObjectIdentifier objectIdentifier = null;
/*     */     try {
/* 247 */       DerInputStream derInputStream = new DerInputStream(arrayOfByte1, i, j);
/*     */       
/* 249 */       objectIdentifier = new ObjectIdentifier(derInputStream);
/* 250 */     } catch (IOException iOException) {
/* 251 */       throw new GSSExceptionImpl(3, "Exported name Object identifier is corrupted!");
/*     */     } 
/*     */     
/* 254 */     Oid oid = new Oid(objectIdentifier.toString());
/* 255 */     i += j;
/* 256 */     int k = (0xFF & arrayOfByte1[i++]) << 24 | (0xFF & arrayOfByte1[i++]) << 16 | (0xFF & arrayOfByte1[i++]) << 8 | 0xFF & arrayOfByte1[i++];
/*     */ 
/*     */ 
/*     */     
/* 260 */     if (k < 0 || i > arrayOfByte1.length - k) {
/* 261 */       throw new GSSExceptionImpl(3, "Exported name mech name is corrupted!");
/*     */     }
/*     */     
/* 264 */     byte[] arrayOfByte2 = new byte[k];
/* 265 */     System.arraycopy(arrayOfByte1, i, arrayOfByte2, 0, k);
/*     */     
/* 267 */     init(paramGSSManagerImpl, arrayOfByte2, NT_EXPORT_NAME, oid);
/*     */   }
/*     */   
/*     */   public GSSName canonicalize(Oid paramOid) throws GSSException {
/* 271 */     if (paramOid == null) paramOid = ProviderList.DEFAULT_MECH_OID;
/*     */     
/* 273 */     return wrapElement(this.gssManager, getElement(paramOid));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(GSSName paramGSSName) throws GSSException {
/* 283 */     if (isAnonymous() || paramGSSName.isAnonymous()) {
/* 284 */       return false;
/*     */     }
/* 286 */     if (paramGSSName == this) {
/* 287 */       return true;
/*     */     }
/* 289 */     if (!(paramGSSName instanceof GSSNameImpl)) {
/* 290 */       return equals(this.gssManager.createName(paramGSSName.toString(), paramGSSName
/* 291 */             .getStringNameType()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     GSSNameImpl gSSNameImpl = (GSSNameImpl)paramGSSName;
/*     */     
/* 300 */     GSSNameSpi gSSNameSpi1 = this.mechElement;
/* 301 */     GSSNameSpi gSSNameSpi2 = gSSNameImpl.mechElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     if (gSSNameSpi1 == null && gSSNameSpi2 != null) {
/* 308 */       gSSNameSpi1 = getElement(gSSNameSpi2.getMechanism());
/* 309 */     } else if (gSSNameSpi1 != null && gSSNameSpi2 == null) {
/* 310 */       gSSNameSpi2 = gSSNameImpl.getElement(gSSNameSpi1.getMechanism());
/*     */     } 
/*     */     
/* 313 */     if (gSSNameSpi1 != null && gSSNameSpi2 != null) {
/* 314 */       return gSSNameSpi1.equals(gSSNameSpi2);
/*     */     }
/*     */     
/* 317 */     if (this.appNameType != null && gSSNameImpl.appNameType != null) {
/*     */       
/* 319 */       if (!this.appNameType.equals(gSSNameImpl.appNameType)) {
/* 320 */         return false;
/*     */       }
/* 322 */       byte[] arrayOfByte1 = null;
/* 323 */       byte[] arrayOfByte2 = null;
/*     */ 
/*     */       
/*     */       try {
/* 327 */         arrayOfByte1 = (this.appNameStr != null) ? this.appNameStr.getBytes("UTF-8") : this.appNameBytes;
/*     */ 
/*     */ 
/*     */         
/* 331 */         arrayOfByte2 = (gSSNameImpl.appNameStr != null) ? gSSNameImpl.appNameStr.getBytes("UTF-8") : gSSNameImpl.appNameBytes;
/*     */       }
/* 333 */       catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */ 
/*     */ 
/*     */       
/* 337 */       return Arrays.equals(arrayOfByte1, arrayOfByte2);
/*     */     } 
/*     */     
/* 340 */     return false;
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
/*     */   public int hashCode() {
/* 360 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*     */     try {
/* 369 */       if (paramObject instanceof GSSName)
/* 370 */         return equals((GSSName)paramObject); 
/* 371 */     } catch (GSSException gSSException) {}
/*     */ 
/*     */ 
/*     */     
/* 375 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] export() throws GSSException {
/* 404 */     if (this.mechElement == null)
/*     */     {
/* 406 */       this.mechElement = getElement(ProviderList.DEFAULT_MECH_OID);
/*     */     }
/*     */     
/* 409 */     byte[] arrayOfByte1 = this.mechElement.export();
/* 410 */     byte[] arrayOfByte2 = null;
/* 411 */     ObjectIdentifier objectIdentifier = null;
/*     */ 
/*     */     
/*     */     try {
/* 415 */       objectIdentifier = new ObjectIdentifier(this.mechElement.getMechanism().toString());
/* 416 */     } catch (IOException iOException) {
/* 417 */       throw new GSSExceptionImpl(11, "Invalid OID String ");
/*     */     } 
/*     */     
/* 420 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     try {
/* 422 */       derOutputStream.putOID(objectIdentifier);
/* 423 */     } catch (IOException iOException) {
/* 424 */       throw new GSSExceptionImpl(11, "Could not ASN.1 Encode " + objectIdentifier
/*     */           
/* 426 */           .toString());
/*     */     } 
/* 428 */     arrayOfByte2 = derOutputStream.toByteArray();
/*     */     
/* 430 */     byte[] arrayOfByte3 = new byte[4 + arrayOfByte2.length + 4 + arrayOfByte1.length];
/*     */ 
/*     */     
/* 433 */     int i = 0;
/* 434 */     arrayOfByte3[i++] = 4;
/* 435 */     arrayOfByte3[i++] = 1;
/* 436 */     arrayOfByte3[i++] = (byte)(arrayOfByte2.length >>> 8);
/* 437 */     arrayOfByte3[i++] = (byte)arrayOfByte2.length;
/* 438 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte3, i, arrayOfByte2.length);
/* 439 */     i += arrayOfByte2.length;
/* 440 */     arrayOfByte3[i++] = (byte)(arrayOfByte1.length >>> 24);
/* 441 */     arrayOfByte3[i++] = (byte)(arrayOfByte1.length >>> 16);
/* 442 */     arrayOfByte3[i++] = (byte)(arrayOfByte1.length >>> 8);
/* 443 */     arrayOfByte3[i++] = (byte)arrayOfByte1.length;
/* 444 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte3, i, arrayOfByte1.length);
/* 445 */     return arrayOfByte3;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 449 */     return this.printableName;
/*     */   }
/*     */ 
/*     */   
/*     */   public Oid getStringNameType() throws GSSException {
/* 454 */     return this.printableNameType;
/*     */   }
/*     */   
/*     */   public boolean isAnonymous() {
/* 458 */     if (this.printableNameType == null) {
/* 459 */       return false;
/*     */     }
/* 461 */     return GSSName.NT_ANONYMOUS.equals(this.printableNameType);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMN() {
/* 466 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized GSSNameSpi getElement(Oid paramOid) throws GSSException {
/* 472 */     GSSNameSpi gSSNameSpi = this.elements.get(paramOid);
/*     */     
/* 474 */     if (gSSNameSpi == null) {
/* 475 */       if (this.appNameStr != null) {
/*     */         
/* 477 */         gSSNameSpi = this.gssManager.getNameElement(this.appNameStr, this.appNameType, paramOid);
/*     */       } else {
/*     */         
/* 480 */         gSSNameSpi = this.gssManager.getNameElement(this.appNameBytes, this.appNameType, paramOid);
/*     */       } 
/* 482 */       this.elements.put(paramOid, gSSNameSpi);
/*     */     } 
/* 484 */     return gSSNameSpi;
/*     */   }
/*     */   
/*     */   Set<GSSNameSpi> getElements() {
/* 488 */     return new HashSet<>(this.elements.values());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getNameTypeStr(Oid paramOid) {
/* 493 */     if (paramOid == null) {
/* 494 */       return "(NT is null)";
/*     */     }
/* 496 */     if (paramOid.equals(NT_USER_NAME))
/* 497 */       return "NT_USER_NAME"; 
/* 498 */     if (paramOid.equals(NT_HOSTBASED_SERVICE))
/* 499 */       return "NT_HOSTBASED_SERVICE"; 
/* 500 */     if (paramOid.equals(NT_EXPORT_NAME))
/* 501 */       return "NT_EXPORT_NAME"; 
/* 502 */     if (paramOid.equals(GSSUtil.NT_GSS_KRB5_PRINCIPAL)) {
/* 503 */       return "NT_GSS_KRB5_PRINCIPAL";
/*     */     }
/* 505 */     return "Unknown";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jgss\GSSNameImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */