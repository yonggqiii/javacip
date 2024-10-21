public class JodaTime49 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MMM-dd");
		DateTime dateTime = FORMATTER.parseDateTime("2005-nov-12");
		LocalDate localDate = dateTime.toLocalDate();
	}

}
