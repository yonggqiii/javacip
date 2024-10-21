public class JodaTime06 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		printDuration(new Duration(5000)); // Prints "5" => OK
		printDuration(new Duration(4900)); // Prints "4" => need "5"
		printDuration(new Duration(1000)); // Prints "1" => OK
		printDuration(new Duration(900)); // Prints "0" => need "1"
		printDuration(new Duration(0)); // Prints "0" => OK
	}

	static void printDuration(Duration d) {
		System.out.println(d.toPeriod(PeriodType.time())
				.toString(new PeriodFormatterBuilder().printZeroAlways().appendSeconds().toFormatter()));
	}

}
