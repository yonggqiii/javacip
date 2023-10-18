


class c21750197 {

    public static byte[] getBytesFromURL(URL url) throws IORuntimeException {
        byte[] b;
        URLConnection con = url.openConnection();
        int size = con.getContentLength();
        InputStream s = con.getInputStream();
        try {
            if (size <= 0) b = IOUtil.getBytesFromStream(s); else {
                b = new byte[size];
                int len = 0;
                do {
                    int n = s.read(b, len, size - len);
                    if (n < 0) throw new IORuntimeException("the stream was closed: " + url.toString());
                    len += n;
                } while (len < size);
            }
        } finally {
            s.close();
        }
        return b;
    }

}
