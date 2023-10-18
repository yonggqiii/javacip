class c23213941 {

    protected void writeSnapshot(final String message, final String details) {
        try {
            final URL url = JavaCIPUnknownScope.proxyAddress == null ? new URL(JavaCIPUnknownScope.url_spec) : new URL("http", JavaCIPUnknownScope.proxyAddress, JavaCIPUnknownScope.proxyPort, JavaCIPUnknownScope.url_spec);
            JavaCIPUnknownScope.LOG.info("connect to " + url);
            final URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            final HttpQueryWriter out = new HttpQueryWriter(connection.getOutputStream());
            out.addParameter("error", message);
            out.addParameter("trace", details);
            out.close();
            final InputStream in = connection.getInputStream();
            int c;
            final StringBuffer result = new StringBuffer();
            while ((c = in.read()) != -1) {
                result.append((char) c);
            }
            JavaCIPUnknownScope.LOG.info(result);
            in.close();
        } catch (final UnknownHostRuntimeException e) {
            JavaCIPUnknownScope.LOG.info("could not find host (unknown host) to submit log to");
        } catch (final IORuntimeException e) {
            JavaCIPUnknownScope.LOG.debug("i/o problem submitting log", e);
        }
    }
}
