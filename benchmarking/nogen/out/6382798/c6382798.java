class c6382798 {

    public String readURL(URL url) throws JasenRuntimeException {
        OutputStream out = new ByteArrayOutputStream();
        InputStream in = null;
        String html = null;
        NonBlockingStreamReader reader = null;
        try {
            in = url.openStream();
            reader = new NonBlockingStreamReader();
            reader.read(in, out, JavaCIPUnknownScope.readBufferSize, JavaCIPUnknownScope.readTimeout, null);
            html = new String(((ByteArrayOutputStream) out).toByteArray());
        } catch (IORuntimeException e) {
            throw new JasenRuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IORuntimeException ignore) {
                }
            }
        }
        return html;
    }
}
