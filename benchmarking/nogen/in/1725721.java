


class c1725721 {

    public static int writeFile(URL url, File outFilename) {
        InputStream input;
        try {
            input = url.openStream();
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return 0;
        }
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(outFilename);
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
            return 0;
        }
        return writeFile(input, outputStream);
    }

}
