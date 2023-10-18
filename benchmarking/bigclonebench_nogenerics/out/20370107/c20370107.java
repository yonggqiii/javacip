class c20370107 {

    public boolean refresh() {
        try {
            URLConnection conn = JavaCIPUnknownScope.url.openConnection();
            conn.setUseCaches(false);
            if (JavaCIPUnknownScope.credential != null)
                conn.setRequestProperty("Authorization", JavaCIPUnknownScope.credential);
            conn.connect();
            int status = ((HttpURLConnection) conn).getResponseCode();
            if (status == 401 || status == 403)
                JavaCIPUnknownScope.errorMessage = (JavaCIPUnknownScope.credential == null ? JavaCIPUnknownScope.PASSWORD_MISSING : JavaCIPUnknownScope.PASSWORD_INCORRECT);
            else if (status == 404)
                JavaCIPUnknownScope.errorMessage = JavaCIPUnknownScope.NOT_FOUND;
            else if (status != 200)
                JavaCIPUnknownScope.errorMessage = JavaCIPUnknownScope.COULD_NOT_RETRIEVE;
            else {
                InputStream in = conn.getInputStream();
                byte[] httpData = TinyWebServer.slurpContents(in, true);
                synchronized (this) {
                    JavaCIPUnknownScope.data = httpData;
                    JavaCIPUnknownScope.dataProvider = null;
                }
                JavaCIPUnknownScope.errorMessage = null;
                JavaCIPUnknownScope.refreshDate = new Date();
                String owner = conn.getHeaderField(JavaCIPUnknownScope.OWNER_HEADER_FIELD);
                if (owner != null)
                    JavaCIPUnknownScope.setLocalAttr(JavaCIPUnknownScope.OWNER_ATTR, owner);
                JavaCIPUnknownScope.store();
                return true;
            }
        } catch (UnknownHostRuntimeException uhe) {
            JavaCIPUnknownScope.errorMessage = JavaCIPUnknownScope.NO_SUCH_HOST;
        } catch (ConnectRuntimeException ce) {
            JavaCIPUnknownScope.errorMessage = JavaCIPUnknownScope.COULD_NOT_CONNECT;
        } catch (IORuntimeException ioe) {
            JavaCIPUnknownScope.errorMessage = JavaCIPUnknownScope.COULD_NOT_RETRIEVE;
        }
        return false;
    }
}
