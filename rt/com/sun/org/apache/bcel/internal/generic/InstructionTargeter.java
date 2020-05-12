package com.sun.org.apache.bcel.internal.generic;

public interface InstructionTargeter {
  boolean containsTarget(InstructionHandle paramInstructionHandle);
  
  void updateTarget(InstructionHandle paramInstructionHandle1, InstructionHandle paramInstructionHandle2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\InstructionTargeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */