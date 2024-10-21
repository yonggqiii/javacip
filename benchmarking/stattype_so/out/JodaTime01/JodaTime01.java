public class JodaTime01 {

    public static void main(String[] args) {
        DateTimeFormatter timeParser = ISODateTimeFormat.timeParser();
        TimeZone timeZone = TimeZone.getDefault();
        // "Europe/London"
        System.out.println(timeZone.getID());
        // "Greenwich Mean Time"
        System.out.println(timeZone.getDisplayName());
        DateTimeZone defaultTimeZone = DateTimeZone.getDefault();
        // "Europe/London"
        System.out.println(defaultTimeZone.getID());
        // "British Summer Time"
        System.out.println(defaultTimeZone.getName(0L));
        DateTime currentTime = new DateTime();
        DateTimeZone currentZone = currentTime.getZone();
        // "Europe/London"
        System.out.println(currentZone.getID());
        // "British Summer Time"
        System.out.println(currentZone.getName(0L));
    }
}
