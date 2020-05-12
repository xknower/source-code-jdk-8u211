/*     */ package java.awt;
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
/*     */ public class BufferCapabilities
/*     */   implements Cloneable
/*     */ {
/*     */   private ImageCapabilities frontCaps;
/*     */   private ImageCapabilities backCaps;
/*     */   private FlipContents flipContents;
/*     */   
/*     */   public BufferCapabilities(ImageCapabilities paramImageCapabilities1, ImageCapabilities paramImageCapabilities2, FlipContents paramFlipContents) {
/*  55 */     if (paramImageCapabilities1 == null || paramImageCapabilities2 == null) {
/*  56 */       throw new IllegalArgumentException("Image capabilities specified cannot be null");
/*     */     }
/*     */     
/*  59 */     this.frontCaps = paramImageCapabilities1;
/*  60 */     this.backCaps = paramImageCapabilities2;
/*  61 */     this.flipContents = paramFlipContents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageCapabilities getFrontBufferCapabilities() {
/*  68 */     return this.frontCaps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageCapabilities getBackBufferCapabilities() {
/*  76 */     return this.backCaps;
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
/*     */   public boolean isPageFlipping() {
/*  90 */     return (getFlipContents() != null);
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
/*     */   public FlipContents getFlipContents() {
/* 108 */     return this.flipContents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullScreenRequired() {
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMultiBufferAvailable() {
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 137 */       return super.clone();
/* 138 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 140 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class FlipContents
/*     */     extends AttributeValue
/*     */   {
/* 152 */     private static int I_UNDEFINED = 0;
/* 153 */     private static int I_BACKGROUND = 1;
/* 154 */     private static int I_PRIOR = 2;
/* 155 */     private static int I_COPIED = 3;
/*     */     
/* 157 */     private static final String[] NAMES = new String[] { "undefined", "background", "prior", "copied" };
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
/* 169 */     public static final FlipContents UNDEFINED = new FlipContents(I_UNDEFINED);
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
/* 182 */     public static final FlipContents BACKGROUND = new FlipContents(I_BACKGROUND);
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
/* 195 */     public static final FlipContents PRIOR = new FlipContents(I_PRIOR);
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
/* 208 */     public static final FlipContents COPIED = new FlipContents(I_COPIED);
/*     */ 
/*     */     
/*     */     private FlipContents(int param1Int) {
/* 212 */       super(param1Int, NAMES);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\BufferCapabilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */