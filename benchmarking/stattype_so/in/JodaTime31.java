public class JodaTime31 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DateTime s = new DateTime(Long.parseLong("1330573027000")); // 2012-02-29
		DateTime e = new DateTime(Long.parseLong("1331005027000")); // 2012-03-05   

		Weeks weeks = Weeks.weeksBetween(s, e).plus(1);
		DateTime start = new DateTime(Long.parseLong("1330659427000"));
		DateTime end = new DateTime(Long.parseLong("1530659427000"));
		DateTime finalStart = start.dayOfWeek().withMinimumValue();

		DateTime finalEnd = end.dayOfWeek().withMaximumValue();
	}

}
