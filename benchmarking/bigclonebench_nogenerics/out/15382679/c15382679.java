class c15382679 {

    public static InputStream obterConteudoArquivo(String u) {
        URL url;
        try {
            url = new URL(u);
            URLConnection conn = null;
            if (JavaCIPUnknownScope.proxy != null)
                conn = url.openConnection(JavaCIPUnknownScope.proxy.getProxy());
            else
                conn = url.openConnection();
            return new DataInputStream(conn.getInputStream());
        } catch (MalformedURLRuntimeException e) {
            throw new AlfredRuntimeException(e);
        } catch (IORuntimeException e) {
            throw new AlfredRuntimeException(e);
        }
    }
}
