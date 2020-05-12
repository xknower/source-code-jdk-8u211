package com.sun.xml.internal.ws.api;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import javax.xml.ws.WebServiceFeature;

public interface WSFeatureList extends Iterable<WebServiceFeature> {
  boolean isEnabled(@NotNull Class<? extends WebServiceFeature> paramClass);
  
  @Nullable
  <F extends WebServiceFeature> F get(@NotNull Class<F> paramClass);
  
  @NotNull
  WebServiceFeature[] toArray();
  
  void mergeFeatures(@NotNull WebServiceFeature[] paramArrayOfWebServiceFeature, boolean paramBoolean);
  
  void mergeFeatures(@NotNull Iterable<WebServiceFeature> paramIterable, boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\WSFeatureList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */