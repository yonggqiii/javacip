class c8489742 {

    public static void fileCopy(String fromPath, String toPath) throws IORuntimeException {
        File inputFile = new File(fromPath);
        File outputFile = new File(toPath);
        FileReader in = new FileReader(inputFile);
        FileWriter out = new FileWriter(outputFile);
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.close();
    }
}
