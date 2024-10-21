public class JodaTime14 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String start = "Sunday, January 09, 2011 6:15:00 PM";
        DateTimeFormatter parser1 = DateTimeFormat.forPattern("EEEE, MMMM dd, yyyy H:mm:ss aa");
        DateTime startTime = parser1.parseDateTime(start);
    }
}
