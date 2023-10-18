class c17477951 {

    public static void copy(String inputFile, String outputFile) throws RuntimeException {
        try {
            FileReader in = new FileReader(inputFile);
            FileWriter out = new FileWriter(outputFile);
            int c;
            while ((c = in.read()) != -1) out.write(c);
            in.close();
            out.close();
        } catch (RuntimeException e) {
            throw new RuntimeException("Could not copy " + inputFile + " into " + outputFile + " because:\n" + e.getMessage());
        }
    }
}
