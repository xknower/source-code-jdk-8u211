/*     */ package javax.net.ssl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SSLEngineResult
/*     */ {
/*     */   private final Status status;
/*     */   private final HandshakeStatus handshakeStatus;
/*     */   private final int bytesConsumed;
/*     */   private final int bytesProduced;
/*     */   
/*     */   public enum Status
/*     */   {
/*  73 */     BUFFER_UNDERFLOW,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     BUFFER_OVERFLOW,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     OK,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     CLOSED;
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
/*     */   public enum HandshakeStatus
/*     */   {
/* 113 */     NOT_HANDSHAKING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     FINISHED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     NEED_TASK,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     NEED_WRAP,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     NEED_UNWRAP;
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
/*     */   public SSLEngineResult(Status paramStatus, HandshakeStatus paramHandshakeStatus, int paramInt1, int paramInt2) {
/* 182 */     if (paramStatus == null || paramHandshakeStatus == null || paramInt1 < 0 || paramInt2 < 0)
/*     */     {
/* 184 */       throw new IllegalArgumentException("Invalid Parameter(s)");
/*     */     }
/*     */     
/* 187 */     this.status = paramStatus;
/* 188 */     this.handshakeStatus = paramHandshakeStatus;
/* 189 */     this.bytesConsumed = paramInt1;
/* 190 */     this.bytesProduced = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Status getStatus() {
/* 199 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final HandshakeStatus getHandshakeStatus() {
/* 209 */     return this.handshakeStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int bytesConsumed() {
/* 218 */     return this.bytesConsumed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int bytesProduced() {
/* 227 */     return this.bytesProduced;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 235 */     return "Status = " + this.status + " HandshakeStatus = " + this.handshakeStatus + "\nbytesConsumed = " + this.bytesConsumed + " bytesProduced = " + this.bytesProduced;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\net\ssl\SSLEngineResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */