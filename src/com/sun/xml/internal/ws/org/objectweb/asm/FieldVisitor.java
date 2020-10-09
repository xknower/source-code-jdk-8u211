package com.sun.xml.internal.ws.org.objectweb.asm;

public interface FieldVisitor {
  AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean);
  
  void visitAttribute(Attribute paramAttribute);
  
  void visitEnd();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\org\objectweb\asm\FieldVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */