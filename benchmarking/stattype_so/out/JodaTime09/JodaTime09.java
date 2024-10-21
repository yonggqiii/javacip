public class JodaTime09 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        final String string_from_3rd_party = "GMT+08:00";
        // Works for standard Java TimeZone!
        System.out.println(TimeZone.getTimeZone(string_from_3rd_party));
        // Exception in thread "main" java.lang.IllegalArgumentException: The datetime zone id is not recognised: GMT+08:00
        System.out.println(DateTimeZone.forID(string_from_3rd_party));
    }
}
