/*     */ package java.nio.channels;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.spi.AbstractSelectableChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Pipe
/*     */ {
/*     */   public abstract SourceChannel source();
/*     */   
/*     */   public abstract SinkChannel sink();
/*     */   
/*     */   public static abstract class SourceChannel
/*     */     extends AbstractSelectableChannel
/*     */     implements ReadableByteChannel, ScatteringByteChannel
/*     */   {
/*     */     protected SourceChannel(SelectorProvider param1SelectorProvider) {
/*  70 */       super(param1SelectorProvider);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int validOps() {
/*  83 */       return 1;
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
/*     */   public static abstract class SinkChannel
/*     */     extends AbstractSelectableChannel
/*     */     implements WritableByteChannel, GatheringByteChannel
/*     */   {
/*     */     protected SinkChannel(SelectorProvider param1SelectorProvider) {
/* 104 */       super(param1SelectorProvider);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int validOps() {
/* 117 */       return 4;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Pipe open() throws IOException {
/* 155 */     return SelectorProvider.provider().openPipe();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\channels\Pipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */