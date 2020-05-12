/*     */ package javax.xml.bind.helpers;
/*     */ 
/*     */ import java.net.URL;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.ValidationEventLocator;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultValidationEventHandler
/*     */   implements ValidationEventHandler
/*     */ {
/*     */   public boolean handleEvent(ValidationEvent event) {
/*  61 */     if (event == null) {
/*  62 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/*  66 */     String severity = null;
/*  67 */     boolean retVal = false;
/*  68 */     switch (event.getSeverity()) {
/*     */       case 0:
/*  70 */         severity = Messages.format("DefaultValidationEventHandler.Warning");
/*  71 */         retVal = true;
/*     */         break;
/*     */       case 1:
/*  74 */         severity = Messages.format("DefaultValidationEventHandler.Error");
/*  75 */         retVal = false;
/*     */         break;
/*     */       case 2:
/*  78 */         severity = Messages.format("DefaultValidationEventHandler.FatalError");
/*  79 */         retVal = false;
/*     */         break;
/*     */       
/*     */       default:
/*  83 */         assert false : Messages.format("DefaultValidationEventHandler.UnrecognizedSeverity", 
/*  84 */             Integer.valueOf(event.getSeverity()));
/*     */         break;
/*     */     } 
/*     */     
/*  88 */     String location = getLocation(event);
/*     */     
/*  90 */     System.out.println(
/*  91 */         Messages.format("DefaultValidationEventHandler.SeverityMessage", severity, event
/*     */           
/*  93 */           .getMessage(), location));
/*     */ 
/*     */ 
/*     */     
/*  97 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getLocation(ValidationEvent event) {
/* 105 */     StringBuffer msg = new StringBuffer();
/*     */     
/* 107 */     ValidationEventLocator locator = event.getLocator();
/*     */     
/* 109 */     if (locator != null) {
/*     */       
/* 111 */       URL url = locator.getURL();
/* 112 */       Object obj = locator.getObject();
/* 113 */       Node node = locator.getNode();
/* 114 */       int line = locator.getLineNumber();
/*     */       
/* 116 */       if (url != null || line != -1) {
/* 117 */         msg.append("line " + line);
/* 118 */         if (url != null)
/* 119 */           msg.append(" of " + url); 
/* 120 */       } else if (obj != null) {
/* 121 */         msg.append(" obj: " + obj.toString());
/* 122 */       } else if (node != null) {
/* 123 */         msg.append(" node: " + node.toString());
/*     */       } 
/*     */     } else {
/* 126 */       msg.append(Messages.format("DefaultValidationEventHandler.LocationUnavailable"));
/*     */     } 
/*     */     
/* 129 */     return msg.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\bind\helpers\DefaultValidationEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */