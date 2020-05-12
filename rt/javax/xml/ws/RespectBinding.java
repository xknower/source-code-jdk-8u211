package javax.xml.ws;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WebServiceFeatureAnnotation(id = "javax.xml.ws.RespectBindingFeature", bean = RespectBindingFeature.class)
public @interface RespectBinding {
  boolean enabled() default true;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\ws\RespectBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */