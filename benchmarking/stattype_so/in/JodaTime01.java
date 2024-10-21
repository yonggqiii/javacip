public class JodaTime01 {

	public static void main(String[] args) {
		DateTimeFormatter timeParser = ISODateTimeFormat.timeParser();
		TimeZone timeZone = TimeZone.getDefault();
		System.out.println(timeZone.getID()); // "Europe/London"
		System.out.println(timeZone.getDisplayName()); // "Greenwich Mean Time"

		DateTimeZone defaultTimeZone = DateTimeZone.getDefault();
		System.out.println(defaultTimeZone.getID()); //"Europe/London"
		System.out.println(defaultTimeZone.getName(0L)); //"British Summer Time"

		DateTime currentTime = new DateTime();
		DateTimeZone currentZone = currentTime.getZone();
		System.out.println(currentZone.getID()); //"Europe/London"
		System.out.println(currentZone.getName(0L)); //"British Summer Time"            
	}
}
