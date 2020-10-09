/*    */ package com.sun.media.sound;
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
/*    */ 
/*    */ public abstract class ModelAbstractChannelMixer
/*    */   implements ModelChannelMixer
/*    */ {
/*    */   public abstract boolean process(float[][] paramArrayOffloat, int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract void stop();
/*    */   
/*    */   public void allNotesOff() {}
/*    */   
/*    */   public void allSoundOff() {}
/*    */   
/*    */   public void controlChange(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public int getChannelPressure() {
/* 49 */     return 0;
/*    */   }
/*    */   
/*    */   public int getController(int paramInt) {
/* 53 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean getMono() {
/* 57 */     return false;
/*    */   }
/*    */   
/*    */   public boolean getMute() {
/* 61 */     return false;
/*    */   }
/*    */   
/*    */   public boolean getOmni() {
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   public int getPitchBend() {
/* 69 */     return 0;
/*    */   }
/*    */   
/*    */   public int getPolyPressure(int paramInt) {
/* 73 */     return 0;
/*    */   }
/*    */   
/*    */   public int getProgram() {
/* 77 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean getSolo() {
/* 81 */     return false;
/*    */   }
/*    */   
/*    */   public boolean localControl(boolean paramBoolean) {
/* 85 */     return false;
/*    */   }
/*    */   
/*    */   public void noteOff(int paramInt) {}
/*    */   
/*    */   public void noteOff(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void noteOn(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void programChange(int paramInt) {}
/*    */   
/*    */   public void programChange(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void resetAllControllers() {}
/*    */   
/*    */   public void setChannelPressure(int paramInt) {}
/*    */   
/*    */   public void setMono(boolean paramBoolean) {}
/*    */   
/*    */   public void setMute(boolean paramBoolean) {}
/*    */   
/*    */   public void setOmni(boolean paramBoolean) {}
/*    */   
/*    */   public void setPitchBend(int paramInt) {}
/*    */   
/*    */   public void setPolyPressure(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void setSolo(boolean paramBoolean) {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\ModelAbstractChannelMixer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */