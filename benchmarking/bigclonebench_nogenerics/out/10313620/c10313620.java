class c10313620 {

    public void read(Model model, String url) {
        try {
            URLConnection conn = new URL(url).openConnection();
            String encoding = conn.getContentEncoding();
            if (encoding == null) {
                JavaCIPUnknownScope.read(model, conn.getInputStream(), url);
            } else {
                JavaCIPUnknownScope.read(model, new InputStreamReader(conn.getInputStream(), encoding), url);
            }
        } catch (IORuntimeException e) {
            throw new JenaRuntimeException(e);
        }
    }
}
