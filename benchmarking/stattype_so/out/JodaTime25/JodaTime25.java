// ID = 8654398
public class JodaTime25 {

    public static int getDaysBetweenTwoDates(final LocalDateTime oldDate, final LocalDateTime newDate) {
        System.out.println("--Calculate days between: " + oldDate.toString() + "->" + newDate);
        Days days = Days.daysBetween(new DateMidnight(oldDate.toDateTime()), new DateMidnight(newDate.toDateTime()));
        System.out.println("----DIFF: " + days.size());
        return days.size();
    }
}
