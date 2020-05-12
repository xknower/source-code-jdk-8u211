/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public final class DLSSampleOptions
/*    */ {
/*    */   int unitynote;
/*    */   short finetune;
/*    */   int attenuation;
/*    */   long options;
/* 43 */   List<DLSSampleLoop> loops = new ArrayList<>();
/*    */   
/*    */   public int getAttenuation() {
/* 46 */     return this.attenuation;
/*    */   }
/*    */   
/*    */   public void setAttenuation(int paramInt) {
/* 50 */     this.attenuation = paramInt;
/*    */   }
/*    */   
/*    */   public short getFinetune() {
/* 54 */     return this.finetune;
/*    */   }
/*    */   
/*    */   public void setFinetune(short paramShort) {
/* 58 */     this.finetune = paramShort;
/*    */   }
/*    */   
/*    */   public List<DLSSampleLoop> getLoops() {
/* 62 */     return this.loops;
/*    */   }
/*    */   
/*    */   public long getOptions() {
/* 66 */     return this.options;
/*    */   }
/*    */   
/*    */   public void setOptions(long paramLong) {
/* 70 */     this.options = paramLong;
/*    */   }
/*    */   
/*    */   public int getUnitynote() {
/* 74 */     return this.unitynote;
/*    */   }
/*    */   
/*    */   public void setUnitynote(int paramInt) {
/* 78 */     this.unitynote = paramInt;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\DLSSampleOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */