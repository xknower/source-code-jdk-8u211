/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import javax.sound.sampled.AudioFileFormat;
/*    */ import javax.sound.sampled.AudioFormat;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class AiffFileFormat
/*    */   extends AudioFileFormat
/*    */ {
/*    */   static final int AIFF_MAGIC = 1179603533;
/*    */   static final int AIFC_MAGIC = 1095321155;
/*    */   static final int AIFF_MAGIC2 = 1095321158;
/*    */   static final int FVER_MAGIC = 1180058962;
/*    */   static final int FVER_TIMESTAMP = -1568648896;
/*    */   static final int COMM_MAGIC = 1129270605;
/*    */   static final int SSND_MAGIC = 1397968452;
/*    */   static final int AIFC_PCM = 1313820229;
/*    */   static final int AIFC_ACE2 = 1094927666;
/*    */   static final int AIFC_ACE8 = 1094927672;
/*    */   static final int AIFC_MAC3 = 1296122675;
/*    */   static final int AIFC_MAC6 = 1296122678;
/*    */   static final int AIFC_ULAW = 1970037111;
/*    */   static final int AIFC_IMA4 = 1768775988;
/*    */   static final int AIFF_HEADERSIZE = 54;
/* 65 */   private final int headerSize = 54;
/*    */ 
/*    */   
/* 68 */   private final int commChunkSize = 26;
/*    */ 
/*    */   
/* 71 */   private final int fverChunkSize = 0;
/*    */   
/*    */   AiffFileFormat(AudioFileFormat paramAudioFileFormat) {
/* 74 */     this(paramAudioFileFormat.getType(), paramAudioFileFormat.getByteLength(), paramAudioFileFormat.getFormat(), paramAudioFileFormat.getFrameLength());
/*    */   }
/*    */   
/*    */   AiffFileFormat(AudioFileFormat.Type paramType, int paramInt1, AudioFormat paramAudioFormat, int paramInt2) {
/* 78 */     super(paramType, paramInt1, paramAudioFormat, paramInt2);
/*    */   }
/*    */   
/*    */   int getHeaderSize() {
/* 82 */     return 54;
/*    */   }
/*    */   
/*    */   int getCommChunkSize() {
/* 86 */     return 26;
/*    */   }
/*    */   
/*    */   int getFverChunkSize() {
/* 90 */     return 0;
/*    */   }
/*    */   
/*    */   int getSsndChunkOffset() {
/* 94 */     return getHeaderSize() - 16;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\AiffFileFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */