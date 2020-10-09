/*     */ package sun.rmi.transport.proxy;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HttpSendOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   HttpSendSocket owner;
/*     */   
/*     */   public HttpSendOutputStream(OutputStream paramOutputStream, HttpSendSocket paramHttpSendSocket) throws IOException {
/*  48 */     super(paramOutputStream);
/*     */     
/*  50 */     this.owner = paramHttpSendSocket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deactivate() {
/*  60 */     this.out = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/*  68 */     if (this.out == null)
/*  69 */       this.out = this.owner.writeNotify(); 
/*  70 */     this.out.write(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  81 */     if (paramInt2 == 0)
/*     */       return; 
/*  83 */     if (this.out == null)
/*  84 */       this.out = this.owner.writeNotify(); 
/*  85 */     this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/*  93 */     if (this.out != null) {
/*  94 */       this.out.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 102 */     flush();
/* 103 */     this.owner.close();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\transport\proxy\HttpSendOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */