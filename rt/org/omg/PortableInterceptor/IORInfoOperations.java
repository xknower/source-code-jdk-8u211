package org.omg.PortableInterceptor;

import org.omg.CORBA.Policy;
import org.omg.IOP.TaggedComponent;

public interface IORInfoOperations {
  Policy get_effective_policy(int paramInt);
  
  void add_ior_component(TaggedComponent paramTaggedComponent);
  
  void add_ior_component_to_profile(TaggedComponent paramTaggedComponent, int paramInt);
  
  int manager_id();
  
  short state();
  
  ObjectReferenceTemplate adapter_template();
  
  ObjectReferenceFactory current_factory();
  
  void current_factory(ObjectReferenceFactory paramObjectReferenceFactory);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableInterceptor\IORInfoOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */