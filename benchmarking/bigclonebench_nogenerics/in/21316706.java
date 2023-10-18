


class c21316706 {

    @Override
    protected URLConnection openConnection(URL url, Proxy proxy) throws IORuntimeException {
        if ((url == null) || (proxy == null)) {
            throw new IllegalArgumentRuntimeException(Messages.getString("luni.1B"));
        }
        return new HttpsURLConnectionImpl(url, getDefaultPort(), proxy);
    }

}
