


class c4491957 {

    public void copyToCurrentDir(File _copyFile, String _fileName) throws IORuntimeException {
        File outputFile = new File(getCurrentPath() + File.separator + _fileName);
        FileReader in;
        FileWriter out;
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        in = new FileReader(_copyFile);
        out = new FileWriter(outputFile);
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.close();
        reList();
    }

}
