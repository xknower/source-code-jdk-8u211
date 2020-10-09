/*     */ package sun.applet;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Choice;
/*     */ import java.awt.Event;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Label;
/*     */ import java.awt.Panel;
/*     */ import java.awt.TextField;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Properties;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AppletProps
/*     */   extends Frame
/*     */ {
/*     */   TextField proxyHost;
/*     */   TextField proxyPort;
/*     */   Choice accessMode;
/*     */   
/*     */   AppletProps() {
/*  47 */     setTitle(amh.getMessage("title"));
/*  48 */     Panel panel = new Panel();
/*  49 */     panel.setLayout(new GridLayout(0, 2));
/*     */     
/*  51 */     panel.add(new Label(amh.getMessage("label.http.server", "Http proxy server:")));
/*  52 */     panel.add(this.proxyHost = new TextField());
/*     */     
/*  54 */     panel.add(new Label(amh.getMessage("label.http.proxy")));
/*  55 */     panel.add(this.proxyPort = new TextField());
/*     */     
/*  57 */     panel.add(new Label(amh.getMessage("label.class")));
/*  58 */     panel.add(this.accessMode = new Choice());
/*  59 */     this.accessMode.addItem(amh.getMessage("choice.class.item.restricted"));
/*  60 */     this.accessMode.addItem(amh.getMessage("choice.class.item.unrestricted"));
/*     */     
/*  62 */     add("Center", panel);
/*  63 */     panel = new Panel();
/*  64 */     panel.add(new Button(amh.getMessage("button.apply")));
/*  65 */     panel.add(new Button(amh.getMessage("button.reset")));
/*  66 */     panel.add(new Button(amh.getMessage("button.cancel")));
/*  67 */     add("South", panel);
/*  68 */     move(200, 150);
/*  69 */     pack();
/*  70 */     reset();
/*     */   }
/*     */   
/*     */   void reset() {
/*  74 */     AppletSecurity appletSecurity = (AppletSecurity)System.getSecurityManager();
/*  75 */     if (appletSecurity != null) {
/*  76 */       appletSecurity.reset();
/*     */     }
/*  78 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("http.proxyHost"));
/*     */     
/*  80 */     String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("http.proxyPort"));
/*     */ 
/*     */     
/*  83 */     Boolean bool = AccessController.<Boolean>doPrivileged(new GetBooleanAction("package.restrict.access.sun"));
/*     */ 
/*     */     
/*  86 */     boolean bool1 = bool.booleanValue();
/*  87 */     if (bool1) {
/*  88 */       this.accessMode.select(amh.getMessage("choice.class.item.restricted"));
/*     */     } else {
/*  90 */       this.accessMode.select(amh.getMessage("choice.class.item.unrestricted"));
/*     */     } 
/*     */     
/*  93 */     if (str1 != null) {
/*  94 */       this.proxyHost.setText(str1);
/*  95 */       this.proxyPort.setText(str2);
/*     */     } else {
/*  97 */       this.proxyHost.setText("");
/*  98 */       this.proxyPort.setText("");
/*     */     } 
/*     */   }
/*     */   
/*     */   void apply() {
/* 103 */     String str1 = this.proxyHost.getText().trim();
/* 104 */     String str2 = this.proxyPort.getText().trim();
/*     */ 
/*     */     
/* 107 */     final Properties props = AccessController.<Properties>doPrivileged(new PrivilegedAction<Properties>()
/*     */         {
/*     */           public Object run() {
/* 110 */             return System.getProperties();
/*     */           }
/*     */         });
/*     */     
/* 114 */     if (str1.length() != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 119 */       int i = 0;
/*     */       try {
/* 121 */         i = Integer.parseInt(str2);
/* 122 */       } catch (NumberFormatException numberFormatException) {}
/*     */       
/* 124 */       if (i <= 0) {
/* 125 */         this.proxyPort.selectAll();
/* 126 */         this.proxyPort.requestFocus();
/* 127 */         (new AppletPropsErrorDialog(this, amh
/* 128 */             .getMessage("title.invalidproxy"), amh
/* 129 */             .getMessage("label.invalidproxy"), amh
/* 130 */             .getMessage("button.ok"))).show();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 135 */       properties.put("http.proxyHost", str1);
/* 136 */       properties.put("http.proxyPort", str2);
/*     */     } else {
/* 138 */       properties.put("http.proxyHost", "");
/*     */     } 
/*     */     
/* 141 */     if (amh.getMessage("choice.class.item.restricted").equals(this.accessMode.getSelectedItem())) {
/* 142 */       properties.put("package.restrict.access.sun", "true");
/*     */     } else {
/* 144 */       properties.put("package.restrict.access.sun", "false");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 149 */       reset();
/* 150 */       AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*     */             public Object run() throws IOException {
/* 152 */               File file = Main.theUserPropertiesFile;
/* 153 */               FileOutputStream fileOutputStream = new FileOutputStream(file);
/* 154 */               Properties properties = new Properties();
/* 155 */               for (byte b = 0; b < Main.avDefaultUserProps.length; b++) {
/* 156 */                 String str = Main.avDefaultUserProps[b][0];
/* 157 */                 properties.setProperty(str, props.getProperty(str));
/*     */               } 
/* 159 */               properties.store(fileOutputStream, AppletProps.amh.getMessage("prop.store"));
/* 160 */               fileOutputStream.close();
/* 161 */               return null;
/*     */             }
/*     */           });
/* 164 */       hide();
/* 165 */     } catch (PrivilegedActionException privilegedActionException) {
/* 166 */       System.out.println(amh.getMessage("apply.exception", privilegedActionException
/* 167 */             .getException()));
/*     */       
/* 169 */       privilegedActionException.printStackTrace();
/* 170 */       reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean action(Event paramEvent, Object paramObject) {
/* 175 */     if (amh.getMessage("button.apply").equals(paramObject)) {
/* 176 */       apply();
/* 177 */       return true;
/*     */     } 
/* 179 */     if (amh.getMessage("button.reset").equals(paramObject)) {
/* 180 */       reset();
/* 181 */       return true;
/*     */     } 
/* 183 */     if (amh.getMessage("button.cancel").equals(paramObject)) {
/* 184 */       reset();
/* 185 */       hide();
/* 186 */       return true;
/*     */     } 
/* 188 */     return false;
/*     */   }
/*     */   
/* 191 */   private static AppletMessageHandler amh = new AppletMessageHandler("appletprops");
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\applet\AppletProps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */