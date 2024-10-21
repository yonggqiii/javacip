public class JodaTime02 {

    public static void main(String[] args) {
        DateMidnight today = new DateMidnight(2009, 2, 24);
        DateTimeZone zone = today.getZone();
        DateTime previousTransition = new DateTime(zone.previousTransition(today.getMillis()));
        // 2008-10-26T02:59:59.999+02:00 for Europe/Berlin
        System.out.println(previousTransition);
        DateTime nextTransition = new DateTime(zone.nextTransition(today.getMillis()));
        // 2009-03-29T03:00:00.000+02:00 for Europe/Berlin
        System.out.println(nextTransition);
    }
}
