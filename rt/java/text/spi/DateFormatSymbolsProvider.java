package java.text.spi;

import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.spi.LocaleServiceProvider;

public abstract class DateFormatSymbolsProvider extends LocaleServiceProvider {
  public abstract DateFormatSymbols getInstance(Locale paramLocale);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\text\spi\DateFormatSymbolsProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */