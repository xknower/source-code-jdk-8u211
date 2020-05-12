/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.sound.midi.SoundbankResource;
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
/*    */ public final class SF2Layer
/*    */   extends SoundbankResource
/*    */ {
/* 39 */   String name = "";
/* 40 */   SF2GlobalRegion globalregion = null;
/* 41 */   List<SF2LayerRegion> regions = new ArrayList<>();
/*    */   
/*    */   public SF2Layer(SF2Soundbank paramSF2Soundbank) {
/* 44 */     super(paramSF2Soundbank, null, null);
/*    */   }
/*    */   
/*    */   public SF2Layer() {
/* 48 */     super(null, null, null);
/*    */   }
/*    */   
/*    */   public Object getData() {
/* 52 */     return null;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 56 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String paramString) {
/* 60 */     this.name = paramString;
/*    */   }
/*    */   
/*    */   public List<SF2LayerRegion> getRegions() {
/* 64 */     return this.regions;
/*    */   }
/*    */   
/*    */   public SF2GlobalRegion getGlobalRegion() {
/* 68 */     return this.globalregion;
/*    */   }
/*    */   
/*    */   public void setGlobalZone(SF2GlobalRegion paramSF2GlobalRegion) {
/* 72 */     this.globalregion = paramSF2GlobalRegion;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 76 */     return "Layer: " + this.name;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\SF2Layer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */