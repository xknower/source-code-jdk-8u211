/*    */ package sun.print;
/*    */ 
/*    */ import javax.print.attribute.PrintRequestAttribute;
/*    */ import javax.print.attribute.standard.Media;
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
/*    */ public class SunAlternateMedia
/*    */   implements PrintRequestAttribute
/*    */ {
/*    */   private static final long serialVersionUID = -8878868345472850201L;
/*    */   private Media media;
/*    */   
/*    */   public SunAlternateMedia(Media paramMedia) {
/* 43 */     this.media = paramMedia;
/*    */   }
/*    */   
/*    */   public Media getMedia() {
/* 47 */     return this.media;
/*    */   }
/*    */   
/*    */   public final Class getCategory() {
/* 51 */     return SunAlternateMedia.class;
/*    */   }
/*    */   
/*    */   public final String getName() {
/* 55 */     return "sun-alternate-media";
/*    */   }
/*    */   
/*    */   public String toString() {
/* 59 */     return "alternate-media: " + this.media.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 67 */     return this.media.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\print\SunAlternateMedia.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */