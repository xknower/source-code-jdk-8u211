/*     */ package javax.swing.plaf.synth;
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
/*     */ public class ColorType
/*     */ {
/*  67 */   public static final ColorType FOREGROUND = new ColorType("Foreground");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final ColorType BACKGROUND = new ColorType("Background");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final ColorType TEXT_FOREGROUND = new ColorType("TextForeground");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final ColorType TEXT_BACKGROUND = new ColorType("TextBackground");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final ColorType FOCUS = new ColorType("Focus");
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
/* 102 */   public static final int MAX_COUNT = Math.max(FOREGROUND.getID(), Math.max(BACKGROUND
/* 103 */         .getID(), FOCUS.getID())) + 1;
/*     */   
/*     */   private static int nextID;
/*     */   
/*     */   private String description;
/*     */   
/*     */   private int index;
/*     */   
/*     */   protected ColorType(String paramString) {
/* 112 */     if (paramString == null) {
/* 113 */       throw new NullPointerException("ColorType must have a valid description");
/*     */     }
/*     */     
/* 116 */     this.description = paramString;
/* 117 */     synchronized (ColorType.class) {
/* 118 */       this.index = nextID++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getID() {
/* 128 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 139 */     return this.description;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\synth\ColorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */