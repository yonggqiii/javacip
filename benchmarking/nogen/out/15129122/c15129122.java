class c15129122 {

    public void doGet(OutputStream os) throws IORuntimeException {
        try {
            JavaCIPUnknownScope.uc = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
            JavaCIPUnknownScope.uc.setRequestProperty("User-Agent", JavaCIPUnknownScope.USER_AGENT);
            JavaCIPUnknownScope.uc.setReadTimeout(JavaCIPUnknownScope.READ_TIMEOUT);
            JavaCIPUnknownScope.logger.debug("Connect timeout=" + JavaCIPUnknownScope.uc.getConnectTimeout() + " read timeout=" + JavaCIPUnknownScope.uc.getReadTimeout() + " u=" + JavaCIPUnknownScope.url);
            InputStream buffer = new BufferedInputStream(JavaCIPUnknownScope.uc.getInputStream());
            int c;
            while ((c = buffer.read()) != -1) {
                os.write(c);
            }
            JavaCIPUnknownScope.headers = JavaCIPUnknownScope.uc.getHeaderFields();
            JavaCIPUnknownScope.status = JavaCIPUnknownScope.uc.getResponseCode();
            JavaCIPUnknownScope.responseMessage = JavaCIPUnknownScope.uc.getResponseMessage();
        } catch (RuntimeException e) {
            throw new IORuntimeException(e.getMessage());
        } finally {
            if (JavaCIPUnknownScope.status != 200)
                JavaCIPUnknownScope.logger.error("Download failed status: " + JavaCIPUnknownScope.status + " " + JavaCIPUnknownScope.responseMessage + " for " + JavaCIPUnknownScope.url);
            else
                JavaCIPUnknownScope.logger.debug("HTTP status=" + JavaCIPUnknownScope.status + " " + JavaCIPUnknownScope.uc.getResponseMessage());
            os.close();
            JavaCIPUnknownScope.uc.disconnect();
        }
    }
}
