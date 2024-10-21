public class JodaTime37 {

	public static void main(String[] args) {
		// 5am on the 20th to 1pm on the 21st, October 2013, Brazil
		DateTimeZone BRAZIL = DateTimeZone.forID("America/Sao_Paulo");
		DateTime start = new DateTime(2013, 10, 20, 5, 0, 0, BRAZIL);
		DateTime end = new DateTime(2013, 10, 21, 13, 0, 0, BRAZIL);
		System.out.println(getDaysBetween(start, end));
		// prints 1
	}

	public static int getDaysBetween(DateTime earlier, DateTime later) {
		return (int) TimeUnit.MILLISECONDS.toDays(later.getMillis() - earlier.getMillis());
	}
}
