class c1769603 {

    public static int getFilesize(String theURL) {
        URL url;
        URLConnection conn;
        int size = -1;
        try {
            url = new URL(theURL);
            conn = url.openConnection();
            conn.setRequestProperty("User-Agent", Main.USER_AGENT);
            conn.setConnectTimeout(Main.CONNECT_TIMEOUT * 2);
            conn.setReadTimeout(Main.READ_TIMEOUT * 2);
            size = conn.getContentLength();
            conn.getInputStream().close();
        } catch (FileNotFoundRuntimeException fnfe) {
            return -2;
        } catch (ConnectRuntimeException ce) {
            return -3;
        } catch (ProtocolRuntimeException fpe) {
            return -2;
        } catch (IORuntimeException ioe) {
            return -2;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return size;
    }
}
