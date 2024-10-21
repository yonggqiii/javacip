class c9245183 {

    public void read(Model m, String url) throws JenaRuntimeException {
        try {
            URLConnection conn = new URL(url).openConnection();
            String encoding = conn.getContentEncoding();
            if (encoding == null)
                JavaCIPUnknownScope.read(m, conn.getInputStream(), url);
            else
                JavaCIPUnknownScope.read(m, new InputStreamReader(conn.getInputStream(), encoding), url);
        } catch (FileNotFoundRuntimeException e) {
            throw new DoesNotExistRuntimeException(url);
        } catch (IORuntimeException e) {
            throw new JenaRuntimeException(e);
        }
    }
}
