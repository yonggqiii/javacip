class c5257755 {

    protected URLConnection openConnection(URL url) throws IORuntimeException {
        if (url == null)
            return null;
        if (!url.getProtocol().equals("nntp"))
            return null;
        if (JavaCIPUnknownScope.m_connection != null) {
            if (JavaCIPUnknownScope.m_connection.getURL().getHost().equals(url.getHost()) && (JavaCIPUnknownScope.m_connection.getURL().getPort() == url.getPort()) && (JavaCIPUnknownScope.m_connection.getURL().getUserInfo().equals(url.getUserInfo()))) {
                return JavaCIPUnknownScope.m_connection;
            }
        }
        JavaCIPUnknownScope.m_connection = new NNTPConnection(url);
        return JavaCIPUnknownScope.m_connection;
    }
}
