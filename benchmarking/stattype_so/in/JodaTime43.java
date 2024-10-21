public class JodaTime43 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateTimeParser[] parsers = { DateTimeFormat.forPattern("yyyy-MM-dd HH").getParser(),
				DateTimeFormat.forPattern("yyyy-MM-dd").getParser() };
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(null, parsers).toFormatter();

		DateTime date1 = formatter.parseDateTime("2010-01-01");
		DateTime date2 = formatter.parseDateTime("2010-01-01 01");
	}

}
