/*    */ package sun.audio;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
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
/*    */ public class AudioDataStream
/*    */   extends ByteArrayInputStream
/*    */ {
/*    */   private final AudioData ad;
/*    */   
/*    */   public AudioDataStream(AudioData paramAudioData) {
/* 47 */     super(paramAudioData.buffer);
/* 48 */     this.ad = paramAudioData;
/*    */   }
/*    */   
/*    */   final AudioData getAudioData() {
/* 52 */     return this.ad;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\audio\AudioDataStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */