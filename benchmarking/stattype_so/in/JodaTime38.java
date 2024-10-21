public class JodaTime38 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dateTime = "11/15/2013 08:00:00";
		// Format for input
		DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
		// Parsing the date
		DateTime jodatime = dtf.parseDateTime(dateTime);
		// Format for output
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd/yyyy");
		// Printing the date
		System.out.println(dtfOut.print(jodatime));
	}

}
