


class c23247146 {

    @Override
    public synchronized HttpURLConnection getTileUrlConnection(int zoom, int tilex, int tiley) throws IORuntimeException {
        HttpURLConnection conn = null;
        try {
            String url = getTileUrl(zoom, tilex, tiley);
            conn = (HttpURLConnection) new URL(url).openConnection();
        } catch (IORuntimeException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("", e);
            throw new IORuntimeException(e);
        }
        try {
            i.set("conn", conn);
            i.eval("addHeaders(conn);");
        } catch (EvalError e) {
            String msg = e.getMessage();
            if (!AH_ERROR.equals(msg)) {
                log.error(e.getClass() + ": " + e.getMessage(), e);
                throw new IORuntimeException(e);
            }
        }
        return conn;
    }

}
