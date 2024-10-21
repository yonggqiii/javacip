public class JodaTime34 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Locale.setDefault(Locale.GERMAN);
		final PeriodFormatter periodFormatter = PeriodFormat.wordBased(Locale.ENGLISH);
		final Period period = new Period(6, 5, 4, 3);
		final String s = period.toString(periodFormatter);
		// i'm expecting english to be outputted
		System.out.println("s = " + s); // outputs german: 6 Stunden, 5 Minuten,
										// 4 Sekunden und 3 Millisekunden
	}

}
