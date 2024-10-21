public class JodaTime50 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

    // days of a month
    public static int daysOfMonth(int year, int month) {
        DateTime dateTime = new DateTime(year, month, 14, 12, 0, 0, 000);
        return dateTime.dayOfMonth().getMaximumValue();
    }
}
