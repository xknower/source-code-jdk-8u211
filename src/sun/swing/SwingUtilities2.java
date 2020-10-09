/*      */ package sun.swing;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.FocusTraversalPolicy;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.font.LineBreakMeasurer;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.font.TextHitInfo;
/*      */ import java.awt.font.TextLayout;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.AttributedString;
/*      */ import java.text.BreakIterator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.ListModel;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.TreeModelEvent;
/*      */ import javax.swing.table.TableCellRenderer;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ import javax.swing.text.DefaultHighlighter;
/*      */ import javax.swing.text.Highlighter;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import javax.swing.tree.TreeModel;
/*      */ import javax.swing.tree.TreePath;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.font.FontDesignMetrics;
/*      */ import sun.font.FontUtilities;
/*      */ import sun.java2d.SunGraphicsEnvironment;
/*      */ import sun.print.ProxyPrintGraphics;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SwingUtilities2
/*      */ {
/*   82 */   public static final Object LAF_STATE_KEY = new StringBuffer("LookAndFeel State");
/*      */ 
/*      */   
/*   85 */   public static final Object MENU_SELECTION_MANAGER_LISTENER_KEY = new StringBuffer("MenuSelectionManager listener key");
/*      */ 
/*      */   
/*      */   private static LSBCacheEntry[] fontCache;
/*      */ 
/*      */   
/*      */   private static final int CACHE_SIZE = 6;
/*      */ 
/*      */   
/*      */   private static int nextIndex;
/*      */ 
/*      */   
/*      */   private static LSBCacheEntry searchKey;
/*      */ 
/*      */   
/*      */   private static final int MIN_CHAR_INDEX = 87;
/*      */ 
/*      */   
/*      */   private static final int MAX_CHAR_INDEX = 88;
/*      */ 
/*      */   
/*  106 */   public static final FontRenderContext DEFAULT_FRC = new FontRenderContext(null, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  114 */   public static final Object AA_TEXT_PROPERTY_KEY = new StringBuffer("AATextInfoPropertyKey");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String IMPLIED_CR = "CR";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  127 */   private static final StringBuilder SKIP_CLICK_COUNT = new StringBuilder("skipClickCount");
/*      */ 
/*      */   
/*      */   public static class AATextInfo
/*      */   {
/*      */     Object aaHint;
/*      */     Integer lcdContrastHint;
/*      */     FontRenderContext frc;
/*      */     
/*      */     private static AATextInfo getAATextInfoFromMap(Map param1Map) {
/*  137 */       Object object1 = param1Map.get(RenderingHints.KEY_TEXT_ANTIALIASING);
/*  138 */       Object object2 = param1Map.get(RenderingHints.KEY_TEXT_LCD_CONTRAST);
/*      */       
/*  140 */       if (object1 == null || object1 == RenderingHints.VALUE_TEXT_ANTIALIAS_OFF || object1 == RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT)
/*      */       {
/*      */         
/*  143 */         return null;
/*      */       }
/*  145 */       return new AATextInfo(object1, (Integer)object2);
/*      */     }
/*      */ 
/*      */     
/*      */     public static AATextInfo getAATextInfo(boolean param1Boolean) {
/*  150 */       SunToolkit.setAAFontSettingsCondition(param1Boolean);
/*  151 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  152 */       Object object = toolkit.getDesktopProperty("awt.font.desktophints");
/*  153 */       if (object instanceof Map) {
/*  154 */         return getAATextInfoFromMap((Map)object);
/*      */       }
/*  156 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AATextInfo(Object param1Object, Integer param1Integer) {
/*  170 */       if (param1Object == null) {
/*  171 */         throw new InternalError("null not allowed here");
/*      */       }
/*  173 */       if (param1Object == RenderingHints.VALUE_TEXT_ANTIALIAS_OFF || param1Object == RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT)
/*      */       {
/*  175 */         throw new InternalError("AA must be on");
/*      */       }
/*  177 */       this.aaHint = param1Object;
/*  178 */       this.lcdContrastHint = param1Integer;
/*  179 */       this.frc = new FontRenderContext(null, param1Object, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  188 */   public static final Object COMPONENT_UI_PROPERTY_KEY = new StringBuffer("ComponentUIPropertyKey");
/*      */ 
/*      */ 
/*      */   
/*  192 */   public static final StringUIClientPropertyKey BASICMENUITEMUI_MAX_TEXT_OFFSET = new StringUIClientPropertyKey("maxTextOffset");
/*      */ 
/*      */ 
/*      */   
/*  196 */   private static Field inputEvent_CanAccessSystemClipboard_Field = null;
/*      */   
/*      */   private static final String UntrustedClipboardAccess = "UNTRUSTED_CLIPBOARD_ACCESS_KEY";
/*      */   
/*      */   private static final int CHAR_BUFFER_SIZE = 100;
/*      */   
/*  202 */   private static final Object charsBufferLock = new Object();
/*  203 */   private static char[] charsBuffer = new char[100];
/*      */   
/*      */   static {
/*  206 */     fontCache = new LSBCacheEntry[6];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int syncCharsBuffer(String paramString) {
/*  213 */     int i = paramString.length();
/*  214 */     if (charsBuffer == null || charsBuffer.length < i) {
/*  215 */       charsBuffer = paramString.toCharArray();
/*      */     } else {
/*  217 */       paramString.getChars(0, i, charsBuffer, 0);
/*      */     } 
/*  219 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean isComplexLayout(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  232 */     return FontUtilities.isComplexText(paramArrayOfchar, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AATextInfo drawTextAntialiased(JComponent paramJComponent) {
/*  255 */     if (paramJComponent != null)
/*      */     {
/*  257 */       return (AATextInfo)paramJComponent.getClientProperty(AA_TEXT_PROPERTY_KEY);
/*      */     }
/*      */     
/*  260 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getLeftSideBearing(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString) {
/*  279 */     if (paramString == null || paramString.length() == 0) {
/*  280 */       return 0;
/*      */     }
/*  282 */     return getLeftSideBearing(paramJComponent, paramFontMetrics, paramString.charAt(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getLeftSideBearing(JComponent paramJComponent, FontMetrics paramFontMetrics, char paramChar) {
/*  295 */     char c = paramChar;
/*  296 */     if (c < 'X' && c >= 'W') {
/*  297 */       Object object = null;
/*      */       
/*  299 */       FontRenderContext fontRenderContext = getFontRenderContext(paramJComponent, paramFontMetrics);
/*  300 */       Font font = paramFontMetrics.getFont();
/*  301 */       synchronized (SwingUtilities2.class) {
/*  302 */         LSBCacheEntry lSBCacheEntry = null;
/*  303 */         if (searchKey == null) {
/*  304 */           searchKey = new LSBCacheEntry(fontRenderContext, font);
/*      */         } else {
/*  306 */           searchKey.reset(fontRenderContext, font);
/*      */         } 
/*      */         
/*  309 */         for (LSBCacheEntry lSBCacheEntry1 : fontCache) {
/*  310 */           if (searchKey.equals(lSBCacheEntry1)) {
/*  311 */             lSBCacheEntry = lSBCacheEntry1;
/*      */             break;
/*      */           } 
/*      */         } 
/*  315 */         if (lSBCacheEntry == null) {
/*      */           
/*  317 */           lSBCacheEntry = searchKey;
/*  318 */           fontCache[nextIndex] = searchKey;
/*  319 */           searchKey = null;
/*  320 */           nextIndex = (nextIndex + 1) % 6;
/*      */         } 
/*  322 */         return lSBCacheEntry.getLeftSideBearing(paramChar);
/*      */       } 
/*      */     } 
/*  325 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontMetrics getFontMetrics(JComponent paramJComponent, Graphics paramGraphics) {
/*  345 */     return getFontMetrics(paramJComponent, paramGraphics, paramGraphics.getFont());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontMetrics getFontMetrics(JComponent paramJComponent, Graphics paramGraphics, Font paramFont) {
/*  368 */     if (paramJComponent != null)
/*      */     {
/*      */ 
/*      */       
/*  372 */       return paramJComponent.getFontMetrics(paramFont);
/*      */     }
/*  374 */     return Toolkit.getDefaultToolkit().getFontMetrics(paramFont);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int stringWidth(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString) {
/*  387 */     if (paramString == null || paramString.equals("")) {
/*  388 */       return 0;
/*      */     }
/*      */     
/*  391 */     boolean bool = (paramJComponent != null && paramJComponent.getClientProperty(TextAttribute.NUMERIC_SHAPING) != null);
/*  392 */     if (bool) {
/*  393 */       synchronized (charsBufferLock) {
/*  394 */         int i = syncCharsBuffer(paramString);
/*  395 */         bool = isComplexLayout(charsBuffer, 0, i);
/*      */       } 
/*      */     }
/*  398 */     if (bool) {
/*  399 */       TextLayout textLayout = createTextLayout(paramJComponent, paramString, paramFontMetrics
/*  400 */           .getFont(), paramFontMetrics.getFontRenderContext());
/*  401 */       return (int)textLayout.getAdvance();
/*      */     } 
/*  403 */     return paramFontMetrics.stringWidth(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String clipStringIfNecessary(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString, int paramInt) {
/*  420 */     if (paramString == null || paramString.equals("")) {
/*  421 */       return "";
/*      */     }
/*  423 */     int i = stringWidth(paramJComponent, paramFontMetrics, paramString);
/*  424 */     if (i > paramInt) {
/*  425 */       return clipString(paramJComponent, paramFontMetrics, paramString, paramInt);
/*      */     }
/*  427 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String clipString(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString, int paramInt) {
/*      */     boolean bool;
/*  444 */     String str = "...";
/*  445 */     paramInt -= stringWidth(paramJComponent, paramFontMetrics, str);
/*  446 */     if (paramInt <= 0)
/*      */     {
/*  448 */       return str;
/*      */     }
/*      */ 
/*      */     
/*  452 */     synchronized (charsBufferLock) {
/*  453 */       int i = syncCharsBuffer(paramString);
/*      */       
/*  455 */       bool = isComplexLayout(charsBuffer, 0, i);
/*  456 */       if (!bool) {
/*  457 */         int j = 0;
/*  458 */         for (byte b = 0; b < i; b++) {
/*  459 */           j += paramFontMetrics.charWidth(charsBuffer[b]);
/*  460 */           if (j > paramInt) {
/*  461 */             paramString = paramString.substring(0, b);
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  467 */     if (bool) {
/*  468 */       AttributedString attributedString = new AttributedString(paramString);
/*  469 */       if (paramJComponent != null) {
/*  470 */         attributedString.addAttribute(TextAttribute.NUMERIC_SHAPING, paramJComponent
/*  471 */             .getClientProperty(TextAttribute.NUMERIC_SHAPING));
/*      */       }
/*      */ 
/*      */       
/*  475 */       LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(attributedString.getIterator(), BreakIterator.getCharacterInstance(), getFontRenderContext(paramJComponent, paramFontMetrics));
/*  476 */       paramString = paramString.substring(0, lineBreakMeasurer.nextOffset(paramInt));
/*      */     } 
/*      */     
/*  479 */     return paramString + str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawString(JComponent paramJComponent, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2) {
/*  499 */     if (paramString == null || paramString.length() <= 0) {
/*      */       return;
/*      */     }
/*  502 */     if (isPrinting(paramGraphics)) {
/*  503 */       Graphics2D graphics2D = getGraphics2D(paramGraphics);
/*  504 */       if (graphics2D != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  511 */         String str = trimTrailingSpaces(paramString);
/*  512 */         if (!str.isEmpty()) {
/*      */           
/*  514 */           float f = (float)graphics2D.getFont().getStringBounds(str, DEFAULT_FRC).getWidth();
/*  515 */           TextLayout textLayout = createTextLayout(paramJComponent, paramString, graphics2D.getFont(), graphics2D
/*  516 */               .getFontRenderContext());
/*      */           
/*  518 */           textLayout = textLayout.getJustifiedLayout(f);
/*      */           
/*  520 */           Color color = graphics2D.getColor();
/*  521 */           if (color instanceof PrintColorUIResource) {
/*  522 */             graphics2D.setColor(((PrintColorUIResource)color).getPrintColor());
/*      */           }
/*      */           
/*  525 */           textLayout.draw(graphics2D, paramInt1, paramInt2);
/*      */           
/*  527 */           graphics2D.setColor(color);
/*      */         } 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  535 */     if (paramGraphics instanceof Graphics2D) {
/*  536 */       AATextInfo aATextInfo = drawTextAntialiased(paramJComponent);
/*  537 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*      */ 
/*      */       
/*  540 */       boolean bool = (paramJComponent != null && paramJComponent.getClientProperty(TextAttribute.NUMERIC_SHAPING) != null);
/*      */       
/*  542 */       if (bool) {
/*  543 */         synchronized (charsBufferLock) {
/*  544 */           int i = syncCharsBuffer(paramString);
/*  545 */           bool = isComplexLayout(charsBuffer, 0, i);
/*      */         } 
/*      */       }
/*      */       
/*  549 */       if (aATextInfo != null) {
/*  550 */         Object object1 = null;
/*  551 */         Object object2 = graphics2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
/*  552 */         if (aATextInfo.aaHint != object2) {
/*  553 */           graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, aATextInfo.aaHint);
/*      */         } else {
/*  555 */           object2 = null;
/*      */         } 
/*  557 */         if (aATextInfo.lcdContrastHint != null) {
/*  558 */           object1 = graphics2D.getRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST);
/*  559 */           if (aATextInfo.lcdContrastHint.equals(object1)) {
/*  560 */             object1 = null;
/*      */           } else {
/*  562 */             graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, aATextInfo.lcdContrastHint);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  567 */         if (bool) {
/*  568 */           TextLayout textLayout = createTextLayout(paramJComponent, paramString, graphics2D.getFont(), graphics2D
/*  569 */               .getFontRenderContext());
/*  570 */           textLayout.draw(graphics2D, paramInt1, paramInt2);
/*      */         } else {
/*  572 */           paramGraphics.drawString(paramString, paramInt1, paramInt2);
/*      */         } 
/*      */         
/*  575 */         if (object2 != null) {
/*  576 */           graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, object2);
/*      */         }
/*  578 */         if (object1 != null) {
/*  579 */           graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, object1);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  585 */       if (bool) {
/*  586 */         TextLayout textLayout = createTextLayout(paramJComponent, paramString, graphics2D.getFont(), graphics2D
/*  587 */             .getFontRenderContext());
/*  588 */         textLayout.draw(graphics2D, paramInt1, paramInt2);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  593 */     paramGraphics.drawString(paramString, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawStringUnderlineCharAt(JComponent paramJComponent, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  609 */     if (paramString == null || paramString.length() <= 0) {
/*      */       return;
/*      */     }
/*  612 */     drawString(paramJComponent, paramGraphics, paramString, paramInt2, paramInt3);
/*  613 */     int i = paramString.length();
/*  614 */     if (paramInt1 >= 0 && paramInt1 < i) {
/*  615 */       int j = paramInt3;
/*  616 */       boolean bool = true;
/*  617 */       int k = 0;
/*  618 */       int m = 0;
/*  619 */       boolean bool1 = isPrinting(paramGraphics);
/*  620 */       boolean bool2 = bool1;
/*  621 */       if (!bool2) {
/*  622 */         synchronized (charsBufferLock) {
/*  623 */           syncCharsBuffer(paramString);
/*      */           
/*  625 */           bool2 = isComplexLayout(charsBuffer, 0, i);
/*      */         } 
/*      */       }
/*  628 */       if (!bool2) {
/*  629 */         FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/*      */         
/*  631 */         k = paramInt2 + stringWidth(paramJComponent, fontMetrics, paramString
/*  632 */             .substring(0, paramInt1));
/*  633 */         m = fontMetrics.charWidth(paramString
/*  634 */             .charAt(paramInt1));
/*      */       } else {
/*  636 */         Graphics2D graphics2D = getGraphics2D(paramGraphics);
/*  637 */         if (graphics2D != null) {
/*      */           
/*  639 */           TextLayout textLayout = createTextLayout(paramJComponent, paramString, graphics2D.getFont(), graphics2D
/*  640 */               .getFontRenderContext());
/*  641 */           if (bool1) {
/*      */             
/*  643 */             float f = (float)graphics2D.getFont().getStringBounds(paramString, DEFAULT_FRC).getWidth();
/*  644 */             textLayout = textLayout.getJustifiedLayout(f);
/*      */           } 
/*      */           
/*  647 */           TextHitInfo textHitInfo1 = TextHitInfo.leading(paramInt1);
/*      */           
/*  649 */           TextHitInfo textHitInfo2 = TextHitInfo.trailing(paramInt1);
/*      */           
/*  651 */           Shape shape = textLayout.getVisualHighlightShape(textHitInfo1, textHitInfo2);
/*  652 */           Rectangle rectangle = shape.getBounds();
/*  653 */           k = paramInt2 + rectangle.x;
/*  654 */           m = rectangle.width;
/*      */         } 
/*      */       } 
/*  657 */       paramGraphics.fillRect(k, j + 1, m, bool);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loc2IndexFileList(JList paramJList, Point paramPoint) {
/*  671 */     int i = paramJList.locationToIndex(paramPoint);
/*  672 */     if (i != -1) {
/*  673 */       Object object = paramJList.getClientProperty("List.isFileList");
/*  674 */       if (object instanceof Boolean && ((Boolean)object).booleanValue() && 
/*  675 */         !pointIsInActualBounds(paramJList, i, paramPoint)) {
/*  676 */         i = -1;
/*      */       }
/*      */     } 
/*  679 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean pointIsInActualBounds(JList paramJList, int paramInt, Point paramPoint) {
/*  689 */     ListCellRenderer listCellRenderer = paramJList.getCellRenderer();
/*  690 */     ListModel<Object> listModel = paramJList.getModel();
/*  691 */     Object object = listModel.getElementAt(paramInt);
/*  692 */     Component component = listCellRenderer.getListCellRendererComponent(paramJList, object, paramInt, false, false);
/*      */     
/*  694 */     Dimension dimension = component.getPreferredSize();
/*  695 */     Rectangle rectangle = paramJList.getCellBounds(paramInt, paramInt);
/*  696 */     if (!component.getComponentOrientation().isLeftToRight()) {
/*  697 */       rectangle.x += rectangle.width - dimension.width;
/*      */     }
/*  699 */     rectangle.width = dimension.width;
/*      */     
/*  701 */     return rectangle.contains(paramPoint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean pointOutsidePrefSize(JTable paramJTable, int paramInt1, int paramInt2, Point paramPoint) {
/*  713 */     if (paramJTable.convertColumnIndexToModel(paramInt2) != 0 || paramInt1 == -1) {
/*  714 */       return true;
/*      */     }
/*  716 */     TableCellRenderer tableCellRenderer = paramJTable.getCellRenderer(paramInt1, paramInt2);
/*  717 */     Object object = paramJTable.getValueAt(paramInt1, paramInt2);
/*  718 */     Component component = tableCellRenderer.getTableCellRendererComponent(paramJTable, object, false, false, paramInt1, paramInt2);
/*      */     
/*  720 */     Dimension dimension = component.getPreferredSize();
/*  721 */     Rectangle rectangle = paramJTable.getCellRect(paramInt1, paramInt2, false);
/*  722 */     rectangle.width = dimension.width;
/*  723 */     rectangle.height = dimension.height;
/*      */ 
/*      */ 
/*      */     
/*  727 */     assert paramPoint.x >= rectangle.x && paramPoint.y >= rectangle.y;
/*  728 */     return (paramPoint.x > rectangle.x + rectangle.width || paramPoint.y > rectangle.y + rectangle.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setLeadAnchorWithoutSelection(ListSelectionModel paramListSelectionModel, int paramInt1, int paramInt2) {
/*  737 */     if (paramInt2 == -1) {
/*  738 */       paramInt2 = paramInt1;
/*      */     }
/*  740 */     if (paramInt1 == -1) {
/*  741 */       paramListSelectionModel.setAnchorSelectionIndex(-1);
/*  742 */       paramListSelectionModel.setLeadSelectionIndex(-1);
/*      */     } else {
/*  744 */       if (paramListSelectionModel.isSelectedIndex(paramInt1)) {
/*  745 */         paramListSelectionModel.addSelectionInterval(paramInt1, paramInt1);
/*      */       } else {
/*  747 */         paramListSelectionModel.removeSelectionInterval(paramInt1, paramInt1);
/*      */       } 
/*  749 */       paramListSelectionModel.setAnchorSelectionIndex(paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean shouldIgnore(MouseEvent paramMouseEvent, JComponent paramJComponent) {
/*  759 */     return (paramJComponent == null || !paramJComponent.isEnabled() || 
/*  760 */       !SwingUtilities.isLeftMouseButton(paramMouseEvent) || paramMouseEvent
/*  761 */       .isConsumed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void adjustFocus(JComponent paramJComponent) {
/*  769 */     if (!paramJComponent.hasFocus() && paramJComponent.isRequestFocusEnabled()) {
/*  770 */       paramJComponent.requestFocus();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int drawChars(JComponent paramJComponent, Graphics paramGraphics, char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  786 */     if (paramInt2 <= 0) {
/*  787 */       return paramInt3;
/*      */     }
/*  789 */     int i = paramInt3 + getFontMetrics(paramJComponent, paramGraphics).charsWidth(paramArrayOfchar, paramInt1, paramInt2);
/*  790 */     if (isPrinting(paramGraphics)) {
/*  791 */       Graphics2D graphics2D = getGraphics2D(paramGraphics);
/*  792 */       if (graphics2D != null) {
/*      */         
/*  794 */         FontRenderContext fontRenderContext1 = graphics2D.getFontRenderContext();
/*  795 */         FontRenderContext fontRenderContext2 = getFontRenderContext(paramJComponent);
/*  796 */         if (fontRenderContext2 != null && 
/*      */           
/*  798 */           !isFontRenderContextPrintCompatible(fontRenderContext1, fontRenderContext2)) {
/*      */           
/*  800 */           String str1 = new String(paramArrayOfchar, paramInt1, paramInt2);
/*  801 */           TextLayout textLayout = new TextLayout(str1, graphics2D.getFont(), fontRenderContext1);
/*      */           
/*  803 */           String str2 = trimTrailingSpaces(str1);
/*  804 */           if (!str2.isEmpty()) {
/*      */             
/*  806 */             float f = (float)graphics2D.getFont().getStringBounds(str2, fontRenderContext2).getWidth();
/*  807 */             textLayout = textLayout.getJustifiedLayout(f);
/*      */ 
/*      */             
/*  810 */             Color color = graphics2D.getColor();
/*  811 */             if (color instanceof PrintColorUIResource) {
/*  812 */               graphics2D.setColor(((PrintColorUIResource)color).getPrintColor());
/*      */             }
/*      */             
/*  815 */             textLayout.draw(graphics2D, paramInt3, paramInt4);
/*      */             
/*  817 */             graphics2D.setColor(color);
/*      */           } 
/*      */           
/*  820 */           return i;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  826 */     AATextInfo aATextInfo = drawTextAntialiased(paramJComponent);
/*  827 */     if (aATextInfo != null && paramGraphics instanceof Graphics2D) {
/*  828 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*      */       
/*  830 */       Object object1 = null;
/*  831 */       Object object2 = graphics2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
/*  832 */       if (aATextInfo.aaHint != null && aATextInfo.aaHint != object2) {
/*  833 */         graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, aATextInfo.aaHint);
/*      */       } else {
/*  835 */         object2 = null;
/*      */       } 
/*  837 */       if (aATextInfo.lcdContrastHint != null) {
/*  838 */         object1 = graphics2D.getRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST);
/*  839 */         if (aATextInfo.lcdContrastHint.equals(object1)) {
/*  840 */           object1 = null;
/*      */         } else {
/*  842 */           graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, aATextInfo.lcdContrastHint);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  847 */       paramGraphics.drawChars(paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       
/*  849 */       if (object2 != null) {
/*  850 */         graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, object2);
/*      */       }
/*  852 */       if (object1 != null) {
/*  853 */         graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, object1);
/*      */       }
/*      */     } else {
/*      */       
/*  857 */       paramGraphics.drawChars(paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } 
/*  859 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float drawString(JComponent paramJComponent, Graphics paramGraphics, AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2) {
/*      */     float f;
/*  872 */     boolean bool = isPrinting(paramGraphics);
/*  873 */     Color color = paramGraphics.getColor();
/*      */     
/*  875 */     if (bool)
/*      */     {
/*  877 */       if (color instanceof PrintColorUIResource) {
/*  878 */         paramGraphics.setColor(((PrintColorUIResource)color).getPrintColor());
/*      */       }
/*      */     }
/*      */     
/*  882 */     Graphics2D graphics2D = getGraphics2D(paramGraphics);
/*  883 */     if (graphics2D == null) {
/*  884 */       paramGraphics.drawString(paramAttributedCharacterIterator, paramInt1, paramInt2);
/*      */       
/*  886 */       f = paramInt1;
/*      */     } else {
/*      */       FontRenderContext fontRenderContext;
/*      */       TextLayout textLayout;
/*  890 */       if (bool) {
/*  891 */         fontRenderContext = getFontRenderContext(paramJComponent);
/*  892 */         if (fontRenderContext.isAntiAliased() || fontRenderContext.usesFractionalMetrics()) {
/*  893 */           fontRenderContext = new FontRenderContext(fontRenderContext.getTransform(), false, false);
/*      */         }
/*  895 */       } else if ((fontRenderContext = getFRCProperty(paramJComponent)) == null) {
/*      */ 
/*      */         
/*  898 */         fontRenderContext = graphics2D.getFontRenderContext();
/*      */       } 
/*      */       
/*  901 */       if (bool) {
/*  902 */         FontRenderContext fontRenderContext1 = graphics2D.getFontRenderContext();
/*  903 */         if (!isFontRenderContextPrintCompatible(fontRenderContext, fontRenderContext1)) {
/*  904 */           textLayout = new TextLayout(paramAttributedCharacterIterator, fontRenderContext1);
/*      */           
/*  906 */           AttributedCharacterIterator attributedCharacterIterator = getTrimmedTrailingSpacesIterator(paramAttributedCharacterIterator);
/*  907 */           if (attributedCharacterIterator != null) {
/*      */             
/*  909 */             float f1 = (new TextLayout(attributedCharacterIterator, fontRenderContext)).getAdvance();
/*  910 */             textLayout = textLayout.getJustifiedLayout(f1);
/*      */           } 
/*      */         } else {
/*  913 */           textLayout = new TextLayout(paramAttributedCharacterIterator, fontRenderContext);
/*      */         } 
/*      */       } else {
/*  916 */         textLayout = new TextLayout(paramAttributedCharacterIterator, fontRenderContext);
/*      */       } 
/*  918 */       textLayout.draw(graphics2D, paramInt1, paramInt2);
/*  919 */       f = textLayout.getAdvance();
/*      */     } 
/*      */     
/*  922 */     if (bool) {
/*  923 */       paramGraphics.setColor(color);
/*      */     }
/*      */     
/*  926 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawVLine(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3) {
/*  941 */     if (paramInt3 < paramInt2) {
/*  942 */       int i = paramInt3;
/*  943 */       paramInt3 = paramInt2;
/*  944 */       paramInt2 = i;
/*      */     } 
/*  946 */     paramGraphics.fillRect(paramInt1, paramInt2, 1, paramInt3 - paramInt2 + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawHLine(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3) {
/*  961 */     if (paramInt2 < paramInt1) {
/*  962 */       int i = paramInt2;
/*  963 */       paramInt2 = paramInt1;
/*  964 */       paramInt1 = i;
/*      */     } 
/*  966 */     paramGraphics.fillRect(paramInt1, paramInt3, paramInt2 - paramInt1 + 1, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawRect(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  986 */     if (paramInt3 < 0 || paramInt4 < 0) {
/*      */       return;
/*      */     }
/*      */     
/*  990 */     if (paramInt4 == 0 || paramInt3 == 0) {
/*  991 */       paramGraphics.fillRect(paramInt1, paramInt2, paramInt3 + 1, paramInt4 + 1);
/*      */     } else {
/*  993 */       paramGraphics.fillRect(paramInt1, paramInt2, paramInt3, 1);
/*  994 */       paramGraphics.fillRect(paramInt1 + paramInt3, paramInt2, 1, paramInt4);
/*  995 */       paramGraphics.fillRect(paramInt1 + 1, paramInt2 + paramInt4, paramInt3, 1);
/*  996 */       paramGraphics.fillRect(paramInt1, paramInt2 + 1, 1, paramInt4);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextLayout createTextLayout(JComponent paramJComponent, String paramString, Font paramFont, FontRenderContext paramFontRenderContext) {
/* 1003 */     Object object = (paramJComponent == null) ? null : paramJComponent.getClientProperty(TextAttribute.NUMERIC_SHAPING);
/* 1004 */     if (object == null) {
/* 1005 */       return new TextLayout(paramString, paramFont, paramFontRenderContext);
/*      */     }
/* 1007 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 1008 */     hashMap.put(TextAttribute.FONT, paramFont);
/* 1009 */     hashMap.put(TextAttribute.NUMERIC_SHAPING, object);
/* 1010 */     return new TextLayout(paramString, (Map)hashMap, paramFontRenderContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFontRenderContextPrintCompatible(FontRenderContext paramFontRenderContext1, FontRenderContext paramFontRenderContext2) {
/* 1027 */     if (paramFontRenderContext1 == paramFontRenderContext2) {
/* 1028 */       return true;
/*      */     }
/*      */     
/* 1031 */     if (paramFontRenderContext1 == null || paramFontRenderContext2 == null) {
/* 1032 */       return false;
/*      */     }
/*      */     
/* 1035 */     if (paramFontRenderContext1.getFractionalMetricsHint() != paramFontRenderContext2
/* 1036 */       .getFractionalMetricsHint()) {
/* 1037 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1041 */     if (!paramFontRenderContext1.isTransformed() && !paramFontRenderContext2.isTransformed()) {
/* 1042 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1049 */     double[] arrayOfDouble1 = new double[4];
/* 1050 */     double[] arrayOfDouble2 = new double[4];
/* 1051 */     paramFontRenderContext1.getTransform().getMatrix(arrayOfDouble1);
/* 1052 */     paramFontRenderContext2.getTransform().getMatrix(arrayOfDouble2);
/* 1053 */     return (arrayOfDouble1[0] == arrayOfDouble2[0] && arrayOfDouble1[1] == arrayOfDouble2[1] && arrayOfDouble1[2] == arrayOfDouble2[2] && arrayOfDouble1[3] == arrayOfDouble2[3]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Graphics2D getGraphics2D(Graphics paramGraphics) {
/* 1065 */     if (paramGraphics instanceof Graphics2D)
/* 1066 */       return (Graphics2D)paramGraphics; 
/* 1067 */     if (paramGraphics instanceof ProxyPrintGraphics) {
/* 1068 */       return (Graphics2D)((ProxyPrintGraphics)paramGraphics).getGraphics();
/*      */     }
/* 1070 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontRenderContext getFontRenderContext(Component paramComponent) {
/* 1083 */     assert paramComponent != null;
/* 1084 */     if (paramComponent == null) {
/* 1085 */       return DEFAULT_FRC;
/*      */     }
/* 1087 */     return paramComponent.getFontMetrics(paramComponent.getFont()).getFontRenderContext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FontRenderContext getFontRenderContext(Component paramComponent, FontMetrics paramFontMetrics) {
/* 1097 */     assert paramFontMetrics != null || paramComponent != null;
/* 1098 */     return (paramFontMetrics != null) ? paramFontMetrics.getFontRenderContext() : 
/* 1099 */       getFontRenderContext(paramComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontMetrics getFontMetrics(JComponent paramJComponent, Font paramFont) {
/* 1109 */     FontRenderContext fontRenderContext = getFRCProperty(paramJComponent);
/* 1110 */     if (fontRenderContext == null) {
/* 1111 */       fontRenderContext = DEFAULT_FRC;
/*      */     }
/* 1113 */     return FontDesignMetrics.getMetrics(paramFont, fontRenderContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FontRenderContext getFRCProperty(JComponent paramJComponent) {
/* 1121 */     if (paramJComponent != null) {
/*      */       
/* 1123 */       AATextInfo aATextInfo = (AATextInfo)paramJComponent.getClientProperty(AA_TEXT_PROPERTY_KEY);
/* 1124 */       if (aATextInfo != null) {
/* 1125 */         return aATextInfo.frc;
/*      */       }
/*      */     } 
/* 1128 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isPrinting(Graphics paramGraphics) {
/* 1136 */     return (paramGraphics instanceof java.awt.print.PrinterGraphics || paramGraphics instanceof java.awt.PrintGraphics);
/*      */   }
/*      */   
/*      */   private static String trimTrailingSpaces(String paramString) {
/* 1140 */     int i = paramString.length() - 1;
/* 1141 */     while (i >= 0 && Character.isWhitespace(paramString.charAt(i))) {
/* 1142 */       i--;
/*      */     }
/* 1144 */     return paramString.substring(0, i + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   private static AttributedCharacterIterator getTrimmedTrailingSpacesIterator(AttributedCharacterIterator paramAttributedCharacterIterator) {
/* 1149 */     int i = paramAttributedCharacterIterator.getIndex();
/*      */     
/* 1151 */     char c = paramAttributedCharacterIterator.last();
/* 1152 */     while (c != Character.MAX_VALUE && Character.isWhitespace(c)) {
/* 1153 */       c = paramAttributedCharacterIterator.previous();
/*      */     }
/*      */     
/* 1156 */     if (c != Character.MAX_VALUE) {
/* 1157 */       int j = paramAttributedCharacterIterator.getIndex();
/*      */       
/* 1159 */       if (j == paramAttributedCharacterIterator.getEndIndex() - 1) {
/* 1160 */         paramAttributedCharacterIterator.setIndex(i);
/* 1161 */         return paramAttributedCharacterIterator;
/*      */       } 
/*      */       
/* 1164 */       AttributedString attributedString = new AttributedString(paramAttributedCharacterIterator, paramAttributedCharacterIterator.getBeginIndex(), j + 1);
/* 1165 */       return attributedString.getIterator();
/*      */     } 
/*      */     
/* 1168 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean useSelectedTextColor(Highlighter.Highlight paramHighlight, JTextComponent paramJTextComponent) {
/* 1184 */     Highlighter.HighlightPainter highlightPainter = paramHighlight.getPainter();
/* 1185 */     String str = highlightPainter.getClass().getName();
/* 1186 */     if (str.indexOf("javax.swing.text.DefaultHighlighter") != 0 && str
/* 1187 */       .indexOf("com.sun.java.swing.plaf.windows.WindowsTextUI") != 0) {
/* 1188 */       return false;
/*      */     }
/*      */     try {
/* 1191 */       DefaultHighlighter.DefaultHighlightPainter defaultHighlightPainter = (DefaultHighlighter.DefaultHighlightPainter)highlightPainter;
/*      */       
/* 1193 */       if (defaultHighlightPainter.getColor() != null && 
/* 1194 */         !defaultHighlightPainter.getColor().equals(paramJTextComponent.getSelectionColor())) {
/* 1195 */         return false;
/*      */       }
/* 1197 */     } catch (ClassCastException classCastException) {
/* 1198 */       return false;
/*      */     } 
/* 1200 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class LSBCacheEntry
/*      */   {
/*      */     private static final byte UNSET = 127;
/*      */ 
/*      */ 
/*      */     
/* 1213 */     private static final char[] oneChar = new char[1];
/*      */     
/*      */     private byte[] lsbCache;
/*      */     
/*      */     private Font font;
/*      */     private FontRenderContext frc;
/*      */     
/*      */     public LSBCacheEntry(FontRenderContext param1FontRenderContext, Font param1Font) {
/* 1221 */       this.lsbCache = new byte[1];
/* 1222 */       reset(param1FontRenderContext, param1Font);
/*      */     }
/*      */ 
/*      */     
/*      */     public void reset(FontRenderContext param1FontRenderContext, Font param1Font) {
/* 1227 */       this.font = param1Font;
/* 1228 */       this.frc = param1FontRenderContext;
/* 1229 */       for (int i = this.lsbCache.length - 1; i >= 0; i--) {
/* 1230 */         this.lsbCache[i] = Byte.MAX_VALUE;
/*      */       }
/*      */     }
/*      */     
/*      */     public int getLeftSideBearing(char param1Char) {
/* 1235 */       int i = param1Char - 87;
/* 1236 */       assert i >= 0 && i < 1;
/* 1237 */       byte b = this.lsbCache[i];
/* 1238 */       if (b == Byte.MAX_VALUE) {
/* 1239 */         oneChar[0] = param1Char;
/* 1240 */         GlyphVector glyphVector = this.font.createGlyphVector(this.frc, oneChar);
/* 1241 */         b = (byte)(glyphVector.getGlyphPixelBounds(0, this.frc, 0.0F, 0.0F)).x;
/* 1242 */         if (b < 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1249 */           Object object = this.frc.getAntiAliasingHint();
/* 1250 */           if (object == RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB || object == RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR)
/*      */           {
/* 1252 */             b = (byte)(b + 1);
/*      */           }
/*      */         } 
/* 1255 */         this.lsbCache[i] = b;
/*      */       } 
/* 1257 */       return b;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1263 */       if (param1Object == this) {
/* 1264 */         return true;
/*      */       }
/* 1266 */       if (!(param1Object instanceof LSBCacheEntry)) {
/* 1267 */         return false;
/*      */       }
/* 1269 */       LSBCacheEntry lSBCacheEntry = (LSBCacheEntry)param1Object;
/* 1270 */       return (this.font.equals(lSBCacheEntry.font) && this.frc
/* 1271 */         .equals(lSBCacheEntry.frc));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1275 */       int i = 17;
/* 1276 */       if (this.font != null) {
/* 1277 */         i = 37 * i + this.font.hashCode();
/*      */       }
/* 1279 */       if (this.frc != null) {
/* 1280 */         i = 37 * i + this.frc.hashCode();
/*      */       }
/* 1282 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean canAccessSystemClipboard() {
/* 1302 */     boolean bool = false;
/* 1303 */     if (!GraphicsEnvironment.isHeadless()) {
/* 1304 */       SecurityManager securityManager = System.getSecurityManager();
/* 1305 */       if (securityManager == null) {
/* 1306 */         bool = true;
/*      */       } else {
/*      */         try {
/* 1309 */           securityManager.checkPermission(SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION);
/* 1310 */           bool = true;
/* 1311 */         } catch (SecurityException securityException) {}
/*      */         
/* 1313 */         if (bool && !isTrustedContext()) {
/* 1314 */           bool = canCurrentEventAccessSystemClipboard(true);
/*      */         }
/*      */       } 
/*      */     } 
/* 1318 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean canCurrentEventAccessSystemClipboard() {
/* 1325 */     return (isTrustedContext() || 
/* 1326 */       canCurrentEventAccessSystemClipboard(false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean canEventAccessSystemClipboard(AWTEvent paramAWTEvent) {
/* 1336 */     return (isTrustedContext() || 
/* 1337 */       canEventAccessSystemClipboard(paramAWTEvent, false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized boolean inputEvent_canAccessSystemClipboard(InputEvent paramInputEvent) {
/* 1346 */     if (inputEvent_CanAccessSystemClipboard_Field == null)
/*      */     {
/* 1348 */       inputEvent_CanAccessSystemClipboard_Field = AccessController.<Field>doPrivileged(new PrivilegedAction<Field>()
/*      */           {
/*      */             public Field run()
/*      */             {
/*      */               
/* 1353 */               try { Field field = InputEvent.class.getDeclaredField("canAccessSystemClipboard");
/* 1354 */                 field.setAccessible(true);
/* 1355 */                 return field; }
/* 1356 */               catch (SecurityException securityException) {  }
/* 1357 */               catch (NoSuchFieldException noSuchFieldException) {}
/*      */               
/* 1359 */               return null;
/*      */             }
/*      */           });
/*      */     }
/* 1363 */     if (inputEvent_CanAccessSystemClipboard_Field == null) {
/* 1364 */       return false;
/*      */     }
/* 1366 */     boolean bool = false;
/*      */     
/*      */     try {
/* 1369 */       bool = inputEvent_CanAccessSystemClipboard_Field.getBoolean(paramInputEvent);
/* 1370 */     } catch (IllegalAccessException illegalAccessException) {}
/*      */     
/* 1372 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAccessClipboardGesture(InputEvent paramInputEvent) {
/* 1383 */     boolean bool = false;
/* 1384 */     if (paramInputEvent instanceof KeyEvent) {
/* 1385 */       KeyEvent keyEvent = (KeyEvent)paramInputEvent;
/* 1386 */       int i = keyEvent.getKeyCode();
/* 1387 */       int j = keyEvent.getModifiers();
/* 1388 */       switch (i) {
/*      */         case 67:
/*      */         case 86:
/*      */         case 88:
/* 1392 */           bool = (j == 2) ? true : false;
/*      */           break;
/*      */         case 155:
/* 1395 */           bool = (j == 2 || j == 1) ? true : false;
/*      */           break;
/*      */         
/*      */         case 65485:
/*      */         case 65487:
/*      */         case 65489:
/* 1401 */           bool = true;
/*      */           break;
/*      */         case 127:
/* 1404 */           bool = (j == 1) ? true : false;
/*      */           break;
/*      */       } 
/*      */     } 
/* 1408 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean canEventAccessSystemClipboard(AWTEvent paramAWTEvent, boolean paramBoolean) {
/* 1421 */     if (EventQueue.isDispatchThread()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1426 */       if (paramAWTEvent instanceof InputEvent && (!paramBoolean || 
/* 1427 */         isAccessClipboardGesture((InputEvent)paramAWTEvent))) {
/* 1428 */         return inputEvent_canAccessSystemClipboard((InputEvent)paramAWTEvent);
/*      */       }
/* 1430 */       return false;
/*      */     } 
/*      */     
/* 1433 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkAccess(int paramInt) {
/* 1444 */     if (System.getSecurityManager() != null && 
/* 1445 */       !Modifier.isPublic(paramInt)) {
/* 1446 */       throw new SecurityException("Resource is not accessible");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean canCurrentEventAccessSystemClipboard(boolean paramBoolean) {
/* 1459 */     AWTEvent aWTEvent = EventQueue.getCurrentEvent();
/* 1460 */     return canEventAccessSystemClipboard(aWTEvent, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isTrustedContext() {
/* 1469 */     return (System.getSecurityManager() == null || 
/* 1470 */       AppContext.getAppContext()
/* 1471 */       .get("UNTRUSTED_CLIPBOARD_ACCESS_KEY") == null);
/*      */   }
/*      */   
/*      */   public static String displayPropertiesToCSS(Font paramFont, Color paramColor) {
/* 1475 */     StringBuffer stringBuffer = new StringBuffer("body {");
/* 1476 */     if (paramFont != null) {
/* 1477 */       stringBuffer.append(" font-family: ");
/* 1478 */       stringBuffer.append(paramFont.getFamily());
/* 1479 */       stringBuffer.append(" ; ");
/* 1480 */       stringBuffer.append(" font-size: ");
/* 1481 */       stringBuffer.append(paramFont.getSize());
/* 1482 */       stringBuffer.append("pt ;");
/* 1483 */       if (paramFont.isBold()) {
/* 1484 */         stringBuffer.append(" font-weight: 700 ; ");
/*      */       }
/* 1486 */       if (paramFont.isItalic()) {
/* 1487 */         stringBuffer.append(" font-style: italic ; ");
/*      */       }
/*      */     } 
/* 1490 */     if (paramColor != null) {
/* 1491 */       stringBuffer.append(" color: #");
/* 1492 */       if (paramColor.getRed() < 16) {
/* 1493 */         stringBuffer.append('0');
/*      */       }
/* 1495 */       stringBuffer.append(Integer.toHexString(paramColor.getRed()));
/* 1496 */       if (paramColor.getGreen() < 16) {
/* 1497 */         stringBuffer.append('0');
/*      */       }
/* 1499 */       stringBuffer.append(Integer.toHexString(paramColor.getGreen()));
/* 1500 */       if (paramColor.getBlue() < 16) {
/* 1501 */         stringBuffer.append('0');
/*      */       }
/* 1503 */       stringBuffer.append(Integer.toHexString(paramColor.getBlue()));
/* 1504 */       stringBuffer.append(" ; ");
/*      */     } 
/* 1506 */     stringBuffer.append(" }");
/* 1507 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object makeIcon(final Class<?> baseClass, final Class<?> rootClass, final String imageFile) {
/* 1531 */     return new UIDefaults.LazyValue()
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public Object createValue(UIDefaults param1UIDefaults)
/*      */         {
/* 1541 */           byte[] arrayOfByte = AccessController.<byte[]>doPrivileged((PrivilegedAction)new PrivilegedAction<byte[]>()
/*      */               {
/*      */                 public byte[] run() {
/*      */                   try {
/* 1545 */                     InputStream inputStream = null;
/* 1546 */                     Class clazz = baseClass;
/*      */                     
/* 1548 */                     while (clazz != null) {
/* 1549 */                       inputStream = clazz.getResourceAsStream(imageFile);
/*      */                       
/* 1551 */                       if (inputStream != null || clazz == rootClass) {
/*      */                         break;
/*      */                       }
/*      */                       
/* 1555 */                       clazz = clazz.getSuperclass();
/*      */                     } 
/*      */                     
/* 1558 */                     if (inputStream == null) {
/* 1559 */                       return null;
/*      */                     }
/*      */                     
/* 1562 */                     BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
/*      */                     
/* 1564 */                     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
/*      */                     
/* 1566 */                     byte[] arrayOfByte = new byte[1024];
/*      */                     int i;
/* 1568 */                     while ((i = bufferedInputStream.read(arrayOfByte)) > 0) {
/* 1569 */                       byteArrayOutputStream.write(arrayOfByte, 0, i);
/*      */                     }
/* 1571 */                     bufferedInputStream.close();
/* 1572 */                     byteArrayOutputStream.flush();
/* 1573 */                     return byteArrayOutputStream.toByteArray();
/* 1574 */                   } catch (IOException iOException) {
/* 1575 */                     System.err.println(iOException.toString());
/*      */                     
/* 1577 */                     return null;
/*      */                   } 
/*      */                 }
/*      */               });
/* 1581 */           if (arrayOfByte == null) {
/* 1582 */             return null;
/*      */           }
/* 1584 */           if (arrayOfByte.length == 0) {
/* 1585 */             System.err.println("warning: " + imageFile + " is zero-length");
/*      */             
/* 1587 */             return null;
/*      */           } 
/*      */           
/* 1590 */           return new ImageIconUIResource(arrayOfByte);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLocalDisplay() {
/*      */     boolean bool;
/* 1604 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 1605 */     if (graphicsEnvironment instanceof SunGraphicsEnvironment) {
/* 1606 */       bool = ((SunGraphicsEnvironment)graphicsEnvironment).isDisplayLocal();
/*      */     } else {
/* 1608 */       bool = true;
/*      */     } 
/* 1610 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getUIDefaultsInt(Object paramObject) {
/* 1622 */     return getUIDefaultsInt(paramObject, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getUIDefaultsInt(Object paramObject, Locale paramLocale) {
/* 1637 */     return getUIDefaultsInt(paramObject, paramLocale, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getUIDefaultsInt(Object paramObject, int paramInt) {
/* 1653 */     return getUIDefaultsInt(paramObject, null, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getUIDefaultsInt(Object paramObject, Locale paramLocale, int paramInt) {
/* 1670 */     Object object = UIManager.get(paramObject, paramLocale);
/*      */     
/* 1672 */     if (object instanceof Integer) {
/* 1673 */       return ((Integer)object).intValue();
/*      */     }
/* 1675 */     if (object instanceof String) {
/*      */       try {
/* 1677 */         return Integer.parseInt((String)object);
/* 1678 */       } catch (NumberFormatException numberFormatException) {}
/*      */     }
/* 1680 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Component compositeRequestFocus(Component paramComponent) {
/* 1686 */     if (paramComponent instanceof Container) {
/* 1687 */       Container container1 = (Container)paramComponent;
/* 1688 */       if (container1.isFocusCycleRoot()) {
/* 1689 */         FocusTraversalPolicy focusTraversalPolicy = container1.getFocusTraversalPolicy();
/* 1690 */         Component component = focusTraversalPolicy.getDefaultComponent(container1);
/* 1691 */         if (component != null) {
/* 1692 */           component.requestFocus();
/* 1693 */           return component;
/*      */         } 
/*      */       } 
/* 1696 */       Container container2 = container1.getFocusCycleRootAncestor();
/* 1697 */       if (container2 != null) {
/* 1698 */         FocusTraversalPolicy focusTraversalPolicy = container2.getFocusTraversalPolicy();
/* 1699 */         Component component = focusTraversalPolicy.getComponentAfter(container2, container1);
/*      */         
/* 1701 */         if (component != null && SwingUtilities.isDescendingFrom(component, container1)) {
/* 1702 */           component.requestFocus();
/* 1703 */           return component;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1707 */     if (paramComponent.isFocusable()) {
/* 1708 */       paramComponent.requestFocus();
/* 1709 */       return paramComponent;
/*      */     } 
/* 1711 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean tabbedPaneChangeFocusTo(Component paramComponent) {
/* 1720 */     if (paramComponent != null) {
/* 1721 */       if (paramComponent.isFocusTraversable()) {
/* 1722 */         compositeRequestFocus(paramComponent);
/* 1723 */         return true;
/* 1724 */       }  if (paramComponent instanceof JComponent && ((JComponent)paramComponent)
/* 1725 */         .requestDefaultFocus())
/*      */       {
/* 1727 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1731 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> Future<V> submit(Callable<V> paramCallable) {
/* 1743 */     if (paramCallable == null) {
/* 1744 */       throw new NullPointerException();
/*      */     }
/* 1746 */     FutureTask<V> futureTask = new FutureTask<>(paramCallable);
/* 1747 */     execute(futureTask);
/* 1748 */     return futureTask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> Future<V> submit(Runnable paramRunnable, V paramV) {
/* 1763 */     if (paramRunnable == null) {
/* 1764 */       throw new NullPointerException();
/*      */     }
/* 1766 */     FutureTask<V> futureTask = new FutureTask<>(paramRunnable, paramV);
/* 1767 */     execute(futureTask);
/* 1768 */     return futureTask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void execute(Runnable paramRunnable) {
/* 1775 */     SwingUtilities.invokeLater(paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setSkipClickCount(Component paramComponent, int paramInt) {
/* 1786 */     if (paramComponent instanceof JTextComponent && ((JTextComponent)paramComponent)
/* 1787 */       .getCaret() instanceof javax.swing.text.DefaultCaret)
/*      */     {
/* 1789 */       ((JTextComponent)paramComponent).putClientProperty(SKIP_CLICK_COUNT, Integer.valueOf(paramInt));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAdjustedClickCount(JTextComponent paramJTextComponent, MouseEvent paramMouseEvent) {
/* 1802 */     int i = paramMouseEvent.getClickCount();
/*      */     
/* 1804 */     if (i == 1) {
/* 1805 */       paramJTextComponent.putClientProperty(SKIP_CLICK_COUNT, (Object)null);
/*      */     } else {
/* 1807 */       Integer integer = (Integer)paramJTextComponent.getClientProperty(SKIP_CLICK_COUNT);
/* 1808 */       if (integer != null) {
/* 1809 */         return i - integer.intValue();
/*      */       }
/*      */     } 
/*      */     
/* 1813 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Section
/*      */   {
/* 1825 */     LEADING,
/*      */ 
/*      */     
/* 1828 */     MIDDLE,
/*      */ 
/*      */     
/* 1831 */     TRAILING;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Section liesIn(Rectangle paramRectangle, Point paramPoint, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/*      */     int i, j, k;
/*      */     boolean bool;
/* 1889 */     if (paramBoolean1) {
/* 1890 */       i = paramRectangle.x;
/* 1891 */       j = paramPoint.x;
/* 1892 */       k = paramRectangle.width;
/* 1893 */       bool = paramBoolean2;
/*      */     } else {
/* 1895 */       i = paramRectangle.y;
/* 1896 */       j = paramPoint.y;
/* 1897 */       k = paramRectangle.height;
/* 1898 */       bool = true;
/*      */     } 
/*      */     
/* 1901 */     if (paramBoolean3) {
/* 1902 */       byte b = (k >= 30) ? 10 : (k / 3);
/*      */       
/* 1904 */       if (j < i + b)
/* 1905 */         return bool ? Section.LEADING : Section.TRAILING; 
/* 1906 */       if (j >= i + k - b) {
/* 1907 */         return bool ? Section.TRAILING : Section.LEADING;
/*      */       }
/*      */       
/* 1910 */       return Section.MIDDLE;
/*      */     } 
/* 1912 */     int m = i + k / 2;
/* 1913 */     if (bool) {
/* 1914 */       return (j >= m) ? Section.TRAILING : Section.LEADING;
/*      */     }
/* 1916 */     return (j < m) ? Section.TRAILING : Section.LEADING;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Section liesInHorizontal(Rectangle paramRectangle, Point paramPoint, boolean paramBoolean1, boolean paramBoolean2) {
/* 1943 */     return liesIn(paramRectangle, paramPoint, true, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Section liesInVertical(Rectangle paramRectangle, Point paramPoint, boolean paramBoolean) {
/* 1966 */     return liesIn(paramRectangle, paramPoint, false, false, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int convertColumnIndexToModel(TableColumnModel paramTableColumnModel, int paramInt) {
/* 1985 */     if (paramInt < 0) {
/* 1986 */       return paramInt;
/*      */     }
/* 1988 */     return paramTableColumnModel.getColumn(paramInt).getModelIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int convertColumnIndexToView(TableColumnModel paramTableColumnModel, int paramInt) {
/* 2008 */     if (paramInt < 0) {
/* 2009 */       return paramInt;
/*      */     }
/* 2011 */     for (byte b = 0; b < paramTableColumnModel.getColumnCount(); b++) {
/* 2012 */       if (paramTableColumnModel.getColumn(b).getModelIndex() == paramInt) {
/* 2013 */         return b;
/*      */       }
/*      */     } 
/* 2016 */     return -1;
/*      */   }
/*      */   
/*      */   public static int getSystemMnemonicKeyMask() {
/* 2020 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 2021 */     if (toolkit instanceof SunToolkit) {
/* 2022 */       return ((SunToolkit)toolkit).getFocusAcceleratorKeyMask();
/*      */     }
/* 2024 */     return 8;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TreePath getTreePath(TreeModelEvent paramTreeModelEvent, TreeModel paramTreeModel) {
/* 2035 */     TreePath treePath = paramTreeModelEvent.getTreePath();
/* 2036 */     if (treePath == null && paramTreeModel != null) {
/* 2037 */       Object object = paramTreeModel.getRoot();
/* 2038 */       if (object != null) {
/* 2039 */         treePath = new TreePath(object);
/*      */       }
/*      */     } 
/* 2042 */     return treePath;
/*      */   }
/*      */   
/*      */   public static interface RepaintListener {
/*      */     void repaintPerformed(JComponent param1JComponent, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\swing\SwingUtilities2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */