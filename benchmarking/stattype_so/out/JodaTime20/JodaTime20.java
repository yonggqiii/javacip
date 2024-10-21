public class JodaTime20 {

    public static void main(String[] args) {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter DATETIME_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT);
        DateTimeZone dateTimeZone = DateTimeZone.forID("-03:00");
        // date is 2000-01-01 00:00:00 -03:00
        DateTime date = DATETIME_FORMATTER.withZone(dateTimeZone).parseDateTime("2000-01-01 00:00:00");
        System.out.println("Current date is: " + date.toString());
        // now convert to UTC
        DateTime convertedDate = date.toDateTime(DateTimeZone.UTC);
        System.out.println("Converted date: " + date.toString());
        // TODO Auto-generated method stub
    }
}
