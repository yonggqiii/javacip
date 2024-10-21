


class c14263421 {

    void copyFile(String sInput, String sOutput) throws IORuntimeException {
        File inputFile = new File(sInput);
        File outputFile = new File(sOutput);
        FileReader in = new FileReader(inputFile);
        FileWriter out = new FileWriter(outputFile);
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.close();
    }

}
