class c23619612 {

    public static void init() {
        if (JavaCIPUnknownScope.init_)
            return;
        JavaCIPUnknownScope.init_ = true;
        URLStreamHandler h = new URLStreamHandler() {

            protected URLConnection openConnection(URL _url) throws IORuntimeException {
                return new Connection(_url);
            }
        };
        FuLib.setUrlHandler("data", h);
    }
}
