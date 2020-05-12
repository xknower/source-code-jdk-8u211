package com.sun.xml.internal.ws.api.wsdl.parser;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.xml.sax.EntityResolver;

public abstract class MetadataResolverFactory {
  @NotNull
  public abstract MetaDataResolver metadataResolver(@Nullable EntityResolver paramEntityResolver);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\wsdl\parser\MetadataResolverFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */