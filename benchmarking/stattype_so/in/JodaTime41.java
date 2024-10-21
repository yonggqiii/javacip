public class JodaTime41 {

	public static void main(String[] args) {
		DateTime dt4 = new DateTime("2017-01-01T10:52:07.656-10:00");
		System.out.println("Given Date:" + dt4);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/DD/YYYY");
		System.out.println("Given Date After Date format: " + fmt.print(dt4));

		DateTime previousDay = dt4.minusDays(1);
		System.out.println("Previous Day: " + previousDay);
		System.out.println("Previous Date After Date format: " + fmt.print(previousDay));

	}

}
