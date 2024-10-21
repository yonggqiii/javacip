public class JodaTime04 {
	private ReadableInstant[] startTime;
	private ReadableInstant[] endTime;

	public String foobar(int dayIndex) {
		Period p = new Period(this.startTime[dayIndex], this.endTime[dayIndex]);
		long hours = p.getHours();
		long minutes = p.getMinutes();

		String format = String.format("%%0%dd", 2);//Ensures that the minutes will always display as two digits.

		return Long.toString(hours) + ":" + String.format(format, minutes);
	}

}
