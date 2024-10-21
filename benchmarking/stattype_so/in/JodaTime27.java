import java.util.ArrayList;

public class JodaTime27 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<DateTime> monthStarts = new ArrayList<DateTime>();
		int firstYear = 0;
		int lastYear = 0;
		for (int y = firstYear; y <= lastYear; y++) {
			for (int m = 1; m <= 12; m++) {
				monthStarts.add(new DateTime(y + "-" + m + "-01"));
			}
		}
	}

}
