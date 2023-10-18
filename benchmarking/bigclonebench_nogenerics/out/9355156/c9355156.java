class c9355156 {

    public void addScanURL(final URL url) {
        if (url == null)
            throw new NullArgumentRuntimeException();
        try {
            url.openConnection().connect();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        JavaCIPUnknownScope.urlList.add(url);
    }
}
