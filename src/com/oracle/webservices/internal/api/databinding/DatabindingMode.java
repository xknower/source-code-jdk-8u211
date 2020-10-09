package com.oracle.webservices.internal.api.databinding;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@WebServiceFeatureAnnotation(id = "", bean = DatabindingModeFeature.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabindingMode {
  String value();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\oracle\webservices\internal\api\databinding\DatabindingMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */