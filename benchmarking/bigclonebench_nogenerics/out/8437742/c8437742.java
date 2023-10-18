class c8437742 {

    protected void copy(URL url, File file) throws IORuntimeException {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = url.openStream();
            out = new FileOutputStream(file);
            IOUtils.copy(in, out);
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }
}
