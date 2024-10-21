public class JodaTime40 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // June 20th, 2010
        Date past = new Date(110, 5, 20);
        // July 24th
        Date today = new Date(110, 6, 24);
        // => 34
        int days = Days.daysBetween(new DateTime(past), new DateTime(today)).getDays();
    }
}
