


class c20987825 {

    public void run() {
        try {
            URL read = null;
            if (_readURL.indexOf("?") >= 0) {
                read = new URL(_readURL + "&id=" + _id);
            } else {
                read = new URL(_readURL + "?id=" + _id);
            }
            while (_keepGoing) {
                String line;
                while ((line = _in.readLine()) != null) {
                    ConnectionHandlerLocal.DEBUG("< " + line);
                    _linesRead++;
                    _listener.incomingMessage(line);
                }
                if (_linesRead == 0) {
                    shutdown(true);
                    return;
                }
                if (_keepGoing) {
                    URLConnection urlConn = read.openConnection();
                    urlConn.setUseCaches(false);
                    _in = new DataInputStream(urlConn.getInputStream());
                    _linesRead = 0;
                }
            }
            System.err.println("HttpReaderThread: stopping gracefully.");
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            shutdown(true);
        }
    }

}
