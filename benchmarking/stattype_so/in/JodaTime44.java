public class JodaTime44 {

	public static void main(String[] args) {
		PeriodFormatter daysHoursMinutes = new PeriodFormatterBuilder().appendDays().appendSuffix(" day", " days")
				.appendSeparator(" and ").appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(" and ")
				.appendSeconds().appendSuffix(" second", " seconds").toFormatter();

		Period period = new Period(72, 24, 12, 0);

		System.out.println(daysHoursMinutes.print(period));
		System.out.println(daysHoursMinutes.print(period.normalizedStandard()));
	}

}
