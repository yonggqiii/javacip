public class JodaTime18 {

    // default our time zone to the machine local one.
    private static final DateTimeZone LOCAL_TZ = DateTimeZone.getDefault();

    // format of date (i.e., timestamp) is yyyy-MM-dd HH:mm:ss.S
    private static final DateTimeFormatter YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLIS_FORMATTER = new DateTimeFormatterBuilder().appendYear(4, 4).appendLiteral('-').appendMonthOfYear(1).appendLiteral('-').appendDayOfMonth(1).appendLiteral(' ').appendHourOfDay(2).appendLiteral(':').appendMinuteOfDay(1).appendLiteral(':').appendSecondOfDay(1).appendLiteral('.').appendMillisOfDay(1).toFormatter().withZone(LOCAL_TZ);
}
