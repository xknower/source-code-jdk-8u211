/*     */ package sun.util.calendar;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
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
/*     */ 
/*     */ public final class Era
/*     */ {
/*     */   private final String name;
/*     */   private final String abbr;
/*     */   private final long since;
/*     */   private final CalendarDate sinceDate;
/*     */   private final boolean localTime;
/*     */   private int hash;
/*     */   
/*     */   public Era(String paramString1, String paramString2, long paramLong, boolean paramBoolean) {
/* 136 */     this.hash = 0; this.name = paramString1; this.abbr = paramString2; this.since = paramLong; this.localTime = paramBoolean; Gregorian gregorian = CalendarSystem.getGregorianCalendar(); Gregorian.Date date = gregorian.newCalendarDate((TimeZone)null); gregorian.getCalendarDate(paramLong, date); this.sinceDate = new ImmutableGregorianDate(date);
/*     */   }
/*     */   public String getName() { return this.name; }
/* 139 */   public String getDisplayName(Locale paramLocale) { return this.name; } public String getAbbreviation() { return this.abbr; } public String getDiaplayAbbreviation(Locale paramLocale) { return this.abbr; } public int hashCode() { if (this.hash == 0) {
/* 140 */       this.hash = this.name.hashCode() ^ this.abbr.hashCode() ^ (int)this.since ^ (int)(this.since >> 32L) ^ (this.localTime ? 1 : 0);
/*     */     }
/*     */     
/* 143 */     return this.hash; }
/*     */   public long getSince(TimeZone paramTimeZone) { if (paramTimeZone == null || !this.localTime) return this.since;  int i = paramTimeZone.getOffset(this.since); return this.since - i; }
/*     */   public CalendarDate getSinceDate() { return this.sinceDate; }
/*     */   public boolean isLocalTime() { return this.localTime; }
/* 147 */   public boolean equals(Object paramObject) { if (!(paramObject instanceof Era)) return false;  Era era = (Era)paramObject; return (this.name.equals(era.name) && this.abbr.equals(era.abbr) && this.since == era.since && this.localTime == era.localTime); } public String toString() { StringBuilder stringBuilder = new StringBuilder();
/* 148 */     stringBuilder.append('[');
/* 149 */     stringBuilder.append(getName()).append(" (");
/* 150 */     stringBuilder.append(getAbbreviation()).append(')');
/* 151 */     stringBuilder.append(" since ").append(getSinceDate());
/* 152 */     if (this.localTime) {
/* 153 */       stringBuilder.setLength(stringBuilder.length() - 1);
/* 154 */       stringBuilder.append(" local time");
/*     */     } 
/* 156 */     stringBuilder.append(']');
/* 157 */     return stringBuilder.toString(); }
/*     */ 
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\su\\util\calendar\Era.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */