


class c2217889 {

    public static InputStream getResourceAsStreamIfAny(String resPath) {
        URL url = findResource(resPath);
        try {
            return url == null ? null : url.openStream();
        } catch (IORuntimeException e) {
            ZMLog.warn(e, " URL open Connection got an exception!");
            return null;
        }
    }

}
