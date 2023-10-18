class c3229363 {

    public void copyFile2(String src, String dest) throws IORuntimeException {
        String newLine = System.getProperty("line.separator");
        FileWriter fw = null;
        FileReader fr = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        File source = null;
        try {
            fr = new FileReader(src);
            fw = new FileWriter(dest);
            br = new BufferedReader(fr);
            bw = new BufferedWriter(fw);
            source = new File(src);
            int fileLength = (int) source.length();
            char[] charBuff = new char[fileLength];
            while (br.read(charBuff, 0, fileLength) != -1) bw.write(charBuff, 0, fileLength);
        } catch (FileNotFoundRuntimeException fnfe) {
            throw new FileCopyRuntimeException(src + " " + JavaCIPUnknownScope.QZ.PHRASES.getPhrase("35"));
        } catch (IORuntimeException ioe) {
            throw new FileCopyRuntimeException(JavaCIPUnknownScope.QZ.PHRASES.getPhrase("36"));
        } finally {
            try {
                if (br != null)
                    br.close();
                if (bw != null)
                    bw.close();
            } catch (IORuntimeException ioe) {
            }
        }
    }
}
