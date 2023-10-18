class c8119563 {

    protected boolean checkLink(URL url) {
        try {
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (IORuntimeException e) {
            MsgLog.error("DapParser.checkLink(): IORuntimeException: " + e.toString());
            return false;
        }
    }
}
