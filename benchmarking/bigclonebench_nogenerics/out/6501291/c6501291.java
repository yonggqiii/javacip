class c6501291 {

    public static void extractFile(String input, String output) throws ZipRuntimeException, IORuntimeException {
        FileReader reader = new FileReader(input);
        InputStream in = reader.getInputStream();
        OutputStream out = new FileOutputStream(new File(output));
        byte[] buf = new byte[512];
        int len;
        while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
        reader.close();
        out.close();
    }
}
