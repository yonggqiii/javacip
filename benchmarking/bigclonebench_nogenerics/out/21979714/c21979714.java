class c21979714 {

    public void list() throws IORuntimeException {
        final URL url = new URL("http://127.0.0.1:" + JavaCIPUnknownScope.testPort + "/list?version=1000");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty(Http11Header.AUTHORIZATION, "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        con.setRequestProperty(Http11Header.WWW_AUTHENTICATE, "Basic realm=\"karatasi\"");
        final InputStream in = con.getInputStream();
        final byte[] buf = new byte[4096];
        JavaCIPUnknownScope.textArea.setText("");
        for (int bytesRead; (bytesRead = in.read(buf)) != -1; ) {
            JavaCIPUnknownScope.textArea.append(new String(buf, 0, bytesRead));
        }
    }
}
