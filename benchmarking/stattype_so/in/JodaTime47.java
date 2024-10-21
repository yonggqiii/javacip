public class JodaTime47 {

	public static void main(String[] args) {
		final DateTimeFormatter df = DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss 'GMT'Z yyyy");
		final DateTime dateTime = df.withOffsetParsed().parseDateTime("Mon Aug 24 12:36:46 GMT+1000 2009");
		System.out.println("dateTime = " + dateTime);
	}

}
