/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.InputMethodEvent;
/*     */ import java.awt.event.InvocationEvent;
/*     */ import java.awt.font.TextAttribute;
/*     */ import java.awt.font.TextHitInfo;
/*     */ import java.awt.im.InputMethodHighlight;
/*     */ import java.awt.im.InputSubset;
/*     */ import java.awt.im.spi.InputMethodContext;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.text.Annotation;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.AttributedString;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import sun.awt.im.InputMethodAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WInputMethod
/*     */   extends InputMethodAdapter
/*     */ {
/*     */   private InputMethodContext inputContext;
/*     */   private Component awtFocussedComponent;
/*  55 */   private WComponentPeer awtFocussedComponentPeer = null;
/*  56 */   private WComponentPeer lastFocussedComponentPeer = null;
/*     */   
/*     */   private boolean isLastFocussedActiveClient = false;
/*     */   
/*     */   private boolean isActive;
/*     */   
/*     */   private int context;
/*     */   
/*     */   private boolean open;
/*     */   
/*     */   private int cmode;
/*     */   
/*     */   private Locale currentLocale;
/*     */   
/*     */   private boolean statusWindowHidden = false;
/*     */   
/*     */   public static final byte ATTR_INPUT = 0;
/*     */   public static final byte ATTR_TARGET_CONVERTED = 1;
/*     */   public static final byte ATTR_CONVERTED = 2;
/*     */   public static final byte ATTR_TARGET_NOTCONVERTED = 3;
/*     */   public static final byte ATTR_INPUT_ERROR = 4;
/*     */   public static final int IME_CMODE_ALPHANUMERIC = 0;
/*     */   public static final int IME_CMODE_NATIVE = 1;
/*     */   public static final int IME_CMODE_KATAKANA = 2;
/*     */   public static final int IME_CMODE_LANGUAGE = 3;
/*     */   public static final int IME_CMODE_FULLSHAPE = 8;
/*     */   public static final int IME_CMODE_HANJACONVERT = 64;
/*     */   public static final int IME_CMODE_ROMAN = 16;
/*     */   private static final boolean COMMIT_INPUT = true;
/*     */   private static final boolean DISCARD_INPUT = false;
/*     */   private static Map<TextAttribute, Object>[] highlightStyles;
/*     */   
/*     */   static {
/*  89 */     Map[] arrayOfMap = new Map[4];
/*     */ 
/*     */ 
/*     */     
/*  93 */     HashMap<Object, Object> hashMap = new HashMap<>(1);
/*  94 */     hashMap.put(TextAttribute.INPUT_METHOD_UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
/*  95 */     arrayOfMap[0] = Collections.unmodifiableMap(hashMap);
/*     */ 
/*     */     
/*  98 */     hashMap = new HashMap<>(1);
/*  99 */     hashMap.put(TextAttribute.INPUT_METHOD_UNDERLINE, TextAttribute.UNDERLINE_LOW_GRAY);
/* 100 */     arrayOfMap[1] = Collections.unmodifiableMap(hashMap);
/*     */ 
/*     */     
/* 103 */     hashMap = new HashMap<>(1);
/* 104 */     hashMap.put(TextAttribute.INPUT_METHOD_UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
/* 105 */     arrayOfMap[2] = Collections.unmodifiableMap(hashMap);
/*     */ 
/*     */     
/* 108 */     hashMap = new HashMap<>(4);
/* 109 */     Color color = new Color(0, 0, 128);
/* 110 */     hashMap.put(TextAttribute.FOREGROUND, color);
/* 111 */     hashMap.put(TextAttribute.BACKGROUND, Color.white);
/* 112 */     hashMap.put(TextAttribute.SWAP_COLORS, TextAttribute.SWAP_COLORS_ON);
/* 113 */     hashMap.put(TextAttribute.INPUT_METHOD_UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
/* 114 */     arrayOfMap[3] = Collections.unmodifiableMap(hashMap);
/*     */     
/* 116 */     highlightStyles = (Map<TextAttribute, Object>[])arrayOfMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public WInputMethod() {
/* 121 */     this.context = createNativeContext();
/* 122 */     this.cmode = getConversionStatus(this.context);
/* 123 */     this.open = getOpenStatus(this.context);
/* 124 */     this.currentLocale = getNativeLocale();
/* 125 */     if (this.currentLocale == null) {
/* 126 */       this.currentLocale = Locale.getDefault();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 134 */     if (this.context != 0) {
/* 135 */       destroyNativeContext(this.context);
/* 136 */       this.context = 0;
/*     */     } 
/* 138 */     super.finalize();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setInputMethodContext(InputMethodContext paramInputMethodContext) {
/* 143 */     this.inputContext = paramInputMethodContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void dispose() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getControlObject() {
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setLocale(Locale paramLocale) {
/* 165 */     return setLocale(paramLocale, false);
/*     */   }
/*     */   
/*     */   private boolean setLocale(Locale paramLocale, boolean paramBoolean) {
/* 169 */     Locale[] arrayOfLocale = WInputMethodDescriptor.getAvailableLocalesInternal();
/* 170 */     for (byte b = 0; b < arrayOfLocale.length; b++) {
/* 171 */       Locale locale = arrayOfLocale[b];
/* 172 */       if (paramLocale.equals(locale) || (locale
/*     */         
/* 174 */         .equals(Locale.JAPAN) && paramLocale.equals(Locale.JAPANESE)) || (locale
/* 175 */         .equals(Locale.KOREA) && paramLocale.equals(Locale.KOREAN))) {
/* 176 */         if (this.isActive) {
/* 177 */           setNativeLocale(locale.toLanguageTag(), paramBoolean);
/*     */         }
/* 179 */         this.currentLocale = locale;
/* 180 */         return true;
/*     */       } 
/*     */     } 
/* 183 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 188 */     if (this.isActive) {
/* 189 */       this.currentLocale = getNativeLocale();
/* 190 */       if (this.currentLocale == null) {
/* 191 */         this.currentLocale = Locale.getDefault();
/*     */       }
/*     */     } 
/* 194 */     return this.currentLocale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCharacterSubsets(Character.Subset[] paramArrayOfSubset) {
/* 204 */     if (paramArrayOfSubset == null) {
/* 205 */       setConversionStatus(this.context, this.cmode);
/* 206 */       setOpenStatus(this.context, this.open);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 212 */     Character.Subset subset = paramArrayOfSubset[0];
/*     */     
/* 214 */     Locale locale = getNativeLocale();
/*     */ 
/*     */     
/* 217 */     if (locale == null) {
/*     */       return;
/*     */     }
/*     */     
/* 221 */     if (locale.getLanguage().equals(Locale.JAPANESE.getLanguage())) {
/* 222 */       if (subset == Character.UnicodeBlock.BASIC_LATIN || subset == InputSubset.LATIN_DIGITS) {
/* 223 */         setOpenStatus(this.context, false);
/*     */       } else {
/* 225 */         int i; if (subset == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || subset == InputSubset.KANJI || subset == Character.UnicodeBlock.HIRAGANA) {
/*     */ 
/*     */           
/* 228 */           i = 9;
/* 229 */         } else if (subset == Character.UnicodeBlock.KATAKANA) {
/* 230 */           i = 11;
/* 231 */         } else if (subset == InputSubset.HALFWIDTH_KATAKANA) {
/* 232 */           i = 3;
/* 233 */         } else if (subset == InputSubset.FULLWIDTH_LATIN) {
/* 234 */           i = 8;
/*     */         } else {
/*     */           return;
/* 237 */         }  setOpenStatus(this.context, true);
/* 238 */         i |= getConversionStatus(this.context) & 0x10;
/* 239 */         setConversionStatus(this.context, i);
/*     */       } 
/* 241 */     } else if (locale.getLanguage().equals(Locale.KOREAN.getLanguage())) {
/* 242 */       if (subset == Character.UnicodeBlock.BASIC_LATIN || subset == InputSubset.LATIN_DIGITS) {
/* 243 */         setOpenStatus(this.context, false);
/*     */       } else {
/* 245 */         byte b; if (subset == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || subset == InputSubset.HANJA || subset == Character.UnicodeBlock.HANGUL_SYLLABLES || subset == Character.UnicodeBlock.HANGUL_JAMO || subset == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 250 */           b = 1;
/* 251 */         } else if (subset == InputSubset.FULLWIDTH_LATIN) {
/* 252 */           b = 8;
/*     */         } else {
/*     */           return;
/* 255 */         }  setOpenStatus(this.context, true);
/* 256 */         setConversionStatus(this.context, b);
/*     */       } 
/* 258 */     } else if (locale.getLanguage().equals(Locale.CHINESE.getLanguage())) {
/* 259 */       if (subset == Character.UnicodeBlock.BASIC_LATIN || subset == InputSubset.LATIN_DIGITS) {
/* 260 */         setOpenStatus(this.context, false);
/*     */       } else {
/* 262 */         byte b; if (subset == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || subset == InputSubset.TRADITIONAL_HANZI || subset == InputSubset.SIMPLIFIED_HANZI) {
/*     */ 
/*     */           
/* 265 */           b = 1;
/* 266 */         } else if (subset == InputSubset.FULLWIDTH_LATIN) {
/* 267 */           b = 8;
/*     */         } else {
/*     */           return;
/* 270 */         }  setOpenStatus(this.context, true);
/* 271 */         setConversionStatus(this.context, b);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispatchEvent(AWTEvent paramAWTEvent) {
/* 278 */     if (paramAWTEvent instanceof ComponentEvent) {
/* 279 */       Component component = ((ComponentEvent)paramAWTEvent).getComponent();
/* 280 */       if (component == this.awtFocussedComponent) {
/* 281 */         if (this.awtFocussedComponentPeer == null || this.awtFocussedComponentPeer
/* 282 */           .isDisposed()) {
/* 283 */           this.awtFocussedComponentPeer = getNearestNativePeer(component);
/*     */         }
/* 285 */         if (this.awtFocussedComponentPeer != null) {
/* 286 */           handleNativeIMEEvent(this.awtFocussedComponentPeer, paramAWTEvent);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate() {
/* 294 */     boolean bool = haveActiveClient();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     if (this.lastFocussedComponentPeer != this.awtFocussedComponentPeer || this.isLastFocussedActiveClient != bool) {
/*     */       
/* 302 */       if (this.lastFocussedComponentPeer != null) {
/* 303 */         disableNativeIME(this.lastFocussedComponentPeer);
/*     */       }
/* 305 */       if (this.awtFocussedComponentPeer != null) {
/* 306 */         enableNativeIME(this.awtFocussedComponentPeer, this.context, !bool);
/*     */       }
/* 308 */       this.lastFocussedComponentPeer = this.awtFocussedComponentPeer;
/* 309 */       this.isLastFocussedActiveClient = bool;
/*     */     } 
/* 311 */     this.isActive = true;
/* 312 */     if (this.currentLocale != null) {
/* 313 */       setLocale(this.currentLocale, true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     if (this.statusWindowHidden) {
/* 323 */       setStatusWindowVisible(this.awtFocussedComponentPeer, true);
/* 324 */       this.statusWindowHidden = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deactivate(boolean paramBoolean) {
/* 334 */     getLocale();
/*     */ 
/*     */ 
/*     */     
/* 338 */     if (this.awtFocussedComponentPeer != null) {
/* 339 */       this.lastFocussedComponentPeer = this.awtFocussedComponentPeer;
/* 340 */       this.isLastFocussedActiveClient = haveActiveClient();
/*     */     } 
/* 342 */     this.isActive = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableInputMethod() {
/* 351 */     if (this.lastFocussedComponentPeer != null) {
/* 352 */       disableNativeIME(this.lastFocussedComponentPeer);
/* 353 */       this.lastFocussedComponentPeer = null;
/* 354 */       this.isLastFocussedActiveClient = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNativeInputMethodInfo() {
/* 364 */     return getNativeIMMDescription();
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
/*     */   protected void stopListening() {
/* 378 */     disableInputMethod();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAWTFocussedComponent(Component paramComponent) {
/* 384 */     if (paramComponent == null) {
/*     */       return;
/*     */     }
/* 387 */     WComponentPeer wComponentPeer = getNearestNativePeer(paramComponent);
/* 388 */     if (this.isActive) {
/*     */ 
/*     */       
/* 391 */       if (this.awtFocussedComponentPeer != null) {
/* 392 */         disableNativeIME(this.awtFocussedComponentPeer);
/*     */       }
/* 394 */       if (wComponentPeer != null) {
/* 395 */         enableNativeIME(wComponentPeer, this.context, !haveActiveClient());
/*     */       }
/*     */     } 
/* 398 */     this.awtFocussedComponent = paramComponent;
/* 399 */     this.awtFocussedComponentPeer = wComponentPeer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void hideWindows() {
/* 405 */     if (this.awtFocussedComponentPeer != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 411 */       setStatusWindowVisible(this.awtFocussedComponentPeer, false);
/* 412 */       this.statusWindowHidden = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotify() {
/* 421 */     endCompositionNative(this.context, false);
/* 422 */     this.awtFocussedComponent = null;
/* 423 */     this.awtFocussedComponentPeer = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Map<TextAttribute, ?> mapInputMethodHighlight(InputMethodHighlight paramInputMethodHighlight) {
/*     */     byte b;
/* 431 */     int i = paramInputMethodHighlight.getState();
/* 432 */     if (i == 0) {
/* 433 */       b = 0;
/* 434 */     } else if (i == 1) {
/* 435 */       b = 2;
/*     */     } else {
/* 437 */       return null;
/*     */     } 
/* 439 */     if (paramInputMethodHighlight.isSelected()) {
/* 440 */       b++;
/*     */     }
/* 442 */     return highlightStyles[b];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean supportsBelowTheSpot() {
/* 448 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endComposition() {
/* 456 */     endCompositionNative(this.context, 
/* 457 */         haveActiveClient());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCompositionEnabled(boolean paramBoolean) {
/* 465 */     setOpenStatus(this.context, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCompositionEnabled() {
/* 473 */     return getOpenStatus(this.context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendInputMethodEvent(int paramInt1, long paramLong, String paramString, int[] paramArrayOfint1, String[] paramArrayOfString, int[] paramArrayOfint2, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, int paramInt4) {
/* 482 */     AttributedCharacterIterator attributedCharacterIterator = null;
/*     */     
/* 484 */     if (paramString != null) {
/*     */ 
/*     */       
/* 487 */       AttributedString attributedString = new AttributedString(paramString);
/*     */ 
/*     */       
/* 490 */       attributedString.addAttribute(AttributedCharacterIterator.Attribute.LANGUAGE, 
/* 491 */           Locale.getDefault(), 0, paramString.length());
/*     */ 
/*     */       
/* 494 */       if (paramArrayOfint1 != null && paramArrayOfString != null && paramArrayOfString.length != 0 && paramArrayOfint1.length == paramArrayOfString.length + 1 && paramArrayOfint1[0] == 0 && paramArrayOfint1[paramArrayOfString.length] <= paramString
/*     */         
/* 496 */         .length()) {
/*     */         
/* 498 */         for (byte b = 0; b < paramArrayOfint1.length - 1; b++) {
/* 499 */           attributedString.addAttribute(AttributedCharacterIterator.Attribute.INPUT_METHOD_SEGMENT, new Annotation(null), paramArrayOfint1[b], paramArrayOfint1[b + 1]);
/*     */           
/* 501 */           attributedString.addAttribute(AttributedCharacterIterator.Attribute.READING, new Annotation(paramArrayOfString[b]), paramArrayOfint1[b], paramArrayOfint1[b + 1]);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 508 */         attributedString.addAttribute(AttributedCharacterIterator.Attribute.INPUT_METHOD_SEGMENT, new Annotation(null), 0, paramString
/* 509 */             .length());
/* 510 */         attributedString.addAttribute(AttributedCharacterIterator.Attribute.READING, new Annotation(""), 0, paramString
/* 511 */             .length());
/*     */       } 
/*     */ 
/*     */       
/* 515 */       if (paramArrayOfint2 != null && paramArrayOfbyte != null && paramArrayOfbyte.length != 0 && paramArrayOfint2.length == paramArrayOfbyte.length + 1 && paramArrayOfint2[0] == 0 && paramArrayOfint2[paramArrayOfbyte.length] == paramString
/*     */         
/* 517 */         .length()) {
/*     */         
/* 519 */         for (byte b = 0; b < paramArrayOfint2.length - 1; b++) {
/*     */           InputMethodHighlight inputMethodHighlight;
/* 521 */           switch (paramArrayOfbyte[b]) {
/*     */             case 1:
/* 523 */               inputMethodHighlight = InputMethodHighlight.SELECTED_CONVERTED_TEXT_HIGHLIGHT;
/*     */               break;
/*     */             case 2:
/* 526 */               inputMethodHighlight = InputMethodHighlight.UNSELECTED_CONVERTED_TEXT_HIGHLIGHT;
/*     */               break;
/*     */             case 3:
/* 529 */               inputMethodHighlight = InputMethodHighlight.SELECTED_RAW_TEXT_HIGHLIGHT;
/*     */               break;
/*     */ 
/*     */             
/*     */             default:
/* 534 */               inputMethodHighlight = InputMethodHighlight.UNSELECTED_RAW_TEXT_HIGHLIGHT;
/*     */               break;
/*     */           } 
/* 537 */           attributedString.addAttribute(TextAttribute.INPUT_METHOD_HIGHLIGHT, inputMethodHighlight, paramArrayOfint2[b], paramArrayOfint2[b + 1]);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 545 */         attributedString.addAttribute(TextAttribute.INPUT_METHOD_HIGHLIGHT, InputMethodHighlight.UNSELECTED_CONVERTED_TEXT_HIGHLIGHT, 0, paramString
/*     */             
/* 547 */             .length());
/*     */       } 
/*     */ 
/*     */       
/* 551 */       attributedCharacterIterator = attributedString.getIterator();
/*     */     } 
/*     */ 
/*     */     
/* 555 */     Component component = getClientComponent();
/* 556 */     if (component == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 565 */     InputMethodEvent inputMethodEvent = new InputMethodEvent(component, paramInt1, paramLong, attributedCharacterIterator, paramInt2, TextHitInfo.leading(paramInt3), TextHitInfo.leading(paramInt4));
/* 566 */     WToolkit.postEvent(WToolkit.targetToAppContext(component), inputMethodEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void inquireCandidatePosition() {
/* 571 */     Component component = getClientComponent();
/* 572 */     if (component == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 579 */     Runnable runnable = new Runnable()
/*     */       {
/*     */         public void run() {
/* 582 */           int i = 0;
/* 583 */           int j = 0;
/* 584 */           Component component = WInputMethod.this.getClientComponent();
/*     */           
/* 586 */           if (component != null) {
/* 587 */             if (!component.isShowing()) {
/*     */               return;
/*     */             }
/* 590 */             if (WInputMethod.this.haveActiveClient()) {
/* 591 */               Rectangle rectangle = WInputMethod.this.inputContext.getTextLocation(TextHitInfo.leading(0));
/* 592 */               i = rectangle.x;
/* 593 */               j = rectangle.y + rectangle.height;
/*     */             } else {
/* 595 */               Point point = component.getLocationOnScreen();
/* 596 */               Dimension dimension = component.getSize();
/* 597 */               i = point.x;
/* 598 */               j = point.y + dimension.height;
/*     */             } 
/*     */           } 
/*     */           
/* 602 */           WInputMethod.this.openCandidateWindow(WInputMethod.this.awtFocussedComponentPeer, i, j);
/*     */         }
/*     */       };
/* 605 */     WToolkit.postEvent(WToolkit.targetToAppContext(component), new InvocationEvent(component, runnable));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WComponentPeer getNearestNativePeer(Component paramComponent) {
/* 613 */     if (paramComponent == null) return null;
/*     */     
/* 615 */     ComponentPeer componentPeer = paramComponent.getPeer();
/* 616 */     if (componentPeer == null) return null;
/*     */     
/* 618 */     while (componentPeer instanceof java.awt.peer.LightweightPeer) {
/* 619 */       paramComponent = paramComponent.getParent();
/* 620 */       if (paramComponent == null) return null; 
/* 621 */       componentPeer = paramComponent.getPeer();
/* 622 */       if (componentPeer == null) return null;
/*     */     
/*     */     } 
/* 625 */     if (componentPeer instanceof WComponentPeer) {
/* 626 */       return (WComponentPeer)componentPeer;
/*     */     }
/* 628 */     return null;
/*     */   }
/*     */   
/*     */   private native int createNativeContext();
/*     */   
/*     */   private native void destroyNativeContext(int paramInt);
/*     */   
/*     */   private native void enableNativeIME(WComponentPeer paramWComponentPeer, int paramInt, boolean paramBoolean);
/*     */   
/*     */   private native void disableNativeIME(WComponentPeer paramWComponentPeer);
/*     */   
/*     */   private native void handleNativeIMEEvent(WComponentPeer paramWComponentPeer, AWTEvent paramAWTEvent);
/*     */   
/*     */   private native void endCompositionNative(int paramInt, boolean paramBoolean);
/*     */   
/*     */   private native void setConversionStatus(int paramInt1, int paramInt2);
/*     */   
/*     */   private native int getConversionStatus(int paramInt);
/*     */   
/*     */   private native void setOpenStatus(int paramInt, boolean paramBoolean);
/*     */   
/*     */   private native boolean getOpenStatus(int paramInt);
/*     */   
/*     */   private native void setStatusWindowVisible(WComponentPeer paramWComponentPeer, boolean paramBoolean);
/*     */   
/*     */   private native String getNativeIMMDescription();
/*     */   
/*     */   static native Locale getNativeLocale();
/*     */   
/*     */   static native boolean setNativeLocale(String paramString, boolean paramBoolean);
/*     */   
/*     */   private native void openCandidateWindow(WComponentPeer paramWComponentPeer, int paramInt1, int paramInt2);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WInputMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */