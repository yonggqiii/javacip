class c3236017 {

    public static void copyFile(String source, String dest) throws IORuntimeException {
        FileChannel in = null, out = null;
        try {
            in = new FileInputStream(new File(source)).getChannel();
            out = new FileOutputStream(new File(dest)).getChannel();
            in.transferTo(0, in.size(), out);
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }
}
