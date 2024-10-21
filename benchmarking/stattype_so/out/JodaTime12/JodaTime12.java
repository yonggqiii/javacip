public class JodaTime12 {

    public static void main(String[] args) {
        String currentDate;
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date1 = null;
        DateTime dt;
        currentDate = "27/12/2010 11:29";
        try {
            date1 = myFormat.parse(currentDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        dt = new DateTime(date1);
        dt.plusMinutes(30);
    }
}
