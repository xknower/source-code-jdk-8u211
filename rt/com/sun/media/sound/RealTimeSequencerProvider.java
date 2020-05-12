/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import javax.sound.midi.MidiDevice;
/*    */ import javax.sound.midi.MidiUnavailableException;
/*    */ import javax.sound.midi.spi.MidiDeviceProvider;
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
/*    */ 
/*    */ 
/*    */ public final class RealTimeSequencerProvider
/*    */   extends MidiDeviceProvider
/*    */ {
/*    */   public MidiDevice.Info[] getDeviceInfo() {
/* 42 */     return new MidiDevice.Info[] { RealTimeSequencer.info };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MidiDevice getDevice(MidiDevice.Info paramInfo) {
/* 48 */     if (paramInfo != null && !paramInfo.equals(RealTimeSequencer.info)) {
/* 49 */       return null;
/*    */     }
/*    */     
/*    */     try {
/* 53 */       return new RealTimeSequencer();
/* 54 */     } catch (MidiUnavailableException midiUnavailableException) {
/* 55 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\RealTimeSequencerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */