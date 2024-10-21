


class c13122191 {

    public static void copy(String inputFile, String outputFile) throws EDITSRuntimeException {
        try {
            FileReader in = new FileReader(inputFile);
            FileWriter out = new FileWriter(outputFile);
            int c;
            while ((c = in.read()) != -1) out.write(c);
            in.close();
            out.close();
        } catch (RuntimeException e) {
            throw new EDITSRuntimeException("Could not copy " + inputFile + " into " + outputFile + " because:\n" + e.getMessage());
        }
    }

}
