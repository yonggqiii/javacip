public class JodaTime33 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PeriodFormatter hoursMinutesSeconds = new PeriodFormatterBuilder().appendHours().appendSuffix(" hour", " hours").appendSeparator(", ", " and ").appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(" and ").printZeroRarelyFirst().appendSeconds().appendSuffix(" second", " seconds").toFormatter();
        System.out.println(hoursMinutesSeconds.print(new Period(0, 0, 0, 500)));
    }
}
