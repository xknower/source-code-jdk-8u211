/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import javax.swing.text.AbstractWriter;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.DefaultStyledDocument;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.ElementIterator;
/*     */ import javax.swing.text.Style;
/*     */ import javax.swing.text.StyleConstants;
/*     */ import javax.swing.text.StyleContext;
/*     */ import javax.swing.text.StyledDocument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MinimalHTMLWriter
/*     */   extends AbstractWriter
/*     */ {
/*     */   private static final int BOLD = 1;
/*     */   private static final int ITALIC = 2;
/*     */   private static final int UNDERLINE = 4;
/*  81 */   private static final CSS css = new CSS();
/*     */   
/*  83 */   private int fontMask = 0;
/*     */   
/*  85 */   int startOffset = 0;
/*  86 */   int endOffset = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributeSet fontAttributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Hashtable<String, String> styleNameMapping;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MinimalHTMLWriter(Writer paramWriter, StyledDocument paramStyledDocument) {
/* 110 */     super(paramWriter, paramStyledDocument);
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
/*     */   public MinimalHTMLWriter(Writer paramWriter, StyledDocument paramStyledDocument, int paramInt1, int paramInt2) {
/* 124 */     super(paramWriter, paramStyledDocument, paramInt1, paramInt2);
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
/*     */   public void write() throws IOException, BadLocationException {
/* 137 */     this.styleNameMapping = new Hashtable<>();
/* 138 */     writeStartTag("<html>");
/* 139 */     writeHeader();
/* 140 */     writeBody();
/* 141 */     writeEndTag("</html>");
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
/*     */   protected void writeAttributes(AttributeSet paramAttributeSet) throws IOException {
/* 158 */     Enumeration<?> enumeration = paramAttributeSet.getAttributeNames();
/* 159 */     while (enumeration.hasMoreElements()) {
/* 160 */       Object object = enumeration.nextElement();
/* 161 */       if (object instanceof StyleConstants.ParagraphConstants || object instanceof StyleConstants.CharacterConstants || object instanceof StyleConstants.FontConstants || object instanceof StyleConstants.ColorConstants) {
/*     */ 
/*     */ 
/*     */         
/* 165 */         indent();
/* 166 */         write(object.toString());
/* 167 */         write(':');
/* 168 */         write(css
/* 169 */             .styleConstantsValueToCSSValue((StyleConstants)object, paramAttributeSet.getAttribute(object))
/* 170 */             .toString());
/* 171 */         write(';');
/* 172 */         write('\n');
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void text(Element paramElement) throws IOException, BadLocationException {
/* 184 */     String str = getText(paramElement);
/* 185 */     if (str.length() > 0 && str
/* 186 */       .charAt(str.length() - 1) == '\n') {
/* 187 */       str = str.substring(0, str.length() - 1);
/*     */     }
/* 189 */     if (str.length() > 0) {
/* 190 */       write(str);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeStartTag(String paramString) throws IOException {
/* 201 */     indent();
/* 202 */     write(paramString);
/* 203 */     write('\n');
/* 204 */     incrIndent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEndTag(String paramString) throws IOException {
/* 215 */     decrIndent();
/* 216 */     indent();
/* 217 */     write(paramString);
/* 218 */     write('\n');
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
/*     */   protected void writeHeader() throws IOException {
/* 234 */     writeStartTag("<head>");
/* 235 */     writeStartTag("<style>");
/* 236 */     writeStartTag("<!--");
/* 237 */     writeStyles();
/* 238 */     writeEndTag("-->");
/* 239 */     writeEndTag("</style>");
/* 240 */     writeEndTag("</head>");
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
/*     */   protected void writeStyles() throws IOException {
/* 257 */     DefaultStyledDocument defaultStyledDocument = (DefaultStyledDocument)getDocument();
/* 258 */     Enumeration<?> enumeration = defaultStyledDocument.getStyleNames();
/*     */     
/* 260 */     while (enumeration.hasMoreElements()) {
/* 261 */       Style style = defaultStyledDocument.getStyle((String)enumeration.nextElement());
/*     */ 
/*     */ 
/*     */       
/* 265 */       if (style.getAttributeCount() == 1 && style
/* 266 */         .isDefined(StyleConstants.NameAttribute)) {
/*     */         continue;
/*     */       }
/* 269 */       indent();
/* 270 */       write("p." + addStyleName(style.getName()));
/* 271 */       write(" {\n");
/* 272 */       incrIndent();
/* 273 */       writeAttributes(style);
/* 274 */       decrIndent();
/* 275 */       indent();
/* 276 */       write("}\n");
/*     */     } 
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
/*     */   protected void writeBody() throws IOException, BadLocationException {
/* 290 */     ElementIterator elementIterator = getElementIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     elementIterator.current();
/*     */ 
/*     */ 
/*     */     
/* 301 */     writeStartTag("<body>");
/*     */     
/* 303 */     boolean bool = false;
/*     */     Element element;
/* 305 */     while ((element = elementIterator.next()) != null) {
/* 306 */       if (!inRange(element)) {
/*     */         continue;
/*     */       }
/* 309 */       if (element instanceof javax.swing.text.AbstractDocument.BranchElement) {
/* 310 */         if (bool) {
/* 311 */           writeEndParagraph();
/* 312 */           bool = false;
/* 313 */           this.fontMask = 0;
/*     */         } 
/* 315 */         writeStartParagraph(element); continue;
/* 316 */       }  if (isText(element)) {
/* 317 */         writeContent(element, !bool);
/* 318 */         bool = true; continue;
/*     */       } 
/* 320 */       writeLeaf(element);
/* 321 */       bool = true;
/*     */     } 
/*     */     
/* 324 */     if (bool) {
/* 325 */       writeEndParagraph();
/*     */     }
/* 327 */     writeEndTag("</body>");
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
/*     */   protected void writeEndParagraph() throws IOException {
/* 340 */     writeEndMask(this.fontMask);
/* 341 */     if (inFontTag()) {
/* 342 */       endSpanTag();
/*     */     } else {
/* 344 */       write('\n');
/*     */     } 
/* 346 */     writeEndTag("</p>");
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
/*     */   protected void writeStartParagraph(Element paramElement) throws IOException {
/* 360 */     AttributeSet attributeSet = paramElement.getAttributes();
/* 361 */     Object object = attributeSet.getAttribute(StyleConstants.ResolveAttribute);
/* 362 */     if (object instanceof StyleContext.NamedStyle) {
/* 363 */       writeStartTag("<p class=" + mapStyleName(((StyleContext.NamedStyle)object).getName()) + ">");
/*     */     } else {
/* 365 */       writeStartTag("<p>");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeLeaf(Element paramElement) throws IOException {
/* 377 */     indent();
/* 378 */     if (paramElement.getName() == "icon") {
/* 379 */       writeImage(paramElement);
/* 380 */     } else if (paramElement.getName() == "component") {
/* 381 */       writeComponent(paramElement);
/*     */     } 
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
/*     */   protected void writeImage(Element paramElement) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeComponent(Element paramElement) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isText(Element paramElement) {
/* 415 */     return (paramElement.getName() == "content");
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
/*     */   protected void writeContent(Element paramElement, boolean paramBoolean) throws IOException, BadLocationException {
/* 430 */     AttributeSet attributeSet = paramElement.getAttributes();
/* 431 */     writeNonHTMLAttributes(attributeSet);
/* 432 */     if (paramBoolean) {
/* 433 */       indent();
/*     */     }
/* 435 */     writeHTMLTags(attributeSet);
/* 436 */     text(paramElement);
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
/*     */   protected void writeHTMLTags(AttributeSet paramAttributeSet) throws IOException {
/* 450 */     int i = this.fontMask;
/* 451 */     setFontMask(paramAttributeSet);
/*     */     
/* 453 */     int j = 0;
/* 454 */     int k = 0;
/* 455 */     if ((i & 0x1) != 0) {
/* 456 */       if ((this.fontMask & 0x1) == 0) {
/* 457 */         j |= 0x1;
/*     */       }
/* 459 */     } else if ((this.fontMask & 0x1) != 0) {
/* 460 */       k |= 0x1;
/*     */     } 
/*     */     
/* 463 */     if ((i & 0x2) != 0) {
/* 464 */       if ((this.fontMask & 0x2) == 0) {
/* 465 */         j |= 0x2;
/*     */       }
/* 467 */     } else if ((this.fontMask & 0x2) != 0) {
/* 468 */       k |= 0x2;
/*     */     } 
/*     */     
/* 471 */     if ((i & 0x4) != 0) {
/* 472 */       if ((this.fontMask & 0x4) == 0) {
/* 473 */         j |= 0x4;
/*     */       }
/* 475 */     } else if ((this.fontMask & 0x4) != 0) {
/* 476 */       k |= 0x4;
/*     */     } 
/* 478 */     writeEndMask(j);
/* 479 */     writeStartMask(k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFontMask(AttributeSet paramAttributeSet) {
/* 490 */     if (StyleConstants.isBold(paramAttributeSet)) {
/* 491 */       this.fontMask |= 0x1;
/*     */     }
/*     */     
/* 494 */     if (StyleConstants.isItalic(paramAttributeSet)) {
/* 495 */       this.fontMask |= 0x2;
/*     */     }
/*     */     
/* 498 */     if (StyleConstants.isUnderline(paramAttributeSet)) {
/* 499 */       this.fontMask |= 0x4;
/*     */     }
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
/*     */   private void writeStartMask(int paramInt) throws IOException {
/* 513 */     if (paramInt != 0) {
/* 514 */       if ((paramInt & 0x4) != 0) {
/* 515 */         write("<u>");
/*     */       }
/* 517 */       if ((paramInt & 0x2) != 0) {
/* 518 */         write("<i>");
/*     */       }
/* 520 */       if ((paramInt & 0x1) != 0) {
/* 521 */         write("<b>");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEndMask(int paramInt) throws IOException {
/* 533 */     if (paramInt != 0) {
/* 534 */       if ((paramInt & 0x1) != 0) {
/* 535 */         write("</b>");
/*     */       }
/* 537 */       if ((paramInt & 0x2) != 0) {
/* 538 */         write("</i>");
/*     */       }
/* 540 */       if ((paramInt & 0x4) != 0) {
/* 541 */         write("</u>");
/*     */       }
/*     */     } 
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
/*     */   protected void writeNonHTMLAttributes(AttributeSet paramAttributeSet) throws IOException {
/* 560 */     String str1 = "";
/* 561 */     String str2 = "; ";
/*     */     
/* 563 */     if (inFontTag() && this.fontAttributes.isEqual(paramAttributeSet)) {
/*     */       return;
/*     */     }
/*     */     
/* 567 */     boolean bool = true;
/* 568 */     Color color = (Color)paramAttributeSet.getAttribute(StyleConstants.Foreground);
/* 569 */     if (color != null) {
/*     */       
/* 571 */       str1 = str1 + "color: " + css.styleConstantsValueToCSSValue((StyleConstants)StyleConstants.Foreground, color);
/*     */       
/* 573 */       bool = false;
/*     */     } 
/* 575 */     Integer integer = (Integer)paramAttributeSet.getAttribute(StyleConstants.FontSize);
/* 576 */     if (integer != null) {
/* 577 */       if (!bool) {
/* 578 */         str1 = str1 + str2;
/*     */       }
/* 580 */       str1 = str1 + "font-size: " + integer.intValue() + "pt";
/* 581 */       bool = false;
/*     */     } 
/*     */     
/* 584 */     String str3 = (String)paramAttributeSet.getAttribute(StyleConstants.FontFamily);
/* 585 */     if (str3 != null) {
/* 586 */       if (!bool) {
/* 587 */         str1 = str1 + str2;
/*     */       }
/* 589 */       str1 = str1 + "font-family: " + str3;
/* 590 */       bool = false;
/*     */     } 
/*     */     
/* 593 */     if (str1.length() > 0) {
/* 594 */       if (this.fontMask != 0) {
/* 595 */         writeEndMask(this.fontMask);
/* 596 */         this.fontMask = 0;
/*     */       } 
/* 598 */       startSpanTag(str1);
/* 599 */       this.fontAttributes = paramAttributeSet;
/*     */     }
/* 601 */     else if (this.fontAttributes != null) {
/* 602 */       writeEndMask(this.fontMask);
/* 603 */       this.fontMask = 0;
/* 604 */       endSpanTag();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean inFontTag() {
/* 613 */     return (this.fontAttributes != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void endFontTag() throws IOException {
/* 624 */     write('\n');
/* 625 */     writeEndTag("</font>");
/* 626 */     this.fontAttributes = null;
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
/*     */   protected void startFontTag(String paramString) throws IOException {
/* 642 */     boolean bool = false;
/* 643 */     if (inFontTag()) {
/* 644 */       endFontTag();
/* 645 */       bool = true;
/*     */     } 
/* 647 */     writeStartTag("<font style=\"" + paramString + "\">");
/* 648 */     if (bool) {
/* 649 */       indent();
/*     */     }
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
/*     */   private void startSpanTag(String paramString) throws IOException {
/* 663 */     boolean bool = false;
/* 664 */     if (inFontTag()) {
/* 665 */       endSpanTag();
/* 666 */       bool = true;
/*     */     } 
/* 668 */     writeStartTag("<span style=\"" + paramString + "\">");
/* 669 */     if (bool) {
/* 670 */       indent();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void endSpanTag() throws IOException {
/* 680 */     write('\n');
/* 681 */     writeEndTag("</span>");
/* 682 */     this.fontAttributes = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String addStyleName(String paramString) {
/* 691 */     if (this.styleNameMapping == null) {
/* 692 */       return paramString;
/*     */     }
/* 694 */     StringBuilder stringBuilder = null;
/* 695 */     for (int i = paramString.length() - 1; i >= 0; i--) {
/* 696 */       if (!isValidCharacter(paramString.charAt(i))) {
/* 697 */         if (stringBuilder == null) {
/* 698 */           stringBuilder = new StringBuilder(paramString);
/*     */         }
/* 700 */         stringBuilder.setCharAt(i, 'a');
/*     */       } 
/*     */     } 
/* 703 */     String str = (stringBuilder != null) ? stringBuilder.toString() : paramString;
/* 704 */     while (this.styleNameMapping.get(str) != null) {
/* 705 */       str = str + 'x';
/*     */     }
/* 707 */     this.styleNameMapping.put(paramString, str);
/* 708 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String mapStyleName(String paramString) {
/* 715 */     if (this.styleNameMapping == null) {
/* 716 */       return paramString;
/*     */     }
/* 718 */     String str = this.styleNameMapping.get(paramString);
/* 719 */     return (str == null) ? paramString : str;
/*     */   }
/*     */   
/*     */   private boolean isValidCharacter(char paramChar) {
/* 723 */     return ((paramChar >= 'a' && paramChar <= 'z') || (paramChar >= 'A' && paramChar <= 'Z'));
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\text\html\MinimalHTMLWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */