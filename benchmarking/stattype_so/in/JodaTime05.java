public class JodaTime05 {

	public static void main(String[] args) {

		DateTimeZone zone = DateTimeZone.forID("Europe/London");
		Chronology coptic = GJChronology.getInstance(zone);

		DateTime dt = new DateTime(coptic);
		DateTime minusOneDay = dt.minusDays(1);

		System.out.println(minusOneDay);

	}

}
