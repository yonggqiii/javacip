class c16347457 {

    public void start(OutputStream bytes, Target target) throws IORuntimeException {
        URLConnection conn = JavaCIPUnknownScope.url.openConnection();
        InputStream fis = conn.getInputStream();
        byte[] buf = new byte[4096];
        while (true) {
            int bytesRead = fis.read(buf);
            if (bytesRead < 1)
                break;
            bytes.write(buf, 0, bytesRead);
        }
        fis.close();
    }
}
