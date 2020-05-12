/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.ProviderMismatchException;
/*     */ import java.nio.file.attribute.AclEntry;
/*     */ import java.nio.file.attribute.AclEntryFlag;
/*     */ import java.nio.file.attribute.AclEntryPermission;
/*     */ import java.nio.file.attribute.AclEntryType;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.nio.file.attribute.UserPrincipal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsSecurityDescriptor
/*     */ {
/*  43 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final short SIZEOF_ACL = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final short SIZEOF_ACCESS_ALLOWED_ACE = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final short SIZEOF_ACCESS_DENIED_ACE = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final short SIZEOF_SECURITY_DESCRIPTOR = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final short OFFSETOF_TYPE = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final short OFFSETOF_FLAGS = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final short OFFSETOF_ACCESS_MASK = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final short OFFSETOF_SID = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private static final WindowsSecurityDescriptor NULL_DESCRIPTOR = new WindowsSecurityDescriptor();
/*     */ 
/*     */   
/*     */   private final List<Long> sidList;
/*     */   
/*     */   private final NativeBuffer aclBuffer;
/*     */   
/*     */   private final NativeBuffer sdBuffer;
/*     */ 
/*     */   
/*     */   private WindowsSecurityDescriptor() {
/* 104 */     this.sidList = null;
/* 105 */     this.aclBuffer = null;
/* 106 */     this.sdBuffer = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WindowsSecurityDescriptor(List<AclEntry> paramList) throws IOException {
/* 113 */     boolean bool = false;
/*     */ 
/*     */     
/* 116 */     paramList = new ArrayList<>(paramList);
/*     */ 
/*     */     
/* 119 */     this.sidList = new ArrayList<>(paramList.size());
/*     */     
/*     */     try {
/* 122 */       int i = 8;
/*     */ 
/*     */       
/* 125 */       for (AclEntry aclEntry : paramList) {
/* 126 */         UserPrincipal userPrincipal = aclEntry.principal();
/* 127 */         if (!(userPrincipal instanceof WindowsUserPrincipals.User))
/* 128 */           throw new ProviderMismatchException(); 
/* 129 */         String str = ((WindowsUserPrincipals.User)userPrincipal).sidString();
/*     */         try {
/* 131 */           long l = WindowsNativeDispatcher.ConvertStringSidToSid(str);
/* 132 */           this.sidList.add(Long.valueOf(l));
/*     */ 
/*     */           
/* 135 */           i += WindowsNativeDispatcher.GetLengthSid(l) + 
/* 136 */             Math.max(12, 12);
/*     */         }
/* 138 */         catch (WindowsException windowsException) {
/* 139 */           throw new IOException("Failed to get SID for " + userPrincipal.getName() + ": " + windowsException
/* 140 */               .errorString());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 145 */       this.aclBuffer = NativeBuffers.getNativeBuffer(i);
/* 146 */       this.sdBuffer = NativeBuffers.getNativeBuffer(20);
/*     */       
/* 148 */       WindowsNativeDispatcher.InitializeAcl(this.aclBuffer.address(), i);
/*     */ 
/*     */       
/* 151 */       byte b = 0;
/* 152 */       while (b < paramList.size()) {
/* 153 */         AclEntry aclEntry = paramList.get(b);
/* 154 */         long l = ((Long)this.sidList.get(b)).longValue();
/*     */         try {
/* 156 */           encode(aclEntry, l, this.aclBuffer.address());
/* 157 */         } catch (WindowsException windowsException) {
/* 158 */           throw new IOException("Failed to encode ACE: " + windowsException
/* 159 */               .errorString());
/*     */         } 
/* 161 */         b++;
/*     */       } 
/*     */ 
/*     */       
/* 165 */       WindowsNativeDispatcher.InitializeSecurityDescriptor(this.sdBuffer.address());
/* 166 */       WindowsNativeDispatcher.SetSecurityDescriptorDacl(this.sdBuffer.address(), this.aclBuffer.address());
/* 167 */       bool = true;
/* 168 */     } catch (WindowsException windowsException) {
/* 169 */       throw new IOException(windowsException.getMessage());
/*     */     } finally {
/*     */       
/* 172 */       if (!bool) {
/* 173 */         release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void release() {
/* 181 */     if (this.sdBuffer != null)
/* 182 */       this.sdBuffer.release(); 
/* 183 */     if (this.aclBuffer != null)
/* 184 */       this.aclBuffer.release(); 
/* 185 */     if (this.sidList != null)
/*     */     {
/* 187 */       for (Long long_ : this.sidList) {
/* 188 */         WindowsNativeDispatcher.LocalFree(long_.longValue());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long address() {
/* 197 */     return (this.sdBuffer == null) ? 0L : this.sdBuffer.address();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AclEntry decode(long paramLong) throws IOException {
/*     */     AclEntryType aclEntryType;
/* 205 */     byte b1 = unsafe.getByte(paramLong + 0L);
/* 206 */     if (b1 != 0 && b1 != 1) {
/* 207 */       return null;
/*     */     }
/* 209 */     if (b1 == 0) {
/* 210 */       aclEntryType = AclEntryType.ALLOW;
/*     */     } else {
/* 212 */       aclEntryType = AclEntryType.DENY;
/*     */     } 
/*     */ 
/*     */     
/* 216 */     byte b2 = unsafe.getByte(paramLong + 1L);
/* 217 */     EnumSet<AclEntryFlag> enumSet = EnumSet.noneOf(AclEntryFlag.class);
/* 218 */     if ((b2 & 0x1) != 0)
/* 219 */       enumSet.add(AclEntryFlag.FILE_INHERIT); 
/* 220 */     if ((b2 & 0x2) != 0)
/* 221 */       enumSet.add(AclEntryFlag.DIRECTORY_INHERIT); 
/* 222 */     if ((b2 & 0x4) != 0)
/* 223 */       enumSet.add(AclEntryFlag.NO_PROPAGATE_INHERIT); 
/* 224 */     if ((b2 & 0x8) != 0) {
/* 225 */       enumSet.add(AclEntryFlag.INHERIT_ONLY);
/*     */     }
/*     */     
/* 228 */     int i = unsafe.getInt(paramLong + 4L);
/* 229 */     EnumSet<AclEntryPermission> enumSet1 = EnumSet.noneOf(AclEntryPermission.class);
/* 230 */     if ((i & 0x1) > 0)
/* 231 */       enumSet1.add(AclEntryPermission.READ_DATA); 
/* 232 */     if ((i & 0x2) > 0)
/* 233 */       enumSet1.add(AclEntryPermission.WRITE_DATA); 
/* 234 */     if ((i & 0x4) > 0)
/* 235 */       enumSet1.add(AclEntryPermission.APPEND_DATA); 
/* 236 */     if ((i & 0x8) > 0)
/* 237 */       enumSet1.add(AclEntryPermission.READ_NAMED_ATTRS); 
/* 238 */     if ((i & 0x10) > 0)
/* 239 */       enumSet1.add(AclEntryPermission.WRITE_NAMED_ATTRS); 
/* 240 */     if ((i & 0x20) > 0)
/* 241 */       enumSet1.add(AclEntryPermission.EXECUTE); 
/* 242 */     if ((i & 0x40) > 0)
/* 243 */       enumSet1.add(AclEntryPermission.DELETE_CHILD); 
/* 244 */     if ((i & 0x80) > 0)
/* 245 */       enumSet1.add(AclEntryPermission.READ_ATTRIBUTES); 
/* 246 */     if ((i & 0x100) > 0)
/* 247 */       enumSet1.add(AclEntryPermission.WRITE_ATTRIBUTES); 
/* 248 */     if ((i & 0x10000) > 0)
/* 249 */       enumSet1.add(AclEntryPermission.DELETE); 
/* 250 */     if ((i & 0x20000) > 0)
/* 251 */       enumSet1.add(AclEntryPermission.READ_ACL); 
/* 252 */     if ((i & 0x40000) > 0)
/* 253 */       enumSet1.add(AclEntryPermission.WRITE_ACL); 
/* 254 */     if ((i & 0x80000) > 0)
/* 255 */       enumSet1.add(AclEntryPermission.WRITE_OWNER); 
/* 256 */     if ((i & 0x100000) > 0) {
/* 257 */       enumSet1.add(AclEntryPermission.SYNCHRONIZE);
/*     */     }
/*     */     
/* 260 */     long l = paramLong + 8L;
/* 261 */     UserPrincipal userPrincipal = WindowsUserPrincipals.fromSid(l);
/*     */     
/* 263 */     return AclEntry.newBuilder()
/* 264 */       .setType(aclEntryType)
/* 265 */       .setPrincipal(userPrincipal)
/* 266 */       .setFlags(enumSet).setPermissions(enumSet1).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void encode(AclEntry paramAclEntry, long paramLong1, long paramLong2) throws WindowsException {
/* 274 */     if (paramAclEntry.type() != AclEntryType.ALLOW && paramAclEntry.type() != AclEntryType.DENY)
/*     */       return; 
/* 276 */     boolean bool = (paramAclEntry.type() == AclEntryType.ALLOW) ? true : false;
/*     */ 
/*     */     
/* 279 */     Set<AclEntryPermission> set = paramAclEntry.permissions();
/* 280 */     int i = 0;
/* 281 */     if (set.contains(AclEntryPermission.READ_DATA))
/* 282 */       i |= 0x1; 
/* 283 */     if (set.contains(AclEntryPermission.WRITE_DATA))
/* 284 */       i |= 0x2; 
/* 285 */     if (set.contains(AclEntryPermission.APPEND_DATA))
/* 286 */       i |= 0x4; 
/* 287 */     if (set.contains(AclEntryPermission.READ_NAMED_ATTRS))
/* 288 */       i |= 0x8; 
/* 289 */     if (set.contains(AclEntryPermission.WRITE_NAMED_ATTRS))
/* 290 */       i |= 0x10; 
/* 291 */     if (set.contains(AclEntryPermission.EXECUTE))
/* 292 */       i |= 0x20; 
/* 293 */     if (set.contains(AclEntryPermission.DELETE_CHILD))
/* 294 */       i |= 0x40; 
/* 295 */     if (set.contains(AclEntryPermission.READ_ATTRIBUTES))
/* 296 */       i |= 0x80; 
/* 297 */     if (set.contains(AclEntryPermission.WRITE_ATTRIBUTES))
/* 298 */       i |= 0x100; 
/* 299 */     if (set.contains(AclEntryPermission.DELETE))
/* 300 */       i |= 0x10000; 
/* 301 */     if (set.contains(AclEntryPermission.READ_ACL))
/* 302 */       i |= 0x20000; 
/* 303 */     if (set.contains(AclEntryPermission.WRITE_ACL))
/* 304 */       i |= 0x40000; 
/* 305 */     if (set.contains(AclEntryPermission.WRITE_OWNER))
/* 306 */       i |= 0x80000; 
/* 307 */     if (set.contains(AclEntryPermission.SYNCHRONIZE)) {
/* 308 */       i |= 0x100000;
/*     */     }
/*     */     
/* 311 */     Set<AclEntryFlag> set1 = paramAclEntry.flags();
/* 312 */     byte b = 0;
/* 313 */     if (set1.contains(AclEntryFlag.FILE_INHERIT))
/* 314 */       b = (byte)(b | 0x1); 
/* 315 */     if (set1.contains(AclEntryFlag.DIRECTORY_INHERIT))
/* 316 */       b = (byte)(b | 0x2); 
/* 317 */     if (set1.contains(AclEntryFlag.NO_PROPAGATE_INHERIT))
/* 318 */       b = (byte)(b | 0x4); 
/* 319 */     if (set1.contains(AclEntryFlag.INHERIT_ONLY)) {
/* 320 */       b = (byte)(b | 0x8);
/*     */     }
/* 322 */     if (bool) {
/* 323 */       WindowsNativeDispatcher.AddAccessAllowedAceEx(paramLong2, b, i, paramLong1);
/*     */     } else {
/* 325 */       WindowsNativeDispatcher.AddAccessDeniedAceEx(paramLong2, b, i, paramLong1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static WindowsSecurityDescriptor create(List<AclEntry> paramList) throws IOException {
/* 335 */     return new WindowsSecurityDescriptor(paramList);
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
/*     */   static WindowsSecurityDescriptor fromAttribute(FileAttribute<?>... paramVarArgs) throws IOException {
/* 347 */     WindowsSecurityDescriptor windowsSecurityDescriptor = NULL_DESCRIPTOR;
/* 348 */     for (FileAttribute<?> fileAttribute : paramVarArgs) {
/*     */       
/* 350 */       if (windowsSecurityDescriptor != NULL_DESCRIPTOR)
/* 351 */         windowsSecurityDescriptor.release(); 
/* 352 */       if (fileAttribute == null)
/* 353 */         throw new NullPointerException(); 
/* 354 */       if (fileAttribute.name().equals("acl:acl")) {
/* 355 */         List<AclEntry> list = (List)fileAttribute.value();
/* 356 */         windowsSecurityDescriptor = new WindowsSecurityDescriptor(list);
/*     */       } else {
/* 358 */         throw new UnsupportedOperationException("'" + fileAttribute.name() + "' not supported as initial attribute");
/*     */       } 
/*     */     } 
/*     */     
/* 362 */     return windowsSecurityDescriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static List<AclEntry> getAcl(long paramLong) throws IOException {
/* 370 */     long l = WindowsNativeDispatcher.GetSecurityDescriptorDacl(paramLong);
/*     */ 
/*     */     
/* 373 */     int i = 0;
/* 374 */     if (l == 0L) {
/*     */       
/* 376 */       i = 0;
/*     */     } else {
/* 378 */       WindowsNativeDispatcher.AclInformation aclInformation = WindowsNativeDispatcher.GetAclInformation(l);
/* 379 */       i = aclInformation.aceCount();
/*     */     } 
/* 381 */     ArrayList<AclEntry> arrayList = new ArrayList(i);
/*     */ 
/*     */     
/* 384 */     for (byte b = 0; b < i; b++) {
/* 385 */       long l1 = WindowsNativeDispatcher.GetAce(l, b);
/* 386 */       AclEntry aclEntry = decode(l1);
/* 387 */       if (aclEntry != null)
/* 388 */         arrayList.add(aclEntry); 
/*     */     } 
/* 390 */     return arrayList;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsSecurityDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */