/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import javax.sound.midi.Soundbank;
/*     */ import javax.sound.midi.SoundbankResource;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
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
/*     */ public final class DLSSample
/*     */   extends SoundbankResource
/*     */ {
/*  45 */   byte[] guid = null;
/*  46 */   DLSInfo info = new DLSInfo();
/*     */   DLSSampleOptions sampleoptions;
/*     */   ModelByteBuffer data;
/*     */   AudioFormat format;
/*     */   
/*     */   public DLSSample(Soundbank paramSoundbank) {
/*  52 */     super(paramSoundbank, null, AudioInputStream.class);
/*     */   }
/*     */   
/*     */   public DLSSample() {
/*  56 */     super(null, null, AudioInputStream.class);
/*     */   }
/*     */   
/*     */   public DLSInfo getInfo() {
/*  60 */     return this.info;
/*     */   }
/*     */   
/*     */   public Object getData() {
/*  64 */     AudioFormat audioFormat = getFormat();
/*     */     
/*  66 */     InputStream inputStream = this.data.getInputStream();
/*  67 */     if (inputStream == null)
/*  68 */       return null; 
/*  69 */     return new AudioInputStream(inputStream, audioFormat, this.data.capacity());
/*     */   }
/*     */   
/*     */   public ModelByteBuffer getDataBuffer() {
/*  73 */     return this.data;
/*     */   }
/*     */   
/*     */   public AudioFormat getFormat() {
/*  77 */     return this.format;
/*     */   }
/*     */   
/*     */   public void setFormat(AudioFormat paramAudioFormat) {
/*  81 */     this.format = paramAudioFormat;
/*     */   }
/*     */   
/*     */   public void setData(ModelByteBuffer paramModelByteBuffer) {
/*  85 */     this.data = paramModelByteBuffer;
/*     */   }
/*     */   
/*     */   public void setData(byte[] paramArrayOfbyte) {
/*  89 */     this.data = new ModelByteBuffer(paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   public void setData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  93 */     this.data = new ModelByteBuffer(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  97 */     return this.info.name;
/*     */   }
/*     */   
/*     */   public void setName(String paramString) {
/* 101 */     this.info.name = paramString;
/*     */   }
/*     */   
/*     */   public DLSSampleOptions getSampleoptions() {
/* 105 */     return this.sampleoptions;
/*     */   }
/*     */   
/*     */   public void setSampleoptions(DLSSampleOptions paramDLSSampleOptions) {
/* 109 */     this.sampleoptions = paramDLSSampleOptions;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 113 */     return "Sample: " + this.info.name;
/*     */   }
/*     */   
/*     */   public byte[] getGuid() {
/* 117 */     return (this.guid == null) ? null : Arrays.copyOf(this.guid, this.guid.length);
/*     */   }
/*     */   
/*     */   public void setGuid(byte[] paramArrayOfbyte) {
/* 121 */     this.guid = (paramArrayOfbyte == null) ? null : Arrays.copyOf(paramArrayOfbyte, paramArrayOfbyte.length);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\DLSSample.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */