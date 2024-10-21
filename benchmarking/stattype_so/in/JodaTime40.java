public class JodaTime40 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date past = new Date(110, 5, 20); // June 20th, 2010
		Date today = new Date(110, 6, 24); // July 24th 
		int days = Days.daysBetween(new DateTime(past), new DateTime(today)).getDays(); // => 34
	}

}
