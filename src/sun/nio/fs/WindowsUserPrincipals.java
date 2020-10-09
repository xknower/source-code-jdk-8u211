/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.GroupPrincipal;
/*     */ import java.nio.file.attribute.UserPrincipal;
/*     */ import java.nio.file.attribute.UserPrincipalNotFoundException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsUserPrincipals
/*     */ {
/*     */   static class User
/*     */     implements UserPrincipal
/*     */   {
/*     */     private final String sidString;
/*     */     private final int sidType;
/*     */     private final String accountName;
/*     */     
/*     */     User(String param1String1, int param1Int, String param1String2) {
/*  47 */       this.sidString = param1String1;
/*  48 */       this.sidType = param1Int;
/*  49 */       this.accountName = param1String2;
/*     */     }
/*     */ 
/*     */     
/*     */     String sidString() {
/*  54 */       return this.sidString;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/*  59 */       return this.accountName;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*  65 */       switch (this.sidType) { case 1:
/*  66 */           str = "User";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  76 */           return this.accountName + " (" + str + ")";case 2: str = "Group"; return this.accountName + " (" + str + ")";case 3: str = "Domain"; return this.accountName + " (" + str + ")";case 4: str = "Alias"; return this.accountName + " (" + str + ")";case 5: str = "Well-known group"; return this.accountName + " (" + str + ")";case 6: str = "Deleted"; return this.accountName + " (" + str + ")";case 7: str = "Invalid"; return this.accountName + " (" + str + ")";case 9: str = "Computer"; return this.accountName + " (" + str + ")"; }  String str = "Unknown"; return this.accountName + " (" + str + ")";
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/*  81 */       if (param1Object == this)
/*  82 */         return true; 
/*  83 */       if (!(param1Object instanceof User))
/*  84 */         return false; 
/*  85 */       User user = (User)param1Object;
/*  86 */       return this.sidString.equals(user.sidString);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  91 */       return this.sidString.hashCode();
/*     */     }
/*     */   }
/*     */   
/*     */   static class Group extends User implements GroupPrincipal {
/*     */     Group(String param1String1, int param1Int, String param1String2) {
/*  97 */       super(param1String1, param1Int, param1String2);
/*     */     }
/*     */   }
/*     */   
/*     */   static UserPrincipal fromSid(long paramLong) throws IOException {
/*     */     String str1, str2;
/*     */     try {
/* 104 */       str1 = WindowsNativeDispatcher.ConvertSidToStringSid(paramLong);
/* 105 */       if (str1 == null)
/*     */       {
/* 107 */         throw new AssertionError();
/*     */       }
/* 109 */     } catch (WindowsException windowsException) {
/* 110 */       throw new IOException("Unable to convert SID to String: " + windowsException
/* 111 */           .errorString());
/*     */     } 
/*     */ 
/*     */     
/* 115 */     WindowsNativeDispatcher.Account account = null;
/*     */     
/*     */     try {
/* 118 */       account = WindowsNativeDispatcher.LookupAccountSid(paramLong);
/* 119 */       str2 = account.domain() + "\\" + account.name();
/* 120 */     } catch (WindowsException windowsException) {
/* 121 */       str2 = str1;
/*     */     } 
/*     */     
/* 124 */     boolean bool = (account == null) ? true : account.use();
/* 125 */     if (bool == 2 || bool == 5 || bool == 4)
/*     */     {
/*     */ 
/*     */       
/* 129 */       return new Group(str1, bool, str2);
/*     */     }
/* 131 */     return new User(str1, bool, str2);
/*     */   }
/*     */ 
/*     */   
/*     */   static UserPrincipal lookup(String paramString) throws IOException {
/* 136 */     SecurityManager securityManager = System.getSecurityManager();
/* 137 */     if (securityManager != null) {
/* 138 */       securityManager.checkPermission(new RuntimePermission("lookupUserInformation"));
/*     */     }
/*     */ 
/*     */     
/* 142 */     int i = 0;
/*     */     try {
/* 144 */       i = WindowsNativeDispatcher.LookupAccountName(paramString, 0L, 0);
/* 145 */     } catch (WindowsException windowsException) {
/* 146 */       if (windowsException.lastError() == 1332)
/* 147 */         throw new UserPrincipalNotFoundException(paramString); 
/* 148 */       throw new IOException(paramString + ": " + windowsException.errorString());
/*     */     } 
/* 150 */     assert i > 0;
/*     */ 
/*     */     
/* 153 */     NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(i);
/*     */     try {
/* 155 */       int j = WindowsNativeDispatcher.LookupAccountName(paramString, nativeBuffer.address(), i);
/* 156 */       if (j != i)
/*     */       {
/* 158 */         throw new AssertionError("SID change during lookup");
/*     */       }
/*     */ 
/*     */       
/* 162 */       return fromSid(nativeBuffer.address());
/* 163 */     } catch (WindowsException windowsException) {
/* 164 */       throw new IOException(paramString + ": " + windowsException.errorString());
/*     */     } finally {
/* 166 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsUserPrincipals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */