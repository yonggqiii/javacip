public class JodaTime22 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getDateFromDateTime(Date date, Boolean display) throws ParseException {

		/*
		 * SimpleDateFormat working as designed, but pf_ordersController.selected.returnDateTime displaying incorrect date/time
		 * 
		 * see below from /orders/pf_View.xhtml
		 * pf_ordersController.selected.returnDateTime (displayed on JSF page) = 11/13/2011 02:00 AM
		 * 
		 * ORDER #  0739  Trip Date 11/11/2011 to 11/12/2011  Customer # 1004
		 * 
		 * Trip Date/Time: 11/11/2011 05:00 PM  Report Date/Time: 11/11/2011 04:45 PM   Return Date/Time: 11/13/2011 02:00 AM
		 * 
		 * orders.returnDateTime (stored in database) = 11/12/2011 21:00:00 (9:00 PM)
		 * SimpleDateFormat converts orders.returnDateTime to 11/12/2011 (working as designed)
		 * 
		 * http://stackoverflow.com/questions/2356672/date-parsing-formating-with-timezone-and-simpledateformat-problem-around-dst-swi
		 * 
		DateFormat formatter;
		String myDate;
		
		if (display)
		    formatter = new SimpleDateFormat("MM/dd/yyyy");
		else
		    formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		myDate = formatter.format(date);
		 * 
		 */

		DateTimeFormatter dtFormatter;

		if (display)
			dtFormatter = DateTimeFormat.forPattern("MM/dd/yyyy");
		else
			dtFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

		DateTime dt = new DateTime(date);
		String myDate = dt.toString(dtFormatter);

		System.out.println(
				"OrderDisplayUtil.java:getDateFromDateTime(" + date + ", " + display + "): " + "myDate = " + myDate);

		return myDate;

	}
}
