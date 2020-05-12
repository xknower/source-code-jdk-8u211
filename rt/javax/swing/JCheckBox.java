/*     */ package javax.swing;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.swing.plaf.ButtonUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JCheckBox
/*     */   extends JToggleButton
/*     */   implements Accessible
/*     */ {
/*     */   public static final String BORDER_PAINTED_FLAT_CHANGED_PROPERTY = "borderPaintedFlat";
/*     */   private boolean flat = false;
/*     */   private static final String uiClassID = "CheckBoxUI";
/*     */   
/*     */   public JCheckBox() {
/*  96 */     this((String)null, (Icon)null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JCheckBox(Icon paramIcon) {
/* 105 */     this((String)null, paramIcon, false);
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
/*     */   public JCheckBox(Icon paramIcon, boolean paramBoolean) {
/* 117 */     this((String)null, paramIcon, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JCheckBox(String paramString) {
/* 126 */     this(paramString, (Icon)null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JCheckBox(Action paramAction) {
/* 136 */     this();
/* 137 */     setAction(paramAction);
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
/*     */   public JCheckBox(String paramString, boolean paramBoolean) {
/* 150 */     this(paramString, (Icon)null, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JCheckBox(String paramString, Icon paramIcon) {
/* 161 */     this(paramString, paramIcon, false);
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
/*     */   public JCheckBox(String paramString, Icon paramIcon, boolean paramBoolean) {
/* 174 */     super(paramString, paramIcon, paramBoolean);
/* 175 */     setUIProperty("borderPainted", Boolean.FALSE);
/* 176 */     setHorizontalAlignment(10);
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
/*     */   public void setBorderPaintedFlat(boolean paramBoolean) {
/* 202 */     boolean bool = this.flat;
/* 203 */     this.flat = paramBoolean;
/* 204 */     firePropertyChange("borderPaintedFlat", bool, this.flat);
/* 205 */     if (paramBoolean != bool) {
/* 206 */       revalidate();
/* 207 */       repaint();
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
/*     */   public boolean isBorderPaintedFlat() {
/* 219 */     return this.flat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 228 */     setUI((ButtonUI)UIManager.getUI(this));
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
/*     */   public String getUIClassID() {
/* 244 */     return "CheckBoxUI";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setIconFromAction(Action paramAction) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 260 */     paramObjectOutputStream.defaultWriteObject();
/* 261 */     if (getUIClassID().equals("CheckBoxUI")) {
/* 262 */       byte b = JComponent.getWriteObjCounter(this);
/* 263 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 264 */       if (b == 0 && this.ui != null) {
/* 265 */         this.ui.installUI(this);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 278 */     paramObjectInputStream.defaultReadObject();
/* 279 */     if (getUIClassID().equals("CheckBoxUI")) {
/* 280 */       updateUI();
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
/*     */   protected String paramString() {
/* 296 */     return super.paramString();
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 316 */     if (this.accessibleContext == null) {
/* 317 */       this.accessibleContext = new AccessibleJCheckBox();
/*     */     }
/* 319 */     return this.accessibleContext;
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
/*     */   protected class AccessibleJCheckBox
/*     */     extends JToggleButton.AccessibleJToggleButton
/*     */   {
/*     */     public AccessibleRole getAccessibleRole() {
/* 346 */       return AccessibleRole.CHECK_BOX;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\JCheckBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */