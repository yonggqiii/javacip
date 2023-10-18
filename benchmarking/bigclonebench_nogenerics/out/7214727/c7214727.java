class c7214727 {

    public byte[] pipeBytes() {
        byte[] ba = null;
        try {
            URL url = new URL(JavaCIPUnknownScope.server);
            JavaCIPUnknownScope.conn = (HttpURLConnection) url.openConnection();
            InputStream is = JavaCIPUnknownScope.conn.getInputStream();
            ByteArrayOutputStream tout = new ByteArrayOutputStream();
            int nmax = 10000;
            byte[] b = new byte[nmax + 1];
            int nread = 0;
            while ((nread = is.read(b, 0, nmax)) >= 0) tout.write(b, 0, nread);
            ba = tout.toByteArray();
        } catch (RuntimeException ex) {
            System.err.println(ex);
        }
        return ba;
    }
}
