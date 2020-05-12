/*     */ package javax.xml.stream.util;
/*     */ 
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventReaderDelegate
/*     */   implements XMLEventReader
/*     */ {
/*     */   private XMLEventReader reader;
/*     */   
/*     */   public EventReaderDelegate() {}
/*     */   
/*     */   public EventReaderDelegate(XMLEventReader reader) {
/*  67 */     this.reader = reader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(XMLEventReader reader) {
/*  75 */     this.reader = reader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader getParent() {
/*  83 */     return this.reader;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEvent nextEvent() throws XMLStreamException {
/*  89 */     return this.reader.nextEvent();
/*     */   }
/*     */   
/*     */   public Object next() {
/*  93 */     return this.reader.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  98 */     return this.reader.hasNext();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEvent peek() throws XMLStreamException {
/* 104 */     return this.reader.peek();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 110 */     this.reader.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementText() throws XMLStreamException {
/* 116 */     return this.reader.getElementText();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEvent nextTag() throws XMLStreamException {
/* 122 */     return this.reader.nextTag();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 128 */     return this.reader.getProperty(name);
/*     */   }
/*     */   
/*     */   public void remove() {
/* 132 */     this.reader.remove();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\strea\\util\EventReaderDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */