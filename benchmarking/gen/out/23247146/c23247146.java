class c23247146 {

    public synchronized HttpURLConnection getTileUrlConnection(int zoom, int tilex, int tiley) throws IORuntimeException {
        HttpURLConnection conn = null;
        try {
            String url = JavaCIPUnknownScope.getTileUrl(zoom, tilex, tiley);
            conn = (HttpURLConnection) new URL(url).openConnection();
        } catch (IORuntimeException e) {
            throw e;
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.error("", e);
            throw new IORuntimeException(e);
        }
        try {
            JavaCIPUnknownScope.i.set("conn", conn);
            JavaCIPUnknownScope.i.eval("addHeaders(conn);");
        } catch (EvalError e) {
            String msg = e.getMessage();
            if (!JavaCIPUnknownScope.AH_ERROR.equals(msg)) {
                JavaCIPUnknownScope.log.error(e.getClass() + ": " + e.getMessage(), e);
                throw new IORuntimeException(e);
            }
        }
        return conn;
    }
}
