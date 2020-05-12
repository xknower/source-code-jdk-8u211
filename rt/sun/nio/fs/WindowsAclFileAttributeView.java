/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.ProviderMismatchException;
/*     */ import java.nio.file.attribute.AclEntry;
/*     */ import java.nio.file.attribute.UserPrincipal;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsAclFileAttributeView
/*     */   extends AbstractAclFileAttributeView
/*     */ {
/*     */   private static final short SIZEOF_SECURITY_DESCRIPTOR = 20;
/*     */   private final WindowsPath file;
/*     */   private final boolean followLinks;
/*     */   
/*     */   WindowsAclFileAttributeView(WindowsPath paramWindowsPath, boolean paramBoolean) {
/*  60 */     this.file = paramWindowsPath;
/*  61 */     this.followLinks = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAccess(WindowsPath paramWindowsPath, boolean paramBoolean1, boolean paramBoolean2) {
/*  69 */     SecurityManager securityManager = System.getSecurityManager();
/*  70 */     if (securityManager != null) {
/*  71 */       if (paramBoolean1)
/*  72 */         securityManager.checkRead(paramWindowsPath.getPathForPermissionCheck()); 
/*  73 */       if (paramBoolean2)
/*  74 */         securityManager.checkWrite(paramWindowsPath.getPathForPermissionCheck()); 
/*  75 */       securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static NativeBuffer getFileSecurity(String paramString, int paramInt) throws IOException {
/*  84 */     int i = 0;
/*     */     try {
/*  86 */       i = WindowsNativeDispatcher.GetFileSecurity(paramString, paramInt, 0L, 0);
/*  87 */     } catch (WindowsException windowsException) {
/*  88 */       windowsException.rethrowAsIOException(paramString);
/*     */     } 
/*  90 */     assert i > 0;
/*     */ 
/*     */     
/*  93 */     NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(i);
/*     */     try {
/*     */       while (true) {
/*  96 */         int j = WindowsNativeDispatcher.GetFileSecurity(paramString, paramInt, nativeBuffer.address(), i);
/*  97 */         if (j <= i) {
/*  98 */           return nativeBuffer;
/*     */         }
/*     */         
/* 101 */         nativeBuffer.release();
/* 102 */         nativeBuffer = NativeBuffers.getNativeBuffer(j);
/* 103 */         i = j;
/*     */       } 
/* 105 */     } catch (WindowsException windowsException) {
/* 106 */       nativeBuffer.release();
/* 107 */       windowsException.rethrowAsIOException(paramString);
/* 108 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserPrincipal getOwner() throws IOException {
/* 116 */     checkAccess(this.file, true, false);
/*     */ 
/*     */ 
/*     */     
/* 120 */     String str = WindowsLinkSupport.getFinalPath(this.file, this.followLinks);
/* 121 */     NativeBuffer nativeBuffer = getFileSecurity(str, 1);
/*     */     
/*     */     try {
/* 124 */       long l = WindowsNativeDispatcher.GetSecurityDescriptorOwner(nativeBuffer.address());
/* 125 */       if (l == 0L)
/* 126 */         throw new IOException("no owner"); 
/* 127 */       return WindowsUserPrincipals.fromSid(l);
/* 128 */     } catch (WindowsException windowsException) {
/* 129 */       windowsException.rethrowAsIOException(this.file);
/* 130 */       return null;
/*     */     } finally {
/* 132 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AclEntry> getAcl() throws IOException {
/* 140 */     checkAccess(this.file, true, false);
/*     */ 
/*     */ 
/*     */     
/* 144 */     String str = WindowsLinkSupport.getFinalPath(this.file, this.followLinks);
/*     */ 
/*     */ 
/*     */     
/* 148 */     NativeBuffer nativeBuffer = getFileSecurity(str, 4);
/*     */     try {
/* 150 */       return WindowsSecurityDescriptor.getAcl(nativeBuffer.address());
/*     */     } finally {
/* 152 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwner(UserPrincipal paramUserPrincipal) throws IOException {
/* 160 */     if (paramUserPrincipal == null)
/* 161 */       throw new NullPointerException("'owner' is null"); 
/* 162 */     if (!(paramUserPrincipal instanceof WindowsUserPrincipals.User))
/* 163 */       throw new ProviderMismatchException(); 
/* 164 */     WindowsUserPrincipals.User user = (WindowsUserPrincipals.User)paramUserPrincipal;
/*     */ 
/*     */     
/* 167 */     checkAccess(this.file, false, true);
/*     */ 
/*     */ 
/*     */     
/* 171 */     String str = WindowsLinkSupport.getFinalPath(this.file, this.followLinks);
/*     */ 
/*     */ 
/*     */     
/* 175 */     long l = 0L;
/*     */     try {
/* 177 */       l = WindowsNativeDispatcher.ConvertStringSidToSid(user.sidString());
/* 178 */     } catch (WindowsException windowsException) {
/* 179 */       throw new IOException("Failed to get SID for " + user.getName() + ": " + windowsException
/* 180 */           .errorString());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 186 */       NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(20);
/*     */       try {
/* 188 */         WindowsNativeDispatcher.InitializeSecurityDescriptor(nativeBuffer.address());
/* 189 */         WindowsNativeDispatcher.SetSecurityDescriptorOwner(nativeBuffer.address(), l);
/*     */ 
/*     */         
/* 192 */         WindowsSecurity.Privilege privilege = WindowsSecurity.enablePrivilege("SeRestorePrivilege");
/*     */         try {
/* 194 */           WindowsNativeDispatcher.SetFileSecurity(str, 1, nativeBuffer
/*     */               
/* 196 */               .address());
/*     */         } finally {
/* 198 */           privilege.drop();
/*     */         } 
/* 200 */       } catch (WindowsException windowsException) {
/* 201 */         windowsException.rethrowAsIOException(this.file);
/*     */       } finally {
/* 203 */         nativeBuffer.release();
/*     */       } 
/*     */     } finally {
/* 206 */       WindowsNativeDispatcher.LocalFree(l);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAcl(List<AclEntry> paramList) throws IOException {
/* 212 */     checkAccess(this.file, false, true);
/*     */ 
/*     */ 
/*     */     
/* 216 */     String str = WindowsLinkSupport.getFinalPath(this.file, this.followLinks);
/* 217 */     WindowsSecurityDescriptor windowsSecurityDescriptor = WindowsSecurityDescriptor.create(paramList);
/*     */     try {
/* 219 */       WindowsNativeDispatcher.SetFileSecurity(str, 4, windowsSecurityDescriptor.address());
/* 220 */     } catch (WindowsException windowsException) {
/* 221 */       windowsException.rethrowAsIOException(this.file);
/*     */     } finally {
/* 223 */       windowsSecurityDescriptor.release();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsAclFileAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */