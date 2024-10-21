class c16875869 {

    public static ArrayList<Quote> fetchAllQuotes(String symbol, Date from, Date to) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(from);
            int m = calendar.get(GregorianCalendar.MONTH);
            int d = calendar.get(GregorianCalendar.DAY_OF_MONTH);
            int y = calendar.get(GregorianCalendar.YEAR);
            String monthFrom = (new Integer(m)).toString();
            String dayFrom = (new Integer(d)).toString();
            String yearFrom = (new Integer(y)).toString();
            calendar.setTime(to);
            m = calendar.get(GregorianCalendar.MONTH);
            d = calendar.get(GregorianCalendar.DAY_OF_MONTH);
            y = calendar.get(GregorianCalendar.YEAR);
            String monthTo = (new Integer(m)).toString();
            String dayTo = (new Integer(d)).toString();
            String yearTo = (new Integer(y)).toString();
            URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s=" + symbol + "&a=" + monthFrom + "&b=" + dayFrom + "&c=" + yearFrom + "&d=" + monthTo + "&e=" + dayTo + "&f=" + yearTo + "&g=d&ignore=.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            ArrayList<Quote> result = new ArrayList<Quote>();
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String date = values[0];
                Date dateQuote = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                double open = Double.valueOf(values[1]);
                double high = Double.valueOf(values[2]);
                double low = Double.valueOf(values[3]);
                double close = Double.valueOf(values[4]);
                long volume = Long.valueOf(values[5]);
                double adjClose = Double.valueOf(values[6]);
                Quote q = new Quote(dateQuote, open, high, low, close, volume, adjClose);
                result.add(q);
            }
            reader.close();
            Collections.reverse(result);
            return result;
        } catch (MalformedURLException e) {
            System.out.println("URL malformee");
        } catch (IOException e) {
            System.out.println("Donnees illisibles");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
