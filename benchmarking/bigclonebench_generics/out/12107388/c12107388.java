class c12107388 {

    private static List<CountryEntry> retrieveCountries() throws IOException {
        URL url = new URL("http://" + JavaCIPUnknownScope.ISO_3166_HOST + JavaCIPUnknownScope.ISO_3166_TXT_FILE_PATH);
        BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
        List<CountryEntry> countries = new LinkedList<CountryEntry>();
        boolean parsing = false;
        int trCount = 0;
        int tdCount = 0;
        CountryEntry current = new CountryEntry();
        String nextLine = input.readLine();
        while (nextLine != null) {
            if (nextLine.startsWith("<table")) {
                parsing = true;
            }
            if (nextLine.startsWith("</table>")) {
                break;
            }
            if (parsing) {
                if (nextLine.startsWith("<tr")) {
                    trCount++;
                } else {
                    if ((trCount > 1 && nextLine.startsWith("<td"))) {
                        tdCount++;
                        String data = JavaCIPUnknownScope.pullData(nextLine);
                        switch(tdCount) {
                            case 1:
                                current.setName(JavaCIPUnknownScope.getCountryName(data));
                                break;
                            case 2:
                                current.setNumber(data);
                                break;
                            case 3:
                                current.setAlpha3(data);
                                break;
                            case 4:
                                current.setAlpha2(data);
                                break;
                            case 5:
                                countries.add(current);
                                current = new CountryEntry();
                                tdCount = 0;
                                break;
                            default:
                                String msg = "Parsing error.  Unexpected column: [" + data + "]";
                                throw new IllegalStateException(msg);
                        }
                    }
                }
            }
            nextLine = input.readLine();
        }
        input.close();
        return countries;
    }
}
