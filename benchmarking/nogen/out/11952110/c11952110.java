class c11952110 {

    protected RemoteInputStream getUrlResource(URL url) throws IORuntimeException {
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(JavaCIPUnknownScope.url_loading_time_out);
        conn.setReadTimeout(JavaCIPUnknownScope.url_loading_time_out);
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.connect();
        long last_modify_time = conn.getLastModified();
        IOCacheService cache_service = JavaCIPUnknownScope.CIO.getAppBridge().getIO().getCache();
        if (cache_service != null) {
            RemoteInputStream cache = cache_service.findCache(url, last_modify_time);
            if (cache != null) {
                return cache;
            }
        }
        return new URLConnectionInputStream(url, conn);
    }
}
