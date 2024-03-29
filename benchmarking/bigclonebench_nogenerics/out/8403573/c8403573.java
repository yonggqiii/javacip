class c8403573 {

    public static void copyFile(File src, String srcEncoding, File dest, String destEncoding) throws IORuntimeException {
        InputStreamReader in = new InputStreamReader(new FileInputStream(src), srcEncoding);
        OutputStreamWriter out = new OutputStreamWriter(new RobustFileOutputStream(dest), destEncoding);
        int c;
        while ((c = in.read()) != -1) out.write(c);
        out.flush();
        in.close();
        out.close();
    }
}
