/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.font.TextHitInfo;
/*     */ import java.awt.font.TextLayout;
/*     */ import java.awt.geom.Rectangle2D;
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
/*     */ class GlyphPainter2
/*     */   extends GlyphView.GlyphPainter
/*     */ {
/*     */   TextLayout layout;
/*     */   
/*     */   public GlyphPainter2(TextLayout paramTextLayout) {
/*  54 */     this.layout = paramTextLayout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlyphView.GlyphPainter getPainter(GlyphView paramGlyphView, int paramInt1, int paramInt2) {
/*  61 */     return null;
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
/*     */   public float getSpan(GlyphView paramGlyphView, int paramInt1, int paramInt2, TabExpander paramTabExpander, float paramFloat) {
/*  73 */     if (paramInt1 == paramGlyphView.getStartOffset() && paramInt2 == paramGlyphView.getEndOffset()) {
/*  74 */       return this.layout.getAdvance();
/*     */     }
/*  76 */     int i = paramGlyphView.getStartOffset();
/*  77 */     int j = paramInt1 - i;
/*  78 */     int k = paramInt2 - i;
/*     */     
/*  80 */     TextHitInfo textHitInfo1 = TextHitInfo.afterOffset(j);
/*  81 */     TextHitInfo textHitInfo2 = TextHitInfo.beforeOffset(k);
/*  82 */     float[] arrayOfFloat = this.layout.getCaretInfo(textHitInfo1);
/*  83 */     float f1 = arrayOfFloat[0];
/*  84 */     arrayOfFloat = this.layout.getCaretInfo(textHitInfo2);
/*  85 */     float f2 = arrayOfFloat[0];
/*  86 */     return (f2 > f1) ? (f2 - f1) : (f1 - f2);
/*     */   }
/*     */   
/*     */   public float getHeight(GlyphView paramGlyphView) {
/*  90 */     return this.layout.getAscent() + this.layout.getDescent() + this.layout.getLeading();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAscent(GlyphView paramGlyphView) {
/*  98 */     return this.layout.getAscent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDescent(GlyphView paramGlyphView) {
/* 106 */     return this.layout.getDescent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(GlyphView paramGlyphView, Graphics paramGraphics, Shape paramShape, int paramInt1, int paramInt2) {
/* 116 */     if (paramGraphics instanceof Graphics2D) {
/* 117 */       Rectangle2D rectangle2D = paramShape.getBounds2D();
/* 118 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 119 */       float f1 = (float)rectangle2D.getY() + this.layout.getAscent() + this.layout.getLeading();
/* 120 */       float f2 = (float)rectangle2D.getX();
/* 121 */       if (paramInt1 > paramGlyphView.getStartOffset() || paramInt2 < paramGlyphView.getEndOffset()) {
/*     */ 
/*     */         
/*     */         try {
/* 125 */           Shape shape1 = paramGlyphView.modelToView(paramInt1, Position.Bias.Forward, paramInt2, Position.Bias.Backward, paramShape);
/*     */           
/* 127 */           Shape shape2 = paramGraphics.getClip();
/* 128 */           graphics2D.clip(shape1);
/* 129 */           this.layout.draw(graphics2D, f2, f1);
/* 130 */           paramGraphics.setClip(shape2);
/* 131 */         } catch (BadLocationException badLocationException) {}
/*     */       } else {
/* 133 */         this.layout.draw(graphics2D, f2, f1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape modelToView(GlyphView paramGlyphView, int paramInt, Position.Bias paramBias, Shape paramShape) throws BadLocationException {
/* 140 */     int i = paramInt - paramGlyphView.getStartOffset();
/* 141 */     Rectangle2D rectangle2D = paramShape.getBounds2D();
/*     */     
/* 143 */     TextHitInfo textHitInfo = (paramBias == Position.Bias.Forward) ? TextHitInfo.afterOffset(i) : TextHitInfo.beforeOffset(i);
/* 144 */     float[] arrayOfFloat = this.layout.getCaretInfo(textHitInfo);
/*     */ 
/*     */ 
/*     */     
/* 148 */     rectangle2D.setRect(rectangle2D.getX() + arrayOfFloat[0], rectangle2D.getY(), 1.0D, rectangle2D.getHeight());
/* 149 */     return rectangle2D;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public int viewToModel(GlyphView paramGlyphView, float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias) {
/* 170 */     Rectangle2D rectangle2D = (paramShape instanceof Rectangle2D) ? (Rectangle2D)paramShape : paramShape.getBounds2D();
/*     */ 
/*     */     
/* 173 */     TextHitInfo textHitInfo = this.layout.hitTestChar(paramFloat1 - (float)rectangle2D.getX(), 0.0F);
/* 174 */     int i = textHitInfo.getInsertionIndex();
/*     */     
/* 176 */     if (i == paramGlyphView.getEndOffset()) {
/* 177 */       i--;
/*     */     }
/*     */     
/* 180 */     paramArrayOfBias[0] = textHitInfo.isLeadingEdge() ? Position.Bias.Forward : Position.Bias.Backward;
/* 181 */     return i + paramGlyphView.getStartOffset();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBoundedPosition(GlyphView paramGlyphView, int paramInt, float paramFloat1, float paramFloat2) {
/*     */     TextHitInfo textHitInfo;
/* 204 */     if (paramFloat2 < 0.0F) {
/* 205 */       throw new IllegalArgumentException("Length must be >= 0.");
/*     */     }
/*     */ 
/*     */     
/* 209 */     if (this.layout.isLeftToRight()) {
/* 210 */       textHitInfo = this.layout.hitTestChar(paramFloat2, 0.0F);
/*     */     } else {
/* 212 */       textHitInfo = this.layout.hitTestChar(this.layout.getAdvance() - paramFloat2, 0.0F);
/*     */     } 
/* 214 */     return paramGlyphView.getStartOffset() + textHitInfo.getCharIndex();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextVisualPositionFrom(GlyphView paramGlyphView, int paramInt1, Position.Bias paramBias, Shape paramShape, int paramInt2, Position.Bias[] paramArrayOfBias) throws BadLocationException {
/*     */     boolean bool;
/*     */     TextHitInfo textHitInfo1, textHitInfo2;
/* 242 */     Document document = paramGlyphView.getDocument();
/* 243 */     int i = paramGlyphView.getStartOffset();
/* 244 */     int j = paramGlyphView.getEndOffset();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     switch (paramInt2) {
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
/*     */       case 1:
/*     */       case 5:
/* 377 */         return paramInt1;
/*     */       case 3:
/*     */         bool = AbstractDocument.isLeftToRight(document, i, j);
/*     */         if (i == document.getLength()) {
/*     */           if (paramInt1 == -1) {
/*     */             paramArrayOfBias[0] = Position.Bias.Forward;
/*     */             return i;
/*     */           } 
/*     */           return -1;
/*     */         } 
/*     */         if (paramInt1 == -1) {
/*     */           if (bool) {
/*     */             paramArrayOfBias[0] = Position.Bias.Forward;
/*     */             return i;
/*     */           } 
/*     */           Segment segment = paramGlyphView.getText(j - 1, j);
/*     */           char c = segment.array[segment.offset];
/*     */           SegmentCache.releaseSharedSegment(segment);
/*     */           if (c == '\n') {
/*     */             paramArrayOfBias[0] = Position.Bias.Forward;
/*     */             return j - 1;
/*     */           } 
/*     */           paramArrayOfBias[0] = Position.Bias.Backward;
/*     */           return j;
/*     */         } 
/*     */         if (paramBias == Position.Bias.Forward) {
/*     */           textHitInfo1 = TextHitInfo.afterOffset(paramInt1 - i);
/*     */         } else {
/*     */           textHitInfo1 = TextHitInfo.beforeOffset(paramInt1 - i);
/*     */         } 
/*     */         textHitInfo2 = this.layout.getNextRightHit(textHitInfo1);
/*     */         if (textHitInfo2 == null)
/*     */           return -1; 
/*     */         if (bool != this.layout.isLeftToRight())
/*     */           textHitInfo2 = this.layout.getVisualOtherHit(textHitInfo2); 
/*     */         paramInt1 = textHitInfo2.getInsertionIndex() + i;
/*     */         if (paramInt1 == j) {
/*     */           Segment segment = paramGlyphView.getText(j - 1, j);
/*     */           char c = segment.array[segment.offset];
/*     */           SegmentCache.releaseSharedSegment(segment);
/*     */           if (c == '\n')
/*     */             return -1; 
/*     */           paramArrayOfBias[0] = Position.Bias.Backward;
/*     */         } else {
/*     */           paramArrayOfBias[0] = Position.Bias.Forward;
/*     */         } 
/*     */         return paramInt1;
/*     */       case 7:
/*     */         bool = AbstractDocument.isLeftToRight(document, i, j);
/*     */         if (i == document.getLength()) {
/*     */           if (paramInt1 == -1) {
/*     */             paramArrayOfBias[0] = Position.Bias.Forward;
/*     */             return i;
/*     */           } 
/*     */           return -1;
/*     */         } 
/*     */         if (paramInt1 == -1) {
/*     */           if (bool) {
/*     */             Segment segment = paramGlyphView.getText(j - 1, j);
/*     */             char c = segment.array[segment.offset];
/*     */             SegmentCache.releaseSharedSegment(segment);
/*     */             if (c == '\n' || Character.isSpaceChar(c)) {
/*     */               paramArrayOfBias[0] = Position.Bias.Forward;
/*     */               return j - 1;
/*     */             } 
/*     */             paramArrayOfBias[0] = Position.Bias.Backward;
/*     */             return j;
/*     */           } 
/*     */           paramArrayOfBias[0] = Position.Bias.Forward;
/*     */           return i;
/*     */         } 
/*     */         if (paramBias == Position.Bias.Forward) {
/*     */           textHitInfo1 = TextHitInfo.afterOffset(paramInt1 - i);
/*     */         } else {
/*     */           textHitInfo1 = TextHitInfo.beforeOffset(paramInt1 - i);
/*     */         } 
/*     */         textHitInfo2 = this.layout.getNextLeftHit(textHitInfo1);
/*     */         if (textHitInfo2 == null)
/*     */           return -1; 
/*     */         if (bool != this.layout.isLeftToRight())
/*     */           textHitInfo2 = this.layout.getVisualOtherHit(textHitInfo2); 
/*     */         paramInt1 = textHitInfo2.getInsertionIndex() + i;
/*     */         if (paramInt1 == j) {
/*     */           Segment segment = paramGlyphView.getText(j - 1, j);
/*     */           char c = segment.array[segment.offset];
/*     */           SegmentCache.releaseSharedSegment(segment);
/*     */           if (c == '\n')
/*     */             return -1; 
/*     */           paramArrayOfBias[0] = Position.Bias.Backward;
/*     */         } else {
/*     */           paramArrayOfBias[0] = Position.Bias.Forward;
/*     */         } 
/*     */         return paramInt1;
/*     */     } 
/*     */     throw new IllegalArgumentException("Bad direction: " + paramInt2);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\text\GlyphPainter2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */