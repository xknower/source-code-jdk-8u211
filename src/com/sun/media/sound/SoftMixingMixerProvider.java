/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import javax.sound.sampled.Mixer;
/*    */ import javax.sound.sampled.spi.MixerProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SoftMixingMixerProvider
/*    */   extends MixerProvider
/*    */ {
/* 38 */   static SoftMixingMixer globalmixer = null;
/*    */   
/* 40 */   static Thread lockthread = null;
/*    */   
/* 42 */   static final Object mutex = new Object();
/*    */   
/*    */   public Mixer getMixer(Mixer.Info paramInfo) {
/* 45 */     if (paramInfo != null && paramInfo != SoftMixingMixer.info) {
/* 46 */       throw new IllegalArgumentException("Mixer " + paramInfo.toString() + " not supported by this provider.");
/*    */     }
/*    */     
/* 49 */     synchronized (mutex) {
/* 50 */       if (lockthread != null && 
/* 51 */         Thread.currentThread() == lockthread) {
/* 52 */         throw new IllegalArgumentException("Mixer " + paramInfo
/* 53 */             .toString() + " not supported by this provider.");
/*    */       }
/* 55 */       if (globalmixer == null)
/* 56 */         globalmixer = new SoftMixingMixer(); 
/* 57 */       return globalmixer;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Mixer.Info[] getMixerInfo() {
/* 63 */     return new Mixer.Info[] { SoftMixingMixer.info };
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\SoftMixingMixerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */