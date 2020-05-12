/*     */ package javax.swing.colorchooser;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractColorChooserPanel
/*     */   extends JPanel
/*     */ {
/*  52 */   private final PropertyChangeListener enabledListener = new PropertyChangeListener() {
/*     */       public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  54 */         Object object = param1PropertyChangeEvent.getNewValue();
/*  55 */         if (object instanceof Boolean) {
/*  56 */           AbstractColorChooserPanel.this.setEnabled(((Boolean)object).booleanValue());
/*     */         }
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JColorChooser chooser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void updateChooser();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void buildChooser();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getDisplayName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMnemonic() {
/* 103 */     return 0;
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
/*     */   public int getDisplayedMnemonicIndex() {
/* 129 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Icon getSmallDisplayIcon();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Icon getLargeDisplayIcon();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installChooserPanel(JColorChooser paramJColorChooser) {
/* 152 */     if (this.chooser != null) {
/* 153 */       throw new RuntimeException("This chooser panel is already installed");
/*     */     }
/* 155 */     this.chooser = paramJColorChooser;
/* 156 */     this.chooser.addPropertyChangeListener("enabled", this.enabledListener);
/* 157 */     setEnabled(this.chooser.isEnabled());
/* 158 */     buildChooser();
/* 159 */     updateChooser();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallChooserPanel(JColorChooser paramJColorChooser) {
/* 167 */     this.chooser.removePropertyChangeListener("enabled", this.enabledListener);
/* 168 */     this.chooser = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorSelectionModel getColorSelectionModel() {
/* 177 */     return (this.chooser != null) ? this.chooser
/* 178 */       .getSelectionModel() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Color getColorFromModel() {
/* 187 */     ColorSelectionModel colorSelectionModel = getColorSelectionModel();
/* 188 */     return (colorSelectionModel != null) ? colorSelectionModel
/* 189 */       .getSelectedColor() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   void setSelectedColor(Color paramColor) {
/* 194 */     ColorSelectionModel colorSelectionModel = getColorSelectionModel();
/* 195 */     if (colorSelectionModel != null) {
/* 196 */       colorSelectionModel.setSelectedColor(paramColor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/* 205 */     super.paint(paramGraphics);
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
/*     */   int getInt(Object paramObject, int paramInt) {
/* 219 */     Object object = UIManager.get(paramObject, getLocale());
/*     */     
/* 221 */     if (object instanceof Integer) {
/* 222 */       return ((Integer)object).intValue();
/*     */     }
/* 224 */     if (object instanceof String) {
/*     */       try {
/* 226 */         return Integer.parseInt((String)object);
/* 227 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/* 229 */     return paramInt;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\colorchooser\AbstractColorChooserPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */