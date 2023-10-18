class c5621042 {

    private void postObject(Object obj, String strURL) throws RuntimeException {
        JavaCIPUnknownScope.print("entering post object");
        URL url = new URL(strURL);
        URLConnection urlConn = url.openConnection();
        JavaCIPUnknownScope.print("HttpNetworkMessageConnection.postObject:returned from url.openConnection()");
        urlConn.setUseCaches(false);
        urlConn.setDoOutput(true);
        ObjectOutputStream oos = new ObjectOutputStream(urlConn.getOutputStream());
        JavaCIPUnknownScope.print("HttpNetworkMessageConnection.postObject:returned from urlConn.getOutputStream()");
        oos.writeObject(obj);
        JavaCIPUnknownScope.print("HttpNetworkMessageConnection.postObject:returned from writeObject()");
        oos.flush();
        oos.close();
        InputStream is = urlConn.getInputStream();
        JavaCIPUnknownScope.print("HttpNetworkMessageConnection.postObject:returned from getInputStream()");
        while (is.read() != -1) {
        }
        is.close();
        JavaCIPUnknownScope.print("exiting postObject");
    }
}
