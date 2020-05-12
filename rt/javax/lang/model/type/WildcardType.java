package javax.lang.model.type;

public interface WildcardType extends TypeMirror {
  TypeMirror getExtendsBound();
  
  TypeMirror getSuperBound();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\lang\model\type\WildcardType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */