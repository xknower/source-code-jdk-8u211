package com.sun.corba.se.spi.ior;

public interface TaggedProfile extends Identifiable, MakeImmutable {
  TaggedProfileTemplate getTaggedProfileTemplate();
  
  ObjectId getObjectId();
  
  ObjectKeyTemplate getObjectKeyTemplate();
  
  ObjectKey getObjectKey();
  
  boolean isEquivalent(TaggedProfile paramTaggedProfile);
  
  org.omg.IOP.TaggedProfile getIOPProfile();
  
  boolean isLocal();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\ior\TaggedProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */