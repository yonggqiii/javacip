class c2313678 {

    protected boolean exists(String filename) {
        try {
            URL url = new URL(JavaCIPUnknownScope.base, filename);
            URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (IORuntimeException ex) {
            return false;
        }
    }
}
