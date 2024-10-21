class c20987825 {

    public void run() {
        try {
            URL read = null;
            if (JavaCIPUnknownScope._readURL.indexOf("?") >= 0) {
                read = new URL(JavaCIPUnknownScope._readURL + "&id=" + JavaCIPUnknownScope._id);
            } else {
                read = new URL(JavaCIPUnknownScope._readURL + "?id=" + JavaCIPUnknownScope._id);
            }
            while (JavaCIPUnknownScope._keepGoing) {
                String line;
                while ((line = JavaCIPUnknownScope._in.readLine()) != null) {
                    ConnectionHandlerLocal.DEBUG("< " + line);
                    JavaCIPUnknownScope._linesRead++;
                    JavaCIPUnknownScope._listener.incomingMessage(line);
                }
                if (JavaCIPUnknownScope._linesRead == 0) {
                    JavaCIPUnknownScope.shutdown(true);
                    return;
                }
                if (JavaCIPUnknownScope._keepGoing) {
                    URLConnection urlConn = read.openConnection();
                    urlConn.setUseCaches(false);
                    JavaCIPUnknownScope._in = new DataInputStream(urlConn.getInputStream());
                    JavaCIPUnknownScope._linesRead = 0;
                }
            }
            System.err.println("HttpReaderThread: stopping gracefully.");
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.shutdown(true);
        }
    }
}
