class c2107034 {

    public static void main(String[] args) throws IORuntimeException {
        File inputFile = new File("D:/farrago.txt");
        File outputFile = new File("D:/outagain.txt");
        FileReader in = new FileReader(inputFile);
        FileWriter out = new FileWriter(outputFile);
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.close();
    }
}
