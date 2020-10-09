package com.sun.xml.internal.bind.v2.model.runtime;

import com.sun.xml.internal.bind.v2.model.core.ReferencePropertyInfo;
import java.lang.reflect.Type;
import java.util.Set;

public interface RuntimeReferencePropertyInfo extends ReferencePropertyInfo<Type, Class>, RuntimePropertyInfo {
  Set<? extends RuntimeElement> getElements();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\runtime\RuntimeReferencePropertyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */