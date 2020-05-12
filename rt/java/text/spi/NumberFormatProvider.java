package java.text.spi;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.spi.LocaleServiceProvider;

public abstract class NumberFormatProvider extends LocaleServiceProvider {
  public abstract NumberFormat getCurrencyInstance(Locale paramLocale);
  
  public abstract NumberFormat getIntegerInstance(Locale paramLocale);
  
  public abstract NumberFormat getNumberInstance(Locale paramLocale);
  
  public abstract NumberFormat getPercentInstance(Locale paramLocale);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\text\spi\NumberFormatProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */