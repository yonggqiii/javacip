class c9924414 {

    public void run() {
        JavaCIPUnknownScope.iStream = null;
        try {
            JavaCIPUnknownScope.tryProxy = false;
            URLConnection connection = JavaCIPUnknownScope.url.openConnection();
            if (connection instanceof HttpURLConnection) {
                HttpURLConnection htc = (HttpURLConnection) connection;
                JavaCIPUnknownScope.contentLength = htc.getContentLength();
            }
            InputStream i = connection.getInputStream();
            JavaCIPUnknownScope.iStream = new LoggedInputStream(i, JavaCIPUnknownScope.wa);
        } catch (ConnectRuntimeException x) {
            JavaCIPUnknownScope.tryProxy = true;
            JavaCIPUnknownScope.exception = x;
        } catch (RuntimeException x) {
            JavaCIPUnknownScope.exception = x;
        } finally {
            if (JavaCIPUnknownScope.dialog != null) {
                Thread.yield();
                JavaCIPUnknownScope.dialog.setVisible(false);
            }
        }
    }
}
