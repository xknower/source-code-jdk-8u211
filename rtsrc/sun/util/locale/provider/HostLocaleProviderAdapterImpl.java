/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.text.DateFormat;
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.text.spi.DateFormatProvider;
/*     */ import java.text.spi.DateFormatSymbolsProvider;
/*     */ import java.text.spi.DecimalFormatSymbolsProvider;
/*     */ import java.text.spi.NumberFormatProvider;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Currency;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*     */ import java.util.spi.CalendarDataProvider;
/*     */ import java.util.spi.CurrencyNameProvider;
/*     */ import java.util.spi.LocaleNameProvider;
/*     */ import sun.util.spi.CalendarProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HostLocaleProviderAdapterImpl
/*     */ {
/*     */   private static final int CAT_DISPLAY = 0;
/*     */   private static final int CAT_FORMAT = 1;
/*     */   private static final int NF_NUMBER = 0;
/*     */   private static final int NF_CURRENCY = 1;
/*     */   private static final int NF_PERCENT = 2;
/*     */   private static final int NF_INTEGER = 3;
/*     */   private static final int NF_MAX = 3;
/*     */   private static final int CD_FIRSTDAYOFWEEK = 0;
/*     */   private static final int CD_MINIMALDAYSINFIRSTWEEK = 1;
/*     */   private static final int DN_CURRENCY_NAME = 0;
/*     */   private static final int DN_CURRENCY_SYMBOL = 1;
/*     */   private static final int DN_LOCALE_LANGUAGE = 2;
/*     */   private static final int DN_LOCALE_SCRIPT = 3;
/*     */   private static final int DN_LOCALE_REGION = 4;
/*     */   private static final int DN_LOCALE_VARIANT = 5;
/*  86 */   private static final String[] calIDToLDML = new String[] { "", "gregory", "gregory_en-US", "japanese", "roc", "", "islamic", "buddhist", "hebrew", "gregory_fr", "gregory_ar", "gregory_en", "gregory_fr" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private static ConcurrentMap<Locale, SoftReference<AtomicReferenceArray<String>>> dateFormatCache = new ConcurrentHashMap<>();
/* 104 */   private static ConcurrentMap<Locale, SoftReference<DateFormatSymbols>> dateFormatSymbolsCache = new ConcurrentHashMap<>();
/* 105 */   private static ConcurrentMap<Locale, SoftReference<AtomicReferenceArray<String>>> numberFormatCache = new ConcurrentHashMap<>();
/* 106 */   private static ConcurrentMap<Locale, SoftReference<DecimalFormatSymbols>> decimalFormatSymbolsCache = new ConcurrentHashMap<>();
/*     */   private static final Set<Locale> supportedLocaleSet;
/*     */   private static final String nativeDisplayLanguage;
/*     */   
/*     */   static {
/* 111 */     HashSet<Locale> hashSet = new HashSet();
/* 112 */     if (initialize()) {
/*     */ 
/*     */       
/* 115 */       ResourceBundle.Control control = ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT);
/* 116 */       String str1 = getDefaultLocale(0);
/* 117 */       Locale locale = Locale.forLanguageTag(str1.replace('_', '-'));
/* 118 */       hashSet.addAll(control.getCandidateLocales("", locale));
/* 119 */       nativeDisplayLanguage = locale.getLanguage();
/*     */       
/* 121 */       String str2 = getDefaultLocale(1);
/* 122 */       if (!str2.equals(str1)) {
/* 123 */         locale = Locale.forLanguageTag(str2.replace('_', '-'));
/* 124 */         hashSet.addAll(control.getCandidateLocales("", locale));
/*     */       } 
/*     */     } else {
/* 127 */       nativeDisplayLanguage = "";
/*     */     } 
/* 129 */     supportedLocaleSet = Collections.unmodifiableSet(hashSet);
/*     */   }
/* 131 */   private static final Locale[] supportedLocale = supportedLocaleSet.<Locale>toArray(new Locale[0]);
/*     */   
/*     */   public static DateFormatProvider getDateFormatProvider() {
/* 134 */     return new DateFormatProvider()
/*     */       {
/*     */         public Locale[] getAvailableLocales() {
/* 137 */           return HostLocaleProviderAdapterImpl.getSupportedCalendarLocales();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isSupportedLocale(Locale param1Locale) {
/* 142 */           return HostLocaleProviderAdapterImpl.isSupportedCalendarLocale(param1Locale);
/*     */         }
/*     */ 
/*     */         
/*     */         public DateFormat getDateInstance(int param1Int, Locale param1Locale) {
/* 147 */           AtomicReferenceArray<String> atomicReferenceArray = getDateTimePatterns(param1Locale);
/* 148 */           return new SimpleDateFormat(atomicReferenceArray.get(param1Int / 2), HostLocaleProviderAdapterImpl
/* 149 */               .getCalendarLocale(param1Locale));
/*     */         }
/*     */ 
/*     */         
/*     */         public DateFormat getTimeInstance(int param1Int, Locale param1Locale) {
/* 154 */           AtomicReferenceArray<String> atomicReferenceArray = getDateTimePatterns(param1Locale);
/* 155 */           return new SimpleDateFormat(atomicReferenceArray.get(param1Int / 2 + 2), HostLocaleProviderAdapterImpl
/* 156 */               .getCalendarLocale(param1Locale));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public DateFormat getDateTimeInstance(int param1Int1, int param1Int2, Locale param1Locale) {
/* 162 */           AtomicReferenceArray<String> atomicReferenceArray = getDateTimePatterns(param1Locale);
/*     */ 
/*     */ 
/*     */           
/* 166 */           String str = (String)atomicReferenceArray.get(param1Int1 / 2) + " " + (String)atomicReferenceArray.get(param1Int2 / 2 + 2);
/* 167 */           return new SimpleDateFormat(str, HostLocaleProviderAdapterImpl.getCalendarLocale(param1Locale));
/*     */         }
/*     */ 
/*     */         
/*     */         private AtomicReferenceArray<String> getDateTimePatterns(Locale param1Locale) {
/* 172 */           SoftReference<AtomicReferenceArray> softReference = (SoftReference)HostLocaleProviderAdapterImpl.dateFormatCache.get(param1Locale);
/*     */           AtomicReferenceArray<String> atomicReferenceArray;
/* 174 */           if (softReference == null || (atomicReferenceArray = softReference.get()) == null) {
/* 175 */             String str = HostLocaleProviderAdapterImpl.removeExtensions(param1Locale).toLanguageTag();
/* 176 */             atomicReferenceArray = new AtomicReferenceArray(4);
/* 177 */             atomicReferenceArray.compareAndSet(0, null, HostLocaleProviderAdapterImpl.convertDateTimePattern(HostLocaleProviderAdapterImpl
/* 178 */                   .getDateTimePattern(1, -1, str)));
/* 179 */             atomicReferenceArray.compareAndSet(1, null, HostLocaleProviderAdapterImpl.convertDateTimePattern(HostLocaleProviderAdapterImpl
/* 180 */                   .getDateTimePattern(3, -1, str)));
/* 181 */             atomicReferenceArray.compareAndSet(2, null, HostLocaleProviderAdapterImpl.convertDateTimePattern(HostLocaleProviderAdapterImpl
/* 182 */                   .getDateTimePattern(-1, 1, str)));
/* 183 */             atomicReferenceArray.compareAndSet(3, null, HostLocaleProviderAdapterImpl.convertDateTimePattern(HostLocaleProviderAdapterImpl
/* 184 */                   .getDateTimePattern(-1, 3, str)));
/* 185 */             softReference = new SoftReference<>(atomicReferenceArray);
/* 186 */             HostLocaleProviderAdapterImpl.dateFormatCache.put(param1Locale, softReference);
/*     */           } 
/*     */           
/* 189 */           return atomicReferenceArray;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static DateFormatSymbolsProvider getDateFormatSymbolsProvider() {
/* 195 */     return new DateFormatSymbolsProvider()
/*     */       {
/*     */         public Locale[] getAvailableLocales()
/*     */         {
/* 199 */           return HostLocaleProviderAdapterImpl.getSupportedCalendarLocales();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isSupportedLocale(Locale param1Locale) {
/* 204 */           return HostLocaleProviderAdapterImpl.isSupportedCalendarLocale(param1Locale);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public DateFormatSymbols getInstance(Locale param1Locale) {
/* 211 */           SoftReference<DateFormatSymbols> softReference = (SoftReference)HostLocaleProviderAdapterImpl.dateFormatSymbolsCache.get(param1Locale);
/*     */           DateFormatSymbols dateFormatSymbols;
/* 213 */           if (softReference == null || (dateFormatSymbols = softReference.get()) == null) {
/* 214 */             dateFormatSymbols = new DateFormatSymbols(param1Locale);
/* 215 */             String str = HostLocaleProviderAdapterImpl.removeExtensions(param1Locale).toLanguageTag();
/*     */             
/* 217 */             dateFormatSymbols.setAmPmStrings(HostLocaleProviderAdapterImpl.getAmPmStrings(str, dateFormatSymbols.getAmPmStrings()));
/* 218 */             dateFormatSymbols.setEras(HostLocaleProviderAdapterImpl.getEras(str, dateFormatSymbols.getEras()));
/* 219 */             dateFormatSymbols.setMonths(HostLocaleProviderAdapterImpl.getMonths(str, dateFormatSymbols.getMonths()));
/* 220 */             dateFormatSymbols.setShortMonths(HostLocaleProviderAdapterImpl.getShortMonths(str, dateFormatSymbols.getShortMonths()));
/* 221 */             dateFormatSymbols.setWeekdays(HostLocaleProviderAdapterImpl.getWeekdays(str, dateFormatSymbols.getWeekdays()));
/* 222 */             dateFormatSymbols.setShortWeekdays(HostLocaleProviderAdapterImpl.getShortWeekdays(str, dateFormatSymbols.getShortWeekdays()));
/* 223 */             softReference = new SoftReference<>(dateFormatSymbols);
/* 224 */             HostLocaleProviderAdapterImpl.dateFormatSymbolsCache.put(param1Locale, softReference);
/*     */           } 
/* 226 */           return (DateFormatSymbols)dateFormatSymbols.clone();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static NumberFormatProvider getNumberFormatProvider() {
/* 232 */     return new NumberFormatProvider()
/*     */       {
/*     */         public Locale[] getAvailableLocales()
/*     */         {
/* 236 */           return HostLocaleProviderAdapterImpl.getSupportedNativeDigitLocales();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isSupportedLocale(Locale param1Locale) {
/* 241 */           return HostLocaleProviderAdapterImpl.isSupportedNativeDigitLocale(param1Locale);
/*     */         }
/*     */ 
/*     */         
/*     */         public NumberFormat getCurrencyInstance(Locale param1Locale) {
/* 246 */           AtomicReferenceArray<String> atomicReferenceArray = getNumberPatterns(param1Locale);
/* 247 */           return new DecimalFormat(atomicReferenceArray.get(1), 
/* 248 */               DecimalFormatSymbols.getInstance(param1Locale));
/*     */         }
/*     */ 
/*     */         
/*     */         public NumberFormat getIntegerInstance(Locale param1Locale) {
/* 253 */           AtomicReferenceArray<String> atomicReferenceArray = getNumberPatterns(param1Locale);
/* 254 */           return new DecimalFormat(atomicReferenceArray.get(3), 
/* 255 */               DecimalFormatSymbols.getInstance(param1Locale));
/*     */         }
/*     */ 
/*     */         
/*     */         public NumberFormat getNumberInstance(Locale param1Locale) {
/* 260 */           AtomicReferenceArray<String> atomicReferenceArray = getNumberPatterns(param1Locale);
/* 261 */           return new DecimalFormat(atomicReferenceArray.get(0), 
/* 262 */               DecimalFormatSymbols.getInstance(param1Locale));
/*     */         }
/*     */ 
/*     */         
/*     */         public NumberFormat getPercentInstance(Locale param1Locale) {
/* 267 */           AtomicReferenceArray<String> atomicReferenceArray = getNumberPatterns(param1Locale);
/* 268 */           return new DecimalFormat(atomicReferenceArray.get(2), 
/* 269 */               DecimalFormatSymbols.getInstance(param1Locale));
/*     */         }
/*     */ 
/*     */         
/*     */         private AtomicReferenceArray<String> getNumberPatterns(Locale param1Locale) {
/* 274 */           SoftReference<AtomicReferenceArray> softReference = (SoftReference)HostLocaleProviderAdapterImpl.numberFormatCache.get(param1Locale);
/*     */           AtomicReferenceArray<String> atomicReferenceArray;
/* 276 */           if (softReference == null || (atomicReferenceArray = softReference.get()) == null) {
/* 277 */             String str = param1Locale.toLanguageTag();
/* 278 */             atomicReferenceArray = new AtomicReferenceArray(4);
/* 279 */             for (byte b = 0; b <= 3; b++) {
/* 280 */               atomicReferenceArray.compareAndSet(b, null, HostLocaleProviderAdapterImpl.getNumberPattern(b, str));
/*     */             }
/* 282 */             softReference = new SoftReference<>(atomicReferenceArray);
/* 283 */             HostLocaleProviderAdapterImpl.numberFormatCache.put(param1Locale, softReference);
/*     */           } 
/* 285 */           return atomicReferenceArray;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static DecimalFormatSymbolsProvider getDecimalFormatSymbolsProvider() {
/* 291 */     return new DecimalFormatSymbolsProvider()
/*     */       {
/*     */         public Locale[] getAvailableLocales()
/*     */         {
/* 295 */           return HostLocaleProviderAdapterImpl.getSupportedNativeDigitLocales();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isSupportedLocale(Locale param1Locale) {
/* 300 */           return HostLocaleProviderAdapterImpl.isSupportedNativeDigitLocale(param1Locale);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public DecimalFormatSymbols getInstance(Locale param1Locale) {
/* 307 */           SoftReference<DecimalFormatSymbols> softReference = (SoftReference)HostLocaleProviderAdapterImpl.decimalFormatSymbolsCache.get(param1Locale);
/*     */           DecimalFormatSymbols decimalFormatSymbols;
/* 309 */           if (softReference == null || (decimalFormatSymbols = softReference.get()) == null) {
/* 310 */             decimalFormatSymbols = new DecimalFormatSymbols(HostLocaleProviderAdapterImpl.getNumberLocale(param1Locale));
/* 311 */             String str = HostLocaleProviderAdapterImpl.removeExtensions(param1Locale).toLanguageTag();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 316 */             decimalFormatSymbols.setInternationalCurrencySymbol(HostLocaleProviderAdapterImpl.getInternationalCurrencySymbol(str, decimalFormatSymbols.getInternationalCurrencySymbol()));
/* 317 */             decimalFormatSymbols.setCurrencySymbol(HostLocaleProviderAdapterImpl.getCurrencySymbol(str, decimalFormatSymbols.getCurrencySymbol()));
/* 318 */             decimalFormatSymbols.setDecimalSeparator(HostLocaleProviderAdapterImpl.getDecimalSeparator(str, decimalFormatSymbols.getDecimalSeparator()));
/* 319 */             decimalFormatSymbols.setGroupingSeparator(HostLocaleProviderAdapterImpl.getGroupingSeparator(str, decimalFormatSymbols.getGroupingSeparator()));
/* 320 */             decimalFormatSymbols.setInfinity(HostLocaleProviderAdapterImpl.getInfinity(str, decimalFormatSymbols.getInfinity()));
/* 321 */             decimalFormatSymbols.setMinusSign(HostLocaleProviderAdapterImpl.getMinusSign(str, decimalFormatSymbols.getMinusSign()));
/* 322 */             decimalFormatSymbols.setMonetaryDecimalSeparator(HostLocaleProviderAdapterImpl.getMonetaryDecimalSeparator(str, decimalFormatSymbols.getMonetaryDecimalSeparator()));
/* 323 */             decimalFormatSymbols.setNaN(HostLocaleProviderAdapterImpl.getNaN(str, decimalFormatSymbols.getNaN()));
/* 324 */             decimalFormatSymbols.setPercent(HostLocaleProviderAdapterImpl.getPercent(str, decimalFormatSymbols.getPercent()));
/* 325 */             decimalFormatSymbols.setPerMill(HostLocaleProviderAdapterImpl.getPerMill(str, decimalFormatSymbols.getPerMill()));
/* 326 */             decimalFormatSymbols.setZeroDigit(HostLocaleProviderAdapterImpl.getZeroDigit(str, decimalFormatSymbols.getZeroDigit()));
/* 327 */             softReference = new SoftReference<>(decimalFormatSymbols);
/* 328 */             HostLocaleProviderAdapterImpl.decimalFormatSymbolsCache.put(param1Locale, softReference);
/*     */           } 
/* 330 */           return (DecimalFormatSymbols)decimalFormatSymbols.clone();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static CalendarDataProvider getCalendarDataProvider() {
/* 336 */     return new CalendarDataProvider()
/*     */       {
/*     */         public Locale[] getAvailableLocales() {
/* 339 */           return HostLocaleProviderAdapterImpl.getSupportedCalendarLocales();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isSupportedLocale(Locale param1Locale) {
/* 344 */           return HostLocaleProviderAdapterImpl.isSupportedCalendarLocale(param1Locale);
/*     */         }
/*     */ 
/*     */         
/*     */         public int getFirstDayOfWeek(Locale param1Locale) {
/* 349 */           int i = HostLocaleProviderAdapterImpl.getCalendarDataValue(HostLocaleProviderAdapterImpl
/* 350 */               .removeExtensions(param1Locale).toLanguageTag(), 0);
/*     */           
/* 352 */           if (i != -1) {
/* 353 */             return (i + 1) % 7 + 1;
/*     */           }
/* 355 */           return 0;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int getMinimalDaysInFirstWeek(Locale param1Locale) {
/* 361 */           return 0;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static CalendarProvider getCalendarProvider() {
/* 367 */     return new CalendarProvider()
/*     */       {
/*     */         public Locale[] getAvailableLocales() {
/* 370 */           return HostLocaleProviderAdapterImpl.getSupportedCalendarLocales();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isSupportedLocale(Locale param1Locale) {
/* 375 */           return HostLocaleProviderAdapterImpl.isSupportedCalendarLocale(param1Locale);
/*     */         }
/*     */ 
/*     */         
/*     */         public Calendar getInstance(TimeZone param1TimeZone, Locale param1Locale) {
/* 380 */           return (new Calendar.Builder())
/* 381 */             .setLocale(HostLocaleProviderAdapterImpl.getCalendarLocale(param1Locale))
/* 382 */             .setTimeZone(param1TimeZone)
/* 383 */             .setInstant(System.currentTimeMillis())
/* 384 */             .build();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static CurrencyNameProvider getCurrencyNameProvider() {
/* 390 */     return new CurrencyNameProvider()
/*     */       {
/*     */         public Locale[] getAvailableLocales() {
/* 393 */           return HostLocaleProviderAdapterImpl.supportedLocale;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean isSupportedLocale(Locale param1Locale) {
/* 399 */           return (HostLocaleProviderAdapterImpl.supportedLocaleSet.contains(param1Locale.stripExtensions()) && param1Locale
/* 400 */             .getLanguage().equals(HostLocaleProviderAdapterImpl.nativeDisplayLanguage));
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public String getSymbol(String param1String, Locale param1Locale) {
/*     */           try {
/* 410 */             if (Currency.getInstance(param1Locale).getCurrencyCode()
/* 411 */               .equals(param1String)) {
/* 412 */               return HostLocaleProviderAdapterImpl.getDisplayString(param1Locale.toLanguageTag(), 1, param1String);
/*     */             }
/*     */           }
/* 415 */           catch (IllegalArgumentException illegalArgumentException) {}
/* 416 */           return null;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public String getDisplayName(String param1String, Locale param1Locale) {
/*     */           try {
/* 426 */             if (Currency.getInstance(param1Locale).getCurrencyCode()
/* 427 */               .equals(param1String)) {
/* 428 */               return HostLocaleProviderAdapterImpl.getDisplayString(param1Locale.toLanguageTag(), 0, param1String);
/*     */             }
/*     */           }
/* 431 */           catch (IllegalArgumentException illegalArgumentException) {}
/* 432 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static LocaleNameProvider getLocaleNameProvider() {
/* 438 */     return new LocaleNameProvider()
/*     */       {
/*     */         public Locale[] getAvailableLocales() {
/* 441 */           return HostLocaleProviderAdapterImpl.supportedLocale;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isSupportedLocale(Locale param1Locale) {
/* 446 */           return (HostLocaleProviderAdapterImpl.supportedLocaleSet.contains(param1Locale.stripExtensions()) && param1Locale
/* 447 */             .getLanguage().equals(HostLocaleProviderAdapterImpl.nativeDisplayLanguage));
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public String getDisplayLanguage(String param1String, Locale param1Locale) {
/* 454 */           return HostLocaleProviderAdapterImpl.getDisplayString(param1Locale.toLanguageTag(), 2, param1String);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public String getDisplayCountry(String param1String, Locale param1Locale) {
/* 462 */           return HostLocaleProviderAdapterImpl.getDisplayString(param1Locale.toLanguageTag(), 4, HostLocaleProviderAdapterImpl
/* 463 */               .nativeDisplayLanguage + "-" + param1String);
/*     */         }
/*     */ 
/*     */         
/*     */         public String getDisplayScript(String param1String, Locale param1Locale) {
/* 468 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getDisplayVariant(String param1String, Locale param1Locale) {
/* 473 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static String convertDateTimePattern(String paramString) {
/* 480 */     String str = paramString.replaceAll("dddd", "EEEE");
/* 481 */     str = str.replaceAll("ddd", "EEE");
/* 482 */     str = str.replaceAll("tt", "aa");
/* 483 */     str = str.replaceAll("g", "GG");
/* 484 */     return str;
/*     */   }
/*     */   
/*     */   private static Locale[] getSupportedCalendarLocales() {
/* 488 */     if (supportedLocale.length != 0 && supportedLocaleSet
/* 489 */       .contains(Locale.JAPAN) && 
/* 490 */       isJapaneseCalendar()) {
/* 491 */       Locale[] arrayOfLocale = new Locale[supportedLocale.length + 1];
/* 492 */       arrayOfLocale[0] = JRELocaleConstants.JA_JP_JP;
/* 493 */       System.arraycopy(supportedLocale, 0, arrayOfLocale, 1, supportedLocale.length);
/* 494 */       return arrayOfLocale;
/*     */     } 
/* 496 */     return supportedLocale;
/*     */   }
/*     */   
/*     */   private static boolean isSupportedCalendarLocale(Locale paramLocale) {
/* 500 */     Locale locale = paramLocale;
/*     */     
/* 502 */     if (locale.hasExtensions() || locale.getVariant() != "")
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 507 */       locale = (new Locale.Builder()).setLocale(paramLocale).clearExtensions().build();
/*     */     }
/*     */     
/* 510 */     if (!supportedLocaleSet.contains(locale)) {
/* 511 */       return false;
/*     */     }
/*     */     
/* 514 */     int i = getCalendarID(locale.toLanguageTag());
/* 515 */     if (i <= 0 || i >= calIDToLDML.length) {
/* 516 */       return false;
/*     */     }
/*     */     
/* 519 */     String str1 = paramLocale.getUnicodeLocaleType("ca");
/*     */     
/* 521 */     String str2 = calIDToLDML[i].replaceFirst("_.*", "");
/*     */     
/* 523 */     if (str1 == null) {
/* 524 */       return Calendar.getAvailableCalendarTypes().contains(str2);
/*     */     }
/* 526 */     return str1.equals(str2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Locale[] getSupportedNativeDigitLocales() {
/* 531 */     if (supportedLocale.length != 0 && supportedLocaleSet
/* 532 */       .contains(JRELocaleConstants.TH_TH) && 
/* 533 */       isNativeDigit("th-TH")) {
/* 534 */       Locale[] arrayOfLocale = new Locale[supportedLocale.length + 1];
/* 535 */       arrayOfLocale[0] = JRELocaleConstants.TH_TH_TH;
/* 536 */       System.arraycopy(supportedLocale, 0, arrayOfLocale, 1, supportedLocale.length);
/* 537 */       return arrayOfLocale;
/*     */     } 
/* 539 */     return supportedLocale;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isSupportedNativeDigitLocale(Locale paramLocale) {
/* 544 */     if (JRELocaleConstants.TH_TH_TH.equals(paramLocale)) {
/* 545 */       return isNativeDigit("th-TH");
/*     */     }
/*     */     
/* 548 */     String str = null;
/* 549 */     Locale locale = paramLocale;
/* 550 */     if (paramLocale.hasExtensions()) {
/* 551 */       str = paramLocale.getUnicodeLocaleType("nu");
/* 552 */       locale = paramLocale.stripExtensions();
/*     */     } 
/*     */     
/* 555 */     if (supportedLocaleSet.contains(locale)) {
/*     */       
/* 557 */       if (str == null || str.equals("latn"))
/* 558 */         return true; 
/* 559 */       if (paramLocale.getLanguage().equals("th")) {
/* 560 */         return ("thai".equals(str) && 
/* 561 */           isNativeDigit(paramLocale.toLanguageTag()));
/*     */       }
/*     */     } 
/*     */     
/* 565 */     return false;
/*     */   }
/*     */   
/*     */   private static Locale removeExtensions(Locale paramLocale) {
/* 569 */     return (new Locale.Builder()).setLocale(paramLocale).clearExtensions().build();
/*     */   }
/*     */   
/*     */   private static boolean isJapaneseCalendar() {
/* 573 */     return (getCalendarID("ja-JP") == 3);
/*     */   }
/*     */   
/*     */   private static Locale getCalendarLocale(Locale paramLocale) {
/* 577 */     int i = getCalendarID(paramLocale.toLanguageTag());
/* 578 */     if (i > 0 && i < calIDToLDML.length) {
/* 579 */       Locale.Builder builder = new Locale.Builder();
/* 580 */       String[] arrayOfString = calIDToLDML[i].split("_");
/* 581 */       if (arrayOfString.length > 1) {
/* 582 */         builder.setLocale(Locale.forLanguageTag(arrayOfString[1]));
/*     */       } else {
/* 584 */         builder.setLocale(paramLocale);
/*     */       } 
/* 586 */       builder.setUnicodeLocaleKeyword("ca", arrayOfString[0]);
/* 587 */       return builder.build();
/*     */     } 
/*     */     
/* 590 */     return paramLocale;
/*     */   }
/*     */   
/*     */   private static Locale getNumberLocale(Locale paramLocale) {
/* 594 */     if (JRELocaleConstants.TH_TH.equals(paramLocale) && 
/* 595 */       isNativeDigit("th-TH")) {
/* 596 */       Locale.Builder builder = (new Locale.Builder()).setLocale(paramLocale);
/* 597 */       builder.setUnicodeLocaleKeyword("nu", "thai");
/* 598 */       return builder.build();
/*     */     } 
/*     */ 
/*     */     
/* 602 */     return paramLocale;
/*     */   }
/*     */   
/*     */   private static native boolean initialize();
/*     */   
/*     */   private static native String getDefaultLocale(int paramInt);
/*     */   
/*     */   private static native String getDateTimePattern(int paramInt1, int paramInt2, String paramString);
/*     */   
/*     */   private static native int getCalendarID(String paramString);
/*     */   
/*     */   private static native String[] getAmPmStrings(String paramString, String[] paramArrayOfString);
/*     */   
/*     */   private static native String[] getEras(String paramString, String[] paramArrayOfString);
/*     */   
/*     */   private static native String[] getMonths(String paramString, String[] paramArrayOfString);
/*     */   
/*     */   private static native String[] getShortMonths(String paramString, String[] paramArrayOfString);
/*     */   
/*     */   private static native String[] getWeekdays(String paramString, String[] paramArrayOfString);
/*     */   
/*     */   private static native String[] getShortWeekdays(String paramString, String[] paramArrayOfString);
/*     */   
/*     */   private static native String getNumberPattern(int paramInt, String paramString);
/*     */   
/*     */   private static native boolean isNativeDigit(String paramString);
/*     */   
/*     */   private static native String getCurrencySymbol(String paramString1, String paramString2);
/*     */   
/*     */   private static native char getDecimalSeparator(String paramString, char paramChar);
/*     */   
/*     */   private static native char getGroupingSeparator(String paramString, char paramChar);
/*     */   
/*     */   private static native String getInfinity(String paramString1, String paramString2);
/*     */   
/*     */   private static native String getInternationalCurrencySymbol(String paramString1, String paramString2);
/*     */   
/*     */   private static native char getMinusSign(String paramString, char paramChar);
/*     */   
/*     */   private static native char getMonetaryDecimalSeparator(String paramString, char paramChar);
/*     */   
/*     */   private static native String getNaN(String paramString1, String paramString2);
/*     */   
/*     */   private static native char getPercent(String paramString, char paramChar);
/*     */   
/*     */   private static native char getPerMill(String paramString, char paramChar);
/*     */   
/*     */   private static native char getZeroDigit(String paramString, char paramChar);
/*     */   
/*     */   private static native int getCalendarDataValue(String paramString, int paramInt);
/*     */   
/*     */   private static native String getDisplayString(String paramString1, int paramInt, String paramString2);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\su\\util\locale\provider\HostLocaleProviderAdapterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */