/*     */ package java.io;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FileDescriptor
/*     */ {
/*  58 */   private int fd = -1;
/*  59 */   private long handle = -1L;
/*     */   private Closeable parent;
/*     */   
/*     */   static {
/*  63 */     initIDs();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     SharedSecrets.setJavaIOFileDescriptorAccess(new JavaIOFileDescriptorAccess()
/*     */         {
/*     */           public void set(FileDescriptor param1FileDescriptor, int param1Int) {
/*  71 */             param1FileDescriptor.fd = param1Int;
/*     */           }
/*     */           
/*     */           public int get(FileDescriptor param1FileDescriptor) {
/*  75 */             return param1FileDescriptor.fd;
/*     */           }
/*     */           
/*     */           public void setHandle(FileDescriptor param1FileDescriptor, long param1Long) {
/*  79 */             param1FileDescriptor.handle = param1Long;
/*     */           }
/*     */           
/*     */           public long getHandle(FileDescriptor param1FileDescriptor) {
/*  83 */             return param1FileDescriptor.handle;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Closeable> otherParents;
/*     */ 
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */   
/*  96 */   public static final FileDescriptor in = standardStream(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final FileDescriptor out = standardStream(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public static final FileDescriptor err = standardStream(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean valid() {
/* 123 */     return (this.handle != -1L || this.fd != -1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FileDescriptor standardStream(int paramInt) {
/* 162 */     FileDescriptor fileDescriptor = new FileDescriptor();
/* 163 */     fileDescriptor.handle = set(paramInt);
/* 164 */     return fileDescriptor;
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
/*     */   synchronized void attach(Closeable paramCloseable) {
/* 179 */     if (this.parent == null) {
/*     */       
/* 181 */       this.parent = paramCloseable;
/* 182 */     } else if (this.otherParents == null) {
/* 183 */       this.otherParents = new ArrayList<>();
/* 184 */       this.otherParents.add(this.parent);
/* 185 */       this.otherParents.add(paramCloseable);
/*     */     } else {
/* 187 */       this.otherParents.add(paramCloseable);
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
/*     */   synchronized void closeAll(Closeable paramCloseable) throws IOException {
/* 199 */     if (!this.closed) {
/* 200 */       this.closed = true;
/* 201 */       IOException iOException = null;
/* 202 */       try (Closeable null = paramCloseable) {
/* 203 */         if (this.otherParents != null) {
/* 204 */           for (Closeable closeable1 : this.otherParents) {
/*     */             try {
/* 206 */               closeable1.close();
/* 207 */             } catch (IOException iOException1) {
/* 208 */               if (iOException == null) {
/* 209 */                 iOException = iOException1; continue;
/*     */               } 
/* 211 */               iOException.addSuppressed(iOException1);
/*     */             }
/*     */           
/*     */           } 
/*     */         }
/* 216 */       } catch (IOException iOException1) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 221 */         if (iOException != null)
/* 222 */           iOException1.addSuppressed(iOException); 
/* 223 */         iOException = iOException1;
/*     */       } finally {
/* 225 */         if (iOException != null)
/* 226 */           throw iOException; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public native void sync() throws SyncFailedException;
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private static native long set(int paramInt);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\io\FileDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */