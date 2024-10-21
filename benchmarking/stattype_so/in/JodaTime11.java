public class JodaTime11 {
	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss z yyyy");
		System.out.println(dtf.parseDateTime("Fri Nov 11 12:13:14 JST 2010"));
		try {
			System.out
					.println(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse("Fri Nov 11 12:13:14 JST 2010"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
