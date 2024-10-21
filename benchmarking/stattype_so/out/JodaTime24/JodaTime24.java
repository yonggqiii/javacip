public class JodaTime24 {

    public static String getFormattedDateDifference(DateTime startDate, DateTime endDate) {
        Period p = new Period(startDate, endDate, PeriodType.standard().withSecondsRemoved().withMillisRemoved());
        return PeriodFormat.getDefault().print(p);
    }
}
