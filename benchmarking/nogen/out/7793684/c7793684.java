class c7793684 {

    private String createCSVFile(String fileName) throws FileNotFoundRuntimeException, IORuntimeException {
        String csvFile = fileName + ".csv";
        BufferedReader buf = new BufferedReader(new FileReader(fileName));
        BufferedWriter out = new BufferedWriter(new FileWriter(csvFile));
        String line;
        while ((line = buf.readLine()) != null) out.write(line + "\n");
        buf.close();
        out.close();
        return csvFile;
    }
}
