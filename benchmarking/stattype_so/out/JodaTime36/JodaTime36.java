public class JodaTime36 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PeriodFormatter pf = new PeriodFormatterBuilder().append(ISOPeriodFormat.standard()).toFormatter();
        Period period = pf.parsePeriod("PT01H00M");
    }
}
