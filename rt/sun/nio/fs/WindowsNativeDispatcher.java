/*      */ package sun.nio.fs;
/*      */ 
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class WindowsNativeDispatcher
/*      */ {
/*      */   static native long CreateEvent(boolean paramBoolean1, boolean paramBoolean2) throws WindowsException;
/*      */   
/*      */   static long CreateFile(String paramString, int paramInt1, int paramInt2, long paramLong, int paramInt3, int paramInt4) throws WindowsException {
/*   69 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*   71 */       return CreateFile0(nativeBuffer.address(), paramInt1, paramInt2, paramLong, paramInt3, paramInt4);
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/*   78 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long CreateFile(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws WindowsException {
/*   88 */     return CreateFile(paramString, paramInt1, paramInt2, 0L, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native long CreateFile0(long paramLong1, int paramInt1, int paramInt2, long paramLong2, int paramInt3, int paramInt4) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void CloseHandle(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void DeleteFile(String paramString) throws WindowsException {
/*  112 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  114 */       DeleteFile0(nativeBuffer.address());
/*      */     } finally {
/*  116 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void DeleteFile0(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void CreateDirectory(String paramString, long paramLong) throws WindowsException {
/*  129 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  131 */       CreateDirectory0(nativeBuffer.address(), paramLong);
/*      */     } finally {
/*  133 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void CreateDirectory0(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */ 
/*      */   
/*      */   static void RemoveDirectory(String paramString) throws WindowsException {
/*  145 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  147 */       RemoveDirectory0(nativeBuffer.address());
/*      */     } finally {
/*  149 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void RemoveDirectory0(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void DeviceIoControlSetSparse(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void DeviceIoControlGetReparsePoint(long paramLong1, long paramLong2, int paramInt) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void FindFirstFile0(long paramLong, FirstFile paramFirstFile) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static FirstFile FindFirstFile(String paramString) throws WindowsException {
/*  182 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  184 */       FirstFile firstFile = new FirstFile();
/*  185 */       FindFirstFile0(nativeBuffer.address(), firstFile);
/*  186 */       return firstFile;
/*      */     } finally {
/*  188 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */   
/*      */   static class FirstFile {
/*      */     private long handle;
/*      */     private String name;
/*      */     
/*      */     public long handle() {
/*  197 */       return this.handle; } private int attributes; private FirstFile() {} public String name() {
/*  198 */       return this.name; } public int attributes() {
/*  199 */       return this.attributes;
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
/*      */   static long FindFirstFile(String paramString, long paramLong) throws WindowsException {
/*  211 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  213 */       return FindFirstFile1(nativeBuffer.address(), paramLong);
/*      */     } finally {
/*  215 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static native long FindFirstFile1(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */ 
/*      */   
/*      */   static native String FindNextFile(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */   
/*      */   private static native void FindFirstStream0(long paramLong, FirstStream paramFirstStream) throws WindowsException;
/*      */ 
/*      */   
/*      */   static native String FindNextStream(long paramLong) throws WindowsException;
/*      */ 
/*      */   
/*      */   static native void FindClose(long paramLong) throws WindowsException;
/*      */ 
/*      */   
/*      */   static native void GetFileInformationByHandle(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */   
/*      */   static FirstStream FindFirstStream(String paramString) throws WindowsException {
/*  241 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  243 */       FirstStream firstStream = new FirstStream();
/*  244 */       FindFirstStream0(nativeBuffer.address(), firstStream);
/*  245 */       if (firstStream.handle() == -1L)
/*  246 */         return null; 
/*  247 */       return firstStream;
/*      */     } finally {
/*  249 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */   static class FirstStream { private long handle; private String name;
/*      */     
/*      */     private FirstStream() {}
/*      */     
/*      */     public long handle() {
/*  257 */       return this.handle; } public String name() {
/*  258 */       return this.name;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void CopyFileEx(String paramString1, String paramString2, int paramInt, long paramLong) throws WindowsException {
/*  301 */     NativeBuffer nativeBuffer1 = asNativeBuffer(paramString1);
/*  302 */     NativeBuffer nativeBuffer2 = asNativeBuffer(paramString2);
/*      */     try {
/*  304 */       CopyFileEx0(nativeBuffer1.address(), nativeBuffer2.address(), paramInt, paramLong);
/*      */     } finally {
/*      */       
/*  307 */       nativeBuffer2.release();
/*  308 */       nativeBuffer1.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void CopyFileEx0(long paramLong1, long paramLong2, int paramInt, long paramLong3) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void MoveFileEx(String paramString1, String paramString2, int paramInt) throws WindowsException {
/*  324 */     NativeBuffer nativeBuffer1 = asNativeBuffer(paramString1);
/*  325 */     NativeBuffer nativeBuffer2 = asNativeBuffer(paramString2);
/*      */     try {
/*  327 */       MoveFileEx0(nativeBuffer1.address(), nativeBuffer2.address(), paramInt);
/*      */     } finally {
/*  329 */       nativeBuffer2.release();
/*  330 */       nativeBuffer1.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void MoveFileEx0(long paramLong1, long paramLong2, int paramInt) throws WindowsException;
/*      */ 
/*      */ 
/*      */   
/*      */   static int GetFileAttributes(String paramString) throws WindowsException {
/*  342 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  344 */       return GetFileAttributes0(nativeBuffer.address());
/*      */     } finally {
/*  346 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int GetFileAttributes0(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void SetFileAttributes(String paramString, int paramInt) throws WindowsException {
/*  360 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  362 */       SetFileAttributes0(nativeBuffer.address(), paramInt);
/*      */     } finally {
/*  364 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void SetFileAttributes0(long paramLong, int paramInt) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void GetFileAttributesEx(String paramString, long paramLong) throws WindowsException {
/*  378 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  380 */       GetFileAttributesEx0(nativeBuffer.address(), paramLong);
/*      */     } finally {
/*  382 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void GetFileAttributesEx0(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void SetFileTime(long paramLong1, long paramLong2, long paramLong3, long paramLong4) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void SetEndOfFile(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native int GetLogicalDrives() throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void GetVolumeInformation0(long paramLong, VolumeInformation paramVolumeInformation) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static VolumeInformation GetVolumeInformation(String paramString) throws WindowsException {
/*  428 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  430 */       VolumeInformation volumeInformation = new VolumeInformation();
/*  431 */       GetVolumeInformation0(nativeBuffer.address(), volumeInformation);
/*  432 */       return volumeInformation;
/*      */     } finally {
/*  434 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */   static class VolumeInformation { private String fileSystemName; private String volumeName;
/*      */     private int volumeSerialNumber;
/*      */     private int flags;
/*      */     
/*      */     private VolumeInformation() {}
/*      */     
/*      */     public String fileSystemName() {
/*  444 */       return this.fileSystemName; }
/*  445 */     public String volumeName() { return this.volumeName; }
/*  446 */     public int volumeSerialNumber() { return this.volumeSerialNumber; } public int flags() {
/*  447 */       return this.flags;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int GetDriveType(String paramString) throws WindowsException {
/*  459 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  461 */       return GetDriveType0(nativeBuffer.address());
/*      */     } finally {
/*  463 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int GetDriveType0(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void GetDiskFreeSpaceEx0(long paramLong, DiskFreeSpace paramDiskFreeSpace) throws WindowsException;
/*      */ 
/*      */ 
/*      */   
/*      */   static DiskFreeSpace GetDiskFreeSpaceEx(String paramString) throws WindowsException {
/*  479 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  481 */       DiskFreeSpace diskFreeSpace = new DiskFreeSpace();
/*  482 */       GetDiskFreeSpaceEx0(nativeBuffer.address(), diskFreeSpace);
/*  483 */       return diskFreeSpace;
/*      */     } finally {
/*  485 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */   static class DiskFreeSpace { private long freeBytesAvailable; private long totalNumberOfBytes;
/*      */     private long totalNumberOfFreeBytes;
/*      */     
/*      */     private DiskFreeSpace() {}
/*      */     
/*      */     public long freeBytesAvailable() {
/*  494 */       return this.freeBytesAvailable; }
/*  495 */     public long totalNumberOfBytes() { return this.totalNumberOfBytes; } public long totalNumberOfFreeBytes() {
/*  496 */       return this.totalNumberOfFreeBytes;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String GetVolumePathName(String paramString) throws WindowsException {
/*  513 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  515 */       return GetVolumePathName0(nativeBuffer.address());
/*      */     } finally {
/*  517 */       nativeBuffer.release();
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
/*      */   private static native String GetVolumePathName0(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void InitializeSecurityDescriptor(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void InitializeAcl(long paramLong, int paramInt) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int GetFileSecurity(String paramString, int paramInt1, long paramLong, int paramInt2) throws WindowsException {
/*  557 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  559 */       return GetFileSecurity0(nativeBuffer.address(), paramInt1, paramLong, paramInt2);
/*      */     } finally {
/*      */       
/*  562 */       nativeBuffer.release();
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
/*      */   private static native int GetFileSecurity0(long paramLong1, int paramInt1, long paramLong2, int paramInt2) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void SetFileSecurity(String paramString, int paramInt, long paramLong) throws WindowsException {
/*  582 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  584 */       SetFileSecurity0(nativeBuffer.address(), paramInt, paramLong);
/*      */     } finally {
/*      */       
/*  587 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void SetFileSecurity0(long paramLong1, int paramInt, long paramLong2) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native long GetSecurityDescriptorOwner(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void SetSecurityDescriptorOwner(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native long GetSecurityDescriptorDacl(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void SetSecurityDescriptorDacl(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void GetAclInformation0(long paramLong, AclInformation paramAclInformation);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native long GetAce(long paramLong, int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static AclInformation GetAclInformation(long paramLong) {
/*  647 */     AclInformation aclInformation = new AclInformation();
/*  648 */     GetAclInformation0(paramLong, aclInformation);
/*  649 */     return aclInformation;
/*      */   }
/*      */   static native void AddAccessAllowedAceEx(long paramLong1, int paramInt1, int paramInt2, long paramLong2) throws WindowsException;
/*      */   static native void AddAccessDeniedAceEx(long paramLong1, int paramInt1, int paramInt2, long paramLong2) throws WindowsException;
/*      */   
/*      */   static class AclInformation { public int aceCount() {
/*  655 */       return this.aceCount;
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
/*      */     private int aceCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AclInformation() {} }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void LookupAccountSid0(long paramLong, Account paramAccount) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Account LookupAccountSid(long paramLong) throws WindowsException {
/*  705 */     Account account = new Account();
/*  706 */     LookupAccountSid0(paramLong, account);
/*  707 */     return account;
/*      */   }
/*      */   static class Account { private String domain; private String name;
/*      */     private int use;
/*      */     
/*      */     private Account() {}
/*      */     
/*      */     public String domain() {
/*  715 */       return this.domain; }
/*  716 */     public String name() { return this.name; } public int use() {
/*  717 */       return this.use;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int LookupAccountName(String paramString, long paramLong, int paramInt) throws WindowsException {
/*  739 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  741 */       return LookupAccountName0(nativeBuffer.address(), paramLong, paramInt);
/*      */     } finally {
/*  743 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native int LookupAccountName0(long paramLong1, long paramLong2, int paramInt) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native int GetLengthSid(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native String ConvertSidToStringSid(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long ConvertStringSidToSid(String paramString) throws WindowsException {
/*  778 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  780 */       return ConvertStringSidToSid0(nativeBuffer.address());
/*      */     } finally {
/*  782 */       nativeBuffer.release();
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
/*      */   private static native long ConvertStringSidToSid0(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native long GetCurrentProcess();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native long GetCurrentThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native long OpenProcessToken(long paramLong, int paramInt) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native long OpenThreadToken(long paramLong, int paramInt, boolean paramBoolean) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native long DuplicateTokenEx(long paramLong, int paramInt) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void SetThreadToken(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native int GetTokenInformation(long paramLong1, int paramInt1, long paramLong2, int paramInt2) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void AdjustTokenPrivileges(long paramLong1, long paramLong2, int paramInt) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native boolean AccessCheck(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long LookupPrivilegeValue(String paramString) throws WindowsException {
/*  878 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  880 */       return LookupPrivilegeValue0(nativeBuffer.address());
/*      */     } finally {
/*  882 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native long LookupPrivilegeValue0(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void CreateSymbolicLink(String paramString1, String paramString2, int paramInt) throws WindowsException {
/*  898 */     NativeBuffer nativeBuffer1 = asNativeBuffer(paramString1);
/*  899 */     NativeBuffer nativeBuffer2 = asNativeBuffer(paramString2);
/*      */     try {
/*  901 */       CreateSymbolicLink0(nativeBuffer1.address(), nativeBuffer2.address(), paramInt);
/*      */     } finally {
/*      */       
/*  904 */       nativeBuffer2.release();
/*  905 */       nativeBuffer1.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void CreateSymbolicLink0(long paramLong1, long paramLong2, int paramInt) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void CreateHardLink(String paramString1, String paramString2) throws WindowsException {
/*  921 */     NativeBuffer nativeBuffer1 = asNativeBuffer(paramString1);
/*  922 */     NativeBuffer nativeBuffer2 = asNativeBuffer(paramString2);
/*      */     try {
/*  924 */       CreateHardLink0(nativeBuffer1.address(), nativeBuffer2.address());
/*      */     } finally {
/*  926 */       nativeBuffer2.release();
/*  927 */       nativeBuffer1.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void CreateHardLink0(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String GetFullPathName(String paramString) throws WindowsException {
/*  942 */     NativeBuffer nativeBuffer = asNativeBuffer(paramString);
/*      */     try {
/*  944 */       return GetFullPathName0(nativeBuffer.address());
/*      */     } finally {
/*  946 */       nativeBuffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native String GetFullPathName0(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native String GetFinalPathNameByHandle(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native String FormatMessage(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void LocalFree(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native long CreateIoCompletionPort(long paramLong1, long paramLong2, long paramLong3) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void GetQueuedCompletionStatus0(long paramLong, CompletionStatus paramCompletionStatus) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void PostQueuedCompletionStatus(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void ReadDirectoryChangesW(long paramLong1, long paramLong2, int paramInt1, boolean paramBoolean, int paramInt2, long paramLong3, long paramLong4) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void CancelIo(long paramLong) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native int GetOverlappedResult(long paramLong1, long paramLong2) throws WindowsException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static CompletionStatus GetQueuedCompletionStatus(long paramLong) throws WindowsException {
/* 1006 */     CompletionStatus completionStatus = new CompletionStatus();
/* 1007 */     GetQueuedCompletionStatus0(paramLong, completionStatus);
/* 1008 */     return completionStatus;
/*      */   }
/*      */   static class CompletionStatus { private int error; private int bytesTransferred;
/*      */     private long completionKey;
/*      */     
/*      */     private CompletionStatus() {}
/*      */     
/*      */     int error() {
/* 1016 */       return this.error; }
/* 1017 */     int bytesTransferred() { return this.bytesTransferred; } long completionKey() {
/* 1018 */       return this.completionKey;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BackupResult BackupRead(long paramLong1, long paramLong2, int paramInt, boolean paramBoolean, long paramLong3) throws WindowsException {
/* 1092 */     BackupResult backupResult = new BackupResult();
/* 1093 */     BackupRead0(paramLong1, paramLong2, paramInt, paramBoolean, paramLong3, backupResult);
/* 1094 */     return backupResult;
/*      */   }
/*      */   static class BackupResult { private int bytesTransferred;
/*      */     private long context;
/*      */     
/*      */     private BackupResult() {}
/*      */     
/* 1101 */     int bytesTransferred() { return this.bytesTransferred; } long context() {
/* 1102 */       return this.context;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1124 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*      */   private static native void BackupRead0(long paramLong1, long paramLong2, int paramInt, boolean paramBoolean, long paramLong3, BackupResult paramBackupResult) throws WindowsException;
/*      */   static NativeBuffer asNativeBuffer(String paramString) {
/* 1127 */     int i = paramString.length() << 1;
/* 1128 */     int j = i + 2;
/*      */ 
/*      */     
/* 1131 */     NativeBuffer nativeBuffer = NativeBuffers.getNativeBufferFromCache(j);
/* 1132 */     if (nativeBuffer == null) {
/* 1133 */       nativeBuffer = NativeBuffers.allocNativeBuffer(j);
/*      */     
/*      */     }
/* 1136 */     else if (nativeBuffer.owner() == paramString) {
/* 1137 */       return nativeBuffer;
/*      */     } 
/*      */ 
/*      */     
/* 1141 */     char[] arrayOfChar = paramString.toCharArray();
/* 1142 */     unsafe.copyMemory(arrayOfChar, Unsafe.ARRAY_CHAR_BASE_OFFSET, null, nativeBuffer
/* 1143 */         .address(), i);
/* 1144 */     unsafe.putChar(nativeBuffer.address() + i, false);
/* 1145 */     nativeBuffer.setOwner(paramString);
/* 1146 */     return nativeBuffer;
/*      */   }
/*      */   
/*      */   static native void BackupSeek(long paramLong1, long paramLong2, long paramLong3) throws WindowsException;
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   static {
/* 1154 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */         {
/*      */           public Void run() {
/* 1157 */             System.loadLibrary("net");
/* 1158 */             System.loadLibrary("nio");
/* 1159 */             return null; }
/*      */         });
/* 1161 */     initIDs();
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsNativeDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */