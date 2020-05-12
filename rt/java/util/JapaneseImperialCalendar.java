/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import sun.util.calendar.BaseCalendar;
/*      */ import sun.util.calendar.CalendarDate;
/*      */ import sun.util.calendar.CalendarSystem;
/*      */ import sun.util.calendar.CalendarUtils;
/*      */ import sun.util.calendar.Era;
/*      */ import sun.util.calendar.Gregorian;
/*      */ import sun.util.calendar.LocalGregorianCalendar;
/*      */ import sun.util.calendar.ZoneInfo;
/*      */ import sun.util.locale.provider.CalendarDataUtility;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class JapaneseImperialCalendar
/*      */   extends Calendar
/*      */ {
/*      */   public static final int BEFORE_MEIJI = 0;
/*      */   public static final int MEIJI = 1;
/*      */   public static final int TAISHO = 2;
/*      */   public static final int SHOWA = 3;
/*      */   public static final int HEISEI = 4;
/*      */   private static final int REIWA = 5;
/*      */   private static final int EPOCH_OFFSET = 719163;
/*      */   private static final int EPOCH_YEAR = 1970;
/*      */   private static final int ONE_SECOND = 1000;
/*      */   private static final int ONE_MINUTE = 60000;
/*      */   private static final int ONE_HOUR = 3600000;
/*      */   private static final long ONE_DAY = 86400000L;
/*      */   private static final long ONE_WEEK = 604800000L;
/*  124 */   private static final LocalGregorianCalendar jcal = (LocalGregorianCalendar)CalendarSystem.forName("japanese");
/*      */ 
/*      */ 
/*      */   
/*  128 */   private static final Gregorian gcal = CalendarSystem.getGregorianCalendar();
/*      */ 
/*      */   
/*  131 */   private static final Era BEFORE_MEIJI_ERA = new Era("BeforeMeiji", "BM", Long.MIN_VALUE, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Era[] eras;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long[] sinceFixedDates;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int currentEra;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  170 */   static final int[] MIN_VALUES = new int[] { 0, -292275055, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, -46800000, 0 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  189 */   static final int[] LEAST_MAX_VALUES = new int[] { 0, 0, 0, 0, 4, 28, 0, 7, 4, 1, 11, 23, 59, 59, 999, 50400000, 1200000 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  208 */   static final int[] MAX_VALUES = new int[] { 0, 292278994, 11, 53, 6, 31, 366, 7, 6, 1, 11, 23, 59, 59, 999, 50400000, 7200000 };
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -3364572813905467929L;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient LocalGregorianCalendar.Date jdate;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient int[] zoneOffsets;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient int[] originalFields;
/*      */ 
/*      */   
/*      */   private transient long cachedFixedDate;
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  232 */     Era[] arrayOfEra = jcal.getEras();
/*  233 */     int i = arrayOfEra.length + 1;
/*  234 */     eras = new Era[i];
/*  235 */     sinceFixedDates = new long[i];
/*      */ 
/*      */ 
/*      */     
/*  239 */     byte b1 = 0;
/*  240 */     byte b2 = b1;
/*  241 */     sinceFixedDates[b1] = gcal.getFixedDate(BEFORE_MEIJI_ERA.getSinceDate());
/*  242 */     eras[b1++] = BEFORE_MEIJI_ERA;
/*  243 */     for (Era era : arrayOfEra) {
/*  244 */       if (era.getSince(TimeZone.NO_TIMEZONE) < System.currentTimeMillis()) {
/*  245 */         b2 = b1;
/*      */       }
/*  247 */       CalendarDate calendarDate = era.getSinceDate();
/*  248 */       sinceFixedDates[b1] = gcal.getFixedDate(calendarDate);
/*  249 */       eras[b1++] = era;
/*      */     } 
/*  251 */     currentEra = b2;
/*      */     
/*  253 */     MAX_VALUES[0] = eras.length - 1; LEAST_MAX_VALUES[0] = eras.length - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  258 */     int j = Integer.MAX_VALUE;
/*  259 */     int k = Integer.MAX_VALUE;
/*  260 */     Gregorian.Date date = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/*  261 */     for (byte b3 = 1; b3 < eras.length; b3++) {
/*  262 */       long l1 = sinceFixedDates[b3];
/*  263 */       CalendarDate calendarDate = eras[b3].getSinceDate();
/*  264 */       date.setDate(calendarDate.getYear(), 1, 1);
/*  265 */       long l2 = gcal.getFixedDate(date);
/*  266 */       if (l1 != l2) {
/*  267 */         k = Math.min((int)(l1 - l2) + 1, k);
/*      */       }
/*  269 */       date.setDate(calendarDate.getYear(), 12, 31);
/*  270 */       l2 = gcal.getFixedDate(date);
/*  271 */       if (l1 != l2) {
/*  272 */         k = Math.min((int)(l2 - l1) + 1, k);
/*      */       }
/*  274 */       LocalGregorianCalendar.Date date1 = getCalendarDate(l1 - 1L);
/*  275 */       int m = date1.getYear();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  280 */       if (date1.getMonth() != 1 || date1.getDayOfMonth() != 1) {
/*  281 */         m--;
/*      */       }
/*  283 */       j = Math.min(m, j);
/*      */     } 
/*  285 */     LEAST_MAX_VALUES[1] = j;
/*  286 */     LEAST_MAX_VALUES[6] = k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JapaneseImperialCalendar(TimeZone paramTimeZone, Locale paramLocale)
/*      */   {
/*  316 */     super(paramTimeZone, paramLocale);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1540 */     this.cachedFixedDate = Long.MIN_VALUE; this.jdate = jcal.newCalendarDate(paramTimeZone); setTimeInMillis(System.currentTimeMillis()); } public String getCalendarType() { return "japanese"; } public boolean equals(Object paramObject) { return (paramObject instanceof JapaneseImperialCalendar && super.equals(paramObject)); } public int hashCode() { return super.hashCode() ^ this.jdate.hashCode(); } public void add(int paramInt1, int paramInt2) { if (paramInt2 == 0) return;  if (paramInt1 < 0 || paramInt1 >= 15) throw new IllegalArgumentException();  complete(); if (paramInt1 == 1) { LocalGregorianCalendar.Date date = (LocalGregorianCalendar.Date)this.jdate.clone(); date.addYear(paramInt2); pinDayOfMonth(date); set(0, getEraIndex(date)); set(1, date.getYear()); set(2, date.getMonth() - 1); set(5, date.getDayOfMonth()); } else if (paramInt1 == 2) { LocalGregorianCalendar.Date date = (LocalGregorianCalendar.Date)this.jdate.clone(); date.addMonth(paramInt2); pinDayOfMonth(date); set(0, getEraIndex(date)); set(1, date.getYear()); set(2, date.getMonth() - 1); set(5, date.getDayOfMonth()); } else if (paramInt1 == 0) { int i = internalGet(0) + paramInt2; if (i < 0) { i = 0; } else if (i > eras.length - 1) { i = eras.length - 1; }  set(0, i); } else { long l1 = paramInt2; long l2 = 0L; switch (paramInt1) { case 10: case 11: l1 *= 3600000L; break;case 12: l1 *= 60000L; break;case 13: l1 *= 1000L; break;case 3: case 4: case 8: l1 *= 7L; break;case 9: l1 = (paramInt2 / 2); l2 = (12 * paramInt2 % 2); break; }  if (paramInt1 >= 10) { setTimeInMillis(this.time + l1); return; }  long l3 = this.cachedFixedDate; l2 += internalGet(11); l2 *= 60L; l2 += internalGet(12); l2 *= 60L; l2 += internalGet(13); l2 *= 1000L; l2 += internalGet(14); if (l2 >= 86400000L) { l3++; l2 -= 86400000L; } else if (l2 < 0L) { l3--; l2 += 86400000L; }  l3 += l1; int i = internalGet(15) + internalGet(16); setTimeInMillis((l3 - 719163L) * 86400000L + l2 - i); i -= internalGet(15) + internalGet(16); if (i != 0) { setTimeInMillis(this.time + i); long l = this.cachedFixedDate; if (l != l3) setTimeInMillis(this.time - i);  }  }  } public void roll(int paramInt, boolean paramBoolean) { roll(paramInt, paramBoolean ? 1 : -1); } JapaneseImperialCalendar(TimeZone paramTimeZone, Locale paramLocale, boolean paramBoolean) { super(paramTimeZone, paramLocale); this.cachedFixedDate = Long.MIN_VALUE; this.jdate = jcal.newCalendarDate(paramTimeZone); }
/*      */   public void roll(int paramInt1, int paramInt2) { int m; boolean bool; long l2; int k; long l1; int n; long l3; int i2; long l5; int i1; long l4; LocalGregorianCalendar.Date date2; long l7; LocalGregorianCalendar.Date date1; long l6; int i4; long l8; int i3, i6; long l9; LocalGregorianCalendar.Date date3; int i5, i7; LocalGregorianCalendar.Date date4; long l10; int i8; LocalGregorianCalendar.Date date5; int i9; long l11; if (paramInt2 == 0) return;  if (paramInt1 < 0 || paramInt1 >= 15) throw new IllegalArgumentException();  complete(); int i = getMinimum(paramInt1); int j = getMaximum(paramInt1); switch (paramInt1) { case 10: case 11: m = j + 1; n = internalGet(paramInt1); i2 = (n + paramInt2) % m; if (i2 < 0) i2 += m;  this.time += (3600000 * (i2 - n)); date2 = jcal.getCalendarDate(this.time, getZone()); if (internalGet(5) != date2.getDayOfMonth()) { date2.setEra(this.jdate.getEra()); date2.setDate(internalGet(1), internalGet(2) + 1, internalGet(5)); if (paramInt1 == 10) { assert internalGet(9) == 1; date2.addHours(12); }  this.time = jcal.getTime(date2); }  i4 = date2.getHours(); internalSet(paramInt1, i4 % m); if (paramInt1 == 10) { internalSet(11, i4); } else { internalSet(9, i4 / 12); internalSet(10, i4 % 12); }  i6 = date2.getZoneOffset(); i7 = date2.getDaylightSaving(); internalSet(15, i6 - i7); internalSet(16, i7); return;case 1: i = getActualMinimum(paramInt1); j = getActualMaximum(paramInt1); break;case 2: if (!isTransitionYear(this.jdate.getNormalizedYear())) { m = this.jdate.getYear(); if (m == getMaximum(1)) { LocalGregorianCalendar.Date date6 = jcal.getCalendarDate(this.time, getZone()); LocalGregorianCalendar.Date date7 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); j = date7.getMonth() - 1; int i10 = getRolledValue(internalGet(paramInt1), paramInt2, i, j); if (i10 == j) { date6.addYear(-400); date6.setMonth(i10 + 1); if (date6.getDayOfMonth() > date7.getDayOfMonth()) { date6.setDayOfMonth(date7.getDayOfMonth()); jcal.normalize(date6); }  if (date6.getDayOfMonth() == date7.getDayOfMonth() && date6.getTimeOfDay() > date7.getTimeOfDay()) { date6.setMonth(i10 + 1); date6.setDayOfMonth(date7.getDayOfMonth() - 1); jcal.normalize(date6); i10 = date6.getMonth() - 1; }  set(5, date6.getDayOfMonth()); }  set(2, i10); } else if (m == getMinimum(1)) { LocalGregorianCalendar.Date date6 = jcal.getCalendarDate(this.time, getZone()); LocalGregorianCalendar.Date date7 = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); i = date7.getMonth() - 1; int i10 = getRolledValue(internalGet(paramInt1), paramInt2, i, j); if (i10 == i) { date6.addYear(400); date6.setMonth(i10 + 1); if (date6.getDayOfMonth() < date7.getDayOfMonth()) { date6.setDayOfMonth(date7.getDayOfMonth()); jcal.normalize(date6); }  if (date6.getDayOfMonth() == date7.getDayOfMonth() && date6.getTimeOfDay() < date7.getTimeOfDay()) { date6.setMonth(i10 + 1); date6.setDayOfMonth(date7.getDayOfMonth() + 1); jcal.normalize(date6); i10 = date6.getMonth() - 1; }  set(5, date6.getDayOfMonth()); }  set(2, i10); } else { n = (internalGet(2) + paramInt2) % 12; if (n < 0) n += 12;  set(2, n); i2 = monthLength(n); if (internalGet(5) > i2) set(5, i2);  }  } else { m = getEraIndex(this.jdate); CalendarDate calendarDate = null; if (this.jdate.getYear() == 1) { calendarDate = eras[m].getSinceDate(); i = calendarDate.getMonth() - 1; } else if (m < eras.length - 1) { calendarDate = eras[m + 1].getSinceDate(); if (calendarDate.getYear() == this.jdate.getNormalizedYear()) { j = calendarDate.getMonth() - 1; if (calendarDate.getDayOfMonth() == 1) j--;  }  }  if (i == j) return;  i2 = getRolledValue(internalGet(paramInt1), paramInt2, i, j); set(2, i2); if (i2 == i) { if ((calendarDate.getMonth() != 1 || calendarDate.getDayOfMonth() != 1) && this.jdate.getDayOfMonth() < calendarDate.getDayOfMonth()) set(5, calendarDate.getDayOfMonth());  } else if (i2 == j && calendarDate.getMonth() - 1 == i2) { int i10 = calendarDate.getDayOfMonth(); if (this.jdate.getDayOfMonth() >= i10) set(5, i10 - 1);  }  }  return;case 3: m = this.jdate.getNormalizedYear(); j = getActualMaximum(3); set(7, internalGet(7)); n = internalGet(3); i2 = n + paramInt2; if (!isTransitionYear(this.jdate.getNormalizedYear())) { int i10 = this.jdate.getYear(); if (i10 == getMaximum(1)) { j = getActualMaximum(3); } else if (i10 == getMinimum(1)) { i = getActualMinimum(3); j = getActualMaximum(3); if (i2 > i && i2 < j) { set(3, i2); return; }  }  if (i2 > i && i2 < j) { set(3, i2); return; }  l8 = this.cachedFixedDate; long l = l8 - (7 * (n - i)); if (i10 != getMinimum(1)) { if (gcal.getYearFromFixedDate(l) != m) i++;  } else { LocalGregorianCalendar.Date date = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); if (l < jcal.getFixedDate(date)) i++;  }  l8 += (7 * (j - internalGet(3))); if (gcal.getYearFromFixedDate(l8) != m) j--;  break; }  l7 = this.cachedFixedDate; l9 = l7 - (7 * (n - i)); date4 = getCalendarDate(l9); if (date4.getEra() != this.jdate.getEra() || date4.getYear() != this.jdate.getYear()) i++;  l7 += (7 * (j - n)); jcal.getCalendarDateFromFixedDate(date4, l7); if (date4.getEra() != this.jdate.getEra() || date4.getYear() != this.jdate.getYear()) j--;  i2 = getRolledValue(n, paramInt2, i, j) - 1; date4 = getCalendarDate(l9 + (i2 * 7)); set(2, date4.getMonth() - 1); set(5, date4.getDayOfMonth()); return;case 4: bool = isTransitionYear(this.jdate.getNormalizedYear()); n = internalGet(7) - getFirstDayOfWeek(); if (n < 0) n += 7;  l5 = this.cachedFixedDate; if (bool) { l8 = getFixedDateMonth1(this.jdate, l5); i7 = actualMonthLength(); } else { l8 = l5 - internalGet(5) + 1L; i7 = jcal.getMonthLength(this.jdate); }  l10 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l8 + 6L, getFirstDayOfWeek()); if ((int)(l10 - l8) >= getMinimalDaysInFirstWeek()) l10 -= 7L;  j = getActualMaximum(paramInt1); i9 = getRolledValue(internalGet(paramInt1), paramInt2, 1, j) - 1; l11 = l10 + (i9 * 7) + n; if (l11 < l8) { l11 = l8; } else if (l11 >= l8 + i7) { l11 = l8 + i7 - 1L; }  set(5, (int)(l11 - l8) + 1); return;case 5: if (!isTransitionYear(this.jdate.getNormalizedYear())) { j = jcal.getMonthLength(this.jdate); break; }  l2 = getFixedDateMonth1(this.jdate, this.cachedFixedDate); i1 = getRolledValue((int)(this.cachedFixedDate - l2), paramInt2, 0, actualMonthLength() - 1); date1 = getCalendarDate(l2 + i1); assert getEraIndex(date1) == internalGetEra() && date1.getYear() == internalGet(1) && date1.getMonth() - 1 == internalGet(2); set(5, date1.getDayOfMonth()); return;case 6: j = getActualMaximum(paramInt1); if (!isTransitionYear(this.jdate.getNormalizedYear())) break;  k = getRolledValue(internalGet(6), paramInt2, i, j); l3 = this.cachedFixedDate - internalGet(6); date1 = getCalendarDate(l3 + k); assert getEraIndex(date1) == internalGetEra() && date1.getYear() == internalGet(1); set(2, date1.getMonth() - 1); set(5, date1.getDayOfMonth()); return;case 7: k = this.jdate.getNormalizedYear(); if (!isTransitionYear(k) && !isTransitionYear(k - 1)) { int i10 = internalGet(3); if (i10 > 1 && i10 < 52) { set(3, internalGet(3)); j = 7; break; }  }  paramInt2 %= 7; if (paramInt2 == 0) return;  l3 = this.cachedFixedDate; l6 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l3, getFirstDayOfWeek()); l3 += paramInt2; if (l3 < l6) { l3 += 7L; } else if (l3 >= l6 + 7L) { l3 -= 7L; }  date3 = getCalendarDate(l3); set(0, getEraIndex(date3)); set(date3.getYear(), date3.getMonth() - 1, date3.getDayOfMonth()); return;case 8: i = 1; if (!isTransitionYear(this.jdate.getNormalizedYear())) { k = internalGet(5); int i10 = jcal.getMonthLength(this.jdate); i1 = i10 % 7; j = i10 / 7; int i11 = (k - 1) % 7; if (i11 < i1)
/*      */             j++;  set(7, internalGet(7)); break; }  l1 = this.cachedFixedDate; l4 = getFixedDateMonth1(this.jdate, l1); i3 = actualMonthLength(); i5 = i3 % 7; j = i3 / 7; i7 = (int)(l1 - l4) % 7; if (i7 < i5)
/*      */           j++;  i8 = getRolledValue(internalGet(paramInt1), paramInt2, i, j) - 1; l1 = l4 + (i8 * 7) + i7; date5 = getCalendarDate(l1); set(5, date5.getDayOfMonth()); return; }  set(paramInt1, getRolledValue(internalGet(paramInt1), paramInt2, i, j)); }
/*      */   public String getDisplayName(int paramInt1, int paramInt2, Locale paramLocale) { if (!checkDisplayNameParams(paramInt1, paramInt2, 1, 4, paramLocale, 647))
/*      */       return null;  int i = get(paramInt1); if (paramInt1 == 1 && (getBaseStyle(paramInt2) != 2 || i != 1 || get(0) == 0))
/*      */       return null;  String str = CalendarDataUtility.retrieveFieldValueName(getCalendarType(), paramInt1, i, paramInt2, paramLocale); if (str == null && paramInt1 == 0 && i < eras.length) { Era era = eras[i]; str = (paramInt2 == 1) ? era.getAbbreviation() : era.getName(); }  return str; }
/*      */   public Map<String, Integer> getDisplayNames(int paramInt1, int paramInt2, Locale paramLocale) { if (!checkDisplayNameParams(paramInt1, paramInt2, 0, 4, paramLocale, 647))
/*      */       return null;  Map<String, Integer> map = CalendarDataUtility.retrieveFieldValueNames(getCalendarType(), paramInt1, paramInt2, paramLocale); if (map != null && paramInt1 == 0) { int i = map.size(); if (paramInt2 == 0) { HashSet hashSet = new HashSet(); for (String str : map.keySet())
/*      */           hashSet.add(map.get(str));  i = hashSet.size(); }  if (i < eras.length) { int j = getBaseStyle(paramInt2); for (int k = i; k < eras.length; k++) { Era era = eras[k]; if (j == 0 || j == 1 || j == 4)
/*      */             map.put(era.getAbbreviation(), Integer.valueOf(k));  if (j == 0 || j == 2)
/*      */             map.put(era.getName(), Integer.valueOf(k));  }  }  }  return map; }
/* 1552 */   public int getMinimum(int paramInt) { return MIN_VALUES[paramInt]; } protected void computeFields() { int i = 0;
/* 1553 */     if (isPartiallyNormalized()) {
/*      */       
/* 1555 */       i = getSetStateFields();
/* 1556 */       int j = (i ^ 0xFFFFFFFF) & 0x1FFFF;
/* 1557 */       if (j != 0 || this.cachedFixedDate == Long.MIN_VALUE) {
/* 1558 */         i |= computeFields(j, i & 0x18000);
/*      */         
/* 1560 */         assert i == 131071;
/*      */       } 
/*      */     } else {
/*      */       
/* 1564 */       i = 131071;
/* 1565 */       computeFields(i, 0);
/*      */     } 
/*      */     
/* 1568 */     setFieldsComputed(i); } public int getMaximum(int paramInt) { LocalGregorianCalendar.Date date; switch (paramInt) { case 1: date = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); return Math.max(LEAST_MAX_VALUES[1], date.getYear()); }  return MAX_VALUES[paramInt]; }
/*      */   public int getGreatestMinimum(int paramInt) { return (paramInt == 1) ? 1 : MIN_VALUES[paramInt]; }
/*      */   public int getLeastMaximum(int paramInt) { switch (paramInt) { case 1: return Math.min(LEAST_MAX_VALUES[1], getMaximum(1)); }  return LEAST_MAX_VALUES[paramInt]; }
/*      */   public int getActualMinimum(int paramInt) { LocalGregorianCalendar.Date date2; int k; long l1, l2; int m; long l3; if (!isFieldSet(14, paramInt)) return getMinimum(paramInt);  int i = 0; JapaneseImperialCalendar japaneseImperialCalendar = getNormalizedCalendar(); LocalGregorianCalendar.Date date1 = jcal.getCalendarDate(japaneseImperialCalendar.getTimeInMillis(), getZone()); int j = getEraIndex(date1); switch (paramInt) { case 1: if (j > 0) { i = 1; long l = eras[j].getSince(getZone()); LocalGregorianCalendar.Date date = jcal.getCalendarDate(l, getZone()); date1.setYear(date.getYear()); jcal.normalize(date1); assert date1.isLeapYear() == date.isLeapYear(); if (getYearOffsetInMillis(date1) < getYearOffsetInMillis(date)) i++;  break; }  i = getMinimum(paramInt); date2 = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); k = date2.getYear(); if (k > 400) k -= 400;  date1.setYear(k); jcal.normalize(date1); if (getYearOffsetInMillis(date1) < getYearOffsetInMillis(date2)) i++;  break;case 2: if (j > 1 && date1.getYear() == 1) { long l = eras[j].getSince(getZone()); LocalGregorianCalendar.Date date = jcal.getCalendarDate(l, getZone()); i = date.getMonth() - 1; if (date1.getDayOfMonth() < date.getDayOfMonth()) i++;  }  break;case 3: i = 1; date2 = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); date2.addYear(400); jcal.normalize(date2); date1.setEra(date2.getEra()); date1.setYear(date2.getYear()); jcal.normalize(date1); l1 = jcal.getFixedDate(date2); l2 = jcal.getFixedDate(date1); m = getWeekNumber(l1, l2); l3 = l2 - (7 * (m - 1)); if (l3 < l1 || (l3 == l1 && date1.getTimeOfDay() < date2.getTimeOfDay())) i++;  break; }  return i; }
/*      */   public int getActualMaximum(int paramInt) { LocalGregorianCalendar.Date date3; int k; LocalGregorianCalendar.Date date2; int m; LocalGregorianCalendar.Date date4; int n; BaseCalendar.Date date; int i1; if ((0x1FE81 & 1 << paramInt) != 0) return getMaximum(paramInt);  JapaneseImperialCalendar japaneseImperialCalendar = getNormalizedCalendar(); LocalGregorianCalendar.Date date1 = japaneseImperialCalendar.jdate; int i = date1.getNormalizedYear(); int j = -1; switch (paramInt) { case 2: j = 11; if (isTransitionYear(date1.getNormalizedYear())) { int i2 = getEraIndex(date1); if (date1.getYear() != 1) { i2++; assert i2 < eras.length; }  long l1 = sinceFixedDates[i2]; long l2 = japaneseImperialCalendar.cachedFixedDate; if (l2 < l1) { LocalGregorianCalendar.Date date5 = (LocalGregorianCalendar.Date)date1.clone(); jcal.getCalendarDateFromFixedDate(date5, l1 - 1L); j = date5.getMonth() - 1; }  } else { LocalGregorianCalendar.Date date5 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); if (date1.getEra() == date5.getEra() && date1.getYear() == date5.getYear()) j = date5.getMonth() - 1;  }  return j;case 5: j = jcal.getMonthLength(date1); return j;case 6: if (isTransitionYear(date1.getNormalizedYear())) { int i2 = getEraIndex(date1); if (date1.getYear() != 1) { i2++; assert i2 < eras.length; }  long l1 = sinceFixedDates[i2]; long l2 = japaneseImperialCalendar.cachedFixedDate; Gregorian.Date date5 = gcal.newCalendarDate(TimeZone.NO_TIMEZONE); date5.setDate(date1.getNormalizedYear(), 1, 1); if (l2 < l1) { j = (int)(l1 - gcal.getFixedDate(date5)); } else { date5.addYear(1); j = (int)(gcal.getFixedDate(date5) - l1); }  } else { LocalGregorianCalendar.Date date5 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); if (date1.getEra() == date5.getEra() && date1.getYear() == date5.getYear()) { long l1 = jcal.getFixedDate(date5); long l2 = getFixedDateJan1(date5, l1); j = (int)(l1 - l2) + 1; } else if (date1.getYear() == getMinimum(1)) { date4 = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); long l1 = jcal.getFixedDate(date4); date4.addYear(1); date4.setMonth(1).setDayOfMonth(1); jcal.normalize(date4); long l2 = jcal.getFixedDate(date4); j = (int)(l2 - l1); } else { j = jcal.getYearLength(date1); }  }  return j;case 3: if (!isTransitionYear(date1.getNormalizedYear())) { LocalGregorianCalendar.Date date5 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); if (date1.getEra() == date5.getEra() && date1.getYear() == date5.getYear()) { long l1 = jcal.getFixedDate(date5); long l2 = getFixedDateJan1(date5, l1); j = getWeekNumber(l2, l1); } else if (date1.getEra() == null && date1.getYear() == getMinimum(1)) { date4 = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); date4.addYear(400); jcal.normalize(date4); date5.setEra(date4.getEra()); date5.setDate(date4.getYear() + 1, 1, 1); jcal.normalize(date5); long l1 = jcal.getFixedDate(date4); long l2 = jcal.getFixedDate(date5); long l3 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l2 + 6L, getFirstDayOfWeek()); int i2 = (int)(l3 - l2); if (i2 >= getMinimalDaysInFirstWeek()) l3 -= 7L;  j = getWeekNumber(l1, l3); } else { Gregorian.Date date6 = gcal.newCalendarDate(TimeZone.NO_TIMEZONE); date6.setDate(date1.getNormalizedYear(), 1, 1); int i2 = gcal.getDayOfWeek(date6); i2 -= getFirstDayOfWeek(); if (i2 < 0)
/*      */               i2 += 7;  j = 52; int i3 = i2 + getMinimalDaysInFirstWeek() - 1; if (i3 == 6 || (date1.isLeapYear() && (i3 == 5 || i3 == 12)))
/*      */               j++;  }  } else { if (japaneseImperialCalendar == this)
/*      */             japaneseImperialCalendar = (JapaneseImperialCalendar)japaneseImperialCalendar.clone();  int i2 = getActualMaximum(6); japaneseImperialCalendar.set(6, i2); j = japaneseImperialCalendar.get(3); if (j == 1 && i2 > 7) { japaneseImperialCalendar.add(3, -1); j = japaneseImperialCalendar.get(3); }  }  return j;case 4: date3 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); if (date1.getEra() != date3.getEra() || date1.getYear() != date3.getYear()) { Gregorian.Date date5 = gcal.newCalendarDate(TimeZone.NO_TIMEZONE); date5.setDate(date1.getNormalizedYear(), date1.getMonth(), 1); int i2 = gcal.getDayOfWeek(date5); int i3 = gcal.getMonthLength(date5); i2 -= getFirstDayOfWeek(); if (i2 < 0)
/*      */             i2 += 7;  int i4 = 7 - i2; j = 3; if (i4 >= getMinimalDaysInFirstWeek())
/*      */             j++;  i3 -= i4 + 21; if (i3 > 0) { j++; if (i3 > 7)
/*      */               j++;  }  } else { long l1 = jcal.getFixedDate(date3); long l2 = l1 - date3.getDayOfMonth() + 1L; j = getWeekNumber(l2, l1); }  return j;case 8: n = date1.getDayOfWeek(); date = (BaseCalendar.Date)date1.clone(); k = jcal.getMonthLength(date); date.setDayOfMonth(1); jcal.normalize(date); m = date.getDayOfWeek(); i1 = n - m; if (i1 < 0)
/*      */           i1 += 7;  k -= i1; j = (k + 6) / 7; return j;case 1: date2 = jcal.getCalendarDate(japaneseImperialCalendar.getTimeInMillis(), getZone()); n = getEraIndex(date1); if (n == eras.length - 1) { date4 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); j = date4.getYear(); if (j > 400)
/*      */             date2.setYear(j - 400);  } else { date4 = jcal.getCalendarDate(eras[n + 1].getSince(getZone()) - 1L, getZone()); j = date4.getYear(); date2.setYear(j); }  jcal.normalize(date2); if (getYearOffsetInMillis(date2) > getYearOffsetInMillis(date4))
/*      */           j--;  return j; }  throw new ArrayIndexOutOfBoundsException(paramInt); }
/*      */   private long getYearOffsetInMillis(CalendarDate paramCalendarDate) { long l = (jcal.getDayOfYear(paramCalendarDate) - 1L) * 86400000L; return l + paramCalendarDate.getTimeOfDay() - paramCalendarDate.getZoneOffset(); }
/*      */   public Object clone() { JapaneseImperialCalendar japaneseImperialCalendar = (JapaneseImperialCalendar)super.clone(); japaneseImperialCalendar.jdate = (LocalGregorianCalendar.Date)this.jdate.clone(); japaneseImperialCalendar.originalFields = null; japaneseImperialCalendar.zoneOffsets = null; return japaneseImperialCalendar; }
/*      */   public TimeZone getTimeZone() { TimeZone timeZone = super.getTimeZone(); this.jdate.setZone(timeZone); return timeZone; }
/*      */   public void setTimeZone(TimeZone paramTimeZone) { super.setTimeZone(paramTimeZone); this.jdate.setZone(paramTimeZone); }
/* 1586 */   private int computeFields(int paramInt1, int paramInt2) { int i = 0;
/* 1587 */     TimeZone timeZone = getZone();
/* 1588 */     if (this.zoneOffsets == null) {
/* 1589 */       this.zoneOffsets = new int[2];
/*      */     }
/* 1591 */     if (paramInt2 != 98304) {
/* 1592 */       if (timeZone instanceof ZoneInfo) {
/* 1593 */         i = ((ZoneInfo)timeZone).getOffsets(this.time, this.zoneOffsets);
/*      */       } else {
/* 1595 */         i = timeZone.getOffset(this.time);
/* 1596 */         this.zoneOffsets[0] = timeZone.getRawOffset();
/* 1597 */         this.zoneOffsets[1] = i - this.zoneOffsets[0];
/*      */       } 
/*      */     }
/* 1600 */     if (paramInt2 != 0) {
/* 1601 */       if (isFieldSet(paramInt2, 15)) {
/* 1602 */         this.zoneOffsets[0] = internalGet(15);
/*      */       }
/* 1604 */       if (isFieldSet(paramInt2, 16)) {
/* 1605 */         this.zoneOffsets[1] = internalGet(16);
/*      */       }
/* 1607 */       i = this.zoneOffsets[0] + this.zoneOffsets[1];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1613 */     long l = i / 86400000L;
/* 1614 */     int j = i % 86400000;
/* 1615 */     l += this.time / 86400000L;
/* 1616 */     j += (int)(this.time % 86400000L);
/* 1617 */     if (j >= 86400000L) {
/* 1618 */       j = (int)(j - 86400000L);
/* 1619 */       l++;
/*      */     } else {
/* 1621 */       while (j < 0) {
/* 1622 */         j = (int)(j + 86400000L);
/* 1623 */         l--;
/*      */       } 
/*      */     } 
/* 1626 */     l += 719163L;
/*      */ 
/*      */     
/* 1629 */     if (l != this.cachedFixedDate || l < 0L) {
/* 1630 */       jcal.getCalendarDateFromFixedDate(this.jdate, l);
/* 1631 */       this.cachedFixedDate = l;
/*      */     } 
/* 1633 */     int k = getEraIndex(this.jdate);
/* 1634 */     int m = this.jdate.getYear();
/*      */ 
/*      */     
/* 1637 */     internalSet(0, k);
/* 1638 */     internalSet(1, m);
/* 1639 */     int n = paramInt1 | 0x3;
/*      */     
/* 1641 */     int i1 = this.jdate.getMonth() - 1;
/* 1642 */     int i2 = this.jdate.getDayOfMonth();
/*      */ 
/*      */     
/* 1645 */     if ((paramInt1 & 0xA4) != 0) {
/*      */       
/* 1647 */       internalSet(2, i1);
/* 1648 */       internalSet(5, i2);
/* 1649 */       internalSet(7, this.jdate.getDayOfWeek());
/* 1650 */       n |= 0xA4;
/*      */     } 
/*      */     
/* 1653 */     if ((paramInt1 & 0x7E00) != 0) {
/*      */       
/* 1655 */       if (j != 0) {
/* 1656 */         int i3 = j / 3600000;
/* 1657 */         internalSet(11, i3);
/* 1658 */         internalSet(9, i3 / 12);
/* 1659 */         internalSet(10, i3 % 12);
/* 1660 */         int i4 = j % 3600000;
/* 1661 */         internalSet(12, i4 / 60000);
/* 1662 */         i4 %= 60000;
/* 1663 */         internalSet(13, i4 / 1000);
/* 1664 */         internalSet(14, i4 % 1000);
/*      */       } else {
/* 1666 */         internalSet(11, 0);
/* 1667 */         internalSet(9, 0);
/* 1668 */         internalSet(10, 0);
/* 1669 */         internalSet(12, 0);
/* 1670 */         internalSet(13, 0);
/* 1671 */         internalSet(14, 0);
/*      */       } 
/* 1673 */       n |= 0x7E00;
/*      */     } 
/*      */ 
/*      */     
/* 1677 */     if ((paramInt1 & 0x18000) != 0) {
/* 1678 */       internalSet(15, this.zoneOffsets[0]);
/* 1679 */       internalSet(16, this.zoneOffsets[1]);
/* 1680 */       n |= 0x18000;
/*      */     } 
/*      */     
/* 1683 */     if ((paramInt1 & 0x158) != 0) {
/*      */       int i4; long l1;
/* 1685 */       int i3 = this.jdate.getNormalizedYear();
/*      */ 
/*      */       
/* 1688 */       boolean bool = isTransitionYear(this.jdate.getNormalizedYear());
/*      */ 
/*      */       
/* 1691 */       if (bool) {
/* 1692 */         l1 = getFixedDateJan1(this.jdate, l);
/* 1693 */         i4 = (int)(l - l1) + 1;
/* 1694 */       } else if (i3 == MIN_VALUES[1]) {
/* 1695 */         LocalGregorianCalendar.Date date = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/* 1696 */         l1 = jcal.getFixedDate(date);
/* 1697 */         i4 = (int)(l - l1) + 1;
/*      */       } else {
/* 1699 */         i4 = (int)jcal.getDayOfYear(this.jdate);
/* 1700 */         l1 = l - i4 + 1L;
/*      */       } 
/*      */       
/* 1703 */       long l2 = bool ? getFixedDateMonth1(this.jdate, l) : (l - i2 + 1L);
/*      */       
/* 1705 */       internalSet(6, i4);
/* 1706 */       internalSet(8, (i2 - 1) / 7 + 1);
/*      */       
/* 1708 */       int i5 = getWeekNumber(l1, l);
/*      */ 
/*      */ 
/*      */       
/* 1712 */       if (i5 == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1720 */         long l4, l3 = l1 - 1L;
/*      */         
/* 1722 */         LocalGregorianCalendar.Date date = getCalendarDate(l3);
/* 1723 */         if (!bool && !isTransitionYear(date.getNormalizedYear())) {
/* 1724 */           l4 = l1 - 365L;
/* 1725 */           if (date.isLeapYear()) {
/* 1726 */             l4--;
/*      */           }
/* 1728 */         } else if (bool) {
/* 1729 */           if (this.jdate.getYear() == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1735 */             if (k > 5) {
/* 1736 */               CalendarDate calendarDate = eras[k - 1].getSinceDate();
/* 1737 */               if (i3 == calendarDate.getYear()) {
/* 1738 */                 date.setMonth(calendarDate.getMonth()).setDayOfMonth(calendarDate.getDayOfMonth());
/*      */               }
/*      */             } else {
/* 1741 */               date.setMonth(1).setDayOfMonth(1);
/*      */             } 
/* 1743 */             jcal.normalize(date);
/* 1744 */             l4 = jcal.getFixedDate(date);
/*      */           } else {
/* 1746 */             l4 = l1 - 365L;
/* 1747 */             if (date.isLeapYear()) {
/* 1748 */               l4--;
/*      */             }
/*      */           } 
/*      */         } else {
/* 1752 */           CalendarDate calendarDate = eras[getEraIndex(this.jdate)].getSinceDate();
/* 1753 */           date.setMonth(calendarDate.getMonth()).setDayOfMonth(calendarDate.getDayOfMonth());
/* 1754 */           jcal.normalize(date);
/* 1755 */           l4 = jcal.getFixedDate(date);
/*      */         } 
/* 1757 */         i5 = getWeekNumber(l4, l3);
/*      */       }
/* 1759 */       else if (!bool) {
/*      */         
/* 1761 */         if (i5 >= 52) {
/* 1762 */           long l3 = l1 + 365L;
/* 1763 */           if (this.jdate.isLeapYear()) {
/* 1764 */             l3++;
/*      */           }
/* 1766 */           long l4 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l3 + 6L, 
/* 1767 */               getFirstDayOfWeek());
/* 1768 */           int i6 = (int)(l4 - l3);
/* 1769 */           if (i6 >= getMinimalDaysInFirstWeek() && l >= l4 - 7L)
/*      */           {
/* 1771 */             i5 = 1; } 
/*      */         } 
/*      */       } else {
/*      */         long l3;
/* 1775 */         LocalGregorianCalendar.Date date = (LocalGregorianCalendar.Date)this.jdate.clone();
/*      */         
/* 1777 */         if (this.jdate.getYear() == 1) {
/* 1778 */           date.addYear(1);
/* 1779 */           date.setMonth(1).setDayOfMonth(1);
/* 1780 */           l3 = jcal.getFixedDate(date);
/*      */         } else {
/* 1782 */           int i7 = getEraIndex(date) + 1;
/* 1783 */           CalendarDate calendarDate = eras[i7].getSinceDate();
/* 1784 */           date.setEra(eras[i7]);
/* 1785 */           date.setDate(1, calendarDate.getMonth(), calendarDate.getDayOfMonth());
/* 1786 */           jcal.normalize(date);
/* 1787 */           l3 = jcal.getFixedDate(date);
/*      */         } 
/* 1789 */         long l4 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l3 + 6L, 
/* 1790 */             getFirstDayOfWeek());
/* 1791 */         int i6 = (int)(l4 - l3);
/* 1792 */         if (i6 >= getMinimalDaysInFirstWeek() && l >= l4 - 7L)
/*      */         {
/* 1794 */           i5 = 1;
/*      */         }
/*      */       } 
/*      */       
/* 1798 */       internalSet(3, i5);
/* 1799 */       internalSet(4, getWeekNumber(l2, l));
/* 1800 */       n |= 0x158;
/*      */     } 
/* 1802 */     return n; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getWeekNumber(long paramLong1, long paramLong2) {
/* 1817 */     long l = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(paramLong1 + 6L, 
/* 1818 */         getFirstDayOfWeek());
/* 1819 */     int i = (int)(l - paramLong1);
/* 1820 */     assert i <= 7;
/* 1821 */     if (i >= getMinimalDaysInFirstWeek()) {
/* 1822 */       l -= 7L;
/*      */     }
/* 1824 */     int j = (int)(paramLong2 - l);
/* 1825 */     if (j >= 0) {
/* 1826 */       return j / 7 + 1;
/*      */     }
/* 1828 */     return CalendarUtils.floorDivide(j, 7) + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void computeTime() {
/*      */     byte b1, b2;
/* 1842 */     if (!isLenient()) {
/* 1843 */       if (this.originalFields == null) {
/* 1844 */         this.originalFields = new int[17];
/*      */       }
/* 1846 */       for (byte b = 0; b < 17; b++) {
/* 1847 */         b1 = internalGet(b);
/* 1848 */         if (isExternallySet(b))
/*      */         {
/* 1850 */           if (b1 < getMinimum(b) || b1 > getMaximum(b)) {
/* 1851 */             throw new IllegalArgumentException(getFieldName(b));
/*      */           }
/*      */         }
/* 1854 */         this.originalFields[b] = b1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1860 */     int i = selectFields();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1865 */     if (isSet(0)) {
/* 1866 */       b2 = internalGet(0);
/* 1867 */       b1 = isSet(1) ? internalGet(1) : 1;
/*      */     }
/* 1869 */     else if (isSet(1)) {
/* 1870 */       b2 = currentEra;
/* 1871 */       b1 = internalGet(1);
/*      */     } else {
/*      */       
/* 1874 */       b2 = 3;
/* 1875 */       b1 = 45;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1881 */     long l1 = 0L;
/* 1882 */     if (isFieldSet(i, 11)) {
/* 1883 */       l1 += internalGet(11);
/*      */     } else {
/* 1885 */       l1 += internalGet(10);
/*      */       
/* 1887 */       if (isFieldSet(i, 9)) {
/* 1888 */         l1 += (12 * internalGet(9));
/*      */       }
/*      */     } 
/* 1891 */     l1 *= 60L;
/* 1892 */     l1 += internalGet(12);
/* 1893 */     l1 *= 60L;
/* 1894 */     l1 += internalGet(13);
/* 1895 */     l1 *= 1000L;
/* 1896 */     l1 += internalGet(14);
/*      */ 
/*      */ 
/*      */     
/* 1900 */     long l2 = l1 / 86400000L;
/* 1901 */     l1 %= 86400000L;
/* 1902 */     while (l1 < 0L) {
/* 1903 */       l1 += 86400000L;
/* 1904 */       l2--;
/*      */     } 
/*      */ 
/*      */     
/* 1908 */     l2 += getFixedDate(b2, b1, i);
/*      */ 
/*      */     
/* 1911 */     long l3 = (l2 - 719163L) * 86400000L + l1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1926 */     TimeZone timeZone = getZone();
/* 1927 */     if (this.zoneOffsets == null) {
/* 1928 */       this.zoneOffsets = new int[2];
/*      */     }
/* 1930 */     int j = i & 0x18000;
/* 1931 */     if (j != 98304) {
/* 1932 */       if (timeZone instanceof ZoneInfo) {
/* 1933 */         ((ZoneInfo)timeZone).getOffsetsByWall(l3, this.zoneOffsets);
/*      */       } else {
/* 1935 */         timeZone.getOffsets(l3 - timeZone.getRawOffset(), this.zoneOffsets);
/*      */       } 
/*      */     }
/* 1938 */     if (j != 0) {
/* 1939 */       if (isFieldSet(j, 15)) {
/* 1940 */         this.zoneOffsets[0] = internalGet(15);
/*      */       }
/* 1942 */       if (isFieldSet(j, 16)) {
/* 1943 */         this.zoneOffsets[1] = internalGet(16);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1948 */     l3 -= (this.zoneOffsets[0] + this.zoneOffsets[1]);
/*      */ 
/*      */     
/* 1951 */     this.time = l3;
/*      */     
/* 1953 */     int k = computeFields(i | getSetStateFields(), j);
/*      */     
/* 1955 */     if (!isLenient()) {
/* 1956 */       for (byte b = 0; b < 17; b++) {
/* 1957 */         if (isExternallySet(b))
/*      */         {
/*      */           
/* 1960 */           if (this.originalFields[b] != internalGet(b)) {
/* 1961 */             int m = internalGet(b);
/*      */             
/* 1963 */             System.arraycopy(this.originalFields, 0, this.fields, 0, this.fields.length);
/* 1964 */             throw new IllegalArgumentException(getFieldName(b) + "=" + m + ", expected " + this.originalFields[b]);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/* 1969 */     setFieldsNormalized(k);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long getFixedDate(int paramInt1, int paramInt2, int paramInt3) {
/* 1984 */     int i = 0;
/* 1985 */     int j = 1;
/* 1986 */     if (isFieldSet(paramInt3, 2)) {
/*      */ 
/*      */       
/* 1989 */       i = internalGet(2);
/*      */ 
/*      */       
/* 1992 */       if (i > 11) {
/* 1993 */         paramInt2 += i / 12;
/* 1994 */         i %= 12;
/* 1995 */       } else if (i < 0) {
/* 1996 */         int[] arrayOfInt = new int[1];
/* 1997 */         paramInt2 += CalendarUtils.floorDivide(i, 12, arrayOfInt);
/* 1998 */         i = arrayOfInt[0];
/*      */       }
/*      */     
/* 2001 */     } else if (paramInt2 == 1 && paramInt1 != 0) {
/* 2002 */       CalendarDate calendarDate = eras[paramInt1].getSinceDate();
/* 2003 */       i = calendarDate.getMonth() - 1;
/* 2004 */       j = calendarDate.getDayOfMonth();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2009 */     if (paramInt2 == MIN_VALUES[1]) {
/* 2010 */       LocalGregorianCalendar.Date date1 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/* 2011 */       int k = date1.getMonth() - 1;
/* 2012 */       if (i < k) {
/* 2013 */         i = k;
/*      */       }
/* 2015 */       if (i == k) {
/* 2016 */         j = date1.getDayOfMonth();
/*      */       }
/*      */     } 
/*      */     
/* 2020 */     LocalGregorianCalendar.Date date = jcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 2021 */     date.setEra((paramInt1 > 0) ? eras[paramInt1] : null);
/* 2022 */     date.setDate(paramInt2, i + 1, j);
/* 2023 */     jcal.normalize(date);
/*      */ 
/*      */ 
/*      */     
/* 2027 */     long l = jcal.getFixedDate(date);
/*      */     
/* 2029 */     if (isFieldSet(paramInt3, 2)) {
/*      */       
/* 2031 */       if (isFieldSet(paramInt3, 5)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2038 */         if (isSet(5))
/*      */         {
/*      */           
/* 2041 */           l += internalGet(5);
/* 2042 */           l -= j;
/*      */         }
/*      */       
/* 2045 */       } else if (isFieldSet(paramInt3, 4)) {
/* 2046 */         long l1 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l + 6L, 
/* 2047 */             getFirstDayOfWeek());
/*      */ 
/*      */         
/* 2050 */         if (l1 - l >= getMinimalDaysInFirstWeek()) {
/* 2051 */           l1 -= 7L;
/*      */         }
/* 2053 */         if (isFieldSet(paramInt3, 7)) {
/* 2054 */           l1 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l1 + 6L, 
/* 2055 */               internalGet(7));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2060 */         l = l1 + (7 * (internalGet(4) - 1));
/*      */       } else {
/*      */         int k; byte b;
/* 2063 */         if (isFieldSet(paramInt3, 7)) {
/* 2064 */           k = internalGet(7);
/*      */         } else {
/* 2066 */           k = getFirstDayOfWeek();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2072 */         if (isFieldSet(paramInt3, 8)) {
/* 2073 */           b = internalGet(8);
/*      */         } else {
/* 2075 */           b = 1;
/*      */         } 
/* 2077 */         if (b) {
/* 2078 */           l = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l + (7 * b) - 1L, k);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2083 */           int m = monthLength(i, paramInt2) + 7 * (b + 1);
/*      */           
/* 2085 */           l = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l + m - 1L, k);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 2092 */     else if (isFieldSet(paramInt3, 6)) {
/* 2093 */       if (isTransitionYear(date.getNormalizedYear())) {
/* 2094 */         l = getFixedDateJan1(date, l);
/*      */       }
/*      */       
/* 2097 */       l += internalGet(6);
/* 2098 */       l--;
/*      */     } else {
/* 2100 */       long l1 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l + 6L, 
/* 2101 */           getFirstDayOfWeek());
/*      */ 
/*      */       
/* 2104 */       if (l1 - l >= getMinimalDaysInFirstWeek()) {
/* 2105 */         l1 -= 7L;
/*      */       }
/* 2107 */       if (isFieldSet(paramInt3, 7)) {
/* 2108 */         int k = internalGet(7);
/* 2109 */         if (k != getFirstDayOfWeek()) {
/* 2110 */           l1 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l1 + 6L, k);
/*      */         }
/*      */       } 
/*      */       
/* 2114 */       l = l1 + 7L * (internalGet(3) - 1L);
/*      */     } 
/*      */     
/* 2117 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long getFixedDateJan1(LocalGregorianCalendar.Date paramDate, long paramLong) {
/* 2129 */     Era era = paramDate.getEra();
/* 2130 */     if (paramDate.getEra() != null && paramDate.getYear() == 1) {
/* 2131 */       for (int i = getEraIndex(paramDate); i > 0; ) {
/* 2132 */         CalendarDate calendarDate = eras[i].getSinceDate();
/* 2133 */         long l = gcal.getFixedDate(calendarDate);
/*      */         
/* 2135 */         if (l > paramLong) {
/*      */           i--; continue;
/*      */         } 
/* 2138 */         return l;
/*      */       } 
/*      */     }
/* 2141 */     Gregorian.Date date = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 2142 */     date.setDate(paramDate.getNormalizedYear(), 1, 1);
/* 2143 */     return gcal.getFixedDate(date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long getFixedDateMonth1(LocalGregorianCalendar.Date paramDate, long paramLong) {
/* 2156 */     int i = getTransitionEraIndex(paramDate);
/* 2157 */     if (i != -1) {
/* 2158 */       long l = sinceFixedDates[i];
/*      */ 
/*      */       
/* 2161 */       if (l <= paramLong) {
/* 2162 */         return l;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2167 */     return paramLong - paramDate.getDayOfMonth() + 1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static LocalGregorianCalendar.Date getCalendarDate(long paramLong) {
/* 2176 */     LocalGregorianCalendar.Date date = jcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 2177 */     jcal.getCalendarDateFromFixedDate(date, paramLong);
/* 2178 */     return date;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int monthLength(int paramInt1, int paramInt2) {
/* 2188 */     return CalendarUtils.isGregorianLeapYear(paramInt2) ? GregorianCalendar.LEAP_MONTH_LENGTH[paramInt1] : GregorianCalendar.MONTH_LENGTH[paramInt1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int monthLength(int paramInt) {
/* 2199 */     assert this.jdate.isNormalized();
/* 2200 */     return this.jdate.isLeapYear() ? GregorianCalendar.LEAP_MONTH_LENGTH[paramInt] : GregorianCalendar.MONTH_LENGTH[paramInt];
/*      */   }
/*      */ 
/*      */   
/*      */   private int actualMonthLength() {
/* 2205 */     int i = jcal.getMonthLength(this.jdate);
/* 2206 */     int j = getTransitionEraIndex(this.jdate);
/* 2207 */     if (j == -1) {
/* 2208 */       long l = sinceFixedDates[j];
/* 2209 */       CalendarDate calendarDate = eras[j].getSinceDate();
/* 2210 */       if (l <= this.cachedFixedDate) {
/* 2211 */         i -= calendarDate.getDayOfMonth() - 1;
/*      */       } else {
/* 2213 */         i = calendarDate.getDayOfMonth() - 1;
/*      */       } 
/*      */     } 
/* 2216 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getTransitionEraIndex(LocalGregorianCalendar.Date paramDate) {
/* 2228 */     int i = getEraIndex(paramDate);
/* 2229 */     CalendarDate calendarDate = eras[i].getSinceDate();
/* 2230 */     if (calendarDate.getYear() == paramDate.getNormalizedYear() && calendarDate
/* 2231 */       .getMonth() == paramDate.getMonth()) {
/* 2232 */       return i;
/*      */     }
/* 2234 */     if (i < eras.length - 1) {
/* 2235 */       calendarDate = eras[++i].getSinceDate();
/* 2236 */       if (calendarDate.getYear() == paramDate.getNormalizedYear() && calendarDate
/* 2237 */         .getMonth() == paramDate.getMonth()) {
/* 2238 */         return i;
/*      */       }
/*      */     } 
/* 2241 */     return -1;
/*      */   }
/*      */   
/*      */   private boolean isTransitionYear(int paramInt) {
/* 2245 */     for (int i = eras.length - 1; i > 0; i--) {
/* 2246 */       int j = eras[i].getSinceDate().getYear();
/* 2247 */       if (paramInt == j) {
/* 2248 */         return true;
/*      */       }
/* 2250 */       if (paramInt > j) {
/*      */         break;
/*      */       }
/*      */     } 
/* 2254 */     return false;
/*      */   }
/*      */   
/*      */   private static int getEraIndex(LocalGregorianCalendar.Date paramDate) {
/* 2258 */     Era era = paramDate.getEra();
/* 2259 */     for (int i = eras.length - 1; i > 0; i--) {
/* 2260 */       if (eras[i] == era) {
/* 2261 */         return i;
/*      */       }
/*      */     } 
/* 2264 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JapaneseImperialCalendar getNormalizedCalendar() {
/*      */     JapaneseImperialCalendar japaneseImperialCalendar;
/* 2274 */     if (isFullyNormalized()) {
/* 2275 */       japaneseImperialCalendar = this;
/*      */     } else {
/*      */       
/* 2278 */       japaneseImperialCalendar = (JapaneseImperialCalendar)clone();
/* 2279 */       japaneseImperialCalendar.setLenient(true);
/* 2280 */       japaneseImperialCalendar.complete();
/*      */     } 
/* 2282 */     return japaneseImperialCalendar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void pinDayOfMonth(LocalGregorianCalendar.Date paramDate) {
/* 2292 */     int i = paramDate.getYear();
/* 2293 */     int j = paramDate.getDayOfMonth();
/* 2294 */     if (i != getMinimum(1)) {
/* 2295 */       paramDate.setDayOfMonth(1);
/* 2296 */       jcal.normalize(paramDate);
/* 2297 */       int k = jcal.getMonthLength(paramDate);
/* 2298 */       if (j > k) {
/* 2299 */         paramDate.setDayOfMonth(k);
/*      */       } else {
/* 2301 */         paramDate.setDayOfMonth(j);
/*      */       } 
/* 2303 */       jcal.normalize(paramDate);
/*      */     } else {
/* 2305 */       LocalGregorianCalendar.Date date1 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/* 2306 */       LocalGregorianCalendar.Date date2 = jcal.getCalendarDate(this.time, getZone());
/* 2307 */       long l = date2.getTimeOfDay();
/*      */       
/* 2309 */       date2.addYear(400);
/* 2310 */       date2.setMonth(paramDate.getMonth());
/* 2311 */       date2.setDayOfMonth(1);
/* 2312 */       jcal.normalize(date2);
/* 2313 */       int k = jcal.getMonthLength(date2);
/* 2314 */       if (j > k) {
/* 2315 */         date2.setDayOfMonth(k);
/*      */       }
/* 2317 */       else if (j < date1.getDayOfMonth()) {
/* 2318 */         date2.setDayOfMonth(date1.getDayOfMonth());
/*      */       } else {
/* 2320 */         date2.setDayOfMonth(j);
/*      */       } 
/*      */       
/* 2323 */       if (date2.getDayOfMonth() == date1.getDayOfMonth() && l < date1.getTimeOfDay()) {
/* 2324 */         date2.setDayOfMonth(Math.min(j + 1, k));
/*      */       }
/*      */       
/* 2327 */       paramDate.setDate(i, date2.getMonth(), date2.getDayOfMonth());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getRolledValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 2336 */     assert paramInt1 >= paramInt3 && paramInt1 <= paramInt4;
/* 2337 */     int i = paramInt4 - paramInt3 + 1;
/* 2338 */     paramInt2 %= i;
/* 2339 */     int j = paramInt1 + paramInt2;
/* 2340 */     if (j > paramInt4) {
/* 2341 */       j -= i;
/* 2342 */     } else if (j < paramInt3) {
/* 2343 */       j += i;
/*      */     } 
/* 2345 */     assert j >= paramInt3 && j <= paramInt4;
/* 2346 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int internalGetEra() {
/* 2354 */     return isSet(0) ? internalGet(0) : currentEra;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 2362 */     paramObjectInputStream.defaultReadObject();
/* 2363 */     if (this.jdate == null) {
/* 2364 */       this.jdate = jcal.newCalendarDate(getZone());
/* 2365 */       this.cachedFixedDate = Long.MIN_VALUE;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\JapaneseImperialCalendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */