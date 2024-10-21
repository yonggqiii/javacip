public class JodaTime03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String formatDuration(long start, long end) {
		Interval interval = new Interval(start, end);
		return getPeriodFormatter().print(interval.toPeriod()).trim();
	}

	private static PeriodFormatter getPeriodFormatter() {
		PeriodFormatter pf = new PeriodFormatterBuilder().printZeroRarelyFirst().appendYears().appendSuffix("y ", "y ")
				.appendMonths().appendSuffix("m", "m ").appendDays().appendSuffix("d ", "d ").appendHours()
				.appendSuffix("h ", "h ").appendMinutes().appendSuffix("m ", "m ").appendSeconds()
				.appendSuffix("s ", "s ").toFormatter();

		return pf;
	}
}
