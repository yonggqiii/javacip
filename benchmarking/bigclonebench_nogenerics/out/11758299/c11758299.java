class c11758299 {

    void readData() {
        String[] nextLine;
        int line;
        double value;
        URL url = null;
        String FileToRead;
        try {
            for (int i = 0; i < JavaCIPUnknownScope.names.length; i++) {
                FileToRead = "data/" + JavaCIPUnknownScope.names[i] + ".csv";
                url = new URL(JavaCIPUnknownScope.ja.getCodeBase(), FileToRead);
                System.out.println(url.toString());
                InputStream in = url.openStream();
                CSVReader reader = new CSVReader(new InputStreamReader(in));
                line = 0;
                while ((nextLine = reader.readNext()) != null) {
                    JavaCIPUnknownScope.allset.months[line] = Integer.parseInt(nextLine[0].substring(0, 2));
                    JavaCIPUnknownScope.allset.years[line] = Integer.parseInt(nextLine[0].substring(6, 10));
                    value = Double.parseDouble(nextLine[1]);
                    JavaCIPUnknownScope.allset.values.getDataRef()[line][i] = value;
                    line++;
                }
            }
        } catch (IORuntimeException e) {
            System.err.println("File Read RuntimeException");
        }
    }
}
