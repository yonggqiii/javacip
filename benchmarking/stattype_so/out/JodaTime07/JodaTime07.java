public class JodaTime07 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
        Date someDate = new Date();
        Date today = Calendar.getInstance().getTime();
        try {
            someDate = formatter.parse("06/22/2010");
        } catch (ParseException pe) {
            System.out.println("Parser Exception");
        }
        int days = Days.daysBetween(new DateTime(someDate), new DateTime(today)).getDays();
        System.out.println(" Days Between " + someDate + " : " + today + " - " + days);
    }
}
