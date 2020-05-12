/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicSliderUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalSliderUI
/*     */   extends BasicSliderUI
/*     */ {
/*  55 */   protected final int TICK_BUFFER = 4;
/*     */ 
/*     */   
/*     */   protected boolean filledSlider = false;
/*     */ 
/*     */   
/*     */   protected static Color thumbColor;
/*     */ 
/*     */   
/*     */   protected static Color highlightColor;
/*     */ 
/*     */   
/*     */   protected static Color darkShadowColor;
/*     */   
/*     */   protected static int trackWidth;
/*     */   
/*     */   protected static int tickLength;
/*     */   
/*     */   private int safeLength;
/*     */   
/*     */   protected static Icon horizThumbIcon;
/*     */   
/*     */   protected static Icon vertThumbIcon;
/*     */   
/*     */   private static Icon SAFE_HORIZ_THUMB_ICON;
/*     */   
/*     */   private static Icon SAFE_VERT_THUMB_ICON;
/*     */   
/*  83 */   protected final String SLIDER_FILL = "JSlider.isFilled";
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  86 */     return new MetalSliderUI();
/*     */   }
/*     */   
/*     */   public MetalSliderUI() {
/*  90 */     super(null);
/*     */   }
/*     */   
/*     */   private static Icon getHorizThumbIcon() {
/*  94 */     if (System.getSecurityManager() != null) {
/*  95 */       return SAFE_HORIZ_THUMB_ICON;
/*     */     }
/*  97 */     return horizThumbIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Icon getVertThumbIcon() {
/* 102 */     if (System.getSecurityManager() != null) {
/* 103 */       return SAFE_VERT_THUMB_ICON;
/*     */     }
/* 105 */     return vertThumbIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 110 */     trackWidth = ((Integer)UIManager.get("Slider.trackWidth")).intValue();
/* 111 */     tickLength = this.safeLength = ((Integer)UIManager.get("Slider.majorTickLength")).intValue();
/*     */     
/* 113 */     horizThumbIcon = SAFE_HORIZ_THUMB_ICON = UIManager.getIcon("Slider.horizontalThumbIcon");
/*     */     
/* 115 */     vertThumbIcon = SAFE_VERT_THUMB_ICON = UIManager.getIcon("Slider.verticalThumbIcon");
/*     */     
/* 117 */     super.installUI(paramJComponent);
/*     */     
/* 119 */     thumbColor = UIManager.getColor("Slider.thumb");
/* 120 */     highlightColor = UIManager.getColor("Slider.highlight");
/* 121 */     darkShadowColor = UIManager.getColor("Slider.darkShadow");
/*     */     
/* 123 */     this.scrollListener.setScrollByBlock(false);
/*     */     
/* 125 */     prepareFilledSliderField();
/*     */   }
/*     */   
/*     */   protected PropertyChangeListener createPropertyChangeListener(JSlider paramJSlider) {
/* 129 */     return new MetalPropertyListener();
/*     */   }
/*     */   
/*     */   protected class MetalPropertyListener extends BasicSliderUI.PropertyChangeHandler {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 134 */       super.propertyChange(param1PropertyChangeEvent);
/*     */       
/* 136 */       if (param1PropertyChangeEvent.getPropertyName().equals("JSlider.isFilled")) {
/* 137 */         MetalSliderUI.this.prepareFilledSliderField();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void prepareFilledSliderField() {
/* 144 */     this.filledSlider = MetalLookAndFeel.usingOcean();
/*     */     
/* 146 */     Object object = this.slider.getClientProperty("JSlider.isFilled");
/*     */     
/* 148 */     if (object != null) {
/* 149 */       this.filledSlider = ((Boolean)object).booleanValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public void paintThumb(Graphics paramGraphics) {
/* 154 */     Rectangle rectangle = this.thumbRect;
/*     */     
/* 156 */     paramGraphics.translate(rectangle.x, rectangle.y);
/*     */     
/* 158 */     if (this.slider.getOrientation() == 0) {
/* 159 */       getHorizThumbIcon().paintIcon(this.slider, paramGraphics, 0, 0);
/*     */     } else {
/*     */       
/* 162 */       getVertThumbIcon().paintIcon(this.slider, paramGraphics, 0, 0);
/*     */     } 
/*     */     
/* 165 */     paramGraphics.translate(-rectangle.x, -rectangle.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle getPaintTrackRect() {
/* 172 */     int j, m, i = 0, k = 0;
/* 173 */     if (this.slider.getOrientation() == 0) {
/* 174 */       m = this.trackRect.height - 1 - getThumbOverhang();
/* 175 */       k = m - getTrackWidth() - 1;
/* 176 */       j = this.trackRect.width - 1;
/*     */     } else {
/*     */       
/* 179 */       if (MetalUtils.isLeftToRight(this.slider)) {
/*     */         
/* 181 */         i = this.trackRect.width - getThumbOverhang() - getTrackWidth();
/* 182 */         j = this.trackRect.width - getThumbOverhang() - 1;
/*     */       } else {
/*     */         
/* 185 */         i = getThumbOverhang();
/* 186 */         j = getThumbOverhang() + getTrackWidth() - 1;
/*     */       } 
/* 188 */       m = this.trackRect.height - 1;
/*     */     } 
/* 190 */     return new Rectangle(this.trackRect.x + i, this.trackRect.y + k, j - i, m - k);
/*     */   }
/*     */   
/*     */   public void paintTrack(Graphics paramGraphics) {
/*     */     int k, m;
/* 195 */     if (MetalLookAndFeel.usingOcean()) {
/* 196 */       oceanPaintTrack(paramGraphics);
/*     */       
/*     */       return;
/*     */     } 
/* 200 */     Color color = !this.slider.isEnabled() ? MetalLookAndFeel.getControlShadow() : this.slider.getForeground();
/*     */     
/* 202 */     boolean bool = MetalUtils.isLeftToRight(this.slider);
/*     */     
/* 204 */     paramGraphics.translate(this.trackRect.x, this.trackRect.y);
/*     */     
/* 206 */     int i = 0;
/* 207 */     int j = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     if (this.slider.getOrientation() == 0) {
/* 213 */       m = this.trackRect.height - 1 - getThumbOverhang();
/* 214 */       j = m - getTrackWidth() - 1;
/* 215 */       k = this.trackRect.width - 1;
/*     */     } else {
/*     */       
/* 218 */       if (bool) {
/*     */         
/* 220 */         i = this.trackRect.width - getThumbOverhang() - getTrackWidth();
/* 221 */         k = this.trackRect.width - getThumbOverhang() - 1;
/*     */       } else {
/*     */         
/* 224 */         i = getThumbOverhang();
/* 225 */         k = getThumbOverhang() + getTrackWidth() - 1;
/*     */       } 
/* 227 */       m = this.trackRect.height - 1;
/*     */     } 
/*     */     
/* 230 */     if (this.slider.isEnabled()) {
/* 231 */       paramGraphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 232 */       paramGraphics.drawRect(i, j, k - i - 1, m - j - 1);
/*     */ 
/*     */       
/* 235 */       paramGraphics.setColor(MetalLookAndFeel.getControlHighlight());
/* 236 */       paramGraphics.drawLine(i + 1, m, k, m);
/* 237 */       paramGraphics.drawLine(k, j + 1, k, m);
/*     */       
/* 239 */       paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/* 240 */       paramGraphics.drawLine(i + 1, j + 1, k - 2, j + 1);
/* 241 */       paramGraphics.drawLine(i + 1, j + 1, i + 1, m - 2);
/*     */     } else {
/*     */       
/* 244 */       paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/* 245 */       paramGraphics.drawRect(i, j, k - i - 1, m - j - 1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 250 */     if (this.filledSlider) {
/*     */       int n;
/*     */       
/*     */       int i1;
/*     */       
/*     */       int i2;
/*     */       int i3;
/* 257 */       if (this.slider.getOrientation() == 0) {
/* 258 */         int i4 = this.thumbRect.x + this.thumbRect.width / 2;
/* 259 */         i4 -= this.trackRect.x;
/* 260 */         n = !this.slider.isEnabled() ? j : (j + 1);
/* 261 */         i2 = !this.slider.isEnabled() ? (m - 1) : (m - 2);
/*     */         
/* 263 */         if (!drawInverted()) {
/* 264 */           i1 = !this.slider.isEnabled() ? i : (i + 1);
/* 265 */           i3 = i4;
/*     */         } else {
/*     */           
/* 268 */           i1 = i4;
/* 269 */           i3 = !this.slider.isEnabled() ? (k - 1) : (k - 2);
/*     */         } 
/*     */       } else {
/*     */         
/* 273 */         int i4 = this.thumbRect.y + this.thumbRect.height / 2;
/* 274 */         i4 -= this.trackRect.y;
/* 275 */         i1 = !this.slider.isEnabled() ? i : (i + 1);
/* 276 */         i3 = !this.slider.isEnabled() ? (k - 1) : (k - 2);
/*     */         
/* 278 */         if (!drawInverted()) {
/* 279 */           n = i4;
/* 280 */           i2 = !this.slider.isEnabled() ? (m - 1) : (m - 2);
/*     */         } else {
/*     */           
/* 283 */           n = !this.slider.isEnabled() ? j : (j + 1);
/* 284 */           i2 = i4;
/*     */         } 
/*     */       } 
/*     */       
/* 288 */       if (this.slider.isEnabled()) {
/* 289 */         paramGraphics.setColor(this.slider.getBackground());
/* 290 */         paramGraphics.drawLine(i1, n, i3, n);
/* 291 */         paramGraphics.drawLine(i1, n, i1, i2);
/*     */         
/* 293 */         paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/* 294 */         paramGraphics.fillRect(i1 + 1, n + 1, i3 - i1, i2 - n);
/*     */       }
/*     */       else {
/*     */         
/* 298 */         paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/* 299 */         paramGraphics.fillRect(i1, n, i3 - i1, i2 - n);
/*     */       } 
/*     */     } 
/*     */     
/* 303 */     paramGraphics.translate(-this.trackRect.x, -this.trackRect.y);
/*     */   }
/*     */   
/*     */   private void oceanPaintTrack(Graphics paramGraphics) {
/* 307 */     boolean bool1 = MetalUtils.isLeftToRight(this.slider);
/* 308 */     boolean bool2 = drawInverted();
/* 309 */     Color color = (Color)UIManager.get("Slider.altTrackColor");
/*     */ 
/*     */ 
/*     */     
/* 313 */     Rectangle rectangle = getPaintTrackRect();
/* 314 */     paramGraphics.translate(rectangle.x, rectangle.y);
/*     */ 
/*     */     
/* 317 */     int i = rectangle.width;
/* 318 */     int j = rectangle.height;
/*     */     
/* 320 */     if (this.slider.getOrientation() == 0) {
/* 321 */       int k = this.thumbRect.x + this.thumbRect.width / 2 - rectangle.x;
/*     */       
/* 323 */       if (this.slider.isEnabled()) {
/*     */ 
/*     */ 
/*     */         
/* 327 */         if (k > 0) {
/* 328 */           paramGraphics.setColor(bool2 ? MetalLookAndFeel.getControlDarkShadow() : 
/* 329 */               MetalLookAndFeel.getPrimaryControlDarkShadow());
/*     */           
/* 331 */           paramGraphics.drawRect(0, 0, k - 1, j - 1);
/*     */         } 
/*     */         
/* 334 */         if (k < i) {
/* 335 */           paramGraphics.setColor(bool2 ? MetalLookAndFeel.getPrimaryControlDarkShadow() : 
/* 336 */               MetalLookAndFeel.getControlDarkShadow());
/*     */           
/* 338 */           paramGraphics.drawRect(k, 0, i - k - 1, j - 1);
/*     */         } 
/*     */         
/* 341 */         if (this.filledSlider) {
/* 342 */           boolean bool; int m; paramGraphics.setColor(MetalLookAndFeel.getPrimaryControlShadow());
/* 343 */           if (bool2) {
/* 344 */             bool = k;
/* 345 */             m = i - 2;
/* 346 */             paramGraphics.drawLine(1, 1, k, 1);
/*     */           } else {
/* 348 */             bool = true;
/* 349 */             m = k;
/* 350 */             paramGraphics.drawLine(k, 1, i - 1, 1);
/*     */           } 
/* 352 */           if (j == 6) {
/* 353 */             paramGraphics.setColor(MetalLookAndFeel.getWhite());
/* 354 */             paramGraphics.drawLine(bool, 1, m, 1);
/* 355 */             paramGraphics.setColor(color);
/* 356 */             paramGraphics.drawLine(bool, 2, m, 2);
/* 357 */             paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/* 358 */             paramGraphics.drawLine(bool, 3, m, 3);
/* 359 */             paramGraphics.setColor(MetalLookAndFeel.getPrimaryControlShadow());
/* 360 */             paramGraphics.drawLine(bool, 4, m, 4);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 364 */         paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/*     */         
/* 366 */         if (k > 0) {
/* 367 */           if (!bool2 && this.filledSlider) {
/* 368 */             paramGraphics.fillRect(0, 0, k - 1, j - 1);
/*     */           } else {
/* 370 */             paramGraphics.drawRect(0, 0, k - 1, j - 1);
/*     */           } 
/*     */         }
/*     */         
/* 374 */         if (k < i) {
/* 375 */           if (bool2 && this.filledSlider) {
/* 376 */             paramGraphics.fillRect(k, 0, i - k - 1, j - 1);
/*     */           } else {
/* 378 */             paramGraphics.drawRect(k, 0, i - k - 1, j - 1);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } else {
/* 383 */       int k = this.thumbRect.y + this.thumbRect.height / 2 - rectangle.y;
/*     */       
/* 385 */       if (this.slider.isEnabled()) {
/*     */ 
/*     */ 
/*     */         
/* 389 */         if (k > 0) {
/* 390 */           paramGraphics.setColor(bool2 ? MetalLookAndFeel.getPrimaryControlDarkShadow() : 
/* 391 */               MetalLookAndFeel.getControlDarkShadow());
/*     */           
/* 393 */           paramGraphics.drawRect(0, 0, i - 1, k - 1);
/*     */         } 
/*     */         
/* 396 */         if (k < j) {
/* 397 */           paramGraphics.setColor(bool2 ? MetalLookAndFeel.getControlDarkShadow() : 
/* 398 */               MetalLookAndFeel.getPrimaryControlDarkShadow());
/*     */           
/* 400 */           paramGraphics.drawRect(0, k, i - 1, j - k - 1);
/*     */         } 
/*     */         
/* 403 */         if (this.filledSlider) {
/* 404 */           int m, n; paramGraphics.setColor(MetalLookAndFeel.getPrimaryControlShadow());
/* 405 */           if (drawInverted()) {
/* 406 */             m = 1;
/* 407 */             n = k;
/* 408 */             if (bool1) {
/* 409 */               paramGraphics.drawLine(1, k, 1, j - 1);
/*     */             } else {
/* 411 */               paramGraphics.drawLine(i - 2, k, i - 2, j - 1);
/*     */             } 
/*     */           } else {
/* 414 */             m = k;
/* 415 */             n = j - 2;
/* 416 */             if (bool1) {
/* 417 */               paramGraphics.drawLine(1, 1, 1, k);
/*     */             } else {
/* 419 */               paramGraphics.drawLine(i - 2, 1, i - 2, k);
/*     */             } 
/*     */           } 
/* 422 */           if (i == 6) {
/* 423 */             paramGraphics.setColor(bool1 ? MetalLookAndFeel.getWhite() : MetalLookAndFeel.getPrimaryControlShadow());
/* 424 */             paramGraphics.drawLine(1, m, 1, n);
/* 425 */             paramGraphics.setColor(bool1 ? color : MetalLookAndFeel.getControlShadow());
/* 426 */             paramGraphics.drawLine(2, m, 2, n);
/* 427 */             paramGraphics.setColor(bool1 ? MetalLookAndFeel.getControlShadow() : color);
/* 428 */             paramGraphics.drawLine(3, m, 3, n);
/* 429 */             paramGraphics.setColor(bool1 ? MetalLookAndFeel.getPrimaryControlShadow() : MetalLookAndFeel.getWhite());
/* 430 */             paramGraphics.drawLine(4, m, 4, n);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 434 */         paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/*     */         
/* 436 */         if (k > 0) {
/* 437 */           if (bool2 && this.filledSlider) {
/* 438 */             paramGraphics.fillRect(0, 0, i - 1, k - 1);
/*     */           } else {
/* 440 */             paramGraphics.drawRect(0, 0, i - 1, k - 1);
/*     */           } 
/*     */         }
/*     */         
/* 444 */         if (k < j) {
/* 445 */           if (!bool2 && this.filledSlider) {
/* 446 */             paramGraphics.fillRect(0, k, i - 1, j - k - 1);
/*     */           } else {
/* 448 */             paramGraphics.drawRect(0, k, i - 1, j - k - 1);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 454 */     paramGraphics.translate(-rectangle.x, -rectangle.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintFocus(Graphics paramGraphics) {}
/*     */   
/*     */   protected Dimension getThumbSize() {
/* 461 */     Dimension dimension = new Dimension();
/*     */     
/* 463 */     if (this.slider.getOrientation() == 1) {
/* 464 */       dimension.width = getVertThumbIcon().getIconWidth();
/* 465 */       dimension.height = getVertThumbIcon().getIconHeight();
/*     */     } else {
/*     */       
/* 468 */       dimension.width = getHorizThumbIcon().getIconWidth();
/* 469 */       dimension.height = getHorizThumbIcon().getIconHeight();
/*     */     } 
/*     */     
/* 472 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTickLength() {
/* 481 */     return (this.slider.getOrientation() == 0) ? (this.safeLength + 4 + 1) : (this.safeLength + 4 + 3);
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
/*     */   protected int getTrackWidth() {
/* 495 */     if (this.slider.getOrientation() == 0) {
/* 496 */       return (int)(0.4375D * this.thumbRect.height);
/*     */     }
/*     */     
/* 499 */     return (int)(0.4375D * this.thumbRect.width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getTrackLength() {
/* 508 */     if (this.slider.getOrientation() == 0) {
/* 509 */       return this.trackRect.width;
/*     */     }
/* 511 */     return this.trackRect.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getThumbOverhang() {
/* 518 */     return (int)(getThumbSize().getHeight() - getTrackWidth()) / 2;
/*     */   }
/*     */   
/*     */   protected void scrollDueToClickInTrack(int paramInt) {
/* 522 */     scrollByUnit(paramInt);
/*     */   }
/*     */   
/*     */   protected void paintMinorTickForHorizSlider(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/* 526 */     paramGraphics.setColor(this.slider.isEnabled() ? this.slider.getForeground() : MetalLookAndFeel.getControlShadow());
/* 527 */     paramGraphics.drawLine(paramInt, 4, paramInt, 4 + this.safeLength / 2);
/*     */   }
/*     */   
/*     */   protected void paintMajorTickForHorizSlider(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/* 531 */     paramGraphics.setColor(this.slider.isEnabled() ? this.slider.getForeground() : MetalLookAndFeel.getControlShadow());
/* 532 */     paramGraphics.drawLine(paramInt, 4, paramInt, 4 + this.safeLength - 1);
/*     */   }
/*     */   
/*     */   protected void paintMinorTickForVertSlider(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/* 536 */     paramGraphics.setColor(this.slider.isEnabled() ? this.slider.getForeground() : MetalLookAndFeel.getControlShadow());
/*     */     
/* 538 */     if (MetalUtils.isLeftToRight(this.slider)) {
/* 539 */       paramGraphics.drawLine(4, paramInt, 4 + this.safeLength / 2, paramInt);
/*     */     } else {
/*     */       
/* 542 */       paramGraphics.drawLine(0, paramInt, this.safeLength / 2, paramInt);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void paintMajorTickForVertSlider(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/* 547 */     paramGraphics.setColor(this.slider.isEnabled() ? this.slider.getForeground() : MetalLookAndFeel.getControlShadow());
/*     */     
/* 549 */     if (MetalUtils.isLeftToRight(this.slider)) {
/* 550 */       paramGraphics.drawLine(4, paramInt, 4 + this.safeLength, paramInt);
/*     */     } else {
/*     */       
/* 553 */       paramGraphics.drawLine(0, paramInt, this.safeLength, paramInt);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\metal\MetalSliderUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */