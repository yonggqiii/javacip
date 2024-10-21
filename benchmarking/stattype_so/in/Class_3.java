public class Class_3 {

	private static final String SYMBOL_INFINITE = "\u221e";
	private static final char SYMBOL_MINUS = '-';
	private static final char SYMBOL_ZERO = '0';
	private static final int DECIMAL_LEADING_GROUPS = 10;
	private static final int EXPONENTIAL_INT_THRESHOLD = 1000000000; // After this value switch to exponential notation
	private static final double EXPONENTIAL_DEC_THRESHOLD = 0.0001; // Below this value switch to exponential notation

	private DecimalFormat decimalFormat;
	private DecimalFormat decimalFormatLong;
	private DecimalFormat exponentialFormat;

	private char groupSeparator;

	public void NumberFormatter(int decimalPlaces) {
		configureDecimalPlaces(decimalPlaces);
	}

	public void configureDecimalPlaces(int decimalPlaces) {
		if (decimalPlaces <= 0) {
			throw new IllegalArgumentException("Invalid decimal places");
		}

		DecimalFormatSymbols separators = new DecimalFormatSymbols(Locale.getDefault());
		separators.setMinusSign(SYMBOL_MINUS);
		separators.setZeroDigit(SYMBOL_ZERO);

		groupSeparator = separators.getGroupingSeparator();

		StringBuilder decimal = new StringBuilder();
		StringBuilder exponential = new StringBuilder("0.");

		for (int i = 0; i < DECIMAL_LEADING_GROUPS; i++) {
			decimal.append("###").append(i == DECIMAL_LEADING_GROUPS - 1 ? "." : ",");
		}

		for (int i = 0; i < decimalPlaces; i++) {
			decimal.append("#");
			exponential.append("0");
		}

		exponential.append("E0");

		decimalFormat = new DecimalFormat(decimal.toString(), separators);
		decimalFormatLong = new DecimalFormat(decimal.append("####").toString(), separators);
		exponentialFormat = new DecimalFormat(exponential.toString(), separators);

		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		decimalFormatLong.setRoundingMode(RoundingMode.HALF_UP);
		exponentialFormat.setRoundingMode(RoundingMode.HALF_UP);
	}

	public String format(double value) {
		String result;
		if (Double.isNaN(value)) {
			result = "";
		} else if (Double.isInfinite(value)) {
			result = String.valueOf(SYMBOL_INFINITE);
		} else {
			double absValue = Math.abs(value);
			if (absValue >= 1) {
				if (absValue >= EXPONENTIAL_INT_THRESHOLD) {
					value = Math.floor(value);
					result = exponentialFormat.format(value);
				} else {
					result = decimalFormat.format(value);
				}
			} else if (absValue < 1 && absValue > 0) {
				if (absValue >= EXPONENTIAL_DEC_THRESHOLD) {
					result = decimalFormat.format(value);
					if (result.equalsIgnoreCase("0")) {
						result = decimalFormatLong.format(value);
					}
				} else {
					result = exponentialFormat.format(value);
				}
			} else {
				result = "0";
			}
		}
		return result;
	}

	public String formatWithoutGroupSeparators(double value) {
		return removeGroupSeparators(format(value));
	}

	public double parse(String value, double defValue) {
		// try {
			return decimalFormat.parse(value).doubleValue();
		// } catch (ParseException e) {
		// 	e.printStackTrace();
		// }
	}

	private String removeGroupSeparators(String number) {
		return number.replace(String.valueOf(groupSeparator), "");
	}

}
